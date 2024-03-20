package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.CommonObjs;
import application.Project;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class CardProjectController implements Initializable {
	@FXML
	private AnchorPane project_form;

	@FXML
	private Label proj_date;

	@FXML
	private Label proj_desc;

	@FXML
	private Label proj_name;

	@FXML
	private Label proj_status;

	private Project projData;

	@FXML
	private Button editButton;
	CommonObjs cj = CommonObjs.getInstance();

	@FXML
	HBox mainBox;

	public void setData(Project projData) {
		this.projData = projData;

		proj_name.setText(projData.getProjectName());
		proj_date.setText(String.valueOf(projData.getProjectDate()));
		proj_desc.setText(projData.getProjectDesc());
		proj_status.setText(projData.getProjectStatus());

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mainBox = cj.getMainBox();
	}

	@FXML
	public void editButtonOp() {

		try {
			System.out.println("inside editButton op" + cj.getMainBox());
			URL url = getClass().getClassLoader().getResource("view/UpdateProject.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(url);

			AnchorPane pane3 = (AnchorPane) fxmlLoader.load();
			
			UpdateProjectController updateController = fxmlLoader.getController();
			updateController.initData(this.projData); //send project data to updatecontroller
			
			if (cj.getMainBox().getChildren().size() > 1)
				cj.getMainBox().getChildren().remove(1);
			
			cj.getMainBox().getChildren().add(pane3);
			
			
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
