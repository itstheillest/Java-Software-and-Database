package sample;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class AppointmentData {
    private static int nextId = 1;
    private int ID;
    private String lastName;
    private String firstName;
    private String middleName;
    private String appointmentType;
    private String selectedDay;
    private String note;
    
    // Add this constructor to your AppointmentData class
    public AppointmentData(String lastName, String firstName, String middleName, 
                          String appointmentType, String selectedDay, String note, int ID) {
        
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.appointmentType = appointmentType;
        this.selectedDay = selectedDay;
        this.note = note;
        this.ID = ID;
    }
    
    // Getters
    public int getId() { return ID; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getAppointmentType() { return appointmentType; }
    public String getSelectedDay() { return selectedDay; }
    public String getNote() { return note; }
}

public class rightButtonPanelFunctions {
    
    private rightButtonPanelGUI gui;
    private List<AppointmentData> appointmentList;
    private boolean isTableVisible = false;
    
    private boolean isEditMode = false;
    private int selectedRowIndex = -1;
    private AppointmentData originalAppointmentData; // Store original values for undo
    private boolean isSearchMode = false;

    public rightButtonPanelFunctions(rightButtonPanelGUI gui){
        this.gui = gui;
        this.appointmentList = new ArrayList<>();
        SwingUtilities.invokeLater(() -> {
        	refreshTableData();
        });
    }

    public void submitAppointment() {
        // Validate name fields
        String lastName = gui.getLastNameField().getText().trim();
        String firstName = gui.getFirstNameField().getText().trim();
        String middleName = gui.getMiddleNameField().getText().trim();
        
        if (lastName.isEmpty() || firstName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter both Last Name and First Name.", 
                                        "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validate appointment type
        String selectedType = (String) gui.getAppointmentDropdown().getSelectedItem();
        if (selectedType == null || selectedType.isEmpty() || selectedType.equals("   -- Select Appointment --")) {
            JOptionPane.showMessageDialog(null, "Please select an appointment type.", 
                                        "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validate day selection if needed
     // Validate day selection if needed
     // Validate day selection if needed
        String selectedDay = "";
        if (gui.getDaySelectionPanel().getParent() != null && gui.getDaySelectionPanel().isVisible()) {
            // Only validate if day panel is actually visible
            selectedDay = (String) gui.getDayDropdown().getSelectedItem();
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
        String note = gui.getNoteTextArea().getText().trim();
        if (note.equals("Please describe your conditions here...")) {
            note = "";
        }
        
        // Get the appointment to update
     // Generate new ID for new appointment
        int appointmentId = appointmentList.size() + 1;
        // Or use a better ID generation method

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
        // TODO: Here you would call your appointmentInventory.java to save to database
        // saveToDatabase(newAppointment);
    }

    private void clearForm() {
        gui.getLastNameField().setText("");
        gui.getFirstNameField().setText("");
        gui.getMiddleNameField().setText("");
        gui.getAppointmentDropdown().setSelectedIndex(0);
        // Remove day selection panel properly
        if (gui.getDaySelectionPanel().getParent() != null) {
            gui.getFieldsPanel().remove(gui.getDaySelectionPanel());
        }
        gui.getFieldsPanel().revalidate();
        gui.getFieldsPanel().repaint();
        gui.getNoteTextArea().setText("Please describe your conditions here...");
        gui.getNoteTextArea().setForeground(new Color(150, 150, 150, 128));
    }
    
    // Add this method to your rightButtonPanel class
    // Replace your populateFormFromTable method with this corrected version:
    // Replace your populateFormFromTable method with this corrected version:
    // Replace your populateFormFromTable method with this:
    public void populateFormFromTable(int selectedRow) {
        if (selectedRow < 0 || selectedRow >= appointmentList.size()) {
            System.out.println("Invalid row selected: " + selectedRow);
            return;
        }
        
        // Check if same row is selected again - exit edit mode
        if (isEditMode && selectedRowIndex == selectedRow) {
            exitEditMode();
            gui.getAppointmentTable().clearSelection();
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
        gui.getLastNameField().setText(selectedAppt.getLastName());
        gui.getFirstNameField().setText(selectedAppt.getFirstName());
        gui.getMiddleNameField().setText(selectedAppt.getMiddleName());
        // Set appointment type and trigger the day dropdown setup
        gui.getAppointmentDropdown().setSelectedItem(selectedAppt.getAppointmentType());
        // Set day if applicable - use a timer to ensure dropdown is populated first
        String selectedDay = selectedAppt.getSelectedDay();
        if (selectedDay != null && !selectedDay.isEmpty()) {
            Timer timer = new Timer(200, e -> { // Increased delay
                if (gui.getDayDropdown().getItemCount() > 1) { // Check if items are loaded
                    gui.getDayDropdown().setSelectedItem(selectedDay);
                }
          
               ((Timer)e.getSource()).stop();
            });
            timer.start();
        }
        
        // Set note
        String note = selectedAppt.getNote();
        if (note != null && !note.isEmpty()) {
            gui.getNoteTextArea().setText(note);
            gui.getNoteTextArea().setForeground(Color.BLACK);
        } else {
            gui.getNoteTextArea().setText("Please describe your conditions here...");
            gui.getNoteTextArea().setForeground(new Color(150, 150, 150, 128));
        }
    }
    
    public void enterEditMode(int rowIndex) {
        isEditMode = true;
        selectedRowIndex = rowIndex;
        
        // Hide submit button and show edit buttons
        gui.getSubmitButton().setVisible(false);
        showEditButtons();
    }
    
    public void exitEditMode() {
        isEditMode = false;
        selectedRowIndex = -1;
        originalAppointmentData = null;
        
        // Show submit button and hide edit buttons
        hideEditButtons();
        gui.getSubmitButton().setVisible(true);
        
        // Clear form
        clearForm();
        // Clear table selection
        gui.getAppointmentTable().clearSelection();
        // Force repaint
        gui.getRightPanel().revalidate();
        gui.getRightPanel().repaint();
    }
    
    private void showEditButtons() {
        if (gui.getButtonPanel() != null) {
            gui.getButtonPanel().setVisible(true);
            return;
        }
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        gui.setButtonPanel(buttonPanel);
        buttonPanel.setOpaque(false);
        
        // Delete button
        JButton deleteButton = new RoundedSubmitButton("Delete", 20, new Color(220, 20, 60));
        // Crimson
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setPreferredSize(new Dimension(120, 40));
        deleteButton.addActionListener(e -> deleteAppointment());
        // Undo button
        JButton undoButton = new RoundedSubmitButton("Undo", 20, new Color(255, 165, 0));
        // Orange
        undoButton.setForeground(Color.WHITE);
        undoButton.setFont(new Font("Arial", Font.BOLD, 14));
        undoButton.setPreferredSize(new Dimension(120, 40));
        undoButton.addActionListener(e -> undoChanges());
        // Save button
        JButton saveButton = new RoundedSubmitButton("Save", 20, new Color(34, 139, 34));
        // Forest Green
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setPreferredSize(new Dimension(120, 40));
        saveButton.addActionListener(e -> saveChanges());
        
        buttonPanel.add(deleteButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(saveButton);
        
        // Add to the same parent as submit button
        JPanel submitPanel = (JPanel) gui.getSubmitButton().getParent();
        submitPanel.add(buttonPanel);
        submitPanel.revalidate();
        submitPanel.repaint();
    }

    private void hideEditButtons() {
        if (gui.getButtonPanel() != null) {
            gui.getButtonPanel().setVisible(false);
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
        gui.getLastNameField().setText(originalAppointmentData.getLastName());
        gui.getFirstNameField().setText(originalAppointmentData.getFirstName());
        gui.getMiddleNameField().setText(originalAppointmentData.getMiddleName());
        gui.getAppointmentDropdown().setSelectedItem(originalAppointmentData.getAppointmentType());
        
        // Set day if applicable
        String originalDay = originalAppointmentData.getSelectedDay();
        if (originalDay != null && !originalDay.isEmpty()) {
            Timer timer = new Timer(100, e -> {
                gui.getDayDropdown().setSelectedItem(originalDay);
                ((Timer)e.getSource()).stop();
            });
            timer.start();
        }
        
        // Set note
        String originalNote = originalAppointmentData.getNote();
        if (originalNote != null && !originalNote.isEmpty()) {
            gui.getNoteTextArea().setText(originalNote);
            gui.getNoteTextArea().setForeground(Color.BLACK);
        } else {
            gui.getNoteTextArea().setText("Please describe your conditions here...");
            gui.getNoteTextArea().setForeground(new Color(150, 150, 150, 128));
        }
    }

    private void saveChanges() {
        if (selectedRowIndex < 0 || selectedRowIndex >= appointmentList.size()) {
            return;
        }
        
        // Validate form (similar to submit validation)
        String lastName = gui.getLastNameField().getText().trim();
        String firstName = gui.getFirstNameField().getText().trim();
        String middleName = gui.getMiddleNameField().getText().trim();
        
        if (lastName.isEmpty() || firstName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter both Last Name and First Name.", 
                                        "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String selectedType = (String) gui.getAppointmentDropdown().getSelectedItem();
        if (selectedType == null || selectedType.isEmpty() || selectedType.equals("   -- Select Appointment --")) {
            JOptionPane.showMessageDialog(null, "Please select an appointment type.", 
                                        "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String selectedDay = "";
        if (gui.getDaySelectionPanel().isVisible()) {
            selectedDay = (String) gui.getDayDropdown().getSelectedItem();
            if (selectedDay == null || selectedDay.equals(" -- Select Day -- ")) {
                JOptionPane.showMessageDialog(null, "Please select a day for your appointment.", 
                                            "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        
        String note = gui.getNoteTextArea().getText().trim();
        if (note.equals("Please describe your conditions here...")) {
            note = "";
        }
        
        // Get the appointment to update
        AppointmentData appointmentToUpdate = appointmentList.get(selectedRowIndex);
        int appointmentId = appointmentToUpdate.getId();
        
        // Update database
        appointmentInventory.connect();
        appointmentInventory.updateAppointmentInfo(lastName, firstName, middleName, selectedType, selectedDay, note, appointmentId);
        appointmentInventory.disconnect();
        
        // Update local list
        AppointmentData updatedAppointment = new AppointmentData(lastName, firstName, middleName, selectedType, selectedDay, note, appointmentId);
        // Note: You'll need to add a method to set the ID in AppointmentData, or modify the constructor
        appointmentList.set(selectedRowIndex, updatedAppointment);
        // Refresh table
        refreshTableData();
        // Exit edit mode
        exitEditMode();
        JOptionPane.showMessageDialog(null, "Appointment updated successfully!", 
                                    "Saved", JOptionPane.INFORMATION_MESSAGE);
    }
    
 // Add this method to your rightButtonPanel class
    public void toggleTable() {
        isTableVisible = !isTableVisible;
        gui.getTableScrollPane().setVisible(isTableVisible);
        
        if (isTableVisible) {
            // Load data from database and refresh table
            refreshTableData();
            gui.getEditButton().setText("Hide Table");
        } else {
            gui.getEditButton().setText("Edit");
        }
        
        gui.getRightPanel().revalidate();
        gui.getRightPanel().repaint();
    }

    public void refreshTableData() {
        // Clear existing data
        gui.getTableModel().setRowCount(0);
        // Load from database
        appointmentInventory.connect();
        List<AppointmentData> appointments = appointmentInventory.loadAppointmentInfoFromDatabase();
        appointmentInventory.disconnect();
        // Update local list
        this.appointmentList = appointments;
        // Populate table
        for (AppointmentData appt : appointments) {
            Object[] row = {appt.getLastName(), appt.getId()};
            gui.getTableModel().addRow(row);
        }
    }
    
    public void toggleSearchMode() {
        isSearchMode = !isSearchMode;
        if (isSearchMode) {
            gui.getSearchField().setEditable(true);
            gui.getSearchField().setText("");
            gui.getSearchField().setForeground(Color.BLACK);
            gui.getSearchField().requestFocus();
            gui.getSearchButton().setText("Cancel");
        } else {
            gui.getSearchField().setEditable(false);
            gui.getSearchField().setText("Click Search");
            gui.getSearchField().setForeground(Color.GRAY);
            gui.getSearchButton().setText("Search");
            // Reset table to show all appointments
            refreshTableData();
        }
    }

    public void filterTable() {
        String searchText = gui.getSearchField().getText().toLowerCase().trim();
        if (searchText.isEmpty()) {
            refreshTableData();
            return;
        }
        
        // Clear existing table data
        gui.getTableModel().setRowCount(0);
        // Filter appointments based on search text
        for (AppointmentData appt : appointmentList) {
            boolean matches = false;
            // Search in last name, first name, middle name, or ID
            if (appt.getLastName().toLowerCase().contains(searchText) ||
                appt.getFirstName().toLowerCase().contains(searchText) ||
                appt.getMiddleName().toLowerCase().contains(searchText) ||
                String.valueOf(appt.getId()).contains(searchText) ||
                appt.getAppointmentType().toLowerCase().contains(searchText)) {
        
                 matches = true;
        }
            
            if (matches) {
                Object[] row = {appt.getLastName(), appt.getId()};
                gui.getTableModel().addRow(row);
            }
        }
    }

    public void refreshTableWithSorting(String sortType) {
        // Clear existing table data
        gui.getTableModel().setRowCount(0);
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
            case "ID (Low-High)":
                sortedList.sort((a, b) -> Integer.compare(a.getId(), b.getId()));
                break;
            case "ID (High-Low)":
                sortedList.sort((a, b) -> Integer.compare(b.getId(), a.getId()));
                break;
        }
        
        // Populate table with sorted data
        for (AppointmentData appt : sortedList) {
            Object[] row = {appt.getLastName(), appt.getId()};
            gui.getTableModel().addRow(row);
        }
    }
    
    private AppointmentData findAppointmentById(int appointmentId) {
        for (AppointmentData appt : appointmentList) {
            if (appt.getId() == appointmentId) {
                return appt;
            }
        }
        return null; // Should not happen if table is in sync
    }
    
    public void populateFormById(int appointmentId) {
        AppointmentData selectedAppt = findAppointmentById(appointmentId);
        if (selectedAppt == null) {
            System.out.println("Error: Could not find appointment with ID: " + appointmentId);
            return;
        }

        // Find the actual index of the appointment in our master list
        int modelIndex = appointmentList.indexOf(selectedAppt);

        // Check if same row is selected again - exit edit mode
        if (isEditMode && selectedRowIndex == modelIndex) {
            exitEditMode();
            gui.getAppointmentTable().clearSelection();
            return;
        }

        // Enter edit mode using the correct index from the master list
        enterEditMode(modelIndex);

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
        gui.getLastNameField().setText(selectedAppt.getLastName());
        gui.getFirstNameField().setText(selectedAppt.getFirstName());
        gui.getMiddleNameField().setText(selectedAppt.getMiddleName());
        
        // Set appointment type and trigger the day dropdown setup
        gui.getAppointmentDropdown().setSelectedItem(selectedAppt.getAppointmentType());
        
        // Set day if applicable - use a timer to ensure dropdown is populated first
        String selectedDay = selectedAppt.getSelectedDay();
        if (selectedDay != null && !selectedDay.isEmpty()) {
            Timer timer = new Timer(100, e -> {
                if (gui.getDayDropdown().getItemCount() > 1) { // Check if items are loaded
                    gui.getDayDropdown().setSelectedItem(selectedDay);
                }
                ((Timer)e.getSource()).stop();
            });
            timer.start();
        }
        
        // Set note
        String note = selectedAppt.getNote();
        if (note != null && !note.isEmpty()) {
            gui.getNoteTextArea().setText(note);
            gui.getNoteTextArea().setForeground(Color.BLACK);
        } else {
            gui.getNoteTextArea().setText("Please describe your conditions here...");
            gui.getNoteTextArea().setForeground(new Color(150, 150, 150, 128));
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
    
    public boolean isSearchMode(){
        return isSearchMode;
    }
    
    public int getSelectedRowIndex(){
        return selectedRowIndex;
    }
    
    public boolean isEditMode(){
        return isEditMode;
    }
}