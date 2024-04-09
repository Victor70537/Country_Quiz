package edu.uga.cs.countryquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ResultsDBHelper extends SQLiteOpenHelper {
    private static final String DEBUG_TAG = "ResultsDBHelper";

    // Define your database name and version
    private static final String DATABASE_NAME = "results.db";
    private static final int DATABASE_VERSION = 1;

    private Context context;

    public static final String TABLE_RESULTS = "results";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_GRADE = "Grade";

    private static ResultsDBHelper helperInstance;

    // Define your table creation SQL statements
    private static final String CREATE_RESULTS =
            "create table " + TABLE_RESULTS + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY, "
                    + COLUMN_GRADE + " INTEGER "
                    + ")";

    public ResultsDBHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context.getApplicationContext();
    }

    public static synchronized ResultsDBHelper getInstance( Context context ) {
        // check if the instance already exists and if not, create the instance
        if( helperInstance == null ) {
            helperInstance = new ResultsDBHelper ( context.getApplicationContext() );
        }
        return helperInstance;
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL( CREATE_RESULTS );
        Log.d( DEBUG_TAG, "Table " + TABLE_RESULTS + " created" );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( "drop table if exists " + TABLE_RESULTS );
        onCreate( db );
        Log.d( DEBUG_TAG, "Table " + TABLE_RESULTS + " upgraded" );
    }

}
