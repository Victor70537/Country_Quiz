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

    private CountryData countryData;

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

        RadioButton option1 = view.findViewById(R.id.option1);
        RadioButton option2 = view.findViewById(R.id.option2);
        RadioButton option3 = view.findViewById(R.id.option3);

        // retrieve all the different countries
        List<Country> countriesList = countryData.retrieveAllCountries();

        Log.d("Quiz Fragment", "" + countriesList.size());

        // generate the random countries
        Random random = new Random();
//        int randomIndex = random.nextInt(countriesList.size());
        Country randomCountry = countriesList.get(random.nextInt(countriesList.size()));

        TextView question = view.findViewById(R.id.question);
        question.setText("Question " + (country + 1) + ": Name the continent on which " + randomCountry.getCountry() + " is located.");

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


    }

    public static int getNumberOfVersions() {
        return countries.length;
    }
}