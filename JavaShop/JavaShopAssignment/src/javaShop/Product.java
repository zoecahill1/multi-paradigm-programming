package javaShop;

public class Product {
	private String name;
	private double price;

	public Product(String name, double price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public void printProduct() {
		System.out.printf("Prodcut Name: %s", name);
	}

	@Override
	public String toString() {
		System.out.printf("Product Name: %s \nProduct Price: €%.2f", name, price);

		return "";
	}

}