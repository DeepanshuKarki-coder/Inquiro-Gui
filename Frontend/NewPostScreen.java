package Frontend;

import Backend.QuestionDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class NewPostScreen extends JFrame {
    private JTextArea questionTextArea;

    public NewPostScreen() {
        setTitle("Inquiro - Create New Post");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 550);
        setLocationRelativeTo(null);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        // Background
        JPanel backgroundPanel = new JPanel(null);
        backgroundPanel.setBackground(new Color(230, 240, 250));
        setContentPane(backgroundPanel);

        // Main Rounded Panel
        RoundedPanel mainPanel = new RoundedPanel(30);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds(50, 30, 600, 450);
        mainPanel.setLayout(null);
        backgroundPanel.add(mainPanel);

        // Header
        JLabel headerLabel = new JLabel("Create New Post", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setBounds(200, 20, 200, 30);
        mainPanel.add(headerLabel);

        RoundedButton backBtn = new RoundedButton("← Dashboard", 20);
        backBtn.setBounds(20, 20, 130, 30);
        backBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        backBtn.setFocusPainted(false);
        backBtn.setBackground(new Color(50, 90, 90));
        backBtn.setForeground(Color.WHITE);
        backBtn.setBorder(BorderFactory.createEmptyBorder());
        backBtn.addActionListener(e -> goBackToDashboard());
        mainPanel.add(backBtn);

        // Instruction Label
        JLabel instructionLabel = new JLabel("Write your question or post:");
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        instructionLabel.setBounds(40, 70, 300, 20);
        mainPanel.add(instructionLabel);

        // Text Area with Scroll
        questionTextArea = new JTextArea();
        questionTextArea.setFont(new Font("Arial", Font.PLAIN, 13));
        questionTextArea.setLineWrap(true);
        questionTextArea.setWrapStyleWord(true);
        questionTextArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(questionTextArea);
        scrollPane.setBounds(40, 100, 520, 200);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        mainPanel.add(scrollPane);

        // Buttons
        RoundedButton postBtn = new RoundedButton("Post Question", 20);
        postBtn.setFont(new Font("Arial", Font.BOLD, 14));
        postBtn.setBackground(new Color(50, 90, 90));
        postBtn.setForeground(Color.WHITE);
        postBtn.setFocusPainted(false);
        postBtn.setBounds(320, 320, 150, 40);
        postBtn.addActionListener(e -> handlePost());
        mainPanel.add(postBtn);

        RoundedButton clearBtn = new RoundedButton("Clear", 20);
        clearBtn.setFont(new Font("Arial", Font.BOLD, 14));
        clearBtn.setBackground(new Color(180, 60, 60));
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setFocusPainted(false);
        clearBtn.setBounds(150, 320, 120, 40);
        clearBtn.addActionListener(e -> questionTextArea.setText(""));
        mainPanel.add(clearBtn);

        questionTextArea.requestFocus();
    }

    private void handlePost() {
        String question = questionTextArea.getText().trim();

        if (question.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your question!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (question.length() < 10) {
            JOptionPane.showMessageDialog(this, "Question should be at least 10 characters long!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        QuestionDAO.addQuestion(Session.currentUserId, question);
        JOptionPane.showMessageDialog(this, "Your post has been added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        questionTextArea.setText("");
    }

    private void goBackToDashboard() {
        dispose();
        new DashboardScreen();
    }
}
