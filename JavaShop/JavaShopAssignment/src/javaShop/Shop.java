package javaShop;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shop {

	// TODO getter and setter
	static double orderTotal = 0;

	private double cash;
	private ArrayList<ProductStock> stock;

	public Shop(String fileName) {

		stock = new ArrayList<>();
		List<String> lines = Collections.emptyList();

		try {
			lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
			String[] vals = lines.get(0).split(",");
			cash = Double.parseDouble(vals[0]);
			lines.remove(0);

			for (String line : lines) {
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

	public void setCash(double cash) {
		this.cash = cash;
	}

	public String searchProduct(String product, int orderAmt) {
		// defualt init value
		int missingStock = 0;
		// will remain blank unless stock is missing
		String missing = "";
		// init var line containing default value
		String line = "\n";

		// serach through stock for product
		for (int i = 0; i < this.stock.size(); i++) {

			ProductStock item = this.stock.get(i);
			// equalsIgnoreCase if they are of the same length, and corresponding characters
			// in the two strings are equal ignoring case. [3]
			if (item.getProduct().getName().equalsIgnoreCase(product)) {

				String productName = item.getProduct().getName();
				int productAmt = item.getQuantity();
				double productPrice = item.getProduct().getPrice();

				// display result
				System.out.println(line);

				// if there are more items on the order that we have available
				if (orderAmt > productAmt) {
					// more things are ordered than available
					missingStock = orderAmt - productAmt;
					// then the we will set the order amount to what we have
					orderAmt = productAmt - orderAmt;
				}

				// if we can fill the order we will add it
				if (missingStock > 0) {
					missing = "," + missingStock;
				}
				// total for 1 product
				double total = orderAmt * productPrice;
				// total customers bill
				orderTotal = orderTotal + total;
				// retruns values in csv format which we can easily split later
				line = "" + productName + "," + orderAmt + "," + String.format("€%.2f", productPrice) + ","
						+ String.format("€%.2f", total) + missing;
				// orderList = new ArrayList<>();
			}
		}
		return line;
	}

	// seraches through order
	public String processProduct(String product, int orderAmt) {
		orderTotal = 0;
		int missingStock = 0;
		String missing = "";
		String line = "\n";

		for (int i = 0; i < this.stock.size(); i++) {

			ProductStock item = this.stock.get(i);
			if (item.getProduct().getName().equalsIgnoreCase(product)) {

				String productName = item.getProduct().getName();
				int productAmt = item.getQuantity();
				double productPrice = item.getProduct().getPrice();
				System.out.println(line);
				if (orderAmt > productAmt) {
					missingStock = orderAmt - productAmt;
					// GIVING MINUS VALUES CHECK THIS
					orderAmt = productAmt - missingStock;

				}
				if (missingStock > 0) {
					missing = "," + missingStock;
				}

				double total = orderAmt * productPrice;
				// remove the order from shop stock
				item.setQuantity(productAmt - orderAmt);
				// retruns values in csv format which we can easily split later
				line = "" + productName + "," + orderAmt + "," + String.format("€%.2f", productPrice) + ","
						+ String.format("€%.2f", total) + missing;
			}
		}
		return line;
	}

	public ArrayList<ProductStock> getStock() {
		return stock;
	}

	// prints out product serach results
	public static void printProduct(String product) {

		// split products into array [5]
		String[] products = product.split(",");

		// if only one this is the default value we set earlier meanig there was no
		// match
		if (products.length == 1) {
			System.out.println("\n\n****Order Check****");
			System.out.printf("Product not found\n");
		}

		// otherwise check length, if less than 5 means there was no unfilled stock
		// message
		else {
			if (products.length <= 5) {
				// [4]
				System.out.printf("****Order Check****\n%s %s costing %s \nTotal: %s", products[1], products[0],
						products[2], products[3]);

			}

			// otherwise there is so we print how many items were missing
			else {

				System.out.printf("****Order Check****\n%s %s costing %s but we are missing %s \nTotal: %s",
						products[1], products[0], products[2], products[4], products[3]);
			}

		}

	}

	// method to print nicely formatted shop
	public static void printShop(Shop shop) {

		System.out.println("------------------------\nWELCOME TO THE SHOP\n------------------------\n");
		System.out.printf("This shop currently holds €%.2f \n\n", shop.getCash());
		System.out.println(shop.getStock());
	}

	// method to print out order list
	public static void printOrderList(ArrayList<ProductStock> order) {

		for (ProductStock productStock : order) {

			System.out.println(productStock.getQuantity());
			System.out.println(productStock.getProduct().getName());

		}
	}

}