package hw3.hw3.model;
/**
 * Obiectul care reprezinta un Orderr cu id, clientId, ...
 * @author Timotei
 *
 */
public class Orderr {
	private int id;
	private int clientId;
	private int productId;
	private int quantity;
	/**
	 * Constructor simplu
	 */
	public Orderr() {
		
	}
	/**
	 * Constructor cu toate campurile
	 * @param id
	 * @param n
	 * @param a
	 * @param e
	 * @param age
	 */
	public Orderr(int id, int cId, int pId, int q) {
		this.id = id;
		clientId = cId;
		productId = pId;
		quantity = q;
	}
	/**
	 * Constructor fara id, folosit cel mai des 
	 * @param n
	 * @param a
	 * @param e
	 * @param age
	 */
	public Orderr(int cId, int pId, int q) {
		clientId = cId;
		productId = pId;
		quantity = q;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getClientId() {
		return clientId;
	}
	
	public void setClientId(int cId) {
		clientId = cId;
	}
	
	public int getProductId() {
		return productId;
	}
	
	public void setProductId(int pId) {
		productId = pId;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int q) {
		quantity = q;
	}
	
	@Override
	public String toString() {
		return "Order #"+id+" made by client #"+clientId+" for product #"+productId+" with quantity of "+quantity+" pieces";
	}
}
