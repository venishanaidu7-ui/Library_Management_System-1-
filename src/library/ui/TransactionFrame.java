package library.ui;

import library.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TransactionFrame extends JFrame {

    private JTextField searchField;
    private JTable transactionTable;
    private DefaultTableModel tableModel;

    public TransactionFrame() {

        setTitle("Transaction History");
        setSize(1150, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        UITheme.applyFrame(this);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(UITheme.BACKGROUND);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );

        // =========================
        // TITLE
        // =========================

        JLabel title = UITheme.createTitle("TRANSACTION HISTORY");
        mainPanel.add(title, BorderLayout.NORTH);

        // =========================
        // SEARCH PANEL
        // =========================

        JPanel searchPanel = new JPanel(new FlowLayout(
                FlowLayout.LEFT, 10, 10
        ));

        searchPanel.setBackground(UITheme.CARD);
        searchPanel.setBorder(
                BorderFactory.createLineBorder(UITheme.BORDER)
        );

        JLabel searchLabel =
                UITheme.createLabel("Search:");

        searchField =
                UITheme.createTextField();

        searchField.setPreferredSize(
                new Dimension(280, 35)
        );

        JButton searchButton =
                UITheme.createPrimaryButton("SEARCH");

        JButton showAllButton =
                UITheme.createSecondaryButton("SHOW ALL");

        JButton refreshButton =
                UITheme.createSecondaryButton("REFRESH");

        JButton backButton =
                UITheme.createSecondaryButton("← BACK");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);
        searchPanel.add(refreshButton);
        searchPanel.add(backButton);

        mainPanel.add(searchPanel, BorderLayout.PAGE_START);

        // =========================
        // TABLE
        // =========================

        String[] columns = {
                "Issue ID",
                "Student",
                "Roll No",
                "Book",
                "Issue Date",
                "Due Date",
                "Return Date",
                "Status",
                "Late Days",
                "Fine (₹)"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column) {
                return false;
            }
        };

        transactionTable =
                new JTable(tableModel);

        UITheme.styleTable(transactionTable);

        transactionTable.setAutoCreateRowSorter(true);

        JScrollPane scrollPane =
                new JScrollPane(transactionTable);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        UITheme.BORDER
                )
        );

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =========================
        // FOOTER
        // =========================

        JLabel footer =
                new JLabel(
                        "Transaction history of all issued and returned books.",
                        SwingConstants.CENTER
                );

        footer.setFont(UITheme.NORMAL_FONT);
        footer.setForeground(
                UITheme.SECONDARY_TEXT
        );

        mainPanel.add(
                footer,
                BorderLayout.SOUTH
        );

        // =========================
        // BUTTON ACTIONS
        // =========================

        searchButton.addActionListener(e ->
                loadTransactions(
                        searchField.getText().trim()
                )
        );

        showAllButton.addActionListener(e -> {
            searchField.setText("");
            loadTransactions("");
        });

        refreshButton.addActionListener(e ->
                loadTransactions(
                        searchField.getText().trim()
                )
        );

        backButton.addActionListener(e -> {

            dispose();
            new AdminDashboard();
        });

        // Press Enter to search
        searchField.addActionListener(e ->
                loadTransactions(
                        searchField.getText().trim()
                )
        );

        // =========================
        // LOAD DATA
        // =========================

        loadTransactions("");

        add(mainPanel);
        setVisible(true);
    }

    // =====================================================
    // LOAD TRANSACTIONS
    // =====================================================

    private void loadTransactions(String keyword) {

        tableModel.setRowCount(0);

        String sql =
                "SELECT " +
                "ib.issue_id, " +
                "s.name AS student_name, " +
                "s.roll_no, " +
                "b.book_name, " +
                "ib.issue_date, " +
                "ib.due_date, " +
                "r.return_date, " +
                "ib.status, " +
                "COALESCE(r.late_days, 0) AS late_days, " +
                "COALESCE(r.fine, 0) AS fine " +
                "FROM issued_books ib " +
                "JOIN students s " +
                "ON ib.student_id = s.student_id " +
                "JOIN books b " +
                "ON ib.book_id = b.book_id " +
                "LEFT JOIN returns r " +
                "ON ib.issue_id = r.issue_id ";

        // =================================================
        // IMPORTANT:
        // If keyword exists, we add 3 ? parameters.
        // =================================================

        boolean searching =
                keyword != null &&
                !keyword.isEmpty();

        if (searching) {

            sql +=
                    "WHERE s.name LIKE ? " +
                    "OR s.roll_no LIKE ? " +
                    "OR b.book_name LIKE ";
            
            sql += "? ";
        }

        sql +=
                "ORDER BY ib.issue_id DESC";

        try (Connection con =
                     DBConnection.getConnection();

             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            // =================================================
            // THIS IS THE IMPORTANT FIX
            // =================================================

            if (searching) {

                String searchValue =
                        "%" + keyword + "%";

                ps.setString(1, searchValue);
                ps.setString(2, searchValue);
                ps.setString(3, searchValue);
            }

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Object[] row = {

                        rs.getInt("issue_id"),

                        rs.getString(
                                "student_name"
                        ),

                        rs.getString(
                                "roll_no"
                        ),

                        rs.getString(
                                "book_name"
                        ),

                        rs.getDate(
                                "issue_date"
                        ),

                        rs.getDate(
                                "due_date"
                        ),

                        rs.getDate(
                                "return_date"
                        ),

                        rs.getString(
                                "status"
                        ),

                        rs.getInt(
                                "late_days"
                        ),

                        rs.getDouble(
                                "fine"
                        )
                };

                tableModel.addRow(row);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load transaction history.\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}