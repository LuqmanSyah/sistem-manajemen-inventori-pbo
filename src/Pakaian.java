public class Pakaian extends Barang {

    private String ukuran;
    private String bahan;

    public Pakaian(
        String kode,
        String nama,
        int stok,
        double harga,
        String ukuran,
        String bahan
    ) {
        super(kode, nama, stok, harga);
        this.ukuran = ukuran;
        this.bahan = bahan;
    }

    public String getUkuran() {
        return ukuran;
    }

    public String getBahan() {
        return bahan;
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println(" | Ukuran: " + ukuran + " | Bahan: " + bahan);
    }
}
