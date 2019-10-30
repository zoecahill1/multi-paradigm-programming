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

	public ArrayList<ProductStock> getStock() {
		return stock;
	}

	@Override
	// ref [2]
	public String toString() {
		System.out.println("------------------------\nWELCOME TO THE SHOP\n------------------------\n");
		System.out.printf("This shop currently holds €%.2f \n\n", cash);
		System.out.println(stock);
		
		return "";
		//return "Shop cash=" + cash + "\n\n" + stock;
	}

	public static void main(String[] args) {
		Shop shop = new Shop("src/javaShop/stock.csv");
		System.out.println(shop);
		//Customer james = new Customer("src/javaShop/customer.csv");
		//System.out.println(james);
	}

}