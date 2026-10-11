package library.ui;

import library.DBConnection;
import library.dao.ReturnDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ReturnFrame extends JFrame {

    private JTextField issueIdField;
    private JTextField studentField;
    private JTextField rollNoField;
    private JTextField bookField;
    private JTextField dueDateField;
    private JTextField returnDateField;
    private JTextField lateDaysField;
    private JTextField fineField;

    private JTable issueTable;
    private DefaultTableModel tableModel;

    private int selectedIssueId = -1;

    private final ReturnDAO returnDAO = new ReturnDAO();

    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final double FINE_PER_DAY = 10.0;

    public ReturnFrame() {

        setTitle("Return Book");
        setSize(1150, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        UITheme.applyFrame(this);

        createUI();
        loadIssuedBooks();

        setVisible(true);
    }

    private void createUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(12, 12));

        mainPanel.setBackground(UITheme.BACKGROUND);

        mainPanel.setBorder(
                new EmptyBorder(10, 18, 12, 18)
        );

        // =====================================================
        // TITLE
        // =====================================================

        JLabel title =
                UITheme.createTitle("RETURN BOOK");

        mainPanel.add(title, BorderLayout.NORTH);

        // =====================================================
        // LEFT DETAILS PANEL
        // =====================================================

        JPanel detailsPanel = new JPanel();

        detailsPanel.setBackground(UITheme.CARD);

        detailsPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UITheme.BORDER
                        ),
                        new EmptyBorder(12, 14, 12, 14)
                )
        );

        detailsPanel.setPreferredSize(
                new Dimension(340, 0)
        );

        detailsPanel.setLayout(
                new BoxLayout(
                        detailsPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel detailsTitle =
                new JLabel("Return Details");

        detailsTitle.setFont(UITheme.HEADING_FONT);
        detailsTitle.setForeground(UITheme.PRIMARY_DARK);
        detailsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        detailsPanel.add(detailsTitle);

        detailsPanel.add(
                Box.createVerticalStrut(8)
        );

        // Issue ID
        addField(
                detailsPanel,
                "Issue ID",
                issueIdField = createReadOnlyField()
        );

        // Student
        addField(
                detailsPanel,
                "Student",
                studentField = createReadOnlyField()
        );

        // Roll Number
        addField(
                detailsPanel,
                "Roll Number",
                rollNoField = createReadOnlyField()
        );

        // Book
        addField(
                detailsPanel,
                "Book",
                bookField = createReadOnlyField()
        );

        // Due Date
        addField(
                detailsPanel,
                "Due Date",
                dueDateField = createReadOnlyField()
        );

        // Return Date
        addField(
                detailsPanel,
                "Return Date",
                returnDateField = UITheme.createTextField()
        );

        returnDateField.setText(
                LocalDate.now().format(formatter)
        );

        // Late Days
        addField(
                detailsPanel,
                "Late Days",
                lateDaysField = createReadOnlyField()
        );

        lateDaysField.setText("0");

        // Fine
        addField(
                detailsPanel,
                "Fine (₹)",
                fineField = createReadOnlyField()
        );

        fineField.setText("0.00");

        // =====================================================
        // FINE NOTE
        // =====================================================

        JLabel fineNote =
                new JLabel("Fine: ₹10 per late day");

        fineNote.setFont(
                new Font(
                        "Arial",
                        Font.ITALIC,
                        11
                )
        );

        fineNote.setForeground(
                UITheme.SECONDARY_TEXT
        );

        fineNote.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        detailsPanel.add(
                Box.createVerticalStrut(5)
        );

        detailsPanel.add(fineNote);

        detailsPanel.add(
                Box.createVerticalStrut(10)
        );

        // =====================================================
        // BUTTONS
        // =====================================================

        JButton returnButton =
                UITheme.createSuccessButton(
                        "RETURN BOOK"
                );

        JButton clearButton =
                UITheme.createSecondaryButton(
                        "CLEAR"
                );

        JButton refreshButton =
                UITheme.createPrimaryButton(
                        "REFRESH"
                );

        JButton backButton =
                UITheme.createSecondaryButton(
                        "← BACK"
                );

        Dimension buttonSize =
                new Dimension(
                        140,
                        38
                );

        returnButton.setPreferredSize(buttonSize);
        clearButton.setPreferredSize(buttonSize);
        refreshButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                7,
                                7
                        )
                );

        buttonPanel.setBackground(UITheme.CARD);

        buttonPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        83
                )
        );

        buttonPanel.add(returnButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        detailsPanel.add(buttonPanel);

        // =====================================================
        // RIGHT TABLE PANEL
        // =====================================================

        JPanel tablePanel =
                new JPanel(
                        new BorderLayout(8, 8)
                );

        tablePanel.setBackground(
                UITheme.BACKGROUND
        );

        JPanel tableHeader =
                new JPanel(
                        new BorderLayout()
                );

        tableHeader.setBackground(
                UITheme.CARD
        );

        tableHeader.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UITheme.BORDER
                        ),
                        new EmptyBorder(
                                8, 12, 8, 12
                        )
                )
        );

        JLabel tableTitle =
                new JLabel(
                        "Currently Issued Books"
                );

        tableTitle.setFont(
                UITheme.HEADING_FONT
        );

        tableTitle.setForeground(
                UITheme.PRIMARY_DARK
        );

        tableHeader.add(
                tableTitle,
                BorderLayout.WEST
        );

        tablePanel.add(
                tableHeader,
                BorderLayout.NORTH
        );

        // =====================================================
        // TABLE
        // =====================================================

        String[] columns = {
                "Issue ID",
                "Student",
                "Roll No",
                "Book",
                "Issue Date",
                "Due Date"
        };

        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        issueTable =
                new JTable(tableModel);

        UITheme.styleTable(issueTable);

        issueTable.setAutoCreateRowSorter(true);

        issueTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(issueTable);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        UITheme.BORDER
                )
        );

        tablePanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =====================================================
        // MAIN LAYOUT
        // =====================================================

        mainPanel.add(
                detailsPanel,
                BorderLayout.WEST
        );

        mainPanel.add(
                tablePanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        // =====================================================
        // BUTTON ACTIONS
        // =====================================================

        returnButton.addActionListener(
                e -> returnBook()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        refreshButton.addActionListener(
                e -> loadIssuedBooks()
        );

        backButton.addActionListener(e -> {

            dispose();
            new AdminDashboard();

        });

        issueTable.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        int selectedRow =
                                issueTable.getSelectedRow();

                        if (selectedRow >= 0) {

                            int modelRow =
                                    issueTable.convertRowIndexToModel(
                                            selectedRow
                                    );

                            loadSelectedIssue(modelRow);
                        }
                    }
                });

        returnDateField.addActionListener(
                e -> calculateFine()
        );
    }

    // =========================================================
    // ADD FIELD TO DETAILS PANEL
    // =========================================================

    private void addField(
            JPanel panel,
            String labelText,
            JTextField field) {

        JLabel label =
                UITheme.createLabel(labelText);

        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        panel.add(label);

        panel.add(
                Box.createVerticalStrut(2)
        );

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        32
                )
        );

        panel.add(field);

        panel.add(
                Box.createVerticalStrut(5)
        );
    }

    // =========================================================
    // READ-ONLY FIELD
    // =========================================================

    private JTextField createReadOnlyField() {

        JTextField field =
                UITheme.createTextField();

        field.setEditable(false);

        field.setBackground(
                new Color(250, 238, 238)
        );

        return field;
    }

    // =========================================================
    // LOAD ISSUED BOOKS
    // =========================================================

    private void loadIssuedBooks() {

        tableModel.setRowCount(0);

        String sql =
                "SELECT ib.issue_id, " +
                "s.name AS student_name, " +
                "s.roll_no, " +
                "b.book_name, " +
                "ib.issue_date, " +
                "ib.due_date " +
                "FROM issued_books ib " +
                "JOIN students s " +
                "ON ib.student_id = s.student_id " +
                "JOIN books b " +
                "ON ib.book_id = b.book_id " +
                "WHERE ib.status = 'ISSUED' " +
                "ORDER BY ib.issue_id DESC";

        try (
                Connection conn =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                tableModel.addRow(
                        new Object[]{
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
                                )
                        }
                );
            }

            clearFields();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load issued books.\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // LOAD SELECTED ISSUE
    // =========================================================

    private void loadSelectedIssue(int row) {

        selectedIssueId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(row, 0)
                                .toString()
                );

        issueIdField.setText(
                tableModel
                        .getValueAt(row, 0)
                        .toString()
        );

        studentField.setText(
                tableModel
                        .getValueAt(row, 1)
                        .toString()
        );

        rollNoField.setText(
                tableModel
                        .getValueAt(row, 2)
                        .toString()
        );

        bookField.setText(
                tableModel
                        .getValueAt(row, 3)
                        .toString()
        );

        dueDateField.setText(
                tableModel
                        .getValueAt(row, 5)
                        .toString()
        );

        returnDateField.setText(
                LocalDate.now().format(formatter)
        );

        calculateFine();
    }

    // =========================================================
    // CALCULATE FINE
    // =========================================================

    private void calculateFine() {

        if (selectedIssueId == -1) {
            return;
        }

        try {

            LocalDate dueDate =
                    LocalDate.parse(
                            dueDateField.getText().trim(),
                            formatter
                    );

            LocalDate returnDate =
                    LocalDate.parse(
                            returnDateField.getText().trim(),
                            formatter
                    );

            if (returnDate.isBefore(dueDate)) {

                lateDaysField.setText("0");
                fineField.setText("0.00");

                return;
            }

            long days =
                    java.time.temporal.ChronoUnit.DAYS.between(
                            dueDate,
                            returnDate
                    );

            int lateDays =
                    (int) days;

            double fine =
                    lateDays * FINE_PER_DAY;

            lateDaysField.setText(
                    String.valueOf(lateDays)
            );

            fineField.setText(
                    String.format(
                            "%.2f",
                            fine
                    )
            );

        } catch (DateTimeParseException e) {

            lateDaysField.setText("0");
            fineField.setText("0.00");
        }
    }

    // =========================================================
    // RETURN BOOK
    // =========================================================

    private void returnBook() {

        if (selectedIssueId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an issued book from the table first.",
                    "No Book Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String returnDate =
                returnDateField.getText().trim();

        try {

            LocalDate.parse(
                    returnDate,
                    formatter
            );

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid return date.\n\n"
                            + "Use YYYY-MM-DD\n\n"
                            + "Example: 2026-10-06",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        calculateFine();

        int lateDays;

        double fine;

        try {

            lateDays =
                    Integer.parseInt(
                            lateDaysField.getText()
                    );

            fine =
                    Double.parseDouble(
                            fineField.getText()
                    );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to calculate fine.",
                    "Calculation Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Student:\n"
                                + studentField.getText()
                                + " ("
                                + rollNoField.getText()
                                + ")\n\n"
                                + "Book:\n"
                                + bookField.getText()
                                + "\n\n"
                                + "Return Date:\n"
                                + returnDate
                                + "\n\n"
                                + "Late Days: "
                                + lateDays
                                + "\n"
                                + "Fine: ₹"
                                + String.format(
                                        "%.2f",
                                        fine
                                )
                                + "\n\n"
                                + "Confirm book return?",
                        "Confirm Return",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean success =
                returnDAO.returnBook(
                        selectedIssueId,
                        returnDate,
                        lateDays,
                        fine
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Book returned successfully!\n\n"
                            + "Late Days: "
                            + lateDays
                            + "\n"
                            + "Fine: ₹"
                            + String.format(
                                    "%.2f",
                                    fine
                            ),
                    "Return Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadIssuedBooks();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to return the book.\n\n"
                            + "The transaction may already have been completed.",
                    "Return Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // CLEAR
    // =========================================================

    private void clearFields() {

        selectedIssueId = -1;

        issueIdField.setText("");
        studentField.setText("");
        rollNoField.setText("");
        bookField.setText("");
        dueDateField.setText("");

        returnDateField.setText(
                LocalDate.now().format(formatter)
        );

        lateDaysField.setText("0");
        fineField.setText("0.00");

        issueTable.clearSelection();
    }
}