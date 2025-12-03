package repository;

import db.Database;
import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    public void add(Student student) {
        String sql = "INSERT INTO students(name, department) VALUES (?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getDepartment());
            ps.executeUpdate();

            System.out.println("Öğrenci eklendi.");

        } catch (SQLException e) {
            System.out.println("Öğrenci eklerken hata: " + e.getMessage());
        }
    }

    public void update(Student student) {
        String sql = "UPDATE students SET name = ?, department = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getDepartment());
            ps.setInt(3, student.getId());
            ps.executeUpdate();

            System.out.println("Öğrenci güncellendi.");

        } catch (SQLException e) {
            System.out.println("Öğrenci güncellenirken hata: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Öğrenci silindi.");

        } catch (SQLException e) {
            System.out.println("Öğrenci silinirken hata: " + e.getMessage());
        }
    }

    public Student getById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department")
                );
            }

        } catch (SQLException e) {
            System.out.println("Öğrenci getirilirken hata: " + e.getMessage());
        }
        return null;
    }

    public List<Student> getAll() {
        String sql = "SELECT * FROM students";
        List<Student> students = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Öğrenciler listelenirken hata: " + e.getMessage());
        }

        return students;
    }
}
