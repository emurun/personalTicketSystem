package application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ticket {
	private Integer ticketID;
	private String projectName;
    private String ticketTitle;
    private String ticketDesc;
    
    public Ticket (Integer ticketID, String projectName, String ticketTitle, String ticketDesc) {
        this.ticketID = ticketID;
        this.projectName = projectName;
        this.ticketTitle = ticketTitle;
        this.ticketDesc = ticketDesc;
        
    }
    
    public Integer getTicketId() {
        return ticketID;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getTicketTitle() {
        return ticketTitle;
    }

    public String getTicketDesc() {
        return ticketDesc;
    }

	public void setTicketTitle(String updatedTitle) {
		// TODO Auto-generated method stub
		
	}

	public void setTicketDesc(String updatedDescription) {
		// TODO Auto-generated method stub
		
	}
	
	
    
//    
//    @Override
//    public String toString() {
//        return ticketTitleProperty().get(); //ticketTitleProperty returns StringProperty and string can be accessed with .get() call
//    }
//    
    
}