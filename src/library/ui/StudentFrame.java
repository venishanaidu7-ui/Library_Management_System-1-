package library.ui;

import library.dao.StudentDAO;
import library.model.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class StudentFrame extends JFrame {

    private JTextField nameField;
    private JTextField rollField;
    private JTextField departmentField;
    private JTextField yearField;
    private JTextField phoneField;
    private JTextField searchField;

    private JTable studentTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    private int selectedStudentId = -1;

    private final StudentDAO studentDAO = new StudentDAO();

    public StudentFrame() {

        setTitle("Student Management");
        setSize(1150, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        UITheme.applyFrame(this);

        createUI();

        loadStudents();

        setVisible(true);
    }

    private void createUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(UITheme.BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // =========================
        // TITLE
        // =========================

        JLabel title =
                UITheme.createTitle("STUDENT MANAGEMENT");

        mainPanel.add(title, BorderLayout.NORTH);

        // =========================
        // LEFT PANEL
        // =========================

        JPanel formPanel = new JPanel();
        formPanel.setBackground(UITheme.CARD);
        formPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UITheme.BORDER),
                        new EmptyBorder(15, 15, 15, 15)
                )
        );

        formPanel.setPreferredSize(new Dimension(330, 0));

        formPanel.setLayout(
                new BoxLayout(formPanel, BoxLayout.Y_AXIS)
        );

        JLabel detailsLabel =
                new JLabel("Student Details");

        detailsLabel.setFont(UITheme.HEADING_FONT);
        detailsLabel.setForeground(UITheme.PRIMARY_DARK);
        detailsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(detailsLabel);
        formPanel.add(Box.createVerticalStrut(18));

        // Name
        formPanel.add(UITheme.createLabel("Student Name"));
        formPanel.add(Box.createVerticalStrut(5));

        nameField = UITheme.createTextField();
        nameField.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 38)
        );

        formPanel.add(nameField);
        formPanel.add(Box.createVerticalStrut(12));

        // Roll No
        formPanel.add(UITheme.createLabel("Roll Number"));
        formPanel.add(Box.createVerticalStrut(5));

        rollField = UITheme.createTextField();
        rollField.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 38)
        );

        formPanel.add(rollField);
        formPanel.add(Box.createVerticalStrut(12));

        // Department
        formPanel.add(UITheme.createLabel("Department"));
        formPanel.add(Box.createVerticalStrut(5));

        departmentField = UITheme.createTextField();
        departmentField.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 38)
        );

        formPanel.add(departmentField);
        formPanel.add(Box.createVerticalStrut(12));

        // Year
        formPanel.add(UITheme.createLabel("Year"));
        formPanel.add(Box.createVerticalStrut(5));

        yearField = UITheme.createTextField();
        yearField.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 38)
        );

        formPanel.add(yearField);
        formPanel.add(Box.createVerticalStrut(12));

        // Phone
        formPanel.add(UITheme.createLabel("Phone Number"));
        formPanel.add(Box.createVerticalStrut(5));

        phoneField = UITheme.createTextField();
        phoneField.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 38)
        );

        formPanel.add(phoneField);
        formPanel.add(Box.createVerticalStrut(18));

        // Buttons
        JPanel buttonPanel =
                new JPanel(new GridLayout(2, 2, 8, 8));

        buttonPanel.setBackground(UITheme.CARD);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton addButton =
                UITheme.createSuccessButton("ADD");

        JButton updateButton =
                UITheme.createPrimaryButton("UPDATE");

        JButton deleteButton =
                UITheme.createDangerButton("DELETE");

        JButton clearButton =
                UITheme.createSecondaryButton("CLEAR");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        formPanel.add(buttonPanel);

        formPanel.add(Box.createVerticalStrut(15));

        JButton backButton =
                UITheme.createSecondaryButton("← BACK TO DASHBOARD");

        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(backButton);

        // =========================
        // RIGHT PANEL
        // =========================

        JPanel rightPanel =
                new JPanel(new BorderLayout(10, 10));

        rightPanel.setBackground(UITheme.BACKGROUND);

        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout(8, 8));

        searchPanel.setBackground(UITheme.CARD);

        searchPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UITheme.BORDER),
                        new EmptyBorder(10, 10, 10, 10)
                )
        );

        JLabel searchLabel =
                UITheme.createLabel("Search:");

        searchField =
                UITheme.createTextField();

        JButton searchButton =
                UITheme.createPrimaryButton("SEARCH");

        JButton showAllButton =
                UITheme.createSecondaryButton("SHOW ALL");

        JPanel searchButtons =
                new JPanel(new GridLayout(1, 2, 8, 0));

        searchButtons.setBackground(UITheme.CARD);

        searchButtons.add(searchButton);
        searchButtons.add(showAllButton);

        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButtons, BorderLayout.EAST);

        rightPanel.add(searchPanel, BorderLayout.NORTH);

        // =========================
        // TABLE
        // =========================

        String[] columns = {
                "Student ID",
                "Name",
                "Roll No",
                "Department",
                "Year",
                "Phone"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column) {

                return false;
            }
        };

        studentTable =
                new JTable(tableModel);

        UITheme.styleTable(studentTable);

        studentTable.setAutoCreateRowSorter(true);
        studentTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        sorter =
                new TableRowSorter<>(tableModel);

        studentTable.setRowSorter(sorter);

        JScrollPane scrollPane =
                new JScrollPane(studentTable);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        UITheme.BORDER
                )
        );

        rightPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =========================
        // ADD TO MAIN PANEL
        // =========================

        mainPanel.add(formPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);

        // =========================
        // BUTTON ACTIONS
        // =========================

        addButton.addActionListener(e ->
                addStudent()
        );

        updateButton.addActionListener(e ->
                updateStudent()
        );

        deleteButton.addActionListener(e ->
                deleteStudent()
        );

        clearButton.addActionListener(e ->
                clearFields()
        );

        searchButton.addActionListener(e ->
                searchStudents()
        );

        showAllButton.addActionListener(e ->
                loadStudents()
        );

        backButton.addActionListener(e -> {

            dispose();
            new AdminDashboard();

        });

        // Enter in search box
        searchField.addActionListener(e ->
                searchStudents()
        );

        // Table row click
        studentTable.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        int selectedRow =
                                studentTable.getSelectedRow();

                        if (selectedRow >= 0) {

                            int modelRow =
                                    studentTable.convertRowIndexToModel(
                                            selectedRow
                                    );

                            loadSelectedStudent(modelRow);
                        }
                    }
                });
    }

    // =========================================================
    // LOAD STUDENTS
    // =========================================================

    private void loadStudents() {

        tableModel.setRowCount(0);

        List<Student> students =
                studentDAO.getAllStudents();

        for (Student student : students) {

            tableModel.addRow(new Object[]{
                    student.getStudentId(),
                    student.getName(),
                    student.getRollNo(),
                    student.getDepartment(),
                    student.getYear(),
                    student.getPhone()
            });
        }

        clearFields();
    }

    // =========================================================
    // SEARCH
    // =========================================================

    private void searchStudents() {

        String keyword =
                searchField.getText().trim();

        if (keyword.isEmpty()) {

            loadStudents();
            return;
        }

        tableModel.setRowCount(0);

        List<Student> students =
                studentDAO.searchStudents(keyword);

        for (Student student : students) {

            tableModel.addRow(new Object[]{
                    student.getStudentId(),
                    student.getName(),
                    student.getRollNo(),
                    student.getDepartment(),
                    student.getYear(),
                    student.getPhone()
            });
        }

        if (students.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No students found.",
                    "Search Result",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

        clearFields();
    }

    // =========================================================
    // ADD STUDENT
    // =========================================================

    private void addStudent() {

        if (!validateFields()) {
            return;
        }

        String name =
                nameField.getText().trim();

        String rollNo =
                rollField.getText().trim().toUpperCase();

        String department =
                departmentField.getText().trim().toUpperCase();

        int year =
                Integer.parseInt(
                        yearField.getText().trim()
                );

        String phone =
                phoneField.getText().trim();

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Add this student?",
                        "Confirm Add",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        Student student =
                new Student(
                        name,
                        rollNo,
                        department,
                        year,
                        phone
                );

        if (studentDAO.addStudent(student)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadStudents();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to add student.\n"
                            + "Roll number may already exist.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // UPDATE STUDENT
    // =========================================================

    private void updateStudent() {

        if (selectedStudentId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a student from the table first.",
                    "No Student Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (!validateFields()) {
            return;
        }

        String name =
                nameField.getText().trim();

        String rollNo =
                rollField.getText().trim().toUpperCase();

        String department =
                departmentField.getText().trim().toUpperCase();

        int year =
                Integer.parseInt(
                        yearField.getText().trim()
                );

        String phone =
                phoneField.getText().trim();

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Update this student?",
                        "Confirm Update",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        Student student =
                new Student(
                        name,
                        rollNo,
                        department,
                        year,
                        phone
                );

        student.setStudentId(selectedStudentId);

        if (studentDAO.updateStudent(student)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadStudents();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update student.\n"
                            + "Roll number may already exist.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // DELETE STUDENT
    // =========================================================

    private void deleteStudent() {

        if (selectedStudentId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a student from the table first.",
                    "No Student Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this student?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        if (studentDAO.deleteStudent(selectedStudentId)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadStudents();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to delete student.\n"
                            + "The student may have existing transactions.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // LOAD SELECTED STUDENT
    // =========================================================

    private void loadSelectedStudent(int row) {

        selectedStudentId =
                Integer.parseInt(
                        tableModel.getValueAt(
                                row, 0
                        ).toString()
                );

        nameField.setText(
                tableModel.getValueAt(
                        row, 1
                ).toString()
        );

        rollField.setText(
                tableModel.getValueAt(
                        row, 2
                ).toString()
        );

        departmentField.setText(
                tableModel.getValueAt(
                        row, 3
                ).toString()
        );

        yearField.setText(
                tableModel.getValueAt(
                        row, 4
                ).toString()
        );

        phoneField.setText(
                tableModel.getValueAt(
                        row, 5
                ).toString()
        );
    }

    // =========================================================
    // VALIDATION
    // =========================================================

    private boolean validateFields() {

        String name =
                nameField.getText().trim();

        String rollNo =
                rollField.getText().trim().toUpperCase();

        String department =
                departmentField.getText().trim().toUpperCase();

        String yearText =
                yearField.getText().trim();

        String phone =
                phoneField.getText().trim();

        // Name
        if (name.isEmpty()) {

            showError("Please enter student name.");
            nameField.requestFocus();
            return false;
        }

        if (!name.matches("[a-zA-Z ]+")) {

            showError(
                    "Student name should contain only letters and spaces."
            );

            nameField.requestFocus();
            return false;
        }

        // Roll number
        if (rollNo.isEmpty()) {

            showError("Please enter roll number.");
            rollField.requestFocus();
            return false;
        }

        if (!rollNo.matches(
                "(EXTC|ECS|MECH|AUTO|COMP|IT)[ABC][0-9]{3}"
        )) {

            showError(
                    "Invalid roll number format.\n\n"
                            + "Examples:\n"
                            + "EXTCA012\n"
                            + "EXTCB401\n"
                            + "ECSA123"
            );

            rollField.requestFocus();
            return false;
        }

        // Department
        if (department.isEmpty()) {

            showError("Please enter department.");
            departmentField.requestFocus();
            return false;
        }

        if (!department.matches(
                "(EXTC|ECS|MECH|AUTO|COMP|IT)"
        )) {

            showError(
                    "Invalid department.\n\n"
                            + "Allowed:\n"
                            + "EXTC, ECS, MECH, AUTO, COMP, IT"
            );

            departmentField.requestFocus();
            return false;
        }

        // Year
        if (yearText.isEmpty()) {

            showError("Please enter year.");
            yearField.requestFocus();
            return false;
        }

        int year;

        try {

            year =
                    Integer.parseInt(yearText);

        } catch (NumberFormatException e) {

            showError(
                    "Year must be a number from 1 to 4."
            );

            yearField.requestFocus();
            return false;
        }

        if (year < 1 || year > 4) {

            showError(
                    "Year must be between 1 and 4."
            );

            yearField.requestFocus();
            return false;
        }

        // Phone
        if (phone.isEmpty()) {

            showError("Please enter phone number.");
            phoneField.requestFocus();
            return false;
        }

        if (!phone.matches("[0-9]{10}")) {

            showError(
                    "Phone number must contain exactly 10 digits."
            );

            phoneField.requestFocus();
            return false;
        }

        return true;
    }

    // =========================================================
    // CLEAR
    // =========================================================

    private void clearFields() {

        selectedStudentId = -1;

        nameField.setText("");
        rollField.setText("");
        departmentField.setText("");
        yearField.setText("");
        phoneField.setText("");

        studentTable.clearSelection();

        nameField.requestFocus();
    }

    // =========================================================
    // ERROR MESSAGE
    // =========================================================

    private void showError(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Validation Error",
                JOptionPane.WARNING_MESSAGE
        );
    }
}