package library.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import library.dao.AdminDAO;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {

        setTitle("Library Management System - Login");
        setSize(520, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        UITheme.applyFrame(this);

        // =========================
        // MAIN PANEL
        // =========================

        JPanel mainPanel = new JPanel(
                new BorderLayout()
        );

        mainPanel.setBackground(
                UITheme.BACKGROUND
        );

        mainPanel.setBorder(
                new EmptyBorder(
                        30, 50, 30, 50
                )
        );

        // =========================
        // HEADER
        // =========================

        JPanel headerPanel = new JPanel(
                new BorderLayout(5, 5)
        );

        headerPanel.setBackground(
                UITheme.BACKGROUND
        );

        JLabel title = new JLabel(
                "LIBRARY MANAGEMENT SYSTEM",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        title.setForeground(
                UITheme.PRIMARY_DARK
        );

        JLabel subtitle = new JLabel(
                "ADMIN LOGIN",
                SwingConstants.CENTER
        );

        subtitle.setFont(
                UITheme.HEADING_FONT
        );

        subtitle.setForeground(
                UITheme.PRIMARY
        );

        headerPanel.add(
                title,
                BorderLayout.NORTH
        );

        headerPanel.add(
                subtitle,
                BorderLayout.CENTER
        );

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =========================
        // LOGIN CARD
        // =========================

        JPanel card = new JPanel(
                new GridBagLayout()
        );

        card.setBackground(
                UITheme.CARD
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UITheme.BORDER
                        ),
                        new EmptyBorder(
                                30, 30, 30, 30
                        )
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(8, 5, 8, 5);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1.0;

        // =========================
        // USERNAME
        // =========================

        JLabel usernameLabel =
                UITheme.createLabel(
                        "Username"
                );

        gbc.gridx = 0;
        gbc.gridy = 0;

        card.add(
                usernameLabel,
                gbc
        );

        usernameField =
                UITheme.createTextField();

        gbc.gridy = 1;

        card.add(
                usernameField,
                gbc
        );

        // =========================
        // PASSWORD
        // =========================

        JLabel passwordLabel =
                UITheme.createLabel(
                        "Password"
                );

        gbc.gridy = 2;

        card.add(
                passwordLabel,
                gbc
        );

        passwordField =
                UITheme.createPasswordField();

        gbc.gridy = 3;

        card.add(
                passwordField,
                gbc
        );

        // =========================
        // LOGIN BUTTON
        // =========================

        JButton loginButton =
                UITheme.createPrimaryButton(
                        "LOGIN"
                );

        gbc.gridy = 4;
        gbc.insets =
                new Insets(
                        18, 5, 8, 5
                );

        card.add(
                loginButton,
                gbc
        );

        // =========================
        // EXIT BUTTON
        // =========================

        JButton exitButton =
                UITheme.createDangerButton(
                        "EXIT"
                );

        gbc.gridy = 5;
        gbc.insets =
                new Insets(
                        5, 5, 12, 5
                );

        card.add(
                exitButton,
                gbc
        );

        // =========================
        // REGISTER
        // =========================

        JLabel registerLabel =
                new JLabel(
                        "New administrator?",
                        SwingConstants.CENTER
                );

        registerLabel.setFont(
                UITheme.NORMAL_FONT
        );

        registerLabel.setForeground(
                UITheme.SECONDARY_TEXT
        );

        gbc.gridy = 6;
        gbc.insets =
                new Insets(
                        5, 5, 2, 5
                );

        card.add(
                registerLabel,
                gbc
        );

        JButton registerButton =
                UITheme.createSecondaryButton(
                        "REGISTER"
                );

        gbc.gridy = 7;
        gbc.insets =
                new Insets(
                        2, 5, 8, 5
                );

        card.add(
                registerButton,
                gbc
        );

        // =========================
        // ACCOUNT RECOVERY
        // =========================

        JPanel recoveryPanel =
                new JPanel(
                        new GridLayout(
                                1, 2, 10, 0
                        )
                );

        recoveryPanel.setBackground(
                UITheme.CARD
        );

        JButton forgotUsernameButton =
                UITheme.createSecondaryButton(
                        "Forgot Username?"
                );

        JButton forgotPasswordButton =
                UITheme.createSecondaryButton(
                        "Forgot Password?"
                );

        recoveryPanel.add(
                forgotUsernameButton
        );

        recoveryPanel.add(
                forgotPasswordButton
        );

        gbc.gridy = 8;
        gbc.insets =
                new Insets(
                        8, 5, 5, 5
                );

        card.add(
                recoveryPanel,
                gbc
        );

        mainPanel.add(
                card,
                BorderLayout.CENTER
        );

        // =========================
        // FOOTER
        // =========================

        JLabel footerLabel =
                new JLabel(
                        "Library Management System",
                        SwingConstants.CENTER
                );

        footerLabel.setFont(
                new Font(
                        "Arial",
                        Font.ITALIC,
                        11
                )
        );

        footerLabel.setForeground(
                UITheme.SECONDARY_TEXT
        );

        footerLabel.setBorder(
                new EmptyBorder(
                        15, 0, 0, 0
                )
        );

        mainPanel.add(
                footerLabel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        // =========================
        // LOGIN ACTION
        // =========================

        loginButton.addActionListener(
                e -> login()
        );

        // =========================
        // ENTER KEY
        // =========================

        passwordField.addActionListener(
                e -> login()
        );

        // =========================
        // EXIT ACTION
        // =========================

        exitButton.addActionListener(e -> {

            int choice =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to exit?",
                            "Confirm Exit",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // =========================
        // REGISTER ACTION
        // =========================

        registerButton.addActionListener(e -> {

            dispose();

            new RegistrationFrame();
        });

        // =========================
        // FORGOT USERNAME
        // =========================

        forgotUsernameButton.addActionListener(
                e -> {

                    dispose();

                    new ForgotUsernameFrame();
                }
        );

        // =========================
        // FORGOT PASSWORD
        // =========================

        forgotPasswordButton.addActionListener(
                e -> {

                    dispose();

                    new ForgotPasswordFrame();
                }
        );

        setVisible(true);
    }

    // =====================================================
    // LOGIN METHOD
    // =====================================================

    private void login() {

        String username =
                usernameField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        // =========================
        // EMPTY CHECK
        // =========================

        if (username.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter both username and password.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =========================
        // DATABASE LOGIN
        // =========================

        AdminDAO dao =
                new AdminDAO();

        boolean success =
                dao.login(
                        username,
                        password
                );

        if (success) {

            dispose();

            new AdminDashboard();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            passwordField.setText("");

            passwordField.requestFocus();
        }
    }
}