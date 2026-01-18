# Penjelasan Kode InventoriGUI.java

## Overview
Aplikasi GUI untuk mengelola sistem inventori barang dengan 4 menu utama:
- **Tambah Barang** - Memasukkan barang baru
- **Lihat Barang** - Melihat daftar semua barang
- **Update Stok** - Mengubah jumlah stok
- **Keluar** - Menutup aplikasi

---

## 1. Konstruktor (Baris 9-50)

### Inisialisasi Jendela Utama
```java
inventori = new Inventori();                    // Objek penyimpanan data
setTitle("Sistem Inventori");                   // Judul aplikasi
setSize(800, 600);                              // Ukuran jendela: 800x600 pixel
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Matikan program saat tombol X diklik
setLocationRelativeTo(null);                    // Posisi jendela di tengah layar
setLayout(new BorderLayout());                  // Layout utama: NORTH, CENTER, SOUTH, EAST, WEST
```

### Panel Menu (Baris 18-37)
**Layout:** `FlowLayout` - komponen berjejer dari kiri ke kanan

| Tombol | Fungsi |
|--------|--------|
| btnTambah | Panggil `tampilkanFormTambah()` |
| btnLihat | Panggil `tampilkanTabel()` |
| btnUpdate | Panggil `tampilkanFormUpdate()` |
| btnKeluar | `System.exit(0)` - matiin program |

### Content Panel (Baris 39-47)
**Layout:** `BorderLayout`
- Awalnya berisi label: *"Pilih menu di atas untuk memulai"*
- Akan berubah sesuai menu yang diklik

---

## 2. Form Tambah Barang (Baris 52-172)

### Reset Panel
```java
contentPanel.removeAll();  // Hapus semua komponen yang ada
```

### Struktur Form
**Panel Utama:** `BorderLayout`

| Posisi | Komponen |
|--------|----------|
| NORTH | Judul "TAMBAH BARANG BARU" |
| CENTER | Form input |
| SOUTH | Tombol SIMPAN & RESET |

### Form Input (Baris 65-96)
**Layout:** `GridLayout(8, 2, 10, 10)`
- 8 baris, 2 kolom
- Jarak antar komponen: 10 pixel

| Label | Field | Keterangan |
|-------|-------|------------|
| Jenis Barang | JComboBox | Pilihan: Pakaian / Elektronik |
| Kode | JTextField | Kode unik barang |
| Nama | JTextField | Nama barang |
| Stok | JTextField | Jumlah stok (angka) |
| Harga | JTextField | Harga barang (angka) |
| Dinamis 1 | field1 | Ukuran (Pakaian) / Garansi (Elektronik) |
| Dinamis 2 | field2 | Bahan (Pakaian) / hidden (Elektronik) |

### Perubahan Label Dinamis (Baris 98-109)
```java
jenisCombo.addActionListener(e -> {
    String pilihan = (String) jenisCombo.getSelectedItem();
    
    if (pilihan.equals("Pakaian")) {
        label1.setText("Ukuran:");
        label2.setText("Bahan:");
        field2.setVisible(true);   // Tampilkan field bahan
        label2.setVisible(true);
    } else {  // Elektronik
        label1.setText("Garansi:");
        label2.setText("");
        field2.setVisible(false);  // Sembunyikan field bahan
        label2.setVisible(false);
    }
});
```

### Tombol SIMPAN (Baris 128-157)
```java
simpanBtn.addActionListener(e -> {
    try {
        // Ambil data dari field
        String kode = kodeField.getText();
        String nama = namaField.getText();
        int stok = Integer.parseInt(stokField.getText());      // String ke int
        double harga = Double.parseDouble(hargaField.getText()); // String ke double
        String pilihan = (String) jenisCombo.getSelectedItem();
        
        if (pilihan.equals("Pakaian")) {
            String ukuran = field1.getText();
            String bahan = field2.getText();
            inventori.tambahBarang(new Pakaian(kode, nama, stok, harga, ukuran, bahan));
        } else {
            String garansi = field1.getText();
            inventori.tambahBarang(new Elektronik(kode, nama, stok, harga, garansi));
        }
        
        JOptionPane.showMessageDialog(this, "Barang berhasil ditambahkan!");
        
        // Kosongkan semua field
        kodeField.setText("");
        namaField.setText("");
        stokField.setText("");
        hargaField.setText("");
        field1.setText("");
        field2.setText("");
        
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: Periksa input Anda!");
    }
});
```

### Tombol RESET (Baris 159-167)
```java
resetBtn.addActionListener(e -> {
    kodeField.setText("");
    namaField.setText("");
    stokField.setText("");
    hargaField.setText("");
    field1.setText("");
    field2.setText("");
    jenisCombo.setSelectedIndex(0);  // Kembali ke pilihan pertama
});
```

### Refresh Tampilan (Baris 169-171)
```java
contentPanel.add(mainPanel);
contentPanel.revalidate();  // Update layout
contentPanel.repaint();     // Gambar ulang tampilan
```

---

## 3. Tabel Daftar Barang (Baris 174-223)

### Struktur Tabel
**Panel Utama:** `BorderLayout`

| Posisi | Komponen |
|--------|----------|
| NORTH | Judul "DAFTAR SEMUA BARANG" |
| CENTER | Tabel dengan scroll |
| SOUTH | Tombol REFRESH |

