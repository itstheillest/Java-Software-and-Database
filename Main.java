import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JLabel backgroundLabel;
    private Timer slideshowTimer;
    private List<String> imagePaths;
    private int currentImageIndex = 0;
    private BufferedImage currentBackgroundImage;

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

    private void createLeftPanel() {
        leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Create semi-transparent background with blur effect simulation
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Create a more solid overlay - increased opacity from 120 to 180
                g2d.setColor(new Color(0, 0, 0, 180)); // Black with 70% opacity (more solid)
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

        // Center - 60% weight
        gbc.gridy = 1;
        gbc.weighty = 0.60;
        gbc.insets = new Insets(5, 5, 5, 5);
        containerPanel.add(centerPanel, gbc);

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

                // Create a darker overlay for the header - increased opacity
                g2d.setColor(new Color(128, 0, 0, 230)); // Deep red with higher opacity (90%)
                g2d.fillRect(0, 0, getWidth(), getHeight());

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
                Image scaledImage = originalLogo.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
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
        titleLabel.setForeground(new Color(255, 255, 255)); // Pure white for better contrast
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
                            g2d.setColor(new Color(200, 200, 195, 220)); // Dark green when pressed
                        } else if (getModel().isRollover()) {
                            g2d.setColor(new Color(220, 220, 215, 180)); // Light green on hover
                        } else {
                            g2d.setColor(new Color(245, 245, 240, 200)); // Medium sea green default
                        }
                    } else {
                        // Original styling for Home and About (indexes 0 and 5)
                        if (getModel().isPressed()) {
                            g2d.setColor(new Color(164, 4, 10, 220));
                        } else if (getModel().isRollover()) {
                            g2d.setColor(new Color(164, 4, 10, 180));
                        } else {
                            g2d.setColor(new Color(255, 193, 7, 200));
                        }
                    }

                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                    // Border color also changes
                    if (buttonIndex >= 1 && buttonIndex <= 4) {
                        g2d.setColor(new Color(180, 180, 175, 150)); // Green border
                    } else {
                        g2d.setColor(new Color(128, 0, 0, 150)); // Red border
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
            button.setMaximumSize(new Dimension(180, 35));
            button.setPreferredSize(new Dimension(180, 35));
            button.setFont(new Font("Arial", Font.BOLD, 12));
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

            // Create content panel with semi-transparent background
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

            JLabel titleLabel = new JLabel(menuItem + " Section");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            titleLabel.setForeground(new Color(128, 0, 0));
            titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 40, 20, 40));

            JTextArea contentArea = new JTextArea("This is the " + menuItem + " section.\n\nYou can add specific functionality here.\n\nThe background image remains visible with a beautiful overlay effect.");
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
            rightPanel.revalidate();
            rightPanel.repaint();
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