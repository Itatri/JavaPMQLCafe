package frmQLCafe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.sql.*;
import java.util.Vector;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.math.BigDecimal;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class frmQuanLyMenu extends JFrame implements Serializable {

    private static final long serialVersionUID = 1L;
    private JTextField txtTimKiem;
    private JTable tableCTDonHang;
    private JScrollPane scrollPane_DSSP;
    private JTable tableSanPham;
    private JPanel panel_DSSP;
    private JTextArea textAreaTongTienCongLai;
    private TableRowSorter<DefaultTableModel> rowSorter;

    private static final String URL = "jdbc:mysql://localhost:3306/cafe";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frmQuanLyMenu window = new frmQuanLyMenu();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public frmQuanLyMenu() {
        getContentPane().setBackground(new Color(255, 255, 255));
        initialize();
        loadSanPhamToScrollPane();
        calculateAndSetTotal();
    }

    private void initialize() {
        setTitle("Form Quản Lý Menu");
        setSize(1130, 780);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.BLACK);
        panel.setBounds(0, 0, 1116, 51);
        getContentPane().add(panel);

        JLabel lblHan = new JLabel("MENU");
        lblHan.setForeground(Color.WHITE);
        lblHan.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblHan.setBounds(531, 10, 67, 31);
        panel.add(lblHan);

        JLabel lblNewLabel_1 = new JLabel("Tìm kiếm : ");
        lblNewLabel_1.setForeground(Color.BLACK);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1.setBounds(10, 61, 75, 22);
        getContentPane().add(lblNewLabel_1);

        txtTimKiem = new JTextField();
        txtTimKiem.setColumns(10);
        txtTimKiem.setBounds(77, 64, 237, 19);
        getContentPane().add(txtTimKiem);

        JButton btnTimKiem = new JButton("Tìm Kiếm ");
        btnTimKiem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = txtTimKiem.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnTimKiem.setBackground(Color.BLACK);
        btnTimKiem.setBounds(324, 58, 125, 30);
        getContentPane().add(btnTimKiem);

        JPanel panel_CTDH = new JPanel();
        panel_CTDH.setBackground(new Color(255, 255, 255));
        panel_CTDH.setBorder(new TitledBorder(null, "Chi Tiết Đơn Hàng", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_CTDH.setBounds(567, 61, 539, 609);
        getContentPane().add(panel_CTDH);
        panel_CTDH.setLayout(null);

        JScrollPane scrollPane_CTDH = new JScrollPane();
        scrollPane_CTDH.setBounds(10, 21, 519, 380);
        panel_CTDH.add(scrollPane_CTDH);

        tableCTDonHang = new JTable();
        tableCTDonHang.setBackground(new Color(242, 242, 242));
        scrollPane_CTDH.setViewportView(tableCTDonHang);

        JLabel lblNewLabel_1_3 = new JLabel("Tổng Số Tiền Đã Mua :");
        lblNewLabel_1_3.setForeground(Color.BLACK);
        lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1_3.setBounds(18, 435, 179, 22);
        panel_CTDH.add(lblNewLabel_1_3);

        textAreaTongTienCongLai = new JTextArea();
        textAreaTongTienCongLai.setBounds(247, 435, 257, 19);
        panel_CTDH.add(textAreaTongTienCongLai);

        JButton btnXoaMonAn = new JButton("Xóa Món Ăn");
        btnXoaMonAn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableCTDonHang.getSelectedRow();
                if (selectedRow != -1) {
                    int maSanPham = (int) tableCTDonHang.getValueAt(selectedRow, 1);

                    DefaultTableModel modelCTDH = (DefaultTableModel) tableCTDonHang.getModel();
                    modelCTDH.removeRow(selectedRow);

                    try {
                        Connection conn = getConnection();
                        if (conn != null) {
                            String deleteQuery = "DELETE FROM chitietdonhang WHERE MaSanPham = ?";
                            PreparedStatement pstmt = conn.prepareStatement(deleteQuery);
                            pstmt.setInt(1, maSanPham);
                            pstmt.executeUpdate();
                            pstmt.close();
                            conn.close();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một món ăn để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
                calculateAndSetTotal();
            }
        });
        btnXoaMonAn.setForeground(Color.WHITE);
        btnXoaMonAn.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnXoaMonAn.setBackground(Color.BLACK);
        btnXoaMonAn.setBounds(21, 480, 229, 47);
        panel_CTDH.add(btnXoaMonAn);

        JButton btnThanhToan = new JButton("Thanh Toán ");
        btnThanhToan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thanh toán?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    calculateAndSetTotal();
                    Vector<Vector<Object>> orderDetails = getOrderDetails();
                    BigDecimal totalAmount = new BigDecimal(textAreaTongTienCongLai.getText());
                    frmQuanLyThanhToan thanhToanForm = new frmQuanLyThanhToan(orderDetails, totalAmount);
                    thanhToanForm.setVisible(true);

                    // Xóa dữ liệu sau khi thanh toán
                    clearTable();
                }
            }
        });
        btnThanhToan.setForeground(Color.WHITE);
        btnThanhToan.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnThanhToan.setBackground(Color.BLACK);
        btnThanhToan.setBounds(21, 547, 229, 47);
        panel_CTDH.add(btnThanhToan);

        JButton btnXoaTatCaDonHang = new JButton("Xóa Tất Cả Đơn Hàng");
        btnXoaTatCaDonHang.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa tất cả đơn hàng?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {
                    DefaultTableModel modelCTDH = (DefaultTableModel) tableCTDonHang.getModel();
                    modelCTDH.setRowCount(0);

                    try {
                        Connection conn = getConnection();
                        if (conn != null) {
                            String deleteAllQuery = "DELETE FROM chitietdonhang";
                            Statement stmt = conn.createStatement();
                            stmt.executeUpdate(deleteAllQuery);
                            stmt.close();
                            conn.close();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                calculateAndSetTotal();
            }
        });
        btnXoaTatCaDonHang.setForeground(Color.WHITE);
        btnXoaTatCaDonHang.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnXoaTatCaDonHang.setBackground(Color.BLACK);
        btnXoaTatCaDonHang.setBounds(275, 480, 229, 47);
        panel_CTDH.add(btnXoaTatCaDonHang);

        JButton btnThemSanPham = new JButton("Thêm Sản Phẩm Mới ");
      	        btnThemSanPham.setForeground(Color.WHITE);
        btnThemSanPham.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnThemSanPham.setBackground(Color.BLACK);
        btnThemSanPham.setBounds(275, 547, 229, 47);
        panel_CTDH.add(btnThemSanPham);

        JButton btnThoat = new JButton("Thoát ");
        btnThoat.setBounds(784, 680, 95, 38);
        getContentPane().add(btnThoat);
        btnThoat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thoát không?", "Xác nhận thoát", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
        btnThoat.setForeground(Color.WHITE);
        btnThoat.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnThoat.setBackground(Color.BLACK);

        panel_DSSP = new JPanel();
        panel_DSSP.setBackground(new Color(255, 255, 255));
        panel_DSSP.setBorder(new TitledBorder(null, "Danh S\u00E1ch S\u1EA3n Ph\u1EA9m", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_DSSP.setBounds(10, 90, 547, 643);
        getContentPane().add(panel_DSSP);
        panel_DSSP.setLayout(null);

        scrollPane_DSSP = new JScrollPane();
        scrollPane_DSSP.setBounds(10, 22, 429, 611);
        panel_DSSP.add(scrollPane_DSSP);

        tableSanPham = new JTable();
        scrollPane_DSSP.setViewportView(tableSanPham);

        JButton btnThem = new JButton("Thêm");
        btnThem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableSanPham.getSelectedRow();

                if (selectedRow != -1) {
                    int maSanPham = (int) tableSanPham.getValueAt(selectedRow, 0);
                                        String tenSanPham = (String) tableSanPham.getValueAt(selectedRow, 1);
                    double giaSanPham = (double) tableSanPham.getValueAt(selectedRow, 2);

                    boolean productExists = false;
                    DefaultTableModel modelCTDH = (DefaultTableModel) tableCTDonHang.getModel();
                    for (int i = 0; i < modelCTDH.getRowCount(); i++) {
                        int maSanPhamCTDH = (int) modelCTDH.getValueAt(i, 1);
                        if (maSanPhamCTDH == maSanPham) {
                            int soLuong = (int) modelCTDH.getValueAt(i, 3);
                            modelCTDH.setValueAt(soLuong + 1, i, 3);
                            double TongTienCongLai = giaSanPham * (soLuong + 1);
                            modelCTDH.setValueAt(TongTienCongLai, i, 4);
                            try {
                                Connection conn = getConnection();
                                if (conn != null) {
                                    String updateQuery = "UPDATE chitietdonhang SET SoLuong = ?, TongTienCongLai = ? WHERE MaSanPham = ? AND MaDonHang = ?";
                                    PreparedStatement pstmt = conn.prepareStatement(updateQuery);
                                    pstmt.setInt(1, soLuong + 1);
                                    pstmt.setDouble(2, TongTienCongLai);
                                    pstmt.setInt(3, maSanPham);
                                    pstmt.setInt(4, getMaDonHang());
                                    pstmt.executeUpdate();
                                    pstmt.close();
                                    conn.close();
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }

                            productExists = true;
                            break;
                        }
                    }

                    if (!productExists) {
                        Vector<Object> newRow = new Vector<>();
                        newRow.add(null);
                        newRow.add(maSanPham);
                        newRow.add(tenSanPham);
                        newRow.add(1);
                        newRow.add(giaSanPham);
                        newRow.add(getMaDonHang());
                        modelCTDH.addRow(newRow);

                        try {
                            Connection conn = getConnection();
                            if (conn != null) {
                                String insertQuery = "INSERT INTO chitietdonhang (MaSanPham, SoLuong, TongTienCongLai, MaDonHang) VALUES (?, ?, ?, ?)";
                                PreparedStatement pstmt = conn.prepareStatement(insertQuery);
                                pstmt.setInt(1, maSanPham);
                                pstmt.setInt(2, 1);
                                pstmt.setDouble(3, giaSanPham);
                                pstmt.setInt(4, getMaDonHang());
                                pstmt.executeUpdate();
                                pstmt.close();
                                conn.close();
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một sản phẩm để thêm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }

                calculateAndSetTotal();
            }
        });
        btnThem.setForeground(Color.WHITE);
        btnThem.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnThem.setBackground(Color.BLACK);
        btnThem.setBounds(449, 22, 88, 611);
        panel_DSSP.add(btnThem);

        btnThemSanPham.addActionListener(e -> openQuanLySanPhamForm());

        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateRowFilter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateRowFilter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateRowFilter();
            }

            private void updateRowFilter() {
                String text = txtTimKiem.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        initializeEmptyOrderDetailsTable();
    }

    private void initializeEmptyOrderDetailsTable() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Mã Chi Tiết Đơn Hàng");
        columnNames.add("Mã SP");
        columnNames.add("Tên Sản Phẩm");
        columnNames.add("Số Lượng");
        columnNames.add("Tổng Tiền");

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        tableCTDonHang.setModel(model);
    }

    private int getMaDonHang() {
        int maDonHang = -1;
        try {
            Connection conn = getConnection();
            if (conn != null) {
                String query = "SELECT MaDonHang FROM donhang ORDER BY MaDonHang DESC LIMIT 1";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    maDonHang = rs.getInt("MaDonHang");
                }
                rs.close();
                stmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maDonHang;
    }

    private Vector<Vector<Object>> getOrderDetails() {
        DefaultTableModel model = (DefaultTableModel) tableCTDonHang.getModel();
        Vector<Vector<Object>> orderDetails = new Vector<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            Vector<Object> row = new Vector<>();
            for (int j = 0; j < model.getColumnCount(); j++) {
                row.add(model.getValueAt(i, j));
            }
            orderDetails.add(row);
        }
        return orderDetails;
    }

    private void loadSanPhamToScrollPane() {
        tableSanPham.setBackground(new Color(242, 242, 242));

        DefaultTableModel modelSanPham = new DefaultTableModel();
        modelSanPham.addColumn("Mã Sản Phẩm");
        modelSanPham.addColumn("Tên Sản Phẩm");
        modelSanPham.addColumn("Giá");
        modelSanPham.addColumn("Hình Ảnh");

        Vector<Vector<Object>> dataSanPham = getSanPhamData();

        for (Vector<Object> row : dataSanPham) {
            modelSanPham.addRow(row);
        }

        tableSanPham.setModel(modelSanPham);
        tableSanPham.getColumnModel().getColumn(3).setCellRenderer(new ImageRenderer());

        int rowHeight = 110;
        tableSanPham.setRowHeight(rowHeight);

        rowSorter = new TableRowSorter<>(modelSanPham);
        tableSanPham.setRowSorter(rowSorter);

        scrollPane_DSSP.setViewportView(tableSanPham);
    }

    private Vector<Vector<Object>> getSanPhamData() {
        Vector<Vector<Object>> data = new Vector<>();
        Connection conn = getConnection();

        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                String query = "SELECT MaSanPham, Ten, Gia, LinkSP FROM sanpham";
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    Vector<Object> row = new Vector<>();
                    row.add(rs.getInt("MaSanPham"));
                    row.add(rs.getString("Ten"));
                    row.add(rs.getDouble("Gia"));
                    row.add(rs.getString("LinkSP"));
                    data.add(row);
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return data;
    }

    private Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    class ImageRenderer extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 1L;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel();
            if (value != null) {
                String imagePath = value.toString();
                try {
                    ImageIcon icon = new ImageIcon(imagePath);
                    Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(image);
                    label.setIcon(icon);
                    label.setPreferredSize(new Dimension(50, 50));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return label;
        }
    }

    private void calculateAndSetTotal() {
        DefaultTableModel model = (DefaultTableModel) tableCTDonHang.getModel();
        double total = 0.0;
        for (int i = 0; i < model.getRowCount(); i++) {
            total += (double) model.getValueAt(i, 4);  // Cột tổng tiền
        }
        textAreaTongTienCongLai.setText(String.valueOf(total));
    }

    private void clearTable() {
        DefaultTableModel model = (DefaultTableModel) tableCTDonHang.getModel();
        model.setRowCount(0);
        textAreaTongTienCongLai.setText("");
    }

    private void openQuanLySanPhamForm() {
        frmQuanLySanPham menuForm = new frmQuanLySanPham();
        menuForm.setVisible(true);
    }
}


