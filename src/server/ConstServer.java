package server;

import json.FileCommand;
import json.UserComand;

public interface ConstServer {

	boolean writeMessage(String message);
	UserComand readMessage();
	//boolean writeFile(String file);
	FileCommand readFile();

}
