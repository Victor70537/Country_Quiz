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

    private List<Country> countriesList = new ArrayList<>();

//    private static int pageCount;

    private TextView question;
    private RadioButton option1, option2, option3;
    private RadioGroup radioGroup;

    private int correct_choice;

    public QuizFragment() {
        // Required empty public constructor
    }

    public static QuizFragment newInstance(int country) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putInt("Country", country);
//        pageCount = country+1;
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

        question = view.findViewById(R.id.results);
        // creates the radio buttons and group them into a radio group
        option1 = view.findViewById(R.id.option1);
        option2 = view.findViewById(R.id.option2);
        option3 = view.findViewById(R.id.option3);

        radioGroup = view.findViewById(R.id.options);

        new CountryDBReader().execute();


    }

    // This is an AsyncTask class (it extends AsyncTask) to perform DB reading of job leads, asynchronously.
    private class CountryDBReader extends AsyncTask<Void, List<Country>> {
        // This method will run as a background process to read from db.
        // It returns a list of retrieved JobLead objects.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onCreate callback (the job leads review activity is started).
        @Override
        protected List<Country> doInBackground(Void... params) {
            countriesList = countryData.retrieveAllCountries();

            Log.d("CountryDBReader", "CountryDBReader: Countries retrieved: " + countriesList.size());

            return countriesList;
        }

        @Override
        protected void onPostExecute( List<Country> countriesList ) {

            if (((QuizActivity)getActivity()).getPosition() < 6) {

                Log.d("Quiz Fragment", "Size of countriesList after CountryDBReader().execute(): " + countriesList.size());

                // generate the random countries
                Random random = new Random();
                Country randomCountry = countriesList.get(random.nextInt(countriesList.size()));

                int j = ((QuizActivity)getActivity()).getPosition() + 1;

                question.setText("Question " + j + ": Name the continent on which " + randomCountry.getCountry() + " is located.");



                // randomly generates the answer choices and make sure there's no repeated answer choices
                List<String> choices = new ArrayList<>();

                while (choices.size() < 3) {

                    int i = random.nextInt(6);

                    if (!choices.contains(continents[i])) {
                        choices.add(continents[i]);
                    }
                }

//                Log.d("Quiz Fragment", "Random Answer Choices" + choices);

                // add the correct answer choice if it's not already generated
                correct_choice = random.nextInt(3);
                if (!choices.contains(randomCountry.getContinent())) {
                    choices.set(correct_choice, randomCountry.getContinent());
                } else {
                    if (choices.get(0).equals(randomCountry.getContinent())) {
                        correct_choice = 0;
                    } else if (choices.get(1).equals(randomCountry.getContinent())) {
                        correct_choice = 1;
                    } else if (choices.get(2).equals(randomCountry.getContinent())) {
                        correct_choice = 2;
                    }
                }

                Log.d("Quiz Fragment", "Correct answer for " + randomCountry.getCountry() + ": " + randomCountry.getContinent());

                option1.setText(choices.get(0));
                option2.setText(choices.get(1));
                option3.setText(choices.get(2));

                // listens to the radio buttons and check if the user has selected the correct answer choice
                // if answered correctly, increment grade in the parent activity with updateTotalGrade()
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton checkedRadioButton = getView().findViewById(checkedId);

//                        String correct_choice_string = choices.get(correct_choice);

                        Log.d("CheckedRadioButton", checkedRadioButton.getText().toString());

                        if (checkedRadioButton != null && checkedRadioButton.getText().toString().equals(choices.get(correct_choice))) {

                            Log.d("Quiz Fragment", "You picked the correct choice");

                            ((QuizActivity)getActivity()).updateTotalGrade();

                        } else {
                            // do nothing if its wrong
                        }
                    }
                });
            } else {
                question.setText("End");
                option1.setVisibility(View.INVISIBLE);
                option2.setVisibility(View.INVISIBLE);
                option3.setVisibility(View.INVISIBLE);
            }


        }


    }

    public static int getNumberOfVersions() {
        return 7;
    }
}