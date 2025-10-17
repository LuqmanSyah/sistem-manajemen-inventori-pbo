import java.util.Scanner;

public class properti_barang {
  private String id;
  private String nama;
  private int jumlah;

  void inputBarang() {
    Scanner input = new Scanner(System.in);

    System.out.print("Masukkan id barang: ");
    String id = input.nextLine();
    System.out.print("Masukkan nama barang: ");
    String nama = input.nextLine(); 
    System.out.print("Masukkan jumlah barang: ");
    int jumlah = input.nextInt();

    setBarang(id, nama, jumlah);
  }

  void setBarang(String id, String nama, int jumlah) {
    this.id = id;
    this.nama = nama;
    this.jumlah = jumlah;
  }

  void tampilBarang() {
    System.out.println("id barang: " + this.id);
    System.out.println("nama barang: " + this.nama);
    System.out.println("jumlah barang: " + this.jumlah);
  }
}
