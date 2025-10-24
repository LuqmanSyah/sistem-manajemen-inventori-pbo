public class Barang {
  private String id;
  private String name;
  private int qty;

  public void setBarang(String id, String name, int qty) {
    this.id = id;
    this.name = name;
    this.qty = qty;
  }

  public void getBarang() {
    System.out.println("\nId barang: " + this.id);
    System.out.println("Nama barang: " + this.name);
    System.out.println("Jumlah barang: " + this.qty);
  }
}
