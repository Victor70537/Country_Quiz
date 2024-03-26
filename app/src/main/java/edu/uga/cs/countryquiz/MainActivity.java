package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button quizButton;
    private Button resultsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Need to set text for instructions.

        quizButton = findViewById(R.id.button);
        quizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), QuizActivity.class);
                // send the conversion type info to the child activity
                intent.putExtra("Version", "Quiz");
                v.getContext().startActivity(intent);
            }
        });

        resultsButton = findViewById(R.id.button2);
        resultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResultsActivity.class);
                // send the conversion type info to the child activity
                intent.putExtra("Version", "Results");
                v.getContext().startActivity(intent);
            }
        });
    }
}