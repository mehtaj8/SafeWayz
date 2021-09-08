package Eclipse;

import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

/**Class with static methods to be used anywhere to sort paths**/
public class paths_sort {
	
		//Returns the path with least crime via Quicksort
		//Creates a new 2D Vector consisting of index position of paths vector along with total crimes. (index, total_crime_weight)
		//This index is used to find the specific path of the paths vector with least crime
		@SuppressWarnings("unchecked")
		public static Vector<String> paths_crime_sorted(ArrayList<Vector> paths, Map <String, Object> hashtable) {
			
			int path_index = 0; //used to keep track of the indices associated with a path in the paths list
			Vector<Vector<Integer>> all_crimes_path = new Vector<Vector<Integer>>();
			
			//Create the 2D vector consisting of position in paths list along with total crime_weight for a given path for each sublist
			for(Vector<String> path : paths) {
				
				Integer total_Intersection_Crime_Weight = 0;
				for(int i = 0; i<path.size()-1; i++) {
					//System.out.println(path.get(i) + "," + path.get(i+1));
					Map<String, Object> street1 = (Map<String, Object>) hashtable.get(path.get(i));
					Map<String, Object> street2 = (Map<String, Object>) street1.get(path.get(i+1));
					Integer weight = ((Number)street2.get("Intersection_Crime_Weight")).intValue();
					total_Intersection_Crime_Weight += weight;
					//System.out.println(weight);
					
				}
				Vector<Integer> crime_info = new Vector<Integer>();
				crime_info.add(path_index);
				crime_info.add(total_Intersection_Crime_Weight);
				all_crimes_path.add(crime_info);
				
				//System.out.println(crime_info);
				//System.out.println("**********");
				path_index++;
			}
			
			//Sort the data and return the path/path_index with least crime
			quicksort(all_crimes_path,0,all_crimes_path.size()-1); //sorts the list consisting of index position in path along with total crime assoicated to it. ie; [[0, 196404], [1, 261180], [2, 163094]] 
			int index_least_crime = all_crimes_path.get(0).get(0);
			return paths.get(index_least_crime);
		}
		
		
	    public static int partition(Vector<Vector<Integer>> arr, int start, int end) {
			int pivot = end;
			int p_index = start;
			
			for(int i = start; i<=end-1;i++) {
				if(arr.get(i).get(1) < arr.get(pivot).get(1)) {
					exch(arr,i,p_index);
					p_index++;
				}
			}
	    	exch(arr,p_index,pivot);
	    	return(p_index);    	
	    }
	    
	    public static void quicksort(Vector<Vector<Integer>> arr, int start, int end) {
	    	
	    	if(start<end) {
	    		int partition_index = partition(arr,start,end);
	    		quicksort(arr,start,partition_index-1); //sort LHS
	    		quicksort(arr,partition_index+1,end); //sort RHS
	    	}
	    }
	    

		private static void exch(Vector<Vector<Integer>> a, int i, int j){
			Vector<Integer> temp = a.get(i); 
			a.set(i, a.get(j)); //a[i] = a[j]
			a.set(j, temp);  //a[j] = t;

			}
}
