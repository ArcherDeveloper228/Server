package gui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ServerInterface extends Stage {

	private final String TITLE_WINDOW;
	private AnchorPane pane;
	
	{ this.TITLE_WINDOW = new String("Server"); }
	
	public ServerInterface() {

		try {
			this.pane = (AnchorPane) FXMLLoader.load(ServerInterface.class.getResource("Server.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		super.setScene(new Scene(this.pane));
		super.setTitle(this.TITLE_WINDOW);
		super.setResizable(false);
		super.show();
		
	}
	
}
