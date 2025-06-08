package Frontend;

import Backend.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Login Screen
public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginScreen() {
        setTitle("Inquiro - Login");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel background = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(220, 230, 240)); // light background color
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        background.setLayout(null);
        setContentPane(background);

        // Rounded translucent login panel
        JPanel loginPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 180)); // semi-transparent
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
            }
        };
        loginPanel.setLayout(null);
        loginPanel.setBounds(50, 50, 300, 380);
        background.add(loginPanel);

        // Close Button
        JButton closeBtn = new JButton("X");
        closeBtn.setBounds(270, 10, 20, 20);
        closeBtn.setForeground(Color.RED);
        closeBtn.setBorderPainted(false);
        closeBtn.setContentAreaFilled(false);
        closeBtn.setFocusPainted(false);
        closeBtn.addActionListener(e -> System.exit(0));
        loginPanel.add(closeBtn);

        // Header
        JLabel headerLabel = new JLabel("Login", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setBounds(0, 30, 300, 30);
        loginPanel.add(headerLabel);

        // Username Label
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setBounds(40, 70, 100, 20);
        loginPanel.add(userLabel);

        // Username Field
        usernameField = new JTextField();
        usernameField.setBounds(40, 90, 220, 25);
        usernameField.setBorder(null);
        loginPanel.add(usernameField);

        JSeparator userSep = new JSeparator();
        userSep.setBounds(40, 115, 220, 1);
        loginPanel.add(userSep);

        // Password Label
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passLabel.setBounds(40, 130, 100, 20);
        loginPanel.add(passLabel);

        // Password Field
        passwordField = new JPasswordField();
        passwordField.setBounds(40, 150, 220, 25);
        passwordField.setBorder(null);
        loginPanel.add(passwordField);

        JSeparator passSep = new JSeparator();
        passSep.setBounds(40, 175, 220, 1);
        loginPanel.add(passSep);

        // Login Button
        RoundedButton loginBtn = new RoundedButton("Submit", 25);
        loginBtn.setBounds(85, 210, 130, 40);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 16));
        loginBtn.setBackground(new Color(50, 90, 90));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorder(BorderFactory.createEmptyBorder());
        loginBtn.addActionListener(e -> handleLogin());
        loginPanel.add(loginBtn);

        // Register Link
        JLabel registerLabel = new JLabel("<html><i>Don't have an account? <u>Register …</u></i></html>");
        registerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        registerLabel.setForeground(Color.DARK_GRAY);
        registerLabel.setBounds(55, 270, 200, 30);
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openRegisterScreen();
            }
        });
        loginPanel.add(registerLabel);

        // Set default button
        getRootPane().setDefaultButton(loginBtn);
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserModel userId = UserDAO.login(username, password);
        if (userId != null) {
            Session.currentUserId = userId.getId();
            Session.currentUsername = userId.getName();
            dispose();
            new DashboardScreen();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }

    private void openRegisterScreen() {
        dispose();
        new RegisterScreen();
    }
}
