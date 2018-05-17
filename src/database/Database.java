package database;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

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

	/** Property - statement */
	private Statement statement;

	/** Property - prepared_statement */
	private PreparedStatement prepared_statement;

	/**
	 * Make new object Database
	 * */
	public Database() {

		this.statement = null;
		this.prepared_statement = null;

		try {
			this.connection = (Connection) DriverManager.getConnection(URL, CLIENT_NAME, CLIENT_PASSWORD);
			System.out.println("Database running...");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ResultSet getResult(String inquiry) {

		ResultSet result_set = null;

		try {

			this.statement = (Statement) this.connection.createStatement();
			result_set = this.statement.executeQuery(inquiry);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result_set;

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
	 * This method add user in database
	 * @param user it is object contains information about user
	 * */
	public void addUser(User user) {

		try {

			this.prepared_statement = (PreparedStatement) this.connection.prepareStatement(SQL_INSERT_USER);

			this.prepared_statement.setString(1, user.getName());
			this.prepared_statement.setString(2, user.getSurname());
			this.prepared_statement.setInt(3, user.getDayOfBirth());
			this.prepared_statement.setString(4, user.getMonthOfBirth());
			this.prepared_statement.setInt(5, user.getYearOfBirth());
			this.prepared_statement.setString(6, user.getSex());
			this.prepared_statement.setString(7, user.getUserLogin());
			this.prepared_statement.setInt(8, user.getUserPassword());

			this.prepared_statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			// если объект PreparedStatement не null, то закрываем его
			if (this.prepared_statement != null) {

				try {
					this.prepared_statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

		}

	}

	/**
	 * This method check user in database
	 * @param user value of the object User
	 * */
	public final String authorizationUser(User user) {

		String attention = new String("Check your login (password)!");
		ResultSet result_set = null;

		try {

			result_set = this.getResult("SELECT * FROM user WHERE login=\"" + user.getUserLogin() + "\"");

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

			result_set = this.getResult("SELECT * FROM USER WHERE login=\"" + user.getUserLogin() + "\"");

			if (result_set.next() && result_set.getString(8).hashCode() == user.getUserLogin().hashCode())
				return message;
			else if (!result_set.next() || (result_set.next() && result_set.getString(8).hashCode() != user.getUserLogin().hashCode())) {

				file = new File("D:\\eclipse\\workspace\\Server\\src\\database\\" + user.getUserLogin());

				if (!file.exists()) file.mkdir();

				this.addUser(user);
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
			if ((result_set = this.getResult("SELECT user_id FROM user WHERE login=\"" + user_login + "\"")).next()) {

				switch (flag) {

				case "Image":	file = new File("D:\\eclipse\\workspace\\Server\\src\\database\\" + user_login + "\\images\\");
		 	 					if (!file.exists()) file.mkdir();
		 	 					path = file.getPath();
		 	 					file = new File(file.getPath() + "\\" + file_name);
		 	 					// проверяем на существование такого файла
		 	 					if (!file.exists()) {
		 	 						try {
										buffered_image = ImageIO.read(new ByteArrayInputStream(bytes));
										ImageIO.write(buffered_image, this.getFileExtension(file_name), new File(path, file_name));
									} catch (IOException e) {
										e.printStackTrace();
									}
									this.prepared_statement = (PreparedStatement) this.connection.prepareStatement(SQL_INSERT_IMAGE);
									this.prepared_statement.setInt(1, result_set.getInt(1));
									this.prepared_statement.setString(2, file_name);
									this.prepared_statement.executeUpdate();
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
			if ((result_set = this.getResult("SELECT user_id FROM user WHERE login=\"" + user_login + "\"")).next()) {

				switch (flag) {

				case "Image":	file = new File("D:\\eclipse\\workspace\\Server\\src\\database\\" + user_login + "\\images\\" + file_name);
								// выполняем проверку на существование файла
								if (file.exists()) {
									this.prepared_statement = (PreparedStatement) this.connection.prepareStatement(SQL_DELETE_FROM_IMAGES +
											"WHERE user_id='" + result_set.getInt(1) + "' AND image_path='" + file_name + "'");
									this.prepared_statement.executeUpdate();
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
			if ((result_set_1 = this.getResult(SQL_SELECT_USER + "WHERE login=\"" + user_login + "\"")).next()) {

				// проверяем на существование картинок пользователя в базе данных
				if ((result_set_2 = this.getResult(SQL_SELECT_IMAGE + "WHERE user_id=\"" + result_set_1.getInt(1) + "\"")).next()) {

					images = new Container();
					file = new File("D:\\eclipse\\workspace\\Server\\src\\database\\" + user_login +
										"\\images\\" + result_set_2.getString(3));
					images.addElement(new ListFiles(result_set_2.getString(3), this.getFileBytes(file)));

					while (result_set_2.next()) {

						file = new File("D:\\eclipse\\workspace\\Server\\src\\database\\" + user_login +
											"\\images\\" + result_set_2.getString(3));
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
