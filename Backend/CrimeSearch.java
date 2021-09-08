package Eclipse;

import android.util.Log;

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
