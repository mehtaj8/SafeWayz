/** @file runDijkstra.java
 @author Daniel Di Cesare
 @brief Executes Dijkstra path computation
 @date February 28,2020
 */
package Eclipse;

import android.net.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class runDijkstra {
	
	private static String start1 = "";

	private static String start2 = "";

	private static String end1 = "";

	private static String end2 = "";
	
	static List<Vertex> nodes;
	static List<Edge> edges;
	static List<Edge> Edges;
	static HashMap<String, Vertex> Nodes = new HashMap<String, Vertex>();
	
	//private static String fileName = "WeightedData.json";
	private static Map <String, Object> hashtable;
	private static Map <String, Object> weights = new HashMap<String, Object>();
	
	private static HashMap<String, ArrayList<String>> intersection = new HashMap<String, ArrayList<String>>();	
	
	public runDijkstra(String s1, String s2, String e1, String e2, Map <String, Object> hashtable) {
		this.start1 = s1;
		this.start2 = s2;
		this.end1 = e1;
		this.end2 = e2;
		this.hashtable = hashtable;
	}
	
	public static LinkedList<Vertex> newPath() throws FileNotFoundException, IOException, ParseException {

		
		nodes = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
		Edges = new ArrayList<Edge>();
		
		//Finding weights
		for (Map.Entry<String, Object> entry : hashtable.entrySet()) {
			String key = entry.getKey();
			for (Map.Entry<String, Object> entry2 : hashtable.entrySet()) {
				String checkNew = entry2.getKey();
				LinkedHashMap a = (LinkedHashMap) entry.getValue();
				boolean check = a.containsKey(checkNew);
				if (check) {
					Object test = ((LinkedHashMap<String, Object>) hashtable.get(key)).get(checkNew);
					Object test2 = (((LinkedHashMap<String, Object>) test).get("Crime_Weight"));
					weights.put(key + "=" + checkNew, test2);
				}
	        }
		}
		
        checkConnected();
        addVertex();
        addEdge2();

        
        
        Graph graph2 = new Graph(new ArrayList<Vertex>(Nodes.values()), Edges);
        Dijkstra dijkstra2 = new Dijkstra(graph2,hashtable,weights);
       
        dijkstra2.execute(Nodes.get(getNode(start1, start2)));
        LinkedList<Vertex> path2 = dijkstra2.getPath(Nodes.get(getNode(end1, end2)));
        
        System.out.println("");
        try {
	        for (Vertex vertex : path2) {
	            System.out.println(vertex + " Crime weight @ intersection: " + weights.get(vertex.getName()));
	        }
        } catch (Exception e) {
        	System.out.println("No path");
        }		
        return path2;
	}
	
	private static void checkConnected() throws FileNotFoundException, IOException, ParseException {

		for (Map.Entry<String, Object> entry : hashtable.entrySet()) {
			ArrayList<String> temp = new ArrayList<String>();
			
			for (Map.Entry<String, Object> entry2 : hashtable.entrySet()) {
				
				String checkNew = entry2.getKey();
				LinkedHashMap a = (LinkedHashMap) entry.getValue();
				boolean check = a.containsKey(checkNew);
				if (check) {
					temp.add(entry2.getKey());
				}
	        }
			intersection.put(entry.getKey(), temp);
		}
	}
	
	private static void printIntersections() {
		for (Entry<String, ArrayList<String>> entry : intersection.entrySet()) {
		    System.out.println(entry.getKey() + " \t\t " + entry.getValue().toString());
		}
	}
	
	private static void addVertex() {
		for (Entry<String, ArrayList<String>> entry : intersection.entrySet()) {
			
		   for(String i : entry.getValue()) {
			   String street1 = entry.getKey();
			   String street2 = i;
			   			   
			   
			   //check if intersection was already added
			   boolean exists = nodeExists(street1, street2);
			   //if the intersection does not already exist, add it.
			   if (!exists) {
				   String intersect = street1 + "=" + street2;
				   Vertex insert = new Vertex(intersect, intersect, (int)weights.get(intersect));
				   Nodes.put(street1 + "=" + street2, insert);
			   }
		   }
		}
	}

	//startIntersecton
	private static void addEdge2() {
		String startIntersection;
		String endIntersection;
		for(Entry<String, ArrayList<String>> entry : intersection.entrySet()) {
			if(entry.getValue().size() > 1) {
				//startIntersection = entry.getKey() + "=" + entry.getValue().get(0);
				startIntersection = getNode(entry.getKey(), entry.getValue().get(0));
				for(int i = 1; i < entry.getValue().size(); i++) {
					//endIntersection = entry.getKey() + "=" + entry.getValue().get(i);
					endIntersection = getNode(entry.getKey(), entry.getValue().get(i));

					Edge lane = new Edge(entry.getKey(), Nodes.get(startIntersection), Nodes.get(endIntersection), 
							avgWeights((int) weights.get(startIntersection), (int) weights.get(endIntersection)));
	 				Edges.add(lane);
	 				startIntersection = endIntersection;
	 				
				}
			}
		}
	}
	
	//Check if there is already a permutation of the vertex in Edges
	private static boolean checkPermutation(String s) {
		String first = s.substring(0, s.indexOf("="));
		String second = s.substring((s.indexOf("=") + 1), s.length());
		for(Edge e : Edges) {
			if(e.getSource() != null)
				if(e.getSource().getName().contains(first) && 
						e.getSource().getId().contains(second)) 
					return true;
			if(e.getDestination()!=null)
				if(e.getDestination().getName().contains(first) && 
						e.getDestination().getId().contains(second)) 
					return true;
		}
		return false;
	}
	
	//Swap the order of the streets if a permutation occurs
	private static String swapOrder(String s) {
		String first = s.substring(0, s.indexOf("="));
		String second = s.substring((s.indexOf("=") + 1), s.length());
		return second + "=" + first;
	}
	
	private static boolean nodeExists(String street1, String street2) {
		boolean exists = false;
		for (Entry<String, Vertex> nodeVertex : Nodes.entrySet()) {
			   if (nodeVertex.getKey().contains(street1) && nodeVertex.getKey().contains(street2)) {
				   exists = true;
				   break;
			   }
		   }
		return exists;
	}
	
	private static String getNode(String street1, String street2) {
		String nodeName = "";
		for (Entry<String, Vertex> nodeVertex : Nodes.entrySet()) {
			   if (nodeVertex.getKey().contains(street1) && nodeVertex.getKey().contains(street2)) {
				   nodeName = nodeVertex.getKey();
				   break;
			   }
		   }
		return nodeName;
	}
	
	private static void printEdges() {
		for (Edge edge : Edges) {
			if (edge.getId().contains("FOLSOM"))
				System.out.println(edge);
		}
	}
	
	private static int avgWeights(int a, int b) {
		return (a + b)/2;
	}
		
}
