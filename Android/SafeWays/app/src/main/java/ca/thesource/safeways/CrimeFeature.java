/** @file CrimeFeature.java
 @author Jash Mehta
 @brief Activity class that represents page for CrimeFeature menu
 @date march 28,2020
 */
package ca.thesource.safeways;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import Eclipse.CrimeSearch;


/**
 * Crime Street Finder Feature Menu
 * Finds the street that forms an intersection
 * with input street and input crime weight
 */
public class CrimeFeature extends AppCompatActivity {
    FloatingActionButton start;
    TextView output;
    EditText crimeRating;
    int crime_rating;
    String inputString;
    String out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_feature);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        start = findViewById(R.id.go);
        output = findViewById(R.id.output);

        final HashMap<String, Object> hashtable = hashify();
        Set<String> streetNames = hashtable.keySet();
        Object[] streetnames_arr = streetNames.toArray();

        final Spinner dropdown = findViewById(R.id.streetNames);
        String[] items = Arrays.copyOf(streetnames_arr, streetnames_arr.length, String[].class);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        crimeRating = (EditText) findViewById(R.id.crimeRating);

        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                inputString = dropdown.getSelectedItem().toString();
                String input = crimeRating.getText().toString();
                if(input.equals("")){
                    input = "0";
                }
                crime_rating = Integer.parseInt(input);
                HashMap<String, Object> hashtable = hashify();
                out = CrimeSearch.search(inputString, crime_rating, hashtable);

                output.setText(out);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private HashMap<String, Object> hashify() {
        HashMap<String,Object> json_data = null;
        try (InputStream reader = getAssets().open("WeightedData.json"))
        {
            //Parse JSON file to a map
            json_data = new ObjectMapper().readValue(reader, HashMap.class);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();


        }
        return json_data;
    }
}
