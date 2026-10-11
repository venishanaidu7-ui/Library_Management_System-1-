package library.ui;

import javax.swing.*;
import java.awt.*;

import library.dao.AdminDAO;

public class ChangePasswordFrame extends JFrame {

    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;

    private String username;
    private String email;

    public ChangePasswordFrame(String username, String email) {

        this.username = username;
        this.email = email;

        setTitle("Change Password");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        UITheme.applyFrame(this);

        // =========================
        // MAIN PANEL
        // =========================

        JPanel mainPanel = new JPanel();

        mainPanel.setBackground(UITheme.BACKGROUND);

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 40, 20, 40
                )
        );

        mainPanel.setLayout(
                new BorderLayout(10, 15)
        );

        // =========================
        // TITLE
        // =========================

        JLabel title =
                UITheme.createTitle("CHANGE PASSWORD");

        mainPanel.add(
                title,
                BorderLayout.NORTH
        );

        // =========================
        // FORM PANEL
        // =========================

        JPanel formPanel = new JPanel(
                new GridLayout(4, 1, 10, 10)
        );

        formPanel.setBackground(
                UITheme.BACKGROUND
        );

        // New Password
        JPanel passwordPanel = new JPanel(
                new BorderLayout(10, 5)
        );

        passwordPanel.setBackground(
                UITheme.BACKGROUND
        );

        JLabel passwordLabel =
                UITheme.createLabel(
                        "New Password:"
                );

        newPasswordField =
                UITheme.createPasswordField();

        passwordPanel.add(
                passwordLabel,
                BorderLayout.WEST
        );

        passwordPanel.add(
                newPasswordField,
                BorderLayout.CENTER
        );

        // Confirm Password
        JPanel confirmPanel = new JPanel(
                new BorderLayout(10, 5)
        );

        confirmPanel.setBackground(
                UITheme.BACKGROUND
        );

        JLabel confirmLabel =
                UITheme.createLabel(
                        "Confirm Password:"
                );

        confirmPasswordField =
                UITheme.createPasswordField();

        confirmPanel.add(
                confirmLabel,
                BorderLayout.WEST
        );

        confirmPanel.add(
                confirmPasswordField,
                BorderLayout.CENTER
        );

        formPanel.add(passwordPanel);
        formPanel.add(confirmPanel);

        // Information label
        JLabel infoLabel = new JLabel(
                "<html><center>"
                + "Password must contain at least 6 characters."
                + "<br>"
                + "Make sure both passwords are the same."
                + "</center></html>",
                SwingConstants.CENTER
        );

        infoLabel.setFont(
                UITheme.NORMAL_FONT
        );

        infoLabel.setForeground(
                UITheme.SECONDARY_TEXT
        );

        formPanel.add(infoLabel);

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        // =========================
        // BUTTON PANEL
        // =========================

        JPanel buttonPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        15,
                        10
                )
        );

        buttonPanel.setBackground(
                UITheme.BACKGROUND
        );

        JButton changeButton =
                UITheme.createPrimaryButton(
                        "Change Password"
                );

        JButton backButton =
                UITheme.createSecondaryButton(
                        "Back to Login"
                );

        buttonPanel.add(changeButton);
        buttonPanel.add(backButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        // =========================
        // CHANGE PASSWORD BUTTON
        // =========================

        changeButton.addActionListener(
                e -> changePassword()
        );

        // =========================
        // BACK BUTTON
        // =========================

        backButton.addActionListener(e -> {

            dispose();

            new LoginFrame();
        });

        setVisible(true);
    }

    // =====================================================
    // CHANGE PASSWORD METHOD
    // =====================================================

    private void changePassword() {

        String newPassword =
                new String(
                        newPasswordField.getPassword()
                );

        String confirmPassword =
                new String(
                        confirmPasswordField.getPassword()
                );

        // =========================
        // EMPTY CHECK
        // =========================

        if (newPassword.isEmpty()
                || confirmPassword.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter and confirm your new password.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =========================
        // PASSWORD LENGTH
        // =========================

        if (newPassword.length() < 6) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password must contain at least 6 characters.",
                    "Invalid Password",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =========================
        // PASSWORD MATCH
        // =========================

        if (!newPassword.equals(confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match.",
                    "Mismatch",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =========================
        // CONFIRMATION
        // =========================

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to change your password?",
                "Confirm Password Change",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        // =========================
        // DATABASE UPDATE
        // =========================

        AdminDAO dao = new AdminDAO();

        boolean success =
                dao.changePassword(
                        username,
                        email,
                        newPassword
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password changed successfully!\n\n"
                    + "Please login using your new password.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

            new LoginFrame();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Password could not be changed.\n\n"
                    + "Please try again.",
                    "Change Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}