package textInterface;

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
import Database.readDatabase;

public class Menu {
	private static String Location = "/c:/test/";
	private static Databases database = null;
	private static int maxSize = 20;
	private static String filePath = String.format("..\\"); 
	

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
			System.out.println("Create a database             | \"create\"");
			System.out.println("Enter a database              | \"enter\"");
			System.out.println("Delete a database             | \"delete\"");
			System.out.println("Import a database             | \"import\"");
			System.out.println("");
			
			System.out.println("Exit                          | \"exit\"");
			
			s = new Scanner(System.in);			
			String input = s.nextLine();
		    String[] split = input.split("\\s+");
		    
		    switch(split[0].toLowerCase()){
		    	case("create"):
		    		create();
		    		break;
		    	case("enter"):
		    		enter();
		    		break;
		    	case("delete"):
		    		delete();
		    	case("import"):
		    		importDatabase();
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
	
	private static void importDatabase() {
		
		boolean gate = true;
		boolean hasHeaders = false;
		int primeColumn = 0;
		int[] dataType;
		String newDatabaseLocation;
		String input;
		String databaseName = null;
		String[] names;
		Scanner s = new Scanner(Location);
		ArrayList<ArrayList<String>> table = null;
		
		System.out.println("-------------------------|   Import a database   |--------------------------");
		System.out.println("----------------------------------------------------------------------------");
		
		System.out.println();
		System.out.println();
		System.out.println("Welcome to file import!");	
		
		//-------------------------------------------------------------------
		//----------------      Get file location      ----------------------
		//-------------------------------------------------------------------
		
		
		
		System.out.println("Where is the file being kept?");
		System.out.println("Note file must be .csv");
		while(gate){
			System.out.println("Please enter the file path or 'exit' to leave.");
			gate = false;
			s = new Scanner(System.in);	
			input = s.nextLine();  
			
			if(input.toLowerCase().compareTo("exit") == 0){
				System.out.println("Exiting...");
				return;
			}
			
			File file = new File(input);	
			
			if(file.exists()){
				System.out.println("Okay! We found the file!");
				System.out.println("Attempting to read...");
				
				try{
					table = readDatabase.readCSV(input);
					newDatabaseLocation = input;
					
					System.out.println("Read successfull!");
					System.out.printf("%d columns found with %d rows%n", table.size(), table.get(0).size());
					
				}catch(Exception e){
					System.out.println("File could not be read");
					gate = true;
				}
				
			}else{
				System.out.printf("File not found! (%s)%n", input);
				gate = true;
			}
		}
				
		
		//-------------------------------------------------------------------
		//----------------      Get Column Headers     ----------------------
		//-------------------------------------------------------------------
		
		System.out.println("Does this CSV have headers?");
		System.out.println("Yes \"yes\" No \"no\"");
		s = new Scanner(System.in);			
		input = s.nextLine();
		
		switch(input){
		case("y"):
		case("yes"):
			hasHeaders =true;	
			break;
		case("n"):
		case("no"):
			hasHeaders = false;			
		    break;
		default:
			System.out.println("Input unknown.");
			break;
	   }
		
		names = new String[table.get(0).size()];
		
		if(!hasHeaders){
			System.out.println("No problem, we can create them.");
			
			
			for(int i = 0; i < table.get(0).size(); i++){
				
				System.out.printf("What do you want column %d to be called?", i + 1);
				
				s = new Scanner(System.in);	
				input = s.nextLine();
				
				names[i] = input;
			}			
		}else{
			
			
			for(int i = 0; i < table.get(0).size(); i++){
				names[i] = table.get(0).get(i);
			}
			
			table.remove(0);
			
		}
		
		//-------------------------------------------------------------------
		//------------------      Get Column Types     ----------------------
		//-------------------------------------------------------------------
		
		System.out.println("Alright! Now we need to get column types.");
		
		dataType = new int[table.get(0).size()];
		
		for(int col = 0; col < table.get(0).size(); col++){
			gate = true;
			while(gate){
				gate = false;
				System.out.printf("What type of data is in column: %s%n", names[col]);
				System.out.println("String \"string\", Numerical \"number\", Date \"date\"");
				
				s = new Scanner(System.in);		
				input = s.nextLine();
			
				//String = 1; Double = 2; Date = 3
				switch(input){
					case("s"):
					case("string"):						
						dataType[col] = 1;						
						break;
					case("n"):
					case("number"):
						try{
							for(int row = 0; row < table.size(); row++){
								Double.parseDouble(table.get(row).get(col));
							}							
							dataType[col] = 2;
						}catch(Exception e){
							System.out.println("There was an error parsing the data.");
							System.out.println("'number' is not a vaild data type for this column");
							gate = true;
						}					
						break;
					case("d"):
					case("date"):						
						try{
    	   	        		SimpleDateFormat parser = new SimpleDateFormat("MMM d yyyy HH:mm:ss");
    	   	                
    	   	        		for(int row = 0; row < table.size(); row++){
    	   	        			@SuppressWarnings("unused")
								Date date = parser.parse(table.get(row).get(col));    	   	        			
    	   	        		}
							 dataType[col] = 3;
    	   	        	 }catch(Exception  e){
 							System.out.println("There was an error parsing the data.");
 							System.out.println("'data' is not a vaild data type for this column");
 							System.out.println("Dates are only accepted in 'MMM d yyyy HH:mm:ss' format");
 							gate = true;
    	   	        	 }
						
						
			
						break;
					default:
						System.out.println("Input unknown");
						gate = true;
						break;
			    }
		    }  
		}
		
		//-------------------------------------------------------------------
		//-----------------      Get Primary Column     ---------------------
		//-------------------------------------------------------------------
		
		System.out.println("Awesome! What column will be the primary column?");
		
		System.out.println("Which column header do you want to change");
		System.out.println("or 0 to return");
			
	    //Print column titles
		 for(int i = 0; i < names.length; i++){
		    System.out.printf("[%d]%s %n",i+1, names[i]);
		 }
		 
		 while(gate){
				gate = false;
				
				
				try{
				   s = new Scanner(System.in);
				   primeColumn = s.nextInt();
				   primeColumn--;
				}catch(Exception e){
					System.out.println("Please enter the a valid row number");
					System.out.println("1 - " + (names.length - 1));
					gate = true;
				}	
			
				if(primeColumn > (names.length - 1)){
					System.out.println("Please enter the a valid row number");
					System.out.println("1 - " + (names.length - 1));
					gate = true;
				}else{		
					if(primeColumn < 0){
						System.out.println("Please enter the a valid row number");
						System.out.println("1 - " + (names.length - 1));
						gate = true;
					}else{												
							//Number is valid
				    }
				}
				
			}
		 
		//-------------------------------------------------------------------
		//----------------      Get Database Name      ----------------------
		//-------------------------------------------------------------------
		
		 System.out.println("Wonderful! Finally what do you want to call this database?");	
		 
		 s = new Scanner(System.in);		
		 databaseName = s.nextLine();
			
		 
		//-------------------------------------------------------------------
		//----------------     Confirm and submit      ----------------------
		//-------------------------------------------------------------------
			
			
		 System.out.println("Fanstic! Does everything look correct?");		
		 System.out.println();
		 System.out.println();
		 
		 
		 printName(databaseName);
		 
		 for(int i = 0; i < names.length; i++){
				System.out.printf("%10.10s ", names[i]);
			}		 
		 System.out.println();
		 
			
			
			gate = true;
			while(gate){
				gate = false;
				System.out.println("Yes \"yes\" No \"no\"");
				s = new Scanner(System.in);			
				input = s.nextLine();
				
				switch(input){
				case("y"):
				case("yes"):
					try {
						database.importDatabase(databaseName, dataType, table, names);	
						database.setPrime(databaseName, primeColumn);
						
						System.out.printf("%s was imported successfully!%n%n", databaseName);
					} catch (Exception e) {
						System.out.printf("Database Error!%n"
								        + "%s was not imported successfully :C%n%n", databaseName);
					}	
					break;
				case("n"):
				case("no"):
					System.out.println("Canceled");				
				    break;
				default:
					System.out.println("Input unknown.");
					gate = true;
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
		System.out.printf("What type of data will be in column %d%n", count);
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
	
	//Try to create the database
	try {
					
		database.createDatabase(databaseName, dataType,names);
		System.out.println("Database created!");
	} catch (Exception e) {
		System.out.println("Database was not created!");
		e.printStackTrace();
	}
	
	}

	private static void enter() {
		String databaseName;
		int size = 0;
		ArrayList<ArrayList<String>> databaseTable = null;
		String[] column = null;
		int page = 1;
		boolean next = false;
		boolean back = false;
		String input;
		
		
		System.out.println("-------------------------|   Choose a database   |--------------------------");
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
		databaseName = table.get(choice);
		
		DatabaseMenu.mainMenu(databaseName, database, filePath);
		
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
		database = new Databases(Location);
		
	}

	private static void save() {
		
		try {
	         FileOutputStream fileOut =
	         new FileOutputStream(filePath);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(database);
	         out.close();
	         fileOut.close();
	      }catch(Exception i) {
	         i.printStackTrace();
	      }
		
	}

	private static void load() {
		File f1 = new File("Settings.java");		
		filePath = f1.getAbsolutePath();
		System.out.println(filePath);
		
		
		try {
	         FileInputStream fileIn = new FileInputStream(filePath);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         database = (Databases) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(ClassNotFoundException c) {
	         System.out.println("Old data not found");
	         settings();
	      }catch(Exception i) {
	    	 System.out.println("Old data not found");
	    	 settings();
	      }
		
		System.out.println(database.getLocation());
		
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
			
			
			gate = true;
			while(gate){
				gate = false;
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
					gate = true;
					break;
			   }
			}
			
					
		}
		
		
		
		
	}
	
	private static void printName(String databaseName){
		
		//String is 76 chars long
		int stringLength = 76;
		// 3 spaces on each side of the word and a bar on each side = -8
		stringLength = stringLength - 8 - databaseName.length();		
		for(int i = 0; i < (stringLength/2); i++){
			System.out.print("-");
		}		
		
	
		System.out.printf("|   %s   |", databaseName);
		
		for(int i = 0; i < (stringLength - (stringLength/2)); i++){
			System.out.print("-");
		}
		
		System.out.println();
		
	}
		
}

