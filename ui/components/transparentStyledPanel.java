package ui.components;

import java.awt.*;
import javax.swing.*;

public class transparentStyledPanel extends JPanel {

    public transparentStyledPanel() {
        super();
        setOpaque(false); // Fully transparent
    }

    public transparentStyledPanel(LayoutManager layout) {
        super(layout);
        setOpaque(false); // Fully transparent
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Optional: remove all custom painting for a clean transparent background
        super.paintComponent(g);
    }

    // Static factory methods
    public static transparentStyledPanel create() {
        return new transparentStyledPanel();
    }

    public static transparentStyledPanel create(LayoutManager layout) {
        return new transparentStyledPanel(layout);
    }
}
