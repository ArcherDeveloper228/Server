package database;

/**
 * Singleton class what make file paths
 * @author Nikita.Ustyushenko
 * @version 1.0
 * */
public final class PathResolver implements IConstDatabase {

	/** Property - instance */
	private static PathResolver instance = null;

	/** Property - user_login */
	private String user_login;

	/**
	 * Make new object PathResolver
	 * */
	private PathResolver() {

		this.user_login = null;

	}

	/**
	 * Make new object PathResolver
	 * @param user_login value of the object String
	 * what contains information about user login
	 * */
	private PathResolver(String user_login) {

		this.user_login = user_login;

	}

	/**
	 * This method create Singleton class PathResolver
	 * @param user_login value of the object String
	 * what contains information about user login
	 * @return value of the object PathResolver
	 * */
	public static PathResolver getInstance() {

		if (instance == null) instance = new PathResolver();
		return instance;

	}

	/**
	 * This method return database path
	 * @return value of the object String
	 * what contains database path
	 * */
	public String getDatabasePath() {

		return new String(CURRENT_DIRECTORY + "\\" + this.user_login);

	}

	/**
	 * This method return images path
	 * @return value of the object String
	 * what contains information about images path
	 * */
	public String getImagesPath() {

		return new String(CURRENT_DIRECTORY + "\\" + this.user_login + "\\images\\");

	}

	/**
	 * This method return music path
	 * @return value of the object String
	 * what contains information about music path
	 * */
	public String getMusicPath() {

		return new String(CURRENT_DIRECTORY + "\\" + this.user_login + "\\music\\");

	}

	/**
	 * This method return file image path
	 * @param file_name value of the object String what contains information about file name
	 * @return value of the object String what contains information about file image path
	 * */
	public String getFileImagePath(String file_name) {

		return new String(CURRENT_DIRECTORY + "\\" + this.user_login + "\\images\\" + file_name);

	}

	/**
	 * This method return file music path
	 * @param file_name value of the object String what contains information about file name
	 * @return value of the object String what contains information about file music path
	 * */
	public String getFileMusicPath(String file_name) {

		return new String(CURRENT_DIRECTORY + "\\" + this.user_login + "\\music\\" + file_name);

	}

	/**
	 * This method return value of the object String
	 * what contains information about user_login
	 * @return value of the object String what contains information about user login
	 * */
	public String getUserLogin() {

		return this.user_login;

	}

	/**
	 * This method set value of the object String
	 * what contains information about user login
	 * @param user_login value of the object String what contains information about user login
	 * */
	public void setUserLogin(String user_login) {

		this.user_login = user_login;

	}

}
