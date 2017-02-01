package Interface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import Database.Databases;

public class Interface {
	private static String Location = "/c:/test/";
	private static Databases database = null;
	private static int maxSize = 20;
	private static String fileName = String.format("%s%s", Location, "/info"); 
	
	

	public static void mainMenu(){
		
		load();
		
		boolean gate = true;
		
		System.out.println("Welcome to Java CSV database");		
		System.out.println();	
		
		
		Scanner s;
		
		while(gate){
			System.out.println("-------------------------|      Main Menu        |--------------------------");
			System.out.println("----------------------------------------------------------------------------");
			System.out.println();
			System.out.println();
			System.out.println("Please type and option below");
			System.out.println();
			System.out.println("Create database        \"create\"");
			System.out.println("add to database        \"add\"");
			System.out.println("view database          \"view\"");
			System.out.println("delete database        \"delete\"");
			
			System.out.println("Exit                   \"exit\"");
			
			s = new Scanner(System.in);			
			String input = s.nextLine();
		    String[] split = input.split("\\s+");
		    
		    switch(split[0].toLowerCase()){
		    	case("create"):
		    		create();
		    		break;
		    	case("add"):
		    		add();
		    		break;
		    	case("view"):
		    		view();
		    		break;
		    	case("delete"):
		    		delete();
		    		break;
		    	case("exit"):
		    		gate = false;
		    		break;
		    	default:
		    		gate = true;
		    		break;		    
		    }
		    
		    save();
		    
		}    
		
	}
	
	private static void settings() {
		String input = null;
		System.out.println("--------------------------|       Settings      |---------------------------");
		System.out.println("----------------------------------------------------------------------------");
		
		System.out.println();
		System.out.println();
		System.out.println("Where do you want the databases to be saved?");
				
		Scanner s = new Scanner(Location);
		boolean gate = true;
		
		while(gate){
			gate = false;
			s = new Scanner(System.in);
			
			input = s.nextLine();
			
			File file = new File(input);			
			
			
			if (!file.exists()) {
				boolean successful = file.mkdir();
			    if (successful)
			    {
			      // creating the directory succeeded
			      System.out.println("directory was created successfully");
			      
			    }
			    else
			    {
			      // creating the directory failed
			      System.out.println("failed trying to create the directory");
			      gate = true;
			    }
		  }else {
				if (!file.isDirectory()) {
				   System.out.println("Please enter a directory location");
			       gate = true;
			    }
		 
		 }}
		
		Location = input;
		fileName = String.format("%s%s", Location, "/info"); 
		database = new Databases(Location);
		
	}

