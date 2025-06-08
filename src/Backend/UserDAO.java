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

    public static boolean changePassword(int userID, String oldPassword, String newPassword) {
        if (oldPassword == null || oldPassword.trim().isEmpty() ||
                newPassword == null || newPassword.trim().isEmpty() || userID <= 0) {
            return false;
        }

        try (Connection connection = DbConnection.getConnection()) {
            if (connection == null) {
                System.out.println("Database connection failed");
                return false;
            }

            // First, check if oldPassword is correct
            String checkQuery = "SELECT password FROM user WHERE id=?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, userID);
                try (ResultSet resultSet = checkStmt.executeQuery()) {
                    if (resultSet.next()) {
                        String currentPassword = resultSet.getString("password");

                        // CRITICAL: Verify the old password matches
                        if (!currentPassword.equals(oldPassword)) {
                            System.out.println("Old password is incorrect");
                            return false;
                        }

                        // Old password is correct, now update to new password
                        String updateQuery = "UPDATE user SET password=? WHERE id=?";
                        try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                            updateStmt.setString(1, newPassword);
                            updateStmt.setInt(2, userID);
                            int rowsAffected = updateStmt.executeUpdate();

                            if (rowsAffected > 0) {
                                System.out.println("Password changed successfully");
                                Logger.log("User_id: " + userID + " changed password successfully");
                                return true;
                            } else {
                                System.out.println("Password change failed - no rows affected");
                                return false;
                            }
                        }
                    } else {
                        System.out.println("User not found");
                        return false;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error changing password: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
