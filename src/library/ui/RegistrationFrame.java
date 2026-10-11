package library.ui;

import library.dao.AdminDAO;

import javax.swing.*;
import java.awt.*;

public class RegistrationFrame extends JFrame {

    private JTextField usernameField;
    private JTextField emailField;
    private JTextField phoneField;

    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public RegistrationFrame() {

        setTitle("ADMIN REGISTRATION");
        setSize(550, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        UITheme.applyFrame(this);

        createUI();

        setVisible(true);
    }

    // =====================================================
    // CREATE UI
    // =====================================================

    private void createUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.setBackground(
                UITheme.BACKGROUND
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 40, 25, 40
                )
        );

        // =================================================
        // TITLE
        // =================================================

        JLabel title =
                UITheme.createTitle(
                        "ADMIN REGISTRATION"
                );

        mainPanel.add(
                title,
                BorderLayout.NORTH
        );

        // =================================================
        // FORM PANEL
        // =================================================

        JPanel formPanel =
                UITheme.createPanel();

        formPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UITheme.BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                25, 30, 25, 30
                        )
                )
        );

        formPanel.setLayout(
                new GridBagLayout()
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(10, 8, 10, 8);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1;

        // =================================================
        // USERNAME
        // =================================================

        addLabel(
                formPanel,
                gbc,
                0,
                "Username"
        );

        usernameField =
                UITheme.createTextField();

        addField(
                formPanel,
                gbc,
                1,
                usernameField
        );

        // =================================================
        // EMAIL
        // =================================================

        addLabel(
                formPanel,
                gbc,
                2,
                "Email"

        );

        emailField =
                UITheme.createTextField();

        addField(
                formPanel,
                gbc,
                3,
                emailField
        );

        // =================================================
        // PHONE
        // =================================================

        addLabel(
                formPanel,
                gbc,
                4,
                "Phone Number"
        );

        phoneField =
                UITheme.createTextField();

        addField(
                formPanel,
                gbc,
                5,
                phoneField
        );

        // =================================================
        // PASSWORD
        // =================================================

        addLabel(
                formPanel,
                gbc,
                6,
                "Password"
        );

        passwordField =
                UITheme.createPasswordField();

        addField(
                formPanel,
                gbc,
                7,
                passwordField
        );

        // =================================================
        // CONFIRM PASSWORD
        // =================================================

        addLabel(
                formPanel,
                gbc,
                8,
                "Confirm Password"
        );

        confirmPasswordField =
                UITheme.createPasswordField();

        addField(
                formPanel,
                gbc,
                9,
                confirmPasswordField
        );

        // =================================================
        // BUTTON PANEL
        // =================================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                12,
                                5
                        )
                );

        buttonPanel.setBackground(
                UITheme.CARD
        );

        JButton registerButton =
                UITheme.createSuccessButton(
                        "Register"
                );

        JButton clearButton =
                UITheme.createSecondaryButton(
                        "Clear"
                );

        JButton backButton =
                UITheme.createDangerButton(
                        "Back"
                );

        buttonPanel.add(registerButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.weightx = 1;

        formPanel.add(
                buttonPanel,
                gbc
        );

        // =================================================
        // ADD FORM TO MAIN PANEL
        // =================================================

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        // =================================================
        // REGISTER BUTTON
        // =================================================

        registerButton.addActionListener(
                e -> registerAdmin()
        );

        // =================================================
        // CLEAR BUTTON
        // =================================================

        clearButton.addActionListener(
                e -> clearFields()
        );

        // =================================================
        // BACK BUTTON
        // =================================================

        backButton.addActionListener(
                e -> {

                    dispose();

                    new LoginFrame();
                }
        );

        // =================================================
        // ENTER KEY
        // =================================================

        confirmPasswordField.addActionListener(
                e -> registerAdmin()
        );

        setContentPane(mainPanel);
    }

    // =====================================================
    // ADD LABEL
    // =====================================================

    private void addLabel(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String text) {

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;

        panel.add(
                UITheme.createLabel(text),
                gbc
        );
    }

    // =====================================================
    // ADD FIELD
    // =====================================================

    private void addField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            JTextField field) {

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.weightx = 1;

        panel.add(
                field,
                gbc
        );
    }

    // =====================================================
    // REGISTER ADMIN
    // =====================================================

    private void registerAdmin() {

        String username =
                usernameField.getText()
                        .trim();

        String email =
                emailField.getText()
                        .trim();

        String phone =
                phoneField.getText()
                        .trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        String confirmPassword =
                new String(
                        confirmPasswordField.getPassword()
                );

        // =================================================
        // EMPTY FIELD CHECK
        // =================================================

        if (username.isEmpty()
                || email.isEmpty()
                || phone.isEmpty()
                || password.isEmpty()
                || confirmPassword.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =================================================
        // USERNAME VALIDATION
        // =================================================

        if (!username.matches(
                "[a-zA-Z0-9_]{3,30}"
        )) {

            JOptionPane.showMessageDialog(
                    this,
                    "Username must contain 3-30 characters.\n"
                            + "Only letters, numbers and underscore are allowed.",
                    "Invalid Username",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =================================================
        // EMAIL VALIDATION
        // =================================================

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

        // =================================================
        // PHONE VALIDATION
        // =================================================

        if (!phone.matches(
                "[0-9]{10}"
        )) {

            JOptionPane.showMessageDialog(
                    this,
                    "Phone number must contain exactly 10 digits.",
                    "Invalid Phone Number",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =================================================
        // PASSWORD VALIDATION
        // =================================================

        if (password.length() < 6) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password must contain at least 6 characters.",
                    "Invalid Password",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =================================================
        // CONFIRM PASSWORD
        // =================================================

        if (!password.equals(confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password and Confirm Password do not match.",
                    "Password Mismatch",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =================================================
        // CONFIRM REGISTRATION
        // =================================================

        int choice =
                JOptionPane.showConfirmDialog(
                        this,

                        "Username: "
                                + username
                                + "\nEmail: "
                                + email
                                + "\nPhone: "
                                + phone
                                + "\n\nCreate this admin account?",

                        "Confirm Registration",

                        JOptionPane.YES_NO_OPTION,

                        JOptionPane.QUESTION_MESSAGE
                );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        // =================================================
        // DATABASE REGISTRATION
        // =================================================

        AdminDAO dao =
                new AdminDAO();

        boolean success =
                dao.registerAdmin(
                        username,
                        password,
                        email,
                        phone
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Admin registered successfully!",
                    "Registration Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

            // Return to login
            dispose();

            new LoginFrame();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration failed.\n\n"
                            + "Username, email or phone number may already exist.",
                    "Registration Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // CLEAR FIELDS
    // =====================================================

    private void clearFields() {

        usernameField.setText("");

        emailField.setText("");

        phoneField.setText("");

        passwordField.setText("");

        confirmPasswordField.setText("");

        usernameField.requestFocus();
    }
}