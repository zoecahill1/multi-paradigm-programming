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
			// System.out.println(lines.get(0));

			String[] vals = lines.get(0).split(",");
			cash = Double.parseDouble(vals[0]);
			// cash = Double.parseDouble(lines.get(0));
			// i am removing at index 0 as it is the only one treated differently
			lines.remove(0);

			for (String line : lines) {
				// System.out.println(line);
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

	public String searchProduct(String product, int orderAmt){
		// init line var with nothing

		int missingStock=0;
		String missing="";// populate if stock is short
		// init var line containing default value
		String line = "false";
		
		// serach through stock for product
		for (int i = 0; i < this.stock.size(); i++) {
			
			ProductStock item = this.stock.get(i);
			// equalsIgnoreCase if they are of the same length, and corresponding characters in the two strings are equal ignoring case. [3]
			if (item.getProduct().getName().equalsIgnoreCase(product)){
				
				//line="";
				String productName = item.getProduct().getName();
				int productAmt = item.getQuantity();
				double productPrice = item.getProduct().getPrice();
				
				// display result
				//line = "Name: " + productName + "\nPrice: " + productPrice + "\nStock: " + productAmt;
				System.out.println(line);
				//line = String.format("The shop has %d x %s at €%.2f in stock",productAmt,productName, productPrice);
				
				// if there are more items on the order that we have available
				if (orderAmt > productAmt) {
					//more things are ordered than available
					missingStock = orderAmt - productAmt;
					// then the we will set the order amount to what we have
					orderAmt = productAmt; 
				} 
				// if we can fill the order we will add it
				if (missingStock > 0) {
					missing="," + missingStock;
				}
				
				double total = orderAmt * productPrice;
				// remove the order from shop stock
				item.setQuantity(productAmt-orderAmt);
				// retruns values in csv format which we can easily split later
				line = "" + productName + "," + orderAmt + "," + String.format("€%.2f",productPrice) + "," + String.format("€%.2f", total) + missing;
			}			
		}
		return line;
	}

	public ArrayList<ProductStock> getStock() {
		return stock;
	}

	public static void printProduct(String product) {
		// split products into array [5]
		String[] products = product.split(",");
		
		// if only one this is the default value we set earlier meanig there was no match
		if (products.length == 1) {
			System.out.println("Product not found");
		}
		
		// otherwise check length, if less than 5 means there was no unfilled stock message
		else {
			if (products.length <= 5) {
				//[4]
				System.out.printf("***Order Check****\n%s %s costing %s \nTotal: %s",products[1], products[0], products[2] , products[3]);
			}
			// otherwise there is so we print how many items were missing
			else{
				
			System.out.printf("***Order Check****\n%s %s costing %s but we are missing %s \nTotal: %s",products[1], products[0], products[2] ,products[4], products[3]);
			}
		}
		

		
	}
	
	public static void printShop(Shop shop) {

		System.out.println("------------------------\nWELCOME TO THE SHOP\n------------------------\n");
		System.out.printf("This shop currently holds €%.2f \n\n", shop.getCash());
		System.out.println(shop.getStock());
	}
	
	

//public static void main(String[] args) {
//		Shop shop = new Shop("src/javaShop/stock.csv");
//		printShop(shop);
//		String search= shop.searchProduct("Coke Can", 1);
//		printProduct(search);
//		
//}

		// Customer cust = new Customer("src/javaShop/customer.csv");
		// double budget = cust.getBudget();
		// String name = cust.getName();


}