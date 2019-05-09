package hw3.hw3.presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.NoSuchElementException;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hw3.hw3.businessLayer.ClientBLL;
import hw3.hw3.businessLayer.OrderBLL;
import hw3.hw3.businessLayer.ProductBLL;
import hw3.hw3.model.Client;
import hw3.hw3.model.Orderr;
import hw3.hw3.model.Product;
/**
 * Ascultator folosit pentru butonul de efectuare a comenzilor
 * @author Timotei
 *
 */
public class OrderListener implements ActionListener{

	private static int nrOfOrder = 0;	
	private JLabel error;
	private JTextField clientNr;
	private JTextField prodNr;
	private JTextField quantNr;
	/**
	 * Constructor care se foloseste de componente din interfata
	 * @param e eroare
	 * @param c id client
	 * @param p id produs
	 * @param q cantitate
	 */
	public OrderListener(JLabel e, JTextField c, JTextField p, JTextField q) {
		error = e;
		clientNr = c;
		prodNr = p;
		quantNr = q;
	}
	
	/**
	 * Metoda care este apelata automat la apasarea butonului 
	 * si anume metoda care va realiza crearea comenzii
	 * si scrierea ei intru-un fisier daca toate datele de intrare sunt valide
	 */
	public void actionPerformed(ActionEvent event) {
		error.setText("");
		ClientBLL cbl = new ClientBLL();
		ProductBLL pbl = new ProductBLL();
		OrderBLL obl = new OrderBLL();
		int cliendId, productId, quantity;
		
		try {
			cliendId = Integer.parseInt(clientNr.getText());
			productId = Integer.parseInt(prodNr.getText());
			quantity = Integer.parseInt(quantNr.getText());
		}catch(NumberFormatException e) {
			error.setText("Invalid input!");
			return;
		}
		
		if(quantity <= 0) {
			error.setText("Negative quantity!");
			return;
		}
		Client c = null;
		Product p = null;
		try {
			c = cbl.findClientById(cliendId);
			p = pbl.findProductById(productId);
		}catch(NoSuchElementException e) {
			error.setText(e.getMessage());
			return;
		}
		if(!p.equals(null)) {
			if(p.getQuantity() < quantity) {
				error.setText("Under stock!");
				return;
			}else {
				nrOfOrder++;
				Orderr o = new Orderr(nrOfOrder, cliendId, productId, quantity);
				
				obl.insertOrder(o);
				
				StringBuilder fileName = new StringBuilder();
				fileName.append("order");
				fileName.append(nrOfOrder);
				fileName.append(".txt");
				
				PrintWriter writer = null;
				try{
					writer = new PrintWriter(fileName.toString(), "UTF-8");
				}catch(FileNotFoundException e) {
				}catch(UnsupportedEncodingException e) {
				}
				if(!writer.equals(null)) {
					writer.println("This is the order #"+nrOfOrder);
					writer.println("");
					writer.println(c+" bought ");
					writer.println(p);
					writer.println(o);
					writer.close();
				}
				
				p.setQuantity(p.getQuantity() - quantity);
				try {
					pbl.updateProduct(p);
				}catch(IllegalArgumentException e) {
					error.setText(e.getMessage());
					return ;
				}
				
			}
		}
	}
}
