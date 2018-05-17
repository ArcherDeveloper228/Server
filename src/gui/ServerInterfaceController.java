package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import server.Server;

public class ServerInterfaceController {

	/** Property - server */
	private Server server;

	{ this.server = Server.getInstance(); }

	@FXML
	private Button exit_button;

	@FXML
	private Button start_button;

	@FXML
	private Button update_button;

	@FXML
	private Label information_label;

	@FXML
	private Label clients_label;

	@FXML
	public void initialize() {

		// ������������� ���������� ������� ��� ������ "Start"
		this.start_button.setOnAction(event -> {

			// ������� ������ �������
			//this.server = new Server();

			// ��������� ������
			this.server.start();
			this.start_button.setDisable(true);
			this.information_label.setText("Server running...");

		});

		// ������������� ���������� ������� ��� ������ "Exit"
		this.exit_button.setOnAction(event -> {

			// ��������� ������ �������
			this.server.closeConnection();
			this.exit_button.getScene().getWindow().hide();
			System.exit(1);

		});

	}

}
