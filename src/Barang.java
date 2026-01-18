public class Barang {

    private String kode;
    private String nama;
    private int stok;
    private double harga;

    public Barang(String kode, String nama, int stok, double harga) {
        this.kode = kode;
        this.nama = nama;
        this.stok = stok;
        this.harga = harga;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }

    public void tampilkanInfo() {
        System.out.print(
            "Kode: " +
                kode +
                " | Nama: " +
                nama +
                " | Stok: " +
                stok +
                " | Harga: " +
                harga
        );
    }
}
