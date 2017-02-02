package Database;

import java.util.ArrayList;


public class sortDatabase {
	

	private static final Exception exception = null;

	public static  ArrayList<ArrayList<String>> sortArray(ArrayList<ArrayList<String>> table, int column, int dataType) throws java.lang.Exception{
		ArrayList<ArrayList<String>> temp = null;		
		
		switch(dataType){
			case(1):
				temp = sortString(table, column);
				break;
			case(2):
				temp = sortDouble(table, column);
				break;
			case(3):
				break;
			default:
				System.err.printf("Data type \"%d\" unknown, can't sort%n", dataType);
				throw exception;		
		}
		
		return temp;
		
		
		
	}
	
	public static  ArrayList<ArrayList<String>> sortString(ArrayList<ArrayList<String>> table, int column) throws java.lang.Exception{
		ArrayList<String> sort = new ArrayList<String>(0);
		
		// get(row).get(column)
		for(int i=0; i < table.size(); i++){
			sort.add(table.get(i).get(column));
		}
		
			 int row;
		     boolean whileSwap = true; 
		     String temp;
		     
		     
		     //Keep going if a swap happens
		     while ( whileSwap )
		     {
		         whileSwap= false;    //set flag to false awaiting a possible swap
		         for( row=0;  row < table.size() -1;  row++ )
		         {
		             if (table.get(row).get(column).compareToIgnoreCase(table.get(row + 1).get(column)) > 0)   // change to > for ascending sort
		             {
		                  temp = table.get(row).get(column);                
		                  table.get(row).set(column, table.get(row+1).get(column));
		                  table.get(row + 1).set(column, temp);
		                  
		                  int i;
		                  for(i = 0; i < table.get(0).size(); i++){
		                	  if (i != column){
		                	     temp = table.get(row).get(i);                
			                     table.get(row).set(i, table.get(row+1).get(i));
			                     table.get(row + 1).set(i, temp);
		                	  }		                	  
		                  }
		                  
		                  //Swap happened check again  
		                  whileSwap = true;                                      
		            } 
		         } 
		      }     
	
		     return table;
	}
	
	public static  ArrayList<ArrayList<String>> sortDouble(ArrayList<ArrayList<String>> table, int column) throws java.lang.Exception{
		ArrayList<String> sort = new ArrayList<String>(0);
		
		// get(row).get(column)
		for(int i=0; i < table.size(); i++){
			sort.add(table.get(i).get(column));
		}
		
			 int row;
		     boolean whileSwap = true; 
		     String temp;
		     
		     
		     //Keep going if a swap happens
		     while ( whileSwap )
		     {
		         whileSwap= false;    //set flag to false awaiting a possible swap
		         for( row=0;  row < table.size() -1;  row++ )
		         {
		             if (Double.parseDouble(table.get(row).get(column)) > Double.parseDouble(table.get(row + 1).get(column)))   // change to > for ascending sort
		             {
		                  temp = table.get(row).get(column);                
		                  table.get(row).set(column, table.get(row+1).get(column));
		                  table.get(row + 1).set(column, temp);
		                  
		                  int i;
		                  for(i = 0; i < table.get(0).size(); i++){
		                	  if (i != column){
		                	     temp = table.get(row).get(i);                
			                     table.get(row).set(i, table.get(row+1).get(i));
			                     table.get(row + 1).set(i, temp);
		                	  }		                	  
		                  }
		                  
		                  //Swap happened check again  
		                  whileSwap = true;                                      
		            } 
		         } 
		      }     
	
		     return table;
	}
	
	
	


}
