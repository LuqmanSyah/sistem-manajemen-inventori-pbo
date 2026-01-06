import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Inventori inv = new Inventori();
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;

        while (loop) {
            System.out.println("\nMenu Inventori:");
            System.out.println("1. Tambah Barang");
            System.out.println("2. Lihat Semua Barang");
            System.out.println("3. Update Stok");
            System.out.println("4. Keluar");
            System.out.print("Pilih: ");
            int menu = scanner.nextInt();
            scanner.nextLine();

            switch (menu) {
                case 1:
                    System.out.print("Jenis Barang (1. Pakaian, 2. Elektronik): ");
                    int jenis = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Kode: ");
                    String kode = scanner.nextLine();
                    System.out.print("Nama: ");
                    String nama = scanner.nextLine();
                    System.out.print("Stok: ");
                    int stok = scanner.nextInt();
                    System.out.print("Harga: ");
                    double harga = scanner.nextDouble();
                    if (jenis == 1) {
                        scanner.nextLine();
                        System.out.print("Ukuran: ");
                        String ukuran = scanner.nextLine();
                        System.out.print("Bahan: ");
                        String bahan = scanner.nextLine();
                        inv.tambahBarang(new Pakaian(kode, nama, stok, harga, ukuran, bahan));
                        break;
                    } else if (jenis == 2) {
                        scanner.nextLine();
                        System.out.print("Garansi: ");
                        String garansi = scanner.nextLine();
                        inv.tambahBarang(new Elektronik(kode, nama, stok, harga, garansi));
                        break;
                    }
                    break;
                case 2:
                    inv.tampilkanSemua();
                    break;
                case 3:
                    System.out.print("Kode Barang: ");
                    String k = scanner.nextLine();
                    System.out.print("Stok Baru: ");
                    int s  = scanner.nextInt();
                    inv.ubahStok(k, s);
                    break;
                case 4:
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        scanner.close();
    }
}
