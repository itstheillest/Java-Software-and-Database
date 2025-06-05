package utils;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
}