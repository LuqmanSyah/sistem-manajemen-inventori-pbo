class Kategori {
  protected String kategori;

  public Kategori() {}
}

public class Barang extends Kategori {
  private String id;
  private String name;
  private int qty;

  public void setBarang(String id, String name, int qty, String k) {
    this.id = id;
    this.name = name;
    this.qty = qty;
    kategori = k;
  }

  public void getBarang() {
    System.out.println("\nDetail Barang");
    System.out.println("Id barang: " + this.id);
    System.out.println("Nama barang: " + this.name);
    System.out.println("Jumlah barang: " + this.qty);
    System.out.println("Kategori barang: " + kategori);
  }
}
