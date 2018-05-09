package database;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import application.User;
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
	 * This method
	 * */
	public final String addFile(byte[] bytes, String file_name, String user_login, String flag) {

		String message = new String("");
		File file = null;
		ResultSet result_set = null;
		BufferedImage buffered_image = null;

		try {

			// проверяем на существование такого пользователя
			if ((result_set = this.getResult("SELECT user_id FROM user WHERE login=\"" + user_login + "\"")).next()) {

				switch (flag) {

				case "Image":	file = new File("D:\\eclipse\\workspace\\Server\\src\\database\\" + user_login + "\\images\\");
		 	 					if (!file.exists()) file.mkdir();

								try {

									buffered_image = ImageIO.read(new ByteArrayInputStream(bytes));
									ImageIO.write(buffered_image, this.getFileExtension(file_name), new File(file.getPath(), file_name));

								} catch (IOException e) {
									e.printStackTrace();
								}
								
								
								//this.prepared_statement = (PreparedStatement) this.connection.prepareStatement(SQL_INSERT_IMAGE);
								//this.prepared_statement.setInt(2, result_set.getInt(1));
								//this.prepared_statement.setString(3, file.getPath());

								break;

				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return message;

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
