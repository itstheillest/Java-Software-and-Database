import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.runtime.SwitchBootstraps;
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
                "Home",        // index 0
                "Group 2.1",   // index 1
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
                        // Original styling for Home and About (indexes 0 and 5)
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
                button.setForeground(new Color(11, 18, 21)); // Dark text for Home/About
            }

            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(220, 40));
            button.setPreferredSize(new Dimension(220, 40));
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
                        button.setForeground(new Color(255, 255, 240)); // Light color for Home/About
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

    private JPanel createHomeContent() {
        JPanel homePanel = new JPanel() {
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
        homePanel.setLayout(new BorderLayout(10, 10));
        homePanel.setOpaque(false);

        // Header Section
        JPanel headerSection = new JPanel(new BorderLayout());
        headerSection.setOpaque(false);
        headerSection.setBorder(BorderFactory.createEmptyBorder(25, 30, 10, 30));

        JLabel welcomeLabel = new JLabel("Welcome to Dashboard");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(128, 0, 0));

        JLabel dateTimeLabel = new JLabel();
        dateTimeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        dateTimeLabel.setForeground(new Color(100, 100, 100));

        // Update date/time
        Timer clockTimer = new Timer(1000, e -> {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy - HH:mm:ss");
            dateTimeLabel.setText(now.format(formatter));
        });
        clockTimer.start();

        // Initial time set
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy - HH:mm:ss");
        dateTimeLabel.setText(now.format(formatter));

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

        homePanel.add(headerSection, BorderLayout.NORTH);
        homePanel.add(contentArea, BorderLayout.CENTER);

        return homePanel;
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

        String[] actionNames = {"New Record", "Search", "Reports", "Settings", "Backup", "Export"};
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

            actionBtn.setText("<html><center>" + actionIcons[i] + "<br>" + actionName + "</center></html>");
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
                case "home":
                    // For home, add the complete home dashboard
                    JPanel homeContent = createHomeContent();
                    rightPanel.add(homeContent, BorderLayout.CENTER);
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
            // Create content panel with semi-transparent background for non-home items
            JPanel contentPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Semi-transparent white background for readability
                    g2d.setColor(new Color(255, 255, 255, 220));
                    g2d.fillRoundRect(20, 20, getWidth()-40, getHeight()-40, 15, 15);

                    // Border
                    g2d.setColor(new Color(128, 0, 0, 100));
                    g2d.setStroke(new BasicStroke(2));
                    g2d.drawRoundRect(20, 20, getWidth()-40, getHeight()-40, 15, 15);

                    g2d.dispose();
                    super.paintComponent(g);
                }
            };
            contentPanel.setLayout(new BorderLayout());
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
