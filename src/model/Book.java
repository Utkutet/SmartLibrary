package model;

public class Book {
    private int id;
    private String title;
    private String author;
    private int year;

    // Boş constructor (JDBC için vs.)
    public Book() {
    }

    // Yeni kitap eklerken id bilmediğimiz durum
    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    // Veritabanından dönen kayıtlar için
    public Book(int id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    // Getter – Setter’lar
    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) { this.author = author; }

    public int getYear() {
        return year;
    }

    public void setYear(int year) { this.year = year; }

    @Override
    public String toString() {
        return "model.Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }
}
