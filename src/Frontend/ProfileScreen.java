package Frontend;

import Backend.UserDAO;

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

        // Back to Dashboard button (outside the rounded panel, top-left)
        RoundedButton backBtn = new RoundedButton("← Back to Dashboard", 20);
        backBtn.setBounds(20, 20, 180, 35);
        backBtn.setFont(new Font("Arial", Font.BOLD, 14));
        backBtn.setBackground(new Color(50, 90, 90));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder());
        backgroundPanel.add(backBtn);

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

        // Logout button (smaller, bottom-right)
        RoundedButton logoutBtn = new RoundedButton("Logout", 20);
        logoutBtn.setBounds(390, 320, 75, 25);
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 14));
        logoutBtn.setBackground(new Color(220, 53, 69)); // Red color for logout
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(logoutBtn);

        // Change Password Button (smaller, bottom-left)
        RoundedButton changePassBtn = new RoundedButton("Change Password", 20);
        changePassBtn.setBounds(20, 320, 150, 25);
        changePassBtn.setFont(new Font("Arial", Font.BOLD, 14));
        changePassBtn.setBackground(new Color(69, 53, 220)); // Blue color for change password
        changePassBtn.setForeground(Color.WHITE);
        changePassBtn.setFocusPainted(false);
        changePassBtn.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(changePassBtn);

        // Action listeners
        backBtn.addActionListener(e -> backToDashboard());
        logoutBtn.addActionListener(e -> logout());
        changePassBtn.addActionListener(e -> openChangePasswordDialog());
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

    private void openChangePasswordDialog() {
        JDialog changePasswordDialog = new JDialog(this, "Change Password", true);
        changePasswordDialog.setSize(450, 450);
        changePasswordDialog.setLocationRelativeTo(this);
        changePasswordDialog.setResizable(false);

        // Background panel with light background
        JPanel backgroundPanel = new JPanel(null);
        backgroundPanel.setBackground(new Color(230, 240, 250));
        changePasswordDialog.setContentPane(backgroundPanel);

        // Main rounded panel
        RoundedPanel mainPanel = new RoundedPanel(30);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds(25, 25, 400, 350);
        mainPanel.setLayout(null);
        backgroundPanel.add(mainPanel);

        // Title
        JLabel titleLabel = new JLabel("Change Password", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(50, 90, 90));
        titleLabel.setBounds(50, 20, 300, 30);
        mainPanel.add(titleLabel);

        // Current Password
        JLabel oldPasswordLabel = new JLabel("Current Password:");
        oldPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        oldPasswordLabel.setBounds(50, 70, 150, 25);
        mainPanel.add(oldPasswordLabel);

        JPasswordField oldPasswordField = new JPasswordField();
        oldPasswordField.setBounds(50, 95, 300, 35);
        oldPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        oldPasswordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        mainPanel.add(oldPasswordField);

        // New Password
        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        newPasswordLabel.setBounds(50, 140, 120, 25);
        mainPanel.add(newPasswordLabel);

        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setBounds(50, 165, 300, 35);
        newPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        newPasswordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        mainPanel.add(newPasswordField);

        // Confirm New Password
        JLabel confirmPasswordLabel = new JLabel("Confirm New Password:");
        confirmPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        confirmPasswordLabel.setBounds(50, 210, 180, 25);
        mainPanel.add(confirmPasswordLabel);

        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(50, 235, 300, 35);
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        mainPanel.add(confirmPasswordField);

        // Buttons
        RoundedButton changeBtn = new RoundedButton("Change Password", 20);
        changeBtn.setBounds(50, 290, 140, 40);
        changeBtn.setFont(new Font("Arial", Font.BOLD, 14));
        changeBtn.setBackground(new Color(50, 90, 90));
        changeBtn.setForeground(Color.WHITE);
        changeBtn.setFocusPainted(false);
        changeBtn.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(changeBtn);

        RoundedButton cancelBtn = new RoundedButton("Cancel", 20);
        cancelBtn.setBounds(210, 290, 140, 40);
        cancelBtn.setFont(new Font("Arial", Font.BOLD, 14));
        cancelBtn.setBackground(new Color(220, 53, 69));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(cancelBtn);

        // Action listeners
        changeBtn.addActionListener(e -> {
            String oldPassword = new String(oldPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (oldPassword.trim().isEmpty() || newPassword.trim().isEmpty() || confirmPassword.trim().isEmpty()) {
                JOptionPane.showMessageDialog(changePasswordDialog, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(changePasswordDialog, "New passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (newPassword.length() < 6) {
                JOptionPane.showMessageDialog(changePasswordDialog, "New password must be at least 6 characters", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = UserDAO.changePassword(Session.currentUserId, oldPassword, newPassword);

            if (success) {
                JOptionPane.showMessageDialog(changePasswordDialog, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                changePasswordDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(changePasswordDialog, "Failed to change password. Please check your current password.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Clear password fields for security
            oldPasswordField.setText("");
            newPasswordField.setText("");
            confirmPasswordField.setText("");
        });

        cancelBtn.addActionListener(e -> changePasswordDialog.dispose());

        changePasswordDialog.setVisible(true);
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