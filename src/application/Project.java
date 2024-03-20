package application;

// Goes with HomeController to fetch all the data.
public class Project {
    private String name; 
    private String startDate; 
    private String projectDesc; 
    private String status; 
    private int id;

    public Project(String name, String startDate, String projectDesc, String status) {
        this.name = name;
        this.startDate = startDate;
        this.projectDesc = projectDesc;
        this.status = status;
    }
    
    //Accessors for Project
    
	public String getProjectName() {
		return name;
	}

	public String getProjectDate() {
		return startDate;
	}


	public String getProjectDesc() {
		return projectDesc;
	}
	
	public String getProjectStatus() {
		return status;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return this.id;
	}
	
	
}

