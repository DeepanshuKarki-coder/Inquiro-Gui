package Backend;

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
    public static List<QuestionModel> getQuestions() {
        List<QuestionModel> questions = new ArrayList<>();

        String questionQuery = "SELECT q.id, q.question, q.user_id, u.name " +
                "FROM question q JOIN user u ON q.user_id = u.id";

        String commentQuery = "SELECT c.id, c.comments, c.user_id, u.name AS user_name " +
                "FROM comments c JOIN user u ON c.user_id = u.id " +
                "WHERE c.question_id = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement questionStmt = connection.prepareStatement(questionQuery)) {

            if (connection == null) {
                System.out.println("Database connection failed");
                return questions;
            }

            try (ResultSet questionRs = questionStmt.executeQuery()) {
                while (questionRs.next()) {
                    int questionId = questionRs.getInt("id");
                    String questionText = questionRs.getString("question");
                    int userId = questionRs.getInt("user_id");
                    String userName = questionRs.getString("name");

                    QuestionModel question = new QuestionModel(questionId, questionText, userId, userName);

                    // Get comments for this question
                    try (PreparedStatement commentStmt = connection.prepareStatement(commentQuery)) {
                        commentStmt.setInt(1, questionId);
                        try (ResultSet commentRs = commentStmt.executeQuery()) {
                            while (commentRs.next()) {
                                int commentId = commentRs.getInt("id");
                                String commentText = commentRs.getString("comments");
                                int commentUserId = commentRs.getInt("user_id");
                                String commentUserName = commentRs.getString("user_name");

                                CommentModel comment = new CommentModel(commentId, commentText, questionId, commentUserId, commentUserName);
                                question.addComment(comment);
                            }
                        }
                    }

                    questions.add(question);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;
    }
}
