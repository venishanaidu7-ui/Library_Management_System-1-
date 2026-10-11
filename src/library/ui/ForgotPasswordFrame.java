package library.ui;

import javax.swing.*;
import java.awt.*;

import library.dao.AdminDAO;

public class ForgotPasswordFrame extends JFrame {

    private JTextField usernameField;
    private JTextField emailField;

    public ForgotPasswordFrame() {

        setTitle("Forgot Password");
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
                UITheme.createTitle("FORGOT PASSWORD");

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

        // Username
        JPanel usernamePanel = new JPanel(
                new BorderLayout(10, 5)
        );

        usernamePanel.setBackground(
                UITheme.BACKGROUND
        );

        JLabel usernameLabel =
                UITheme.createLabel("Username:");

        usernameField =
                UITheme.createTextField();

        usernamePanel.add(
                usernameLabel,
                BorderLayout.WEST
        );

        usernamePanel.add(
                usernameField,
                BorderLayout.CENTER
        );

        // Email
        JPanel emailPanel = new JPanel(
                new BorderLayout(10, 5)
        );

        emailPanel.setBackground(
                UITheme.BACKGROUND
        );

        JLabel emailLabel =
                UITheme.createLabel(
                        "Registered Email:"
                );

        emailField =
                UITheme.createTextField();

        emailPanel.add(
                emailLabel,
                BorderLayout.WEST
        );

        emailPanel.add(
                emailField,
                BorderLayout.CENTER
        );

        formPanel.add(usernamePanel);
        formPanel.add(emailPanel);

        // Information
        JLabel infoLabel = new JLabel(
                "<html><center>"
                + "Enter the username and registered email "
                + "associated with your account."
                + "<br>"
                + "Your information will be verified before "
                + "changing the password."
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

        JButton verifyButton =
                UITheme.createPrimaryButton(
                        "Verify"
                );

        JButton backButton =
                UITheme.createSecondaryButton(
                        "Back to Login"
                );

        buttonPanel.add(verifyButton);
        buttonPanel.add(backButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        // =========================
        // VERIFY BUTTON
        // =========================

        verifyButton.addActionListener(
                e -> verifyDetails()
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
    // VERIFY USER DETAILS
    // =====================================================

    private void verifyDetails() {

        String username =
                usernameField.getText().trim();

        String email =
                emailField.getText().trim();

        // =========================
        // EMPTY CHECK
        // =========================

        if (username.isEmpty()
                || email.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter both username and email.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =========================
        // USERNAME VALIDATION
        // =========================

        if (!username.matches(
                "[a-zA-Z0-9_]{3,30}"
        )) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username.\n\n"
                    + "Username must contain 3-30 characters "
                    + "and use only letters, numbers and underscore.",
                    "Invalid Username",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =========================
        // EMAIL VALIDATION
        // =========================

        if (!email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        )) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid email address.",
                    "Invalid Email",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =========================
        // DATABASE VERIFICATION
        // =========================

        AdminDAO dao = new AdminDAO();

        boolean verified =
                dao.verifyUsernameAndEmail(
                        username,
                        email
                );

        if (verified) {

            JOptionPane.showMessageDialog(
                    this,
                    "Username and email verified successfully!",
                    "Verification Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

            new ChangePasswordFrame(
                    username,
                    email
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Username and registered email do not match.\n\n"
                    + "Please check your details and try again.",
                    "Verification Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}