// Update 3: Final 
// Run me

package sample;

import sample.Inventory;


import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import javax.swing.border.AbstractBorder;

class RoundedBorder extends AbstractBorder {
    private int radius;

    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2.dispose();
    }
}


public class Main {

    private JFrame frame;
    private JPanel leftPanel;
    private JPanel rightPanel;

    public static void main(String[] args) {
    	
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.showPanel();  // Entry point
        });
    }

    // 1. Set up and show the JFrame
    public void showPanel() {
        frame = new JFrame("Full Screen Split Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen

        createMainPanel(); // Set up split pane and layout

        frame.setVisible(true);
    }

    // 2. Create left and right panels, and add them to a JSplitPane
    public void createMainPanel() {
        // Left Panel (30%)
        leftPanel = new JPanel();
        leftPanel.setBackground(Color.LIGHT_GRAY);

        // Right Panel (70%)
        rightPanel = new JPanel(new BorderLayout());

        // Add titled border with header
        setBorderTitle();

        // Add the button panel to the center of rightPanel
        JPanel buttonPanel = createButtons();
        rightPanel.add(buttonPanel, BorderLayout.CENTER);

        // JSplitPane Setup
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.25); // 30% for left, 70% for right
        splitPane.setDividerSize(0);
        splitPane.setContinuousLayout(true);

        frame.getContentPane().add(splitPane);
    }

    // 3. Create a panel with two buttons in GridLayout
    public JPanel createButtons() {
        JPanel buttonGridPanel = new JPanel(new GridLayout(1, 2, 10, 0)); // 10px horizontal gap
        buttonGridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        JButton leftButton = new JButton("Medincine Inventory System");
        leftButton.setBorder(new RoundedBorder(20));
        leftButton.setContentAreaFilled(true);

        JButton rightButton = new JButton("Appointment Form");
        rightButton.setBorder(new RoundedBorder(20));
        rightButton.setContentAreaFilled(true);

        buttonGridPanel.add(leftButton);
        buttonGridPanel.add(rightButton);
        
        rightButton.addActionListener(e -> {rightButtonAction();});
        leftButton.addActionListener(e -> {leftButtonAction ();});

        return buttonGridPanel;
    }
    
    public void rightButtonAction() {
    		// insert the code to call rightButtonPanel here
    	    rightButtonPanelGUI rightPanelObj = new rightButtonPanelGUI(this);
    	    rightPanel.removeAll();
    	    rightPanel.add(rightPanelObj.getPanel());
    	    rightPanel.revalidate();
    	    rightPanel.repaint();
    }
    
    public void leftButtonAction() {
    	// insert the code to call leftButtonPanel here
        leftButtonFunctions leftPanelObj = new leftButtonFunctions(this);
        rightPanel.removeAll();
        rightPanel.add(leftPanelObj.getMainPanel(), BorderLayout.CENTER);
        rightPanel.revalidate();
        rightPanel.repaint();
    }
    
    public void restoreMainButtons() {
        rightPanel.removeAll();
        setBorderTitle();
        rightPanel.add(createButtons(), BorderLayout.CENTER);
        rightPanel.revalidate();
        rightPanel.repaint();
    }
    
    public void setBorderTitle() {
        // Add titled border with header
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Health Information Hub");
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 16));
        titledBorder.setTitleJustification(TitledBorder.LEFT); // or LEFT/RIGHT
        rightPanel.setBorder(titledBorder);
    }
}
