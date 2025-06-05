// CenterMenuPanel.java
package ui.panels.leftpanel;

import main.Main;
import main.ApplicationConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CenterMenuPanel {
    private Main mainFrame;
    private ActionListener menuActionListener;

    public CenterMenuPanel(Main mainFrame) {
        this.mainFrame = mainFrame;
    }

    // Public method to allow external classes to set custom action listeners
    public void setMenuActionListener(ActionListener listener){
        this.menuActionListener = listener;
    }

    public JPanel createPanel() {
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        // Create navigation menu panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setOpaque(false);

        // Use ApplicationConstants.MENU_ITEMS instead of hardcoded array
        String[] menuItems = ApplicationConstants.MENU_ITEMS;

        for (int i = 0; i < menuItems.length; i++) {
            String item = menuItems[i];
            final int buttonIndex = i; // Create a final copy of the index

            JButton button = createMenuButton(item, buttonIndex);
            menuPanel.add(button);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(menuPanel, gbc);

        return centerPanel;
    }

    private JButton createMenuButton(String item, int buttonIndex) {
        JButton button = new JButton(item) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Different styling for different button groups
                Color buttonColor = getButtonColor(buttonIndex);
                g2d.setColor(buttonColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                // Border
                g2d.setColor(new Color(0, 0, 0, 150));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);

                g2d.dispose();
                super.paintComponent(g);
            }
        };

        // Set button properties
        setupButtonProperties(button, buttonIndex);

        // Add event listeners
        addButtonListeners(button, item, buttonIndex);

        return button;
    }

    private Color getButtonColor(int buttonIndex) {
        if (buttonIndex >= 1 && buttonIndex <= 4) {
            // Different styling for main menu buttons - earth tones to complement moss green
            JButton tempButton = new JButton(); // Temporary button to check model state
            if (tempButton.getModel().isPressed()) {
                return new Color(139, 115, 85, 220); // Darker earth brown when pressed
            } else if (tempButton.getModel().isRollover()) {
                return new Color(160, 140, 110, 180); // Light earth brown on hover
            } else {
                return new Color(205, 190, 165, 200); // Cream/beige default
            }
        } else {
            // Original styling for Dashboard and About - forest green tones
            JButton tempButton = new JButton();
            if (tempButton.getModel().isPressed()) {
                return new Color(184, 134, 11, 220); // Dark gold
            } else if (tempButton.getModel().isRollover()) {
                return new Color(217, 158, 33, 180); // Medium gold
            } else {
                return new Color(245, 205, 92, 200); // Light gold
            }
        }
    }

    private void setupButtonProperties(JButton button, int buttonIndex) {
        // Different text color for different button groups
        if (buttonIndex >= 1 && buttonIndex <= 7) {
            button.setForeground(new Color(60, 60, 55));
        } else {
            button.setForeground(new Color(11, 18, 21));
        }

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(300, 40));
        button.setPreferredSize(new Dimension(300, 40));
        button.setFont(new Font("Verdana", Font.BOLD, 12));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
    }

    private void addButtonListeners(JButton button, String item, int buttonIndex) {
        // Enhanced hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (buttonIndex >= 1 && buttonIndex <= 7) {
                    button.setForeground(new Color(255, 255, 255));
                } else {
                    button.setForeground(new Color(255, 255, 240));
                }
                button.repaint();
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (buttonIndex >= 1 && buttonIndex <= 7) {
                    button.setForeground(new Color(60, 60, 55));
                } else {
                    button.setForeground(new Color(11, 18, 21));
                }
                button.repaint();
            }
        });

        // Action listener for menu actions
        button.addActionListener(e -> handleMenuAction(item));
    }

    // Modified method to directly call Main's menu handling
    private void handleMenuAction(String menuItem) {
        if (menuActionListener != null) {
            // Create an ActionEvent and pass it to the MenuActionListener
            ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, menuItem);
            menuActionListener.actionPerformed(event);
        } else {
            // Direct call to Main's menu selection handler
            mainFrame.handleMenuSelection(menuItem);
        }
    }
}