package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.util.Log;

import java.util.*;

public class QuizFragment extends Fragment {

//    private static final String[] countries = {
//            "Egypt",
//            "Laos",
//            "Venezuela",
//            "Kenya",
//            "Cuba",
//            "Congo",
//    };

//    private static final String[] countriesContinents = {
//            "Africa",
//            "Asia",
//            "South America",
//            "Africa",
//            "North America",
//            "Africa"
//    };

    private static final String[] continents = {
        "Africa",
        "Asia",
        "Europe",
        "North America",
        "Oceania",
        "South America"
    };

    private int country;

    private CountryData countryData;

    private static int pageCount;

    public QuizFragment() {
        // Required empty public constructor
    }

    public static QuizFragment newInstance(int country) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putInt("Country", country);
        pageCount = country + 1;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            country = getArguments().getInt("Country");
        }

        // initialize the countryData variable
        countryData = new CountryData( getActivity().getApplication() );
        countryData.open();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

        TextView question = view.findViewById(R.id.results);
        // creates the radio buttons and group them into a radio group
        RadioButton option1 = view.findViewById(R.id.option1);
        RadioButton option2 = view.findViewById(R.id.option2);
        RadioButton option3 = view.findViewById(R.id.option3);


        if (pageCount < 7) {
            // retrieve all the different countries
            List<Country> countriesList = countryData.retrieveAllCountries();
        RadioGroup radioGroup = view.findViewById(R.id.options);


        // retrieve all the different countries
        List<Country> countriesList = countryData.retrieveAllCountries();

            Log.d("Quiz Fragment", "" + countriesList.size());

        // generate the random countries
        Random random = new Random();
        Country randomCountry = countriesList.get(random.nextInt(countriesList.size()));

            question.setText("Question " + pageCount + ": Name the continent on which " + randomCountry.getCountry() + " is located.");

//        option1.setText(continents[random.nextInt(continents.length)]);
//        option2.setText(countriesContinents[country]);
//        option3.setText(continents[random.nextInt(continents.length)]);


        // randomly generates the answer choices and make sure there's no repeated answer choices
        List<String> choices = new ArrayList<>();

        while (choices.size() < 3) {

            int i = random.nextInt(6);

            if (!choices.contains(continents[i])) {
                choices.add(continents[i]);
            }
        }

        Log.d("Quiz Fragment", "Random Answer Choices" + choices);

        // add the correct answer choice if it's not already generated
        int correct_choice = random.nextInt(3);
        if (!choices.contains(randomCountry.getContinent())) {
            choices.set(correct_choice, randomCountry.getContinent());

        }

            option1.setText(choices.get(0));
            option2.setText(choices.get(1));
            option3.setText(choices.get(2));
        } else {
            question.setText("End");
            option1.setVisibility(View.INVISIBLE);
            option2.setVisibility(View.INVISIBLE);
            option3.setVisibility(View.INVISIBLE);

        // listens to the radio buttons and check if the user has selected the correct answer choice
        // if answered correctly, increment grade in the parent activity with updateTotalGrade()
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = view.findViewById(checkedId);

                String correct_choice_string = choices.get(correct_choice);

                Log.d("Quiz Fragment", "Correct answer: " + correct_choice_string);

                if (checkedRadioButton != null && checkedRadioButton.getText().toString().equals(choices.get(correct_choice))) {

                    Log.d("Quiz Fragment", "You picked the correct choice");

                    ((QuizActivity)getActivity()).updateTotalGrade();

                } else {
                    // do nothing if its wrong
                }
            }
        });
    }

    public static int getNumberOfVersions() {
        return 6;
    }
}