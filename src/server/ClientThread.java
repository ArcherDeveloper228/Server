package server;

import java.net.Socket;
import java.util.Map;

import database.Database;
import javafx.scene.image.Image;
import json.FileCommand;
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
		FileCommand file_command = null;

		// читаем сообщение от клиента
		user_command = this.server_interface.readMessage();

		// выполняем проверку на чтение сообщение от клиента
		if (user_command != null) {

			switch (user_command.getCommand()) {

			case "Registration": this.server_interface.writeMessage(this.database.registrationUser(user_command.getUser())); break;

			case "Authorization": this.server_interface.writeMessage(this.database.authorizationUser(user_command.getUser())); break;

			}

		} else {

			file_command = this.server_interface.readFile();

			if (file_command != null)

			switch (file_command.getCommand()) {

			case "AddImage":		this.server_interface.writeMessage(this.database.addFile(file_command.getFileBytes(),
										file_command.getFileName(), file_command.getUserLogin(), "Image"));
							 		break;

			case "DeleteImage": 	this.server_interface.writeMessage(this.database.deleteFile(file_command.getFileName(),
										file_command.getUserLogin(), "Image"));
									break;

			case "LoadImages":		this.server_interface.writeFile(this.database.loadImages(file_command.getUserLogin()));
									break;

			case "AddMusic":		this.server_interface.writeMessage(this.database.addFile(file_command.getFileBytes(),
										file_command.getFileName(), file_command.getUserLogin(), "Music"));
									break;

			}

		}

		System.out.println("End...");

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
