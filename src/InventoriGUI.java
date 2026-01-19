import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;

public class InventoriGUI extends JFrame {

    private InventoriDAO dao;
    private JPanel contentPanel;

    // Modern UI Constants
    private static final Color PRIMARY_COLOR = new Color(52, 73, 94);
    private static final Color ACCENT_COLOR = new Color(52, 152, 219);
    private static final Color BG_COLOR = new Color(236, 240, 241);
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);

    public InventoriGUI() {
        // Setup LookAndFeel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            // Customizing Nimbus defaults for better colors
            UIManager.put("control", new Color(245, 245, 245));
            UIManager.put("nimbusBase", PRIMARY_COLOR);
            UIManager.put("nimbusBlueGrey", PRIMARY_COLOR);
            UIManager.put("nimbusFocus", ACCENT_COLOR);

        } catch (Exception e) {
            e.printStackTrace();
        }

        dao = new InventoriDAO();

        if (!DatabaseConnection.testConnection()) {
            JOptionPane.showMessageDialog(null,
                    "Gagal terhubung ke database!\nPastikan PostgreSQL sudah berjalan dan konfigurasi database sudah benar.",
                    "Error Koneksi Database",
                    JOptionPane.ERROR_MESSAGE);
        }

        setTitle("Sistem Manajemen Inventori Modern");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG_COLOR);

        // Menu Panel (SidebarStyle/Header)
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        menuPanel.setBackground(PRIMARY_COLOR);
        menuPanel.setBorder(new EmptyBorder(5, 10, 5, 10));

        JButton btnTambah = createStyledButton("Tambah Barang", "plus.png");
        JButton btnLihat = createStyledButton("Lihat Barang", "list.png");
        JButton btnUpdate = createStyledButton("Update Stok", "refresh.png");
        JButton btnKeluar = createStyledButton("Keluar", "logout.png");

        // Red color for logout
        btnKeluar.setBackground(new Color(231, 76, 60));
        btnKeluar.setForeground(Color.WHITE);

        btnTambah.addActionListener(e -> tampilkanFormTambah());
        btnLihat.addActionListener(e -> tampilkanTabel());
        btnUpdate.addActionListener(e -> tampilkanFormUpdate());
        btnKeluar.addActionListener(e -> System.exit(0));

        menuPanel.add(btnTambah);
        menuPanel.add(btnLihat);
        menuPanel.add(btnUpdate);
        menuPanel.add(btnKeluar);

        add(menuPanel, BorderLayout.NORTH);

        // Content Panel
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(BG_COLOR);

        JPanel welcomePanel = new JPanel(new GridBagLayout());
        welcomePanel.setBackground(BG_COLOR);

        JLabel welcomeLabel = new JLabel("Selamat Datang di Sistem Inventori");
        welcomeLabel.setFont(HEADER_FONT);
        welcomeLabel.setForeground(TEXT_COLOR);

        JLabel subLabel = new JLabel("Silakan pilih menu di atas untuk mengelola inventori Anda.");
        subLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        subLabel.setForeground(Color.GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        welcomePanel.add(welcomeLabel, gbc);

        gbc.gridy = 1;
        welcomePanel.add(subLabel, gbc);

        contentPanel.add(welcomePanel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JButton createStyledButton(String text, String iconName) {
        JButton btn = new JButton(text);
        btn.setFont(BUTTON_FONT);
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setForeground(PRIMARY_COLOR);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!text.equals("Keluar")) {
                    btn.setBackground(ACCENT_COLOR);
                    btn.setForeground(Color.WHITE);
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!text.equals("Keluar")) {
                    btn.setBackground(Color.WHITE);
                    btn.setForeground(PRIMARY_COLOR);
                }
            }
        });
        return btn;
    }

    private void tampilkanFormTambah() {
        contentPanel.removeAll();
        contentPanel.setBackground(BG_COLOR);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(new EmptyBorder(30, 50, 30, 50));

        // Header
        JLabel judulLabel = new JLabel("Tambah Barang Baru");
        judulLabel.setFont(HEADER_FONT);
        judulLabel.setForeground(PRIMARY_COLOR);
        judulLabel.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(judulLabel, BorderLayout.NORTH);

        // Form Container with Card Styled look
        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(30, 30, 30, 30)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Form Components
        String[] jenis = { "Pakaian", "Elektronik" };
        JComboBox<String> jenisCombo = new JComboBox<>(jenis);
        JTextField kodeField = new JTextField(20);
        JTextField namaField = new JTextField(20);
        JTextField stokField = new JTextField(20);
        JTextField hargaField = new JTextField(20);
        JTextField field1 = new JTextField(20); // Ukuran / Garansi
        JTextField field2 = new JTextField(20); // Bahan
        JLabel label1 = new JLabel("Ukuran:");
        JLabel label2 = new JLabel("Bahan:");

        // Styling inputs
        styleComponent(jenisCombo);
        styleComponent(kodeField);
        styleComponent(namaField);
        styleComponent(stokField);
        styleComponent(hargaField);
        styleComponent(field1);
        styleComponent(field2);

        // Adding to GridBag
        addFormRow(cardPanel, gbc, 0, "Jenis Barang:", jenisCombo);
        addFormRow(cardPanel, gbc, 1, "Kode Barang:", kodeField);
        addFormRow(cardPanel, gbc, 2, "Nama Barang:", namaField);
        addFormRow(cardPanel, gbc, 3, "Stok Awal:", stokField);
        addFormRow(cardPanel, gbc, 4, "Harga (Rp):", hargaField);
        addFormRow(cardPanel, gbc, 5, label1, field1);
        addFormRow(cardPanel, gbc, 6, label2, field2);

        // Dynamic Label Logic
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

        mainPanel.add(cardPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(BG_COLOR);

        JButton simpanBtn = createStyledButton("SIMPAN DATA", null);
        simpanBtn.setBackground(new Color(46, 204, 113));
        simpanBtn.setForeground(Color.WHITE);

        JButton resetBtn = createStyledButton("RESET FORM", null);
        resetBtn.setBackground(new Color(149, 165, 166));
        resetBtn.setForeground(Color.WHITE);

        buttonPanel.add(simpanBtn);
        buttonPanel.add(resetBtn);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Listeners
        simpanBtn.addActionListener(e -> {
            try {
                String kode = kodeField.getText().trim();
                String nama = namaField.getText().trim();
                if (kode.isEmpty() || nama.isEmpty()) {
                    showError("Kode dan Nama harus diisi!");
                    return;
                }

                int stok = Integer.parseInt(stokField.getText().trim());
                double harga = Double.parseDouble(hargaField.getText().trim());
                String pilihan = (String) jenisCombo.getSelectedItem();
                boolean berhasil;

                if (pilihan.equals("Pakaian")) {
                    String ukuran = field1.getText().trim();
                    String bahan = field2.getText().trim();
                    if (ukuran.isEmpty() || bahan.isEmpty()) {
                        showError("Ukuran dan Bahan harus diisi!");
                        return;
                    }
                    berhasil = dao.tambahPakaian(new Pakaian(kode, nama, stok, harga, ukuran, bahan));
                } else {
                    String garansi = field1.getText().trim();
                    if (garansi.isEmpty()) {
                        showError("Garansi harus diisi!");
                        return;
                    }
                    berhasil = dao.tambahElektronik(new Elektronik(kode, nama, stok, harga, garansi));
                }

                if (berhasil) {
                    showSuccess("Barang berhasil disimpan!");
                    resetBtn.doClick();
                } else {
                    showError("Gagal menyimpan. Kode mungkin duplikat.");
                }
            } catch (NumberFormatException ex) {
                showError("Stok dan Harga harus angka valid!");
            } catch (Exception ex) {
                showError("Error: " + ex.getMessage());
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

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent component) {
        JLabel label = new JLabel(labelText);
        label.setFont(MAIN_FONT);
        addFormRow(panel, gbc, row, label, component);
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, JLabel label, JComponent component) {
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(component, gbc);
    }

    private void styleComponent(JComponent comp) {
        comp.setFont(MAIN_FONT);
        if (comp instanceof JTextField) {
            comp.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(189, 195, 199)),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        }
    }

    private void tampilkanTabel() {
        contentPanel.removeAll();
        JPanel tablePanel = new JPanel(new BorderLayout(20, 20));
        tablePanel.setBackground(BG_COLOR);
        tablePanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel judulLabel = new JLabel("Daftar Inventori Barang");
        judulLabel.setFont(HEADER_FONT);
        judulLabel.setForeground(PRIMARY_COLOR);
        judulLabel.setHorizontalAlignment(JLabel.CENTER);
        tablePanel.add(judulLabel, BorderLayout.NORTH);

        String[] kolom = { "Kode", "Nama", "Stok", "Harga", "Jenis", "Detail Spesifikasi" };
        ArrayList<Barang> listBarang = dao.getAllBarang();
        String[][] data = new String[listBarang.size()][6];

        int i = 0;
        for (Barang b : listBarang) {
            data[i][0] = b.getKode();
            data[i][1] = b.getNama();
            data[i][2] = String.valueOf(b.getStok());
            data[i][3] = String.format("Rp %,.0f", b.getHarga());
            if (b instanceof Pakaian) {
                data[i][4] = "Pakaian";
                data[i][5] = "Ukuran: " + ((Pakaian) b).getUkuran() + ", Bahan: " + ((Pakaian) b).getBahan();
            } else if (b instanceof Elektronik) {
                data[i][4] = "Elektronik";
                data[i][5] = "Garansi: " + ((Elektronik) b).getGaransi();
            }
            i++;
        }

        JTable tabel = new JTable(data, kolom);
        tabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabel.setRowHeight(30);
        tabel.setShowGrid(true);
        tabel.setGridColor(new Color(230, 230, 230));
        tabel.setSelectionBackground(new Color(220, 237, 255));

        // Header Styling
        JTableHeader header = tabel.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(PRIMARY_COLOR);
        header.setForeground(Color.WHITE);
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        JScrollPane scroll = new JScrollPane(tabel);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        tablePanel.add(scroll, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(BG_COLOR);
        JButton refreshBtn = createStyledButton("Refresh Data", null);
        refreshBtn.addActionListener(e -> tampilkanTabel());
        btnPanel.add(refreshBtn);

        tablePanel.add(btnPanel, BorderLayout.SOUTH);

        contentPanel.add(tablePanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void tampilkanFormUpdate() {
        contentPanel.removeAll();
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(new EmptyBorder(30, 100, 30, 100));

        JLabel judulLabel = new JLabel("Update Stok Barang");
        judulLabel.setFont(HEADER_FONT);
        judulLabel.setForeground(PRIMARY_COLOR);
        judulLabel.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(judulLabel, BorderLayout.NORTH);

        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.WHITE);
        container.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(30, 30, 30, 30)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField kodeField = new JTextField(20);
        JTextField stokField = new JTextField(20);
        styleComponent(kodeField);
        styleComponent(stokField);

        JButton cariBtn = createStyledButton("Cari Barang", null);
        cariBtn.setBackground(ACCENT_COLOR);
        cariBtn.setForeground(Color.WHITE);

        JButton updateBtn = createStyledButton("Simpan Stok Baru", null);
        updateBtn.setBackground(new Color(46, 204, 113));
        updateBtn.setForeground(Color.WHITE);

        JTextArea infoArea = new JTextArea(5, 30);
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        infoArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        infoArea.setBackground(new Color(250, 250, 250));

        // Layout Components
        gbc.gridx = 0;
        gbc.gridy = 0;
        container.add(new JLabel("Kode Barang:"), gbc);

        gbc.gridx = 1;
        container.add(kodeField, gbc);

        gbc.gridx = 2;
        container.add(cariBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        container.add(new JScrollPane(infoArea), gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        container.add(new JLabel("Stok Baru:"), gbc);

        gbc.gridx = 1;
        container.add(stokField, gbc);

        gbc.gridx = 2;
        container.add(updateBtn, gbc);

        // Logic
        cariBtn.addActionListener(e -> {
            String kode = kodeField.getText().trim();
            infoArea.setText("");
            if (kode.isEmpty()) {
                showError("Masukkan kode barang!");
                return;
            }

            Barang b = dao.cariBarang(kode);
            if (b != null) {
                StringBuilder info = new StringBuilder();
                info.append("Nama   : ").append(b.getNama()).append("\n");
                info.append("Stok   : ").append(b.getStok()).append("\n");
                info.append("Harga  : Rp ").append(String.format("%,.0f", b.getHarga())).append("\n");
                info.append("Jenis  : ").append(b instanceof Pakaian ? "Pakaian" : "Elektronik").append("\n");
                if (b instanceof Pakaian) {
                    info.append("Detail : ").append(((Pakaian) b).getUkuran()).append(" / ")
                            .append(((Pakaian) b).getBahan());
                } else if (b instanceof Elektronik) {
                    info.append("Garansi: ").append(((Elektronik) b).getGaransi());
                }
                infoArea.setText(info.toString());
                stokField.setText(String.valueOf(b.getStok()));
                stokField.requestFocus();
            } else {
                infoArea.setText("Barang tidak ditemukan.");
            }
        });

        updateBtn.addActionListener(e -> {
            try {
                String kode = kodeField.getText().trim();
                String stokText = stokField.getText().trim();
                if (kode.isEmpty() || stokText.isEmpty()) {
                    showError("Data tidak lengkap!");
                    return;
                }

                int stokBaru = Integer.parseInt(stokText);
                if (stokBaru < 0) {
                    showError("Stok tidak boleh negatif");
                    return;
                }

                if (dao.updateStok(kode, stokBaru)) {
                    showSuccess("Stok berhasil diperbarui!");
                    infoArea.setText("");
                    kodeField.setText("");
                    stokField.setText("");
                } else {
                    showError("Gagal update. Barang mungkin tidak ada.");
                }
            } catch (NumberFormatException ex) {
                showError("Stok harus angka valid!");
            }
        });

        mainPanel.add(container, BorderLayout.CENTER);
        contentPanel.add(mainPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Suskes", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoriGUI());
    }
}