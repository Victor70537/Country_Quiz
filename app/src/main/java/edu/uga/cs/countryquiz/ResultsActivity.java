package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    private ResultsData resultsData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

//        TextView results = findViewById(R.id.textView);
//
//        resultsData = new ResultsData( getActivity().getApplication() );
//        resultsData.open();
//
//        List<Results> resultsList = resultsData.retrieveAllResults();


    }
}