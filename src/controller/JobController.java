package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import database.Database;
import javafx.scene.control.Alert;
import model.Job;

public class JobController {

	public void AddNewJob(Integer UserID, Integer PcID) {
		if (UserID == null || PcID == null) {
            ShowAlert("Invalid Input", "Please provide valid User ID and PC ID.", Alert.AlertType.ERROR);
            return;
        }
		if (!IsTechnician(UserID)) {
	        ShowAlert("Invalid User Role", "User must have the role 'Technician'.", Alert.AlertType.ERROR);
	        return;
	    }
		if (!IsPCExist(PcID)) {
	        ShowAlert("Invalid PC ID", "PC with ID " + PcID + " does not exist.", Alert.AlertType.ERROR);
	        return;
	    }
		Job j = new Job();
		j.setUserID(UserID);
	    j.setPC_ID(PcID);		
	    String query = "INSERT INTO Job(UserID, PC_ID) VALUES (?, ?)";
		try(Connection connection = Database.getDB().getConnection();
			PreparedStatement ps = connection.prepareStatement(query)){
				ps.setInt(1, j.getUserID());
				ps.setInt(2, j.getPC_ID());
				ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private boolean IsTechnician(Integer UserID) {
	    String query = "SELECT COUNT(*) FROM Users WHERE UserID = ? AND UserRole = 'Technician'";

	    try (Connection connection = Database.getDB().getConnection();
	         PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setInt(1, UserID);
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
		
	   	String query = "UPDATE Users SET JobStatus = ? WHERE JobID = ?";
	   	try (Connection connection = Database.getDB().getConnection();
	   		PreparedStatement ps = connection.prepareStatement(query)){
	   		ps.setString(1, JobStatus);
			ps.setInt(2, JobID);
	   		ps.executeUpdate();
	   	} catch (SQLException e) {
	   		e.printStackTrace();
	   	}
   }
	
	private void ShowAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
	

}
