package Database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

public class Databases implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Exception FileAlreadyExistsException = null;
	private static final Exception FileNotFound = null;
	private static final Exception IndexOutOfBoundsException = null;
	private static final Exception ParseException = null;
	private String databaseLocation = null;
	private String databaseList = null;
	private String lastReadDatabase = null;
	private String databaseInfoFolder = "\\DatabaseInfo\\";
	private ArrayList<String> databaseTable = new ArrayList<String>(0);
	private ArrayList<ArrayList<String>> lastReadArray;
	private infoDatabase databaseInfo = null;
	
	public Databases(String databaseLocation){
		
		//Create save file locations
		this.databaseLocation = String.format("%s\\", databaseLocation);
		databaseList = String.format("%s\\List.csc", databaseLocation);
        String name = String.format("%s\\%s", databaseLocation, "List.csc");
        String name2 = String.format("%s%s", databaseLocation, databaseInfoFolder);
		
		File file = new File(name);
		File file2 = new File(name2);
		
		//Create and fill temp array with file name locations
        ArrayList<String> temp = new ArrayList<String>();
		
        if(file.exists()){
            try{			
	            BufferedReader reader = new BufferedReader(new FileReader(databaseList));
	            String line = ""; 			
		        String[] cols = line.split(","); //note that if you have used space as separator you have to split on " "
				
		        for(String  c : cols)
	  	        {
		 	        temp.add(c);
		        }               
			        reader.close();
		        }catch (IOException e) {
			       e.printStackTrace();
		        }
            }else{
            	try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        
        if (!file2.exists()) {
			boolean successful = file2.mkdir();
		    if (successful)
		    {
		      System.out.println("created DatabaseInfo/");		      
		    }
		    else
		    {		      
		      System.out.println("failed trying tos create the directory");		    
		    }
	   }else{
		   System.out.println("directory already exists");
	   }
    }
	
	public ArrayList<String> getTable(){
		return databaseTable;
		
	}
	
	public void createDatabase(String databaseName, int[] dataType, ArrayList<String> firstRow, String[] names) throws Exception{
		
		String name = String.format("%s%s%s", databaseLocation, databaseInfoFolder, databaseName);
		System.out.println(name);
		
		File file = new File(name);
		  
		//Create the file or throw FileAlreadyExistsException;
		
		try {
			if (file.createNewFile()){
			}else{
			}
		} catch (IOException e) {
			throw FileAlreadyExistsException;
		}
		
		//Add database to the list
		databaseTable.add(databaseName);
		
		//Create and save info
		infoDatabase e = new infoDatabase(databaseName, String.format("%s%s.csc", databaseLocation, databaseName), dataType, names);
		saveFile(databaseName, e);
		
		//Create the table
		ArrayList<ArrayList<String>> temp = new  ArrayList<ArrayList<String>>(1);
		temp.add(firstRow);
		
		
		System.out.println(databaseLocation);
		writeDatabase.write(databaseName, databaseLocation, temp);
		lastReadArray = temp;
		
	}
	
    public void deleteDatabase(String databaseName) throws Exception{
		
    	String name = String.format("%s%s%s", databaseLocation, databaseInfoFolder, databaseName);
    	String name2 = String.format("%s/%s.csc", databaseLocation, databaseName);
    	
		File file = new File(name);
		File file2 = new File(name2);
		  
		//delete the file
		if (file.delete() && file2.delete()){
		    System.out.println("File is deleted!");
		}else{
			throw FileAlreadyExistsException;
		}
		
		//Search for database name in file and remove it from list
		boolean found = false;
		int count = 0;
		String compare;
		
		System.out.println("Deleting");
		
		while(!found){
			compare = databaseTable.get(count);
			
			if(new String(databaseName).equals(compare)){
				databaseTable.remove(count);
				found = true;
			}else{
				count++;
				System.out.print("Not found");
			}
			
		}
		
		//Delete the file
       
	}
    
    public int getSize(String databaseName) throws Exception{
    	readDatabase(databaseName);
    	return  lastReadArray.size();
    }
    
    public void addColumn(String databaseName, int dataType, int place, String name) throws Exception{
    	
    	//Load info of Data base
    	readDatabase(databaseName);
    	infoDatabase e = databaseInfo;
    	
    	//Make changes
    	e.addColumn(dataType, place, name);

    	int rowSize;
    	
    	try{    		
    	     rowSize = lastReadArray.get(0).size();
    	}catch(NullPointerException e1){
    		 rowSize = 0;
    	}
    	ArrayList<String> newColumn = new ArrayList<String>(rowSize);
   	
    	for(int i = 0; i <= rowSize; i++){
    		newColumn.add(" ");
    	}
    	
    	//Adds new column to the correct place, if place is over size adds to end 
    	int arraySize;
    	
    	try{
    		arraySize = lastReadArray.size();
      	}catch(NullPointerException e1){
      		arraySize = 0;
   	    }
    	
    	if(place < arraySize){
    	    lastReadArray.add(place, newColumn);
    	}else{
    		lastReadArray.add(newColumn);
    	}

    	
    	//Save changes
    	saveFile(databaseName, e); 
    	writeDatabase.write(databaseName, databaseLocation, lastReadArray);
    }
    
   public void deleteColumn(String databaseName, int place) throws Exception{
    	
	    //Load info of database
	   readDatabase(databaseName);
   	   infoDatabase e = databaseInfo;
    	
    	//Make changes
    	e.removeColumn(place);    	
    	lastReadArray.remove(place);
    	
    	//Save changes
    	saveFile(databaseName, e);    	
    }
   
    public boolean exist(String databaseName){
    	
    	return databaseTable.contains(databaseName);    
    }
    
   
    
    public int[] getDatabaseColumn(String databaseName) throws Exception{
		
    	   //Load and return database info
    	  readDatabase(databaseName);
    	  infoDatabase e = databaseInfo;
    	  
    	  return e.getTypes();     	  
    	    	   	
    }
    
    public void setPrime(String databaseName, int primaryColumn) throws Exception{
    	
    	//Load info of Data base
    	readDatabase(databaseName);
    	infoDatabase e = databaseInfo;
    			
    	//Make changes
    	e.setPrimary(primaryColumn); 
    	
    	//Sort and save
    	
    	 lastReadArray = sortDatabase.sortArray(lastReadArray, databaseInfo.getPrimary());
    	
    	
    	if(databaseTable.contains(databaseName)){
      	  writeDatabase.write(databaseName, databaseLocation, lastReadArray);
        }else{
      	  throw FileNotFound;
        }      
      
    }
    
    public int getPrime(String databaseName) throws Exception{
    	
    	//Load info of Data base
    	readDatabase(databaseName);
    	infoDatabase e = databaseInfo;
    	
    	//Return prime
    	return e.getPrimary(); 	
    }
    
    
    
    private void saveFile(String databaseName, infoDatabase e) throws Exception {
    	
    	//Create file path
    	String name = String.format("%s%s\\%s", databaseLocation, databaseInfoFolder, databaseName);
    	
    		try {
    		
    			//Write file
 
    			FileOutputStream fileOut =
    					new FileOutputStream(name);
    			ObjectOutputStream out = new ObjectOutputStream(fileOut);
    			out.writeObject(e);
    			out.close();
    			fileOut.close();
            
    		}catch(IOException i) {
    			i.printStackTrace();;
    		}
    	
    	
    	//save local variables 
    	databaseInfo = e;
    	lastReadDatabase = databaseName;
    	lastReadArray = readDatabase(databaseName);
    }
    
    public void addRow(String databaseName, ArrayList<String> row) throws Exception{   	

      //Pull database into memory
      readDatabase(databaseName); 
    	
      //check if row is okay
      if(!checkRow(databaseName, row)){
    	  throw ParseException;
      }
      
      //Add row then sort 
      lastReadArray.add(row);
     
      lastReadArray = sortDatabase.sortArray(lastReadArray, databaseInfo.getPrimary());
      
      //Save file
      if(databaseTable.contains(databaseName)){
    	  writeDatabase.write(databaseName, databaseLocation, lastReadArray);
      }else{
    	  throw FileNotFound;
      }      
    }
    
    public void changeRow(String databaseName, ArrayList<String> row, int changeRow) throws Exception{   	

        //Pull database into memory
        readDatabase(databaseName); 
      	
        //check if row is okay
        if(!checkRow(databaseName, row)){
      	  throw ParseException;
        }
        
        //Add row then sort 
        lastReadArray.remove(changeRow);
        lastReadArray.add(changeRow, row);
       
        lastReadArray = sortDatabase.sortArray(lastReadArray, databaseInfo.getPrimary());
        
        //Save file
        if(databaseTable.contains(databaseName)){
      	  writeDatabase.write(databaseName, databaseLocation, lastReadArray);
        }else{
      	  throw FileNotFound;
        }      
      }
    
    
    
    public void deleteRow(String databaseName, int place) throws Exception{
    	
        readDatabase(databaseName); 

    	
    	if(place >= lastReadArray.size()){;
			throw IndexOutOfBoundsException;
    	}
    	
    	lastReadArray.remove(place);
    	
    }
    
    public boolean checkRow(String databaseName, ArrayList<String> Row) throws Exception{
    	readDatabase(databaseName);
    	int[] Types = databaseInfo.getTypes();
    	
    	//Check to see if Row is correct length
    	
    	if(Row.size() == Types.length){
    		
    		
    		//See if it has the right dataTypes
    		for(int i = 0; i < Row.size(); i++){    			
    			//Right now any value can be null so it's never wrong
    			if(Row.get(i)!= null){
    				switch (Types[i]) {
    	   	         case 1: //Check if string (It'a already a string :D)
    	   	             break;
    	   	         case 2: //check if double
    	   	        	
    	   	        	 try{
    	   	        		@SuppressWarnings("unused")
    						double d = Double.parseDouble(Row.get(i));
    	   	        		
    	   	        	 }
    	   	        	 catch(NumberFormatException n)
    	   	        	 {
    	   	        		System.out.println("Is not number");
    	   	        		return false;
    	   	        	 }
    	   	        	 
    	   	        	 break;
    	   	         case 3: //Check if date (only takes MMM d yyyy)
    	   	        	 try{
    	   	        		SimpleDateFormat parser = new SimpleDateFormat("MMM d yyyy HH:mm:ss");
    	   	                @SuppressWarnings("unused")
							Date date = parser.parse(Row.get(i)); 
    	   	        	 }
    	   	        	 catch(DateTimeParseException  e){
    	   	        		System.out.println("Is not date");
    	   	        		 return false;
    	   	        	 }
    	   	        	 
    	   	        	 break;
    	   	         
    	   	         default:
    	   	        	 System.out.println("Error in infoDatabase");
    	   	        }//ends switch
    			}//ends if not null 			
    		} //ends for loop            		
    	}else{
			return false;
		}
    	
    	return true;
    }
    
    public ArrayList<ArrayList<String>> readDatabase(String databaseName) throws Exception{
    	
    	infoDatabase e = null;
    	if(!(new String(databaseName).equals(lastReadDatabase))){
    	          String name = String.format("%s%s%s", databaseLocation, databaseInfoFolder, databaseName);
    	          String name2 = String.format("%s%s.csc", databaseLocation, databaseName);
    	          if(databaseTable.contains(databaseName)){    	    	      
    	    	      
    	    	      FileInputStream fileIn = new FileInputStream(name);
		               ObjectInputStream in = new ObjectInputStream(fileIn);
		              
		               e = (infoDatabase) in.readObject();
		               
		               //Set local variables
		               databaseInfo = e;
		         
		                //close reader
		                
		                in.close();
		                fileIn.close();
		                
		                lastReadArray = readDatabase.readAll(name2);
	    	    	    lastReadDatabase = databaseName;
		                
                  }else{
      	              throw FileNotFound;
                  }
    	     
    	}else{
    	}
    	
    	return lastReadArray;
    	
    }
    
    public ArrayList<ArrayList<String>> readDatabaseRang(String databaseName,int rowStart, int range) throws Exception{
    	 
    	
    	readDatabase(databaseName);
    	ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>(0);
    	
    	for(int i = 0; i <= range; i++){
    		temp.add(lastReadArray.get(rowStart + i));
    	}
    	
    	return temp;
          
    }
    
   
    
    
    public String[] getColumnNames(String databaseName) throws Exception{
    	
    	readDatabase(databaseName);    	
    	return databaseInfo.getColumnName(); 
    	
    }
    
    
    
    public static String toString(ArrayList<ArrayList<String>> table){
    	String returnString = "";
    	
    	for(int i = 0; i < table.size(); i++)//for each row
        {
           for(int j = 0; j < table.get(i).size(); j++)//for each column
           {

        	   returnString = String.format("%s %s, ", returnString, table.get(i).get(j));  
           }
           
           returnString = String.format("%s %s%n", returnString, table.get(i).get(table.get(i).size() - 1));
        }
    	
    	return returnString;	
    }
    
    public String toString(String databaseName) throws Exception{
    	
    	readDatabase(databaseName);
    	
    	return toString(lastReadArray);	
    }
    
    

}
