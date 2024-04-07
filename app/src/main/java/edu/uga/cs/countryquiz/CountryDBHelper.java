package edu.uga.cs.countryquiz;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.AccessControlContext;
import java.util.Scanner;

public class CountryDBHelper extends SQLiteOpenHelper {

    // Define your database name and version
    private static final String DATABASE_NAME = "country_continent.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DEBUG_TAG = "CountryDBHelper";

    private Context context;

    public static final String TABLE_COUNTRY_CONTINENT = "country_continent";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_COUNTRY = "Country";
    public static final String COLUMN_CONTINENT = "Continent";

    private static CountryDBHelper helperInstance;

    // Define your table creation SQL statements
    private static final String CREATE_COUNTRY_CONTINENT =
            "create table " + TABLE_COUNTRY_CONTINENT+ " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY, "
                    + COLUMN_COUNTRY + " TEXT, "
                    + COLUMN_CONTINENT + " TEXT "
                    + ")";


    public CountryDBHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context.getApplicationContext();
    }

    public static synchronized CountryDBHelper getInstance( Context context ) {
        // check if the instance already exists and if not, create the instance
        if( helperInstance == null ) {
            helperInstance = new CountryDBHelper ( context.getApplicationContext() );
        }
        return helperInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create your tables when the database is created

        db.execSQL( CREATE_COUNTRY_CONTINENT );
        Log.d( DEBUG_TAG, "Table " + TABLE_COUNTRY_CONTINENT + " created" );

        // Insert the data to the table
//        try (InputStream inputStream = context.getResources().openRawResource(R.raw.sqlitecommands)) {
//            String SQLITE_COMMANDS = new Scanner(inputStream).useDelimiter("\\A").next();
//
//            db.execSQL( SQLITE_COMMANDS );
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Handle error: could not read the file
//        }


        // Read the CSV and insert all the data

        InputStream inputStream = context.getResources().openRawResource(R.raw.country_continent);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        try {
            // Assuming the first row contains column headers
            reader.readLine(); // Skip the first line
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // Split CSV row by comma
                // Insert data into database
                db.execSQL("INSERT INTO " + TABLE_COUNTRY_CONTINENT + " (ID, Country, Continent) " + " VALUES (?, ?, ?)", parts);
            }
        } catch (IOException e) {
            Log.e("CSVReader", "Error reading CSV file", e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "drop table if exists " + TABLE_COUNTRY_CONTINENT );
        onCreate( db );
        Log.d( DEBUG_TAG, "Table " + TABLE_COUNTRY_CONTINENT + " upgraded" );
    }


}
