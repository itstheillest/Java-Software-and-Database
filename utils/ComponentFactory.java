package utils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

    public static void addAgeCalculationListener(JComboBox<String> dobYearCombo, JComboBox<String> dobMonthCombo,
                                           JComboBox<String> dobDayCombo, JTextField ageField) {
        ActionListener ageCalculationListener = e -> {
            try {
                String selectedYear = (String) dobYearCombo.getSelectedItem();
                String selectedMonth = (String) dobMonthCombo.getSelectedItem();
                String selectedDay = (String) dobDayCombo.getSelectedItem();

                if (selectedYear != null && selectedMonth != null && selectedDay != null &&
                        !selectedYear.equals("Year") && !selectedMonth.equals("Month") && !selectedDay.equals("Day")) {

                    int year = Integer.parseInt(selectedYear);
                    int month = Month.valueOf(selectedMonth.toUpperCase()).getValue();
                    int day = Integer.parseInt(selectedDay);

                    Calendar today = Calendar.getInstance();
                    Calendar birthDate = Calendar.getInstance();
                    birthDate.set(year, month - 1, day); // month is 0-based

                    int age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
                    if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
                        age--;
                    }

                    ageField.setText(String.valueOf(age));
                }
            } catch (NumberFormatException ex) {
                ageField.setText("");
            }
        };

        dobYearCombo.addActionListener(ageCalculationListener);
        dobMonthCombo.addActionListener(ageCalculationListener);
        dobDayCombo.addActionListener(ageCalculationListener);
    }

    public static void addCommunityTaxCalculationListener(JTextField businessIncomeField, JTextField professionalIncomeField,
                                                    JTextField propertyIncomeField, JTextField totalAmountField) {
        DocumentListener calculationListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { calculateCommunityTax(); }
            public void removeUpdate(DocumentEvent e) { calculateCommunityTax(); }
            public void changedUpdate(DocumentEvent e) { calculateCommunityTax(); }

            private void calculateCommunityTax() {
                try {
                    String businessText = businessIncomeField.getText().replaceAll("[₱,]", "");
                    String professionalText = professionalIncomeField.getText().replaceAll("[₱,]", "");
                    String propertyText = propertyIncomeField.getText().replaceAll("[₱,]", "");

                    double businessIncome = businessText.isEmpty() ? 0 : Double.parseDouble(businessText);
                    double professionalIncome = professionalText.isEmpty() ? 0 : Double.parseDouble(professionalText);
                    double propertyIncome = propertyText.isEmpty() ? 0 : Double.parseDouble(propertyText);

                    double totalIncome = businessIncome + professionalIncome + propertyIncome;
                    double additionalTax = Math.floor(totalIncome / 1000);
                    double totalTax = 5.00 + additionalTax;

                    totalAmountField.setText(String.format("₱%.2f", totalTax));
                } catch (NumberFormatException ex) {
                    totalAmountField.setText("₱5.00");
                }
            }
        };

        businessIncomeField.getDocument().addDocumentListener(calculationListener);
        professionalIncomeField.getDocument().addDocumentListener(calculationListener);
        propertyIncomeField.getDocument().addDocumentListener(calculationListener);
    }

    // Helper method to create a date picker panel
    public static JPanel createDatePickerPanel(Font inputFont) {
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

        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();
        int currentDay = today.getDayOfMonth();

        // Month names array
        String[] monthNames = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };

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

            // Convert month name back to number
            String selectedMonthName = (String) monthCombo.getSelectedItem();
            int selectedMonth = -1;
            for (int i = 0; i < monthNames.length; i++) {
                if (monthNames[i].equals(selectedMonthName)) {
                    selectedMonth = i + 1;
                    break;
                }
            }

            if (selectedMonth == -1) return; // Invalid month name

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
                monthCombo.addItem(monthNames[i - 1]);
            }

            updateDays.run();
        };

        // Set listeners
        yearCombo.addActionListener(e -> updateMonths.run());
        monthCombo.addActionListener(e -> updateDays.run());

        // Trigger initial setup
        updateMonths.run();

        return datePanel;
    }

    public static JComboBox<String> findComboBoxInPanel(JPanel panel, int occurrence) {
        int count = 0;
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JComboBox<?>) {
                if (count == occurrence) return (JComboBox<String>) comp;
                count++;
            }
        }
        return null;
    }

    public static LocalDate extractDateFromPanel(JPanel datePanel) {
        JComboBox<String> monthCombo = findComboBoxInPanel(datePanel, 0);
        JComboBox<String> dayCombo = findComboBoxInPanel(datePanel, 1);
        JComboBox<String> yearCombo = findComboBoxInPanel(datePanel, 2);

        if (monthCombo == null || dayCombo == null || yearCombo == null) return null;

        String monthName = (String) monthCombo.getSelectedItem();
        String[] monthNames = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };

        int month = -1;
        for (int i = 0; i < monthNames.length; i++) {
            if (monthNames[i].equals(monthName)) {
                month = i + 1;
                break;
            }
        }

        int day = Integer.parseInt((String) dayCombo.getSelectedItem());
        int year = Integer.parseInt((String) yearCombo.getSelectedItem());

        return LocalDate.of(year, month, day);
    }

    public static List<String> getSelectedCheckboxes(JPanel containerPanel) {
        List<String> selected = new ArrayList<>();
        for (Component comp : containerPanel.getComponents()) {
            if (comp instanceof JPanel) {
                for (Component innerComp : ((JPanel) comp).getComponents()) {
                    if (innerComp instanceof JCheckBox checkbox && checkbox.isSelected()) {
                        selected.add(checkbox.getText().replaceAll("(?i)<.*?>", "")); // remove HTML tags
                    }
                }
            }
        }
        return selected;
    }

