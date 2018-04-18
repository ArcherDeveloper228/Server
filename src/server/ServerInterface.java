package server;

import java.net.Socket;

public class ServerInterface {

	/** Property - socket */
	private Socket socket;

	/**
	 * Make new object ServerInterface
	 * */
	public ServerInterface() {
		
		this.socket = null;
		
	}
	
	/**
	 * Make new object ServerInterface
	 * @param socket value of the object Socket
	 * */
	public ServerInterface(Socket socket) {

		this.socket = socket;

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
	 * @param socket value of the object Socket
	 * */
	public void setSocket(Socket socket) {
		
		this.socket = socket;
		
	} 

}
