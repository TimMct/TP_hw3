package hw3.hw3.presentation;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import hw3.hw3.businessLayer.ClientBLL;
import hw3.hw3.businessLayer.ProductBLL;
import hw3.hw3.model.Client;
import hw3.hw3.model.Product;
import hw3.hw3.reflection.Reflection;
/**
 * Ascultator folosit pentru butonul de realizare a operatiilor cu Client si Product
 * @author Timotei
 *
 */
public class QueryListener implements ActionListener{

	private JLabel error;
	private JComboBox<String> operation;
	private JComboBox<String> operand;
	
	private JTextField[] leftValues;
	private JTextField[] rightValues;
	
	/**
	 * Constructorul foloseste date din interfata
	 * @param eroare
	 * @param operatia
	 * @param operand
	 * @param lVal 
	 * @param rVal
	 */
	public QueryListener(JLabel e, JComboBox<String> oper, JComboBox<String> o, JTextField[] lVal, JTextField[] rVal) {
		error = e;
		operation = oper;
		operand = o;
		leftValues = lVal;
		rightValues = rVal;
	}
	
	/**
	 * Metoda apelata la apasarea butonului de operatii
	 * si crearea unei ferestre care contine un JTable in cazul operatiei de View all
	 */
	public void actionPerformed(ActionEvent event) {
		ClientBLL cbl = new ClientBLL();
		ProductBLL pbl = new ProductBLL();
		error.setText("");
			if(operation.getSelectedItem().equals("Add")) {
				
				if(operand.getSelectedItem().equals("Client")) {
					int age;
					String name, address, email;
					try {
						name = leftValues[1].getText();
						address = leftValues[2].getText();
						email = leftValues[3].getText();
						age = Integer.parseInt(leftValues[4].getText());
					}catch(NumberFormatException e) {
						error.setText("invalid input");
						return ;
					}
					try {
						cbl.insertClient(new Client(name, address, email, age));
					}catch(IllegalArgumentException e) {
						error.setText(e.getMessage());
					}
				}else if(operand.getSelectedItem().equals("Product")) {
					int quantity;
					double price;
					String name;
					try {
						name = rightValues[1].getText();
						price = Double.parseDouble(rightValues[2].getText());
						quantity = Integer.parseInt(rightValues[3].getText());
					}catch(NumberFormatException e) {
						error.setText("invalid input");
						return ;
					}
					try {
						pbl.insertProduct(new Product(name, price, quantity));
					}catch(IllegalArgumentException e) {
						error.setText(e.getMessage());
					}
				}
				
				
			}else if(operation.getSelectedItem().equals("Edit")) {
				
				if(operand.getSelectedItem().equals("Client")) {
					int id, age;
					String name, address, email;
					try {
						id = Integer.parseInt(leftValues[0].getText());
						name = leftValues[1].getText();
						address = leftValues[2].getText();
						email = leftValues[3].getText();
						age = Integer.parseInt(leftValues[4].getText());
					}catch(NumberFormatException e) {
						error.setText("invalid input");
						return ;
					}
					try {
						cbl.updateClient(new Client(id, name, address, email, age));
					}catch(IllegalArgumentException e) {
						error.setText(e.getMessage());
					}catch(NoSuchElementException e) {
						error.setText(e.getMessage());
					}
				}else if(operand.getSelectedItem().equals("Product")) {
					int id, quantity;
					String name;
					double price;
					try {
						id = Integer.parseInt(rightValues[0].getText());
						name = rightValues[1].getText();
						price = Double.parseDouble(rightValues[2].getText());
						quantity = Integer.parseInt(rightValues[3].getText());
					}catch(NumberFormatException e) {
						error.setText("invalid input");
						return ;
					}
					try {
						pbl.updateProduct(new Product(id, name, price, quantity));
					}catch(IllegalArgumentException e) {
						error.setText(e.getMessage());
					}catch(NoSuchElementException e) {
						error.setText(e.getMessage());
					}
				}
				
				
				
			}else if(operation.getSelectedItem().equals("Delete")) {
				
				if(operand.getSelectedItem().equals("Client")) {
					int id;
					try {
						id = Integer.parseInt(leftValues[0].getText());
					}catch(NumberFormatException e) {
						error.setText("invalid input");
						return ;
					}
					try {
						cbl.deleteClient(id);
					}catch(NoSuchElementException e) {
						error.setText(e.getMessage());
					}
				}else if(operand.getSelectedItem().equals("Product")) {
					int id;
					try {
						id = Integer.parseInt(rightValues[0].getText());
					}catch(NumberFormatException e) {
						error.setText("invalid input");
						return ;
					}
					try {
						pbl.deleteProduct(id);
					}catch(NoSuchElementException e) {
						error.setText(e.getMessage());
					}
				}
				
				
				
			}else if(operation.getSelectedItem().equals("View all")) {
				
				if(operand.getSelectedItem().equals("Client")) {
					JFrame tableWindow = new JFrame("Table of Clients");
					tableWindow.setSize(800, 400);
					tableWindow.setLocationRelativeTo(null);
					tableWindow.setLayout(new BorderLayout());
					
					ArrayList<Client> objs = new ArrayList<Client>();
					try {
						objs = cbl.findAllClients();
					}catch(NoSuchElementException e) {
						error.setText(e.getMessage());
						objs.add(new Client());
					}
					
					JTable table = Reflection.createTable(objs);
					tableWindow.add(new JScrollPane(table), BorderLayout.CENTER);
					tableWindow.setVisible(true);
				}else if(operand.getSelectedItem().equals("Product")) {
					JFrame tableWindow = new JFrame("Table of Products");
					tableWindow.setSize(800, 400);
					tableWindow.setLocationRelativeTo(null);
					tableWindow.setLayout(new BorderLayout());
					
					ArrayList<Product> objs = new ArrayList<Product>();
					try {
						objs = pbl.findAllProducts();
					}catch(NoSuchElementException e) {
						error.setText(e.getMessage());
						objs.add(new Product());
					}
					
					JTable table = Reflection.createTable(objs);
					tableWindow.add(new JScrollPane(table), BorderLayout.CENTER);
					tableWindow.setVisible(true);
				}
			
	}
	}

}
