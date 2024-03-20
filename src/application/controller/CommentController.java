package application.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import application.CommonObjs;
import application.Ticket;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class CommentController implements Initializable {

    @FXML
    private TextArea commentTextArea;
    
    private Ticket ticket;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    public void initData(Ticket ticket) {
    	this.ticket = ticket;
    	
    	System.out.println(ticket.getTicketTitle());
    }


    public void saveComment() {
        String comment = commentTextArea.getText();

        if (comment.isEmpty()) {
            System.out.println("Comment is empty. Please enter a comment.");
            return; // Won't proceed with an empty comment
        }

		CommonObjs cj = CommonObjs.getInstance();

        try {
			Connection connection = cj.getGlobalConnection();

            // Disable auto-commit (was giving error and not letting it save into database otherwise)
            connection.setAutoCommit(false);

            // Corrected SQL statement to insert data into the "comments" table
            String query = "INSERT INTO comments_2 (comment, timestamp, ticket_name, ticket_id, project_name) VALUES (?, ?, ?, ?, ?)";
//            System.out.println(ticket.getTicketTitle());
//            String query = "INSERT INTO comments (comment, timestamp) VALUES (?, ?)";

            PreparedStatement insertStmt = connection.prepareStatement(query);
            insertStmt.setString(1, comment);
            insertStmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            insertStmt.setString(3, this.ticket.getTicketTitle());
            insertStmt.setInt(4, this.ticket.getTicketId());
            insertStmt.setString(5, this.ticket.getProjectName());
            
            int rowsAffected = insertStmt.executeUpdate();

            if (rowsAffected > 0) {
                // Commit the changes
                connection.commit();
                connection.setAutoCommit(true); // Re-enable auto-commit
//                connection.close();
                System.out.println("Comment saved successfully.");

//                /
            } else {
                // Roll back the transaction in case of failure
                connection.rollback();
                connection.setAutoCommit(true); // Re-enable auto-commit
//                connection.close();
                System.out.println("Failed to insert comment.");
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to the database");
            e.printStackTrace();
        }
    }



}

