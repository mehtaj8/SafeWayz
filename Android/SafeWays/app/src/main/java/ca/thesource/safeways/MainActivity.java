/** @file MainActivity.java
 @author Anando Zaman, Daniel Di Cesare, and Jash Mehta
 @brief Homepage activity of the application
 @date march 28,2020
 */
package ca.thesource.safeways;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Homepage/start-up page for application
 * Consists of 4 button options
 * Dijkstra, DFS, Crime Search, GMaps
 * */
public class MainActivity extends AppCompatActivity {

    //Initalize UI elements for the buttons
    Button button_DFS;
    Button button_maps;
    Button button_dijk;
    Button button_crime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connect the UI elements by their ID
        button_DFS = (Button) findViewById(R.id.DFS);
        button_maps = (Button) findViewById(R.id.MAPS);
        button_dijk = (Button) findViewById(R.id.DIJKSTRA);
        button_crime = (Button) findViewById(R.id.crimeSearch);

        //Transition to Extra Feature
        button_crime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent Crimepage = new Intent(getApplicationContext(), CrimeFeature.class);
                startActivity(Crimepage);
            }
        });

        //Transition to GMaps page
        button_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Gmapspage = new Intent(getApplicationContext(), Gmaps.class);
                startActivity(Gmapspage);
            }
        });

        //Transition to Dijkstra page
        button_dijk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Dijkstrapage = new Intent(getApplicationContext(), DijkstraActivity.class);
                startActivity(Dijkstrapage);
            }
        });

        //Transition to DFS_PATHS class
        button_DFS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dfs_page = new Intent(getApplicationContext(), DFS_PATHS.class);
                startActivity(dfs_page);
            }
        });
    }

}
