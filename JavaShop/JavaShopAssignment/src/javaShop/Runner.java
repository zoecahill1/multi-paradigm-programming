package javaShop;

import java.util.Scanner;

public class Runner {

	public static void main(String[] args) {

		Shop shop = new Shop("src/javaShop/stock.csv");
		// create instance of default customer
		Customer customer = new Customer("src/javaShop/customer.csv");

		Scanner input = new Scanner(System.in);

		int choice;
		while (true) {

			// Display the menu
			System.out.println("\n**********Main Menu**********");
			System.out.println("1\t Display shop");
			System.out.println("2\t Show current customer's shopping list");
			System.out.println("3\t Check and process current customers list");
			System.out.println("4\t Search Products");
			System.out.println("5\t Do some shopping");
			System.out.println("6\t Exit\n");

			System.out.println("Please enter your choice:");

			// Get user's choice
			choice = input.nextInt();

			// Display the title of the chosen module
			switch (choice) {

			case 1:
				System.out.println("Shop");
				Shop.printShop(shop);
				break;

			case 2:
				System.out.println("Shopping List");
				Customer.printCustomer(customer);
				break;

			case 3:
				System.out.println("Check and process current customer's shopping list");
				new Order(shop, customer);
				// also save to csv to keep shop consistent
				Order.shopSaveToCSV(shop, "src/javaShop/stock.csv");
				break;

			// will serach user inputted products and amounts and check against stock file
			case 4:
				System.out.println("Search Products");
				Scanner scanner = new Scanner(System.in);
				String PName;
				System.out.print("Enter name: ");
				PName = scanner.nextLine();

				int Amt;
				System.out.print("Enter amount: ");
				Amt = scanner.nextInt();

				String search = shop.searchProduct(PName, Amt);
				Shop.printProduct(search);
				break;

			// live shop mode...allows the user to create a new order
			case 5:
				System.out.println("Hi welcome to the Shop!\n");
				System.out.println("Let's start a shopping list for you...\n");

				String csvName = Order.createNewOrder(shop);
				Customer newPerson = new Customer(csvName);

				Customer.printCustomer(newPerson);
				Customer.printCustomer(newPerson);
				new Order(shop, newPerson);
				// also save to csv to keep shop consistent
				Order.shopSaveToCSV(shop, "src/javaShop/stock.csv");
				System.out.println("\n***************Updated Shop***************\n\n");
				Shop.printShop(shop);

			case 6:
				System.out.println("Thanks for shopping with us! ");
				System.exit(0);
				break;

			// if anything other that options entered
			default:
				System.out.println("Invalid choice");
				break;
			}// end of switch

		}
	}

}
