package shop;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * This is the ShopSystem. It contains of a HashMap customers <id,customer> 
 * and an HashMap products<id,product>.
 * 
 */
public class ShopSystem {
	

	private HashMap<Integer,Customer> customers;
	private HashMap<Integer,Product> products;
	private LinkedList<Instant> anniversaries;
	
	public ShopSystem(){
		/**
		 * The constructor instantiates the HashMaps
		 */
		customers = new HashMap<Integer, Customer>();
		products = new HashMap<Integer, Product>();
		anniversaries = new LinkedList<Instant>();
	};
	
	/**
	 * This method creates an Order, if the given parameters are valid.
	 * 
	 * @param customerNr a valid customer id
	 * @param productNr a valid product id
	 * @param deadline a valid deadline, min 24 hours in the future
	 * @return the created Order
	 * @throws ShopInputException if a parameter is not valid, this Exception is thrown. The Exception contains of an error message, which describes the error.
	 */
	public Order createOrder(int customerID, int productID, Instant created) throws ShopInputException {	    			
	    // handle invalid inputs
	    if(!customers.containsKey(customerID)){
			throw new ShopInputException(ShopInputException.INVALID_CUST_NR);
		} 	    
		if(!products.containsKey(productID)){
			throw new ShopInputException(ShopInputException.INVALID_PROD_NR);
		} 
		if(created == null){
			throw new ShopInputException(ShopInputException.INVALID_DATE);
		}
			
		// get customer and product for given IDs
		Customer c = customers.get(customerID);
		Product p = products.get(productID);

		// create new order
		Order order = new Order(customerID, productID, p.getPrice(), created);
				
		if((c.isPriorityCustomer() & p.isDiscountable()) 
				|| isAnniversary(created)) {
				order.setDiscount(true);
				while(!c.getDiscount().isEmpty()) {
					order.discount(c.getDiscount().removeFirst());					
				}				
		}
				
		return order;
	}
	
	/**
	 * adds a new Customer to the ShopSystem
	 * 
	 * @param customer a customer object
	 */
	public void addCustomer(Customer customer){
		customers.put(customer.getCustNr(),customer);
	}
	
	/**
	 * adds a product to the ShopSystem
	 * 
	 * @param product a product object
	 */
	public void addProduct(Product product){
		products.put(product.getProdNr(), product);
	}

	public Boolean isAnniversary(Instant d1) {
		for(Instant d2 : anniversaries)
			if (java.time.temporal.ChronoUnit.DAYS.between(d1, d2) == 0) return true;
		return false;		
	}

	public void addAnniversary(Instant anniversary) {
		this.anniversaries.add(anniversary);
	}
	
	public void setAnniversaries(LinkedList<Instant> anniv) {
		this.anniversaries = anniv;
	}
}
