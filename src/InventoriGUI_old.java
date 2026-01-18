import javax.swing.*;
import java.awt.*;

public class InventoriGUI_old extends JFrame {
    private Inventori inventori;
    private JPanel contentPanel;

    public InventoriGUI_old() {
        setTitle("Sistem Inventori");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new Color(100, 149, 237));

        JButton btnTambah = new JButton("Tambah Barang");
        JButton btnLihat = new JButton("Lihat Barang");
        JButton btnUpdate = new JButton("Update Stok");
        JButton btnKeluar = new JButton("Keluar");

        btnTambah.addActionListener(e -> tampilkanFormTambah());
        btnLihat.addActionListener(e -> tampilkanTabel());
        btnUpdate.addActionListener(e -> tampilkanFormUpdate());
        btnKeluar.addActionListener(e -> System.exit(0));

        menuPanel.add(btnTambah);
        menuPanel.add(btnLihat);
        menuPanel.add(btnUpdate);
        menuPanel.add(btnKeluar);

        add(menuPanel, BorderLayout.NORTH);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Pilih menu diatas untuk memulai");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(welcomeLabel);

        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void tampilkanFormTambah() {
        JOptionPane.showMessageDialog(this, "Form Tambah Barang");
    }

    private void tampilkanTabel() {
        JOptionPane.showMessageDialog(this, "Tabel akan dibuat");
    }

    private void tampilkanFormUpdate() {
        JOptionPane.showMessageDialog(this, "Form Update akan dibuat");
    }

    public static void main(String[] args) {
        new InventoriGUI_old();
    }
}
