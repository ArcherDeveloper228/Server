package server;

import java.net.Socket;

import database.Database;
import json.UserComand;

public class ClientThread extends Thread {

	/** Property - database */
	private Database database;

	/** Property - server_interface */
	private ServerInterface server_interface;

	/**
	 * Make new object ClientThread
	 * */
	public ClientThread() {

		this.database = null;
		this.server_interface = null;

	}

	/**
	 * Make new object ClientThread
	 * @param socket value of the socket client
	 * @param database value of the object Database
	 * */
	public ClientThread(Socket socket, Database database) {

		this.database = database;
		this.server_interface = new ServerInterface(socket);

	}

	@Override
	public void run() {

		System.out.println("Client is running...");
		UserComand user_command = null;
		
		while (!Thread.interrupted()) {

			user_command = this.server_interface.readMessage();
			
			switch (user_command.getCommand()) {
			
			case "Wait": System.out.println("Command: wait"); break;
			case "Registration": System.out.println("Command: registration"); break; 
			case "Authorization": break;
			
			}

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

}
