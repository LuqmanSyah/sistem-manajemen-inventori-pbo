// Import library util untuk List dan Scanner
import java.util.*;

// Kelas utama aplikasi inventori
public class App {
    // Kelas Item
    // Kelas Item: merepresentasikan barang
    static class Item {
    // Variabel: id barang (String)
    private String id;
    // Variabel: nama barang (String)
    private String name;
    // Variabel: jumlah barang (int)
    private int quantity;

    // Konstruktor Item
    public Item(String id, String name, int quantity) {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
        }

    // Method getter id
    public String getId() { return id; }
    // Method getter nama
    public String getName() { return name; }
    // Method getter jumlah
    public int getQuantity() { return quantity; }
    // Method setter jumlah
    public void setQuantity(int quantity) { this.quantity = quantity; }

        // Method untuk menampilkan info barang
        @Override
        public String toString() {
            // Ekspresi format string
            return String.format("ID: %s | Nama: %s | Jumlah: %d", id, name, quantity);
        }
    }

    // Kelas InventoryManager
    // Kelas InventoryManager: mengelola daftar barang
    static class InventoryManager {
    // Variabel: daftar barang (List<Item>)
    private List<Item> items = new ArrayList<>();

    // Method untuk menambah barang
    public void addItem(Item item) { items.add(item); }
        // Method untuk mengambil semua barang
        public List<Item> getAllItems() { return items; }
    }

    // Method utama aplikasi
    public static void main(String[] args) {
    // Variabel: objek InventoryManager
    InventoryManager manager = new InventoryManager();
    // Variabel: objek Scanner untuk input
    Scanner scanner = new Scanner(System.in);
    // Ekspresi perulangan utama aplikasi
    while (true) {
            // Ekspresi tampilan menu
            System.out.println("\n=== Sistem Inventori Barang ===");
            System.out.println("1. Tambah Barang");
            System.out.println("2. Tampilkan Semua Barang");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu: ");
            // Variabel: pilihan menu (String)
            String pilihan = scanner.nextLine();
            // Ekspresi switch untuk menu
            switch (pilihan) {
                case "1":
                    // Input data barang
                    System.out.print("Masukkan ID barang: ");
                    String id = scanner.nextLine(); // Variabel id
                    System.out.print("Masukkan nama barang: ");
                    String name = scanner.nextLine(); // Variabel name
                    System.out.print("Masukkan jumlah barang: ");
                    int qty; // Variabel qty
                    try {
                        // Ekspresi parsing input ke integer
                        qty = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Jumlah harus angka!");
                        break;
                    }
                    // Ekspresi menambah barang ke inventori
                    manager.addItem(new Item(id, name, qty));
                    System.out.println("Barang berhasil ditambah.");
                    break;
                case "2":
                    // Ekspresi menampilkan semua barang
                    System.out.println("\nDaftar Barang:");
                    for (Item item : manager.getAllItems()) {
                        System.out.println(item);
                    }
                    break;
                case "3":
                    // Ekspresi keluar aplikasi
                    System.out.println("Keluar aplikasi.");
                    scanner.close();
                    return;
                default:
                    // Ekspresi menu tidak valid
                    System.out.println("Menu tidak valid.");
            }
        }
    }
}
