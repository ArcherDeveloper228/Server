package server;

import java.net.Socket;

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
			
		System.out.println("я что-то прочитал");
			
		switch (user_command.getCommand()) {

		case "Registration": System.out.println(user_command.getUser()); break;
		case "Authorization": System.out.println("Command"); break;
			
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
