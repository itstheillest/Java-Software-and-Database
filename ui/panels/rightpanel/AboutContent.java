package ui.panels.rightpanel;

import main.ApplicationConstants;

import javax.swing.*;
import java.awt.*;

public class AboutContent {

    public AboutContent() {
        // Constructor - no mainFrame needed for About content
    }

    public JPanel createInfoSection(String title, String content) {
        JPanel sectionPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Semi-transparent background
                g2d.setColor(new Color(255, 255, 255, 200));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                // Border
                g2d.setColor(new Color(128, 0, 0, 150));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);

                g2d.dispose();
                super.paintComponent(g);
            }
        };
        sectionPanel.setLayout(new BorderLayout());
        sectionPanel.setOpaque(false);
        sectionPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(128, 0, 0));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Content
        JTextArea contentArea = new JTextArea(content);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 12));
        contentArea.setEditable(false);
        contentArea.setOpaque(false);
        contentArea.setForeground(new Color(60, 60, 60));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);

        sectionPanel.add(titleLabel, BorderLayout.NORTH);
        sectionPanel.add(contentArea, BorderLayout.CENTER);

        return sectionPanel;
    }

    public String getSystemInfoContent() {
        return String.format(
                "System Name: %s\n\n" +
                        "Version: %s\n" +
                        "Build Date: %s\n" +
                        "Platform: Java Swing Application\n" +
                        "Java Version: %s\n" +
                        "Operating System: %s\n\n" +
                        "Description:\n" +
                        "This system is designed to streamline the management of barangay market area services, " +
                        "providing efficient tools for market administration, vendor management, and service coordination. " +
                        "The application features an intuitive interface with slideshow backgrounds and organized navigation.",
                ApplicationConstants.SYSTEM_NAME, ApplicationConstants.VERSION, ApplicationConstants.BUILD_DATE,
                System.getProperty("java.version"),
                System.getProperty("os.name") + " " + System.getProperty("os.version")
        );
    }

    public String getUserManualContent() {
        return "Quick Start Guide:\n\n" +
                "1. Navigation:\n" +
                "   • Use the left panel menu to navigate between sections\n" +
                "   • Dashboard: Returns to the main slideshow view\n" +
                "   • Group sections: Access specific management functions\n" +
                "   • About: View system information and help\n\n" +
                "2. Interface Features:\n" +
                "   • Background slideshow automatically cycles through images\n" +
                "   • Semi-transparent overlays maintain visual appeal\n" +
                "   • Admin login available at the bottom of the left panel\n\n" +
                "3. Content Areas:\n" +
                "   • Each section displays relevant information and tools\n" +
                "   • Use 'Return to Main' to go back to the slideshow\n" +
                "   • Content is displayed with readable overlays\n\n" +
                "4. System Requirements:\n" +
                "   • Java Runtime Environment 8 or higher\n" +
                "   • Minimum screen resolution: 1024x768\n" +
                "   • Images folder with slideshow pictures (optional)";
    }

    public String getContactInfoContent() {
        return "Technical Support Information:\n\n" +
                "For technical assistance, please contact:\n\n" +
                "Primary Support:\n" +
                "📧 Email: support@barangaymarket.gov.ph\n" +
                "📞 Phone: (02) 8XXX-XXXX\n" +
                "🕒 Hours: Monday-Friday, 8:00 AM - 5:00 PM\n\n" +
                "Emergency Support:\n" +
                "📧 Email: emergency@barangaymarket.gov.ph\n" +
                "📞 Hotline: (02) 8XXX-YYYY\n" +
                "🕒 Hours: 24/7 for critical system issues\n\n" +
                "Local IT Department:\n" +
                "🏢 Address: Barangay Hall, IT Department\n" +
                "           Local Government Building\n" +
                "📧 Email: it@barangay.gov.ph\n\n" +
                "System Administrator:\n" +
                "👤 Name: [System Admin Name]\n" +
                "📧 Email: admin@barangaymarket.gov.ph\n\n" +
                "When reporting issues, please include:\n" +
                "• Error messages (if any)\n" +
                "• Steps to reproduce the problem\n" +
                "• System information\n" +
                "• Screenshots (if applicable)";
    }

    public String getCreditsContent() {
        return String.format(
                "Development Team Credits:\n\n" +
                        "Project Team: %s\n\n" +
                        "System Architecture & Design:\n" +
                        "• Lead Developer: [Lead Developer Name]\n" +
                        "• UI/UX Designer: [Designer Name]\n" +
                        "• System Analyst: [Analyst Name]\n\n" +
                        "Development Contributors:\n" +
                        "• Frontend Development: [Frontend Dev Team]\n" +
                        "• Backend Integration: [Backend Dev Team]\n" +
                        "• Quality Assurance: [QA Team]\n\n" +
                        "Project Management:\n" +
                        "• Project Manager: [PM Name]\n" +
                        "• Technical Lead: [Tech Lead Name]\n" +
                        "• Business Analyst: [BA Name]\n\n" +
                        "Special Thanks:\n" +
                        "• Barangay Officials for requirements and feedback\n" +
                        "• Market vendors for user testing and insights\n" +
                        "• Local government IT support team\n" +
                        "• Community stakeholders for continuous support\n\n" +
                        "Technology Stack:\n" +
                        "• Java Swing for GUI framework\n" +
                        "• Custom graphics and painting for visual effects\n" +
                        "• Timer-based slideshow implementation\n" +
                        "• Layered pane architecture for transparency effects\n\n" +
                        "Version History:\n" +
                        "• v1.0.0 - Initial release with core functionality\n" +
                        "• Future updates will include additional features\n\n" +
                        "© 2025 Local Government Development Team\n" +
                        "All rights reserved.",
                ApplicationConstants.DEVELOPER_TEAM
        );
    }
}