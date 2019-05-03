package hw3.hw3.businessLayer;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import hw3.hw3.businessLayer.validators.OrderValidator;
import hw3.hw3.dao.OrderDAO;
import hw3.hw3.model.Orderr;

/**
 * Reprezinta logica de business pentru o comanda
 * @author Timotei
 *
 */
public class OrderBLL {
	private OrderValidator v;
	private OrderDAO oDAO;

	public OrderBLL() {
		v = new OrderValidator();
		oDAO = new OrderDAO();
	}
	/**
	 * Metoda este folosita pentru a returna comanda gasita in baza de date prin apelul de data access object a metodei selectById().
	 * @param id este o variabila int care reprezinta cheia primara in tabelul orderr
	 * @return comanda cu id-ul egal cu parametrul de intrare
	 */
	public Orderr findOrderById(int id) {
		Orderr o = oDAO.selectById(id);
		if (o == null) {
			throw new NoSuchElementException("The order with id =" + id + " was not found!");
		}
		return o;
	}
	/**
	 * Metoda folosita pentru a returna toate comenzile din baza de date
	 * @return lista de obiecte de tip orderr care contin valorile din tabelul respectiv
	 */
	public ArrayList<Orderr> findAllOrders(){//view all
		ArrayList<Orderr> all = oDAO.selectAll();
		if(all.size() == 0) {
			throw new NoSuchElementException("No order was found!");
		}
		return all;
	}
	/**
	 * Metoda folosita pentru a insera o noua comanda verificandu-se de asemenea daca este valida
	 * @param o de tip orderr care trebuie introdus in baza de date
	 * @throws IllegalArgumentException in cazul in care un atribut al obiectului este invalid
	 */
	public void insertOrder(Orderr o)  throws IllegalArgumentException{//add op	
		v.validate(o);
		oDAO.insert(o);
	}
	/**
	 * Metoda folosita pentru a edita o comanda daca aceasta exista deja in baza de date
	 * @param o comanda care se va modifica
	 * @throws IllegalArgumentException in cazul in care noua modificare este invalida
	 * @throws NoSuchElementException in cazul in care nu exista comanda respectiva
	 */
	public void updateOrder(Orderr o)  throws IllegalArgumentException, NoSuchElementException{//edit op
		v.validate(o);
		findOrderById(o.getId());
		oDAO.update(o);
	}
	/**
	 * Metoda folosita pentru a sterge o comanda daca exista deja
	 * @param id comanda se va cauta dupa acest id
	 * @throws NoSuchElementException in cazul in care nu exista aceasta comanda
	 */
	public void deleteOrder(int id)  throws NoSuchElementException{//delete op		
		findOrderById(id);
		oDAO.delete(id);
	}
}
