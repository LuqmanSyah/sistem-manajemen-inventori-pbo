import javax.swing.*;
import java.awt.*;

public class InventoriGUI extends JFrame {
    
    private Inventori inventori;
    private JPanel contentPanel;
    
    public InventoriGUI() {
        inventori = new Inventori();
        
        setTitle("Sistem Inventori");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Menu Panel - FlowLayout
        JPanel menuPanel = new JPanel(new FlowLayout());
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
        
        // Content Panel - BorderLayout
        contentPanel = new JPanel(new BorderLayout());
        
        JLabel welcomeLabel = new JLabel("Pilih menu di atas untuk memulai");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        contentPanel.add(welcomeLabel);
        
        add(contentPanel, BorderLayout.CENTER);
        
        setVisible(true);
    }
    
    private void tampilkanFormTambah() {
        contentPanel.removeAll();
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        
        // Judul
        JPanel judulPanel = new JPanel();
        JLabel judulLabel = new JLabel("TAMBAH BARANG BARU");
        judulLabel.setFont(new Font("Arial", Font.BOLD, 18));
        judulPanel.add(judulLabel);
        mainPanel.add(judulPanel, BorderLayout.NORTH);
        
        // Form pakai GridLayout
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        
        formPanel.add(new JLabel("Jenis Barang:"));
        String[] jenis = {"Pakaian", "Elektronik"};
        JComboBox<String> jenisCombo = new JComboBox<>(jenis);
        formPanel.add(jenisCombo);
        
        formPanel.add(new JLabel("Kode:"));
        JTextField kodeField = new JTextField();
        formPanel.add(kodeField);
        
        formPanel.add(new JLabel("Nama:"));
        JTextField namaField = new JTextField();
        formPanel.add(namaField);
        
        formPanel.add(new JLabel("Stok:"));
        JTextField stokField = new JTextField();
        formPanel.add(stokField);
        
        formPanel.add(new JLabel("Harga:"));
        JTextField hargaField = new JTextField();
        formPanel.add(hargaField);
        
        JLabel label1 = new JLabel("Ukuran:");
        JTextField field1 = new JTextField();
        JLabel label2 = new JLabel("Bahan:");
        JTextField field2 = new JTextField();
        
        formPanel.add(label1);
        formPanel.add(field1);
        formPanel.add(label2);
        formPanel.add(field2);
        
        jenisCombo.addActionListener(e -> {
            String pilihan = (String) jenisCombo.getSelectedItem();
            if (pilihan.equals("Pakaian")) {
                label1.setText("Ukuran:");
                label2.setText("Bahan:");
            } else {
                label1.setText("Garansi:");
                label2.setText("");
            }
            field2.setVisible(pilihan.equals("Pakaian"));
            label2.setVisible(pilihan.equals("Pakaian"));
        });
        
        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel(""));
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton simpanBtn = new JButton("SIMPAN");
        JButton resetBtn = new JButton("RESET");
        
        simpanBtn.setBackground(new Color(46, 204, 113));
        simpanBtn.setForeground(Color.WHITE);
        
        buttonPanel.add(simpanBtn);
        buttonPanel.add(resetBtn);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        simpanBtn.addActionListener(e -> {
            try {
                String kode = kodeField.getText();
                String nama = namaField.getText();
                int stok = Integer.parseInt(stokField.getText());
                double harga = Double.parseDouble(hargaField.getText());
                String pilihan = (String) jenisCombo.getSelectedItem();
                
                if (pilihan.equals("Pakaian")) {
                    String ukuran = field1.getText();
                    String bahan = field2.getText();
                    inventori.tambahBarang(new Pakaian(kode, nama, stok, harga, ukuran, bahan));
                } else {
                    String garansi = field1.getText();
                    inventori.tambahBarang(new Elektronik(kode, nama, stok, harga, garansi));
                }
                
                JOptionPane.showMessageDialog(this, "Barang berhasil ditambahkan!");
                
                kodeField.setText("");
                namaField.setText("");
                stokField.setText("");
                hargaField.setText("");
                field1.setText("");
                field2.setText("");
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: Periksa input Anda!");
            }
        });
        
        resetBtn.addActionListener(e -> {
            kodeField.setText("");
            namaField.setText("");
            stokField.setText("");
            hargaField.setText("");
            field1.setText("");
            field2.setText("");
            jenisCombo.setSelectedIndex(0);
        });
        
