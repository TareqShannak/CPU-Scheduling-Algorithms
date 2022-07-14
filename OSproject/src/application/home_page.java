package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/*
|-------------------------------------------------------|
|  home scene was created using Scenebuilder.           |
|-------------------------------------------------------|
*/
public class home_page implements Initializable {

	public static ArrayList<Process> processes;
	public static ArrayList<Process> CPU = new ArrayList<Process>();
	public static String s;

	@FXML
	ComboBox<String> choice_box;

	@FXML
	Label l_q;

	@FXML
	Label incomplete;

	@FXML
	Label cantRead;

	@FXML
	TextField text_q;

	@FXML
	Button exit;

	@FXML
	Button reset;

	@FXML
	Button simulation;

	@FXML
	TextField nop_;
	/*
	------------------------------------------------------------
	 This method here hides the time quantum text if Round robin
	 algorithm is not selected
	-----------------------------------------------------------
	*/
	@FXML
	public void Select(ActionEvent event) {
		l_q.setVisible(false);
		text_q.setVisible(false);
		if (choice_box.getSelectionModel().getSelectedItem() != null)
			s = choice_box.getSelectionModel().getSelectedItem().toString();
		if (s.equalsIgnoreCase("RR")) {
			l_q.setVisible(true);
			text_q.setVisible(true);
		} else {
			l_q.setVisible(false);
			text_q.setVisible(false);
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		ObservableList<String> choice_box_algList = FXCollections.observableArrayList("SJF", "RR", "SRTF", "PWA",
				"FCFS");
		choice_box.setItems(choice_box_algList);
	}

	@FXML
	public void reset(ActionEvent event) {
		choice_box.setValue(null);
		incomplete.setVisible(false);
		cantRead.setVisible(false);
		text_q.clear();
	}

	@FXML
	public void exit(ActionEvent event) {
		exit.setOnAction(actionEvent -> Platform.exit());
	}
	 /*
	  this method chose a scheduling algorithm according to
	  to the user selection from the choice box in view_1
	 */
	@FXML
	public void switch_Scenes(ActionEvent event) {
		processes = new ArrayList<Process>();
		int nop, q;
		BufferedReader input = null;
		try {
			s = choice_box.getSelectionModel().getSelectedItem().toString();
		} catch (Exception exception) {
			incomplete.setVisible(true);
			return;
		}
		try {
			try {
				nop = Integer.parseInt(nop_.getText());
				System.out.println(nop);
			} catch (Exception exception) {
				incomplete.setVisible(true);
				return;
			}
			try {
				File file = new File("p_info.txt");
				input = new BufferedReader(new FileReader(file));
				for (int i = 0; i < nop; i++) {
					String[] result = input.readLine().split("\\s");
					processes.add(new Process(Integer.parseInt(result[0]), Integer.parseInt(result[1]),
							Integer.parseInt(result[2]), Integer.parseInt(result[3])));
				}
				input.close();
			} catch (Exception exception) {
				cantRead.setVisible(true);
				return;
			}

			if (s.equalsIgnoreCase("RR")) {
				try {
					q = Integer.parseInt(text_q.getText());
				} catch (Exception exception) {
					incomplete.setVisible(true);
					return;
				}
				CPU = Scheduler.RR(processes, q);
			}
			if (s.equalsIgnoreCase("SJF"))
				CPU = Scheduler.SJF(processes);
			if (s.equalsIgnoreCase("SRTF"))
				CPU = Scheduler.SRTF(processes);
			if (s.equalsIgnoreCase("PWA"))
				CPU = Scheduler.EPwithoutPreemption(processes);
			if (s.equalsIgnoreCase("FCFS"))
				CPU = Scheduler.FCFS(processes);

			Parent root_2 = FXMLLoader.load(getClass().getResource("view_2.fxml"));
			Scene scene_2 = new Scene(root_2, 985, 645);
			Stage st = (Stage) (((Node) event.getSource()).getScene().getWindow());
			st.setScene(scene_2);
			st.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
