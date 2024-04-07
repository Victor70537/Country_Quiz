package edu.uga.cs.countryquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CountryData {

    private static final String DEBUG_TAG = "CountryData";

    private SQLiteDatabase db;

    private static SQLiteOpenHelper countryDBHelper;

    private static final String[] allColumns = {
        CountryDBHelper.COLUMN_ID,
        CountryDBHelper.COLUMN_COUNTRY,
        CountryDBHelper.COLUMN_CONTINENT
    };

    public CountryData ( Context context ) {
        this.countryDBHelper = CountryDBHelper.getInstance( context );
    }

    // Open the database
    public void open() {
        db = countryDBHelper.getWritableDatabase();
        Log.d( DEBUG_TAG, "JobLeadsData: db open" );
    }

    // Close the database
    public void close() {
        if( countryDBHelper != null ) {
            countryDBHelper.close();
            Log.d(DEBUG_TAG, "JobLeadsData: db closed");
        }
    }

    public boolean isDBOpen()
    {
        return db.isOpen();
    }

    // Retrieve all countries and return them as a List.
    // This is how we restore persistent objects stored as rows in the country table in the database.
    // For each retrieved row, we create a new Country (Java POJO object) instance and add it to the list.
    public List<Country> retrieveAllJobLeads() {

        // create the new country arraylist
        ArrayList<Country> countries = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        try {
            // Execute the select query and get the Cursor to iterate over the retrieved rows
            cursor = db.query( CountryDBHelper.TABLE_COUNTRY_CONTINENT, allColumns,
                    null, null, null, null, null );

            // collect all job leads into a List
            if( cursor != null && cursor.getCount() > 0 ) {

                while( cursor.moveToNext() ) {

                    if( cursor.getColumnCount() >= 5) {

                        // get all attribute values of this job lead
                        columnIndex = cursor.getColumnIndex( CountryDBHelper.COLUMN_ID );
                        int id = cursor.getInt( columnIndex );
                        columnIndex = cursor.getColumnIndex( CountryDBHelper.COLUMN_COUNTRY );
                        String country_string = cursor.getString( columnIndex );
                        columnIndex = cursor.getColumnIndex( CountryDBHelper.COLUMN_CONTINENT );
                        String continent = cursor.getString( columnIndex );

                        // create a new JobLead object and set its state to the retrieved values
                        Country country = new Country( country_string, continent );
                        country.setId(id); // set the id (the primary key) of this object
                        // add it to the list
                        countries.add( country );
                        Log.d(DEBUG_TAG, "Retrieved country: " + country);
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
        return countries;
    }


}
