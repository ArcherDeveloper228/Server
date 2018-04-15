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
	
	/** Property - output_stream */
	private PrintStream output_stream;

	/** Property - input_stream */
	private BufferedReader input_stream;
	
	/** Property - object_input_stream */
	private ObjectInputStream object_input_stream;

	/**
	 * Make new object ClientThread
	 * */
	public ClientThread() {
		
		this.database = null;
		this.output_stream = null;
		this.input_stream = null;
		this.object_input_stream = null;
		
	}
	
	/**
	 * Make new object ClientThread
	 * @param socket value of the socket client
	 * @param database value of the object Database
	 * */
	public ClientThread(Socket socket, Database database) {

		try {
			
			this.database = database;
			this.output_stream = new PrintStream(socket.getOutputStream());
			this.input_stream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.object_input_stream = (ObjectInputStream) socket.getInputStream();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		
		String string;
		try {
			while ((string = this.input_stream.readLine()) != null) {
				
				System.out.println(string);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	/**
	 * 
	 * */
	private boolean checkData(User user) {
		
		ResultSet result_set = null;
		
		try {
			
			result_set = this.database.getResult("SELECT * FROM user WHERE login='" + user.getUserLogin() + "'");
			if (result_set.)
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
		
	}
	
	public Database getDatabase() {
		
		return this.database;
		
	}
	
	public void setDatabase(Database database) {
		
		this.database = database;
		
	}
	
	public PrintStream getOutputStream() {
		
		return this.output_stream;
		
	}
	
	public void setOutputStream(PrintStream output_stream) {
		
		this.output_stream = output_stream;
		
	}
	
	public BufferedReader getInputStream() {
		
		return this.input_stream;
		
	}
	
	public void setInputStream(BufferedReader input_stream) {
		
		this.input_stream = input_stream;
	
	}
	
	public ObjectInputStream getObjectInputStream() {
		
		return this.object_input_stream;
		
	}
	
	public void setObjectInputStream(ObjectInputStream object_input_stream) {
		
		this.object_input_stream = object_input_stream;
		
	}

}
