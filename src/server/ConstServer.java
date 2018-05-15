package server;

import java.util.Map;

import json.FileCommand;
import json.UserComand;

public interface ConstServer {

	boolean writeMessage(String message);
	UserComand readMessage();
	boolean writeFile(Map files);
	FileCommand readFile();

}
