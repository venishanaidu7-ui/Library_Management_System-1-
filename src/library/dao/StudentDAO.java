package library.dao;

import library.DBConnection;
import library.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class StudentDAO {

    // ================= ADD STUDENT =================

    public boolean addStudent(Student student) {

        String sql = "INSERT INTO students " +
                "(name, roll_no, department, year, phone) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(1, student.getName());
            pst.setString(2, student.getRollNo());
            pst.setString(3, student.getDepartment());
            pst.setInt(4, student.getYear());
            pst.setString(5, student.getPhone());

            pst.executeUpdate();

            con.close();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // ================= GET ALL STUDENTS =================

    public ArrayList<Student> getAllStudents() {

        ArrayList<Student> students =
                new ArrayList<>();

        String sql =
                "SELECT * FROM students";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                Student student =
                        new Student();

                student.setStudentId(
                        rs.getInt("student_id")
                );

                student.setName(
                        rs.getString("name")
                );

                student.setRollNo(
                        rs.getString("roll_no")
                );

                student.setDepartment(
                        rs.getString("department")
                );

                student.setYear(
                        rs.getInt("year")
                );

                student.setPhone(
                        rs.getString("phone")
                );

                students.add(student);
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return students;
    }


    // ================= SEARCH STUDENTS =================

    public ArrayList<Student> searchStudents(
            String keyword) {

        ArrayList<Student> students =
                new ArrayList<>();

        String sql =
                "SELECT * FROM students " +
                "WHERE name LIKE ? " +
                "OR roll_no LIKE ? " +
                "OR department LIKE ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            String search =
                    "%" + keyword + "%";

            pst.setString(1, search);
            pst.setString(2, search);
            pst.setString(3, search);

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                Student student =
                        new Student();

                student.setStudentId(
                        rs.getInt("student_id")
                );

                student.setName(
                        rs.getString("name")
                );

                student.setRollNo(
                        rs.getString("roll_no")
                );

                student.setDepartment(
                        rs.getString("department")
                );

                student.setYear(
                        rs.getInt("year")
                );

                student.setPhone(
                        rs.getString("phone")
                );

                students.add(student);
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return students;
    }


    // ================= UPDATE STUDENT =================

    public boolean updateStudent(Student student) {

        String sql =
                "UPDATE students SET " +
                "name = ?, " +
                "roll_no = ?, " +
                "department = ?, " +
                "year = ?, " +
                "phone = ? " +
                "WHERE student_id = ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(
                    1,
                    student.getName()
            );

            pst.setString(
                    2,
                    student.getRollNo()
            );

            pst.setString(
                    3,
                    student.getDepartment()
            );

            pst.setInt(
                    4,
                    student.getYear()
            );

            pst.setString(
                    5,
                    student.getPhone()
            );

            pst.setInt(
                    6,
                    student.getStudentId()
            );

            pst.executeUpdate();

            con.close();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // ================= DELETE STUDENT =================

    public boolean deleteStudent(
            int studentId) {

        String sql =
                "DELETE FROM students " +
                "WHERE student_id = ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setInt(1, studentId);

            pst.executeUpdate();

            con.close();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}