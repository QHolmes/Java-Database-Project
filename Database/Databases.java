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
		databaseList = String.format("%s\\List.csv", databaseLocation);
        String name = String.format("%s\\%s", databaseLocation, "List.csv");
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
	
	public void createDatabase(String databaseName, int[] dataType, String[] names) throws Exception{
		
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
		infoDatabase e = new infoDatabase(databaseName, String.format("%s%s.csv", databaseLocation, databaseName), dataType, names);
		saveFile(databaseName, e);
		
		//Create the table
		ArrayList<ArrayList<String>> temp = new  ArrayList<ArrayList<String>>(0);
		
		
		System.out.println(databaseLocation);
		
		lastReadArray = temp;
		writeData(databaseName);
		
	}
	
	public void importDatabase(String databaseName, int[] dataType, ArrayList<ArrayList<String>> firstRow, String[] names) throws Exception{
		
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
		infoDatabase e = new infoDatabase(databaseName, String.format("%s%s.csv", databaseLocation, databaseName), dataType, names);
		saveFile(databaseName, e);
		
		//Create the table	
		
		System.out.println(databaseLocation);
		
		lastReadArray = firstRow;
		writeData(databaseName);
		
	}
	
    private void writeData(String databaseName) {
    	
    	String[] name = databaseInfo.getColumnName();
    	ArrayList<String> temp = new ArrayList<String>(0);
    	
    	for(int i = 0; i < name.length; i++){
    		temp.add(name[i]);
    	}
    	
    	lastReadArray.add(0, temp);
    	writeDatabase.write(databaseName, databaseLocation, lastReadArray);
    	lastReadArray.remove(0);
		
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

	public void deleteDatabase(String databaseName) throws Exception{
		
    	String name = String.format("%s%s%s", databaseLocation, databaseInfoFolder, databaseName);
    	String name2 = String.format("%s/%s.csv", databaseLocation, databaseName);
    	
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
    
    public boolean exist(String databaseName){
    	
    	return databaseTable.contains(databaseName);    
    }
    
   
    
    public void setPrime(String databaseName, int primaryColumn) throws Exception{
    	
    	//Load info of Data base
    	readDatabase(databaseName);
    	infoDatabase e = databaseInfo;
    			
    	//Make changes
    	e.setPrimary(primaryColumn); 
    	
    	//Sort and save
    	
    	int[] i = databaseInfo.getTypes();
    	
    	lastReadArray = sortDatabase.sortArray(lastReadArray, databaseInfo.getPrimary(), i[databaseInfo.getPrimary()]);
    	
    	
    	if(databaseTable.contains(databaseName)){
    		writeData(databaseName);
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
    
    
    
    public void addRow(String databaseName, ArrayList<String> row) throws Exception{   	

      //Pull database into memory
      readDatabase(databaseName); 
    	
      //check if row is okay
      if(!checkRow(databaseName, row)){
    	  throw ParseException;
      }
      
      //Add row then sort 
      lastReadArray.add(row);

      int[] i = databaseInfo.getTypes();
  	
  	  lastReadArray = sortDatabase.sortArray(lastReadArray, databaseInfo.getPrimary(), i[databaseInfo.getPrimary()]);
      
      //Save file
      if(databaseTable.contains(databaseName)){
    	  writeData(databaseName);
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
       
        int[] i = databaseInfo.getTypes();
    	
    	lastReadArray = sortDatabase.sortArray(lastReadArray, databaseInfo.getPrimary(), i[databaseInfo.getPrimary()]);
        
        //Save file
        if(databaseTable.contains(databaseName)){
        	writeData(databaseName);
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
    	 //Save file
        if(databaseTable.contains(databaseName)){
        	writeData(databaseName);
        }else{
      	  throw FileNotFound;
        }  
    	
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
    	   	         case 3: //Check if date (only takes MMM d yyyy HH:mm:ss)
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
    	          String name2 = String.format("%s%s.csv", databaseLocation, databaseName);
    	          if(databaseTable.contains(databaseName)){    	    	      
    	    	      
    	    	      FileInputStream fileIn = new FileInputStream(name);
		               ObjectInputStream in = new ObjectInputStream(fileIn);
		              
		               e = (infoDatabase) in.readObject();
		               
		               //Set local variables
		               databaseInfo = e;
		         
		                //close reader
		                
		                in.close();
		                fileIn.close();
		                
		                
		                
		                lastReadArray = readDatabase.readCSV(name2);
		                lastReadArray.remove(0);
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
    
   
    
    
    public void addColumn(String databaseName, int dataType, int place, String name, String defaultValue) throws Exception{
		
		//Load info of Data base
		readDatabase(databaseName);
		
		//Make changes
		
	
		int rowSize;
		int columnSize;
		
		try{    		
		     rowSize = lastReadArray.size();
		}catch(NullPointerException e1){
			 rowSize = 0;
		}
		
		try{    		
		     columnSize = lastReadArray.get(0).size();
		}catch(NullPointerException e1){
			 columnSize = 0;
		}
		
		databaseInfo.addColumn(dataType, name);		
		
		
		//Adds null values for each spot
		for(int i = 0; i < rowSize; i++){
			lastReadArray.get(i).add(defaultValue);
		}
		
		//Adds new column to the correct place, if the place is greater then the 
		if(place < columnSize){
			moveColumn(databaseName, place, columnSize);
		}	
	
		
		//Save changes
		saveFile(databaseName, databaseInfo); 
		writeData(databaseName);
	}
    
    public void changeColumn(String databaseName, int place, String name) throws Exception {
    	
    	//Load info of Data base
    			readDatabase(databaseName);
    			infoDatabase e = databaseInfo;
    			
       //Make changes
    			e.changeColumn(place, name);
    			
       //Save changes
    			saveFile(databaseName, e); 
    			writeData(databaseName);
    	
    }

	public void deleteColumn(String databaseName, int place) throws Exception{
		
	    //Load info of database
	   readDatabase(databaseName);
	   infoDatabase e = databaseInfo;
		
		//Make changes
		e.removeColumn(place);
		
		for(int i = 0; i < lastReadArray.size(); i++){
			lastReadArray.get(i).remove(place);
		}
		
		//Save changes
		saveFile(databaseName, e);    	
	}

	public int[] getDatabaseColumn(String databaseName) throws Exception{
		
		   //Load and return database info
		  readDatabase(databaseName);
		  infoDatabase e = databaseInfo;
		  
		  return e.getTypes();     	  
		    	   	
	}

	public String[] getColumnNames(String databaseName) throws Exception{
    	
    	readDatabase(databaseName);    	
    	return databaseInfo.getColumnName(); 
    	
    }
	
    public void moveColumn(String databaseName, int newPlace, int oldPlace) throws Exception{
    	readDatabase(databaseName);
    	
    	boolean forward = false;
    	boolean done = false;
    	String[] names = databaseInfo.getColumnName();
    	int[] Types = databaseInfo.getTypes();
    	int tempType;
    	String tempName;
    	
    	
    	 
    	
    	String temp = "";
    	
    	if(newPlace == oldPlace){
    		System.out.println("no move");
    		return;
    	}
    	
    	//Checks to see which way to go and or if done
		if(newPlace > oldPlace){
    		forward = true;
    	}else{
    		if(oldPlace > newPlace){
    			forward = false;
    		}else{
    			System.out.println("Already there");
    			done = true;
    		}
    	}
    	
    	while(!done){    		
        	
    		//If the new place is in front of the old place and not done
        	if(forward && !done){ 
        		for(int i = 0; i < lastReadArray.size(); i++){
            		temp = lastReadArray.get(i).get(newPlace);
            		lastReadArray.get(i).set(newPlace, lastReadArray.get(i).get(newPlace - 1));
            		lastReadArray.get(i).set(newPlace - 1, temp);
            	}
        		newPlace--;
        		
        		tempType = Types[newPlace];
        		Types[newPlace] = Types[newPlace -1];
        		Types[newPlace -1] = tempType;
        		
        		tempName = names[newPlace];
        		names[newPlace] = names[newPlace -1];
        		names[newPlace -1] = tempName;
        		
        		//if the gap between is now only 1
        		if(!(newPlace > oldPlace)){
        			done = true;
        		}
        	}
        	
        	//If the new place is behind the old place and not done
        	if(!forward && !done){  	
        		for(int i = 0; i < lastReadArray.size(); i++){
            		temp = lastReadArray.get(i).get(oldPlace);
            		lastReadArray.get(i).set(oldPlace, lastReadArray.get(i).get(oldPlace - 1));
            		lastReadArray.get(i).set(oldPlace - 1, temp);
            	}
        		oldPlace--;
        		
        		//if the gap between is now only 1
        		if(!(oldPlace > newPlace)){
        			done = true;
        		}
        		
        		tempType = Types[oldPlace +1];
        		Types[oldPlace +1] = Types[oldPlace];
        		Types[oldPlace] = tempType;
        		
        		tempName = names[oldPlace +1];
        		names[oldPlace +1] = names[oldPlace];
        		names[oldPlace] = tempName;
        	}
        	
        	databaseInfo.setColumnName(names);
        	databaseInfo.setTypes(Types);       	
        	
    	}
        	  	
    	
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
    
    public String getLocation(){
    	return databaseLocation;
    }
    

}
