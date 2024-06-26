package edu.uga.cs.countryquiz;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class QuizPagerAdapter extends FragmentStateAdapter {
    public QuizPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle ) {
        super(fragmentManager, lifecycle);
    }

    @Override
    public Fragment createFragment(int position) {

        Log.d("QuizPagerAdapter", "Quiz Pager Adapter Position: " + position);

        return QuizFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {

        return QuizFragment.getNumberOfVersions();
    }

}
