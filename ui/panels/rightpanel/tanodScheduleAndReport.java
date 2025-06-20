package ui.panels.rightpanel;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import main.Main;
import utils.ComponentFactory;
import utils.ComponentFactory.RoundedButton;


    public class tanodScheduleAndReport {
    private JFrame mainFrame;
    private JPanel rightPanelTanod;

    public tanodScheduleAndReport(JFrame mainFrame)
    {
        this.mainFrame = mainFrame;
        setUpFrame();
    }

    private void setUpFrame()
    {
        rightPanelTanod = new JPanel(new BorderLayout());
        rightPanelTanod.setOpaque(false);

        //add the buttons
        JPanel buttons = createButtonsTanod();
        rightPanelTanod.add(buttons, BorderLayout.CENTER);
        
    }

    //Header where you can see the date today and what this program is for
    public JPanel createHeader() 
    {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        String title = "Barangay Tanod Schedule and Report";
        JPanel titlePanel = ComponentFactory.getHeaderPanel(title);

        header.add(titlePanel, BorderLayout.NORTH);
        return header;
    }

    public JPanel createButtonsTanod()
    {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.setBackground(new Color(255, 255, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); // spacing between buttons
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Set your colors
        Color normalBg = new Color(0, 123, 255);       
        Color hoverBg = new Color(0, 90, 200);         
        Color normalFg = Color.WHITE;
        Color hoverFg = Color.WHITE;
        Color borderColor = Color.WHITE;


        // Manage Schedule Button
        RoundedButton manageButton = new RoundedButton("Manage Schedule ", 50, normalBg, borderColor);
        manageButton.setPreferredSize(new Dimension(300, 60));
        manageButton.setForeground(normalFg);
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(manageButton, gbc);

        // Center Button
        RoundedButton viewButton = new RoundedButton("View Schedule ", 50, normalBg, borderColor);
        viewButton.setPreferredSize(new Dimension(300, 60));
        viewButton.setForeground(normalFg);
        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonPanel.add(viewButton, gbc);

        // Right Button
        RoundedButton reportButton = new RoundedButton("Report ", 50, normalBg, borderColor);
        reportButton.setPreferredSize(new Dimension(300, 60));
        reportButton.setForeground(normalFg);
        gbc.gridx = 0;
        gbc.gridy = 2;
        buttonPanel.add(reportButton, gbc);

        manageButton.addActionListener(e -> {manageButtonAction();});
        viewButton.addActionListener(e -> {viewButtonAction();});
        reportButton.addActionListener(e -> {reportButtonAction();});
    

        return buttonPanel;

    }

    private void manageButtonAction()
    {
        JPanel managePanel = manageSchedule();
        
        rightPanelTanod.removeAll();
        rightPanelTanod.add(managePanel, BorderLayout.CENTER);

        rightPanelTanod.revalidate();
        rightPanelTanod.repaint();
    }

    private void viewButtonAction()
    {   
        //shows schedule
        JPanel schedulePanel = viewSchedule();

        rightPanelTanod.removeAll(); // Remove existing components which is the front page
        rightPanelTanod.add(schedulePanel, BorderLayout.CENTER); // Add schedule panel
        
        rightPanelTanod.revalidate();
        rightPanelTanod.repaint();
    }

    private void reportButtonAction()
    {
        JPanel reportPanel = reportingPanel();

        rightPanelTanod.removeAll(); // Remove existing components which is the front page
        rightPanelTanod.add(reportPanel, BorderLayout.CENTER); // Add schedule panel
        
        rightPanelTanod.revalidate();
        rightPanelTanod.repaint();
    }
    
    public JPanel manageSchedule()
    {
        JPanel manageSchedPanel = Main.createStyledPanel();
        manageSchedPanel.setLayout(new BorderLayout());
        manageSchedPanel.setOpaque(false);

        JPanel header = createHeader();
        manageSchedPanel.add(header, BorderLayout.NORTH);

        JPanel manageTablePanel = ComponentFactory.createManageContentPanel();
        manageSchedPanel.add(manageTablePanel, BorderLayout.CENTER);

        return manageSchedPanel;
    }


    public JPanel viewSchedule() 
    {
        JPanel schedulePanel = Main.createStyledPanel();
        schedulePanel.setLayout(new BorderLayout());
        schedulePanel.setOpaque(false);

        JPanel header = createHeader();
        schedulePanel.add(header, BorderLayout.NORTH);

        // Calendar
        LocalDate today = LocalDate.now();

        //get tanodTeams that would be used to display Team names in the calendar
        ArrayList<ComponentFactory.Team> tanodTeams = ComponentFactory.getTanodTeams();

        JPanel calendarPanel = ComponentFactory.createCalendarPanel(today, tanodTeams);
        calendarPanel.setPreferredSize(new Dimension(700, 500));
        schedulePanel.add(calendarPanel, BorderLayout.CENTER);

        return schedulePanel;
    }

    public JPanel reportingPanel()
    {
        JPanel reportPanel = Main.createStyledPanel();
        reportPanel.setLayout(new BorderLayout());
        reportPanel.setOpaque(false);

        JPanel header = createHeader();
        reportPanel.add(header, BorderLayout.NORTH);

        JPanel incidentReportPanel = ComponentFactory.createIncidentReportPanel();
        reportPanel.add(incidentReportPanel, BorderLayout.CENTER);

        return reportPanel;
    }

    //needs update, don't know how to implement
    public void restoreMainButtons() 
    {
        rightPanelTanod.removeAll();
        setUpFrame();
    }

    public JPanel getRightPanelTanod()
    {
        return rightPanelTanod;
    }

    
}
