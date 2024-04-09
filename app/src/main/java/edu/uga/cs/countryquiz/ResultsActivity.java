package edu.uga.cs.countryquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

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

        resultsList = resultsData.retrieveAllResults();

        displayResults.setText(resultsList.toString());


    }


}