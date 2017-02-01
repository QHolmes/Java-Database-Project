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
	
    protected void addColumn(int dataType, int place, String name){
    	
		    Types = Arrays.copyOf(Types, 1 + Types.length);
		    names = Arrays.copyOf(names, 1 + names.length);


		if(place > Types.length){	
			
		     int temp01 = dataType;
		     
		     for(int i = place; i < Types.length; i++){
			     int temp02 = Types[i];
			     Types[i] = temp01;
			     temp01 = temp02;
		     }
		     
		     Types[place] = dataType;
		     names[place] = name;
		}else{
			Types[Types.length -1] = dataType;
		}
		
		
	}
	
    protected void removeColumn(int place){
		
		Types[place] = 0;
		
		int[] temp = new int[Types.length - 1];
		
		for(int i = 0; i < temp.length; i++){
			if(Types[i] != 0){
				temp[i] = Types[i];
			}
			
			Types = temp;
		}
		
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
    
    protected String[] getColumnName(){
        return names;
    }
}
