import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main extends JFrame {
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JLabel backgroundLabel;
    private Timer slideshowTimer;
    private List<String> imagePaths;
    private int currentImageIndex = 0;
    private BufferedImage currentBackgroundImage;

    // Sample data for dashboard
    private int totalRecords = 1247;
    private int pendingRequests = 23;
    private int completedToday = 8;
    private int activeUsers = 15;

    // System information constants
    private static final String SYSTEM_NAME = "Barangay Market Area Services Management System";
    private static final String VERSION = "1.0.0";
    private static final String BUILD_DATE = "May 2025";
    private static final String DEVELOPER_TEAM = "Local Government Development Team";

    private JPanel dynamicFormContainer;
    private JScrollPane formScrollPane;

    public Main() {
        // Set up the main frame
        setTitle("Barangay Market Area Services Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null); // Center the window

        // Initialize the layout
        initializeComponents();

        // Initialize slideshow
        initializeSlideshow();

        // Make the frame visible
        setVisible(true);
    }

    private void initializeComponents() {
        // Create main container with custom layout for background image
        setLayout(new BorderLayout());

        // Create a layered pane to handle background and foreground layers
        JLayeredPane layeredPane = new JLayeredPane() {
            @Override
            public void doLayout() {
                super.doLayout();
                // Ensure background always matches the current size
                if (backgroundLabel != null) {
                    backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
                }
                // Ensure split pane always matches the current size
                Component[] components = getComponentsInLayer(JLayeredPane.PALETTE_LAYER);
                for (Component comp : components) {
                    if (comp instanceof JSplitPane) {
                        comp.setBounds(0, 0, getWidth(), getHeight());
                    }
                }
            }
        };

        // Create background label that will hold the full-screen image
        backgroundLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (currentBackgroundImage != null) {
                    // Draw the background image to fill the entire component
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2d.drawImage(currentBackgroundImage, 0, 0, getWidth(), getHeight(), this);
                    g2d.dispose();
                }
            }
        };
        backgroundLabel.setOpaque(true);
        backgroundLabel.setBackground(new Color(255, 255, 240)); // Fallback color

        // Create the split pane with 30/70 ratio for foreground content
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.3);
        splitPane.setDividerSize(0);
        splitPane.setContinuousLayout(true);
        splitPane.setOpaque(false); // Make split pane transparent

        // Create left panel (30%) with semi-transparent background
        createLeftPanel();

        // Create right panel (70%) - now transparent to show background
        createRightPanel();

        // Add panels to split pane
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);

        // Add components to layered pane
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(splitPane, JLayeredPane.PALETTE_LAYER);

        // Add layered pane to main frame
        add(layeredPane, BorderLayout.CENTER);

        // Set the divider location after the window is visible
        SwingUtilities.invokeLater(() -> {
            int windowWidth = getWidth();
            int dividerLocation = (int) (windowWidth * 0.3);
            splitPane.setDividerLocation(dividerLocation);
        });
    }

    private JSeparator createHorizontalSeparator() {
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(new Color(0, 0, 0, 100)); // white-ish, semi-transparent
        return separator;
    }

    private JPanel createStyledPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Semi-transparent white background for readability
                g2d.setColor(new Color(255, 255, 255, 235));
                g2d.fillRoundRect(15, 15, getWidth()-30, getHeight()-30, 20, 20);

                // Border
                g2d.setColor(new Color(128, 0, 0, 120));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(15, 15, getWidth()-30, getHeight()-30, 20, 20);

                g2d.dispose();
                super.paintComponent(g);
            }
        };
    }

    private JLabel createDateTimeLabel() {
        JLabel dateTimeLabel = new JLabel();
        dateTimeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        dateTimeLabel.setForeground(new Color(100, 100, 100));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy - HH:mm:ss");

        // Initial time set
        dateTimeLabel.setText(LocalDateTime.now().format(formatter));

        // Update date/time every second
        Timer clockTimer = new Timer(1000, e -> {
            dateTimeLabel.setText(LocalDateTime.now().format(formatter));
        });
        clockTimer.start();

        return dateTimeLabel;
    }

    private void createLeftPanel() {
        leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Create semi-transparent background with blur effect simulation
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Create a more solid overlay - increased opacity from 120 to 180
                g2d.setColor(new Color(128, 0, 0, 242)); // Black with 70% opacity (more solid)
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Add a subtle gradient for depth - reduced transparency
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(255, 100, 100, 30), // Increased opacity
                        getWidth(), 0, new Color(128, 0, 0, 10) // Increased opacity
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.dispose();
                super.paintComponent(g);
            }
        };

        leftPanel.setLayout(new BorderLayout());
        leftPanel.setOpaque(false); // Make panel transparent so custom painting shows

        // Create the three sections with weighted heights
        JPanel headerPanel = createHeaderPanel();
        JPanel centerPanel = createCenterMenuPanel();
        JPanel footerPanel = createFooterPanel();

        // Use a custom panel with GridBagLayout to control the height ratios
        JPanel containerPanel = new JPanel(new GridBagLayout());
        containerPanel.setOpaque(false); // Make transparent

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 0, 0);

        // Header - 25% weight
        gbc.gridy = 0;
        gbc.weighty = 0.25;
        containerPanel.add(headerPanel, gbc);

        // Separator after Header
        gbc.gridy++;
        gbc.weighty = 0.01; // very small height for the separator
        containerPanel.add(createHorizontalSeparator(), gbc);

        // Center - 60% weight
        gbc.gridy = 1;
        gbc.weighty = 0.60;
        gbc.insets = new Insets(5, 5, 5, 5);
        containerPanel.add(centerPanel, gbc);

        // Separator after Center
        gbc.gridy++;
        gbc.weighty = 0.01;
        containerPanel.add(createHorizontalSeparator(), gbc);

        // Footer - 15% weight
        gbc.gridy = 2;
        gbc.weighty = 0.15;
        containerPanel.add(footerPanel, gbc);

        leftPanel.add(containerPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
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
        JLabel logoLabel = new JLabel();
        try {
            ImageIcon originalLogo = new ImageIcon("Images/logo.png");
            if (originalLogo.getIconWidth() > 0) {
                Image scaledImage = originalLogo.getImage().getScaledInstance(135, 135, Image.SCALE_SMOOTH);
                ImageIcon scaledLogo = new ImageIcon(scaledImage);
                logoLabel.setIcon(scaledLogo);
            } else {
                logoLabel.setText("🏛️");
                logoLabel.setFont(new Font("Arial", Font.PLAIN, 80));
                logoLabel.setForeground(new Color(255, 193, 7));
            }
        } catch (Exception e) {
            logoLabel.setText("🏛️");
            logoLabel.setFont(new Font("Arial", Font.PLAIN, 80));
            logoLabel.setForeground(new Color(255, 193, 7));
        }

        logoLabel.setVerticalAlignment(SwingConstants.CENTER);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Title with enhanced contrast
        JLabel titleLabel = new JLabel("<html><div style='text-align: left;'><b>Barangay Market Area<br>Services Management System</b></div></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(245, 245, 240, 200)); // Pure white for better contrast
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        centeredContainer.add(logoLabel, BorderLayout.WEST);
        centeredContainer.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(centeredContainer, BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel createCenterMenuPanel() {
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        // Create navigation menu panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setOpaque(false);

        String[] menuItems = {
                "Dashboard",        // index 0
                "Barangay Document Application",   // index 1
                "Group 2.2",   // index 2
                "Group 2.3",   // index 3
                "Group 2.4",   // index 4
                "About"        // index 5
        };

        for (int i = 0; i < menuItems.length; i++) {
            String item = menuItems[i];
            final int buttonIndex = i; // Create a final copy of the index

            JButton button = new JButton(item) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Now use buttonIndex instead of i
                    if (buttonIndex >= 1 && buttonIndex <= 4) {
                        // Different styling for Group buttons (indexes 1-4)
                        if (getModel().isPressed()) {
                            g2d.setColor(new Color(204, 173, 0, 220)); // Dark green when pressed
                        } else if (getModel().isRollover()) {
                            g2d.setColor(new Color(255, 225, 50, 180)); // Light green on hover
                        } else {
                            g2d.setColor(new Color(245, 245, 240, 200)); // Medium sea green default
                        }
                    } else {
                        // Original styling for Dashboard and About (indexes 0 and 5)
                        if (getModel().isPressed()) {
                            g2d.setColor(new Color(200, 200, 195, 220));
                        } else if (getModel().isRollover()) {
                            g2d.setColor(new Color(220, 220, 215, 180));
                        } else {
                            g2d.setColor(new Color(255, 215, 0, 200));
                        }
                    }

                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                    // Border color also changes
                    if (buttonIndex >= 1 && buttonIndex <= 4) {
                        g2d.setColor(new Color(0, 0, 0, 150)); // Green border
                    } else {
                        g2d.setColor(new Color(0, 0, 0, 150)); // Red border
                    }

                    g2d.setStroke(new BasicStroke(1));
                    g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);

                    g2d.dispose();
                    super.paintComponent(g);
                }
            };

            // Different text color for different button groups
            if (buttonIndex >= 1 && buttonIndex <= 4) {
                button.setForeground(new Color(60, 60, 55)); // White text for group buttons
            } else {
                button.setForeground(new Color(11, 18, 21)); // Dark text for Dashboard/About
            }

            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(300, 40));
            button.setPreferredSize(new Dimension(300, 40));
            button.setFont(new Font("Verdana", Font.BOLD, 12));
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setFocusPainted(false);

            // Enhanced hover effects with different colors
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (buttonIndex >= 1 && buttonIndex <= 4) {
                        button.setForeground(new Color(255, 255, 255)); // Keep white for groups
                    } else {
                        button.setForeground(new Color(255, 255, 240)); // Light color for Dashboard/About
                    }
                    button.repaint();
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (buttonIndex >= 1 && buttonIndex <= 4) {
                        button.setForeground(new Color(60, 60, 55));
                    } else {
                        button.setForeground(new Color(11, 18, 21));
                    }
                    button.repaint();
                }
            });

            button.addActionListener(new MenuActionListener(item));
            menuPanel.add(button);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(menuPanel, gbc);

        return centerPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new GridBagLayout());
        footerPanel.setOpaque(false);

        JButton adminButton = new JButton("Admin Login");
        adminButton.setFont(new Font("Arial", Font.ITALIC, 10));
        adminButton.setForeground(new Color(255, 255, 255, 200)); // White with transparency
        adminButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        adminButton.setFocusPainted(false);
        adminButton.setContentAreaFilled(false);
        adminButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        adminButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                adminButton.setForeground(new Color(255, 193, 7));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                adminButton.setForeground(new Color(255, 255, 255, 200));
            }
        });

        adminButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Admin login functionality to be implemented");
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        footerPanel.add(adminButton, gbc);

        return footerPanel;
    }

    private void createRightPanel() {
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
    }

    private JPanel createDashboardContent() {
        JPanel dashboardPanel = createStyledPanel();
        dashboardPanel.setLayout(new BorderLayout(10, 10));
        dashboardPanel.setOpaque(false);

        // Header Section
        JPanel headerSection = new JPanel(new BorderLayout());
        headerSection.setOpaque(false);
        headerSection.setBorder(BorderFactory.createEmptyBorder(25, 30, 10, 30));

        JLabel welcomeLabel = new JLabel("Welcome to Dashboard");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(128, 0, 0));

        JLabel dateTimeLabel = createDateTimeLabel();

        headerSection.add(welcomeLabel, BorderLayout.WEST);
        headerSection.add(dateTimeLabel, BorderLayout.EAST);

        // Statistics Panel
        JPanel statsPanel = createStatsPanel();

        // Quick Actions Panel
        JPanel quickActionsPanel = createQuickActionsPanel();

        // Recent Activities Panel
        JPanel recentActivitiesPanel = createRecentActivitiesPanel();

        // System Announcements Panel
        JPanel announcementsPanel = createAnnouncementsPanel();

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

        dashboardPanel.add(headerSection, BorderLayout.NORTH);
        dashboardPanel.add(contentArea, BorderLayout.CENTER);

        return dashboardPanel;
    }

    private JPanel createStatsPanel() {
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
        int[] statValues = {totalRecords, pendingRequests, completedToday, activeUsers};
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

    private JPanel createQuickActionsPanel() {
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
                    JOptionPane.showMessageDialog(this, actionName + " functionality to be implemented")
            );

            quickPanel.add(actionBtn);
        }

        return quickPanel;
    }

    private JPanel createRecentActivitiesPanel() {
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

    private JPanel createAnnouncementsPanel() {
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

    private JPanel createBarangayDocumentContent() {
        JPanel documentPanel = createStyledPanel();
        documentPanel.setLayout(new BorderLayout(10, 10));
        documentPanel.setOpaque(false);

        // Header Section
        JPanel headerSection = new JPanel(new BorderLayout());
        headerSection.setOpaque(false);
        headerSection.setBorder(BorderFactory.createEmptyBorder(25, 30, 10, 30));

        JLabel titleLabel = new JLabel("Barangay Document Application");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(128, 0, 0));

        JLabel statusLabel = new JLabel("Select document type to begin");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(Color.GRAY);

        headerSection.add(titleLabel, BorderLayout.WEST);
        headerSection.add(statusLabel, BorderLayout.EAST);

        // Document Type Selection Panel
        JPanel documentTypePanel = createDocumentTypePanel();

        // Dynamic Input Form Panel
        JPanel inputFormPanel = createDynamicInputFormPanel();

        // CRUD Actions Panel
        JPanel crudActionsPanel = createCrudActionsPanel();

        // Document Records Table Panel
        JPanel recordsTablePanel = createDocumentRecordsPanel();

        // Search and Filter Panel
        JPanel searchFilterPanel = createSearchFilterPanel();

        // Main content area
        JPanel contentArea = new JPanel(new GridBagLayout());
        contentArea.setOpaque(false);
        contentArea.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        // First row - Document Type Selection and Search/Filter
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 1; gbc.weightx = 0.6; gbc.weighty = 0.15;
        contentArea.add(documentTypePanel, gbc);

        gbc.gridx = 1; gbc.weightx = 0.4;
        contentArea.add(searchFilterPanel, gbc);

        // Second row - Input Form and CRUD Actions
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0.7; gbc.weighty = 0.4;
        contentArea.add(inputFormPanel, gbc);

        gbc.gridx = 1; gbc.weightx = 0.3;
        contentArea.add(crudActionsPanel, gbc);

        // Third row - Records Table (full width)
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2; gbc.weightx = 1.0; gbc.weighty = 0.45;
        contentArea.add(recordsTablePanel, gbc);

        documentPanel.add(headerSection, BorderLayout.NORTH);
        documentPanel.add(contentArea, BorderLayout.CENTER);

        return documentPanel;
    }

    private JPanel createDocumentTypePanel() {
        JPanel panel = createStyledPanel();
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(128, 0, 0), 2),
                "Document Type Selection",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(128, 0, 0)
        ));

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel selectLabel = new JLabel("Select Document:");
        selectLabel.setFont(new Font("Arial", Font.BOLD, 12));

        String[] documentTypes = {
                "Select Document Type",
                "Certificate of Indigency",
                "Barangay Certificate for Business",
                "Certificate of Residency",
                "Certificate of Solo Parent",
                "Barangay ID",
                "Individual Barangay Clearance",
                "Business Barangay Clearance",
                "Business Permit"
        };

        JComboBox<String> documentTypeCombo = new JComboBox<>(documentTypes);
        documentTypeCombo.setFont(new Font("Arial", Font.PLAIN, 12));
        documentTypeCombo.setPreferredSize(new Dimension(250, 30));

        // Add action listener to update form when document type changes
        documentTypeCombo.addActionListener(e -> {
            String selectedType = (String) documentTypeCombo.getSelectedItem();
            updateInputForm(selectedType);
        });

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        panel.add(selectLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.7;
        panel.add(documentTypeCombo, gbc);

        return panel;
    }

    private JPanel createDynamicInputFormPanel() {
        JPanel panel = createStyledPanel();
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(128, 0, 0), 2),
                "Document Information",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(128, 0, 0)
        ));

        // Store reference to form container
        dynamicFormContainer = new JPanel(new BorderLayout());
        dynamicFormContainer.setOpaque(false);

        JLabel instructionLabel = new JLabel("Please select a document type to display the form");
        instructionLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        instructionLabel.setForeground(Color.GRAY);
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        dynamicFormContainer.add(instructionLabel, BorderLayout.CENTER);

        // Store reference to scroll pane
        formScrollPane = new JScrollPane(dynamicFormContainer);
        formScrollPane.setOpaque(false);
        formScrollPane.getViewport().setOpaque(false);
        formScrollPane.setBorder(null);
        formScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        formScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel.setLayout(new BorderLayout());
        panel.add(formScrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCrudActionsPanel() {
        JPanel panel = createStyledPanel();
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(128, 0, 0), 2),
                "Quick Actions",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(128, 0, 0)
        ));

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 10, 8, 10);

        // Create action buttons
        JButton addButton = createActionButton("Add New Data", "icons/add.png");
        JButton updateButton = createActionButton("Update Data", "icons/edit.png");
        JButton deleteButton = createActionButton("Delete Data", "icons/delete.png");
        JButton clearButton = createActionButton("Clear Form", "icons/clear.png");
        JButton printButton = createActionButton("Print Document", "icons/print.png");

        // Add action listeners
        addButton.addActionListener(e -> handleAddDocument());
        updateButton.addActionListener(e -> handleUpdateDocument());
        deleteButton.addActionListener(e -> handleDeleteDocument());
        clearButton.addActionListener(e -> handleClearForm());
        printButton.addActionListener(e -> handlePrintDocument());

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1.0;
        panel.add(addButton, gbc);

        gbc.gridy = 1;
        panel.add(updateButton, gbc);

        gbc.gridy = 2;
        panel.add(deleteButton, gbc);

        gbc.gridy = 3;
        panel.add(clearButton, gbc);

        gbc.gridy = 4;
        panel.add(printButton, gbc);

        return panel;
    }

    private JButton createActionButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setPreferredSize(new Dimension(140, 35));
        button.setBackground(new Color(128, 0, 0));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(160, 0, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(128, 0, 0));
            }
        });

        return button;
    }

    private JPanel createSearchFilterPanel() {
        JPanel panel = createStyledPanel();
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(128, 0, 0), 2),
                "Search & Filter",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(128, 0, 0)
        ));

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 12));

        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 12));
        searchField.setPreferredSize(new Dimension(200, 25));

        JLabel filterLabel = new JLabel("Filter by Status:");
        filterLabel.setFont(new Font("Arial", Font.BOLD, 12));

        String[] statusOptions = {"All", "Pending", "Approved", "Released", "Rejected"};
        JComboBox<String> statusFilter = new JComboBox<>(statusOptions);
        statusFilter.setFont(new Font("Arial", Font.PLAIN, 12));
        statusFilter.setPreferredSize(new Dimension(120, 25));

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 10));
        searchButton.setBackground(new Color(128, 0, 0));
        searchButton.setForeground(Color.BLACK);
        searchButton.setPreferredSize(new Dimension(80, 25));

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        panel.add(searchLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.7;
        panel.add(searchField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        panel.add(filterLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.7;
        panel.add(statusFilter, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(searchButton, gbc);

        return panel;
    }

    private JPanel createDocumentRecordsPanel() {
        JPanel panel = createStyledPanel();
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(128, 0, 0), 2),
                "Document Records",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(128, 0, 0)
        ));

        // Create table with sample columns (will be dynamic based on document type)
        String[] columnNames = {
                "ID", "Document Type", "Applicant Name", "Date Applied",
                "Status", "Date Processed", "Processed By"
        };

        Object[][] sampleData = {
                {"001", "Certificate of Indigency", "Juan Dela Cruz", "2024-01-15", "Approved", "2024-01-16", "Admin"},
                {"002", "Barangay Clearance", "Maria Santos", "2024-01-14", "Pending", "-", "-"},
                {"003", "Certificate of Residency", "Pedro Garcia", "2024-01-13", "Released", "2024-01-15", "Admin"}
        };

        JTable documentsTable = new JTable(sampleData, columnNames);
        documentsTable.setFont(new Font("Arial", Font.PLAIN, 11));
        documentsTable.setRowHeight(25);
        documentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        documentsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Customize table header
        JTableHeader header = documentsTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setBackground(new Color(128, 0, 0));
        header.setForeground(Color.WHITE);

        JScrollPane tableScrollPane = new JScrollPane(documentsTable);
        tableScrollPane.setPreferredSize(new Dimension(800, 200));

        panel.setLayout(new BorderLayout());
        panel.add(tableScrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCommonFields() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Full Name
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField fullNameField = new JTextField(20);
        panel.add(fullNameField, gbc);

        // Address
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField addressField = new JTextField(20);
        panel.add(addressField, gbc);

        // Contact Number
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Contact Number:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField contactField = new JTextField(20);
        panel.add(contactField, gbc);

        return panel;
    }

    private JPanel createCertificateOfIndigencyForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add common fields
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(createCommonFields(), gbc);

        // Specific fields for Indigency Certificate
        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;

        // Age
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField ageField = new JTextField(20);
        formPanel.add(ageField, gbc);

        // Civil Status
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Civil Status:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        String[] civilStatus = {"Single", "Married", "Widowed", "Divorced", "Separated"};
        JComboBox<String> civilStatusCombo = new JComboBox<>(civilStatus);
        formPanel.add(civilStatusCombo, gbc);

        // Monthly Income
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Monthly Income:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField incomeField = new JTextField(20);
        formPanel.add(incomeField, gbc);

        // Purpose
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Purpose:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0;
        JTextArea purposeArea = new JTextArea(3, 20);
        purposeArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane purposeScroll = new JScrollPane(purposeArea);
        formPanel.add(purposeScroll, gbc);

        return formPanel;
    }

    private JPanel createBarangayCertificateForBusinessForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add common fields
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(createCommonFields(), gbc);

        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;

        // Business Name
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Business Name:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField businessNameField = new JTextField(20);
        formPanel.add(businessNameField, gbc);

        // Business Type
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Business Type:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        String[] businessTypes = {"Retail", "Service", "Manufacturing", "Food", "Other"};
        JComboBox<String> businessTypeCombo = new JComboBox<>(businessTypes);
        formPanel.add(businessTypeCombo, gbc);

        // Business Address
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Business Address:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField businessAddressField = new JTextField(20);
        formPanel.add(businessAddressField, gbc);

        // Nature of Business
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Nature of Business:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0;
        JTextArea natureArea = new JTextArea(3, 20);
        natureArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane natureScroll = new JScrollPane(natureArea);
        formPanel.add(natureScroll, gbc);

        return formPanel;
    }

    private JPanel createCertificateOfResidencyForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add common fields
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(createCommonFields(), gbc);

        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;

        // Years of Residency
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Years of Residency:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField yearsField = new JTextField(20);
        formPanel.add(yearsField, gbc);

        // Date of Birth
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField dobField = new JTextField(20);
        dobField.setToolTipText("MM/DD/YYYY");
        formPanel.add(dobField, gbc);

        // Place of Birth
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Place of Birth:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField pobField = new JTextField(20);
        formPanel.add(pobField, gbc);

        // Purpose
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Purpose:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0;
        JTextArea purposeArea = new JTextArea(3, 20);
        purposeArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane purposeScroll = new JScrollPane(purposeArea);
        formPanel.add(purposeScroll, gbc);

        return formPanel;
    }

    private JPanel createBarangayIDForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add common fields
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(createCommonFields(), gbc);

        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;

        // Date of Birth
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField dobField = new JTextField(20);
        dobField.setToolTipText("MM/DD/YYYY");
        formPanel.add(dobField, gbc);

        // Emergency Contact
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Emergency Contact:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField emergencyContactField = new JTextField(20);
        formPanel.add(emergencyContactField, gbc);

        // Emergency Contact Number
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Emergency Contact Number:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField emergencyNumberField = new JTextField(20);
        formPanel.add(emergencyNumberField, gbc);

        // Photo Upload Button
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Photo:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JButton photoButton = new JButton("Upload Photo");
        photoButton.setBackground(new Color(128, 0, 0));
        photoButton.setForeground(Color.WHITE);
        formPanel.add(photoButton, gbc);

        return formPanel;
    }

    private JPanel createCertificateOfSoloParentForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add common fields
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(createCommonFields(), gbc);

        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;

        // Date of Birth
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField dobField = new JTextField(20);
        dobField.setToolTipText("MM/DD/YYYY");
        formPanel.add(dobField, gbc);

        // Number of Children
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Number of Children:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField childrenField = new JTextField(20);
        formPanel.add(childrenField, gbc);

        // Monthly Income
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Monthly Income:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField incomeField = new JTextField(20);
        formPanel.add(incomeField, gbc);

        // Employment Status
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Employment Status:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        String[] employmentStatus = {"Employed", "Unemployed", "Self-Employed", "Part-time"};
        JComboBox<String> employmentCombo = new JComboBox<>(employmentStatus);
        formPanel.add(employmentCombo, gbc);

        // Reason for Solo Parent Status
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Reason for Solo Parent Status:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        String[] reasons = {"Death of Spouse", "Abandonment", "Separation", "Single Mother", "Other"};
        JComboBox<String> reasonCombo = new JComboBox<>(reasons);
        formPanel.add(reasonCombo, gbc);

        // Additional Details
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Additional Details:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0;
        JTextArea detailsArea = new JTextArea(3, 20);
        detailsArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane detailsScroll = new JScrollPane(detailsArea);
        formPanel.add(detailsScroll, gbc);

        return formPanel;
    }

    private JPanel createIndividualClearanceForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add common fields
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(createCommonFields(), gbc);

        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;

        // Date of Birth
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField dobField = new JTextField(20);
        dobField.setToolTipText("MM/DD/YYYY");
        formPanel.add(dobField, gbc);

        // Place of Birth
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Place of Birth:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField pobField = new JTextField(20);
        formPanel.add(pobField, gbc);

        // Civil Status
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Civil Status:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        String[] civilStatus = {"Single", "Married", "Widowed", "Divorced", "Separated"};
        JComboBox<String> civilStatusCombo = new JComboBox<>(civilStatus);
        formPanel.add(civilStatusCombo, gbc);

        // Years of Residency
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Years of Residency:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField yearsField = new JTextField(20);
        formPanel.add(yearsField, gbc);

        // Clearance Type
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Clearance Type:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        String[] clearanceTypes = {"Employment", "Travel", "Loan Application", "School Requirement", "Other"};
        JComboBox<String> clearanceTypeCombo = new JComboBox<>(clearanceTypes);
        formPanel.add(clearanceTypeCombo, gbc);

        // Purpose
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Purpose:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0;
        JTextArea purposeArea = new JTextArea(3, 20);
        purposeArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane purposeScroll = new JScrollPane(purposeArea);
        formPanel.add(purposeScroll, gbc);

        return formPanel;
    }

    private JPanel createBusinessClearanceForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add common fields (Owner information)
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel ownerPanel = createCommonFields();
        ownerPanel.setBorder(BorderFactory.createTitledBorder("Owner Information"));
        formPanel.add(ownerPanel, gbc);

        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;

        // Business Name
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Business Name:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField businessNameField = new JTextField(20);
        formPanel.add(businessNameField, gbc);

        // Business Address
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Business Address:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField businessAddressField = new JTextField(20);
        formPanel.add(businessAddressField, gbc);

        // Business Type
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Business Type:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        String[] businessTypes = {"Retail", "Service", "Manufacturing", "Food & Beverage", "Healthcare", "Technology", "Other"};
        JComboBox<String> businessTypeCombo = new JComboBox<>(businessTypes);
        formPanel.add(businessTypeCombo, gbc);

        // Date Established
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Date Established:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField dateEstablishedField = new JTextField(20);
        dateEstablishedField.setToolTipText("MM/DD/YYYY");
        formPanel.add(dateEstablishedField, gbc);

        // Number of Employees
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Number of Employees:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField employeesField = new JTextField(20);
        formPanel.add(employeesField, gbc);

        // Nature of Business
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Nature of Business:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0;
        JTextArea natureArea = new JTextArea(3, 20);
        natureArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane natureScroll = new JScrollPane(natureArea);
        formPanel.add(natureScroll, gbc);

        return formPanel;
    }

    private JPanel createBusinessPermitForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Owner Information
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel ownerPanel = createCommonFields();
        ownerPanel.setBorder(BorderFactory.createTitledBorder("Business Owner Information"));
        formPanel.add(ownerPanel, gbc);

        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;

        // Business Registration Number
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("DTI/SEC Registration No:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField registrationField = new JTextField(20);
        formPanel.add(registrationField, gbc);

        // Business Name
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Business Name:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField businessNameField = new JTextField(20);
        formPanel.add(businessNameField, gbc);

        // Business Address
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Business Address:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField businessAddressField = new JTextField(20);
        formPanel.add(businessAddressField, gbc);

        // Business Category
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Business Category:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        String[] categories = {"Micro", "Small", "Medium", "Large"};
        JComboBox<String> categoryCombo = new JComboBox<>(categories);
        formPanel.add(categoryCombo, gbc);

        // Capital Investment
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Capital Investment:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField capitalField = new JTextField(20);
        formPanel.add(capitalField, gbc);

        // Number of Employees
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Number of Employees:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField employeesField = new JTextField(20);
        formPanel.add(employeesField, gbc);

        // Gross Sales/Receipts
        gbc.gridx = 0; gbc.gridy = 7; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Expected Gross Sales/Receipts:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField grossSalesField = new JTextField(20);
        formPanel.add(grossSalesField, gbc);

        // Business Activities
        gbc.gridx = 0; gbc.gridy = 8; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Business Activities:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0;
        JTextArea activitiesArea = new JTextArea(3, 20);
        activitiesArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane activitiesScroll = new JScrollPane(activitiesArea);
        formPanel.add(activitiesScroll, gbc);

        return formPanel;
    }

    private void updateInputForm(String documentType) {
        // Clear existing content
        dynamicFormContainer.removeAll();

        // Add new form based on document type
        switch (documentType) {
            case "Certificate of Indigency":
                dynamicFormContainer.add(createCertificateOfIndigencyForm(), BorderLayout.CENTER);
                break;
            case "Barangay Certificate for Business":
                dynamicFormContainer.add(createBarangayCertificateForBusinessForm(), BorderLayout.CENTER);
                break;
            case "Certificate of Residency":
                dynamicFormContainer.add(createCertificateOfResidencyForm(), BorderLayout.CENTER);
                break;
            case "Certificate of Solo Parent":
                dynamicFormContainer.add(createCertificateOfSoloParentForm(), BorderLayout.CENTER);
                break;
            case "Barangay ID":
                dynamicFormContainer.add(createBarangayIDForm(), BorderLayout.CENTER);
                break;
            case "Individual Barangay Clearance":
                dynamicFormContainer.add(createIndividualClearanceForm(), BorderLayout.CENTER);
                break;
            case "Business Barangay Clearance":
                dynamicFormContainer.add(createBusinessClearanceForm(), BorderLayout.CENTER);
                break;
            case "Business Permit":
                dynamicFormContainer.add(createBusinessPermitForm(), BorderLayout.CENTER);
                break;
            default:
                JLabel instructionLabel = new JLabel("Please select a document type to display the form");
                instructionLabel.setFont(new Font("Arial", Font.ITALIC, 12));
                instructionLabel.setForeground(Color.GRAY);
                instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
                dynamicFormContainer.add(instructionLabel, BorderLayout.CENTER);
                break;
        }

        // Refresh the display
        dynamicFormContainer.revalidate();
        dynamicFormContainer.repaint();
    }

    // CRUD Action Handlers (placeholder methods)
    private void handleAddDocument() {
        System.out.println("Add document action triggered");
        // Implementation will connect to database
    }

    private void handleUpdateDocument() {
        System.out.println("Update document action triggered");
        // Implementation will connect to database
    }

    private void handleDeleteDocument() {
        System.out.println("Delete document action triggered");
        // Implementation will connect to database
    }

    private void handleClearForm() {
        System.out.println("Clear form action triggered");
        // Clear all input fields
    }

    private void handlePrintDocument() {
        System.out.println("Print document action triggered");
        // Generate and print document
    }

    private JPanel createAboutContent() {
        JPanel aboutPanel = new JPanel();
        aboutPanel.setLayout(new BorderLayout());
        aboutPanel.setOpaque(false);

        // Create scrollable content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // System Information Section
        JPanel systemInfoPanel = createInfoSection("System Information", getSystemInfoContent());
        contentPanel.add(systemInfoPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // User Manual Section
        JPanel userManualPanel = createInfoSection("User Manual", getUserManualContent());
        contentPanel.add(userManualPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Contact Information Section
        JPanel contactPanel = createInfoSection("Technical Support", getContactInfoContent());
        contentPanel.add(contactPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Credits Section
        JPanel creditsPanel = createInfoSection("Development Team", getCreditsContent());
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

    private JPanel createInfoSection(String title, String content) {
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

    private String getSystemInfoContent() {
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
                SYSTEM_NAME, VERSION, BUILD_DATE,
                System.getProperty("java.version"),
                System.getProperty("os.name") + " " + System.getProperty("os.version")
        );
    }

    private String getUserManualContent() {
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

    private String getContactInfoContent() {
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

    private String getCreditsContent() {
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
                DEVELOPER_TEAM
        );
    }

    private void initializeSlideshow() {
        if (slideshowTimer != null) {
            slideshowTimer.stop();
            slideshowTimer = null;
        }

        imagePaths = new ArrayList<>();
        imagePaths.add("Images/pic1.jpg");
        imagePaths.add("Images/pic2.jpg");
        imagePaths.add("Images/pic3.jpg");

        currentImageIndex = 0;

        if (!imagePaths.isEmpty()) {
            showCurrentImage();
            startSlideshow();
        } else {
            showNoImagesMessage();
        }
    }

    private void showNoImagesMessage() {
        // Create a default background when no images are available
        currentBackgroundImage = new BufferedImage(1200, 800, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = currentBackgroundImage.createGraphics();
        g.setColor(new Color(255, 255, 240));
        g.fillRect(0, 0, 1200, 800);
        g.setColor(new Color(200, 200, 200));
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("No Background Images Found", 400, 400);
        g.dispose();

        backgroundLabel.repaint();
    }

    private void showCurrentImage() {
        if (imagePaths.isEmpty()) return;

        String imagePath = imagePaths.get(currentImageIndex);

        try {
            ImageIcon originalIcon = new ImageIcon(imagePath);
            if (originalIcon.getIconWidth() > 0) {
                // Convert to BufferedImage for background
                Image img = originalIcon.getImage();
                currentBackgroundImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
                Graphics2D g = currentBackgroundImage.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g.drawImage(img, 0, 0, null);
                g.dispose();

                backgroundLabel.repaint();
            } else {
                showNoImagesMessage();
            }
        } catch (Exception e) {
            showNoImagesMessage();
            System.out.println("Error loading image: " + e.getMessage());
        }
    }

    private void showNextImage() {
        if (imagePaths.isEmpty()) return;
        currentImageIndex = (currentImageIndex + 1) % imagePaths.size();
        showCurrentImage();
    }

    private void startSlideshow() {
        if (slideshowTimer != null) {
            slideshowTimer.stop();
        }

        slideshowTimer = new Timer(3000, e -> showNextImage());
        slideshowTimer.start();
    }

    private void pauseSlideshow() {
        if (slideshowTimer != null) {
            slideshowTimer.stop();
            slideshowTimer = null;
        }
    }

    // Action listener for menu buttons
    private class MenuActionListener implements ActionListener {
        private String menuItem;

        public MenuActionListener(String menuItem) {
            this.menuItem = menuItem;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // When menu item is clicked, show content with background still visible
            pauseSlideshow();

            rightPanel.removeAll();

            // Handle different menu items
            switch (menuItem.toLowerCase()) {
                case "dashboard":
                    // For dashboard, add the complete dashboard
                    JPanel dashboardContent = createDashboardContent();
                    rightPanel.add(dashboardContent, BorderLayout.CENTER);
                    break;

                case "barangay document application":
                    JPanel software1Content = createBarangayDocumentContent();
                    rightPanel.add(software1Content, BorderLayout.CENTER);
                    break;

                case "about":
                    // For dashboard, add the complete dashboard
                    JPanel aboutContent = createAboutContent();
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
            JPanel contentPanel = createStyledPanel();
            contentPanel.setLayout(new BorderLayout(10, 10));
            contentPanel.setOpaque(false);

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
                createRightPanel();
                initializeSlideshow();
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

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new Main());
    }
}
