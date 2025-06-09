// FooterPanel.java
package ui.panels.leftpanel;

import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class FooterPanel {
    private Main mainFrame;
    private JButton adminButton;

    public FooterPanel(Main mainFrame) {
        this.mainFrame = mainFrame;
    }

    public JPanel createPanel() {
        JPanel footerPanel = new JPanel(new GridBagLayout());
        footerPanel.setOpaque(false);

        adminButton = createAdminButton();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        footerPanel.add(adminButton, gbc);

        return footerPanel;
    }

    private JButton createAdminButton() {
        JButton adminButton = new JButton("Admin Login");

        // Set button properties
        setupAdminButtonProperties(adminButton);

        // Add event listeners
        addAdminButtonListeners(adminButton);

        return adminButton;
    }

    private void setupAdminButtonProperties(JButton adminButton) {
        adminButton.setFont(new Font("Arial", Font.ITALIC, 18));
        adminButton.setForeground(new Color(255, 255, 255, 200));
        adminButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        adminButton.setFocusPainted(false);
        adminButton.setContentAreaFilled(false);
        adminButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void addAdminButtonListeners(JButton adminButton) {
        // Hover effects
        adminButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                adminButton.setForeground(new Color(255, 193, 7));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                adminButton.setForeground(new Color(255, 255, 255, 200));
            }
        });

        // Action listener
        adminButton.addActionListener(e -> handleAdminLogin());
    }

    private void handleAdminLogin() {
        // Show admin login dialog
        showAdminLoginDialog();
    }

    private void showAdminLoginDialog() {
        // Create a custom login dialog
        JDialog loginDialog = new JDialog(mainFrame, "Admin Login", true);
        loginDialog.setLayout(new GridBagLayout());
        loginDialog.setSize(300, 200);
        loginDialog.setLocationRelativeTo(mainFrame);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Username field
        gbc.gridx = 0; gbc.gridy = 0;
        loginDialog.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(15);
        loginDialog.add(usernameField, gbc);

        // Password field
        gbc.gridx = 0; gbc.gridy = 1;
        loginDialog.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        loginDialog.add(passwordField, gbc);

        // Buttons panel
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel buttonPanel = createLoginButtonPanel(loginDialog, usernameField, passwordField);
        loginDialog.add(buttonPanel, gbc);

        loginDialog.setVisible(true);
    }

    private JPanel createLoginButtonPanel(JDialog loginDialog, JTextField usernameField, JPasswordField passwordField) {
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton loginBtn = new JButton("Login");
        JButton cancelBtn = new JButton("Cancel");

        loginBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (validateAdminCredentials(username, password)) {
                loginDialog.dispose();
                onSuccessfulAdminLogin();
            } else {
                JOptionPane.showMessageDialog(loginDialog,
                        "Invalid credentials!",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
            }
        });

        cancelBtn.addActionListener(e -> loginDialog.dispose());

        buttonPanel.add(loginBtn);
        buttonPanel.add(cancelBtn);

        return buttonPanel;
    }

    private boolean validateAdminCredentials(String username, String password) {
        // TODO: Implement actual authentication logic
        // For now, using simple hardcoded credentials
        return "admin".equals(username) && "password".equals(password);
    }

    private void onSuccessfulAdminLogin() {
        JOptionPane.showMessageDialog(mainFrame,
                "Admin login successful!",
                "Welcome",
                JOptionPane.INFORMATION_MESSAGE);

        // TODO: Implement admin functionality
        // Example: mainFrame.showAdminPanel();
    }

    // Public methods for external customization
    public void setAdminActionListener(ActionListener listener) {
        if (adminButton != null) {
            // Remove existing listeners
            for (ActionListener al : adminButton.getActionListeners()) {
                adminButton.removeActionListener(al);
            }
            // Add new listener
            adminButton.addActionListener(listener);
        }
    }

    public void setAdminCredentialsValidator(AdminCredentialsValidator validator) {
        // You could implement a custom validator interface
        // This allows for external authentication systems
    }

    // Interface for custom credential validation
    public interface AdminCredentialsValidator {
        boolean validate(String username, String password);
    }
}
