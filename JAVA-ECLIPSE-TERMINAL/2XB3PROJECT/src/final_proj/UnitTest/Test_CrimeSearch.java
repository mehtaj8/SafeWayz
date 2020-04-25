/** @file Test_CrimeSearch.java
@author Jash Mehta
@brief JUnit tests for CrimeSearch module
@date DATE HERE
*/
package final_proj.UnitTest;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import final_proj.CrimeSortSearch.CrimeSearch;

public class Test_CrimeSearch {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	// Invalid Street.
	@Test
	public void test_searchA() {
		String result = CrimeSearch.search("Invalid", 2900);
		assertEquals(result, "Does Not Exist");
	}
	
	@Test
	public void test_searchB() {
		String result = CrimeSearch.search("YORK ST", 4390);
		assertEquals(result, "26TH ST");
	}
	
	//Extra Test Case for Accuracy
	@Test
	public void test_searchC() {
		String result = CrimeSearch.search("GIRARD ST", 21000);
		assertEquals(result, "SILLIMAN ST");
	}

}
