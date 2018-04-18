package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.User;
import database.Database;

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
