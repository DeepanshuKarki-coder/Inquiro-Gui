package Frontend;
import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {
    private int radius;

    public RoundedButton(String text, int radius) {
        super(text);
        this.radius = radius;
        setOpaque(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setForeground(Color.BLACK);
        setBackground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 14));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Enable anti-aliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill rounded background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getForeground());
        g2.setStroke(new BasicStroke(1));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        g2.dispose();
    }
}
