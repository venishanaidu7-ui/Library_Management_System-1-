package library.dao;

import library.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDAO {

    // =========================
    // LOGIN
    // =========================

    public boolean login(String username, String password) {

        String sql =
                "SELECT * FROM admins " +
                "WHERE username = ? AND password = ?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs =
                    pst.executeQuery();

            boolean result = rs.next();

            con.close();

            return result;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // =========================
    // REGISTER ADMIN
    // =========================

    public boolean registerAdmin(
            String username,
            String password,
            String email,
            String phone) {

        String sql =
                "INSERT INTO admins " +
                "(username, password, email, phone) " +
                "VALUES (?, ?, ?, ?)";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, email);
            pst.setString(4, phone);

            pst.executeUpdate();

            con.close();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // =========================
    // CHECK EMAIL + PHONE
    // =========================

    public boolean verifyAdmin(
            String email,
            String phone) {

        String sql =
                "SELECT * FROM admins " +
                "WHERE email = ? AND phone = ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(1, email);
            pst.setString(2, phone);

            ResultSet rs =
                    pst.executeQuery();

            boolean result = rs.next();

            con.close();

            return result;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // =========================
    // CHANGE USERNAME
    // =========================

    public boolean changeUsername(
            String email,
            String phone,
            String newUsername) {

        String sql =
                "UPDATE admins " +
                "SET username = ? " +
                "WHERE email = ? AND phone = ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(1, newUsername);
            pst.setString(2, email);
            pst.setString(3, phone);

            int rows =
                    pst.executeUpdate();

            con.close();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // =========================
    // VERIFY USERNAME + EMAIL
    // =========================

    public boolean verifyUsernameAndEmail(
            String username,
            String email) {

        String sql =
                "SELECT * FROM admins " +
                "WHERE username = ? AND email = ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(1, username);
            pst.setString(2, email);

            ResultSet rs =
                    pst.executeQuery();

            boolean result = rs.next();

            con.close();

            return result;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // =========================
    // CHANGE PASSWORD
    // =========================

    public boolean changePassword(
            String username,
            String email,
            String newPassword) {

        String sql =
                "UPDATE admins " +
                "SET password = ? " +
                "WHERE username = ? AND email = ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(1, newPassword);
            pst.setString(2, username);
            pst.setString(3, email);

            int rows =
                    pst.executeUpdate();

            con.close();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}