package ca.thesource.safeways;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import Eclipse.Vertex;
import Eclipse.runDijkstra;

public class DijkstraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dijkstra);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        Button dykstraGO = findViewById(R.id.GO);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Read JSON DATA
        HashMap<String,Object> hashtable = hashify();
        Set<String> Streetnames = hashtable.keySet(); //Read all possible streetnames as Set
        Object[] streetnames_arr = Streetnames.toArray(); //Change Streetnames to an array. This is generic object but is casted to String[] below

        //get the spinner from the xml.
        final Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = Arrays.copyOf(streetnames_arr, streetnames_arr.length, String[].class) ; //Cast streetnames_arr to String[]
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        final Spinner dropdown2 = findViewById(R.id.spinner2);
        String[] items2 = Arrays.copyOf(streetnames_arr, streetnames_arr.length, String[].class);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        dropdown2.setAdapter(adapter2);

        //get the spinner from the xml.
        final Spinner dropdown3 = findViewById(R.id.spinner3);
        String[] items3 = Arrays.copyOf(streetnames_arr, streetnames_arr.length, String[].class);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items3);
        dropdown3.setAdapter(adapter3);

        final Spinner dropdown4 = findViewById(R.id.spinner4);
        String[] items4 = Arrays.copyOf(streetnames_arr, streetnames_arr.length, String[].class);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items4);
        dropdown4.setAdapter(adapter4);


        //SWITCH TO DYKSTRA MAPS
        dykstraGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the path
                String start1 = (String) dropdown.getSelectedItem();
                String start2 = (String) dropdown2.getSelectedItem();
                String start3 = (String) dropdown3.getSelectedItem();
                String start4 = (String) dropdown4.getSelectedItem();

                //FOR TESTING, REMOVE LATER
                start1 = "15TH";
                start2 = "IRVING";
                start3 = "14TH";
                start4 = "IRVING";

                runDijkstra route = new runDijkstra(start1, start2, start3, start4, hashify());
                try {
                    LinkedList<Vertex> path = route.newPath();

                    String[] GMAPS_path = new String[path.size()];
                    int j = 0;
                    for (Vertex intersection : path) {
                        String street1 = intersection.toString().substring(0, intersection.toString().indexOf(' '));
                        String street2 = intersection.toString().substring(intersection.toString().indexOf('=') + 1);
                        GMAPS_path[j] = "'" + street1+"'@'"+street2 + "'";
                        j++;
                    }
                    //Send new formated path data to Gmaps class
                    Bundle b=new Bundle();
                    b.putStringArray("Gmaps_path", GMAPS_path);
                    Intent i=new Intent(getApplicationContext(), Gmaps_Dijkstra.class);
                    i.putExtras(b);
                    startActivity(i);


                } catch (IOException e) {
                    e.printStackTrace();
                }
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


    //Generates a Hashtable
    private HashMap<String,Object> hashify(){

        HashMap<String,Object> json_data = null;
        try (InputStream reader = getAssets().open("WeightedData.json"))
        {
            //Parse JSONfile to a map
            json_data = new ObjectMapper().readValue(reader, HashMap.class);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();


        }
        return json_data;
    }

}
