package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import application.User;

/**
 * This class stores methods for working with SQL queries
 * @author Nikita.Ustyushenko
 * @version 1.0
 * */
public class SQLQueries implements IConstDatabase {

	/**
	 * This method add user in database
	 * @param user it is object contains information about user
	 * */
	public static void addUser(Connection connection, User user) {

		PreparedStatement prepared_statement = null;

		try {

			prepared_statement = (PreparedStatement) connection.prepareStatement(SQL_INSERT_USER);

			prepared_statement.setString(1, user.getName());
			prepared_statement.setString(2, user.getSurname());
			prepared_statement.setInt(3, user.getDayOfBirth());
			prepared_statement.setString(4, user.getMonthOfBirth());
			prepared_statement.setInt(5, user.getYearOfBirth());
			prepared_statement.setString(6, user.getSex());
			prepared_statement.setString(7, user.getUserLogin());
			prepared_statement.setInt(8, user.getUserPassword());

			prepared_statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			// если объект PreparedStatement не null, то закрываем его
			if (prepared_statement != null) {

				try {
					prepared_statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

		}

	}

	/**
	 * This method return user from database
	 * @param connection value of the object Connection
	 * @param user_login value of the object String what contains information about user login
	 * @return value of the object ResultSet
	 * */
	public static ResultSet getUser(Connection connection, String user_login) {

		PreparedStatement prepared_statement = null;
		ResultSet result_set = null;

		try {

			prepared_statement = (PreparedStatement) connection.prepareStatement(SQL_SELECT_USER);
			prepared_statement.setString(1, user_login);

			result_set = prepared_statement.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result_set;

	}

	/**
	 * This method add image path and user id in database
	 * @param connection value of the object Connection
	 * @param file_name value of the object String what contains information about file name
	 * @param user_id contains information about user id
	 */
	public static void addImage(Connection connection, String file_name, int user_id) {

		PreparedStatement prepared_statement = null;

		try {

			prepared_statement = (PreparedStatement) connection.prepareStatement(SQL_INSERT_IMAGE);
			prepared_statement.setInt(1, user_id);
			prepared_statement.setString(2, file_name);
			prepared_statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			// если объект PreparedStatement не null, то закрываем его
			if (prepared_statement != null) {

				try {
					prepared_statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

		}

	}

	/**
	 * This method delete all information about image from database
	 * @param connection value of the object Connection
	 * @param file_name value of the object String what contains information about file name
	 * @param user_id contains information about user id
	 * */
	public static void deleteImage(Connection connection, String file_name, int user_id) {

		PreparedStatement prepared_statement = null;

		try {

			prepared_statement = (PreparedStatement) connection.prepareStatement(SQL_DELETE_FROM_IMAGES);
			prepared_statement.setInt(1, user_id);
			prepared_statement.setString(2, file_name);
			prepared_statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			// если объект PreparedStatement не null, то закрываем его
			if (prepared_statement != null) {

				try {
					prepared_statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}


		}

	}

	/**
	 * This method return information about image from database
	 * @param connection value of the object Connection
	 * @param user_id contains information about user id
	 * @return value of the object ResultSet
	 * */
	public static ResultSet getImages(Connection connection, int user_id) {

		PreparedStatement prepared_statement = null;
		ResultSet result_set = null;

		try {

			prepared_statement = (PreparedStatement) connection.prepareStatement(SQL_SELECT_IMAGE);
			prepared_statement.setInt(1, user_id);
			result_set = prepared_statement.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result_set;

	}

	/**
	 * This method add all information about music(user id, music path) in database
	 * @param connection value of the object Connection
	 * @param music_path value of the object String what contains information about music path
	 * @param user_id contains information user id
	 * */
	public static void addMusic(Connection connection, String music_path, int user_id) throws SQLException {

		PreparedStatement prepared_statement = (PreparedStatement) connection.prepareStatement(SQL_INSERT_MUSIC);
		prepared_statement.setInt(1, user_id);
		prepared_statement.setString(2, music_path);
		prepared_statement.executeUpdate();

		// если объект prepared_statement не null, то закрываем его
		if (prepared_statement != null) prepared_statement.close();

	}

}
