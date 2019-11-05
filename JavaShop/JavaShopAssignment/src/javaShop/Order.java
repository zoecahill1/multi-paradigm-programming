package javaShop;

//import java.io.Console;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
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
		double listTotal = 0;

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

//
//	public static void main(String[] args) {
//
//		Shop shop = new Shop("src/javaShop/stock.csv");
//		Shop.printShop(shop);
//		Customer cust = new Customer("src/javaShop/customer.csv");
//		new Order(shop, cust);
//		Shop.printShop(shop);
//	}

}
