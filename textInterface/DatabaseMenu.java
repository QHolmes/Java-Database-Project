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

public class DatabaseMenu {

	private static Databases database = null;
	private static int maxSize = 20;
	private static String databaseName = "";
	private static String fileLocation = "";
	private static boolean mainGate = true;
	

	protected static void mainMenu(String databaseNameInput, Databases databaseInput, String filePath){
		
		databaseName = databaseNameInput;
		database = databaseInput;
		fileLocation = filePath;
		
		
		Scanner s;
		mainGate = true;
		while(mainGate){
			System.out.println("-----------------------|      Database Menu        |------------------------");
			printName();
			System.out.println("----------------------------------------------------------------------------");
			System.out.println();
			System.out.println();
			System.out.println("Please type and option below");
			System.out.println();

			System.out.println("View the database               | \"view\"");
			System.out.println("Add a row to database           | \"add\"");
			System.out.println("Remove a row from database      | \"remove\"");
			System.out.println("Delete the database             | \"delete\"");
			System.out.println("Edit database properties        | \"prop\"");
			System.out.println("");
			
			System.out.println("Back                            | \"back\"");
			
			s = new Scanner(System.in);			
			String input = s.nextLine();
		    String[] split = input.split("\\s+");
		    
		    switch(split[0].toLowerCase()){
		    	case("add"):
		    		add();
		    		break;
		    	case("remove"):
		    		remove();
		    		break;
		    	case("view"):
		    		view();
		    		break;
		    	case("delete"):
		    		delete();
		    		break;
		    	case("prop"):
		    	case("properties"):
		    		PropertiesMenu.mainMenu(databaseName, database);
		    		break;
		    	case("exit"):
		    	case("back"):
		    	    mainGate = false;
		    		break;
		    	default:
		    		mainGate = true;
		    		break;		    
		    }
            save();  
		}    
		
	}
	
