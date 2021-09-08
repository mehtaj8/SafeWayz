package ca.thesource.safeways;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Arrays;

public class Gmaps extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    /** GMAP FOR OPTIMAL SAFE DFS PLOTTING**/
    /** USES activity_gmaps.xml FOR THE UI COMPONENTS FOUND IN res>layout**/
    Toolbar toolbar;

    private GoogleMap mMap;
    public static int color = 0xFF1C1C1C;
    public static int textColor = 0xFFFFFFFF;

    private Polyline currentPolyline;
    private String[] intersections = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmaps);
        setSupportActionBar(toolbar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Access paths accessed from DFS if available
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            intersections = extras.getStringArray("Gmaps_path");
        }
        //Log.d("SANFRAN", Boolean.toString(intersections != null));


        //Only plot points if there exists atleast 2 intersections
        if((intersections != null) && (intersections.length > 1)){
            //new FetchURL(Gmaps.this).execute(getUrl(place1.getPosition(), place2.getPosition()), "driving");
            new FetchURL(Gmaps.this).execute(getUrl_arr(intersections), "driving"); //FETCHES THE PARSED DATA FOR THE ROUTES AND DISPLAYS ON MAP
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

    /*private String getUrl(LatLng origin, LatLng dest) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=";
        return url;
    }*/

    //EXECUTES WAYPOINTS API FOR JSON RETRIEVAL
    private String getUrl_arr(String[] intersections) {
        // Origin of route
        String str_origin = null;
        // Destination of route
        String str_dest = null;
        // Waypoints; aka intermediate intersections
        String way_points = "waypoints=";

        //if atleast 3 intersections or more, then there exists a start, end, and atleast one intermediate intersection for waypoints
        if(intersections.length>=3){
            str_origin = "origin=" + intersections[0];
            str_dest = "destination=" + intersections[intersections.length-1];

            way_points = "waypoints=" + intersections[1]; //get first intermediate point to append other ones
            for(int i = 2; i<intersections.length-1; i++){
                way_points = way_points + "|" + intersections[i];
            }
        }

        //if only 2 intersections, then no intermediate intersections
        else if(intersections.length==2){
            str_origin = "origin=" + intersections[0];
            str_dest = "destination=" + intersections[1];
        }


        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&"+ way_points +"&"+ "mode=driving";
        Log.d("SANFRAN", parameters);
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
