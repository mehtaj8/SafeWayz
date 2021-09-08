/** @file BreadthFirstPaths.java
@author Anando Zaman
@brief Finds a single path using BFS Algorithm
@date February 28,2020
*/

package final_proj.GraphTraversals;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ArrayList;
import java.util.Stack;

import final_proj.Extras.Queue;
import final_proj.Hashtable.generateHashtable;

import java.util.Set;

//BFS pathfinder
public class BreadthFirstPaths
{

	/*Load JSON Graph Data*/
	private static String fileName = "WeightedData.json";
	private static Map <String, Object> hashtable = generateHashtable.hashifiy(fileName);
	
	
	/*DATA STRUCTURES SETUP*/
	private static Map<String, String> edgeTo = new HashMap<String, String>(); // last vertex on known path to this vertex. Will be used to backtrack & find path
	private static HashSet<String> marked = new HashSet<String>(); //Keeps track of marked/seen streets?
	private static Queue<String> queue = new Queue<String>();
	
	
	@SuppressWarnings("rawtypes")
	/*PRE-PROCESSING CHECKS IF VALID START & ENDS*/
 	public static boolean validStart(String startPoint) {
		return hashtable.containsKey(startPoint);
	}
	
	
	public static boolean validEnd(String endPoint) {
		return hashtable.containsKey(endPoint);
	}
	
	
	
	private static void bfs(String startPoint, String endPoint){
		marked.add(startPoint); // Mark the source
		queue.enqueue(startPoint); // and put it on the queue.

		while (!queue.isEmpty()){
			
			String v = queue.dequeue();//Remove next vertex/street from queue

			if(hashtable.get(v)!=null) {
				@SuppressWarnings("unchecked")
				Set<String> streetNames = ((Map<String, Object>) hashtable.get(v)).keySet();
				
				for (String street : streetNames) {
					//For every unmarked adjacent vertex,
					if (!marked.contains(street)){ 		
						edgeTo.put(street,v); //Keep track of adjacent street connected to parent street(v)
						marked.add(street);	//mark street as seen
						queue.enqueue(street);	//Insert into the queue.
					}
				}
			}
		}
	}
	
	//Verifies if path can exist by checking if end-node exists
	public static boolean hasPathTo(String endPoint){
			return marked.contains(endPoint);
	}
	
	//Returns the path from start to end in reverse order on to the stack(endStreet to startStreet).
	//Traverses backwards from destination street to the source street
	public static Stack<String> pathTo(String startPoint, String endPoint) { 

        if (!hasPathTo(endPoint)) {
        	return null;
        }
        
        Stack<String> path = new Stack<String>();
        for (String x = endPoint; x != startPoint; x = edgeTo.get(x)) {
        	path.push(x);
        }
        
        path.push(startPoint); //Push the original source/start node onto the stack.
        return path;
    }

}
