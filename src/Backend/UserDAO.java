package Backend;

import java.sql.*;

public class UserDAO {

    public static UserModel login(String username, String password) {
        String query = "SELECT id, name, password, branch, section FROM user WHERE name=? AND password=?";

        try (Connection connection = DbConnection.getConnection()) {

            if (connection == null) {
                System.out.println("Database connection failed");
                return null;
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return new UserModel(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("password"),
                                resultSet.getString("branch"),
                                resultSet.getString("section")
                        );
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static UserModel register(String username, String password, String branch, String section) {
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty() || branch == null || branch.trim().isEmpty() || section == null || section.trim().isEmpty()) {
            return null;
        }

        try (Connection connection = DbConnection.getConnection()) {

            if (connection == null) {
                System.out.println("Database connection failed");
                return null;
            }

            // Check if username already exists
            String checkQuery = "SELECT id FROM user WHERE name=?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery, Statement.RETURN_GENERATED_KEYS)) {
                checkStmt.setString(1, username);
                try (ResultSet resultSet = checkStmt.executeQuery()) {
                    if (resultSet.next()) {
                        return null; // Username exists
                    }
                }
            }

            // Register new user
            String insertQuery = "INSERT INTO user(name, password, branch, section) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, password);
                insertStmt.setString(3, branch);
                insertStmt.setString(4, section);

                int rowsAffected = insertStmt.executeUpdate();
                if (rowsAffected > 0) {
                    try(ResultSet resultSet = insertStmt.getGeneratedKeys()) {
                        if (resultSet.next()) {
                            int newUserID = resultSet.getInt(1);
                            return new UserModel(newUserID, username, password, branch, section);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
