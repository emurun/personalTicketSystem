package application.controller;

import javax.print.DocFlavor.URL;

import application.CommonObjs;
import application.Ticket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class CardTicketController {
    @FXML
    private AnchorPane ticket_form;

    @FXML
    private Label ticket_name;

    @FXML
    private Label proj_name;

    @FXML
    private Label ticket_description;

    @FXML
    private Label ticket_id;

    @FXML
    private Button editButton;

    private Ticket ticketData;
    CommonObjs cj = CommonObjs.getInstance();

    public void setData(Ticket ticketData) {
        this.ticketData = ticketData;

        proj_name.setText(ticketData.getProjectName());
        ticket_id.setText(String.valueOf(ticketData.getTicketId()));
        ticket_description.setText(ticketData.getTicketDesc());
        ticket_name.setText(ticketData.getTicketTitle());
    }

    @FXML
    public void editButtonOp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("view/UpdateTicket.fxml"));
            AnchorPane pane3 = fxmlLoader.load();

            UpdateTicketController updateController = fxmlLoader.getController();
            updateController.initData(this.ticketData); // Send ticket data to UpdateTicketController

            if (cj.getMainBox().getChildren().size() > 1)
                cj.getMainBox().getChildren().remove(1);

            cj.getMainBox().getChildren().add(pane3);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    
    
    
}


