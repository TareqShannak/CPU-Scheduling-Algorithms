package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class pie_chart implements Initializable {
	@FXML
	private PieChart pie_c;

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
	public void initialize(URL arg0, ResourceBundle arg1) {
		double sum = 0;
		for (int k = 0; k < home_page.processes.size(); k++)
			sum = sum + home_page.processes.get(k).getBurst();
		double utilization = sum / (getMaxValue(home_page.processes)) * 1.0;

		ObservableList<PieChart.Data> ol = FXCollections.observableArrayList(
				new PieChart.Data("CPU Utilization", utilization), new PieChart.Data("I-O", 1 - utilization));
		pie_c.setData(ol);
		applyCustomColorSequence(ol, "aqua", "bisque", "chocolate", "coral", "crimson");
	}

	private void applyCustomColorSequence(ObservableList<PieChart.Data> pieChartData, String... pieColors) {
		int i = 0;
		for (PieChart.Data data : pieChartData) {
			data.getNode().setStyle("-fx-pie-color: " + pieColors[i % pieColors.length] + ";");
			i++;
		}
	}

	public static double getMaxValue(ArrayList<Process> array) {
		int maxValue = array.get(0).getFinishtime();
		for (int i = 1; i < array.size(); i++) {
			if (array.get(i).getFinishtime() > maxValue) {
				maxValue = array.get(i).getFinishtime();
			}
		}
		return maxValue;
	}

}
