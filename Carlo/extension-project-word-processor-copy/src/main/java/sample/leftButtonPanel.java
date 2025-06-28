package sample;

import sample.Inventory;	

import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.TableCellEditor;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import java.sql.*;

import org.apache.poi.xwpf.usermodel.*;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.File;

public class leftButtonPanel {
    private JPanel mainPanel;
    private JButton returnButton, editModeButton, addButton, removeButton, backButton, searchButton, editMedicineButton, printButton;
    private JPanel rightHeaderPanel;
    private JComboBox medicineSorting;
    private String currentSortType = "Name (A-Z)"; // Track current sorting
    private static String lastUsedDirectory = null;
    
    private JTextField searchField;
    private JPanel scrollableBodyPanel, headerPanel, searchPanel, footerPanel, viewButtonPanel;
    private JButton viewButton, confirmButton, undoButton, saveButton;
    
    private JCheckBox checkBox;
    
    // New fields for table implementation
    private JTable medicineTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> tableSorter;
    private JPanel paginationPanel;
    
    // Update mode fields
    private JPanel updateButtonsPanel;
    private boolean isUpdateMode = false;
    private boolean isSearchMode = false;
    private boolean isRemoveMode = false;
    private boolean isMedicineUpdateMode = false;
    
    // Track changes for undo functionality
    private Map<String, Integer> originalQuantities = new HashMap<>();
    private Map<String, Integer> currentQuantities = new HashMap<>();
    private boolean hasChanges = false;

    private Main mainApp; // Reference to the main app
    private Inventory inventory;

    public leftButtonPanel(Main mainApp) {
        this.mainApp = mainApp;

        mainPanel = new JPanel(new BorderLayout());

        setUpHeader();
        setUpBody();
        setUpFooter();

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(scrollableBodyPanel), BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        Inventory.loadMedicinesFromDatabase();       
        
        enterUpdateMode();
    }
    
    /*


	|=========================================== Creation of the Header, Body, and Footer ===========================================|


     */

    private void setUpHeader() {
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(85, 107, 47)); // Olive Green
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Return button (Left)
        returnButton = new JButton("Return");
        returnButton.addActionListener(e -> returnButtonAction());
        headerPanel.add(returnButton, BorderLayout.WEST);

        // Search (Center)
        searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        searchPanel.setOpaque(false);
        searchButton = new JButton("Search");
        searchField = new JTextField(15);
        
