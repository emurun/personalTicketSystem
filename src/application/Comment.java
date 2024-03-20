package application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Comment {
	private Integer commentID;
	private String commentName;
    private String date;
    private String ticket_name;
    public Comment (Integer commentID, String name, String ticket_name) {
    	this.commentID = commentID;
        this.commentName = name;
        this.ticket_name = ticket_name;
    }
    public Integer getCommentID() {
    	return commentID;
    }
    
    public String getCommentName() {
        return commentName;
    }

    public String getCommentDate() {
        return date;
    }
    public String getTicketName() {
    	return this.ticket_name;
    }
    
}
