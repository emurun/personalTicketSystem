package application.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import application.CommonObjs;
import application.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

//This class displays projects stored in the database onto the home page.
public class HomeController implements Initializable {
    
    @FXML
    private ScrollPane project_scrollPane;

    @FXML
    private GridPane project_gridPane;
    
    @FXML
    private TextField searchRequestProject;

    @FXML
    private Button searchRequestProjectButton;
    
    @FXML
	private AnchorPane anchorPaneProjObs;
    
    @FXML
    void searchButtonProjectOp() {
    	performSearchProjectOperation();
    }

	@FXML
    void searchChangeProjectOp() {
    	performSearchProjectOperation();
    }

    private ObservableList<Project> projectList = FXCollections.observableArrayList();
    CommonObjs cj = CommonObjs.getInstance();
    
   //Displays all current projects on Home/Projects Page
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	String sqlDisplayAll = "SELECT rowid, name, date_start, project_details, status FROM projects";
		projectDisplayCard(sqlDisplayAll);
    }
    
    //Returns a list of current projects from DB
    private ObservableList<Project> projectGetData(String sqlQuery) {
		ObservableList<Project> listData = FXCollections.observableArrayList();

        try {
			Connection connection = cj.getGlobalConnection();
            Statement statement = connection.createStatement();

            
            ResultSet result = statement.executeQuery(sqlQuery);

            while (result.next()) {
            	int id = result.getInt("rowid");
                String name = result.getString("name");
                String startDate = result.getString("date_start");
                String projectDetails = result.getString("project_details");
                String status = result.getString("status");

                Project project = new Project(name, startDate, projectDetails, status);
                project.setId(id);
                listData.add(project);
            }

        } catch (SQLException e) {
            System.out.println("Error connecting to the database");
            e.printStackTrace();
        }
        
        return listData;
    }
    
    //Displays projects in cards form after executing certain SQL query
    public void projectDisplayCard(String sqlString) {
    	projectList.clear(); 
    	projectList.addAll(projectGetData(sqlString));
    	project_gridPane.getChildren().clear();
    	
    	int row = 0; 
    	int column = 0;
    	
    	project_gridPane.getRowConstraints().clear();
    	project_gridPane.getColumnConstraints().clear();
    	
    	//If multiple searches are done and the last search was no match, it removes the last child "no match" text and displays new found projects
		if(anchorPaneProjObs.getChildren().size() > 5) {
			anchorPaneProjObs.getChildren().remove(5);
		}
    	
		//No match was found when a user searches for a project name
		if(projectList.size() == 0) {
			Label text = new Label("No Project matches your search");
			AnchorPane.setTopAnchor(text, 140.0);
			anchorPaneProjObs.getChildren().addAll(text);
			return;
		}
    	
		//Creates and loads new project cards 
    	for (int q = 0; q < projectList.size(); q++) {
    		try {
   			
    			FXMLLoader load = new FXMLLoader();
    			URL url = getClass().getClassLoader().getResource("view/projectCard.fxml");

    		
    			load.setLocation(url);
    		    			
    			AnchorPane pane = load.load();
    		
    			CardProjectController cardC = load.getController();
    			cardC.setData(projectList.get(q));
    			
        		
    			if (column == 3) {
    				column = 0; 
    				row +=1;
    			}
    			
    			project_gridPane.add(pane, column++, row);
        		
    		} catch(Exception e){
    			e.printStackTrace();
    		}
    		
    	}
    }
    
    //Performs search operation when user clicks on enter or search button
    private void performSearchProjectOperation() {
    	String sqlSearchProjectQuery = "SELECT rowid,  * FROM projects WHERE name LIKE '%" + searchRequestProject.getText()
		+ "%'";
    	
    	//Performs sql query with a user's search and displays match projects
    	projectDisplayCard(sqlSearchProjectQuery);
		
	}
    
   
 }
