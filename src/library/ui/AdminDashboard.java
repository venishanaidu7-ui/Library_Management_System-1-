package library.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import library.dao.DashboardDAO;

public class AdminDashboard extends JFrame {

    private JLabel totalBooksLabel;
    private JLabel availableBooksLabel;
    private JLabel issuedBooksLabel;
    private JLabel totalStudentsLabel;

    public AdminDashboard() {

        setTitle("Library Admin Dashboard");
        setSize(950, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        UITheme.applyFrame(this);

        // =========================
        // MAIN PANEL
        // =========================

        JPanel mainPanel = new JPanel(
                new BorderLayout(15, 15)
        );

        mainPanel.setBackground(
                UITheme.BACKGROUND
        );

        mainPanel.setBorder(
                new EmptyBorder(
                        20, 25, 20, 25
                )
        );

        // =========================
        // HEADER
        // =========================

        JPanel headerPanel = new JPanel(
                new BorderLayout()
        );

        headerPanel.setBackground(
                UITheme.BACKGROUND
        );

        JLabel title =
                UITheme.createTitle(
                        "LIBRARY ADMIN DASHBOARD"
                );

        JLabel welcomeLabel = new JLabel(
                "Welcome, Administrator",
                SwingConstants.CENTER
        );

        welcomeLabel.setFont(
                UITheme.NORMAL_FONT
        );

        welcomeLabel.setForeground(
                UITheme.SECONDARY_TEXT
        );

        headerPanel.add(
                title,
                BorderLayout.NORTH
        );

        headerPanel.add(
                welcomeLabel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =========================
        // CENTER PANEL
        // =========================

        JPanel centerPanel = new JPanel(
                new BorderLayout(15, 15)
        );

        centerPanel.setBackground(
                UITheme.BACKGROUND
        );

        // =========================
        // STATISTICS
        // =========================

        JPanel statisticsPanel = new JPanel(
                new GridLayout(1, 4, 15, 0)
        );

        statisticsPanel.setBackground(
                UITheme.BACKGROUND
        );

        totalBooksLabel =
                createStatisticCard(
                        statisticsPanel,
                        "TOTAL BOOKS"
                );

        availableBooksLabel =
                createStatisticCard(
                        statisticsPanel,
                        "AVAILABLE BOOKS"
                );

        issuedBooksLabel =
                createStatisticCard(
                        statisticsPanel,
                        "ISSUED BOOKS"
                );

        totalStudentsLabel =
                createStatisticCard(
                        statisticsPanel,
                        "TOTAL STUDENTS"
                );

        centerPanel.add(
                statisticsPanel,
                BorderLayout.NORTH
        );

        // =========================
        // MENU PANEL
        // =========================

        JPanel menuPanel = new JPanel(
                new GridLayout(3, 2, 15, 15)
        );

        menuPanel.setBackground(
                UITheme.BACKGROUND
        );

        JButton booksButton =
                UITheme.createPrimaryButton(
                        "Manage Books"
                );

        JButton studentsButton =
                UITheme.createPrimaryButton(
                        "Manage Students"
                );

        JButton issueButton =
                UITheme.createSuccessButton(
                        "Issue Book"
                );

        JButton returnButton =
                UITheme.createPrimaryButton(
                        "Return Book"
                );

        JButton transactionsButton =
                UITheme.createPrimaryButton(
                        "View Transactions"
                );

        JButton refreshButton =
                UITheme.createSecondaryButton(
                        "Refresh Statistics"
                );

        menuPanel.add(booksButton);
        menuPanel.add(studentsButton);
        menuPanel.add(issueButton);
        menuPanel.add(returnButton);
        menuPanel.add(transactionsButton);
        menuPanel.add(refreshButton);

        centerPanel.add(
                menuPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        // =========================
        // BOTTOM PANEL
        // =========================

        JPanel bottomPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        15,
                        5
                )
        );

        bottomPanel.setBackground(
                UITheme.BACKGROUND
        );

        JButton logoutButton =
                UITheme.createDangerButton(
                        "Logout"
                );

        bottomPanel.add(logoutButton);

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        // =========================
        // BUTTON ACTIONS
        // =========================

        booksButton.addActionListener(e -> {

            dispose();

            new BookFrame();
        });

        studentsButton.addActionListener(e -> {

            dispose();

            new StudentFrame();
        });

        issueButton.addActionListener(e -> {

            dispose();

            new IssueFrame();
        });

        returnButton.addActionListener(e -> {

            dispose();

            new ReturnFrame();
        });

        transactionsButton.addActionListener(e -> {

            dispose();

            new TransactionFrame();
        });

        refreshButton.addActionListener(
                e -> loadStatistics()
        );

        logoutButton.addActionListener(e -> {

            int choice =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to logout?",
                            "Confirm Logout",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

            if (choice == JOptionPane.YES_OPTION) {

                dispose();

                new LoginFrame();
            }
        });

        // =========================
        // LOAD STATISTICS
        // =========================

        loadStatistics();

        setVisible(true);
    }

    // =====================================================
    // CREATE STATISTIC CARD
    // =====================================================

    private JLabel createStatisticCard(
            JPanel parent,
            String title
    ) {

        JPanel card = new JPanel(
                new BorderLayout(5, 5)
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
                                15, 10, 15, 10
                        )
                )
        );

        JLabel titleLabel = new JLabel(
                title,
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                UITheme.LABEL_FONT
        );

        titleLabel.setForeground(
                UITheme.SECONDARY_TEXT
        );

        JLabel valueLabel = new JLabel(
                "0",
                SwingConstants.CENTER
        );

        valueLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        valueLabel.setForeground(
                UITheme.PRIMARY
        );

        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                valueLabel,
                BorderLayout.CENTER
        );

        parent.add(card);

        return valueLabel;
    }

    // =====================================================
    // LOAD DASHBOARD STATISTICS
    // =====================================================

    private void loadStatistics() {

        try {

            DashboardDAO dao =
                    new DashboardDAO();

            int totalBooks =
                    dao.getTotalBooks();

            int availableBooks =
                    dao.getAvailableBooks();

            int issuedBooks =
                    dao.getIssuedBooks();

            int totalStudents =
                    dao.getTotalStudents();

            totalBooksLabel.setText(
                    String.valueOf(totalBooks)
            );

            availableBooksLabel.setText(
                    String.valueOf(availableBooks)
            );

            issuedBooksLabel.setText(
                    String.valueOf(issuedBooks)
            );

            totalStudentsLabel.setText(
                    String.valueOf(totalStudents)
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load dashboard statistics.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }
}