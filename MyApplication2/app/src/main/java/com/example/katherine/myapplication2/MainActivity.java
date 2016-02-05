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
import java.util.Iterator;

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
        final EditText equivalences = (EditText) findViewById(R.id.editText3);


        /* Populate exercise hashmap */
        exercises = new HashMap<String, Double>();
        exercises.put("Pushups (reps)", 100.0 / 350.0);
        exercises.put("Situps (reps)", 100.0 / 200.0);
        exercises.put("Squats (reps)", 100.0 / 225.0);
        exercises.put("Leg-lifts (minutes)", 100.0 / 25.0);
        exercises.put("Planks (minutes)", 100.0 / 25.0);
        exercises.put("Jumping Jacks (minutes)", 100.0 / 10.0);
        exercises.put("Pullups (Reps)", 1.0);
        exercises.put("Cycling (minutes)", 100.0/12.0);
        exercises.put("Walking (minutes)", 100.0/20.0);
        exercises.put("Jogging (minutes)", 100.0 / 12.0);
        exercises.put("Swimming (minutes)", 100.0 / 13.0);
        exercises.put("Stair Climbing (minutes)", 100.0 / 15.0);

        ArrayList<String> exerciseStrings = new ArrayList<String>(exercises.keySet());
        ArrayAdapter<String> exerciseAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exerciseStrings);
        exerciseAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(exerciseAdaptor);

        caloriesBurnedDisplay.setText("0");

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equivalences.setText("");

                if(isEmpty(numRepsInput)) {
                    caloriesBurnedDisplay.setText("0");
                    return;
                }

                String selectedExercise = (String) spinner1.getSelectedItem();
                Double numCaloriesPerRep = exercises.get(selectedExercise);
                Double numReps = Double.parseDouble(numRepsInput.getText().toString());
                Double numCaloriesBurned = numCaloriesPerRep * numReps;
                caloriesBurnedDisplay.setText(String.valueOf(numCaloriesPerRep * numReps));

                /* calculate equivalencies */

                Iterator it = exercises.entrySet().iterator();
                while (it.hasNext()) {
                    HashMap.Entry pair = (HashMap.Entry) it.next();
                    String currentExercise = (String) pair.getKey();
                    if(!currentExercise.equals(selectedExercise)) {
                        Double equivalentRepsFromCalories = (1.0 / ((Double) pair.getValue())) * numCaloriesBurned;
                        equivalences.append(String.format("%.1f", equivalentRepsFromCalories) + " " + currentExercise + "\n");
                    }
                }

            }
        });

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
