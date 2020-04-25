/** @file DFS_PATHS.java
 @author Anando Zaman
 @brief Activity class that represents page for DFS path computation menu
 @date march 28,2020
 */
package ca.thesource.safeways;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import Eclipse.graphTraversal;
import Eclipse.paths_sort;

/**
 * DFS_PATHS Menu page that executes DFS_ALLPATHS to compute
 * shortest path from all possible paths between two streets
 */
public class DFS_PATHS extends AppCompatActivity {

    FloatingActionButton go;
    TextView output;
    ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dfs__paths);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Setup UI elements
        output = findViewById(R.id.OUTPUT);
        loading = (ProgressBar) findViewById(R.id.progressBar3);
        go = findViewById(R.id.GO);

        final HashMap<String, Object> hashtable = hashify();
        Set<String> Streetnames = hashtable.keySet();
        Object[] streetnames_arr = Streetnames.toArray();

        //get the spinner from the xml.
        final Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = Arrays.copyOf(streetnames_arr, streetnames_arr.length, String[].class);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        final Spinner dropdown2 = findViewById(R.id.spinner2);
        String[] items2 = Arrays.copyOf(streetnames_arr, streetnames_arr.length, String[].class);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        dropdown2.setAdapter(adapter2);

        final Spinner dropdown3 = findViewById(R.id.spinner3);
        Integer[] items3 = {4,5,6,7};
        ArrayAdapter<Integer> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items3);
        dropdown3.setAdapter(adapter3);


        //Execute DFS for path computation
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int path_size = (Integer) dropdown3.getSelectedItem(); //get selected start item
                final graphTraversal algorithm = new graphTraversal(path_size,hashtable);
                String start = dropdown.getSelectedItem().toString(); //get selected start item
                String end = dropdown2.getSelectedItem().toString(); //get selected end item

                loading.setProgress(30);
                algorithm.DFS(start, end);
                loading.setProgress(60);
                try{
                    Vector<String> least_crime_path = paths_sort.paths_crime_sorted(algorithm.getPaths(), hashtable);

                    //PREPERATION OF DATA FOR GMAPS ACTIVITY IN THE FORM [street1@street2, street2@street3, street3@street4,...streetN-1,streetN]

                    //if no streets
                    if(least_crime_path.size()==0){
                        output.setText("No path exists");
                    }


                    String[] GMAPS_path;

                    //if atleast 2 streets or more, then an intersection forms
                    if(least_crime_path.size()>=2){
                        GMAPS_path = new String[least_crime_path.size()-1];
                        for(int i = 0; i<least_crime_path.size()-1; i++){
                            String temp = least_crime_path.get(i);
                            String temp2 = least_crime_path.get(i+1);

                            //format the data in order to correctly plot on GMaps
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
                            temp = "'"+temp+"'";
                            temp2 = "'"+temp2+"'";

                            GMAPS_path[i] = temp + "@" + temp2;

                            //GMAPS_path[i] = temp.replaceAll("\\s+","") +",SANFRANCISCO"+ "@" + least_crime_path.get(i+1).replaceAll("\\s+","")+",SANFRANCISCO";
                        }
                        loading.setProgress(100); //update progress bar to 100% full
                        output.setText(Arrays.toString(GMAPS_path)); //display path to the user

                        //Send new formated path data to Gmaps class
                        Bundle b=new Bundle();
                        b.putStringArray("Gmaps_path", GMAPS_path);
                        Intent i=new Intent(getApplicationContext(), Gmaps.class);
                        i.putExtras(b);
                        startActivity(i);
                    }

                    //if only one street, then intersection with itself, no need to plot
                    else if(least_crime_path.size()>0){
                        output.setText(least_crime_path.toString());
                    }

                }
                catch (Exception e) {
                    loading.setProgress(0); //reset to 0
                    Toast.makeText(getApplicationContext(), "Please enter larger size or different street", Toast.LENGTH_SHORT).show();
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
