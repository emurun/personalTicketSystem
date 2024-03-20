package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Comment;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class CardCommentController implements Initializable{
	@FXML
    private AnchorPane comment_form;

    @FXML
    private Label comment_name;

//    @FXML
//    private Label date;

    @FXML
    private Label comment_id;
    @FXML
    private Label ticket_name;
    
    private Comment commentData; 
    
	
    public void setData(Comment commentData) {
    	this.commentData = commentData; 
    	//maybe write ticket name here as well
    	comment_name.setText(commentData.getCommentName());
    	comment_id.setText("ID: " + String.valueOf(commentData.getCommentID()));
//    	date.setText(commentData.getCommentDate());
    	ticket_name.setText("Ticket Name: " + commentData.getTicketName());
    }
//	
	 @Override
	    public void initialize(URL location, ResourceBundle resources) {	
		 
	 }
}