	private static void add() {
		
		String input;
		String cloumnNames[] = null;
		int dataType[] = null;
		ArrayList<String> firstRow = new ArrayList<String>(0);
		
		System.out.println("------------------------|   Add to a database   |---------------------------");
		System.out.println("----------------------------------------------------------------------------");
		
		System.out.println();
		System.out.println();
		System.out.println("What Database whould you like to add to?");
		System.out.println("Please enter the corresponding number");
		ArrayList<String> table = database.getTable();
		
		if( table.size() <= 1){
			System.out.println("No datebases to view");
			return;
		}
		
		if(table.size() != 0){
			for(int i = 1; i < table.size(); i++){
				System.out.printf("     [%d] %s%n", i, table.get(i));
			}
		}else{
			System.out.println();
			System.out.println("No databases found.");
			return;
		}	
		
		Scanner s = new Scanner(Location);
		boolean gate = true;
		int choice = 0;
		
		while(gate){
			gate = false;
			s = new Scanner(System.in);
			
			try{
			   choice = s.nextInt();
			}catch(Exception e){
				System.out.println("Please enter the corresponding number");
				gate = true;
			}
		
		
			System.out.println();
			System.out.println();
		
			if(choice > table.size()){
				gate= true;
				System.out.println("That is not a valid number");
			}
			
			if(choice < 1){
				gate= true;
				System.out.println("That is not a valid number");
			}
		}
		
		String databaseName = table.get(choice);
		
		try {
			dataType = database.getDatabaseColumn(databaseName);
			cloumnNames = database.getColumnNames(databaseName);
		} catch (Exception e1) {
			
		}
		
		
		//-------------------------------------------------------------------
		//------------      Get first table to display     ------------------
		//-------------------------------------------------------------------
		
		
		
		
		
		Scanner s1 = new Scanner(databaseName);
		//Fill first row of data, checking the data entered
		for(int i = 0; i < dataType.length; i++){
			
			
			
			switch(dataType[i]){
			case(1):
				System.out.printf("Please enter column %s: (String)%n", cloumnNames[i]);
			
			    s1 = new Scanner(System.in);	
				input = s1.nextLine();
				firstRow.add(input);
				
			
				break;
			case(2):
				System.out.printf("Please enter column %s: (Numerical)%n", cloumnNames[i]);
			
				s1 = new Scanner(System.in);
				input = s1.nextLine();	    
				
				try{
   	        		@SuppressWarnings("unused")
					double d = Double.parseDouble(input); 
   	        		firstRow.add(input);   	        		
   	        	 }
   	        	 catch(NumberFormatException n)
   	        	 {
   	        		i--;
   	        		System.out.println("Input was not numerical.");
   	        	 }
				
				break;
			case(3):
				System.out.printf("Please enter column %s: (Date)%n", cloumnNames[i]);
			
				s1 = new Scanner(System.in);
				input = s1.nextLine();	    
				
				
				try{
   	        		SimpleDateFormat parser = new SimpleDateFormat("MMM d yyyy HH:mm:ss");
   	                @SuppressWarnings("unused")
					Date date = parser.parse(input); 
   	                firstRow.add(input);  
   	        	 }
   	        	 catch(DateTimeParseException | ParseException  e){
   	        		try{
   	   	        		SimpleDateFormat parser = new SimpleDateFormat("MMM d yyyy HH:mm:ss");
   	   	                @SuppressWarnings("unused")
   						Date date = parser.parse(input); 
   	   	                firstRow.add(input);  
   	   	        	 }
   	   	        	 catch(DateTimeParseException | ParseException  f){
   	   	        		i--;
   	   	        		System.out.println("Input was not the correct format (MMM d yyyy) or (MMM d yyyy HH:mm:ss).");
   	   	        	 }
   	        	 }
			
			default:
				System.out.println("Major error, returning to interface.");
				System.out.println("Sorry for the inconvenience.");
				break;
		}
			
		}
		
		//Try to add to the database
		try {
			database.addRow(databaseName, firstRow);
			System.out.println("Database created!");
		} catch (Exception e) {
			System.out.println("Database was not created!");
		}
		
		}
	

	private static void save() {
		
		try {
	         FileOutputStream fileOut =
	         new FileOutputStream(fileName);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(database);
	         out.close();
	         fileOut.close();
	      }catch(Exception i) {
	         i.printStackTrace();
	      }
		
	}

