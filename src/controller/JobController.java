//package controller;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//
//import database.Database;
//import model.Job;
//
//public class JobController {
//
//	public void AddNewUser(String username, String password, Integer age) {
//		try(Connection connection = Database.getDB().getConnection()){
//			String query = "INSERT INTO Users(UserName, UserPassword, UserAge, UserRole) VALUES (" + "'" + username + "'" + "," + "'" + password + "'" + "," + age + "," + "'Customer'" + ")";
//			Statement statement = connection.createStatement();
//			statement.executeUpdate(query);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public ArrayList<User> GetAllUserData() {
//		ArrayList<User> user = new ArrayList<User>();
//		String query = "SELECT * FROM Users WHERE UserRole = 'Customer'";
//		try(Connection connection = Database.getDB().getConnection()){
//			Statement statement = connection.createStatement();
//			ResultSet resultSet = statement.executeQuery(query);
//			while(resultSet.next()) {
//				String name = resultSet.getString("UserName");
//				String pw = resultSet.getString("UserPassword");
//				Integer age = resultSet.getInt("UserAge");
//				user.add(new User(name, pw, age));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return user;
//	}
//	
//	public User GetUserData(String UserName, String UserPassword) {
//		User user = new User();
//		String query = "SELECT * FROM Users WHERE UserName = '" + UserName + "' AND UserPassword = '" + UserPassword + "'";
//		try(Connection connection = Database.getDB().getConnection()){
//			Statement statement = connection.createStatement();
//			ResultSet resultSet = statement.executeQuery(query);
//			if(resultSet.next()) {
//				user.setUserID(resultSet.getInt("UserID"));
//				user.setUserName(resultSet.getString("UserName"));
//				user.setUserPassword(resultSet.getString("UserPassword"));
//				user.setUserAge(resultSet.getInt("UserAge"));
//				user.setUserRole(resultSet.getString("UserRole"));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return user;
//	}
//	
//	public ArrayList<User> GetAllTechnician() {
//		ArrayList<User> user = new ArrayList<User>();
//		String query = "SELECT * FROM Users WHERE UserRole = 'Technician'";
//		try(Connection connection = Database.getDB().getConnection()){
//			Statement statement = connection.createStatement();
//			ResultSet resultSet = statement.executeQuery(query);
//			while(resultSet.next()) {
//				String name = resultSet.getString("UserName");
//				String pw = resultSet.getString("UserPassword");
//				Integer age = resultSet.getInt("UserAge");
//				user.add(new User(name, pw, age));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return user;
//	}
//	
//	public void ChangeUserRole(Integer UserID, String NewRole) {
//   	 //[Step 4] Implement updateProduct here
//   	String query = "UPDATE Users SET UserRole = '"
//               + NewRole + "' WHERE UserID = " + UserID;
//   	try (Connection connection = Database.getDB().getConnection();
//   	  Statement statement = connection.createStatement()) { 
//   	  System.out.println(query);
//   	  statement.executeUpdate(query);
//   	} catch (SQLException e) {
//   	  e.printStackTrace();
//   	}
//   	
//   }
//
//}
