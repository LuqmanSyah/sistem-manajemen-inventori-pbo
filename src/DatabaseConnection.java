import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection = null;

    // Singleton pattern untuk koneksi database
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Load PostgreSQL JDBC Driver
                Class.forName(DatabaseConfig.DB_DRIVER);
                
                // Buat koneksi ke database
                connection = DriverManager.getConnection(
                    DatabaseConfig.DB_URL,
                    DatabaseConfig.DB_USER,
                    DatabaseConfig.DB_PASSWORD
                );
                
                System.out.println("Koneksi database berhasil!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver tidak ditemukan!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Gagal terhubung ke database!");
            e.printStackTrace();
        }
        
        return connection;
    }

    // Method untuk menutup koneksi
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Koneksi database ditutup.");
            }
        } catch (SQLException e) {
            System.err.println("Gagal menutup koneksi database!");
            e.printStackTrace();
        }
    }

    // Method untuk test koneksi
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
