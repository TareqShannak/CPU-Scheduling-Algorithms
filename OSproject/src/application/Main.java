package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	public static Stage s;
	public static Scene scene;
	/*
	|-------------------------------------------------------|
	| This Main class initializes the main scenes(view_1)   |
	| and loads their corresponding fxml files              |
	| that were created using Scenebuilder.                 |
	|-------------------------------------------------------|
	*/
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("view_1.fxml"));
			scene = new Scene(root, 955, 588);
			primaryStage.setScene(scene);
			primaryStage.setTitle("CPU Scheduling"); // give the app. a title
			primaryStage.getIcons().add(new Image("logo.png"));
			primaryStage.setResizable(false); // make the app. not resizable
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	  /*
    this method generates nop number of processes and initiate
    the information for each process using math.random() method
    that gives random numbers.
 */
	public static void main(String[] args) throws IOException {
		/*
		 * Lines(48-58) can be deleted if we need to insert information manually
		 */
		int nop = 20;
		File file = new File("p_info.txt");
		BufferedWriter output = new BufferedWriter(new FileWriter(file));
		for (int i = 0; i < nop; i++) {
			int y = (int) (Math.random() * ((5 - 1) + 1)) + 1;
			int y1 = (int) (Math.random() * ((20 - 1) + 1)) + 1;
			int y2 = (int) (Math.random() * ((3 - 1) + 1)) + 1;
			output.write(i + " " + y + " " + y1 + " " + y2);
			output.newLine();
		}
		output.close();

		launch(args);
	}
}
