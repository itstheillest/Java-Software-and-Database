package ui.forms;

import utils.ComponentFactory;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import javax.swing.JTextField;


public class DocumentFactory {

    public static JPanel createDocumentInformationSection() {
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);
        Font sectionFont = new Font("Trebuchet MS", Font.BOLD, 14);

        JPanel docInfoSection = new JPanel(new GridBagLayout());
        docInfoSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                " Basic Document Information",
                TitledBorder.LEFT, TitledBorder.TOP, sectionFont
        ));
        docInfoSection.setOpaque(false);

        GridBagConstraints docGbc = new GridBagConstraints();
        docGbc.insets = new Insets(5, 5, 5, 5);
        docGbc.anchor = GridBagConstraints.WEST;

        // Certification Number
        docGbc.gridx = 0; docGbc.gridy = 0; docGbc.fill = GridBagConstraints.NONE; docGbc.weightx = 0;
        JLabel certNumberLabel = new JLabel("Certification Number:");
        certNumberLabel.setFont(labelFont);
        docInfoSection.add(certNumberLabel, docGbc);

        docGbc.gridx = 1; docGbc.fill = GridBagConstraints.HORIZONTAL; docGbc.weightx = 1.0;
        JTextField certNumberField = new JTextField(20);
        certNumberField.setName("certNumberField");
        certNumberField.setPreferredSize(new Dimension(0, 25));
        certNumberField.setFont(inputFont);
        certNumberField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(certNumberField, "CERT-2025-XXXX");
        docInfoSection.add(certNumberField, docGbc);

        // Date of Filing
        docGbc.gridx = 0; docGbc.gridy = 1; docGbc.fill = GridBagConstraints.NONE; docGbc.weightx = 0;
        JLabel dateFilingLabel = new JLabel("Date of Filing:");
        dateFilingLabel.setFont(labelFont);
        docInfoSection.add(dateFilingLabel, docGbc);

        JPanel dateFilingField = ComponentFactory.createDatePickerPanel(inputFont);
        dateFilingField.setName("dateFilingField");

        docGbc.gridx = 1; docGbc.fill = GridBagConstraints.HORIZONTAL; docGbc.weightx = 1.0;

        docInfoSection.add(dateFilingField, docGbc);
        docGbc.gridwidth = 1;

        // Date Issued
        docGbc.gridx = 0; docGbc.gridy = 2; docGbc.fill = GridBagConstraints.NONE; docGbc.weightx = 0;
        JLabel dateIssuedLabel = new JLabel("Date Issued:");
        dateIssuedLabel.setFont(labelFont);
        docInfoSection.add(dateIssuedLabel, docGbc);

        JPanel dateIssuedField = ComponentFactory.createDatePickerPanel(inputFont);
        dateIssuedField.setName("dateIssuedField");

        docGbc.gridx = 1; docGbc.fill = GridBagConstraints.HORIZONTAL; docGbc.weightx = 1.0;

        docInfoSection.add(dateIssuedField, docGbc);
        docGbc.gridwidth = 1;

        // Civil Status
        docGbc.gridx = 0; docGbc.gridy = 3; docGbc.fill = GridBagConstraints.NONE; docGbc.weightx = 0;
        JLabel documentStatusLabel = new JLabel("Document Status:");
        documentStatusLabel.setFont(labelFont);
        docInfoSection.add(documentStatusLabel, docGbc);

        docGbc.gridx = 1; docGbc.fill = GridBagConstraints.HORIZONTAL; docGbc.weightx = 1.0;
        String[] civilStatusOptions = {"Select Document Status", "In Process", "To be Claimed", "Claimed"};
        JComboBox<String> documentStatusCombo = new JComboBox<>(civilStatusOptions);
        documentStatusCombo.setName("documentStatusCombo");
        documentStatusCombo.setPreferredSize(new Dimension(0, 25));
        documentStatusCombo.setFont(inputFont);
        documentStatusCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        docInfoSection.add(documentStatusCombo, docGbc);

        return docInfoSection;
    }

    public static JPanel createPersonalInformationSection() {
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);
        Font sectionFont = new Font("Trebuchet MS", Font.BOLD, 14);

        // Personal Information Section Header
        JPanel personalInfoSection = new JPanel(new GridBagLayout());
        personalInfoSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Personal Information",
                TitledBorder.LEFT, TitledBorder.TOP, sectionFont
        ));
        personalInfoSection.setOpaque(false);

        GridBagConstraints personalGbc = new GridBagConstraints();
        personalGbc.insets = new Insets(5, 5, 5, 5);
        personalGbc.anchor = GridBagConstraints.WEST;

        // Full Name
        personalGbc.gridx = 0; personalGbc.gridy = 0; personalGbc.fill = GridBagConstraints.NONE; personalGbc.weightx = 0;
        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameLabel.setFont(labelFont);
        personalInfoSection.add(fullNameLabel, personalGbc);

        personalGbc.gridx = 1; personalGbc.fill = GridBagConstraints.HORIZONTAL; personalGbc.weightx = 1.0;
        JTextField fullNameField = new JTextField(20);
        fullNameField.setName("fullNameField");
        fullNameField.setPreferredSize(new Dimension(0, 25));
        fullNameField.setFont(inputFont);
        fullNameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(fullNameField, "Last Name, First Name, Middle Initial");
        personalInfoSection.add(fullNameField, personalGbc);

        // Date of Birth
        personalGbc.gridx = 0; personalGbc.gridy = 1; personalGbc.fill = GridBagConstraints.NONE; personalGbc.weightx = 0;
        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setFont(labelFont);
        personalInfoSection.add(dobLabel, personalGbc);

        // Create date picker panel using helper
        JPanel dobPanel = ComponentFactory.createDatePickerPanel(inputFont);
        dobPanel.setName("dobPanel");

        // Add Age Field (simple shift to the right)
        dobPanel.add(Box.createHorizontalStrut(20)); // Shift right by 20 pixels

        dobPanel.add(new JLabel("Age:"));
        dobPanel.add(Box.createHorizontalStrut(5));

        JTextField ageField = new JTextField(5);
        ageField.setName("ageField");
        ageField.setFont(inputFont);
        ageField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ageField.setEditable(false);
        ageField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
        ComponentFactory.addPlaceholder(ageField, "Auto-calculated");
        dobPanel.add(ageField);

        // Extract year/month/day combo boxes by position
        JComboBox<String> dobMonthCombo = ComponentFactory.findComboBoxInPanel(dobPanel, 0); // Month
        JComboBox<String> dobDayCombo   = ComponentFactory.findComboBoxInPanel(dobPanel, 1); // Day
        JComboBox<String> dobYearCombo  = ComponentFactory.findComboBoxInPanel(dobPanel, 2); // Year

        ComponentFactory.addAgeCalculationListener(dobYearCombo, dobMonthCombo, dobDayCombo, ageField);

        personalGbc.gridx = 1; personalGbc.fill = GridBagConstraints.HORIZONTAL; personalGbc.weightx = 1.0;
        personalInfoSection.add(dobPanel, personalGbc);

        // Complete Address
        personalGbc.gridx = 0; personalGbc.gridy = 2; personalGbc.fill = GridBagConstraints.NONE; personalGbc.weightx = 0;
        JLabel addressLabel = new JLabel("Complete Address:");
        addressLabel.setFont(labelFont);
        personalInfoSection.add(addressLabel, personalGbc);

        personalGbc.gridx = 1; personalGbc.fill = GridBagConstraints.HORIZONTAL; personalGbc.weightx = 1.0;
        JTextField addressField = new JTextField(20);
        addressField.setName("addressField");
        addressField.setPreferredSize(new Dimension(0, 25));
        addressField.setFont(inputFont);
        addressField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(addressField, "House/Building/Blk/Lot No., Street, Barangay, City/Municipality, Province");
        personalInfoSection.add(addressField, personalGbc);

        // Contact Number
        personalGbc.gridx = 0; personalGbc.gridy = 3; personalGbc.fill = GridBagConstraints.NONE; personalGbc.weightx = 0;
        JLabel contactLabel = new JLabel("Contact Number:");
        contactLabel.setFont(labelFont);
        personalInfoSection.add(contactLabel, personalGbc);

        personalGbc.gridx = 1; personalGbc.fill = GridBagConstraints.HORIZONTAL; personalGbc.weightx = 1.0;
        JTextField contactField = new JTextField(20);
        contactField.setName("contactField");
        contactField.setPreferredSize(new Dimension(0, 25));
        contactField.setFont(inputFont);
        contactField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(contactField, "09XX-XXX-XXXX");
        personalInfoSection.add(contactField, personalGbc);

        return personalInfoSection;
    }

    public static JPanel createDocumentsToPresentSection(String[] documentOptions, Font sectionFont, Font inputFont) {
        JPanel documentsSection = new JPanel(new GridBagLayout());
        documentsSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Documents to Present",
                TitledBorder.LEFT, TitledBorder.TOP, sectionFont
        ));
        documentsSection.setOpaque(false);

        GridBagConstraints docCheckGbc = new GridBagConstraints();
        docCheckGbc.insets = new Insets(5, 5, 5, 5);
        docCheckGbc.anchor = GridBagConstraints.WEST;
        docCheckGbc.gridx = 0;
        docCheckGbc.gridy = 0;
        docCheckGbc.fill = GridBagConstraints.HORIZONTAL;
        docCheckGbc.weightx = 1.0;

        JPanel documentsPanel = new JPanel(new GridLayout(0, 2, 2, 5)); // 2 columns
        documentsPanel.setOpaque(false);

        for (String text : documentOptions) {
            JCheckBox checkbox = new JCheckBox("<html><body style='width:200px'>" + text + "</body></html>");
            checkbox.setFont(inputFont);
            checkbox.setOpaque(false);
            documentsPanel.add(checkbox);
        }

        documentsSection.add(documentsPanel, docCheckGbc);
        return documentsSection;
    }

    //Certificate for Bail Certificate Form
    public static JPanel createBailCertificateForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);
        Font sectionFont = new Font("Trebuchet MS", Font.BOLD, 14);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Document Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel docInfoSection = createDocumentInformationSection();
        formPanel.add(docInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Personal Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel personalInfoSection = createPersonalInformationSection();
        formPanel.add(personalInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Specific Information for Application Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel specificInfoSection = new JPanel(new GridBagLayout());
        specificInfoSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Specific Document Information",
                TitledBorder.LEFT, TitledBorder.TOP, sectionFont
        ));
        specificInfoSection.setOpaque(false);

        GridBagConstraints specificGbc = new GridBagConstraints();
        specificGbc.insets = new Insets(5, 5, 5, 5);
        specificGbc.anchor = GridBagConstraints.WEST;

        // Nature of Case
        specificGbc.gridx = 0; specificGbc.gridy = 0; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel natureOfCaseLabel = new JLabel("Nature of Case:");
        natureOfCaseLabel.setFont(labelFont);
        specificInfoSection.add(natureOfCaseLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField natureOfCaseField = new JTextField(20);
        natureOfCaseField.setName("natureOfCaseField");
        natureOfCaseField.setPreferredSize(new Dimension(0, 25));
        natureOfCaseField.setFont(inputFont);
        natureOfCaseField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(natureOfCaseField, "e.g., Theft, Physical Injury, etc.");
        specificInfoSection.add(natureOfCaseField, specificGbc);

        // Case Number / Docket Number (Optional)
        specificGbc.gridx = 0; specificGbc.gridy = 1; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel caseNumberLabel = new JLabel("Case Number / Docket Number (if known):");
        caseNumberLabel.setFont(labelFont);
        specificInfoSection.add(caseNumberLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField caseNumberField = new JTextField(20);
        caseNumberField.setName("caseNumberField");
        caseNumberField.setPreferredSize(new Dimension(0, 25));
        caseNumberField.setFont(inputFont);
        caseNumberField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(caseNumberField, "Optional - Enter case/docket number if known");
        specificInfoSection.add(caseNumberField, specificGbc);

        // Court or Police Station Where Bail Was Posted
        specificGbc.gridx = 0; specificGbc.gridy = 2; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel courtPoliceStationLabel = new JLabel("Court or Police Station Where Bail Was Posted:");
        courtPoliceStationLabel.setFont(labelFont);
        specificInfoSection.add(courtPoliceStationLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField courtPoliceStationField = new JTextField(20);
        courtPoliceStationField.setName("courtPoliceStationField");
        courtPoliceStationField.setPreferredSize(new Dimension(0, 25));
        courtPoliceStationField.setFont(inputFont);
        courtPoliceStationField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(courtPoliceStationField, "Name of court or police station");
        specificInfoSection.add(courtPoliceStationField, specificGbc);

        // Date Bail Was Posted
        specificGbc.gridx = 0; specificGbc.gridy = 3; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel dateBailPostedLabel = new JLabel("Date Bail Was Posted:");
        dateBailPostedLabel.setFont(labelFont);
        specificInfoSection.add(dateBailPostedLabel, specificGbc);

        // Create the date picker panel using the helper method
        JPanel dateBailPosted = ComponentFactory.createDatePickerPanel(inputFont);
        dateBailPosted.setName("dateBailPosted");

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        specificInfoSection.add(dateBailPosted, specificGbc);

        formPanel.add(specificInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Documents to Present Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;

        String[] documentOptions = {
                "Valid ID",
                "Barangay Clearance / Certificate of Residency (if applicable)",
                "Copy of Bail Document/Court Order",
                "Police Report / Blotter (if applicable)",
                "Receipt of Payment (if applicable)"
        };

        JPanel documentsSection = createDocumentsToPresentSection(documentOptions, sectionFont, inputFont);
        documentsSection.setName("documentsToPresentSection");

        formPanel.add(documentsSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        return formPanel;
    }

    //Barangay Clearance Form
    public static JPanel createBarangayClearanceForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);
        Font sectionFont = new Font("Trebuchet MS", Font.BOLD, 14);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Basic Document Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel docInfoSection = createDocumentInformationSection();
        formPanel.add(docInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Personal Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel personalInfoSection = createPersonalInformationSection();
        formPanel.add(personalInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Specific Document Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel specificInfoSection = new JPanel(new GridBagLayout());
        specificInfoSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Specific Document Information",
                TitledBorder.LEFT, TitledBorder.TOP, sectionFont
        ));
        specificInfoSection.setOpaque(false);

        GridBagConstraints specificGbc = new GridBagConstraints();
        specificGbc.insets = new Insets(5, 5, 5, 5);
        specificGbc.anchor = GridBagConstraints.WEST;

        // Sex
        specificGbc.gridx = 0; specificGbc.gridy = 0; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel sexLabel = new JLabel("Sex:");
        sexLabel.setFont(labelFont);
        specificInfoSection.add(sexLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JPanel sexPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        sexPanel.setOpaque(false);

        ButtonGroup sexGroup = new ButtonGroup();
        JRadioButton maleRadio = new JRadioButton("Male");
        JRadioButton femaleRadio = new JRadioButton("Female");
        JRadioButton notSayRadio = new JRadioButton("Rather not say");

        maleRadio.setFont(inputFont);
        femaleRadio.setFont(inputFont);
        notSayRadio.setFont(inputFont);
        maleRadio.setOpaque(false);
        femaleRadio.setOpaque(false);
        notSayRadio.setOpaque(false);

        sexGroup.add(maleRadio);
        sexGroup.add(femaleRadio);
        sexGroup.add(notSayRadio);

        sexPanel.add(maleRadio);
        sexPanel.add(femaleRadio);
        sexPanel.add(notSayRadio);
        specificInfoSection.add(sexPanel, specificGbc);

        // Civil Status
        specificGbc.gridx = 0; specificGbc.gridy = 1; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel civilStatusLabel = new JLabel("Civil Status:");
        civilStatusLabel.setFont(labelFont);
        specificInfoSection.add(civilStatusLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] civilStatusOptions = {"Select Civil Status", "Single", "Married", "Widowed", "Separated"};
        JComboBox<String> civilStatusCombo = new JComboBox<>(civilStatusOptions);
        civilStatusCombo.setPreferredSize(new Dimension(0, 25));
        civilStatusCombo.setFont(inputFont);
        civilStatusCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(civilStatusCombo, specificGbc);

        // Birthplace
        specificGbc.gridx = 0; specificGbc.gridy = 2; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel birthplaceLabel = new JLabel("Birthplace:");
        birthplaceLabel.setFont(labelFont);
        specificInfoSection.add(birthplaceLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField birthplaceField = new JTextField(20);
        birthplaceField.setPreferredSize(new Dimension(0, 25));
        birthplaceField.setFont(inputFont);
        birthplaceField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(birthplaceField, "Barangay, Municipality/City, Province");
        specificInfoSection.add(birthplaceField, specificGbc);

        // Period of Stay
        specificGbc.gridx = 0; specificGbc.gridy = 3; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel periodOfStayLabel = new JLabel("Period of Stay:");
        periodOfStayLabel.setFont(labelFont);
        specificInfoSection.add(periodOfStayLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField periodOfStayField = new JTextField(20);
        periodOfStayField.setPreferredSize(new Dimension(0, 25));
        periodOfStayField.setFont(inputFont);
        periodOfStayField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(periodOfStayField, "e.g., 5 years, Since birth, 2 months");
        specificInfoSection.add(periodOfStayField, specificGbc);

        // Purpose of Request
        specificGbc.gridx = 0; specificGbc.gridy = 4; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel purposeLabel = new JLabel("Purpose of Request:");
        purposeLabel.setFont(labelFont);
        specificInfoSection.add(purposeLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] purposeOptions = {"Select Purpose", "Employment", "Business Permit", "School Requirement", "Government Transaction", "Legal Requirement", "Others"};
        JComboBox<String> purposeCombo = new JComboBox<>(purposeOptions);
        purposeCombo.setPreferredSize(new Dimension(0, 25));
        purposeCombo.setFont(inputFont);
        purposeCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(purposeCombo, specificGbc);

        // If others, please specify (initially hidden) - Purpose
        specificGbc.gridx = 0; specificGbc.gridy = 5; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel purposeOthersLabel = new JLabel("If others, please specify:");
        purposeOthersLabel.setFont(labelFont);
        purposeOthersLabel.setVisible(false);
        specificInfoSection.add(purposeOthersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField purposeOthersField = new JTextField(20);
        purposeOthersField.setPreferredSize(new Dimension(0, 25));
        purposeOthersField.setFont(inputFont);
        purposeOthersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        purposeOthersField.setVisible(false);
        ComponentFactory.addPlaceholder(purposeOthersField, "Please specify the purpose");
        specificInfoSection.add(purposeOthersField, specificGbc);

        // Purpose combo action listener for conditional field
        purposeCombo.addActionListener(e -> {
            boolean showOthers = "Others".equals(purposeCombo.getSelectedItem());
            purposeOthersLabel.setVisible(showOthers);
            purposeOthersField.setVisible(showOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        // Remarks/Notes (Optional)
        specificGbc.gridx = 0; specificGbc.gridy = 6; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        specificGbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel remarksLabel = new JLabel("Remarks/Notes (Optional):");
        remarksLabel.setFont(labelFont);
        specificInfoSection.add(remarksLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.BOTH; specificGbc.weightx = 1.0; specificGbc.weighty = 1.0;
        JTextArea remarksArea = new JTextArea(3, 20);
        remarksArea.setFont(inputFont);
        remarksArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        remarksArea.setLineWrap(true);
        remarksArea.setWrapStyleWord(true);
        ComponentFactory.addPlaceholder(remarksArea, "Additional information or special circumstances");

        JScrollPane remarksScrollPane = new JScrollPane(remarksArea);
        remarksScrollPane.setPreferredSize(new Dimension(0, 60));
        remarksScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        remarksScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        specificInfoSection.add(remarksScrollPane, specificGbc);

        // Reset anchor and weighty for subsequent components
        specificGbc.anchor = GridBagConstraints.WEST;
        specificGbc.weighty = 0;

        formPanel.add(specificInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Documents to Present Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;

        String[] documentOptions = {
                "Valid ID",
                "Community Tax Certificate (Cedula)",
                "Proof of Residency (e.g., utility bill, previous Barangay Certificate)",
                "Receipt of Payment (if applicable)"
        };

        JPanel documentsSection = createDocumentsToPresentSection(documentOptions, sectionFont, inputFont);

        formPanel.add(documentsSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        return formPanel;
    }

    //Business Permit (New Application) Form
    public static JPanel createNewBusinessPermitForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);
        Font sectionFont = new Font("Trebuchet MS", Font.BOLD, 14);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Basic Document Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel docInfoSection = createDocumentInformationSection();
        formPanel.add(docInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Personal Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel personalInfoSection = createPersonalInformationSection();
        formPanel.add(personalInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Specific Document Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel specificInfoSection = new JPanel(new GridBagLayout());
        specificInfoSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Specific Document Information",
                TitledBorder.LEFT, TitledBorder.TOP, sectionFont
        ));
        specificInfoSection.setOpaque(false);

        GridBagConstraints specificGbc = new GridBagConstraints();
        specificGbc.insets = new Insets(5, 5, 5, 5);
        specificGbc.anchor = GridBagConstraints.WEST;

        // Business Name / Trade Name
        specificGbc.gridx = 0; specificGbc.gridy = 0; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel businessNameLabel = new JLabel("Business Name / Trade Name:");
        businessNameLabel.setFont(labelFont);
        specificInfoSection.add(businessNameLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField businessNameField = new JTextField(20);
        businessNameField.setPreferredSize(new Dimension(0, 25));
        businessNameField.setFont(inputFont);
        businessNameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(businessNameField, "Enter business or trade name");
        specificInfoSection.add(businessNameField, specificGbc);

        // Type of Business
        specificGbc.gridx = 0; specificGbc.gridy = 1; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel businessTypeLabel = new JLabel("Type of Business:");
        businessTypeLabel.setFont(labelFont);
        specificInfoSection.add(businessTypeLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] businessTypeOptions = {"Select Business Type", "Sari-Sari Store", "Online Selling", "Food Stall", "Services", "Others"};
        JComboBox<String> businessTypeCombo = new JComboBox<>(businessTypeOptions);
        businessTypeCombo.setPreferredSize(new Dimension(0, 25));
        businessTypeCombo.setFont(inputFont);
        businessTypeCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(businessTypeCombo, specificGbc);

        // If others, please specify (initially hidden) - Business Type
        specificGbc.gridx = 0; specificGbc.gridy = 2; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel businessTypeOthersLabel = new JLabel("If others, please specify:");
        businessTypeOthersLabel.setFont(labelFont);
        businessTypeOthersLabel.setVisible(false);
        specificInfoSection.add(businessTypeOthersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField businessTypeOthersField = new JTextField(20);
        businessTypeOthersField.setPreferredSize(new Dimension(0, 25));
        businessTypeOthersField.setFont(inputFont);
        businessTypeOthersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        businessTypeOthersField.setVisible(false);
        ComponentFactory.addPlaceholder(businessTypeOthersField, "Please specify the type of business");
        specificInfoSection.add(businessTypeOthersField, specificGbc);

        // Business Type combo action listener for conditional field
        businessTypeCombo.addActionListener(e -> {
            boolean showOthers = "Others".equals(businessTypeCombo.getSelectedItem());
            businessTypeOthersLabel.setVisible(showOthers);
            businessTypeOthersField.setVisible(showOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        // Business Address
        specificGbc.gridx = 0; specificGbc.gridy = 3; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel businessAddressLabel = new JLabel("Business Address:");
        businessAddressLabel.setFont(labelFont);
        specificInfoSection.add(businessAddressLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField businessAddressField = new JTextField(20);
        businessAddressField.setPreferredSize(new Dimension(0, 25));
        businessAddressField.setFont(inputFont);
        businessAddressField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(businessAddressField, "Complete business address");
        specificInfoSection.add(businessAddressField, specificGbc);

        // Estimated No. of Employees
        specificGbc.gridx = 0; specificGbc.gridy = 4; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel employeesLabel = new JLabel("Estimated No. of Employees:");
        employeesLabel.setFont(labelFont);
        specificInfoSection.add(employeesLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField employeesField = new JTextField(20);
        employeesField.setPreferredSize(new Dimension(0, 25));
        employeesField.setFont(inputFont);
        employeesField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(employeesField, "Enter number of employees (including owner)");
        specificInfoSection.add(employeesField, specificGbc);

        // Business Start Date
        specificGbc.gridx = 0; specificGbc.gridy = 5; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel startDateLabel = new JLabel("Business Start Date:");
        startDateLabel.setFont(labelFont);
        specificInfoSection.add(startDateLabel, specificGbc);

        // Date panel with combo boxes
        JPanel startDatePanel = new JPanel();
        startDatePanel.setPreferredSize(new Dimension(0, 25));
        startDatePanel.setFont(inputFont);
        startDatePanel.setLayout(new BoxLayout(startDatePanel, BoxLayout.X_AXIS));
        startDatePanel.setOpaque(false);

        JComboBox<String> startYearCombo = new JComboBox<>();
        JComboBox<String> startMonthCombo = new JComboBox<>();
        JComboBox<String> startDayCombo = new JComboBox<>();

        startYearCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        startMonthCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        startDayCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        startDatePanel.add(new JLabel("Month:"));
        startDatePanel.add(Box.createHorizontalStrut(5));
        startDatePanel.add(startMonthCombo);
        startDatePanel.add(Box.createHorizontalStrut(10));

        startDatePanel.add(new JLabel("Day:"));
        startDatePanel.add(Box.createHorizontalStrut(5));
        startDatePanel.add(startDayCombo);
        startDatePanel.add(Box.createHorizontalStrut(10));

        startDatePanel.add(new JLabel("Year:"));
        startDatePanel.add(Box.createHorizontalStrut(5));
        startDatePanel.add(startYearCombo);

        // Initialize date combo boxes using helper method
        ComponentFactory.setupDateComboBoxes(startYearCombo, startMonthCombo, startDayCombo);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        specificInfoSection.add(startDatePanel, specificGbc);

        // Ownership Type
        specificGbc.gridx = 0; specificGbc.gridy = 6; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel ownershipTypeLabel = new JLabel("Ownership Type:");
        ownershipTypeLabel.setFont(labelFont);
        specificInfoSection.add(ownershipTypeLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] ownershipTypeOptions = {"Select Ownership Type", "Sole Proprietorship", "Partnership", "Corporation", "Cooperative"};
        JComboBox<String> ownershipTypeCombo = new JComboBox<>(ownershipTypeOptions);
        ownershipTypeCombo.setPreferredSize(new Dimension(0, 25));
        ownershipTypeCombo.setFont(inputFont);
        ownershipTypeCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(ownershipTypeCombo, specificGbc);

        // DTI/SEC/Coop Registration Number
        specificGbc.gridx = 0; specificGbc.gridy = 7; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel registrationNumberLabel = new JLabel("DTI/SEC/Coop Registration Number:");
        registrationNumberLabel.setFont(labelFont);
        specificInfoSection.add(registrationNumberLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField registrationNumberField = new JTextField(20);
        registrationNumberField.setPreferredSize(new Dimension(0, 25));
        registrationNumberField.setFont(inputFont);
        registrationNumberField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(registrationNumberField, "Enter registration number if available");
        specificInfoSection.add(registrationNumberField, specificGbc);

        // Declaration
        specificGbc.gridx = 0; specificGbc.gridy = 8; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel declarationLabel = new JLabel("Declaration:");
        declarationLabel.setFont(labelFont);
        specificInfoSection.add(declarationLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JCheckBox declarationCheckbox = new JCheckBox("<html><body style='width:350px'>I confirm that the above business information is accurate and that this is a new application for a barangay business permit.</body></html>");
        declarationCheckbox.setFont(inputFont);
        declarationCheckbox.setOpaque(false);
        declarationCheckbox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(declarationCheckbox, specificGbc);

        formPanel.add(specificInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Documents to Present Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;

        String[] documentOptions = {
                "Valid ID",
                "Community Tax Certificate (Cedula)",
                "Proof of Residency (e.g., utility bill, previous Barangay Certificate)",
                "DTI Certificate of Business Name Registration (for sole proprietors)",
                "Business Location Sketch or Photo",
                "Receipt of Payment (if applicable)"
        };

        JPanel documentsSection = createDocumentsToPresentSection(documentOptions, sectionFont, inputFont);

        formPanel.add(documentsSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        return formPanel;
    }

    //Business Permit (Renewal) Form
    public static JPanel createRenewalBusinessPermitForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);
        Font sectionFont = new Font("Trebuchet MS", Font.BOLD, 14);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Document Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel docInfoSection = createDocumentInformationSection();
        formPanel.add(docInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Personal Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel personalInfoSection = createPersonalInformationSection();
        formPanel.add(personalInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Specific Information for Application Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel specificInfoSection = new JPanel(new GridBagLayout());
        specificInfoSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Specific Document Information",
                TitledBorder.LEFT, TitledBorder.TOP, sectionFont
        ));
        specificInfoSection.setOpaque(false);

        GridBagConstraints specificGbc = new GridBagConstraints();
        specificGbc.insets = new Insets(5, 5, 5, 5);
        specificGbc.anchor = GridBagConstraints.WEST;

        // Business Name / Trade Name
        specificGbc.gridx = 0; specificGbc.gridy = 0; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel businessNameLabel = new JLabel("Business Name / Trade Name:");
        businessNameLabel.setFont(labelFont);
        specificInfoSection.add(businessNameLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField businessNameField = new JTextField(20);
        businessNameField.setPreferredSize(new Dimension(0, 25));
        businessNameField.setFont(inputFont);
        businessNameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(businessNameField, "Enter business or trade name");
        specificInfoSection.add(businessNameField, specificGbc);

        // Type of Business
        specificGbc.gridx = 0; specificGbc.gridy = 1; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel businessTypeLabel = new JLabel("Type of Business:");
        businessTypeLabel.setFont(labelFont);
        specificInfoSection.add(businessTypeLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] businessTypeOptions = {"Select Type", "Sari-Sari Store", "Online Selling", "Food Stall", "Services", "Others"};
        JComboBox<String> businessTypeCombo = new JComboBox<>(businessTypeOptions);
        businessTypeCombo.setPreferredSize(new Dimension(0, 25));
        businessTypeCombo.setFont(inputFont);
        businessTypeCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(businessTypeCombo, specificGbc);

        // If others, please specify (initially hidden) - Business Type
        specificGbc.gridx = 0; specificGbc.gridy = 2; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel businessTypeOthersLabel = new JLabel("If others, please specify:");
        businessTypeOthersLabel.setFont(labelFont);
        businessTypeOthersLabel.setVisible(false);
        specificInfoSection.add(businessTypeOthersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField businessTypeOthersField = new JTextField(20);
        businessTypeOthersField.setPreferredSize(new Dimension(0, 25));
        businessTypeOthersField.setFont(inputFont);
        businessTypeOthersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        businessTypeOthersField.setVisible(false);
        ComponentFactory.addPlaceholder(businessTypeOthersField, "Please specify the type of business");
        specificInfoSection.add(businessTypeOthersField, specificGbc);

        // Business Type combo action listener for conditional field
        businessTypeCombo.addActionListener(e -> {
            boolean showOthers = "Others".equals(businessTypeCombo.getSelectedItem());
            businessTypeOthersLabel.setVisible(showOthers);
            businessTypeOthersField.setVisible(showOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        // Business Address
        specificGbc.gridx = 0; specificGbc.gridy = 3; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel businessAddressLabel = new JLabel("Business Address:");
        businessAddressLabel.setFont(labelFont);
        specificInfoSection.add(businessAddressLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField businessAddressField = new JTextField(20);
        businessAddressField.setPreferredSize(new Dimension(0, 25));
        businessAddressField.setFont(inputFont);
        businessAddressField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(businessAddressField, "Complete business address");
        specificInfoSection.add(businessAddressField, specificGbc);

        // Date of Original Registration
        specificGbc.gridx = 0; specificGbc.gridy = 4; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel originalRegDateLabel = new JLabel("Date of Original Registration:");
        originalRegDateLabel.setFont(labelFont);
        specificInfoSection.add(originalRegDateLabel, specificGbc);

        // Date panel with combo boxes for original registration
        JPanel originalRegDatePanel = new JPanel();
        originalRegDatePanel.setPreferredSize(new Dimension(0, 25));
        originalRegDatePanel.setFont(inputFont);
        originalRegDatePanel.setLayout(new BoxLayout(originalRegDatePanel, BoxLayout.X_AXIS));
        originalRegDatePanel.setOpaque(false);

        JComboBox<String> originalRegYearCombo = new JComboBox<>();
        JComboBox<String> originalRegMonthCombo = new JComboBox<>();
        JComboBox<String> originalRegDayCombo = new JComboBox<>();

        originalRegYearCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        originalRegMonthCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        originalRegDayCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        originalRegDatePanel.add(new JLabel("Month:"));
        originalRegDatePanel.add(Box.createHorizontalStrut(5));
        originalRegDatePanel.add(originalRegMonthCombo);
        originalRegDatePanel.add(Box.createHorizontalStrut(10));

        originalRegDatePanel.add(new JLabel("Day:"));
        originalRegDatePanel.add(Box.createHorizontalStrut(5));
        originalRegDatePanel.add(originalRegDayCombo);
        originalRegDatePanel.add(Box.createHorizontalStrut(10));

        originalRegDatePanel.add(new JLabel("Year:"));
        originalRegDatePanel.add(Box.createHorizontalStrut(5));
        originalRegDatePanel.add(originalRegYearCombo);

        ComponentFactory.setupDateComboBoxes(originalRegYearCombo, originalRegMonthCombo, originalRegDayCombo);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        specificInfoSection.add(originalRegDatePanel, specificGbc);

        // Business Permit Number (Previous Year)
        specificGbc.gridx = 0; specificGbc.gridy = 5; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel previousPermitLabel = new JLabel("Business Permit Number (Previous Year):");
        previousPermitLabel.setFont(labelFont);
        specificInfoSection.add(previousPermitLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField previousPermitField = new JTextField(20);
        previousPermitField.setPreferredSize(new Dimension(0, 25));
        previousPermitField.setFont(inputFont);
        previousPermitField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(previousPermitField, "Enter previous business permit number");
        specificInfoSection.add(previousPermitField, specificGbc);

        // Ownership Type
        specificGbc.gridx = 0; specificGbc.gridy = 6; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel ownershipTypeLabel = new JLabel("Ownership Type:");
        ownershipTypeLabel.setFont(labelFont);
        specificInfoSection.add(ownershipTypeLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] ownershipTypeOptions = {"Select Ownership Type", "Sole Proprietorship", "Partnership", "Corporation", "Cooperative"};
        JComboBox<String> ownershipTypeCombo = new JComboBox<>(ownershipTypeOptions);
        ownershipTypeCombo.setPreferredSize(new Dimension(0, 25));
        ownershipTypeCombo.setFont(inputFont);
        ownershipTypeCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(ownershipTypeCombo, specificGbc);

        // DTI/SEC/Coop Registration Number
        specificGbc.gridx = 0; specificGbc.gridy = 7; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel registrationNumberLabel = new JLabel("DTI/SEC/Coop Registration Number:");
        registrationNumberLabel.setFont(labelFont);
        specificInfoSection.add(registrationNumberLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField registrationNumberField = new JTextField(20);
        registrationNumberField.setPreferredSize(new Dimension(0, 25));
        registrationNumberField.setFont(inputFont);
        registrationNumberField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(registrationNumberField, "Enter registration number (if available)");
        specificInfoSection.add(registrationNumberField, specificGbc);

        // Any Changes in Business Information
        specificGbc.gridx = 0; specificGbc.gridy = 8; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel changesLabel = new JLabel("Any Changes in Business Information?");
        changesLabel.setFont(labelFont);
        specificInfoSection.add(changesLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JPanel changesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        changesPanel.setOpaque(false);

        JRadioButton changesYesRadio = new JRadioButton("Yes");
        JRadioButton changesNoRadio = new JRadioButton("No");
        changesYesRadio.setFont(inputFont);
        changesNoRadio.setFont(inputFont);
        changesYesRadio.setOpaque(false);
        changesNoRadio.setOpaque(false);

        ButtonGroup changesGroup = new ButtonGroup();
        changesGroup.add(changesYesRadio);
        changesGroup.add(changesNoRadio);

        changesPanel.add(changesYesRadio);
        changesPanel.add(Box.createHorizontalStrut(20));
        changesPanel.add(changesNoRadio);

        specificInfoSection.add(changesPanel, specificGbc);

        // If Yes, please specify the changes (initially hidden)
        specificGbc.gridx = 0; specificGbc.gridy = 9; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        specificGbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel changesSpecifyLabel = new JLabel("If yes, please specify the changes:");
        changesSpecifyLabel.setFont(labelFont);
        changesSpecifyLabel.setVisible(false);
        specificInfoSection.add(changesSpecifyLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.BOTH; specificGbc.weightx = 1.0; specificGbc.weighty = 1.0;
        JTextArea changesSpecifyArea = new JTextArea(2, 20);
        changesSpecifyArea.setFont(inputFont);
        changesSpecifyArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        changesSpecifyArea.setLineWrap(true);
        changesSpecifyArea.setWrapStyleWord(true);
        changesSpecifyArea.setVisible(false);
        ComponentFactory.addPlaceholder(changesSpecifyArea, "Please specify what changes were made to the business information");

        JScrollPane changesSpecifyScrollPane = new JScrollPane(changesSpecifyArea);
        changesSpecifyScrollPane.setPreferredSize(new Dimension(0, 50));
        changesSpecifyScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        changesSpecifyScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        changesSpecifyScrollPane.setVisible(false);
        specificInfoSection.add(changesSpecifyScrollPane, specificGbc);

        // Changes radio button action listeners
        changesYesRadio.addActionListener(e -> {
            changesSpecifyLabel.setVisible(true);
            changesSpecifyScrollPane.setVisible(true);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        changesNoRadio.addActionListener(e -> {
            changesSpecifyLabel.setVisible(false);
            changesSpecifyScrollPane.setVisible(false);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        // Reset anchor and weighty for subsequent components
        specificGbc.anchor = GridBagConstraints.WEST;
        specificGbc.weighty = 0;

        // Declaration Checkbox
        specificGbc.gridx = 0; specificGbc.gridy = 10; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0; specificGbc.gridwidth = 2;
        JCheckBox declarationCheckbox = new JCheckBox("I confirm that the above business information is accurate and that this is a renewal application for a barangay business permit.");
        declarationCheckbox.setFont(inputFont);
        declarationCheckbox.setOpaque(false);
        specificInfoSection.add(declarationCheckbox, specificGbc);
        specificGbc.gridwidth = 1; // Reset gridwidth

        formPanel.add(specificInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Documents to Present Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;

        String[] documentOptions = {
                "Valid ID",
                "Community Tax Certificate (Cedula)",
                "Proof of Residency (e.g., utility bill, previous Barangay Certificate)",
                "Copy of Previous Barangay Business Permit",
                "DTI/SEC/Coop Certificate (if updated)",
                "Sketch or Photo of Business Premises (if address has changed)",
                "Receipt of Payment (if applicable)"
        };

        JPanel documentsSection = createDocumentsToPresentSection(documentOptions, sectionFont, inputFont);

        formPanel.add(documentsSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        return formPanel;
    }

    //Certificate for Business Closure Form
    public static JPanel createBusinessClosureForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);
        Font sectionFont = new Font("Trebuchet MS", Font.BOLD, 14);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Document Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel docInfoSection = createDocumentInformationSection();
        formPanel.add(docInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Personal Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel personalInfoSection = createPersonalInformationSection();
        formPanel.add(personalInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Specific Information for Application Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel specificInfoSection = new JPanel(new GridBagLayout());
        specificInfoSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Specific Document Information",
                TitledBorder.LEFT, TitledBorder.TOP, sectionFont
        ));
        specificInfoSection.setOpaque(false);

        GridBagConstraints specificGbc = new GridBagConstraints();
        specificGbc.insets = new Insets(5, 5, 5, 5);
        specificGbc.anchor = GridBagConstraints.WEST;

        // Business Name / Trade Name
        specificGbc.gridx = 0; specificGbc.gridy = 0; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel businessNameLabel = new JLabel("Business Name / Trade Name:");
        businessNameLabel.setFont(labelFont);
        specificInfoSection.add(businessNameLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField businessNameField = new JTextField(20);
        businessNameField.setPreferredSize(new Dimension(0, 25));
        businessNameField.setFont(inputFont);
        businessNameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(businessNameField, "Enter business name or trade name");
        specificInfoSection.add(businessNameField, specificGbc);

        // Type of Business
        specificGbc.gridx = 0; specificGbc.gridy = 1; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel businessTypeLabel = new JLabel("Type of Business:");
        businessTypeLabel.setFont(labelFont);
        specificInfoSection.add(businessTypeLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] businessTypeOptions = {"Select Type", "Sari-Sari Store", "Online Selling", "Food Service", "Others"};
        JComboBox<String> businessTypeCombo = new JComboBox<>(businessTypeOptions);
        businessTypeCombo.setPreferredSize(new Dimension(0, 25));
        businessTypeCombo.setFont(inputFont);
        businessTypeCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(businessTypeCombo, specificGbc);

        // If others, please specify (initially hidden) - Business Type
        specificGbc.gridx = 0; specificGbc.gridy = 2; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel businessTypeOthersLabel = new JLabel("If others, please specify:");
        businessTypeOthersLabel.setFont(labelFont);
        businessTypeOthersLabel.setVisible(false);
        specificInfoSection.add(businessTypeOthersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField businessTypeOthersField = new JTextField(20);
        businessTypeOthersField.setPreferredSize(new Dimension(0, 25));
        businessTypeOthersField.setFont(inputFont);
        businessTypeOthersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        businessTypeOthersField.setVisible(false);
        ComponentFactory.addPlaceholder(businessTypeOthersField, "Please specify the type of business");
        specificInfoSection.add(businessTypeOthersField, specificGbc);

        // Business Type combo action listener for conditional field
        businessTypeCombo.addActionListener(e -> {
            boolean showOthers = "Others".equals(businessTypeCombo.getSelectedItem());
            businessTypeOthersLabel.setVisible(showOthers);
            businessTypeOthersField.setVisible(showOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        // Business Address
        specificGbc.gridx = 0; specificGbc.gridy = 3; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel businessAddressLabel = new JLabel("Business Address:");
        businessAddressLabel.setFont(labelFont);
        specificInfoSection.add(businessAddressLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField businessAddressField = new JTextField(20);
        businessAddressField.setPreferredSize(new Dimension(0, 25));
        businessAddressField.setFont(inputFont);
        businessAddressField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(businessAddressField, "House No., Street, Barangay, Municipality/City, Province");
        specificInfoSection.add(businessAddressField, specificGbc);

        // Date of Business Closure
        specificGbc.gridx = 0; specificGbc.gridy = 4; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel closureDateLabel = new JLabel("Date of Business Closure:");
        closureDateLabel.setFont(labelFont);
        specificInfoSection.add(closureDateLabel, specificGbc);

        // Date panel with combo boxes
        JPanel closureDatePanel = new JPanel();
        closureDatePanel.setPreferredSize(new Dimension(0, 25));
        closureDatePanel.setFont(inputFont);
        closureDatePanel.setLayout(new BoxLayout(closureDatePanel, BoxLayout.X_AXIS));
        closureDatePanel.setOpaque(false);

        JComboBox<String> closureYearCombo = new JComboBox<>();
        JComboBox<String> closureMonthCombo = new JComboBox<>();
        JComboBox<String> closureDayCombo = new JComboBox<>();

        closureYearCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        closureMonthCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        closureDayCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        closureDatePanel.add(new JLabel("Month:"));
        closureDatePanel.add(Box.createHorizontalStrut(5));
        closureDatePanel.add(closureMonthCombo);
        closureDatePanel.add(Box.createHorizontalStrut(10));

        closureDatePanel.add(new JLabel("Day:"));
        closureDatePanel.add(Box.createHorizontalStrut(5));
        closureDatePanel.add(closureDayCombo);
        closureDatePanel.add(Box.createHorizontalStrut(10));

        closureDatePanel.add(new JLabel("Year:"));
        closureDatePanel.add(Box.createHorizontalStrut(5));
        closureDatePanel.add(closureYearCombo);

        // Initialize date combo boxes using your helper method
        ComponentFactory.setupDateComboBoxes(closureYearCombo, closureMonthCombo, closureDayCombo);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        specificInfoSection.add(closureDatePanel, specificGbc);

        // Reason for Closure
        specificGbc.gridx = 0; specificGbc.gridy = 5; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel closureReasonLabel = new JLabel("Reason for Closure:");
        closureReasonLabel.setFont(labelFont);
        specificInfoSection.add(closureReasonLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] closureReasonOptions = {"Select Reason", "Retirement", "Relocation", "Financial Difficulty", "Others"};
        JComboBox<String> closureReasonCombo = new JComboBox<>(closureReasonOptions);
        closureReasonCombo.setPreferredSize(new Dimension(0, 25));
        closureReasonCombo.setFont(inputFont);
        closureReasonCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(closureReasonCombo, specificGbc);

        // If others, please specify (initially hidden) - Closure Reason
        specificGbc.gridx = 0; specificGbc.gridy = 6; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel closureReasonOthersLabel = new JLabel("If others, please specify:");
        closureReasonOthersLabel.setFont(labelFont);
        closureReasonOthersLabel.setVisible(false);
        specificInfoSection.add(closureReasonOthersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField closureReasonOthersField = new JTextField(20);
        closureReasonOthersField.setPreferredSize(new Dimension(0, 25));
        closureReasonOthersField.setFont(inputFont);
        closureReasonOthersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        closureReasonOthersField.setVisible(false);
        ComponentFactory.addPlaceholder(closureReasonOthersField, "Please specify the reason for closure");
        specificInfoSection.add(closureReasonOthersField, specificGbc);

        // Closure Reason combo action listener for conditional field
        closureReasonCombo.addActionListener(e -> {
            boolean showOthers = "Others".equals(closureReasonCombo.getSelectedItem());
            closureReasonOthersLabel.setVisible(showOthers);
            closureReasonOthersField.setVisible(showOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        formPanel.add(specificInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Documents to Present Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;

        String[] documentOptions = {
                "Valid ID",
                "Community Tax Certificate (Cedula)",
                "Proof of Residency (e.g., utility bill, previous Barangay Certificate)",
                "Copy of Previous Barangay Business Permit",
                "DTI/SEC/Coop Certificate (if updated)",
                "Sketch or Photo of Business Premises (if address has changed)",
                "Receipt of Payment (if applicable)"
        };

        JPanel documentsSection = createDocumentsToPresentSection(documentOptions, sectionFont, inputFont);

        formPanel.add(documentsSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        return formPanel;
    }

    //Certificate for Calamity (Disaster) Form
    public static JPanel createCalamityCertificateForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);
        Font sectionFont = new Font("Trebuchet MS", Font.BOLD, 14);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Document Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel docInfoSection = createDocumentInformationSection();
        formPanel.add(docInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Personal Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel personalInfoSection = createPersonalInformationSection();
        formPanel.add(personalInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Specific Information for Application Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel specificInfoSection = new JPanel(new GridBagLayout());
        specificInfoSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Specific Document Information",
                TitledBorder.LEFT, TitledBorder.TOP, sectionFont
        ));
        specificInfoSection.setOpaque(false);

        GridBagConstraints specificGbc = new GridBagConstraints();
        specificGbc.insets = new Insets(5, 5, 5, 5);
        specificGbc.anchor = GridBagConstraints.WEST;

        // Type of Calamity Experienced
        specificGbc.gridx = 0; specificGbc.gridy = 0; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel calamityTypeLabel = new JLabel("Type of Calamity Experienced:");
        calamityTypeLabel.setFont(labelFont);
        specificInfoSection.add(calamityTypeLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] calamityTypeOptions = {"Select Type", "Typhoon", "Fire", "Flood", "Earthquake", "Others"};
        JComboBox<String> calamityTypeCombo = new JComboBox<>(calamityTypeOptions);
        calamityTypeCombo.setPreferredSize(new Dimension(0, 25));
        calamityTypeCombo.setFont(inputFont);
        calamityTypeCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(calamityTypeCombo, specificGbc);

        // If others, please specify (initially hidden) - Calamity Type
        specificGbc.gridx = 0; specificGbc.gridy = 1; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel calamityTypeOthersLabel = new JLabel("If others, please specify:");
        calamityTypeOthersLabel.setFont(labelFont);
        calamityTypeOthersLabel.setVisible(false);
        specificInfoSection.add(calamityTypeOthersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField calamityTypeOthersField = new JTextField(20);
        calamityTypeOthersField.setPreferredSize(new Dimension(0, 25));
        calamityTypeOthersField.setFont(inputFont);
        calamityTypeOthersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        calamityTypeOthersField.setVisible(false);
        ComponentFactory.addPlaceholder(calamityTypeOthersField, "Please specify the type of calamity");
        specificInfoSection.add(calamityTypeOthersField, specificGbc);

        // Calamity Type combo action listener for conditional field
        calamityTypeCombo.addActionListener(e -> {
            boolean showOthers = "Others".equals(calamityTypeCombo.getSelectedItem());
            calamityTypeOthersLabel.setVisible(showOthers);
            calamityTypeOthersField.setVisible(showOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        // Date of Calamity
        specificGbc.gridx = 0; specificGbc.gridy = 2; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel calamityDateLabel = new JLabel("Date of Calamity:");
        calamityDateLabel.setFont(labelFont);
        specificInfoSection.add(calamityDateLabel, specificGbc);

        // Date panel with combo boxes
        JPanel calamityDatePanel = new JPanel();
        calamityDatePanel.setPreferredSize(new Dimension(0, 25));
        calamityDatePanel.setFont(inputFont);
        calamityDatePanel.setLayout(new BoxLayout(calamityDatePanel, BoxLayout.X_AXIS));
        calamityDatePanel.setOpaque(false);

        JComboBox<String> calamityYearCombo = new JComboBox<>();
        JComboBox<String> calamityMonthCombo = new JComboBox<>();
        JComboBox<String> calamityDayCombo = new JComboBox<>();

        calamityYearCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        calamityMonthCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        calamityDayCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        calamityDatePanel.add(new JLabel("Month:"));
        calamityDatePanel.add(Box.createHorizontalStrut(5));
        calamityDatePanel.add(calamityMonthCombo);
        calamityDatePanel.add(Box.createHorizontalStrut(10));

        calamityDatePanel.add(new JLabel("Day:"));
        calamityDatePanel.add(Box.createHorizontalStrut(5));
        calamityDatePanel.add(calamityDayCombo);
        calamityDatePanel.add(Box.createHorizontalStrut(10));

        calamityDatePanel.add(new JLabel("Year:"));
        calamityDatePanel.add(Box.createHorizontalStrut(5));
        calamityDatePanel.add(calamityYearCombo);

        // Initialize date combo boxes using your helper method
        ComponentFactory.setupDateComboBoxes(calamityYearCombo, calamityMonthCombo, calamityDayCombo);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        specificInfoSection.add(calamityDatePanel, specificGbc);

        // Nature of Damage or Loss
        specificGbc.gridx = 0; specificGbc.gridy = 3; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        specificGbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel damageNatureLabel = new JLabel("Nature of Damage or Loss:");
        damageNatureLabel.setFont(labelFont);
        specificInfoSection.add(damageNatureLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.BOTH; specificGbc.weightx = 1.0; specificGbc.weighty = 1.0;
        JTextArea damageNatureArea = new JTextArea(3, 20);
        damageNatureArea.setFont(inputFont);
        damageNatureArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        damageNatureArea.setLineWrap(true);
        damageNatureArea.setWrapStyleWord(true);
        ComponentFactory.addPlaceholder(damageNatureArea, "Describe the damage or loss (e.g., house damaged, livelihood lost, injured family member)");

        JScrollPane damageNatureScrollPane = new JScrollPane(damageNatureArea);
        damageNatureScrollPane.setPreferredSize(new Dimension(0, 70));
        damageNatureScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        damageNatureScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        specificInfoSection.add(damageNatureScrollPane, specificGbc);

        // Reset anchor and weighty for subsequent components
        specificGbc.anchor = GridBagConstraints.WEST;
        specificGbc.weighty = 0;

        formPanel.add(specificInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Documents to Present Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;

        String[] documentOptions = {
                "Valid ID",
                "Community Tax Certificate (Cedula)",
                "Proof of Residency (e.g., utility bill, previous Barangay Certificate)",
                "Photo or Sketch of Affected Property (if available)",
                "Incident Report / Police or Fire Report (if applicable)",
                "Affidavit of Damage or Loss (if available)",
                "Receipt of Payment (if applicable)"
        };

        JPanel documentsSection = createDocumentsToPresentSection(documentOptions, sectionFont, inputFont);

        formPanel.add(documentsSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        return formPanel;
    }

    //Certificate for No Objection Form
    public static JPanel createNoObjectionForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);
        Font sectionFont = new Font("Trebuchet MS", Font.BOLD, 14);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Document Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel docInfoSection = createDocumentInformationSection();
        formPanel.add(docInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Personal Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel personalInfoSection = createPersonalInformationSection();
        formPanel.add(personalInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Specific Information for Application Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel specificInfoSection = new JPanel(new GridBagLayout());
        specificInfoSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Specific Document Information",
                TitledBorder.LEFT, TitledBorder.TOP, sectionFont
        ));
        specificInfoSection.setOpaque(false);

        GridBagConstraints specificGbc = new GridBagConstraints();
        specificGbc.insets = new Insets(5, 5, 5, 5);
        specificGbc.anchor = GridBagConstraints.WEST;

        // Name of Activity/Entity
        specificGbc.gridx = 0; specificGbc.gridy = 0; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel activityEntityLabel = new JLabel("Name of Activity/Entity:");
        activityEntityLabel.setFont(labelFont);
        specificInfoSection.add(activityEntityLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField activityEntityField = new JTextField(20);
        activityEntityField.setPreferredSize(new Dimension(0, 25));
        activityEntityField.setFont(inputFont);
        activityEntityField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(activityEntityField, "Enter name of activity or entity");
        specificInfoSection.add(activityEntityField, specificGbc);

        // Nature of Request or Activity
        specificGbc.gridx = 0; specificGbc.gridy = 1; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel natureLabel = new JLabel("Nature of Request or Activity:");
        natureLabel.setFont(labelFont);
        specificInfoSection.add(natureLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] natureOptions = {"Select Nature", "Renovation", "Utility Installation", "Small Business Setup", "Others"};
        JComboBox<String> natureCombo = new JComboBox<>(natureOptions);
        natureCombo.setPreferredSize(new Dimension(0, 25));
        natureCombo.setFont(inputFont);
        natureCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(natureCombo, specificGbc);

        // If others, please specify (initially hidden)
        specificGbc.gridx = 0; specificGbc.gridy = 2; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel natureOthersLabel = new JLabel("If others, please specify:");
        natureOthersLabel.setFont(labelFont);
        natureOthersLabel.setVisible(false);
        specificInfoSection.add(natureOthersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField natureOthersField = new JTextField(20);
        natureOthersField.setPreferredSize(new Dimension(0, 25));
        natureOthersField.setFont(inputFont);
        natureOthersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        natureOthersField.setVisible(false);
        ComponentFactory.addPlaceholder(natureOthersField, "Please specify the nature of activity");
        specificInfoSection.add(natureOthersField, specificGbc);

        // Nature combo action listener for conditional field
        natureCombo.addActionListener(e -> {
            boolean showOthers = "Others".equals(natureCombo.getSelectedItem());
            natureOthersLabel.setVisible(showOthers);
            natureOthersField.setVisible(showOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        // Location of Activity / Concern
        specificGbc.gridx = 0; specificGbc.gridy = 3; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel locationLabel = new JLabel("Location of Activity / Concern:");
        locationLabel.setFont(labelFont);
        specificInfoSection.add(locationLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField locationField = new JTextField(20);
        locationField.setPreferredSize(new Dimension(0, 25));
        locationField.setFont(inputFont);
        locationField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(locationField, "Specify the exact location of the activity or concern");
        specificInfoSection.add(locationField, specificGbc);

        formPanel.add(specificInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Documents to Present Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;

        String[] documentOptions = {
                "Valid ID",
                "Community Tax Certificate (Cedula)",
                "Proof of Residency (e.g., utility bill, previous Barangay Certificate)",
                "Sketch or Photo of Affected Property / Area (if available)",
                "Endorsement or Letter of Intent (if applicable)",
                "Receipt of Payment (if applicable)"
        };

        JPanel documentsSection = createDocumentsToPresentSection(documentOptions, sectionFont, inputFont);

        formPanel.add(documentsSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        return formPanel;
    }

    //Certificate for PWD's Form
    public static JPanel createPWDCertificateForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);
        Font sectionFont = new Font("Trebuchet MS", Font.BOLD, 14);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Document Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel docInfoSection = createDocumentInformationSection();
        formPanel.add(docInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Personal Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel personalInfoSection = createPersonalInformationSection();
        formPanel.add(personalInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Specific Information for Application Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel specificInfoSection = new JPanel(new GridBagLayout());
        specificInfoSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Specific Document Information",
                TitledBorder.LEFT, TitledBorder.TOP, sectionFont
        ));
        specificInfoSection.setOpaque(false);

        GridBagConstraints specificGbc = new GridBagConstraints();
        specificGbc.insets = new Insets(5, 5, 5, 5);
        specificGbc.anchor = GridBagConstraints.WEST;

        // Type of Disability
        specificGbc.gridx = 0; specificGbc.gridy = 0; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel disabilityTypeLabel = new JLabel("Type of Disability:");
        disabilityTypeLabel.setFont(labelFont);
        specificInfoSection.add(disabilityTypeLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] disabilityOptions = {"Select Type", "Physical", "Visual", "Hearing", "Mental", "Intellectual", "Learning", "Others"};
        JComboBox<String> disabilityTypeCombo = new JComboBox<>(disabilityOptions);
        disabilityTypeCombo.setPreferredSize(new Dimension(0, 25));
        disabilityTypeCombo.setFont(inputFont);
        disabilityTypeCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(disabilityTypeCombo, specificGbc);

        // If others, please specify (initially hidden)
        specificGbc.gridx = 0; specificGbc.gridy = 1; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel disabilityTypeOthersLabel = new JLabel("If others, please specify:");
        disabilityTypeOthersLabel.setFont(labelFont);
        disabilityTypeOthersLabel.setVisible(false);
        specificInfoSection.add(disabilityTypeOthersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField disabilityTypeOthersField = new JTextField(20);
        disabilityTypeOthersField.setPreferredSize(new Dimension(0, 25));
        disabilityTypeOthersField.setFont(inputFont);
        disabilityTypeOthersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        disabilityTypeOthersField.setVisible(false);
        ComponentFactory.addPlaceholder(disabilityTypeOthersField, "Please specify the type of disability");
        specificInfoSection.add(disabilityTypeOthersField, specificGbc);

        // Disability type combo action listener for conditional field
        disabilityTypeCombo.addActionListener(e -> {
            boolean showOthers = "Others".equals(disabilityTypeCombo.getSelectedItem());
            disabilityTypeOthersLabel.setVisible(showOthers);
            disabilityTypeOthersField.setVisible(showOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        // Cause of Disability
        specificGbc.gridx = 0; specificGbc.gridy = 2; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel causeLabel = new JLabel("Cause of Disability:");
        causeLabel.setFont(labelFont);
        specificInfoSection.add(causeLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField causeField = new JTextField(20);
        causeField.setPreferredSize(new Dimension(0, 25));
        causeField.setFont(inputFont);
        causeField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(causeField, "e.g., congenital, acquired through illness/accident");
        specificInfoSection.add(causeField, specificGbc);

        // Mobility Aid or Assistive Device Used
        specificGbc.gridx = 0; specificGbc.gridy = 3; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel mobilityAidLabel = new JLabel("Mobility Aid or Assistive Device Used:");
        mobilityAidLabel.setFont(labelFont);
        specificInfoSection.add(mobilityAidLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField mobilityAidField = new JTextField(20);
        mobilityAidField.setPreferredSize(new Dimension(0, 25));
        mobilityAidField.setFont(inputFont);
        mobilityAidField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(mobilityAidField, "e.g., wheelchair, cane, hearing aid, etc. (if any)");
        specificInfoSection.add(mobilityAidField, specificGbc);

        // Require Constant Assistance
        specificGbc.gridx = 0; specificGbc.gridy = 4; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel assistanceLabel = new JLabel("Do you require constant assistance from another person?");
        assistanceLabel.setFont(labelFont);
        specificInfoSection.add(assistanceLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] assistanceOptions = {"Select Option", "Yes", "No"};
        JComboBox<String> assistanceCombo = new JComboBox<>(assistanceOptions);
        assistanceCombo.setPreferredSize(new Dimension(0, 25));
        assistanceCombo.setFont(inputFont);
        assistanceCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(assistanceCombo, specificGbc);

        formPanel.add(specificInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Documents to Present Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;

        String[] documentOptions = {
                "Valid ID",
                "Barangay Clearance / Certificate of Residency (if applicable)",
                "Medical Certificate / Clinical Abstract (issued by a licensed physician)",
                "1×1 or 2×2 photo (recent)",
                "Receipt of Payment (if applicable)"
        };

        JPanel documentsSection = createDocumentsToPresentSection(documentOptions, sectionFont, inputFont);

        formPanel.add(documentsSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        return formPanel;
    }

    //Certificate for Senior Citizens Form
    public static JPanel createSeniorCitizensForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);
        Font sectionFont = new Font("Trebuchet MS", Font.BOLD, 14);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Document Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel docInfoSection = createDocumentInformationSection();
        formPanel.add(docInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Personal Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel personalInfoSection = createPersonalInformationSection();
        formPanel.add(personalInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Specific Information for Application Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel specificInfoSection = new JPanel(new GridBagLayout());
        specificInfoSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Specific Document Information",
                TitledBorder.LEFT, TitledBorder.TOP, sectionFont
        ));
        specificInfoSection.setOpaque(false);

        GridBagConstraints specificGbc = new GridBagConstraints();
        specificGbc.insets = new Insets(5, 5, 5, 5);
        specificGbc.anchor = GridBagConstraints.WEST;

        // Purpose of Certificate
        specificGbc.gridx = 0; specificGbc.gridy = 0; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel purposeLabel = new JLabel("Purpose of Certificate:");
        purposeLabel.setFont(labelFont);
        specificInfoSection.add(purposeLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] purposeOptions = {"Select Purpose", "OSCA ID Application", "Pension", "Medical Assistance", "Others"};
        JComboBox<String> purposeCombo = new JComboBox<>(purposeOptions);
        purposeCombo.setPreferredSize(new Dimension(0, 25));
        purposeCombo.setFont(inputFont);
        purposeCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(purposeCombo, specificGbc);

        // If others, please specify (initially hidden) - Purpose
        specificGbc.gridx = 0; specificGbc.gridy = 1; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel purposeOthersLabel = new JLabel("If others, please specify:");
        purposeOthersLabel.setFont(labelFont);
        purposeOthersLabel.setVisible(false);
        specificInfoSection.add(purposeOthersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField purposeOthersField = new JTextField(20);
        purposeOthersField.setPreferredSize(new Dimension(0, 25));
        purposeOthersField.setFont(inputFont);
        purposeOthersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        purposeOthersField.setVisible(false);
        ComponentFactory.addPlaceholder(purposeOthersField, "Please specify the purpose");
        specificInfoSection.add(purposeOthersField, specificGbc);

        // Purpose combo action listener for conditional field
        purposeCombo.addActionListener(e -> {
            boolean showOthers = "Others".equals(purposeCombo.getSelectedItem());
            purposeOthersLabel.setVisible(showOthers);
            purposeOthersField.setVisible(showOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        // PhilHealth Number
        specificGbc.gridx = 0; specificGbc.gridy = 2; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel philHealthLabel = new JLabel("PhilHealth Number:");
        philHealthLabel.setFont(labelFont);
        specificInfoSection.add(philHealthLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField philHealthField = new JTextField(20);
        philHealthField.setPreferredSize(new Dimension(0, 25));
        philHealthField.setFont(inputFont);
        philHealthField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(philHealthField, "PhilHealth Number (if applicable)");
        specificInfoSection.add(philHealthField, specificGbc);

        // Receiving Institution
        specificGbc.gridx = 0; specificGbc.gridy = 3; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel institutionLabel = new JLabel("Receiving Institution:");
        institutionLabel.setFont(labelFont);
        specificInfoSection.add(institutionLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] institutionOptions = {"Select Institution", "DSWD", "Hospital", "OSCA Office", "Others"};
        JComboBox<String> institutionCombo = new JComboBox<>(institutionOptions);
        institutionCombo.setPreferredSize(new Dimension(0, 25));
        institutionCombo.setFont(inputFont);
        institutionCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(institutionCombo, specificGbc);

        // If others, please specify (initially hidden) - Institution
        specificGbc.gridx = 0; specificGbc.gridy = 4; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel institutionOthersLabel = new JLabel("If others, please specify:");
        institutionOthersLabel.setFont(labelFont);
        institutionOthersLabel.setVisible(false);
        specificInfoSection.add(institutionOthersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField institutionOthersField = new JTextField(20);
        institutionOthersField.setPreferredSize(new Dimension(0, 25));
        institutionOthersField.setFont(inputFont);
        institutionOthersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        institutionOthersField.setVisible(false);
        ComponentFactory.addPlaceholder(institutionOthersField, "Please specify the institution");
        specificInfoSection.add(institutionOthersField, specificGbc);

        // Institution combo action listener for conditional field
        institutionCombo.addActionListener(e -> {
            boolean showOthers = "Others".equals(institutionCombo.getSelectedItem());
            institutionOthersLabel.setVisible(showOthers);
            institutionOthersField.setVisible(showOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        formPanel.add(specificInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Documents to Present Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;

        String[] documentOptions = {
                "Valid ID",
                "Proof of Residency (e.g., utility bill, previous barangay certificate)",
                "Receipt of Payment (if applicable)"
        };

        JPanel documentsSection = createDocumentsToPresentSection(documentOptions, sectionFont, inputFont);

        formPanel.add(documentsSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        return formPanel;
    }

    //Certificate for Solo Parent Form
    public static JPanel createSoloParentForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);
        Font sectionFont = new Font("Trebuchet MS", Font.BOLD, 14);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Document Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel docInfoSection = createDocumentInformationSection();
        formPanel.add(docInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Personal Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel personalInfoSection = createPersonalInformationSection();
        formPanel.add(personalInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Specific Information for Application Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel specificInfoSection = new JPanel(new GridBagLayout());
        specificInfoSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Specific Document Information",
                TitledBorder.LEFT, TitledBorder.TOP, sectionFont
        ));
        specificInfoSection.setOpaque(false);

        GridBagConstraints specificGbc = new GridBagConstraints();
        specificGbc.insets = new Insets(5, 5, 5, 5);
        specificGbc.anchor = GridBagConstraints.WEST;

        // Civil Status
        specificGbc.gridx = 0; specificGbc.gridy = 0; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel civilStatusLabel = new JLabel("Civil Status:");
        civilStatusLabel.setFont(labelFont);
        specificInfoSection.add(civilStatusLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] civilStatusOptions = {"Select Civil Status", "Single", "Married", "Widowed", "Separated", "Annulled"};
        JComboBox<String> civilStatusCombo = new JComboBox<>(civilStatusOptions);
        civilStatusCombo.setPreferredSize(new Dimension(0, 25));
        civilStatusCombo.setFont(inputFont);
        civilStatusCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(civilStatusCombo, specificGbc);

        // Number of Dependent Children
        specificGbc.gridx = 0; specificGbc.gridy = 1; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel dependentChildrenLabel = new JLabel("Number of Dependent Children:");
        dependentChildrenLabel.setFont(labelFont);
        specificInfoSection.add(dependentChildrenLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField dependentChildrenField = new JTextField(20);
        dependentChildrenField.setPreferredSize(new Dimension(0, 25));
        dependentChildrenField.setFont(inputFont);
        dependentChildrenField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(dependentChildrenField, "Enter number of dependent children");
        specificInfoSection.add(dependentChildrenField, specificGbc);

        // Reason for Solo Parenthood
        specificGbc.gridx = 0; specificGbc.gridy = 2; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel reasonLabel = new JLabel("Reason for Solo Parenthood:");
        reasonLabel.setFont(labelFont);
        specificInfoSection.add(reasonLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] reasonOptions = {"Select Reason", "Deceased spouse", "Legal separation", "Spouse incarcerated", "Incapacitated partner", "Unmarried parent", "Other"};
        JComboBox<String> reasonCombo = new JComboBox<>(reasonOptions);
        reasonCombo.setPreferredSize(new Dimension(0, 25));
        reasonCombo.setFont(inputFont);
        reasonCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(reasonCombo, specificGbc);

        // If others, please specify (initially hidden) - Reason
        specificGbc.gridx = 0; specificGbc.gridy = 3; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel reasonOthersLabel = new JLabel("If others, please specify:");
        reasonOthersLabel.setFont(labelFont);
        reasonOthersLabel.setVisible(false);
        specificInfoSection.add(reasonOthersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField reasonOthersField = new JTextField(20);
        reasonOthersField.setPreferredSize(new Dimension(0, 25));
        reasonOthersField.setFont(inputFont);
        reasonOthersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        reasonOthersField.setVisible(false);
        ComponentFactory.addPlaceholder(reasonOthersField, "Please specify the reason");
        specificInfoSection.add(reasonOthersField, specificGbc);

        // Reason combo action listener for conditional field
        reasonCombo.addActionListener(e -> {
            boolean showOthers = "Other".equals(reasonCombo.getSelectedItem());
            reasonOthersLabel.setVisible(showOthers);
            reasonOthersField.setVisible(showOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        formPanel.add(specificInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Documents to Present Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;

        String[] documentOptions = {
                "Valid ID",
                "Proof of Residency (e.g., utility bill, previous barangay certificate)",
                "Birth Certificate/s of child/children",
                "Death Certificate / Legal Separation Papers / Certificate of No Marriage (as applicable)",
                "Receipt of Payment (if applicable)"
        };

        JPanel documentsSection = createDocumentsToPresentSection(documentOptions, sectionFont, inputFont);

        formPanel.add(documentsSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        return formPanel;
    }

    //Certificate of Good Moral Character Form
    public static JPanel createGoodMoralCharacterForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);
        Font sectionFont = new Font("Trebuchet MS", Font.BOLD, 14);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Document Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel docInfoSection = createDocumentInformationSection();
        formPanel.add(docInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Personal Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel personalInfoSection = createPersonalInformationSection();
        formPanel.add(personalInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Specific Information for Application Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel specificInfoSection = new JPanel(new GridBagLayout());
        specificInfoSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Specific Document Information",
                TitledBorder.LEFT, TitledBorder.TOP, sectionFont
        ));
        specificInfoSection.setOpaque(false);

        GridBagConstraints specificGbc = new GridBagConstraints();
        specificGbc.insets = new Insets(5, 5, 5, 5);
        specificGbc.anchor = GridBagConstraints.WEST;

        // Purpose
        specificGbc.gridx = 0; specificGbc.gridy = 0; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel purposeLabel = new JLabel("Purpose:");
        purposeLabel.setFont(labelFont);
        specificInfoSection.add(purposeLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] purposeOptions = {"Select Purpose", "Personal Reference", "Educational Purposes", "Job Application", "Government Mandate", "Others"};
        JComboBox<String> purposeCombo = new JComboBox<>(purposeOptions);
        purposeCombo.setPreferredSize(new Dimension(0, 25));
        purposeCombo.setFont(inputFont);
        purposeCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(purposeCombo, specificGbc);

        // If others, please specify (initially hidden) - Purpose
        specificGbc.gridx = 0; specificGbc.gridy = 1; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel purposeOthersLabel = new JLabel("If others, please specify:");
        purposeOthersLabel.setFont(labelFont);
        purposeOthersLabel.setVisible(false);
        specificInfoSection.add(purposeOthersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField purposeOthersField = new JTextField(20);
        purposeOthersField.setPreferredSize(new Dimension(0, 25));
        purposeOthersField.setFont(inputFont);
        purposeOthersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        purposeOthersField.setVisible(false);
        ComponentFactory.addPlaceholder(purposeOthersField, "Please specify the purpose");
        specificInfoSection.add(purposeOthersField, specificGbc);

        // Purpose combo action listener for conditional field
        purposeCombo.addActionListener(e -> {
            boolean showOthers = "Others".equals(purposeCombo.getSelectedItem());
            purposeOthersLabel.setVisible(showOthers);
            purposeOthersField.setVisible(showOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        formPanel.add(specificInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Documents to Present Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;

        String[] documentOptions = {
                "Valid ID",
                "Barangay Clearance / Certificate of Residency",
                "Endorsement Letter or Request Form (if requested by institution)",
                "Receipt of Payment (if applicable)"
        };

        JPanel documentsSection = createDocumentsToPresentSection(documentOptions, sectionFont, inputFont);

        formPanel.add(documentsSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        return formPanel;
    }

    //Certificate of Indigency Form
    public static JPanel createIndigencyForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);
        Font sectionFont = new Font("Trebuchet MS", Font.BOLD, 14);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Document Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel docInfoSection = createDocumentInformationSection();
        formPanel.add(docInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Personal Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel personalInfoSection = createPersonalInformationSection();
        formPanel.add(personalInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Specific Information for Application Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel specificInfoSection = new JPanel(new GridBagLayout());
        specificInfoSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Specific Document Information",
                TitledBorder.LEFT, TitledBorder.TOP, sectionFont
        ));
        specificInfoSection.setOpaque(false);

        GridBagConstraints specificGbc = new GridBagConstraints();
        specificGbc.insets = new Insets(5, 5, 5, 5);
        specificGbc.anchor = GridBagConstraints.WEST;

        // Purpose
        specificGbc.gridx = 0; specificGbc.gridy = 0; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel purposeLabel = new JLabel("Purpose:");
        purposeLabel.setFont(labelFont);
        specificInfoSection.add(purposeLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] purposeOptions = {"Select Purpose", "Medical Assistance", "Scholarship", "Legal Aid", "School Requirement", "Others"};
        JComboBox<String> purposeCombo = new JComboBox<>(purposeOptions);
        purposeCombo.setPreferredSize(new Dimension(0, 25));
        purposeCombo.setFont(inputFont);
        purposeCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(purposeCombo, specificGbc);

        // If others, please specify (initially hidden) - Purpose
        specificGbc.gridx = 0; specificGbc.gridy = 1; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel purposeOthersLabel = new JLabel("If others, please specify:");
        purposeOthersLabel.setFont(labelFont);
        purposeOthersLabel.setVisible(false);
        specificInfoSection.add(purposeOthersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField purposeOthersField = new JTextField(20);
        purposeOthersField.setPreferredSize(new Dimension(0, 25));
        purposeOthersField.setFont(inputFont);
        purposeOthersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        purposeOthersField.setVisible(false);
        ComponentFactory.addPlaceholder(purposeOthersField, "Please specify the purpose");
        specificInfoSection.add(purposeOthersField, specificGbc);

        // Purpose combo action listener for conditional field
        purposeCombo.addActionListener(e -> {
            boolean showOthers = "Others".equals(purposeCombo.getSelectedItem());
            purposeOthersLabel.setVisible(showOthers);
            purposeOthersField.setVisible(showOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        formPanel.add(specificInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Documents to Present Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;

        String[] documentOptions = {
                "Valid ID",
                "Barangay Clearance / Certificate of Residency",
                "Proof of Informal Work / No Income (if applicable)",
                "Receipt of Payment (if applicable)"
        };

        JPanel documentsSection = createDocumentsToPresentSection(documentOptions, sectionFont, inputFont);

        formPanel.add(documentsSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        return formPanel;
    }

    //Certificate of Residency Form
    public static JPanel createResidencyForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);
        Font sectionFont = new Font("Trebuchet MS", Font.BOLD, 14);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Document Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel docInfoSection = createDocumentInformationSection();
        formPanel.add(docInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Personal Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel personalInfoSection = createPersonalInformationSection();
        formPanel.add(personalInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Specific Information for Application Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel specificInfoSection = new JPanel(new GridBagLayout());
        specificInfoSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Specific Document Information",
                TitledBorder.LEFT, TitledBorder.TOP, sectionFont
        ));
        specificInfoSection.setOpaque(false);

        GridBagConstraints specificGbc = new GridBagConstraints();
        specificGbc.insets = new Insets(5, 5, 5, 5);
        specificGbc.anchor = GridBagConstraints.WEST;

        // Years of Residency
        specificGbc.gridx = 0; specificGbc.gridy = 0; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel yearsLabel = new JLabel("Years of Residency:");
        yearsLabel.setFont(labelFont);
        specificInfoSection.add(yearsLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField yearsField = new JTextField(20);
        yearsField.setPreferredSize(new Dimension(0, 25));
        yearsField.setFont(inputFont);
        yearsField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(yearsField, "Enter number of years");
        specificInfoSection.add(yearsField, specificGbc);

        // Residency Type (Radio buttons)
        specificGbc.gridx = 0; specificGbc.gridy = 1; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel residencyTypeLabel = new JLabel("Residency Type:");
        residencyTypeLabel.setFont(labelFont);
        specificInfoSection.add(residencyTypeLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.X_AXIS));
        radioPanel.setOpaque(false);

        ButtonGroup residencyGroup = new ButtonGroup();
        JRadioButton permanentRadio = new JRadioButton("Permanent");
        JRadioButton temporaryRadio = new JRadioButton("Temporary");

        permanentRadio.setFont(inputFont);
        temporaryRadio.setFont(inputFont);
        permanentRadio.setOpaque(false);
        temporaryRadio.setOpaque(false);

        residencyGroup.add(permanentRadio);
        residencyGroup.add(temporaryRadio);

        radioPanel.add(permanentRadio);
        radioPanel.add(Box.createHorizontalStrut(20));
        radioPanel.add(temporaryRadio);

        specificInfoSection.add(radioPanel, specificGbc);

        // Purpose
        specificGbc.gridx = 0; specificGbc.gridy = 2; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel purposeLabel = new JLabel("Purpose:");
        purposeLabel.setFont(labelFont);
        specificInfoSection.add(purposeLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] purposeOptions = {"Select Purpose", "Voter Registration", "School", "Employment", "Business", "Others"};
        JComboBox<String> purposeCombo = new JComboBox<>(purposeOptions);
        purposeCombo.setPreferredSize(new Dimension(0, 25));
        purposeCombo.setFont(inputFont);
        purposeCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(purposeCombo, specificGbc);

        // If others, please specify (initially hidden) - Purpose
        specificGbc.gridx = 0; specificGbc.gridy = 3; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel purposeOthersLabel = new JLabel("If others, please specify:");
        purposeOthersLabel.setFont(labelFont);
        purposeOthersLabel.setVisible(false);
        specificInfoSection.add(purposeOthersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField purposeOthersField = new JTextField(20);
        purposeOthersField.setPreferredSize(new Dimension(0, 25));
        purposeOthersField.setFont(inputFont);
        purposeOthersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        purposeOthersField.setVisible(false);
        ComponentFactory.addPlaceholder(purposeOthersField, "Please specify the purpose");
        specificInfoSection.add(purposeOthersField, specificGbc);

        // Purpose combo action listener for conditional field
        purposeCombo.addActionListener(e -> {
            boolean showOthers = "Others".equals(purposeCombo.getSelectedItem());
            purposeOthersLabel.setVisible(showOthers);
            purposeOthersField.setVisible(showOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        formPanel.add(specificInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Documents to Present Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;

        String[] documentOptions = {
                "Valid ID",
                "Barangay Clearance / Certificate of Residency",
                "Community Tax Certificate (Cedula)",
                "Receipt of Payment (if applicable)"
        };

        JPanel documentsSection = createDocumentsToPresentSection(documentOptions, sectionFont, inputFont);

        formPanel.add(documentsSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        return formPanel;
    }

    //Certificate for Community Tax Certificate Form (Cedula)
    public static JPanel createCommunityTaxCertificateForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);
        Font sectionFont = new Font("Trebuchet MS", Font.BOLD, 14);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Document Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel docInfoSection = createDocumentInformationSection();
        formPanel.add(docInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Personal Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel personalInfoSection = createPersonalInformationSection();
        formPanel.add(personalInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Specific Information for Application Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel specificInfoSection = new JPanel(new GridBagLayout());
        specificInfoSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Specific Document Information",
                TitledBorder.LEFT, TitledBorder.TOP, sectionFont
        ));
        specificInfoSection.setOpaque(false);

        GridBagConstraints specificGbc = new GridBagConstraints();
        specificGbc.insets = new Insets(5, 5, 5, 5);
        specificGbc.anchor = GridBagConstraints.WEST;

        // Sex (Radio Buttons)
        specificGbc.gridx = 0; specificGbc.gridy = 0; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel sexLabel = new JLabel("Sex:");
        sexLabel.setFont(labelFont);
        specificInfoSection.add(sexLabel, specificGbc);

        JPanel sexPanel = new JPanel();
        sexPanel.setLayout(new BoxLayout(sexPanel, BoxLayout.X_AXIS));
        sexPanel.setOpaque(false);

        ButtonGroup sexGroup = new ButtonGroup();
        JRadioButton maleRadio = new JRadioButton("Male");
        JRadioButton femaleRadio = new JRadioButton("Female");
        JRadioButton notSayRadio = new JRadioButton("Rather not say");

        maleRadio.setFont(inputFont);
        femaleRadio.setFont(inputFont);
        notSayRadio.setFont(inputFont);
        maleRadio.setOpaque(false);
        femaleRadio.setOpaque(false);
        notSayRadio.setOpaque(false);

        sexGroup.add(maleRadio);
        sexGroup.add(femaleRadio);
        sexGroup.add(notSayRadio);

        sexPanel.add(maleRadio);
        sexPanel.add(Box.createHorizontalStrut(15));
        sexPanel.add(femaleRadio);
        sexPanel.add(Box.createHorizontalStrut(15));
        sexPanel.add(notSayRadio);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        specificInfoSection.add(sexPanel, specificGbc);

        // Civil Status
        specificGbc.gridx = 0; specificGbc.gridy = 1; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel civilStatusLabel = new JLabel("Civil Status:");
        civilStatusLabel.setFont(labelFont);
        specificInfoSection.add(civilStatusLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] civilStatusOptions = {"Select Civil Status", "Single", "Married", "Widowed", "Separated"};
        JComboBox<String> civilStatusCombo = new JComboBox<>(civilStatusOptions);
        civilStatusCombo.setPreferredSize(new Dimension(0, 25));
        civilStatusCombo.setFont(inputFont);
        civilStatusCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(civilStatusCombo, specificGbc);

        // Birthplace
        specificGbc.gridx = 0; specificGbc.gridy = 2; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel birthplaceLabel = new JLabel("Birthplace:");
        birthplaceLabel.setFont(labelFont);
        specificInfoSection.add(birthplaceLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField birthplaceField = new JTextField(20);
        birthplaceField.setPreferredSize(new Dimension(0, 25));
        birthplaceField.setFont(inputFont);
        birthplaceField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(birthplaceField, "City/Municipality, Province, Country");
        specificInfoSection.add(birthplaceField, specificGbc);

        // Purpose
        specificGbc.gridx = 0; specificGbc.gridy = 3; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel purposeLabel = new JLabel("Purpose:");
        purposeLabel.setFont(labelFont);
        specificInfoSection.add(purposeLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] purposeOptions = {
                "Select Purpose",
                "Proof of Identification",
                "Employment",
                "Business Registration/Renewal",
                "Notarization of Documents",
                "Filing Income Tax Returns",
                "Government Transactions/Permits",
                "Others"
        };
        JComboBox<String> purposeCombo = new JComboBox<>(purposeOptions);
        purposeCombo.setPreferredSize(new Dimension(0, 25));
        purposeCombo.setFont(inputFont);
        purposeCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(purposeCombo, specificGbc);

        // If others, please specify (initially hidden)
        specificGbc.gridx = 0; specificGbc.gridy = 4; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel othersLabel = new JLabel("If others, please specify:");
        othersLabel.setFont(labelFont);
        othersLabel.setVisible(false);
        specificInfoSection.add(othersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField othersField = new JTextField(20);
        othersField.setPreferredSize(new Dimension(0, 25));
        othersField.setFont(inputFont);
        othersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        othersField.setVisible(false);
        ComponentFactory.addPlaceholder(othersField, "Please specify your purpose");
        specificInfoSection.add(othersField, specificGbc);

        // Purpose combo action listener for conditional field
        purposeCombo.addActionListener(e -> {
            boolean showOthers = "Others".equals(purposeCombo.getSelectedItem());
            othersLabel.setVisible(showOthers);
            othersField.setVisible(showOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        // Business Income
        specificGbc.gridx = 0; specificGbc.gridy = 5; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel businessIncomeLabel = new JLabel("Business Income:");
        businessIncomeLabel.setFont(labelFont);
        specificInfoSection.add(businessIncomeLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField businessIncomeField = new JTextField(20);
        businessIncomeField.setPreferredSize(new Dimension(0, 25));
        businessIncomeField.setFont(inputFont);
        businessIncomeField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(businessIncomeField, "Gross business earnings last year");
        specificInfoSection.add(businessIncomeField, specificGbc);

        // Professional/Occupational Income
        specificGbc.gridx = 0; specificGbc.gridy = 6; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel professionalIncomeLabel = new JLabel("Professional/Occupational Income:");
        professionalIncomeLabel.setFont(labelFont);
        specificInfoSection.add(professionalIncomeLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField professionalIncomeField = new JTextField(20);
        professionalIncomeField.setPreferredSize(new Dimension(0, 25));
        professionalIncomeField.setFont(inputFont);
        professionalIncomeField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(professionalIncomeField, "Total job earnings last year");
        specificInfoSection.add(professionalIncomeField, specificGbc);

        // Real Property Income
        specificGbc.gridx = 0; specificGbc.gridy = 7; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel propertyIncomeLabel = new JLabel("Real Property Income:");
        propertyIncomeLabel.setFont(labelFont);
        specificInfoSection.add(propertyIncomeLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField propertyIncomeField = new JTextField(20);
        propertyIncomeField.setPreferredSize(new Dimension(0, 25));
        propertyIncomeField.setFont(inputFont);
        propertyIncomeField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(propertyIncomeField, "Total rental income last year");
        specificInfoSection.add(propertyIncomeField, specificGbc);

        // Total Amount
        specificGbc.gridx = 0; specificGbc.gridy = 8; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel totalAmountLabel = new JLabel("Total Amount:");
        totalAmountLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        specificInfoSection.add(totalAmountLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField totalAmountField = new JTextField(20);
        totalAmountField.setPreferredSize(new Dimension(0, 25));
        totalAmountField.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        totalAmountField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        totalAmountField.setEditable(false);
        totalAmountField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
        ComponentFactory.addPlaceholder(totalAmountField, "Calculated total tax");
        specificInfoSection.add(totalAmountField, specificGbc);

        ComponentFactory.addCommunityTaxCalculationListener(businessIncomeField, professionalIncomeField, propertyIncomeField, totalAmountField);

        formPanel.add(specificInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Documents to Present Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;

        String[] documentOptions = {
                "Valid ID (Driver's License, SSS ID, Postal ID, etc.)",
                "Proof of Income (if applicable)",
                "Receipt of Payment (if applicable)"
        };

        JPanel documentsSection = createDocumentsToPresentSection(documentOptions, sectionFont, inputFont);

        formPanel.add(documentsSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        return formPanel;
    }

    //Certificate for First Time Job Seeker Form
    public static JPanel createFirstTimeJobSeekerForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);
        Font sectionFont = new Font("Trebuchet MS", Font.BOLD, 14);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Basic Document Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel docInfoSection = createDocumentInformationSection();
        formPanel.add(docInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Personal Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel personalInfoSection = createPersonalInformationSection();
        formPanel.add(personalInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Specific Document Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel specificInfoSection = new JPanel(new GridBagLayout());
        specificInfoSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Specific Document Information",
                TitledBorder.LEFT, TitledBorder.TOP, sectionFont
        ));
        specificInfoSection.setOpaque(false);

        GridBagConstraints specificGbc = new GridBagConstraints();
        specificGbc.insets = new Insets(5, 5, 5, 5);
        specificGbc.anchor = GridBagConstraints.WEST;

        // Highest Education
        specificGbc.gridx = 0; specificGbc.gridy = 0; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel educationLabel = new JLabel("Highest Education:");
        educationLabel.setFont(labelFont);
        specificInfoSection.add(educationLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] educationOptions = {"Select Highest Education", "Elementary", "High School", "Senior High", "College", "Vocational", "Others"};
        JComboBox<String> educationCombo = new JComboBox<>(educationOptions);
        educationCombo.setPreferredSize(new Dimension(0, 25));
        educationCombo.setFont(inputFont);
        educationCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(educationCombo, specificGbc);

        // If others (education), please specify (initially hidden)
        specificGbc.gridx = 0; specificGbc.gridy = 1; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel educationOthersLabel = new JLabel("If others, please specify:");
        educationOthersLabel.setFont(labelFont);
        educationOthersLabel.setVisible(false);
        specificInfoSection.add(educationOthersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField educationOthersField = new JTextField(20);
        educationOthersField.setPreferredSize(new Dimension(0, 25));
        educationOthersField.setFont(inputFont);
        educationOthersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        educationOthersField.setVisible(false);
        ComponentFactory.addPlaceholder(educationOthersField, "Please specify your education");
        specificInfoSection.add(educationOthersField, specificGbc);

        // Education combo action listener for conditional field
        educationCombo.addActionListener(e -> {
            boolean showEducationOthers = "Others".equals(educationCombo.getSelectedItem());
            educationOthersLabel.setVisible(showEducationOthers);
            educationOthersField.setVisible(showEducationOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        // Purpose
        specificGbc.gridx = 0; specificGbc.gridy = 2; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel purposeLabel = new JLabel("Purpose:");
        purposeLabel.setFont(labelFont);
        specificInfoSection.add(purposeLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] purposeOptions = {"Select Purpose", "NBI Clearance", "Job Application", "Government Exam", "Others"};
        JComboBox<String> purposeCombo = new JComboBox<>(purposeOptions);
        purposeCombo.setPreferredSize(new Dimension(0, 25));
        purposeCombo.setFont(inputFont);
        purposeCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(purposeCombo, specificGbc);

        // If others, please specify (initially hidden)
        specificGbc.gridx = 0; specificGbc.gridy = 3; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel othersLabel = new JLabel("If others, please specify:");
        othersLabel.setFont(labelFont);
        othersLabel.setVisible(false);
        specificInfoSection.add(othersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField othersField = new JTextField(20);
        othersField.setPreferredSize(new Dimension(0, 25));
        othersField.setFont(inputFont);
        othersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        othersField.setVisible(false);
        ComponentFactory.addPlaceholder(othersField, "Please specify your purpose");
        specificInfoSection.add(othersField, specificGbc);

        // Purpose combo action listener for conditional field
        purposeCombo.addActionListener(e -> {
            boolean showOthers = "Others".equals(purposeCombo.getSelectedItem());
            othersLabel.setVisible(showOthers);
            othersField.setVisible(showOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        // Declaration
        specificGbc.gridx = 0; specificGbc.gridy = 4; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel declarationLabel = new JLabel("Declaration:");
        declarationLabel.setFont(labelFont);
        specificInfoSection.add(declarationLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JCheckBox declarationCheckbox = new JCheckBox("<html><body style='width:400px'>I solemnly declare that I have no prior employment history, in accordance with the requirements for this certification.</body></html>");
        declarationCheckbox.setFont(inputFont);
        declarationCheckbox.setOpaque(false);
        specificInfoSection.add(declarationCheckbox, specificGbc);

        formPanel.add(specificInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Documents to Present Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;

        String[] documentOptions = {
                "Valid ID",
                "Diploma/Certificate of Completion (if applicable)",
                "Receipt of Payment (if applicable)"
        };

        JPanel documentsSection = createDocumentsToPresentSection(documentOptions, sectionFont, inputFont);

        formPanel.add(documentsSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        return formPanel;
    }

    //Certificate for Late Registration Form
    public static JPanel createLateRegistrationForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);
        Font sectionFont = new Font("Trebuchet MS", Font.BOLD, 14);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Document Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel docInfoSection = createDocumentInformationSection();
        formPanel.add(docInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Personal Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel personalInfoSection = createPersonalInformationSection();
        formPanel.add(personalInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Specific Information for Application Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel specificInfoSection = new JPanel(new GridBagLayout());
        specificInfoSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Specific Document Information",
                TitledBorder.LEFT, TitledBorder.TOP, sectionFont
        ));
        specificInfoSection.setOpaque(false);

        GridBagConstraints specificGbc = new GridBagConstraints();
        specificGbc.insets = new Insets(5, 5, 5, 5);
        specificGbc.anchor = GridBagConstraints.WEST;

        // Birthplace
        specificGbc.gridx = 0; specificGbc.gridy = 0; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel birthplaceLabel = new JLabel("Birthplace:");
        birthplaceLabel.setFont(labelFont);
        specificInfoSection.add(birthplaceLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField birthplaceField = new JTextField(20);
        birthplaceField.setPreferredSize(new Dimension(0, 25));
        birthplaceField.setFont(inputFont);
        birthplaceField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(birthplaceField, "Barangay, Municipality/City, Province");
        specificInfoSection.add(birthplaceField, specificGbc);

        // Reason for Late Registration
        specificGbc.gridx = 0; specificGbc.gridy = 1; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel reasonLabel = new JLabel("Reason for Late Registration:");
        reasonLabel.setFont(labelFont);
        specificInfoSection.add(reasonLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] reasonOptions = {"Select Reason", "Lack of documents", "Born at home", "Financial constraints", "Others"};
        JComboBox<String> reasonCombo = new JComboBox<>(reasonOptions);
        reasonCombo.setPreferredSize(new Dimension(0, 25));
        reasonCombo.setFont(inputFont);
        reasonCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(reasonCombo, specificGbc);

        // If others, please specify (initially hidden) - Reason
        specificGbc.gridx = 0; specificGbc.gridy = 2; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel reasonOthersLabel = new JLabel("If others, please specify:");
        reasonOthersLabel.setFont(labelFont);
        reasonOthersLabel.setVisible(false);
        specificInfoSection.add(reasonOthersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField reasonOthersField = new JTextField(20);
        reasonOthersField.setPreferredSize(new Dimension(0, 25));
        reasonOthersField.setFont(inputFont);
        reasonOthersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        reasonOthersField.setVisible(false);
        ComponentFactory.addPlaceholder(reasonOthersField, "Please specify the reason");
        specificInfoSection.add(reasonOthersField, specificGbc);

        // Reason combo action listener for conditional field
        reasonCombo.addActionListener(e -> {
            boolean showOthers = "Others".equals(reasonCombo.getSelectedItem());
            reasonOthersLabel.setVisible(showOthers);
            reasonOthersField.setVisible(showOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        // Relationship to Registrant
        specificGbc.gridx = 0; specificGbc.gridy = 3; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel relationshipLabel = new JLabel("Relationship to Registrant:");
        relationshipLabel.setFont(labelFont);
        specificInfoSection.add(relationshipLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] relationshipOptions = {"Select Relationship", "Self", "Parent", "Guardian", "Spouse", "Child", "Others"};
        JComboBox<String> relationshipCombo = new JComboBox<>(relationshipOptions);
        relationshipCombo.setPreferredSize(new Dimension(0, 25));
        relationshipCombo.setFont(inputFont);
        relationshipCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(relationshipCombo, specificGbc);

        // If others, please specify (initially hidden) - Relationship
        specificGbc.gridx = 0; specificGbc.gridy = 4; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel relationshipOthersLabel = new JLabel("If others, please specify:");
        relationshipOthersLabel.setFont(labelFont);
        relationshipOthersLabel.setVisible(false);
        specificInfoSection.add(relationshipOthersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField relationshipOthersField = new JTextField(20);
        relationshipOthersField.setPreferredSize(new Dimension(0, 25));
        relationshipOthersField.setFont(inputFont);
        relationshipOthersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        relationshipOthersField.setVisible(false);
        ComponentFactory.addPlaceholder(relationshipOthersField, "Please specify the relationship");
        specificInfoSection.add(relationshipOthersField, specificGbc);

        // Relationship combo action listener for conditional field
        relationshipCombo.addActionListener(e -> {
            boolean showOthers = "Others".equals(relationshipCombo.getSelectedItem());
            relationshipOthersLabel.setVisible(showOthers);
            relationshipOthersField.setVisible(showOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        // Event to be Registered Late
        specificGbc.gridx = 0; specificGbc.gridy = 5; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel eventLabel = new JLabel("Event to be Registered Late:");
        eventLabel.setFont(labelFont);
        specificInfoSection.add(eventLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] eventOptions = {"Select Event", "Birth", "Marriage", "Death"};
        JComboBox<String> eventCombo = new JComboBox<>(eventOptions);
        eventCombo.setPreferredSize(new Dimension(0, 25));
        eventCombo.setFont(inputFont);
        eventCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(eventCombo, specificGbc);

        // Approximate Date of Event
        specificGbc.gridx = 0; specificGbc.gridy = 6; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel approximateDateLabel = new JLabel("Approximate Date of Event:");
        approximateDateLabel.setFont(labelFont);
        specificInfoSection.add(approximateDateLabel, specificGbc);

        // Date panel with combo boxes
        JPanel datePanel = new JPanel();
        datePanel.setPreferredSize(new Dimension(0, 25));
        datePanel.setFont(inputFont);
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.X_AXIS));
        datePanel.setOpaque(false);

        JComboBox<String> yearCombo = new JComboBox<>();
        JComboBox<String> monthCombo = new JComboBox<>();
        JComboBox<String> dayCombo = new JComboBox<>();

        yearCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        monthCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        dayCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        datePanel.add(new JLabel("Month:"));
        datePanel.add(Box.createHorizontalStrut(5));
        datePanel.add(monthCombo);
        datePanel.add(Box.createHorizontalStrut(10));

        datePanel.add(new JLabel("Day:"));
        datePanel.add(Box.createHorizontalStrut(5));
        datePanel.add(dayCombo);
        datePanel.add(Box.createHorizontalStrut(10));

        datePanel.add(new JLabel("Year:"));
        datePanel.add(Box.createHorizontalStrut(5));
        datePanel.add(yearCombo);

        // Initialize date combo boxes using your helper method
        ComponentFactory.setupDateComboBoxes(yearCombo, monthCombo, dayCombo);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        specificInfoSection.add(datePanel, specificGbc);

        // Place of Occurrence
        specificGbc.gridx = 0; specificGbc.gridy = 7; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel placeOfOccurrenceLabel = new JLabel("Place of Occurrence:");
        placeOfOccurrenceLabel.setFont(labelFont);
        specificInfoSection.add(placeOfOccurrenceLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField placeOfOccurrenceField = new JTextField(20);
        placeOfOccurrenceField.setPreferredSize(new Dimension(0, 25));
        placeOfOccurrenceField.setFont(inputFont);
        placeOfOccurrenceField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(placeOfOccurrenceField, "Barangay, Municipality/City, Province");
        specificInfoSection.add(placeOfOccurrenceField, specificGbc);

        // Supporting Details/Narrative
        specificGbc.gridx = 0; specificGbc.gridy = 8; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        specificGbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel narrativeLabel = new JLabel("Supporting Details/Narrative:");
        narrativeLabel.setFont(labelFont);
        specificInfoSection.add(narrativeLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.BOTH; specificGbc.weightx = 1.0; specificGbc.weighty = 1.0;
        JTextArea narrativeArea = new JTextArea(2, 20);
        narrativeArea.setFont(inputFont);
        narrativeArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        narrativeArea.setLineWrap(true);
        narrativeArea.setWrapStyleWord(true);
        ComponentFactory.addPlaceholder(narrativeArea, "Brief explanation of circumstances surrounding the late registration");

        JScrollPane narrativeScrollPane = new JScrollPane(narrativeArea);
        narrativeScrollPane.setPreferredSize(new Dimension(0, 50));
        narrativeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        narrativeScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        specificInfoSection.add(narrativeScrollPane, specificGbc);

        // Reset anchor and weighty for subsequent components
        specificGbc.anchor = GridBagConstraints.WEST;
        specificGbc.weighty = 0;

        formPanel.add(specificInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Documents to Present Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;

        String[] documentOptions = {
                "Valid ID",
                "Barangay Clearance / Certificate of Residency",
                "Supporting Documents (e.g., Baptismal Certificate, Marriage Contract, Medical Certificate of Death, or any applicable documents)",
                "Receipt of Payment (if applicable)"
        };

        JPanel documentsSection = createDocumentsToPresentSection(documentOptions, sectionFont, inputFont);

        formPanel.add(documentsSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        return formPanel;
    }

    //Certificate for Low Income Certificate Form
    public static JPanel createLowIncomeCertificateForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);
        Font sectionFont = new Font("Trebuchet MS", Font.BOLD, 14);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Document Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel docInfoSection = createDocumentInformationSection();
        formPanel.add(docInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Personal Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel personalInfoSection = createPersonalInformationSection();
        formPanel.add(personalInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Specific Information for Application Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel specificInfoSection = new JPanel(new GridBagLayout());
        specificInfoSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Specific Document Information",
                TitledBorder.LEFT, TitledBorder.TOP, sectionFont
        ));
        specificInfoSection.setOpaque(false);

        GridBagConstraints specificGbc = new GridBagConstraints();
        specificGbc.insets = new Insets(5, 5, 5, 5);
        specificGbc.anchor = GridBagConstraints.WEST;

        // Number of Family Members in Household
        specificGbc.gridx = 0; specificGbc.gridy = 0; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel familyMembersLabel = new JLabel("Number of Family Members in Household:");
        familyMembersLabel.setFont(labelFont);
        specificInfoSection.add(familyMembersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JSpinner familyMembersSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        familyMembersSpinner.setPreferredSize(new Dimension(0, 25));
        familyMembersSpinner.setFont(inputFont);
        ((JSpinner.DefaultEditor) familyMembersSpinner.getEditor()).getTextField().setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(familyMembersSpinner, specificGbc);

        // Monthly Household Income
        specificGbc.gridx = 0; specificGbc.gridy = 1; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel monthlyIncomeLabel = new JLabel("Monthly Household Income:");
        monthlyIncomeLabel.setFont(labelFont);
        specificInfoSection.add(monthlyIncomeLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField monthlyIncomeField = new JTextField(20);
        monthlyIncomeField.setPreferredSize(new Dimension(0, 25));
        monthlyIncomeField.setFont(inputFont);
        monthlyIncomeField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(monthlyIncomeField, "Enter amount in PHP (e.g., 15000)");
        specificInfoSection.add(monthlyIncomeField, specificGbc);

        // Occupation / Source of Income
        specificGbc.gridx = 0; specificGbc.gridy = 2; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel occupationLabel = new JLabel("Occupation / Source of Income:");
        occupationLabel.setFont(labelFont);
        specificInfoSection.add(occupationLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] occupationOptions = {"Select Source", "Employment", "Business", "Farming", "Remittance", "Pension", "Others"};
        JComboBox<String> occupationCombo = new JComboBox<>(occupationOptions);
        occupationCombo.setPreferredSize(new Dimension(0, 25));
        occupationCombo.setFont(inputFont);
        occupationCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(occupationCombo, specificGbc);

        // If others, please specify (initially hidden) - Occupation
        specificGbc.gridx = 0; specificGbc.gridy = 3; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel occupationOthersLabel = new JLabel("If others, please specify:");
        occupationOthersLabel.setFont(labelFont);
        occupationOthersLabel.setVisible(false);
        specificInfoSection.add(occupationOthersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField occupationOthersField = new JTextField(20);
        occupationOthersField.setPreferredSize(new Dimension(0, 25));
        occupationOthersField.setFont(inputFont);
        occupationOthersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        occupationOthersField.setVisible(false);
        ComponentFactory.addPlaceholder(occupationOthersField, "Please specify the occupation/source of income");
        specificInfoSection.add(occupationOthersField, specificGbc);

        // Occupation combo action listener for conditional field
        occupationCombo.addActionListener(e -> {
            boolean showOthers = "Others".equals(occupationCombo.getSelectedItem());
            occupationOthersLabel.setVisible(showOthers);
            occupationOthersField.setVisible(showOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        // Purpose of Request
        specificGbc.gridx = 0; specificGbc.gridy = 4; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel purposeLabel = new JLabel("Purpose of Request:");
        purposeLabel.setFont(labelFont);
        specificInfoSection.add(purposeLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] purposeOptions = {"Select Purpose", "Scholarship", "Medical Assistance", "Government Aid", "Tuition Discount", "Legal Aid", "Others"};
        JComboBox<String> purposeCombo = new JComboBox<>(purposeOptions);
        purposeCombo.setPreferredSize(new Dimension(0, 25));
        purposeCombo.setFont(inputFont);
        purposeCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(purposeCombo, specificGbc);

        // If others, please specify (initially hidden) - Purpose
        specificGbc.gridx = 0; specificGbc.gridy = 5; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel purposeOthersLabel = new JLabel("If others, please specify:");
        purposeOthersLabel.setFont(labelFont);
        purposeOthersLabel.setVisible(false);
        specificInfoSection.add(purposeOthersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField purposeOthersField = new JTextField(20);
        purposeOthersField.setPreferredSize(new Dimension(0, 25));
        purposeOthersField.setFont(inputFont);
        purposeOthersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        purposeOthersField.setVisible(false);
        ComponentFactory.addPlaceholder(purposeOthersField, "Please specify the purpose");
        specificInfoSection.add(purposeOthersField, specificGbc);

        // Purpose combo action listener for conditional field
        purposeCombo.addActionListener(e -> {
            boolean showOthers = "Others".equals(purposeCombo.getSelectedItem());
            purposeOthersLabel.setVisible(showOthers);
            purposeOthersField.setVisible(showOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        formPanel.add(specificInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Documents to Present Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;

        String[] documentOptions = {
                "Valid ID",
                "Barangay Clearance / Certificate of Residency (if applicable)",
                "Proof of Income (e.g., payslip, informal work affidavit, etc.)",
                "Receipt of Payment (if applicable)"
        };

        JPanel documentsSection = createDocumentsToPresentSection(documentOptions, sectionFont, inputFont);

        formPanel.add(documentsSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        return formPanel;
    }

    //Certificate for No Income Certificate Form
    public static JPanel createNoIncomeCertificateForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);
        Font sectionFont = new Font("Trebuchet MS", Font.BOLD, 14);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Document Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel docInfoSection = createDocumentInformationSection();
        formPanel.add(docInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Personal Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel personalInfoSection = createPersonalInformationSection();
        formPanel.add(personalInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Specific Information for Application Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel specificInfoSection = new JPanel(new GridBagLayout());
        specificInfoSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Specific Document Information",
                TitledBorder.LEFT, TitledBorder.TOP, sectionFont
        ));
        specificInfoSection.setOpaque(false);

        GridBagConstraints specificGbc = new GridBagConstraints();
        specificGbc.insets = new Insets(5, 5, 5, 5);
        specificGbc.anchor = GridBagConstraints.WEST;

        // Employment Status
        specificGbc.gridx = 0; specificGbc.gridy = 0; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel employmentStatusLabel = new JLabel("Employment Status:");
        employmentStatusLabel.setFont(labelFont);
        specificInfoSection.add(employmentStatusLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] employmentOptions = {"Select Employment Status", "Unemployed", "Student", "Dependent", "Informal Work", "Others"};
        JComboBox<String> employmentCombo = new JComboBox<>(employmentOptions);
        employmentCombo.setPreferredSize(new Dimension(0, 25));
        employmentCombo.setFont(inputFont);
        employmentCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(employmentCombo, specificGbc);

        // If others, please specify (initially hidden) - Employment Status
        specificGbc.gridx = 0; specificGbc.gridy = 1; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel employmentOthersLabel = new JLabel("If others, please specify:");
        employmentOthersLabel.setFont(labelFont);
        employmentOthersLabel.setVisible(false);
        specificInfoSection.add(employmentOthersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField employmentOthersField = new JTextField(20);
        employmentOthersField.setPreferredSize(new Dimension(0, 25));
        employmentOthersField.setFont(inputFont);
        employmentOthersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        employmentOthersField.setVisible(false);
        ComponentFactory.addPlaceholder(employmentOthersField, "Please specify employment status");
        specificInfoSection.add(employmentOthersField, specificGbc);

        // Employment combo action listener for conditional field
        employmentCombo.addActionListener(e -> {
            boolean showOthers = "Others".equals(employmentCombo.getSelectedItem());
            employmentOthersLabel.setVisible(showOthers);
            employmentOthersField.setVisible(showOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        // Purpose of Request
        specificGbc.gridx = 0; specificGbc.gridy = 2; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel purposeLabel = new JLabel("Purpose of Request:");
        purposeLabel.setFont(labelFont);
        specificInfoSection.add(purposeLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] purposeOptions = {"Select Purpose", "Scholarship", "Medical Assistance", "Government Aid", "Tuition Discount", "Legal Aid", "Job Application", "Others"};
        JComboBox<String> purposeCombo = new JComboBox<>(purposeOptions);
        purposeCombo.setPreferredSize(new Dimension(0, 25));
        purposeCombo.setFont(inputFont);
        purposeCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(purposeCombo, specificGbc);

        // If others, please specify (initially hidden) - Purpose
        specificGbc.gridx = 0; specificGbc.gridy = 3; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel purposeOthersLabel = new JLabel("If others, please specify:");
        purposeOthersLabel.setFont(labelFont);
        purposeOthersLabel.setVisible(false);
        specificInfoSection.add(purposeOthersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField purposeOthersField = new JTextField(20);
        purposeOthersField.setPreferredSize(new Dimension(0, 25));
        purposeOthersField.setFont(inputFont);
        purposeOthersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        purposeOthersField.setVisible(false);
        ComponentFactory.addPlaceholder(purposeOthersField, "Please specify purpose");
        specificInfoSection.add(purposeOthersField, specificGbc);

        // Purpose combo action listener for conditional field
        purposeCombo.addActionListener(e -> {
            boolean showOthers = "Others".equals(purposeCombo.getSelectedItem());
            purposeOthersLabel.setVisible(showOthers);
            purposeOthersField.setVisible(showOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        // Source of Support
        specificGbc.gridx = 0; specificGbc.gridy = 4; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel supportLabel = new JLabel("Source of Support (if any):");
        supportLabel.setFont(labelFont);
        specificInfoSection.add(supportLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField supportField = new JTextField(20);
        supportField.setPreferredSize(new Dimension(0, 25));
        supportField.setFont(inputFont);
        supportField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(supportField, "e.g., Parents, Spouse, Sibling, None");
        specificInfoSection.add(supportField, specificGbc);

        // Declaration Checkbox
        specificGbc.gridx = 0; specificGbc.gridy = 5; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel declarationLabel = new JLabel("Declaration:");
        declarationLabel.setFont(labelFont);
        specificInfoSection.add(declarationLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JCheckBox declarationCheckbox = new JCheckBox("I hereby certify that I have no regular source of income and am currently unemployed.");
        declarationCheckbox.setFont(inputFont);
        declarationCheckbox.setOpaque(false);
        specificInfoSection.add(declarationCheckbox, specificGbc);

        formPanel.add(specificInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Documents to Present Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;

        String[] documentOptions = {
                "Valid ID",
                "Barangay Clearance / Certificate of Residency (if applicable)",
                "Proof of Income (e.g., payslip, informal work affidavit, etc.)",
                "Receipt of Payment (if applicable)"
        };

        JPanel documentsSection = createDocumentsToPresentSection(documentOptions, sectionFont, inputFont);

        formPanel.add(documentsSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        return formPanel;
    }

    //Certificate for State Tax Exemption Certificate
    public static JPanel createTaxExemptionForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);
        Font sectionFont = new Font("Trebuchet MS", Font.BOLD, 14);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Document Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel docInfoSection = createDocumentInformationSection();
        formPanel.add(docInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Personal Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel personalInfoSection = createPersonalInformationSection();
        formPanel.add(personalInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Specific Information for Application Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel specificInfoSection = new JPanel(new GridBagLayout());
        specificInfoSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Specific Document Information",
                TitledBorder.LEFT, TitledBorder.TOP, sectionFont
        ));
        specificInfoSection.setOpaque(false);

        GridBagConstraints specificGbc = new GridBagConstraints();
        specificGbc.insets = new Insets(5, 5, 5, 5);
        specificGbc.anchor = GridBagConstraints.WEST;

        // Reason for Exemption
        specificGbc.gridx = 0; specificGbc.gridy = 0; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel reasonLabel = new JLabel("Reason for Exemption:");
        reasonLabel.setFont(labelFont);
        specificInfoSection.add(reasonLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] reasonOptions = {"Select Reason", "Indigent", "Senior Citizen", "Person with Disability", "Student", "Non-Profit/Religious Institution Representative", "Others"};
        JComboBox<String> reasonCombo = new JComboBox<>(reasonOptions);
        reasonCombo.setPreferredSize(new Dimension(0, 25));
        reasonCombo.setFont(inputFont);
        reasonCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(reasonCombo, specificGbc);

        // If others, please specify (initially hidden) - Reason
        specificGbc.gridx = 0; specificGbc.gridy = 1; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel reasonOthersLabel = new JLabel("If others, please specify:");
        reasonOthersLabel.setFont(labelFont);
        reasonOthersLabel.setVisible(false);
        specificInfoSection.add(reasonOthersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField reasonOthersField = new JTextField(20);
        reasonOthersField.setPreferredSize(new Dimension(0, 25));
        reasonOthersField.setFont(inputFont);
        reasonOthersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        reasonOthersField.setVisible(false);
        ComponentFactory.addPlaceholder(reasonOthersField, "Please specify the reason for exemption");
        specificInfoSection.add(reasonOthersField, specificGbc);

        // Reason combo action listener for conditional field
        reasonCombo.addActionListener(e -> {
            boolean showOthers = "Others".equals(reasonCombo.getSelectedItem());
            reasonOthersLabel.setVisible(showOthers);
            reasonOthersField.setVisible(showOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        // Type of Tax Exemption Requested
        specificGbc.gridx = 0; specificGbc.gridy = 2; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel taxTypeLabel = new JLabel("Type of Tax Exemption Requested:");
        taxTypeLabel.setFont(labelFont);
        specificInfoSection.add(taxTypeLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        String[] taxTypeOptions = {"Select Tax Type", "Community Tax (Cedula)", "Local Business Tax", "Government Transaction Fee", "Others"};
        JComboBox<String> taxTypeCombo = new JComboBox<>(taxTypeOptions);
        taxTypeCombo.setPreferredSize(new Dimension(0, 25));
        taxTypeCombo.setFont(inputFont);
        taxTypeCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specificInfoSection.add(taxTypeCombo, specificGbc);

        // If others, please specify (initially hidden) - Tax Type
        specificGbc.gridx = 0; specificGbc.gridy = 3; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel taxTypeOthersLabel = new JLabel("If others, please specify:");
        taxTypeOthersLabel.setFont(labelFont);
        taxTypeOthersLabel.setVisible(false);
        specificInfoSection.add(taxTypeOthersLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JTextField taxTypeOthersField = new JTextField(20);
        taxTypeOthersField.setPreferredSize(new Dimension(0, 25));
        taxTypeOthersField.setFont(inputFont);
        taxTypeOthersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        taxTypeOthersField.setVisible(false);
        ComponentFactory.addPlaceholder(taxTypeOthersField, "Please specify the type of tax exemption");
        specificInfoSection.add(taxTypeOthersField, specificGbc);

        // Tax Type combo action listener for conditional field
        taxTypeCombo.addActionListener(e -> {
            boolean showOthers = "Others".equals(taxTypeCombo.getSelectedItem());
            taxTypeOthersLabel.setVisible(showOthers);
            taxTypeOthersField.setVisible(showOthers);
            specificInfoSection.revalidate();
            specificInfoSection.repaint();
        });

        // Declaration Checkbox
        specificGbc.gridx = 0; specificGbc.gridy = 4; specificGbc.fill = GridBagConstraints.NONE; specificGbc.weightx = 0;
        JLabel declarationLabel = new JLabel("Declaration:");
        declarationLabel.setFont(labelFont);
        specificInfoSection.add(declarationLabel, specificGbc);

        specificGbc.gridx = 1; specificGbc.fill = GridBagConstraints.HORIZONTAL; specificGbc.weightx = 1.0;
        JCheckBox declarationCheckbox = new JCheckBox("<html><body style='width:330px'>I hereby declare that I qualify for tax exemption under applicable laws and local ordinances.</body></html>");
        declarationCheckbox.setFont(inputFont);
        declarationCheckbox.setOpaque(false);
        specificInfoSection.add(declarationCheckbox, specificGbc);

        formPanel.add(specificInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Documents to Present Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;

        String[] documentOptions = {
                "Valid ID",
                "Barangay Clearance / Certificate of Residency (if applicable)",
                "Proof of Income or Indigency (e.g., payslip, affidavit of no income, ID for senior/PWD/student)",
                "Supporting Document for Exemption Type (e.g., Senior Citizen ID, PWD ID, School ID, 4Ps certificate, etc.)",
                "Receipt of Payment (if applicable)"
        };

        JPanel documentsSection = createDocumentsToPresentSection(documentOptions, sectionFont, inputFont);

        formPanel.add(documentsSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        return formPanel;
    }

}
