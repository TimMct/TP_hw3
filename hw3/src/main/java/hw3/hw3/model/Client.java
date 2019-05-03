package hw3.hw3.model;
/**
 * Obiectul care reprezinta un Client cu id, nume, ...
 * @author Timotei
 *
 */
public class Client {
	private int id;
	private String name;
	private String address;
	private String email;
	private int age;
	
	/**
	 * Constructor simplu
	 */
	public Client() {
		
	}
	
	/**
	 * Constructor cu toate campurile
	 * @param id
	 * @param n
	 * @param a
	 * @param e
	 * @param age
	 */
	public Client(int id, String n, String a, String e, int age) {
		this.id = id;
		name = n;
		address = a;
		email = e;
		this.age = age;
	}
	
	/**
	 * Constructor fara id, folosit cel mai des 
	 * @param n
	 * @param a
	 * @param e
	 * @param age
	 */
	public Client(String n, String a, String e, int age) {
		name = n;
		address = a;
		email = e;
		this.age = age;
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
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String a) {
		address = a;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String e) {
		email = e;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "Client #"+id+" called "+name+" from "+address+" with email "+email+" having "+age+" years";
	}
	
}
