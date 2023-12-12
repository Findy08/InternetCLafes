package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;
import javafx.scene.control.Alert;
import model.User;

public class UserController {
	
	//validasi register
	public boolean validateRegister(String username, String password, String confirmPassword, Integer age) {
	    if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
	        showAlert("Input Error", "Please fill in all fields.", Alert.AlertType.ERROR);
	        return false;
	    }

	    if (username.length() < 7) {
	        showAlert("Input Error", "Username must be at least 7 characters long.", Alert.AlertType.ERROR);
	        return false;
	    }

	    if (!password.equals(confirmPassword)) {
	        showAlert("Input Error", "Password have to match with confirm password.", Alert.AlertType.ERROR);
	        return false;
	    }

	    if (age < 13 || age > 65) {
	        showAlert("Input Error", "Age must be between 13 - 65 (inclusive)", Alert.AlertType.ERROR);
	        return false;
	    }

	    if (!isUnique(username)) {
	        showAlert("Input Error", "Username is already taken.", Alert.AlertType.ERROR);
	        return false;
	    }

	    if (!isAlphaNumeric(password)) {
	        showAlert("Input Error", "Password must contain alpha numeric characters.", Alert.AlertType.ERROR);
	        return false;
	    }

	    return true;
	}
	
	private boolean isAlphaNumeric(String str) {
	    boolean hasLetter = false;
	    boolean hasDigit = false;

	    for (char c : str.toCharArray()) {
	        if (Character.isLetter(c)) {
	            hasLetter = true;
	        } else if (Character.isDigit(c)) {
	            hasDigit = true;
	        }

	        if (hasLetter && hasDigit) {
	            break; //kalo udh ketemu atleast 1 kombinasi lgsg break dari loop
	        }
	    }

	    return hasLetter && hasDigit; //harus kombinasi
	}

	private boolean isUnique(String username) {
	    String query = "SELECT COUNT(*) FROM Users WHERE UserName = ?";
	    try (Connection connection = Database.getDB().getConnection();
	         PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setString(1, username);

	        ResultSet resultSet = ps.executeQuery();
	        if (resultSet.next()) {
	            int count = resultSet.getInt(1);
	            return count == 0; // kalo 0 artinya username udah unique, ntr di return true sbg hasil
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	//validasi login
	public boolean validateLogin(String username, String password) {
	    if (username.isEmpty() || password.isEmpty()) {
	        showAlert("Input Error", "Please fill all fields", Alert.AlertType.ERROR);
	        return false;
	    }

	    if (!usernameExist(username)) {
	        showAlert("Login Error", "Username invalid (does not exist).", Alert.AlertType.ERROR);
	        return false;
	    }

	    if (!passwordMatch(username, password)) {
	        showAlert("Login Error", "Incorrect password.", Alert.AlertType.ERROR);
	        return false;
	    }

	    return true;
	}

	private boolean usernameExist(String username) {
	    String query = "SELECT * FROM Users WHERE UserName = ?";
	    try (Connection connection = Database.getDB().getConnection();
	         PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setString(1, username);

	        ResultSet rs = ps.executeQuery();
	        return rs.next(); 
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false; 
	}

	private boolean passwordMatch(String username, String password) {
	    String query = "SELECT * FROM Users WHERE UserName = ? AND UserPassword = ?";
	    try (Connection connection = Database.getDB().getConnection();
	         PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setString(1, username);
	        ps.setString(2, password);

	        ResultSet rs = ps.executeQuery();
	        return rs.next(); 
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false; 
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
	
	//login
//	public boolean login(String username, String password) {
//	    String query = "SELECT * FROM Users WHERE UserName = ? AND UserPassword = ?";
//	    
//	    try (Connection connection = Database.getDB().getConnection();
//	         PreparedStatement ps = connection.prepareStatement(query)) {
//	        ps.setString(1, username);
//	        ps.setString(2, password);
//
//	        ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return true;
//            }
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }
//
//	    return false;
//	}

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
	
	private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
