/** @file runDijkstra.java
@author Daniel Di Cesare and Zach Ren
@brief Executes Dijkstra path computation
@date February 28,2020
*/
package final_proj.DijkstrasPaths;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import final_proj.DijkstrasPaths.Vertex;
import final_proj.Hashtable.generateHashtable;
import final_proj.DijkstrasPaths.Edge;
import final_proj.DijkstrasPaths.Graph;

public class Dijkstra {

	private static String fileName = "WeightedData.json";
	private static Map <String, Object> hashtable = generateHashtable.hashifiy(fileName);
	private static Map <String, Object> weights = generateHashtable.hashifiy(fileName);
	
	//vogella
	private final List<Edge> edges; 
	private final List<Vertex> nodes; 
	private static Set<Vertex> settledNodes = new HashSet<Vertex>();
	private static Set<Vertex> unsettledNodes = new HashSet<Vertex>();
	private static Map<Vertex, Integer> distance = new HashMap<Vertex, Integer>();
	private static Map<Vertex, Vertex> predecessors = new HashMap<Vertex, Vertex>();
	
	public Dijkstra(Graph g) {
		this.nodes = new ArrayList<Vertex>(g.getVetexs());
		this.edges = new ArrayList<Edge>(g.getEdges());
		
	}
	//making weighted hash table
	//each: street: weight
	
	@SuppressWarnings("unused")
	private static void weights() throws JSONException, FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		Object objTemp = parser.parse(new FileReader("data/WeightedData.json"));

		JSONObject obj = new JSONObject (objTemp);
	    
		//key of 'hashtable' is all streets
		//take keys + each key's weight
		
	    for (Map.Entry<String, Object> entry : hashtable.entrySet()) {
	        String key = entry.getKey();
	        //Object value = entry.getValue();
	        
	        try {
	        	System.out.println("PASS");
	        	weights.put(key, obj.getJSONObject(key).getString("Crime_Weight"));
	        } catch(Exception e) { 
	        	System.out.println("fail");
	        	weights.put(key, "0");
	        }
	        
	    }
	    //testing for values
	    for (Map.Entry<String, Object> entry : weights.entrySet()) {
	        String key = entry.getKey();
	        Object value = entry.getValue();

	        System.out.println ("Key: " + key + "Value: " + value);
	    }
	    
	}
	
	
	
	public void execute(Vertex start) {
		distance.put(start, 0);
		unsettledNodes.add(start);
		
		while (unsettledNodes.size() > 0) {
			//find smallest in unsettled nodes
			//add this node to 'settled nodes'
			//remove this node from 'settled nodes'
			//find smallest distances from this node
			Vertex node = getMinimum(unsettledNodes);
			settledNodes.add(node);
			unsettledNodes.remove(node);
			findMinimalDistances(node);
		}
	}
	
	public void findMinimalDistances(Vertex node) {
		List<Vertex> adjacentNodes = getNeighbors(node);
		for (Vertex target : adjacentNodes) {
			
			if(getShortestDistance(target) > getShortestDistance(node)
					+ getDistance(node, target)) {
				
				distance.put(target, getShortestDistance(node) + getDistance(node, target)); 
				predecessors.put(target, node);
				unsettledNodes.add(target);
			}
		}
	}
	
	private int getDistance(Vertex node, Vertex target) {
		for(Edge edge : edges) {
			if(edge.getSource().equals(node) && edge.getDestination().equals(target)) {
				return edge.getWeight();
			}
		}
		throw new RuntimeException("You wont hit this error hopefully");
	}
	
	private List<Vertex> getNeighbors(Vertex node) {
		List<Vertex> neighbors = new ArrayList<Vertex>();
		for (Edge edge : edges) {
			if (edge.getSource().equals(node) && !isSettled(edge.getDestination())) {
				neighbors.add(edge.getDestination());
			}
		}
		return neighbors;
	}
	
	private Vertex getMinimum(Set<Vertex> vertexs) {
		Vertex minimum = null;
		for (Vertex vertex : vertexs) {
			if (minimum == null) {
				minimum = vertex;
			}
			else {
				if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
					minimum = vertex;
				}
			}
		}
		return minimum;
	}
	
	private boolean isSettled(Vertex vertex) {
		return settledNodes.contains(vertex);
	}
	
	private int getShortestDistance(Vertex destination) {
		Integer d = distance.get(destination);
		if (d == null) {
			return Integer.MAX_VALUE;
		}
		else {
			return d;
		}
	}
	
	public LinkedList<Vertex> getPath(Vertex target) {
		LinkedList<Vertex> path = new LinkedList<Vertex>();
		Vertex step = target;
		
		//check viable path
		if (predecessors.get(step) == null) {
			return null;
		}
		path.add(step);
		while (predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(step);
		}
		Collections.reverse(path);
		return path;
	}
	
	
	public static void main(String[] args) throws JSONException, FileNotFoundException, IOException, ParseException {
		weights();
		
		String start = "YORK ST";
		String end = "OAK ST";
		
		

	}

}
