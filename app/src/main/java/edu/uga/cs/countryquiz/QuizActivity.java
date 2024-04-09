package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

public class QuizActivity extends AppCompatActivity {

    private int totalGrade = 0;
    ViewPager2 pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        pager = findViewById( R.id.viewpager );
        QuizPagerAdapter qpAdapter = new QuizPagerAdapter(getSupportFragmentManager(), getLifecycle());
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        pager.setAdapter(qpAdapter);
    }

    public void updateTotalGrade() {
        totalGrade ++;

        Log.d("Quiz Activity", "Total Grade: " + totalGrade);
    }

    public int getTotalGrade() {
        return totalGrade;
    }

    public int getPosition() {
        return pager.getCurrentItem();
    }

}