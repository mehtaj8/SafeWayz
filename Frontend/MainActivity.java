package ca.thesource.safeways;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


/**LANDING PAGE. CONSISTS OF BUTTONS REDIRECTING TO DIJKSTRA PATHS, DFS PATHS, AND JASHES EXTRA FEATURE**/
/**HASHIFY METHODS WILL BE FOUND IN THOSE RESPECTIVE FEATURE ACTIVITIES/CLASSES**/

public class MainActivity extends AppCompatActivity {

    //UI elements for the buttons
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

        //on-click, extract specific component
        button_DFS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dfs_page = new Intent(getApplicationContext(), DFS_PATHS.class);
                startActivity(dfs_page);
            }
        });
    }

}
