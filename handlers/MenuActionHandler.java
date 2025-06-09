package handlers;

import ui.panels.RightPanel;
import ui.components.StyledPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MenuActionHandler {
    private RightPanel rightPanelManager;
    private JPanel rightPanel;
    private SlideshowHandler slideshowHandler;
    private JFrame parentFrame;

    public MenuActionHandler(RightPanel rightPanelManager, JPanel rightPanel,
                             SlideshowHandler slideshowHandler, JFrame parentFrame) {
        this.rightPanelManager = rightPanelManager;
        this.rightPanel = rightPanel;
        this.slideshowHandler = slideshowHandler;
        this.parentFrame = parentFrame;
    }

    public void handleMenuSelection(String menuItem) {
        // Create an ActionEvent to pass to the action performed method
        ActionEvent event = new ActionEvent(parentFrame, ActionEvent.ACTION_PERFORMED, menuItem);

        // Handle the menu action
        handleMenuAction(menuItem, event);
    }

    public void handleMenuAction(String menuItem, ActionEvent e) {
        // When menu item is clicked, show content with background still visible
        slideshowHandler.pauseSlideshow();

        rightPanel.removeAll();

        // Handle different menu items
        switch (menuItem.toLowerCase()) {
            case "dashboard":
                // For dashboard, add the complete dashboard
                JPanel dashboardContent = rightPanelManager.createDashboardContent("Welcome to Dashboard");
                rightPanel.add(dashboardContent, BorderLayout.CENTER);
                break;

            case "barangay document application":
                JPanel software1Content = rightPanelManager.createBarangayDocumentContent("Barangay Document Application");
                rightPanel.add(software1Content, BorderLayout.CENTER);
                break;

            case "file complaints":
                //Inset code here
                createGenericContent(menuItem);
                break;

            case "barangay tanod records":
                //Insert code here
                createGenericContent(menuItem);
                break;

            case "health system records":
                //Insert code here
                createGenericContent(menuItem);
                break;

            case "about":
                // For about, add the complete about content
                JPanel aboutContent = rightPanelManager.createAboutContent();
                rightPanel.add(aboutContent, BorderLayout.CENTER);
                break;

            default:
                // For other menu items, show the generic content
                createGenericContent(menuItem);
                break;
        }

        rightPanel.revalidate();
        rightPanel.repaint();
    }

    private void createGenericContent(String menuTitle) {
        JPanel contentPanel = StyledPanel.create();
        contentPanel.setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel(menuTitle + " Section");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(128, 0, 0));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 40, 20, 40));

        JTextArea contentArea = new JTextArea("This is the " + menuTitle + " section.\n\nYou can add specific functionality here.\n\nThe background image remains visible with a beautiful overlay effect.");
        contentArea.setFont(new Font("Arial", Font.PLAIN, 14));
        contentArea.setEditable(false);
        contentArea.setOpaque(false);
        contentArea.setForeground(new Color(50, 50, 50));
        contentArea.setBorder(BorderFactory.createEmptyBorder(0, 40, 20, 40));

        JButton backToSlideshowButton = new JButton("Return to Main") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2d.setColor(new Color(164, 4, 10));
                } else if (getModel().isRollover()) {
                    g2d.setColor(new Color(200, 50, 50));
                } else {
                    g2d.setColor(new Color(128, 0, 0));
                }

                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2d.dispose();
                super.paintComponent(g);
            }
        };

        backToSlideshowButton.setForeground(Color.WHITE);
        backToSlideshowButton.setFont(new Font("Arial", Font.BOLD, 12));
        backToSlideshowButton.setContentAreaFilled(false);
        backToSlideshowButton.setBorderPainted(false);
        backToSlideshowButton.setFocusPainted(false);
        backToSlideshowButton.setPreferredSize(new Dimension(150, 35));

        backToSlideshowButton.addActionListener(actionEvent -> {
            rightPanelManager.createRightPanel();
            slideshowHandler.initializeSlideshow();
            rightPanel.revalidate();
            rightPanel.repaint();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(backToSlideshowButton);

        contentPanel.add(titleLabel, BorderLayout.NORTH);
        contentPanel.add(contentArea, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        rightPanel.add(contentPanel, BorderLayout.CENTER);
    }
}