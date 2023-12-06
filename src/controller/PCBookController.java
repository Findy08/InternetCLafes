package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;

public class PCBookController {

	private boolean isAvailable(int pcId, Date bookedDate) {
        String query = "SELECT * FROM PCBook WHERE PC_ID = ? AND BookedDate = ?";

        try (Connection connection = Database.getDB().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, pcId);
            ps.setDate(2, new java.sql.Date(bookedDate.getTime()));

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
            	return false;
            }
            else {
            	return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }
	
	public void bookPC(int userId, int pcId, Date bookedDate) {
		boolean pcAvailable = isAvailable(pcId, bookedDate);
		
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
        } else return;
    }

}
