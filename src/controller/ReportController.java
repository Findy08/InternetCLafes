package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;
import model.Report;

public class ReportController {

	public void AddNewReport(String UserRole, Integer PcID, String ReportNote) {
		Report r = new Report();
		r.setUserRole(UserRole);
		r.setPC_ID(PcID);
		r.setReportNote(ReportNote);
		String query = "INSERT INTO Report(UserRole, PcID, ReportNote, ReportDate) VALUES (?, ?, ?, NOW())";
		try(Connection connection = Database.getDB().getConnection();
				PreparedStatement ps = connection.prepareStatement(query)){
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
				String date = resultSet.getDate("ReportDate").toString();
				r.add(new Report(id, pcid, role, note, date));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}
}
