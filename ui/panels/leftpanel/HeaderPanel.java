// HeaderPanel.java
package ui.panels.leftpanel;

import main.Main;
import main.ApplicationConstants;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class HeaderPanel {
    private Main mainFrame;

    public HeaderPanel(Main mainFrame) {
        this.mainFrame = mainFrame;
    }

    public JPanel createPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.dispose();
                super.paintComponent(g);
            }
        };

        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Create a centered container for logo and title
        JPanel centeredContainer = new JPanel(new BorderLayout());
        centeredContainer.setOpaque(false);

        // Logo
        JLabel logoLabel = createLogoLabel();
        JLabel titleLabel = createTitleLabel();

        centeredContainer.add(logoLabel, BorderLayout.WEST);
        centeredContainer.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(centeredContainer, BorderLayout.CENTER);

        return headerPanel;
    }

    private JLabel createLogoLabel() {
        JLabel logoLabel = new JLabel();
        try {
            // Load the logo from the same package/directory
            URL logoUrl = getClass().getResource("logo.png");

            if (logoUrl != null) {
                ImageIcon originalLogo = new ImageIcon(logoUrl);
                Image scaledImage = originalLogo.getImage().getScaledInstance(135, 135, Image.SCALE_SMOOTH);
                ImageIcon scaledLogo = new ImageIcon(scaledImage);
                logoLabel.setIcon(scaledLogo);
                System.out.println("Logo loaded successfully!");
            } else {
                System.out.println("Logo file not found in package");
                setFallbackLogo(logoLabel);
            }
        } catch (Exception e) {
            System.out.println("Error loading logo: " + e.getMessage());
            setFallbackLogo(logoLabel);
        }

        logoLabel.setVerticalAlignment(SwingConstants.CENTER);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return logoLabel;
    }

    private void setFallbackLogo(JLabel logoLabel) {
        logoLabel.setText("🏛️");
        logoLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        logoLabel.setForeground(new Color(255, 193, 7));
    }

    private JLabel createTitleLabel() {
        JLabel titleLabel = new JLabel("<html><div style='text-align: left;'><b>" +
                ApplicationConstants.SYSTEM_NAME + "</b></div></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(245, 245, 240, 200));
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        return titleLabel;
    }
}
