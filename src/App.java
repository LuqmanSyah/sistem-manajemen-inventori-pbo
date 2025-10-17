import java.util.Scanner;

public class App {
    public static void createBarang(Scanner input, String[] ids, String[] names, int[] qtys, int count) {
        if (count >= 100) {
            System.out.println("Data barang penuh!");
            return;
        }
        System.out.print("Masukkan ID barang: ");
        ids[count] = input.nextLine();
        System.out.print("Masukkan nama barang: ");
        names[count] = input.nextLine();
        System.out.print("Masukkan jumlah barang: ");
        qtys[count] = input.nextInt();
        input.nextLine(); // konsumsi enter
        System.out.println("\nData barang berhasil ditambahkan! \n");

        System.out.println("Id barang: " + ids[0]);
        System.out.println("Nama barang: " + names[0]);
        System.out. println("Jumlah barang: " + qtys[0]);
    }

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
        if (input.hasNextInt()) {
            int menu = input.nextInt();
            input.nextLine(); // konsumsi enter

            switch (menu) {
            case 1:
                createBarang(input, ids, names, qtys, count);
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