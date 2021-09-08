/** @file CrimeSearch.java
@author Anando Zaman
@brief Writes to external file
@date February 28, 2020
*/
package final_proj.FileWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class File_writer {
	
	/*Static method that writes a given string to a file*/
    public static void Write_to_file(String str, String output_name) 
	{ 
		try { 
			BufferedWriter output = new BufferedWriter(new FileWriter(output_name, true)); //append mode set to true
			output.write(str); 
			output.close(); 
		} 
		catch (IOException e) { 
			System.out.println("ERROR, could not write to file"); 
		} 
	} 
}
