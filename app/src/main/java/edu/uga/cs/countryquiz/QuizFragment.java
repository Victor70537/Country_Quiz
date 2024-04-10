package edu.uga.cs.countryquiz;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

//    private int country;

    private CountryData countryData;
    private ResultsData resultsData = null;
    private List<Country> countriesList = new ArrayList<>();

//    private static int pageCount;

    private TextView question;
    private RadioButton option1, option2, option3;
    private RadioGroup radioGroup;
    private Button button;

    private int correct_choice;
    String dateString;
    int total;

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
        super.onViewCreated(view, savedInstanceState);

        // get the questions
        question = view.findViewById(R.id.results);

        // creates the radio buttons and group them into a radio group
        option1 = view.findViewById(R.id.option1);
        option2 = view.findViewById(R.id.option2);
        option3 = view.findViewById(R.id.option3);

        radioGroup = view.findViewById(R.id.options);

        // creates the buttons for going back home
        button = view.findViewById(R.id.button4);
        button.setOnClickListener(new SaveButtonClickListener());

        // executes reading database asynchronously
        new CountryDBReader().execute();
    }



    // This is an AsyncTask class (it extends AsyncTask) to perform DB reading of countries asynchronously.
    private class CountryDBReader extends AsyncTask<Void, List<Country>> {
        // This method will run as a background process to read from db.
        // It returns a list of retrieved Country objects.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onCreate callback (the quiz activity is started).
        @Override
        protected List<Country> doInBackground(Void... params) {
            countriesList = countryData.retrieveAllCountries();

            // prints out all the retrieved countries
            Log.d("CountryDBReader", "CountryDBReader: Countries retrieved: " + countriesList.size());

            return countriesList;
        }

        @Override
        protected void onPostExecute(List<Country> countriesList) {

            // this is to check if we should show the quiz or the results
            if (((QuizActivity) getActivity()).getPosition() < 6) {

                // sets button to invisible until necessary
                button.setVisibility(View.INVISIBLE);

                // testing
//                Log.d("Quiz Fragment", "Size of countriesList after CountryDBReader().execute(): " + countriesList.size());

                // generate the random countries
                Random random = new Random();
                Country randomCountry = countriesList.get(random.nextInt(countriesList.size()));

                // get the correct question index
                int j = ((QuizActivity) getActivity()).getPosition() + 1;

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

                // testing
                Log.d("Quiz Fragment", "Correct answer for " + randomCountry.getCountry() + ": " + randomCountry.getContinent());

                // sets the texts for the answer choices
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

                        // testing
                        Log.d("CheckedRadioButton", checkedRadioButton.getText().toString());

                        if (checkedRadioButton != null && checkedRadioButton.getText().toString().equals(choices.get(correct_choice))) {

                            // testing
                            Log.d("Quiz Fragment", "You picked the correct choice");

                            ((QuizActivity) getActivity()).updateTotalGrade();

                        } else {
                            // do nothing if its wrong
                        }
                    }
                });
            }
            // if the user is on the last page
            else {

                // displays the current date and the results of the current quiz
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Date date = new Date();
                dateString = dateFormat.format(date);

                total = ((QuizActivity) getActivity()).getTotalGrade();
                question.setText("Date: " + dateString + "\n\nResults: " + total + "/6");

                option1.setVisibility(View.INVISIBLE);
                option2.setVisibility(View.INVISIBLE);
                option3.setVisibility(View.INVISIBLE);


                // no idea why this is here
                resultsData = new ResultsData(getActivity());
                resultsData.open();

//            Results results = new Results(dateFormat.format(date), total);
//            new ResultsDBWriter().execute(results);
            }
        }
    }

    // for saving the results
    private class SaveButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick( View v ) {
            Results results = new Results(dateString, total);

            Intent intent = new Intent( v.getContext(), MainActivity.class );
            v.getContext().startActivity( intent );


            // Store this new job lead in the database asynchronously,
            // without blocking the UI thread.
            new ResultsDBWriter().execute( results );
        }
    }

    public class ResultsDBWriter extends AsyncTask<Results, Results> {

        // This method will run as a background process to write into db.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onClick listener of the Save button.
        @Override
        protected Results doInBackground(Results... results) {
            resultsData.storeResult(results[0]);
            return results[0];
        }

        // This method will be automatically called by Android once the writing to the database
        // in a background process has finished.  Note that doInBackground returns a JobLead object.
        // That object will be passed as argument to onPostExecute.
        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
        @Override
        protected void onPostExecute(Results results) {
            // Show a quick confirmation message
            Toast.makeText(getActivity(), "Results created with grade of " + results.getGrade(),
                    Toast.LENGTH_SHORT).show();

            // Clear the EditTexts for next use.

            Log.d("Adding Results", "Results saved: " + results);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // open the database in onResume
        if( resultsData != null )
            resultsData.open();
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle( getResources().getString( R.string.app_name ) );
    }

    // We need to save job leads into a file as the activity stops being a foreground activity
    @Override
    public void onPause() {
        Log.d( "Adding Results", "AddJobLeadFragment.onPause()" );
        super.onPause();
        // close the database in onPause
        if( resultsData != null )
            resultsData.close();
    }

    public static int getNumberOfVersions() {
        return 7;
    }
}