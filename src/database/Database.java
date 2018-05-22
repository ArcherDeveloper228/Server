package database;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import com.mysql.jdbc.Connection;

import application.User;
import json.Container;
import json.ListFiles;
/**
 * This class make connection with database mediateka
 * @author Nikita.Ustyushenko
 * @version 1.0
 * */
public class Database implements IConstDatabase {

	/** Property - connection */
	private Connection connection;

	/**
	 * Make new object Database
	 * */
	public Database() {

		try {
			this.connection = (Connection) DriverManager.getConnection(URL, CLIENT_NAME, CLIENT_PASSWORD);
			System.out.println("Database running...");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	/**
	 * This method close connection with database mediateka
	 * */
	public final void closeConnection() {

		// закрываем соеденение с базой данных mediateka
		if (this.connection != null) {

			try {
				this.connection.close();
				System.out.println("Database ending...");
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else ;

	}

	/**
	 * This method check user in database
	 * @param user value of the object User
	 * */
	public final String authorizationUser(User user) {

		String attention = new String("Check your login (password)!");
		ResultSet result_set = null;

		try {

			result_set = SQLQueries.getUser(this.connection, user.getUserLogin());
			if (!result_set.next()) return attention;
			else if (result_set.getInt(9) == user.getUserPassword() &&
					result_set.getString(8).hashCode() == user.getUserLogin().hashCode()) return new String("Ok");
			else return attention;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return attention;

	}

	/**
	 * This method add user into database
	 * @param user value of the object User
	 * */
	public final String registrationUser(User user) {

		String message = new String("This login is already exists!");
		ResultSet result_set = null;
		File file = null;

		try {

			result_set = SQLQueries.getUser(this.connection, user.getUserLogin());

			if (result_set.next() && result_set.getString(8).hashCode() == user.getUserLogin().hashCode())
				return message;
			else if (!result_set.next() || (result_set.next() && result_set.getString(8).hashCode() != user.getUserLogin().hashCode())) {

				file = new File(this.createFilePath(user.getUserLogin(), null, "RegistrationUser"));

				if (!file.exists()) file.mkdir();

				SQLQueries.addUser(this.connection, user);
				return new String("Ok");

			} else return message;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return message;

	}

	/**
	 * This method add file into database
	 * @param bytes array file bytes
	 * @param file_name value of the object String what contains information about file name
	 * @param user_login value of the object String what contains information about user login
	 * @param flag value of the object String what contains information about flag
	 * @return value of the object String
	 * */
	public final String addFile(byte[] bytes, String file_name, String user_login, String flag) {

		String message = new String("File with name \"" + file_name + "\" already exists!");
		File file = null;
		String path = null;
		ResultSet result_set = null;
		BufferedImage buffered_image = null;

		try {

			// проверяем на существование такого пользователя
			if ((result_set = SQLQueries.getUser(this.connection, user_login)).next()) {

				switch (flag) {

				case "Image":	file = new File(this.createFilePath(user_login, null, "MakeDirImages"));
		 	 					if (!file.exists()) file.mkdir();
		 	 					path = file.getPath();
		 	 					file = new File(this.createFilePath(user_login, file_name, "MakeFileImage"));
		 	 					// проверяем на существование такого файла
		 	 					if (!file.exists()) {
		 	 						try {
										buffered_image = ImageIO.read(new ByteArrayInputStream(bytes));
										ImageIO.write(buffered_image, this.getFileExtension(file_name), new File(path, file_name));
									} catch (IOException e) {
										e.printStackTrace();
									}
									SQLQueries.addImage(this.connection, file_name, result_set.getInt(1));
									message = new String("Ok");
		 	 					} else ;
								break;

				case "Music":	file = new File(this.createFilePath(user_login, null, "MakeDirMusic"));
								if (!file.exists()) file.mkdir();
								path = file.getPath();
								file = new File(this.createFilePath(user_login, file_name, "MakeFileMusic"));
								// проверяем на существование такого файла
								if (!file.exists()) {
									try {
										FileOutputStream file_output_stream = new FileOutputStream(file);
										file_output_stream.write(bytes);
										file_output_stream.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
									SQLQueries.addMusic(this.connection, file_name, result_set.getInt(1));
									message = new String("Ok");
								} else ;
								break;

				}

			} else ;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return message;

	}

	/**
	 * This method delete image from database and directory
	 * @param file_name value of the object String what contains information about file name
	 * @param user_login value of the object String what contains information about user login
	 * @param flag value of the object String what contains information about flag
	 * @return value of the object String
	 * */
	public final String deleteFile(String file_name, String user_login, String flag) {

		String message = new String("There is no such file on the server!");
		ResultSet result_set = null;
		File file = null;

		try {

			// проверка на существование пользователя
			if ((result_set = SQLQueries.getUser(this.connection, user_login)).next()) {

				switch (flag) {

				case "Image":	file = new File(this.createFilePath(user_login, file_name, "MakeFileImage"));
								// выполняем проверку на существование файла
								if (file.exists()) {
									SQLQueries.deleteImage(this.connection, file_name, result_set.getInt(1));
									// удаляем файл из дирректории
									file.delete();
									message = new String("Ok");
								} else ;
							 	break;

				}

			} else ;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return message;

	}

	/**
	 * This method load images from database
	 * @param user_login value of the object String what contains information about user login
	 * @return object of the collection Map<K,V>
	 * */
	public final Container loadImages(String user_login) {

		Container images = null;
		File file = null;
		ResultSet result_set_1 = null;
		ResultSet result_set_2 = null;

		try {

			// проверяем на существование пользователя
			if ((result_set_1 = SQLQueries.getUser(this.connection, user_login)).next()) {

				// проверяем на существование картинок пользователя в базе данных
				if ((result_set_2 = SQLQueries.getImages(this.connection, result_set_1.getInt(1))).next()) {

					images = new Container();
					file = new File(this.createFilePath(user_login, result_set_2.getString(3), "MakeFileImage"));
					images.addElement(new ListFiles(result_set_2.getString(3), this.getFileBytes(file)));

					while (result_set_2.next()) {

						file = new File(this.createFilePath(user_login, result_set_2.getString(3), "MakeFileImage"));
						images.addElement(new ListFiles(result_set_2.getString(3), this.getFileBytes(file)));

					}

				} else ;

			} else ;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return images;

	}

	/**
	 * This method create file path
	 * @param user_login value of the object String what contains information about user login
	 * @param file_name value of the object String what contains information about file name
	 * @param flag value of the object String
	 * @return value of the object String what contains information about file path
	 * */
	public String createFilePath(String user_login, String file_name, String flag) {

		String file_path = null;

		switch (flag) {

		case "RegistrationUser": 	file_path = new String(CURRENT_DIRECTORY + "\\" + user_login); break;
		case "MakeDirImages": 		file_path = new String(CURRENT_DIRECTORY + "\\" + user_login + "\\images\\"); break;
		case "MakeDirMusic": 		file_path = new String(CURRENT_DIRECTORY + "\\" + user_login + "\\music\\"); break;
		case "MakeFileImage":		file_path = new String(CURRENT_DIRECTORY + "\\" + user_login + "\\images\\" + file_name); break;
		case "MakeFileMusic":		file_path = new String(CURRENT_DIRECTORY + "\\" + user_login + "\\music\\" + file_name); break;

		}

		return file_path;

	}

	/**
	 * This method return array file bytes
	 * @param file value of the object File
	 * @return array file bytes
	 * */
	public byte[] getFileBytes(File file) {

		byte[] file_bytes = null;
		String extension = null;

		try {

			extension = this.getFileExtension(file.getName());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedImage buffered_image = ImageIO.read(file);

			ImageIO.write(buffered_image, extension, baos);
			file_bytes = baos.toByteArray();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return file_bytes;

	}

	/**
	 * This method return file extension
	 * @param file_name value of the object String
	 * @return value of the object String
	 * */
	private final String getFileExtension(String file_name) {

        // если в имени файла есть точка и она не является первым символом в названии файла
        if(file_name.lastIndexOf(".") != -1 && file_name.lastIndexOf(".") != 0)
        // то вырезаем все знаки после последней точки в названии файла, то есть ХХХХХ.txt -> txt
        return file_name.substring(file_name.lastIndexOf(".")+1);
        // в противном случае возвращаем заглушку, то есть расширение не найдено
        else return "";

	}

}
