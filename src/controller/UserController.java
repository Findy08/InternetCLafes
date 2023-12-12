package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;
import model.User;

public class UserController {

	public void AddNewUser(String username, String password, Integer age) {
		User u = new User();
		u.setUserName(username);
	    u.setUserPassword(password);
	    u.setUserAge(age);
	    u.setUserRole("Customer");
		String query = "INSERT INTO Users(UserName, UserPassword, UserAge, UserRole) VALUES (?, ?, ?, 'Customer')";
		try(Connection connection = Database.getDB().getConnection();
			PreparedStatement ps = connection.prepareStatement(query)){
				ps.setString(1, u.getUserName());
				ps.setString(2, u.getUserPassword());
				ps.setInt(3, u.getUserAge());
				ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//login
		public boolean login(String username, String password) {
		    String query = "SELECT * FROM Users WHERE UserName = ? AND UserPassword = ?";
		    
		    try (Connection connection = Database.getDB().getConnection();
		         PreparedStatement ps = connection.prepareStatement(query)) {
		        ps.setString(1, username);
		        ps.setString(2, password);

		        ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                return true;
	            }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return false;
		}

	public ArrayList<User> GetAllUserData() {
		ArrayList<User> user = new ArrayList<User>();
		String query = "SELECT * FROM Users WHERE UserRole = 'Customer'";
		try(Connection connection = Database.getDB().getConnection()){
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet resultSet = ps.executeQuery();
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
		try{
			Connection connection = Database.getDB().getConnection();
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, UserName);
			ps.setString(2, UserPassword);
			
			ResultSet resultSet = ps.executeQuery();
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
		System.out.println(user.getUserName());
		return user;
	}
	
	public ArrayList<User> GetAllTechnician() {
		ArrayList<User> user = new ArrayList<User>();
		String query = "SELECT * FROM Users WHERE UserRole = 'Technician'";
		try(Connection connection = Database.getDB().getConnection()){
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet resultSet = ps.executeQuery();
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
   	String query = "UPDATE Users SET UserRole = ? WHERE UserID = ?";
   	try (Connection connection = Database.getDB().getConnection();
   		PreparedStatement ps = connection.prepareStatement(query)){
   		ps.setString(1, NewRole);
		ps.setInt(2, UserID);
   		ps.executeUpdate();
   	} catch (SQLException e) {
   	  e.printStackTrace();
   	}
   	
   }

	public Integer GetID(String name) {
		Integer id = null;
		String query = "SELECT UserID FROM Users WHERE UserName = ?";
		try(Connection connection = Database.getDB().getConnection()){
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, name);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()) {
				id = resultSet.getInt("UserID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public String GetName(Integer id) {
		String name = null;
		String query = "SELECT UserName FROM Users WHERE UserID = ?";
		try(Connection connection = Database.getDB().getConnection()){
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()) {
				name = resultSet.getString("UserName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return name;
	}

}
