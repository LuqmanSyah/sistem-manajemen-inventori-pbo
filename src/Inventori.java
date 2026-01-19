import java.util.ArrayList;

public class Inventori {

    private ArrayList<Barang> listBarang = new ArrayList<>();
    private InventoriDAO dao;

    public Inventori() {
        this.dao = new InventoriDAO();
    }

    public void tambahBarang(Barang b) {
        // Simpan ke database
        boolean berhasil = false;
        if (b instanceof Pakaian) {
            berhasil = dao.tambahPakaian((Pakaian) b);
        } else if (b instanceof Elektronik) {
            berhasil = dao.tambahElektronik((Elektronik) b);
        }

        if (berhasil) {
            listBarang.add(b);
            System.out.println("Barang berhasil ditambahkan ke database");
        } else {
            System.out.println("Gagal menambahkan barang ke database");
        }
    }

    public void tampilkanSemua() {
        // Load data dari database
        listBarang = dao.getAllBarang();

        if (listBarang.isEmpty()) {
            System.out.println("Inventori kosong");
        } else {
            for (Barang b : listBarang) {
                b.tampilkanInfo();
            }
        }
    }

    public void ubahStok(String kode, int stokBaru) {
        // Update di database
        boolean berhasil = dao.updateStok(kode, stokBaru);

        if (berhasil) {
            // Update juga di list lokal jika ada
            for (Barang b : listBarang) {
                if (b.getKode().equalsIgnoreCase(kode)) {
                    b.setStok(stokBaru);
                    break;
                }
            }
            System.out.println("Stok berhasil diupdate");
        } else {
            System.out.println("Barang tidak ditemukan");
        }
    }

    public ArrayList<Barang> getListBarang() {
        // Selalu load dari database untuk data terbaru
        listBarang = dao.getAllBarang();
        return listBarang;
    }
}
