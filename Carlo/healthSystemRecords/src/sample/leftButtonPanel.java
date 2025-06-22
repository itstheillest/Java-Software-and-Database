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

public class leftButtonPanel {
    private JPanel mainPanel;
    private JButton returnButton, updateButton, addButton, removeButton, backButton, searchButton;
    private JComboBox medicineSorting;
    private String currentSortType = "Name (A-Z)"; // Track current sorting
    
    private JTextField searchField;
    private JPanel scrollableBodyPanel;
    private JPanel headerPanel;
    private JPanel searchPanel;
    
    private JPanel footerPanel;
    private JButton cancelButton;
    private JButton confirmButton;
    private JButton undoButton;
    private JButton saveButton;
    
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
    }

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
        
        

        // Update button (Right) - will be replaced with panel when in update mode
        updateButton = new JButton("Update");
        updateButton.addActionListener(e -> enterUpdateMode());
        headerPanel.add(updateButton, BorderLayout.EAST);
        
        // Create update buttons panel (initially hidden)
        createUpdateButtonsPanel();
    }
    
    private void createUpdateButtonsPanel() {
        updateButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        updateButtonsPanel.setOpaque(false);
        
        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        backButton = new JButton("Back");
        
        addButton.addActionListener(e -> addButtonAction());
        removeButton.addActionListener(e -> removeButtonAction());
        backButton.addActionListener(e -> exitUpdateMode());
        
        updateButtonsPanel.add(addButton);
        updateButtonsPanel.add(removeButton);
        updateButtonsPanel.add(backButton);
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
    
    private void enterUpdateMode() {
        isUpdateMode = true;
        
        // Replace update button with update buttons panel
        headerPanel.remove(updateButton);
        headerPanel.add(updateButtonsPanel, BorderLayout.EAST);
        
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
        
        // Replace update buttons panel with update button
        headerPanel.remove(updateButtonsPanel);
        headerPanel.add(updateButton, BorderLayout.EAST);
        
        // Hide footer buttons
        hideFooterButtons();
        
        // Clear changes tracking
        hasChanges = false;
        originalQuantities.clear();
        currentQuantities.clear();
        
        // Refresh the table to hide counter controls and checkboxes
        refreshTable();
        
        headerPanel.revalidate();
        headerPanel.repaint();
    }
    
    private void storeOriginalQuantities() {
        originalQuantities.clear();
        currentQuantities.clear();
        
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
    
    private void setUpFooter() {
        footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        footerPanel.setBackground(new Color(245, 245, 245)); // light gray for distinction

        undoButton = new JButton("Undo");
        cancelButton = new JButton("Cancel");
        saveButton = new JButton("Save");

        undoButton.addActionListener(e -> undoChanges());
        cancelButton.addActionListener(e -> exitUpdateMode());
        saveButton.addActionListener(e -> saveChanges());

        footerPanel.add(undoButton);
        footerPanel.add(cancelButton);
        footerPanel.add(saveButton);

        // Keep panel visible, but buttons hidden by default
        undoButton.setVisible(false);
        cancelButton.setVisible(false);
        saveButton.setVisible(false);
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
        
        int result = JOptionPane.showConfirmDialog(
            mainPanel,
            "Save changes?",
            "Confirm Save",
            JOptionPane.YES_NO_OPTION
        );
        
        if (result == JOptionPane.YES_OPTION) {
            // Apply changes to inventory
            for (Map.Entry<String, Integer> entry : currentQuantities.entrySet()) {
                // You'll need to implement this method in your Inventory class
                // Inventory.setStock(entry.getKey(), entry.getValue());
            }
            
            JOptionPane.showMessageDialog(mainPanel, "Changes saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            exitUpdateMode();
        }
    }
    
 // Replace the existing updateFooterButtonStates() method with this:
 // Replace the existing updateFooterButtonStates() method with this:
    private void updateFooterButtonStates() {
        // Always keep buttons enabled
        undoButton.setEnabled(true);
        saveButton.setEnabled(true);
        
        // Always keep buttons highlighted
        undoButton.setBackground(new Color(59, 130, 246)); // Blue
        undoButton.setForeground(Color.WHITE);
        saveButton.setBackground(new Color(34, 197, 94)); // Green
        saveButton.setForeground(Color.WHITE);
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
    
    
    // IMPORTANT!!!!
    // To Do
    private JPanel createStatusPanel() {
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        statusPanel.setBackground(Color.WHITE);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        statusPanel.add(createStatusItem("27", "Available", new Color(34, 197, 94)));
        statusPanel.add(createStatusItem("9", "Low Stock", new Color(245, 158, 11)));
        statusPanel.add(createStatusItem("9", "Out of Stock", new Color(107, 114, 128)));
        
        return statusPanel;
    }
    
    private JPanel createStatusItem(String count, String status, Color dotColor) {
        JPanel statusItem = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        statusItem.setBackground(Color.WHITE);
        
        // Create colored dot
        JPanel dot = new JPanel();
        dot.setBackground(dotColor);
        dot.setPreferredSize(new Dimension(8, 8));
        dot.setBorder(BorderFactory.createEmptyBorder());
        
        JLabel countLabel = new JLabel(count);
        countLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        countLabel.setForeground(Color.BLACK);
        
        JLabel statusLabel = new JLabel(status);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(107, 114, 128));
        
        statusItem.add(dot);
        statusItem.add(countLabel);
        statusItem.add(statusLabel);
        
        return statusItem;
    }
    
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(229, 231, 235), 1));
        
        // Create table model
        String[] columnNames;
        if (isUpdateMode) {
            if (isRemoveMode) {
                columnNames = new String[]{"", "MEDICINE", "MANUFACTURER", "QUANTITY"};
            } else {
                columnNames = new String[]{"MEDICINE", "MANUFACTURER", "QUANTITY"};
            }
        } else {
            columnNames = new String[]{"MEDICINE", "MANUFACTURER", "QUANTITY"};
        }
        
        // Initialize table model with data from Inventory
        tableModel = new DefaultTableModel(columnNames, 0) {
        	@Override
        	public boolean isCellEditable(int row, int column) {
        	    if (isRemoveMode && column == 0) return true; // Checkbox column
        	    if (isUpdateMode && column == (isRemoveMode ? 3 : 2)) return true; // Quantity column in update mode
        	    return false;
        	}
            
            @Override
            public Class<?> getColumnClass(int column) {
                if (isRemoveMode && column == 0) return Boolean.class;
                return String.class;
            }
        };
        
        // Populate table with inventory data
        populateTableData();
        
        medicineTable = new JTable(tableModel);
     // FIXED: Enable single-click editing and proper cell selection
        medicineTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        medicineTable.setSurrendersFocusOnKeystroke(true);
        
     // Add this RIGHT AFTER medicineTable = new JTable(tableModel); in createTablePanel():

     // Fixed: Make table cells clickable and editable for update mode
        if (isUpdateMode) {
            medicineTable.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    int row = medicineTable.rowAtPoint(e.getPoint());
                    int col = medicineTable.columnAtPoint(e.getPoint());
                    
                    // Allow interaction with quantity column in update mode
                    int quantityCol = isRemoveMode ? 3 : 2;
                    if (row >= 0 && col == quantityCol && isUpdateMode) {
                        // Stop any current editing
                        if (medicineTable.isEditing()) {
                            medicineTable.getCellEditor().stopCellEditing();
                        }
                        
                        // Start editing the clicked cell
                        medicineTable.editCellAt(row, col);
                        Component editor = medicineTable.getEditorComponent();
                        if (editor != null) {
                            editor.requestFocusInWindow();
                        }
                    }
                }
            });
        }
        
        // Add this right after: medicineTable = new JTable(tableModel);
        // Setup table mouse listener for checkbox handling
        setupTableMouseListener();

        // Add table model listener to track changes
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
        
        // Set up table sorter for filtering
        tableSorter = new TableRowSorter<>(tableModel);
        medicineTable.setRowSorter(tableSorter);
        
        // Custom header renderer
        medicineTable.getTableHeader().setDefaultRenderer(new HeaderRenderer());
        medicineTable.getTableHeader().setPreferredSize(new Dimension(0, 50));
        medicineTable.getTableHeader().setReorderingAllowed(false);
        
        // Set column widths
        if (isRemoveMode) {
            medicineTable.getColumnModel().getColumn(0).setPreferredWidth(30);  // Checkbox - smaller width
            medicineTable.getColumnModel().getColumn(0).setMaxWidth(50);       // Limit max width
            medicineTable.getColumnModel().getColumn(1).setPreferredWidth(370); // Medicine
            medicineTable.getColumnModel().getColumn(2).setPreferredWidth(200); // Manufacturer
            medicineTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Quantity
        } else {
            medicineTable.getColumnModel().getColumn(0).setPreferredWidth(400); // Medicine
            medicineTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Manufacturer
            medicineTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Quantity
        }
        

        
        // Custom cell renderers
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
    
    
    // IMPORTANT 2
    // To do: Connect to DB
    private void populateTableData() {
        // Clear existing data
        tableModel.setRowCount(0);
        
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
        return medicine + "\n" + getMedicineType(medicine);
    }
    
    private String getManufacturer(String medicine) {
        // Implement this method based on your Inventory class
        return "Generic"; // Replace with actual implementation
    }
    
    private String getQuantity(String medicine) {
        // Implement this method based on your Inventory class
        int stock = Inventory.getStock(medicine);
        String str = String.valueOf(stock);
        return str; // Replace with actual implementation
    }
    
    private String getMedicineType(String medicine) {
        // Implement this method based on your Inventory class
    	String str = "Pain Relief (500mg)";
        return str; // Replace with actual implementation
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
    
    // IMPORTANT: Add Button Action
    public void addButtonAction() {
        // Create input dialog for new medicine
    	inventory = new Inventory();
    	for (;;) {
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
            panel.add(new JLabel("Dosage:"));
            panel.add(dosageField);
            panel.add(new JLabel("Brand:"));
            panel.add(brandField);
            panel.add(new JLabel("Stock/Quantity:"));
            panel.add(stockField);
            
            int result = JOptionPane.showConfirmDialog(
                mainPanel,
                panel,
                "Add New Medicine",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );
            
            System.out.println(result);
            
            if (result == -1 || result == 2) {
            	break;
            }
            
            // IMPORTANT
            // Adfd the database here
            
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
        
        if (isRemoveMode) {
            removeButton.setText("Cancel Remove");
        } else {
            removeButton.setText("Remove");
        }
        
        refreshTable();
    }

    public void returnButtonAction() {
        mainApp.restoreMainButtons();
    }
    
    private void showFooterButtons() {
        undoButton.setVisible(true);
        cancelButton.setVisible(true);
        saveButton.setVisible(true);
        updateFooterButtonStates();
    }

    private void hideFooterButtons() {
        undoButton.setVisible(false);
        cancelButton.setVisible(false);
        saveButton.setVisible(false);
    }
    
    // Custom renderers
    class HeaderRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setBackground(new Color(249, 250, 251));
            label.setForeground(new Color(107, 114, 128));
            label.setFont(new Font("SansSerif", Font.BOLD, 11));
            label.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
            
            // Adjust alignment for different columns
            if (isRemoveMode) {
                if (column == 0) {
                    label.setHorizontalAlignment(SwingConstants.CENTER); // Checkbox column
                } else {
                    label.setHorizontalAlignment(column == 3 ? SwingConstants.RIGHT : SwingConstants.LEFT);
                }
            } else {
                label.setHorizontalAlignment(column == 2 ? SwingConstants.RIGHT : SwingConstants.LEFT);
            }
            
            return label;
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
    
 // 3. NEW: Add custom cell editor for quantity column
 // 3. FIXED: Replace your UpdateQuantityCellEditor class with this improved version:
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
            // Don't call fireEditingStopped() here to keep editing active
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            
            // Get the medicine name for this row
            medicineName = getMedicineNameFromRow(row);
            currentValue = Integer.parseInt(value.toString());
            stockField.setText(String.valueOf(currentValue));
            isEditing = true;
            
            // FIXED: Ensure components are focusable and enabled
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
    
    // Helper method to get medicine name from table row
    private String getMedicineNameFromRow(int row) {
        int medicineColumn = isRemoveMode ? 1 : 0;
        String fullText = (String) tableModel.getValueAt(row, medicineColumn);
        // Extract medicine name (before the newline)
        return fullText.split("\n")[0];
    }
    
    // Helper method to update quantity and track changes
 // Replace the existing updateQuantity() method with this:
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
                String medicineName = ((String) tableModel.getValueAt(i, 1)).split("\n")[0];
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
    
    // Add table mouse listener for checkbox handling
    // Add table mouse listener for checkbox handling
    // Replace the existing setupTableMouseListener() method with this:
 // 2. FIXED: Replace your setupTableMouseListener method with this:
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
    
    // Add this new method after setupTableMouseListener():
    // 1. FIXED: More precise change detection
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
        
        // Update status panel with new counts
        // This would require modifying createStatusPanel to accept parameters
        // For now, the counts are hardcoded in createStatusPanel
    }
}