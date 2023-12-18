package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import database.Database;
import javafx.scene.control.Alert;

public class Job {
	
	private Integer Job_ID, UserID, PC_ID;
	private String JobStatus;

	public Job(Integer job_ID, Integer userID, Integer pC_ID, String jobStatus) {
		super();
		Job_ID = job_ID;
		UserID = userID;
		PC_ID = pC_ID;
		JobStatus = jobStatus;
	}

	public Job() {
		// TODO Auto-generated constructor stub
	}

	public Integer getJob_ID() {
		return Job_ID;
	}

	public void setJob_ID(Integer job_ID) {
		Job_ID = job_ID;
	}

	public Integer getUserID() {
		return UserID;
	}

	public void setUserID(Integer userID) {
		UserID = userID;
	}

	public Integer getPC_ID() {
		return PC_ID;
	}

	public void setPC_ID(Integer pC_ID) {
		PC_ID = pC_ID;
	}

	public String getJobStatus() {
		return JobStatus;
	}

	public void setJobStatus(String jobStatus) {
		JobStatus = jobStatus;
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
