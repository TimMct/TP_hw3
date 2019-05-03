package hw3.hw3.reflection;

import java.lang.reflect.Field;
import java.util.List;
import javax.swing.JTable;


//am folosit cod de pe BitBucket, utcn_dsrl
/**
 * Clasa in care este implementata metoda JTable createTable(List<T> objects)
 * care se foloseste de reflection techniques
 * @author Timotei
 *
 */
public class Reflection {
	/**
	 * Metoda ce returneaza numarul de atribute al unui obiect
	 * @param o obiectul de analizat
	 * @return numarul de coloane(atribute)
	 */
	public static int nrOfCols(Object o) {
		return o.getClass().getDeclaredFields().length;
	}
	
	/**
	 * Metoda care returneaza numele coloanelor pentru un obiect
	 * @param o obiectul de analizat
	 * @return tablou cu toate numele coloanelor
	 */
	public static String[] retrieveColumns(Object o){
		int nrOfCols = Reflection.nrOfCols(o);
		String[] colNames = new String[nrOfCols];
		int i = 0;
		for (Field field : o.getClass().getDeclaredFields()) {
			field.setAccessible(true); 
			colNames[i] = field.getName();
			i++;
		}
		return colNames;
	}
	
	/**
	 * Metoda care returneaza valorile continute de atributele unui obiect
	 * @param o obiectul de analizat
	 * @return tablou de Object cu valorile atributelor
	 */
	public static Object[] retrieveValues(Object o) {

		int nrOfCols = Reflection.nrOfCols(o);
		Object[] values = new Object[nrOfCols];
		int i = 0;
		for (Field field : o.getClass().getDeclaredFields()) {
			field.setAccessible(true); 
			Object value;
			try {
				value = field.get(o);
				values[i] = value;
			} catch (IllegalArgumentException e) {
				System.out.println("Exception "+e.getMessage());
			} catch (IllegalAccessException e) {
				System.out.println("Exception "+e.getMessage());
			}
			i++;
		}
		return values;
	}
	
	/**
	 * Metoda de baza a acestei clase care creaza un tabel in functie de lista de obiecte pe care o primeste 
	 * @param objects lista de obiecte care trebuie inmagazinate in tabel
	 * @return tabelul rezultat
	 */
	@SuppressWarnings("serial")
	public static <T> JTable createTable(List<T> objects) {
		JTable table = null;
		if(objects.size() == 0)
			return table;
		String[] colNames = Reflection.retrieveColumns(objects.get(0));
		int nrOfRows = objects.size();
		int nrOfCols = Reflection.nrOfCols(objects.get(0));
		Object[][] values = new Object[nrOfRows][nrOfCols];
		int i = 0;
		for(Object o : objects) {
			values[i] =  Reflection.retrieveValues(o);
			i++;
		}
		table = new JTable(values, colNames) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setRowHeight(50);
		
		return table;
	}
}
