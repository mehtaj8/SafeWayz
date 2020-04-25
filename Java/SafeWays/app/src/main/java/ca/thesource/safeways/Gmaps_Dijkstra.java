/** @file Gmaps_Dijkstra.java
 @author Anando Zaman & Daniel Di Cesare
 @brief Activity class to showcase general Google Maps
 @date march 28,2020
 */
package ca.thesource.safeways;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;

/**
* Google Maps View for Dijkstra's Algorithm
* */
public class Gmaps_Dijkstra extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    /** GMAP FOR DIJSKTRAS PLOTTING**/
    /** USES activity_gmapsdijstra.xml FOR THE UI COMPONENTS FOUND IN res>layout**/
    Toolbar toolbar;

    private GoogleMap mMap;
    public static int color = 0xFF1C1C1C;
    public static int textColor = 0xFFFFFFFF;

    private Polyline currentPolyline;
    private String[] intersections = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmapsdijstra);
        setSupportActionBar(toolbar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Access paths accessed from Dijkstra if available
        Bundle extras = getIntent().getExtras();

        //create arraylist with the path to fit within 20 streets Gmaps API limit
        ArrayList smaller_path = null;
        if (extras != null) {
           intersections = extras.getStringArray("Gmaps_path");


            //if size is greater than 20, must resize the path to fit API limits
            if(intersections.length>20){
                smaller_path = new ArrayList();
                //increment up by this to stay within GMaps API limit of 20 waypoints
                int increment = 5;

                for(int i = 0; i<intersections.length; i = i + increment){
                    if(i<intersections.length){
                        smaller_path.add(intersections[i]);
                    }
                }

                //if it missed inserting the end position
                if(!smaller_path.contains(intersections[intersections.length-1])){
                    smaller_path.add(intersections[intersections.length-1]);
                }
                Log.d("incrementTT",smaller_path.toString());
            }

            else{
                smaller_path = new ArrayList(Arrays.asList(intersections));
            }



        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Only plot points if there exists atleast 2 intersections
        if((intersections != null) && (intersections.length > 1)){
            //new FetchURL(Gmaps.this).execute(getUrl(place1.getPosition(), place2.getPosition()), "driving");
            new FetchURL(Gmaps_Dijkstra.this).execute(getUrl_arr(smaller_path), "driving");//FETCHES THE PARSED DATA FOR THE ROUTES AND DISPLAYS ON MAP
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mylog", "Added Markers");
        LatLng SanFran = new LatLng(37.7749, -122.4194);
        mMap.addMarker(new MarkerOptions().position(SanFran).title("San Francisco"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SanFran,10));

    }


    //EXECUTES WAYPOINTS API FOR JSON RETRIEVAL
    private String getUrl_arr(ArrayList<String> intersections) {
        // Origin of route
        String str_origin = null;
        // Destination of route
        String str_dest = null;
        // Waypoints; aka intermediate intersections
        String way_points = "waypoints=";

        //if atleast 3 intersections or more, then there exists a start, end, and atleast one intermediate intersection for waypoints
        if(intersections.size()>=3){
            str_origin = "origin=" + intersections.get(0);
            str_dest = "destination=" + intersections.get(intersections.size()-1);

            way_points = "waypoints=" + intersections.get(1); //get first intermediate point to append other ones
            for(int i = 2; i<intersections.size()-1; i++){
                way_points = way_points + "|" + intersections.get(i);
            }
        }

        //if only 2 intersections, then no intermediate intersections
        else if(intersections.size()==2){
            str_origin = "origin=" + intersections.get(0);
            str_dest = "destination=" + intersections.get(1);
        }


        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&"+ way_points +"&"+ "mode=driving";
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=";
        return url;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }


}
