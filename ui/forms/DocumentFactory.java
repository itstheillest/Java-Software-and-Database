package ui.forms;

import utils.ComponentFactory;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JTextField;


public class DocumentFactory {

    public static JPanel createDocumentInformationSection() {
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);
        Font sectionFont = new Font("Trebuchet MS", Font.BOLD, 14);

        JPanel docInfoSection = new JPanel(new GridBagLayout());
        docInfoSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Document Information",
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
        certNumberField.setPreferredSize(new Dimension(0, 25));
        certNumberField.setFont(inputFont);
        certNumberField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        certNumberField.setEditable(false);
        certNumberField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
        ComponentFactory.addPlaceholder(certNumberField, "Auto-generated upon processing");
        docInfoSection.add(certNumberField, docGbc);

        // Date Issued
        docGbc.gridx = 0; docGbc.gridy = 1; docGbc.fill = GridBagConstraints.NONE; docGbc.weightx = 0;
        JLabel dateIssuedLabel = new JLabel("Date Issued:");
        dateIssuedLabel.setFont(labelFont);
        docInfoSection.add(dateIssuedLabel, docGbc);

        docGbc.gridx = 1; docGbc.fill = GridBagConstraints.HORIZONTAL; docGbc.weightx = 1.0;
        JTextField dateIssuedField = new JTextField(20);
        dateIssuedField.setPreferredSize(new Dimension(0, 25));
        dateIssuedField.setFont(inputFont);
        dateIssuedField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        dateIssuedField.setEditable(false);
        dateIssuedField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
        ComponentFactory.addPlaceholder(dateIssuedField, "Auto-generated upon processing");
        docInfoSection.add(dateIssuedField, docGbc);

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

        JPanel dobPanel = new JPanel();
        dobPanel.setPreferredSize(new Dimension(0, 25));
        dobPanel.setFont(inputFont);
        dobPanel.setLayout(new BoxLayout(dobPanel, BoxLayout.X_AXIS));
        dobPanel.setOpaque(false);

        JComboBox<String> dobYearCombo = new JComboBox<>();
        JComboBox<String> dobMonthCombo = new JComboBox<>();
        JComboBox<String> dobDayCombo = new JComboBox<>();

        dobYearCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        dobMonthCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        dobDayCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        dobPanel.add(new JLabel("Month:"));
        dobPanel.add(Box.createHorizontalStrut(5));
        dobPanel.add(dobMonthCombo);
        dobPanel.add(Box.createHorizontalStrut(10));

        dobPanel.add(new JLabel("Day:"));
        dobPanel.add(Box.createHorizontalStrut(5));
        dobPanel.add(dobDayCombo);
        dobPanel.add(Box.createHorizontalStrut(10));

        dobPanel.add(new JLabel("Year:"));
        dobPanel.add(Box.createHorizontalStrut(5));
        dobPanel.add(dobYearCombo);
        dobPanel.add(Box.createHorizontalStrut(10));

        dobPanel.add(new JLabel("Age:"));
        dobPanel.add(Box.createHorizontalStrut(5));
        JTextField ageField = new JTextField(5);
        ageField.setFont(inputFont);
        ageField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ageField.setEditable(false);
        ageField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
        ComponentFactory.addPlaceholder(ageField, "Auto-calculated");
        dobPanel.add(ageField);

        ComponentFactory.setupDateComboBoxes(dobYearCombo, dobMonthCombo, dobDayCombo);

        personalGbc.gridx = 1; personalGbc.fill = GridBagConstraints.HORIZONTAL; personalGbc.weightx = 1.0;
        personalInfoSection.add(dobPanel, personalGbc);

        ComponentFactory.addAgeCalculationListener(dobYearCombo, dobMonthCombo, dobDayCombo, ageField);

        // Complete Address
        personalGbc.gridx = 0; personalGbc.gridy = 2; personalGbc.fill = GridBagConstraints.NONE; personalGbc.weightx = 0;
        JLabel addressLabel = new JLabel("Complete Address:");
        addressLabel.setFont(labelFont);
        personalInfoSection.add(addressLabel, personalGbc);

        personalGbc.gridx = 1; personalGbc.fill = GridBagConstraints.HORIZONTAL; personalGbc.weightx = 1.0;
        JTextField addressField = new JTextField(20);
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
        contactField.setPreferredSize(new Dimension(0, 25));
        contactField.setFont(inputFont);
        contactField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(contactField, "Mobile number or landline with area code");
        personalInfoSection.add(contactField, personalGbc);

