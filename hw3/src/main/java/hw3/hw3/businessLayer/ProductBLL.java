package hw3.hw3.businessLayer;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import hw3.hw3.businessLayer.validators.ProductValidator;
import hw3.hw3.dao.ProductDAO;
import hw3.hw3.model.Product;
/**
 * Reprezinta logica de business pentru un produs
 * @author Timotei
 *
 */
public class ProductBLL {
	private ProductValidator v;
	private ProductDAO pDAO;

	public ProductBLL() {
		v = new ProductValidator();
		pDAO = new ProductDAO();
	}
	/**
	 * Metoda este folosita pentru a returna produsul gasit in baza de date prin apelul de data access object a metodei selectById().
	 * @param id este o variabila int care reprezinta cheia primara in tabelul product
	 * @return produsul cu id-ul egal cu parametrul de intrare
	 */
	public Product findProductById(int id) {
		Product p = pDAO.selectById(id);
		if (p == null) {
			throw new NoSuchElementException("The product with id =" + id + " was not found!");
		}
		return p;
	}
	/**
	 * Metoda folosita pentru a returna totate produsele din baza de date
	 * @return lista de obiecte de tip produs care contin valorile din tabelul respectiv
	 */
	public ArrayList<Product> findAllProducts(){//view all
		ArrayList<Product> all = pDAO.selectAll();
		if(all.size() == 0) {
			throw new NoSuchElementException("No product was found!");
		}
		return all;
	}
	/**
	 * Metoda folosita pentru a insera un nou produs verificandu-se de asemenea daca este valid
	 * @param p obiectul de tip produs care trebuie introdus in baza de date
	 * @throws IllegalArgumentException in cazul in care un atribut al obiectului este invalid
	 */
	public void insertProduct(Product p)  throws IllegalArgumentException{//add op
		v.validate(p);
		pDAO.insert(p);
	}
	/**
	 * Metoda folosita pentru a edita un produs daca acesta exista deja in baza de date
	 * @param p produsul care se va modifica
	 * @throws IllegalArgumentException in cazul in care noua modificare este invalida
	 * @throws NoSuchElementException in cazul in care nu exista produsul respectiv
	 */
	public void updateProduct(Product p)  throws IllegalArgumentException, NoSuchElementException{//edit op
		v.validate(p);
		findProductById(p.getId());
		pDAO.update(p);
	}
	/**
	 * Metoda folosita pentru a sterge un produs daca exista deja
	 * @param id produsul se va cauta dupa acest id
	 * @throws NoSuchElementException in cazul in care nu exista acest produs
	 */
	public void deleteProduct(int id)  throws NoSuchElementException{//delete op
		findProductById(id);
		pDAO.delete(id);
	}
}
