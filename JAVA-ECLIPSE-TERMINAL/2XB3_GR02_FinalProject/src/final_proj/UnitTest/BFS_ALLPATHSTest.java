/** @file BFS_ALLPATHSTest.java
@author Anando Zaman
@brief JUnit tests for correctness of BFS_ALLPATHSTEST module
@date February 28,2020
*/
package final_proj.UnitTest;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import final_proj.FileWriter.File_writer;
import final_proj.GraphTraversals.BFS_ALLPATHS;
import final_proj.Hashtable.generateHashtable;

//JUnit tests
//Loads input data from data/graph_input_test.txt
public class BFS_ALLPATHSTest {
	
	//private global variables that only need to be setup once
	private  Vector<Vector<String>> start_end_list;
	private BFS_ALLPATHS pathobject = new BFS_ALLPATHS(100); //object for path
	
	private static String fileName = "WeightedData.json";
	private static Map <String, Object> hashtable = generateHashtable.hashifiy(fileName);

	
	//Items that need to be setUp each time
	@Before
	public void setUp() throws Exception {
		
		/**LOAD INPUT START/END DATA**/
		Scanner input = new Scanner(new File("data/graph_input_test.txt"));
		start_end_list = new Vector<Vector<String>>(); //Vector that contains [[start_city, end_city],[start_city_2, end_city_2],[start_city_3, end_city_3],...]
		while (input.hasNextLine()){
			String readLine = input.nextLine();
			String start = readLine.substring(0,readLine.indexOf(",")).trim();
			String end = readLine.substring(readLine.indexOf(",")+1).trim();
			Vector<String> temp = new Vector<String>();
			temp.add(start);
			temp.add(end);
			start_end_list.add(temp);
		}
		input.close();
		
		}
	
	@After
	public void tearDown() throws Exception {
		}

	
	@Test
	public void test_bfs_allPaths() {
		
		String outputfile = "data/BFS_test_output.txt";
		
		File_writer.Write_to_file("Executing BFS test suite...", outputfile); 
		File_writer.Write_to_file("******************\n", outputfile); 
		
		System.out.println("Executing BFS test suite...");
		System.out.println("******************\n");
		
		//Read start/end data from start_end_list
		for (Vector<String> start_end : start_end_list) {
			String start = start_end.get(0);
			String end = start_end.get(1);
			
			//Execute BFS to extract all paths
			pathobject.BFS(start, end); 
			
			//Return all the paths generate from BFS
			File_writer.Write_to_file("TESTING BFS order of paths for " + start + " to " + end + "\n", outputfile); 
			File_writer.Write_to_file("-----------------------------  + \n", outputfile); 
			System.out.println("TESTING BFS order of paths for " + start + " to " + end);
			System.out.println("-----------------------------");
			ArrayList<Vector> paths = pathobject.getPaths();
			
			//Check each path from the 2D path list
			for (Vector<String> path : paths) {
				for(int i = 0; i<path.size()-1; i++) {
					String current_street = path.get(i).trim();
					String next_street = path.get(i+1).trim();
					Set<String> connected_streets = ((HashMap<String, Object>) hashtable.get(current_street)).keySet(); /**extracts adjacent connected cities to current city**/

					/**Verifies if order of BFS is correct by checking if adjacency list of current street contains the next_street from BFS**/
					if(connected_streets.contains(next_street)) {
						File_writer.Write_to_file(next_street + " is VALID. It is contained in the adjacency list of " + current_street +"\n", outputfile); 
						System.out.println(next_street + " is VALID. It is contained in the adjacency list of " + current_street); 
						assertTrue(true); 
					}
					
					/**Otherwise, invalid order. assert false**/
					else {
						File_writer.Write_to_file(next_street + " is INVALID. It is NOT contained in the adjacency list of " + current_street +"\n", outputfile); 
						
						System.out.println(next_street + " is INVALID. It is NOT contained in the adjacency list of " + current_street); 
						assertTrue(false); 
					}
				}
			}
			File_writer.Write_to_file("******************", outputfile); 
			File_writer.Write_to_file("TESTING COMPLETE ", outputfile); 
			File_writer.Write_to_file("******************\n", outputfile); 
			File_writer.Write_to_file("\n", outputfile); 
			File_writer.Write_to_file("\n", outputfile); 
			System.out.println("******************");
			System.out.println("TESTING COMPLETE ");
			System.out.println("******************\n");
		}

				
	}

}
