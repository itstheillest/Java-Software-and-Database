package ui.panels;

import main.Main;
import ui.panels.rightpanel.BarangayDocumentContent;
import ui.panels.rightpanel.DashboardContent;
import ui.panels.rightpanel.tanodScheduleAndReport;
import ui.panels.rightpanel.FileComplaintContent;
import ui.panels.rightpanel.AboutContent;
import utils.ComponentFactory;

import javax.swing.*;
import java.awt.*;

public class RightPanel{
    private Main mainFrame;
    private JPanel rightPanel;
    private DashboardContent dashboardContent;
    private FileComplaintContent fileComplaintContent;
    private BarangayDocumentContent barangayDocumentContent;
    private AboutContent aboutContent;

    public RightPanel(Main mainFrame){
        this.mainFrame = mainFrame;
        this.dashboardContent = new DashboardContent(mainFrame);
        this.barangayDocumentContent = new BarangayDocumentContent(mainFrame);
        this.aboutContent = new AboutContent();
    }

    public JPanel createRightPanel() {
        if (rightPanel == null) {
            rightPanel = new JPanel();
            rightPanel.setLayout(new BorderLayout());
        } else {
            rightPanel.removeAll();
        }

        // Make right panel transparent to show background
        rightPanel.setOpaque(false);

        // Add a subtle overlay for content when needed
        JPanel contentOverlay = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 30)); // Very subtle overlay
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        contentOverlay.setOpaque(false);
        rightPanel.add(contentOverlay, BorderLayout.CENTER);

        return rightPanel;
    }

    public JPanel createDashboardContent(String title) {
        JPanel dashboardPanel = mainFrame.createStyledPanel();
        dashboardPanel.setLayout(new BorderLayout(10, 10));
        dashboardPanel.setOpaque(false);

        // Header Section
        JPanel headerSection = ComponentFactory.getHeaderPanel(title);
        dashboardPanel.add(headerSection, BorderLayout.NORTH);

        // Use the DashboardContent instance to create panels
        JPanel statsPanel = dashboardContent.createStatsPanel();
        JPanel quickActionsPanel = dashboardContent.createQuickActionsPanel();
        JPanel recentActivitiesPanel = dashboardContent.createRecentActivitiesPanel();
        JPanel announcementsPanel = dashboardContent.createAnnouncementsPanel();

        // Main content area
        JPanel contentArea = new JPanel(new GridBagLayout());
        contentArea.setOpaque(false);
        contentArea.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        // First row - Statistics (full width)
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2; gbc.weightx = 1.0; gbc.weighty = 0.3;
        contentArea.add(statsPanel, gbc);

        // Second row - Quick Actions (left) and Recent Activities (right)
        gbc.gridy = 1; gbc.gridwidth = 1; gbc.weighty = 0.4;
        gbc.gridx = 0; gbc.weightx = 0.5;
        contentArea.add(quickActionsPanel, gbc);

        gbc.gridx = 1; gbc.weightx = 0.5;
        contentArea.add(recentActivitiesPanel, gbc);

        // Third row - Announcements (full width)
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2; gbc.weightx = 1.0; gbc.weighty = 0.3;
        contentArea.add(announcementsPanel, gbc);

        dashboardPanel.add(contentArea, BorderLayout.CENTER);

        return dashboardPanel;
    }

    public JPanel createBarangayDocumentContent(String title) {
        JPanel complaintPanel = mainFrame.createStyledPanel();
        complaintPanel.setLayout(new BorderLayout(10, 10));
        complaintPanel.setOpaque(false);

        // Get the complete card panel that contains both views
        JPanel cardPanel = barangayDocumentContent.getCardPanel();

        // Main content area
        JPanel contentArea = new JPanel(new BorderLayout());
        contentArea.setOpaque(false);
        contentArea.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        contentArea.add(cardPanel, BorderLayout.CENTER);

        complaintPanel.add(contentArea, BorderLayout.CENTER);

        return complaintPanel;
    }

    //public JPanel createFileComplaintContent(){}

    //calls tanod sched and report
    public JPanel createTanodScheduleAndReportContent (String title)
    {
        JPanel scheduleReportPanel = mainFrame.createTransStyledPanel();
        scheduleReportPanel.setLayout(new BorderLayout(10, 10));
        scheduleReportPanel.setOpaque(false);

        // Instantiate your header handler (tanodScheduleAndReport)
        tanodScheduleAndReport schedulePanelCreator = new tanodScheduleAndReport(mainFrame);

        // Get the right Panel that would show the 3 buttons
        JPanel rightPanelTanodBtns = schedulePanelCreator.getRightPanelTanod();

        // Add to the main container
        scheduleReportPanel.add(rightPanelTanodBtns, BorderLayout.CENTER);

        return scheduleReportPanel;
    }

    //public JPanel createHealthSystemRecordsContent(){}

    public JPanel createAboutContent() {
        JPanel aboutPanel = new JPanel();
        aboutPanel.setLayout(new BorderLayout());
        aboutPanel.setOpaque(false);

        // Create scrollable content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // System Information Section
        JPanel systemInfoPanel = aboutContent.createInfoSection("System Information", aboutContent.getSystemInfoContent());
        contentPanel.add(systemInfoPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // User Manual Section
        JPanel userManualPanel = aboutContent.createInfoSection("User Manual", aboutContent.getUserManualContent());
        contentPanel.add(userManualPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Contact Information Section
        JPanel contactPanel = aboutContent.createInfoSection("Technical Support", aboutContent.getContactInfoContent());
        contentPanel.add(contactPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Credits Section
        JPanel creditsPanel = aboutContent.createInfoSection("Development Team", aboutContent.getCreditsContent());
        contentPanel.add(creditsPanel);

        // Create scroll pane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Customize scrollbar
        scrollPane.getVerticalScrollBar().setOpaque(false);
        scrollPane.getVerticalScrollBar().setBackground(new Color(0, 0, 0, 0));

        aboutPanel.add(scrollPane, BorderLayout.CENTER);

        return aboutPanel;
    }
}

