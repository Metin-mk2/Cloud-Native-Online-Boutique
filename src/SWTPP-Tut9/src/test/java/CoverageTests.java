package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import shop.Customer;
import shop.Order;
import shop.Product;
import shop.ShopInputException;
import shop.ShopSystem;

public class CoverageTests {

	static ShopSystem shop;
		
	static Date now = new Date(System.currentTimeMillis());
	static Date anniversary = new Date(now.getTime() + java.time.temporal.ChronoUnit.DAYS.getDuration().toMillis()); // tomorrow
	
	@Before
	public void setUp() throws Exception {
		shop = new ShopSystem();
		// add 6 Customers
		shop.addCustomer(new Customer(0, "Frank", "Street 1", "some@mail.com", false));
		shop.addCustomer(new Customer(1, "Johanna", "Street 2", "bla@mail.com", false));
		shop.addCustomer(new Customer(2, "Ulf", "Street 3", "blub@mail.com", false));
		shop.addCustomer(new Customer(3, "Susanne", "Street 4", "plii@mail.com", false));
		
		// Create two priority Customers with discount lists
		Customer mohamed = new Customer(4, "Mohamed", "Street 5", "bra@mail.com", true);
		mohamed.addDiscount(2);
		mohamed.addDiscount(3);
		mohamed.addDiscount(10);
		shop.addCustomer(mohamed);
		Customer chantal = new Customer(5, "Chantal", "Street 5", "bra@mail.com", true);
		chantal.addDiscount(5);
		chantal.addDiscount(1);
		chantal.addDiscount(2);
		shop.addCustomer(chantal);
		
		// add 4 non-discountable products
		shop.addProduct(new Product(0, "Ziegelstein", 20, false));
		shop.addProduct(new Product(1, "Fliese", 50, false));
		shop.addProduct(new Product(2, "Zement", 90, false));
		shop.addProduct(new Product(3, "Spachtel", 30, false));
		
		// add 2 discountable products
		shop.addProduct(new Product(4, "Hammer", 20, true));
		shop.addProduct(new Product(5, "Nagel", 1, true));
	
		// add anniversary
		shop.addAnniversary(anniversary.toInstant());
	}

}
