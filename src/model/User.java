package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import database.Database;
import javafx.scene.control.Alert;

public class User {
	
	private Integer UserAge, UserID;
	private String UserName, UserPassword, UserRole;

	public User(String UserName, String UserPassword, Integer UserAge) {
		this.UserName = UserName;
		this.UserPassword = UserPassword;
		this.UserAge = UserAge;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(Integer id, String name, Integer age, String role) {
		this.UserID = id;
		this.UserName = name;
		this.UserAge = age;
		this.UserRole = role;
	}

	public Integer getUserID() {
		return UserID;
	}

	public void setUserID(Integer userID) {
		UserID = userID;
	}

	public String getUserRole() {
		return UserRole;
	}

	public void setUserRole(String userRole) {
		UserRole = userRole;
	}

	public Integer getUserAge() {
		return UserAge;
	}

	public void setUserAge(Integer userAge) {
		UserAge = userAge;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserPassword() {
		return UserPassword;
	}

	public void setUserPassword(String userPassword) {
		UserPassword = userPassword;
	}
	
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
	
	public ArrayList<User> GetAllUserData() {
		ArrayList<User> user = new ArrayList<User>();
		String query = "SELECT * FROM Users";
		try(Connection connection = Database.getDB().getConnection()){
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				Integer id = resultSet.getInt("UserID");
				String name = resultSet.getString("UserName");
				Integer age = resultSet.getInt("UserAge");
				String role = resultSet.getString("UserRole");
				user.add(new User(id, name, age, role));
			}
		} catch (SQLException e) {
			showAlert("No Data", "There is no Staff Data", Alert.AlertType.ERROR);
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
		String query = "SELECT * FROM Users WHERE UserRole = 'Computer Technician'";
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
	
	public ArrayList<User> GetAllStaff() {
		ArrayList<User> user = new ArrayList<User>();
		String query = "SELECT * FROM Users WHERE UserRole = 'Computer Technician' OR UserRole = 'Admin' OR UserRole = 'Operator'";
		try(Connection connection = Database.getDB().getConnection()){
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				Integer id = resultSet.getInt("UserID");
				String name = resultSet.getString("UserName");
				Integer age = resultSet.getInt("UserAge");
				String role = resultSet.getString("UserRole");
				user.add(new User(id, name, age, role));
			}
		} catch (SQLException e) {
			showAlert("No Data", "There is no Staff Data", Alert.AlertType.ERROR);
		}
		return user;
	}
	
	public void ChangeUserRole(Integer UserID, String NewRole) {
		if (!IsStaffExists(UserID)) {
	        showAlert("Invalid Selection", "Please choose a staff member.", Alert.AlertType.ERROR);
	        return;
	    }
		
		ArrayList<String> validRoles = new ArrayList<>(Arrays.asList("Admin", "Customer", "Operator", "Computer Technician"));
		if (!validRoles.contains(NewRole)) {
            showAlert("Invalid UserRole", "Must be either 'Admin', 'Customer', 'Operator', or 'Computer Technician'.", Alert.AlertType.ERROR);
            return;
        }

        String query = "UPDATE Users SET UserRole = ? WHERE UserID = ?";
        try (Connection connection = Database.getDB().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, NewRole);
            ps.setInt(2, UserID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }  	
	}
	
	private boolean IsStaffExists(Integer userID) {
	    String query = "SELECT COUNT(*) FROM Users WHERE UserID = ?";
	    try (Connection connection = Database.getDB().getConnection();
	         PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setInt(1, userID);
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
	
	public String GetRole(String user) {
		String role = null;
		String query = "SELECT UserRole FROM Users WHERE UserName = ?";
		try(Connection connection = Database.getDB().getConnection()){
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, user);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()) {
				role = resultSet.getString("UserRole");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return role;
	}
	
	private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
	
}
