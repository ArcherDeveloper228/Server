package database;

import java.nio.file.Paths;

public interface IConstDatabase {

	String URL = new String("jdbc:mysql://localhost:3306/mediateka");
	String CLIENT_NAME = new String("root");
	String CLIENT_PASSWORD = new String("Nikita09061999");
	String CURRENT_DIRECTORY = new String(Paths.get(".").toAbsolutePath().normalize().toString());
	String SQL_INSERT_USER = new String("INSERT INTO user(name, surname, birth_day, birth_month, birth_year,"
			+ " sex, login, password) VALUES(?,?,?,?,?,?,?,?)");
	String SQL_INSERT_IMAGE = new String("INSERT INTO images(user_id, image_path) VALUES(?,?)");
	String SQL_INSERT_MUSIC = new String("INSERT INTO music(user_id, music _path) VALUES(?,?)");
	String SQL_SELECT_USER = new String("SELECT * FROM user WHERE login=?");
	String SQL_SELECT_IMAGE = new String("SELECT * FROM images WHERE user_id=?");
	String SQL_DELETE_FROM_IMAGES = new String("DELETE FROM images WHERE user_id=? AND image_path=?");

}
