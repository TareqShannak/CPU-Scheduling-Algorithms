package application;

import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class gantt_chart implements Initializable {

	@FXML
	private Label algorithm_type;

	@FXML
	private TableView<Process> table;

	@FXML
	private TableColumn<Process, Integer> pid_p;

	@FXML
	private TableColumn<Process, Integer> arrival_p;

	@FXML
	private TableColumn<Process, Integer> burst_p;

	@FXML
	private TableColumn<Process, Integer> priority_p;

	@FXML
	private TableColumn<Process, Integer> repeat_p;

	@FXML
	private TableColumn<Process, Integer> start_p;

	@FXML
	private TableColumn<Process, Integer> finish_p;

	@FXML
	private TableColumn<Process, Integer> wait_p;

	@FXML
	private TableColumn<Process, Integer> turnaround_p;

	@FXML
	private TableColumn<Process, Double> wta_p;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	Button exit;

	@FXML
	TextField ata;

	@FXML
	TextField awt;

	@FXML
	TextField aawt;

	@FXML
	void back(ActionEvent event) {

		try {

			Parent root_3 = FXMLLoader.load(getClass().getResource("view_1.fxml"));
			Scene scene_3 = new Scene(root_3, 955, 588);
			Stage st = (Stage) (((Node) event.getSource()).getScene().getWindow());
			st.setScene(scene_3);
			st.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void exit(ActionEvent event) {
		exit.setOnAction(actionEvent -> Platform.exit());
	}

	ObservableList<Process> list_t = FXCollections.observableArrayList();
	static ObservableList<String> r1 = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			for (int i = 0; i < home_page.processes.size(); i++)
				list_t.add(home_page.processes.get(i));
		} catch (Exception e) {
			e.printStackTrace();
		}
		pid_p.setCellValueFactory(new PropertyValueFactory<>("ID"));
		arrival_p.setCellValueFactory(new PropertyValueFactory<>("arrival"));
		burst_p.setCellValueFactory(new PropertyValueFactory<>("burst"));
		priority_p.setCellValueFactory(new PropertyValueFactory<>("priority"));
		repeat_p.setCellValueFactory(new PropertyValueFactory<>("repeat"));
		turnaround_p.setCellValueFactory(new PropertyValueFactory<>("turnaround"));
		wait_p.setCellValueFactory(new PropertyValueFactory<>("waitingtime"));
		finish_p.setCellValueFactory(new PropertyValueFactory<>("finishtime"));
		start_p.setCellValueFactory(new PropertyValueFactory<>("starttime"));
		wta_p.setCellValueFactory(new PropertyValueFactory<>("WTA"));

		table.setItems(list_t);

		ata.setText(Double.toString(Scheduler.taavg));
		awt.setText(Double.toString(Scheduler.waittimeavg));
		aawt.setText(Double.toString(Scheduler.wtaavg));
		r1.clear();
		for (int i = 0; i < home_page.CPU.size(); i++)
			r1.add(Integer.toString(home_page.CPU.get(i).getID()) + "\n"
					+ Integer.toString(home_page.CPU.get(i).getRunningBurst()));

		int j = 0;
		HBox hBox = new HBox();
		Rectangle rect;
		/*
   	 ----------------------------------------------------------
   	  This while loop creates colored rectangles, each rectangle
   	  represent a process in the gantt_chart
   	 ----------------------------------------------------------
   	*/
		while (j < r1.size()) {
			rect = new Rectangle(55, 110);
			float x1 = (float) Math.random();
			float x2 = (float) Math.random();
			float x3 = (float) Math.random();
			rect.setFill(Color.color(x1, x2, x3));
			Label t = new Label("P" + r1.get(j));
			t.setFont(new Font("Times New Roman", 18));
			t.setStyle("-fx-font-weight: bold");
			StackPane s = new StackPane();
			s.getChildren().addAll(rect, t);
			s.setPadding(new Insets(7, 7, 7, 7));
			hBox.getChildren().add(s);
			scrollPane.setContent(hBox);
			j++;
		}
		scrollPane.setStyle("-fx-color:#3b7479;");

		if (home_page.s.equalsIgnoreCase("RR"))
			algorithm_type.setText("Round Robin Algorithm");
		if (home_page.s.equalsIgnoreCase("SJF"))
			algorithm_type.setText("Shortest Job First Algorithm");
		if (home_page.s.equalsIgnoreCase("SRTF"))
			algorithm_type.setText("Shortest Remaining Time First Algorithm");
		if (home_page.s.equalsIgnoreCase("PWA"))
			algorithm_type.setText("Priority without preemption Algorithm");
		if (home_page.s.equalsIgnoreCase("FCFS"))
			algorithm_type.setText("First Come First Served Algorithm");
	}

	@FXML
	public void goToPieChart(ActionEvent event) {
		try {

			Parent root_4 = FXMLLoader.load(getClass().getResource("view_3.fxml"));
			Scene scene_4 = new Scene(root_4, 500, 492);
			Stage st = (Stage) (((Node) event.getSource()).getScene().getWindow());
			st.setScene(scene_4);
			st.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void goToSummary(ActionEvent event) {

		try {

			Parent root_4 = FXMLLoader.load(getClass().getResource("view_4.fxml"));
			Scene scene_4 = new Scene(root_4, 660, 567);
			Stage st = (Stage) (((Node) event.getSource()).getScene().getWindow());
			st.setScene(scene_4);
			st.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}