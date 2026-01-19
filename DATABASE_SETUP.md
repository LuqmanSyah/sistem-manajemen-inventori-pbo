# Setup Database PostgreSQL

## Persyaratan
- PostgreSQL 12 atau lebih baru
- Java Development Kit (JDK) 11 atau lebih baru
- PostgreSQL JDBC Driver (sudah disediakan di folder `lib/`)

## Langkah-langkah Setup

### 1. Install PostgreSQL
Download dan install PostgreSQL dari [https://www.postgresql.org/download/](https://www.postgresql.org/download/)

### 2. Buat Database

Buka command prompt atau terminal, lalu jalankan:

```bash
# Login ke PostgreSQL sebagai superuser
psql -U postgres

# Buat database baru
CREATE DATABASE inventori_db;

# Keluar dari psql
\q
```

### 3. Jalankan Schema Database

```bash
# Jalankan file schema SQL
psql -U postgres -d inventori_db -f database_schema.sql
```

Atau bisa juga dengan cara:
```bash
# Login ke database inventori_db
psql -U postgres -d inventori_db

# Jalankan file schema
\i database_schema.sql

# Keluar
\q
```

### 4. Konfigurasi Koneksi Database

Edit file `src/DatabaseConfig.java` sesuai dengan konfigurasi PostgreSQL Anda:

```java
public static final String DB_URL = "jdbc:postgresql://localhost:5432/inventori_db";
public static final String DB_USER = "postgres";          // Ganti dengan username Anda
public static final String DB_PASSWORD = "password";      // Ganti dengan password Anda
```

### 5. Compile Project dengan JDBC Driver

Untuk compile di terminal:

```bash
# Windows
javac -cp ".;lib\postgresql-42.7.1.jar" -d bin src\*.java

# Linux/Mac
javac -cp ".:lib/postgresql-42.7.1.jar" -d bin src/*.java
```

### 6. Jalankan Aplikasi

```bash
# Windows
java -cp ".;lib\postgresql-42.7.1.jar;bin" InventoriGUI

# Linux/Mac
java -cp ".:lib/postgresql-42.7.1.jar:bin" InventoriGUI
```

## Konfigurasi VS Code

Jika menggunakan VS Code, tambahkan JDBC driver ke classpath:

1. Buka file `.vscode/settings.json`
2. Tambahkan konfigurasi berikut:

```json
{
    "java.project.referencedLibraries": [
        "lib/**/*.jar"
    ]
}
```

## Struktur Database

### Tabel: barang
- `id` (SERIAL PRIMARY KEY)
- `kode` (VARCHAR UNIQUE) - Kode unik barang
- `nama` (VARCHAR) - Nama barang
- `stok` (INTEGER) - Jumlah stok
- `harga` (DECIMAL) - Harga barang
- `jenis` (VARCHAR) - 'ELEKTRONIK' atau 'PAKAIAN'
- `created_at` (TIMESTAMP) - Waktu dibuat
- `updated_at` (TIMESTAMP) - Waktu diupdate

### Tabel: elektronik
- `id` (SERIAL PRIMARY KEY)
- `barang_id` (INTEGER FK) - Referensi ke tabel barang
- `garansi` (VARCHAR) - Masa garansi

### Tabel: pakaian
- `id` (SERIAL PRIMARY KEY)
- `barang_id` (INTEGER FK) - Referensi ke tabel barang
- `ukuran` (VARCHAR) - Ukuran pakaian
- `bahan` (VARCHAR) - Bahan pakaian

## Penggunaan DAO (Data Access Object)

File `InventoriDAO.java` menyediakan method untuk:

- `tambahElektronik(Elektronik e)` - Menambah barang elektronik
- `tambahPakaian(Pakaian p)` - Menambah barang pakaian
- `getAllBarang()` - Mengambil semua barang
- `updateStok(String kode, int stokBaru)` - Update stok barang
- `hapusBarang(String kode)` - Hapus barang
- `cariBarang(String kode)` - Cari barang berdasarkan kode

## Contoh Penggunaan

```java
// Inisialisasi DAO
InventoriDAO dao = new InventoriDAO();

// Tambah barang elektronik
Elektronik laptop = new Elektronik("EL003", "Laptop Dell", 5, 12000000, "1 Tahun");
dao.tambahElektronik(laptop);

// Tambah barang pakaian
Pakaian baju = new Pakaian("PK003", "Kaos Polo", 20, 85000, "XL", "Cotton");
dao.tambahPakaian(baju);

// Ambil semua barang
ArrayList<Barang> listBarang = dao.getAllBarang();

// Update stok
dao.updateStok("EL003", 10);

// Hapus barang
dao.hapusBarang("EL003");
```

## Troubleshooting

### Error: "JDBC Driver not found"
- Pastikan file `postgresql-42.7.1.jar` ada di folder `lib/`
- Pastikan classpath sudah di-set dengan benar

### Error: "Connection refused"
- Pastikan PostgreSQL service sudah berjalan
- Cek apakah port 5432 sudah benar
- Verifikasi username dan password di `DatabaseConfig.java`

### Error: "Database does not exist"
- Pastikan database `inventori_db` sudah dibuat
- Jalankan command: `CREATE DATABASE inventori_db;`

### Error: "Permission denied"
- Pastikan user PostgreSQL memiliki hak akses yang cukup
- Atau gunakan superuser `postgres`

## Catatan
- Data sample akan otomatis ditambahkan saat menjalankan schema SQL
- Koneksi database menggunakan Singleton pattern
- Semua operasi INSERT/UPDATE/DELETE otomatis menggunakan transaction
