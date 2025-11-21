import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        double toMB = 1024.0 * 1024.0;

        long totalBefore = runtime.totalMemory();
        long freeBefore = runtime.freeMemory();
        long usedBefore = totalBefore - freeBefore;
        System.out.printf("Total memori awal: %.2f MB%n", totalBefore / toMB);
        System.out.printf("Free memori awal: %.2f MB%n", freeBefore / toMB);
        System.out.printf("Memori terpakai awal: %.2f MB%n", usedBefore / toMB);

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
            input.nextLine();
            System.out.print("Masukkan Kategori Barang: ");
            String k = input.nextLine();
            
            switch (menu) {
            case 1:
                barang.setBarang(id, name, qty, k);
                barang.getBarang();
                break;
            default:
                System.out.println("Menu tidak valid!");
            }
        } else {
            System.out.println("Input tidak valid");
        }

        long totalAfter = runtime.totalMemory();
        long freeAfter = runtime.freeMemory();
        long usedAfter = totalAfter - freeAfter;
        System.out.printf("%nTotal memori akhir: %.2f MB%n", totalAfter / toMB);
        System.out.printf("Free memori akhir: %.2f MB%n", freeAfter / toMB);
        System.out.printf("Memori terpakai akhir: %.2f MB%n", usedAfter / toMB);
    
        long beforeGC = runtime.freeMemory();
        System.gc();
        long afterGC = runtime.freeMemory();

        System.out.printf("%nMemori bebas sebelum gc: %.2f%n", beforeGC / toMB);
        System.out.printf("Memori bebas setelah gc: %.2f%n", afterGC/ toMB);

        input.close();
    }
}