        return personalInfoSection;
    }

    public static JScrollPane createBarangayClearanceForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Create form fields
        JTextField fullNameField = new JTextField(20);
        JTextField addressField = new JTextField(20);
        JTextField contactNumberField = new JTextField(20);
        JTextField birthdateField = new JTextField(20);
        JTextField birthplaceField = new JTextField(20);
        JTextField civilStatusField = new JTextField(20);
        JTextField occupationField = new JTextField(20);
        JComboBox<String> purposeCombo = new JComboBox<>(new String[]{
                "Employment", "Business", "Travel", "School", "Others"
        });
        JTextField otherPurposeField = new JTextField(20);
        JTextField yearsResidedField = new JTextField(20);

        // Add components to form
        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(fullNameField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        formPanel.add(addressField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Contact Number:"), gbc);
        gbc.gridx = 1;
        formPanel.add(contactNumberField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Birthdate:"), gbc);
        gbc.gridx = 1;
        formPanel.add(birthdateField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Birthplace:"), gbc);
        gbc.gridx = 1;
        formPanel.add(birthplaceField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Civil Status:"), gbc);
        gbc.gridx = 1;
        formPanel.add(civilStatusField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Occupation:"), gbc);
        gbc.gridx = 1;
        formPanel.add(occupationField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Purpose:"), gbc);
        gbc.gridx = 1;
        formPanel.add(purposeCombo, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Other Purpose:"), gbc);
        gbc.gridx = 1;
        formPanel.add(otherPurposeField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Years Resided:"), gbc);
        gbc.gridx = 1;
        formPanel.add(yearsResidedField, gbc);

        return new JScrollPane(formPanel);
    }

    public static JScrollPane createCalamityForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Create form fields
        JTextField applicantNameField = new JTextField(20);
        JTextField addressField = new JTextField(20);
        JTextField contactNumberField = new JTextField(20);
        JComboBox<String> calamityTypeCombo = new JComboBox<>(new String[]{
                "Flood", "Fire", "Earthquake", "Typhoon", "Landslide", "Others"
        });
        JTextField otherCalamityField = new JTextField(20);
        JTextField dateOccurredField = new JTextField(20);
        JTextField estimatedDamageField = new JTextField(20);
        JComboBox<String> affectedAreaCombo = new JComboBox<>(new String[]{
                "Residential", "Commercial", "Agricultural", "Mixed"
        });
        JTextField familyMembersAffectedField = new JTextField(20);
        JTextArea damageDescriptionArea = new JTextArea(3, 20);
        damageDescriptionArea.setLineWrap(true);
        damageDescriptionArea.setWrapStyleWord(true);
        JTextArea assistanceNeededArea = new JTextArea(3, 20);
        assistanceNeededArea.setLineWrap(true);
        assistanceNeededArea.setWrapStyleWord(true);

        // Add components to form
        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Applicant Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(applicantNameField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        formPanel.add(addressField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Contact Number:"), gbc);
        gbc.gridx = 1;
        formPanel.add(contactNumberField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Calamity Type:"), gbc);
        gbc.gridx = 1;
        formPanel.add(calamityTypeCombo, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Other Calamity:"), gbc);
        gbc.gridx = 1;
        formPanel.add(otherCalamityField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Date Occurred:"), gbc);
        gbc.gridx = 1;
        formPanel.add(dateOccurredField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Estimated Damage (PHP):"), gbc);
        gbc.gridx = 1;
        formPanel.add(estimatedDamageField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Affected Area Type:"), gbc);
        gbc.gridx = 1;
        formPanel.add(affectedAreaCombo, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Family Members Affected:"), gbc);
        gbc.gridx = 1;
        formPanel.add(familyMembersAffectedField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Damage Description:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(damageDescriptionArea), gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Assistance Needed:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(assistanceNeededArea), gbc);

        return new JScrollPane(formPanel);
    }

    public static JScrollPane createNoObjectionForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Create form fields
        JTextField applicantNameField = new JTextField(20);
        JTextField addressField = new JTextField(20);
        JTextField contactNumberField = new JTextField(20);
        JTextField projectNameField = new JTextField(20);
        JTextField constructionSiteField = new JTextField(20);
        JComboBox<String> buildingTypeCombo = new JComboBox<>(new String[]{
                "Residential", "Commercial", "Industrial", "Mixed-use", "Others"
        });
        JTextField otherBuildingTypeField = new JTextField(20);
        JTextField floorAreaField = new JTextField(20);
        JTextField numberOfFloorsField = new JTextField(20);
        JTextField estimatedCostField = new JTextField(20);
        JTextField constructionStartField = new JTextField(20);
        JTextField constructionEndField = new JTextField(20);
        JTextField contractorField = new JTextField(20);
        JTextField engineerField = new JTextField(20);
        JTextField architectField = new JTextField(20);
        JTextArea projectDescriptionArea = new JTextArea(3, 20);
        projectDescriptionArea.setLineWrap(true);
        projectDescriptionArea.setWrapStyleWord(true);

        // Add components to form
        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Applicant Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(applicantNameField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        formPanel.add(addressField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Contact Number:"), gbc);
        gbc.gridx = 1;
        formPanel.add(contactNumberField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Project Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(projectNameField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Construction Site:"), gbc);
        gbc.gridx = 1;
        formPanel.add(constructionSiteField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Building Type:"), gbc);
        gbc.gridx = 1;
        formPanel.add(buildingTypeCombo, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Other Building Type:"), gbc);
        gbc.gridx = 1;
        formPanel.add(otherBuildingTypeField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Floor Area (sq.m):"), gbc);
        gbc.gridx = 1;
        formPanel.add(floorAreaField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Number of Floors:"), gbc);
        gbc.gridx = 1;
        formPanel.add(numberOfFloorsField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Estimated Cost (PHP):"), gbc);
        gbc.gridx = 1;
        formPanel.add(estimatedCostField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Construction Start Date:"), gbc);
        gbc.gridx = 1;
        formPanel.add(constructionStartField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Construction End Date:"), gbc);
        gbc.gridx = 1;
        formPanel.add(constructionEndField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Contractor:"), gbc);
        gbc.gridx = 1;
        formPanel.add(contractorField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Engineer:"), gbc);
        gbc.gridx = 1;
        formPanel.add(engineerField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(new JLabel("Architect:"), gbc);
        gbc.gridx = 1;
        formPanel.add(architectField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Project Description:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(projectDescriptionArea), gbc);

        return new JScrollPane(formPanel);
    }

    //Solo Parent Form
    public static JPanel createSoloParentForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Applicant Name ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel parentNameLabel = new JLabel("Name:");
        parentNameLabel.setFont(labelFont);
        formPanel.add(parentNameLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField parentNameField = new JTextField(20);
        parentNameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        formPanel.add(parentNameField, gbc);

        // Applicant Age ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel parentAgeLabel = new JLabel("Age:");
        parentAgeLabel.setFont(labelFont);
        formPanel.add(parentAgeLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField parentAgeField = new JTextField(10);
        parentAgeField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        formPanel.add(parentAgeField, gbc);

        // Applicant Address ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel parentAddressLabel = new JLabel("Address:");
        parentAddressLabel.setFont(labelFont);
        formPanel.add(parentAddressLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField parentAddressField = new JTextField(10);
        parentAddressField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        formPanel.add(parentAddressField, gbc);

        // Date of Birth ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel dateOfBirthLabel = new JLabel("Date of Birth:");
        dateOfBirthLabel.setFont(labelFont);
        formPanel.add(dateOfBirthLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        datePanel.setOpaque(false);

        // Create combo boxes
        JComboBox<String> monthCombo = new JComboBox<>();
        monthCombo.setPreferredSize(new Dimension(50, monthCombo.getPreferredSize().height));
        monthCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JComboBox<String> dayCombo = new JComboBox<>();
        dayCombo.setPreferredSize(new Dimension(50, dayCombo.getPreferredSize().height));
        dayCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JComboBox<String> yearCombo = new JComboBox<>();
        yearCombo.setPreferredSize(new Dimension(70, yearCombo.getPreferredSize().height));
        yearCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //Call helper method to set up proper dates
        ComponentFactory.setupDateComboBoxes(yearCombo, monthCombo, dayCombo);

        datePanel.add(monthCombo);
        datePanel.add(new JLabel("/"));
        datePanel.add(dayCombo);
        datePanel.add(new JLabel("/"));
        datePanel.add(yearCombo);

        formPanel.add(datePanel, gbc);

        // Number of Children ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel numChildrenLabel = new JLabel("Number of Children:");
        numChildrenLabel.setFont(labelFont);
        formPanel.add(numChildrenLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JSpinner numChildrenSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 15, 1));
        numChildrenSpinner.setPreferredSize(new Dimension(60, numChildrenSpinner.getPreferredSize().height));
        numChildrenSpinner.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        formPanel.add(numChildrenSpinner, gbc);

        // Container for dynamic children fields
        JPanel childrenContainer = new JPanel(new GridBagLayout());
        childrenContainer.setOpaque(false);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(childrenContainer, gbc);

        // Method to update children fields
        Runnable updateFields = () -> {
            childrenContainer.removeAll();

            GridBagConstraints childGbc = new GridBagConstraints();
            childGbc.insets = new Insets(5, 5, 5, 5);
            childGbc.anchor = GridBagConstraints.WEST;

            int numChildren = (Integer) numChildrenSpinner.getValue();
            for (int i = 0; i < numChildren; i++) {
                int childNum = i + 1;

                // Child Name Label
                childGbc.gridx = 0; childGbc.gridy = i; childGbc.fill = GridBagConstraints.NONE; childGbc.weightx = 0;
                JLabel nameLabel = new JLabel("Child #" + childNum + " Name:");
                nameLabel.setFont(labelFont);
                childrenContainer.add(nameLabel, childGbc);

                // Child Name Field
                childGbc.gridx = 1; childGbc.fill = GridBagConstraints.HORIZONTAL; childGbc.weightx = 0.4;
                JTextField nameField = new JTextField(15);
                nameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                childrenContainer.add(nameField, childGbc);

                // Child Age Label
                childGbc.gridx = 2; childGbc.fill = GridBagConstraints.NONE; childGbc.weightx = 0;
                childGbc.insets = new Insets(5, 15, 5, 5); // Extra left margin for spacing
                JLabel ageLabel = new JLabel("Age:");
                ageLabel.setFont(labelFont);
                childrenContainer.add(ageLabel, childGbc);

                // Child Age Field
                childGbc.gridx = 3; childGbc.fill = GridBagConstraints.HORIZONTAL; childGbc.weightx = 0.2;
                childGbc.insets = new Insets(5, 5, 5, 5); // Reset insets
                JTextField ageField = new JTextField(8);
                ageField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                childrenContainer.add(ageField, childGbc);
            }

            formPanel.revalidate();
            formPanel.repaint();
        };

        // Initially create fields for 1 child
        updateFields.run();

        // Add listener to update children fields when spinner value changes
        numChildrenSpinner.addChangeListener(e -> updateFields.run());

        return formPanel;
    }

    //Bail Certificate Form - Done
    public static JPanel createBailCertificateForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Certificate Number ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel certNumberLabel = new JLabel("Certificate Number:");
        certNumberLabel.setFont(labelFont);
        formPanel.add(certNumberLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField certNumberField = new JTextField(20);
        certNumberField.setPreferredSize(new Dimension(0, 25));
        certNumberField.setFont(inputFont);
        certNumberField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        formPanel.add(certNumberField, gbc);

        // Date Issued ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel dateIssuedLabel = new JLabel("Date Issued:");
        dateIssuedLabel.setFont(labelFont);
        formPanel.add(dateIssuedLabel, gbc);

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

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(datePanel, gbc);

        // Barangay ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel barangayLabel = new JLabel("Barangay:");
        barangayLabel.setFont(labelFont);
        formPanel.add(barangayLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField barangayField = new JTextField(20);
        barangayField.setPreferredSize(new Dimension(0, 25));
        barangayField.setFont(inputFont);
        barangayField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ComponentFactory.addPlaceholder(barangayField, "e.g., Market Area");
        formPanel.add(barangayField, gbc);

        // Municipal/City ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel cityLabel = new JLabel("Municipal/City:");
        cityLabel.setFont(labelFont);
        formPanel.add(cityLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField cityField = new JTextField(20);
        cityField.setPreferredSize(new Dimension(0, 25));
        cityField.setFont(inputFont);
        cityField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ComponentFactory.addPlaceholder(cityField, "e.g., Santa Rosa");
        formPanel.add(cityField, gbc);

        // Province ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel provinceLabel = new JLabel("Province:");
        provinceLabel.setFont(labelFont);
        formPanel.add(provinceLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField provinceField = new JTextField(20);
        provinceField.setPreferredSize(new Dimension(0, 25));
        provinceField.setFont(inputFont);
        provinceField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ComponentFactory.addPlaceholder(provinceField, "e.g., Laguna");
        formPanel.add(provinceField, gbc);

        // Name of Person Involved (Accused) ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel accusedNameLabel = new JLabel("Name of Person Involved:");
        accusedNameLabel.setFont(labelFont);
        formPanel.add(accusedNameLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField accusedNameField = new JTextField(20);
        accusedNameField.setPreferredSize(new Dimension(0, 25));
        accusedNameField.setFont(inputFont);
        accusedNameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ComponentFactory.addPlaceholder(accusedNameField, "Enter the full name of the person involved in the case");
        formPanel.add(accusedNameField, gbc);

        // Case Number ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel caseNumberLabel = new JLabel("Case Number:");
        caseNumberLabel.setFont(labelFont);
        formPanel.add(caseNumberLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField caseNumberField = new JTextField(20);
        caseNumberField.setPreferredSize(new Dimension(0, 25));
        caseNumberField.setFont(inputFont);
        caseNumberField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ComponentFactory.addPlaceholder(caseNumberField, "e.g., Criminal Case No. 2024-001");
        formPanel.add(caseNumberField, gbc);

        // Court Name ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 7; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel courtNameLabel = new JLabel("Court Name:");
        courtNameLabel.setFont(labelFont);
        formPanel.add(courtNameLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField courtNameField = new JTextField(20);
        courtNameField.setPreferredSize(new Dimension(0, 25));
        courtNameField.setFont(inputFont);
        courtNameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ComponentFactory.addPlaceholder(courtNameField, "e.g., Municipal Trial Court, Regional Trial Court");
        formPanel.add(courtNameField, gbc);

        // Nature of Case ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 8; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; gbc.weighty = 0;
        JLabel natureOfCaseLabel = new JLabel("Nature of Case:");
        natureOfCaseLabel.setFont(labelFont);
        formPanel.add(natureOfCaseLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 0;
        JTextArea natureOfCaseField = new JTextArea(3, 20);
        natureOfCaseField.setFont(inputFont);
        natureOfCaseField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JScrollPane natureScroll = new JScrollPane(natureOfCaseField);

        ComponentFactory.addPlaceholder(natureOfCaseField, "Describe the nature of the case (e.g., Theft, Physical Injury, Estafa, etc.)");
        formPanel.add(natureScroll, gbc);

        // Relation to the Accused ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 9; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; gbc.weighty = 0;
        JLabel relationLabel = new JLabel("Relation to the Accused:");
        relationLabel.setFont(labelFont);
        formPanel.add(relationLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;

        String[] relationOptions = {
                "I am the accused",
                "Family Member",
                "Relative",
                "Legal Representative/Lawyer",
                "Friend/Acquaintance",
                "Other"
        };

        ButtonGroup relationGroup = new ButtonGroup();
        JPanel relationPanel = new JPanel(new GridLayout(3, 2, 5, 5)); // rows, cols, hgap, vgap
        relationPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        relationPanel.setOpaque(false);

        for (String option : relationOptions) {
            JRadioButton radio = new JRadioButton(option);
            radio.setOpaque(false);
            radio.setFont(inputFont);
            relationGroup.add(radio);
            relationPanel.add(radio);
        }

        formPanel.add(relationPanel, gbc);

        // Required Documents (Checkboxes) ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 10; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel documentsLabel = new JLabel("Required Documents:");
        documentsLabel.setFont(labelFont);
        formPanel.add(documentsLabel, gbc);

        String[] documentOptions = {
                "Valid government-issued ID (Driver's License, SSS ID, Postal ID, etc.)",
                "Court documents or case records (if available)",
                "Police/Barangay blotter or incident report",
                "Authorization letter (if requesting on behalf of another person)",
                "Proof of relationship to the accused (if applicable)",
                "Lawyer's ID and authorization (for legal representatives)"
        };

        JPanel documentsPanel = new JPanel();
        documentsPanel.setLayout(new BoxLayout(documentsPanel, BoxLayout.Y_AXIS));
        documentsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        documentsPanel.setOpaque(false);

        for (String text : documentOptions) {
            JCheckBox checkbox = new JCheckBox(text);
            checkbox.setFont(inputFont);         // Use consistent font
            checkbox.setOpaque(false);           // Avoid gray background
            documentsPanel.add(checkbox);
        }

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(documentsPanel, gbc);

        return formPanel;
    }

    //Business Permit Application Form (New Application) - Done
    public static JPanel createNewBusinessPermitForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Business Name ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel businessNameLabel = new JLabel("Business Name:");
        businessNameLabel.setFont(labelFont);
        formPanel.add(businessNameLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField businessNameField = new JTextField(20);
        businessNameField.setPreferredSize(new Dimension(0, 25));
        businessNameField.setFont(inputFont);
        businessNameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ComponentFactory.addPlaceholder(businessNameField, "e.g., Market Shop");
        formPanel.add(businessNameField, gbc);

        // Owner's Full Name ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel ownerNameLabel = new JLabel("Owner's Full Name:");
        ownerNameLabel.setFont(labelFont);
        formPanel.add(ownerNameLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField ownerNameField = new JTextField(20);
        ownerNameField.setPreferredSize(new Dimension(0, 25));
        ownerNameField.setFont(inputFont);
        ownerNameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ComponentFactory.addPlaceholder(ownerNameField, "Last Name, First Name, Middle Initial");
        formPanel.add(ownerNameField, gbc);

        // Type of Business (Nature of Operation) ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel businessTypeLabel = new JLabel("Type of Business (Nature of Operation):");
        businessTypeLabel.setFont(labelFont);
        formPanel.add(businessTypeLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 0;
        JTextArea businessTypeArea = new JTextArea();
        businessTypeArea.setRows(2);
        businessTypeArea.setFont(inputFont);
        businessTypeArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        businessTypeArea.setLineWrap(true);
        businessTypeArea.setWrapStyleWord(true);

        // Add placeholder functionality for business type
        ComponentFactory.addPlaceholder(businessTypeArea, "e.g., Retail Store, Restaurant, Services");

        JScrollPane businessTypeScroll = new JScrollPane(businessTypeArea);
        formPanel.add(businessTypeScroll, gbc);

        // Business Address ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; gbc.weighty = 0;
        JLabel businessAddressLabel = new JLabel("Business Address:");
        businessAddressLabel.setFont(labelFont);
        formPanel.add(businessAddressLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField businessAddressField = new JTextField(20);
        businessAddressField.setPreferredSize(new Dimension(0, 25));
        businessAddressField.setFont(inputFont);
        businessAddressField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ComponentFactory.addPlaceholder(businessAddressField, "House/Building/Blk/Lot No., Street, Barangay, City/Municipality, Province");
        formPanel.add(businessAddressField, gbc);

        // Sketch of Location ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel sketchLabel = new JLabel("Sketch of Location:");
        sketchLabel.setFont(labelFont);
        formPanel.add(sketchLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 0;
        JTextArea sketchArea = new JTextArea();
        sketchArea.setRows(3);
        sketchArea.setFont(inputFont);
        sketchArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        sketchArea.setLineWrap(true);
        sketchArea.setWrapStyleWord(true);

        // Add placeholder functionality for sketch description
        ComponentFactory.addPlaceholder(sketchArea, "Describe the location and nearby landmarks");

        JScrollPane sketchScroll = new JScrollPane(sketchArea);
        formPanel.add(sketchScroll, gbc);

        // Lease Contract / Land Ownership Document ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; gbc.weighty = 0;
        JLabel documentTypeLabel = new JLabel("Lease Contract / Land Ownership Document:");
        documentTypeLabel.setFont(labelFont);
        formPanel.add(documentTypeLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;

        String[] documentTypeOptions = {
                "Select document type",
                "Lease Contract",
                "Land Title/Ownership Document",
                "Authorization Letter from Property Owner"
        };

        JComboBox<String> documentTypeCombo = new JComboBox<>(documentTypeOptions);
        documentTypeCombo.setPreferredSize(new Dimension(0, 25));
        documentTypeCombo.setFont(inputFont);
        documentTypeCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        formPanel.add(documentTypeCombo, gbc);

        // DTI/SEC Registration Number ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel registrationLabel = new JLabel("DTI/SEC Registration Number:");
        registrationLabel.setFont(labelFont);
        formPanel.add(registrationLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField registrationField = new JTextField();
        registrationField.setPreferredSize(new Dimension(0, 25));
        registrationField.setFont(inputFont);
        registrationField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Add placeholder functionality for DTI/SEC field
        ComponentFactory.addPlaceholder(registrationField, "If applicable");

        formPanel.add(registrationField, gbc);

        // Contact Number ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 7; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel contactLabel = new JLabel("Contact Number:");
        contactLabel.setFont(labelFont);
        formPanel.add(contactLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField contactField = new JTextField(20);
        contactField.setPreferredSize(new Dimension(0, 25));
        contactField.setFont(inputFont);
        contactField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ComponentFactory.addPlaceholder(contactField, "09XX-XXX-XXXX");
        formPanel.add(contactField, gbc);

        // Email Address ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 8; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel emailLabel = new JLabel("Email Address:");
        emailLabel.setFont(labelFont);
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(0, 25));
        emailField.setFont(inputFont);
        emailField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Add placeholder functionality for email field
        ComponentFactory.addPlaceholder(emailField, "Optional");

        formPanel.add(emailField, gbc);

        // Date of Filing ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 9; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel filingDateLabel = new JLabel("Date of Filing:");
        filingDateLabel.setFont(labelFont);
        formPanel.add(filingDateLabel, gbc);

        // Date panel with combo boxes for filing date
        JPanel filingDatePanel = new JPanel();
        filingDatePanel.setPreferredSize(new Dimension(0, 25));
        filingDatePanel.setFont(inputFont);
        filingDatePanel.setLayout(new BoxLayout(filingDatePanel, BoxLayout.X_AXIS));
        filingDatePanel.setOpaque(false);

        JComboBox<String> filingYearCombo = new JComboBox<>();
        JComboBox<String> filingMonthCombo = new JComboBox<>();
        JComboBox<String> filingDayCombo = new JComboBox<>();

        filingYearCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        filingMonthCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        filingDayCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        filingDatePanel.add(new JLabel("Month:"));
        filingDatePanel.add(Box.createHorizontalStrut(5));
        filingDatePanel.add(filingMonthCombo);
        filingDatePanel.add(Box.createHorizontalStrut(10));

        filingDatePanel.add(new JLabel("Day:"));
        filingDatePanel.add(Box.createHorizontalStrut(5));
        filingDatePanel.add(filingDayCombo);
        filingDatePanel.add(Box.createHorizontalStrut(10));

        filingDatePanel.add(new JLabel("Year:"));
        filingDatePanel.add(Box.createHorizontalStrut(5));
        filingDatePanel.add(filingYearCombo);

        // Initialize filing date combo boxes using your helper method
        ComponentFactory.setupDateComboBoxes(filingYearCombo, filingMonthCombo, filingDayCombo);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(filingDatePanel, gbc);

        // Required Documents (Checkbox) ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 10; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel requirementsLabel = new JLabel("Required Documents:");
        requirementsLabel.setFont(labelFont);
        formPanel.add(requirementsLabel, gbc);

        String[] requirementOptions = {
                "Sketch of business location (hand-drawn or printed map)",
                "Original copy of Lease Contract or Land Ownership Document",
                "DTI/SEC Registration Certificate (if applicable)",
                "Valid ID of Business Owner",
                "Barangay Clearance"
        };

        JPanel requirementsPanel = new JPanel();
        requirementsPanel.setLayout(new BoxLayout(requirementsPanel, BoxLayout.Y_AXIS));
        requirementsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        requirementsPanel.setOpaque(false);

        for (String text : requirementOptions) {
            JCheckBox checkbox = new JCheckBox(text);
            checkbox.setFont(inputFont);
            checkbox.setOpaque(false);
            requirementsPanel.add(checkbox);
        }

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(requirementsPanel, gbc);

        return formPanel;
    }

    //Business Permit Application Form (Renewal)
    public static JPanel createRenewalBusinessPermitForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Business Name ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel businessNameLabel = new JLabel("Business Name:");
        businessNameLabel.setFont(labelFont);
        formPanel.add(businessNameLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField businessNameField = new JTextField(20);
        businessNameField.setPreferredSize(new Dimension(0, 25));
        businessNameField.setFont(inputFont);
        businessNameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ComponentFactory.addPlaceholder(businessNameField, "e.g., Market Shop");
        formPanel.add(businessNameField, gbc);

        // Owner's Full Name ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel ownerNameLabel = new JLabel("Owner's Full Name:");
        ownerNameLabel.setFont(labelFont);
        formPanel.add(ownerNameLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField ownerNameField = new JTextField(20);
        ownerNameField.setPreferredSize(new Dimension(0, 25));
        ownerNameField.setFont(inputFont);
        ownerNameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ComponentFactory.addPlaceholder(ownerNameField, "Last Name, First Name, Middle Initial");
        formPanel.add(ownerNameField, gbc);

        // Last Year's Barangay Business Permit Number ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lastPermitNumberLabel = new JLabel("Last Year's Barangay Business Permit Number:");
        lastPermitNumberLabel.setFont(labelFont);
        formPanel.add(lastPermitNumberLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField lastPermitNumberField = new JTextField();
        lastPermitNumberField.setPreferredSize(new Dimension(0, 25));
        lastPermitNumberField.setFont(inputFont);
        lastPermitNumberField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Add placeholder functionality for Last Year's Permit Number field
        ComponentFactory.addPlaceholder(lastPermitNumberField, "Enter previous permit number");

        formPanel.add(lastPermitNumberField, gbc);

        // Updated DTI/SEC Registration Number ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel updatedRegistrationLabel = new JLabel("Updated DTI/SEC Registration Number:");
        updatedRegistrationLabel.setFont(labelFont);
        formPanel.add(updatedRegistrationLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField updatedRegistrationField = new JTextField();
        updatedRegistrationField.setPreferredSize(new Dimension(0, 25));
        updatedRegistrationField.setFont(inputFont);
        updatedRegistrationField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Add placeholder functionality for Updated DTI/SEC field
        ComponentFactory.addPlaceholder(updatedRegistrationField, "Leave blank if not updated");

        formPanel.add(updatedRegistrationField, gbc);

        // Have your business details changed since last year? (Radio Button) ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel businessChangedLabel = new JLabel("Have your business details changed since last year?");
        businessChangedLabel.setFont(labelFont);
        formPanel.add(businessChangedLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;

        String[] businessChangedOptions = {"Yes", "No"};
        ButtonGroup businessChangedGroup = new ButtonGroup();
        JPanel businessChangedPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        businessChangedPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        businessChangedPanel.setOpaque(false);

        // Keep references to individual buttons
        JRadioButton yesButton = null;
        JRadioButton noButton = null;

        for (String option : businessChangedOptions) {
            JRadioButton radio = new JRadioButton(option);
            radio.setOpaque(false);
            radio.setFont(inputFont);
            businessChangedGroup.add(radio);
            businessChangedPanel.add(radio);

            if (option.equals("Yes")) yesButton = radio;
            else if (option.equals("No")) noButton = radio;
        }

        formPanel.add(businessChangedPanel, gbc);

        // Please describe what has changed ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; gbc.weighty = 0;
        JLabel changesDescriptionLabel = new JLabel("Please describe what has changed:");
        changesDescriptionLabel.setFont(labelFont);
        formPanel.add(changesDescriptionLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 0;
        JTextArea changesDescriptionField = new JTextArea();
        changesDescriptionField.setRows(3);
        changesDescriptionField.setFont(inputFont);
        changesDescriptionField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        changesDescriptionField.setEnabled(false); // initially disabled
        changesDescriptionField.setBackground(UIManager.getColor("TextField.inactiveBackground")); // Add this line

        ComponentFactory.addPlaceholder(changesDescriptionField,
                "e.g., Business Address, Nature of Operation, Ownership. Leave blank if there are no changes");

        JScrollPane changesDescriptionScroll = new JScrollPane(changesDescriptionField);
        formPanel.add(changesDescriptionScroll, gbc);

        // Apply helper method logic
        if (yesButton != null && noButton != null) {
            ComponentFactory.setupRadioToggleBehavior(yesButton, noButton, changesDescriptionField,
                    "e.g., Business Address, Nature of Operation, Ownership. Leave blank if there are no changes");
        }

        // Contact Number ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; gbc.weighty = 0;
        JLabel contactNumberLabel = new JLabel("Contact Number:");
        contactNumberLabel.setFont(labelFont);
        formPanel.add(contactNumberLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField contactNumberField = new JTextField(20);
        contactNumberField.setPreferredSize(new Dimension(0, 25));
        contactNumberField.setFont(inputFont);
        contactNumberField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ComponentFactory.addPlaceholder(contactNumberField, "09XX-XXX-XXXX");
        formPanel.add(contactNumberField, gbc);

        // Email Address ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 7; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel emailAddressLabel = new JLabel("Email Address:");
        emailAddressLabel.setFont(labelFont);
        formPanel.add(emailAddressLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField emailAddressField = new JTextField();
        emailAddressField.setPreferredSize(new Dimension(0, 25));
        emailAddressField.setFont(inputFont);
        emailAddressField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Add placeholder functionality for Email Address field
        ComponentFactory.addPlaceholder(emailAddressField, "Optional");

        formPanel.add(emailAddressField, gbc);

        // Date of Filing ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 8; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; gbc.weighty = 0;
        JLabel filingDateLabel = new JLabel("Date of Filing:");
        filingDateLabel.setFont(labelFont);
        formPanel.add(filingDateLabel, gbc);

        // Date panel with combo boxes for filing date
        JPanel filingDatePanel = new JPanel();
        filingDatePanel.setPreferredSize(new Dimension(0, 25));
        filingDatePanel.setFont(inputFont);
        filingDatePanel.setLayout(new BoxLayout(filingDatePanel, BoxLayout.X_AXIS));
        filingDatePanel.setOpaque(false);

        JComboBox<String> filingYearCombo = new JComboBox<>();
        JComboBox<String> filingMonthCombo = new JComboBox<>();
        JComboBox<String> filingDayCombo = new JComboBox<>();

        filingYearCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        filingMonthCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        filingDayCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        filingDatePanel.add(new JLabel("Month:"));
        filingDatePanel.add(Box.createHorizontalStrut(5));
        filingDatePanel.add(filingMonthCombo);
        filingDatePanel.add(Box.createHorizontalStrut(10));

        filingDatePanel.add(new JLabel("Day:"));
        filingDatePanel.add(Box.createHorizontalStrut(5));
        filingDatePanel.add(filingDayCombo);
        filingDatePanel.add(Box.createHorizontalStrut(10));

        filingDatePanel.add(new JLabel("Year:"));
        filingDatePanel.add(Box.createHorizontalStrut(5));
        filingDatePanel.add(filingYearCombo);

        // Initialize filing date combo boxes using your helper method
        ComponentFactory.setupDateComboBoxes(filingYearCombo, filingMonthCombo, filingDayCombo);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(filingDatePanel, gbc);

        // Required Documents (Checkbox) ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 9; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel requiredDocumentsLabel = new JLabel("Required Documents:");
        requiredDocumentsLabel.setFont(labelFont);
        formPanel.add(requiredDocumentsLabel, gbc);

        String[] requiredDocumentOptions = {
                "Original copy of Last Year's Barangay Business Permit",
                "Updated DTI/SEC documents (if changes were made)",
                "Valid ID of Business Owner",
                "Additional documents if business details have changed"
        };

        JPanel requiredDocumentsPanel = new JPanel();
        requiredDocumentsPanel.setLayout(new BoxLayout(requiredDocumentsPanel, BoxLayout.Y_AXIS));
        requiredDocumentsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        requiredDocumentsPanel.setOpaque(false);

        for (String text : requiredDocumentOptions) {
            JCheckBox checkbox = new JCheckBox(text);
            checkbox.setFont(inputFont);
            checkbox.setOpaque(false);
            requiredDocumentsPanel.add(checkbox);
        }

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(requiredDocumentsPanel, gbc);

        return formPanel;
    }

    //Certificate for Business Closure Form
    public static JPanel createBusinessClosureForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Name of Business Owner / Proprietor ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel ownerNameLabel = new JLabel("Name of Business Owner / Proprietor:");
        ownerNameLabel.setFont(labelFont);
        formPanel.add(ownerNameLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField ownerNameField = new JTextField(20);
        ownerNameField.setPreferredSize(new Dimension(0, 25));
        ownerNameField.setFont(inputFont);
        ownerNameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ComponentFactory.addPlaceholder(ownerNameField,"Last Name, First Name, Middle Initial");
        formPanel.add(ownerNameField, gbc);

        // Business Name / Trade Name ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel businessNameLabel = new JLabel("Business Name / Trade Name:");
        businessNameLabel.setFont(labelFont);
        formPanel.add(businessNameLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField businessNameField = new JTextField(20);
        businessNameField.setPreferredSize(new Dimension(0, 25));
        businessNameField.setFont(inputFont);
        businessNameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ComponentFactory.addPlaceholder(businessNameField, "e.g., Market Shop");
        formPanel.add(businessNameField, gbc);

        // Business Address ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel businessAddressLabel = new JLabel("Business Address:");
        businessAddressLabel.setFont(labelFont);
        formPanel.add(businessAddressLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField businessAddressField = new JTextField(20);
        businessAddressField.setPreferredSize(new Dimension(0, 25));
        businessAddressField.setFont(inputFont);
        businessAddressField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ComponentFactory.addPlaceholder(businessAddressField, "House/Building/Blk/Lot No., Street, Barangay, City/Municipality, Province");
        formPanel.add(businessAddressField, gbc);

        // Type of Business (Radio Button) ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel businessTypeLabel = new JLabel("Type of Business:");
        businessTypeLabel.setFont(labelFont);
        formPanel.add(businessTypeLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;

        String[] businessTypeOptions = {
                "Sole Proprietorship",
                "Partnership",
                "Corporation",
                "Other"
        };

        ButtonGroup businessTypeGroup = new ButtonGroup();
        JPanel businessTypePanel = new JPanel(new GridLayout(2, 2, 5, 5)); // 2 rows, 2 cols
        businessTypePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        businessTypePanel.setOpaque(false);

        for (String option : businessTypeOptions) {
            JRadioButton radio = new JRadioButton(option);
            radio.setOpaque(false);
            radio.setFont(inputFont);
            businessTypeGroup.add(radio);
            businessTypePanel.add(radio);
        }

        formPanel.add(businessTypePanel, gbc);

        // Barangay Business Permit / Clearance Number ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel permitNumberLabel = new JLabel("Barangay Business Permit / Clearance Number:");
        permitNumberLabel.setFont(labelFont);
        formPanel.add(permitNumberLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField permitNumberField = new JTextField(20);
        permitNumberField.setPreferredSize(new Dimension(0, 25));
        permitNumberField.setFont(inputFont);
        permitNumberField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ComponentFactory.addPlaceholder(permitNumberField, "e.g., BMA-2024-12345");
        formPanel.add(permitNumberField, gbc);

        // Date of Closure ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel closureDateLabel = new JLabel("Date of Closure:");
        closureDateLabel.setFont(labelFont);
        formPanel.add(closureDateLabel, gbc);

        // Date panel with combo boxes for closure date
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

        // Initialize closure date combo boxes using your helper method
        ComponentFactory.setupDateComboBoxes(closureYearCombo, closureMonthCombo, closureDayCombo);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(closureDatePanel, gbc);

        // Reason for Closure (Drop Down) ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel closureReasonLabel = new JLabel("Reason for Closure:");
        closureReasonLabel.setFont(labelFont);
        formPanel.add(closureReasonLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;

        String[] closureReasonOptions = {
                "Select Reason for Closure",
                "Ceased Operations",
                "Financial Loss",
                "Relocation",
                "Retirement",
                "Change of Business",
                "Others"
        };

        JComboBox<String> closureReasonCombo = new JComboBox<>(closureReasonOptions);
        closureReasonCombo.setPreferredSize(new Dimension(0, 25));
        closureReasonCombo.setFont(inputFont);
        closureReasonCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        formPanel.add(closureReasonCombo, gbc);

        // Please Specify Your Reason for Closure ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 7; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; gbc.weighty = 0;
        JLabel additionalDetailsLabel = new JLabel("Please specify your reason for closure:");
        additionalDetailsLabel.setFont(labelFont);
        formPanel.add(additionalDetailsLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField additionalDetailsField = new JTextField();
        additionalDetailsField.setPreferredSize(new Dimension(0, 25));
        additionalDetailsField.setFont(inputFont);
        additionalDetailsField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        additionalDetailsField.setEnabled(false);
        additionalDetailsField.setBackground(UIManager.getColor("Textfield.inactiveBackground"));

        // Add placeholder functionality for Additional Details text area
        ComponentFactory.addPlaceholder(additionalDetailsField,"Specify if 'Others' is selected");
        formPanel.add(additionalDetailsField, gbc);

        ComponentFactory.setupEducationFieldToggle(closureReasonCombo, additionalDetailsField, "Specify if 'Others' is selected");

        // Taxpayer Identification Number (TIN) ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 8; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel tinLabel = new JLabel("Taxpayer Identification Number (TIN):");
        tinLabel.setFont(labelFont);
        formPanel.add(tinLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField tinField = new JTextField();
        tinField.setPreferredSize(new Dimension(0, 25));
        tinField.setFont(inputFont);
        tinField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Add placeholder functionality for TIN field
        ComponentFactory.addPlaceholder(tinField, "e.g., 123-456-789-000");

        formPanel.add(tinField, gbc);

        // DTI / SEC Registration Number ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 9; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel registrationLabel = new JLabel("DTI / SEC Registration Number:");
        registrationLabel.setFont(labelFont);
        formPanel.add(registrationLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField registrationField = new JTextField();
        registrationField.setPreferredSize(new Dimension(0, 25));
        registrationField.setFont(inputFont);
        registrationField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Add placeholder functionality for DTI/SEC field
        ComponentFactory.addPlaceholder(registrationField, "e.g., DTI-123456-2023 (if applicable)");

        formPanel.add(registrationField, gbc);

        // Date of Filing ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 10; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; gbc.weighty = 0;
        JLabel filingDateLabel = new JLabel("Date of Filing:");
        filingDateLabel.setFont(labelFont);
        formPanel.add(filingDateLabel, gbc);

        // Date panel with combo boxes for filing date
        JPanel filingDatePanel = new JPanel();
        filingDatePanel.setPreferredSize(new Dimension(0, 25));
        filingDatePanel.setFont(inputFont);
        filingDatePanel.setLayout(new BoxLayout(filingDatePanel, BoxLayout.X_AXIS));
        filingDatePanel.setOpaque(false);

        JComboBox<String> filingYearCombo = new JComboBox<>();
        JComboBox<String> filingMonthCombo = new JComboBox<>();
        JComboBox<String> filingDayCombo = new JComboBox<>();

        filingYearCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        filingMonthCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        filingDayCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        filingDatePanel.add(new JLabel("Month:"));
        filingDatePanel.add(Box.createHorizontalStrut(5));
        filingDatePanel.add(filingMonthCombo);
        filingDatePanel.add(Box.createHorizontalStrut(10));

        filingDatePanel.add(new JLabel("Day:"));
        filingDatePanel.add(Box.createHorizontalStrut(5));
        filingDatePanel.add(filingDayCombo);
        filingDatePanel.add(Box.createHorizontalStrut(10));

        filingDatePanel.add(new JLabel("Year:"));
        filingDatePanel.add(Box.createHorizontalStrut(5));
        filingDatePanel.add(filingYearCombo);

        // Initialize filing date combo boxes using your helper method
        ComponentFactory.setupDateComboBoxes(filingYearCombo, filingMonthCombo, filingDayCombo);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(filingDatePanel, gbc);

        // Other Requirements (Checkbox) ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 11; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel requirementsLabel = new JLabel("Other Requirements:");
        requirementsLabel.setFont(labelFont);
        formPanel.add(requirementsLabel, gbc);

        String[] requirementOptions = {
                "Original copy of Barangay Business Permit",
                "Valid ID of Business Owner",
                "DTI / SEC Certificate (if applicable)",
                "BIR Certificate of Registration (if applicable)",
                "Clearance from other government agencies (if applicable)"
        };

        JPanel requirementsPanel = new JPanel();
        requirementsPanel.setLayout(new BoxLayout(requirementsPanel, BoxLayout.Y_AXIS));
        requirementsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        requirementsPanel.setOpaque(false);

        for (String text : requirementOptions) {
            JCheckBox checkbox = new JCheckBox(text);
            checkbox.setFont(inputFont);
            checkbox.setOpaque(false);
            requirementsPanel.add(checkbox);
        }

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(requirementsPanel, gbc);

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
                "Specific Information for Application",
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
        docCheckGbc.gridx = 0; docCheckGbc.gridy = 0; docCheckGbc.fill = GridBagConstraints.HORIZONTAL; docCheckGbc.weightx = 1.0;

        String[] documentOptions = {
                "Valid ID (Driver's License, SSS ID, Postal ID, etc.)",
                "Proof of Income (if applicable)",
                "Receipt of Payment (if applicable)"
        };

        JPanel documentsPanel = new JPanel();
        documentsPanel.setLayout(new BoxLayout(documentsPanel, BoxLayout.Y_AXIS));
        documentsPanel.setOpaque(false);

        for (String text : documentOptions) {
            JCheckBox checkbox = new JCheckBox(text);
            checkbox.setFont(inputFont);
            checkbox.setOpaque(false);
            documentsPanel.add(checkbox);
        }

        documentsSection.add(documentsPanel, docCheckGbc);
        formPanel.add(documentsSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        return formPanel;
    }

    //Certificate for Community Tax Certificate Form (Cedula)
    public static JPanel createsCommunityTaxCertificateForm(){
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
        JPanel docInfoSection = new JPanel(new GridBagLayout());
        docInfoSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Document Information",
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
        certNumberField.setPreferredSize(new Dimension(0, 25));
        certNumberField.setFont(inputFont);
        certNumberField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        certNumberField.setEditable(false);
        certNumberField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
        ComponentFactory.addPlaceholder(certNumberField, "Auto-generated upon processing");
        docInfoSection.add(certNumberField, docGbc);

        // Date Issued
        docGbc.gridx = 0; docGbc.gridy = 1; docGbc.fill = GridBagConstraints.NONE; docGbc.weightx = 0;
        JLabel dateIssuedLabel = new JLabel("Date Issued:");
        dateIssuedLabel.setFont(labelFont);
        docInfoSection.add(dateIssuedLabel, docGbc);

        docGbc.gridx = 1; docGbc.fill = GridBagConstraints.HORIZONTAL; docGbc.weightx = 1.0;
        JTextField dateIssuedField = new JTextField(20);
        dateIssuedField.setPreferredSize(new Dimension(0, 25));
        dateIssuedField.setFont(inputFont);
        dateIssuedField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        dateIssuedField.setEditable(false);
        dateIssuedField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
        ComponentFactory.addPlaceholder(dateIssuedField, "Auto-generated upon processing");
        docInfoSection.add(dateIssuedField, docGbc);

        formPanel.add(docInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Personal Information Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
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

        JPanel dobPanel = new JPanel();
        dobPanel.setPreferredSize(new Dimension(0, 25));
        dobPanel.setFont(inputFont);
        dobPanel.setLayout(new BoxLayout(dobPanel, BoxLayout.X_AXIS));
        dobPanel.setOpaque(false);

        JComboBox<String> dobYearCombo = new JComboBox<>();
        JComboBox<String> dobMonthCombo = new JComboBox<>();
        JComboBox<String> dobDayCombo = new JComboBox<>();

        dobYearCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        dobMonthCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        dobDayCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        dobPanel.add(new JLabel("Month:"));
        dobPanel.add(Box.createHorizontalStrut(5));
        dobPanel.add(dobMonthCombo);
        dobPanel.add(Box.createHorizontalStrut(10));

        dobPanel.add(new JLabel("Day:"));
        dobPanel.add(Box.createHorizontalStrut(5));
        dobPanel.add(dobDayCombo);
        dobPanel.add(Box.createHorizontalStrut(10));

        dobPanel.add(new JLabel("Year:"));
        dobPanel.add(Box.createHorizontalStrut(5));
        dobPanel.add(dobYearCombo);
        dobPanel.add(Box.createHorizontalStrut(10));

        dobPanel.add(new JLabel("Age:"));
        dobPanel.add(Box.createHorizontalStrut(5));
        JTextField ageField = new JTextField(5);
        ageField.setFont(inputFont);
        ageField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ageField.setEditable(false);
        ageField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
        ComponentFactory.addPlaceholder(ageField, "Auto-calculated");
        dobPanel.add(ageField);

        ComponentFactory.setupDateComboBoxes(dobYearCombo, dobMonthCombo, dobDayCombo);

        personalGbc.gridx = 1; personalGbc.fill = GridBagConstraints.HORIZONTAL; personalGbc.weightx = 1.0;
        personalInfoSection.add(dobPanel, personalGbc);

        ComponentFactory.addAgeCalculationListener(dobYearCombo, dobMonthCombo, dobDayCombo, ageField);

        // Complete Address
        personalGbc.gridx = 0; personalGbc.gridy = 2; personalGbc.fill = GridBagConstraints.NONE; personalGbc.weightx = 0;
        JLabel addressLabel = new JLabel("Complete Address:");
        addressLabel.setFont(labelFont);
        personalInfoSection.add(addressLabel, personalGbc);

        personalGbc.gridx = 1; personalGbc.fill = GridBagConstraints.HORIZONTAL; personalGbc.weightx = 1.0;
        JTextField addressField = new JTextField(20);
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
        contactField.setPreferredSize(new Dimension(0, 25));
        contactField.setFont(inputFont);
        contactField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(contactField, "Mobile number or landline with area code");
        personalInfoSection.add(contactField, personalGbc);

        formPanel.add(personalInfoSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Specific Information for Application Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridwidth = 2;
        JPanel specificInfoSection = new JPanel(new GridBagLayout());
        specificInfoSection.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Specific Information for Application",
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
        docCheckGbc.gridx = 0; docCheckGbc.gridy = 0; docCheckGbc.fill = GridBagConstraints.HORIZONTAL; docCheckGbc.weightx = 1.0;

        String[] documentOptions = {
                "Valid ID (Driver's License, SSS ID, Postal ID, etc.)",
                "Proof of Income (if applicable)",
                "Receipt of Payment (if applicable)"
        };

        JPanel documentsPanel = new JPanel();
        documentsPanel.setLayout(new BoxLayout(documentsPanel, BoxLayout.Y_AXIS));
        documentsPanel.setOpaque(false);

        for (String text : documentOptions) {
            JCheckBox checkbox = new JCheckBox(text);
            checkbox.setFont(inputFont);
            checkbox.setOpaque(false);
            documentsPanel.add(checkbox);
        }

        documentsSection.add(documentsPanel, docCheckGbc);
        formPanel.add(documentsSection, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        return formPanel;
    }

    //Certificate for First Time Job Seeker Form
    public static JPanel createFirstTimeJobSeekerForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Full Name ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameLabel.setFont(labelFont);
        formPanel.add(fullNameLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField fullNameField = new JTextField(20);
        fullNameField.setPreferredSize(new Dimension(0, 25));
        fullNameField.setFont(inputFont);
        fullNameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ComponentFactory.addPlaceholder(fullNameField, "Last Name, First Name, Middle Initial");
        formPanel.add(fullNameField, gbc);

        // Age (Auto-calculated) ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(labelFont);
        formPanel.add(ageLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField ageField = new JTextField(20);
        ageField.setPreferredSize(new Dimension(0, 25));
        ageField.setFont(inputFont);
        ageField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ageField.setEditable(false); // Auto-calculated, so not editable
        ageField.setBackground(UIManager.getColor("TextField.inactiveBackground")); // Visual indication it's auto-calculated
        ComponentFactory.addPlaceholder(ageField, "Auto-calculated from Date of Birth");
        formPanel.add(ageField, gbc);

        // Date of Birth ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setFont(labelFont);
        formPanel.add(dobLabel, gbc);

        // Date panel with combo boxes for date of birth
        JPanel dobPanel = new JPanel();
        dobPanel.setPreferredSize(new Dimension(0, 25));
        dobPanel.setFont(inputFont);
        dobPanel.setLayout(new BoxLayout(dobPanel, BoxLayout.X_AXIS));
        dobPanel.setOpaque(false);

        JComboBox<String> dobYearCombo = new JComboBox<>();
        JComboBox<String> dobMonthCombo = new JComboBox<>();
        JComboBox<String> dobDayCombo = new JComboBox<>();

        dobYearCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        dobMonthCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        dobDayCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        dobPanel.add(new JLabel("Month:"));
        dobPanel.add(Box.createHorizontalStrut(5));
        dobPanel.add(dobMonthCombo);
        dobPanel.add(Box.createHorizontalStrut(10));

        dobPanel.add(new JLabel("Day:"));
        dobPanel.add(Box.createHorizontalStrut(5));
        dobPanel.add(dobDayCombo);
        dobPanel.add(Box.createHorizontalStrut(10));

        dobPanel.add(new JLabel("Year:"));
        dobPanel.add(Box.createHorizontalStrut(5));
        dobPanel.add(dobYearCombo);

        // Initialize date of birth combo boxes
        ComponentFactory.setupDateComboBoxes(dobYearCombo, dobMonthCombo, dobDayCombo);

        // Add action listeners to auto-calculate age when DOB changes
        ActionListener ageCalculator = e -> ComponentFactory.calculateAge(dobYearCombo, dobMonthCombo, dobDayCombo, ageField);
        dobYearCombo.addActionListener(ageCalculator);
        dobMonthCombo.addActionListener(ageCalculator);
        dobDayCombo.addActionListener(ageCalculator);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(dobPanel, gbc);

        // Complete Address ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel addressLabel = new JLabel("Complete Address:");
        addressLabel.setFont(labelFont);
        formPanel.add(addressLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField addressField = new JTextField(20);
        addressField.setPreferredSize(new Dimension(0, 25));
        addressField.setFont(inputFont);
        addressField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ComponentFactory.addPlaceholder(addressField, "House/Building/Blk/Lot No., Street, Barangay, City/Municipality, Province");
        formPanel.add(addressField, gbc);

        // Contact Number ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel contactLabel = new JLabel("Contact Number:");
        contactLabel.setFont(labelFont);
        formPanel.add(contactLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField contactField = new JTextField(20);
        contactField.setPreferredSize(new Dimension(0, 25));
        contactField.setFont(inputFont);
        contactField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ComponentFactory.addPlaceholder(contactField, "09XX-XXX-XXXX");
        formPanel.add(contactField, gbc);

        // Email Address ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel emailLabel = new JLabel("Email Address:");
        emailLabel.setFont(labelFont);
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField emailField = new JTextField(20);
        emailField.setPreferredSize(new Dimension(0, 25));
        emailField.setFont(inputFont);
        emailField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ComponentFactory.addPlaceholder(emailField, "Optional");
        formPanel.add(emailField, gbc);

        // Educational Background (Dropdown) ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel educationLabel = new JLabel("Educational Background:");
        educationLabel.setFont(labelFont);
        formPanel.add(educationLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;

        String[] educationOptions = {
                "Select your highest educational attainment",
                "Elementary Graduate",
                "High School Graduate",
                "Senior High School Graduate",
                "Vocational/Technical Course Graduate",
                "College Undergraduate",
                "College Graduate",
                "Post Graduate (Masters/Doctorate)",
                "Others"
        };

        JComboBox<String> educationCombo = new JComboBox<>(educationOptions);
        educationCombo.setPreferredSize(new Dimension(0, 25));
        educationCombo.setFont(inputFont);
        educationCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        formPanel.add(educationCombo, gbc);

        // Please specify educational attainment ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 7; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel specifyEducationLabel = new JLabel("Please specify your educational attainment:");
        specifyEducationLabel.setFont(labelFont);
        formPanel.add(specifyEducationLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField specifyEducationField = new JTextField();
        specifyEducationField.setPreferredSize(new Dimension(0, 25));
        specifyEducationField.setFont(inputFont);
        specifyEducationField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specifyEducationField.setEnabled(false); // Initially disabled
        specifyEducationField.setBackground(UIManager.getColor("Textfield.inactiveBackground"));

        ComponentFactory.addPlaceholder(specifyEducationField, "Specify if 'Others' is selected");
        formPanel.add(specifyEducationField, gbc);

        ComponentFactory.setupEducationFieldToggle(educationCombo, specifyEducationField, "Specify if 'Others' is selected");

        // Declaration of No Prior Employment (Checkbox) ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 8; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel declarationLabel = new JLabel("Declaration of No Prior Employment:");
        declarationLabel.setFont(labelFont);
        formPanel.add(declarationLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JCheckBox declarationCheckbox = new JCheckBox("<html>I declare that I have never been employed in any capacity.<br>This is my first job application.</html>");
        declarationCheckbox.setFont(inputFont);
        declarationCheckbox.setOpaque(false);
        formPanel.add(declarationCheckbox, gbc);

        // Purpose of Request (Dropdown) ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 9; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel purposeLabel = new JLabel("Purpose of Request:");
        purposeLabel.setFont(labelFont);
        formPanel.add(purposeLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;

        String[] purposeOptions = {
                "Select the purpose of your request",
                "NBI Clearance Application",
                "PRC Registration",
                "Passport Application",
                "Job Application Requirement",
                "Government Examination",
                "Scholarship Application",
                "Others"
        };

        JComboBox<String> purposeCombo = new JComboBox<>(purposeOptions);
        purposeCombo.setPreferredSize(new Dimension(0, 25));
        purposeCombo.setFont(inputFont);
        purposeCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        formPanel.add(purposeCombo, gbc);

        // Please specify the purpose ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 10; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel specifyPurposeLabel = new JLabel("Please specify the purpose:");
        specifyPurposeLabel.setFont(labelFont);
        formPanel.add(specifyPurposeLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField specifyPurposeField = new JTextField();
        specifyPurposeField.setPreferredSize(new Dimension(0, 25));
        specifyPurposeField.setFont(inputFont);
        specifyPurposeField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specifyPurposeField.setEnabled(false); // Initially disabled

        ComponentFactory.addPlaceholder(specifyPurposeField, "Specify if 'Others' is selected");
        formPanel.add(specifyPurposeField, gbc);

        ComponentFactory.setupEducationFieldToggle(purposeCombo, specifyPurposeField, "Specify if 'Others' is selected");

        // Date of Filing ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 11; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel filingDateLabel = new JLabel("Date of Filing:");
        filingDateLabel.setFont(labelFont);
        formPanel.add(filingDateLabel, gbc);

        // Date panel with combo boxes for filing date
        JPanel filingDatePanel = new JPanel();
        filingDatePanel.setPreferredSize(new Dimension(0, 25));
        filingDatePanel.setFont(inputFont);
        filingDatePanel.setLayout(new BoxLayout(filingDatePanel, BoxLayout.X_AXIS));
        filingDatePanel.setOpaque(false);

        JComboBox<String> filingYearCombo = new JComboBox<>();
        JComboBox<String> filingMonthCombo = new JComboBox<>();
        JComboBox<String> filingDayCombo = new JComboBox<>();

        filingYearCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        filingMonthCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        filingDayCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        filingDatePanel.add(new JLabel("Month:"));
        filingDatePanel.add(Box.createHorizontalStrut(5));
        filingDatePanel.add(filingMonthCombo);
        filingDatePanel.add(Box.createHorizontalStrut(10));

        filingDatePanel.add(new JLabel("Day:"));
        filingDatePanel.add(Box.createHorizontalStrut(5));
        filingDatePanel.add(filingDayCombo);
        filingDatePanel.add(Box.createHorizontalStrut(10));

        filingDatePanel.add(new JLabel("Year:"));
        filingDatePanel.add(Box.createHorizontalStrut(5));
        filingDatePanel.add(filingYearCombo);

        // Initialize filing date combo boxes
        ComponentFactory.setupDateComboBoxes(filingYearCombo, filingMonthCombo, filingDayCombo);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(filingDatePanel, gbc);

        // Required Documents (Checkbox) ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 12; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel requirementsLabel = new JLabel("Required Documents:");
        requirementsLabel.setFont(labelFont);
        formPanel.add(requirementsLabel, gbc);

        String[] requirementOptions = {
                "Valid Government-issued ID (Original and Photocopy)",
                "Birth Certificate (Original and Photocopy)",
                "Diploma or Certificate of Graduation (Original and Photocopy)",
                "Transcript of Records (if college graduate or undergraduate)",
                "Barangay Clearance",
                "2 pieces of 2x2 ID Pictures",
                "Completed Application Form"
        };

        JPanel requirementsPanel = new JPanel();
        requirementsPanel.setLayout(new BoxLayout(requirementsPanel, BoxLayout.Y_AXIS));
        requirementsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        requirementsPanel.setOpaque(false);

        for (String text : requirementOptions) {
            JCheckBox checkbox = new JCheckBox(text);
            checkbox.setFont(inputFont);
            checkbox.setOpaque(false);
            requirementsPanel.add(checkbox);
        }

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(requirementsPanel, gbc);

        return formPanel;
    }

    //Certificate for Late Registration Form
    public static JPanel createLateRegistrationForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Type of Registration (Radio Buttons) ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel registrationTypeLabel = new JLabel("Type of Registration:");
        registrationTypeLabel.setFont(labelFont);
        formPanel.add(registrationTypeLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
        radioPanel.setOpaque(false);

        ButtonGroup registrationTypeGroup = new ButtonGroup();
        JRadioButton birthRadio = new JRadioButton("Birth Certificate");
        JRadioButton marriageRadio = new JRadioButton("Marriage Certificate");
        JRadioButton deathRadio = new JRadioButton("Death Certificate");

        birthRadio.setFont(inputFont);
        marriageRadio.setFont(inputFont);
        deathRadio.setFont(inputFont);

        birthRadio.setOpaque(false);
        marriageRadio.setOpaque(false);
        deathRadio.setOpaque(false);

        registrationTypeGroup.add(birthRadio);
        registrationTypeGroup.add(marriageRadio);
        registrationTypeGroup.add(deathRadio);

        radioPanel.add(birthRadio);
        radioPanel.add(marriageRadio);
        radioPanel.add(deathRadio);

        formPanel.add(radioPanel, gbc);

        // Reason for Late Registration (Dropdown) ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel reasonLabel = new JLabel("Reason for Late Registration:");
        reasonLabel.setFont(labelFont);
        formPanel.add(reasonLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;

        String[] reasonOptions = {
                "Select reason for late registration",
                "Lost or Destroyed Documents",
                "Lack of Knowledge about Registration Requirements",
                "Financial Constraints",
                "Geographical Isolation",
                "Family Negligence",
                "Administrative Error",
                "Others"
        };

        JComboBox<String> reasonCombo = new JComboBox<>(reasonOptions);
        reasonCombo.setPreferredSize(new Dimension(0, 25));
        reasonCombo.setFont(inputFont);
        reasonCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        formPanel.add(reasonCombo, gbc);

        // Please specify the reason ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel specifyReasonLabel = new JLabel("Please specify the reason:");
        specifyReasonLabel.setFont(labelFont);
        formPanel.add(specifyReasonLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField specifyReasonField = new JTextField();
        specifyReasonField.setPreferredSize(new Dimension(0, 25));
        specifyReasonField.setFont(inputFont);
        specifyReasonField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specifyReasonField.setEnabled(false); // Initially disabled
        specifyReasonField.setBackground(UIManager.getColor("TextField.inactiveBackground"));

        ComponentFactory.addPlaceholder(specifyReasonField, "Specify if 'Others' is selected");
        formPanel.add(specifyReasonField, gbc);

        ComponentFactory.setupEducationFieldToggle(reasonCombo, specifyReasonField, "Specify if 'Others' is selected");

        // Date when the event occurred ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel eventDateLabel = new JLabel("Date when the event occurred:");
        eventDateLabel.setFont(labelFont);
        formPanel.add(eventDateLabel, gbc);

        // Date panel with combo boxes for event date
        JPanel eventDatePanel = new JPanel();
        eventDatePanel.setPreferredSize(new Dimension(0, 25));
        eventDatePanel.setFont(inputFont);
        eventDatePanel.setLayout(new BoxLayout(eventDatePanel, BoxLayout.X_AXIS));
        eventDatePanel.setOpaque(false);

        JComboBox<String> eventYearCombo = new JComboBox<>();
        JComboBox<String> eventMonthCombo = new JComboBox<>();
        JComboBox<String> eventDayCombo = new JComboBox<>();

        eventYearCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        eventMonthCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        eventDayCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        eventDatePanel.add(new JLabel("Month:"));
        eventDatePanel.add(Box.createHorizontalStrut(5));
        eventDatePanel.add(eventMonthCombo);
        eventDatePanel.add(Box.createHorizontalStrut(10));

        eventDatePanel.add(new JLabel("Day:"));
        eventDatePanel.add(Box.createHorizontalStrut(5));
        eventDatePanel.add(eventDayCombo);
        eventDatePanel.add(Box.createHorizontalStrut(10));

        eventDatePanel.add(new JLabel("Year:"));
        eventDatePanel.add(Box.createHorizontalStrut(5));
        eventDatePanel.add(eventYearCombo);

        // Initialize event date combo boxes
        ComponentFactory.setupDateComboBoxes(eventYearCombo, eventMonthCombo, eventDayCombo);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(eventDatePanel, gbc);

        // Place where the event occurred ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel eventPlaceLabel = new JLabel("Place where the event occurred:");
        eventPlaceLabel.setFont(labelFont);
        formPanel.add(eventPlaceLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField eventPlaceField = new JTextField(20);
        eventPlaceField.setPreferredSize(new Dimension(0, 25));
        eventPlaceField.setFont(inputFont);
        eventPlaceField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ComponentFactory.addPlaceholder(eventPlaceField, "e.g., Birthplace, Wedding Venue, Deathplace");
        formPanel.add(eventPlaceField, gbc);

        // Full Name of Applicant ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel applicantNameLabel = new JLabel("Full Name of Applicant:");
        applicantNameLabel.setFont(labelFont);
        formPanel.add(applicantNameLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField applicantNameField = new JTextField(20);
        applicantNameField.setPreferredSize(new Dimension(0, 25));
        applicantNameField.setFont(inputFont);
        applicantNameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ComponentFactory.addPlaceholder(applicantNameField, "Last Name, First Name, Middle Initial");
        formPanel.add(applicantNameField, gbc);

        // Relationship to the Person in the Record (Dropdown) ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel relationshipLabel = new JLabel("Relationship to the Person in the Record:");
        relationshipLabel.setFont(labelFont);
        formPanel.add(relationshipLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;

        String[] relationshipOptions = {
                "Select Relationship",
                "Self",
                "Parent",
                "Child",
                "Spouse",
                "Sibling",
                "Legal Guardian",
                "Authorized Representative"
        };

        JComboBox<String> relationshipCombo = new JComboBox<>(relationshipOptions);
        relationshipCombo.setPreferredSize(new Dimension(0, 25));
        relationshipCombo.setFont(inputFont);
        relationshipCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        formPanel.add(relationshipCombo, gbc);

        // Date of Filing ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 7; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel filingDateLabel = new JLabel("Date of Filing:");
        filingDateLabel.setFont(labelFont);
        formPanel.add(filingDateLabel, gbc);

        // Date panel with combo boxes for filing date
        JPanel filingDatePanel = new JPanel();
        filingDatePanel.setPreferredSize(new Dimension(0, 25));
        filingDatePanel.setFont(inputFont);
        filingDatePanel.setLayout(new BoxLayout(filingDatePanel, BoxLayout.X_AXIS));
        filingDatePanel.setOpaque(false);

        JComboBox<String> filingYearCombo = new JComboBox<>();
        JComboBox<String> filingMonthCombo = new JComboBox<>();
        JComboBox<String> filingDayCombo = new JComboBox<>();

        filingYearCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        filingMonthCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        filingDayCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        filingDatePanel.add(new JLabel("Month:"));
        filingDatePanel.add(Box.createHorizontalStrut(5));
        filingDatePanel.add(filingMonthCombo);
        filingDatePanel.add(Box.createHorizontalStrut(10));

        filingDatePanel.add(new JLabel("Day:"));
        filingDatePanel.add(Box.createHorizontalStrut(5));
        filingDatePanel.add(filingDayCombo);
        filingDatePanel.add(Box.createHorizontalStrut(10));

        filingDatePanel.add(new JLabel("Year:"));
        filingDatePanel.add(Box.createHorizontalStrut(5));
        filingDatePanel.add(filingYearCombo);

        // Initialize filing date combo boxes
        ComponentFactory.setupDateComboBoxes(filingYearCombo, filingMonthCombo, filingDayCombo);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(filingDatePanel, gbc);

        // Required Documents (Checkbox) ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 8; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel requirementsLabel = new JLabel("Required Documents:");
        requirementsLabel.setFont(labelFont);
        formPanel.add(requirementsLabel, gbc);

        String[] requirementOptions = {
                "Baptismal Certificate (if applicable)",
                "School Records (if applicable)",
                "Barangay Certification (if applicable)",
                "Affidavit of Delayed Registration (if applicable)",
                "Affidavit of Two Disinterested Persons (if applicable)",
                "Valid government-issued ID of the applicant",
                "Proof of relationship (if applying for someone else)",
                "Authorization letter (if applying as a representative)",
                "Payment for registration fees and penalties"
        };

        JPanel requirementsPanel = new JPanel();
        requirementsPanel.setLayout(new BoxLayout(requirementsPanel, BoxLayout.Y_AXIS));
        requirementsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        requirementsPanel.setOpaque(false);

        for (String text : requirementOptions) {
            JCheckBox checkbox = new JCheckBox(text);
            checkbox.setFont(inputFont);
            checkbox.setOpaque(false);
            requirementsPanel.add(checkbox);
        }

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(requirementsPanel, gbc);

        return formPanel;
    }

    // Certificate for Low Income Form
    public static JPanel createLowIncomeCertificateForm() {
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);
        Font sectionFont = new Font("Trebuchet MS", Font.BOLD, 14);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int currentRow = 0;

        // Certificate Number ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = currentRow; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel certNumberLabel = new JLabel("Certificate Number:");
        certNumberLabel.setFont(labelFont);
        formPanel.add(certNumberLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField certNumberField = new JTextField(20);
        certNumberField.setPreferredSize(new Dimension(0, 25));
        certNumberField.setFont(inputFont);
        certNumberField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(certNumberField, "Auto-generated");
        formPanel.add(certNumberField, gbc);
        currentRow++;

        // Date Issued ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = currentRow; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel dateIssuedLabel = new JLabel("Date Issued:");
        dateIssuedLabel.setFont(labelFont);
        formPanel.add(dateIssuedLabel, gbc);

        JPanel dateIssuedPanel = new JPanel();
        dateIssuedPanel.setPreferredSize(new Dimension(0, 25));
        dateIssuedPanel.setLayout(new BoxLayout(dateIssuedPanel, BoxLayout.X_AXIS));
        dateIssuedPanel.setOpaque(false);

        JComboBox<String> issuedYearCombo = new JComboBox<>();
        JComboBox<String> issuedMonthCombo = new JComboBox<>();
        JComboBox<String> issuedDayCombo = new JComboBox<>();

        issuedYearCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        issuedMonthCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        issuedDayCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        dateIssuedPanel.add(new JLabel("Month:"));
        dateIssuedPanel.add(Box.createHorizontalStrut(5));
        dateIssuedPanel.add(issuedMonthCombo);
        dateIssuedPanel.add(Box.createHorizontalStrut(10));
        dateIssuedPanel.add(new JLabel("Day:"));
        dateIssuedPanel.add(Box.createHorizontalStrut(5));
        dateIssuedPanel.add(issuedDayCombo);
        dateIssuedPanel.add(Box.createHorizontalStrut(10));
        dateIssuedPanel.add(new JLabel("Year:"));
        dateIssuedPanel.add(Box.createHorizontalStrut(5));
        dateIssuedPanel.add(issuedYearCombo);

        ComponentFactory.setupDateComboBoxes(issuedYearCombo, issuedMonthCombo, issuedDayCombo);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(dateIssuedPanel, gbc);
        currentRow++;

        // Barangay ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = currentRow; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel barangayLabel = new JLabel("Barangay:");
        barangayLabel.setFont(labelFont);
        formPanel.add(barangayLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField barangayField = new JTextField(20);
        barangayField.setPreferredSize(new Dimension(0, 25));
        barangayField.setFont(inputFont);
        barangayField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(barangayField, "Enter barangay name");
        formPanel.add(barangayField, gbc);
        currentRow++;

        // Municipality/City ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = currentRow; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel municipalityLabel = new JLabel("Municipality/City:");
        municipalityLabel.setFont(labelFont);
        formPanel.add(municipalityLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField municipalityField = new JTextField(20);
        municipalityField.setPreferredSize(new Dimension(0, 25));
        municipalityField.setFont(inputFont);
        municipalityField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(municipalityField, "Enter municipality or city name");
        formPanel.add(municipalityField, gbc);
        currentRow++;

        // Province ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = currentRow; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel provinceLabel = new JLabel("Province:");
        provinceLabel.setFont(labelFont);
        formPanel.add(provinceLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField provinceField = new JTextField(20);
        provinceField.setPreferredSize(new Dimension(0, 25));
        provinceField.setFont(inputFont);
        provinceField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(provinceField, "Enter province name");
        formPanel.add(provinceField, gbc);
        currentRow++;

        // Applicant's Full Name ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = currentRow; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel applicantNameLabel = new JLabel("Applicant's Full Name:");
        applicantNameLabel.setFont(labelFont);
        formPanel.add(applicantNameLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField applicantNameField = new JTextField(20);
        applicantNameField.setPreferredSize(new Dimension(0, 25));
        applicantNameField.setFont(inputFont);
        applicantNameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(applicantNameField, "Last Name, First Name, Middle Initial");
        formPanel.add(applicantNameField, gbc);
        currentRow++;

        // Complete Address ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = currentRow; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel completeAddressLabel = new JLabel("Complete Address:");
        completeAddressLabel.setFont(labelFont);
        formPanel.add(completeAddressLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField completeAddressField = new JTextField(20);
        completeAddressField.setPreferredSize(new Dimension(0, 25));
        completeAddressField.setFont(inputFont);
        completeAddressField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(completeAddressField, "House/Building/Blk/Lot No., Street, Barangay, City/Municipality, Province");
        formPanel.add(completeAddressField, gbc);
        currentRow++;

        // Section Header: Household Composition & Income Details ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = currentRow; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel householdSectionLabel = new JLabel("Household Composition & Income Details");
        householdSectionLabel.setFont(sectionFont);
        householdSectionLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        formPanel.add(householdSectionLabel, gbc);
        gbc.gridwidth = 1; // Reset gridwidth
        currentRow++;

        // Total Number of Household Members ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = currentRow; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel totalMembersLabel = new JLabel("Total Number of Household Members:");
        totalMembersLabel.setFont(labelFont);
        formPanel.add(totalMembersLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField totalMembersField = new JTextField(20);
        totalMembersField.setPreferredSize(new Dimension(0, 25));
        totalMembersField.setFont(inputFont);
        totalMembersField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(totalMembersField, "Enter number (e.g., 4)");
        formPanel.add(totalMembersField, gbc);
        currentRow++;

        // Number of Dependents ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = currentRow; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel dependentsLabel = new JLabel("Number of Dependents (non-working):");
        dependentsLabel.setFont(labelFont);
        formPanel.add(dependentsLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField dependentsField = new JTextField(20);
        dependentsField.setPreferredSize(new Dimension(0, 25));
        dependentsField.setFont(inputFont);
        dependentsField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(dependentsField, "Enter number (e.g., 2)");
        formPanel.add(dependentsField, gbc);
        currentRow++;

        // Dynamic Household Members Panel ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = currentRow; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel householdMembersPanel = new JPanel();
        householdMembersPanel.setLayout(new BoxLayout(householdMembersPanel, BoxLayout.Y_AXIS));
        householdMembersPanel.setBorder(BorderFactory.createTitledBorder("Household Member Details"));
        householdMembersPanel.setOpaque(false);

        JLabel instructionLabel = new JLabel("Note: Household member details will be generated based on total number entered above");
        instructionLabel.setFont(new Font("Trebuchet MS", Font.ITALIC, 11));
        householdMembersPanel.add(instructionLabel);

        formPanel.add(householdMembersPanel, gbc);
        gbc.gridwidth = 1; // Reset gridwidth
        currentRow++;

        // Section Header: Combined Monthly Family Income ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = currentRow; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel incomeSectionLabel = new JLabel("Combined Monthly Family Income");
        incomeSectionLabel.setFont(sectionFont);
        incomeSectionLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        formPanel.add(incomeSectionLabel, gbc);
        gbc.gridwidth = 1; // Reset gridwidth
        currentRow++;

        // Total Monthly Family Income ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = currentRow; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel totalMonthlyIncomeLabel = new JLabel("Total Monthly Family Income (₱):");
        totalMonthlyIncomeLabel.setFont(labelFont);
        formPanel.add(totalMonthlyIncomeLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField totalMonthlyIncomeField = new JTextField(20);
        totalMonthlyIncomeField.setPreferredSize(new Dimension(0, 25));
        totalMonthlyIncomeField.setFont(inputFont);
        totalMonthlyIncomeField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        totalMonthlyIncomeField.setEditable(false);
        totalMonthlyIncomeField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
        ComponentFactory.addPlaceholder(totalMonthlyIncomeField, "Auto-calculated from household member incomes");
        formPanel.add(totalMonthlyIncomeField, gbc);
        currentRow++;

        // Estimated Annual Income ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = currentRow; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel annualIncomeLabel = new JLabel("Estimated Annual Income (₱):");
        annualIncomeLabel.setFont(labelFont);
        formPanel.add(annualIncomeLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField annualIncomeField = new JTextField(20);
        annualIncomeField.setPreferredSize(new Dimension(0, 25));
        annualIncomeField.setFont(inputFont);
        annualIncomeField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        annualIncomeField.setEditable(false);
        annualIncomeField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
        ComponentFactory.addPlaceholder(annualIncomeField, "Auto-calculated (Monthly Income × 12)");
        formPanel.add(annualIncomeField, gbc);
        currentRow++;

        // Section Header: Source(s) of Income ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = currentRow; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel incomeSourceSectionLabel = new JLabel("Source(s) of Income");
        incomeSourceSectionLabel.setFont(sectionFont);
        incomeSourceSectionLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        formPanel.add(incomeSourceSectionLabel, gbc);
        gbc.gridwidth = 1; // Reset gridwidth
        currentRow++;

        // Primary Sources of Income (Checkboxes) ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = currentRow; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel primarySourcesLabel = new JLabel("Primary Sources of Income:");
        primarySourcesLabel.setFont(labelFont);
        formPanel.add(primarySourcesLabel, gbc);

        String[] incomeSourceOptions = {
                "Regular Employment/Salary",
                "Business/Self-Employment",
                "Farming/Agriculture",
                "Overseas Worker Remittance",
                "Pension/Retirement Benefits",
                "Freelance/Contract Work",
                "Rental Income",
                "Other"
        };

        JPanel incomeSourcesPanel = new JPanel();
        incomeSourcesPanel.setLayout(new BoxLayout(incomeSourcesPanel, BoxLayout.Y_AXIS));
        incomeSourcesPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        incomeSourcesPanel.setOpaque(false);

        for (String source : incomeSourceOptions) {
            JCheckBox checkbox = new JCheckBox(source);
            checkbox.setFont(inputFont);
            checkbox.setOpaque(false);
            incomeSourcesPanel.add(checkbox);
        }

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(incomeSourcesPanel, gbc);
        currentRow++;

        // Detailed Description of Income Sources ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = currentRow; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel detailedDescriptionLabel = new JLabel("Detailed Description of Income Sources:");
        detailedDescriptionLabel.setFont(labelFont);
        formPanel.add(detailedDescriptionLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextArea detailedDescriptionArea = new JTextArea(3, 20);
        detailedDescriptionArea.setFont(inputFont);
        detailedDescriptionArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        detailedDescriptionArea.setLineWrap(true);
        detailedDescriptionArea.setWrapStyleWord(true);

        JScrollPane descriptionScrollPane = new JScrollPane(detailedDescriptionArea);
        descriptionScrollPane.setPreferredSize(new Dimension(0, 75));
        formPanel.add(descriptionScrollPane, gbc);
        currentRow++;

        // Section Header: Purpose of Request ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = currentRow; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel purposeSectionLabel = new JLabel("Purpose of Request");
        purposeSectionLabel.setFont(sectionFont);
        purposeSectionLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        formPanel.add(purposeSectionLabel, gbc);
        gbc.gridwidth = 1; // Reset gridwidth
        currentRow++;

        // Purpose of Certificate ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = currentRow; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel purposeLabel = new JLabel("What is the purpose of this certificate?:");
        purposeLabel.setFont(labelFont);
        formPanel.add(purposeLabel, gbc);

        String[] purposeOptions = {
                "Select the purpose of your request",
                "Scholarship Application",
                "Medical Aid/Financial Assistance",
                "Government Program/Benefits",
                "Loan Application",
                "Employment Requirement",
                "Legal Proceeding",
                "Others"
        };

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JComboBox<String> purposeCombo = new JComboBox<>(purposeOptions);
        purposeCombo.setPreferredSize(new Dimension(0, 25));
        purposeCombo.setFont(inputFont);
        purposeCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        formPanel.add(purposeCombo, gbc);
        currentRow++;

        // Specify Purpose ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = currentRow; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel specifyPurposeLabel = new JLabel("Please specify the purpose of your request:");
        specifyPurposeLabel.setFont(labelFont);
        formPanel.add(specifyPurposeLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField specifyPurposeField = new JTextField(20);
        specifyPurposeField.setPreferredSize(new Dimension(0, 25));
        specifyPurposeField.setFont(inputFont);
        specifyPurposeField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        specifyPurposeField.setEnabled(false);
        specifyPurposeField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
        ComponentFactory.addPlaceholder(specifyPurposeField, "Specify if 'Others' is selected");
        formPanel.add(specifyPurposeField, gbc);
        currentRow++;

        ComponentFactory.setupEducationFieldToggle(purposeCombo, specifyPurposeField, "Specify if 'Others' is selected");

        // Required Documents ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = currentRow; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel requiredDocsLabel = new JLabel("Required Documents:");
        requiredDocsLabel.setFont(labelFont);
        formPanel.add(requiredDocsLabel, gbc);

        String[] requiredDocOptions = {
                "Valid government-issued ID (Driver's License, SSS ID, Postal ID, etc.)",
                "Proof of income (payslips, certificate of employment, business permit)",
                "ITR (Income Tax Return) or Certificate of No Income Tax Due",
                "Bank statements or passbook",
                "Barangay residency certificate",
                "Birth certificates of dependents (if applicable)",
                "Marriage certificate (if applicable)"
        };

        JPanel requiredDocsPanel = new JPanel();
        requiredDocsPanel.setLayout(new BoxLayout(requiredDocsPanel, BoxLayout.Y_AXIS));
        requiredDocsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        requiredDocsPanel.setOpaque(false);

        for (String doc : requiredDocOptions) {
            JCheckBox checkbox = new JCheckBox(doc);
            checkbox.setFont(inputFont);
            checkbox.setOpaque(false);
            requiredDocsPanel.add(checkbox);
        }

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(requiredDocsPanel, gbc);

        return formPanel;
    }

    //Certificate for No Income Form
    public static JPanel createNoIncomeCertificateForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // === BASIC INFORMATION SECTION ===

        // Certificate Number ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel certNumberLabel = new JLabel("Certificate Number:");
        certNumberLabel.setFont(labelFont);
        formPanel.add(certNumberLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField certNumberField = new JTextField(20);
        certNumberField.setPreferredSize(new Dimension(0, 25));
        certNumberField.setFont(inputFont);
        certNumberField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(certNumberField, "Auto-generated or manual entry");
        formPanel.add(certNumberField, gbc);

        // Date Issued ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel dateIssuedLabel = new JLabel("Date Issued:");
        dateIssuedLabel.setFont(labelFont);
        formPanel.add(dateIssuedLabel, gbc);

        // Date panel with combo boxes for date issued
        JPanel dateIssuedPanel = new JPanel();
        dateIssuedPanel.setPreferredSize(new Dimension(0, 25));
        dateIssuedPanel.setFont(inputFont);
        dateIssuedPanel.setLayout(new BoxLayout(dateIssuedPanel, BoxLayout.X_AXIS));
        dateIssuedPanel.setOpaque(false);

        JComboBox<String> issuedYearCombo = new JComboBox<>();
        JComboBox<String> issuedMonthCombo = new JComboBox<>();
        JComboBox<String> issuedDayCombo = new JComboBox<>();

        issuedYearCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        issuedMonthCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        issuedDayCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        dateIssuedPanel.add(new JLabel("Month:"));
        dateIssuedPanel.add(Box.createHorizontalStrut(5));
        dateIssuedPanel.add(issuedMonthCombo);
        dateIssuedPanel.add(Box.createHorizontalStrut(10));

        dateIssuedPanel.add(new JLabel("Day:"));
        dateIssuedPanel.add(Box.createHorizontalStrut(5));
        dateIssuedPanel.add(issuedDayCombo);
        dateIssuedPanel.add(Box.createHorizontalStrut(10));

        dateIssuedPanel.add(new JLabel("Year:"));
        dateIssuedPanel.add(Box.createHorizontalStrut(5));
        dateIssuedPanel.add(issuedYearCombo);

        ComponentFactory.setupDateComboBoxes(issuedYearCombo, issuedMonthCombo, issuedDayCombo);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(dateIssuedPanel, gbc);

        // Barangay ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel barangayLabel = new JLabel("Barangay:");
        barangayLabel.setFont(labelFont);
        formPanel.add(barangayLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField barangayField = new JTextField(20);
        barangayField.setPreferredSize(new Dimension(0, 25));
        barangayField.setFont(inputFont);
        barangayField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(barangayField, "Enter barangay name");
        formPanel.add(barangayField, gbc);

        // Municipality/City ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel municipalityLabel = new JLabel("Municipality/City:");
        municipalityLabel.setFont(labelFont);
        formPanel.add(municipalityLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField municipalityField = new JTextField(20);
        municipalityField.setPreferredSize(new Dimension(0, 25));
        municipalityField.setFont(inputFont);
        municipalityField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(municipalityField, "Enter municipality or city");
        formPanel.add(municipalityField, gbc);

        // Province ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel provinceLabel = new JLabel("Province:");
        provinceLabel.setFont(labelFont);
        formPanel.add(provinceLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField provinceField = new JTextField(20);
        provinceField.setPreferredSize(new Dimension(0, 25));
        provinceField.setFont(inputFont);
        provinceField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(provinceField, "Enter province");
        formPanel.add(provinceField, gbc);

        // Full Name ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameLabel.setFont(labelFont);
        formPanel.add(fullNameLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField fullNameField = new JTextField(20);
        fullNameField.setPreferredSize(new Dimension(0, 25));
        fullNameField.setFont(inputFont);
        fullNameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(fullNameField, "Last Name, First Name, Middle Initial");
        formPanel.add(fullNameField, gbc);

        // Date of Birth ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setFont(labelFont);
        formPanel.add(dobLabel, gbc);

        // Date panel with combo boxes for date of birth
        JPanel dobPanel = new JPanel();
        dobPanel.setPreferredSize(new Dimension(0, 25));
        dobPanel.setFont(inputFont);
        dobPanel.setLayout(new BoxLayout(dobPanel, BoxLayout.X_AXIS));
        dobPanel.setOpaque(false);

        JComboBox<String> dobYearCombo = new JComboBox<>();
        JComboBox<String> dobMonthCombo = new JComboBox<>();
        JComboBox<String> dobDayCombo = new JComboBox<>();

        dobYearCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        dobMonthCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        dobDayCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        dobPanel.add(new JLabel("Month:"));
        dobPanel.add(Box.createHorizontalStrut(5));
        dobPanel.add(dobMonthCombo);
        dobPanel.add(Box.createHorizontalStrut(10));

        dobPanel.add(new JLabel("Day:"));
        dobPanel.add(Box.createHorizontalStrut(5));
        dobPanel.add(dobDayCombo);
        dobPanel.add(Box.createHorizontalStrut(10));

        dobPanel.add(new JLabel("Year:"));
        dobPanel.add(Box.createHorizontalStrut(5));
        dobPanel.add(dobYearCombo);

        ComponentFactory.setupDateComboBoxes(dobYearCombo, dobMonthCombo, dobDayCombo);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(dobPanel, gbc);

        // Age (Auto-calculated) ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 7; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(labelFont);
        formPanel.add(ageLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField ageField = new JTextField(20);
        ageField.setPreferredSize(new Dimension(0, 25));
        ageField.setFont(inputFont);
        ageField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ageField.setEditable(false);
        ageField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
        ComponentFactory.addPlaceholder(ageField, "Auto-calculated from Date of Birth");
        formPanel.add(ageField, gbc);

        // Add action listeners to auto-calculate age when DOB changes
        ActionListener ageCalculator = e -> ComponentFactory.calculateAge(dobYearCombo, dobMonthCombo, dobDayCombo, ageField);
        dobYearCombo.addActionListener(ageCalculator);
        dobMonthCombo.addActionListener(ageCalculator);
        dobDayCombo.addActionListener(ageCalculator);

        // Complete Address ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 8; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel addressLabel = new JLabel("Complete Address:");
        addressLabel.setFont(labelFont);
        formPanel.add(addressLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField addressField = new JTextField(20);
        addressField.setPreferredSize(new Dimension(0, 25));
        addressField.setFont(inputFont);
        addressField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(addressField, "House/Building/Blk/Lot No., Street, Barangay, City/Municipality, Province");
        formPanel.add(addressField, gbc);

        // === CIVIL STATUS SECTION ===

        // Civil Status ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 9; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel civilStatusLabel = new JLabel("Civil Status:");
        civilStatusLabel.setFont(labelFont);
        formPanel.add(civilStatusLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;

        String[] civilStatusOptions = {
                "Select your civil status",
                "Single",
                "Married",
                "Widowed",
                "Separated",
                "Divorced"
        };

        JComboBox<String> civilStatusCombo = new JComboBox<>(civilStatusOptions);
        civilStatusCombo.setPreferredSize(new Dimension(0, 25));
        civilStatusCombo.setFont(inputFont);
        civilStatusCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        formPanel.add(civilStatusCombo, gbc);

        // === CONFIRMATION OF BEING UNEMPLOYED SECTION ===

        // Current Employment Status ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 10; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel employmentStatusLabel = new JLabel("Current Employment Status:");
        employmentStatusLabel.setFont(labelFont);
        formPanel.add(employmentStatusLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;

        JPanel employmentStatusPanel = new JPanel();
        employmentStatusPanel.setLayout(new BoxLayout(employmentStatusPanel, BoxLayout.Y_AXIS));
        employmentStatusPanel.setOpaque(false);

        ButtonGroup employmentStatusGroup = new ButtonGroup();

        JRadioButton unemployedRadio = new JRadioButton("I am currently unemployed and have no source of income");
        unemployedRadio.setFont(inputFont);
        unemployedRadio.setOpaque(false);
        employmentStatusGroup.add(unemployedRadio);
        employmentStatusPanel.add(unemployedRadio);

        JRadioButton neverEmployedRadio = new JRadioButton("I have never been employed and have no source of income");
        neverEmployedRadio.setFont(inputFont);
        neverEmployedRadio.setOpaque(false);
        employmentStatusGroup.add(neverEmployedRadio);
        employmentStatusPanel.add(neverEmployedRadio);

        JRadioButton studentRadio = new JRadioButton("I am a student with no source of income");
        studentRadio.setFont(inputFont);
        studentRadio.setOpaque(false);
        employmentStatusGroup.add(studentRadio);
        employmentStatusPanel.add(studentRadio);

        formPanel.add(employmentStatusPanel, gbc);

        // Duration of Unemployment ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 11; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel durationLabel = new JLabel("Duration of Unemployment:");
        durationLabel.setFont(labelFont);
        formPanel.add(durationLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField durationField = new JTextField(20);
        durationField.setPreferredSize(new Dimension(0, 25));
        durationField.setFont(inputFont);
        durationField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(durationField, "e.g., 6 months, 1 year, N/A if never employed");
        formPanel.add(durationField, gbc);

        // Reason for Unemployment ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 12; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel reasonLabel = new JLabel("Reason for Unemployment:");
        reasonLabel.setFont(labelFont);
        formPanel.add(reasonLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField reasonField = new JTextField(20);
        reasonField.setPreferredSize(new Dimension(0, 25));
        reasonField.setFont(inputFont);
        reasonField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(reasonField, "Explain reason for unemployment");
        formPanel.add(reasonField, gbc);

        // === FINANCIAL SUPPORT INFORMATION SECTION ===

        // Financial Support Status ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 13; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel financialSupportLabel = new JLabel("Are you financially supported by someone?:");
        financialSupportLabel.setFont(labelFont);
        formPanel.add(financialSupportLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;

        JPanel financialSupportPanel = new JPanel();
        financialSupportPanel.setLayout(new BoxLayout(financialSupportPanel, BoxLayout.Y_AXIS));
        financialSupportPanel.setOpaque(false);

        ButtonGroup financialSupportGroup = new ButtonGroup();

        JRadioButton supportedRadio = new JRadioButton("Yes, I am financially supported");
        supportedRadio.setFont(inputFont);
        supportedRadio.setOpaque(false);
        financialSupportGroup.add(supportedRadio);
        financialSupportPanel.add(supportedRadio);

        JRadioButton notSupportedRadio = new JRadioButton("No, I have no financial support");
        notSupportedRadio.setFont(inputFont);
        notSupportedRadio.setOpaque(false);
        financialSupportGroup.add(notSupportedRadio);
        financialSupportPanel.add(notSupportedRadio);

        formPanel.add(financialSupportPanel, gbc);

        // === PURPOSE OF REQUEST SECTION ===

        // Purpose of Request ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 14; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel purposeLabel = new JLabel("What is the purpose of this certificate?:");
        purposeLabel.setFont(labelFont);
        formPanel.add(purposeLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;

        String[] purposeOptions = {
                "Job Application",
                "School Requirement",
                "Scholarship Application",
                "Government Program/Benefits",
                "Legal Requirement",
                "Financial Assistance Application",
                "Visa/Travel Application",
                "Others"
        };

        JPanel purposePanel = new JPanel();
        purposePanel.setLayout(new BoxLayout(purposePanel, BoxLayout.Y_AXIS));
        purposePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        purposePanel.setOpaque(false);

        for (String text : purposeOptions) {
            JCheckBox checkbox = new JCheckBox(text);
            checkbox.setFont(inputFont);
            checkbox.setOpaque(false);
            purposePanel.add(checkbox);
        }

        formPanel.add(purposePanel, gbc);

        // Please specify the purpose ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 15; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel specifyPurposeLabel = new JLabel("Please specify the purpose:");
        specifyPurposeLabel.setFont(labelFont);
        formPanel.add(specifyPurposeLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField specifyPurposeField = new JTextField();
        specifyPurposeField.setPreferredSize(new Dimension(0, 25));
        specifyPurposeField.setFont(inputFont);
        specifyPurposeField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(specifyPurposeField, "Specify if 'Others' is selected");
        formPanel.add(specifyPurposeField, gbc);

        // === REQUIRED DOCUMENTS SECTION ===

        // Required Documents ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 16; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel requirementsLabel = new JLabel("Required Documents:");
        requirementsLabel.setFont(labelFont);
        formPanel.add(requirementsLabel, gbc);

        String[] requirementOptions = {
                "Valid government-issued ID (Driver's License, SSS ID, Postal ID, etc.)",
                "Birth Certificate (PSA-issued)",
                "Barangay Residency Certificate",
                "Marriage Certificate (if married)",
                "School enrollment certificate (if student)",
                "Affidavit of Support (if financially supported by someone)"
        };

        JPanel requirementsPanel = new JPanel();
        requirementsPanel.setLayout(new BoxLayout(requirementsPanel, BoxLayout.Y_AXIS));
        requirementsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        requirementsPanel.setOpaque(false);

        for (String text : requirementOptions) {
            JCheckBox checkbox = new JCheckBox(text);
            checkbox.setFont(inputFont);
            checkbox.setOpaque(false);
            requirementsPanel.add(checkbox);
        }

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(requirementsPanel, gbc);

        return formPanel;
    }

    //Certificate for State Tax Certificate Form
    public static JPanel createStateTaxCertificateForm(){
        Font labelFont = new Font("Trebuchet MS", Font.BOLD, 12);
        Font inputFont = new Font("Trebuchet MS", Font.PLAIN, 12);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Certificate Number ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel certNumberLabel = new JLabel("Certificate Number:");
        certNumberLabel.setFont(labelFont);
        formPanel.add(certNumberLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField certNumberField = new JTextField(20);
        certNumberField.setPreferredSize(new Dimension(0, 25));
        certNumberField.setFont(inputFont);
        certNumberField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        certNumberField.setEditable(false); // Auto-generated
        certNumberField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
        ComponentFactory.addPlaceholder(certNumberField, "Auto-generated upon processing");
        formPanel.add(certNumberField, gbc);

        // Date Issued ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel dateIssuedLabel = new JLabel("Date Issued:");
        dateIssuedLabel.setFont(labelFont);
        formPanel.add(dateIssuedLabel, gbc);

        // Date panel with combo boxes for date issued
        JPanel dateIssuedPanel = new JPanel();
        dateIssuedPanel.setPreferredSize(new Dimension(0, 25));
        dateIssuedPanel.setFont(inputFont);
        dateIssuedPanel.setLayout(new BoxLayout(dateIssuedPanel, BoxLayout.X_AXIS));
        dateIssuedPanel.setOpaque(false);

        JComboBox<String> issuedYearCombo = new JComboBox<>();
        JComboBox<String> issuedMonthCombo = new JComboBox<>();
        JComboBox<String> issuedDayCombo = new JComboBox<>();

        issuedYearCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        issuedMonthCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        issuedDayCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        dateIssuedPanel.add(new JLabel("Month:"));
        dateIssuedPanel.add(Box.createHorizontalStrut(5));
        dateIssuedPanel.add(issuedMonthCombo);
        dateIssuedPanel.add(Box.createHorizontalStrut(10));

        dateIssuedPanel.add(new JLabel("Day:"));
        dateIssuedPanel.add(Box.createHorizontalStrut(5));
        dateIssuedPanel.add(issuedDayCombo);
        dateIssuedPanel.add(Box.createHorizontalStrut(10));

        dateIssuedPanel.add(new JLabel("Year:"));
        dateIssuedPanel.add(Box.createHorizontalStrut(5));
        dateIssuedPanel.add(issuedYearCombo);

        // Initialize date issued combo boxes
        ComponentFactory.setupDateComboBoxes(issuedYearCombo, issuedMonthCombo, issuedDayCombo);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(dateIssuedPanel, gbc);

        // Barangay ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel barangayLabel = new JLabel("Barangay:");
        barangayLabel.setFont(labelFont);
        formPanel.add(barangayLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField barangayField = new JTextField(20);
        barangayField.setPreferredSize(new Dimension(0, 25));
        barangayField.setFont(inputFont);
        barangayField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(barangayField, "Enter your barangay");
        formPanel.add(barangayField, gbc);

        // Municipality/City ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel municipalityLabel = new JLabel("Municipality/City:");
        municipalityLabel.setFont(labelFont);
        formPanel.add(municipalityLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField municipalityField = new JTextField(20);
        municipalityField.setPreferredSize(new Dimension(0, 25));
        municipalityField.setFont(inputFont);
        municipalityField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(municipalityField, "Enter your municipality or city");
        formPanel.add(municipalityField, gbc);

        // Province ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel provinceLabel = new JLabel("Province:");
        provinceLabel.setFont(labelFont);
        formPanel.add(provinceLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField provinceField = new JTextField(20);
        provinceField.setPreferredSize(new Dimension(0, 25));
        provinceField.setFont(inputFont);
        provinceField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(provinceField, "Enter your province");
        formPanel.add(provinceField, gbc);

        // Full Name ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameLabel.setFont(labelFont);
        formPanel.add(fullNameLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField fullNameField = new JTextField(20);
        fullNameField.setPreferredSize(new Dimension(0, 25));
        fullNameField.setFont(inputFont);
        fullNameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(fullNameField, "Last Name, First Name, Middle Initial");
        formPanel.add(fullNameField, gbc);

        // Complete Address ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel addressLabel = new JLabel("Complete Address:");
        addressLabel.setFont(labelFont);
        formPanel.add(addressLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField addressField = new JTextField(20);
        addressField.setPreferredSize(new Dimension(0, 25));
        addressField.setFont(inputFont);
        addressField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(addressField, "House/Building/Blk/Lot No., Street, Barangay, City/Municipality, Province");
        formPanel.add(addressField, gbc);

        // Tax Computation Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 7; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; gbc.gridwidth = 2;
        JLabel taxComputationHeaderLabel = new JLabel("TAX COMPUTATION");
        taxComputationHeaderLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        formPanel.add(taxComputationHeaderLabel, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Basic Community Tax ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 8; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel basicTaxLabel = new JLabel("Basic Community Tax:");
        basicTaxLabel.setFont(labelFont);
        formPanel.add(basicTaxLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField basicTaxField = new JTextField(20);
        basicTaxField.setPreferredSize(new Dimension(0, 25));
        basicTaxField.setFont(inputFont);
        basicTaxField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        basicTaxField.setEditable(false);
        basicTaxField.setText("₱5.00");
        basicTaxField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
        ComponentFactory.addPlaceholder(basicTaxField, "Fixed amount for all citizens of ₱5");
        formPanel.add(basicTaxField, gbc);

        // Gross Receipts/Earnings ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 9; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel grossReceiptsLabel = new JLabel("Gross Receipts/Earnings (Annual):");
        grossReceiptsLabel.setFont(labelFont);
        formPanel.add(grossReceiptsLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField grossReceiptsField = new JTextField(20);
        grossReceiptsField.setPreferredSize(new Dimension(0, 25));
        grossReceiptsField.setFont(inputFont);
        grossReceiptsField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(grossReceiptsField, "Enter your total annual income from all sources");
        formPanel.add(grossReceiptsField, gbc);

        // Real Property Owned ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 10; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel realPropertyLabel = new JLabel("Real Property Owned (Assessed Value):");
        realPropertyLabel.setFont(labelFont);
        formPanel.add(realPropertyLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField realPropertyField = new JTextField(20);
        realPropertyField.setPreferredSize(new Dimension(0, 25));
        realPropertyField.setFont(inputFont);
        realPropertyField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ComponentFactory.addPlaceholder(realPropertyField, "Enter the total assessed value of all real properties you own");
        formPanel.add(realPropertyField, gbc);

        // Tax Computation Breakdown Section Header ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 11; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; gbc.gridwidth = 2;
        JLabel breakdownHeaderLabel = new JLabel("TAX COMPUTATION BREAKDOWN");
        breakdownHeaderLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        formPanel.add(breakdownHeaderLabel, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Basic Community Tax Breakdown ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 12; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel basicTaxBreakdownLabel = new JLabel("Basic Community Tax:");
        basicTaxBreakdownLabel.setFont(labelFont);
        formPanel.add(basicTaxBreakdownLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField basicTaxBreakdownField = new JTextField(20);
        basicTaxBreakdownField.setPreferredSize(new Dimension(0, 25));
        basicTaxBreakdownField.setFont(inputFont);
        basicTaxBreakdownField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        basicTaxBreakdownField.setEditable(false);
        basicTaxBreakdownField.setText("₱5.00");
        basicTaxBreakdownField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
        formPanel.add(basicTaxBreakdownField, gbc);

        // Additional Tax on Income ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 13; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel additionalIncomeLabel = new JLabel("Additional Tax on Income (₱1 per ₱1,000):");
        additionalIncomeLabel.setFont(labelFont);
        formPanel.add(additionalIncomeLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField additionalIncomeField = new JTextField(20);
        additionalIncomeField.setPreferredSize(new Dimension(0, 25));
        additionalIncomeField.setFont(inputFont);
        additionalIncomeField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        additionalIncomeField.setEditable(false);
        additionalIncomeField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
        ComponentFactory.addPlaceholder(additionalIncomeField, "Auto-calculated from gross receipts");
        formPanel.add(additionalIncomeField, gbc);

        // Additional Tax on Property ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 14; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel additionalPropertyLabel = new JLabel("Additional Tax on Property (₱1 per ₱1,000):");
        additionalPropertyLabel.setFont(labelFont);
        formPanel.add(additionalPropertyLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField additionalPropertyField = new JTextField(20);
        additionalPropertyField.setPreferredSize(new Dimension(0, 25));
        additionalPropertyField.setFont(inputFont);
        additionalPropertyField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        additionalPropertyField.setEditable(false);
        additionalPropertyField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
        ComponentFactory.addPlaceholder(additionalPropertyField, "Auto-calculated from property value");
        formPanel.add(additionalPropertyField, gbc);

        // Total Amount to Pay ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 15; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel totalAmountLabel = new JLabel("Total Amount to Pay:");
        totalAmountLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        formPanel.add(totalAmountLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField totalAmountField = new JTextField(20);
        totalAmountField.setPreferredSize(new Dimension(0, 25));
        totalAmountField.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        totalAmountField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        totalAmountField.setEditable(false);
        totalAmountField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
        ComponentFactory.addPlaceholder(totalAmountField, "Total computed tax amount");
        formPanel.add(totalAmountField, gbc);

        // Add calculation listeners for automatic computation
        DocumentListener calculationListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { calculateTax(); }
            public void removeUpdate(DocumentEvent e) { calculateTax(); }
            public void changedUpdate(DocumentEvent e) { calculateTax(); }

            private void calculateTax() {
                try {
                    // Parse gross receipts
                    String grossText = grossReceiptsField.getText().replaceAll("[₱,]", "");
                    double grossReceipts = grossText.isEmpty() ? 0 : Double.parseDouble(grossText);

                    // Parse property value
                    String propertyText = realPropertyField.getText().replaceAll("[₱,]", "");
                    double propertyValue = propertyText.isEmpty() ? 0 : Double.parseDouble(propertyText);

                    // Calculate additional taxes
                    double additionalIncome = Math.floor(grossReceipts / 1000);
                    double additionalProperty = Math.floor(propertyValue / 1000);
                    double total = 5.00 + additionalIncome + additionalProperty;

                    // Update fields
                    additionalIncomeField.setText(String.format("₱%.2f", additionalIncome));
                    additionalPropertyField.setText(String.format("₱%.2f", additionalProperty));
                    totalAmountField.setText(String.format("₱%.2f", total));

                } catch (NumberFormatException ex) {
                    // Handle invalid input gracefully
                    additionalIncomeField.setText("₱0.00");
                    additionalPropertyField.setText("₱0.00");
                    totalAmountField.setText("₱5.00");
                }
            }
        };

        grossReceiptsField.getDocument().addDocumentListener(calculationListener);
        realPropertyField.getDocument().addDocumentListener(calculationListener);

        // Required Documents (Checkbox) ------------------------------------------------------------------------------------
        gbc.gridx = 0; gbc.gridy = 16; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel requirementsLabel = new JLabel("Required Documents:");
        requirementsLabel.setFont(labelFont);
        formPanel.add(requirementsLabel, gbc);

        String[] requirementOptions = {
                "Valid government-issued ID (Driver's License, SSS ID, Postal ID, etc.)",
                "Proof of income (Certificate of Employment, ITR, Business Permit, etc.)",
                "Tax Declaration of Real Property (if applicable)",
                "Previous year's Community Tax Certificate (if available)",
                "Barangay Certificate of Residency"
        };

        JPanel requirementsPanel = new JPanel();
        requirementsPanel.setLayout(new BoxLayout(requirementsPanel, BoxLayout.Y_AXIS));
        requirementsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        requirementsPanel.setOpaque(false);

        for (String text : requirementOptions) {
            JCheckBox checkbox = new JCheckBox(text);
            checkbox.setFont(inputFont);
            checkbox.setOpaque(false);
            requirementsPanel.add(checkbox);
        }

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(requirementsPanel, gbc);

        return formPanel;
    }


    public static JPanel createCommonFields() {
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

    public static JPanel createBusinessClearanceForm() {
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




}
