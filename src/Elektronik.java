public class Elektronik extends Barang {

    private String garansi;

    public Elektronik(
        String kode,
        String nama,
        int stok,
        double harga,
        String garansi
    ) {
        super(kode, nama, stok, harga);
        this.garansi = garansi;
    }

    public String getGaransi() {
        return garansi;
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println(" | Garansi: " + garansi);
    }
}
