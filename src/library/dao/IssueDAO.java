package library.dao;

import library.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class IssueDAO {

    public boolean issueBook(
            int studentId,
            int bookId,
            String issueDate,
            String dueDate) {

        String checkBookSql =
                "SELECT available_quantity " +
                "FROM books " +
                "WHERE book_id = ?";

        String checkIssueSql =
                "SELECT issue_id " +
                "FROM issued_books " +
                "WHERE student_id = ? " +
                "AND book_id = ? " +
                "AND status = 'ISSUED'";

        String issueSql =
                "INSERT INTO issued_books " +
                "(student_id, book_id, issue_date, due_date, status) " +
                "VALUES (?, ?, ?, ?, 'ISSUED')";

        String updateBookSql =
                "UPDATE books " +
                "SET available_quantity = available_quantity - 1 " +
                "WHERE book_id = ?";


        Connection con = null;

        try {

            con = DBConnection.getConnection();

            if (con == null) {
                return false;
            }


            // Start database transaction

            con.setAutoCommit(false);


            // =========================================
            // CHECK BOOK AVAILABILITY
            // =========================================

            PreparedStatement checkBook =
                    con.prepareStatement(checkBookSql);

            checkBook.setInt(1, bookId);

            ResultSet bookResult =
                    checkBook.executeQuery();


            if (!bookResult.next()) {

                con.rollback();

                return false;
            }


            int availableQuantity =
                    bookResult.getInt("available_quantity");


            if (availableQuantity <= 0) {

                con.rollback();

                return false;
            }


            // =========================================
            // CHECK DUPLICATE ISSUE
            // =========================================

            PreparedStatement checkIssue =
                    con.prepareStatement(checkIssueSql);

            checkIssue.setInt(1, studentId);
            checkIssue.setInt(2, bookId);

            ResultSet issueResult =
                    checkIssue.executeQuery();


            if (issueResult.next()) {

                con.rollback();

                return false;
            }


            // =========================================
            // INSERT ISSUE RECORD
            // =========================================

            PreparedStatement issue =
                    con.prepareStatement(issueSql);

            issue.setInt(1, studentId);
            issue.setInt(2, bookId);
            issue.setString(3, issueDate);
            issue.setString(4, dueDate);

            issue.executeUpdate();


            // =========================================
            // DECREASE AVAILABLE BOOKS
            // =========================================

            PreparedStatement updateBook =
                    con.prepareStatement(updateBookSql);

            updateBook.setInt(1, bookId);

            updateBook.executeUpdate();


            // =========================================
            // SAVE EVERYTHING
            // =========================================

            con.commit();

            con.setAutoCommit(true);

            return true;


        } catch (Exception e) {

            e.printStackTrace();


            try {

                if (con != null) {

                    con.rollback();
                }

            } catch (Exception rollbackError) {

                rollbackError.printStackTrace();
            }


            return false;


        } finally {

            try {

                if (con != null) {

                    con.setAutoCommit(true);
                    con.close();
                }

            } catch (Exception closeError) {

                closeError.printStackTrace();
            }
        }
    }
}