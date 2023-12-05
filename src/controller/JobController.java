package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;
import model.Job;

public class JobController {

	public void AddNewJob(Integer UserID, Integer PcID) {
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
	
	public void UpdateRoleStatus(Integer JobID, String JobStatus) {
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
	
	//GetPcOnWorkingList belom, ga paham soalnya

}
