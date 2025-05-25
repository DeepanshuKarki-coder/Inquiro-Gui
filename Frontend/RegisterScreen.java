package Frontend;

import Backend.UserDAO;
import javax.swing.*;
import java.awt.*;

public class RegisterScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public RegisterScreen() {
        setTitle("Inquiro - Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        // Background panel
        JPanel backgroundPanel = new JPanel(null);
        backgroundPanel.setBackground(new Color(230, 240, 250)); // light bluish
        setContentPane(backgroundPanel);

        // Rounded register form panel
        RoundedPanel registerPanel = new RoundedPanel(30);
        registerPanel.setBackground(Color.WHITE);
        registerPanel.setBounds(50, 30, 340, 300);
        registerPanel.setLayout(null);
        backgroundPanel.add(registerPanel);

        // Header
        JLabel headerLabel = new JLabel("Create Account", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setBounds(90, 20, 160, 30);
        registerPanel.add(headerLabel);

        // Username
        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(30, 70, 100, 20);
        registerPanel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(30, 90, 270, 30);
        registerPanel.add(usernameField);

        // Password
        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(30, 130, 100, 20);
        registerPanel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(30, 150, 270, 30);
        registerPanel.add(passwordField);

        // Confirm Password
        JLabel confirmLabel = new JLabel("Confirm Password");
        confirmLabel.setBounds(30, 190, 150, 20);
        registerPanel.add(confirmLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(30, 210, 270, 30);
        registerPanel.add(confirmPasswordField);

        // Buttons
        RoundedButton registerBtn = new RoundedButton("Register", 20);
        registerBtn.setBounds(40, 255, 110, 35);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 16));
        registerBtn.setBackground(new Color(50, 90, 90));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        registerBtn.setBorder(BorderFactory.createEmptyBorder());
        registerPanel.add(registerBtn);

        RoundedButton backBtn = new RoundedButton("Back", 20);
        backBtn.setBounds(180, 255, 110, 35);
        backBtn.setFont(new Font("Arial", Font.BOLD, 16));
        backBtn.setBackground(new Color(50, 90, 90));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder());
        registerPanel.add(backBtn);

        registerBtn.addActionListener(e -> handleRegister());
        backBtn.addActionListener(e -> goBackToLogin());

        getRootPane().setDefaultButton(registerBtn);
    }

    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            confirmPasswordField.setText("");
            return;
        }

        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this, "Password must be at least 4 characters!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = UserDAO.register(username, password);
        if (success) {
            JOptionPane.showMessageDialog(this, "Registration successful! Please login.", "Success", JOptionPane.INFORMATION_MESSAGE);
            goBackToLogin();
        } else {
            JOptionPane.showMessageDialog(this, "Username already exists or registration failed!", "Registration Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void goBackToLogin() {
        dispose();
        new LoginScreen();
    }
}

