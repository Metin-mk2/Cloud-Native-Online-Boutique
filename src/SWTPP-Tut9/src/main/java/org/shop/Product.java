package shop;

public class Product {

	private String name;
	private int price;
	private int prodNr;
	private Boolean discountable;
	
	public Product(int prodNr, String name, int price, Boolean discountable){
		this.name = name;
		this.price = price;
		this.prodNr = prodNr;
		this.discountable = discountable;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public int getProdNr(){
		return this.prodNr;
	}
	
	public Boolean isDiscountable() {
		return discountable;
	}

	public void setDiscountable(Boolean discountable) {
		this.discountable = discountable;
	}
}
