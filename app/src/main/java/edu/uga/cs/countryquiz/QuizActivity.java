package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

public class QuizActivity extends AppCompatActivity {

    private int totalGrade = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ViewPager2 pager = findViewById( R.id.viewpager );
        QuizPagerAdapter qpAdapter = new QuizPagerAdapter(getSupportFragmentManager(), getLifecycle());
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        pager.setAdapter(qpAdapter);
    }

    public void updateTotalGrade() {
        totalGrade ++;

        Log.d("Quiz Activity", "Total Grade: " + totalGrade);
    }
}