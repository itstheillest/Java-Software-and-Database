package main;

import ui.panels.LeftPanel;
import ui.panels.RightPanel;
import ui.components.StyledPanel;
import ui.components.transparentStyledPanel;
import handlers.SlideshowHandler;
import handlers.MenuActionHandler;
import utils.ComponentFactory;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JLabel backgroundLabel;
    private RightPanel rightPanelManager;
    private SlideshowHandler slideshowHandler;
    private MenuActionHandler menuActionHandler;

    public Main() {
        rightPanelManager = new RightPanel(this);

        // Configure main application window
        setTitle(ApplicationConstants.WINDOW_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize to full screen
        setUndecorated(false); // Optional: Set to true if you want to remove the title bar (true = borderless)
        setLocationRelativeTo(null); // Center window on screen

        // Build UI components and layout
        initializeComponents();

        // Initialize menu action handler after components are created
        menuActionHandler = new MenuActionHandler(rightPanelManager, rightPanel, slideshowHandler, this);

        // Start background slideshow functionality
        slideshowHandler.initializeSlideshow();

        // Display the application
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
                if (slideshowHandler != null && slideshowHandler.getCurrentBackgroundImage() != null) {
                    // Draw the background image to fill the entire component
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2d.drawImage(slideshowHandler.getCurrentBackgroundImage(), 0, 0, getWidth(), getHeight(), this);
                    g2d.dispose();
                }
            }
        };
        backgroundLabel.setOpaque(true);
        backgroundLabel.setBackground(new Color(255, 255, 240)); // Fallback color

        // Initialize slideshow handler after backgroundLabel is created
        slideshowHandler = new SlideshowHandler(backgroundLabel);

        // Create the split pane with 30/70 ratio for foreground content
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.25);
        splitPane.setDividerSize(0);
        splitPane.setContinuousLayout(true);
        splitPane.setOpaque(false); // Make split pane transparent

        // Create left panel (30%) with semi-transparent background
        LeftPanel leftPanelManager = new LeftPanel(this);
        leftPanel = leftPanelManager.createLeftPanel(); // Get the actual JPanel from LeftPanel class

        // Create right panel (70%) - now transparent to show background
        RightPanel rightPanelManager = new RightPanel(this);
        rightPanel = rightPanelManager.createRightPanel(); // Get the actual JPanel from RightPanel class

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
            int dividerLocation = (int) (windowWidth * 0.25);
            splitPane.setDividerLocation(dividerLocation);
        });
    }

    public JPanel createStyledPanel() {
        // Delegate to StyledPanel component
        return StyledPanel.create();
    }

    public static JPanel createTransStyledPanel()
    {
        return transparentStyledPanel.create();
    }
    

    public void handleMenuSelection(String menuItem) {
        // Delegate to MenuActionHandler
        menuActionHandler.handleMenuSelection(menuItem);
    }
}
