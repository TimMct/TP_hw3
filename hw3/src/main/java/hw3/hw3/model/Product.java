package hw3.hw3.model;
/**
 * Obiectul care reprezinta un Product cu id, nume, ...
 * @author Timotei
 *
 */
public class Product {
	private int id;
	private String name;
	private double price;
	private int quantity;
	
	/**
	 * Constructor simplu
	 */
	public Product() {
		
	}
	/**
	 * Constructor cu toate campurile
	 * @param id
	 * @param n
	 * @param a
	 * @param e
	 * @param age
	 */
	public Product(int id, String n, double p, int q) {
		this.id = id;
		name = n;
		price = p;
		quantity = q;
	}
	/**
	 * Constructor fara id, folosit cel mai des 
	 * @param n
	 * @param a
	 * @param e
	 * @param age
	 */
	public Product(String n, double p, int q) {
		name = n;
		price = p;
		quantity = q;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double p) {
		price = p;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int q) {
		quantity = q;
	}
	
	@Override
	public String toString() {
		return "Product #"+id+" named "+name+" priced with "+price+" u.m. available in "+quantity+" pieces";
	}
}
