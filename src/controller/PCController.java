package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import database.Database;
import javafx.scene.control.Alert;
import model.PC;

public class PCController {

	public void AddNewPC(Integer PcID) {
	    if (PcID == null) {
	        showAlert("Invalid PC ID", "Please provide a valid PC ID.", Alert.AlertType.ERROR);
	        return;
	    }
	    if (IsExist(PcID)) {
	        showAlert("Duplicate PC", "A PC with the provided ID already exists.", Alert.AlertType.ERROR);
	        return;
	    }
		PC pc = new PC();
		pc.setPC_ID(PcID);
		String query = "INSERT INTO PC(PC_ID) VALUES (?)";
		try(Connection connection = Database.getDB().getConnection()){
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, pc.getPC_ID());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private boolean IsExist(Integer pcID) {
	    String query = "SELECT COUNT(*) FROM PC WHERE PC_ID = ?";
	    try (Connection connection = Database.getDB().getConnection();
	         PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setInt(1, pcID);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            return count > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	public ArrayList<PC> GetAllPCData() {
		ArrayList<PC> pc = new ArrayList<PC>();
		String query = "SELECT * FROM PC";
		try(Connection connection = Database.getDB().getConnection()){
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				Integer id = resultSet.getInt("PC_ID");
				String cond = resultSet.getString("PC_Condition");
				pc.add(new PC(id, cond));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pc;
	}
	
	public PC GetPCDetail(Integer PcID) {
		PC pc = new PC();
		String query = "SELECT * FROM PC WHERE PC_ID = ?";
		try(Connection connection = Database.getDB().getConnection()){
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, PcID);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()) {
				pc.setPC_ID(resultSet.getInt("PC_ID"));
				pc.setPC_Condition(resultSet.getString("PC_Condition"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pc;
	}
	
	public void UpdatePCCondition(Integer PcID, String cond) {
	    if (PcID == null) {
	        showAlert("Invalid Selection", "Please choose a PC.", Alert.AlertType.ERROR);
	        return;
	    }

	    ArrayList<String> validConditions = new ArrayList<>(Arrays.asList("Usable", "Maintenance", "Broken"));
	    if (!validConditions.contains(cond)) {
	        showAlert("Invalid PC Condition", "Must be either 'Usable', 'Maintenance', or 'Broken'.", Alert.AlertType.ERROR);
	        return;
	    }
	    
	   	String query = "UPDATE PC SET PC_Condition = ? WHERE PC_ID = ?";
	   	try {
	   		Connection connection = Database.getDB().getConnection();
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, cond);
			ps.setInt(2, PcID);
			ps.executeUpdate();
	   	} catch (SQLException e) {
	   		e.printStackTrace();
	   	}
	}
	
	public void DeletePC(Integer PcID) {
		if (PcID == null) {
			showAlert("Invalid Selection", "Please choose a PC.", Alert.AlertType.ERROR);
	        return;
	    }
	
        String query = "DELETE FROM PC WHERE PC_ID = ?";
        try (Connection connection = Database.getDB().getConnection();
        		PreparedStatement ps = connection.prepareStatement(query)) {
        	ps.setInt(1, PcID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
