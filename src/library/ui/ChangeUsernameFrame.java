package library.ui;

import javax.swing.*;
import java.awt.*;

import library.dao.AdminDAO;

public class ChangeUsernameFrame extends JFrame {

    private JTextField newUsernameField;
    private JTextField confirmUsernameField;

    private String email;
    private String phone;

    public ChangeUsernameFrame(String email, String phone) {

        this.email = email;
        this.phone = phone;

        setTitle("Change Username");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        UITheme.applyFrame(this);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UITheme.BACKGROUND);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 40, 20, 40)
        );

        mainPanel.setLayout(new BorderLayout(10, 15));

        // =========================
        // TITLE
        // =========================

        JLabel title =
                UITheme.createTitle("CHANGE USERNAME");

        mainPanel.add(title, BorderLayout.NORTH);

        // =========================
        // FORM PANEL
        // =========================

        JPanel formPanel = new JPanel(
                new GridLayout(4, 1, 10, 10)
        );

        formPanel.setBackground(UITheme.BACKGROUND);

        // New Username
        JPanel usernamePanel = new JPanel(
                new BorderLayout(10, 5)
        );
        usernamePanel.setBackground(UITheme.BACKGROUND);

        JLabel usernameLabel =
                UITheme.createLabel("New Username:");

        newUsernameField =
                UITheme.createTextField();

        usernamePanel.add(
                usernameLabel,
                BorderLayout.WEST
        );

        usernamePanel.add(
                newUsernameField,
                BorderLayout.CENTER
        );

        // Confirm Username
        JPanel confirmPanel = new JPanel(
                new BorderLayout(10, 5)
        );
        confirmPanel.setBackground(UITheme.BACKGROUND);

        JLabel confirmLabel =
                UITheme.createLabel("Confirm Username:");

        confirmUsernameField =
                UITheme.createTextField();

        confirmPanel.add(
                confirmLabel,
                BorderLayout.WEST
        );

        confirmPanel.add(
                confirmUsernameField,
                BorderLayout.CENTER
        );

        formPanel.add(usernamePanel);
        formPanel.add(confirmPanel);

        // Information label
        JLabel infoLabel = new JLabel(
                "<html><center>"
                + "Username must contain 3-30 characters.<br>"
                + "Only letters, numbers and underscore (_) are allowed."
                + "</center></html>",
                SwingConstants.CENTER
        );

        infoLabel.setFont(UITheme.NORMAL_FONT);
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
                        "Change Username"
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
        // CHANGE USERNAME BUTTON
        // =========================

        changeButton.addActionListener(e -> changeUsername());

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
    // CHANGE USERNAME METHOD
    // =====================================================

    private void changeUsername() {

        String newUsername =
                newUsernameField.getText().trim();

        String confirmUsername =
                confirmUsernameField.getText().trim();

        // Empty check
        if (newUsername.isEmpty()
                || confirmUsername.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter and confirm your new username.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Username format check
        if (!newUsername.matches(
                "[a-zA-Z0-9_]{3,30}"
        )) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username.\n\n"
                    + "Username must:\n"
                    + "• Contain 3-30 characters\n"
                    + "• Use only letters, numbers and _",
                    "Invalid Username",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Confirm username
        if (!newUsername.equals(confirmUsername)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Usernames do not match.",
                    "Mismatch",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Confirmation
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to change your username?",
                "Confirm Username Change",
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
                dao.changeUsername(
                        email,
                        phone,
                        newUsername
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Username changed successfully!\n\n"
                    + "Your new username is: "
                    + newUsername
                    + "\n\n"
                    + "Please login using your new username.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

            new LoginFrame();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Username could not be changed.\n\n"
                    + "The username may already exist.",
                    "Change Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}