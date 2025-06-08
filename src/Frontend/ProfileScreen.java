package Frontend;

import javax.swing.*;
import java.awt.*;

public class ProfileScreen extends JFrame {
    public ProfileScreen() {
        setTitle("Inquiro - Profile");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 550);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel backgroundPanel = new JPanel(null);
        backgroundPanel.setBackground(new Color(230, 240, 250)); // Light background

        setContentPane(backgroundPanel);

        // Main panel with rounded corners
        RoundedPanel mainPanel = new RoundedPanel(30);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds(100, 80, 500, 370);
        mainPanel.setLayout(null);
        backgroundPanel.add(mainPanel);

        // Profile title
        JLabel titleLabel = new JLabel("Profile Information", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(50, 90, 90));
        titleLabel.setBounds(50, 30, 400, 40);
        mainPanel.add(titleLabel);

        // Profile picture (same as dashboard)
        ImageIcon icon = new ImageIcon(getClass().getResource("/Pics/ProfilePicture.jpg"));
        Image image = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(image);

        JLabel profilePicLabel = new JLabel(scaledIcon);
        profilePicLabel.setBounds(210, 80, 80, 80);
        mainPanel.add(profilePicLabel);

        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        usernameLabel.setBounds(100, 180, 100, 25);
        mainPanel.add(usernameLabel);

        JLabel usernameValue = new JLabel(Session.currentUsername);
        usernameValue.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameValue.setBounds(200, 180, 200, 25);
        mainPanel.add(usernameValue);

        // Branch field
        JLabel branchLabel = new JLabel("Branch:");
        branchLabel.setFont(new Font("Arial", Font.BOLD, 16));
        branchLabel.setBounds(100, 215, 100, 25);
        mainPanel.add(branchLabel);

        JLabel branchValue = new JLabel(Session.currentUserBranch);
        branchValue.setFont(new Font("Arial", Font.PLAIN, 16));
        branchValue.setBounds(200, 215, 200, 25);
        mainPanel.add(branchValue);

        // Section field
        JLabel sectionLabel = new JLabel("Section:");
        sectionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        sectionLabel.setBounds(100, 250, 100, 25);
        mainPanel.add(sectionLabel);

        JLabel sectionValue = new JLabel(Session.currentUserSection);
        sectionValue.setFont(new Font("Arial", Font.PLAIN, 16));
        sectionValue.setBounds(200, 250, 200, 25);
        mainPanel.add(sectionValue);

        // Back to Dashboard button
        RoundedButton backBtn = new RoundedButton("Back to Dashboard", 20);
        backBtn.setBounds(150, 300, 200, 45);
        styleButton(backBtn);
        mainPanel.add(backBtn);

        // Logout button (smaller, bottom-right)
        RoundedButton logoutBtn = new RoundedButton("Logout", 20);
        logoutBtn.setBounds(390, 320, 75, 25);
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 14));
        logoutBtn.setBackground(new Color(220, 53, 69)); // Red color for logout
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(logoutBtn);

        // Action listeners
        backBtn.addActionListener(e -> backToDashboard());
        logoutBtn.addActionListener(e -> logout());
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(50, 90, 90));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
    }

    private void backToDashboard() {
        dispose();
        new DashboardScreen();
    }

    private void logout() {
        Session.currentUserId = -1;
        Session.currentUsername = "";
        Session.currentUserBranch = "";
        Session.currentUserSection = "";
        dispose();
        new ForumApp.HomeScreen();
    }
}