	private static void load() {
		settings();
		try {
	         FileInputStream fileIn = new FileInputStream(fileName);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         database = (Databases) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(ClassNotFoundException c) {
	         System.out.println("Old data not found");
	      }catch(Exception i) {
	    	     System.out.println("Old data not found");
	      }
		
	}

	private static void view(){
		String databaseName;
		int size = 0;
		ArrayList<ArrayList<String>> databaseTable = null;
		String[] column = null;
		int page = 1;
		boolean next = false;
		boolean back = false;
		String input;
		int place = 0;
		
		
		System.out.println("--------------------------|   View a database   |---------------------------");
		System.out.println("----------------------------------------------------------------------------");
		
		System.out.println();
		System.out.println();
		System.out.println("What Database whould you like to view?");
		System.out.println("Please enter the corresponding number");
		ArrayList<String> table = database.getTable();
		
		if( table.size() <= 0){
			System.out.println("No datebases to view");
			return;
		}
		
		if(table.size() != 0){
			for(int i = 0; i < table.size(); i++){
				System.out.printf("     [%d] %s%n", i+1, table.get(i));
			}
		}else{
			System.out.println();
			System.out.println("No databases found.");
			return;
		}	
		
		Scanner s = new Scanner(Location);
		boolean gate = true;
		int choice = 0;
		
		while(gate){
			gate = false;
			s = new Scanner(System.in);
			
			try{
			   choice = s.nextInt();
			}catch(Exception e){
				System.out.println("Please enter the corresponding number");
				gate = true;
			}
		
		
			System.out.println();
			System.out.println();
		
			if(choice > table.size()){
				gate= true;
				System.out.println("That is not a valid number");
			}
			
			if(choice < 1){
				gate= true;
				System.out.println("That is not a valid number");
			}
		}
		
		choice--;
		
		//-------------------------------------------------------------------
		//------------      Get first table to display     ------------------
		//-------------------------------------------------------------------
		
		
		databaseName = table.get(choice);
		
		try {
			size = database.getSize(databaseName);
			column = database.getColumnNames(databaseName);
		} catch (Exception e) {
		}
		
		if(size >= maxSize){
			try {
				databaseTable = database.readDatabaseRang(databaseName, 0, maxSize - 1);
			} catch (Exception e) {
				System.out.println("Critical error");
				e.printStackTrace();
			}
			next = true;
			place = maxSize - 1;
		}else{
			try {
				databaseTable = database.readDatabaseRang(databaseName, 0, size-1);
			} catch (Exception e) {
				System.out.println("Critical error");
				e.printStackTrace();
		    }
			
			place = size - 1;
		}
		
		gate = true;
		while(gate){
			System.out.printf("----------------Page: %d%n", page);			
				
			for(int i = 0; i < column.length; i++){
				System.out.printf("%10.10s ", column[i]);
			}
				
			System.out.println();
				
			for(int row = 0; row < databaseTable.size(); row++){//for each row		       
		       for(int col = 0; col < databaseTable.get(row).size(); col++){//for each column
		          
		          System.out.printf("%10.10s ", databaseTable.get(row).get(col));  
		       }		           
		       System.out.println();
		   }
			
		   if(back){
			  System.out.printf("Back a page \"back\"   "); 
		   }else{			   
			  System.out.printf("                       "); 
		   }
		   
		   if(next){
			   System.out.printf("Next page \"next\"    ");   
		   }else{
			   System.out.printf("                      "); 
		   }
		      
		      System.out.println("Done \"done\"");
		      
		      s = new Scanner(System.in);
			  input = s.nextLine();
		      
		      switch(input){
		    	case("next"):
		    		if(!next){
		    			System.out.println("Sorry that is not an option");
		    			break;
		    		}
		    		page++;
		    		
		    		
		    		if((maxSize + place) <= size){
		    			try {
		    				databaseTable = database.readDatabaseRang(databaseName, place, maxSize - 1);
		    			} catch (Exception e) {
		    				System.out.println("Critical error");
		    				e.printStackTrace();
		    			}
		    			next = true;
		    			back = true;
		    			place = place + maxSize; 
		    		}else{
		    			try {
		    				databaseTable = database.readDatabaseRang(databaseName, place, (size-1) - place);
		    			} catch (Exception e) {
		    				System.out.println("Critical error");
		    				e.printStackTrace();
		    		    }
		    			next = false;	
		    			back = true;
		    			place = size-1; 
		    		}
		    		break;
		    	case("back"):
		    		if(!back){
		    			System.out.println("Sorry that is not an option");
		    			break;
		    		}
		    	    page--;
		    	    
		    	    if(place == (size - 1)){
                 	place = place - (size % maxSize);		    	    	
		    	    }
		    	    
		    		if((place - maxSize) >= 0){
		    			try {
		    				databaseTable = database.readDatabaseRang(databaseName, place - maxSize, maxSize);
		    			} catch (Exception e) {
		    				System.out.println("Critical error");
		    				e.printStackTrace();
		    			}
		    			back = true;
		    			next = true;
		    			place = place - maxSize; 
		    		}else{
		    			try {
		    				databaseTable = database.readDatabaseRang(databaseName, 0, maxSize);
		    			} catch (Exception e) {
		    				System.out.println("Critical error");
		    				e.printStackTrace();
		    		    }
		    			back = false;
		    			next = true;
		    			place = maxSize - 1; 
		    		}
		    		break;
		    	case("done"):
		    		gate = false;
		    		break;
		    	default:
		    		gate = true;
		    		break;		    
		    }  
		   
		}//end while
		
		
	}
	
	

	@SuppressWarnings("resource")
	private static void delete(){
		String input;			
		
		System.out.println("-------------------------|   Delete a database   |--------------------------");
		System.out.println("----------------------------------------------------------------------------");
		
		System.out.println();
		System.out.println();
		
				
		System.out.println();
		System.out.println();
		System.out.println("What Database whould you like to delete?");
		System.out.println("Please enter the corresponding number");
		ArrayList<String> table = database.getTable();
		
		if( table.size() <= 0){
			System.out.println("No datebases to view");
			return;
		}
		
		if(table.size() != 0){
			for(int i = 0; i < table.size(); i++){
				System.out.printf("     [%d] %s%n", i+1, table.get(i));
			}
		}else{
			System.out.println();
			System.out.println("No databases found.");
			return;
		}	
		
		Scanner s = new Scanner(Location);
		boolean gate = true;
		int choice = 0;
		
		while(gate){
			gate = false;
			s = new Scanner(System.in);
			
			try{
			   choice = s.nextInt();
			}catch(Exception e){
				System.out.println("Please enter the corresponding number");
				gate = true;
			}
		
		
			System.out.println();
			System.out.println();
		
			if(choice > table.size()){
				gate= true;
				System.out.println("That is not a valid number");
			}
			
			if(choice < 1){
				gate= true;
				System.out.println("That is not a valid number");
			}
		}
		
		choice--;
		String databaseName = table.get(choice);
		
		
		
		if(!database.exist(databaseName)){
			
			System.out.println("That database does not exist.");
			
		}else{
			
			System.out.printf("Are you sure you want to delete: \"%s\"?%n", databaseName);
			System.out.println("Yes \"yes\" No \"no\"");
			s = new Scanner(System.in);			
			input = s.nextLine();
			
			switch(input){
			case("y"):
			case("yes"):
				try {
					database.deleteDatabase(databaseName);
					System.out.println("Database deleted!");	
				} catch (Exception e) {
					System.out.println("Database failure");
					e.printStackTrace();
				}	
				break;
			case("n"):
			case("no"):
				System.out.println("Canceled");				
			    break;
			default:
				System.out.println("Input unknown.");
				break;
		   }
			
					
		}
		
		
		
		
	}
	
	private static void create(){
		String input;		
		boolean gate = true;
		String databaseName = null;
		ArrayList<String> firstRow = new ArrayList<String>(0);
		int[] dataType = new int[0];
		String[] names = new String[0];
		int count = 1;
		Scanner s;
		
		
		System.out.println("-------------------------|   Create a database   |--------------------------");
		System.out.println("----------------------------------------------------------------------------");
		
		
		
		
		//Get name and test if it is correct
		
		while(gate){
			System.out.println();
			System.out.println();
			System.out.println("What do you want to name the database?");
			s = new Scanner(System.in);			
			input = s.nextLine();  			
			
			if(!database.exist(input)){
				gate = false;
				databaseName = input;
			}else{
				boolean gate2 = true;
				System.out.println("That database already exists");
				
				while(gate2){
					
					System.out.println("Return (exit) or try again (again)?");
				
					s = new Scanner(System.in);		
					input = s.nextLine(); 					
				
					switch(input){
					case("again"):
						gate2 = false;
						break;
					case("exit"):
						System.out.println("Exiting back to the interface.");
					    return;
					default:
						gate2 = true;
						System.out.println("Input unknown.");
						break;
				   }//close inner switch
		        }//close inner while   
				
			}//end if
	    }//close outer while
		
		System.out.println();
		System.out.println();
		System.out.println("Alright! That name works!");
		System.out.println("Time to set up the columns");		
		gate = true;
		
		//Get column data types
		while(gate){
			System.out.println();
			System.out.printf("What type of data will be in row %d%n", count);
			System.out.println("String \"string\", Numerical \"number\", Date \"date\"");
			System.out.println("When you are done type \"next\" or \"exit\" to quit");
			
			s = new Scanner(System.in);		
			input = s.nextLine();
		
			//String = 1; Double = 2; Date = 3
			switch(input){
			case("string"):
				dataType = Arrays.copyOf(dataType, 1 + dataType.length);
				dataType[count - 1] = 1;
				
				System.out.println();
				System.out.println("What do you want this column to be called?");
				
				s = new Scanner(System.in);	
				input = s.nextLine();
				
				names = Arrays.copyOf(names, 1 + names.length);
				names[count - 1] = input;

				count++;
				
				break;
			case("number"):
				dataType = Arrays.copyOf(dataType, 1 + dataType.length);
				dataType[count - 1] = 2;
				
				System.out.println();
				System.out.println("What do you want this column to be called?");
				
				s = new Scanner(System.in);	
				input = s.nextLine();
				
				names = Arrays.copyOf(names, 1 + names.length);
				names[count - 1] = input;

				count++;
				
				break;
			case("date"):
				dataType = Arrays.copyOf(dataType, 1 + dataType.length);
				dataType[count - 1] = 3;
				
				System.out.println();
				System.out.println("What do you want this column to be called?");
				
				s = new Scanner(System.in);	
				input = s.nextLine();	    
				
				
				names = Arrays.copyOf(names, 1 + names.length);
				names[count - 1] = input;

				count++;

				break;
			case("next"):
				
				if(dataType.length != 0){
					gate = false;
				}else{
					System.out.println("You need to have at least one column to create a database");
				}
				break;				
			case("exit"):
				System.out.println("Exiting back to the interface.");
			    return;
			default:
				System.out.println("Input unknown.");
				break;
		   }
			
		}
		
			
		
		System.out.println();
		System.out.println();
		System.out.println("Now we need to enter the first row of data");	
		System.out.println();
		
		Scanner s1 = new Scanner(databaseName);
		//Fill first row of data, checking the data entered
		for(int i = 0; i < dataType.length; i++){
			
			
			
			switch(dataType[i]){
			case(1):
				System.out.printf("Please enter column %d: (String)%n", i + 1);
			
			    s1 = new Scanner(System.in);	
				input = s1.nextLine();
				firstRow.add(input);
				
			
				break;
			case(2):
				System.out.printf("Please enter column %d: (Numerical)%n", i + 1);
			
				s1 = new Scanner(System.in);
				input = s1.nextLine();	    
				
				try{
   	        		@SuppressWarnings("unused")
					double d = Double.parseDouble(input); 
   	        		firstRow.add(input);   	        		
   	        	 }
   	        	 catch(NumberFormatException n)
   	        	 {
   	        		i--;
   	        		System.out.println("Input was not numerical.");
   	        	 }
				
				break;
			case(3):
				System.out.printf("Please enter column %d: (Date)%n", i + 1);
			
				s1 = new Scanner(System.in);
				input = s1.nextLine();	    
				
				
				try{
   	        		SimpleDateFormat parser = new SimpleDateFormat("MMM d yyyy HH:mm:ss");
   	                @SuppressWarnings("unused")
					Date date = parser.parse(input); 
   	                firstRow.add(input);  
   	        	 }
   	        	 catch(DateTimeParseException | ParseException  e){
   	        		try{
   	   	        		SimpleDateFormat parser = new SimpleDateFormat("MMM d yyyy HH:mm:ss");
   	   	                @SuppressWarnings("unused")
   						Date date = parser.parse(input); 
   	   	                firstRow.add(input);  
   	   	        	 }
   	   	        	 catch(DateTimeParseException | ParseException  f){
   	   	        		i--;
   	   	        		System.out.println("Input was not the correct format (MMM d yyyy) or (MMM d yyyy HH:mm:ss).");
   	   	        	 }
   	        	 }
			
			default:
				System.out.println("Major error, returning to interface.");
				System.out.println("Sorry for the inconvenience.");
				break;
		}
			
		}
		
		//Try to create the database
		try {
						
			database.createDatabase(databaseName, dataType, firstRow, names);
			System.out.println("Database created!");
		} catch (Exception e) {
			System.out.println("Database was not created!");
			e.printStackTrace();
		}
		
		}
		
}

