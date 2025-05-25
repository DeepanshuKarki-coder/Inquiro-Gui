package src.Backend;

import java.sql.*;

public class UserDAO {

    public static int login(String username, String password) {
        String query = "SELECT id FROM user WHERE name=? AND password=?";

        try (Connection connection = DbConnection.getConnection()) {

            if (connection == null) {
                System.out.println("Database connection failed");
                return -1;
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static boolean register(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        try (Connection connection = DbConnection.getConnection()) {

            if (connection == null) {
                System.out.println("Database connection failed");
                return false;
            }

            // Check if username already exists
            String checkQuery = "SELECT id FROM user WHERE name=?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setString(1, username);
                try (ResultSet resultSet = checkStmt.executeQuery()) {
                    if (resultSet.next()) {
                        return false; // Username exists
                    }
                }
            }

            // Register new user
            String insertQuery = "INSERT INTO user(name, password) VALUES (?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, password);

                int rowsAffected = insertStmt.executeUpdate();
                return rowsAffected > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
