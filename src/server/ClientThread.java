package server;

import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.User;
import database.Database;
import json.UserComand;

public class ClientThread extends Thread {

	/** Property - socket */
	private Socket socket;
	
	/** Property - database */
	private Database database;

	/** Property - server_interface */
	private ServerInterface server_interface;

	/**
	 * Make new object ClientThread
	 * */
	public ClientThread() {

		this.socket = null;
		this.database = null;
		this.server_interface = null;

	}

	/**
	 * Make new object ClientThread
	 * @param socket value of the socket client
	 * @param database value of the object Database
	 * */
	public ClientThread(Socket socket, Database database) {

		this.socket = socket;
		this.database = database;
		this.server_interface = new ServerInterface(socket);

	}

	@Override
	public void run() {

		System.out.println("Client is running...");
		UserComand user_command = null;
			
		user_command = this.server_interface.readMessage();
			
		switch (user_command.getCommand()) {

		case "Registration": this.registrationUser(user_command.getUser()); break;
		case "Authorization": this.authorizationUser(user_command.getUser()); break;
			
		}

	}
	
	/**
	 * This method check user in database 
	 * @param user value of the object User
	 * */
	private final void authorizationUser(User user) {
		
		String attention = new String("Check your login (password)!");
		ResultSet result_set = null;
		
		try {
			
			result_set = this.database.getResult("SELECT * FROM user WHERE login=\"" + user.getUserLogin() + "\"");
			
			if (!result_set.next()) this.server_interface.writeMessage(attention);
			else if (result_set.getInt(9) == user.getUserPassword()) this.server_interface.writeMessage("Ok");
			else this.server_interface.writeMessage(attention);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This method add user into database
	 * @param user value of the object User
	 * */
	private final void registrationUser(User user) {
		
		try {
			
			if (!this.database.getResult("SELECT * FROM USER WHERE login=\"" + user.getUserLogin() + "\"").next()) {
				
				this.database.addUser(user);
				this.server_interface.writeMessage("Ok");
				
			} else this.server_interface.writeMessage("This login is already exists!");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * This method get value of the object Database
	 * @return value of the object database
	 * */
	public Database getDatabase() {

		return this.database;

	}

	/**
	 * This method set value of the object Database
	 * @param database value of the object database
	 * */
	public void setDatabase(Database database) {

		this.database = database;

	}

	/**
	 * This method return value of the object ServerInterface
	 * @return value of the object server_interface
	 * */
	public ServerInterface getServerInterface() {

		return this.server_interface;

	}

	/**
	 * This method set value of the object ServerInterface
	 * @param server_interface value of the object ServerInterface
	 * */
	public void setServerInterface(ServerInterface server_interface) {

		this.server_interface = server_interface;

	}
	
	/**
	 * This method return value of the object Socket
	 * @return value of the object Socket
	 * */
	public Socket getSocket() {
		
		return this.socket;
		
	}
	
	/**
	 * This method set value of the object Socket 
	 * @param socket value o the object Socket
	 * */
	public void setSocket(Socket socket) {
		
		this.socket = socket;
		
	} 

}
