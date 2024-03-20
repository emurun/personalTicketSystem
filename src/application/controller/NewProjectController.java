package application.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ResourceBundle;

import application.CommonObjs;
import application.Project;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.time.LocalDate;

// This class is to save all the projects and info into the database.
public class NewProjectController implements Initializable{
	@FXML
	private Button save_project;
	@FXML
	private TextField project_name;
	@FXML
	private DatePicker start_date;
	
	@FXML
	private TextArea project_details;
	
	@FXML
	private TextField status;
	


    private ObservableList<Project> projectList;
	
	@FXML
	public void saveProjectOp() {
		CommonObjs cj = CommonObjs.getInstance();

		try {
			Connection connection = cj.getGlobalConnection();
			Statement statement = connection.createStatement();
			System.out.println("connected!");

			String query = "insert into projects values(?, ?, ?, ?)";
			
			PreparedStatement insertStmt = connection.prepareStatement(query);
			insertStmt.setString(1, project_name.getText());
			insertStmt.setString(2, start_date.getValue().toString());
			insertStmt.setString(3, project_details.getText());
			insertStmt.setString(4, status.getText());

			String sql = "SELECT rowid, * FROM projects";
			
			insertStmt.executeUpdate();
			ResultSet result = statement.executeQuery(sql);
			
			while(result.next()) {
				int id = result.getInt("rowid");
				String name = result.getString("name");
				String start = result.getString("date_start");
				String project_details = result.getString("project_details");
				String status = result.getString("status");
				System.out.println(id + " | "  + name +  " | " + start + " | " + project_details + " | " + status);

			}
			
		} catch (SQLException e) {
			System.out.println("Error connecting");
			e.printStackTrace();
		}

		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Setting default date
		LocalDate today = LocalDate.now(); 
		start_date.setValue(today);
		
	}
}