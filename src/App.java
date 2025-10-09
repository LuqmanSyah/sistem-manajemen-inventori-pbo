import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Input data barang
        System.out.print("Masukkan ID barang: ");
        String id = input.nextLine();

        System.out.print("Masukkan nama barang: ");
        String name = input.nextLine();

        System.out.print("Masukkan jumlah barang: ");
        int qty = input.nextInt();

        // Tampilkan hasil
        System.out.println("\n=== Data Barang ===");
        System.out.println("ID: " + id);
        System.out.println("Nama: " + name);
        System.out.println("Jumlah: " + qty);

        input.close();
    }
}