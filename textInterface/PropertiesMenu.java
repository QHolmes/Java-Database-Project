package textInterface;

import java.util.ArrayList;
import java.util.Scanner;

import Database.Databases;

public class PropertiesMenu {

	private static Databases database = null;
	private static int maxSize = 20;
	private static String databaseName = "";
	public static boolean mainGate = true;
	

	protected static void mainMenu(String databaseNameInput, Databases databaseInput){
		
		databaseName = databaseNameInput;
		database = databaseInput;
		
		
		Scanner s;
		mainGate = true;
		while(mainGate){
			System.out.println("-----------------------|      Property Menu        |------------------------");
			printName();
			System.out.println("----------------------------------------------------------------------------");
			System.out.println();
			System.out.println();
			System.out.println("Please type and option below");
			System.out.println();
			System.out.println("Change primary column           | \"prime\"");
			System.out.println("Change column header            | \"change\"");
			System.out.println("Add a column                    | \"add\"");
			System.out.println("Remove a column                 | \"remove\"");
			System.out.println("Move a column                   | \"move\"");
			System.out.println("");
			
			System.out.println("Back                            | \"back\"");
			
			s = new Scanner(System.in);			
			String input = s.nextLine();
		    String[] split = input.split("\\s+");
		    
		    switch(split[0].toLowerCase()){
		    	case("prime"):
		    		setPrime();
		    		break;
		    	case("change"):
		    		changeHeader();
		    		break;
		    	case("add"):
		    		addColumn();
		    		break;
		    	case("remove"):
		    		removeColumn();
		    		break;
		    	case("move"):
		    		move();
		    		break;
		    	case("exit"):
		    	case("back"):
		    		mainGate = false;
		    		break;
		    	default:
		    		mainGate = true;
		    		break;		    
		    }
		    
		}    
		
	}
	
