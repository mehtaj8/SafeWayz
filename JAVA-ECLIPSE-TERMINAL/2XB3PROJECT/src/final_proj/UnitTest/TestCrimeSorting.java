package final_proj.UnitTest;

import org.junit.*;

import final_proj.CrimeSortSearch.CrimeSorting;

import static org.junit.Assert.*;

import java.util.Map;

public class TestCrimeSorting{
    private Map<String, Integer> result;

    @Before
    public void setUp(){
    }

    @After
    public void tearDown(){
    }

    //Invalid Street
    @Test
    public void test_crimeSortA(){
        this.result = CrimeSorting.crimeSort("InvalidStreet");
        assertEquals(result.Entry.getKey(), "-1");
    }
}