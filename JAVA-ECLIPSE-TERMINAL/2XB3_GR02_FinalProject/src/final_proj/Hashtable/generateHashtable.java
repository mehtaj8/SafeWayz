/** @file generateHashtable.java
@author Anando Zaman & Aditya Sharma
@brief Parses JSON file to a HashMap
@date February 28, 2020
*/
package final_proj.Hashtable;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class generateHashtable {
	@SuppressWarnings("unchecked")
	public static Map<String, Object> hashifiy(String fileName){
		Map <String, Object> hashtable = null;
		try (FileReader reader = new FileReader("data/WeightedData.json"))//Read JSON file
        {
        	hashtable = new ObjectMapper().readValue(reader, HashMap.class);
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hashtable;
	}
}

