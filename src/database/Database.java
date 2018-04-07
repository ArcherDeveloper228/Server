package database;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

/**
 * This class make connection with database mediateka
 * @author Nikita.Ustyushenko
 * @version 1.0
 * */
public class Database implements IConstDatabase {

	/** Property - connection */
	private Connection connection;
	
	/** Property - statement */
	private Statement statement;
	
	/** Property - prepared_statement */
	private PreparedStatement prepared_statement;
	
	/**
	 * Make new object Database
	 * */
	public Database() {
		
		this.statement = null;
		this.prepared_statement = null;
		
		try {
			this.connection = (Connection) DriverManager.getConnection(URL, CLIENT_NAME, CLIENT_PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/** 
	 * This method close connection with database mediateka
	 * */
	public final void closeConnection() {
		
		// ��������� ���������� � ����� ������ mediateka
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e1) {
			e1.printStackTrace();
		}	
		
	}
	
}