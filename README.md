# Sistem Manajemen Inventori (PBO)

Aplikasi sederhana untuk pengelolaan inventori barang berbasis Java GUI (Swing) dan database PostgreSQL. Project ini dibuat untuk tugas/pembelajaran Pemrograman Berorientasi Objek (PBO).

## Fitur Utama
- **CRUD Barang**: Menambah dan melihat daftar barang.
- **Kategori Khusus**: Mendukung input spesifik untuk kategori **Pakaian** (Ukuran, Bahan) dan **Elektronik** (Garansi).
- **Update Stok**: Update jumlah stok barang dengan mudah.
- **UI Modern**: Menggunakan Java Swing dengan styling custom (Nimbus LookAndFeel).
- **Database**: Terintegrasi dengan PostgreSQL untuk penyimpanan data persisten.

## Persyaratan Sistem
- **Java JDK**: Versi 8 atau lebih baru.
- **PostgreSQL**: Pastikan service database berjalan.
- **JDBC Driver**: Library PostgreSQL driver (sudah termasuk di folder `lib` jika ada, atau tambahkan manual).

## Cara Instalasi & Menjalankan

1. **Persiapan Database**:
   - Buat database kosong di PostgreSQL bernama `inventori_db`.
   - Jalankan query yang ada di file `database_schema.sql` untuk membuat tabel dan relasi.

2. **Konfigurasi Kode**:
   - Karena file `src/DatabaseConfig.java` masuk ke `.gitignore`, Anda perlu membuatnya secara manual.
   - Buat file `src/DatabaseConfig.java` dan isi dengan template berikut (sesuaikan username & password):

   ```java
   public class DatabaseConfig {
       // Konfigurasi koneksi database PostgreSQL
       public static final String DB_URL = "jdbc:postgresql://localhost:5432/inventori_db";
       public static final String DB_USER = "postgres";     // Ganti dengan username database Anda
       public static final String DB_PASSWORD = "password"; // Ganti dengan password database Anda
       
       // Driver class name
       public static final String DB_DRIVER = "org.postgresql.Driver";
   }
   ```

3. **Jalankan**:
   - Buka folder project ini di VS Code (pastikan Extension Pack for Java sudah terinstall).
   - Buka file `src/InventoriGUI.java`.
   - Tekan `F5` atau klik **Run**.

## Struktur Folder
- `src/`: Berisi kode sumber Java (`InventoriGUI`, `DatabaseConfig`, DAO, dll).
- `database_schema.sql`: Script SQL untuk inisialisasi database.

---
*Project PBO - Manajemen Inventori Sederhana*
