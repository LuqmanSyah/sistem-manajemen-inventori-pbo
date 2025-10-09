import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Maksimal 100 barang
        String[] ids = new String[100];
        String[] names = new String[100];
        int[] qtys = new int[100];
        int count = 0;

        System.out.println("\n=== Menu ===");
        System.out.println("1. Create (Tambah Data Barang)");
        System.out.print("Pilih menu: ");
        int menu = input.nextInt();
        input.nextLine(); // konsumsi enter

        switch (menu) {
            case 1:
                if (count >= 100) {
                    System.out.println("Data barang penuh!");
                    break;
                }
                System.out.print("Masukkan ID barang: ");
                ids[count] = input.nextLine();
                System.out.print("Masukkan nama barang: ");
                names[count] = input.nextLine();
                System.out.print("Masukkan jumlah barang: ");
                qtys[count] = input.nextInt();
                input.nextLine(); // konsumsi enter
                count++;
                System.out.println("Data barang berhasil ditambahkan!");
                break;
            default:
                System.out.println("Menu tidak valid!");
        }

        input.close();
    }
}