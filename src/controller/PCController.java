package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;
import model.PC;

public class PCController {

	public void AddNewPC(Integer PcID) {
		PC pc = new PC();
		pc.setPC_ID(PcID);
		String query = "INSERT INTO PC(PC_ID) VALUES (?)";
		try(Connection connection = Database.getDB().getConnection()){
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, pc.getPC_ID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
        String query = "DELETE FROM PC WHERE PC_ID = ?";
        try (Connection connection = Database.getDB().getConnection();
        		PreparedStatement ps = connection.prepareStatement(query)) {
        	ps.setInt(1, PcID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
