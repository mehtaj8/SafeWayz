/** @file Test_CrimeSort.java
@author Jash Mehta
@brief JUnit tests for CrimeSort module
@date DATE HERE
*/
package final_proj.UnitTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import final_proj.CrimeSortSearch.CrimeSorting;

public class Test_CrimeSorting {
	private ArrayList<Integer> resultValue;

	@Before
	public void setUp() throws Exception {
		this.resultValue = new ArrayList<Integer>();
	}

	@After
	public void tearDown() throws Exception {
		this.resultValue = new ArrayList<Integer>();
	}

	// Invalid Input
	@Test
	public void test_crimeSortA() {
		Map<String, Integer> sorted = CrimeSorting.crimeSort("Invalid");
		int i = 0;
		for (Map.Entry mapElement : sorted.entrySet()) {
			this.resultValue.add(i, (Integer) mapElement.getValue());
			i++;
		}
		assert this.resultValue.get(0) == -1;
	}
	
	@Test
	public void test_crimeSortB() {
		Map<String, Integer> sorted = CrimeSorting.crimeSort("GIRARD ST");
		int i = 0;
		for (Map.Entry mapElement : sorted.entrySet()) {
			this.resultValue.add(i, (Integer) mapElement.getValue());
			i++;
		}
		String ans = "[0, 0, 0, 10, 2150, 2160, 5040, 6916, 7920, 10944, 17010, 27744, 43316, 82688, 83200, 204204]";
		assertEquals(this.resultValue.toString(), ans);
	}
	
	@Test
	public void test_crimeSortC() {
		Map<String, Integer> sorted = CrimeSorting.crimeSort("YORK ST");
		int i = 0;
		for (Map.Entry mapElement : sorted.entrySet()) {
			this.resultValue.add(i, (Integer) mapElement.getValue());
			i++;
		}
		String ans = "[0, 0, 4, 8, 1632, 2016, 2375, 6475, 9729, 29216, 37800, 59940, 70380, 201600, 819280]";
		assertEquals(this.resultValue.toString(), ans);
	}

}
