package database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import application.User;

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
			System.out.println("Database running...");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public ResultSet getResult(String inquiry) {
		
		ResultSet result_set = null;
		
		try {
			
			this.statement = (Statement) this.connection.createStatement();
			result_set = this.statement.executeQuery(inquiry);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result_set;
		
	}
	
	
	/** 
	 * This method close connection with database mediateka
	 * */
	public final void closeConnection() {
		
		// закрываем соеденение с базой данных mediateka
		if (this.connection != null) {
			
			try {
				this.connection.close();
				System.out.println("Database ending...");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} else ;
		
	}
	
	/**
	 * This method add user in database
	 * @param user it is object contains information about user
	 * */
	public void addUser(User user) {

		try {

			this.prepared_statement = (PreparedStatement) this.connection.prepareStatement(SQL_INSERT_USER);

			this.prepared_statement.setString(1, user.getName());
			this.prepared_statement.setString(2, user.getSurname());
			this.prepared_statement.setInt(3, user.getDayOfBirth());
			this.prepared_statement.setString(4, user.getMonthOfBirth());
			this.prepared_statement.setInt(5, user.getYearOfBirth());
			this.prepared_statement.setString(6, user.getSex());
			this.prepared_statement.setString(7, user.getUserLogin());
			this.prepared_statement.setInt(8, user.getUserPassword());

			this.prepared_statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			// если объект PreparedStatement не null, то закрываем его
			if (this.prepared_statement != null) {

				try {
					this.prepared_statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

		}

	}
	
}
