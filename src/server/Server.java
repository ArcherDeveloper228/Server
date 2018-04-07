package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

	private ServerSocket server_socket;
	private Socket socket;

	/**
	 * Make new object Server
	 * */
	private Server() {

		try {
			this.server_socket = new ServerSocket(9999);
		} catch (IOException e) {
			e.printStackTrace();
		}



	}

	@Override
	public void run() {



	}

	public ServerSocket getServerSocket() {

		return this.server_socket;

	}

}
