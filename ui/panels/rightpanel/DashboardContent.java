package ui.panels.rightpanel;

import main.ApplicationConstants;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class DashboardContent {
    private JFrame mainFrame;

    public DashboardContent(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        statsPanel.setOpaque(false);
        statsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(128, 0, 0, 100), 1),
                "System Statistics",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(128, 0, 0)
        ));

        String[] statTitles = {"Total Records", "Pending Requests", "Completed Today", "Active Users"};
        int[] statValues = {ApplicationConstants.totalRecords, ApplicationConstants.pendingRequests,
                ApplicationConstants.completedToday, ApplicationConstants.activeUsers};
        Color[] statColors = {
                new Color(52, 152, 219),
                new Color(241, 196, 15),
                new Color(46, 204, 113),
                new Color(155, 89, 182)
        };

        for (int i = 0; i < statTitles.length; i++) {
            final int index = i;
            JPanel statCard = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    g2d.setColor(statColors[index]);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                    g2d.setColor(new Color(255, 255, 255, 50));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight()/2, 10, 10);

                    g2d.dispose();
                    super.paintComponent(g);
                }
            };
            statCard.setLayout(new BorderLayout());
            statCard.setOpaque(false);

            JLabel valueLabel = new JLabel(String.valueOf(statValues[i]), SwingConstants.CENTER);
            valueLabel.setFont(new Font("Arial", Font.BOLD, 28));
            valueLabel.setForeground(Color.WHITE);

            JLabel titleLabel = new JLabel(statTitles[i], SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

            statCard.add(valueLabel, BorderLayout.CENTER);
            statCard.add(titleLabel, BorderLayout.SOUTH);

            statsPanel.add(statCard);
        }

        return statsPanel;
    }

    public JPanel createQuickActionsPanel() {
        JPanel quickPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        quickPanel.setOpaque(false);
        quickPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(128, 0, 0, 100), 1),
                "Quick Actions",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(128, 0, 0)
        ));

        String[] actionNames = {"New Record", "Search ", "Reports ", "Settings", "Backup ", "Export "};
        String[] actionIcons = {"➕", "🔍", "📊", "⚙️", "💾", "📤"};

        for (int i = 0; i < actionNames.length; i++) {
            final String actionName = actionNames[i];
            JButton actionBtn = new JButton() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    if (getModel().isPressed()) {
                        g2d.setColor(new Color(108, 117, 125));
                    } else if (getModel().isRollover()) {
                        g2d.setColor(new Color(52, 152, 219));
                    } else {
                        g2d.setColor(new Color(73, 80, 87));
                    }

                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2d.dispose();
                    super.paintComponent(g);
                }
            };

            actionBtn.setText("<html><div style='text-align:center; white-space:nowrap; line-height:1.1;'>" +
                    "<span style='font-size:18px;'>" + actionIcons[i] + "</span><br>" +
                    "<span style='font-size:10px;'>" + actionName + "</span>" +
                    "</div></html>");
            actionBtn.setFont(new Font("Arial", Font.PLAIN, 11));
            actionBtn.setForeground(Color.WHITE);
            actionBtn.setContentAreaFilled(false);
            actionBtn.setBorderPainted(false);
            actionBtn.setFocusPainted(false);

            actionBtn.addActionListener(e ->
                    JOptionPane.showMessageDialog(mainFrame, actionName + " functionality to be implemented")
            );

            quickPanel.add(actionBtn);
        }

        return quickPanel;
    }

    public JPanel createRecentActivitiesPanel() {
        JPanel activitiesPanel = new JPanel(new BorderLayout());
        activitiesPanel.setOpaque(false);
        activitiesPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(128, 0, 0, 100), 1),
                "Recent Activities",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(128, 0, 0)
        ));

        String[] activities = {
                "New vendor registration - Maria Santos",
                "Permit renewal - Juan Dela Cruz",
                "Fee payment - ABC Store",
                "Document verification - City Market",
                "System backup completed"
        };

        JPanel activityList = new JPanel();
        activityList.setLayout(new BoxLayout(activityList, BoxLayout.Y_AXIS));
        activityList.setOpaque(false);

        for (String activity : activities) {
            JLabel activityLabel = new JLabel("• " + activity);
            activityLabel.setFont(new Font("Arial", Font.PLAIN, 11));
            activityLabel.setForeground(new Color(60, 60, 60));
            activityLabel.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
            activityList.add(activityLabel);
        }

        JScrollPane scrollPane = new JScrollPane(activityList);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        activitiesPanel.add(scrollPane, BorderLayout.CENTER);
        return activitiesPanel;
    }

    public JPanel createAnnouncementsPanel() {
        JPanel announcementsPanel = new JPanel(new BorderLayout());
        announcementsPanel.setOpaque(false);
        announcementsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(128, 0, 0, 100), 1),
                "System Announcements",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(128, 0, 0)
        ));

        JTextArea announcementText = new JTextArea();
        announcementText.setText("📢 System Maintenance Notice: Scheduled maintenance on Sunday 2:00-4:00 AM\n" +
                "🎉 New Feature: Online permit application now available\n" +
                "⚠️ Reminder: Monthly reports due by end of week");
        announcementText.setFont(new Font("Arial", Font.PLAIN, 12));
        announcementText.setForeground(new Color(60, 60, 60));
        announcementText.setEditable(false);
        announcementText.setOpaque(false);
        announcementText.setLineWrap(true);
        announcementText.setWrapStyleWord(true);
        announcementText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        announcementsPanel.add(announcementText, BorderLayout.CENTER);
        return announcementsPanel;
    }
}