        contentPanel.add(mainPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void tampilkanTabel() {
        contentPanel.removeAll();
        
        JPanel tabelPanel = new JPanel(new BorderLayout(10, 10));
        
        JLabel judulLabel = new JLabel("DAFTAR SEMUA BARANG");
        judulLabel.setFont(new Font("Arial", Font.BOLD, 18));
        judulLabel.setHorizontalAlignment(JLabel.CENTER);
        tabelPanel.add(judulLabel, BorderLayout.NORTH);
        
        String[] kolom = {"Kode", "Nama", "Stok", "Harga", "Jenis", "Detail"};
        String[][] data = new String[inventori.getListBarang().size()][6];
        
        int i = 0;
        for (Barang b : inventori.getListBarang()) {
            data[i][0] = b.getKode();
            data[i][1] = b.getNama();
            data[i][2] = String.valueOf(b.getStok());
            data[i][3] = String.valueOf(b.getHarga());
            
            if (b instanceof Pakaian) {
                Pakaian p = (Pakaian) b;
                data[i][4] = "Pakaian";
                data[i][5] = "Ukuran: " + p.getUkuran() + ", Bahan: " + p.getBahan();
            } else if (b instanceof Elektronik) {
                Elektronik e = (Elektronik) b;
                data[i][4] = "Elektronik";
                data[i][5] = "Garansi: " + e.getGaransi();
            }
            i++;
        }
        
        JTable tabel = new JTable(data, kolom);
        tabel.setFont(new Font("Arial", Font.PLAIN, 12));
        tabel.setRowHeight(25);
        tabel.setEnabled(false);
        
        JScrollPane scroll = new JScrollPane(tabel);
        tabelPanel.add(scroll, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton refreshBtn = new JButton("REFRESH");
        refreshBtn.addActionListener(e -> tampilkanTabel());
        buttonPanel.add(refreshBtn);
        tabelPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        contentPanel.add(tabelPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void tampilkanFormUpdate() {
        contentPanel.removeAll();
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        
        JPanel judulPanel = new JPanel();
        JLabel judulLabel = new JLabel("UPDATE STOK BARANG");
        judulLabel.setFont(new Font("Arial", Font.BOLD, 18));
        judulPanel.add(judulLabel);
        mainPanel.add(judulPanel, BorderLayout.NORTH);
        
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        
        formPanel.add(new JLabel("Kode Barang:"));
        JTextField kodeField = new JTextField();
        formPanel.add(kodeField);
        
        formPanel.add(new JLabel("Stok Baru:"));
        JTextField stokField = new JTextField();
        formPanel.add(stokField);
        
        JButton cariBtn = new JButton("CARI");
        JButton updateBtn = new JButton("UPDATE");
        updateBtn.setBackground(new Color(52, 152, 219));
        updateBtn.setForeground(Color.WHITE);
        
        formPanel.add(cariBtn);
        formPanel.add(updateBtn);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        JTextArea infoArea = new JTextArea(8, 30);
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Arial", Font.PLAIN, 12));
        infoArea.setBorder(BorderFactory.createTitledBorder("Informasi Barang"));
        
        JScrollPane scrollInfo = new JScrollPane(infoArea);
        mainPanel.add(scrollInfo, BorderLayout.SOUTH);
        
        cariBtn.addActionListener(e -> {
            String kode = kodeField.getText().trim();
            infoArea.setText("");
            
            boolean found = false;
            for (Barang b : inventori.getListBarang()) {
                if (b.getKode().equalsIgnoreCase(kode)) {
                    infoArea.append("Kode: " + b.getKode() + "\n");
                    infoArea.append("Nama: " + b.getNama() + "\n");
                    infoArea.append("Stok Saat Ini: " + b.getStok() + "\n");
                    infoArea.append("Harga: " + b.getHarga() + "\n");
                    
                    if (b instanceof Pakaian) {
                        Pakaian p = (Pakaian) b;
                        infoArea.append("Jenis: Pakaian\n");
                        infoArea.append("Ukuran: " + p.getUkuran() + "\n");
                        infoArea.append("Bahan: " + p.getBahan() + "\n");
                    } else if (b instanceof Elektronik) {
                        Elektronik el = (Elektronik) b;
                        infoArea.append("Jenis: Elektronik\n");
                        infoArea.append("Garansi: " + el.getGaransi() + "\n");
                    }
                    
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                infoArea.setText("Barang dengan kode '" + kode + "' tidak ditemukan!");
            }
        });
        
        updateBtn.addActionListener(e -> {
            try {
                String kode = kodeField.getText().trim();
                int stokBaru = Integer.parseInt(stokField.getText().trim());
                
                inventori.ubahStok(kode, stokBaru);
                JOptionPane.showMessageDialog(this, "Stok berhasil diupdate!");
                
                kodeField.setText("");
                stokField.setText("");
                infoArea.setText("");
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Stok harus berupa angka!");
            }
        });
        
        contentPanel.add(mainPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    public static void main(String[] args) {
        new InventoriGUI();
    }
}