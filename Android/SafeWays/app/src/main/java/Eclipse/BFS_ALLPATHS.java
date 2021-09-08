/** @file BFS_ALLPATHS.java
 @author Anando Zaman
 @brief Computes all possible paths for a given size dataset
 @date February 28,2020
 */
package Eclipse;

import java.util.HashSet;
import java.util.Map;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;
import java.util.Set;

public class BFS_ALLPATHS
{
	
	/*STATIC STRUCTURES SETUP*/
	/*Will be used to read JSON data everywhere in this class, so can remain static*/
	private static String fileName = "WeightedData.json";
	private Map <String, Object> hashtable;
	
	/*NON-STATIC DATA STRUCTURES SETUP*/
	private HashSet<String> marked;
	@SuppressWarnings("rawtypes")
	private ArrayList<Stack> allPaths;
	private ArrayList<Vector> allpaths_FINAL;
	private int path_size;
	
	public BFS_ALLPATHS(int size, Map <String, Object>  hashtable) {
		this.allpaths_FINAL  = new ArrayList<Vector>(); //2D
		this.marked = new HashSet<String>(); //marks seen streets
		this.allPaths = new ArrayList<Stack>();
		this.path_size = size;
		this.hashtable = hashtable;
	}
	public Map <String, Object> gethashtable(){
		return this.hashtable;
	}
	//getter for retrieving all paths generated
	public ArrayList<Vector> getPaths(){
		return this.allpaths_FINAL; // Getter used to retrieve all paths
	}
	
	//getter for retrieving all paths length
	public int getPaths_size(){
		return this.allpaths_FINAL.size();
	}

	
	/*PRE-PROCESSING CHECKS IF VALID START & ENDS*/
 	public boolean validStart(String startPoint) {
		return hashtable.containsKey(startPoint);
	}
	
	public boolean validEnd(String endPoint) {
		return hashtable.containsKey(endPoint);
	}


	//Calculates all possible paths using BFS and returns a 2D ArrayList
	public void bfs_allPaths(String startPoint, String endPoint){
					
		//Queue for BFS seen paths
		Queue_BFSALL <Vector> allpaths = new Queue_BFSALL <Vector>();
		
		// path vector to store the current path
	    Vector<String> current_path = new Vector<String>();
	    
	    current_path.add(startPoint);
	    allpaths.enqueue(current_path);
		marked.add(startPoint); // Mark the source
		

		while (!allpaths.isEmpty()){
			
			current_path = allpaths.dequeue(); //returns an arraylist of path at the start of the Queue
			String last = current_path.get(current_path.size() - 1);//Remove next vertex/street from the current_path.
	        
			{ //limit total number of paths to 10 to reduce memory
	        if(allpaths_FINAL.size() == path_size) 
	        	return;
	        }
	        
	            
			if(hashtable.get(last)!=null) {
				@SuppressWarnings("unchecked")
				Set<String> streetNames = ((Map<String, Object>) hashtable.get(last)).keySet(); //Get all streets attached to last added street
				
				for (String street : streetNames) {
					
					/* *******VERY IMPORTANT PART OF THIS CODE: 
					 * Used to create all possible paths without ending on just one path by adding onto the Queue 
					 * each time a new path is found. This ensures that when we dequeue and take the path saved, 
					 * it has all possible streets as a seperate path.
					 * */
					Vector newpath = new Vector(current_path); //create a new vector that takes all elements of current_path vector
	                newpath.add(street); //adds the new street to it
					allpaths.enqueue(newpath);	//add new path onto the queue of all_paths					
						
						//Add the Path to the allpaths_FINAL lists which contains lists of all possible paths from start to end points
						if(street.equals(endPoint)) {
							allpaths_FINAL.add(newpath);
							//System.out.println(allpaths_FINAL);
						}
				}
			}
		}
		
	}
	
	public void BFS(String starting, String ending) { // used to execute code. This will be executed from the app.
		if (validStart(starting) && validEnd(ending)) {
			bfs_allPaths(starting, ending);
			System.out.println("Path Generation Complete");
		} else {
			System.out.println("Invalid Start or End Point");
		}
	}
	
		
	
	/*public static void main(String[] args) {
		String starting = "YORK ST";
		String ending = "OAK ST";
		
		BFS_ALLPATHS pathobject = new BFS_ALLPATHS(1000); //object for path
		pathobject.BFS(starting, ending);
		Vector<String> least_crime_path = paths_sort.paths_crime_sorted(pathobject.getPaths(), pathobject.gethashtable());
		System.out.println(least_crime_path);
		//HashMap<String,Object> data = (HashMap<String, Object>) hashtable.get("01ST ST");
		//System.out.println(data.keySet());

			
		} */
}

