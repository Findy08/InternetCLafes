package model;

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

}
