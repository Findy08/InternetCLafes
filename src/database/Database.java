package database;
import java.sql.*;

public class Database {
	
	private static Database db;
	private Connection connection;

    private void connectToDatabase() {
        String db_name = "internetclafes";
        try {
            String url = "jdbc:mysql://localhost:3306/"+db_name;
            String user = "root";
            String password = "";
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public static Database getDB() {
        if (db == null) {
            synchronized (Database.class) {
                if (db == null) {
                    db = new Database();
                }
            }
        }
        return db;
    }
    
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connectToDatabase();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking connection status", e);
        }
        return connection;
    }
}
