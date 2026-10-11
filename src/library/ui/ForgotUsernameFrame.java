package library.ui;

import library.dao.AdminDAO;

import javax.swing.*;
import java.awt.*;

public class ForgotUsernameFrame extends JFrame {

    private JTextField emailField;
    private JTextField phoneField;

    public ForgotUsernameFrame() {

        setTitle("FORGOT USERNAME");
        setSize(550, 500);
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
                        "FORGOT USERNAME"
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
                                30, 30, 30, 30
                        )
                )
        );

        formPanel.setLayout(
                new GridBagLayout()
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(12, 8, 12, 8);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1;

        // =================================================
        // INFORMATION LABEL
        // =================================================

        JLabel infoLabel =
                new JLabel(
                        "<html><center>"
                                + "Enter your registered email and phone number<br>"
                                + "to verify your account."
                                + "</center></html>",
                        SwingConstants.CENTER
                );

        infoLabel.setFont(
                UITheme.NORMAL_FONT
        );

        infoLabel.setForeground(
                UITheme.SECONDARY_TEXT
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        formPanel.add(
                infoLabel,
                gbc
        );

        // =================================================
        // EMAIL
        // =================================================

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;

        formPanel.add(
                UITheme.createLabel("Registered Email:"),
                gbc
        );

        emailField =
                UITheme.createTextField();

        gbc.gridx = 1;

        formPanel.add(
                emailField,
                gbc
        );

        // =================================================
        // PHONE
        // =================================================

        gbc.gridx = 0;
        gbc.gridy = 2;

        formPanel.add(
                UITheme.createLabel("Registered Phone:"),
                gbc
        );

        phoneField =
                UITheme.createTextField();

        gbc.gridx = 1;

        formPanel.add(
                phoneField,
                gbc
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

        JButton verifyButton =
                UITheme.createPrimaryButton(
                        "Verify"
                );

        JButton clearButton =
                UITheme.createSecondaryButton(
                        "Clear"
                );

        JButton backButton =
                UITheme.createDangerButton(
                        "Back"
                );

        buttonPanel.add(
                verifyButton
        );

        buttonPanel.add(
                clearButton
        );

        buttonPanel.add(
                backButton
        );

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;

        formPanel.add(
                buttonPanel,
                gbc
        );

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        // =================================================
        // VERIFY BUTTON
        // =================================================

        verifyButton.addActionListener(
                e -> verifyAccount()
        );

        // =================================================
        // ENTER KEY
        // =================================================

        phoneField.addActionListener(
                e -> verifyAccount()
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

        setContentPane(mainPanel);
    }

    // =====================================================
    // VERIFY ACCOUNT
    // =====================================================

    private void verifyAccount() {

        String email =
                emailField.getText()
                        .trim();

        String phone =
                phoneField.getText()
                        .trim();

        // =================================================
        // EMPTY CHECK
        // =================================================

        if (email.isEmpty()
                || phone.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter both email and phone number.",
                    "Missing Information",
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
                    "Invalid Phone",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =================================================
        // VERIFY WITH DATABASE
        // =================================================

        AdminDAO dao =
                new AdminDAO();

        boolean verified =
                dao.verifyAdmin(
                        email,
                        phone
                );

        if (verified) {

            JOptionPane.showMessageDialog(
                    this,
                    "Account verified successfully!",
                    "Verification Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

            new ChangeUsernameFrame(
                    email,
                    phone
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Email and phone number do not match any account.",
                    "Verification Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // CLEAR FIELDS
    // =====================================================

    private void clearFields() {

        emailField.setText("");

        phoneField.setText("");

        emailField.requestFocus();
    }
}