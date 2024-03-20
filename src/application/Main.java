package application;
	
import java.net.URL;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.fxml.FXMLLoader;


/**
 * This is the Main Class
 * Goal is to load the Home Page on start as well as display the 2 buttons that the app can navigate to. 
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		try {
			HBox root = (HBox)FXMLLoader.load(getClass().getClassLoader().getResource("view/Main.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			//Home Page displays all projects and is on by default when a user launches the app
			URL url = getClass().getClassLoader().getResource("view/Home.fxml");
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			root.getChildren().add(pane1);
			
			CommonObjs cj = CommonObjs.getInstance();
			
			cj.setMainBox(root);
			System.out.println("main box is updated");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
