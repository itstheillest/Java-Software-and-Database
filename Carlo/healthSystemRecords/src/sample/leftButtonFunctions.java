package sample;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class leftButtonFunctions {
    private final leftButtonPanelGUI gui;
    private final Main mainApp;
    private Inventory inventory;

    private String currentSortType = "Name (A-Z)";
    private Map<String, Integer> originalQuantities = new HashMap<>();
    private Map<String, Integer> currentQuantities = new HashMap<>();
    private boolean hasChanges = false;

    public leftButtonFunctions(Main mainApp) {
        this.mainApp = mainApp;
        this.gui = new leftButtonPanelGUI();
        this.inventory = new Inventory();
        
        gui.mainPanel = new JPanel(new BorderLayout());

        setUpHeader();
        setUpBody();
        setUpFooter();

        gui.mainPanel.add(gui.headerPanel, BorderLayout.NORTH);
        gui.mainPanel.add(new JScrollPane(gui.scrollableBodyPanel), BorderLayout.CENTER);
        gui.mainPanel.add(gui.footerPanel, BorderLayout.SOUTH);
    	
        Inventory.loadMedicinesFromDatabase();
        enterUpdateMode();
    }

    private void setUpHeader() {
        gui.headerPanel = new JPanel(new BorderLayout());
        gui.headerPanel.setBackground(new Color(85, 107, 47)); // Olive Green
        gui.headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        gui.returnButton = new JButton("Return");
        gui.returnButton.addActionListener(e -> returnButtonAction());
        gui.headerPanel.add(gui.returnButton, BorderLayout.WEST);

        gui.searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        gui.searchPanel.setOpaque(false);
        gui.searchButton = new JButton("Search");
        gui.searchField = new JTextField(15);
        gui.searchField.setText("Click Search");
        gui.searchField.setForeground(Color.GRAY);
        gui.searchField.setEditable(false);

        gui.searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                if (gui.isSearchMode) filterProductPanels();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                if (gui.isSearchMode) filterProductPanels();
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                if (gui.isSearchMode) filterProductPanels();
            }
        });

        gui.medicineSorting = new JComboBox<>(new String[]{"Name (A-Z)", "Name (Z-A)", "Low Stock", "Out of Stock"});
        gui.medicineSorting.addActionListener(e -> {
            currentSortType = (String) gui.medicineSorting.getSelectedItem();
            refreshTableWithSorting();
        });

        gui.searchButton.addActionListener(e -> toggleSearchMode());
        gui.searchPanel.add(gui.searchButton);
        gui.searchPanel.add(gui.searchField);
        gui.searchPanel.add(gui.medicineSorting);
        gui.headerPanel.add(gui.searchPanel, BorderLayout.CENTER);
        
        createUpdateButtonsPanel();
    }
    
    private void createUpdateButtonsPanel() {
        gui.updateButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        gui.updateButtonsPanel.setOpaque(false);
        
        gui.addButton = new JButton("Add");
        gui.removeButton = new JButton("Remove");
        gui.editMedicineButton = new JButton("Edit");

        gui.addButton.addActionListener(e -> addButtonAction());
        gui.removeButton.addActionListener(e -> removeButtonAction());
        gui.editMedicineButton.addActionListener(e -> editMedicineAction());
        
        gui.updateButtonsPanel.add(gui.addButton);
        gui.updateButtonsPanel.add(gui.removeButton);
        gui.updateButtonsPanel.add(gui.editMedicineButton);
    }

    private void setUpBody() {
        gui.scrollableBodyPanel = new JPanel(new BorderLayout());
        gui.scrollableBodyPanel.setBackground(Color.WHITE);
        gui.scrollableBodyPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel statusPanel = createStatusPanel();
        gui.scrollableBodyPanel.add(statusPanel, BorderLayout.NORTH);

        JPanel tablePanel = createTablePanel();
        gui.scrollableBodyPanel.add(tablePanel, BorderLayout.CENTER);

        gui.paginationPanel = createPaginationPanel();
        gui.scrollableBodyPanel.add(gui.paginationPanel, BorderLayout.SOUTH);
    }
    
    private void setUpFooter() {
        gui.editModeButton = new JButton("Edit Mode");
        gui.editModeButton.addActionListener(e -> enterUpdateMode());
        
        gui.footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        gui.footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        gui.footerPanel.setBackground(new Color(245, 245, 245));

        gui.undoButton = new JButton("Undo Quantity");
        gui.viewButton = new JButton("View Mode");
        gui.saveButton = new JButton("Save");

        gui.undoButton.addActionListener(e -> undoChanges());
        gui.viewButton.addActionListener(e -> exitUpdateMode());
        gui.saveButton.addActionListener(e -> saveChanges());

        gui.footerPanel.add(gui.undoButton);
        gui.footerPanel.add(gui.viewButton);
        gui.footerPanel.add(gui.saveButton);
        gui.footerPanel.add(gui.editModeButton);

        gui.undoButton.setVisible(false);
        gui.viewButton.setVisible(false);
        gui.saveButton.setVisible(false);
        gui.editModeButton.setVisible(false);
    }
    
    private JPanel createStatusPanel() {
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        statusPanel.setBackground(Color.WHITE);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // These values can be updated dynamically
        statusPanel.add(createStatusItem("0", "Available", new Color(34, 197, 94)));
        statusPanel.add(createStatusItem("0", "Low Stock", new Color(245, 158, 11)));
        statusPanel.add(createStatusItem("0", "Out of Stock", new Color(107, 114, 128)));
        
        return statusPanel;
    }
    
    private JPanel createStatusItem(String count, String status, Color dotColor) {
        JPanel statusItem = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        statusItem.setBackground(Color.WHITE);
        
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
    
        String[] columnNames;
        if (gui.isUpdateMode) {
            if (gui.isRemoveMode) {
                columnNames = new String[]{"", "MEDICINE", "MANUFACTURER", "QUANTITY"};
            } else if (gui.isMedicineUpdateMode) {
                columnNames = new String[]{"EDIT", "MEDICINE", "MANUFACTURER", "QUANTITY"};
            } else {
                columnNames = new String[]{"MEDICINE", "MANUFACTURER", "QUANTITY"};
            }
        } else {
            columnNames = new String[]{"MEDICINE", "MANUFACTURER", "QUANTITY"};
        }
    
        gui.tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (gui.isMedicineUpdateMode) {
                    return column == 0;
                }
                if (gui.isRemoveMode) {
                    return column == 0;
                }
                if (gui.isUpdateMode) {
                    return column == (gui.isRemoveMode ? 3 : 2);
                }
                return false;
            }
    
            @Override
            public Class<?> getColumnClass(int column) {
                if (gui.isRemoveMode && column == 0) return Boolean.class;
                return String.class;
            }
        };
    
        populateTableData();
    
        gui.medicineTable = new JTable(gui.tableModel);
        gui.medicineTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        gui.medicineTable.setSurrendersFocusOnKeystroke(true);
        
        if (gui.isUpdateMode) {
            gui.medicineTable.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    int row = gui.medicineTable.rowAtPoint(e.getPoint());
                    int col = gui.medicineTable.columnAtPoint(e.getPoint());
                    int quantityCol = gui.isRemoveMode ? 3 : 2;
                    if (row >= 0 && col == quantityCol && gui.isUpdateMode) {
                        if (gui.medicineTable.isEditing()) {
                            gui.medicineTable.getCellEditor().stopCellEditing();
                        }
                        gui.medicineTable.editCellAt(row, col);
                        Component editor = gui.medicineTable.getEditorComponent();
                        if (editor != null) {
                            editor.requestFocusInWindow();
                        }
                    }
                }
            });
        }
    
        setupTableMouseListener();
        gui.tableModel.addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                hasChanges = true;
                updateFooterButtonStates();
            }
        });
    
        gui.medicineTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gui.medicineTable.setRowHeight(gui.isUpdateMode ? 80 : 60);
        gui.medicineTable.setShowGrid(false);
        gui.medicineTable.setIntercellSpacing(new Dimension(0, 0));
        gui.medicineTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
        gui.tableSorter = new TableRowSorter<>(gui.tableModel);
        gui.medicineTable.setRowSorter(gui.tableSorter);
        gui.medicineTable.getTableHeader().setDefaultRenderer(gui.new HeaderRenderer());
        gui.medicineTable.getTableHeader().setPreferredSize(new Dimension(0, 50));
        gui.medicineTable.getTableHeader().setReorderingAllowed(false);
    
        if (gui.isRemoveMode) {
            gui.medicineTable.getColumnModel().getColumn(0).setPreferredWidth(50);
            gui.medicineTable.getColumnModel().getColumn(0).setMaxWidth(70);
            gui.medicineTable.getColumnModel().getColumn(1).setPreferredWidth(370);
            gui.medicineTable.getColumnModel().getColumn(2).setPreferredWidth(200);
            gui.medicineTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        } else if (gui.isMedicineUpdateMode) {
            gui.medicineTable.getColumnModel().getColumn(0).setPreferredWidth(63);
            gui.medicineTable.getColumnModel().getColumn(0).setMaxWidth(63);
            gui.medicineTable.getColumnModel().getColumn(1).setPreferredWidth(370);
            gui.medicineTable.getColumnModel().getColumn(2).setPreferredWidth(200);
            gui.medicineTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        } else {
            gui.medicineTable.getColumnModel().getColumn(0).setPreferredWidth(400);
            gui.medicineTable.getColumnModel().getColumn(1).setPreferredWidth(200);
            gui.medicineTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        }
    
        setupCellRenderers();
        JScrollPane scrollPane = new JScrollPane(gui.medicineTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
    
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    private void setupCellRenderers() {
        if (gui.isRemoveMode) {
            gui.medicineTable.getColumnModel().getColumn(0).setCellRenderer(gui.new CheckboxCellRenderer());
            gui.medicineTable.getColumnModel().getColumn(1).setCellRenderer(gui.new MedicineCellRenderer());
            gui.medicineTable.getColumnModel().getColumn(2).setCellRenderer(gui.new ManufacturerCellRenderer());
            gui.medicineTable.getColumnModel().getColumn(3).setCellRenderer(gui.new QuantityCellRenderer());
        } else if (gui.isMedicineUpdateMode) {
            gui.medicineTable.getColumnModel().getColumn(0).setCellRenderer(gui.new EditButtonCellRenderer());
            gui.medicineTable.getColumnModel().getColumn(0).setCellEditor(gui.new EditButtonCellEditor(this));
            gui.medicineTable.getColumnModel().getColumn(1).setCellRenderer(gui.new MedicineCellRenderer());
            gui.medicineTable.getColumnModel().getColumn(2).setCellRenderer(gui.new ManufacturerCellRenderer());
            gui.medicineTable.getColumnModel().getColumn(3).setCellRenderer(gui.new QuantityCellRenderer());
        } else {
            gui.medicineTable.getColumnModel().getColumn(0).setCellRenderer(gui.new MedicineCellRenderer());
            gui.medicineTable.getColumnModel().getColumn(1).setCellRenderer(gui.new ManufacturerCellRenderer());
            if (gui.isUpdateMode) {
                gui.medicineTable.getColumnModel().getColumn(2).setCellRenderer(gui.new UpdateQuantityCellRenderer());
                gui.medicineTable.getColumnModel().getColumn(2).setCellEditor(gui.new UpdateQuantityCellEditor(this));
            } else {
                gui.medicineTable.getColumnModel().getColumn(2).setCellRenderer(gui.new QuantityCellRenderer());
            }
        }
    }

    private void populateTableData() {
        gui.tableModel.setRowCount(0);
        Inventory.refreshFromDatabase();
        List<String> filteredMedicines = getFilteredMedicines(currentSortType);
        for (String medicine : filteredMedicines) {
            String manufacturer = getManufacturer(medicine);
            String quantity = gui.isUpdateMode && currentQuantities.containsKey(medicine) ?
                    String.valueOf(currentQuantities.get(medicine)) : getQuantity(medicine);
            
            Object[] rowData;
            if (gui.isRemoveMode) {
                rowData = new Object[]{
                    false,
                    formatMedicineName(medicine),
                    manufacturer,
                    quantity
                };
            } else if (gui.isMedicineUpdateMode) {
                rowData = new Object[]{
                        "Edit",
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
            gui.tableModel.addRow(rowData);
        }
    }
    
    private void refreshTableWithSorting() {
        boolean wasEditing = gui.medicineTable != null && gui.medicineTable.isEditing();
        if (wasEditing) {
            gui.medicineTable.getCellEditor().stopCellEditing();
        }
        
        if (gui.tableModel != null) {
            gui.tableModel.setRowCount(0);
        }
        
        List<String> filteredMedicines = getFilteredMedicines(currentSortType);
        for (String medicine : filteredMedicines) {
            String manufacturer = getManufacturer(medicine);
            String quantity = gui.isUpdateMode && currentQuantities.containsKey(medicine) ?
                             String.valueOf(currentQuantities.get(medicine)) : getQuantity(medicine);
            
            Object[] rowData;
            if (gui.isRemoveMode) {
                rowData = new Object[]{
                    false,
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
            gui.tableModel.addRow(rowData);
        }
        
        if (gui.isSearchMode && !gui.searchField.getText().trim().isEmpty()) {
            filterProductPanels();
        }
        
        updatePaginationInfo();
        if (gui.medicineTable != null) {
            gui.medicineTable.revalidate();
            gui.medicineTable.repaint();
        }
    }

    public List<String> getFilteredMedicines(String sortType) {
        List<String> allMedicines = Inventory.getAllMedicines("Name (A-Z)");
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
                for (String medicine : allMedicines) {
                    int quantity = Integer.parseInt(getQuantity(medicine));
                    if (quantity > 0 && quantity < 100) {
                        filteredMedicines.add(medicine);
                    }
                }
                filteredMedicines.sort((m1, m2) -> {
                    int q1 = Integer.parseInt(getQuantity(m1));
                    int q2 = Integer.parseInt(getQuantity(m2));
                    return Integer.compare(q1, q2);
                });
                break;
            case "Out of Stock":
                for (String medicine : allMedicines) {
                    int quantity = Integer.parseInt(getQuantity(medicine));
                    if (quantity == 0) {
                        filteredMedicines.add(medicine);
                    }
                }
                Collections.sort(filteredMedicines);
                break;
            default:
                filteredMedicines = new ArrayList<>(allMedicines);
                Collections.sort(filteredMedicines);
                break;
        }
        return filteredMedicines;
    }

    public void toggleSearchMode() {
        gui.isSearchMode = !gui.isSearchMode;
        if (gui.isSearchMode) {
            gui.searchField.setEditable(true);
            gui.searchField.setText("");
            gui.searchField.setForeground(Color.BLACK);
            gui.searchField.requestFocus();
        } else {
            gui.searchField.setEditable(false);
            gui.searchField.setText("Click Search");
            gui.searchField.setForeground(Color.GRAY);
            gui.tableSorter.setRowFilter(null);
        }
    }

    public void enterUpdateMode() {
        gui.isUpdateMode = true;
        gui.isRemoveMode = true; 
        gui.isMedicineUpdateMode = false;
        
        gui.headerPanel.remove(gui.editModeButton);
        gui.headerPanel.add(gui.updateButtonsPanel, BorderLayout.EAST);
        removeButtonAction();
        
        showFooterButtons();
        storeOriginalQuantities();
        refreshTable();
        
        gui.headerPanel.revalidate();
        gui.headerPanel.repaint();
    }

    public void exitUpdateMode() {
        gui.isUpdateMode = false;
        gui.isRemoveMode = false;
        gui.isMedicineUpdateMode = false;
        
        gui.headerPanel.remove(gui.updateButtonsPanel);
        gui.footerPanel.add(gui.editModeButton, BorderLayout.EAST);
        
        hideFooterButtons();
        hasChanges = false;
        originalQuantities.clear();
        currentQuantities.clear();
        refreshTable();
        
        gui.headerPanel.revalidate();
        gui.headerPanel.repaint();
    }

    private void storeOriginalQuantities() {
        originalQuantities.clear();
        currentQuantities.clear();
        
        Inventory.refreshFromDatabase();
        for (String medicine : Inventory.getAllMedicines("Name (A-Z)")) {
            int quantity = Integer.parseInt(getQuantity(medicine));
            originalQuantities.put(medicine, quantity);
            currentQuantities.put(medicine, quantity);
        }
    }
    
    private void refreshTable() {
        gui.scrollableBodyPanel.removeAll();
        JPanel statusPanel = createStatusPanel();
        gui.scrollableBodyPanel.add(statusPanel, BorderLayout.NORTH);
        JPanel tablePanel = createTablePanel();
        gui.scrollableBodyPanel.add(tablePanel, BorderLayout.CENTER);
        gui.paginationPanel = createPaginationPanel();
        gui.scrollableBodyPanel.add(gui.paginationPanel, BorderLayout.SOUTH);
        gui.scrollableBodyPanel.revalidate();
        gui.scrollableBodyPanel.repaint();
        enableCounterInteraction();
    }

    private void enableCounterInteraction() {
        if (!gui.isUpdateMode) return;
        gui.medicineTable.setRowSelectionAllowed(true);
        gui.medicineTable.setColumnSelectionAllowed(true);
        gui.medicineTable.setFocusTraversalKeysEnabled(false);
    }
    
    private void showFooterButtons() {
        gui.undoButton.setVisible(true);
        gui.viewButton.setVisible(true);
        gui.saveButton.setVisible(true);
        gui.editModeButton.setVisible(false);
        updateFooterButtonStates();
    }

    private void hideFooterButtons() {
        gui.undoButton.setVisible(false);
        gui.viewButton.setVisible(false);
        gui.saveButton.setVisible(false);
        gui.editModeButton.setVisible(true);
    }

    private void updateFooterButtonStates() {
        gui.undoButton.setEnabled(true);
        gui.saveButton.setEnabled(true);
        gui.editModeButton.setEnabled(true);

        gui.undoButton.setBackground(new Color(59, 130, 246));
        gui.undoButton.setForeground(Color.WHITE);

        if (!gui.isRemoveMode) {
            gui.saveButton.setText("Save");
            gui.saveButton.setBackground(new Color(34, 197, 94));
            gui.saveButton.setForeground(Color.WHITE);
        } else {
            gui.saveButton.setText("Archive");
            gui.saveButton.setBackground(new Color(255, 0, 0));
            gui.saveButton.setForeground(Color.WHITE);
        }
    }

    public void undoChanges() {
        for (Map.Entry<String, Integer> entry : originalQuantities.entrySet()) {
            currentQuantities.put(entry.getKey(), entry.getValue());
        }
        hasChanges = false;
        updateFooterButtonStates();
        refreshTable();
    }

    public void saveChanges() {
        if (!hasChanges) {
            JOptionPane.showMessageDialog(gui.mainPanel, "No Changes Made", "Make changes", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (gui.isRemoveMode) {
            List<String> selectedMedicines = new ArrayList<>();
            for (int i = 0; i < gui.tableModel.getRowCount(); i++) {
                Boolean selected = (Boolean) gui.tableModel.getValueAt(i, 0);
                if (selected != null && selected) {
                    String medicineName = ((String) gui.tableModel.getValueAt(i, 1)).split("\n")[0];
                    selectedMedicines.add(medicineName);
                }
            }
            if (selectedMedicines.isEmpty()) {
                JOptionPane.showMessageDialog(gui.mainPanel, "No medicines selected for removal!", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int result = JOptionPane.showConfirmDialog(
                gui.mainPanel,
                "Are you sure you want to permanently delete " + selectedMedicines.size() + " selected medicine(s)?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if (result == JOptionPane.YES_OPTION) {
                int successCount = 0;
                List<String> failedDeletions = new ArrayList<>();
                for (String medicineName : selectedMedicines) {
                    boolean success = Inventory.removeMedicineFromDatabase(medicineName);
                    if (success) {
                        successCount++;
                    } else {
                        failedDeletions.add(medicineName);
                    }
                }
                if (successCount > 0) {
                    JOptionPane.showMessageDialog(gui.mainPanel,
                        successCount + " medicine(s) deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    Inventory.refreshFromDatabase();
                    gui.isRemoveMode = true;
                    removeButtonAction();
                    refreshTable();
                    if (!failedDeletions.isEmpty()) {
                        JOptionPane.showMessageDialog(gui.mainPanel,
                            "Failed to delete: " + String.join(", ", failedDeletions),
                            "Partial Success",
                            JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(gui.mainPanel,
                        "Failed to delete any medicines!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            int result = JOptionPane.showConfirmDialog(
                gui.mainPanel,
                "Save changes to stock quantities?",
                "Confirm Save",
                JOptionPane.YES_NO_OPTION
            );
            if (result == JOptionPane.YES_OPTION) {
                int successCount = 0;
                List<String> failedUpdates = new ArrayList<>();
                for (Map.Entry<String, Integer> entry : currentQuantities.entrySet()) {
                    String medicineName = entry.getKey();
                    int newQuantity = entry.getValue();
                    if (!originalQuantities.get(medicineName).equals(newQuantity)) {
                        boolean success = Inventory.updateStock(medicineName, newQuantity);
                        if (success) {
                            successCount++;
                        } else {
                            failedUpdates.add(medicineName);
                        }
                    }
                }
                if (successCount > 0) {
                    JOptionPane.showMessageDialog(gui.mainPanel,
                        successCount + " medicine(s) updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    refreshTable();
                    if (!failedUpdates.isEmpty()) {
                        JOptionPane.showMessageDialog(gui.mainPanel,
                            "Failed to update: " + String.join(", ", failedUpdates),
                            "Partial Success",
                            JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(gui.mainPanel,
                        "No changes were made or all updates failed!",
                        "No Changes",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    private JPanel createPaginationPanel() {
        JPanel paginationPanel = new JPanel(new BorderLayout());
        paginationPanel.setBackground(Color.WHITE);
        paginationPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        
        JLabel paginationInfo = new JLabel("Showing medicines from inventory");
        paginationInfo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        paginationInfo.setForeground(new Color(107, 114, 128));
        paginationPanel.add(paginationInfo, BorderLayout.WEST);
        
        return paginationPanel;
    }
    
    private void filterProductPanels() {
        if (!gui.isSearchMode) return;
        String query = gui.searchField.getText().trim().toLowerCase();
        
        if (query.isEmpty()) {
            gui.tableSorter.setRowFilter(null);
        } else {
            int medicineCol = gui.isRemoveMode ? 1 : 0;
            
            RowFilter<DefaultTableModel, Object> rf = new RowFilter<DefaultTableModel, Object>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                    String medicineData = (String) entry.getValue(medicineCol);
                    String medicineName = medicineData.split("\n")[0].toLowerCase();
                    return medicineName.contains(query);
                }
            };
            gui.tableSorter.setRowFilter(rf);
        }
        updatePaginationInfo();
    }
    
    private void updatePaginationInfo() {
        int totalRows = gui.medicineTable.getRowCount();
        JLabel paginationInfo = new JLabel("Showing " + totalRows + " medicines");
        paginationInfo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        paginationInfo.setForeground(new Color(107, 114, 128));
        
        gui.paginationPanel.removeAll();
        gui.paginationPanel.add(paginationInfo, BorderLayout.WEST);
        gui.paginationPanel.revalidate();
        gui.paginationPanel.repaint();
    }

    public void addButtonAction() {
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

        while (true) {
            int result = JOptionPane.showConfirmDialog(gui.mainPanel, panel, "Add New Medicine", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == -1 || result == 2) {
                break;
            }
            if (result == JOptionPane.OK_OPTION) {
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
                            Inventory.refreshFromDatabase();
                            refreshTable();
                            JOptionPane.showMessageDialog(gui.mainPanel, "Medicine added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(gui.mainPanel, "Failed to add medicine!", "Fail", JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(gui.mainPanel, "Please enter a valid stock number!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(gui.mainPanel, "Please fill in required fields!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void removeButtonAction() {
        gui.isRemoveMode = !gui.isRemoveMode;
        gui.isMedicineUpdateMode = false;
        
        if (gui.isRemoveMode) {
            gui.removeButton.setText("Exit Remove");
            gui.editMedicineButton.setText("Edit");
        } else {
            gui.removeButton.setText("Remove");
        }
        refreshTable();
        updateFooterButtonStates();
    }

    public void returnButtonAction() {
        mainApp.restoreMainButtons();
    }
    
    public void editMedicineAction() {
        gui.isMedicineUpdateMode = !gui.isMedicineUpdateMode;
        gui.isRemoveMode = false;
        
        if (gui.isMedicineUpdateMode) {
            gui.editMedicineButton.setText("Exit Edit");
            gui.removeButton.setText("Remove");
        } else {
            gui.editMedicineButton.setText("Edit");
        }
        refreshTable();
        updateFooterButtonStates();
    }

    public void editMedicineAction(String medicineName) {
        Map<String, Object> details = Inventory.getMedicineDetails(medicineName);
        if (details.isEmpty()) {
            JOptionPane.showMessageDialog(gui.mainPanel, "Could not find details for " + medicineName, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String currentClass = (String) details.get("pharmaClass");
        String currentDosage = String.valueOf(details.get("dosage"));
        String currentBrand = (String) details.get("brand");
        String currentStock = String.valueOf(details.get("stock"));

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        JTextField nameField = new JTextField(medicineName);
        nameField.setEditable(false);
        JTextField classField = new JTextField(currentClass);
        JTextField dosageField = new JTextField(currentDosage);
        JTextField brandField = new JTextField(currentBrand);
        JTextField stockField = new JTextField(currentStock);

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

        int result = JOptionPane.showConfirmDialog(gui.mainPanel, panel, "Edit Medicine: " + medicineName,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String newClass = classField.getText().trim();
                int newDosage = Integer.parseInt(dosageField.getText().trim());
                String newBrand = brandField.getText().trim();
                int newStock = Integer.parseInt(stockField.getText().trim());
                boolean success = Inventory.updateMedicine(medicineName, newClass, newDosage, newBrand, newStock);
                if (success) {
                    Inventory.refreshFromDatabase();
                    refreshTable();
                    JOptionPane.showMessageDialog(gui.mainPanel, "Medicine updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(gui.mainPanel, "Failed to update medicine in the database!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(gui.mainPanel, "Dosage and Stock must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String formatMedicineName(String medicine) {
        return medicine + "\n" + getMedicineType(medicine);
    }

    private String getManufacturer(String medicine) {
        return Inventory.getManufacturer(medicine);
    }

    private String getQuantity(String medicine) {
        int stock = Inventory.getStock(medicine);
        return String.valueOf(stock);
    }

    private String getMedicineType(String medicine) {
        return Inventory.getMedicineType(medicine);
    }

    public String getMedicineNameFromRow(int row) {
        int medicineColumn;
        if (gui.isRemoveMode || gui.isMedicineUpdateMode) {
            medicineColumn = 1;
        } else {
            medicineColumn = 0;
        }
        String fullText = (String) gui.tableModel.getValueAt(row, medicineColumn);
        return fullText.split("\n")[0];
    }

    public void updateQuantity(String medicineName, int newQuantity) {
        currentQuantities.put(medicineName, newQuantity);
        
        boolean quantitiesChanged = false;
        for (Map.Entry<String, Integer> entry : currentQuantities.entrySet()) {
            Integer original = originalQuantities.get(entry.getKey());
            if (original == null || !original.equals(entry.getValue())) {
                quantitiesChanged = true;
                break;
            }
        }
        
        boolean anyCheckboxSelected = false;
        if (gui.isRemoveMode) {
            for (int i = 0; i < gui.tableModel.getRowCount(); i++) {
                Boolean selected = (Boolean) gui.tableModel.getValueAt(i, 0);
                if (selected != null && selected) {
                    anyCheckboxSelected = true;
                    break;
                }
            }
        }
        
        boolean newHasChanges = quantitiesChanged || anyCheckboxSelected;
        if(hasChanges != newHasChanges){
            hasChanges = newHasChanges;
            SwingUtilities.invokeLater(() -> updateFooterButtonStates());
        }
        
        updateTableQuantity(medicineName, newQuantity);
    }

    private void updateTableQuantity(String medicineName, int newQuantity) {
        for (int i = 0; i < gui.tableModel.getRowCount(); i++) {
            int medicineColumn = gui.isRemoveMode ? 1 : 0;
            String rowMedicineName = ((String) gui.tableModel.getValueAt(i, medicineColumn)).split("\n")[0];
            
            if (rowMedicineName.equals(medicineName)) {
                int quantityColumn = gui.isRemoveMode ? 3 : 2;
                gui.tableModel.setValueAt(String.valueOf(newQuantity), i, quantityColumn);
                break;
            }
        }
    }
    
    private void setupTableMouseListener() {
        gui.medicineTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                int row = gui.medicineTable.rowAtPoint(e.getPoint());
                int col = gui.medicineTable.columnAtPoint(e.getPoint());
                
                if (row >= 0) {
                    if (gui.isRemoveMode && col == 0) {
                        int modelRow = gui.medicineTable.convertRowIndexToModel(row);
                        Boolean currentValue = (Boolean) gui.tableModel.getValueAt(modelRow, 0);
                        boolean newValue = currentValue == null ? true : !currentValue;
                        gui.tableModel.setValueAt(newValue, modelRow, 0);
                        checkForCheckboxChanges();
                        gui.medicineTable.repaint();
                    } else if (gui.isUpdateMode && col == (gui.isRemoveMode ? 3 : 2)) {
                        if (!gui.medicineTable.isEditing()) {
                            gui.medicineTable.editCellAt(row, col);
                            Component editor = gui.medicineTable.getEditorComponent();
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
        if (gui.isRemoveMode) {
            for (int i = 0; i < gui.tableModel.getRowCount(); i++) {
                Boolean selected = (Boolean) gui.tableModel.getValueAt(i, 0);
                if (selected != null && selected) {
                    anySelected = true;
                    break;
                }
            }
        }
        
        boolean quantitiesChanged = false;
        for (Map.Entry<String, Integer> entry : currentQuantities.entrySet()) {
            Integer original = originalQuantities.get(entry.getKey());
            if (original == null || !original.equals(entry.getValue())) {
                quantitiesChanged = true;
                break;
            }
        }
        
        boolean newHasChanges = anySelected || quantitiesChanged;
        if (hasChanges != newHasChanges) {
            hasChanges = newHasChanges;
            SwingUtilities.invokeLater(() -> updateFooterButtonStates());
        }
    }

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
        // This part needs to update the GUI labels in createStatusPanel
    }

    public void cleanup() {
        if (hasChanges) {
            int result = JOptionPane.showConfirmDialog(
                gui.mainPanel,
                "You have unsaved changes. Do you want to save them?",
                "Unsaved Changes",
                JOptionPane.YES_NO_CANCEL_OPTION
            );
            if (result == JOptionPane.YES_OPTION) {
                saveChanges();
            }
        }
    }

    public JPanel getMainPanel() {
        return gui.mainPanel;
    }
}