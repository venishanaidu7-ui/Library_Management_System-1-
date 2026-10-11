package library.ui;

import library.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class IssueFrame extends JFrame {

    private JComboBox<StudentItem> studentCombo;
    private JComboBox<BookItem> bookCombo;

    private JTextField issueDateField;
    private JTextField dueDateField;

    private JButton issueButton;
    private JButton clearButton;
    private JButton refreshButton;
    private JButton backButton;

    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public IssueFrame() {

        setTitle("Issue Book");
        setSize(720, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        UITheme.applyFrame(this);

        createUI();

        loadStudents();
        loadBooks();

        setVisible(true);
    }

    // =========================================================
    // CREATE UI
    // =========================================================

    private void createUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.setBackground(UITheme.BACKGROUND);

        mainPanel.setBorder(
                new EmptyBorder(20, 25, 20, 25)
        );

        // =====================================================
        // TITLE
        // =====================================================

        JLabel title =
                UITheme.createTitle("ISSUE BOOK");

        mainPanel.add(
                title,
                BorderLayout.NORTH
        );

        // =====================================================
        // FORM CARD
        // =====================================================

        JPanel card =
                new JPanel();

        card.setBackground(UITheme.CARD);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UITheme.BORDER
                        ),
                        new EmptyBorder(
                                25, 30, 25, 30
                        )
                )
        );

        card.setLayout(
                new GridBagLayout()
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(8, 8, 8, 8);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1;

        // =====================================================
        // STUDENT
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;

        JLabel studentLabel =
                UITheme.createLabel("Student");

        card.add(studentLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        studentCombo =
                new JComboBox<>();

        studentCombo.setFont(
                UITheme.NORMAL_FONT
        );

        studentCombo.setBackground(
                UITheme.CARD
        );

        studentCombo.setForeground(
                UITheme.TEXT
        );

        studentCombo.setPreferredSize(
                new Dimension(380, 38)
        );

        card.add(studentCombo, gbc);

        // =====================================================
        // BOOK
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;

        JLabel bookLabel =
                UITheme.createLabel("Book");

        card.add(bookLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;

        bookCombo =
                new JComboBox<>();

        bookCombo.setFont(
                UITheme.NORMAL_FONT
        );

        bookCombo.setBackground(
                UITheme.CARD
        );

        bookCombo.setForeground(
                UITheme.TEXT
        );

        bookCombo.setPreferredSize(
                new Dimension(380, 38)
        );

        card.add(bookCombo, gbc);

        // =====================================================
        // ISSUE DATE
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;

        JLabel issueDateLabel =
                UITheme.createLabel(
                        "Issue Date"
                );

        card.add(issueDateLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;

        issueDateField =
                UITheme.createTextField();

        issueDateField.setText(
                LocalDate.now().format(formatter)
        );

        issueDateField.setPreferredSize(
                new Dimension(380, 38)
        );

        card.add(issueDateField, gbc);

        // =====================================================
        // DUE DATE
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;

        JLabel dueDateLabel =
                UITheme.createLabel(
                        "Due Date"
                );

        card.add(dueDateLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;

        dueDateField =
                UITheme.createTextField();

        dueDateField.setPreferredSize(
                new Dimension(380, 38)
        );

        card.add(dueDateField, gbc);

        // =====================================================
        // DATE FORMAT NOTE
        // =====================================================

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;

        JLabel dateNote =
                new JLabel(
                        "Date format: YYYY-MM-DD"
                );

        dateNote.setFont(
                new Font(
                        "Arial",
                        Font.ITALIC,
                        11
                )
        );

        dateNote.setForeground(
                UITheme.SECONDARY_TEXT
        );

        card.add(dateNote, gbc);

        // =====================================================
        // BUTTONS
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                10,
                                0
                        )
                );

        buttonPanel.setBackground(
                UITheme.CARD
        );

        issueButton =
                UITheme.createSuccessButton(
                        "ISSUE BOOK"
                );

        clearButton =
                UITheme.createSecondaryButton(
                        "CLEAR"
                );

        refreshButton =
                UITheme.createPrimaryButton(
                        "REFRESH"
                );

        backButton =
                UITheme.createSecondaryButton(
                        "← BACK"
                );

        buttonPanel.add(issueButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        card.add(buttonPanel, gbc);

        mainPanel.add(
                card,
                BorderLayout.CENTER
        );

        add(mainPanel);

        // =====================================================
        // ACTIONS
        // =====================================================

        issueButton.addActionListener(e ->
                issueBook()
        );

        clearButton.addActionListener(e ->
                clearFields()
        );

        refreshButton.addActionListener(e -> {

            loadStudents();
            loadBooks();

        });

        backButton.addActionListener(e -> {

            dispose();
            new AdminDashboard();

        });
    }

    // =========================================================
    // LOAD STUDENTS
    // =========================================================

    private void loadStudents() {

        studentCombo.removeAllItems();

        String sql =
                "SELECT student_id, name, roll_no " +
                "FROM students " +
                "ORDER BY name";

        try (
                Connection conn =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                int id =
                        rs.getInt("student_id");

                String name =
                        rs.getString("name");

                String rollNo =
                        rs.getString("roll_no");

                studentCombo.addItem(
                        new StudentItem(
                                id,
                                name,
                                rollNo
                        )
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load students.\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // LOAD BOOKS
    // =========================================================

    private void loadBooks() {

        bookCombo.removeAllItems();

        String sql =
                "SELECT book_id, book_name, author, " +
                "available_quantity " +
                "FROM books " +
                "WHERE available_quantity > 0 " +
                "ORDER BY book_name";

        try (
                Connection conn =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                int id =
                        rs.getInt("book_id");

                String bookName =
                        rs.getString("book_name");

                String author =
                        rs.getString("author");

                int available =
                        rs.getInt(
                                "available_quantity"
                        );

                bookCombo.addItem(
                        new BookItem(
                                id,
                                bookName,
                                author,
                                available
                        )
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load books.\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // ISSUE BOOK
    // =========================================================

    private void issueBook() {

        StudentItem student =
                (StudentItem)
                        studentCombo.getSelectedItem();

        BookItem book =
                (BookItem)
                        bookCombo.getSelectedItem();

        if (student == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a student.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (book == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a book.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String issueDate =
                issueDateField.getText().trim();

        String dueDate =
                dueDateField.getText().trim();

        // =====================================================
        // VALIDATE DATES
        // =====================================================

        LocalDate issue;

        LocalDate due;

        try {

            issue =
                    LocalDate.parse(
                            issueDate,
                            formatter
                    );

            due =
                    LocalDate.parse(
                            dueDate,
                            formatter
                    );

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid date format.\n\n"
                            + "Please use:\n"
                            + "YYYY-MM-DD\n\n"
                            + "Example:\n"
                            + "2026-10-06",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (due.isBefore(issue)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Due date cannot be before issue date.",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =====================================================
        // CONFIRMATION
        // =====================================================

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Student:\n"
                                + student.name
                                + " ("
                                + student.rollNo
                                + ")\n\n"
                                + "Book:\n"
                                + book.bookName
                                + "\n\n"
                                + "Issue Date:\n"
                                + issueDate
                                + "\n\n"
                                + "Due Date:\n"
                                + dueDate
                                + "\n\n"
                                + "Issue this book?",
                        "Confirm Book Issue",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm !=
                JOptionPane.YES_OPTION) {

            return;
        }

        // =====================================================
        // DATABASE OPERATION
        // =====================================================

        library.dao.IssueDAO issueDAO =
                new library.dao.IssueDAO();

        boolean success =
                issueDAO.issueBook(
                        student.studentId,
                        book.bookId,
                        issueDate,
                        dueDate
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Book issued successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

            loadStudents();
            loadBooks();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to issue the book.\n\n"
                            + "Possible reasons:\n"
                            + "• Book is no longer available\n"
                            + "• Student already has this book issued",
                    "Issue Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // CLEAR
    // =========================================================

    private void clearFields() {

        if (studentCombo.getItemCount() > 0) {
            studentCombo.setSelectedIndex(0);
        }

        if (bookCombo.getItemCount() > 0) {
            bookCombo.setSelectedIndex(0);
        }

        issueDateField.setText(
                LocalDate.now().format(formatter)
        );

        dueDateField.setText("");

        dueDateField.requestFocus();
    }

    // =========================================================
    // STUDENT ITEM
    // =========================================================

    private static class StudentItem {

        private final int studentId;
        private final String name;
        private final String rollNo;

        public StudentItem(
                int studentId,
                String name,
                String rollNo) {

            this.studentId = studentId;
            this.name = name;
            this.rollNo = rollNo;
        }

        @Override
        public String toString() {

            return name
                    + " - "
                    + rollNo;
        }
    }

    // =========================================================
    // BOOK ITEM
    // =========================================================

    private static class BookItem {

        private final int bookId;
        private final String bookName;
        private final String author;
        private final int availableQuantity;

        public BookItem(
                int bookId,
                String bookName,
                String author,
                int availableQuantity) {

            this.bookId = bookId;
            this.bookName = bookName;
            this.author = author;
            this.availableQuantity =
                    availableQuantity;
        }

        @Override
        public String toString() {

            return bookName
                    + " - "
                    + author
                    + " (Available: "
                    + availableQuantity
                    + ")";
        }
    }
}