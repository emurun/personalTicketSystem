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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NewTicketController implements Initializable {
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

	private ObservableList<String> projectList; // stores list of projects to add to choicePicker
	CommonObjs cj = CommonObjs.getInstance();

	private boolean saved;
	public Ticket ticket;
	@FXML
	public void saveTicketOp() {
		if (!saved) {
			try {
				Connection connection = cj.getGlobalConnection();
				Statement statement = connection.createStatement();
				System.out.println("connected!");
//				System.out.println(chooseProject.getValue().toString());
				String query = "insert into tickets values(?, ?, ?)";

				PreparedStatement insertStmt = connection.prepareStatement(query);
				insertStmt.setString(1, chooseProject.getValue().toString());
				insertStmt.setString(2, ticketTitle.getText());
				insertStmt.setString(3, ticketDescription.getText());

				String sql = "SELECT rowid, * FROM tickets";

				insertStmt.executeUpdate();
				ResultSet result = statement.executeQuery(sql);

				while (result.next()) {
					int id = result.getInt("rowid");
					String proj_name = result.getString("project_name");
					String ticket_title = result.getString("ticket_title");
					String ticket_description = result.getString("description");
					ticket = new Ticket(id, proj_name, ticket_title, ticket_description);
					System.out.println(id + " | " + proj_name + " | " + ticket_title + " | " + ticket_description);

				}

			} catch (SQLException e) {
				System.out.println("Error connecting");
				e.printStackTrace();
			}
			saved = true;
		}
		System.out.println("SAVED!");

	}

	@FXML
	public void addCommentOp() {
//		System.out.println("something happen?");
//		cj.goToPage("view/NewComment.fxml");
		if(!saved) {
			saveTicketOp();
			saved = true;

		}
		System.out.println("saved the ticket first");
		
		//now send this information to new comment 
		
//		URL url = getClass().getClassLoader().getResource("view/NewComment.fxml");
//
//		try {
//			AnchorPane commentPane = (AnchorPane) FXMLLoader.load(url);
//			Stage commentStage = new Stage();
//			commentStage.setTitle("Add Comment");
//			commentStage.initModality(Modality.APPLICATION_MODAL);
//			commentStage.setScene(new Scene(commentPane));
//			commentStage.showAndWait(); // Show the comment page
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		try {
			System.out.println("inside add comment op" + cj.getMainBox());
			URL url = getClass().getClassLoader().getResource("view/NewComment.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(url);

			AnchorPane pane3 = (AnchorPane) fxmlLoader.load();
			
			CommentController commentController = fxmlLoader.getController();
			commentController.initData(this.ticket); //send project data to updatecontroller
			
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

		chooseProject.setItems(projectList);

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

}
