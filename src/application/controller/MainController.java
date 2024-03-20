package application.controller;

import java.io.IOException;
import java.net.URL;

import application.CommonObjs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
/**
 * This is the Main Controller class
 * The primary goal is to make sure the pages are linked and displayed when the respective buttons are clickecd
 */
public class MainController {
	
	
	CommonObjs cj = CommonObjs.getInstance();
	@FXML HBox mainBox = cj.getMainBox(); 

	
	/**This method is a function that is linked to the Home Page button via the Home.fxml page. 
	 * Once button is clicked it goes to the Home Page
	 * @return Void
	 */
	@FXML public void showHomePageOp() {
		System.out.println(mainBox);

		cj.goToPage("view/Home.fxml");
	}
	
	@FXML public void showCurrentTicketsOp() {

		cj.goToPage("view/Tickets.fxml");
	}
	/**This method is a function that is linked to the New Project button via the NewProject.fxml page. 
	 * Once button is clicked the right side of the pane goes to the New Project page. 
	 * @return Void
	 */
	@FXML public void showNewProjectPageOp() {


		cj.goToPage("view/NewProject.fxml");
		
	}
	
	//Creates a New Ticket when a user clicks on a Create a New Ticket button. Leads to New Ticket fxml page
	@FXML public void showNewTicketPageOp() {

		cj.goToPage("view/NewTicket.fxml");
		
	}
	
	@FXML public void showCurrentCommentsOp() {


		cj.goToPage("view/Comments.fxml");
		
	}
}
