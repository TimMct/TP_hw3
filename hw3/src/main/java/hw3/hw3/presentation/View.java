package hw3.hw3.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import hw3.hw3.model.Client;
import hw3.hw3.model.Product;
import hw3.hw3.reflection.Reflection;
/**
 * Interfata grafica de baza
 * @author Timotei
 *
 */
public class View{
	
	private JFrame appWindow;
	
	private JPanel op1;
	private JPanel op2;
	private JPanel op3;
	private JPanel op4;
	
	private JLabel header1;
	private JLabel header2;
	private JPanel[] panels;
	private JLabel[] cols;
	private JTextField[] leftValues;
	private JTextField[] rightValues;
	

	private JComboBox<String> operand;
	private JComboBox<String> operation;
	
	private JButton executeQuery;
	private JButton executeOper;
	
	private JLabel client;
	private JLabel product;
	private JLabel quantity;
	private JTextField clientNr;
	private JTextField prodNr;
	private JTextField quantNr;
	
	private JLabel error1;
	private JLabel error2;
	/**
	 * In acest constructor se initializeaza toate componentele interfetei
	 */
	public View() {
		
		appWindow = new JFrame("Warehouse");
		appWindow.setSize(900, 500);
		appWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		appWindow.setLocationRelativeTo(null);
		appWindow.setLayout(new BorderLayout());
		
		op1 = new JPanel();
		op1.setLayout(new FlowLayout());
		//op1.setBackground(Color.CYAN);
		
		operation = new JComboBox<String>();
		operation.addItem("Add");
		operation.addItem("Edit");
		operation.addItem("Delete");
		operation.addItem("View all");
		op1.add(operation);
		
		
		operand = new JComboBox<String>();
		operand.addItem("Client");
		operand.addItem("Product");
		op1.add(operand);
		
		executeQuery = new JButton("Execute query");
		op1.add(executeQuery);
		
		error1 = new JLabel();
		op1.add(error1);
		
		appWindow.add(op1, BorderLayout.PAGE_START);
		
		String[] colNames = Reflection.retrieveColumns(new Client());
		int size = colNames.length;
		op2 = new JPanel();
		op2.setLayout(new GridLayout(size + 1, 1));
		op2.setBackground(Color.CYAN);
		
		
		header1 = new JLabel("Client columns >>");
		op2.add(header1);
		
		panels = new JPanel[size];
		cols = new JLabel[size];
		leftValues = new JTextField[size];
		for(int i = 0; i < size; i++) {
			panels[i] = new JPanel();
			panels[i].setLayout(new FlowLayout());
			panels[i].setBackground(Color.CYAN);
			cols[i] = new JLabel(colNames[i]);
			leftValues[i] = new JTextField(20);
			panels[i].add(cols[i]);
			panels[i].add(leftValues[i]);
			op2.add(panels[i]);
		}
		appWindow.add(op2, BorderLayout.WEST);
		
		colNames = Reflection.retrieveColumns(new Product());
		size = colNames.length;
		op3 = new JPanel();
		op3.setLayout(new GridLayout(size + 1, 1));
		op3.setBackground(Color.CYAN);
		

		header2 = new JLabel("Product columns >>");
		op3.add(header2);
		
		
		panels = new JPanel[size];
		cols = new JLabel[size];
		rightValues = new JTextField[size];
		for(int i = 0; i < size; i++) {
			panels[i] = new JPanel();
			panels[i].setLayout(new FlowLayout());
			panels[i].setBackground(Color.CYAN);
			cols[i] = new JLabel(colNames[i]);
			rightValues[i] = new JTextField(20);
			panels[i].add(cols[i]);
			panels[i].add(rightValues[i]);
			op3.add(panels[i]);
		}
		appWindow.add(op3, BorderLayout.EAST);
		
		
		
		executeQuery.addActionListener(new QueryListener(error1, operation, operand, leftValues, rightValues));
		
		
		
		
		op4 = new JPanel();
		op4.setLayout(new FlowLayout());
		op4.setBackground(Color.CYAN);
		
		client = new JLabel("Client #");
		op4.add(client);
		clientNr = new JTextField(5);
		op4.add(clientNr);
		product = new JLabel(" buys product #");
		op4.add(product);
		prodNr = new JTextField(5);
		op4.add(prodNr);
		quantity = new JLabel(" with quantity ");
		op4.add(quantity);
		quantNr = new JTextField(5);
		op4.add(quantNr);
		executeOper = new JButton("Execute operation");
		op4.add(executeOper);
		
		error2 = new JLabel();
		op4.add(error2);
		
		executeOper.addActionListener(new OrderListener(error2,  clientNr, prodNr, quantNr));
		
		appWindow.add(op4, BorderLayout.PAGE_END);
		
		appWindow.setVisible(true);
	}
}
