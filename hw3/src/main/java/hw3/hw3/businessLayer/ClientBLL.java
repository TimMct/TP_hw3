package hw3.hw3.businessLayer;

import hw3.hw3.dao.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import hw3.hw3.businessLayer.validators.*;
import hw3.hw3.model.*;

//am folosit cod de pe BitBucket, utcn_dsrl
/**
 * Reprezinta logica de business pentru un client
 * @author Timotei
 *
 */
public class ClientBLL {
	
	private ClientValidator v;
	private ClientDAO cDAO;
	
	public ClientBLL() {
		v = new ClientValidator();
		cDAO = new ClientDAO();
	}
	/**
	 * Metoda este folosita pentru a returna clientul gasit in baza de date prin apelul de data access object a metodei selectById().
	 * @param id este o variabila int care reprezinta cheia primara in tabelul client
	 * @return clientul cu id-ul egal cu parametrul de intrare
	 */
	public Client findClientById(int id) {
		Client c = cDAO.selectById(id);
		if (c == null) {
			throw new NoSuchElementException("The client with id =" + id + " was not found!");
		}
		return c;
	}
	/**
	 * Metoda folosita pentru a returna toti clientii din baza de date
	 * @return lista de obiecte de tip client care contin valorile din tabelul respectiv
	 */
	public ArrayList<Client> findAllClients(){//view all
		ArrayList<Client> all = cDAO.selectAll();
		if(all.size() == 0) {
			throw new NoSuchElementException("No client was found!");
		}
		return all;
	}
	/**
	 * Metoda folosita pentru a insera un nou client verificandu-se de asemenea daca este valid
	 * @param c obiectul de tip client care trebuie introdus in baza de date
	 * @throws IllegalArgumentException in cazul in care un atribut al obiectului este invalid
	 */
	public void insertClient(Client c) throws IllegalArgumentException{//add op
		v.validate(c);
		cDAO.insert(c);
	}
	/**
	 * Metoda folosita pentru a edita un client daca acesta exista deja in baza de date
	 * @param c clientul care se va modifica
	 * @throws IllegalArgumentException in cazul in care noua modificare este invalida
	 * @throws NoSuchElementException in cazul in care nu exista clientul respectiv
	 */
	public void updateClient(Client c) throws IllegalArgumentException, NoSuchElementException{//edit op
		v.validate(c);
		findClientById(c.getId());
		cDAO.update(c);
	}
	/**
	 * Metoda folosita pentru a sterge un client daca exista deja
	 * @param id clientul se va cauta dupa acest id
	 * @throws NoSuchElementException in cazul in care nu exista acest client
	 */
	public void deleteClient(int id) throws NoSuchElementException{//delete op
		findClientById(id);
		cDAO.delete(id);
	}
}
