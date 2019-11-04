package javaShop;

//import java.io.Console;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Order {

	public Order(Shop shop, Customer customer) {

		// private ArrayList<ProductStock> s;
		double budget = customer.getBudget();
		String name = customer.getName();
		double listTotal = 0;

		System.out.printf("\nNAME: %s \nBUDGET:  €%.2f\n", name, budget);
		ArrayList<ProductStock> shoppingList = customer.getShoppingList();

		for (ProductStock productStock : shoppingList) {

			int shoppingListAmt = productStock.getQuantity();

			String shoppingListItem = productStock.getProduct().getName();

			String item = shop.searchProduct(shoppingListItem, shoppingListAmt);
			Shop.printProduct(item);

			// double itemTotal = Shop.printProduct(item);
			// listTotal += itemTotal;
		}
		System.out.printf("\n\nYour total order comes to: €%.2f\n", Shop.orderTotal);

		double budgetCheck = (budget - Shop.orderTotal);
		System.out.printf("Your remaining budget is: €%.2f\n", budgetCheck);
		checkBudget(budgetCheck);

		double money = shop.getCash();
		shop.setCash(money + Shop.orderTotal);
		//System.out.println(Shop.orderList);
		
		Shop.printOrderList(Shop.orderList);

	}


	public static void main(String[] args) {

		Shop shop = new Shop("src/javaShop/stock.csv");
		Shop.printShop(shop);
		Customer cust = new Customer("src/javaShop/customer.csv");
		new Order(shop, cust);
		Shop.printShop(shop);
	}

	public static void checkBudget(double checkNum) {

		if (checkNum < 0) {
			System.out.println("You have no money bye");
		} 
		else {
			System.out.println("Lets process this");

		}
	}
}