package sample;

import sample.appointmentInventory;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
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
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import javax.swing.JFileChooser;

// Data class to hold appointment information
class AppointmentData {
    private static int nextId = 1;
    private String ID;
    private String lastName;
    private String firstName;
    private String middleName;
    private String appointmentType;
    private String selectedDay;
    private String note;
    
    // Add this constructor to your AppointmentData class
    public AppointmentData(String lastName, String firstName, String middleName, 
                          String appointmentType, String selectedDay, String note, String ID) {
        
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.appointmentType = appointmentType;
        this.selectedDay = selectedDay;
        this.note = note;
        this.ID = ID;
    }
    
    // Getters
    public String getId() { return ID; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getAppointmentType() { return appointmentType; }
    public String getSelectedDay() { return selectedDay; }
    public String getNote() { return note; }
}

class RoundedButtonBorder extends AbstractBorder {
    private int radius;
    private Color backgroundColor;

    public RoundedButtonBorder(int radius, Color backgroundColor) {
        this.radius = radius;
        this.backgroundColor = backgroundColor;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Fill the rounded background first
        g2.setColor(backgroundColor);
        g2.fillRoundRect(x, y, width - 1, height - 1, radius, radius);
        
        // Then draw the border (optional - you can remove this if you don't want a border)
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(1));
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(4, 8, 4, 8);
    }
}

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

public class rightButtonPanel {
    private JPanel rightPanel;
    private JButton returnButton;
    private JButton editButton;
    private JButton printButton;
    
    // Name fields
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
    
    // Data storage (will be replaced with database integration)
    private List<AppointmentData> appointmentList;
    
 // Add these instance variables to your rightButtonPanel class
    private JTable appointmentTable;
    private DefaultTableModel tableModel;
    private JScrollPane tableScrollPane;
    private boolean isTableVisible = false;
    
    private boolean isEditMode = false;
    private int selectedRowIndex = -1;
    private AppointmentData originalAppointmentData; // Store original values for undo
    private JButton deleteButton;
    private JButton undoButton;
    private JButton saveButton;
    private JPanel buttonPanel; // Container for buttons
    
 // Add these with your other instance variables
    private JPanel searchPanel;
    private JButton searchButton;
    private JTextField searchField;
    private JComboBox<String> sortingComboBox;
    private boolean isSearchMode = false;

    private Main mainApp;
    
    private static String lastUsedDirectory = null;  // Add this line

    public rightButtonPanel(Main mainApp) {
        this.mainApp = mainApp;
        this.appointmentList = new ArrayList<>();
        
        // Add this to your constructor after creating rightPanel:
        // Load data immediately when panel is created
        SwingUtilities.invokeLater(() -> {
        	refreshTableData();
        });

        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(500, 800)); // Adjust size as needed

