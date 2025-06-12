package ui.panels.rightpanel;

import main.ApplicationConstants;
import utils.ComponentFactory;
import ui.forms.DocumentFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BarangayDocumentContent {
    // ==================== FIELDS ====================
    private JFrame mainFrame;
    private String selectedDocumentType;
    private JPanel cardPanel; // Main panel with CardLayout
    private CardLayout cardLayout; // CardLayout manager
    private JPanel dynamicFormContainer;
    private JPanel dynamicRecordsContainer;

    // Card names for easy reference
    private static final String DOCUMENT_TYPE_CARD = "DOCUMENT_TYPE";
    private static final String FORM_CARD = "FORM";

    // ==================== CONSTRUCTOR ====================
    public BarangayDocumentContent(JFrame mainFrame){
        this.mainFrame = mainFrame;
        setupCardLayout();
    }

    // ==================== INITIALIZATION METHODS ====================

    //Initialize the CardLayout system
    private void setupCardLayout() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);

        // Create and add initial document type panel
        JPanel documentTypePanel = createInitialPanel();
        cardPanel.add(documentTypePanel, DOCUMENT_TYPE_CARD);

        // Start with document type selection
        cardLayout.show(cardPanel, DOCUMENT_TYPE_CARD);
    }

    // ==================== MAIN PANEL CREATION METHODS ====================

    //This method returns the initial document type selection panel
    public JPanel createInitialPanel(){
        JPanel initialPanel = new JPanel(new BorderLayout());
        initialPanel.setOpaque(false);

        // Create the main content panel
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BorderLayout(10, 10));
        dashboardPanel.setOpaque(false);

        // Header Section
        String title = "Barangay Document Application - Select Document Type";
        JPanel headerSection = ComponentFactory.getHeaderPanel(title);
        dashboardPanel.add(headerSection, BorderLayout.NORTH);

        // Document type selection panel
        JPanel documentTypePanel = createDocumentTypePanel();

        // Wrap in a container with padding
        JPanel contentArea = new JPanel(new BorderLayout());
        contentArea.setOpaque(false);
        contentArea.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        contentArea.add(documentTypePanel, BorderLayout.CENTER);

        dashboardPanel.add(contentArea, BorderLayout.CENTER);
        initialPanel.add(dashboardPanel, BorderLayout.CENTER);

        return initialPanel;
    }

    //This method creates the form layout that appears AFTER document type selection
    public JPanel createFormLayout(){
        JPanel formLayout = new JPanel(new BorderLayout());
        formLayout.setOpaque(false);

        // Create the main content panel with proper styling
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BorderLayout(10, 10));
        dashboardPanel.setOpaque(false);

        // Header Section showing selected document type (now it will be correct)
        String title = (selectedDocumentType != null ? selectedDocumentType : "Document Type");
        JPanel headerSection = ComponentFactory.getHeaderPanel(title);
        dashboardPanel.add(headerSection, BorderLayout.NORTH);

        // Create the different sections
        JPanel inputFormPanel = createInputFormLayout();
        JPanel crudPanel = createCrudLayout();
        JPanel documentRecordsPanel = createDocumentRecordsLayout();

        // Main content area with GridBagLayout - Only 2 rows
        JPanel contentArea = new JPanel(new GridBagLayout());
        contentArea.setOpaque(false);
        contentArea.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        // First row - Input Form (left) and CRUD Actions (right)
        gbc.gridy = 0; gbc.gridwidth = 1; gbc.weighty = 0.4;
        gbc.gridx = 0; gbc.weightx = 0.96;
        contentArea.add(inputFormPanel, gbc);

        gbc.gridx = 1; gbc.weightx = 0.04;
        contentArea.add(crudPanel, gbc);

        // Second row - Document Records (full width)
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 2; gbc.weightx = 1.0; gbc.weighty = 0.6;
        contentArea.add(documentRecordsPanel, gbc);

        dashboardPanel.add(contentArea, BorderLayout.CENTER);
        formLayout.add(dashboardPanel, BorderLayout.CENTER);

        return formLayout;
    }

    // ==================== COMPONENT CREATION METHODS ====================

    public JPanel createDocumentTypePanel(){
        JPanel documentTypePanel = new JPanel(new GridLayout(4, 5, 10, 10));
        documentTypePanel.setOpaque(false);
        documentTypePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(75, 83, 32, 100), 2),
                "Choose the Document Type",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(75, 83, 32)
        ));

        String[] documentTypes = ApplicationConstants.DOCUMENT_TYPES;

        // Colors for different document types
        Color[] documentColors = {
                new Color(75, 83, 32),     // #4B5320 - Dark Olive
                new Color(106, 115, 55),   // #6A7337 - Olive
                new Color(138, 154, 91),   // #8A9A5B - Light Olive
                new Color(189, 143, 66),   // #BD8F42 - Golden Brown
                new Color(208, 181, 100),  // #D0B564 - Light Golden
                new Color(11, 18, 21),     // #0B1215 - Very Dark Blue-Green
                new Color(43, 50, 9),      // #2B3209 - Dark Olive Green
        };

        for (int i = 0; i < documentTypes.length; i++) {
            final int index = i;
            final String docType = documentTypes[i];

            JPanel docCard = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Use modulo to cycle through colors
                    Color cardColor = documentColors[index % documentColors.length];
                    g2d.setColor(cardColor);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                    // Add highlight effect
                    g2d.setColor(new Color(255, 255, 255, 50));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight()/2, 15, 15);

                    g2d.dispose();
                    super.paintComponent(g);
                }
            };

            docCard.setLayout(new BorderLayout());
            docCard.setOpaque(false);
            docCard.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Make it clickable
            docCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    onDocumentTypeSelected(docType);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    docCard.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                    docCard.repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    docCard.setBorder(null);
                    docCard.repaint();
                }
            });

            JLabel docLabel = new JLabel(docType, SwingConstants.CENTER);
            docLabel.setFont(new Font("Arial", Font.BOLD, 12));
            docLabel.setForeground(Color.WHITE);
            docLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

            // For longer text, use HTML to wrap text
            if (docType.length() > 15) {
                docLabel.setText("<html><center>" + docType + "</center></html>");
            }

            docCard.add(docLabel, BorderLayout.CENTER);
            documentTypePanel.add(docCard);
        }

        // Fill remaining slots with empty panels
        int totalSlots = 20; // 4 rows × 5 columns
        for (int i = documentTypes.length; i < totalSlots; i++) {
            JPanel emptyPanel = new JPanel();
            emptyPanel.setOpaque(false);
            documentTypePanel.add(emptyPanel);
        }

        return documentTypePanel;
    }

    public JPanel createInputFormLayout(){
        JPanel inputFormLayout = new JPanel(new BorderLayout());
        inputFormLayout.setOpaque(false);
        inputFormLayout.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(75, 83, 32, 100), 2),
                "Input Document Information",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(75, 83, 32)
        ));

        // Create the dynamic container that will hold different forms
        dynamicFormContainer = new JPanel(new BorderLayout());
        dynamicFormContainer.setOpaque(false);

        // Initially show instruction message
        JLabel instructionLabel = new JLabel("Please select a document type to display the form");
        instructionLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        instructionLabel.setForeground(Color.GRAY);
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dynamicFormContainer.add(instructionLabel, BorderLayout.CENTER);

        // Wrap in JScrollPane with fixed size
        JScrollPane scrollPane = new JScrollPane(dynamicFormContainer);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setPreferredSize(new Dimension(100, 100)); // Adjust these values as needed
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        inputFormLayout.add(scrollPane, BorderLayout.CENTER);
        return inputFormLayout;
    }

    public JPanel createCrudLayout(){
        JPanel crudLayout = new JPanel(new GridLayout(6, 1, 10, 10)); // Changed to 6 rows to include Back button
        crudLayout.setOpaque(false);
        crudLayout.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(75, 83, 32, 100), 2),
                        "Quick Action Buttons",
                        TitledBorder.CENTER,
                        TitledBorder.TOP,
                        new Font("Arial", Font.BOLD, 14),
                        new Color(75, 83, 32)
                ),
                BorderFactory.createEmptyBorder(10, 10, 10, 10) // Add padding: top, left, bottom, right
        ));

        // Add action buttons including a Back button
        String[] buttonLabels = {"Add New Data", "Update Data", "Delete Data", "Clear Form", "Print Document", "Document Types"};
        for (String label : buttonLabels) {
            JButton button = new JButton(label) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Button background color
                    g2d.setColor(new Color(75, 83, 32));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                    // Border
                    g2d.setColor(new Color(0, 0, 0, 150));
                    g2d.setStroke(new BasicStroke(1));
                    g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);

                    g2d.dispose();
                    super.paintComponent(g);
                }
            };

            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setOpaque(false); // Important for custom painting

            // Add action listener for the Back button
            if (label.equals("Document Types")) {
                button.addActionListener(e -> switchToDocumentTypeSelection());
            }
            // You can add other button actions here

            crudLayout.add(button);
        }

        return crudLayout;
    }

    public JPanel createDocumentRecordsLayout(){
        JPanel documentRecordsLayout = new JPanel(new BorderLayout());
        documentRecordsLayout.setOpaque(false);
        documentRecordsLayout.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(75, 83, 32, 100), 1),
                "Recent Document Records",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(75, 83, 32)
        ));

        // Create the dynamic container for records
        dynamicRecordsContainer = new JPanel(new BorderLayout());
        dynamicRecordsContainer.setOpaque(false);

        // Initially show instruction message
        JLabel instructionLabel = new JLabel("Document records will appear here after selecting a document type");
        instructionLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        instructionLabel.setForeground(Color.GRAY);
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dynamicRecordsContainer.add(instructionLabel, BorderLayout.CENTER);

        documentRecordsLayout.add(dynamicRecordsContainer, BorderLayout.CENTER);
        return documentRecordsLayout;
    }

    // ==================== PUBLIC ACCESS METHODS ====================

    //Method to get the main card panel (call this from your main UI)
    public JPanel getCardPanel() {
        return cardPanel;
    }

    // ==================== NAVIGATION METHODS ====================

    //Method to switch to form panel
    private void switchToFormPanel() {
        // Simply switch to the existing form panel
        // The dynamic content will be updated by updateInputForm() and updateDocumentRecords()
        cardLayout.show(cardPanel, FORM_CARD);
    }

    //Method to switch back to document type selection
    public void switchToDocumentTypeSelection() {
        cardLayout.show(cardPanel, DOCUMENT_TYPE_CARD);
    }

    // ==================== EVENT HANDLERS ====================

    //Method to handle document type selection
    private void onDocumentTypeSelected(String documentType) {
        this.selectedDocumentType = documentType;
        System.out.println("Selected document type: " + documentType);

        // Remove existing form panel if it exists
        try {
            cardPanel.remove(cardPanel.getComponent(1)); // Remove the form panel
        } catch (Exception e) {
            // Form panel doesn't exist yet, which is fine
        }

        // Create new form panel with updated document type
        JPanel formPanel = createFormLayout();
        cardPanel.add(formPanel, FORM_CARD);

        // Update form content
        updateInputForm(documentType);
        updateDocumentRecords(documentType);

        // Switch to the form layout panel using CardLayout
        switchToFormPanel();
    }

    // ==================== DYNAMIC CONTENT UPDATE METHODS ====================

    //Add your dynamic form updating method:
    private void updateInputForm(String documentType) {
        // Clear existing content
        dynamicFormContainer.removeAll();

        // Add new form based on document type
        switch (documentType) {
            case "Bail Certificate":
                dynamicFormContainer.add(DocumentFactory.createBailCertificateForm(), BorderLayout.CENTER);
                break;
            case "Barangay Clearance":
                dynamicFormContainer.add(DocumentFactory.createBarangayClearanceForm(), BorderLayout.CENTER);
                break;
            case "Business Permit (New Application)":
                dynamicFormContainer.add(DocumentFactory.createNewBusinessPermitForm(), BorderLayout.NORTH);
                break;
            case "Business Permit (Renewal)":
                dynamicFormContainer.add(DocumentFactory.createRenewalBusinessPermitForm(), BorderLayout.NORTH);
                break;
            case "Certificate for Business Closure":
                dynamicFormContainer.add(DocumentFactory.createBusinessClosureForm(), BorderLayout.NORTH);
                break;
            case "Certificate for Calamity (Disaster)":
                dynamicFormContainer.add(DocumentFactory.createCalamityForm(), BorderLayout.CENTER);
                break;
            case "Certificate for No Objection (for Building Construction)":
                dynamicFormContainer.add(DocumentFactory.createNoObjectionForm(), BorderLayout.CENTER);
                break;
            case "Certificate for PWD's":
                // Will be implemented in next batch
                break;
            case "Certificate for Senior Citizens":
                // Will be implemented in next batch
                break;
            case "Certificate for Solo Parent":
                dynamicFormContainer.add(DocumentFactory.createSoloParentForm(), BorderLayout.NORTH);
                break;
            case "Certificate of Good Moral Character":
                // Will be implemented in next batch
                break;
            case "Certificate of Indigency":
                dynamicFormContainer.add(DocumentFactory.createBusinessClearanceForm(), BorderLayout.CENTER);
                break;
            case "Certificate of Residency":
                dynamicFormContainer.add(DocumentFactory.createsCommunityTaxCertificateForm(), BorderLayout.NORTH);
                break;
            case "Community Tax Certificate (Cedula)":
                dynamicFormContainer.add(DocumentFactory.createCommunityTaxCertificateForm(), BorderLayout.NORTH);
                break;
            case "First Time Job Seeker":
                dynamicFormContainer.add(DocumentFactory.createFirstTimeJobSeekerForm(), BorderLayout.NORTH);
                break;
            case "Late Registration":
                dynamicFormContainer.add(DocumentFactory.createLateRegistrationForm(), BorderLayout.NORTH);
                break;
            case "Low Income Certificate":
                dynamicFormContainer.add(DocumentFactory.createLowIncomeCertificateForm(), BorderLayout.NORTH);
                break;
            case "No Income Certificate":
                dynamicFormContainer.add(DocumentFactory.createNoIncomeCertificateForm(), BorderLayout.NORTH);
                break;
            case "State Tax Certificate":
                dynamicFormContainer.add(DocumentFactory.createStateTaxCertificateForm(), BorderLayout.NORTH);
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

    //Add similar method for document records:
    private void updateDocumentRecords(String documentType) {
        // Clear existing content
        dynamicRecordsContainer.removeAll();

        // Create document-specific records panel
        switch (documentType) {
            case "Certificate of Indigency":
                //dynamicRecordsContainer.add(createIndigencyRecordsPanel(), BorderLayout.CENTER);
                break;
            case "Barangay Certificate for Business":
                //dynamicRecordsContainer.add(createBusinessCertificateRecordsPanel(), BorderLayout.CENTER);
                break;
            case "Certificate of Residency":
                //dynamicRecordsContainer.add(createResidencyRecordsPanel(), BorderLayout.CENTER);
                break;
            case "Certificate of Solo Parent":
                //dynamicRecordsContainer.add(createSoloParentRecordsPanel(), BorderLayout.CENTER);
                break;
            case "Barangay ID":
                //dynamicRecordsContainer.add(createBarangayIDRecordsPanel(), BorderLayout.CENTER);
                break;
            case "Individual Barangay Clearance":
                //dynamicRecordsContainer.add(createIndividualClearanceRecordsPanel(), BorderLayout.CENTER);
                break;
            case "Business Barangay Clearance":
                //dynamicRecordsContainer.add(createBusinessClearanceRecordsPanel(), BorderLayout.CENTER);
                break;
            case "Business Permit":
                //dynamicRecordsContainer.add(createBusinessPermitRecordsPanel(), BorderLayout.CENTER);
                break;
            default:
                JLabel instructionLabel = new JLabel("Document records will appear here after selecting a document type");
                instructionLabel.setFont(new Font("Arial", Font.ITALIC, 12));
                instructionLabel.setForeground(Color.GRAY);
                instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
                dynamicRecordsContainer.add(instructionLabel, BorderLayout.CENTER);
                break;
        }

        // Refresh the display
        dynamicRecordsContainer.revalidate();
        dynamicRecordsContainer.repaint();
    }
}
