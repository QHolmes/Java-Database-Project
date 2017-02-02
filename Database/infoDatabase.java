package Database;

import java.io.Serializable;
import java.util.Arrays;

public class infoDatabase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8180284098554210057L;
	private int[] Types; //String = 1; Double = 2; Date = 3
	private String databaseName;
	private String databaseLocation;
	private int primaryColumn = 0;
	private String[] names;
	private Exception exception;
	
    protected infoDatabase(String databaseName, String databaseLocation, int[] Types, int primaryColumn, String[] names){
		
		this.Types = Types;
		this.databaseName = databaseName;
		this.primaryColumn = primaryColumn;
		this.databaseLocation = databaseLocation;
		this.names = names;
		
	}
	
	protected infoDatabase(String databaseName, String databaseLocation, int[] Types, String[] names){
		
		this.Types = Types;
		this.databaseName = databaseName;
		this.databaseLocation = databaseLocation;
		this.names = names;
		
	}
	
	
    protected String getName(){
		return databaseName;
	}
	
    protected void setPrimary(int primaryColumn){
    	this.primaryColumn = primaryColumn;
    }
    
    protected int getPrimary(){
    	return primaryColumn;
    }
    
    protected void setLocation(String databaseLocation){
    	this.databaseLocation = databaseLocation;
    }
    
    protected String getLocation(){
    	return databaseLocation;
    }
    
    protected int getSize(){
    	return Types.length;
    }
    
    protected int[] getTypes(){
    	return Types;
    }
    
    protected void setTypes(int[] newTypes) throws Exception{
    	
    	//Checks that there are the same number of variables 
    	if(Types.length == newTypes.length){
    		Types = newTypes;
    	}else{
    		System.err.println("Error setting column types");
    		System.err.println("Column types differnt lengths");
    		throw exception; 
    	}
    	
    }

	protected String[] getColumnName(){
	    return names;
	}
	
	protected void setColumnName(String[] newNames) throws Exception{
		//Checks that there are the same number of variables 
    	if(names.length == newNames.length){
    		names = newNames;
    	}else{
    		System.err.println("Error setting column names");
    		System.err.println("Column names differnt lengths");
    		throw exception; 
    	}
	}

	protected void addColumn(int dataType, String name){
		
		Types = Arrays.copyOf(Types, 1 + Types.length);
		names = Arrays.copyOf(names, 1 + names.length);
	
		Types[Types.length -1] = dataType;
		names[names.length -1] = name;
				
		
	}

	protected void removeColumn(int place){
		
		int[] temp = new int[Types.length - 1];
		int j = 0;
		
		for(int i = 0; i < Types.length; i++){
			if(i != place){
				temp[j] = Types[i];
				j++;
			}			
		}
		
		Types = temp;
		
		String[] temp2 = new String[names.length - 1];
		j=0;
		
		for(int i = 0; i < names.length; i++){
			if(i != place){
				temp2[j] = names[i];
				j++;
			}	
			
		}
		
		names = temp2;
		System.out.println("done");
	}
	
   protected void changeColumn(int place, String name){
		
	   names[place] = name;		
	}
}
