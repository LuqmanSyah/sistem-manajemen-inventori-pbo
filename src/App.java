import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Barang barang = new Barang();
        
        System.out.println("\n=== Menu ===");
        System.out.println("1. Create (Tambah Data Barang)");
        System.out.print("Pilih menu: ");
        if (input.hasNextInt()) { // Menambahkan validasi input
            int menu = input.nextInt();
            input.nextLine(); // Membersihkan newline setelah nextInt

            System.out.print("Masukkan ID Barang: ");
            String id = input.nextLine();
            System.out.print("Masukkan Nama Barang: ");
            String name = input.nextLine();
            System.out.print("Masukkan Jumlah Barang: ");
            int qty = input.nextInt();
            
            switch (menu) {
            case 1:
                barang.setBarang(id, name, qty);
                barang.getBarang();
                break;
            default:
                System.out.println("Menu tidak valid!");
            }
        } else {
            System.out.println("Input tidak valid");
        }
    
        input.close();
    }
}