package hw3.hw3.dao;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



import hw3.hw3.dataAccessLayer.DBConnection;
/**
 * Data access object generic(de tip T) dolosit pentru accesarea obiectelor din baza de date
 * @author Timotei
 *
 * @param <T> tip generic Client, Product, Orderr
 */
public class AbstractDAO<T> {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
	private final Class<T> type;
	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		ParameterizedType genSup = (ParameterizedType) getClass().getGenericSuperclass();
		type = (Class<T>) genSup.getActualTypeArguments()[0];			
	}
	/**
	 * Metoda folosita pentru a crea interogarea care selecteaza toate obiectele din baza de date	
	 * @return interogarea formata
	 */
	private String createSelectAllQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		return sb.toString();
	}
	/**
	 * Metoda care efectueaza interogarea de selectare a tuturor obiectelor din table
	 * @return o lista cu toate obiectele existente
	 */
	public ArrayList<T> selectAll() {
		ArrayList<T> result = new ArrayList<T>();
		Connection con = DBConnection.getConnection();
		PreparedStatement state = null;
		ResultSet rs = null;
		String query = createSelectAllQuery();
		try {
			state = con.prepareStatement(query);
			rs = state.executeQuery();
			result = createResult(rs);
		}catch(SQLException e) {
			LOGGER.log(Level.WARNING,  type.getName() + "   DAO:selectAll " + e.getMessage());
		}finally {
			DBConnection.close(rs);
			DBConnection.close(state);
			DBConnection.close(con);
		}
		return result;
	}
	
	/**
	 * Metoda pentru crearea de interogare de selectare a unui obiect in functie de id
	 * @return interogarea de select
	 */
	private String createSelectByIdQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE id = ?");
		return sb.toString();
	}
	
	/**
	 * Metoda care realizeaza operatia de selectare a unui obiect dupa id
	 * @param id obiectului care se doreste a se afla
	 * @return obiectul de tip generic T reprezentand rezultatul operatiei de selectare dupa id
	 */
	public T selectById(int id) {
		T result = null;
		Connection con = DBConnection.getConnection();
		PreparedStatement state = null;
		ResultSet rs = null;
		String query = createSelectByIdQuery();
		try {
			state = con.prepareStatement(query);
			state.setInt(1, id);
			rs = state.executeQuery();
			ArrayList<T> aux = createResult(rs);
			if(aux.size() > 0)
				result = aux.get(0);
		}catch(SQLException e) {
			LOGGER.log(Level.WARNING,  type.getName() + "   DAO:selectById " + e.getMessage());
		}finally {
			DBConnection.close(rs);
			DBConnection.close(state);
			DBConnection.close(con);
		}
		return result;
	}
	/**
	 * Metoda care converteste rezultatul in lista de obiecte 
	 * @param rs multimea de rezultate ale operatiilor de dinainte
	 * @return lista cu obiectele din rezultat
	 */
	private ArrayList<T> createResult(ResultSet rs){
		ArrayList<T> list = new ArrayList<T>();
		try {
			while(rs.next()) {
				T instance = type.getDeclaredConstructor().newInstance();
				for(Field f: type.getDeclaredFields()) {
					Object value = rs.getObject(f.getName());
					PropertyDescriptor pd = new PropertyDescriptor(f.getName(), type);
					Method m = pd.getWriteMethod();
					m.invoke(instance, value);
				}
				list.add(instance);
			}
		}catch(Exception e) {
			System.out.println("here "+e.getMessage());
		}
		return list;
	}
	
	/**
	 * Metoda de crearea a interogarii de insert 
	 * @return interogarea de insert
	 */
	private String createInsertQuery() {
		int size, i;
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(type.getSimpleName());
		sb.append(" (");
		i = 0;
		size = type.getDeclaredFields().length;
		for(Field f : type.getDeclaredFields()) {
			if(!f.getName().equals("id"))
				sb.append(f.getName());
			if(i > 0 && i < size - 1)
				sb.append(", ");
			i++;
		}
		sb.append(") VALUES (");
		for(i = 0; i < size - 1; i++) {
			sb.append("?");
			if(i < size - 2)
				sb.append(", ");
		}
		sb.append(")");
		return sb.toString();
	}
	
	/**
	 * Metoda folosita pentru a efectua operatia de inserare a unui nou obiect de tip T
	 * @param t obiectul care trebuie introdus
	 */
	public void insert(T t) {
		int i = 1;
		Connection con = DBConnection.getConnection();
		PreparedStatement state = null;
		String query = createInsertQuery();
		try {
			state = con.prepareStatement(query);
			
			for(Field f : t.getClass().getDeclaredFields()) {
				if(!f.getName().equals("id")) {
					PropertyDescriptor pd = new PropertyDescriptor(f.getName(), t.getClass());
					Method m = pd.getReadMethod();
					Object value = m.invoke(t);
					state.setObject(i, value);
					i++;
				}
			}
			state.executeUpdate();
		}catch(Exception e){
			LOGGER.log(Level.WARNING,  type.getName() + "   DAO:insert " + e.getMessage());
		}finally {
			DBConnection.close(state);
			DBConnection.close(con);
		}
	}
	
	/**
	 * metoda care creaza interogarea de update
	 * @return interogarea respectiva
	 */
	private String createUpdateQuery() {
		int i = 0, size;
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(type.getSimpleName());
		sb.append(" SET ");
		size = type.getDeclaredFields().length;
		for(Field f : type.getDeclaredFields()) {
			if(!f.getName().equals("id")) {
				sb.append(f.getName());
				sb.append(" = ?");
			}
			if(i > 0 && i < size - 1)
				sb.append(", ");
			i++;
		}
		sb.append(" WHERE id = ?");
		return sb.toString();
	}
	
	/**
	 * Metoda folosita pentru realozarea operatiei de update in functie de un obiect
	 * @param obiectul in functie de a cui id se va face modificarea
	 */
	public void update(T t) {
		int i = 1;
		Connection con = DBConnection.getConnection();
		PreparedStatement state = null;
		String query = createUpdateQuery();
		try {
			state = con.prepareStatement(query);
			Object id = null;
			for(Field f : t.getClass().getDeclaredFields()) {
				PropertyDescriptor pd = new PropertyDescriptor(f.getName(), t.getClass());
				Method m = pd.getReadMethod();
				Object value = m.invoke(t);
				if(!f.getName().equals("id")) {
					state.setObject(i, value);
					i++;
				}else
					id = value;
			}
			state.setObject(i, id);
			state.executeUpdate();
		}catch(Exception e){
			LOGGER.log(Level.WARNING,  type.getName() + "   DAO:update " + e.getMessage());
		}finally {
			DBConnection.close(state);
			DBConnection.close(con);
		}
	}
	
	/**
	 * Metoda care returneaza interogarea de stergere
	 * @return interogarea delete
	 */
	private String createDeleteQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE id = ?");
		return sb.toString();
	}
	
	/**
	 * Metoda care realizeaza operatia de stergere
	 * @param id in functie de acesta se va face stergerea 
	 */
	public void delete(int id) {
		Connection con = DBConnection.getConnection();
		PreparedStatement state = null;
		String query = createDeleteQuery();
		try {
			state = con.prepareStatement(query);
			state.setInt(1, id);
			state.executeUpdate();
		}catch(SQLException e) {
			LOGGER.log(Level.WARNING,  type.getName() + "   DAO:delete " + e.getMessage());
		}finally {
			DBConnection.close(state);
			DBConnection.close(con);
		}
	}
}
