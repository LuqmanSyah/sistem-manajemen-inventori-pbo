-- Database Schema untuk Sistem Manajemen Inventori

-- Buat database (jalankan sebagai superuser)
-- CREATE DATABASE inventori_db;

-- Koneksi ke database inventori_db
-- \c inventori_db

-- Tabel untuk menyimpan data barang
CREATE TABLE IF NOT EXISTS barang (
    id SERIAL PRIMARY KEY,
    kode VARCHAR(50) UNIQUE NOT NULL,
    nama VARCHAR(255) NOT NULL,
    stok INTEGER NOT NULL DEFAULT 0,
    harga DECIMAL(15, 2) NOT NULL,
    jenis VARCHAR(50) NOT NULL, -- 'ELEKTRONIK' atau 'PAKAIAN'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabel untuk menyimpan detail elektronik
CREATE TABLE IF NOT EXISTS elektronik (
    id SERIAL PRIMARY KEY,
    barang_id INTEGER UNIQUE NOT NULL,
    garansi VARCHAR(100),
    FOREIGN KEY (barang_id) REFERENCES barang(id) ON DELETE CASCADE
);

-- Tabel untuk menyimpan detail pakaian
CREATE TABLE IF NOT EXISTS pakaian (
    id SERIAL PRIMARY KEY,
    barang_id INTEGER UNIQUE NOT NULL,
    ukuran VARCHAR(10),
    bahan VARCHAR(100),
    FOREIGN KEY (barang_id) REFERENCES barang(id) ON DELETE CASCADE
);

-- Index untuk performa lebih baik
CREATE INDEX IF NOT EXISTS idx_barang_kode ON barang(kode);
CREATE INDEX IF NOT EXISTS idx_barang_jenis ON barang(jenis);

-- Trigger untuk update timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_barang_updated_at
    BEFORE UPDATE ON barang
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Insert data contoh
INSERT INTO barang (kode, nama, stok, harga, jenis) VALUES
    ('EL001', 'Laptop ASUS', 10, 15000000, 'ELEKTRONIK'),
    ('EL002', 'Mouse Logitech', 25, 150000, 'ELEKTRONIK'),
    ('PK001', 'Kemeja Putih', 50, 120000, 'PAKAIAN'),
    ('PK002', 'Celana Jeans', 30, 250000, 'PAKAIAN')
ON CONFLICT (kode) DO NOTHING;

INSERT INTO elektronik (barang_id, garansi) VALUES
    ((SELECT id FROM barang WHERE kode = 'EL001'), '2 Tahun'),
    ((SELECT id FROM barang WHERE kode = 'EL002'), '1 Tahun')
ON CONFLICT (barang_id) DO NOTHING;

INSERT INTO pakaian (barang_id, ukuran, bahan) VALUES
    ((SELECT id FROM barang WHERE kode = 'PK001'), 'L', 'Katun'),
    ((SELECT id FROM barang WHERE kode = 'PK002'), 'M', 'Denim')
ON CONFLICT (barang_id) DO NOTHING;
