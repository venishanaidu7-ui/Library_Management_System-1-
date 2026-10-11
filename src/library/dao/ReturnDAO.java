package library.dao;

import library.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReturnDAO {

    public boolean returnBook(
            int issueId,
            String returnDate,
            int lateDays,
            double fine) {

        Connection con = null;


        // =========================================
        // SQL TO GET BOOK ID
        // =========================================

        String getBookSql =
                "SELECT book_id, status " +
                "FROM issued_books " +
                "WHERE issue_id = ?";


        // =========================================
        // SQL TO INSERT RETURN
        // =========================================

        String returnSql =
                "INSERT INTO returns " +
                "(issue_id, return_date, late_days, fine) " +
                "VALUES (?, ?, ?, ?)";


        // =========================================
        // SQL TO UPDATE ISSUE STATUS
        // =========================================

        String issueSql =
                "UPDATE issued_books " +
                "SET status = 'RETURNED' " +
                "WHERE issue_id = ?";


        // =========================================
        // SQL TO INCREASE AVAILABLE BOOKS
        // =========================================

        String bookSql =
                "UPDATE books " +
                "SET available_quantity = " +
                "available_quantity + 1 " +
                "WHERE book_id = ?";


        try {

            con = DBConnection.getConnection();

            if (con == null) {
                return false;
            }


            // Start transaction

            con.setAutoCommit(false);


            // =========================================
            // GET BOOK AND CHECK ISSUE STATUS
            // =========================================

            PreparedStatement getBook =
                    con.prepareStatement(
                            getBookSql
                    );

            getBook.setInt(
                    1,
                    issueId
            );

            ResultSet rs =
                    getBook.executeQuery();


            // Issue does not exist

            if (!rs.next()) {

                con.rollback();

                return false;
            }


            int bookId =
                    rs.getInt("book_id");


            String status =
                    rs.getString("status");


            // =========================================
            // CHECK IF ALREADY RETURNED
            // =========================================

            if (!"ISSUED".equals(status)) {

                con.rollback();

                return false;
            }


            // =========================================
            // INSERT RETURN RECORD
            // =========================================

            PreparedStatement returnPst =
                    con.prepareStatement(
                            returnSql
                    );

            returnPst.setInt(
                    1,
                    issueId
            );

            returnPst.setString(
                    2,
                    returnDate
            );

            returnPst.setInt(
                    3,
                    lateDays
            );

            returnPst.setDouble(
                    4,
                    fine
            );

            returnPst.executeUpdate();


            // =========================================
            // CHANGE STATUS
            // =========================================

            PreparedStatement issuePst =
                    con.prepareStatement(
                            issueSql
                    );

            issuePst.setInt(
                    1,
                    issueId
            );

            issuePst.executeUpdate();


            // =========================================
            // INCREASE AVAILABLE QUANTITY
            // =========================================

            PreparedStatement bookPst =
                    con.prepareStatement(
                            bookSql
                    );

            bookPst.setInt(
                    1,
                    bookId
            );

            bookPst.executeUpdate();


            // =========================================
            // SAVE ALL CHANGES
            // =========================================

            con.commit();

            con.setAutoCommit(true);

            return true;


        } catch (Exception e) {

            e.printStackTrace();


            // =========================================
            // ROLLBACK IF SOMETHING FAILS
            // =========================================

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