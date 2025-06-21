package sample;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LBPSample {
    private JPanel mainPanel;
    private JButton returnButton, updateButton, addButton, removeButton, backButton, searchButton;
    private JTextField searchField;
    private JPanel scrollableBodyPanel;
    private JPanel headerPanel;
    private JPanel searchPanel;
    
    private JPanel footerPanel;
    private JButton cancelButton;
    private JButton confirmButton;
    
    private JCheckBox checkBox;
    
    // New fields for table implementation
    private JTable medicineTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> tableSorter;
    private JPanel paginationPanel;

    private Main mainApp; // Reference to the main app

    public LBPSample(Main mainApp) {
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
        
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filterProductPanels();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filterProductPanels();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filterProductPanels();
            }
        });

        searchButton.addActionListener(e -> filterProductPanels());
        searchPanel.add(searchButton);
        searchPanel.add(searchField);
        headerPanel.add(searchPanel, BorderLayout.CENTER);

        // Update button (Right)
        updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateButtonAction());
        headerPanel.add(updateButton, BorderLayout.EAST);
    }
    
    private void setUpFooter() {
        footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        footerPanel.setBackground(new Color(245, 245, 245)); // light gray for distinction

        cancelButton = new JButton("Cancel");
        confirmButton = new JButton("Confirm");

        cancelButton.addActionListener(e -> hideFooterButtons());
        confirmButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Confirmed changes!", "Success", JOptionPane.INFORMATION_MESSAGE);
            hideFooterButtons(); // optionally hide after confirming
        });

        footerPanel.add(cancelButton);
        footerPanel.add(confirmButton);

        // ❗️ Keep panel visible, but buttons hidden by default
        cancelButton.setVisible(false);
        confirmButton.setVisible(false);
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
        String[] columnNames = {"MEDICINE", "MANUFACTURER", "QUANTITY"};
        
        // Initialize table model with data from Inventory
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Populate table with inventory data
        populateTableData();
        
        medicineTable = new JTable(tableModel);
        medicineTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        medicineTable.setRowHeight(60);
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
        medicineTable.getColumnModel().getColumn(0).setPreferredWidth(400);
        medicineTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        medicineTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        
        // Custom cell renderers
        medicineTable.getColumnModel().getColumn(0).setCellRenderer(new MedicineCellRenderer());
        medicineTable.getColumnModel().getColumn(1).setCellRenderer(new ManufacturerCellRenderer());
        medicineTable.getColumnModel().getColumn(2).setCellRenderer(new QuantityCellRenderer());
        
        JScrollPane scrollPane = new JScrollPane(medicineTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        return tablePanel;
    }
    
    private void populateTableData() {
        // Clear existing data
        tableModel.setRowCount(0);
        
        // Add data from Inventory class
        for (String medicine : Inventory.getAllMedicines()) {
            // You'll need to modify this based on your Inventory class structure
            // Assuming you have methods to get manufacturer and quantity
            String manufacturer = getManufacturer(medicine); // You'll need to implement this
            String quantity = getQuantity(medicine); // You'll need to implement this
            
            Object[] rowData = {
                formatMedicineName(medicine), // Format with name and type
                manufacturer,
                quantity
            };
            tableModel.addRow(rowData);
        }
    }
    
    private String formatMedicineName(String medicine) {
        // This method formats the medicine name for display
        // You may need to adjust this based on your data structure
        return medicine + "\n" + getMedicineType(medicine); // You'll need to implement getMedicineType
    }
    
    private String getManufacturer(String medicine) {
        // Implement this method based on your Inventory class
        // For now, returning sample data
        return "Generic"; // Replace with actual implementation
    }
    
    private String getQuantity(String medicine) {
        // Implement this method based on your Inventory class
        // For now, returning sample data
        return "100"; // Replace with actual implementation
    }
    
    private String getMedicineType(String medicine) {
        // Implement this method based on your Inventory class
        // For now, returning sample data
        return "Pain Relief (500mg)"; // Replace with actual implementation
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
    
    // Updated filterProductPanels method for real-time search
    private void filterProductPanels() {
        String query = searchField.getText().trim().toLowerCase();
        
        if (query.isEmpty()) {
            tableSorter.setRowFilter(null); // Show all rows
        } else {
            // Create a row filter that searches in medicine name and manufacturer columns
            RowFilter<DefaultTableModel, Object> rf = RowFilter.regexFilter("(?i)" + query, 0, 1);
            tableSorter.setRowFilter(rf);
        }
        
        // Update pagination info based on filtered results
        updatePaginationInfo();
    }
    
    private void updatePaginationInfo() {
        int totalRows = medicineTable.getRowCount();
        JLabel paginationInfo = new JLabel("Showing " + totalRows + " medicines");
        paginationInfo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        paginationInfo.setForeground(new Color(107, 114, 128));
        
        // Update the pagination panel
        paginationPanel.removeAll();
        paginationPanel.add(paginationInfo, BorderLayout.WEST);
        paginationPanel.revalidate();
        paginationPanel.repaint();
    }

    // Custom renderers (same as before)
    class HeaderRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setBackground(new Color(249, 250, 251));
            label.setForeground(new Color(107, 114, 128));
            label.setFont(new Font("SansSerif", Font.BOLD, 11));
            label.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
            label.setHorizontalAlignment(column == 2 ? SwingConstants.RIGHT : SwingConstants.LEFT);
            
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
                label.setForeground(new Color(107, 114, 128)); // Gray for out of stock
            } else if (quantity <= 10) {
                label.setForeground(new Color(245, 158, 11)); // Orange for low stock
            } else {
                label.setForeground(new Color(34, 197, 94)); // Green for available
            }
            
            label.setFont(new Font("SansSerif", Font.BOLD, 16));
            label.setHorizontalAlignment(SwingConstants.RIGHT);
            label.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
            
            return label;
        }
    }
    
    // Your existing methods (keeping them as-is)
    public void updateButtonAction() {
        // Ignore this
    }
    
    public void addButtonAction() {
        // Ignore this
    }
    
    public void removeButtonAction() {
        // Ignore this
    }
    
    private JPanel getGridPanel() {
        // Ignore this
        return null;
    }

    private void restoreHeaderButtons() {
        // Ignore this
    }

    public void returnButtonAction() {
        // Returns to the main menu
    }
    
    private void showFooterButtons() {
        // Shows the footer
    }

    private void hideFooterButtons() {
        // Hides the footer
    }
    
    // ✅ Getter Methods
    public JPanel getPanel() {
        return mainPanel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getReturnButton() {
        return returnButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JPanel getScrollableBodyPanel() {
        return scrollableBodyPanel;
    }
}
