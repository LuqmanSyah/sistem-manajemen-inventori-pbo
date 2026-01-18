import java.util.ArrayList;

public class Inventori {

    private ArrayList<Barang> listBarang = new ArrayList<>();

    public void tambahBarang(Barang b) {
        listBarang.add(b);
        System.out.println("Barang berhasil ditambahkan");
    }

    public void tampilkanSemua() {
        if (listBarang.isEmpty()) {
            System.out.println("Inventori kosong");
        } else {
            for (Barang b : listBarang) {
                b.tampilkanInfo();
            }
        }
    }

    public void ubahStok(String kode, int stokBaru) {
        for (Barang b : listBarang) {
            if (b.getKode().equalsIgnoreCase(kode)) {
                b.setStok(stokBaru);
                System.out.println("Stok berhasil diupdate");
                return;
            }
        }
        System.out.println("Barang tidak ditemukan");
    }

    public ArrayList<Barang> getListBarang() {
        return listBarang;
    }
}
