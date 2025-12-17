package shop;

public class ShopInputException extends Exception {

	private static final long serialVersionUID = -6547363831733100942L;
	
	static public final String INVALID_CUST_NR = "Kunde nicht im System.";
	static public final String INVALID_PROD_NR = "Es existiert keine Ware mit der gegebenen Warennummer.";
	static public final String INVALID_DATE = "Kein Erstellungsdatum angegeben.";
	
	public ShopInputException(String msg){
		super(msg);
	}	
}
