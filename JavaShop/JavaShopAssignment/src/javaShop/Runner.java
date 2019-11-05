package javaShop;

import java.util.Scanner;
import java.util.jar.Attributes.Name;

public class Runner {

	public static void main(String[] args) {
		// Customer.createCustomer();
		//Customer customer = new Customer("src/javaShop/customer.csv");
		Shop shop = new Shop("src/javaShop/stock.csv");
		// Shop.printShop(shop);
		// Customer.printCustomer(customer);
		//new Order(shop, customer);
		//Customer.printCustomer(customer);
		//Order.shopSaveToCSV(shop, "src/javaShop/stock.csv");  
		Order.createNewOrder(shop);
		
		//HERE
		//Customer customer = new Customer(Order.csv);
		
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

	}

}
