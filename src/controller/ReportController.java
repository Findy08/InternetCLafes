package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;
import javafx.scene.control.Alert;
import model.Report;
import view.ReportView;

public class ReportController {

	public ReportController(ReportView custView, Integer uid) {
		// TODO Auto-generated constructor stub
	}

	public void AddNewReport(String UserRole, Integer PcID, String ReportNote) {
        if (PcID == null || ReportNote == null || ReportNote.isEmpty()) {
        	ShowAlert("Input can't be empty", Alert.AlertType.ERROR);
            return;
        }
        if (!IsPCExists(PcID)) {
        	ShowAlert("PC with ID " + PcID + " does not exist", Alert.AlertType.ERROR);
            return;
        }
        Report r = new Report();
        r.setUserRole(UserRole);
        r.setPC_ID(PcID);
        r.setReportNote(ReportNote);

        String query = "INSERT INTO Report(UserRole, PcID, ReportNote, ReportDate) VALUES (?, ?, ?, NOW())";

        try (Connection connection = Database.getDB().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, r.getUserRole());
            ps.setInt(2, r.getPC_ID());
            ps.setString(3, r.getReportNote());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public ArrayList<Report> GetAllUserData() {
		ArrayList<Report> r = new ArrayList<Report>();
		String query = "SELECT * FROM Report";
		try(Connection connection = Database.getDB().getConnection()){
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				Integer id = resultSet.getInt("Report_ID");
				String role = resultSet.getString("UserRole");
				Integer pcid = resultSet.getInt("PC_ID");
				String note = resultSet.getString("ReportNote");
				Date date = resultSet.getDate("ReportDate");
				r.add(new Report(id, pcid, role, note, date));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}
	
	private void ShowAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("PC Booking System");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

	private boolean IsPCExists(Integer pcID) {
	    String query = "SELECT COUNT(*) FROM PC WHERE PC_ID = ?";
	    try (Connection connection = Database.getDB().getConnection();
	         PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setInt(1, pcID);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                int count = rs.getInt(1);
	                return count > 0;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; 
	}

}
