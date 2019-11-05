package javaShop;

import java.util.Scanner;

public class Runner {

	public static void main(String[] args) {
		
		Shop shop = new Shop("src/javaShop/stock.csv");
		// create instance of 'surrent' customer
		Customer customer = new Customer("src/javaShop/customer.csv");
		
		Scanner input = new Scanner(System.in);
		
		  int choice;
		    while(true){
		
	        // Display the menu
		    	System.out.println("\n**********Main Menu**********");
	        System.out.println("1\t Display shop");
	        System.out.println("2\t Show current customer's shopping list");
	        System.out.println("3\t Check and process current customers list");
	        System.out.println("4\t Search Products");
	        System.out.println("5\t Do some shopping");
	        System.out.println("6\t Exit\n");

	        System.out.println("Please enter your choice:");
	        
	        //Get user's choice
	        choice=input.nextInt();
	         
	        //Display the title of the chosen module
	        switch (choice) {
	            case 1: System.out.println("Shop"); 
	            Shop.printShop(shop);
	            	break;
	            case 2: System.out.println("Shopping List");
	            Customer.printCustomer(customer);
	                    break;
	            case 3: System.out.println("Check and process current customer's shopping list");
	            new Order(shop, customer);
	            // also save to csv to keep shop consistent
	            Order.shopSaveToCSV(shop, "src/javaShop/stock.csv"); 
	                    break;
	            case 4: System.out.println("Search Products"); 
	            Scanner scanner = new Scanner( System.in );
	    		String PName;
	    		System.out.print("Enter name: ");
	    		PName = scanner.nextLine( );
	    		
	    		int Amt;
	    		System.out.print("Enter amount: ");
	    		Amt = scanner.nextInt( );
	    		
    		String search = shop.searchProduct(PName, Amt);
	    		Shop.printProduct(search);
	                     break;
	            case 5: System.out.println("Hi welcome to the Shop!\n"); 
	            System.out.println("Let's start a shopping list for you...\n");
		           
	            
	            	String csvName = Order.createNewOrder(shop);
		            Customer newPerson = new Customer(csvName);
		            
		            
		            Scanner scan = new Scanner(System.in);
		            int subChoice;
		            boolean shouldBreak = false;
		            while(true){
		            	System.out.println("****Order Menu****");
			        System.out.println("1\t Show shop");
			        System.out.println("2\t Show shopping list");
			        System.out.println("3\t Check and process your order");
			        System.out.println("0\t To go back to the main menu\n");
			        System.out.println("Please enter your choice:");
			        subChoice=input.nextInt();
			        switch (subChoice) {
		            case 1: System.out.println("Shopping List"); 
		            Shop.printShop(shop);
	            	break;
		            case 2: System.out.println("Shopping List"); 
		            Customer.printCustomer(newPerson);
	            	break;
		            case 3: System.out.println("Check and Process Order");
		            Customer.printCustomer(newPerson);
		            new Order(shop, newPerson);
		            // also save to csv to keep shop consistent
		            Order.shopSaveToCSV(shop, "src/javaShop/stock.csv"); 
		            break;
		            case 0: 
		                System.out.println("Exiting Program...");
		                shouldBreak = true;
		                break;
		            default :
		                System.out.println("This is not a valid Menu Option! Please Select Another");
		                break;
		                
		        }
			        if (shouldBreak) break;
			        
		            }
	            
                		//break;	      
	            case 6: System.out.println("Exit"); 
	            System.exit(0);
        		break;	   
	            
	            default: System.out.println("Invalid choice");
	            break;
	        }//end of switch
		// Customer.createCustomer();
		//Customer customer = new Customer("src/javaShop/customer.csv");
		//Shop shop = new Shop("src/javaShop/stock.csv");
		// Shop.printShop(shop);
		// Customer.printCustomer(customer);
		//new Order(shop, customer);
		//Customer.printCustomer(customer);
		//Order.shopSaveToCSV(shop, "src/javaShop/stock.csv");  
		//String csvName = Order.createNewOrder(shop);
		
		//HERE
		//Customer customer = new Customer(csvName);
		//Customer.printCustomer(customer);
		
//		// input methods to search a product name and amount
//		Scanner scanner = new Scanner( System.in );
//		String PName;
//		System.out.print("Enter name: ");
//		PName = scanner.nextLine( );
//		
//		int Amt;
//		System.out.print("Enter amount: ");
//		Amt = scanner.nextInt( );
//		
//		//String search = shop.searchProduct("Coke Can", 1);
//		String search = shop.searchProduct(PName, Amt);
//		Shop.printProduct(search);

		    }}

}