        rightPanel.add(setUpHeader(), BorderLayout.NORTH);
        rightPanel.add(setUpBody(), BorderLayout.CENTER);
    }

 // Replace your setUpHeader method with this:
    private JPanel setUpHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout()); 
        headerPanel.setBackground(new Color(85, 107, 47)); // Olive green 

        // Left side - Return button only
        JPanel leftHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
        leftHeaderPanel.setOpaque(false);
        returnButton = new JButton("Return");
        returnButton.addActionListener(e -> mainApp.restoreMainButtons()); 
        leftHeaderPanel.add(returnButton);

        // Center - Search functionality
        searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0)); 
        searchPanel.setOpaque(false); 
        searchButton = new JButton("Search");
        searchField = new JTextField(15); 
        searchField.setText("Click Search"); 
        searchField.setForeground(Color.GRAY); 
        searchField.setEditable(false); 
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                if (isSearchMode) filterTable(); 
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { 
                if (isSearchMode) filterTable(); 
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { 
                if (isSearchMode) filterTable();
            }
        });
        
        sortingComboBox = new JComboBox<>(new String[]{"Name (A-Z)", "Name (Z-A)", "Date (Oldest-Newest)", "Date (Newest-Oldest)"});
        sortingComboBox.addActionListener(e -> {
            String currentSortType = (String) sortingComboBox.getSelectedItem();
            refreshTableWithSorting(currentSortType);
        });
        searchButton.addActionListener(e -> toggleSearchMode()); 

        searchPanel.add(searchButton);
        searchPanel.add(searchField); 
        searchPanel.add(sortingComboBox);

        // Create a wrapper panel for searchPanel
        // Change FlowLayout.CENTER to FlowLayout.CENTER for vertical alignment
        JPanel searchPanelWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)); // No horizontal gap, no vertical gap 
        searchPanelWrapper.setOpaque(false);
        
        // You can adjust the top and bottom padding here to fine-tune vertical centering
        searchPanelWrapper.setBorder(new EmptyBorder(5, 0, 5, 0)); // Add a small vertical padding (top, left, bottom, right)
        searchPanelWrapper.add(searchPanel);
        
        JPanel rightHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rightHeaderPanel.setOpaque(false);
        printButton = new JButton("Print");
        printButton.setEnabled(false);
        
        printButton.addActionListener(e -> printMode()); 
        
        rightHeaderPanel.add(printButton);

        headerPanel.add(leftHeaderPanel, BorderLayout.WEST); 
        headerPanel.add(searchPanelWrapper, BorderLayout.CENTER);
        headerPanel.add(rightHeaderPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel setUpBody() {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        bodyPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        // Initialize table first
        initializeTable();
        
        // Create main content panel (form + table)
        JPanel mainContentPanel = new JPanel(new BorderLayout());
     // In setUpBody(), change this line:
        mainContentPanel.setPreferredSize(new Dimension(1000, 600)); // Increased width for bigger table

        // Form Panel with rounded border
        JPanel appointmentFormPanel = new JPanel();
        appointmentFormPanel.setLayout(new BorderLayout());
        appointmentFormPanel.setBackground(new Color(245, 245, 245)); // Light gray
        appointmentFormPanel.setOpaque(true);
        appointmentFormPanel.setPreferredSize(new Dimension(800, 600));
        appointmentFormPanel.setMaximumSize(new Dimension(800, 600));

        Border roundedBorder = BorderFactory.createCompoundBorder(
            new LineBorder(Color.GRAY, 8, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );
        appointmentFormPanel.setBorder(roundedBorder);

        // === Title Label ===
        JLabel titleLabel = new JLabel("Appointment Form", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel);
        appointmentFormPanel.add(titlePanel, BorderLayout.NORTH);

        // === Fields Panel ===
        fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 5, 0); // Top, left, bottom, right padding

        // === Name Fields Section ===
        JLabel nameLabel = new JLabel("Personal Information:");
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 0;
        fieldsPanel.add(nameLabel, gbc);

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        namePanel.setOpaque(false);
        namePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Last Name
        namePanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField(12);
        lastNameField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        namePanel.add(lastNameField);

        // First Name
        namePanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField(12);
        firstNameField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        namePanel.add(firstNameField);

        // Middle Name
        namePanel.add(new JLabel("Middle Name:"));
        middleNameField = new JTextField(12);
        middleNameField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        namePanel.add(middleNameField);

        gbc.gridy = 1;
        fieldsPanel.add(namePanel, gbc);

        // === Appointment Type Section ===
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
            "Baby Vaccination - Every Thursday"
        });
        appointmentDropdown.setPreferredSize(new Dimension(300, 35));
        appointmentDropdown.addActionListener(new AppointmentTypeListener());
        appointmentPanel.add(appointmentDropdown);
        
        gbc.gridy = 3;
        fieldsPanel.add(appointmentPanel, gbc);

        // Create day selection components but don't add them yet
        dayLabel = new JLabel("Choose Day:");
        dayLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        dayDropdown = new JComboBox<>();
        dayDropdown.setPreferredSize(new Dimension(200, 35));
        dayDropdown.setMaximumSize(new Dimension(200, 35));
        dayDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Create the panel but don't add it to fieldsPanel yet
        daySelectionPanel = new JPanel();
        daySelectionPanel.setLayout(new BoxLayout(daySelectionPanel, BoxLayout.Y_AXIS));
        daySelectionPanel.setOpaque(false);
        daySelectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        daySelectionPanel.add(dayLabel);
        daySelectionPanel.add(dayDropdown);

        // Note Label
        JLabel noteLabel = new JLabel("Note (Optional):");
        noteLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        gbc.gridy = 5;
        fieldsPanel.add(noteLabel, gbc);

        // Note Area
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
        gbc.weighty = 1.0; // This pushes everything up
        fieldsPanel.add(scrollPane, gbc);

        appointmentFormPanel.add(fieldsPanel, BorderLayout.CENTER);

        // === Submit Button at Bottom ===
        submitButton = new RoundedSubmitButton("Submit", 20, new Color(34, 139, 34));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setPreferredSize(new Dimension(750, 40));
        submitButton.setMaximumSize(new Dimension(750, 40));
        submitButton.addActionListener(e -> submitAppointment());

        JPanel submitPanel = new JPanel();
        submitPanel.setOpaque(false);
        submitPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        submitPanel.add(submitButton);

        appointmentFormPanel.add(submitPanel, BorderLayout.SOUTH);
        
        // Add form to center of main content
        mainContentPanel.add(appointmentFormPanel, BorderLayout.CENTER);
        
        // Add table to east of main content
        mainContentPanel.add(tableScrollPane, BorderLayout.EAST);
        
        // Add main content to body
        bodyPanel.add(mainContentPanel, BorderLayout.CENTER);

        return bodyPanel;
    }

    // ActionListener for appointment type dropdown
    private class AppointmentTypeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedType = (String) appointmentDropdown.getSelectedItem();
            
            // Remove day selection panel if it exists
            if (daySelectionPanel.getParent() != null) {
                fieldsPanel.remove(daySelectionPanel);
            }
            
            if ("Medical Check-Up (OPD - Clinic Set Up)".equals(selectedType)) {
                dayLabel.setText("Choose Day for OPD Check-up (9:00 AM - 3:00 PM):");
                dayDropdown.removeAllItems();
                dayDropdown.addItem(" -- Select Day -- ");
                dayDropdown.addItem("Monday");
                dayDropdown.addItem("Tuesday");
                
                // Add back with GridBagConstraints
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.anchor = GridBagConstraints.NORTHWEST;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1.0;
                gbc.insets = new Insets(5, 0, 5, 0);
                gbc.gridy = 4;
                fieldsPanel.add(daySelectionPanel, gbc);
                
            } else if ("Medical Check-Up (Doctor)".equals(selectedType)) {
                dayLabel.setText("Choose Day for Doctor Check-up:");
                dayDropdown.removeAllItems();
                dayDropdown.addItem(" -- Select Day -- ");
                dayDropdown.addItem("Wednesday");
                dayDropdown.addItem("Thursday");
                
                // Add back with GridBagConstraints
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.anchor = GridBagConstraints.NORTHWEST;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1.0;
                gbc.insets = new Insets(5, 0, 5, 0);
                gbc.gridy = 4;
                fieldsPanel.add(daySelectionPanel, gbc);
            } else if ("Pregnant Check-Up - Every Wednesday".equals(selectedType)) {
                // Don't show day selection panel for fixed day appointments
                // Day is already specified in the appointment type
                
            } else if ("Baby Vaccination - Every Thursday".equals(selectedType)) {
                // Don't show day selection panel for fixed day appointments  
                // Day is already specified in the appointment type
            }
            
            fieldsPanel.revalidate();
            fieldsPanel.repaint();
        }
    }

    private void submitAppointment() {
        // Validate name fields
        String lastName = lastNameField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String middleName = middleNameField.getText().trim();
        
        if (lastName.isEmpty() || firstName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter both Last Name and First Name.", 
                                        "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validate appointment type
        String selectedType = (String) appointmentDropdown.getSelectedItem();
        if (selectedType == null || selectedType.isEmpty() || selectedType.equals("   -- Select Appointment --")) {
            JOptionPane.showMessageDialog(null, "Please select an appointment type.", 
                                        "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validate day selection if needed
     // Validate day selection if needed
     // Validate day selection if needed
        String selectedDay = "";
        if (daySelectionPanel.getParent() != null && daySelectionPanel.isVisible()) {
            // Only validate if day panel is actually visible
            selectedDay = (String) dayDropdown.getSelectedItem();
            if (selectedDay == null || selectedDay.equals(" -- Select Day -- ")) {
                JOptionPane.showMessageDialog(null, "Please select a day for your appointment.", 
                                            "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } else {
            // For fixed day appointments
            if (selectedType.contains("Wednesday")) {
                selectedDay = "Wednesday";
            } else if (selectedType.contains("Thursday")) {
                selectedDay = "Thursday";
            }
        }

        // Get note
        String note = noteTextArea.getText().trim();
        if (note.equals("Please describe your conditions here...")) {
            note = "";
        }
        
        // Get the appointment to update
        // Generate new ID for new appointment
        // IMPORTANT 1: Instead of size+1, it's the date
        // appointmentID is also the date recorded, not the scheduled date
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("MM-dd-yyyy-HH:mm:ss");
        String appointmentId = dateFormat.format(new java.util.Date());

        // Create appointment data object
        AppointmentData newAppointment = new AppointmentData(lastName, firstName, middleName, 
                                                           selectedType, selectedDay, note, appointmentId);
        
        // Add to list (this will be replaced with database insertion)
        appointmentList.add(newAppointment);

        // Show success message
        StringBuilder message = new StringBuilder();
        message.append("Name: ").append(firstName).append(" ");
        if (!middleName.isEmpty()) {
            message.append(middleName).append(" ");
        }
        message.append(lastName).append("\n");
        message.append("Appointment Type: ").append(selectedType);
        if (!selectedDay.isEmpty()) {
            message.append("\nDay: ").append(selectedDay);
        }
        message.append("\nNote: ").append(note.isEmpty() ? "None" : note);
        message.append("\nID: ").append(newAppointment.getId());

        JOptionPane.showMessageDialog(null, message.toString(),
                "Appointment Submitted", JOptionPane.INFORMATION_MESSAGE);
        
        // Sends to database
        appointmentInventory.connect();
        appointmentInventory.insertAppointmentInfoToDatabase(newAppointment);
        
        // Clear form
        clearForm();
        
        // ADD THIS LINE - Refresh table after submission
        refreshTableData();
    }

    private void clearForm() {
        lastNameField.setText("");
        firstNameField.setText("");
        middleNameField.setText("");
        appointmentDropdown.setSelectedIndex(0);
        // Remove day selection panel properly
        if (daySelectionPanel.getParent() != null) {
            fieldsPanel.remove(daySelectionPanel);
        }
        fieldsPanel.revalidate();
        fieldsPanel.repaint();
        noteTextArea.setText("Please describe your conditions here...");
        noteTextArea.setForeground(new Color(150, 150, 150, 128));
    }
    
    // Add this method to your rightButtonPanel class
    // Replace your initializeTable method with this:
    private void initializeTable() {
        // Create table model
        String[] columnNames = {"Date Recorded","Last Name"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        // Create table
        appointmentTable = new JTable(tableModel);
        appointmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        appointmentTable.getTableHeader().setReorderingAllowed(false);
        
        // Make font bigger
        appointmentTable.setFont(new Font("Arial", Font.PLAIN, 14));
        appointmentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        appointmentTable.setRowHeight(25); // Make rows taller
        
        // Add selection listener
        // Add mouse listener to handle all row clicks
        // In rightButtonPanelGUI.java, inside the initializeTable() method

        appointmentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int clickedRow = appointmentTable.getSelectedRow();
                
                if (clickedRow == -1) {
                    return;
                }
                
                // Use the new method that handles duplicate IDs
                AppointmentData selectedAppt = findAppointmentByTableRow(clickedRow);
                
                if (selectedAppt != null) {
                    // Find the correct index in the master list
                    int originalListIndex = appointmentList.indexOf(selectedAppt);
                    populateFormDirectly(selectedAppt, originalListIndex);
                } else {
                    System.out.println("ERROR: Could not find appointment for clicked row: " + clickedRow);
                }
            }
        });
        
        // Create scroll pane - make it bigger
        tableScrollPane = new JScrollPane(appointmentTable);
        tableScrollPane.setPreferredSize(new Dimension(350, 500)); // Bigger size
        
        // Add thick border like the form
        Border thickBorder = BorderFactory.createCompoundBorder(
            new LineBorder(Color.GRAY, 8, true),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
        tableScrollPane.setBorder(thickBorder);
        
        // Make table visible by default
        tableScrollPane.setVisible(true);
    }
    
    private void populateFormFromTable(int selectedRow) {
        if (selectedRow < 0 || selectedRow >= appointmentList.size()) {
            System.out.println("Invalid row selected: " + selectedRow);
            return;
        }
        
        // Check if same row is selected again - exit edit mode
        if (isEditMode && selectedRowIndex == selectedRow) {
            exitEditMode();
            appointmentTable.clearSelection();
            return;
        }
        
        // Enter edit mode
        enterEditMode(selectedRow);
        
        // Get the appointment directly by row index
        AppointmentData selectedAppt = appointmentList.get(selectedRow);
        
        // Store original data for undo functionality
        originalAppointmentData = new AppointmentData(
            selectedAppt.getLastName(),
            selectedAppt.getFirstName(), 
            selectedAppt.getMiddleName(),
            selectedAppt.getAppointmentType(),
            selectedAppt.getSelectedDay(),
            selectedAppt.getNote(),
            selectedAppt.getId()
        );
        
        // Populate form fields
        lastNameField.setText(selectedAppt.getLastName());
        firstNameField.setText(selectedAppt.getFirstName());
        middleNameField.setText(selectedAppt.getMiddleName());
        
        // Set appointment type and trigger the day dropdown setup
        appointmentDropdown.setSelectedItem(selectedAppt.getAppointmentType());
        
        // Set day if applicable - use a timer to ensure dropdown is populated first
        String selectedDay = selectedAppt.getSelectedDay();
        if (selectedDay != null && !selectedDay.isEmpty()) {
            Timer timer = new Timer(200, e -> { // Increased delay
                if (dayDropdown.getItemCount() > 1) { // Check if items are loaded
                    dayDropdown.setSelectedItem(selectedDay);
                }
                ((Timer)e.getSource()).stop();
            });
            timer.start();
        }
        
        // Set note
        String note = selectedAppt.getNote();
        if (note != null && !note.isEmpty()) {
            noteTextArea.setText(note);
            noteTextArea.setForeground(Color.BLACK);
        } else {
            noteTextArea.setText("Please describe your conditions here...");
            noteTextArea.setForeground(new Color(150, 150, 150, 128));
        }
    }
    
    private void enterEditMode(int rowIndex) {
        isEditMode = true;
        printButton.setEnabled(true);
        selectedRowIndex = rowIndex;
        
        // Hide submit button and show edit buttons
        submitButton.setVisible(false);
        showEditButtons();
    }
    
    private void exitEditMode() {
        isEditMode = false;
        printButton.setEnabled(false);
        selectedRowIndex = -1;
        originalAppointmentData = null;
        
        // Show submit button and hide edit buttons
        hideEditButtons();
        submitButton.setVisible(true);
        
        // Clear form
        clearForm();
        
        // Clear table selection
        appointmentTable.clearSelection();
        
        // Force repaint
        rightPanel.revalidate();
        rightPanel.repaint();
    }
    
    private void showEditButtons() {
        if (buttonPanel != null) {
            buttonPanel.setVisible(true);
            return;
        }
        
        // Create button panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        
        // Delete button
        deleteButton = new RoundedSubmitButton("Delete", 20, new Color(220, 20, 60)); // Crimson
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setPreferredSize(new Dimension(120, 40));
        deleteButton.addActionListener(e -> deleteAppointment());
        
        // Undo button
        undoButton = new RoundedSubmitButton("Undo", 20, new Color(255, 165, 0)); // Orange
        undoButton.setForeground(Color.WHITE);
        undoButton.setFont(new Font("Arial", Font.BOLD, 14));
        undoButton.setPreferredSize(new Dimension(120, 40));
        undoButton.addActionListener(e -> undoChanges());
        
        // Save button
        saveButton = new RoundedSubmitButton("Save", 20, new Color(34, 139, 34)); // Forest Green
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setPreferredSize(new Dimension(120, 40));
        saveButton.addActionListener(e -> saveChanges());
        
        buttonPanel.add(deleteButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(saveButton);
        
        // Add to the same parent as submit button
        JPanel submitPanel = (JPanel) submitButton.getParent();
        submitPanel.add(buttonPanel);
        submitPanel.revalidate();
        submitPanel.repaint();
    }

    private void hideEditButtons() {
        if (buttonPanel != null) {
            buttonPanel.setVisible(false);
        }
    }

    private void deleteAppointment() {
        if (selectedRowIndex < 0 || selectedRowIndex >= appointmentList.size()) {
            return;
        }
        
        AppointmentData appointmentToDelete = appointmentList.get(selectedRowIndex);
        
        // Confirm deletion
        int result = JOptionPane.showConfirmDialog(
            null,
            "Are you sure you want to delete the appointment for " + 
            appointmentToDelete.getFirstName() + " " + appointmentToDelete.getLastName() + "?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (result == JOptionPane.YES_OPTION) {
            // Delete from database
            appointmentInventory.connect();
            appointmentInventory.removeAppointment(appointmentToDelete.getId());
            appointmentInventory.disconnect();
            
            // Remove from local list
            appointmentList.remove(selectedRowIndex);
            
            // Refresh table
            refreshTableData();
            
            // Exit edit mode
            exitEditMode();
            
            JOptionPane.showMessageDialog(null, "Appointment deleted successfully!", 
                                        "Deleted", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void undoChanges() {
        if (originalAppointmentData == null) {
            return;
        }
        
        // Restore original values
        lastNameField.setText(originalAppointmentData.getLastName());
        firstNameField.setText(originalAppointmentData.getFirstName());
        middleNameField.setText(originalAppointmentData.getMiddleName());
        appointmentDropdown.setSelectedItem(originalAppointmentData.getAppointmentType());
        
        // Set day if applicable
        String originalDay = originalAppointmentData.getSelectedDay();
        if (originalDay != null && !originalDay.isEmpty()) {
            Timer timer = new Timer(100, e -> {
                dayDropdown.setSelectedItem(originalDay);
                ((Timer)e.getSource()).stop();
            });
            timer.start();
        }
        
        // Set note
        String originalNote = originalAppointmentData.getNote();
        if (originalNote != null && !originalNote.isEmpty()) {
            noteTextArea.setText(originalNote);
            noteTextArea.setForeground(Color.BLACK);
        } else {
            noteTextArea.setText("Please describe your conditions here...");
            noteTextArea.setForeground(new Color(150, 150, 150, 128));
        }
    }

    private void saveChanges() {
        if (selectedRowIndex < 0 || selectedRowIndex >= appointmentList.size()) {
            return;
        }
        
        // Get the appointment to update
        AppointmentData appointmentToUpdate = appointmentList.get(selectedRowIndex);
        
        // Validate form (similar to submit validation)
        String lastName = lastNameField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String middleName = middleNameField.getText().trim();
        
        if (lastName.isEmpty() || firstName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter both Last Name and First Name.", 
                                        "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String selectedType = (String) appointmentDropdown.getSelectedItem();
        if (selectedType == null || selectedType.isEmpty() || selectedType.equals("   -- Select Appointment --")) {
            JOptionPane.showMessageDialog(null, "Please select an appointment type.", 
                                        "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Determine the selected day
        String selectedDay = "";
        if (daySelectionPanel.getParent() != null && daySelectionPanel.isVisible()) {
            // Only validate if day panel is actually visible
            selectedDay = (String) dayDropdown.getSelectedItem();
            if (selectedDay == null || selectedDay.equals(" -- Select Day -- ")) {
                JOptionPane.showMessageDialog(null, "Please select a day for your appointment.", 
                                            "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } else {
            // For fixed day appointments, set the day automatically
            if (selectedType.contains("Wednesday")) {
                selectedDay = "Wednesday";
            } else if (selectedType.contains("Thursday")) {
                selectedDay = "Thursday";
            }
        }
        
        String note = noteTextArea.getText().trim();
        if (note.equals("Please describe your conditions here...")) {
            note = "";
        }
        
        // Check if anything has actually changed
        boolean hasChanges = false;
        
        if (!lastName.equals(appointmentToUpdate.getLastName())) {
            hasChanges = true;
        }
        if (!firstName.equals(appointmentToUpdate.getFirstName())) {
            hasChanges = true;
        }
        if (!middleName.equals(appointmentToUpdate.getMiddleName())) {
            hasChanges = true;
        }
        if (!selectedType.equals(appointmentToUpdate.getAppointmentType())) {
            hasChanges = true;
        }
        if (!selectedDay.equals(appointmentToUpdate.getSelectedDay())) {
            hasChanges = true;
        }
        // Handle note comparison (account for null values and placeholder text)
        String originalNote = appointmentToUpdate.getNote();
        if (originalNote == null) originalNote = "";
        if (!note.equals(originalNote)) {
            hasChanges = true;
        }
        
        // If no changes detected, show message and return
        if (!hasChanges) {
            JOptionPane.showMessageDialog(null, "Nothing was changed.", 
                                        "No Changes", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Keep existing ID (don't generate new one for updates)
        String appointmentId = appointmentToUpdate.getId();
        
        // Update database
        appointmentInventory.connect();
        appointmentInventory.updateAppointmentInfo(lastName, firstName, middleName, selectedType, selectedDay, note, appointmentId);
        appointmentInventory.disconnect();
        
        // Update local list
        AppointmentData updatedAppointment = new AppointmentData(lastName, firstName, middleName, selectedType, selectedDay, note, appointmentId);
        appointmentList.set(selectedRowIndex, updatedAppointment);
        
        // Refresh table
        refreshTableData();
        
        // Exit edit mode
        exitEditMode();
        
        JOptionPane.showMessageDialog(null, "Appointment updated successfully!", 
                                    "Saved", JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshTableData() {
        // Clear existing data
        tableModel.setRowCount(0);
        
        // Load from database
        appointmentInventory.connect();
        List<AppointmentData> appointments = appointmentInventory.loadAppointmentInfoFromDatabase();
        appointmentInventory.disconnect();
        
        // Update local list - IMPORTANT: Keep original order
        this.appointmentList.clear();
        this.appointmentList.addAll(appointments);
        
        // Populate table in same order
        for (int i = 0; i < appointmentList.size(); i++) {
            AppointmentData appt = appointmentList.get(i);
            Object[] row = {appt.getId(), appt.getLastName()};
            tableModel.addRow(row);
        }
    }
    
    private void toggleSearchMode() {
        isSearchMode = !isSearchMode;
        
        if (isSearchMode) {
            searchField.setEditable(true);
            searchField.setText("");
            searchField.setForeground(Color.BLACK);
            searchField.requestFocus();
            searchButton.setText("Cancel");
        } else {
            searchField.setEditable(false);
            searchField.setText("Click Search");
            searchField.setForeground(Color.GRAY);
            searchButton.setText("Search");
            // Reset table to show all appointments
            refreshTableData();
        }
    }

    private void filterTable() {
        String searchText = searchField.getText().toLowerCase().trim();
        
        if (searchText.isEmpty()) {
            refreshTableData();
            return;
        }
        
        // Clear existing table data
        tableModel.setRowCount(0);
        
        // Filter appointments based on search text
        for (AppointmentData appt : appointmentList) {
            boolean matches = false;
            
            // Search in last name, first name, middle name, or ID
            if (appt.getLastName().toLowerCase().contains(searchText) ||
                appt.getFirstName().toLowerCase().contains(searchText) ||
                appt.getMiddleName().toLowerCase().contains(searchText) ||
                appt.getId().toLowerCase().contains(searchText) ||
                appt.getAppointmentType().toLowerCase().contains(searchText)) {
                matches = true;
            }
            
            if (matches) {
                Object[] row = {appt.getId(), appt.getLastName()};
                tableModel.addRow(row);
            }
        }
    }

    private void refreshTableWithSorting(String sortType) {
        // Clear existing table data
        tableModel.setRowCount(0);
        
        // Create a copy of the list to sort
        List<AppointmentData> sortedList = new ArrayList<>(appointmentList);
        
        // Sort based on selected type
        switch (sortType) {
        case "Name (A-Z)":
            sortedList.sort((a, b) -> a.getLastName().compareToIgnoreCase(b.getLastName()));
            break;
        case "Name (Z-A)":
            sortedList.sort((a, b) -> b.getLastName().compareToIgnoreCase(a.getLastName()));
            break;
        case "Date (Oldest-Newest)": // Changed from "ID (Low-High)"
            sortedList.sort((a, b) -> {
                try {
                    java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("MM-dd-yyyy");
                    java.util.Date dateA = dateFormat.parse(a.getId());
                    java.util.Date dateB = dateFormat.parse(b.getId());
                    return dateA.compareTo(dateB);
                } catch (java.text.ParseException e) {
                    return 0; // If parsing fails, consider them equal
                }
            });
            break;
        case "Date (Newest-Oldest)": // Changed from "ID (High-Low)"
            sortedList.sort((a, b) -> {
                try {
                    java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("MM-dd-yyyy");
                    java.util.Date dateA = dateFormat.parse(a.getId());
                    java.util.Date dateB = dateFormat.parse(b.getId());
                    return dateB.compareTo(dateA);
                } catch (java.text.ParseException e) {
                    return 0;
                }
            });
            break;
    }
        
        // Populate table with sorted data
        for (AppointmentData appt : sortedList) {
        	Object[] row = {appt.getId(), appt.getLastName()};
            tableModel.addRow(row);
        }
    }
    
    // In rightButtonPanelFunctions.java

    /**
     * Finds an appointment and its true index in the master list based on its ID.
     * @param appointmentId The ID of the appointment to find.
     * @return The correct AppointmentData object, or null if not found.
     */
    private AppointmentData findAppointmentByTableRow(int tableRow) {
        // Get the ID from the table at the clicked row
        String tableId = (String) appointmentTable.getValueAt(tableRow, 0);
        String tableName = (String) appointmentTable.getValueAt(tableRow, 1);
        
        // Find all appointments with this ID
        List<AppointmentData> matchingAppts = new ArrayList<>();
        for (AppointmentData appt : appointmentList) {
            if (appt.getId().equals(tableId)) {
                matchingAppts.add(appt);
            }
        }
        
        // If only one match, return it
        if (matchingAppts.size() == 1) {
            return matchingAppts.get(0);
        }
        
        // If multiple matches, use last name to differentiate
        for (AppointmentData appt : matchingAppts) {
            if (appt.getLastName().equals(tableName)) {
                return appt;
            }
        }
        
        // Fallback - return first match
        return matchingAppts.isEmpty() ? null : matchingAppts.get(0);
    }

    /**
     * Populates the form fields based on a unique appointment ID.
     * This is the new entry point from the GUI click listener.
     * @param appointmentId The ID of the selected appointment.
     */
    
    private void populateFormDirectly(AppointmentData selectedAppt, int listIndex) {
        // Check if same row is selected again - exit edit mode
        if (isEditMode && selectedRowIndex == listIndex) {
            exitEditMode();
            appointmentTable.clearSelection();
            return;
        }
        
        // Enter edit mode
        enterEditMode(listIndex);
        
        // Store original data for undo functionality
        originalAppointmentData = new AppointmentData(
            selectedAppt.getLastName(),
            selectedAppt.getFirstName(), 
            selectedAppt.getMiddleName(),
            selectedAppt.getAppointmentType(),
            selectedAppt.getSelectedDay(),
            selectedAppt.getNote(),
            selectedAppt.getId()
        );
        
        // Populate form fields
        lastNameField.setText(selectedAppt.getLastName());
        firstNameField.setText(selectedAppt.getFirstName());
        middleNameField.setText(selectedAppt.getMiddleName());
        
        // Set appointment type and trigger the day dropdown setup
        appointmentDropdown.setSelectedItem(selectedAppt.getAppointmentType());
        
        // Set day if applicable
        String selectedDay = selectedAppt.getSelectedDay();
        if (selectedDay != null && !selectedDay.isEmpty()) {
            Timer timer = new Timer(200, e -> {
                if (dayDropdown.getItemCount() > 1) {
                    dayDropdown.setSelectedItem(selectedDay);
                }
                ((Timer)e.getSource()).stop();
            });
            timer.start();
        }
        
        // Set note
        String note = selectedAppt.getNote();
        if (note != null && !note.isEmpty()) {
            noteTextArea.setText(note);
            noteTextArea.setForeground(Color.BLACK);
        } else {
            noteTextArea.setText("Please describe your conditions here...");
            noteTextArea.setForeground(new Color(150, 150, 150, 128));
        }
    }
    
    private void printMode() {
        try {
        	if (!isEditMode || selectedRowIndex < 0 || selectedRowIndex >= appointmentList.size()) {
                JOptionPane.showMessageDialog(null, "No appointment selected for printing.", 
                                            "Print Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
        	
            // Step 1: Collect user information
        	// Get the selected appointment data
            AppointmentData selectedAppt = appointmentList.get(selectedRowIndex);
            
            String fName = selectedAppt.getFirstName();
            String lName = selectedAppt.getLastName();
            String mName = selectedAppt.getMiddleName();
            String apptType = selectedAppt.getAppointmentType();
            String apptDate = selectedAppt.getSelectedDay();
            String note = selectedAppt.getNote();
            String date = selectedAppt.getId();
            
            // Show print dialog with the data
            StringBuilder printData = new StringBuilder();
            printData.append("Date Recorded: ").append(date).append("\n");
            printData.append("Name: ").append(fName).append(" ");
            if (!mName.isEmpty()) {
                printData.append(mName).append(" ");
            }
            printData.append(lName).append("\n");
            printData.append("Appointment Type: ").append(apptType).append("\n");
            printData.append("Date Scheduled: ").append(apptDate).append("\n");
            printData.append("Note: ").append(note.isEmpty() ? "None" : note);
            
            JOptionPane.showMessageDialog(null, printData.toString(), "Printing...", JOptionPane.INFORMATION_MESSAGE);
            
            // Step 2: Load the template
            FileInputStream templateFile = new FileInputStream("appointmentFormDocx.docx");
            XWPFDocument document = new XWPFDocument(templateFile);
            
            // Step 3: Replace placeholders in all paragraphs
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    if (text != null) {
                        // Replace placeholders with actual data
                        text = text.replace("{{FIRST_NAME}}", fName);
                        text = text.replace("{{LAST_NAME}}", lName);
                        text = text.replace("{{MIDDLE_NAME}}", mName);
                        text = text.replace("{{APPOINTMENT_TYPE}}", apptType);
                        text = text.replace("{{APPOINTMENT_DATE}}", apptDate);
                        text = text.replace("{{NOTE}}", note);
                        text = text.replace("{{DATE}}", date);
                        run.setText(text, 0);
                        
                        // Set font properties for the replaced text
                        if (text.contains(fName) || text.contains(lName) || text.contains(mName)
                        		|| text.contains(apptType) || text.contains(apptDate) || text.contains(note) || text.contains(date)) {
                            run.setFontFamily("Calibri");  // Change font
                            run.setFontSize(11);          // Change size
                            // run.setBold(true);            // Make bold (optional)
                            // run.setItalic(true);       // Make italic (optional)
                            // run.setColor("FF0000");    // Set color to red (optional)
                        }
                    }
                }
            }
            
            // Step 4: Save the processed document
            // Step 4: Save the processed document with file chooser
            JFileChooser fileChooser = new JFileChooser(lastUsedDirectory);
            fileChooser.setDialogTitle("Save Appointment Form");
            fileChooser.setSelectedFile(new File(lName + "_" + fName + "_Appointment_Note.docx"));
            int userSelection = fileChooser.showSaveDialog(null); // Change 'null' to your main panel reference if available

            if (userSelection != JFileChooser.APPROVE_OPTION) {
                document.close();
                templateFile.close();
                return;
            }

            File outputFile = fileChooser.getSelectedFile();
            // Ensure the file has a .docx extension
            if (!outputFile.getName().toLowerCase().endsWith(".docx")) {
                outputFile = new File(outputFile.getParentFile(), outputFile.getName() + ".docx");
            }

            // Update the last used directory
            lastUsedDirectory = outputFile.getParent();

            FileOutputStream out = new FileOutputStream(outputFile);

            document.write(out);
            
            // Clean up
            out.close();
            document.close();
            templateFile.close();
            
            JOptionPane.showMessageDialog(null, "Appointment form saved successfully to:\n" + outputFile.getAbsolutePath(), "Success", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (IOException e) {
        	String messageBuilder = "ERROR: " + e.getMessage() + "\nMake sure 'appointmentFormDocx.docx' exists in your project folder!";
        	JOptionPane.showMessageDialog(null, messageBuilder, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Method to be called by appointmentInventory.java to load data from database
    public void loadAppointmentsFromDatabase(List<AppointmentData> appointments) {
        this.appointmentList = appointments;
        // Implement code here
    }

    // Method to get current appointments (for database operations)
    public List<AppointmentData> getAppointments() {
        return appointmentList;
    }

    public JPanel getPanel() {
        return rightPanel;
    }
}