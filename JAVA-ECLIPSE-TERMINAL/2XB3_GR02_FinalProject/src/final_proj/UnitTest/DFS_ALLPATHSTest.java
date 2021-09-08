package final_proj.UnitTest;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import final_proj.FileWriter.File_writer;
import final_proj.GraphTraversals.DFS_ALLPATHs;
import final_proj.Hashtable.generateHashtable;


public class DFS_ALLPATHSTest {

	private  Vector<Vector<String>> start_end_list;
	private DFS_ALLPATHs pathobject = new DFS_ALLPATHs(6); //object for path
	
	private static String fileName = "WeightedData.json";
	private static Map <String, Object> hashtable = generateHashtable.hashifiy(fileName);
	
	//Items that need to be setUp each time
		@Before
		public void setUp() throws Exception {
			
			}
	
	
		@Test
		public void test_DFS() {
			
			String outputfile = "data/DFS_test_output.txt";
			
			File_writer.Write_to_file("Executing DFS test suite...", outputfile); 
			File_writer.Write_to_file("******************\n", outputfile); 
			
			System.out.println("Executing DFS test suite...");
			System.out.println("******************\n");
			
			//{"ERIK ST", "MCMASTER ST"} is a boundary/exception case as it doesn't exist in the dataset
			String[][] testcase = {{"ERIK ST", "MCMASTER ST"}, {"YORK ST", "OAK ST"}, {"01ST ST", "MARKET ST"}, {"GRACE ST", "LAUREL ST"}, {"MAIN DR", "FITZGERALD AV"}};
			
			for(String[] start_end : testcase) {
				String starting = start_end[0];
				String ending = start_end[1];


				//Execute DFS to extract all paths
				pathobject.DFS(starting, ending); 
				
				//Return all the paths generate from DFS
				File_writer.Write_to_file("TESTING DFS order of paths for " + starting + " to " + ending + "...\n", outputfile); 
				File_writer.Write_to_file("-----------------------------  + \n", outputfile); 
				System.out.println("TESTING DFS order of paths for " + starting + " to " + ending +"...");
				System.out.println("-----------------------------");
				ArrayList<Vector<String>> paths = pathobject.getPaths();
				
				//Check each path from the 2D path list
				for (Vector<String> path : paths) {
					System.out.println(path);
					for(int i = 0; i<path.size()-1; i++) {
						String current_street = path.get(i).trim();
						String next_street = path.get(i+1).trim();
						Set<String> connected_streets = ((HashMap<String, Object>) hashtable.get(current_street)).keySet(); /**extracts adjacent connected cities to current city**/

						/**Verifies if order of DFS is correct by checking if adjacency list of current street contains the next_street from BFS**/
						if(connected_streets.contains(next_street)) {
							File_writer.Write_to_file(next_street + " is VALID. It is contained in the adjacency list of " + current_street +"\n", outputfile); 
							System.out.println(next_street + " is VALID. It is contained in the adjacency list of " + current_street); 
							assertTrue(true); 
						}
						
						/**Otherwise, invalid order. assert false**/
						else {
							File_writer.Write_to_file(next_street + " is INVALID. It is NOT contained in the adjacency list of " + current_street +"\n", outputfile); 
							System.out.println(path);
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
