package ui.panels.rightpanel;

import main.Main;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BarangayDocumentContent {
    private Main mainFrame;
    private JPanel dynamicFormContainer;
    private JScrollPane formScrollPane;

    public BarangayDocumentContent(Main mainFrame) {
        this.mainFrame = mainFrame;
    }

    public JPanel createDocumentTypePanel() {
        JPanel panel = mainFrame.createStyledPanel();
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(128, 0, 0), 2),
                "Document Type Selection",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(128, 0, 0)
        ));

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel selectLabel = new JLabel("Select Document:");
        selectLabel.setFont(new Font("Arial", Font.BOLD, 12));

        String[] documentTypes = {
                "Select Document Type",
                "Certificate of Indigency",
                "Barangay Certificate for Business",
                "Certificate of Residency",
                "Certificate of Solo Parent",
                "Barangay ID",
                "Individual Barangay Clearance",
                "Business Barangay Clearance",
                "Business Permit"
        };

        JComboBox<String> documentTypeCombo = new JComboBox<>(documentTypes);
        documentTypeCombo.setFont(new Font("Arial", Font.PLAIN, 12));
        documentTypeCombo.setPreferredSize(new Dimension(250, 30));

        // Add action listener to update form when document type changes
        documentTypeCombo.addActionListener(e -> {
            String selectedType = (String) documentTypeCombo.getSelectedItem();
            updateInputForm(selectedType);
        });

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        panel.add(selectLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.7;
        panel.add(documentTypeCombo, gbc);

        return panel;
    }

    public JPanel createDynamicInputFormPanel() {
        JPanel panel = mainFrame.createStyledPanel();
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(128, 0, 0), 2),
                "Document Information",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(128, 0, 0)
        ));

        // Store reference to form container
        dynamicFormContainer = new JPanel(new BorderLayout());
        dynamicFormContainer.setOpaque(false);

        JLabel instructionLabel = new JLabel("Please select a document type to display the form");
        instructionLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        instructionLabel.setForeground(Color.GRAY);
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        dynamicFormContainer.add(instructionLabel, BorderLayout.CENTER);

        // Store reference to scroll pane
        formScrollPane = new JScrollPane(dynamicFormContainer);
        formScrollPane.setOpaque(false);
        formScrollPane.getViewport().setOpaque(false);
        formScrollPane.setBorder(null);
        formScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        formScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel.setLayout(new BorderLayout());
        panel.add(formScrollPane, BorderLayout.CENTER);

        return panel;
    }

    public JPanel createCrudActionsPanel() {
        JPanel panel = mainFrame.createStyledPanel();
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(128, 0, 0), 2),
                "Quick Actions",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(128, 0, 0)
        ));

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 10, 8, 10);

        // Create action buttons
        JButton addButton = createActionButton("Add New Data", "icons/add.png");
        JButton updateButton = createActionButton("Update Data", "icons/edit.png");
        JButton deleteButton = createActionButton("Delete Data", "icons/delete.png");
        JButton clearButton = createActionButton("Clear Form", "icons/clear.png");
        JButton printButton = createActionButton("Print Document", "icons/print.png");

        // Add action listeners
        addButton.addActionListener(e -> handleAddDocument());
        updateButton.addActionListener(e -> handleUpdateDocument());
        deleteButton.addActionListener(e -> handleDeleteDocument());
        clearButton.addActionListener(e -> handleClearForm());
        printButton.addActionListener(e -> handlePrintDocument());

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1.0;
        panel.add(addButton, gbc);

        gbc.gridy = 1;
        panel.add(updateButton, gbc);

        gbc.gridy = 2;
        panel.add(deleteButton, gbc);

        gbc.gridy = 3;
        panel.add(clearButton, gbc);

        gbc.gridy = 4;
        panel.add(printButton, gbc);

        return panel;
    }

    public JButton createActionButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setPreferredSize(new Dimension(140, 35));
        button.setBackground(new Color(128, 0, 0));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(160, 0, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(128, 0, 0));
            }
        });

        return button;
    }

    public JPanel createSearchFilterPanel() {
        JPanel panel = mainFrame.createStyledPanel();
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(128, 0, 0), 2),
                "Search & Filter",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(128, 0, 0)
        ));

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 12));

        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 12));
        searchField.setPreferredSize(new Dimension(200, 25));

        JLabel filterLabel = new JLabel("Filter by Status:");
        filterLabel.setFont(new Font("Arial", Font.BOLD, 12));

        String[] statusOptions = {"All", "Pending", "Approved", "Released", "Rejected"};
        JComboBox<String> statusFilter = new JComboBox<>(statusOptions);
        statusFilter.setFont(new Font("Arial", Font.PLAIN, 12));
        statusFilter.setPreferredSize(new Dimension(120, 25));

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 10));
        searchButton.setBackground(new Color(128, 0, 0));
        searchButton.setForeground(Color.BLACK);
        searchButton.setPreferredSize(new Dimension(80, 25));

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        panel.add(searchLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.7;
        panel.add(searchField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        panel.add(filterLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.7;
        panel.add(statusFilter, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(searchButton, gbc);

        return panel;
    }

    public JPanel createDocumentRecordsPanel() {
        JPanel panel = mainFrame.createStyledPanel();
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(128, 0, 0), 2),
                "Document Records",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(128, 0, 0)
        ));

        // Create table with sample columns (will be dynamic based on document type)
        String[] columnNames = {
                "ID", "Document Type", "Applicant Name", "Date Applied",
                "Status", "Date Processed", "Processed By"
        };

        Object[][] sampleData = {
                {"001", "Certificate of Indigency", "Juan Dela Cruz", "2024-01-15", "Approved", "2024-01-16", "Admin"},
                {"002", "Barangay Clearance", "Maria Santos", "2024-01-14", "Pending", "-", "-"},
                {"003", "Certificate of Residency", "Pedro Garcia", "2024-01-13", "Released", "2024-01-15", "Admin"}
        };

        JTable documentsTable = new JTable(sampleData, columnNames);
        documentsTable.setFont(new Font("Arial", Font.PLAIN, 11));
        documentsTable.setRowHeight(25);
        documentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        documentsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Customize table header
        JTableHeader header = documentsTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setBackground(new Color(128, 0, 0));
        header.setForeground(Color.BLACK);

        JScrollPane tableScrollPane = new JScrollPane(documentsTable);
        tableScrollPane.setPreferredSize(new Dimension(800, 200));

        panel.setLayout(new BorderLayout());
        panel.add(tableScrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCommonFields() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Full Name
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField fullNameField = new JTextField(20);
        panel.add(fullNameField, gbc);

        // Address
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField addressField = new JTextField(20);
        panel.add(addressField, gbc);

        // Contact Number
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Contact Number:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField contactField = new JTextField(20);
        panel.add(contactField, gbc);

        return panel;
    }

    private JPanel createCertificateOfIndigencyForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add common fields
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(createCommonFields(), gbc);

        // Specific fields for Indigency Certificate
        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;

        // Age
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField ageField = new JTextField(20);
        formPanel.add(ageField, gbc);

        // Civil Status
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Civil Status:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        String[] civilStatus = {"Single", "Married", "Widowed", "Divorced", "Separated"};
        JComboBox<String> civilStatusCombo = new JComboBox<>(civilStatus);
        formPanel.add(civilStatusCombo, gbc);

        // Monthly Income
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Monthly Income:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField incomeField = new JTextField(20);
        formPanel.add(incomeField, gbc);

        // Purpose
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Purpose:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0;
        JTextArea purposeArea = new JTextArea(3, 20);
        purposeArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane purposeScroll = new JScrollPane(purposeArea);
        formPanel.add(purposeScroll, gbc);

        return formPanel;
    }

    private JPanel createBarangayCertificateForBusinessForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add common fields
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(createCommonFields(), gbc);

        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;

        // Business Name
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Business Name:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField businessNameField = new JTextField(20);
        formPanel.add(businessNameField, gbc);

        // Business Type
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Business Type:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        String[] businessTypes = {"Retail", "Service", "Manufacturing", "Food", "Other"};
        JComboBox<String> businessTypeCombo = new JComboBox<>(businessTypes);
        formPanel.add(businessTypeCombo, gbc);

        // Business Address
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Business Address:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField businessAddressField = new JTextField(20);
        formPanel.add(businessAddressField, gbc);

        // Nature of Business
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Nature of Business:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0;
        JTextArea natureArea = new JTextArea(3, 20);
        natureArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane natureScroll = new JScrollPane(natureArea);
        formPanel.add(natureScroll, gbc);

        return formPanel;
    }

    private JPanel createCertificateOfResidencyForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add common fields
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(createCommonFields(), gbc);

        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;

        // Years of Residency
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Years of Residency:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField yearsField = new JTextField(20);
        formPanel.add(yearsField, gbc);

        // Date of Birth
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField dobField = new JTextField(20);
        dobField.setToolTipText("MM/DD/YYYY");
        formPanel.add(dobField, gbc);

        // Place of Birth
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Place of Birth:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField pobField = new JTextField(20);
        formPanel.add(pobField, gbc);

        // Purpose
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Purpose:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0;
        JTextArea purposeArea = new JTextArea(3, 20);
        purposeArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane purposeScroll = new JScrollPane(purposeArea);
        formPanel.add(purposeScroll, gbc);

        return formPanel;
    }

    private JPanel createBarangayIDForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add common fields
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(createCommonFields(), gbc);

        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;

        // Date of Birth
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField dobField = new JTextField(20);
        dobField.setToolTipText("MM/DD/YYYY");
        formPanel.add(dobField, gbc);

        // Emergency Contact
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Emergency Contact:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField emergencyContactField = new JTextField(20);
        formPanel.add(emergencyContactField, gbc);

        // Emergency Contact Number
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Emergency Contact Number:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField emergencyNumberField = new JTextField(20);
        formPanel.add(emergencyNumberField, gbc);

        // Photo Upload Button
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Photo:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JButton photoButton = new JButton("Upload Photo");
        photoButton.setBackground(new Color(128, 0, 0));
        photoButton.setForeground(Color.WHITE);
        formPanel.add(photoButton, gbc);

        return formPanel;
    }

    private JPanel createCertificateOfSoloParentForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add common fields
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(createCommonFields(), gbc);

        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;

        // Date of Birth
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField dobField = new JTextField(20);
        dobField.setToolTipText("MM/DD/YYYY");
        formPanel.add(dobField, gbc);

        // Number of Children
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Number of Children:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField childrenField = new JTextField(20);
        formPanel.add(childrenField, gbc);

        // Monthly Income
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Monthly Income:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField incomeField = new JTextField(20);
        formPanel.add(incomeField, gbc);

        // Employment Status
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Employment Status:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        String[] employmentStatus = {"Employed", "Unemployed", "Self-Employed", "Part-time"};
        JComboBox<String> employmentCombo = new JComboBox<>(employmentStatus);
        formPanel.add(employmentCombo, gbc);

        // Reason for Solo Parent Status
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Reason for Solo Parent Status:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        String[] reasons = {"Death of Spouse", "Abandonment", "Separation", "Single Mother", "Other"};
        JComboBox<String> reasonCombo = new JComboBox<>(reasons);
        formPanel.add(reasonCombo, gbc);

        // Additional Details
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Additional Details:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0;
        JTextArea detailsArea = new JTextArea(3, 20);
        detailsArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane detailsScroll = new JScrollPane(detailsArea);
        formPanel.add(detailsScroll, gbc);

        return formPanel;
    }

    private JPanel createIndividualClearanceForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add common fields
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(createCommonFields(), gbc);

        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;

        // Date of Birth
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField dobField = new JTextField(20);
        dobField.setToolTipText("MM/DD/YYYY");
        formPanel.add(dobField, gbc);

        // Place of Birth
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Place of Birth:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField pobField = new JTextField(20);
        formPanel.add(pobField, gbc);

        // Civil Status
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Civil Status:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        String[] civilStatus = {"Single", "Married", "Widowed", "Divorced", "Separated"};
        JComboBox<String> civilStatusCombo = new JComboBox<>(civilStatus);
        formPanel.add(civilStatusCombo, gbc);

        // Years of Residency
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Years of Residency:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField yearsField = new JTextField(20);
        formPanel.add(yearsField, gbc);

        // Clearance Type
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Clearance Type:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        String[] clearanceTypes = {"Employment", "Travel", "Loan Application", "School Requirement", "Other"};
        JComboBox<String> clearanceTypeCombo = new JComboBox<>(clearanceTypes);
        formPanel.add(clearanceTypeCombo, gbc);

        // Purpose
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Purpose:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0;
        JTextArea purposeArea = new JTextArea(3, 20);
        purposeArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane purposeScroll = new JScrollPane(purposeArea);
        formPanel.add(purposeScroll, gbc);

        return formPanel;
    }

    private JPanel createBusinessClearanceForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add common fields (Owner information)
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel ownerPanel = createCommonFields();
        ownerPanel.setBorder(BorderFactory.createTitledBorder("Owner Information"));
        formPanel.add(ownerPanel, gbc);

        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;

        // Business Name
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Business Name:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField businessNameField = new JTextField(20);
        formPanel.add(businessNameField, gbc);

        // Business Address
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Business Address:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField businessAddressField = new JTextField(20);
        formPanel.add(businessAddressField, gbc);

        // Business Type
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Business Type:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        String[] businessTypes = {"Retail", "Service", "Manufacturing", "Food & Beverage", "Healthcare", "Technology", "Other"};
        JComboBox<String> businessTypeCombo = new JComboBox<>(businessTypes);
        formPanel.add(businessTypeCombo, gbc);

        // Date Established
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Date Established:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField dateEstablishedField = new JTextField(20);
        dateEstablishedField.setToolTipText("MM/DD/YYYY");
        formPanel.add(dateEstablishedField, gbc);

        // Number of Employees
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Number of Employees:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField employeesField = new JTextField(20);
        formPanel.add(employeesField, gbc);

        // Nature of Business
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Nature of Business:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0;
        JTextArea natureArea = new JTextArea(3, 20);
        natureArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane natureScroll = new JScrollPane(natureArea);
        formPanel.add(natureScroll, gbc);

        return formPanel;
    }

    private JPanel createBusinessPermitForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Owner Information
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel ownerPanel = createCommonFields();
        ownerPanel.setBorder(BorderFactory.createTitledBorder("Business Owner Information"));
        formPanel.add(ownerPanel, gbc);

        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;

        // Business Registration Number
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("DTI/SEC Registration No:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField registrationField = new JTextField(20);
        formPanel.add(registrationField, gbc);

        // Business Name
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Business Name:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField businessNameField = new JTextField(20);
        formPanel.add(businessNameField, gbc);

        // Business Address
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Business Address:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField businessAddressField = new JTextField(20);
        formPanel.add(businessAddressField, gbc);

        // Business Category
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Business Category:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        String[] categories = {"Micro", "Small", "Medium", "Large"};
        JComboBox<String> categoryCombo = new JComboBox<>(categories);
        formPanel.add(categoryCombo, gbc);

        // Capital Investment
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Capital Investment:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField capitalField = new JTextField(20);
        formPanel.add(capitalField, gbc);

        // Number of Employees
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Number of Employees:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField employeesField = new JTextField(20);
        formPanel.add(employeesField, gbc);

        // Gross Sales/Receipts
        gbc.gridx = 0; gbc.gridy = 7; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Expected Gross Sales/Receipts:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField grossSalesField = new JTextField(20);
        formPanel.add(grossSalesField, gbc);

        // Business Activities
        gbc.gridx = 0; gbc.gridy = 8; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Business Activities:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0;
        JTextArea activitiesArea = new JTextArea(3, 20);
        activitiesArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane activitiesScroll = new JScrollPane(activitiesArea);
        formPanel.add(activitiesScroll, gbc);

        return formPanel;
    }

    private void updateInputForm(String documentType) {
        // Clear existing content
        dynamicFormContainer.removeAll();

        // Add new form based on document type
        switch (documentType) {
            case "Certificate of Indigency":
                dynamicFormContainer.add(createCertificateOfIndigencyForm(), BorderLayout.CENTER);
                break;
            case "Barangay Certificate for Business":
                dynamicFormContainer.add(createBarangayCertificateForBusinessForm(), BorderLayout.CENTER);
                break;
            case "Certificate of Residency":
                dynamicFormContainer.add(createCertificateOfResidencyForm(), BorderLayout.CENTER);
                break;
            case "Certificate of Solo Parent":
                dynamicFormContainer.add(createCertificateOfSoloParentForm(), BorderLayout.CENTER);
                break;
            case "Barangay ID":
                dynamicFormContainer.add(createBarangayIDForm(), BorderLayout.CENTER);
                break;
            case "Individual Barangay Clearance":
                dynamicFormContainer.add(createIndividualClearanceForm(), BorderLayout.CENTER);
                break;
            case "Business Barangay Clearance":
                dynamicFormContainer.add(createBusinessClearanceForm(), BorderLayout.CENTER);
                break;
            case "Business Permit":
                dynamicFormContainer.add(createBusinessPermitForm(), BorderLayout.CENTER);
                break;
            default:
                JLabel instructionLabel = new JLabel("Please select a document type to display the form");
                instructionLabel.setFont(new Font("Arial", Font.ITALIC, 12));
                instructionLabel.setForeground(Color.GRAY);
                instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
                dynamicFormContainer.add(instructionLabel, BorderLayout.CENTER);
                break;
        }

        // Refresh the display
        dynamicFormContainer.revalidate();
        dynamicFormContainer.repaint();
    }

    // CRUD Action Handlers (placeholder methods)
    private void handleAddDocument() {
        System.out.println("Add document action triggered");
        // Implementation will connect to database
    }

    private void handleUpdateDocument() {
        System.out.println("Update document action triggered");
        // Implementation will connect to database
    }

    private void handleDeleteDocument() {
        System.out.println("Delete document action triggered");
        // Implementation will connect to database
    }

    private void handleClearForm() {
        System.out.println("Clear form action triggered");
        // Clear all input fields
    }

    private void handlePrintDocument() {
        System.out.println("Print document action triggered");
        // Generate and print document
    }
}
