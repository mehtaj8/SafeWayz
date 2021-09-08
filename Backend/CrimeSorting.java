package Eclipse;

import android.util.Log;

import java.util.Map;
import java.util.LinkedHashMap;

public class CrimeSorting {

	//private static String fileName = "WeightedData.json";
	//private static Map<String, Object> hashtable = generateHashtable.hashifiy(fileName);

	private static boolean validKey(String key, Map<String, Object> hashtable) {
		return hashtable.containsKey(key);
	}

	public static Map<String, Integer> crimeSort(String input, Map<String, Object> hashtable) {
		if (validKey(input, hashtable)) {
			//Log.d("TESTT", input); //hashtable.keySet().toString()
			int i = 0;
			Map<String, Object> inner_hashmap = (Map<String, Object>) hashtable.get(input);
			Map<String, Integer> final_hashmap = new LinkedHashMap<String, Integer>();
			String[][] entries = new String[inner_hashmap.size()][2];
			for (Map.Entry mapElement : inner_hashmap.entrySet()) {
				String key = (String) mapElement.getKey();
				Map<String, Map<String, Integer>> value = (Map<String, Map<String, Integer>>) mapElement.getValue();
				entries[i][0] = key;
				entries[i][1] = String.valueOf(value.get("Intersection_Crime_Weight"));
				i++;
			}
			
			sort(entries);
			for(int j = 0; j < entries.length; j++) {
				final_hashmap.put(entries[j][0], Integer.parseInt(entries[j][1]));
			}
			return final_hashmap;

		} else {
			Map<String, Integer> errorMap = new LinkedHashMap<String, Integer>();
			errorMap.put("-1", 0);
			return errorMap;
		}

	}
	
	private static void exch(String[][] a, int i, int j) {
		String[] t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	public static void sort(String[][] a) {
		StdRandom.shuffle(a); // Eliminate dependence on input.
		sort(a, 0, a.length - 1);
	}

	private static void sort(String[][] a, int lo, int hi) {
		if (hi <= lo)
			return;
		int j = partition(a, lo, hi); // Partition (see page 291).
		sort(a, lo, j - 1); // Sort left part a[lo .. j-1].
		sort(a, j + 1, hi); // Sort right part a[j+1 .. hi].
	}

	private static int partition(String[][] a, int lo, int hi) { // Partition into a[lo..i-1], a[i], a[i+1..hi].
		int i = lo, j = hi + 1; // left and right scan indices
		int v = Integer.parseInt(a[lo][1]); // partitioning item
		while (true) { // Scan right, scan left, check for scan complete, and exchange.
			while (Integer.parseInt(a[++i][1]) < v){
				if (i == hi) {
					break;
				}
			}
			while (v < Integer.parseInt(a[--j][1])) {
				if (j == lo) {
					break;
				}
			}
			if (i >= j)
				break;
			exch(a, i, j);
		}
		exch(a, lo, j); // Put v = a[j] into position
		return j; // with a[lo..j-1] <= a[j] <= a[j+1..hi].
	}

	/*public static void main(String[] args) {
		String starting = "YORK ST";
		Map<String, Integer> sorted = crimeSort(starting,Map<String, Object> hashtable);
		for (Map.Entry mapElement : sorted.entrySet()) {
			System.out.printf("The key: %s, The Value: %d", mapElement.getKey(), mapElement.getValue());
			System.out.println();
		}

	}*/

}
