package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import server.Server;

public class ServerInterfaceController {

	private final Server server;

	{ this.server = new Server(); }

	@FXML
	private Button exit_button;

	@FXML
	private Button start_button;

	@FXML
	private Button stop_button;

	@FXML
	private Label information_label_1;

	@FXML
	private Label information_label_2;

	@FXML
	private Label clients_label;

	@FXML
	public void initialize() {

		this.stop_button.setDisable(true);

		this.start_button.setOnAction(event -> {

			 this.server.start();
			 this.start_button.setDisable(true);
			 this.stop_button.setDisable(false);
			 this.information_label_2.setText("");
			 this.information_label_1.setText("Server connecting...");

		});

		this.exit_button.setOnAction(event -> {

			this.exit_button.getScene().getWindow().hide();

		});

	}

}
