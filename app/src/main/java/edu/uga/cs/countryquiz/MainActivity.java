package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button quizButton;
    private Button resultsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView instructions = findViewById(R.id.instructions);
        instructions.setText("Get ready to learn countries and continents!\n\nBelow are two buttons."
                + "The first button takes you to a six question quiz of random countries. The second button takes"
                + "takes you to a page with your past quiz results.\n\nGoodluck and safe travels!");

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