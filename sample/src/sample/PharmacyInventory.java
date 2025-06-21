package sample;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PharmacyInventory extends JFrame {
    
    private JPanel bodyPanel;
    private JTable medicineTable;
    private DefaultTableModel tableModel;
    
    public void setUpBody() {
        bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setBackground(Color.WHITE);
        bodyPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create header panel with status and last updated info
        JPanel headerPanel = createHeaderPanel();
        bodyPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Create medicine table
        JPanel tablePanel = createTablePanel();
        bodyPanel.add(tablePanel, BorderLayout.CENTER);
        
        // Create pagination panel
        JPanel paginationPanel = createPaginationPanel();
        bodyPanel.add(paginationPanel, BorderLayout.SOUTH);
        
        // Add body panel to main frame
        this.add(bodyPanel, BorderLayout.CENTER);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Last updated label
        JLabel lastUpdatedLabel = new JLabel("Last updated: Saturday, June 21, 2025 at 01:16:24 PM");
        lastUpdatedLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lastUpdatedLabel.setForeground(new Color(107, 114, 128));
        headerPanel.add(lastUpdatedLabel, BorderLayout.WEST);
        
        // Status legend panel
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        statusPanel.setBackground(Color.WHITE);
        
        statusPanel.add(createStatusItem("27", "Available", new Color(34, 197, 94)));
        statusPanel.add(createStatusItem("9", "Low Stock", new Color(245, 158, 11)));
        statusPanel.add(createStatusItem("9", "Out of Stock", new Color(107, 114, 128)));
        
        headerPanel.add(statusPanel, BorderLayout.EAST);
        
        return headerPanel;
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
        
        // Create table model with custom column structure
        String[] columnNames = {"MEDICINE", "MANUFACTURER", "QUANTITY"};
        Object[][] data = {
            {"Paracetamol\nPain Relief (500mg)", "Generic", "245"},
            {"Amoxicillin\nAntibiotic (250mg)", "PharmaCorp", "12"},
            {"Ibuprofen\nPain Relief (200mg)", "HealthPlus", "89"},
            {"Loratadine\nAntihistamine (10mg)", "AllerFree", "0"},
            {"Omeprazole\nAntacid (20mg)", "Generic", "5"},
            {"Paracetamol\nPain Relief (500mg)", "Generic", "245"},
            {"Amoxicillin\nAntibiotic (250mg)", "PharmaCorp", "12"},
            {"Ibuprofen\nPain Relief (200mg)", "HealthPlus", "89"},
            {"Loratadine\nAntihistamine (10mg)", "AllerFree", "0"},
            {"Omeprazole\nAntacid (20mg)", "Generic", "5"}
        };
        
        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        medicineTable = new JTable(tableModel);
        medicineTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        medicineTable.setRowHeight(60);
        medicineTable.setShowGrid(false);
        medicineTable.setIntercellSpacing(new Dimension(0, 0));
        medicineTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
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
    
    private JPanel createPaginationPanel() {
        JPanel paginationPanel = new JPanel(new BorderLayout());
        paginationPanel.setBackground(Color.WHITE);
        paginationPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        
        // Pagination info
        JLabel paginationInfo = new JLabel("Showing 1 to 10 of 45 medicines");
        paginationInfo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        paginationInfo.setForeground(new Color(107, 114, 128));
        paginationPanel.add(paginationInfo, BorderLayout.WEST);
        
        // Pagination controls
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        controlsPanel.setBackground(Color.WHITE);
        
        JButton prevButton = createPaginationButton("Previous");
        prevButton.setEnabled(false);
        
        JButton page1Button = createPaginationButton("1");
        page1Button.setBackground(new Color(107, 114, 128));
        page1Button.setForeground(Color.WHITE);
        
        JButton page2Button = createPaginationButton("2");
        JButton page3Button = createPaginationButton("3");
        JButton nextButton = createPaginationButton("Next");
        
        controlsPanel.add(prevButton);
        controlsPanel.add(page1Button);
        controlsPanel.add(page2Button);
        controlsPanel.add(page3Button);
        controlsPanel.add(nextButton);
        
        paginationPanel.add(controlsPanel, BorderLayout.EAST);
        
        return paginationPanel;
    }
    
    private JButton createPaginationButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 12));
        button.setPreferredSize(new Dimension(text.equals("Previous") || text.equals("Next") ? 80 : 35, 30));
        button.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219), 1));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(107, 114, 128));
        button.setFocusPainted(false);
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pagination logic would go here
            }
        });
        
        return button;
    }
    
    // Custom header renderer
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
    
    // Medicine name cell renderer
    class MedicineCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            String[] parts = value.toString().split("\n");
            String html = "<html><div style='line-height: 1.2;'>" +
                         "<div style='font-weight: bold; font-size: 14px; color: #1f2937;'>" + parts[0] + "</div>" +
                         "<div style='font-size: 12px; color: #6b7280; margin-top: 2px;'>" + parts[1] + "</div>" +
                         "</div></html>";
            
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, html, isSelected, hasFocus, row, column);
            label.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
            label.setVerticalAlignment(SwingConstants.CENTER);
            
            return label;
        }
    }
    
    // Manufacturer cell renderer
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
    
    // Quantity cell renderer with color coding
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
    
    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PharmacyInventory frame = new PharmacyInventory();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 600);
            frame.setLocationRelativeTo(null);
            frame.setTitle("Pharmacy Inventory Management");
            
            frame.setUpBody();
            frame.setVisible(true);
        });
    }
}