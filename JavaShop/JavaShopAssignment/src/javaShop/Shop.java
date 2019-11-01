package javaShop;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shop {
	
	private double cash;
	private ArrayList<ProductStock> stock;

	public Shop(String fileName) {
		
		stock = new ArrayList<>();
		List<String> lines = Collections.emptyList();
		
		try {
			lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
			//System.out.println(lines.get(0));
			
			String[] vals = lines.get(0).split(",");
			cash = Double.parseDouble(vals[0]);
			//cash = Double.parseDouble(lines.get(0));
			// i am removing at index 0 as it is the only one treated differently
			lines.remove(0);
			
			for (String line : lines) {
				//System.out.println(line);
				String[] arr = line.split(",");
				String name = arr[0];
				
				double price = Double.parseDouble(arr[1]);
				int quantity = Integer.parseInt(arr[2].trim());
				
				Product p = new Product(name, price);
				ProductStock s = new ProductStock(p, quantity);
				stock.add(s);
				
				
			}
			
		}

		catch (IOException e) {

			// do something
			e.printStackTrace();
		}
	}

	public double getCash() {
		return cash;
	}
	
	public String searchProd(String product){
		// init line var with default message
		String line="We do not stock: " + product;
		// init bool to track positive results
		boolean found = false;
		
		// serach through stock for product
		for (int i=0; i<this.stock.size(); i++) {
			
			ProductStock item = this.stock.get(i);
			// equalsIgnoreCase if they are of the same length, and corresponding characters in the two strings are equal ignoring case. [3]
			if (item.getProduct().getName().equalsIgnoreCase(product)){
				// set bool to true if we have a match
				found = true;
				// get matching details
				String productName = item.getProduct().getName();
				int productAmt = item.getQuantity();
				double productPrice = item.getProduct().getPrice();
				
				// display result
				//line = "Name: " + productName + "\nPrice: " + productPrice + "\nStock: " + productAmt;
				line = String.format("The shop has %d x %s at €%.2f in stock",productAmt,productName, productPrice);
				//System.out.println(line); String.format("%d %d", a, c);
			}
		}
		
		if (found == true) {
			return line;
		}
		return line;
	}

	public ArrayList<ProductStock> getStock() {
		return stock;
	}
	
	public static void printShop(Shop shop) {
		
	System.out.println("------------------------\nWELCOME TO THE SHOP\n------------------------\n");
	System.out.printf("This shop currently holds €%.2f \n\n", shop.getCash());
	System.out.println(shop.getStock());	
	}



		  
		  
		  //public static void main(String[] args) {
//		Shop shop = new Shop("src/javaShop/stock.csv");
//		String result = shop.searchProd("Big Bags");
//		System.out.println(result);
//		//System.out.println(shop);
//		//printShop(shop);
//		//Customer james = new Customer("src/javaShop/customer.csv");
//		//System.out.println(james);



	public static void createShop() {
	    Shop shop = new Shop("src/javaShop/stock.csv");
	    printShop(shop);
	    //Customer cust = new Customer("src/javaShop/customer.csv");
	    //double budget = cust.getBudget();
	    //String name = cust.getName();
		
	}

}