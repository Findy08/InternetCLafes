package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.Database;
import model.PC;

public class PCController {

	public void AddNewPC(Integer PcID) {
		try(Connection connection = Database.getDB().getConnection()){
			String query = "INSERT INTO PC(PC_ID) VALUES (" + PcID + ")";
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<PC> GetAllPCData() {
		ArrayList<PC> pc = new ArrayList<PC>();
		String query = "SELECT * FROM PC";
		try(Connection connection = Database.getDB().getConnection()){
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
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
	
	public PC GetAllPCData(Integer PcID) {
		PC pc = new PC();
		String query = "SELECT * FROM Users WHERE PC_ID = " + PcID;
		try(Connection connection = Database.getDB().getConnection()){
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
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
	   	String query = "UPDATE PC SET PC_Condition = '"
	               + cond + "' WHERE PC_ID = " + PcID;
	   	try (Connection connection = Database.getDB().getConnection();
	   	  Statement statement = connection.createStatement()) { 
	   	  System.out.println(query);
	   	  statement.executeUpdate(query);
	   	} catch (SQLException e) {
	   		e.printStackTrace();
	   	}
	}
	
	public void DeletePC(Integer PcID) {
        String query = "DELETE FROM PC WHERE PC_ID = " + PcID;
        try (Connection connection = Database.getDB().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
