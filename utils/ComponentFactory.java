package utils;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import main.ApplicationConstants;

public class ComponentFactory {

    public static JLabel createDateTimeLabel() {
        JLabel dateTimeLabel = new JLabel();
        dateTimeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        dateTimeLabel.setForeground(new Color(100, 100, 100));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy - HH:mm:ss");

        // Initial time set
        dateTimeLabel.setText(LocalDateTime.now().format(formatter));

        // Update date/time every second
        Timer clockTimer = new Timer(1000, e -> {
            dateTimeLabel.setText(LocalDateTime.now().format(formatter));
        });
        clockTimer.start();

        return dateTimeLabel;
    }

    // Creates a horizontal separator with semi-transparent styling
    public static JSeparator createHorizontalSeparator() {
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(new Color(0, 0, 0, 100));
        return separator;
    }

    public static JPanel getHeaderPanel(String title) {
        JPanel headerSection = new JPanel(new BorderLayout());
        headerSection.setOpaque(false);
        headerSection.setBorder(BorderFactory.createEmptyBorder(25, 30, 10, 30));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(75, 83, 32));

        JLabel dateTimeLabel = createDateTimeLabel();

        headerSection.add(titleLabel, BorderLayout.WEST);
        headerSection.add(dateTimeLabel, BorderLayout.EAST);

        return headerSection;
    }

    public static void setupDateComboBoxes(JComboBox<String> yearCombo, JComboBox<String> monthCombo, JComboBox<String> dayCombo) {
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();
        int currentDay = today.getDayOfMonth();

        // Populate years (last 100 years)
        String[] years = new String[100];
        for (int i = 0; i < 100; i++) {
            years[i] = String.valueOf(currentYear - i);
        }
        yearCombo.setModel(new DefaultComboBoxModel<>(years));

        // Define update methods
        Runnable updateDays = () -> {
            if (yearCombo.getSelectedItem() == null || monthCombo.getSelectedItem() == null) return;

            dayCombo.removeAllItems();
            int selectedYear = Integer.parseInt((String) yearCombo.getSelectedItem());
            int selectedMonth = Integer.parseInt((String) monthCombo.getSelectedItem());

            YearMonth yearMonth = YearMonth.of(selectedYear, selectedMonth);
            int daysInMonth = yearMonth.lengthOfMonth();
            int maxDay = (selectedYear == currentYear && selectedMonth == currentMonth)
                    ? Math.min(currentDay, daysInMonth)
                    : daysInMonth;

            for (int i = 1; i <= maxDay; i++) {
                dayCombo.addItem(String.format("%02d", i));
            }
        };

        Runnable updateMonths = () -> {
            if (yearCombo.getSelectedItem() == null) return;

            monthCombo.removeAllItems();
            int selectedYear = Integer.parseInt((String) yearCombo.getSelectedItem());
            int maxMonth = (selectedYear == currentYear) ? currentMonth : 12;

            for (int i = 1; i <= maxMonth; i++) {
                monthCombo.addItem(String.format("%02d", i));
            }

            updateDays.run();
        };

        // Set listeners
        yearCombo.addActionListener(e -> updateMonths.run());
        monthCombo.addActionListener(e -> updateDays.run());

        // Trigger initial setup
        updateMonths.run();
    }

    public static void addPlaceholder(JTextComponent component, String placeholder) {
        component.setText(placeholder);
        component.setForeground(Color.GRAY);

        component.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (component.getText().equals(placeholder)) {
                    component.setText("");
                    component.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (component.getText().isEmpty()) {
                    component.setText(placeholder);
                    component.setForeground(Color.GRAY);
                }
            }
        });
    }

    // Helper method to calculate age from date of birth
    public static void calculateAge(JComboBox<String> yearCombo, JComboBox<String> monthCombo,
                                     JComboBox<String> dayCombo, JTextField ageField) {
        try {
            String yearStr = (String) yearCombo.getSelectedItem();
            String monthStr = (String) monthCombo.getSelectedItem();
            String dayStr = (String) dayCombo.getSelectedItem();

            if (yearStr != null && !yearStr.equals("Year") && !yearStr.isEmpty() &&
                    monthStr != null && !monthStr.equals("Month") && !monthStr.isEmpty() &&
                    dayStr != null && !dayStr.equals("Day") && !dayStr.isEmpty()) {

                int birthYear = Integer.parseInt(yearStr);
                int birthMonth = Integer.parseInt(monthStr);
                int birthDay = Integer.parseInt(dayStr);

                // Get current date
                Calendar today = Calendar.getInstance();
                int currentYear = today.get(Calendar.YEAR);
                int currentMonth = today.get(Calendar.MONTH) + 1; // Calendar months are 0-based
                int currentDay = today.get(Calendar.DAY_OF_MONTH);

                // Calculate age
                int age = currentYear - birthYear;

                // Adjust age if birthday hasn't occurred this year
                if (currentMonth < birthMonth ||
                        (currentMonth == birthMonth && currentDay < birthDay)) {
                    age--;
                }

                // Ensure age is not negative
                if (age >= 0) {
                    ageField.setText(String.valueOf(age));
                } else {
                    ageField.setText("");
                }
            } else {
                ageField.setText("");
            }
        } catch (NumberFormatException e) {
            ageField.setText("");
        }
    }

    public static void setupEducationFieldToggle(JComboBox<String> comboBox, JTextField specifyField, String placeholderText) {
        comboBox.addActionListener(e -> {
            boolean isOthers = "Others".equals(comboBox.getSelectedItem());
            specifyField.setEnabled(isOthers);

            if (isOthers) {
                // Field is enabled - set normal background
                specifyField.setBackground(UIManager.getColor("TextField.background"));
            } else {
                // Field is disabled - set inactive background and clear text
                specifyField.setText("");
                specifyField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
                ComponentFactory.addPlaceholder(specifyField, placeholderText);
            }
        });
    }

    // Helper method for radio button toggle
    public static void setupRadioToggleBehavior(JRadioButton yesButton, JRadioButton noButton, JTextComponent targetField, String placeholderText) {
        yesButton.addActionListener(e -> {
            targetField.setEnabled(true);
            targetField.setBackground(UIManager.getColor("TextField.background"));
        });

        noButton.addActionListener(e -> {
            targetField.setText("");
            targetField.setEnabled(false);
            targetField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
            ComponentFactory.addPlaceholder(targetField, placeholderText);
        });
    }



}