//=============================================== Tanod Schedule and Report =============================

    
    // ===============  REUSABLE FONT ========================
    public static Font getDefaultFont(int size, boolean bold) 
    {
        return new Font("Arial", bold ? Font.BOLD : Font.PLAIN, size);
    }


    //
    public static class Team 
    {
        private String teamName;
        private ArrayList<Tanod> members;

        public Team(String teamName) {
            this.teamName = teamName;
            this.members = new ArrayList<>();
        }

        public String getTeamName() 
        { return teamName; }

        public ArrayList<Tanod> getMembers()
         { return members; }

        public void addMember(Tanod tanod) 
        { members.add(tanod); }

        public void removeMember(int index) 
        { members.remove(index); }

        @Override
        public String toString() { return teamName; }
    }

    
    public static class Tanod 
    {
        private String name;
        private String position;

        public Tanod(String name, String position) {
            this.name = name;
            this.position = position;
        }

        public String getName() 
        { return name; }


        public String getPosition() 
        { return position; }

        public void setName(String name) 
        { this.name = name; }

        public void setPosition(String position) 
        { this.position = position; }

        @Override
        public String toString() {
            return name + " (" + position + ")";
        }

        
    }

    
    private static final ArrayList<Team> tanodTeams = new ArrayList<>();

    public static JPanel createManageContentPanel() 
    {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Table and model
        String[] columns = {"Team", "Name", "Position"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(28);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        
        JScrollPane scrollPane = new JScrollPane(table);
        

        // Team selector
        JComboBox<String> teamSelector = new JComboBox<>();
        teamSelector.setFont(new Font("Arial", Font.PLAIN, 16));
        updateTeamDropdown(teamSelector);

        teamSelector.addActionListener(e -> {
            Team selectedTeam = getSelectedTeam(teamSelector);
            refreshTable(model, selectedTeam);
        });

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        RoundedButton addTeamBtn = new RoundedButton("Add Team", 30, new Color(0,123,255), Color.WHITE);
        RoundedButton addMemberBtn = new RoundedButton("Add Member", 30, new Color(0,123,255), Color.WHITE);
        RoundedButton updateMemberBtn = new RoundedButton("Update Member", 30, new Color(0,123,255), Color.WHITE);
        RoundedButton deleteMemberBtn = new RoundedButton("Delete Member", 30, new Color(0,123,255), Color.WHITE);
        
        buttonPanel.add(addTeamBtn);
        buttonPanel.add(addMemberBtn);
        buttonPanel.add(updateMemberBtn);
        buttonPanel.add(deleteMemberBtn);

        // Add Team
        addTeamBtn.addActionListener(e -> {
            String teamName = JOptionPane.showInputDialog("Enter Team Name:");
            if (teamName != null && !teamName.trim().isEmpty()) {
                tanodTeams.add(new Team(teamName.trim()));
                updateTeamDropdown(teamSelector);
            }
        });

        // Add Member
        addMemberBtn.addActionListener(e -> {
            Team selectedTeam = getSelectedTeam(teamSelector);
            if (selectedTeam != null) {
                String name = JOptionPane.showInputDialog("Enter Name:");
                String position = JOptionPane.showInputDialog("Enter Position:");
                if (name != null && position != null) {
                    selectedTeam.addMember(new Tanod(name, position));
                    refreshTable(model, selectedTeam);
                }
            } else {
                JOptionPane.showMessageDialog(contentPanel, "Please select a team first.");
            }
        });

        // Update Member
        updateMemberBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            Team selectedTeam = getSelectedTeam(teamSelector);
            if (selectedTeam != null && selectedRow >= 0 && selectedRow < selectedTeam.getMembers().size()) {
                Tanod member = selectedTeam.getMembers().get(selectedRow);
                String name = JOptionPane.showInputDialog("Edit Name:", member.getName());
                String position = JOptionPane.showInputDialog("Edit Position:", member.getPosition());
                if (name != null && position != null) {
                    member.setName(name);
                    member.setPosition(position);
                    refreshTable(model, selectedTeam);
                }
            }
        });

        // Delete Member
        deleteMemberBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            Team selectedTeam = getSelectedTeam(teamSelector);
            if (selectedTeam != null && selectedRow >= 0 && selectedRow < selectedTeam.getMembers().size()) {
                selectedTeam.removeMember(selectedRow);
                refreshTable(model, selectedTeam);
            }
        });

        // Layout
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);

        JLabel teamLabel = new JLabel("Select Team:");
        teamLabel.setFont(getDefaultFont(16,false)); 

        topPanel.add(teamLabel);
        topPanel.add(teamSelector);

        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        return contentPanel;
    }

    // === Helper Methods ===
    private static void refreshTable(DefaultTableModel model, Team team) {
        model.setRowCount(0);
        if (team != null) {
            for (Tanod t : team.getMembers()) {
                model.addRow(new Object[]{team.getTeamName(), t.getName(), t.getPosition()});
            }
        }
    }

    private static Team getSelectedTeam(JComboBox<String> teamSelector) {
        String selected = (String) teamSelector.getSelectedItem();
        for (Team team : tanodTeams) {
            if (team.getTeamName().equals(selected)) {
                return team;
            }
        }
        return null;
    }

    private static void updateTeamDropdown(JComboBox<String> dropdown) {
        dropdown.removeAllItems();
        for (Team team : tanodTeams) {
            dropdown.addItem(team.getTeamName());
        }
    }

    // Optional getter if needed externally
    public static ArrayList<Team> getTanodTeams() {
        return tanodTeams;
    }


    
    //the whole calendar panel
    public static JPanel createCalendarPanel(LocalDate date, ArrayList<Team> tanodTeams) 
    {
        JPanel fullCalendarPanel = new JPanel(new BorderLayout());
        fullCalendarPanel.setOpaque(false);
        fullCalendarPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Day header (Sun to Sat)
        JPanel dayHeaderPanel = new JPanel(new GridLayout(1, 7, 10, 10));
        dayHeaderPanel.setOpaque(false);
        String[] dayNames = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String dayName : dayNames) 
        {
            JLabel dayLabel = new JLabel(dayName, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Arial", Font.BOLD, 18));
            dayHeaderPanel.add(dayLabel);
        }

        // Calendar grid
        JPanel calendarPanel = new JPanel(new GridLayout(6, 7, 10, 10));
        calendarPanel.setOpaque(false);

        YearMonth yearMonth = YearMonth.of(date.getYear(), date.getMonth());
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstDay = LocalDate.of(date.getYear(), date.getMonth(), 1);
        int startDay = firstDay.getDayOfWeek().getValue(); // Monday = 1 ... Sunday = 7
        int calendarStart = startDay % 7;

        // Create the hover popup window
        JPopupMenu hoverPopup = new JPopupMenu();
        JPanel popupContent = new JPanel(new GridLayout(0, 1));
        popupContent.setBorder(new EmptyBorder(5, 10, 5, 10));
        popupContent.setBackground(Color.WHITE);
        hoverPopup.add(popupContent);


        for (int i = 0; i < 42; i++) {
            JPanel dayCell = new JPanel(new BorderLayout());
            dayCell.setPreferredSize(new Dimension(100, 100));
            dayCell.setBackground(Color.WHITE);
            dayCell.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            if (i >= calendarStart && i < calendarStart + daysInMonth) {
                int dayNum = i - calendarStart + 1;

                JLabel dayLabel = new JLabel(String.valueOf(dayNum), SwingConstants.LEFT);
                dayLabel.setFont(new Font("Arial", Font.BOLD, 16));

                // Get the team for this day (rotate through teams)
                Team team = tanodTeams.get(dayNum % tanodTeams.size());
                

                JLabel teamLabel = new JLabel(team.getTeamName(), SwingConstants.CENTER);
                teamLabel.setFont(new Font("Arial", Font.BOLD, 16));
                teamLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor effect

                 // Create the hover popup
            

                teamLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    popupContent.removeAll();

                    if (team.getMembers().isEmpty()) {
                        JLabel noMembers = new JLabel("No members");
                        noMembers.setFont(new Font("Arial", Font.ITALIC, 14));
                        popupContent.add(noMembers);
                    } else {
                        for (Tanod t : team.getMembers()) {
                            JLabel memberLabel = new JLabel(t.getName() + " - " + t.getPosition());
                            memberLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                            popupContent.add(memberLabel);
                        }
                    }

                    popupContent.revalidate();
                    popupContent.repaint();
                    hoverPopup.pack();
                    hoverPopup.show(teamLabel, 10, teamLabel.getHeight());
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hoverPopup.setVisible(false);
                }
            });

                dayCell.add(dayLabel, BorderLayout.NORTH);
                dayCell.add(teamLabel, BorderLayout.CENTER);

                // Highlight current day
                if (date.getDayOfMonth() == dayNum &&
                    date.getMonthValue() == LocalDate.now().getMonthValue() &&
                    date.getYear() == LocalDate.now().getYear()) {
                    dayCell.setBackground(new Color(173, 216, 230)); // Light blue
                }

            } else {
                dayCell.setBackground(new Color(240, 240, 240)); // Empty cells
            }

            calendarPanel.add(dayCell);
        }

        fullCalendarPanel.add(dayHeaderPanel, BorderLayout.NORTH);
        fullCalendarPanel.add(calendarPanel, BorderLayout.CENTER);
        return fullCalendarPanel;
    }

    
    //for report button
    public static JPanel createIncidentReportPanel() 
    {
        JPanel reportingPanel = new JPanel();
        reportingPanel.setLayout(new BoxLayout(reportingPanel, BoxLayout.Y_AXIS));
        reportingPanel.setOpaque(false);
        reportingPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        
        // Date
        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        JComboBox<String> monthBox = new JComboBox<>(months);
        JComboBox<String> dayBox = new JComboBox<>(IntStream.rangeClosed(1, 31).mapToObj(i -> String.format("%02d", i)).toArray(String[]::new));
        JComboBox<String> yearBox = new JComboBox<>(IntStream.rangeClosed(2000, 2030).mapToObj(String::valueOf).toArray(String[]::new));

        monthBox.setFont(getDefaultFont(16, false));
        dayBox.setFont(getDefaultFont(16, false));
        yearBox.setFont(getDefaultFont(16, false));

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.setOpaque(false);
        datePanel.add(monthBox);
        datePanel.add(new JLabel("/"));
        datePanel.add(dayBox);
        datePanel.add(new JLabel("/"));
        datePanel.add(yearBox);

        JTextField timeField = new JTextField(15);
        JTextField locationField = new JTextField(30);
        JTextArea descriptionArea = new JTextArea(5, 30);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JScrollPane descriptionScroll = new JScrollPane(descriptionArea);

        // File upload
        JButton uploadButton = new JButton("Choose File");
        JLabel fileNameLabel = new JLabel("No file chosen");
        File[] attachedFile = new File[1];

        uploadButton.setFont(getDefaultFont(16, false));
        fileNameLabel.setFont(new Font("Arial", Font.ITALIC, 14));

        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                attachedFile[0] = fileChooser.getSelectedFile();
                fileNameLabel.setText(attachedFile[0].getName());
            }
        });

        // Incident Types
        String[] incidentTypes = {"Theft", "Vandalism", "Trespassing", "Personal Injury", "Assault", "Medical Emergency", "Traffic Accident", "Other"};
        ButtonGroup typeGroup = new ButtonGroup();
        JPanel typePanel = new JPanel(new GridLayout(0, 2, 10, 10));
        typePanel.setOpaque(false);

        JTextField otherTypeField = new JTextField(10);
        otherTypeField.setEnabled(false);

        for (String type : incidentTypes) {
            if (!type.equals("Other")) 
            {
                JRadioButton btn = new JRadioButton(type);
                btn.setFont(getDefaultFont(16, false));
                btn.setOpaque(false);
                typeGroup.add(btn);
                typePanel.add(btn);
            } 
            else
            {
                JPanel otherPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
                otherPanel.setOpaque(false);
                JRadioButton otherBtn = new JRadioButton("Other");
                otherBtn.setFont(getDefaultFont(16, false));
                otherBtn.setOpaque(false);
                typeGroup.add(otherBtn);
                otherPanel.add(otherBtn);
                otherPanel.add(otherTypeField);
                typePanel.add(otherPanel);

                otherBtn.addActionListener(e -> otherTypeField.setEnabled(true));
            }
        }

        // Involved person
        JTextField personNameField = new JTextField(20);
        JTextField contactField = new JTextField(20);

        // Submit button
        JButton submitButton = new JButton("Submit Report");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.addActionListener(e -> {
            String selectedType = null;
            for (Component c : typePanel.getComponents()) 
            {
                if (c instanceof JRadioButton btn && btn.isSelected()) 
                {
                    selectedType = btn.getText().equals("Other") ? otherTypeField.getText().trim() : btn.getText();
                    break;
                } 
                else if (c instanceof JPanel panel) 
                {
                    for (Component inner : panel.getComponents()) 
                    {
                        if (inner instanceof JRadioButton btn && btn.isSelected()) 
                        {
                            selectedType = otherTypeField.getText().trim();
                            break;
                        }
                    }
                }
            }

            boolean allFilled = monthBox.getSelectedItem() != null &&
                    dayBox.getSelectedItem() != null &&
                    yearBox.getSelectedItem() != null &&
                    !timeField.getText().isEmpty() &&
                    !locationField.getText().isEmpty() &&
                    !descriptionArea.getText().isEmpty() &&
                    selectedType != null && !selectedType.isEmpty() &&
                    !personNameField.getText().isEmpty() &&
                    !contactField.getText().isEmpty() &&
                    attachedFile[0] != null;

            if (!allFilled) 
            {
                JOptionPane.showMessageDialog(reportingPanel, "Please complete all fields including the file attachment.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            } 
            else 
            {
                JOptionPane.showMessageDialog(reportingPanel, "Incident report submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Logic to process/save data can go here
            }
        });

        // --- Assembly ---
        reportingPanel.add(createLabeledField("Date (MM/DD/YYYY):", datePanel));
        reportingPanel.add(createLabeledField("Time:", timeField));
        reportingPanel.add(createLabeledField("Location of the incident:", locationField));
        reportingPanel.add(createLabeledField("Describe the incident:", descriptionScroll));

        JPanel uploadPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        uploadPanel.setOpaque(false);
        uploadPanel.add(uploadButton);
        uploadPanel.add(fileNameLabel);
        reportingPanel.add(createLabeledField("Attached Document:", uploadPanel));

        reportingPanel.add(createLabeledField("Type of Incident:", typePanel));

        JPanel personDetailsPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        personDetailsPanel.setOpaque(false);
        personDetailsPanel.add(new JLabel("Name:"));
        personDetailsPanel.add(personNameField);
        personDetailsPanel.add(new JLabel("Contact:"));
        personDetailsPanel.add(contactField);
        reportingPanel.add(createLabeledField("Involved Person(s) Details:", personDetailsPanel));

        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitPanel.setOpaque(false);
        submitPanel.add(submitButton);
        reportingPanel.add(submitPanel);

        return reportingPanel;
    }


    // Create labeled field helper
    private static JPanel createLabeledField(String label, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setOpaque(false);
        JLabel jlabel = new JLabel(label);
        jlabel.setFont(new Font("Arial", Font.BOLD, 16));
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(jlabel, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }



    //for rounded buttons to be used in other program parts
    // from carlo villanueva's button
    public static class RoundedBorder extends AbstractBorder {
        private int radius;
        private Color borderColor;
        
        public RoundedBorder(int radius, Color borderColor) {
            this.radius = radius;
            this.borderColor = borderColor;
        }
    
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(borderColor); // Line color here
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }
    }

    


    // to easily call the rounderBorder class
    public static RoundedBorder getRoundedBorder(int radius, Color borderColor) {
        return new RoundedBorder(radius, borderColor);
    }

    // I actually dont know what this is for
    //to apply dynamically and for flexibility but still dont know this shit
    public static void styleRoundedButton(JButton button, int radius, Color bgColor, Color fgColor, Color borderColor) 
    {
        button.setFocusPainted(false);
        button.setBorder(getRoundedBorder(radius, borderColor));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("SanSerif", Font.BOLD, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }       

    public static class RoundedButton extends JButton 
    {
        private final int radius;
        private final Color bgColor;
        private final Color borderColor;
        
        //this is for a reusable rounded button
        public RoundedButton(String text, int radius, Color bgColor, Color borderColor) {
            super(text);
            this.radius = radius;
            this.bgColor = bgColor;
            this.borderColor = borderColor;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setForeground(Color.WHITE);
            setFont(new Font("SansSerif", Font.BOLD, 20));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) 
        {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
        }
    }



}