        // Set up search field with placeholder and disabled state
        searchField.setText("Click Search");
        searchField.setForeground(Color.GRAY);
        searchField.setEditable(false);
        
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                if (isSearchMode) filterProductPanels();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                if (isSearchMode) filterProductPanels();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                if (isSearchMode) filterProductPanels();
            }
        });
        
        medicineSorting = new JComboBox<>(new String[]{"Name (A-Z)", "Name (Z-A)", "Low Stock", "Out of Stock"});
        
        medicineSorting.addActionListener(e -> {
            currentSortType = (String) medicineSorting.getSelectedItem();
            refreshTableWithSorting();
        });

        searchButton.addActionListener(e -> toggleSearchMode());
        searchPanel.add(searchButton);
        searchPanel.add(searchField);
        searchPanel.add(medicineSorting);
        headerPanel.add(searchPanel, BorderLayout.CENTER);

        rightHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rightHeaderPanel.setOpaque(false);
        printButton = new JButton("Print");
        printButton.setVisible(true); // This hides the button
        
        printButton.addActionListener(e -> printMode()); 
        
        rightHeaderPanel.add(printButton);
        

        headerPanel.add(rightHeaderPanel, BorderLayout.EAST);
        
        // Create update buttons panel (initially hidden)
        createUpdateButtonsPanel();
    }
    
    private void setUpBody() {
        scrollableBodyPanel = new JPanel(new BorderLayout());
        scrollableBodyPanel.setBackground(Color.WHITE);
        scrollableBodyPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create status legend panel (without last updated)
        JPanel statusPanel = createStatusPanel();
        scrollableBodyPanel.add(statusPanel, BorderLayout.NORTH);
        
        // Create medicine table
        JPanel tablePanel = createTablePanel();
        scrollableBodyPanel.add(tablePanel, BorderLayout.CENTER);
        
        // Create pagination panel
        paginationPanel = createPaginationPanel();
        scrollableBodyPanel.add(paginationPanel, BorderLayout.SOUTH);
    }
    
    private void setUpFooter() {
    	editModeButton = new JButton("Edit Mode");
    	editModeButton.addActionListener(e -> enterUpdateMode());
        
        footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        footerPanel.setBackground(new Color(245, 245, 245)); // light gray for distinction

        undoButton = new JButton("Undo Quantity");
        viewButton = new JButton("View Mode");
        saveButton = new JButton("Save");

        undoButton.addActionListener(e -> undoChanges());
        viewButton.addActionListener(e -> exitUpdateMode());
        saveButton.addActionListener(e -> saveChanges());

        footerPanel.add(undoButton);
        footerPanel.add(viewButton);
        footerPanel.add(saveButton);
        footerPanel.add(editModeButton);

        // Keep panel visible, but buttons hidden by default
        undoButton.setVisible(false);
        viewButton.setVisible(false);
        saveButton.setVisible(false);
        editModeButton.setVisible(false);
    }
    
    
    
    /*


		|=========================================== Creation of Header Buttons and their functions ===========================================|


     */
    
    private void createUpdateButtonsPanel() {
        updateButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        updateButtonsPanel.setOpaque(false);
        
        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        editMedicineButton = new JButton("Edit");
        
        addButton.addActionListener(e -> addButtonAction());
        removeButton.addActionListener(e -> removeButtonAction());
        editMedicineButton.addActionListener(e -> editMedicineAction());
        
        updateButtonsPanel.add(addButton);
        updateButtonsPanel.add(removeButton);
        updateButtonsPanel.add(editMedicineButton); // Add this
    }
    
    // IMPORTANT: Add Button Action
    public void addButtonAction() {
        // Create input dialog for new medicine
    	inventory = new Inventory();
    	
    		JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
            
            JTextField nameField = new JTextField();
            JTextField classField = new JTextField();
            JTextField dosageField = new JTextField();
            JTextField brandField = new JTextField();
            JTextField stockField = new JTextField();
            
            panel.add(new JLabel("Medicine Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Pharmacologic Class:"));
            panel.add(classField);
            panel.add(new JLabel("Dosage (Mg):"));
            panel.add(dosageField);
            panel.add(new JLabel("Brand:"));
            panel.add(brandField);
            panel.add(new JLabel("Stock/Quantity:"));
            panel.add(stockField);
            
            for (;;) {
            	
            int result = JOptionPane.showConfirmDialog( mainPanel, panel, "Add New Medicine",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);

            if (result == -1 || result == 2) {
            	break;
            }
            
            // IMPORTANT
            // Add the database here
            
            if (result == JOptionPane.OK_OPTION) {
                // Add the new medicine to inventory
                String name = nameField.getText().trim();
                String pharmaClass = classField.getText().trim();
                String dosage = dosageField.getText().trim();
                String brand = brandField.getText().trim();
                String stock = stockField.getText().trim();
                
                if (!name.isEmpty() && !stock.isEmpty()) {
                    try {
                        int stockValue = Integer.parseInt(stock);
                        int dosageValue = Integer.parseInt(dosage);
                        
                        boolean success = inventory.insertMedicineToDatabase(name, pharmaClass, dosageValue, brand, stockValue);
                        
                        if (success) {
                            // Your existing success logic
                        	Inventory.refreshFromDatabase();
                        	refreshTable(); // or whatever you do to update the GUI
                        	JOptionPane.showMessageDialog(mainPanel, "Medicine added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                        	JOptionPane.showMessageDialog(mainPanel, "Failed to add medicine!", "Fail", JOptionPane.INFORMATION_MESSAGE);
                        }
                        
                        // You'll need to implement this method in your Inventory class
                        // Inventory.addMedicine(name, pharmacoClass, dosage, brand, stockValue);
                        // JOptionPane.showMessageDialog(mainPanel, "Medicine added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(mainPanel, "Please enter a valid stock number!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Please fill in required fields!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
    	}
        
    }
    
    public void removeButtonAction() {
        isRemoveMode = !isRemoveMode;
        isMedicineUpdateMode = false;
        
        if (isRemoveMode) {
            removeButton.setText("Exit Remove");
            editMedicineButton.setText("Edit");
        } else {
            removeButton.setText("Remove");
        }
        refreshTable();
        updateFooterButtonStates();
    }

    public void returnButtonAction() {
        mainApp.restoreMainButtons();
    }
    
    private void editMedicineAction() {
    	isMedicineUpdateMode = !isMedicineUpdateMode;
    	isRemoveMode = false;
    	
        if (isMedicineUpdateMode) {
        	editMedicineButton.setText("Exit Edit");
        	removeButton.setText("Remove");
        } else {
        	editMedicineButton.setText("Edit");
        }
        
        refreshTable();
        updateFooterButtonStates();
    }
    
    // Replace the old editMedicineAction(String medicineName) method with this one in leftButtonPanel.java
    private void editMedicineAction(String medicineName) {
        // Use the new, efficient method to get all details in one go
    	Map<String, Object> details = Inventory.getMedicineDetails(extractBaseMedicineName(medicineName));

        if (details.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Could not find details for " + medicineName, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Correctly get the current values from the details map
        String currentClass = (String) details.get("pharmaClass");
        String currentDosage = String.valueOf(details.get("dosage"));
        String currentBrand = (String) details.get("brand");
        String currentStock = String.valueOf(details.get("stock"));

        // --- The rest of the method builds the dialog panel ---
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));

        JTextField nameField = new JTextField(medicineName);
        JTextField classField = new JTextField(currentClass);
        JTextField dosageField = new JTextField(currentDosage);
        JTextField brandField = new JTextField(currentBrand);
        JTextField stockField = new JTextField(currentStock);

        // Make name field read-only as it's the primary identifier

        panel.add(new JLabel("Medicine Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Pharmacologic Class:"));
        panel.add(classField);
        panel.add(new JLabel("Dosage (Mg):"));
        panel.add(dosageField);
        panel.add(new JLabel("Brand:"));
        panel.add(brandField);
        panel.add(new JLabel("Stock/Quantity:"));
        panel.add(stockField);

        int result = JOptionPane.showConfirmDialog(mainPanel, panel, "Edit Medicine: " + medicineName,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                // Get the new values from the text fields
                String newName = nameField.getText().trim();
                String newClass = classField.getText().trim();
                int newDosage = Integer.parseInt(dosageField.getText().trim());
                String newBrand = brandField.getText().trim();
                int newStock = Integer.parseInt(stockField.getText().trim());

                String oldName = medicineName; // the name passed to this method is the old name

                // Use a new updateMedicine signature: updateMedicine(oldName, newName, ...)
                boolean success = Inventory.updateMedicine(oldName, newName, newClass, newDosage, newBrand, newStock);

                if (success) {
                    Inventory.refreshFromDatabase(); // Refresh local cache
                    refreshTable(); // Refresh the UI table
                    JOptionPane.showMessageDialog(mainPanel, "Medicine updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Failed to update medicine in the database!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Dosage and Stock must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
    /*


		|=========================================== Creation of the Table Information ===========================================|


     */
    
    private void populateTableData() {
        // Clear existing data
        tableModel.setRowCount(0);
        
        // Refresh data from database first
        Inventory.refreshFromDatabase();
        
        // Use filtered medicines instead of sorted
        List<String> filteredMedicines = getFilteredMedicines(currentSortType);
        
        // Add data from filtered list
        for (String medicine : filteredMedicines) {
            String manufacturer = getManufacturer(medicine);
            String quantity = isUpdateMode && currentQuantities.containsKey(medicine) ?
                String.valueOf(currentQuantities.get(medicine)) : getQuantity(medicine);
            
            Object[] rowData;
            if (isRemoveMode) {
                rowData = new Object[]{
                    false, // Checkbox
                    formatMedicineName(medicine),
                    manufacturer,
                    quantity
                };
            } else if (isMedicineUpdateMode) {
                rowData = new Object[]{
                		"Edit", // Button text
                        formatMedicineName(medicine),
                        manufacturer,
                        quantity
                    };
            } else {
                rowData = new Object[]{
                    formatMedicineName(medicine),
                    manufacturer,
                    quantity
                };
            }
            tableModel.addRow(rowData);
        }
    }

    private String formatMedicineName(String medicine) {
        return medicine + " (" + Inventory.getDosage(medicine) + "mg)\n" + Inventory.getPharmaClass(medicine);
    }

    private String getManufacturer(String medicine) {
        return Inventory.getManufacturer(medicine);
    }

    private String getQuantity(String medicine) {
        int stock = Inventory.getStock(medicine);
        return String.valueOf(stock);
    }
    
    /*


	|=========================================== Creation of Footer Buttons and their functions ===========================================|


     */
    
    private void showFooterButtons() {
        undoButton.setVisible(true);
        viewButton.setVisible(true);
        saveButton.setVisible(true);
        editModeButton.setVisible(false);
        updateFooterButtonStates();
    }

    private void hideFooterButtons() {
        undoButton.setVisible(false);
        viewButton.setVisible(false);
        saveButton.setVisible(false);
        editModeButton.setVisible(true);
    }
    
    // Replace the existing updateFooterButtonStates() method with this:
    // Replace the existing updateFooterButtonStates() method with this:
    private void updateFooterButtonStates() {
        // Always keep buttons enabled
        undoButton.setEnabled(true);
        saveButton.setEnabled(true);
        editModeButton.setEnabled(true);
        
        // Always keep buttons highlighted
        undoButton.setBackground(new Color(59, 130, 246)); // Blue
        undoButton.setForeground(Color.WHITE);
        
        if (!isRemoveMode) {
        	saveButton.setText("Save");
            saveButton.setBackground(new Color(34, 197, 94)); // Green
            saveButton.setForeground(Color.WHITE);
        } else {
        	saveButton.setText("Archive");
            saveButton.setBackground(new Color(255, 0, 0)); // Red
            saveButton.setForeground(Color.WHITE);
        }

    }
    
    private void undoChanges() {
        // Restore original quantities
        for (Map.Entry<String, Integer> entry : originalQuantities.entrySet()) {
            currentQuantities.put(entry.getKey(), entry.getValue());
        }
        
        hasChanges = false;
        updateFooterButtonStates();
        refreshTable();
    }
    
    private void saveChanges() {
        if (!hasChanges) {
            JOptionPane.showMessageDialog(mainPanel, "No Changes Made", "Make changes", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Handle Remove Mode - Delete selected medicines
        if (isRemoveMode) {
            // Get list of selected medicines for removal
            List<String> selectedMedicines = new ArrayList<>();
            
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                Boolean selected = (Boolean) tableModel.getValueAt(i, 0);
                if (selected != null && selected) {
                	String displayName = ((String) tableModel.getValueAt(i, 1)).split("\n")[0];
                	String medicineName = extractBaseMedicineName(displayName);
                    selectedMedicines.add(medicineName);
                }
            }
            
            if (selectedMedicines.isEmpty()) {
                JOptionPane.showMessageDialog(mainPanel, "No medicines selected for removal!", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int result = JOptionPane.showConfirmDialog(
                mainPanel,
                "Are you sure you want to permanently delete " + selectedMedicines.size() + " selected medicine(s)?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (result == JOptionPane.YES_OPTION) {
                int successCount = 0;
                List<String> failedDeletions = new ArrayList<>();
                
                // Delete each selected medicine from database
                for (String medicineName : selectedMedicines) {
                    boolean success = Inventory.removeMedicineFromDatabase(medicineName);
                    if (success) {
                        successCount++;
                    } else {
                        failedDeletions.add(medicineName);
                    }
                }
                
                // Show results
                if (successCount > 0) {
                    JOptionPane.showMessageDialog(mainPanel, 
                        successCount + " medicine(s) deleted successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Refresh data and exit remove mode
                    Inventory.refreshFromDatabase();
                    isRemoveMode = true;
                    removeButtonAction();
                    refreshTable();
                    
                    if (!failedDeletions.isEmpty()) {
                        JOptionPane.showMessageDialog(mainPanel, 
                            "Failed to delete: " + String.join(", ", failedDeletions), 
                            "Partial Success", 
                            JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(mainPanel, 
                        "Failed to delete any medicines!", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        // Handle Update Mode - Update stock quantities
        else {
            int result = JOptionPane.showConfirmDialog(
                mainPanel,
                "Save changes to stock quantities?",
                "Confirm Save",
                JOptionPane.YES_NO_OPTION
            );
            
            if (result == JOptionPane.YES_OPTION) {
                int successCount = 0;
                List<String> failedUpdates = new ArrayList<>();
                
                // Update stock quantities in database
                for (Map.Entry<String, Integer> entry : currentQuantities.entrySet()) {
                    String medicineName = entry.getKey();
                    int newQuantity = entry.getValue();
                    
                    // Only update if quantity actually changed
                    Integer originalQty = originalQuantities.get(medicineName);
                    if (originalQty != null && !originalQty.equals(newQuantity)) {
                    	boolean success = Inventory.updateStock(extractBaseMedicineName(medicineName), newQuantity);
                        if (success) {
                            successCount++;
                        } else {
                            failedUpdates.add(medicineName);
                        }
                    } else if (originalQty == null) {
                        failedUpdates.add(medicineName + " (not found in originalQuantities)");
                    }

                }
                
                if (successCount > 0) {
                    JOptionPane.showMessageDialog(mainPanel, 
                        successCount + " medicine(s) updated successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    refreshTable();
                    
                    if (!failedUpdates.isEmpty()) {
                        JOptionPane.showMessageDialog(mainPanel, 
                            "Failed to update: " + String.join(", ", failedUpdates), 
                            "Partial Success", 
                            JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(mainPanel, 
                        "No changes were made or all updates failed!", 
                        "No Changes", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
    
    private void enterUpdateMode() {
        isUpdateMode = true;
        isRemoveMode = true;
        isMedicineUpdateMode = false; // Add this line

        
        // Replace update button with update buttons panel
        headerPanel.remove(editModeButton);
        
        headerPanel.remove(rightHeaderPanel); 
        headerPanel.add(updateButtonsPanel, BorderLayout.EAST);
        removeButtonAction();
        
        // Show footer buttons
        showFooterButtons();
        
        // Store original quantities for undo functionality
        storeOriginalQuantities();
        
        // Refresh the table to show counter controls
        refreshTable();
        
        headerPanel.revalidate();
        headerPanel.repaint();
    }
    
    private void exitUpdateMode() {
        isUpdateMode = false;
        isRemoveMode = false;
        isMedicineUpdateMode = false;
        
        headerPanel.remove(updateButtonsPanel); // <-- This line is correct
        headerPanel.add(rightHeaderPanel, BorderLayout.EAST); // <-- ADD THIS LINE to restore the print button's panel
        
        // Replace update buttons panel with update button
        headerPanel.remove(updateButtonsPanel);
        
        // IMPORTANT PRINT BUTTON
        // printButton.setVisible(true);
        footerPanel.add(editModeButton, BorderLayout.EAST);
        
        // Hide footer buttons
        hideFooterButtons();
        editMedicineButton.setText("Edit");
        
        // Clear changes tracking
        hasChanges = false;
        originalQuantities.clear();
        currentQuantities.clear();
        
        // Refresh the table to hide counter controls and checkboxes
        refreshTable();
        
        headerPanel.revalidate();
        headerPanel.repaint();
    }
    
    
    /*


		|=========================================== Methods ===========================================|


     */
    
    private void toggleSearchMode() {
        isSearchMode = !isSearchMode;
        
        if (isSearchMode) {
            searchField.setEditable(true);
            searchField.setText("");
            searchField.setForeground(Color.BLACK);
            searchField.requestFocus();
        } else {
            searchField.setEditable(false);
            searchField.setText("Click Search");
            searchField.setForeground(Color.GRAY);
            tableSorter.setRowFilter(null); // Clear any existing filter
        }
    }
    
    private void toggleMedicineEditMode() {
        isMedicineUpdateMode = !isMedicineUpdateMode;
        
        if (isMedicineUpdateMode) {
            isRemoveMode = false; // Disable remove mode
            JOptionPane.showMessageDialog(mainPanel, "Click on a medicine row to edit its details", "Edit Mode", JOptionPane.INFORMATION_MESSAGE);
        }
        
        refreshTable();
    }
    
    private void storeOriginalQuantities() {
        originalQuantities.clear();
        currentQuantities.clear();
        
        // FIXED: Get fresh data from database
        Inventory.refreshFromDatabase();
        
        for (String medicine : Inventory.getAllMedicines("Name (A-Z)")) {
            int quantity = Integer.parseInt(getQuantity(medicine));
            originalQuantities.put(medicine, quantity);
            currentQuantities.put(medicine, quantity);
        }
    }
    
    private void refreshTable() {
        // Recreate the table with updated columns and renderers
        scrollableBodyPanel.removeAll();
        
        // Create status legend panel
        JPanel statusPanel = createStatusPanel();
        scrollableBodyPanel.add(statusPanel, BorderLayout.NORTH);
        
        // Create medicine table with current mode settings
        JPanel tablePanel = createTablePanel();
        scrollableBodyPanel.add(tablePanel, BorderLayout.CENTER);
        
        // Create pagination panel
        paginationPanel = createPaginationPanel();
        scrollableBodyPanel.add(paginationPanel, BorderLayout.SOUTH);
        
        // Revalidate and repaint
        scrollableBodyPanel.revalidate();
        scrollableBodyPanel.repaint();
        enableCounterInteraction();
    }
    
 // Add this new method and call it from refreshTable():
    private void enableCounterInteraction() {
        if (!isUpdateMode) return;
        
        // Make sure the table allows selection
        medicineTable.setRowSelectionAllowed(true);
        medicineTable.setColumnSelectionAllowed(true);
        
        // Set focus traversal to make components interactive
        medicineTable.setFocusTraversalKeysEnabled(false);
    }
    
    private JPanel createStatusPanel() {
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        statusPanel.setBackground(Color.WHITE);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        statusPanel.add(createStatusItem("Available", new Color(34, 197, 94)));
        statusPanel.add(createStatusItem("Low Stock", new Color(245, 158, 11)));
        statusPanel.add(createStatusItem("Out of Stock", new Color(107, 114, 128)));
        
        return statusPanel;
    }
    
    private JPanel createStatusItem(String status, Color dotColor) {
        JPanel statusItem = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        statusItem.setBackground(Color.WHITE);
        
        // Create colored dot
        JPanel dot = new JPanel();
        dot.setBackground(dotColor);
        dot.setPreferredSize(new Dimension(8, 8));
        dot.setBorder(BorderFactory.createEmptyBorder());
        
        JLabel statusLabel = new JLabel(status);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(107, 114, 128));
        
        statusItem.add(dot);
        statusItem.add(statusLabel);
        
        return statusItem;
    }
    
    private JPanel createPaginationPanel() {
        JPanel paginationPanel = new JPanel(new BorderLayout());
        paginationPanel.setBackground(Color.WHITE);
        paginationPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        
        // Pagination info
        JLabel paginationInfo = new JLabel("Showing medicines from inventory");
        paginationInfo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        paginationInfo.setForeground(new Color(107, 114, 128));
        paginationPanel.add(paginationInfo, BorderLayout.WEST);
        
        return paginationPanel;
    }
    
    // Updated filterProductPanels method for real-time search - FIXED to search only medicine names
    private void filterProductPanels() {
        if (!isSearchMode) return;
        
        String query = searchField.getText().trim().toLowerCase();
        
        if (query.isEmpty()) {
            tableSorter.setRowFilter(null);
        } else {
            int medicineCol = isRemoveMode ? 1 : 0;
            
            // Create custom filter that only searches medicine names (not the full formatted string)
            RowFilter<DefaultTableModel, Object> rf = new RowFilter<DefaultTableModel, Object>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                    String medicineData = (String) entry.getValue(medicineCol);
                    // Extract only the medicine name (before the newline)
                    String medicineName = medicineData.split("\n")[0].toLowerCase();
                    return medicineName.contains(query);
                }
            };
            tableSorter.setRowFilter(rf);
        }
        
        updatePaginationInfo();
    }
    
    private void updatePaginationInfo() {
        int totalRows = medicineTable.getRowCount();
        JLabel paginationInfo = new JLabel("Showing " + totalRows + " medicines");
        paginationInfo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        paginationInfo.setForeground(new Color(107, 114, 128));
        
        paginationPanel.removeAll();
        paginationPanel.add(paginationInfo, BorderLayout.WEST);
        paginationPanel.revalidate();
        paginationPanel.repaint();
    }

    
    // Helper method to get medicine name from table row
    private String getMedicineNameFromRow(int row) {
        int medicineColumn;

        // If in remove mode OR medicine edit mode, the medicine name is in column 1.
        if (isRemoveMode || isMedicineUpdateMode) {
            medicineColumn = 1;
        } else {
            // Otherwise, it's in column 0.
            medicineColumn = 0;
        }

        String fullText = (String) tableModel.getValueAt(row, medicineColumn);
        // Extract just the medicine name (the part before the newline)
        return fullText.split("\n")[0];
    }
    
    private void updateQuantity(String medicineName, int newQuantity) {
        currentQuantities.put(medicineName, newQuantity);
        
        // Fixed: Smart change detection - check if value is different from original
        Integer originalQty = originalQuantities.get(medicineName);
        boolean quantitiesChanged = false;
        
        for (Map.Entry<String, Integer> entry : currentQuantities.entrySet()) {
            Integer original = originalQuantities.get(entry.getKey());
            if (original == null || !original.equals(entry.getValue())) {
                quantitiesChanged = true;
                break;
            }
        }
        
        // Check checkbox changes too
        boolean anyCheckboxSelected = false;
        if (isRemoveMode) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                Boolean selected = (Boolean) tableModel.getValueAt(i, 0);
                if (selected != null && selected) {
                    anyCheckboxSelected = true;
                    break;
                }
            }
        }
        
        hasChanges = quantitiesChanged || anyCheckboxSelected;
        updateFooterButtonStates();
        
        // Update the table model to reflect the change
        updateTableQuantity(medicineName, newQuantity);
    }
    
    // Helper method to update table quantity display
    private void updateTableQuantity(String medicineName, int newQuantity) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int medicineColumn = isRemoveMode ? 1 : 0;
            String rowMedicineName = ((String) tableModel.getValueAt(i, medicineColumn)).split("\n")[0];
            
            if (rowMedicineName.equals(medicineName)) {
                int quantityColumn = isRemoveMode ? 3 : 2;
                tableModel.setValueAt(String.valueOf(newQuantity), i, quantityColumn);
                break;
            }
        }
    }
    
    // Method to handle checkbox selection in remove mode
    private void handleRemoveSelection() {
        if (!isRemoveMode) return;
        
        java.util.List<String> selectedMedicines = new java.util.ArrayList<>();
        
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Boolean selected = (Boolean) tableModel.getValueAt(i, 0);
            if (selected != null && selected) {
            	String displayName = ((String) tableModel.getValueAt(i, 1)).split("\n")[0];
            	String medicineName = extractBaseMedicineName(displayName);
                selectedMedicines.add(medicineName);
            }
        }
        
        if (!selectedMedicines.isEmpty()) {
            int result = JOptionPane.showConfirmDialog(
                mainPanel,
                "Are you sure you want to remove " + selectedMedicines.size() + " selected medicine(s)?",
                "Confirm Removal",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (result == JOptionPane.YES_OPTION) {
                // Remove selected medicines
                for (String medicine : selectedMedicines) {
                    // Inventory.removeMedicine(medicine); // Implement this method
                }
                
                refreshTable();
                JOptionPane.showMessageDialog(mainPanel, 
                    selectedMedicines.size() + " medicine(s) removed successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void setupTableMouseListener() {
        medicineTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                int row = medicineTable.rowAtPoint(e.getPoint());
                int col = medicineTable.columnAtPoint(e.getPoint());
                
                if (row >= 0) {
                    if (isRemoveMode && col == 0) {
                        // Handle checkbox clicks
                        int modelRow = medicineTable.convertRowIndexToModel(row);
                        Boolean currentValue = (Boolean) tableModel.getValueAt(modelRow, 0);
                        boolean newValue = currentValue == null ? true : !currentValue;
                        tableModel.setValueAt(newValue, modelRow, 0);
                        checkForCheckboxChanges();
                        medicineTable.repaint();
                    } else if (isUpdateMode && col == (isRemoveMode ? 3 : 2)) {
                        // FIXED: Properly start editing the quantity cell
                        if (!medicineTable.isEditing()) {
                            medicineTable.editCellAt(row, col);
                            Component editor = medicineTable.getEditorComponent();
                            if (editor != null) {
                                editor.requestFocusInWindow();
                            }
                        }
                    }
                }
            }
        });
    }
    

    private void checkForCheckboxChanges() {
        boolean anySelected = false;
        if (isRemoveMode) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                Boolean selected = (Boolean) tableModel.getValueAt(i, 0);
                if (selected != null && selected) {
                    anySelected = true;
                    break;
                }
            }
        }
        
        // Check if quantities have changed from original
        boolean quantitiesChanged = false;
        for (Map.Entry<String, Integer> entry : currentQuantities.entrySet()) {
            Integer original = originalQuantities.get(entry.getKey());
            if (original == null || !original.equals(entry.getValue())) {
                quantitiesChanged = true;
                break;
            }
        }
        
        // FIXED: More stable change detection - only update if the state actually changed
        boolean newHasChanges = anySelected || quantitiesChanged;
        if (hasChanges != newHasChanges) {
            hasChanges = newHasChanges;
            // Only update footer if the state actually changed
            SwingUtilities.invokeLater(() -> updateFooterButtonStates());
        }
    }
    
    private void refreshTableWithSorting() {
        // Store current editing state
        boolean wasEditing = medicineTable != null && medicineTable.isEditing();
        if (wasEditing) {
            medicineTable.getCellEditor().stopCellEditing();
        }
        
        // Clear existing data
        if (tableModel != null) {
            tableModel.setRowCount(0);
        }
        
        // Get filtered and sorted medicines using the updated logic
        List<String> filteredMedicines = getFilteredMedicines(currentSortType);
        
        // Populate table with filtered data
        for (String medicine : filteredMedicines) {
            String manufacturer = getManufacturer(medicine);
            String quantity = isUpdateMode && currentQuantities.containsKey(medicine) ? 
                             String.valueOf(currentQuantities.get(medicine)) : getQuantity(medicine);
            
            Object[] rowData;
            if (isRemoveMode) {
                rowData = new Object[]{
                    false, // Checkbox
                    formatMedicineName(medicine),
                    manufacturer,
                    quantity
                };
            } else {
                rowData = new Object[]{
                    formatMedicineName(medicine),
                    manufacturer,
                    quantity
                };
            }
            tableModel.addRow(rowData);
        }
        
        // Reapply search filter if in search mode
        if (isSearchMode && !searchField.getText().trim().isEmpty()) {
            filterProductPanels();
        }
        
        // Update pagination info
        updatePaginationInfo();
        
        // Refresh the display
        if (medicineTable != null) {
            medicineTable.revalidate();
            medicineTable.repaint();
        }
    }
    
    // Add this new method to handle the filtering logic:
    private List<String> getFilteredMedicines(String sortType) {
        List<String> allMedicines = Inventory.getAllMedicines("Name (A-Z)"); // Get all medicines without sorting
        List<String> filteredMedicines = new ArrayList<>();
        
        switch (sortType) {
            case "Name (A-Z)":
                filteredMedicines = new ArrayList<>(allMedicines);
                Collections.sort(filteredMedicines);
                break;
                
            case "Name (Z-A)":
                filteredMedicines = new ArrayList<>(allMedicines);
                Collections.sort(filteredMedicines, Collections.reverseOrder());
                break;
                
            case "Low Stock":
                // Only show medicines with quantity less than 100 (but not 0)
                for (String medicine : allMedicines) {
                    int quantity = Integer.parseInt(getQuantity(medicine));
                    if (quantity > 0 && quantity < 100) {
                        filteredMedicines.add(medicine);
                    }
                }
                // Sort by quantity ascending (lowest first)
                filteredMedicines.sort((m1, m2) -> {
                    int q1 = Integer.parseInt(getQuantity(m1));
                    int q2 = Integer.parseInt(getQuantity(m2));
                    return Integer.compare(q1, q2);
                });
                break;
                
            case "Out of Stock":
                // Only show medicines with quantity exactly 0
                for (String medicine : allMedicines) {
                    int quantity = Integer.parseInt(getQuantity(medicine));
                    if (quantity == 0) {
                        filteredMedicines.add(medicine);
                    }
                }
                // Sort alphabetically
                Collections.sort(filteredMedicines);
                break;
                
            default:
                filteredMedicines = new ArrayList<>(allMedicines);
                Collections.sort(filteredMedicines);
                break;
        }
        
        return filteredMedicines;
    }
    
    private String extractBaseMedicineName(String tableDisplayName) {
        // Strips off everything after the first " (" to get just the medicine name
        int idx = tableDisplayName.indexOf(" (");
        if (idx != -1) {
            return tableDisplayName.substring(0, idx).trim();
        }
        return tableDisplayName.trim();
    }
    
    /*


		|=========================================== GUI and Editors ===========================================|


     */
    
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout()); 
        tablePanel.setBackground(Color.WHITE); 
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(229, 231, 235), 1));

        // Create table model
        String[] columnNames;
        if (isUpdateMode) { 
            if (isRemoveMode) {
                columnNames = new String[]{"", "MEDICINE", "MANUFACTURER", "QUANTITY"}; 
            } else if (isMedicineUpdateMode) {
                columnNames = new String[]{"EDIT", "MEDICINE", "MANUFACTURER", "QUANTITY"}; 
            } else {
                columnNames = new String[]{"MEDICINE", "MANUFACTURER", "QUANTITY"}; 
            }
        } else {
            columnNames = new String[]{"MEDICINE", "MANUFACTURER", "QUANTITY"}; 
        }

        // Initialize table model with data from Inventory
        tableModel = new DefaultTableModel(columnNames, 0) {
            // THIS IS THE CRITICAL FIX
            @Override
            public boolean isCellEditable(int row, int column) {
                // Allow editing for the "Edit" button when in medicine update mode
                if (isMedicineUpdateMode) {
                    return column == 0;
                }
                // Allow editing for the checkbox when in remove mode
                if (isRemoveMode) {
                    return column == 0;
                }
                // Allow editing for the quantity counter in other update modes
                if (isUpdateMode) {
                    return column == 2;
                }
                // Otherwise, cells are not editable
                return false;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                if (isRemoveMode && column == 0) return Boolean.class; 
                return String.class; 
            }
        };
        
        populateTableData(); 

        medicineTable = new JTable(tableModel); 
        medicineTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE); 
        medicineTable.setSurrendersFocusOnKeystroke(true); 

        if (isUpdateMode) { 
            medicineTable.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    int row = medicineTable.rowAtPoint(e.getPoint());
                    int col = medicineTable.columnAtPoint(e.getPoint()); 

                    int quantityCol = isRemoveMode ? 3 : 2; 
                    if (row >= 0 && col == quantityCol && isUpdateMode) {
                        if (medicineTable.isEditing()) {
                            medicineTable.getCellEditor().stopCellEditing();
                        }
                        medicineTable.editCellAt(row, col);
                        Component editor = medicineTable.getEditorComponent();
                        if (editor != null) {
                            editor.requestFocusInWindow(); 
                        }
                    }
                }
            });
        }

        setupTableMouseListener(); 
        tableModel.addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                hasChanges = true; 
                updateFooterButtonStates();
            }
        });
        medicineTable.setFont(new Font("SansSerif", Font.PLAIN, 14)); 
        medicineTable.setRowHeight(isUpdateMode ? 80 : 60);
        medicineTable.setShowGrid(false); 
        medicineTable.setIntercellSpacing(new Dimension(0, 0)); 
        medicineTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
        
        tableSorter = new TableRowSorter<>(tableModel); 
        medicineTable.setRowSorter(tableSorter); 
        
        medicineTable.getTableHeader().setDefaultRenderer(new HeaderRenderer());
        medicineTable.getTableHeader().setPreferredSize(new Dimension(0, 50)); 
        medicineTable.getTableHeader().setReorderingAllowed(false); 
        
        if (isRemoveMode) {
            medicineTable.getColumnModel().getColumn(0).setPreferredWidth(50);
            medicineTable.getColumnModel().getColumn(0).setMaxWidth(70); 
            medicineTable.getColumnModel().getColumn(1).setPreferredWidth(370); 
            medicineTable.getColumnModel().getColumn(2).setPreferredWidth(200); 
            medicineTable.getColumnModel().getColumn(3).setPreferredWidth(150); 
        } else if (isMedicineUpdateMode) {
            medicineTable.getColumnModel().getColumn(0).setPreferredWidth(63);
            medicineTable.getColumnModel().getColumn(0).setMaxWidth(63); 
        	medicineTable.getColumnModel().getColumn(1).setPreferredWidth(370);
            medicineTable.getColumnModel().getColumn(2).setPreferredWidth(200);
            medicineTable.getColumnModel().getColumn(3).setPreferredWidth(100); 
        } else {
            medicineTable.getColumnModel().getColumn(0).setPreferredWidth(400); 
            medicineTable.getColumnModel().getColumn(1).setPreferredWidth(200); 
            medicineTable.getColumnModel().getColumn(2).setPreferredWidth(150); 
        }

        setupCellRenderers(); 
        JScrollPane scrollPane = new JScrollPane(medicineTable); 
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); 
        scrollPane.getViewport().setBackground(Color.WHITE);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel; 
    }
    
    private void setupCellRenderers() {
        if (isRemoveMode) {
            medicineTable.getColumnModel().getColumn(0).setCellRenderer(new CheckboxCellRenderer());
            medicineTable.getColumnModel().getColumn(1).setCellRenderer(new MedicineCellRenderer());
            medicineTable.getColumnModel().getColumn(2).setCellRenderer(new ManufacturerCellRenderer());
            medicineTable.getColumnModel().getColumn(3).setCellRenderer(new QuantityCellRenderer());
        } else if (isMedicineUpdateMode) {
            medicineTable.getColumnModel().getColumn(0).setCellRenderer(new EditButtonCellRenderer());
            medicineTable.getColumnModel().getColumn(0).setCellEditor(new EditButtonCellEditor());
            medicineTable.getColumnModel().getColumn(1).setCellRenderer(new MedicineCellRenderer());
            medicineTable.getColumnModel().getColumn(2).setCellRenderer(new ManufacturerCellRenderer());
            medicineTable.getColumnModel().getColumn(3).setCellRenderer(new QuantityCellRenderer());

        } else {
            medicineTable.getColumnModel().getColumn(0).setCellRenderer(new MedicineCellRenderer());
            medicineTable.getColumnModel().getColumn(1).setCellRenderer(new ManufacturerCellRenderer());
            
            if (isUpdateMode) {
                // FIXED: Set both renderer and editor for interactive quantity column
                medicineTable.getColumnModel().getColumn(2).setCellRenderer(new UpdateQuantityCellRenderer());
                medicineTable.getColumnModel().getColumn(2).setCellEditor(new UpdateQuantityCellEditor());
            } else {
                medicineTable.getColumnModel().getColumn(2).setCellRenderer(new QuantityCellRenderer());
            }
        }
    }
    
    class EditButtonCellRenderer extends JButton implements TableCellRenderer {
        public EditButtonCellRenderer() {
            setText("Edit");
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            // This will ensure the button looks consistent even if the table selection colors change
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            return this;
        }
    }

    class EditButtonCellEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private final JButton button;
        private String medicineName;

        public EditButtonCellEditor() {
            button = new JButton("Edit");
            button.setOpaque(true);
            button.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // When the button is clicked, execute the action and then stop editing
            editMedicineAction(medicineName);
            fireEditingStopped();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            // When editing starts, get the medicine name for the current row
            // The row index here is the view index, we need to convert it to the model index
            int modelRow = table.convertRowIndexToModel(row);
            String displayName = getMedicineNameFromRow(modelRow);
            this.medicineName = extractBaseMedicineName(displayName);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            // The value of the cell after editing. For a button, this can be its text.
            return button.getText();
        }
    }
    
    // Replace the existing CheckboxCellRenderer class with this:
    class CheckboxCellRenderer implements TableCellRenderer {
        private JPanel panel;
        private JCheckBox checkBox;
        
        public CheckboxCellRenderer() {
            panel = new JPanel(new BorderLayout());
            checkBox = new JCheckBox();
            checkBox.setHorizontalAlignment(SwingConstants.CENTER);
            checkBox.setVerticalAlignment(SwingConstants.CENTER);
            checkBox.setBackground(Color.WHITE);
            panel.setBackground(Color.WHITE);
            panel.add(checkBox, BorderLayout.CENTER);
            // Fixed: Add padding to center it properly
            panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            checkBox.setSelected(value != null && (Boolean) value);
            
            Color bgColor = isSelected ? table.getSelectionBackground() : Color.WHITE;
            panel.setBackground(bgColor);
            checkBox.setBackground(bgColor);
            
            return panel;
        }
    }
    
    // Get main panel for external access
    public JPanel getMainPanel() {
        return mainPanel;
    }
    
    // Cleanup method
    public void cleanup() {
        if (hasChanges) {
            int result = JOptionPane.showConfirmDialog(
                mainPanel,
                "You have unsaved changes. Do you want to save them?",
                "Unsaved Changes",
                JOptionPane.YES_NO_CANCEL_OPTION
            );
            
            if (result == JOptionPane.YES_OPTION) {
                saveChanges();
            }
        }
    }
    
    // Method to refresh status counts
    // IMPORTANT: get's the medicine
    private void updateStatusCounts() {
        int available = 0, lowStock = 0, outOfStock = 0;
        
        for (String medicine : Inventory.getAllMedicines("Name (A-Z)")) {
            int quantity = Integer.parseInt(getQuantity(medicine));
            if (quantity == 0) {
                outOfStock++;
            } else if (quantity <= 10) {
                lowStock++;
            } else {
                available++;
            }
        }
    }
    
    class HeaderRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); // 
            label.setBackground(new Color(249, 250, 251)); // 
            label.setForeground(new Color(107, 114, 128)); // 
            label.setFont(new Font("SansSerif", Font.BOLD, 11)); // 
            label.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20)); // 

            // --- Start of Fix ---

            // 1. Default alignment to LEFT for all columns.
            label.setHorizontalAlignment(SwingConstants.LEFT); // 

            // 2. Specifically center the checkbox column header when in Remove Mode.
            if (isRemoveMode && column == 0) { // 
                label.setHorizontalAlignment(SwingConstants.CENTER); // 
            }

            // 3. Dynamically right-align the LAST column (which is always QUANTITY).
            //    This works correctly for all modes (View, Edit, and Remove).
            if (column == table.getColumnCount() - 1) {
                label.setHorizontalAlignment(SwingConstants.RIGHT);
            }

            // --- End of Fix ---
            
            return label; // 
        }
    }
    
    
    class MedicineCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            String[] parts = value.toString().split("\n");
            String html = "<html><div style='line-height: 1.2;'>" +
                         "<div style='font-weight: bold; font-size: 14px; color: #1f2937;'>" + parts[0] + "</div>";
            
            if (parts.length > 1) {
                html += "<div style='font-size: 12px; color: #6b7280; margin-top: 2px;'>" + parts[1] + "</div>";
            }
            
            html += "</div></html>";
            
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, html, isSelected, hasFocus, row, column);
            label.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
            label.setVerticalAlignment(SwingConstants.CENTER);
            
            return label;
        }
    }
    
    class ManufacturerCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setForeground(new Color(59, 130, 246));
            label.setFont(new Font("SansSerif", Font.PLAIN, 14));
            label.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
            
            return label;
        }
    }
    
    class QuantityCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            int quantity = Integer.parseInt(value.toString());
            
            if (quantity == 0) {
                label.setForeground(new Color(107, 114, 128));
            } else if (quantity <= 100) {
                label.setForeground(new Color(245, 158, 11));
            } else {
                label.setForeground(new Color(34, 197, 94));
            }
            
            label.setFont(new Font("SansSerif", Font.BOLD, 16));
            label.setHorizontalAlignment(SwingConstants.RIGHT);
            label.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
            
            return label;
        }
    }
    
    // IMPORTANT
    class UpdateQuantityCellRenderer implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
            panel.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            // Create display-only components for rendering
            JButton minusBtn = new JButton("−");
            minusBtn.setPreferredSize(new Dimension(30, 30));
            minusBtn.setFont(new Font("Arial", Font.BOLD, 16));
            minusBtn.setMargin(new Insets(0, 0, 0, 0));
            minusBtn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            minusBtn.setFocusPainted(false);
            minusBtn.setBackground(Color.WHITE);
            
            JButton plusBtn = new JButton("+");
            plusBtn.setPreferredSize(new Dimension(30, 30));
            plusBtn.setFont(new Font("Arial", Font.BOLD, 16));
            plusBtn.setMargin(new Insets(0, 0, 0, 0));
            plusBtn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            plusBtn.setFocusPainted(false);
            plusBtn.setBackground(Color.WHITE);
            
            JTextField stockField = new JTextField(8);
            stockField.setText(value.toString());
            stockField.setHorizontalAlignment(JTextField.CENTER);
            stockField.setFont(new Font("SansSerif", Font.BOLD, 14));
            stockField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            stockField.setEditable(false); // Display only in renderer
            
            panel.add(minusBtn);
            panel.add(stockField);
            panel.add(plusBtn);
            
            return panel;
        }
    }

    
    class UpdateQuantityCellEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;
        private JButton minusBtn, plusBtn;
        private JTextField stockField;
        private String medicineName;
        private int currentValue;
        private boolean isEditing = false;
        
        public UpdateQuantityCellEditor() {
            panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
            panel.setBackground(Color.WHITE);
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            minusBtn = new JButton("−");
            minusBtn.setPreferredSize(new Dimension(30, 30));
            minusBtn.setFont(new Font("Arial", Font.BOLD, 16));
            minusBtn.setMargin(new Insets(0, 0, 0, 0));
            minusBtn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            minusBtn.setFocusPainted(false);
            minusBtn.setBackground(Color.WHITE);
            
            plusBtn = new JButton("+");
            plusBtn.setPreferredSize(new Dimension(30, 30));
            plusBtn.setFont(new Font("Arial", Font.BOLD, 16));
            plusBtn.setMargin(new Insets(0, 0, 0, 0));
            plusBtn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            plusBtn.setFocusPainted(false);
            plusBtn.setBackground(Color.WHITE);
            
            stockField = new JTextField(8);
            stockField.setHorizontalAlignment(JTextField.CENTER);
            stockField.setFont(new Font("SansSerif", Font.BOLD, 14));
            stockField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            
            // FIXED: Proper event handling with immediate response
            minusBtn.addActionListener(e -> {
                if (isEditing && currentValue > 0) {
                    currentValue--;
                    stockField.setText(String.valueOf(currentValue));
                    updateQuantityImmediately();
                }
            });
            
            plusBtn.addActionListener(e -> {
                if (isEditing) {
                    currentValue++;
                    stockField.setText(String.valueOf(currentValue));
                    updateQuantityImmediately();
                }
            });
            
            // FIXED: Handle direct text input
            stockField.addActionListener(e -> {
                if (isEditing) {
                    validateAndUpdateFromTextField();
                }
            });
            
            // FIXED: Handle Enter key and focus loss
            stockField.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyPressed(java.awt.event.KeyEvent e) {
                    if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER && isEditing) {
                        validateAndUpdateFromTextField();
                        fireEditingStopped();
                    }
                }
            });
            
            stockField.addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    if (isEditing) {
                        validateAndUpdateFromTextField();
                    }
                }
            });
            
            panel.add(minusBtn);
            panel.add(stockField);
            panel.add(plusBtn);
        }
        
        private void validateAndUpdateFromTextField() {
            try {
                int newValue = Integer.parseInt(stockField.getText().trim());
                if (newValue >= 0) {
                    currentValue = newValue;
                    updateQuantityImmediately();
                } else {
                    stockField.setText(String.valueOf(currentValue));
                }
            } catch (NumberFormatException ex) {
                stockField.setText(String.valueOf(currentValue));
            }
        }
        
        private void updateQuantityImmediately() {
            updateQuantity(medicineName, currentValue);
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            
            // Get the medicine name for this row
        	String displayName = getMedicineNameFromRow(row);
        	medicineName = extractBaseMedicineName(displayName);
            currentValue = Integer.parseInt(value.toString());
            stockField.setText(String.valueOf(currentValue));
            isEditing = true;
            
            // FIXED: Ensure components are focusable and enabled
            // IMPORTANT 5
            minusBtn.setEnabled(true);
            plusBtn.setEnabled(true);
            stockField.setEditable(true);
            
            // Request focus after a short delay to ensure proper setup
            SwingUtilities.invokeLater(() -> {
                stockField.requestFocusInWindow();
            });
            
            return panel;
        }
        
        @Override
        public Object getCellEditorValue() {
            return String.valueOf(currentValue);
        }
        
        @Override
        public boolean stopCellEditing() {
            isEditing = false;
            return super.stopCellEditing();
        }
        
        @Override
        public void cancelCellEditing() {
            isEditing = false;
            super.cancelCellEditing();
        }
    }
    
    
    /*


		|=========================================== Print Function ===========================================|


     */
    
    private void printMode() {
        // 2. REMEMBER LAST DIRECTORY: Use the static variable for the initial path
        JFileChooser fileChooser = new JFileChooser(lastUsedDirectory);
        fileChooser.setDialogTitle("Save As");
        fileChooser.setSelectedFile(new File("InventoryReport.docx"));
        int userSelection = fileChooser.showSaveDialog(mainPanel);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File outputFile = fileChooser.getSelectedFile();
        // Ensure the file has a .docx extension
        if (!outputFile.getName().toLowerCase().endsWith(".docx")) {
            outputFile = new File(outputFile.getParentFile(), outputFile.getName() + ".docx");
        }

        // 2. REMEMBER LAST DIRECTORY: Update the path after a successful save
        lastUsedDirectory = outputFile.getParent();

        try {
            FileInputStream fis = new FileInputStream("medicineInventoryDocx.docx");
            XWPFDocument document = new XWPFDocument(fis);
            fis.close();

            XWPFTable table = document.getTables().get(0);
            XWPFTableRow templateRow = table.getRow(1);

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String medicineData = (String) tableModel.getValueAt(i, isRemoveMode ? 1 : 0);
                String medicineName = medicineData.split("\n")[0];
                String quantity = (String) tableModel.getValueAt(i, isRemoveMode ? 3 : 2);

                XWPFTableRow newRow = table.createRow();
                newRow.getCtRow().setTrPr(templateRow.getCtRow().getTrPr());

                for (int j = 0; j < templateRow.getTableCells().size(); j++) {
                    XWPFTableCell templateCell = templateRow.getCell(j);
                    XWPFTableCell newCell = newRow.getCell(j);
                 // 1. Clear the cell's default content
                    while (newCell.getParagraphs().size() > 0) {
                        newCell.removeParagraph(0);
                    }

                    // 2. Create a new paragraph and set its properties
                    XWPFParagraph paragraph = newCell.addParagraph();
                    paragraph.setSpacingBetween(1.0); // Sets single line spacing

                    // 3. Create a "run" to hold the text and its style
                    XWPFRun run = paragraph.createRun();
                    run.setFontFamily("Calibri"); // Sets the font
                    run.setFontSize(12);          // Sets the font size

                    // 4. Determine the text and apply conditional color
                    String cellText = "";
                    switch (j) {
                        case 0: // Medicine Column
                            cellText = medicineName;
                            run.setText(cellText); // Apply the text to the run
                            break;

                        case 1: // Quantity Column
                            cellText = quantity;
                            int quantityValue = Integer.parseInt(cellText);

                            // Apply color based on the quantity's value
                            if (quantityValue == 0) {
                                run.setColor("FF0000"); // Red hex code
                            } else if (quantityValue < 100) {
                                run.setColor("FFA500"); // Orange hex code
                            } else {
                                run.setColor("008000"); // Green hex code
                            }

                            run.setText(cellText); // Apply the text to the run
                            break;
                    }
                }
            }

            table.removeRow(1);

            FileOutputStream fos = new FileOutputStream(outputFile);
            document.write(fos);
            fos.close();
            document.close();

            JOptionPane.showMessageDialog(mainPanel, "Inventory report saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
        	String messageBuilder = "ERROR: " + e.getMessage() + "\nMake sure 'medicineInventoryDocx.docx' exists in your project folder!";
        	JOptionPane.showMessageDialog(null, messageBuilder, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}