package server;

import json.Container;
import json.FileCommand;
import json.UserComand;

public interface ConstServer {

	boolean writeMessage(String message);
	UserComand readMessage();
	boolean writeFile(Container files);
	FileCommand readFile();

}
