package Eclipse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.Vector;
import java.util.Set;

public class graphTraversal {
	private static int pathCount;
	private static int maxSize;
	private static String fileName;
	private static Map <String, Object> hashtable; // Turns JSON to Hashtable/map
	private static Stack <String> singularPath; // Contains ONE path
	private static Stack <String> storePath; // Contains ONE path
	@SuppressWarnings("rawtypes")
	private static ArrayList<Vector> allPaths; // Will contain ALL Paths
	
	private static ArrayList<ArrayList<Integer>> allPathWeights; // Array that stores information corresponding to paths
	// pathIndexNumber, pathCrimeWeighting
	
	private static boolean validStart(String startPoint) { // Confirms that the starting point exists
		return hashtable.containsKey(startPoint);
	}
	
	private static boolean validEnd(String endPoint) { // Confirms that the end point exists
		return hashtable.containsKey(endPoint);
	}
	
	private static boolean inStack(String street) { // Checks if we already encountered the street in the singularPath
		if (singularPath.search(street) == -1) {
			return false;
		}
		return true;
	}

	public graphTraversal(int maximumSize,Map <String, Object> hashtable) {
		this.pathCount = 0;
		this.maxSize = maximumSize;
		this.fileName = "WeightedData.json";
		this.hashtable = hashtable;
		this.singularPath = new Stack <String>();
		this.storePath = new Stack <String>();
		this.allPaths = new ArrayList<Vector>();
		this.allPathWeights = new ArrayList<ArrayList<Integer>>();
	}
	
	
	public ArrayList<Vector> getPaths(){
		return this.allPaths; // Getter used to retrieve all paths
	}
	
	public ArrayList<ArrayList<Integer>> getPathWeights(){
		return this.allPathWeights; // Getter used to retrieve pathWeightings
	}
	
	private static void addWeight(int number, Stack<String> singularPath) { // Adds weight to each path
		ArrayList<String> array = new ArrayList<String>(); 
		ArrayList<Integer> pathTuple = new ArrayList<Integer>(); // Will store pathIndex, pathCrimeWeighting
		
		Iterator pathIterator = singularPath.iterator(); // Used to iterate stack
		while (pathIterator.hasNext()) array.add((String) pathIterator.next()); // Add stack contents to array list for ease of iteration
		
		
		int pathCrimeWeight = 0;
		for(int i = 0; i < array.size() - 1; i++) {
			Map<String, Object> firstStreet = (Map<String, Object>) hashtable.get(array.get(i)); // Finds the first street
			Map<String, Object> secondStreet = (Map<String, Object>) firstStreet.get(array.get(i+1)); // Finds the intersection of the second street on the first street
			if(secondStreet != null) {
				Integer weight = ((Number)secondStreet.get("Intersection_Crime_Weight")).intValue(); // Grabs the Intersection_Crime_Weight of that intersection
				pathCrimeWeight += weight; 
			}
		}
		
		pathTuple.add(number);
		pathTuple.add(pathCrimeWeight);
		allPathWeights.add(pathTuple); // Adds to private variable. This variable is the output into Jash's module
		
		//System.out.println(number + " " + pathCrimeWeight + " " + array); // Visual Indicator
	}
	
	@SuppressWarnings("unchecked")
	private boolean findPath(String startPoint, String endPoint) {
		if (startPoint == endPoint) { // If the last point is the end point, save it to all paths and print the path
			addWeight(pathCount, singularPath);
			this.storePath = (Stack<String>) singularPath.clone();
			
			//parses storepath Stack to Vector
			//copies each element in Stack for Vector
			Vector<String> vectorized_path = new Vector(this.storePath); 
			allPaths.add(vectorized_path);
			pathCount++;
			return true; // Proceed to finding another path
		} else {
			Object hashtableGet = hashtable.get(startPoint); // Get the new street to find the other streets that it connects to
			
			if (hashtableGet == null) { // If the street can't be found for some reason, ignore it and try another
				return false;
			}
			
			Set<String> streetNames = ((Map<String, Object>) hashtableGet).keySet(); // Gets set of strings of all connecting streets
			for(String street : streetNames) { // Iterates through the streets
	        	if (!inStack(street)) { // If the street (street[i]) doesn't exist, mark it as an intersection and continue
	        		if (singularPath.size() + 1 < maxSize) {
	        			singularPath.add(street);
		        		findPath(street, endPoint);
		        		singularPath.pop();
	        		}
	        	} 
	        }
			return false;
		}
	}
	
	public void DFS(String starting, String ending) { // used to execute code. This will be exxecuted from the app.
		if (validStart(starting) && validEnd(ending)) {
			singularPath.add(starting);
			findPath(starting, ending);
			System.out.println("Path Generation Complete");
		} else {
			System.out.println("Invalid Start or End Point");
		}
	}
	
	/*public static void main(String[] args) {
		//String[][] testcase = {{"ERIK ST", "MCMASTER ST"}, {"YORK ST", "OAK ST"}, {"01ST ST", "MARKET ST"}, {"GRACE ST", "LAUREL ST"}, {"MAIN DR", "FITZGERALD AV"}};
		graphTraversal algorithm = new graphTraversal(6);
		/*for(String[] start_end : testcase) {
			String starting = start_end[0];
			String ending = start_end[1];

			algorithm.DFS(starting, ending);
			System.out.println(algorithm.getPaths());
		}
		
		String starting = "YORK ST";
		String ending = "OAK ST";

		algorithm.DFS(starting, ending);
		//System.out.println(algorithm.getPaths());
		Vector<String> least_crime_path = paths_sort.paths_crime_sorted(algorithm.getPaths(), hashtable);
		System.out.println(least_crime_path);
	}*/
}