package edu.uga.cs.countryquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ResultsActivity extends AppCompatActivity {


    private TextView displayResults;
    private ResultsData resultsData;
    private List<Results> resultsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        displayResults = findViewById(R.id.textView);

        resultsData = new ResultsData( getApplication() );
        resultsData.open();

        new ResultsDBReader().execute();

    }

    private class ResultsDBReader extends AsyncTask<Void, List<Results>> {
        // This method will run as a background process to read from db.
        // It returns a list of retrieved Country objects.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onCreate callback (the quiz activity is started).
        @Override
        protected List<Results> doInBackground(Void... params) {
            resultsList = resultsData.retrieveAllResults();

            // prints out all the retrieved countries
            Log.d("CountryDBReader", "CountryDBReader: Countries retrieved: " + resultsList.size());

            return resultsList;
        }

        @Override
        protected void onPostExecute(List<Results> resultsList) {

            String resultsString = "";

            for (int i = 0; i < resultsList.size(); i++) {

                resultsString = resultsString + resultsList.get(i) + "\n";

            }

            displayResults.setText(resultsString);

            }
        }

}