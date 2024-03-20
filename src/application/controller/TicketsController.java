package application.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import application.CommonObjs;
import application.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class TicketsController implements Initializable {

	@FXML
	private ScrollPane ticket_scrollPane;

	@FXML
	private GridPane ticket_gridPane;

	private ObservableList<Ticket> ticketList = FXCollections.observableArrayList();

	@FXML
	private TextField searchRequest;

	@FXML
	private Button searchRequestButton;
	@FXML
	private AnchorPane anchorPaneObjs;
	
	@FXML
	public void searchButtonOp() { // if user decides to click search
		performSearchOperation();

	}

	@FXML
	public void searchChangeOp() { // if user presses enter
		performSearchOperation();
	}

	CommonObjs cj = CommonObjs.getInstance();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		String sqlDisplayAll = "SELECT rowid, * FROM tickets";
		ticketDisplayCard(sqlDisplayAll);

	}

	// populating tickets information
	private ObservableList<Ticket> populateTicketList(String sqlQuery) {
		ObservableList<Ticket> listData = FXCollections.observableArrayList();

		try {
			Connection connection = cj.getGlobalConnection();
			Statement statement = connection.createStatement();

			ResultSet result = statement.executeQuery(sqlQuery);

			while (result.next()) {
				int ticketID = result.getInt("rowid");
				String projectName = result.getString("project_name");
				String ticketTitle = result.getString("ticket_title");
				String ticketDescription = result.getString("description");

				Ticket ticket = new Ticket(ticketID, projectName, ticketTitle, ticketDescription);
				listData.add(ticket);
			}

//            connection.close();

		} catch (SQLException e) {
			System.out.println("exception occured");
//			e.printStackTrace();
		}
		return listData;
	}

	public void performSearchOperation() {
		String sqlSearchQuery = "SELECT rowid, * FROM tickets WHERE project_name LIKE '%" + searchRequest.getText()
				+ "%' OR ticket_title like '%" + searchRequest.getText() + "%'";

		ticketDisplayCard(sqlSearchQuery);
	}

	public void ticketDisplayCard(String sqlString) {
		ticketList.clear();
		ticketList.addAll(populateTicketList(sqlString));
		ticket_gridPane.getChildren().clear();
		int row = 0;
		int column = 0;

		ticket_gridPane.getRowConstraints().clear();
		ticket_gridPane.getColumnConstraints().clear();
		
		if(anchorPaneObjs.getChildren().size() > 5) {
			anchorPaneObjs.getChildren().remove(5);
		}
	
		if(ticketList.size() == 0) {
			Label text = new Label("No Project or Ticket matches your search");
			AnchorPane.setTopAnchor(text, 140.0);
			anchorPaneObjs.getChildren().addAll(text);
			return;
		}
		for (int q = 0; q < ticketList.size(); q++) {
			try {
    			
				FXMLLoader load = new FXMLLoader();
				URL url = getClass().getClassLoader().getResource("view/TicketCard.fxml");

				load.setLocation(url);

				AnchorPane pane = load.load();

				CardTicketController cardC = load.getController();
				cardC.setData(ticketList.get(q));

				if (column == 3) {
					column = 0;
					row += 1;
				}

				ticket_gridPane.add(pane, column++, row);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}