	private static void move() {
		int size = 0;
		Scanner s = new Scanner("");
		String[] column = null;
		String input;
		int choice = 0;
		int move = 0;
		int place = 0;
		boolean gate = true;
		
		
		System.out.println("-----------------------------|   Move Column |------------------------------");
		System.out.println("----------------------------------------------------------------------------");
		
		System.out.println();
		System.out.println();
		
		//-------------------------------------------------------------------
		//----------------      Choose what column     ----------------------
		//-------------------------------------------------------------------
		
		
		try {			
			column = database.getColumnNames(databaseName);
			size = column.length;
		} catch (Exception e) {
		}
		
		System.out.println("Which column do you want to move.");
		System.out.println("or 0 to return");
			
	    //Print column titles
		 for(int i = 0; i < column.length; i++){
		    System.out.printf("[%d]%s %n",i+1, column[i]);
		 }
		 
		 while(gate){
				gate = false;
				s = new Scanner(System.in);
				
				try{
				   choice = s.nextInt();
				   if(choice == 0){
					   break;
				   }
				   choice--;
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
					if(choice < 0){
						System.out.println("Please enter the a valid row number");
						System.out.println("1 - " + size);
						gate = true;
					}else{
						
				    }
				}				
			}
		 
		 move = choice;
		 
		//-------------------------------------------------------------------
		//----------------      Choose what column     ----------------------
		//-------------------------------------------------------------------
		 
		 System.out.println("Where do you want to move the column too?");
		 System.out.println("or 0 to return");
		 
			
		 
		    //Print column titles
			 for(int i = 0; i < column.length; i++){
			    System.out.printf("[%d]%s %n",i+1, column[i]);
			 }
		 
		
			 
		 gate = true;
		 while(gate){
				gate = false;
				s = new Scanner(System.in);
				try{
				   choice = s.nextInt();
				   if(choice == 0){
					   System.out.println("Cancelling");
					   return;
				   }
				   choice--;
				}catch(Exception e){
					System.out.println("Please enter the a valid row number");
					System.out.println("1 - " + (size));
					gate = true;
				}	
			
				if(choice > size){
					System.out.println("Please enter the a valid row number");
					System.out.println("1 - " + (size));
					gate = true;
				}else{		
					if(choice < 0){
						System.out.println("Please enter the a valid row number");
						System.out.println("1 - " + (size));
						gate = true;
					}else{
						try{			
							place = choice;
							
							
						}catch(Exception e){
							gate= true;
							System.out.println("----error reading database!----");
							e.printStackTrace();
						}
				    }
				}
				
			}
		 
		 try {
			database.moveColumn(databaseName, choice, move);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

	private static void removeColumn() {
		int size = 0;
		Scanner s = new Scanner("");
		String[] column = null;
		String input;
		int choice = 0;
		boolean gate = true;
		
		
		System.out.println("------------------------|   Change Column Header  |-------------------------");
		System.out.println("----------------------------------------------------------------------------");
		
		System.out.println();
		System.out.println();
		
		//-------------------------------------------------------------------
		//------------      Get first table to display     ------------------
		//-------------------------------------------------------------------
		
		
		try {			
			column = database.getColumnNames(databaseName);
			size = column.length;
		} catch (Exception e) {
		}
		
		System.out.println("Which column do you want to delete");
		System.out.println("or 0 to return");
			
	    //Print column titles
		 for(int i = 0; i < column.length; i++){
		    System.out.printf("[%d]%s %n",i+1, column[i]);
		 }
		 
		 while(gate){
				gate = false;
				s = new Scanner(System.in);
				
				try{
				   choice = s.nextInt();
				   if(choice == 0){
					   break;
				   }
				   choice--;
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
					if(choice < 0){
						System.out.println("Please enter the a valid row number");
						System.out.println("1 - " + size);
						gate = true;
					}else{
						try{
							
							System.out.println("Are you sure?");
							System.out.println("Yes \"yes\" or No \"no\"");
							
							gate = true;
							while(gate){
								gate = false;
								s = new Scanner(System.in);			
								input = s.nextLine();
								String[] split = input.split("\\s+");
								
								switch(split[0].toLowerCase()){
						    		case("y"):
						    		case("yes"):
						    			
						    			System.out.println("Removing index:" + choice);
						    			
						    			database.deleteColumn(databaseName, choice);
						    			System.out.println("Column removed");
						    			break;
						    		case("n"):
						    		case("no"):
						    			
						    			System.out.println("Canceled");
						    			break;
						    		default:
						    			gate = true;
						    			break;
								}
							}
							
						}catch(Exception e){
							gate= true;
							System.out.println("----error reading database!----");
							e.printStackTrace();
						}
				    }
				}
				
			}
		
	}
	
	private static void addColumn() {
		int size = 0;
		Scanner s = new Scanner("");
		String[] column = null;
		String input = "";
		String name = "";
		int choice = 0;
		int place = 0;
		boolean gate = true;
		boolean wantTo = true; //Escape gate
		
		
		System.out.println("----------------------------|   Add A Column   |----------------------------");
		printName();
		System.out.println("----------------------------------------------------------------------------");
		
		System.out.println();
		System.out.println();
		
		//-------------------------------------------------------------------
		//-------      Display current columns and ask to confirm     -------
		//-------------------------------------------------------------------
		
		
		try {			
			column = database.getColumnNames(databaseName);
			size = column.length;
		} catch (Exception e) {
		}
		
		System.out.println("Please confirm you want to add a column");
		System.out.println("Yes \"yes\" No \"No\" ");
			
	    //Print column titles
		 for(int i = 0; i < column.length; i++){
		    System.out.printf("[%d]%s %n",i+1, column[i]);
		 }
		 
		 while(gate){
				gate = false;
				s = new Scanner(System.in);			
				input = s.nextLine();
				String[] split = input.split("\\s+");
				
				switch(split[0].toLowerCase()){
		    		case("y"):
		    		case("yes"):
		    			System.out.println("Okay! Let's add a column!");
		    			break;
		    		case("n"):
		    		case("no"):
		    		    wantTo = false;
		    			break;
		    		default:
		    			gate = true;
		    			break;			
			}
		}
		 
		 if(!wantTo){ 			
 			 System.out.println("Canceled");
			 return;
		 }
		
		//-------------------------------------------------------------------
		//-------------------      Get new column name    -------------------
		//-------------------------------------------------------------------
		 gate = true;
		 while(gate){
			 gate = false;
			 
			 System.out.println("What do you want the new header to be called?");
				s = new Scanner(System.in);			
				input = s.nextLine(); 
				name = input;
				
		 }
		//-------------------------------------------------------------------
		//---------------------    Get column location   --------------------
		//-------------------------------------------------------------------
		 
		 
		 System.out.println("Where do you want to insert the new column?");
		 System.out.println("or 0 to return");
		 
			
		 
		    //Print column titles
			 for(int i = 0; i < column.length; i++){
			    System.out.printf("[%d]%s %n",i+1, column[i]);
			 }
			 
			 System.out.printf("[%d]%n", column.length + 1);
		 
		
			 
		 gate = true;
		 while(gate){
				gate = false;
				s = new Scanner(System.in);
				try{
				   choice = s.nextInt();
				   if(choice == 0){
					   System.out.println("Cancelling");
					   return;
				   }
				   choice--;
				}catch(Exception e){
					System.out.println("Please enter the a valid row number");
					System.out.println("1 - " + (size + 1));
					gate = true;
				}	
			
				if(choice > size + 1){
					System.out.println("Please enter the a valid row number");
					System.out.println("1 - " + (size + 1));
					gate = true;
				}else{		
					if(choice < 0){
						System.out.println("Please enter the a valid row number");
						System.out.println("1 - " + (size + 1));
						gate = true;
					}else{
						try{			
							place = choice;
							
							
						}catch(Exception e){
							gate= true;
							System.out.println("----error reading database!----");
							e.printStackTrace();
						}
				    }
				}
				
			}
		 
		 System.out.println("What data type is this column?");
		 System.out.println("String \"string\", Numerical \"number\", Date \"date\"");
		 
		 gate = true;
			while(gate){
				gate = false;
				s = new Scanner(System.in);			
				input = s.nextLine();
				String[] split = input.split("\\s+");
				
				switch(split[0].toLowerCase()){
		    		case("string"):
		    			choice = 1;
		    			break;
		    		case("number"):
		    			choice = 2;
		    			break;
		    		case("date"):
		    			choice = 3;
		    			break;
		    		case("exit"):
		    		case("back"):
		    			return;
		    		default:
		    			System.out.println("Please enter a data type");
		    			gate = true;
		    			break;
				}
			}
			
			
			System.out.println("Are you sure you want to add this column?");
			System.out.printf("Name: %s%n", name);
			System.out.printf("Data Type: %s%n", input);
			System.out.println("Yes \"yes\" or No \"no\"");
			
			gate = true;
			while(gate){
				gate = false;
				s = new Scanner(System.in);			
				input = s.nextLine();
				String[] split = input.split("\\s+");
				
				switch(split[0].toLowerCase()){
		    		case("y"):
		    		case("yes"):
					try {
						database.addColumn(databaseName, choice, place, name, "");
						System.out.println("Column added");
					} catch (Exception e) {
						System.out.println("Database error");
						e.printStackTrace();
					}
		    			break;
		    		case("n"):
		    		case("no"):
		    			System.out.println("Cancelling");
		    			break;
		    		default:
		    			gate = true;
		    			break;
				}
			}
		 
			
		 
		 
		 
		 
	}

	private static void changeHeader() {
		int size = 0;
		Scanner s = new Scanner("");
		String[] column = null;
		String input;
		int choice = 0;
		boolean gate = true;
		
		
		System.out.println("------------------------|   Change Column Header  |-------------------------");
		printName();
		System.out.println("----------------------------------------------------------------------------");
		
		System.out.println();
		System.out.println();
		
		//-------------------------------------------------------------------
		//---------      Get columns and ask which to change    -------------
		//-------------------------------------------------------------------
		
		
		try {			
			column = database.getColumnNames(databaseName);
			size = column.length;
		} catch (Exception e) {
		}
		
		System.out.println("Which column header do you want to change");
		System.out.println("or 0 to return");
			
	    //Print column titles
		 for(int i = 0; i < column.length; i++){
		    System.out.printf("[%d]%s %n",i+1, column[i]);
		 }
		 
		 while(gate){
				gate = false;
				s = new Scanner(System.in);
				
				try{
				   choice = s.nextInt();
				   if(choice == 0){
					   break;
				   }
				   choice--;
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
					if(choice < 0){
						System.out.println("Please enter the a valid row number");
						System.out.println("1 - " + size);
						gate = true;
					}else{
						try{
							
							//-------------------------------------------------------------------
							//--------    Ask for new column name and send method call  ---------
							//-------------------------------------------------------------------
							
							System.out.println("What do you want the new header to be called?");
							s = new Scanner(System.in);			
							input = s.nextLine(); 
							
							database.changeColumn(databaseName, choice, input);
							
						}catch(Exception e){
							gate= true;
							System.out.println("----error reading database!----");
							e.printStackTrace();
						}
				    }
				}
				
			}
		
	}

	private static void setPrime() {
		int prime = 0;
		int size = 0;
		int choice = 0;
		Scanner s = new Scanner("");
		String[] column = null;
		String input;
		boolean gate = true;
		boolean change = false;
		
		
		System.out.println("----------------------------|   Change Prime  |-----------------------------");
		printName();
		System.out.println("----------------------------------------------------------------------------");
		
		System.out.println();
		System.out.println();
		
		//Getting column names and prime
		try {			
			column = database.getColumnNames(databaseName);
			size = column.length;
			prime = database.getPrime(databaseName);
		} catch (Exception e) {
		}
		
		System.out.printf("%s is currently being sorted by \"%s\"%n", databaseName, column[prime]);
		System.out.println("Do you want to change which column to sort by?");
		System.out.println("");
		System.out.println("Yes \"yes\" or No \"no\"");
		
		gate = true;
		while(gate){
			gate = false;
			s = new Scanner(System.in);			
			input = s.nextLine();
			String[] split = input.split("\\s+");
			
			switch(split[0].toLowerCase()){
	    		case("y"):
	    		case("yes"):
	    			change = true;
	    			break;
	    		case("n"):
	    		case("no"):
	    			break;
	    		default:
	    			gate = true;
	    			break;
			}
		}
		
		if(change){
			System.out.println("What column do you want to sort by?");
			System.out.println("or 0 to return");
				
		    //Print column titles
			 for(int i = 0; i < column.length; i++){
			    System.out.printf("[%d]%s %n",i+1, column[i]);
			 }
			 
			 gate = true;
			 while(gate){
					gate = false;
					s = new Scanner(System.in);
					
					try{
					   choice = s.nextInt();
					   if(choice == 0){
						   break;
					   }
					   choice--;
					}catch(Exception e){
						System.out.println("Please enter the a valid column number");
						System.out.println("1 - " + size);
						gate = true;
					}	
				
					if(choice > size){
						System.out.println("Please enter the a valid column number");
						System.out.println("1 - " + size);
						gate = true;
					}else{		
						if(choice < 0){
							System.out.println("Please enter the a valid column number");
							System.out.println("1 - " + size);
							gate = true;
						}else{
							try{								
								database.setPrime(databaseName, choice);
								
							}catch(Exception e){
								gate= true;
								System.out.println("----error reading database!----");
								e.printStackTrace();
							}
					    }
					}
					
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
		
		for(int i = 1; i < (stringLength - (stringLength/2)); i++){
			System.out.print("-");
		}
		
		System.out.println();
		
	}
}
