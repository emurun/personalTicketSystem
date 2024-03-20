package application;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class CommonObjs {
//only instantiate once
	private static CommonObjs commonObjs = new CommonObjs();
	@FXML HBox mainBox;
	String jdbcUrl = "jdbc:sqlite:ladybug.db";
	Connection globalConn;
	private CommonObjs() {
		//in constructor get connection only once
		try {
			globalConn = DriverManager.getConnection(jdbcUrl);
		}
		catch (SQLException e) {
			System.out.println("Error connecting");
			e.printStackTrace();
		}
		
	}
	

	public static CommonObjs getInstance() {
		return commonObjs;
	}

	public HBox getMainBox() {
		return mainBox;
	}

	public void setMainBox(HBox mainBox) {
		System.out.println(mainBox);
		this.mainBox = mainBox;
	}
	
	
	public Connection getGlobalConnection() {
		return globalConn;
	}
	
	public URL goToPage(String location) {
		System.out.println("go to page mainbox: " + mainBox);
		URL url = getClass().getClassLoader().getResource(location);

		try {
			AnchorPane pane3 = (AnchorPane) FXMLLoader.load(url);

			if (mainBox.getChildren().size() > 1)
				mainBox.getChildren().remove(1);
			
			mainBox.getChildren().add(pane3);
			setMainBox(mainBox);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}


	
	

	
}
