import java.sql.*;
import java.util.ArrayList;

public class InventoriDAO {
    
    // Method untuk menambah barang elektronik
    public boolean tambahElektronik(Elektronik elektronik) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return false;
        
        try {
            conn.setAutoCommit(false);
            
            // Insert ke tabel barang
            String sqlBarang = "INSERT INTO barang (kode, nama, stok, harga, jenis) VALUES (?, ?, ?, ?, 'ELEKTRONIK')";
            PreparedStatement pstmtBarang = conn.prepareStatement(sqlBarang, Statement.RETURN_GENERATED_KEYS);
            pstmtBarang.setString(1, elektronik.getKode());
            pstmtBarang.setString(2, elektronik.getNama());
            pstmtBarang.setInt(3, elektronik.getStok());
            pstmtBarang.setDouble(4, elektronik.getHarga());
            pstmtBarang.executeUpdate();
            
            // Dapatkan ID barang yang baru ditambahkan
            ResultSet rs = pstmtBarang.getGeneratedKeys();
            int barangId = 0;
            if (rs.next()) {
                barangId = rs.getInt(1);
            }
            
            // Insert ke tabel elektronik
            String sqlElektronik = "INSERT INTO elektronik (barang_id, garansi) VALUES (?, ?)";
            PreparedStatement pstmtElektronik = conn.prepareStatement(sqlElektronik);
            pstmtElektronik.setInt(1, barangId);
            pstmtElektronik.setString(2, elektronik.getGaransi());
            pstmtElektronik.executeUpdate();
            
            conn.commit();
            System.out.println("Barang elektronik berhasil ditambahkan ke database");
            return true;
            
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.err.println("Gagal menambahkan barang elektronik: " + e.getMessage());
            return false;
        }
    }
    
    // Method untuk menambah barang pakaian
    public boolean tambahPakaian(Pakaian pakaian) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return false;
        
        try {
            conn.setAutoCommit(false);
            
            // Insert ke tabel barang
            String sqlBarang = "INSERT INTO barang (kode, nama, stok, harga, jenis) VALUES (?, ?, ?, ?, 'PAKAIAN')";
            PreparedStatement pstmtBarang = conn.prepareStatement(sqlBarang, Statement.RETURN_GENERATED_KEYS);
            pstmtBarang.setString(1, pakaian.getKode());
            pstmtBarang.setString(2, pakaian.getNama());
            pstmtBarang.setInt(3, pakaian.getStok());
            pstmtBarang.setDouble(4, pakaian.getHarga());
            pstmtBarang.executeUpdate();
            
            // Dapatkan ID barang yang baru ditambahkan
            ResultSet rs = pstmtBarang.getGeneratedKeys();
            int barangId = 0;
            if (rs.next()) {
                barangId = rs.getInt(1);
            }
            
            // Insert ke tabel pakaian
            String sqlPakaian = "INSERT INTO pakaian (barang_id, ukuran, bahan) VALUES (?, ?, ?)";
            PreparedStatement pstmtPakaian = conn.prepareStatement(sqlPakaian);
            pstmtPakaian.setInt(1, barangId);
            pstmtPakaian.setString(2, pakaian.getUkuran());
            pstmtPakaian.setString(3, pakaian.getBahan());
            pstmtPakaian.executeUpdate();
            
            conn.commit();
            System.out.println("Barang pakaian berhasil ditambahkan ke database");
            return true;
            
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.err.println("Gagal menambahkan barang pakaian: " + e.getMessage());
            return false;
        }
    }
    
    // Method untuk mengambil semua barang dari database
    public ArrayList<Barang> getAllBarang() {
        ArrayList<Barang> listBarang = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return listBarang;
        
        try {
            String sql = "SELECT b.*, e.garansi, p.ukuran, p.bahan " +
                        "FROM barang b " +
                        "LEFT JOIN elektronik e ON b.id = e.barang_id " +
                        "LEFT JOIN pakaian p ON b.id = p.barang_id " +
                        "ORDER BY b.id";
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                String jenis = rs.getString("jenis");
                
                if ("ELEKTRONIK".equals(jenis)) {
                    Elektronik elektronik = new Elektronik(
                        rs.getString("kode"),
                        rs.getString("nama"),
                        rs.getInt("stok"),
                        rs.getDouble("harga"),
                        rs.getString("garansi")
                    );
                    listBarang.add(elektronik);
                } else if ("PAKAIAN".equals(jenis)) {
                    Pakaian pakaian = new Pakaian(
                        rs.getString("kode"),
                        rs.getString("nama"),
                        rs.getInt("stok"),
                        rs.getDouble("harga"),
                        rs.getString("ukuran"),
                        rs.getString("bahan")
                    );
                    listBarang.add(pakaian);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Gagal mengambil data barang: " + e.getMessage());
        }
        
        return listBarang;
    }
    
    // Method untuk update stok barang
    public boolean updateStok(String kode, int stokBaru) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return false;
        
        try {
            String sql = "UPDATE barang SET stok = ? WHERE kode = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, stokBaru);
            pstmt.setString(2, kode);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Stok berhasil diupdate");
                return true;
            } else {
                System.out.println("Barang dengan kode " + kode + " tidak ditemukan");
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("Gagal update stok: " + e.getMessage());
            return false;
        }
    }
    
    // Method untuk menghapus barang berdasarkan kode
    public boolean hapusBarang(String kode) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return false;
        
        try {
            String sql = "DELETE FROM barang WHERE kode = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, kode);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Barang berhasil dihapus");
                return true;
            } else {
                System.out.println("Barang dengan kode " + kode + " tidak ditemukan");
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("Gagal menghapus barang: " + e.getMessage());
            return false;
        }
    }
    
    // Method untuk mencari barang berdasarkan kode
    public Barang cariBarang(String kode) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return null;
        
        try {
            String sql = "SELECT b.*, e.garansi, p.ukuran, p.bahan " +
                        "FROM barang b " +
                        "LEFT JOIN elektronik e ON b.id = e.barang_id " +
                        "LEFT JOIN pakaian p ON b.id = p.barang_id " +
                        "WHERE b.kode = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, kode);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String jenis = rs.getString("jenis");
                
                if ("ELEKTRONIK".equals(jenis)) {
                    return new Elektronik(
                        rs.getString("kode"),
                        rs.getString("nama"),
                        rs.getInt("stok"),
                        rs.getDouble("harga"),
                        rs.getString("garansi")
                    );
                } else if ("PAKAIAN".equals(jenis)) {
                    return new Pakaian(
                        rs.getString("kode"),
                        rs.getString("nama"),
                        rs.getInt("stok"),
                        rs.getDouble("harga"),
                        rs.getString("ukuran"),
                        rs.getString("bahan")
                    );
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Gagal mencari barang: " + e.getMessage());
        }
        
        return null;
    }
}
