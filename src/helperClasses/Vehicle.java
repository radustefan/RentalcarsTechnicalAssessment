package helperClasses;
/**
 * JavaBean class for Vehicle object.
 * 
 * @author Radu Stefan
 *
 */
public class Vehicle implements Comparable<Vehicle> {

	private String sipp;
	private String name;
	private String supplier;
	private double price;
	private double rating;
	
	/**
	 * Empty constructor.
	 */
	public Vehicle () {
		this.sipp 	  = null;
		this.name	  = null;
		this.supplier = null;
		this.price  = 0;
		this.rating = 0;
	}
	
	/**
	 * Public constructor.
	 * 
	 * @param sipp - vehicle SIPP code
	 * @param price - vehicle price
	 * @param supplier - vehicle supplier
	 * @param name - vehicle name
	 * @param rating - supplier rating
	 */
	public Vehicle(String sipp, double price, String supplier, String name, double rating) {
		this.sipp	  = sipp;
		this.name	  = name;
		this.supplier = supplier;
		this.price    = price;
		this.rating   = rating;
	}
	
	/*
	 *  SETTER METHODS
	 */
	
	public void setSipp(String sipp) {
		this.sipp = sipp;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	
	public void setPrice (double price) {
		this.price = price;
	}
	
	public void setRating(double rating) {
		this.rating = rating;
	}
	
	/*
	 *  GETTER METHODS
	 */
	
	public String getSipp() {
		return this.sipp;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getSupplier() {
		return this.supplier;
	}
	
	public Double getPrice() {
		return this.price;
	}
	
	public Double getRating() {
		return this.rating;
	}
	
	@Override
	public String toString() {
		return "Vehicle {sipp: \""     + this.sipp 		+ "\", " + 
						"name: \""     + this.name 		+ "\", " +
						"supplier: \"" + this.supplier  + "\", " +
						"price: "	   + this.price 	+ ", "	 +
						"rating: " 	   + this.rating 	+ "}";
	}

	@Override
	public int compareTo(Vehicle o) {
		// TODO No method of comparing vehicles was provided.
		//		Replace when algorithm is provided.
		return 0;
	}
}
