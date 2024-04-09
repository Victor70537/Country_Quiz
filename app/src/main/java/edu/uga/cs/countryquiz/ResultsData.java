package edu.uga.cs.countryquiz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ResultsData {

    private static final String DEBUG_TAG = "ResultsData";

    private SQLiteDatabase db;

    private static SQLiteOpenHelper resultsDBHelper;

    private static final String[] allColumns = {
            ResultsDBHelper.COLUMN_ID,
            ResultsDBHelper.COLUMN_GRADE,
    };

    public ResultsData ( Context context ) {
        this.resultsDBHelper = ResultsDBHelper.getInstance( context );
    }

    // Open the database
    public void open() {
        db = resultsDBHelper.getWritableDatabase();
        Log.d( DEBUG_TAG, "JobLeadsData: db open" );
    }

    // Close the database
    public void close() {
        if( resultsDBHelper != null ) {
            resultsDBHelper.close();
            Log.d(DEBUG_TAG, "JobLeadsData: db closed");
        }
    }

    public boolean isDBOpen()
    {
        return db.isOpen();
    }

    // Retrieve all results and return them as a List.
    // This is how we restore persistent objects stored as rows in the country table in the database.
    // For each retrieved row, we create a new Country (Java POJO object) instance and add it to the list.
    public List<Results> retrieveAllResults() {

        // create the new country arraylist
        ArrayList<Results> results_list = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        try {
            // Execute the select query and get the Cursor to iterate over the retrieved rows
            cursor = db.query( ResultsDBHelper.TABLE_RESULTS, allColumns,
                    null, null, null, null, null );


            Log.d(DEBUG_TAG, ""+ cursor.getCount());

            // collect all job leads into a List
            if( cursor != null && cursor.getCount() > 0 ) {

                while( cursor.moveToNext() ) {

                    if( cursor.getColumnCount() >= 2) {

                        // get all attribute values of this job lead
                        columnIndex = cursor.getColumnIndex( ResultsDBHelper.COLUMN_ID );
                        int id = cursor.getInt( columnIndex );
                        columnIndex = cursor.getColumnIndex( ResultsDBHelper.COLUMN_GRADE);
                        int grade = cursor.getInt( columnIndex );

                        // create a new JobLead object and set its state to the retrieved values
                        Results results = new Results( grade );
                        results.setId(id); // set the id (the primary key) of this object
                        // add it to the list
                        results_list.add( results );
                        Log.d(DEBUG_TAG, "Retrieved results: " + results);
                    }
                }
            }


            if( cursor != null )
                Log.d( DEBUG_TAG, "Number of records from DB: " + cursor.getCount() );
            else
                Log.d( DEBUG_TAG, "Number of records from DB: 0" );
        }
        catch( Exception e ){
            Log.d( DEBUG_TAG, "Exception caught: " + e );
        }
        finally{
            // we should close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        // return a list of retrieved job leads
        return results_list;
    }
}