	private static void add() {
	
	String input;
	String cloumnNames[] = null;
	int dataType[] = null;
	ArrayList<String> firstRow = new ArrayList<String>(0);
	
	System.out.println("------------------------|   Add to a database   |---------------------------");
	System.out.println("----------------------------------------------------------------------------");
	
	
	
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
		e.printStackTrace();
	}
	
	}

	@SuppressWarnings("resource")
	private static void delete(){
		String input;
		Scanner s = new Scanner("");
		
		System.out.println("-------------------------|   Delete a database   |--------------------------");
		System.out.println("----------------------------------------------------------------------------");
		
		System.out.println();
		System.out.println();
		
		
		
			
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
				mainGate = false;
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

	private static void edit(int passedInt) {
		String cloumnNames[] = null;
		int dataType[] = null;
		int size = 0;		
		int choice = 0;
		boolean gate = true;
		boolean change = false;
		boolean inputPassed = false;
		@SuppressWarnings("resource")
		Scanner s = new Scanner("");
		ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>(0);
		ArrayList<String> row = new ArrayList<String> (0);
		ArrayList<String> newRow = new ArrayList<String> (0);
		String input;
		
		System.out.println("---------------------------|   Edit a row   |-------------------------------");
		System.out.println("----------------------------------------------------------------------------");
		
		try {
				dataType = database.getDatabaseColumn(databaseName);
				cloumnNames = database.getColumnNames(databaseName);
				size = database.getSize(databaseName);
				
			} catch (Exception e1) {
				
			}
		
		if(passedInt >= 0 && passedInt < size){
			choice = passedInt;
			inputPassed = true;
			try {
				rows = database.readDatabaseRang(databaseName, choice, 0);
			} catch (Exception e) {
				System.out.println("----error reading database!----");
				e.printStackTrace();
			}
			row = rows.get(0);
		}
		
		if(!inputPassed){

			System.out.println("Which row would you like to edit?");
			System.out.println();
			
			gate = true;
			choice = 0;
			
			while(gate){
				gate = false;
				s = new Scanner(System.in);
				
				try{
				   choice = s.nextInt();
				}catch(Exception e){
					System.out.println("Please enter the a valid row number");
					System.out.println("1 - " + size);
					gate = true;
				}	
			
				if(choice > size){
					System.out.println("Please enter the a valid row number");
					System.out.println("1 - " + size);
					gate = true;
				}else{		
					if(choice < 1){
						System.out.println("Please enter the a valid row number");
						System.out.println("1 - " + size);
						gate = true;
					}else{
						try{
							choice--;
							rows = database.readDatabaseRang(databaseName, choice, 0);
							row = rows.get(0);
						}catch(Exception e){
							gate= true;
							System.out.println("----error reading database!----");
							e.printStackTrace();
						}
				    }
				}
				
			}
		}
		
		System.out.println();
		System.out.printf("Is this the correct row? (%d)%n", choice + 1);
		System.out.println("-----------------------------------------------------------");
		for(int i = 0; i < cloumnNames.length; i++){
			System.out.printf("%10.10s ", cloumnNames[i]);
		}
		
		System.out.println();
		
		for(int i = 0; i < row.size(); i++){
			System.out.printf("%10.10s ", row.get(i));
		}
		
		System.out.println();
		
		
		System.out.println("Yes \"yes\" No \"no\"");
		s = new Scanner(System.in);			
		input = s.nextLine();
		
		gate = false;
		
		switch(input){
		case("y"):
		case("yes"):
			gate = true;	
			break;
		case("n"):
		case("no"):
			System.out.println("Canceled");				
		    break;
		default:
			System.out.println("Input unknown.");
			break;
	   }
		
		if(gate){
			for(int i =0; i < cloumnNames.length; i++){
				
				
				
				gate = true;
				change = false;
				while(gate){
				
					System.out.printf("Do you want to change (yes)\"%s\" or leave it the same (no)?%n", cloumnNames[i]);
					
					s = new Scanner(System.in);			
					input = s.nextLine();
					
					switch(input){
					case("yes"):
					case("y"):
						gate = false;
					    change = true;
						break;
					case("no"):
					case("n"):
						newRow.add(row.get(i));
					    gate = false;	
					    break;
					default:
						System.out.println("Input unknown.");
						break;
				   }
					
					if(change){
						switch(dataType[i]){
						case(1):
							System.out.printf("Please enter column %s: (String)%n", cloumnNames[i]);
						
						    s = new Scanner(System.in);	
							input = s.nextLine();
							newRow.add(input);
							
						
							break;
						case(2):
							System.out.printf("Please enter column %s: (Numerical)%n", cloumnNames[i]);
						
							s = new Scanner(System.in);
							input = s.nextLine();	    
							
							try{
			   	        		@SuppressWarnings("unused")
								double d = Double.parseDouble(input); 
			   	        		newRow.add(input);   	        		
			   	        	 }
			   	        	 catch(NumberFormatException n)
			   	        	 {
			   	        		i--;
			   	        		System.out.println("Input was not numerical.");
			   	        	 }
							
							break;
						case(3):
							System.out.printf("Please enter column %s: (Date)%n", cloumnNames[i]);
						
							s = new Scanner(System.in);
							input = s.nextLine();	    
							
							
							try{
			   	        		SimpleDateFormat parser = new SimpleDateFormat("MMM d yyyy HH:mm:ss");
			   	                @SuppressWarnings("unused")
								Date date = parser.parse(input); 
			   	             newRow.add(input);  
			   	        	 }
			   	        	 catch(DateTimeParseException | ParseException  e){
			   	        		try{
			   	   	        		SimpleDateFormat parser = new SimpleDateFormat("MMM d yyyy HH:mm:ss");
			   	   	                @SuppressWarnings("unused")
			   						Date date = parser.parse(input); 
			   	   	                newRow.add(input);  
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
				}//end gate
			
			}//End for loop
			
			System.out.println();
			System.out.printf("Is this the correct row? (%d)%n", choice + 1);
			System.out.println("-----------------------------------------------------------");
			for(int i = 0; i < cloumnNames.length; i++){
				System.out.printf("%10.10s ", cloumnNames[i]);
			}
			
			System.out.println();
			
			System.out.println("Old Row");
			
			for(int i = 0; i < row.size(); i++){
				System.out.printf("%10.10s ", row.get(i));
			}
			System.out.println();
			System.out.println("New row");
			
			for(int i = 0; i < row.size(); i++){
				System.out.printf("%10.10s ", newRow.get(i));
			}			
			
			System.out.println();
			
			
			System.out.println("Yes \"yes\" No \"no\"");
			s = new Scanner(System.in);			
			input = s.nextLine();
			
			switch(input){
			case("y"):
			case("yes"):
				
				try {
					database.changeRow(databaseName, newRow, choice);
					System.out.println("Row changed!");
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
			
			try {
				database.changeRow(databaseName, newRow, choice);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	private static void printName(){
		
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

	private static void remove() {
		
		String cloumnNames[] = null;
		int dataType[] = null;
		int size = 0;
		boolean gate = false;
		ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>(0);
		ArrayList<String> row = new ArrayList<String> (0);
		String input;
		Scanner s = new Scanner("");
		
		System.out.println("----------------------|   Remove from a database   |------------------------");
		System.out.println("----------------------------------------------------------------------------");
		
		System.out.println();
		System.out.println();
		
		
		try {
			dataType = database.getDatabaseColumn(databaseName);
			cloumnNames = database.getColumnNames(databaseName);
			size = database.getSize(databaseName);
			
		} catch (Exception e1) {
			
		}
		
		System.out.println("Which row would you like to remove?");
		System.out.println();
		
		gate = true;
		int choice = 0;
		
		while(gate){
			gate = false;
			s = new Scanner(System.in);
			
			try{
			   choice = s.nextInt();
			}catch(Exception e){
				System.out.println("Please enter the a valid row number");
				System.out.println("1 - " + size);
				gate = true;
			}	
		
			if(choice > size){
				System.out.println("Please enter the a valid row number");
				System.out.println("1 - " + size);
				gate = true;
			}else{		
				if(choice < 1){
					System.out.println("Please enter the a valid row number");
					System.out.println("1 - " + size);
					gate = true;
				}else{
					try{
						choice--;
						rows = database.readDatabaseRang(databaseName, choice, 0);
						row = rows.get(0);
					}catch(Exception e){
						gate= true;
						System.out.println("----error reading database!----");
						e.printStackTrace();
					}
			    }
			}
			
		}
		
		System.out.println();
		System.out.printf("Is this the correct row? (%d)%n", choice + 1);
		System.out.println("-----------------------------------------------------------");
		for(int i = 0; i < cloumnNames.length; i++){
			System.out.printf("%10.10s ", cloumnNames[i]);
		}
		
		System.out.println();
		
		for(int i = 0; i < row.size(); i++){
			System.out.printf("%10.10s ", row.get(i));
		}
		
		System.out.println();
		
		
		System.out.println("Yes \"yes\" No \"no\"");
		s = new Scanner(System.in);			
		input = s.nextLine();
		
		switch(input){
		case("y"):
		case("yes"):
			try {
				database.deleteRow(databaseName, choice);;
				System.out.println("Row removed!");	
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
	
	private static void view(){
		
		ArrayList<ArrayList<String>> databaseTable = null;
		Scanner s = new Scanner("");
		String[] column = null;
		int page = 1;
		int totalPages = 1;
		int size = 0;
		int start = 0;
		int end = 0;
		int goToPage = 0;
		int passedInt = 0;
		boolean next = false;
		boolean back = false;
		boolean goTo = false;
		boolean inputPassed = false;
		boolean gate = false;
		boolean gate2 = true;
		String input;
		
		
		
		System.out.println("--------------------------|   View a database   |---------------------------");
		System.out.println("----------------------------------------------------------------------------");
		
		System.out.println();
		System.out.println();
		
		//-------------------------------------------------------------------
		//------------      Get first table to display     ------------------
		//------------        and calculate variables      ------------------
		//-------------------------------------------------------------------
		
		
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
			goTo = true;
			end = maxSize;
			start = 0;
		}else{
			try {
				databaseTable = database.readDatabaseRang(databaseName, 0, size-1);
			} catch (Exception e) {
				System.out.println("Critical error");
				e.printStackTrace();
		    }
			
			end = size;
			start = 0;
		}
		
		totalPages = size/maxSize;
		if(size%maxSize > 0){
			totalPages++;
		}
		
		//-------------------------------------------------------------------
		//----------------     Display table and options  -------------------
		//-------------------------------------------------------------------
		
		
		gate = true;
		while(gate){
			printName();
			System.out.printf("----------------Page: %d of %d%n", page, totalPages);			
			
			//Print column titles
			System.out.printf("%9s", "");
			for(int i = 0; i < column.length; i++){
				System.out.printf("%10.10s ", column[i]);
			}
				
			System.out.println();
				
			for(int row = 0; row < databaseTable.size(); row++){//for each row
			   System.out.printf("[%6d] ", (start + 1) + row);
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
		   
		   if(goTo){
			   System.out.printf("Goto page \"Goto\"    ");			   
		   }else{
			   System.out.printf("                      "); 
		   }
		      
		      System.out.printf("edit a row \"edit\"    ");	
		      System.out.println("Done \"done\"");
		      
		      s = new Scanner(System.in);
			  input = s.nextLine();	
			  String[] split = input.split("\\s+");
			    
			  switch(split[0].toLowerCase()){
		    	case("next"):		    	
		    		if(!next){
		    			System.out.println("Sorry that is not an option");
		    			break;
		    		}
		    		page++;
		    		
		    		
		    		if((maxSize + end) <= size){
		    			try {
		    				databaseTable = database.readDatabaseRang(databaseName, end, maxSize);
		    			} catch (Exception e) {
		    				System.out.println("Critical error");
		    				e.printStackTrace();
		    			}
		    			next = true;
		    			back = true;
		    			start = end;
		    			end = end + maxSize; 
		    		}else{
		    			try {
		    				databaseTable = database.readDatabaseRang(databaseName, end, (size-1) - end);
		    			} catch (Exception e) {
		    				System.out.println("Critical error");
		    				e.printStackTrace();
		    		    }
		    			next = false;	
		    			back = true;
		    			start = end;
		    			end = size-1; 
		    		}
		    		break;
		    	case("back"):		    	
		    		if(!back){
		    			System.out.println("Sorry that is not an option");
		    			break;
		    		}
		    	    page--;
		    	    
		    	    if(end >= (size)){
	             	//end = end - (size % maxSize) -1;
	             	end = start;
	             	start = start - maxSize;
	             	
		    	    }else{
		    	    	if(start >= 0){
			    			try {
			    				databaseTable = database.readDatabaseRang(databaseName, start - maxSize, maxSize);
			    			} catch (Exception e) {
			    				System.out.println("Critical error");
			    				e.printStackTrace();
			    			}
			    			back = true;
			    			next = true;
			    			start = start - maxSize;
			    			end = start; 
			    		}else{
			    			try {
			    				databaseTable = database.readDatabaseRang(databaseName, 0, maxSize);
			    			} catch (Exception e) {
			    				System.out.println("Critical error");
			    				e.printStackTrace();
			    		    }
			    			back = false;
			    			next = true;
			    			end = maxSize; 
			    			start = 0;
			    		}
		    	    }
		    	    
		    		
		    		break;		    		
		    	case("goto"):
		    		
		    		//Find out if page number was passed
		    		try{
		    			goToPage = Integer.parseInt(split[1]);
		    			
		    			if(goToPage > 0 && goToPage <= totalPages){
		    				inputPassed = true;
		    			} 			
		    			
		    		}catch(Exception e){
		    			inputPassed = false;
		    		}
		    		
		    		//Get page number if number not passsed
		    		if(!inputPassed){
		    			System.out.println("Which page do you want to goto?");
		    			System.out.println("or 0 to return");
		    			  			
		    			 
		    			 while(gate2){
		    					gate2 = false;
		    					
		    					
		    					try{
		    					   s = new Scanner(System.in);
		    					   goToPage = s.nextInt();
		    					   
		    					   if(goToPage == 0){
		    						   System.out.println("Canceled");
		    						   break;
		    					   }
		    					   
		    					}catch(Exception e){
		    						System.out.println("Please enter the a valid row number");
		    						System.out.println("1 - " + totalPages);
		    						gate2 = true;
		    					}	
		    				
		    					if(goToPage > totalPages){
		    						System.out.println("Please enter the a valid row number");
		    						System.out.println("1 - " + totalPages);
		    						gate2 = true;
		    					}else{		
		    						if(goToPage < 0){
		    							System.out.println("Please enter the a valid row number");
		    							System.out.println("1 - " + totalPages);
		    							gate2 = true;
		    						}
		    					}
		    					
		    				}
		    		}
		    		
		    		//Go to page
		    		
		    		page = goToPage;
		    		
		    		if(page == totalPages){
		    			next = false;
		    			if(totalPages > 1)
		    				back = true;
		    			
		    			if(size%maxSize > 0){		    				
		    				start = (page*maxSize) - maxSize;
		    				end = start + (size%maxSize);
		    				
//		    				System.out.printf("Start %d end %d size %d page %d max Size%d%n", start, end, size, page, maxSize);
		    				
		    				try {
								databaseTable = database.readDatabaseRang(databaseName, start, (size%maxSize) - 1);
							} catch (Exception e) {
								System.out.println("Critical error");
								e.printStackTrace();
							}
		    			}
		    		}else{
		    				if(page == 1){
		    					start = 0;
		    					if(totalPages > 1){
		    						end = maxSize;
		    					}else{
		    						end = size;
		    					}
		    					
		    					System.out.printf("Start %d end %d size %d page %d max Size%d%n", start, end, size, page, maxSize);
		    					
		    					try {
									databaseTable = database.readDatabaseRang(databaseName, start, end - 1);
								} catch (Exception e) {
									System.out.println("Critical error");
									e.printStackTrace();
								}	    					
		    				}else{
		    					
		    					start = (page * maxSize) - maxSize;
		    					end = start + maxSize;
		    					
		    					try {
									databaseTable = database.readDatabaseRang(databaseName, start, maxSize);
								} catch (Exception e) {
									System.out.println("Critical error");
									e.printStackTrace();
								}
		    					
		    				}
		    		}
		    		
		    	
		    		break;
		    	case("edit"):
		    		
		    		//Find out if page number was passed
		    		try{
		    			passedInt = Integer.parseInt(split[1]);
		    			
		    			if(passedInt > 0 && passedInt <= (totalPages * maxSize) + (size%maxSize)){
		    				inputPassed = true;
		    				passedInt--;
		    			} else{
		    				passedInt = -1;
		    			}
		    			
		    		}catch(Exception e){
		    			inputPassed = false;
		    			passedInt = -1;
		    			
		    		}
		    		
		    		edit(passedInt);
		    		break;
		    	case("done"):
		    		gate = false;
		    		break;
		    	default:
		    		System.out.println("Input unknown!");
		    		gate = true;
		    		break;		    
		    }  
		   
		}//end while
		
		
	}

	private static void save() {
		
		try {
	         FileOutputStream fileOut =
	         new FileOutputStream(fileLocation);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(database);
	         out.close();
	         fileOut.close();
	      }catch(Exception i) {
	         i.printStackTrace();
	      }
		
	}
	
}
