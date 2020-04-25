/** @file BFS_ALLPATHS.java
@author Anando Zaman
@brief Computes all possible paths for a given size dataset
@date February 28,2020
*/
package final_proj.GraphTraversals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.Vector;

import final_proj.Extras.Queue_BFSALL;
import final_proj.Hashtable.generateHashtable;

import java.util.Set;

//Class that computes all possible paths from provided start and end streets
public class BFS_ALLPATHS
{
	
	/*Load JSON data in the form of a HashMap*/
	private static String fileName = "WeightedData.json";
	private static Map <String, Object> hashtable = generateHashtable.hashifiy(fileName);
	
	/*BFS Data Structures Setup*/
	private HashSet<String> marked; //keeps track of child and parent streets, used for backtracking to compute path
	@SuppressWarnings("rawtypes")
	private ArrayList<Stack> allPaths; //used for intermediate computations
	private ArrayList<Vector> allpaths_FINAL; //Contains all computed paths
	private int path_size; //Number of paths to be contained in allpaths_FINAL
	
	//Constructor
	public BFS_ALLPATHS(int size) {
		this.allpaths_FINAL  = new ArrayList<Vector>(); //2D
		this.marked = new HashSet<String>(); //marks seen streets
		this.allPaths = new ArrayList<Stack>();
		this.path_size = size;
	}
	
	//Getter for retrieving all paths generated
	public ArrayList<Vector> getPaths(){
		return this.allpaths_FINAL; // Getter used to retrieve all paths
	}
	
	//getter for retrieving all paths length
	private int getPaths_size(){
		return this.allpaths_FINAL.size();
	}

	
	/*PRE-PROCESSING CHECKS IF VALID START & ENDS*/
 	private boolean validStart(String startPoint) {
		return hashtable.containsKey(startPoint);
	}
	
	private boolean validEnd(String endPoint) {
		return hashtable.containsKey(endPoint);
	}


	//Calculates all possible paths using BFS
	private void bfs_allPaths(String startPoint, String endPoint){
					
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
	        
			{ //limit total number of paths to that of given size
	        if(allpaths_FINAL.size() == path_size) 
	        	return;
	        }
	        
	            
			if(hashtable.get(last)!=null) {
				@SuppressWarnings("unchecked")
				Set<String> streetNames = ((Map<String, Object>) hashtable.get(last)).keySet(); //Get all streets attached to last added street
				
				for (String street : streetNames) {
					
					/* *******VERY IMPORTANT PART OF THIS CODE: 
					 * Used to create all possible paths without ending on just one path.
					 * Adds onto the Queue each time a new path is found. 
					 * This ensures that when we dequeue and take the path saved, 
					 * that it has all possible streets appended to a newpath.
					 * */
					Vector newpath = new Vector(current_path); //create a new vector that takes all elements of current_path vector
	                newpath.add(street); //adds the new street to it
					allpaths.enqueue(newpath);	//add new path onto the queue of all_paths					
						
						//Add the Path to the Vector of all possible paths
						if(street.equals(endPoint)) {
							allpaths_FINAL.add(newpath);
						}
				}
			}
		}
		
	}
	
	 // Used to execute code. This will be executed from the within the mainactivity/app
	public void BFS(String starting, String ending) {
		if (validStart(starting) && validEnd(ending)) {
			bfs_allPaths(starting, ending);
			System.out.println("Path Generation Complete");
		} else {
			System.out.println("Invalid Start or End Point");
		}
	}
	
}

