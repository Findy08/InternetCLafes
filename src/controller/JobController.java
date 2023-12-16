package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import database.Database;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Job;
import model.PC;
import view.AdminPCUpdateView;
import view.AdminPCView;
import view.AdminUpdateJobView;
import view.CompleteJobView;
import view.TechnicianJobView;
import view.TechnicianPCView;
import view.ViewAllJob;

public class JobController {
	
	private Integer uid;
	private Stage primaryStage;
	private TechnicianJobView tech;
	private ViewAllJob vaj;
	private AdminPCView adminPCView;
	private CompleteJobView comp;
	private AdminUpdateJobView aujv;
	

	public JobController(TechnicianJobView tech, Integer uid) {
		this.tech = tech;
		this.uid = uid;
		initializeJob();
		loadTableDataJob();
	}
	
	public JobController(ViewAllJob vaj, Integer uid) {
		this.vaj = vaj;
		this.uid = uid;
		initializeAdminHandler();
		loadAllJob();
	}
	
	public JobController(AdminUpdateJobView aujv, Integer uid) {
		this.aujv = aujv;
		this.uid = uid;
		initializeAdminUpdateHandler();
		loadAllJobUpdate();
	}

	public void initializeJob() {

		tech.getBackButton().setOnAction(event -> {
			primaryStage = tech.getPrimaryStage();
    		TechnicianPCView tech = new TechnicianPCView(primaryStage, uid);
    		PCController p = new PCController(tech, uid);
        });
	}
	
	public void initializeAdminUpdateHandler() {
		aujv.getBackButton().setOnAction(event -> {
			primaryStage = aujv.getPrimaryStage();
			ViewAllJob vaj = new ViewAllJob(primaryStage, uid);
    		JobController j = new JobController(vaj, uid);
        });
		
		aujv.getUpdateButton().setOnAction(event -> {
			try {
				UpdateJobStatus(Integer.parseInt(aujv.getIdInput().getText()), aujv.getStatusInput().getText().toString());
				loadAllJobUpdate();
            } catch(RuntimeException e) {
            	ShowAlert("Input All Fields", "Please input all fields", Alert.AlertType.ERROR);
            }
		});
	}
	
	public void initializeAdminHandler() {
		vaj.getBackButton().setOnAction(event -> {
			primaryStage = vaj.getPrimaryStage();
			AdminPCView apv = new AdminPCView(primaryStage, uid);
    		PCController p = new PCController(apv, uid);
        });
		
		vaj.getAddButton().setOnAction(event -> {
			try {
				String id = vaj.getIdInput().getText();
				String userid = vaj.getUserInput().getText();
				String status = vaj.getStatusInput().getText();
				
                AddNewJob(Integer.parseInt(userid), Integer.parseInt(id), status);
                loadAllJob();
            } catch(RuntimeException e) {
            	ShowAlert("Input Invalid", "Please input all fields", Alert.AlertType.ERROR);
            }
		});
		
		vaj.getUpdateButton().setOnAction(event -> {
			primaryStage = vaj.getPrimaryStage();
    		AdminUpdateJobView aujv = new AdminUpdateJobView(primaryStage, uid);
    		JobController j = new JobController(aujv, uid);
		});
	}

	void loadAllJob() {
		ArrayList<Job> job = GetAllJobData();
		vaj.getTable().getItems().setAll(job);
	}
	
	void loadAllJobUpdate() {
		ArrayList<Job> job = GetAllJobData();
		aujv.getTable().getItems().setAll(job);
	}
	
	void loadTableDataJob() {
		ArrayList<Job> job = GetAllJobData();
		tech.getTable().getItems().setAll(job);
	}

	public JobController(CompleteJobView comp, Integer uid) {
		this.comp = comp;
		this.uid = uid;
		initializeFinishJob();
		loadTableDataFinishJob();
	}

	public void initializeFinishJob() {
		comp.getCompleteButton().setOnAction(event -> {
			Integer id = Integer.parseInt(comp.getIdInput().getText());
			UpdateJobStatus(id, "Complete");
			loadTableDataFinishJob();
		});

		comp.getBackButton().setOnAction(event -> {
			primaryStage = comp.getPrimaryStage();
    		TechnicianPCView tech = new TechnicianPCView(primaryStage, uid);
    		PCController p = new PCController(tech, uid);
        });
	}
	
	void loadTableDataFinishJob() {
		ArrayList<Job> job = GetUncompleteJobData();
		comp.getTable().getItems().setAll(job);
	}

