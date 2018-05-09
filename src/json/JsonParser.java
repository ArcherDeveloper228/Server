package json;

import java.io.File;

import com.google.gson.Gson;

import application.User;

/**
 * This class make and parse Gson objects
 * @author Nikita.Ustyushenko
 * @version 1.0
 * */
public class JsonParser implements IParseUserInterface {

	/** Property - gson */
	private final Gson gson;

	/**
	 * Make new object JsonParser
	 * */
	public JsonParser() {

		this.gson = new Gson();

	}

	// ���������� ������ �������� json ������ ������� UserComand
	@Override
	public String makeUserJson(User user, String command) {

		return new String(this.gson.toJson(new UserComand(user, command)));

	}

	// ���������� ������ �������� json ������ � ������ UserComand
	@Override
	public UserComand parseUserJson(String user_json) {

		 return this.gson.fromJson(user_json, UserComand.class);

	}

	// ���������� ������ �������� json ������ ������� FileCommand
	@Override
	public String makeFileJson(byte[] bytes, String file_name, String user_login, String command) {

		return new String(this.gson.toJson(new FileCommand(bytes, file_name, user_login, command)));

	}

	// ���������� ������ �������� json ������ � ������ FileCommand
	@Override
	public FileCommand parseFileJson(String file_json) {

		return this.gson.fromJson(file_json, FileCommand.class);

	}

	/**
	 * This method return value of the object Gson
	 * @return value of the object Gson
	 * */
	public Gson getGson() {

		return this.gson;

	}

}
