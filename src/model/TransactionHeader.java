package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controller.UserController;
import database.Database;

public class TransactionHeader {
	
	private Integer TransactionID, StaffID;
	private String StaffName;
	private Date TransactionDate;

	public Integer getTransactionID() {
		return TransactionID;
	}

	public void setTransactionID(Integer transactionID) {
		TransactionID = transactionID;
	}

	public Integer getStaffID() {
		return StaffID;
	}

	public void setStaffID(Integer staffID) {
		StaffID = staffID;
	}

	public String getStaffName() {
		return StaffName;
	}

	public void setStaffName(String staffName) {
		StaffName = staffName;
	}


	public Date getTransactionDate() {
		return TransactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		TransactionDate = transactionDate;
	}

	public TransactionHeader(Integer transactionID, Integer staffID, String staffName, Date transactionDate) {
		super();
		TransactionID = transactionID;
		StaffID = staffID;
		StaffName = staffName;
		TransactionDate = transactionDate;
	}

	public TransactionHeader() {
		// TODO Auto-generated constructor stub
	}
	
	public TransactionHeader AddNewTransactionHeader(Integer staffID, Date transactionDate) {
	    TransactionHeader header = new TransactionHeader();
	    UserController uc = new UserController();

	    String staffName = uc.GetName(staffID);
	    header.setStaffID(staffID);
	    header.setStaffName(staffName);
	    header.setTransactionDate(transactionDate);

	    String query = "INSERT INTO TransactionHeader(StaffID, StaffName, TransactionDate) VALUES (?, ?, ?)";
	    try (Connection connection = Database.getDB().getConnection();
	         PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

	        ps.setInt(1, header.getStaffID());
	        ps.setString(2, header.getStaffName());
	        ps.setDate(3, new java.sql.Date(header.getTransactionDate().getTime()));

	        int rowsAffected = ps.executeUpdate();

	        if (rowsAffected > 0) {
	            ResultSet generatedKeys = ps.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                header.setTransactionID(generatedKeys.getInt(1));
	                return header;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	public ArrayList<TransactionHeader> GetAllTransactionHeaderData() {
		ArrayList<TransactionHeader> th = new ArrayList<TransactionHeader>();
		String query = "SELECT * FROM TransactionHeader";
		try(Connection connection = Database.getDB().getConnection()){
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				Integer id = resultSet.getInt("TransactionID");
				Integer sid = resultSet.getInt("StaffID");
				String name = resultSet.getString("StaffName");
				Date date = resultSet.getDate("TransactionDate");
				th.add(new TransactionHeader(id, sid, name, date));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return th;
	}

}
