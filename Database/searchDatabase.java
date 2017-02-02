package Database;

import java.util.ArrayList;

public class searchDatabase {

	
	/* This method will search a column for the double value 'search' of an unsorted array
	 * This method requires the file name of data base (databaseName)
	 * This method also requires the column number that it should search
	 * Returns an array of int numbers that represents all locations that value was found
	 * 
	 */
	public static int[] SearchDoubleNS(double search, String databaseName, int column){
	    
		ArrayList<ArrayList<String>> table = readDatabase.readCSV(databaseName);
		double compare;
		int[] matches = new int[0];
		int arrayPlace = 0;
		
		for(int row = 1; row < table.size() ;row++){
			compare = Double.parseDouble(table.get(row).get(column));
			
			if(compare == search){				
				matches[arrayPlace] = row;	
				arrayPlace++;
			}
		}				
		
		return matches;
		
	}
	
	/* This method will search a column for the string value 'search' of an unsorted array
	 * This method requires the file name of data base (databaseName)
	 * This method also requires the column number that it should search
	 * Returns an array of int numbers that represents all locations that value was found
	 */
	
    public static int[] SearchStringNS(String search, String databaseName, int column){	 
    	
		ArrayList<ArrayList<String>> table = readDatabase.readCSV(databaseName);
		
		double compare;
		int[] matches = new int[0];
		int arrayPlace = 0;
		
		for(int row = 1; row < table.size() ;row++){
			compare = Double.parseDouble(table.get(row).get(column));
			
			if(new String(search).equals(compare)){		
				matches[arrayPlace] = row;	
				arrayPlace++;
			}
		}				
		
		return matches;
		
	}
	
}
