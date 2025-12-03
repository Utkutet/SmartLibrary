package app;

import java.util.List;
import java.util.Scanner;

import db.Database;
import model.Book;
import model.Loan;
import model.Student;
import repository.BookRepository;
import repository.LoanRepository;
import repository.StudentRepository;

public class SmartLibraryApp {

    private final BookRepository bookRepo = new BookRepository();
    private final StudentRepository studentRepo = new StudentRepository();
    private final LoanRepository loanRepo = new LoanRepository();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Veritabanını hazırla
        Database.initialize();

        // Uygulamayı başlat
        SmartLibraryApp app = new SmartLibraryApp();
        app.run();
    }

    public void run() {
        int choice = -1;

        while (choice != 0) {
            printMenu();
            System.out.print("Seçim: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    deleteBook();
                    break;
                case 3:
                    listBooks();
                    break;
                case 4:
                    addStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    listStudents();
                    break;
                case 7:
                    giveLoan();
                    break;
                case 8:
                    returnBook();
                    break;
                case 9:
                    listLoans();
                    break;
                case 0:
                    System.out.println("Çıkış yapılıyor...");
                    break;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== SmartLibrary Menü ===");
        System.out.println("1. Kitap Ekle");
        System.out.println("2. Kitap Sil");
        System.out.println("3. Kitapları Listele");
        System.out.println("4. Öğrenci Ekle");
        System.out.println("5. Öğrenci Sil");
        System.out.println("6. Öğrencileri Listele");
        System.out.println("7. Kitap Ödünç Ver");
        System.out.println("8. Kitap Geri Teslim Al");
        System.out.println("9. Ödünç Listesini Görüntüle");
        System.out.println("0. Çıkış");
    }


    // 1. Kitap Ekle
    private void addBook() {
        System.out.print("Kitap adı: ");
        String title = scanner.nextLine();

        System.out.print("Yazar: ");
        String author = scanner.nextLine();

        System.out.print("Yıl: ");
        int year = Integer.parseInt(scanner.nextLine());

        Book book = new Book(title, author, year);
        bookRepo.add(book);
    }

    // 2. Kitapları Listele
    private void listBooks() {
        List<Book> books = bookRepo.getAll();
        if (books.isEmpty()) {
            System.out.println("Hiç kitap yok.");
        } else {
            for (Book b : books) {
                System.out.println(b);
            }
        }
    }

    // 3. Öğrenci Ekle
    private void addStudent() {
        System.out.print("Öğrenci adı: ");
        String name = scanner.nextLine();

        System.out.print("Bölüm: ");
        String department = scanner.nextLine();

        Student student = new Student(name, department);
        studentRepo.add(student);
    }

    // 4. Öğrencileri Listele
    private void listStudents() {
        List<Student> students = studentRepo.getAll();
        if (students.isEmpty()) {
            System.out.println("Hiç öğrenci yok.");
        } else {
            for (Student s : students) {
                System.out.println(s);
            }
        }
    }

    // 5. Kitap Ödünç Ver
    private void giveLoan() {
        System.out.print("Öğrenci ID: ");
        int studentId = Integer.parseInt(scanner.nextLine());

        System.out.print("Kitap ID: ");
        int bookId = Integer.parseInt(scanner.nextLine());

        System.out.print("Ödünç alınan tarih (ör: 2025-12-03): ");
        String dateBorrowed = scanner.nextLine();

        // Basit kontrol: kitap ödünçte mi?
        boolean available = loanRepo.isBookAvailable(bookId);
        if (!available) {
            System.out.println("Bu kitap şu an ödünçte!");
            return;
        }

        Loan loan = new Loan(bookId, studentId, dateBorrowed);
        loanRepo.add(loan);
    }

    // 6. Ödünç Listesini Görüntüle
    private void listLoans() {
        List<Loan> loans = loanRepo.getAll();
        if (loans.isEmpty()) {
            System.out.println("Hiç ödünç kaydı yok.");
        } else {
            for (Loan l : loans) {
                System.out.println(l);
            }
        }
    }

    // 7. Kitap Geri Teslim Al
    private void returnBook() {
        System.out.print("İade edilecek loan ID: ");
        int loanId = Integer.parseInt(scanner.nextLine());

        System.out.print("İade tarihi (ör: 2025-12-03): ");
        String returnDate = scanner.nextLine();

        loanRepo.returnBook(loanId, returnDate);
    }

    // 8. Kitap Sil
    private void deleteBook() {
        System.out.print("Silinecek kitap ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        // İstersen önce gerçekten var mı diye bakabilirsin
        Book book = bookRepo.getById(id);
        if (book == null) {
            System.out.println("Bu ID'ye ait kitap bulunamadı.");
            return;
        }

        bookRepo.delete(id);
    }

    // 9. Öğrenci Sil
    private void deleteStudent() {
        System.out.print("Silinecek öğrenci ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        Student student = studentRepo.getById(id);
        if (student == null) {
            System.out.println("Bu ID'ye ait öğrenci bulunamadı.");
            return;
        }

        studentRepo.delete(id);
    }

}