	public void AddNewJob(Integer UserID, Integer PcID, String status) {
		if (UserID == null || PcID == null) {
            ShowAlert("Invalid Input", "Please provide valid User ID and PC ID.", Alert.AlertType.ERROR);
            return;
        }
		if (!isTechnician(UserID)) {
	        ShowAlert("Invalid User Role", "User must have the role 'Technician'.", Alert.AlertType.ERROR);
	        return;
	    }
		if (!IsPCExist(PcID)) {
	        ShowAlert("Invalid PC ID", "PC with ID " + PcID + " does not exist.", Alert.AlertType.ERROR);
	        return;
	    }
		 if (!isValidJobStatus(status)) {
		        ShowAlert("Invalid Job Status", "Job status must be either 'Complete' or 'UnComplete'.", Alert.AlertType.ERROR);
		        return;
		    }
		
		Job j = new Job();
		j.setUserID(UserID);
	    j.setPC_ID(PcID);		
	    j.setJobStatus(status);
	    String query = "INSERT INTO Job(UserID, PC_ID, JobStatus) VALUES (?, ?, ?)";
		try(Connection connection = Database.getDB().getConnection();
			PreparedStatement ps = connection.prepareStatement(query)){
				ps.setInt(1, j.getUserID());
				ps.setInt(2, j.getPC_ID());
				ps.setString(3, j.getJobStatus());
				ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isValidJobStatus(String status) {
	    return status != null && (status.equals("Complete") || status.equals("UnComplete"));
	}
	
	private boolean isTechnician(int userID) {
	    String query = "SELECT COUNT(*) FROM Users WHERE UserID = ? AND UserRole = 'Technician'";

	    try (Connection connection = Database.getDB().getConnection();
	         PreparedStatement ps = connection.prepareStatement(query)) {

	        ps.setInt(1, userID);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            int count = rs.getInt(1);
	            return count > 0;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}
	
	private boolean IsPCExist(Integer PcID) {
	    String query = "SELECT COUNT(*) FROM PC WHERE PC_ID = ?";
	    try (Connection connection = Database.getDB().getConnection();
	         PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setInt(1, PcID);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            return count > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	public ArrayList<Job> GetAllJobData() {
		ArrayList<Job> j = new ArrayList<Job>();
		String query = "SELECT * FROM Job";
		try(Connection connection = Database.getDB().getConnection()){
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				Integer id = resultSet.getInt("Job_ID");
				Integer uID = resultSet.getInt("UserID");
				Integer pcID = resultSet.getInt("PC_ID");
				String stat = resultSet.getString("JobStatus");
				j.add(new Job(id, uID, pcID, stat));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return j;
	}
	
	public ArrayList<Job> GetUncompleteJobData() {
		ArrayList<Job> j = new ArrayList<Job>();
		String query = "SELECT * FROM Job WHERE JobStatus = 'UnComplete'";
		try(Connection connection = Database.getDB().getConnection()){
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				Integer id = resultSet.getInt("Job_ID");
				Integer uID = resultSet.getInt("UserID");
				Integer pcID = resultSet.getInt("PC_ID");
				String stat = resultSet.getString("JobStatus");
				j.add(new Job(id, uID, pcID, stat));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return j;
	}
	
	public ArrayList<Job> GetTechnicianJob(Integer UserID) {
		if (UserID == null) {
            ShowAlert("Invalid Input", "Please provide a valid User ID.", Alert.AlertType.ERROR);
            return new ArrayList<>();
        }
		ArrayList<Job> user = new ArrayList<Job>();
		String query = "SELECT * FROM Job WHERE UserID = ?";
		try(Connection connection = Database.getDB().getConnection()){
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, UserID);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				Integer id = resultSet.getInt("Job_ID");
				Integer pcid = resultSet.getInt("PC_ID");
				String stat = resultSet.getString("JobStatus");
				user.add(new Job(id, UserID, pcid, stat));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public void UpdateJobStatus(Integer JobID, String JobStatus) {
		ArrayList<String> validJobStatus = new ArrayList<>(Arrays.asList("Complete", "UnComplete"));
		if (!validJobStatus.contains(JobStatus)) {
			ShowAlert("Invalid Job Status", "Must be either 'Complete' or 'UnComplete'.", Alert.AlertType.ERROR);
		    return;
		}
		
	   	String query = "UPDATE Job SET JobStatus = ? WHERE Job_ID = ?";
	   	try (Connection connection = Database.getDB().getConnection();
	   		PreparedStatement ps = connection.prepareStatement(query)){
	   		ps.setString(1, JobStatus);
			ps.setInt(2, JobID);
	   		ps.executeUpdate();
	   	} catch (SQLException e) {
	   		e.printStackTrace();
	   	}
   }
	
	public PC GetPcOnWorkingList(Integer pcID) {
	    PC pc = null;

	    String query = "SELECT PC.* FROM PC " +
	                   "INNER JOIN Job ON PC.PC_ID = Job.PC_ID " +
	                   "WHERE Job.JobStatus = 'UnComplete' AND PC.PC_ID = ?";

	    try (Connection connection = Database.getDB().getConnection();
	         PreparedStatement ps = connection.prepareStatement(query)) {

	        ps.setInt(1, pcID);

	        try (ResultSet resultSet = ps.executeQuery()) {
	            if (resultSet.next()) {
	                Integer retrievedPCID = resultSet.getInt("PC_ID");
	                String condition = resultSet.getString("PC_Condition");
	                pc = new PC(retrievedPCID, condition);
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return pc;
	}
	
	private void ShowAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
	

}
