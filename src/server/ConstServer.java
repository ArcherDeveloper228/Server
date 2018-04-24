package server;

import json.UserComand;

public interface ConstServer {

	boolean writeMessage(String message);
	UserComand readMessage();

}
