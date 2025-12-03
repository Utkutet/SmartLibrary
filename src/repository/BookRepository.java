package repository;

import db.Database;
import model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    public void add(Book book) {
        String sql = "INSERT INTO books(title, author, year) VALUES (?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getYear());
            ps.executeUpdate();

            System.out.println("Kitap eklendi.");

        } catch (SQLException e) {
            System.out.println("Kitap eklerken hata: " + e.getMessage());
        }
    }

    public void update(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, year = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getYear());
            ps.setInt(4, book.getId());
            ps.executeUpdate();

            System.out.println("Kitap güncellendi.");

        } catch (SQLException e) {
            System.out.println("Kitap güncellerken hata: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Kitap silindi.");

        } catch (SQLException e) {
            System.out.println("Kitap silerken hata: " + e.getMessage());
        }
    }

    public Book getById(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("year")
                );
            }

        } catch (SQLException e) {
            System.out.println("Kitap getirilirken hata: " + e.getMessage());
        }
        return null;
    }

    public List<Book> getAll() {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("year")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Kitaplar listelenirken hata: " + e.getMessage());
        }

        return books;
    }
}
