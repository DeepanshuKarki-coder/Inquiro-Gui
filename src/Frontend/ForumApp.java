package Frontend;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;


public class ForumApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new HomeScreen();
        });
    }

    // Home Screen
    static class HomeScreen extends JFrame{
        private Image backgroundImage;
        private Image scaledImage;

        public HomeScreen() {
            setTitle("Home Screen");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(800, 600);
            setLocationRelativeTo(null);

            // Load image from resources
            URL imgUrl = getClass().getResource("/Pics/HomeScreenBackground.jpg");
            backgroundImage = new ImageIcon(imgUrl).getImage();

            JPanel backgroundPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);

                    int panelWidth = getWidth();
                    int panelHeight = getHeight();

                    if (backgroundImage != null) {
                        double imgWidth = backgroundImage.getWidth(this);
                        double imgHeight = backgroundImage.getHeight(this);
                        double panelRatio = (double) panelWidth / panelHeight;
                        double imgRatio = imgWidth / imgHeight;

                        int drawWidth, drawHeight;

                        if (panelRatio > imgRatio) {
                            drawHeight = panelHeight;
                            drawWidth = (int) (imgRatio * drawHeight);
                        } else {
                            drawWidth = panelWidth;
                            drawHeight = (int) (drawWidth / imgRatio);
                        }

                        int x = (panelWidth - drawWidth) / 2;
                        int y = (panelHeight - drawHeight) / 2;

                        // Scale image smoothly if needed
                        if (scaledImage == null || scaledImage.getWidth(this) != drawWidth || scaledImage.getHeight(this) != drawHeight) {
                            scaledImage = backgroundImage.getScaledInstance(drawWidth, drawHeight, Image.SCALE_SMOOTH);
                        }

                        g.drawImage(scaledImage, x, y, this);
                    }
                }
            };

            backgroundPanel.setLayout(null);
            setContentPane(backgroundPanel);

            RoundedButton loginButton = new RoundedButton("Login", 20);
            loginButton.setBounds(605, 28, 100, 30);
            backgroundPanel.add(loginButton);

            // Add action listener to handle button click
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new LoginScreen().setVisible(true);
                    dispose();
                }
            });

            // Make the frame visible
            setVisible(true);
        }

        public static void main(String[] args) {
            new HomeScreen();
        }
    }
}

