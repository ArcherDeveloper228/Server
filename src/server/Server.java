package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import database.Database;

/**
 * This class make server and contains information about server
 * @author Nikita.Ustyushenko
 * @version 1.0
 * */
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
		this.socket = null;

		// выполняем создание сервер сокета с портом 9999
		try {
			this.server_socket = new ServerSocket(9999);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {

		// запускаем работу сервера
		while (!Thread.interrupted()) {

			try {

				System.out.println("Server running...");

				// ожидаем подключения клиента
				this.socket = this.server_socket.accept();
				// наращиваем количество подключенных клиентов к серверу
				this.count_clients++;
				// запускаем соеденение с клиентом в отдельном потоке
				new ClientThread(this.socket, this.database).start();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * This method close connection with server
	 * */
	public void closeConnection() {

		super.interrupt();
		this.database.closeConnection();
		System.out.println("Server ending...");

	}

	/**
	 * This method return object ServerSocket
	 * @return value of the ServerSocket
	 * */
	public ServerSocket getServerSocket() {

		return this.server_socket;

	}

	/**
	 * This method set value of the property server_socket
	 * @param server_socket value of the object ServerSocket
	 * */
	public void setServerSocket(ServerSocket server_socket) {

		this.server_socket = server_socket;

	}

	/**
	 * This method return count of the clients in server
	 * @return value of the count clients
	 * */
	public int getCountClients() {

		return this.count_clients;

	}

	/**
	 * This method set value of the count clients
	 * @param count_clients value of the count clients
	 * */
	public void setConutClients(int count_clients) {

		this.count_clients = count_clients;

	}

	/**
	 * This method return value of the object Socket
	 * @return value of the socket
	 * */
	public Socket getSocket() {

		return this.socket;

	}

	/**
	 * This method set value of the socket
	 * @param socket value of the socket
	 * */
	public void setSocket(Socket socket) {

		this.socket = socket;

	}

	/**
	 * This method return object Database
	 * @return object Database
	 * */
	public Database getDatabase() {

		return this.database;

	}

	/**
	 * This method set value of the object Database
	 * @param database value of the object Database
	 * */
	public void setDatabase(Database database) {

		this.database = database;

	}

}
