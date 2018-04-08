package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import database.Database;

public class Server extends Thread {

	/** Property - database */
	private Database database;
	
	/** Property - server_socket */
	private ServerSocket server_socket;
	
	/** Property - socket */
	private Socket socket;
	
	/** Property - count_clients */
	private int count_clients;

	/**
	 * Make new object Server
	 * */
	public Server() {

		this.count_clients = 0;
		this.database = new Database();
		
		// выполняем создание сервер сокета с портом 9999
		try {
			this.server_socket = new ServerSocket(9999);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {

		while (true) {
			
			try {
				
				// ожидаем подключения клиента
				this.socket = this.server_socket.accept();
				
				// наращиваем количество подключенных клиентов к серверу
				this.count_clients++;
				new ClientThread(this.socket).start();
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

	}

	public ServerSocket getServerSocket() {

		return this.server_socket;

	}
	
	public void setServerSocket(ServerSocket server_socket) {
		
		this.server_socket = server_socket;
		
	}
	
	public int getCountClients() {
		
		return this.count_clients;
		
	}
	
	public void setConutClients(int count_clients) {
		
		this.count_clients = count_clients;
		
	}
	
	public Socket getSocket() {}

}
