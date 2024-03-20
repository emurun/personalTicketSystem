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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.time.LocalDate;

// This class is to save all the projects and info into the database.
public class UpdateProjectController implements Initializable {
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

	private int id; // row id that we get from init;
	private ObservableList<Project> projectList;

	CommonObjs cj = CommonObjs.getInstance();
	@FXML
	HBox mainBox = cj.getMainBox();

	@FXML
	public void saveProjectOp() {

		System.out.println("saved in update");

		try {
			Connection connection = cj.getGlobalConnection();
			Statement statement = connection.createStatement();
			System.out.println("connected!");

			String query = "update projects set name = ?, " + "date_start = ?, " + "project_details = ?, "
					+ "status = ?" + "where rowid = ?";

			PreparedStatement insertStmt = connection.prepareStatement(query);
			insertStmt.setString(1, project_name.getText());
			insertStmt.setString(2, start_date.getValue().toString());
			insertStmt.setString(3, project_details.getText());
			insertStmt.setString(4, status.getText());
			insertStmt.setInt(5, id);

			String sql = "SELECT rowid, * FROM projects";

			insertStmt.executeUpdate();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				int id = result.getInt("rowid");
				String name = result.getString("name");
				String start = result.getString("date_start");
				String project_details = result.getString("project_details");
				String status = result.getString("status");
				System.out.println(id + " | " + name + " | " + start + " | " + project_details + " | " + status);

			}
			cj.goToPage("view/Home.fxml");

		} catch (SQLException e) {
			System.out.println("Error connecting");
			e.printStackTrace();
		}

	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		// Setting default date

		LocalDate today = LocalDate.now();
		start_date.setValue(today);

		System.out.println(
				"probably here i want to put the information that we already have and then make the user edit it");

	}

	public void initData(Project project) {
		System.out.println(project.getProjectName());
		project_name.setText(project.getProjectName());
		project_details.setText(project.getProjectDesc());
		status.setText(project.getProjectStatus());
		start_date.setValue(LocalDate.parse(project.getProjectDate()));
		this.id = project.getId();
	}
	@FXML
	public void deleteProjectOp() {
	    // Create a confirmation dialog
	    Alert alert = new Alert(AlertType.CONFIRMATION);
	    alert.setTitle("Confirmation Dialog");
	    alert.setHeaderText("Delete Project");
	    alert.setContentText("Are you sure you want to delete this project?");

	    // Shows confirmation dialog and wait for user response
	    ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

	    if (result == ButtonType.OK) {
	        // User clicked OK, then delete
	        try {
	            Connection connection = cj.getGlobalConnection();
	            Statement statement = connection.createStatement();
	            
	         /* HAVE TO FIX THIS CODE TO DELETE ASSOCIATED COMMENTS*/
	            String deleteCommentsQuery = "DELETE FROM comments_2 WHERE project_name = ?";
	            PreparedStatement deleteCommentsStmt = connection.prepareStatement(deleteCommentsQuery);
	            deleteCommentsStmt.setString(1, project_name.getText());
	            deleteCommentsStmt.executeUpdate();
	            
 
	            // Delete associated tickets
	            String deleteTicketsQuery = "DELETE FROM tickets WHERE project_name = ?";
	            PreparedStatement deleteTicketsStmt = connection.prepareStatement(deleteTicketsQuery);
	            deleteTicketsStmt.setString(1, project_name.getText());
	            deleteTicketsStmt.executeUpdate();

	            // Delete the project
	            String deleteProjectQuery = "DELETE FROM projects WHERE rowid = ?";
	            PreparedStatement deleteProjectStmt = connection.prepareStatement(deleteProjectQuery);
	            deleteProjectStmt.setInt(1, id);

	            // Delete statement
	            deleteProjectStmt.executeUpdate();

	            System.out.println("Project and associated tickets/comments deleted successfully!");

	            cj.goToPage("view/Home.fxml");

	        } catch (SQLException e) {
	            System.out.println("Error deleting project and associated tickets/comments");
	            e.printStackTrace();
	        }
	    } else {
	        // User clicked Cancel or closed the dialog, do nothing
	        System.out.println("Deletion canceled");
	    }
	}


}