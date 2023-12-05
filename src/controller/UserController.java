package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.Database;
import model.User;

public class UserController {

	public void AddNewUser(String username, String password, Integer age) {
		User u = new User();
		String query = "INSERT INTO Users(UserName, UserPassword, UserAge, UserRole) VALUES (?, ?, ?, ?)";
		try(Connection connection = Database.getDB().getConnection();
			PreparedStatement ps = connection.prepareStatement(query)){
				ps.setString(2, u.getUserName());
				ps.setString(3, u.getUserPassword());
				ps.setInt(4, u.getUserAge());
				ps.setString(3, u.getUserRole());
				ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<User> GetAllUserData() {
		ArrayList<User> user = new ArrayList<User>();
		String query = "SELECT * FROM Users WHERE UserRole = 'Customer'";
		try(Connection connection = Database.getDB().getConnection()){
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				String name = resultSet.getString("UserName");
				String pw = resultSet.getString("UserPassword");
				Integer age = resultSet.getInt("UserAge");
				user.add(new User(name, pw, age));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public User GetUserData(String UserName, String UserPassword) {
		User user = new User();
		String query = "SELECT * FROM Users WHERE UserName = ? AND UserPassword = ?";
		try(Connection connection = Database.getDB().getConnection();
    			PreparedStatement ps = connection.prepareStatement(query)){
			ps.setString(1, user.getUserName());
			ps.setString(1, user.getUserPassword());
			ps.executeUpdate();
			if(resultSet.next()) {
				user.setUserID(resultSet.getInt("UserID"));
				user.setUserName(resultSet.getString("UserName"));
				user.setUserPassword(resultSet.getString("UserPassword"));
				user.setUserAge(resultSet.getInt("UserAge"));
				user.setUserRole(resultSet.getString("UserRole"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public ArrayList<User> GetAllTechnician() {
		ArrayList<User> user = new ArrayList<User>();
		String query = "SELECT * FROM Users WHERE UserRole = 'Technician'";
		try(Connection connection = Database.getDB().getConnection()){
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				String name = resultSet.getString("UserName");
				String pw = resultSet.getString("UserPassword");
				Integer age = resultSet.getInt("UserAge");
				user.add(new User(name, pw, age));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public void ChangeUserRole(Integer UserID, String NewRole) {
   	 //[Step 4] Implement updateProduct here
   	String query = "UPDATE Users SET UserRole = '"
               + NewRole + "' WHERE UserID = " + UserID;
   	try (Connection connection = Database.getDB().getConnection();
   	  Statement statement = connection.createStatement()) { 
   	  System.out.println(query);
   	  statement.executeUpdate(query);
   	} catch (SQLException e) {
   	  e.printStackTrace();
   	}
   	
   }

	public Integer GetID(String name) {
		Integer id = null;
		String query = "SELECT UserID FROM Users WHERE UserName = '" + name + "'";
		try(Connection connection = Database.getDB().getConnection()){
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if(resultSet.next()) {
				id = resultSet.getInt("UserID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

}
