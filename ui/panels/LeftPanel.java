package ui.panels;

import main.Main;
import ui.panels.leftpanel.CenterMenuPanel;
import ui.panels.leftpanel.FooterPanel;
import ui.panels.leftpanel.HeaderPanel;
import utils.ComponentFactory;
import ui.components.StyledPanel;

import javax.swing.*;
import java.awt.*;

public class LeftPanel {
    private Main mainFrame;
    private JPanel leftPanel;
    private HeaderPanel headerPanel;
    private CenterMenuPanel centerMenuPanel;
    private FooterPanel footerPanel;

    public LeftPanel(Main mainFrame) {
        this.mainFrame = mainFrame;
        this.headerPanel = new HeaderPanel(mainFrame);
        this.centerMenuPanel = new CenterMenuPanel(mainFrame);
        this.footerPanel = new FooterPanel(mainFrame);
    }

    public JPanel createLeftPanel() {
        leftPanel = StyledPanel.MossGreenPanel.create();

        leftPanel.setLayout(new BorderLayout());
        leftPanel.setOpaque(false); // Make panel transparent so custom painting shows

        // Create the container panel with GridBagLayout
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
        containerPanel.add(headerPanel.createPanel(), gbc);

        // Separator after Header
        gbc.gridy++;
        gbc.weighty = 0.01; // very small height for the separator
        containerPanel.add(ComponentFactory.createHorizontalSeparator(), gbc);

        // Center - 60% weight
        gbc.gridy = 1;
        gbc.weighty = 0.60;
        gbc.insets = new Insets(5, 5, 5, 5);
        containerPanel.add(centerMenuPanel.createPanel(), gbc);

        // Separator after Center
        gbc.gridy++;
        gbc.weighty = 0.01;
        containerPanel.add(ComponentFactory.createHorizontalSeparator(), gbc);

        // Footer - 15% weight
        gbc.gridy = 2;
        gbc.weighty = 0.15;
        containerPanel.add(footerPanel.createPanel(), gbc);

        leftPanel.add(containerPanel, BorderLayout.CENTER);
        return leftPanel;
    }

    // Getter methods for accessing individual components if needed
    public HeaderPanel getHeaderPanel() {
        return headerPanel;
    }

    public CenterMenuPanel getCenterMenuPanel(){
        return centerMenuPanel;
    }

    public FooterPanel getFooterPanel(){
        return footerPanel;
    }
}