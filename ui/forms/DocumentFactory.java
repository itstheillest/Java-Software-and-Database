package ui.forms;

import utils.ComponentFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class DocumentFactory {

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
