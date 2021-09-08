/** @file DijkstraRoute.java
 @author Anando Zaman
 @brief Activity class that showcases the computed path from Dijkstras in ListView Format
 @date march 28,2020
 */
package ca.thesource.safeways;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**Class that displays each intersection of the route as ListView **/
public class DijkstraRoute extends AppCompatActivity {

    private String[] intersections = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dijkstra_route);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Connect UI elements
        final ListView routes_view = (ListView) findViewById(R.id.routes); //listview UI element

        //Access paths accessed from DijkstraActivity, if available
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            intersections = extras.getStringArray("route_info");
        }

        // Create a List from String Array elements
        final List<String> intersections_list = new ArrayList<String>(Arrays.asList(intersections));

        // Create an ArrayAdapter from List. Essentially, adapts the information to be used as UI elements
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, intersections_list);

        // Update ListView UI element with items from ArrayAdapter
        routes_view.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
