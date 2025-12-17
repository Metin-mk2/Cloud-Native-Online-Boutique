package shop;

import java.time.Instant;

public class Order {

	private int customerNr;
	private int productNr;
	private Instant created;
	private int price;
	private boolean discount = false;

	public Order(int customerNr, int productNr, int price, Instant created){
		this.customerNr = customerNr;
		this.productNr = productNr;
		this.created = created;
		this.price = price;
	}

	public void setCustomerNr(int cNr){
		this.customerNr = cNr;
	}

	public int getCustomerNr(){
		return customerNr;
	}

	public void setProductNr(int pNr){
		this.productNr = pNr;
	}

	public int getProductNr(){
		return productNr;
	}
	
	public Instant getCreated() {
		// defensive copy
		return created;
	}

	public void setCreated(Instant created) {
		// defensive copy
		this.created = created;
	}

	public int getPrice() {
			return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}


	public void discount(int discount) {
		this.price = (this.price * (100 - discount))/100;		
	}

	public boolean hasDiscount() {
		return discount;
	}

	public void setDiscount(boolean discount) {
		this.discount = discount;
	}

}
