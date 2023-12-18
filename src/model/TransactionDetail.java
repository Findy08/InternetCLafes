package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.UserController;
import database.Database;

public class TransactionDetail {
	
	private Integer TransactionID, PC_ID;
	private String CustomerName, BookedTime;

	public Integer getTransactionID() {
		return TransactionID;
	}

	public void setTransactionID(Integer transactionID) {
		TransactionID = transactionID;
	}

	public Integer getPC_ID() {
		return PC_ID;
	}

	public void setPC_ID(Integer pC_ID) {
		PC_ID = pC_ID;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}

	public String getBookedTime() {
		return BookedTime;
	}

	public void setBookedTime(String bookedTime) {
		BookedTime = bookedTime;
	}

	public TransactionDetail(Integer transactionID, Integer pC_ID, String customerName, String bookedTime) {
		super();
		TransactionID = transactionID;
		PC_ID = pC_ID;
		CustomerName = customerName;
		BookedTime = bookedTime;
	}

	public TransactionDetail() {
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<TransactionDetail> GetUserTransactionDetail(Integer userID) {
		UserController uc = new UserController();
		String custName = uc.GetName(userID);
		ArrayList<TransactionDetail> transactionDetails = new ArrayList<>();
        try (Connection connection = Database.getDB().getConnection()) {
            String sql = "SELECT * FROM TransactionDetail WHERE CustomerName = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, custName);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        TransactionDetail transactionDetail = new TransactionDetail();
                        transactionDetail.setTransactionID(resultSet.getInt("TransactionID"));
                        transactionDetail.setPC_ID(resultSet.getInt("PC_ID"));
                        transactionDetail.setCustomerName(resultSet.getString("CustomerName"));
                        transactionDetail.setBookedTime(resultSet.getDate("BookedTime").toString());

                        transactionDetails.add(transactionDetail);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionDetails;
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
	
	public void AddTransactionDetail(Integer TransactionID, ArrayList<PCBook> PcBook) {
		UserController uc = new UserController();
		ArrayList<String> names = new ArrayList<>();
		for (PCBook pcb : PcBook) {
			names.add(uc.GetName(pcb.getUserID()));
		}
		int index=0;
	    try (Connection connection = Database.getDB().getConnection()) {
	        String sql = "INSERT INTO TransactionDetail (TransactionID, PC_ID, CustomerName, BookedTime) VALUES (?, ?, ?, ?)";
	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            for (PCBook pcBook : PcBook) {
	                try {
	                    statement.setInt(1, TransactionID);
	                    statement.setInt(2, pcBook.getPC_ID());
	                    statement.setString(3, names.get(index));
	                    index++;
	                    statement.setDate(4, java.sql.Date.valueOf(pcBook.getBookedDate().toString()));
	                    statement.executeUpdate();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}
