package src.Backend;

import java.sql.*;
import java.util.*;

public class QuestionDAO {

    // Add a new question
    public static void addQuestion(int user_id, String question) {
        String query = "INSERT INTO question(question, user_id) VALUES(?, ?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (connection == null) {
                System.out.println("Database connection failed");
                return;
            }

            preparedStatement.setString(1, question);
            preparedStatement.setInt(2, user_id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Question added successfully");
                Logger.log("User ID " + user_id + " added question: " + question);
            }else {
             System.out.println("Failed to add question");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Add a new comment
    public static void addComment(int user_id, int question_id, String comment) {
        // edge case to ensure no empty comment
        if (user_id <= 0 || question_id <= 0 || comment == null || comment.trim().isEmpty()) return;

        String query = "INSERT INTO comments(question_id, user_id, comments) VALUES(?, ?, ?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (connection == null) {
                System.out.println("Database connection failed");
                return;
            }

            preparedStatement.setInt(1, question_id);
            preparedStatement.setInt(2, user_id);
            preparedStatement.setString(3, comment);

            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0) {
                System.out.println("Comment added successfully");
                Logger.log("User ID " + user_id + " commented on QID " + question_id + ": " + comment);
            } else {
                System.out.println("Comment not added");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // View questions and comments
    public static List<String> getQuestions() {
        List<String> results = new ArrayList<>();

        String questionQuery = "SELECT q.id, u.name, q.question FROM question q JOIN user u ON q.user_id = u.id";
        String commentQuery = "SELECT u.name, c.comments FROM comments c JOIN user u ON c.user_id = u.id WHERE c.question_id = ?";

        try (Connection connection = DbConnection.getConnection();
             Statement questionStmt = connection != null ? connection.createStatement() : null;
             ResultSet questionRs = questionStmt != null ? questionStmt.executeQuery(questionQuery) : null) {

            if (connection == null) {
                System.out.println("Database connection failed");
                return results;
            }

            while (questionRs != null && questionRs.next()) {
                int question_id = questionRs.getInt("id");
                String u_name = questionRs.getString("name");
                String q_question = questionRs.getString("question");
                StringBuilder entry = new StringBuilder();
                entry.append("QID ").append(question_id)
                        .append(" by ").append(u_name)
                        .append(": ").append(q_question);

                try (PreparedStatement commentStmt = connection.prepareStatement(commentQuery)) {
                    commentStmt.setInt(1, question_id);
                    try (ResultSet commentRs = commentStmt.executeQuery()) {
                        while (commentRs.next()) {
                            entry.append("\n    ↳ ")
                                    .append(commentRs.getString("name"))
                                    .append(": ")
                                    .append(commentRs.getString("comments"));
                        }
                    }
                }

                results.add(entry.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }
}
