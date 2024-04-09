package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.util.Log;

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
//        if (getArguments() != null) {
//            country = getArguments().getInt("Country");
//        }

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
        RadioButton option1 = view.findViewById(R.id.option1);
        RadioButton option2 = view.findViewById(R.id.option2);
        RadioButton option3 = view.findViewById(R.id.option3);


        if (pageCount < 7) {
            // retrieve all the different countries
            List<Country> countriesList = countryData.retrieveAllCountries();

            Log.d("Quiz Fragment", "" + countriesList.size());

            // generate the random countries
            Random random = new Random();
//        int randomIndex = random.nextInt(countriesList.size());
            Country randomCountry = countriesList.get(random.nextInt(countriesList.size()));

            question.setText("Question " + pageCount + ": Name the continent on which " + randomCountry.getCountry() + " is located.");

//        option1.setText(continents[random.nextInt(continents.length)]);
//        option2.setText(countriesContinents[country]);
//        option3.setText(continents[random.nextInt(continents.length)]);

            List<String> choices = new ArrayList<>();

            while (choices.size() < 3) {

                int i = random.nextInt(6);

                if (!choices.contains(continents[i])) {
                    choices.add(continents[i]);
                }
            }

            Log.d("Random Answer Choices", choices.toString());

            if (!choices.contains(randomCountry.getContinent())) {
                choices.set(random.nextInt(3), randomCountry.getContinent());
            }

            option1.setText(choices.get(0));
            option2.setText(choices.get(1));
            option3.setText(choices.get(2));
        } else {
            question.setText("End");
            option1.setVisibility(View.INVISIBLE);
            option2.setVisibility(View.INVISIBLE);
            option3.setVisibility(View.INVISIBLE);


        }
    }

    public static int getNumberOfVersions() {
        return countries.length + 1;
    }
}