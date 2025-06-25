package sample;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class leftButtonPanelGUI {
    protected JPanel mainPanel;
    protected JButton returnButton, editModeButton, addButton, removeButton, backButton, searchButton, editMedicineButton;
    protected JComboBox<String> medicineSorting;
    protected JTextField searchField;
    protected JPanel scrollableBodyPanel, headerPanel, searchPanel, footerPanel, viewButtonPanel;
    protected JButton viewButton, confirmButton, undoButton, saveButton;
    protected JCheckBox checkBox;
    protected JTable medicineTable;
    protected DefaultTableModel tableModel;
    protected TableRowSorter<DefaultTableModel> tableSorter;
    protected JPanel paginationPanel;
    protected JPanel updateButtonsPanel;

    // GUI-related flags
    protected boolean isUpdateMode = false;
    protected boolean isSearchMode = false;
    protected boolean isRemoveMode = false;
    protected boolean isMedicineUpdateMode = false;

    // --- Inner Classes for Table Cell Rendering and Editing ---

    class HeaderRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                     boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setBackground(new Color(249, 250, 251));
            label.setForeground(new Color(107, 114, 128));
            label.setFont(new Font("SansSerif", Font.BOLD, 11));
            label.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
            label.setHorizontalAlignment(SwingConstants.LEFT);

            if (isRemoveMode && column == 0) {
                label.setHorizontalAlignment(SwingConstants.CENTER);
            }

            if (column == table.getColumnCount() - 1) {
                label.setHorizontalAlignment(SwingConstants.RIGHT);
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
            stockField.setEditable(false);

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
        private final leftButtonFunctions functions;

        public UpdateQuantityCellEditor(leftButtonFunctions functions) {
            this.functions = functions;
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

            stockField.addActionListener(e -> {
                if (isEditing) {
                    validateAndUpdateFromTextField();
                }
            });

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
            functions.updateQuantity(medicineName, currentValue);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            medicineName = functions.getMedicineNameFromRow(row);
            currentValue = Integer.parseInt(value.toString());
            stockField.setText(String.valueOf(currentValue));
            isEditing = true;

            minusBtn.setEnabled(true);
            plusBtn.setEnabled(true);
            stockField.setEditable(true);

            SwingUtilities.invokeLater(() -> stockField.requestFocusInWindow());
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

    class EditButtonCellRenderer extends JButton implements TableCellRenderer {
        public EditButtonCellRenderer() {
            setText("Edit");
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                     boolean isSelected, boolean hasFocus, int row, int column) {
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
        private final leftButtonFunctions functions;

        public EditButtonCellEditor(leftButtonFunctions functions) {
            this.functions = functions;
            button = new JButton("Edit");
            button.setOpaque(true);
            button.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            functions.editMedicineAction(medicineName);
            fireEditingStopped();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            int modelRow = table.convertRowIndexToModel(row);
            this.medicineName = functions.getMedicineNameFromRow(modelRow);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return button.getText();
        }
    }
    
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

    public JPanel getMainPanel() {
        return mainPanel;
    }
}