<<<<<<< HEAD:Java/SafeWays/app/src/main/java/Eclipse/CrimeSearch.java
/** @file CrimeSearch.java
 @author Jash Mehta
 @brief Searches for a crime within a given range
 @date February 28,2020
 */
package Eclipse;
import java.util.Map;

public class CrimeSearch {

	public static String search(String inputKey, int crimeKey, Map <String, Object> hashtable) {
		Map<String, Integer> sortedCrime = CrimeSorting.crimeSort(inputKey,hashtable); // Sorts the map in ascending order of crime weight
		String[][] crimeList = new String[sortedCrime.size()][2]; // Creates a new 2D array
		int i = 0;
		for (Map.Entry mapElement : sortedCrime.entrySet()) { // Iterates through the sorted map
			if (!mapElement.equals(null)) {
				crimeList[i][0] = (String) mapElement.getKey(); // Saves the key
				crimeList[i][1] = String.valueOf(mapElement.getValue()); // Saves the value
				i++;
			}
		}

		if(crimeList[0][0].equals("-1")) {
			return "Does Not Exist";
		}

		if (indexOf(crimeList, crimeKey) != -1) {
			return crimeList[indexOf(crimeList, crimeKey)][0];
		} else {
			return "Does Not Exist";
		}
	}

	public static int indexOf(String[][] a, int key) {
		int lo = 0;
		int hi = a.length - 1;
		int mid = lo + (hi - lo) / 2;
		while (lo <= hi) {
			mid = lo + (hi - lo) / 2;
			if (key < Integer.parseInt(a[mid][1])) { // Checks if key is less than mid
				hi = mid - 1;
			} else if (key > Integer.parseInt(a[mid][1])) { // Checks if key is greater than mid
				lo = mid + 1;
			} else {
				return mid;
			}
		}
		// Return conditions based on closest key value
		if(mid+1 > a.length || mid-1 < a.length) {
			return mid;
		}
		if(Math.abs(key - Integer.parseInt(a[mid-1][1])) < Math.abs(key - Integer.parseInt(a[mid][1]))) {
			return mid-1;
		}
		else if(Math.abs(key - Integer.parseInt(a[mid][1])) < Math.abs(key - Integer.parseInt(a[mid+1][1]))) {
			return mid;
		}
		else {
			return mid+1;
		}
	}

//	public static void main(String[] args) {
//		String starting = "YORK ST";
//		int crimeKey = 4390;
//		String street = search(starting, crimeKey,Map <String, Object> hashtable);
//		System.out.println(street);
//	}

}
=======
/** @file CrimeSearch.java
 @author Jash Mehta
 @brief Searches for a crime within a given range
 @date February 28,2020
 */
package Eclipse;
import java.util.Map;

public class CrimeSearch {

	public static String search(String inputKey, int crimeKey, Map <String, Object> hashtable) {
		Map<String, Integer> sortedCrime = CrimeSorting.crimeSort(inputKey,hashtable);
		String[][] crimeList = new String[sortedCrime.size()][2];
		int i = 0;
		for (Map.Entry mapElement : sortedCrime.entrySet()) {
			if (!mapElement.equals(null)) {
				crimeList[i][0] = (String) mapElement.getKey();
				crimeList[i][1] = String.valueOf(mapElement.getValue());
				i++;
			}
		}

		if(crimeList[0][0].equals("-1")) {
			return "Does Not Exist";
		}

		if (indexOf(crimeList, crimeKey) != -1) {
			return crimeList[indexOf(crimeList, crimeKey)][0];
		} else {
			return "Does Not Exist";
		}
	}

	public static int indexOf(String[][] a, int key) {
		int lo = 0;
		int hi = a.length - 1;
		int mid = lo + (hi - lo) / 2;
		while (lo <= hi) {
			mid = lo + (hi - lo) / 2;
			if (key < Integer.parseInt(a[mid][1])) {
				hi = mid - 1;
			} else if (key > Integer.parseInt(a[mid][1])) {
				lo = mid + 1;
			} else {
				return mid;
			}
		}
		if(mid+1 > a.length || mid-1 < a.length) {
			return mid;
		}
		if(Math.abs(key - Integer.parseInt(a[mid-1][1])) < Math.abs(key - Integer.parseInt(a[mid][1]))) {
			return mid-1;
		}
		else if(Math.abs(key - Integer.parseInt(a[mid][1])) < Math.abs(key - Integer.parseInt(a[mid+1][1]))) {
			return mid;
		}
		else {
			return mid+1;
		}
	}

	/*public static void main(String[] args) {
		String starting = "YORK ST";
		int crimeKey = 4390;
		String street = search(starting, crimeKey,Map <String, Object> hashtable);
		System.out.println(street);
	}*/

}
>>>>>>> ba7814a03e02346e98e2d9c739d0d1b08d9201c4:Android/SafeWays/app/src/main/java/Eclipse/CrimeSearch.java
