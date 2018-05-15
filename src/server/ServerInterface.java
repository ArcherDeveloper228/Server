package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;

import application.User;
import json.FileCommand;
import json.JsonParser;
import json.UserComand;

public class ServerInterface implements ConstServer {

	/** Property - socket */
	private Socket socket;

	/** Property - buffered_reader */
	private BufferedReader buffered_reader;

	/** Property - print_stream */
	private PrintStream print_stream;

	/** Property - json_parser */
	private JsonParser json_parser;

	/**
	 * Make new object ServerInterface
	 * */
	public ServerInterface() {

		this.socket = null;
		this.buffered_reader = null;
		this.print_stream = null;
		this.json_parser = null;

	}

	/**
	 * Make new object ServerInterface
	 * @param socket value of the object Socket
	 * */
	public ServerInterface(Socket socket) {

		try {

			this.socket = socket;
			this.print_stream = new PrintStream(this.socket.getOutputStream());
			this.print_stream.flush();
			this.buffered_reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			this.json_parser = new JsonParser();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// реализация метода чтения информации из потока от клиента
	@Override
	public UserComand readMessage() {

		UserComand user_command = null;
		String json = null;

		try {

			if ((json = this.buffered_reader.readLine()) != null)
				if (json.equals("User")) {

					json =  this.buffered_reader.readLine();
					user_command = this.json_parser.parseUserJson(json);

				}


		} catch (IOException e) {
			e.printStackTrace();
		}

		return user_command;

	}

	// реализация метода отправки сообщения клиенту
	@Override
	public boolean writeMessage(String message) {

		if (this.print_stream == null) return false;
		else {

			this.print_stream.println(this.json_parser.makeUserJson(new User(), message));
			this.print_stream.flush();
			return true;

		}

	}

	// реализация метода получения данных от клиента
	@Override
	public FileCommand readFile() {

		FileCommand file_command = null;
		String json = null;

		try {

			if ((json = this.buffered_reader.readLine()) != null)
				file_command = this.json_parser.parseFileJson(json);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return file_command;

	}

	// реализация метода передачи файлов клиенту
	@Override
	public boolean writeFile(Map files) {

		if (this.print_stream == null) return false;
		else {

			this.print_stream.println(this.json_parser.getGson().toJson(files));
			this.print_stream.flush();
			return true;

		}

	}

	/**
	 * This method close connection with all threads
	 * */
	public void closeConnection() {

		// если поток чтения не null, то закрываем его
		if (this.buffered_reader != null) {

			try {
				this.buffered_reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		// если поток записи не null, то закрываем его
		if (this.print_stream != null) {

			this.print_stream.flush();
			this.print_stream.close();

		}

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

	/**
	 * This method return value of the object PrintStream
	 * @return value of the object PrintStream
	 * */
	public PrintStream getPrintStream() {

		return this.print_stream;

	}

	/**
	 * This method set value of the object PrintStream
	 * @param print_stream value of the object PrintStream
	 * */
	public void setPrintStream(PrintStream print_stream) {

		this.print_stream = print_stream;

	}

	/**
	 * This method return value of the object BufferedReader
	 * @return value of the object BufferedReader
	 * */
	public BufferedReader getBufferedReader() {

		return this.buffered_reader;

	}

	/**
	 * This method set value of the object BufferedReader
	 * @param buffered_reader
	 * */
	public void setBufferedReader(BufferedReader buffered_reader) {

		this.buffered_reader = buffered_reader;

	}

	/**
	 * This method get value of the object JsonParser
	 * @return value of the object JsonParser
	 * */
	public JsonParser getJsonParser() {

		return this.json_parser;

	}

	/**
	 * This method set value of the object JsonParser
	 * @param json_parser value of the object JsonParser
	 * */
	public void setJsonParser(JsonParser json_parser) {

		this.json_parser = json_parser;

	}

}
