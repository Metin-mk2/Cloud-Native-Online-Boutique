package shop;

import java.util.LinkedList;

public class Customer {

	private String name;
	private String street;
	private String mail;
	private int custNr;
	private Boolean priorityCostumer;
	private LinkedList<Integer> discount; 
	
	public Customer(int custNr, String name, String street, String mail, Boolean priority){
		this.name = name;
		this.street = street;
		this.mail = mail;
		this.custNr = custNr;
		this.priorityCostumer = priority;
		this.discount = new LinkedList<Integer>(); 
	}

	public String getName() {
		return name;
	}

	public String getStreet() {
		return street;
	}

	public String getMail() {
		return mail;
	}
	
	public int getCustNr(){
		return this.custNr;
	}
	
	public Boolean isPriorityCustomer() {
		return priorityCostumer;
	}

	public void setPriorityCostumer(Boolean priorityCostumer) {
		this.priorityCostumer = priorityCostumer;
	}

	public LinkedList<Integer> getDiscount() {
		return discount;
	}

	public void setDiscount(LinkedList<Integer> discount) {
		this.discount = discount;
	}

	public void addDiscount(int discount) {
		this.discount.add(discount);
	}
}
