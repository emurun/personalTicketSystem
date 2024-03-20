package application.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import application.Comment;
import application.CommonObjs;
import application.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class CommentDisplayController implements Initializable {
	@FXML
	private ScrollPane comment_scrollPane;

	@FXML
	private GridPane comment_gridPane;

	private ObservableList<Comment> commentList = FXCollections.observableArrayList();
	
	@FXML
	private AnchorPane anchorPaneObjs;
	
	CommonObjs cj = CommonObjs.getInstance();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("here!");
		String sqlDisplayAll = "SELECT rowid, * FROM comments_2";
		commentDisplayCard(sqlDisplayAll);

	}
	
	private ObservableList<Comment> populateCommentList(String sqlQuery) {
		ObservableList<Comment> listData = FXCollections.observableArrayList();

		try {
			Connection connection = cj.getGlobalConnection();
			Statement statement = connection.createStatement();

			ResultSet result = statement.executeQuery(sqlQuery);
			System.out.println(result);
			while (result.next()) {
				int commentId = result.getInt("rowid");
				String comment = result.getString("comment");
//				String date = result.getString("timestamp");
				String ticket_name = result.getString("ticket_name");
				System.out.println(comment);

				Comment comm = new Comment(commentId, comment, ticket_name );
				listData.add(comm);
			}

//            connection.close();

		} catch (SQLException e) {
//			System.out.println("exception occured");
			e.printStackTrace();
		}
		return listData;
	}

	public void commentDisplayCard(String sqlString) {
		System.out.println("inside comment display card");
		commentList.clear();
		commentList.addAll(populateCommentList(sqlString));
		System.out.println(commentList.size());
		comment_gridPane.getChildren().clear();
		int row = 0;
		int column = 0;

		comment_gridPane.getRowConstraints().clear();
		comment_gridPane.getColumnConstraints().clear();
		
		if(anchorPaneObjs.getChildren().size() > 5) {
			anchorPaneObjs.getChildren().remove(5);
		}
	
		if(commentList.size() == 0) {
			Label text = new Label("No Comment");
			AnchorPane.setTopAnchor(text, 140.0);
			anchorPaneObjs.getChildren().addAll(text);
			return;
		}
		for (int q = 0; q < commentList.size(); q++) {
			try {
    			
				FXMLLoader load = new FXMLLoader();
				URL url = getClass().getClassLoader().getResource("view/CommentCard.fxml");

				load.setLocation(url);

				AnchorPane pane = load.load();

				CardCommentController cardC = load.getController();
				cardC.setData(commentList.get(q));

				if (column == 3) {
					column = 0;
					row += 1;
				}

				comment_gridPane.add(pane, column++, row);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	
	}
}
