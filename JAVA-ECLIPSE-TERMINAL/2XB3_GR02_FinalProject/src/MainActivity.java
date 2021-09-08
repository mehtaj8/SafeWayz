import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import org.json.simple.parser.ParseException;

import final_proj.CrimeSortSearch.CrimeSearch;
import final_proj.CrimeSortSearch.paths_sort;
import final_proj.DijkstrasPaths.Vertex;
import final_proj.DijkstrasPaths.runDijkstra;
import final_proj.GraphTraversals.BFS_ALLPATHS;
import final_proj.GraphTraversals.DFS_ALLPATHs;
import final_proj.Hashtable.generateHashtable;

//Essentially controller to run the program.
public class MainActivity {
	
	//Hashtable from created JSON
	private static String fileName = "WeightedData.json";
	private static Map <String, Object> hashtable = generateHashtable.hashifiy(fileName);
	
	private static void bfs_run() {
		Scanner input = new Scanner(System.in);
		System.out.println("*******************************************");
    	System.out.println("	Welcome to BFS PATH SELECTION");
    	System.out.println("A list of possible streets available in the data/AvailableStreets.txt");
    	System.out.println("Please input your start & end streets in the following format --> Street1,Street2");
    	System.out.println("*******************************************");
    	System.out.println("SAMPLE INPUT/OUTPUT...");
    	System.out.println("Input: YORK ST,OAK ST\n");
    	System.out.println("OUTPUT:\nPath Generation Complete\n" + "[YORK ST, 20TH ST, CAPP ST, 17TH ST, CLAYTON ST, OAK ST]");
    	System.out.println("*******************************************");
    	
    	System.out.println("Input Intersection:");
    	String s = input.nextLine();
    	
    	//return to main menu
    	if(s.trim().toUpperCase().equals("EXIT")) {
    		System.out.println("Returning to main menu...");
    		return;
    	}
    	
    	String[] data = s.split(",");
    	
    	while(!(data.length == 2)) {
    		System.out.println("Invalid format. Please input using the following format... start,end,size , OR type 'exit' to return to main menu");
    		s = input.nextLine();
    		data = s.split(",");
    		
    		//return to main menu
    		if(data[0].trim().toUpperCase().equals("EXIT")) {
    			System.out.println("Returning to main menu...");
    			return;
    		}
    		
    	}
    	
    	String start = data[0].trim().toUpperCase();
    	String end = data[1].trim().toUpperCase();
    	
    	if( !(hashtable.containsKey(start)) || !(hashtable.containsKey(end)) ){
    		System.out.println("Invalid Start or End streets");
    		return;
    	}
    	
    	else {
    		BFS_ALLPATHS algorithm = new BFS_ALLPATHS(1500);

    		algorithm.BFS(start, end);
    		Vector<String> least_crime_path = paths_sort.paths_crime_sorted(algorithm.getPaths(), hashtable);
    		System.out.println(least_crime_path);
    		System.out.println("Returning to main menu...");
    	}
	}
	
