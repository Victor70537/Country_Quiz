package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.*;

public class QuizFragment extends Fragment {

    private static final String[] countries = {
            "Egypt",
            "Laos",
            "Venezuela",
            "Kenya",
            "Cuba",
            "Congo",
    };

    private static final String[] countriesContinents = {
            "Africa",
            "Asia",
            "South America",
            "Africa",
            "North America",
            "Africa"
    };

    private static final String[] continents = {
        "Africa",
        "Asia",
        "Europe",
        "North America",
        "Oceania",
        "South America"
    };

    private int country;

    public QuizFragment() {
        // Required empty public constructor
    }

    public static QuizFragment newInstance(int country) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putInt("Country", country);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            country = getArguments().getInt("Country");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

        TextView question = view.findViewById(R.id.question);
        question.setText("Question " + (country + 1) + ": Name the continent on which " + countries[country] + " is located.");

        RadioButton option1 = view.findViewById(R.id.option1);
        RadioButton option2 = view.findViewById(R.id.option2);
        RadioButton option3 = view.findViewById(R.id.option3);

        Random random = new Random();


        option1.setText(continents[random.nextInt(continents.length)]);
        option2.setText(countriesContinents[country]);
        option3.setText(continents[random.nextInt(continents.length)]);
    }

    public static int getNumberOfVersions() {
        return countries.length;
    }
}