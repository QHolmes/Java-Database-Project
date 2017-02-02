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
	
	public static ArrayList<ArrayList<String>> readCSV(String databaseLocation){
		
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
		boolean enclosed = false;
		String enclosedString = "";
		
		try{
			
			BufferedReader reader = new BufferedReader(new FileReader(databaseLocation));
			String line = "";
			
			while((line = reader.readLine()) != null) //While the next row is not blank
			{
				String[] readCols = line.split(","); //note that if you have used space as separator you have to split on " "
				ArrayList<String> colsAdd = new ArrayList<String>();
				
				
				
				for(int i = 0; i < readCols.length; i++){
					
					//If string starts with '"' it is enclosed and not currently enclosed
					if(readCols[i].charAt(0) == '"' && !enclosed){
						if(readCols[i].charAt(readCols[i].length() - 1) == '"'){
							colsAdd.add(readCols[i].substring(1, readCols[i].length() - 2));
						}else{
							enclosed = true;
							enclosedString = readCols[i].substring(1);
						}
						
					}else{
						
						if(enclosed){
							//If it is currently enclosed and ends with '"' it is the end
							if(readCols[i].charAt(readCols[i].length() - 1) == '"'){
								enclosedString = String.format("%s%s", enclosedString, readCols[i].substring(1));
								colsAdd.add(enclosedString);
								enclosed = false;
							}else{
								//if enclosed but not the end add to enclosed string
								enclosedString = String.format("%s%s", enclosedString, readCols[i]);
							}
						//If not enclosed then add to list normally
						}else{
							colsAdd.add(readCols[i]);
						}
					}
					
				}//End checking for enclosed
				
				

				ArrayList<String> rowInput = new ArrayList<String>(); //Fills a temp arraylist then adds it to table
				
				for(int i = 0; i < colsAdd.size(); i++)
				{
					String c = colsAdd.get(i);
					
					if(new String(c).equals("null")){
			    		rowInput.add("");
			    	}else{
			    	rowInput.add(c);
			    	}
				}
				
               table.add(rowInput);
			}//End while loop
			
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
		boolean enclosed = false;
		String enclosedString = "";
		
try{
			
			BufferedReader reader = new BufferedReader(new FileReader(databaseName));
			String line = "";
			int currentRow = 0;
			
			boolean found = false;
			
			while(!found && (line = reader.readLine()) != null )
				
				//If current row is the row we are looking for read, else inc currentRow
				if(currentRow == row){
				    String[] readCols = line.split(","); //note that if you have used space as separator you have to split on " "
				    
				    for(int i = 0; i < readCols.length; i++){
						
						//If string starts with '"' it is enclosed and not currently enclosed
						if(readCols[i].charAt(0) == '"' && !enclosed){
							if(readCols[i].charAt(readCols[i].length() - 1) == '"'){
								table.add(readCols[i].substring(1, readCols[i].length() - 2));
							}else{
								enclosed = true;
								enclosedString = readCols[i].substring(1);
							}
							
						}else{
							
							if(enclosed){
								//If it is currently enclosed and ends with '"' it is the end
								if(readCols[i].charAt(readCols[i].length() - 1) == '"'){
									enclosedString = String.format("%s%s", enclosedString, readCols[i].substring(1));
									table.add(enclosedString);
									enclosed = false;
								}else{
									//if enclosed but not the end add to enclosed string
									enclosedString = String.format("%s%s", enclosedString, readCols[i]);
								}
							//If not enclosed then add to list normally
							}else{
								table.add(readCols[i]);
							}
						}
						
					}//End checking for enclosed
				    
				    found = true;
				}else{
				    currentRow++;
				}
			
			
			reader.close();
		}catch (IOException e) {
			e.printStackTrace();
		}	
		
		
		return table;		
	}	
	
	/*
	 * Reads a given database (databaseName) for the given row (rowStart) and then reads the next X (range) rows
	 * and return row as a String array. (includes the last one)
	 * 
	 * range needs to be a positive number, if range passes the end of line returned will be cut short
	 * 
	 * Throws out of bounds and file not found
	 */
	
	protected static ArrayList<ArrayList<String>> readRowRang(String databaseName,int rowStart, int range){
		
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
		boolean enclosed = false;
		String enclosedString = "";
		
		try{
			
			BufferedReader reader = new BufferedReader(new FileReader(databaseName));
			String line = "";
			int currentRow = 0;
			
			boolean found = false;
					
			
			while(!found && (line = reader.readLine()) != null)	
				
				//If currentRow is between start and end
				if(currentRow >= rowStart && currentRow <= range + rowStart){
					
					String[] readCols = line.split(","); //note that if you have used space as separator you have to split on " "
					ArrayList<String> colsAdd = new ArrayList<String>();
					
					
					
					for(int i = 0; i < readCols.length; i++){
						
						//If string starts with '"' it is enclosed and not currently enclosed
						if(readCols[i].charAt(0) == '"' && !enclosed){
							if(readCols[i].charAt(readCols[i].length() - 1) == '"'){
								colsAdd.add(readCols[i].substring(1, readCols[i].length() - 2));
							}else{
								enclosed = true;
								enclosedString = readCols[i].substring(1);
							}
							
						}else{
							
							if(enclosed){
								//If it is currently enclosed and ends with '"' it is the end
								if(readCols[i].charAt(readCols[i].length() - 1) == '"'){
									enclosedString = String.format("%s%s", enclosedString, readCols[i].substring(1));
									colsAdd.add(enclosedString);
									enclosed = false;
								}else{
									//if enclosed but not the end add to enclosed string
									enclosedString = String.format("%s%s", enclosedString, readCols[i]);
								}
							//If not enclosed then add to list normally
							}else{
								colsAdd.add(readCols[i]);
							}
						}
						
					}//End checking for enclosed
					
					

					ArrayList<String> rowInput = new ArrayList<String>(); //Fills a temp arraylist then adds it to table
					
					for(int i = 0; i < colsAdd.size(); i++)
					{
						String c = colsAdd.get(i);
						
						if(new String(c).equals("null")){
				    		rowInput.add("");
				    	}else{
				    	rowInput.add(c);
				    	}
					}
					
	               table.add(rowInput);			
					
				}
				
				
				currentRow++;
				
			
			reader.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		return table;
		
	}

}
