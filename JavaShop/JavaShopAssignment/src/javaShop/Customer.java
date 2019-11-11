package javaShop;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer {

	private String name;
	private double budget;
	private ArrayList<ProductStock> shoppingList;

	public Customer(String fileName) {
		shoppingList = new ArrayList<>();
		List<String> lines = Collections.emptyList();
		try {
			lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
			String[] firstLine = lines.get(0).split(",");
			name = firstLine[0];
			budget = Double.parseDouble(firstLine[1]);
			// i am removing at index 0 as it is the only one treated differently
			lines.remove(0);
			for (String line : lines) {
				String[] arr = line.split(",");
				String name = arr[0];
				int quantity = Integer.parseInt(arr[1].trim());
				Product p = new Product(name, 0);
				ProductStock s = new ProductStock(p, quantity);
				shoppingList.add(s);
			}

		}

		catch (IOException e) {

			// do something
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public double getBudget() {
		return budget;
	}

	public ArrayList<ProductStock> getShoppingList() {
		return shoppingList;
	}

	// method to print nicely formatted customer's shopping list
	public static void printCustomer(Customer customer) {
		System.out.println("------------------------\nCUSTOMER\n------------------------\n");

		System.out.printf("Customer Name: %s \nCustomer Budget: €%.2f", customer.getName(), customer.getBudget());
		System.out.println("\n\n**********SHOPPING LIST**********\n");
		// loops through shopping list
		for (ProductStock productStock : customer.getShoppingList()) {
			System.out.println(productStock.getQuantity() + " x " + productStock.getProduct().getName() + "\n");
		}
	}

}