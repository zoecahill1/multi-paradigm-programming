package javaShop;

//import java.io.Console;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import com.opencsv.CSVWriter;

public class Order {
	public Order(Shop shop, Customer customer) {

		double budget = customer.getBudget();
		String name = customer.getName();

		System.out.printf("\nName: %s \nBudget:  €%.2f\n", name, budget);
		ArrayList<ProductStock> shoppingList = customer.getShoppingList();

		// loop through shopping list
		for (ProductStock productStock : shoppingList) {

			// get names and qunatities from shopping list
			int shoppingListAmt = productStock.getQuantity();
			String shoppingListItem = productStock.getProduct().getName();
			// uses serach product to compare
			String item = shop.searchProduct(shoppingListItem, shoppingListAmt);
			Shop.printProduct(item);

		}

		// calls ordertotal which is a static variable in Shop where serachprod function
		// is
		// should not use static variable like this but had some visablity problems
		// calling it
		System.out.printf("\n\nYour total order comes to: €%.2f\n", Shop.orderTotal);

		// checks budget of customer against total
		double budgetCheck = (budget - Shop.orderTotal);
		System.out.printf("Your remaining budget is: €%.2f\n", budgetCheck);

		// checks if the customer has enough money...if they do ask if we process it or
		// not
		if (budgetCheck < 0) {
			System.out.println("\nYou have don't have enough money for this...Sorry!");
		} else {
			System.out.println("\nYou have enough in your budget for this...lets buy some stuff!");
			String ans;
			Scanner keyboard;
			keyboard = new Scanner(System.in);
			System.out.println("Lets process this order! Do you want to continue? y/n");
			ans = keyboard.next();

			// no means we just finish
			if ("n".equals(ans)) {
				System.out.println("Ok Bye");

			}

			// yes process order
			else if ("y".equals(ans)) {

				System.out.println("************Summary************");
				Order.ProcessOrder(shop, customer);
			}

			else {
				System.out.println("Only y or n please");
			}


		}
	}

	public static void ProcessOrder(Shop shop, Customer customer) {

		double budget = customer.getBudget();
		String name = customer.getName();

		System.out.printf("\nName: %s \nBudget:  €%.2f\n", name, budget);
		System.out.printf("\n\nYour total order comes to: €%.2f\n", Shop.orderTotal);

		double budgetCheck = (budget - Shop.orderTotal);
		System.out.printf("Your remaining budget is: €%.2f\n", budgetCheck);

		// sets remaining budget for this instance of the customer
		customer.setBudget(budgetCheck);

		// sets the new total for the shop cash by adding the money we get from the
		// order
		double money = shop.getCash();
		shop.setCash(money + Shop.orderTotal);

	}

	// saving shop updates to csv file to keep shop consistent
	public static int shopSaveToCSV(Shop shop, String shopCSVFile) {
		try {

			FileWriter writer = new FileWriter(shopCSVFile);

			double cash = shop.getCash();
			// write new cash value from current instance of shop after order processed
			writer.append(cash + "\n");
			// get current stock levels from instance of shop
			ArrayList<ProductStock> stock = shop.getStock();
			for (ProductStock productStock : stock) {
				int quantity = productStock.getQuantity();
				String name = productStock.getProduct().getName();
				double price = productStock.getProduct().getPrice();
				// write these to file
				writer.append(name + ", " + price + ", " + quantity + "\n");
			}
			writer.flush();
			// close file when done
			writer.close();
		} catch (Exception e) {
			// error handling
			e.printStackTrace();
			return -1;
		}
		// returns 0 if errors occur
		return 0;
	}

	// this will create a new order by writing a csv file of it
	public static String createNewOrder(Shop shop) {
		// create blank var for filename
		String csvName = "";
		try {
			// generates a timestamp as filename so each individual order will be unique
			// also useful for a shop to keep record of their orders

			String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			// add folder name to path
			csvName = "src/javaShop/" + timestamp + ".csv";
			// no_quote_char as csv was being written with "" in it
			CSVWriter writer = new CSVWriter(new FileWriter(csvName), CSVWriter.DEFAULT_SEPARATOR,
					CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.RFC4180_LINE_END);

			ArrayList<String[]> data = new ArrayList<String[]>();

			// first line
			Scanner scanner = new Scanner(System.in);
			// had to init a second scanner as got only using one threw alot of errors
			Scanner intscanner = new Scanner(System.in);
			String name;
			System.out.print("Please enter your name: ");
			name = scanner.nextLine();

			// TODO taking a string instead of na int come back to this
			String budget;
			System.out.print("Please enter your budget: ");
			budget = scanner.nextLine();

			// add to the file
			data.add(new String[] { name, budget });

			int length;
			// getting length for loop
			System.out.println("How many items would you like to buy today: ");
			length = intscanner.nextInt();

			for (int i = 0; i < length; i++) {

				String itemName;
				System.out.print("Enter name: ");
				itemName = scanner.nextLine();

				String itemAmt;
				System.out.print("Enter amount: ");
				itemAmt = scanner.nextLine();

				// add product to file
				data.add(new String[] { itemName, itemAmt });

			}
			writer.writeAll(data);
			// close file
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
		
		// returns the file name so we know what to call in runner
		return csvName;
	}

}
