package library.dao;

import library.DBConnection;
import library.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BookDAO {

    // ================= ADD BOOK =================

    public boolean addBook(Book book) {

        String sql = "INSERT INTO books " +
                "(book_name, author, category, publisher, quantity, available_quantity) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(1, book.getBookName());
            pst.setString(2, book.getAuthor());
            pst.setString(3, book.getCategory());
            pst.setString(4, book.getPublisher());
            pst.setInt(5, book.getQuantity());

            // Initially all books are available
            pst.setInt(6, book.getQuantity());

            pst.executeUpdate();

            con.close();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // ================= GET ALL BOOKS =================

    public ArrayList<Book> getAllBooks() {

        ArrayList<Book> books =
                new ArrayList<>();

        String sql =
                "SELECT * FROM books";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                Book book =
                        new Book();

                book.setBookId(
                        rs.getInt("book_id")
                );

                book.setBookName(
                        rs.getString("book_name")
                );

                book.setAuthor(
                        rs.getString("author")
                );

                book.setCategory(
                        rs.getString("category")
                );

                book.setPublisher(
                        rs.getString("publisher")
                );

                book.setQuantity(
                        rs.getInt("quantity")
                );

                book.setAvailableQuantity(
                        rs.getInt("available_quantity")
                );

                books.add(book);
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return books;
    }


    // ================= SEARCH BOOKS =================

    public ArrayList<Book> searchBooks(String keyword) {

        ArrayList<Book> books =
                new ArrayList<>();

        String sql =
                "SELECT * FROM books " +
                "WHERE book_name LIKE ? " +
                "OR author LIKE ? " +
                "OR category LIKE ?";

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

                Book book =
                        new Book();

                book.setBookId(
                        rs.getInt("book_id")
                );

                book.setBookName(
                        rs.getString("book_name")
                );

                book.setAuthor(
                        rs.getString("author")
                );

                book.setCategory(
                        rs.getString("category")
                );

                book.setPublisher(
                        rs.getString("publisher")
                );

                book.setQuantity(
                        rs.getInt("quantity")
                );

                book.setAvailableQuantity(
                        rs.getInt("available_quantity")
                );

                books.add(book);
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return books;
    }


    // ================= UPDATE BOOK =================

    public boolean updateBook(Book book) {

        String sql =
                "UPDATE books SET " +
                "book_name = ?, " +
                "author = ?, " +
                "category = ?, " +
                "publisher = ?, " +
                "quantity = ?, " +
                "available_quantity = ? " +
                "WHERE book_id = ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(
                    1,
                    book.getBookName()
            );

            pst.setString(
                    2,
                    book.getAuthor()
            );

            pst.setString(
                    3,
                    book.getCategory()
            );

            pst.setString(
                    4,
                    book.getPublisher()
            );

            pst.setInt(
                    5,
                    book.getQuantity()
            );

            pst.setInt(
                    6,
                    book.getAvailableQuantity()
            );

            pst.setInt(
                    7,
                    book.getBookId()
            );

            pst.executeUpdate();

            con.close();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // ================= DELETE BOOK =================

    public boolean deleteBook(int bookId) {

        String sql =
                "DELETE FROM books " +
                "WHERE book_id = ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setInt(1, bookId);

            pst.executeUpdate();

            con.close();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}