package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class summary implements Initializable {
	@FXML
	Button back;

	@FXML
	Button exit;

	@FXML
	public void exit(ActionEvent event) {
		exit.setOnAction(actionEvent -> Platform.exit());
	}

	@FXML
	void back(ActionEvent event) {
		try {
			Parent root_5 = FXMLLoader.load(getClass().getResource("view_2.fxml"));
			Scene scene_5 = new Scene(root_5, 985, 645);
			Stage st = (Stage) (((Node) event.getSource()).getScene().getWindow());
			st.setScene(scene_5);
			st.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
}
