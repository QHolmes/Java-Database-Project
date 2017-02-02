package Database;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class writeDatabase {

	/*
	 * Writes a given 2D ArrayList<ArrayList<String>> (table) as the given file name (databaseName)
	 * 
	 */
   protected static void write(String databaseName, String databaseLocation, ArrayList<ArrayList<String>> table){   
	   

	try {
	          
	          BufferedWriter writer = new BufferedWriter(new FileWriter(String.format("%s\\%s.csv", databaseLocation, databaseName)));
		
	          for(int c = 0; c < table.size(); c++)//for each row
	          {
	        	
	        	 StringBuilder builder = new StringBuilder();
	        	 
	             for(int r = 0; r < table.get(c).size(); r++)//for each column
	            {	 
	            	 String builderString = table.get(c).get(r)+"";
	            	 
	            	 if(builderString == null || builderString.isEmpty()){
	            		 builderString = "null";
	            	 }
	            	 
	            	 
	                builder.append(builderString);
	                if(r < table.get(c).size() - 1)//if this is not the last row element
	                   builder.append(",");//add comma
	             }
	                 writer.write(builder.toString());
	                 writer.newLine();//new line
	            }
	              	
			      writer.close();
			      
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
   
}


