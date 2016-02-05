package com.example.katherine.myapplication2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static HashMap<String, Double> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText numRepsInput = (EditText) findViewById(R.id.editText);
        final EditText caloriesBurnedDisplay = (EditText) findViewById(R.id.editText2);
        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner);
        final Button updateButton = (Button) findViewById(R.id.button);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /* Populate exercise hashmap */
        exercises = new HashMap<String, Double>();
        exercises.put("Pushups (reps)", 100.0 / 350.0);
        exercises.put("Situps (reps)", 100.0 / 200.0);
        exercises.put("Jumping Jacks (minutes)", 100.0 / 10.0);
        exercises.put("Jogging (minutes)", 100.0 / 12.0);

        ArrayList<String> exerciseStrings = new ArrayList<String>(exercises.keySet());
        ArrayAdapter<String> exerciseAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exerciseStrings);
        exerciseAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(exerciseAdaptor);

        final ArrayList<String> numRepsPerCalorieArray = new ArrayList<>();
        ArrayAdapter<String> exerciseAdaptor2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, numRepsPerCalorieArray);
        exerciseAdaptor2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(exerciseAdaptor2);


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(numRepsInput)) {
                    caloriesBurnedDisplay.setText("0");
                    return;
                }

                Double numCaloriesPerRep = exercises.get(spinner1.getSelectedItem());
                Double numReps = Double.parseDouble(numRepsInput.getText().toString());
                Double numCaloriesBurned = numCaloriesPerRep * numReps;
                caloriesBurnedDisplay.setText(String.valueOf(numCaloriesPerRep * numReps));

                Double equivalentRepsFromCalories = (1.0 / exercises.get(spinner1.getSelectedItem())) * numCaloriesBurned;
                numRepsPerCalorieArray.add(String.valueOf(equivalentRepsFromCalories) + " " + spinner1.getSelectedItem());


            }
        });

/*
        numRepsInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                return;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                return;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(isEmpty(numRepsInput)) {
                    caloriesBurnedDisplay.setText("0");
                    return;
                }

                Double numCaloriesPerRep = exercises.get(spinner1.getSelectedItem());
                Double numReps = Double.parseDouble(numRepsInput.getText().toString());
                String numCaloriesBurned = String.valueOf(numCaloriesPerRep * numReps);
                caloriesBurnedDisplay.setText(numCaloriesBurned);
            }

        });
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
