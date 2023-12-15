package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Database;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.PC;
import model.PCBook;

public class PCBookController {

	private boolean IsAvailable(int pcId, Date bookedDate) {
        String query = "SELECT * FROM PCBook WHERE PC_ID = ? AND BookedDate = ?";

        try (Connection connection = Database.getDB().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, pcId);
            ps.setDate(2, new java.sql.Date(bookedDate.getTime()));

            ResultSet rs = ps.executeQuery();
            return !rs.next();  // Return true if no records found, indicating availability

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void AddNewBook(Integer pcId, Integer userId, Date bookedDate) {
        if (bookedDate == null || pcId == null) {
        	ShowAlert("Input can't be empty", AlertType.ERROR);
            return;
        }
        boolean pcAvailable = IsAvailable(pcId, bookedDate);
        if (pcAvailable) {
            String query = "INSERT INTO PCBook(PC_ID, UserID, BookedDate) VALUES (?, ?, ?)";
            try (Connection connection = Database.getDB().getConnection();
                 PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, pcId);
                ps.setInt(2, userId);
                ps.setDate(3, new java.sql.Date(bookedDate.getTime()));
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
        	ShowAlert("PC is not available on the chosen date", AlertType.WARNING);
            return;
        }
    }
	
    public void DeleteBookData(Integer bookID, Date chosenDate) {
    	if (chosenDate == null) {
			ShowAlert("Date must be selected", AlertType.WARNING);
	        return;
	    }
        String query = "DELETE FROM PCBOOK WHERE BookID = ? AND BookedDate = ?";
        try (Connection connection = Database.getDB().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, bookID);
            ps.setDate(2, new java.sql.Date(chosenDate.getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public PCBook GetPCBookedData(Integer pcID, Date date) {
	    PCBook pcbook = new PCBook();

	    String query = "SELECT * FROM PCBook WHERE PC_ID = ? AND BookedDate = ?";

	    try (Connection connection = Database.getDB().getConnection();
	         PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setInt(1, pcID);
	        ps.setDate(2, new java.sql.Date(date.getTime()));

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                pcbook.setBookID(rs.getInt("BookID"));
	                pcbook.setUserID(rs.getInt("UserID"));
	                pcbook.setBookedDate(rs.getDate("BookedDate"));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return pcbook;
	}

	public void AssignUserToNewPC(Integer bookID, Integer newPCID) {
		if (newPCID == null) {
	        ShowAlert("PC ID must be filled.", AlertType.ERROR);
	        return;
	    }

	    Date chosenDate = GetBookedDate(bookID);
	    if (!IsAvailable(newPCID, chosenDate)) {
	        ShowAlert("PC with ID " + newPCID + " is already booked on the chosen date of " + chosenDate, AlertType.WARNING);
	        return;
	    }
	    
	    String query = "UPDATE PCBook SET PC_ID = ? WHERE BookID = ?";
	    try (Connection connection = Database.getDB().getConnection();
	         PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setInt(1, newPCID);
	        ps.setInt(2, bookID);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	private Date GetBookedDate(Integer bookID) {
	    String query = "SELECT BookedDate FROM PCBook WHERE BookID = ?";

	    try (Connection connection = Database.getDB().getConnection();
	         PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setInt(1, bookID);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return rs.getDate("BookedDate");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	public PCBook GetPCBookedDetail(Integer bookID) {
	    PCBook pcbook = null;
	    String query = "SELECT * FROM PCBook WHERE BookID = ?";
	    try (Connection connection = Database.getDB().getConnection();
	         PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setInt(1, bookID);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                pcbook = new PCBook();
	                pcbook.setBookID(rs.getInt("BookID"));
	                pcbook.setUserID(rs.getInt("UserID"));
	                pcbook.setPC_ID(rs.getInt("PC_ID"));
	                pcbook.setBookedDate(rs.getDate("BookedDate"));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return pcbook;
	}
	
	public void FinishBook(List<PCBook> pcBookList, Date chosenDate) {
		if (chosenDate == null) {
			ShowAlert("Date must be selected", AlertType.WARNING);
	        return;
	    }
	    String finishBookQuery = "DELETE FROM PCBook WHERE BookID = ? AND BookedDate = ?";
	    
	    try (Connection connection = Database.getDB().getConnection();
	         PreparedStatement ps = connection.prepareStatement(finishBookQuery)) {
	        for (PCBook pcBook : pcBookList) {
	            ps.setInt(1, pcBook.getBookID());
	            ps.setDate(2, new java.sql.Date(chosenDate.getTime()));
	            ps.executeUpdate();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public ArrayList<PCBook> GetAllPCBookedData() {
	    ArrayList<PCBook> pcBookList = new ArrayList<>();
	    String query = "SELECT * FROM PCBook";
	    
	    try (Connection connection = Database.getDB().getConnection();
	         PreparedStatement ps = connection.prepareStatement(query);
	         ResultSet resultSet = ps.executeQuery()) {
	        while (resultSet.next()) {
	            Integer bookID = resultSet.getInt("BookID");
	            Integer pcID = resultSet.getInt("PC_ID");
	            Integer userID = resultSet.getInt("UserID");
	            Date bookedDate = resultSet.getDate("BookedDate");
	            pcBookList.add(new PCBook(bookID, pcID, userID, bookedDate));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return pcBookList;
	}
	
	public ArrayList<PCBook> GetPcBookedByDate(Date date) {
	    ArrayList<PCBook> pcBookList = new ArrayList<>();
	    
	    String query = "SELECT * FROM PCBook WHERE BookedDate = ?";

	    try (Connection connection = Database.getDB().getConnection();
	         PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setDate(1, new java.sql.Date(date.getTime()));
	        try (ResultSet resultSet = ps.executeQuery()) {
	            while (resultSet.next()) {
	                Integer bookID = resultSet.getInt("BookID");
	                Integer pcID = resultSet.getInt("PC_ID");
	                Integer userID = resultSet.getInt("UserID");
	                Date bookedDate = resultSet.getDate("BookedDate");
	                pcBookList.add(new PCBook(bookID, pcID, userID, bookedDate));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return pcBookList;
	}
	
	private void ShowAlert(String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("PC Booking System");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
