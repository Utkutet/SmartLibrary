SMARTLIBRARY – Java OOP | JDBC | SQLite Kütüphane Yönetim Sistemi

SmartLibrary, Java programlama dili kullanılarak geliştirilmiş, konsol tabanlı bir kütüphane yönetim uygulamasıdır.
Uygulama; kitap, öğrenci ve ödünç verme işlemlerini yönetmek için Java OOP (Nesne Yönelimli Programlama) prensipleri,
JDBC bağlantısı ve SQLite veri tabanı teknolojilerini kullanır.

Amaç, yazılım mimarisini katmanlara ayırarak (model, repository, database, uygulama) temiz ve bakımı kolay bir yapı oluşturmaktır.

-------------------------------------

📚 Proje Özellikleri

- Kitap İşlemleri

Yeni kitap ekleme

Kitap silme

Tüm kitapları listeleme


- Öğrenci İşlemleri

Yeni öğrenci ekleme

Öğrenci silme

Tüm öğrencileri listeleme


- Ödünç İşlemleri

Kitap ödünç verme

Kitap geri teslim alma

Ödünç geçmişini listeleme


- Diğer Özellikler

Basit ödünç kontrol mekanizması (kitap zaten ödünçte mi?)

Veriler SQLite veri tabanında saklanır

Konsol tabanlı temiz kullanıcı menüsü

---------------------------------------

🏗️ Kullanılan Teknolojiler

Java (JDK 17+)

JDBC (Java Database Connectivity)

SQLite (Yerel veri tabanı)

SQLite JDBC Driver (xerial/sqlite-jdbc)

IntelliJ IDEA / NetBeans (geliştirme ortamı)

---------------------------------------
⚙️ Kurulum ve Çalıştırma

Projeyi GitHub üzerinden indirin:
Code → Download ZIP

IntelliJ IDEA veya NetBeans ile açın.

lib klasöründeki SQLite JDBC .jar dosyasını projeye ekleyin:

IntelliJ → File > Project Structure > Libraries > Add > Java

SmartLibraryApp sınıfındaki main metodunu çalıştırın.

Konsol menüsü açılacaktır.

---------------------------------------

🧩 Kodun Mimari Yapısı

1️⃣ Database Katmanı (Database.java)

SQLite bağlantısını yönetir

Veritabanını oluşturur (smartlibrary.db)

Gerekli tabloları oluşturur (books, students, loans)

2️⃣ Model Katmanı (model/)

Projedeki varlık sınıfları:

Book

Student

Loan

Her biri gerekli alanları, constructor'ları ve getter/setter metotlarını içerir.

3️⃣ Repository Katmanı (repository/)

Her entity için ayrı Repository sınıfı vardır:

CRUD işlemleri (add, delete, update, getById, getAll)

PreparedStatement ile güvenli SQL işlemleri

4️⃣ Uygulama Katmanı (SmartLibraryApp)

Konsol menüsü

Kullanıcı etkileşimleri

İş akışları

Repository çağrıları
