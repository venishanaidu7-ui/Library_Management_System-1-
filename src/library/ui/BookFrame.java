package library.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import library.dao.BookDAO;
import library.model.Book;

public class BookFrame extends JFrame {

    private JTextField bookNameField;
    private JTextField authorField;
    private JTextField categoryField;
    private JTextField publisherField;
    private JTextField quantityField;
    private JTextField availableField;
    private JTextField searchField;

    private JTable bookTable;
    private DefaultTableModel tableModel;

    private int selectedBookId = -1;
    private int selectedOldQuantity = 0;
    private int selectedOldAvailable = 0;

    private BookDAO bookDAO;

    public BookFrame() {

        bookDAO = new BookDAO();

        setTitle("Book Management");
        setSize(1150, 720);
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
                        15, 20, 20, 20
                )
        );

        // =========================
        // TITLE
        // =========================

        JLabel title =
                UITheme.createTitle(
                        "BOOK MANAGEMENT"
                );

        mainPanel.add(
                title,
                BorderLayout.NORTH
        );

        // =========================
        // TOP CONTENT
        // =========================

        JPanel topPanel = new JPanel(
                new BorderLayout(15, 10)
        );

        topPanel.setBackground(
                UITheme.BACKGROUND
        );

        // =========================
        // DETAILS CARD
        // =========================

        JPanel detailsPanel = new JPanel(
                new GridBagLayout()
        );

        detailsPanel.setBackground(
                UITheme.CARD
        );

        detailsPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UITheme.BORDER
                        ),
                        new EmptyBorder(
                                15, 15, 15, 15
                        )
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(5, 6, 5, 6);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1.0;

        // Book Name
        gbc.gridx = 0;
        gbc.gridy = 0;

        detailsPanel.add(
                UITheme.createLabel("Book Name:"),
                gbc
        );

        bookNameField =
                UITheme.createTextField();

        gbc.gridx = 1;

        detailsPanel.add(
                bookNameField,
                gbc
        );

        // Author
        gbc.gridx = 2;

        detailsPanel.add(
                UITheme.createLabel("Author:"),
                gbc
        );

        authorField =
                UITheme.createTextField();

        gbc.gridx = 3;

        detailsPanel.add(
                authorField,
                gbc
        );

        // Category
        gbc.gridx = 0;
        gbc.gridy = 1;

        detailsPanel.add(
                UITheme.createLabel("Category:"),
                gbc
        );

        categoryField =
                UITheme.createTextField();

        gbc.gridx = 1;

        detailsPanel.add(
                categoryField,
                gbc
        );

        // Publisher
        gbc.gridx = 2;

        detailsPanel.add(
                UITheme.createLabel("Publisher:"),
                gbc
        );

        publisherField =
                UITheme.createTextField();

        gbc.gridx = 3;

        detailsPanel.add(
                publisherField,
                gbc
        );

        // Quantity
        gbc.gridx = 0;
        gbc.gridy = 2;

        detailsPanel.add(
                UITheme.createLabel("Quantity:"),
                gbc
        );

        quantityField =
                UITheme.createTextField();

        gbc.gridx = 1;

        detailsPanel.add(
                quantityField,
                gbc
        );

        // Available
        gbc.gridx = 2;

        detailsPanel.add(
                UITheme.createLabel(
                        "Available:"
                ),
                gbc
        );

        availableField =
                UITheme.createTextField();

        availableField.setEditable(false);

        gbc.gridx = 3;

        detailsPanel.add(
                availableField,
                gbc
        );

        topPanel.add(
                detailsPanel,
                BorderLayout.CENTER
        );

        // =========================
        // BUTTON PANEL
        // =========================

        JPanel buttonPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        8,
                        5
                )
        );

        buttonPanel.setBackground(
                UITheme.BACKGROUND
        );

        JButton addButton =
                UITheme.createSuccessButton(
                        "Add Book"
                );

        JButton updateButton =
                UITheme.createPrimaryButton(
                        "Update"
                );

        JButton deleteButton =
                UITheme.createDangerButton(
                        "Delete"
                );

        JButton clearButton =
                UITheme.createSecondaryButton(
                        "Clear"
                );

        JButton backButton =
                UITheme.createSecondaryButton(
                        "Back"
                );

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);

        topPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        mainPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        // =========================
        // SEARCH PANEL
        // =========================

        JPanel searchPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        10,
                        8
                )
        );

        searchPanel.setBackground(
                UITheme.BACKGROUND
        );

        JLabel searchLabel =
                UITheme.createLabel(
                        "Search Book:"
                );

        searchField =
                UITheme.createTextField();

        searchField.setPreferredSize(
                new Dimension(300, 35)
        );

        JButton searchButton =
                UITheme.createPrimaryButton(
                        "Search"
                );

        JButton showAllButton =
                UITheme.createSecondaryButton(
                        "Show All"
                );

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);

        // =========================
        // TABLE
        // =========================

        tableModel = new DefaultTableModel(
                new String[]{
                        "Book ID",
                        "Book Name",
                        "Author",
                        "Category",
                        "Publisher",
                        "Quantity",
                        "Available"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        bookTable =
                new JTable(tableModel);

        UITheme.styleTable(bookTable);

        bookTable.setAutoCreateRowSorter(true);

        JScrollPane scrollPane =
                new JScrollPane(bookTable);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        UITheme.BORDER
                )
        );

        JPanel tablePanel = new JPanel(
                new BorderLayout(5, 5)
        );

        tablePanel.setBackground(
                UITheme.BACKGROUND
        );

        tablePanel.add(
                searchPanel,
                BorderLayout.NORTH
        );

        tablePanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        mainPanel.add(
                tablePanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        // =========================
        // ADD BUTTON
        // =========================

        addButton.addActionListener(
                e -> addBook()
        );

        // =========================
        // UPDATE BUTTON
        // =========================

        updateButton.addActionListener(
                e -> updateBook()
        );

        // =========================
        // DELETE BUTTON
        // =========================

        deleteButton.addActionListener(
                e -> deleteBook()
        );

        // =========================
        // CLEAR BUTTON
        // =========================

        clearButton.addActionListener(
                e -> clearFields()
        );

        // =========================
        // BACK BUTTON
        // =========================

        backButton.addActionListener(e -> {

            dispose();

            new AdminDashboard();
        });

        // =========================
        // SEARCH BUTTON
        // =========================

        searchButton.addActionListener(
                e -> searchBooks()
        );

        // =========================
        // SHOW ALL
        // =========================

        showAllButton.addActionListener(
                e -> loadBooks()
        );

        // =========================
        // TABLE ROW CLICK
        // =========================

        bookTable.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e
                    ) {

                        int viewRow =
                                bookTable.getSelectedRow();

                        if (viewRow == -1) {
                            return;
                        }

                        int row =
                                bookTable.convertRowIndexToModel(
                                        viewRow
                                );

                        selectedBookId =
                                Integer.parseInt(
                                        tableModel
                                                .getValueAt(
                                                        row,
                                                        0
                                                )
                                                .toString()
                                );

                        bookNameField.setText(
                                tableModel
                                        .getValueAt(
                                                row,
                                                1
                                        )
                                        .toString()
                        );

                        authorField.setText(
                                tableModel
                                        .getValueAt(
                                                row,
                                                2
                                        )
                                        .toString()
                        );

                        categoryField.setText(
                                tableModel
                                        .getValueAt(
                                                row,
                                                3
                                        )
                                        .toString()
                        );

                        publisherField.setText(
                                tableModel
                                        .getValueAt(
                                                row,
                                                4
                                        )
                                        .toString()
                        );

                        selectedOldQuantity =
                                Integer.parseInt(
                                        tableModel
                                                .getValueAt(
                                                        row,
                                                        5
                                                )
                                                .toString()
                                );

                        selectedOldAvailable =
                                Integer.parseInt(
                                        tableModel
                                                .getValueAt(
                                                        row,
                                                        6
                                                )
                                                .toString()
                                );

                        quantityField.setText(
                                String.valueOf(
                                        selectedOldQuantity
                                )
                        );

                        availableField.setText(
                                String.valueOf(
                                        selectedOldAvailable
                                )
                        );
                    }
                }
        );

        // =========================
        // INITIAL LOAD
        // =========================

        loadBooks();

        setVisible(true);
    }

    // =====================================================
    // ADD BOOK
    // =====================================================

    private void addBook() {

        String bookName =
                bookNameField.getText().trim();

        String author =
                authorField.getText().trim();

        String category =
                categoryField.getText().trim();

        String publisher =
                publisherField.getText().trim();

        String quantityText =
                quantityField.getText().trim();

        if (bookName.isEmpty()
                || author.isEmpty()
                || category.isEmpty()
                || publisher.isEmpty()
                || quantityText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all book details.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (!isValidText(bookName)
                || !isValidText(author)
                || !isValidText(category)
                || !isValidText(publisher)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Book details contain invalid characters.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int quantity;

        try {

            quantity =
                    Integer.parseInt(
                            quantityText
                    );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Quantity must be a valid number.",
                    "Invalid Quantity",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (quantity < 1
                || quantity > 10000) {

            JOptionPane.showMessageDialog(
                    this,
                    "Quantity must be between 1 and 10000.",
                    "Invalid Quantity",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Add this book to the library?",
                        "Confirm Add",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        Book book =
                new Book(
                        bookName,
                        author,
                        category,
                        publisher,
                        quantity,
                        quantity
                );

        if (bookDAO.addBook(book)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Book added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();
            loadBooks();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to add the book.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // UPDATE BOOK
    // =====================================================

    private void updateBook() {

        if (selectedBookId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a book from the table.",
                    "No Book Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String bookName =
                bookNameField.getText().trim();

        String author =
                authorField.getText().trim();

        String category =
                categoryField.getText().trim();

        String publisher =
                publisherField.getText().trim();

        String quantityText =
                quantityField.getText().trim();

        if (bookName.isEmpty()
                || author.isEmpty()
                || category.isEmpty()
                || publisher.isEmpty()
                || quantityText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all book details.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (!isValidText(bookName)
                || !isValidText(author)
                || !isValidText(category)
                || !isValidText(publisher)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Book details contain invalid characters.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int newQuantity;

        try {

            newQuantity =
                    Integer.parseInt(
                            quantityText
                    );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Quantity must be a valid number.",
                    "Invalid Quantity",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (newQuantity < 1
                || newQuantity > 10000) {

            JOptionPane.showMessageDialog(
                    this,
                    "Quantity must be between 1 and 10000.",
                    "Invalid Quantity",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int issuedCopies =
                selectedOldQuantity
                        - selectedOldAvailable;

        if (newQuantity < issuedCopies) {

            JOptionPane.showMessageDialog(
                    this,
                    "Quantity cannot be less than "
                    + "the number of currently issued copies.\n\n"
                    + "Currently issued: "
                    + issuedCopies,
                    "Invalid Quantity",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int newAvailable =
                newQuantity - issuedCopies;

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Update this book?",
                        "Confirm Update",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        Book book =
                new Book(
                        bookName,
                        author,
                        category,
                        publisher,
                        newQuantity,
                        newAvailable
                );

        book.setBookId(
                selectedBookId
        );

        if (bookDAO.updateBook(book)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Book updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();
            loadBooks();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update the book.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // DELETE BOOK
    // =====================================================

    private void deleteBook() {

        if (selectedBookId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a book from the table.",
                    "No Book Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this book?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        if (bookDAO.deleteBook(
                selectedBookId
        )) {

            JOptionPane.showMessageDialog(
                    this,
                    "Book deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();
            loadBooks();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to delete the book.\n"
                    + "It may be associated with transactions.",
                    "Delete Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // SEARCH BOOKS
    // =====================================================

    private void searchBooks() {

        String keyword =
                searchField.getText().trim();

        if (keyword.isEmpty()) {

            loadBooks();

            return;
        }

        List<Book> books =
                bookDAO.searchBooks(keyword);

        tableModel.setRowCount(0);

        for (Book book : books) {

            tableModel.addRow(
                    new Object[]{
                            book.getBookId(),
                            book.getBookName(),
                            book.getAuthor(),
                            book.getCategory(),
                            book.getPublisher(),
                            book.getQuantity(),
                            book.getAvailableQuantity()
                    }
            );
        }

        if (books.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No books found.",
                    "Search Result",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // =====================================================
    // LOAD BOOKS
    // =====================================================

    private void loadBooks() {

        tableModel.setRowCount(0);

        List<Book> books =
                bookDAO.getAllBooks();

        for (Book book : books) {

            tableModel.addRow(
                    new Object[]{
                            book.getBookId(),
                            book.getBookName(),
                            book.getAuthor(),
                            book.getCategory(),
                            book.getPublisher(),
                            book.getQuantity(),
                            book.getAvailableQuantity()
                    }
            );
        }
    }

    // =====================================================
    // CLEAR FIELDS
    // =====================================================

    private void clearFields() {

        bookNameField.setText("");
        authorField.setText("");
        categoryField.setText("");
        publisherField.setText("");
        quantityField.setText("");
        availableField.setText("");

        searchField.setText("");

        selectedBookId = -1;
        selectedOldQuantity = 0;
        selectedOldAvailable = 0;

        bookTable.clearSelection();
    }

    // =====================================================
    // TEXT VALIDATION
    // =====================================================

    private boolean isValidText(
            String text
    ) {

        return text.matches(
                "[a-zA-Z0-9 .,'&+()\\-]+"
        );
    }
}