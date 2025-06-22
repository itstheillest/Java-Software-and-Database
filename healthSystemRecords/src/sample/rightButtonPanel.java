package sample;

// import sample.Main;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;	
import javax.swing.border.AbstractBorder;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.TitledBorder;

class RoundedButtonBorder extends AbstractBorder {
    private int radius;
    private Color backgroundColor;

    public RoundedButtonBorder(int radius, Color backgroundColor) {
        this.radius = radius;
        this.backgroundColor = backgroundColor;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Fill the rounded background first
        g2.setColor(backgroundColor);
        g2.fillRoundRect(x, y, width - 1, height - 1, radius, radius);
        
        // Then draw the border (optional - you can remove this if you don't want a border)
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(1));
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(4, 8, 4, 8);
    }
}

class RoundedSubmitButton extends JButton {
    private int radius;
    private Color bgColor;
    
    public RoundedSubmitButton(String text, int radius, Color bgColor) {
        super(text);
        this.radius = radius;
        this.bgColor = bgColor;
        setOpaque(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Fill the background
        g2.setColor(bgColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        
        // Add hover effect - black border when mouse is over
        if (getModel().isPressed()) {
            // Darker background when pressed
            g2.setColor(bgColor.darker());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        } else if (getModel().isRollover()) {
            // Slightly lighter background when hovering
            g2.setColor(bgColor.brighter());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            
            // Black border when hovering
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, radius, radius);
        } else {
            // Normal state
            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        }
        
        g2.dispose();
        super.paintComponent(g);
    }
}

public class rightButtonPanel {
    private JPanel rightPanel;
    private JButton returnButton;
    private JComboBox<String> appointmentDropdown;
    private JTextArea noteTextArea;
    private JButton submitButton;

    private Main mainApp;

    public rightButtonPanel(Main mainApp) {
        this.mainApp = mainApp;

        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(500, 800)); // Adjust size as needed

        rightPanel.add(setUpHeader(), BorderLayout.NORTH);
        rightPanel.add(setUpBody(), BorderLayout.CENTER);
    }

    private JPanel setUpHeader() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(85, 107, 47)); // Olive green

        returnButton = new JButton("Return");
        returnButton.addActionListener(e -> mainApp.restoreMainButtons());
        headerPanel.add(returnButton);

        return headerPanel;
    }

    private JPanel setUpBody() {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        bodyPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Form Panel with rounded border
        JPanel appointmentFormPanel = new JPanel();
        appointmentFormPanel.setLayout(new BorderLayout());
        appointmentFormPanel.setBackground(new Color(245, 245, 245)); // Light gray, for example
        appointmentFormPanel.setOpaque(true); // Very important!
        appointmentFormPanel.setPreferredSize(new Dimension(800, 900));
        appointmentFormPanel.setMaximumSize(new Dimension(800, 900)); // 400, 450

        Border roundedBorder = BorderFactory.createCompoundBorder(
            new LineBorder(Color.GRAY, 8, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );
        appointmentFormPanel.setBorder(roundedBorder);

        // === Title Label ===
        JLabel titleLabel = new JLabel("Appointment Form", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel);
        appointmentFormPanel.add(titlePanel, BorderLayout.NORTH);

        // === Fields Panel ===
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        fieldsPanel.setOpaque(false);

        // Appointment Type Label
        JLabel typeLabel = new JLabel("Appointment Type:");
        typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldsPanel.add(typeLabel);

        appointmentDropdown = new JComboBox<>(new String[]{"   -- Select Appointment --", "Health Check-up - Every Teusday", "Vaccination - Every Friday"});
        appointmentDropdown.setPreferredSize(new Dimension(300, 35)); // width, height
        appointmentDropdown.setMaximumSize(new Dimension(300, 50));
        appointmentDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
        appointmentDropdown.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0)); // tighter spacing
        fieldsPanel.add(appointmentDropdown);

        // Note Label
        JLabel noteLabel = new JLabel("Note (Optional):");
        noteLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldsPanel.add(noteLabel);

        // Note Area
        noteTextArea = new JTextArea("Please describe your conditions here...", 5, 20);
        noteTextArea.setLineWrap(true);
        noteTextArea.setWrapStyleWord(true);
        noteTextArea.setForeground(new Color(150, 150, 150, 128));
        noteTextArea.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // ← Add this!
        
        noteTextArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (noteTextArea.getText().equals("Please describe your conditions here...")) {
                    noteTextArea.setText("");
                    noteTextArea.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (noteTextArea.getText().isEmpty()) {
                    noteTextArea.setText("Please describe your conditions here...");
                    noteTextArea.setForeground(new Color(150, 150, 150, 128));
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(noteTextArea);
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        fieldsPanel.add(scrollPane);

        appointmentFormPanel.add(fieldsPanel, BorderLayout.CENTER);

        // === Submit Button at Bottom ===
        submitButton = new RoundedSubmitButton("Submit", 20, new Color(34, 139, 34));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setPreferredSize(new Dimension(750, 40));
        submitButton.setMaximumSize(new Dimension(750, 40)); // 300, 40
        submitButton.addActionListener(e -> submitAppointment());

        JPanel submitPanel = new JPanel();
        submitPanel.setOpaque(false);
        submitPanel.setBorder(new EmptyBorder(10, 0, 0, 0)); // top padding
        submitPanel.add(submitButton);

        appointmentFormPanel.add(submitPanel, BorderLayout.SOUTH);

        // Add to body
        bodyPanel.add(appointmentFormPanel);

        return bodyPanel;
    }

    private void submitAppointment() {
        String selectedType = (String) appointmentDropdown.getSelectedItem();
        String selectedNote =  noteTextArea.getText();

        if (selectedType == null || selectedType.isEmpty() || selectedType == "   -- Select Appointment --" ) {
            JOptionPane.showMessageDialog(null, "Please select an appointment type.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String note = noteTextArea.getText().trim();
        
        if (selectedNote.equals("Please describe your conditions here...")) { 
        	selectedNote = "";
        	System.out.println("Reachable");
        }

        JOptionPane.showMessageDialog(null,
                "Appointment Type: " + selectedType + "\nNote: " + (selectedNote.isEmpty() ? "" : note),
                "Appointment Submitted", JOptionPane.INFORMATION_MESSAGE);
        
        appointmentDropdown.setSelectedIndex(0);
        noteTextArea.setText("Please describe your conditions here...");
        noteTextArea.setForeground(new Color(150, 150, 150, 128));
        
        noteTextArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (noteTextArea.getText().equals("Please describe your conditions here...")) {
                    noteTextArea.setText("");
                    noteTextArea.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (noteTextArea.getText().isEmpty()) {
                    noteTextArea.setText("Please describe your conditions here...");
                    noteTextArea.setForeground(new Color(150, 150, 150, 128));
                }
            }
        });
    }

    public JPanel getPanel() {
        return rightPanel;
    }
}

