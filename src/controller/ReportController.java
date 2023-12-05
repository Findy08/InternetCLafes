package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.Database;
import model.Report;

public class ReportController {

	public void AddNewReport(String UserRole, Integer PcID, String ReportNote) {
		try(Connection connection = Database.getDB().getConnection()){
			String query = "INSERT INTO Report(UserRole, PcID, ReportNote) VALUES (" + "'" + UserRole + "'" + "," +  PcID + "," + "'" + ReportNote + "'" + ")";
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Report> GetAllUserData() {
		ArrayList<Report> r = new ArrayList<Report>();
		String query = "SELECT * FROM Report";
		try(Connection connection = Database.getDB().getConnection()){
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
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
