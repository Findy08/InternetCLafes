package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;
import javafx.scene.control.Alert;

public class Report {
	
	private Integer Report_ID, PC_ID;
	private String UserRole, ReportNote;
	private Date ReportDate;

	public Report(Integer report_ID, Integer pC_ID, String userRole, String reportNote, Date reportDate) {
		super();
		Report_ID = report_ID;
		PC_ID = pC_ID;
		UserRole = userRole;
		ReportNote = reportNote;
		ReportDate = reportDate;
	}

	public Report() {
		// TODO Auto-generated constructor stub
	}

	public Integer getReport_ID() {
		return Report_ID;
	}

	public void setReport_ID(Integer report_ID) {
		Report_ID = report_ID;
	}

	public Integer getPC_ID() {
		return PC_ID;
	}

	public void setPC_ID(Integer pC_ID) {
		PC_ID = pC_ID;
	}

	public String getUserRole() {
		return UserRole;
	}

	public void setUserRole(String userRole) {
		UserRole = userRole;
	}

	public String getReportNote() {
		return ReportNote;
	}

	public void setReportNote(String reportNote) {
		ReportNote = reportNote;
	}

	public Date getReportDate() {
		return ReportDate;
	}

	public void setReportDate(Date reportDate) {
		ReportDate = reportDate;
	}
	
	public void AddNewReport(String UserRole, Integer PcID, String ReportNote) {
        if (PcID == null || ReportNote == null || ReportNote.isEmpty()) {
        	showAlert("Input Error", "Input can't be empty", Alert.AlertType.ERROR);
            return;
        }
        if (!IsPCExists(PcID)) {
        	showAlert("Input Error", "PC with ID " + PcID + " does not exist", Alert.AlertType.ERROR);
            return;
        }
        Report r = new Report();
        r.setUserRole(UserRole);
        r.setPC_ID(PcID);
        r.setReportNote(ReportNote);

        String query = "INSERT INTO Report(UserRole, PC_ID, ReportNote, ReportDate) VALUES (?, ?, ?, NOW())";

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
	
	public ArrayList<Report> GetAllReportData() {
		ArrayList<Report> reports = new ArrayList<Report>();
		String query = "SELECT * FROM Report";
		try(Connection connection = Database.getDB().getConnection()){
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				Integer Pid = resultSet.getInt("PC_ID");
				Integer Rid = resultSet.getInt("ReportID");
				String role = resultSet.getString("UserRole");
				String note = resultSet.getString("ReportNote");
				Date date = resultSet.getDate("ReportDate");
				reports.add(new Report(Rid, Pid, role, note, date));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reports;
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
	
	private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
