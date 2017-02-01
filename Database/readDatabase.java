package Database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class readDatabase  {
	
	
	/*
	 * Reads the file name (fileName) and returns it as a ArrayList<ArrayList<String>>
	 *  Needs to throws filenotfound
	 * 
	 */
	
	protected static ArrayList<ArrayList<String>> readAll(String databaseName){
		
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
		
		try{
			
			BufferedReader reader = new BufferedReader(new FileReader(databaseName));
			String line = "";
			
			while((line = reader.readLine()) != null) //While the next row is not blank
			{
				String[] cols = line.split(","); //note that if you have used space as separator you have to split on " "

				ArrayList<String> temp = new ArrayList<String>(); //Fills a temp arraylist then adds it to table
				
				for(String  c : cols)
				{
					if(new String(c).equals("null")){
			    		temp.add("");
			    	}else{
			    	temp.add(c);
			    	}
				}
               table.add(temp);
			}
			reader.close();
		}catch (IOException e) {
			e.printStackTrace();
		}

		return table;
		
	}
	
	/*
	 * Reads a given database (databaseName) for the given row and return row as a String array
	 * Throws out of bounds and file not found
	 */
	protected static ArrayList<String> readRow(String databaseName, int row){
		ArrayList<String> table = new ArrayList<String>();
		
try{
			
			BufferedReader reader = new BufferedReader(new FileReader(databaseName));
			String line = "";
			int currentRow = 0;
			
			boolean found = false;
			
			while(!found)
			{
				
				line = reader.readLine();
				
				if(currentRow == row){
				    String[] cols = line.split(","); //note that if you have used space as separator you have to split on " "

				    for(String  c : cols)
				    {
				    	if(new String(c).equals("null")){
				    		table.add("");
				    	}else{
				    	table.add(c);
				    	}
				    }
				    
				    found = true;
				}else{
				    currentRow++;
				}
			}
			reader.close();
		}catch (IOException e) {
			e.printStackTrace();
		}	
		
		
		return table;		
	}	
	
	/*
	 * Reads a given database (databaseName) for the given row (rowStart) and then reads the next X (range) rows
	 * and return row as a String array
	 * 
	 * Throws out of bounds and file not found
	 */
	
	protected static ArrayList<ArrayList<String>> readRowRang(String databaseName,int rowStart, int range){
		
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
		
try{
			
			BufferedReader reader = new BufferedReader(new FileReader(databaseName));
			String line = "";
			int row = rowStart;
			int currentRow = 0;
			
			boolean found = false;
					
			
			while(!found)
			{				
				line = reader.readLine();	
				
				if(currentRow == row){
					String[] cols = line.split(","); 
					
					for(int i = 0; i <= range; i++){
				        cols = line.split(","); 
				        ArrayList<String> temp = new ArrayList<String>();
				        
				        
				        for(String  c : cols)
				        {				        	
				        	if(new String(c).equals("null")){
					    		temp.add("");
					    	}else{
					    	temp.add(c);
					    	}
				        }
				        
				        table.add(temp);				        
				        line = reader.readLine();   
				        
					}//end first for loop
					
				    found = true;
				}else{
					currentRow++;
				}
				
				line = reader.readLine();
			}
			reader.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		return table;
		
	}

}
