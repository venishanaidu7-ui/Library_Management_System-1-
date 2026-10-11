package library.dao;

import library.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardDAO {

    public int getTotalBooks() {

        String sql = "SELECT COUNT(*) FROM books";

        try {

            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                int count = rs.getInt(1);

                con.close();

                return count;
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }


    public int getAvailableBooks() {

        String sql =
                "SELECT COALESCE(SUM(available_quantity), 0) " +
                "FROM books";

        try {

            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                int count = rs.getInt(1);

                con.close();

                return count;
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }


    public int getIssuedBooks() {

        String sql =
                "SELECT COUNT(*) " +
                "FROM issued_books " +
                "WHERE status = 'ISSUED'";

        try {

            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                int count = rs.getInt(1);

                con.close();

                return count;
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }


    public int getTotalStudents() {

        String sql = "SELECT COUNT(*) FROM students";

        try {

            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                int count = rs.getInt(1);

                con.close();

                return count;
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }
}