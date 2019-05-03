package hw3.hw3.dataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Obiect folosit pentru conectarea la baza de date
 * design pattern singleton 
 *  
 * @author Timotei
 *
 */
public class DBConnection {
	private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DBURL = "jdbc:mysql://127.0.0.1:3306/warehouse";
	private static final String USER = "root";
	private static final String PASS = "root";
	
	private static DBConnection singleInstance = new DBConnection();
	private DBConnection() {
		try {
			Class.forName(DRIVER);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Metoda principala de conectare la baza de date
	 * @return conexiunea propriu-zisa
	 */
	private Connection createConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(DBURL, USER, PASS);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "An error occured while trying to connect to the database");
			e.printStackTrace();
		}
		return connection;
	}
	/**
	 * Metoda de accesare a conexiunii, care este de fapt una singura (singleton)
	 * @return conexiunea la baza de date
	 */
	public static Connection getConnection() {
		return singleInstance.createConnection();
	}
	/**
	 * Metoda de inchidere a conexiunii
	 * @param conn de inchis
	 */
	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "An error occured while trying to close the Connection");
			}
		}
	}
	/**
	 * Metoda de inchidere a interogarii
	 * @param state de inchis
	 */
	public static void close(Statement state) {
		if (state != null) {
			try {
				state.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "An error occured while trying to close the Statement");
			}
		}
	}
	/**
	 * metoda de inchidere a multimii de rezultate
	 * @param rs de inchis
	 */
	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
			}
		}
	}
	
	
}
