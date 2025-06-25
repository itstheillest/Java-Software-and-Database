package sample;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;	
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.AbstractBorder;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.TitledBorder;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class RoundedSubmitButton extends JButton {
    private int radius;
    private Color bgColor;
    
    public RoundedSubmitButton(String text, int radius, Color bgColor) {
        super(text);
        this.radius = radius;
        this.bgColor = bgColor;
        setOpaque(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Fill the background
        g2.setColor(bgColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        
        // Add hover effect - black border when mouse is over
        if (getModel().isPressed()) {
            // Darker background when pressed
            g2.setColor(bgColor.darker());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        } else if (getModel().isRollover()) {
            // Slightly lighter background when hovering
            g2.setColor(bgColor.brighter());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            
            // Black border when hovering
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, radius, radius);
        } else {
            // Normal state
            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        }
        
        g2.dispose();
        super.paintComponent(g);
    }
}

public class rightButtonPanelGUI {
    private JPanel rightPanel;
    private JButton returnButton;
    private JButton editButton;
    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField middleNameField;
    private JPanel fieldsPanel;
    private JComboBox<String> appointmentDropdown;
    private JLabel dayLabel;
    private JComboBox<String> dayDropdown;
    private JPanel daySelectionPanel;
    private JTextArea noteTextArea;
    private JButton submitButton;
    private JTable appointmentTable;
    private DefaultTableModel tableModel;
    private JScrollPane tableScrollPane;
    private JButton deleteButton;
    private JButton undoButton;
    private JButton saveButton;
    private JPanel buttonPanel;
    private JPanel searchPanel;
    private JButton searchButton;
    private JTextField searchField;
    private JComboBox<String> sortingComboBox;
    private Main mainApp;
    private rightButtonPanelFunctions functions;

    public rightButtonPanelGUI(Main mainApp) {
        this.mainApp = mainApp;
        this.functions = new rightButtonPanelFunctions(this);
        
        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(500, 800));

        rightPanel.add(setUpHeader(), BorderLayout.NORTH);
        rightPanel.add(setUpBody(), BorderLayout.CENTER);
    }

    private JPanel setUpHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(85, 107, 47));

        JPanel leftHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftHeaderPanel.setOpaque(false);
        returnButton = new JButton("Return");
        returnButton.addActionListener(e -> mainApp.restoreMainButtons()); 
        leftHeaderPanel.add(returnButton);

        searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        searchPanel.setOpaque(false); 
        searchButton = new JButton("Search");
        searchField = new JTextField(15); 
        searchField.setText("Click Search"); 
        searchField.setForeground(Color.GRAY); 
        searchField.setEditable(false);
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                if (functions.isSearchMode()) functions.filterTable(); 
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { 
                if (functions.isSearchMode()) functions.filterTable(); 
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { 
                if (functions.isSearchMode()) functions.filterTable();
            }
        });
        sortingComboBox = new JComboBox<>(new String[]{"Name (A-Z)", "Name (Z-A)", "ID (Low-High)", "ID (High-Low)"});
        sortingComboBox.addActionListener(e -> {
            String currentSortType = (String) sortingComboBox.getSelectedItem();
            functions.refreshTableWithSorting(currentSortType);
        });
        searchButton.addActionListener(e -> functions.toggleSearchMode()); 

        searchPanel.add(searchButton);
        searchPanel.add(searchField); 
        searchPanel.add(sortingComboBox);

        JPanel searchPanelWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        searchPanelWrapper.setOpaque(false);
        searchPanelWrapper.setBorder(new EmptyBorder(5, 0, 5, 0));
        searchPanelWrapper.add(searchPanel);

        headerPanel.add(leftHeaderPanel, BorderLayout.WEST);
        headerPanel.add(searchPanelWrapper, BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel setUpBody() {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        bodyPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        initializeTable();
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setPreferredSize(new Dimension(1000, 600));

        JPanel appointmentFormPanel = new JPanel();
        appointmentFormPanel.setLayout(new BorderLayout());
        appointmentFormPanel.setBackground(new Color(245, 245, 245));
        appointmentFormPanel.setOpaque(true);
        appointmentFormPanel.setPreferredSize(new Dimension(800, 600));
        appointmentFormPanel.setMaximumSize(new Dimension(800, 600));

        Border roundedBorder = BorderFactory.createCompoundBorder(
            new LineBorder(Color.GRAY, 8, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );
        appointmentFormPanel.setBorder(roundedBorder);

        JLabel titleLabel = new JLabel("Appointment Form", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel);
        appointmentFormPanel.add(titlePanel, BorderLayout.NORTH);
        
        fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 5, 0);

        JLabel nameLabel = new JLabel("Personal Information:");
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 0;
        fieldsPanel.add(nameLabel, gbc);

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        namePanel.setOpaque(false);
        namePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        namePanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField(12);
        lastNameField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        namePanel.add(lastNameField);
        
        namePanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField(12);
        firstNameField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        namePanel.add(firstNameField);
        
        namePanel.add(new JLabel("Middle Name:"));
        middleNameField = new JTextField(12);
        middleNameField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        namePanel.add(middleNameField);
        
        gbc.gridy = 1;
        fieldsPanel.add(namePanel, gbc);

        JLabel typeLabel = new JLabel("Appointment Type:");
        typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        gbc.gridy = 2;
        fieldsPanel.add(typeLabel, gbc);

        JPanel appointmentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        appointmentPanel.setOpaque(false);
        appointmentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        appointmentDropdown = new JComboBox<>(new String[]{
            "   -- Select Appointment --",
            "Medical Check-Up (OPD - Clinic Set Up)", 
            "Medical Check-Up (Doctor)",
            "Pregnant Check-Up - Every Wednesday",
            " Baby Vaccination - Every Thursday"
        });
        appointmentDropdown.setPreferredSize(new Dimension(300, 35));
        appointmentDropdown.addActionListener(new AppointmentTypeListener());
        appointmentPanel.add(appointmentDropdown);
        
        gbc.gridy = 3;
        fieldsPanel.add(appointmentPanel, gbc);
        
        dayLabel = new JLabel("Choose Day:");
        dayLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        dayDropdown = new JComboBox<>();
        dayDropdown.setPreferredSize(new Dimension(200, 35));
        dayDropdown.setMaximumSize(new Dimension(200, 35));
        dayDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        daySelectionPanel = new JPanel();
        daySelectionPanel.setLayout(new BoxLayout(daySelectionPanel, BoxLayout.Y_AXIS));
        daySelectionPanel.setOpaque(false);
        daySelectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        daySelectionPanel.add(dayLabel);
        daySelectionPanel.add(dayDropdown);

        JLabel noteLabel = new JLabel("Note (Optional):");
        noteLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        gbc.gridy = 5;
        fieldsPanel.add(noteLabel, gbc);

        noteTextArea = new JTextArea("Please describe your conditions here...", 5, 20);
        noteTextArea.setLineWrap(true);
        noteTextArea.setWrapStyleWord(true);
        noteTextArea.setForeground(new Color(150, 150, 150, 128));
        noteTextArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        noteTextArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (noteTextArea.getText().equals("Please describe your conditions here...")) {
                    noteTextArea.setText("");
                    noteTextArea.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (noteTextArea.getText().isEmpty()) {
                    noteTextArea.setText("Please describe your conditions here...");
                    noteTextArea.setForeground(new Color(150, 150, 150, 128));
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(noteTextArea);
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        
        gbc.gridy = 6;
        gbc.weighty = 1.0;
        fieldsPanel.add(scrollPane, gbc);

        appointmentFormPanel.add(fieldsPanel, BorderLayout.CENTER);
        
        submitButton = new RoundedSubmitButton("Submit", 20, new Color(34, 139, 34));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setPreferredSize(new Dimension(750, 40));
        submitButton.setMaximumSize(new Dimension(750, 40));
        submitButton.addActionListener(e -> functions.submitAppointment());

        JPanel submitPanel = new JPanel();
        submitPanel.setOpaque(false);
        submitPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        submitPanel.add(submitButton);

        appointmentFormPanel.add(submitPanel, BorderLayout.SOUTH);
        
        mainContentPanel.add(appointmentFormPanel, BorderLayout.CENTER);
        mainContentPanel.add(tableScrollPane, BorderLayout.EAST);
        bodyPanel.add(mainContentPanel, BorderLayout.CENTER);

        return bodyPanel;
    }

    private class AppointmentTypeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedType = (String) appointmentDropdown.getSelectedItem();
            if (daySelectionPanel.getParent() != null) {
                fieldsPanel.remove(daySelectionPanel);
            }
            
            if ("Medical Check-Up (OPD - Clinic Set Up)".equals(selectedType) || "Medical Check-Up (Doctor)".equals(selectedType)) {
                dayLabel.setText("Medical Check-Up (OPD - Clinic Set Up)".equals(selectedType) ? "Choose Day for OPD Check-up (9:00 AM - 3:00 PM):" : "Choose Day for Doctor Check-up:");
                dayDropdown.removeAllItems();
                dayDropdown.addItem(" -- Select Day -- ");
                if ("Medical Check-Up (OPD - Clinic Set Up)".equals(selectedType)){
                    dayDropdown.addItem("Monday");
                    dayDropdown.addItem("Tuesday");
                } else {
                    dayDropdown.addItem("Wednesday");
                    dayDropdown.addItem("Thursday");
                }
                
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.anchor = GridBagConstraints.NORTHWEST;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1.0;
                gbc.insets = new Insets(5, 0, 5, 0);
                gbc.gridy = 4;
                fieldsPanel.add(daySelectionPanel, gbc);
            }
            
            fieldsPanel.revalidate();
            fieldsPanel.repaint();
        }
    }
    
    private void initializeTable() {
        String[] columnNames = {"Last Name", "ID"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        appointmentTable = new JTable(tableModel);
        appointmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        appointmentTable.getTableHeader().setReorderingAllowed(false);
        appointmentTable.setFont(new Font("Arial", Font.PLAIN, 14));
        appointmentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        appointmentTable.setRowHeight(25);
        
        appointmentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int clickedRow = appointmentTable.rowAtPoint(e.getPoint());
                
                // If clicked outside table rows, do nothing
                if (clickedRow == -1) {
                    return;
                }
                
                // --- FIX: Get the ID from the table model, not the visual row index ---
                int appointmentId = (int) tableModel.getValueAt(clickedRow, 1); // Column 1 is the ID
                
                // Use SwingUtilities.invokeLater to ensure the selection is processed
                SwingUtilities.invokeLater(() -> {
                    // This now passes the actual ID, which won't change with sorting
                    functions.populateFormById(appointmentId);
                });
            }
        });
        
        tableScrollPane = new JScrollPane(appointmentTable);
        tableScrollPane.setPreferredSize(new Dimension(350, 500));
        
        Border thickBorder = BorderFactory.createCompoundBorder(
            new LineBorder(Color.GRAY, 8, true),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
        tableScrollPane.setBorder(thickBorder);
        tableScrollPane.setVisible(true);
    }
    
    public JPanel getPanel() { return rightPanel; }
    public JTextField getLastNameField() { return lastNameField; }
    public JTextField getFirstNameField() { return firstNameField; }
    public JTextField getMiddleNameField() { return middleNameField; }
    public JComboBox<String> getAppointmentDropdown() { return appointmentDropdown; }
    public JComboBox<String> getDayDropdown() { return dayDropdown; }
    public JPanel getDaySelectionPanel(){ return daySelectionPanel; }
    public JTextArea getNoteTextArea() { return noteTextArea; }
    public JButton getSubmitButton() { return submitButton; }
    public JPanel getFieldsPanel(){ return fieldsPanel; }
    public JTable getAppointmentTable() { return appointmentTable; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JScrollPane getTableScrollPane() { return tableScrollPane; }
    public JPanel getButtonPanel() { return buttonPanel; }
    public void setButtonPanel(JPanel buttonPanel){ this.buttonPanel = buttonPanel; }
    public JPanel getRightPanel(){ return rightPanel; }
    public JButton getSearchButton() { return searchButton; }
    public JTextField getSearchField() { return searchField; }
    public JButton getEditButton() { return editButton; }
    public void setEditButton(JButton editButton) { this.editButton = editButton; }
}