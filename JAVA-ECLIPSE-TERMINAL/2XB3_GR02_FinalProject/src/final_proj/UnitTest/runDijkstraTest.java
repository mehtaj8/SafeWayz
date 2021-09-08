package final_proj.UnitTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import final_proj.DijkstrasPaths.Vertex;
import final_proj.DijkstrasPaths.Edge;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import final_proj.DijkstrasPaths.runDijkstra;

public class runDijkstraTest {

	runDijkstra test;
	@Before
	public void setUp() throws Exception {
		test = new runDijkstra("22ND", "YORK", "22ND", "FLORIDA");
	}

	@After
	public void tearDown() throws Exception {
		test = null;
	}

	@Test
	public void test() throws FileNotFoundException, IOException, ParseException {
		LinkedList<Vertex> path = test.newPath();
		
		String prev1 = path.get(0).toString().substring(0, path.get(0).toString().indexOf(' '));
		String prev2 = path.get(0).toString().substring(path.get(0).toString().indexOf('=') + 1);
		prev2 = prev2.substring(0, prev2.indexOf(' '));
		String next1 = "";
		String next2 = "";
		
		//Loops through returned path and ensures each of the intersections connect to one another.
		try {
	        for (Vertex vertex : path) {

	        	next1 = vertex.toString().substring(0, vertex.toString().indexOf(' '));
	        	next2 = vertex.toString().substring(vertex.toString().indexOf('=') + 1);
	        	next2 = next2.substring(0, next2.indexOf(' '));
	        	
	        	if (next1 == null || next2 == null)
	        		assertTrue(true);
	        	
	        	
	        	System.out.println("\n\n\nPrev: " + prev1 + " " + prev2 + "\nNext: " + next1 + " " + next2);
	        	
	        	if (!next1.equals(prev1) && !next1.equals(prev2) && !next2.equals(prev1) && !next2.equals(prev2))
	        		assertTrue(false);
	        	
	        	prev1 = next1;
	        	prev2 = next2;
	        }
        } catch (Exception e) {
        	assertTrue(false);
        }
		
		assertTrue(true);
	}

}