### Persiapan Data Tabel (Baris 184-204)
```java
String[] kolom = {"Kode", "Nama", "Stok", "Harga", "Jenis", "Detail"};
String[][] data = new String[inventori.getListBarang().size()][6];

int i = 0;
for (Barang b : inventori.getListBarang()) {
    data[i][0] = b.getKode();                           // Kolom 0: Kode
    data[i][1] = b.getNama();                           // Kolom 1: Nama
    data[i][2] = String.valueOf(b.getStok());           // Kolom 2: Stok
    data[i][3] = String.valueOf(b.getHarga());          // Kolom 3: Harga
    
    // Cek tipe objek dengan instanceof
    if (b instanceof Pakaian) {
        Pakaian p = (Pakaian) b;  // Cast ke tipe Pakaian
        data[i][4] = "Pakaian";
        data[i][5] = "Ukuran: " + p.getUkuran() + ", Bahan: " + p.getBahan();
    } else if (b instanceof Elektronik) {
        Elektronik e = (Elektronik) b;  // Cast ke tipe Elektronik
        data[i][4] = "Elektronik";
        data[i][5] = "Garansi: " + e.getGaransi();
    }
    i++;
}
```

### Buat Tabel (Baris 206-209)
```java
JTable tabel = new JTable(data, kolom);
tabel.setFont(new Font("Arial", Font.PLAIN, 12));
tabel.setRowHeight(25);
tabel.setEnabled(false);  // Read-only (tidak bisa diedit)
```

### JScrollPane (Baris 211-212)
```java
JScrollPane scroll = new JScrollPane(tabel);
// Memungkinkan scroll jika data melebihi area tampilan
```

---

## 4. Form Update Stok (Baris 225-317)

### Struktur Form
**Panel Utama:** `BorderLayout`

| Posisi | Komponen |
|--------|----------|
| NORTH | Judul "UPDATE STOK BARANG" |
| CENTER | Form input |
| SOUTH | Text area info barang |

### Form Input (Baris 236-252)
**Layout:** `GridLayout(3, 2, 10, 10)`

| Label | Field |
|-------|-------|
| Kode Barang | kodeField |
| Stok Baru | stokField |
| CARI | UPDATE |

### Tombol CARI (Baris 264-295)
```java
cariBtn.addActionListener(e -> {
    String kode = kodeField.getText().trim();
    infoArea.setText("");  // Kosongkan text area
    
    boolean found = false;
    for (Barang b : inventori.getListBarang()) {
        if (b.getKode().equalsIgnoreCase(kode)) {  // Cek kode (ignore case)
            infoArea.append("Kode: " + b.getKode() + "\n");
            infoArea.append("Nama: " + b.getNama() + "\n");
            infoArea.append("Stok Saat Ini: " + b.getStok() + "\n");
            infoArea.append("Harga: " + b.getHarga() + "\n");
            
            if (b instanceof Pakaian) {
                Pakaian p = (Pakaian) b;
                infoArea.append("Jenis: Pakaian\n");
                infoArea.append("Ukuran: " + p.getUkuran() + "\n");
                infoArea.append("Bahan: " + p.getBahan() + "\n");
            } else if (b instanceof Elektronik) {
                Elektronik el = (Elektronik) b;
                infoArea.append("Jenis: Elektronik\n");
                infoArea.append("Garansi: " + el.getGaransi() + "\n");
            }
            
            found = true;
            break;  // Stop loop setelah ketemu
        }
    }
    
    if (!found) {
        infoArea.setText("Barang dengan kode '" + kode + "' tidak ditemukan!");
    }
});
```

### Tombol UPDATE (Baris 297-312)
```java
updateBtn.addActionListener(e -> {
    try {
        String kode = kodeField.getText().trim();
        int stokBaru = Integer.parseInt(stokField.getText().trim());
        
        inventori.ubahStok(kode, stokBaru);  // Panggil method update stok
        JOptionPane.showMessageDialog(this, "Stok berhasil diupdate!");
        
        // Kosongkan field
        kodeField.setText("");
        stokField.setText("");
        infoArea.setText("");
        
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Error: Stok harus berupa angka!");
    }
});
```

---

## 5. Main Method (Baris 319-321)

```java
public static void main(String[] args) {
    new InventoriGUI();  // Jalankan aplikasi
}
```

---

## Alur Kerja Aplikasi

```
1. Program Jalan
   ↓
2. Buat GUI dan tampilkan menu utama
   ↓
3. User klik menu
   ↓
4. contentPanel.removeAll() → Hapus tampilan lama
   ↓
5. Tampilkan form baru (Tambah / Lihat / Update)
   ↓
6. User input data
   ↓
7. Simpan ke objek inventori
   ↓
8. revalidate() + repaint() → Update tampilan
   ↓
9. Kembali ke langkah 3 (loop)
```

---

## Komponen Penting

### Layout Managers

| Layout | Kegunaan |
|--------|----------|
| BorderLayout | Mengatur posisi: NORTH, CENTER, SOUTH, EAST, WEST |
| FlowLayout | Komponen berjejer dari kiri ke kanan |
| GridLayout | Grid dengan jumlah baris dan kolom tetap |

### Komponen Swing

| Komponen | Fungsi |
|----------|--------|
| JFrame | Jendela utama aplikasi |
| JPanel | Panel/container untuk komponen lain |
| JButton | Tombol yang bisa diklik |
| JTextField | Input teks satu baris |
| JComboBox | Dropdown untuk memilih opsi |
| JLabel | Teks statis |
| JTable | Tabel data |
| JTextArea | Area teks banyak baris |
| JScrollPane | Scroll panel untuk konten yang panjang |

### Fitur Lainnya

- **Lambda Expression**: `e -> kode` - cara singkat membuat ActionListener
- **instanceof**: Cek tipe objek (`b instanceof Pakaian`)
- **Type Casting**: `(Pakaian) b` - ubah tipe referensi
- **try-catch**: Menangani error input
- **equalsIgnoreCase**: Banding string tanpa peduli besar kecil huruf
