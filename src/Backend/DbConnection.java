package src.Backend;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DbConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/guvi?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Attempt to establish connection
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            
            if (connection != null) {
                System.out.println("Database connection successful!");
                return connection;
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Please check if the driver is in the classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection failed. Please check your database settings.");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
}
