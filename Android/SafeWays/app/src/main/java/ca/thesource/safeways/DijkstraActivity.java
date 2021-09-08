/** @file DijkstraActivity.java
 @author Anando Zaman & Daniel Di Cesare
 @brief Activity class that represents page for Dijkstra path computation menu
 @date march 28,2020
 */
package ca.thesource.safeways;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import Eclipse.Vertex;
import Eclipse.runDijkstra;

/** Computes Safest Route via Dijkstra algo
 * The computepath() method at bottom of page calls Dijkstra algo **/
public class DijkstraActivity extends AppCompatActivity {

    //Initialize global variables and some UI elements
    SearchableSpinner searchable_spinner;
    SearchableSpinner searchable_spinner2;
    SearchableSpinner searchable_spinner3;
    SearchableSpinner searchable_spinner4;

    //Initialize Spinner dropdown elements
    String streetstart1 = "I-80 WESTBOUND"; //default street when opening app
    String streetstart2; //street that makes intersection with start
    String streetend1 = "I-80 WESTBOUND";
    String streetend2; //street that makes intersection with end

    //used for computed_path later
    String[] Routes_path = null;

    //loading/progress bar
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dijkstra);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        Button compute_path = findViewById(R.id.GO);
        Button routeinfo = findViewById(R.id.routeinfo);
        Button MapRoute = findViewById(R.id.MapIt);

        searchable_spinner = findViewById(R.id.spinner1);
        searchable_spinner2 = findViewById(R.id.spinner2);
        searchable_spinner3 = findViewById(R.id.spinner3);
        searchable_spinner4 = findViewById(R.id.spinner4);
        

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Read JSON DATA
        final HashMap<String,Object> hashtable = hashify();
        Set<String> Streetnames = hashtable.keySet(); //Read all possible streetnames as Set
        Object[] streetnames_arr = Streetnames.toArray(); //Change Streetnames to an array. This is generic object but is casted to String[] below

        //Extract streets adjacent to streetstart1 for VALID INTERSECTION
        //Default streetstart1 is ("I-80 WESTBOUND")
        Set<String> start_street2 = ((HashMap <String,Object>)hashtable.get(streetstart1)).keySet();
        Object[] start_street2_arr = start_street2.toArray();

        //Extract streets adjacent to streetend1 for VALID INTERSECTION
        //Default intersection street for end is ("I-80 WESTBOUND")
        Set<String> end_street2 = ((HashMap <String,Object>)hashtable.get(streetend1)).keySet();
        Object[] end_street2_arr = end_street2.toArray();

        //parse data from Generic Object Type to String
        String[] searchable_items = Arrays.copyOf(streetnames_arr, streetnames_arr.length, String[].class);
        String[] searchable_start2 = Arrays.copyOf(start_street2_arr, start_street2_arr.length, String[].class);
        String[] searchable_end2 = Arrays.copyOf(end_street2_arr, end_street2_arr.length, String[].class);

        //Searchable street selection spinners setup
        searchable_spinner.setAdapter(new ArrayAdapter<>(DijkstraActivity.this, android.R.layout.simple_spinner_dropdown_item,searchable_items));
        searchable_spinner2.setAdapter(new ArrayAdapter<>(DijkstraActivity.this, android.R.layout.simple_spinner_dropdown_item,searchable_start2));
        searchable_spinner3.setAdapter(new ArrayAdapter<>(DijkstraActivity.this, android.R.layout.simple_spinner_dropdown_item,searchable_items));
        searchable_spinner4.setAdapter(new ArrayAdapter<>(DijkstraActivity.this, android.R.layout.simple_spinner_dropdown_item,searchable_end2));


        //spinners can only do OnItemSelectedListeners, on onclicklisteners
        //However, we can still click on them and save data like DFS_PATHS class due to the XML containing spinnerMode="dropdown"

        //StartStreet intersections are in the form streetstart1@streetstart2
        //Startstreet 1 spinner
        searchable_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //get selected item value
                streetstart1 = parent.getItemAtPosition(position).toString();

                //update streetstart2 spinner dropdown to have streets ONLY adjacent to streetstart1
                Set<String> start_street2 = ((HashMap <String,Object>)hashtable.get(streetstart1)).keySet();
                Object[] start_street2_arr_temp = start_street2.toArray();
                String[] searchable_start2_temp = Arrays.copyOf(start_street2_arr_temp, start_street2_arr_temp.length, String[].class);
                searchable_spinner2.setAdapter(new ArrayAdapter<>(DijkstraActivity.this, android.R.layout.simple_spinner_dropdown_item,searchable_start2_temp));


                Log.d("STREETINFO1", streetstart1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Startstreet 2 spinner
        searchable_spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //get selected item value
                streetstart2 = parent.getItemAtPosition(position).toString();
                Log.d("STREETINFO2", streetstart2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //EndStreet intersections are in the form streetend1@streetend2
        //End Street1 spinner
        searchable_spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //get selected item value
                streetend1 = parent.getItemAtPosition(position).toString();

                //update streetend2 spinner dropdown to have streets ONLY adjacent to streetend1
                Set<String> end_street2 = ((HashMap <String,Object>)hashtable.get(streetend1)).keySet();
                Object[] end_street2_arr_temp = end_street2.toArray();
                String[] searchable_end2_temp = Arrays.copyOf(end_street2_arr_temp, end_street2_arr_temp.length, String[].class);
                searchable_spinner4.setAdapter(new ArrayAdapter<>(DijkstraActivity.this, android.R.layout.simple_spinner_dropdown_item,searchable_end2_temp));

                Log.d("STREETINFO3", streetend1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //End Street2 spinner
        searchable_spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //get selected item value
                streetend2 = parent.getItemAtPosition(position).toString();
                Log.d("STREETINFO4", streetend2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //compute the path
        compute_path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Setup progressbar UI elements
                progressDialog = new ProgressDialog(DijkstraActivity.this);
                progressDialog.setMessage("Loading..."); // Setting Message
                progressDialog.setTitle("Generating route"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);

                //Executes a thread to run computation of Dijkstras in parallel with rest of the code
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            //save the path
                            Routes_path = computepath(streetstart1, streetstart2, streetend1, streetend2).clone();

                            //run a seperate UI thread in parallel with route thread
                            //once route thread complete route_path, the ui thread checks if route_path is valid
                            runOnUiThread(new Runnable() {
                                public void run() {

                                    //display route generation status to the user
                                    if(Routes_path != null){
                                        Toast.makeText(getApplicationContext(), "Path Generation Success", Toast.LENGTH_SHORT).show();
                                    }

                                    else{
                                        Toast.makeText(getApplicationContext(), "Path Generation Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss(); //terminate progressDialog animation

                    }
                }).start();


            }
        });



        //Intent to new page to display path information as ListView
        routeinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Routes_path!=null){
                    Bundle b=new Bundle();
                    b.putStringArray("route_info", Routes_path);
                    Intent i=new Intent(getApplicationContext(), DijkstraRoute.class);
                    i.putExtras(b);
                    startActivity(i);
                }

                //otherwise
                else{
                    Toast.makeText(getApplicationContext(), "Please compute a path first", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //Intent to Gmaps_Dijkstra class for plotting
        MapRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Routes_path!=null){
                    Bundle b=new Bundle();
                    b.putStringArray("Gmaps_path", Routes_path);
                    Intent i=new Intent(getApplicationContext(), Gmaps_Dijkstra.class);
                    i.putExtras(b);
                    startActivity(i);
                }

                //otherwise
                else{
                    Toast.makeText(getApplicationContext(), "Please compute a path first", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    //returns a String array route, given the start and end intersections
    private String[] computepath(String start1, String start2, String end1, String end2){

        Log.d("PATHHH2",start1);
        Log.d("PATHHH3",start2);
        Log.d("PATHHH4",end1);
        Log.d("PATHHH5",end2);

        runDijkstra route = new runDijkstra(start1, start2, end1, end2, hashify());
        String[] GMAPS_path = null;

        try {
            LinkedList<Vertex> path = route.newPath();
            Log.d("PATHHH1",path.toString());

            GMAPS_path = new String[path.size()];
            int j = 0;
            for (Vertex intersection : path) {
                String temp = intersection.toString().substring(0, intersection.toString().indexOf('='));
                String temp2 = intersection.toString().substring(intersection.toString().indexOf('=') + 1);

                //formats specific streets in order to correctly plot on GMaps
                if(temp.equals("01ST ST")){
                    temp = "1ST ST";
                }

                if(temp2.equals("01ST ST")){
                    temp2 = "1ST ST";
                }

                if(temp.equals("02ND ST")){
                    temp = "2ND ST";
                }

                if(temp2.equals("02ND ST")){
                    temp2 = "2ND ST";
                }

                if(temp.equals("03RD ST")){
                    temp = "3RD ST";
                }

                if(temp2.equals("03RD ST")){
                    temp = "3RD ST";
                }


                if(temp.equals("04TH ST")){
                    temp = "4TH ST";
                }

                if(temp2.equals("04TH ST")){
                    temp2 = "4TH ST";
                }

                if(temp.equals("05TH ST")){
                    temp = "5TH ST";
                }

                if(temp2.equals("05TH ST")){
                    temp2 = "5TH ST";
                }

                if(temp.equals("06TH ST")){
                    temp = "6TH ST";
                }

                if(temp2.equals("06TH ST")){
                    temp2 = "6TH ST";
                }

                if(temp.equals("07TH ST")){
                    temp = "7TH ST";
                }

                if(temp2.equals("07TH ST")){
                    temp2 = "7TH ST";
                }

                if(temp.equals("08TH ST")){
                    temp = "8TH ST";
                }

                if(temp2.equals("08TH ST")){
                    temp2 = "8TH ST";
                }

                if(temp.equals("09TH ST")){
                    temp = "9TH ST";
                }

                if(temp2.equals("09TH ST")){
                    temp2 = "9TH ST";
                }

                GMAPS_path[j] = "'" + temp + "'@'" + temp2 + "'";
                j++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return GMAPS_path;
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
