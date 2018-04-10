package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientThread extends Thread {
	
	/** Property - output_stream */
	PrintStream output_stream;
	
	/** Property - input_stream */
	BufferedReader input_stream;
	
	/** 
	 * Make new object ClientThread
	 * @param socket value of the socket client
	 * */
	public ClientThread(Socket socket) {
		
		try {
			
			this.output_stream = new PrintStream(socket.getOutputStream());
			this.input_stream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		} catch (IOException e) {
			e.printStackTrace();
		}	
	
	}
	
	@Override
	public void run() {
		
		
		
	}
	
}
