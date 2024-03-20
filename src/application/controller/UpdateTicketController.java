package application.controller;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.CommonObjs;
import application.Project;
import application.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UpdateTicketController implements Initializable {
	@FXML
	private Button saveTicket;
	@FXML
	private ChoiceBox chooseProject;
	@FXML
	private TextField ticketTitle;
	@FXML
	private TextArea ticketDescription;

	@FXML
	private Button addComment; // TO BE IMPLEMENTED!
	
	@FXML
    private TableView<Project> projectTable;
    @FXML
    private TableColumn<Project, String> nameColumn;
    @FXML
    private TableColumn<Project, String> startDateColumn;
    @FXML
    private TableColumn<Project, String> projectDetailsColumn;
    @FXML
    private TableColumn<Project, String> statusColumn;
    @FXML
	private TextField project_name;
    
    private Ticket ticketData;
    CommonObjs cj = CommonObjs.getInstance();
   
	private ObservableList<String> projectList; // stores list of projects to add to choicePicker

	@FXML
	public void saveTicketOp() {
	    try {
	        Connection connection = cj.getGlobalConnection();

	        // Assuming your tickets table has a project_name field, adjust the query accordingly
	        String updateQuery = "UPDATE tickets SET project_name=?, ticket_title=?, description=? WHERE rowid=?";

	        PreparedStatement updateStmt = connection.prepareStatement(updateQuery);

	        

	        // Assuming ticketTitle and ticketDescription are not null, but you can add null checks if needed
	        updateStmt.setString(2, ticketTitle.getText());
	        updateStmt.setString(3, ticketDescription.getText());

	        // Assuming ticketData is not null and has a getTicketId() method
	        updateStmt.setInt(4, ticketData.getTicketId());

	        updateStmt.executeUpdate();

	        System.out.println("Ticket updated successfully!");

	    } catch (SQLException e) {
	        System.out.println("Error updating ticket");
	        e.printStackTrace();
	    }
	}
	@FXML
	public void addCommentOp() {
//		System.out.println("something happen?");
//		cj.goToPage("view/NewComment.fxml");
	
		System.out.println("saved the ticket first");
		

		try {
			System.out.println("inside add comment op" + cj.getMainBox());
			URL url = getClass().getClassLoader().getResource("view/NewComment.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(url);

			AnchorPane pane3 = (AnchorPane) fxmlLoader.load();
			
			CommentController commentController = fxmlLoader.getController();
			commentController.initData(this.ticketData); //send project data to updatecontroller
			
			if (cj.getMainBox().getChildren().size() > 1)
				cj.getMainBox().getChildren().remove(1);
			
			cj.getMainBox().getChildren().add(pane3);
			
			
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Setting default date
		projectList = FXCollections.observableArrayList();

		// Populate the projectList from your database
		populateProjectList();

		

	}

	private void populateProjectList() { // get all project list
		String jdbcUrl = "jdbc:sqlite:ladybug.db";

		try {
			Connection connection = DriverManager.getConnection(jdbcUrl);
			Statement statement = connection.createStatement();

			String sql = "SELECT rowid, name, date_start, project_details, status FROM projects";
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				String name = result.getString("name");

				projectList.add(name);
			}

//			connection.close();
		} catch (SQLException e) {
			System.out.println("Error connecting to the database");
			e.printStackTrace();
		}
	}
	
	public void initData(Ticket ticketData) {
        this.ticketData = ticketData;

        ticketTitle.setText(ticketData.getTicketTitle());
        ticketDescription.setText(ticketData.getTicketDesc());
    }
	
	@FXML
	public void deleteTicketOp() {
	    // Create a confirmation dialog
	    Alert alert = new Alert(AlertType.CONFIRMATION);
	    alert.setTitle("Confirmation Dialog");
	    alert.setHeaderText("Delete Ticket");
	    alert.setContentText("Are you sure you want to delete this ticket?");

	    // Shows confirmation dialog and wait for user response
	    ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

	    if (result == ButtonType.OK) {
	        // User clicked OK, then delete
	        try {
	            Connection connection = cj.getGlobalConnection();
	            Statement statement = connection.createStatement();

	            String deleteCommentsQuery = "DELETE FROM comments_2 WHERE ticket_id  = ?";
	            PreparedStatement deleteCommentsStmt = connection.prepareStatement(deleteCommentsQuery);
	            deleteCommentsStmt.setInt(1, ticketData.getTicketId());
	            deleteCommentsStmt.executeUpdate();
	            
	            // Delete the specific ticket using its unique identifier (rowid)
	            String deleteTicketQuery = "DELETE FROM tickets WHERE rowid = ?";
	            PreparedStatement deleteTicketStmt = connection.prepareStatement(deleteTicketQuery);
	            deleteTicketStmt.setInt(1, ticketData.getTicketId()); // Assuming getTicketId() returns the rowid
	            deleteTicketStmt.executeUpdate();

	            System.out.println("Ticket deleted successfully!");

	            cj.goToPage("view/Tickets.fxml");

	        } catch (SQLException e) {
	            System.out.println("Error deleting ticket");
	            e.printStackTrace();
	        }
	    } else {
	        // User clicked Cancel or closed the dialog, do nothing
	        System.out.println("Deletion canceled");
	    }
	}


	


}