	private static void dijkstra_paths() {
		Scanner input = new Scanner(System.in);
		System.out.println("*******************************************");
    	System.out.println("	Welcome to Dijkstra path SELECTION");
    	System.out.println("A list of possible streets available in the data/AvailableStreets.txt");
    	System.out.println("Please input your start & end intersection in the following format --> Start1,Start2,end1,end2");
    	System.out.println("*******************************************");
    	System.out.println("SAMPLE INPUT/OUTPUT...");
    	System.out.println("Input: JESSIE,ANTHONY,14TH, IRVING\n");
    	System.out.println("This would represent JESSIE ST@ANTHONY ST to 14TH ST@IRVING ST as start and end intersections\n");
    	System.out.println("*******************************************");
    	
    	System.out.println("Input Intersection:");
    	String s = input.nextLine();
    	
    	//return to main menu
    	if(s.trim().toUpperCase().equals("EXIT")) {
    		System.out.println("Returning to main menu...");
    		input.close();
    		return;
    		
    	}
    	
    	String[] data = s.split(",");
    	
    	while(!(data.length == 4)) {
    		System.out.println("Invalid format. Please input using the following format... start1,start2,end1,end2, OR type 'exit' to return to main menu");
    		s = input.nextLine();
    		data = s.split(",");
    		
    		//return to main menu
    		if(data[0].trim().toUpperCase().equals("EXIT")) {
    			System.out.println("Returning to main menu...");
    			return;
    		}
    	}
    	
    	String start1 = data[0].trim().toUpperCase();
    	String start2 = data[1].trim().toUpperCase();
    	String end1 = data[2].trim().toUpperCase();
    	String end2 = data[3].trim().toUpperCase();

		//Compute path
    	System.out.println("Computing path, please wait...");
        runDijkstra route = new runDijkstra(start1, start2, end1, end2);
        String[] GMAPS_path = null;

        try {
            LinkedList<Vertex> path = route.newPath();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No path found");
            return;
        } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
            System.out.println("No path found");
            return;
		}
        System.out.println("PATH PRINTED ABOVE:");
		System.out.println("Returning to main menu...");
	}
	
	private static void crime_feature() {
		Scanner input = new Scanner(System.in);
		System.out.println("*******************************************");
    	System.out.println("	Welcome to Crime Feature");
    	System.out.println("This feature takes an input street along with a crime weighting.");
    	System.out.print("Returns the associated street that makes an intersection with that crime value/range");
    	System.out.println("A list of possible streets available in the data/AvailableStreets.txt");
    	System.out.println("Please input a street and crime value --> street,200");
    	System.out.println("*******************************************");
    	System.out.println("SAMPLE INPUT/OUTPUT...");
    	System.out.println("Input: JESSIE ST,200\n");
    	System.out.println("The output(2ND ST) would represent the street that makes an intersection with JESSIE ST with closest crime weight of 200\n");
    	System.out.println("*******************************************");
    	
    	System.out.println("Input Intersection:");
    	String s = input.nextLine();
    	
    	//return to main menu
    	if(s.trim().toUpperCase().equals("EXIT")) {
    		System.out.println("Returning to main menu...");
    		return;
    		
    	}
    	
    	String[] data = s.split(",");
    	String street = null;
    	int weighting;
    	
    	boolean valid_street = true;
    	boolean valid_weighting = true;
    	
    	while(!(data.length == 2)) {
    		System.out.println("Invalid format. Please input using the following format... start1,start2,end1,end2");
    		s = input.nextLine();
    		data = s.split(",");
    		
    		
    		
    		try{
    			street = data[0].trim().toUpperCase();
    			valid_street = hashtable.containsKey(street);
    			
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    		
    		try{
    			weighting = Integer.parseInt(data[1].trim().toUpperCase());
    			valid_weighting = true;
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    		
    	}
    	
    	street = data[0].trim().toUpperCase();
    	weighting = Integer.parseInt(data[1].trim().toUpperCase());
        System.out.println(CrimeSearch.search(street, weighting));
        System.out.println("Returning to main menu...");
	}
	
	public static void main(String[] args) {
		//Display intro greeting
		System.out.println("*******************************************");
		System.out.println("	WELCOME TO SAFEWAYZ TERMINAL");
		System.out.println("*******************************************");
		System.out.println(" Please type in one of the following options");
		
		System.out.println(String.format("%-22s%-22s%-22s%-22s\n","Dijkstra Path", "BFS Path", "Crime Search","Exit"));
		System.out.println();
		
		Scanner input = new Scanner(System.in);
		
		while(true) {
			System.out.print("\nEnter input: ");
	        String s = input.nextLine();
	        
	        if(s.trim().toLowerCase().equals("exit")) {
	        	System.out.println("Program terminated.");
	        	input.close();
	        	break;
	        	
	        }

	        if(s.trim().toLowerCase().equals("bfs path") || s.trim().toLowerCase().equals("bfs") || s.trim().toLowerCase().equals("bfs paths")) {

	        	bfs_run();
	        	
	        }
	        
	        else if(s.trim().toLowerCase().equals("dijkstra path") || s.trim().toLowerCase().equals("dijkstra") || s.trim().toLowerCase().equals("dijkstra paths")) {
	        	dijkstra_paths();
	        }
	        
	        else if(s.trim().toLowerCase().equals("crime search") || s.trim().toLowerCase().equals("crime") || s.trim().toLowerCase().equals("search")) {
	        	crime_feature();
	        }
	        
	        else {
	        	System.out.println("Invalid choice, please choose from the following: dijkstra path, bfs path, crime search, OR exit");
	        }
		}
		input.close();

        

	}
}
