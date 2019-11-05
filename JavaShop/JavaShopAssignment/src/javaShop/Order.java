package javaShop;

//import java.io.Console;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import com.opencsv.CSVWriter;


public class Order {
	static String csv = "";
	public Order(Shop shop, Customer customer) {

		// private ArrayList<ProductStock> s;
		double budget = customer.getBudget();
		String name = customer.getName();

		System.out.printf("\nNAME: %s \nBUDGET:  €%.2f\n", name, budget);
		ArrayList<ProductStock> shoppingList = customer.getShoppingList();

		for (ProductStock productStock : shoppingList) {

			int shoppingListAmt = productStock.getQuantity();

			String shoppingListItem = productStock.getProduct().getName();

			String item = shop.searchProduct(shoppingListItem, shoppingListAmt);
			Shop.printProduct(item);

		}
		System.out.printf("\n\nYour total order comes to: €%.2f\n", Shop.orderTotal);

		double budgetCheck = (budget - Shop.orderTotal);
		System.out.printf("Your remaining budget is: €%.2f\n", budgetCheck);

		if (budgetCheck < 0) {
			System.out.println("You have don't have enough money for this...Sorry!");
		} else {
			System.out.println("Lets buy some stuff");
			String ans;
			Scanner keyboard;
			keyboard = new Scanner(System.in);
			System.out.println("Lets process this order! Do you want to continue? y/n");
			ans = keyboard.next();

			if ("n".equals(ans)) {
				System.out.println("Ok Bye");

			}

			else if ("y".equals(ans)) {

				System.out.println("************Summary************");
				Order.ProcessOrder(shop, customer);
			}

			else {
				System.out.println("No");
			}

		}
	}

	public static void ProcessOrder(Shop shop, Customer customer) {

		// private ArrayList<ProductStock> s;
		double budget = customer.getBudget();
		String name = customer.getName();

		System.out.printf("\nNAME: %s \nBUDGET:  €%.2f\n", name, budget);
		ArrayList<ProductStock> shoppingList = customer.getShoppingList();

		for (ProductStock productStock : shoppingList) {

			int shoppingListAmt = productStock.getQuantity();

			String shoppingListItem = productStock.getProduct().getName();

			String item = shop.processProduct(shoppingListItem, shoppingListAmt);
			Shop.printProduct(item);

		}
		System.out.printf("\n\nYour total order comes to: €%.2f\n", Shop.orderTotal);

		double budgetCheck = (budget - Shop.orderTotal);
		System.out.printf("Your remaining budget is: €%.2f\n", budgetCheck);
		
		customer.setBudget(budgetCheck);

		double money = shop.getCash();
		shop.setCash(money + Shop.orderTotal);
		
		

	}
	
	
	  public static int shopSaveToCSV(Shop shop, String shopCSVFile) {
		    try {
		      FileWriter writer = new FileWriter(shopCSVFile);
		      //FileWriter writer = new FileWriter(shopCSVFile);
		      
		      double cash = shop.getCash();
		      writer.append(cash + "\n");
		      ArrayList<ProductStock> stock = shop.getStock();
		      for (ProductStock productStock : stock) {
		        int quantity = productStock.getQuantity();
		        String name = productStock.getProduct().getName();
		        double price = productStock.getProduct().getPrice();
		        writer.append(name + ", " + price + ", " + quantity + "\n");
		      }
		      writer.flush();
		      writer.close();  
		    } catch (Exception e) {
		      e.printStackTrace();
		      return -1;
		    }
		    // returns 0 if errors occur
		    return 0;
		  }
	
	  public static int createNewOrder(Shop shop) {
		  try {

			  String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			  
			  String csv = "src/javaShop/" + timestamp + ".csv";
			  
			  CSVWriter writer = new CSVWriter(new FileWriter(csv),
				    CSVWriter.DEFAULT_SEPARATOR,
				    CSVWriter.NO_QUOTE_CHARACTER,
				    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
				    CSVWriter.RFC4180_LINE_END);

		  ArrayList<String[]> data = new ArrayList<String[]>();
		  
		  //first line
		  Scanner scanner = new Scanner( System.in );
		  Scanner intscanner = new Scanner( System.in );
		  String name;
		  System.out.print("Enter name: ");
		  name = scanner.nextLine( );
		  
		  // taking a string instead of na int come back to this
		  String budget;
		  System.out.print("Enter your budget: ");
		  budget = scanner.nextLine( );
		  
		  
		  data.add(new String[] {name, budget});
		  
		  int length;
		  System.out.println("How many items would you like to buy: ");
		  length = intscanner.nextInt();

		for(int i=0;i<length;i++){

			  String itemName;
			  System.out.print("Enter name: ");
			  itemName = scanner.nextLine( );
			  
			  String itemAmt;
			  System.out.print("Enter amount: ");
			  itemAmt = scanner.nextLine( );
			  
			  data.add(new String[] {itemName, itemAmt});
			
		  //data.add(new String[] {"Coke Can", "6"});
		  //data.add(new String[] {"Big Bags", "2"});
		}
		  writer.writeAll(data);

		  writer.close();
		  }
		  catch (Exception e){
			  e.printStackTrace();
		      return -1;
	  }
		  return 0;
		  }
	  


//	public static void main(String[] args) {
//
//		Shop shop = new Shop("src/javaShop/stock.csv");
//		//Shop.printShop(shop);
//		//Customer cust = new Customer("src/javaShop/customer.csv");
//		
//		Customer cust = new Customer("src/javaShop/2019.11.05.17.54.34.csv");
//		//new Order(shop, cust);
//		//shopSaveToCSV(shop, "src/javaShop/stock.csv");  
//		//Shop.printShop(shop);
//		//Customer.printCustomer(cust);
//		
//		//createNewOrder(shop);
//		
//		
//	  }

}
