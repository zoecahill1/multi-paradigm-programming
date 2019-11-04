package javaShop;

public class ProductStock {
	private Product product;
	private int quantity;
	
	public ProductStock(Product product, int quantity) {
		super();
		this.product = product;
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		//System.out.println(product + "\nAmount: "+ quantity + "\n");
		System.out.println(getProduct() + "\nAmount: "+ getQuantity() + "\n");
		return "";
	}
	
	

}