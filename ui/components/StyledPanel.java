package ui.components;

import javax.swing.*;
import java.awt.*;

public class StyledPanel extends JPanel {

    public StyledPanel() {
        super();
        setOpaque(false); // Make transparent so custom painting shows
    }

    public StyledPanel(LayoutManager layout) {
        super(layout);
        setOpaque(false); // Make transparent so custom painting shows
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Semi-transparent white background for readability
        g2d.setColor(new Color(255, 255, 255, 235));
        g2d.fillRoundRect(15, 15, getWidth()-30, getHeight()-30, 20, 20);

        // Border
        g2d.setColor(new Color(75, 83, 32, 120));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(15, 15, getWidth()-30, getHeight()-30, 20, 20);

        g2d.dispose();
        super.paintComponent(g);
    }


    // Static factory method to create a new StyledPanel instance
    // This maintains compatibility with the existing createStyledPanel() method usage
    public static StyledPanel create() {
        return new StyledPanel();
    }



    // Static factory method to create a new StyledPanel with specified layout
    public static StyledPanel create(LayoutManager layout) {
        return new StyledPanel(layout);
    }


    // Creates a StyledPanel with moss green gradient background
    // Used for left panel and similar components that need this specific styling
    public static class MossGreenPanel extends JPanel {

        public MossGreenPanel() {
            super();
            setOpaque(false);
        }

        public MossGreenPanel(LayoutManager layout) {
            super(layout);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            // Create semi-transparent background with blur effect simulation
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Create a more solid overlay - increased opacity from 120 to 180
            g2d.setColor(new Color(138, 154, 91, 242)); // Moss green with 95% opacity (more solid)
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // Add a subtle gradient for depth - complementary moss green tones
            GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(120, 150, 90, 30), // Lighter moss green
                    getWidth(), 0, new Color(82, 115, 62, 10) // Same moss green with lower opacity
            );
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.dispose();
            super.paintComponent(g);
        }

        public static MossGreenPanel create() {
            return new MossGreenPanel();
        }

        public static MossGreenPanel create(LayoutManager layout) {
            return new MossGreenPanel(layout);
        }
    }
}