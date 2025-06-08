package Frontend;

import Backend.CommentModel;
import Backend.QuestionDAO;
import Backend.QuestionModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class PopularPostsScreen extends JFrame {
    private JPanel postsPanel;
    private JScrollPane scrollPane;

    public PopularPostsScreen() {
        setTitle("Inquiro - Popular Posts");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        initComponents();
        loadPosts();
        setVisible(true);
    }

    private void initComponents() {
        // Background
        JPanel backgroundPanel = new JPanel(null);
        backgroundPanel.setBackground(new Color(230, 240, 250));
        setContentPane(backgroundPanel);

        // Main Rounded Panel (bigger now)
        RoundedPanel mainPanel = new RoundedPanel(30);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds(50, 30, 700, 500);
        mainPanel.setLayout(null);
        backgroundPanel.add(mainPanel);

        // Header
        JLabel headerLabel = new JLabel("Popular Posts", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setBounds(250, 20, 200, 30);
        mainPanel.add(headerLabel);

        RoundedButton backBtn = new RoundedButton("← Dashboard", 20);
        backBtn.setBounds(20, 20, 130, 30);  // Smaller and placed top-left
        backBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        backBtn.setFocusPainted(false);
        backBtn.setBackground(new Color(50, 90, 90));
        backBtn.setForeground(Color.WHITE);
        backBtn.setBorder(BorderFactory.createEmptyBorder());
        backBtn.addActionListener(e -> goBackToDashboard());
        mainPanel.add(backBtn);

        // Posts Panel inside ScrollPane
        postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
        postsPanel.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(postsPanel);
        scrollPane.setBounds(30, 70, 640, 360);
        scrollPane.setBorder(null);
        scrollPane.setBackground(Color.WHITE);
        mainPanel.add(scrollPane);

        // Refresh Button
        RoundedButton refreshBtn = new RoundedButton("Refresh Posts", 20);
        refreshBtn.setBounds(280, 440, 140, 35);
        refreshBtn.setFont(new Font("Arial", Font.BOLD, 14));
        refreshBtn.setBackground(new Color(50, 90, 90));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setBorder(BorderFactory.createEmptyBorder());
        refreshBtn.addActionListener(e -> {
            loadPosts();
            JOptionPane.showMessageDialog(this, "Posts refreshed!", "Info", JOptionPane.INFORMATION_MESSAGE);
        });
        mainPanel.add(refreshBtn);
    }

    private void loadPosts() {
        postsPanel.removeAll();
        List<QuestionModel> questions = QuestionDAO.getQuestions();

        if (questions.isEmpty()) {
            JLabel noPostsLabel = new JLabel("No posts available yet.", JLabel.CENTER);
            noPostsLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            postsPanel.add(noPostsLabel);
        } else {
            for (QuestionModel question : questions) {
                JPanel postPanel = createPostPanel(question);
                postsPanel.add(postPanel);
                postsPanel.add(Box.createVerticalStrut(10));
            }
        }

        postsPanel.revalidate();
        postsPanel.repaint();
    }

    private JPanel createPostPanel(QuestionModel question) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                new EmptyBorder(15, 15, 15, 15)
        ));
        panel.setBackground(Color.WHITE);

        // Build question + comments in formatted string
        StringBuilder content = new StringBuilder();
        content.append("QID ").append(question.getId())
                .append(" by ").append(question.getUserName())
                .append(": ").append(question.getQuestion());

        for (CommentModel comment : question.getComments()) {
            content.append("\n    ↳ ")
                    .append(comment.getUserName())
                    .append(": ")
                    .append(comment.getComment());
        }

        JTextArea textArea = new JTextArea(content.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        textArea.setBackground(Color.WHITE);
        textArea.setBorder(new EmptyBorder(0, 0, 10, 0));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        panel.add(textArea, BorderLayout.CENTER);

        RoundedButton commentBtn = new RoundedButton("Add Comment", 20);
        commentBtn.setFont(new Font("Arial", Font.PLAIN, 13));
        commentBtn.setBackground(new Color(50, 90, 90));
        commentBtn.setForeground(Color.WHITE);
        commentBtn.setFocusPainted(false);
        commentBtn.setPreferredSize(new Dimension(120, 30));
        commentBtn.addActionListener(e -> openCommentDialog(question.getId()));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(commentBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }



    private void openCommentDialog(int questionId) {
        String commentText = JOptionPane.showInputDialog(this, "Enter your comment:", "Add Comment", JOptionPane.PLAIN_MESSAGE);

        if (commentText != null && !commentText.trim().isEmpty()) {
            QuestionDAO.addComment(Session.currentUserId, questionId, commentText.trim());
            JOptionPane.showMessageDialog(this, "Comment added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadPosts(); // Refresh posts to show new comment
        }
    }



    private void goBackToDashboard() {
        dispose();
        new DashboardScreen();
    }
}
