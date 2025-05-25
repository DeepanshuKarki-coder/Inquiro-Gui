package Frontend;

import javax.swing.*;
import java.awt.*;

public class DashboardScreen extends JFrame {
    public DashboardScreen() {
        setTitle("Inquiro - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 550); // Bigger window
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel backgroundPanel = new JPanel(null);
        backgroundPanel.setBackground(new Color(230, 240, 250)); // Light background
        setContentPane(backgroundPanel);

        // Bigger and more centered panel
        RoundedPanel mainPanel = new RoundedPanel(30);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds(100, 80, 500, 370); // Bigger rounded panel
        mainPanel.setLayout(null);
        backgroundPanel.add(mainPanel);

        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome, " + Session.currentUsername + "!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setBounds(50, 30, 400, 40); // More padding and larger text
        mainPanel.add(welcomeLabel);

        // Popular Posts Button
        RoundedButton popularPostsBtn = new RoundedButton("Popular Posts", 20);
        popularPostsBtn.setBounds(150, 100, 200, 55);
        styleButton(popularPostsBtn);
        mainPanel.add(popularPostsBtn);

        // New Post Button
        RoundedButton newPostBtn = new RoundedButton("New Post", 20);
        newPostBtn.setBounds(150, 200, 200, 55);
        styleButton(newPostBtn);
        mainPanel.add(newPostBtn);

        // Logout Button (bottom-right)
        RoundedButton logoutBtn = new RoundedButton("Logout", 20);
        logoutBtn.setBounds(390, 320, 75, 25);
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 14));
        logoutBtn.setBackground(new Color(50, 90, 90));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(logoutBtn);

        // Action listeners
        popularPostsBtn.addActionListener(e -> openPopularPosts());
        newPostBtn.addActionListener(e -> openNewPost());
        logoutBtn.addActionListener(e -> logout());
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(50, 90, 90));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
    }

    private void openPopularPosts() {
        dispose();
        new PopularPostsScreen();
    }

    private void openNewPost() {
        dispose();
        new NewPostScreen();
    }

    private void logout() {
        Session.currentUserId = -1;
        Session.currentUsername = "";
        dispose();
        new ForumApp.HomeScreen();
    }
}
