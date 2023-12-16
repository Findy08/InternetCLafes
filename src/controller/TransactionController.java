package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.Database;
import model.TransactionHeader;
import view.HistoryView;
import model.PCBook;
import model.TransactionDetail;

public class TransactionController {

//	public void AddTransaction(Integer TransactionID, ArrayList<PCBook> PcBook, Integer StaffID) {
//		TransactionHeader th = new TransactionHeader();
//		UserController uc = new UserController();
//		String StaffName = uc.GetName(StaffID);
//		th.setTransactionID(TransactionID);
//		th.setStaffID(StaffID);
//		th.setStaffName(StaffName);
//		String query = "INSERT INTO TransactionHeader(TransactionID, StaffID, StaffName, TransactionDate) VALUES (?, ?, ?, NOW())";
//		try(Connection connection = Database.getDB().getConnection()){
//			PreparedStatement ps = connection.prepareStatement(query);
//			ps.setInt(1, th.getTransactionID());
//			ps.setInt(1, th.getStaffID());
//			ps.setString(1, th.getStaffName());
//			ps.setDate(1, th.getTransactionDate());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
//	public void AddTransactionHeader(Integer StaffID, Date transactionDate) {
//		TransactionHeader th = new TransactionHeader();
//		UserController uc = new UserController();
//		String StaffName = uc.GetName(StaffID);
//		th.setStaffID(StaffID);
//		th.setStaffName(StaffName);
//		th.setTransactionDate(transactionDate);
//		String query = "INSERT INTO TransactionHeader(StaffID, StaffName, TransactionDate) VALUES (?, ?, ?, ?)";
//		try(Connection connection = Database.getDB().getConnection()){
//			PreparedStatement ps = connection.prepareStatement(query);
//			ps.setInt(1, th.getStaffID());
//			ps.setString(1, th.getStaffName());
//			ps.setDate(1, th.getTransactionDate());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void AddTransactionDetail(Integer TransactionID, ArrayList<PCBook> PcBook) {
//		for(int i=0;i<PcBook.size();i++) {
//			TransactionDetail td = new TransactionDetail();
//			String queryDetail = "INSERT INTO TransactionDetail(TransactionID, PC_ID, CustomerName, BookedTime) VALUES (?, ?, ?, ?)";
//			PCBookController uc = new PCBookController();
//			td.setTransactionID(TransactionID);
////			Integer PC_ID = ;
////			th.setStaffID(StaffID);
////			th.setStaffName(StaffName);
////			th.setTransactionDate(transactionDate);
////			String query = "INSERT INTO TransactionHeader(StaffID, StaffName, TransactionDate) VALUES (?, ?, ?, ?)";
////			try(Connection connection = Database.getDB().getConnection()){
////				PreparedStatement ps = connection.prepareStatement(query);
////				ps.setInt(1, th.getStaffID());
////				ps.setString(1, th.getStaffName());
////				ps.setDate(1, th.getTransactionDate());
////			} catch (SQLException e) {
////				e.printStackTrace();
////			}
//		}
//	}
	
	public TransactionController(HistoryView custView, Integer uid) {
		// TODO Auto-generated constructor stub
	}
	
	public TransactionController() {
		
	}

	public void AddTransaction(Integer staffID, Date transactionDate, ArrayList<PCBook> pcBooks) {
        TransactionHeader header = AddNewTransactionHeader(staffID, transactionDate);
        if (header != null) {
            int transactionID = header.getTransactionID();
            AddTransactionDetail(transactionID, pcBooks);
        }
    }

	private TransactionHeader AddNewTransactionHeader(Integer staffID, Date transactionDate) {
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

	private void AddTransactionDetail(Integer TransactionID, ArrayList<PCBook> PcBook) {
	    try (Connection connection = Database.getDB().getConnection()) {
	        String sql = "INSERT INTO TransactionDetail (TransactionID, PC_ID, CustomerName, BookedTime) VALUES (?, ?, ?, ?)";
	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            for (PCBook pcBook : PcBook) {
	                statement.setInt(1, TransactionID);
	                statement.setInt(2, pcBook.getPC_ID());

	                UserController uc = new UserController();
	                String customerName = uc.GetName(pcBook.getUserID());
	                statement.setString(3, customerName);

	                statement.setDate(4, java.sql.Date.valueOf(pcBook.getBookedDate().toString()));
	                statement.addBatch();
	            }
	            statement.executeBatch();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public ArrayList<TransactionHeader> GetAllTransactionHeader() {
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
	
	public ArrayList<TransactionDetail> GetAllTransactionDetail(Integer TransactionID) {
		ArrayList<TransactionDetail> td = new ArrayList<TransactionDetail>();
		String query = "SELECT * FROM TransactionDetail WHERE TransactionID = ?";
		try(Connection connection = Database.getDB().getConnection()){
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, TransactionID);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				Integer id = resultSet.getInt("TransactionID");
				Integer pid = resultSet.getInt("PC_ID");
				String name = resultSet.getString("CustomerName");
				String date = resultSet.getString("BookedTime");
				td.add(new TransactionDetail(id, pid, name, date));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return td;
	}
}
