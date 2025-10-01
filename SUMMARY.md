# Summary: App.java

Sistem manajemen inventori barang sederhana berbasis Java.

## Identifikasi Tipe Data, Variabel, Methods, dan Ekspresi

### Tipe Data

- `String`: Untuk id dan nama barang, serta input menu.
- `int`: Untuk jumlah barang.
- `List<Item>`: Untuk menyimpan daftar barang.
- `Scanner`: Untuk input dari terminal.

### Variabel

- `id`: ID barang (String)
- `name`: Nama barang (String)
- `quantity`: Jumlah barang (int)
- `items`: Daftar barang (List<Item>)
- `manager`: Objek InventoryManager
- `scanner`: Objek Scanner
- `pilihan`: Input menu dari user
- `qty`: Jumlah barang yang diinput

### Methods

- `Item(String id, String name, int quantity)`: Konstruktor barang
- `getId()`, `getName()`, `getQuantity()`: Getter barang
- `setQuantity(int quantity)`: Setter barang
- `toString()`: Format tampilan barang
- `addItem(Item item)`: Menambah barang ke inventori
- `getAllItems()`: Mengambil semua barang
- `main(String[] args)`: Entry point aplikasi

### Ekspresi

- Perulangan utama aplikasi: `while (true)`
- Switch menu: `switch (pilihan)`
- Input data barang: `scanner.nextLine()`
- Parsing jumlah barang: `Integer.parseInt(scanner.nextLine())`
- Menambah barang: `manager.addItem(new Item(id, name, qty))`
- Menampilkan barang: `for (Item item : manager.getAllItems())`
- Keluar aplikasi: `scanner.close(); return;`

## Struktur Kode

- Kelas utama: `App`
  - Kelas nested: `Item` dan `InventoryManager`
  - Method utama: `main`
- Komentar sudah ditambahkan di setiap bagian kode sesuai identifikasi.

---

**Catatan:**

- Fitur yang tersedia: tambah barang, tampilkan semua barang, keluar aplikasi.
- Komentar pada kode membantu memahami tipe data, variabel, method, dan ekspresi yang digunakan.
