package repository;

import db.Database;
import model.Loan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanRepository {

    public void add(Loan loan) {
        String sql = "INSERT INTO loans(bookId, studentId, dateBorrowed, dateReturned) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, loan.getBookId());
            ps.setInt(2, loan.getStudentId());
            ps.setString(3, loan.getDateBorrowed());
            ps.setString(4, loan.getDateReturned()); // genelde null gelecek
            ps.executeUpdate();

            System.out.println("Ödünç kaydı eklendi.");

        } catch (SQLException e) {
            System.out.println("Ödünç eklenirken hata: " + e.getMessage());
        }
    }

    public void update(Loan loan) {
        String sql = "UPDATE loans SET bookId = ?, studentId = ?, dateBorrowed = ?, " +
                "dateReturned = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, loan.getBookId());
            ps.setInt(2, loan.getStudentId());
            ps.setString(3, loan.getDateBorrowed());
            ps.setString(4, loan.getDateReturned());
            ps.setInt(5, loan.getId());
            ps.executeUpdate();

            System.out.println("Ödünç kaydı güncellendi.");

        } catch (SQLException e) {
            System.out.println("Ödünç güncellenirken hata: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM loans WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Ödünç kaydı silindi.");

        } catch (SQLException e) {
            System.out.println("Ödünç silinirken hata: " + e.getMessage());
        }
    }

    public Loan getById(int id) {
        String sql = "SELECT * FROM loans WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Loan(
                        rs.getInt("id"),
                        rs.getInt("bookId"),
                        rs.getInt("studentId"),
                        rs.getString("dateBorrowed"),
                        rs.getString("dateReturned")
                );
            }

        } catch (SQLException e) {
            System.out.println("Ödünç getirilirken hata: " + e.getMessage());
        }
        return null;
    }

    public List<Loan> getAll() {
        String sql = "SELECT * FROM loans";
        List<Loan> loans = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                loans.add(new Loan(
                        rs.getInt("id"),
                        rs.getInt("bookId"),
                        rs.getInt("studentId"),
                        rs.getString("dateBorrowed"),
                        rs.getString("dateReturned")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Ödünçler listelenirken hata: " + e.getMessage());
        }

        return loans;
    }

    // Basit kontrol: aynı bookId için dateReturned NULL ise ödünçte demektir
    public boolean isBookAvailable(int bookId) {
        String sql = "SELECT COUNT(*) AS cnt FROM loans " +
                "WHERE bookId = ? AND dateReturned IS NULL";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("cnt");
                return count == 0;
            }

        } catch (SQLException e) {
            System.out.println("Kitap uygunluk kontrolü hata: " + e.getMessage());
        }
        // Hata durumunda güvenlik olsun diye false yerine true yapmak istemiyorsan:
        return false;
    }

    // Kitap iade: dateReturned alanını güncelle
    public void returnBook(int loanId, String returnDate) {
        String sql = "UPDATE loans SET dateReturned = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, returnDate);
            ps.setInt(2, loanId);
            ps.executeUpdate();

            System.out.println("Kitap geri teslim alındı.");

        } catch (SQLException e) {
            System.out.println("Kitap iade edilirken hata: " + e.getMessage());
        }
    }
}
