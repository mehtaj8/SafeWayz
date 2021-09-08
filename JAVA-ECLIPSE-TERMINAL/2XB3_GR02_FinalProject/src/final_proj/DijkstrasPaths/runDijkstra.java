/** @file runDijkstra.java
@author Daniel Di Cesare
@brief Executes Dijkstra path computation
@date February 28,2020
*/
package final_proj.DijkstrasPaths;

import final_proj.DijkstrasPaths.Vertex;
import final_proj.Hashtable.generateHashtable;
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

public class runDijkstra {
	
	//Names of start and end intersections
	private static String start1 = "";

	private static String start2 = "";

	private static String end1 = "";

	private static String end2 = "";
	
	static List<Vertex> nodes;
	static List<Edge> edges;
	static List<Edge> Edges;
	static HashMap<String, Vertex> Nodes = new HashMap<String, Vertex>();
	
	private static String fileName = "WeightedData.json";
	//Hashtable from created JSON
	private static Map <String, Object> hashtable = generateHashtable.hashifiy(fileName);
	
	//Stores weight values of all intersections
	private static Map <String, Object> weights = new HashMap<String, Object>();
	
	private static HashMap<String, ArrayList<String>> intersection = new HashMap<String, ArrayList<String>>();	
	
	public runDijkstra(String s1, String s2, String e1, String e2) {
		this.start1 = s1;
		this.start2 = s2;
		this.end1 = e1;
		this.end2 = e2;
	}
	
	public static LinkedList<Vertex> newPath() throws FileNotFoundException, IOException, ParseException {

		
		nodes = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
		Edges = new ArrayList<Edge>();
		
		
		JSONParser parser = new JSONParser();
		Object objTemp = parser.parse(new FileReader("data/WeightedData.json"));

		JSONObject obj = new JSONObject (objTemp);
		
		//Finding weights for each intersection
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

        
        //Starting execution of graph
        Graph graph2 = new Graph(new ArrayList<Vertex>(Nodes.values()), Edges);
        Dijkstra dijkstra2 = new Dijkstra(graph2);
       
        dijkstra2.execute(Nodes.get(getNode(start1, start2)));

        LinkedList<Vertex> path2 = dijkstra2.getPath(Nodes.get(getNode(end1, end2)));
        System.out.println(start1 + "" + start2 + "" + end1 + "" + end2);
        
        //output of the path
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
	
	//Determines if the created graph is connected to itself
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
	
	//Output all intersections
	private static void printIntersections() {
		for (Entry<String, ArrayList<String>> entry : intersection.entrySet()) {
		    System.out.println(entry.getKey() + " \t\t " + entry.getValue().toString());
		}
	}
	
	//Adds vertices from the graph to the local intersection map.
	//Used for double occurrences within the JSON
	
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
	//Creates edges between intersections sharing a street.
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
	
	//Check if there is already a permutation of the vertex in Edges (Main & Emmerson vs Emmerson & Main)
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
	
	//Checks if a vertex already exists
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
	
	//Returns the vertex containing the two streets
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
	
	public static void main(String[] args) {
        //FOR TESTING, REMOVE LATER
        start1 = "GRACE ST";
        start2 = "MISSION ST";
        end1 = "PERU ST";
        end2 = "MADRID ST";
        
        //start1 = "JESSIE";
          //start2 = "ANTHONY";
          
         //end1 = "14TH";
          //end2 = "IRVING";
         

        runDijkstra route = new runDijkstra(start1, start2, end1, end2);
        String[] GMAPS_path = null;

        try {
            LinkedList<Vertex> path = route.newPath();

            GMAPS_path = new String[path.size()];
            int j = 0;
            for (Vertex intersection : path) {
                String street1 = intersection.toString().substring(0, intersection.toString().indexOf(' '));
                String street2 = intersection.toString().substring(intersection.toString().indexOf('=') + 1);
                GMAPS_path[j] = "'" + street1+"'@'"+street2 + "'";
                j++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(GMAPS_path);
	}
		
}
