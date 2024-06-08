package frmQLCafe;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Font;

public class frmQuanLyKhachHang extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTable table;
    private JTextField txtTenKhachHang;
    private JTextField txtSoDienThoai;
    private DefaultTableModel tableModel;
    private static final String URL = "jdbc:mysql://localhost:3306/cafe";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private frmQuanLyThanhToan parent; // Thêm đối tượng parent

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frmQuanLyKhachHang window = new frmQuanLyKhachHang();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public frmQuanLyKhachHang() {
    	getContentPane().setBackground(new Color(255, 255, 255));
        initialize();
        loadData();
    }

    public frmQuanLyKhachHang(frmQuanLyThanhToan parent) {
        this.parent = parent;
        initialize();
        loadData();
    }

    private void initialize() {
        setTitle("Quản Lý Khách Hàng");
        setBounds(100, 100, 600, 443);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel lblTenKhachHang = new JLabel("Tên Khách Hàng");
        lblTenKhachHang.setBounds(10, 56, 100, 14);
        getContentPane().add(lblTenKhachHang);

        txtTenKhachHang = new JTextField();
        txtTenKhachHang.setBounds(120, 53, 150, 20);
        getContentPane().add(txtTenKhachHang);
        txtTenKhachHang.setColumns(10);

        JLabel lblSoDienThoai = new JLabel("Số Điện Thoại");
        lblSoDienThoai.setBounds(10, 87, 100, 14);
        getContentPane().add(lblSoDienThoai);

        txtSoDienThoai = new JTextField();
        txtSoDienThoai.setBounds(120, 84, 150, 20);
        getContentPane().add(txtSoDienThoai);
        txtSoDienThoai.setColumns(10);

        JButton btnThem = new JButton("Thêm");
        btnThem.setForeground(new Color(255, 255, 255));
        btnThem.setBackground(new Color(0, 0, 0));
        btnThem.setBounds(10, 126, 89, 23);
        getContentPane().add(btnThem);

        JButton btnSua = new JButton("Sửa");
        btnSua.setBackground(new Color(0, 0, 0));
        btnSua.setForeground(new Color(255, 255, 255));
        btnSua.setBounds(109, 126, 89, 23);
        getContentPane().add(btnSua);

        JButton btnXoa = new JButton("Xóa");
        btnXoa.setBackground(new Color(0, 0, 0));
        btnXoa.setForeground(new Color(255, 255, 255));
        btnXoa.setBounds(208, 126, 89, 23);
        getContentPane().add(btnXoa);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 169, 564, 227);
        getContentPane().add(scrollPane);

        table = new JTable();
        tableModel = new DefaultTableModel(
                new Object[][] {},
                new String[] {
                        "Mã Khách Hàng", "Tên Khách Hàng", "Số Điện Thoại"
                }
        );
        table.setModel(tableModel);
        scrollPane.setViewportView(table);
        
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setBounds(0, 0, 586, 35);
        getContentPane().add(panel);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        JLabel lblKhchHng = new JLabel("KHÁCH HÀNG ");
        lblKhchHng.setForeground(Color.WHITE);
        lblKhchHng.setFont(new Font("Tahoma", Font.BOLD, 20));
        panel.add(lblKhchHng);

        btnThem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tenKH = txtTenKhachHang.getText();
                String sdt = txtSoDienThoai.getText();
                if (tenKH.isEmpty() || sdt.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin khách hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                        String query = "INSERT INTO khachhang (Ten, DienThoai) VALUES (?, ?)";
                        PreparedStatement pstmt = conn.prepareStatement(query);
                        pstmt.setString(1, tenKH);
                        pstmt.setString(2, sdt);
                        pstmt.executeUpdate();
                        conn.close();
                        loadData();
                        txtTenKhachHang.setText("");
                        txtSoDienThoai.setText("");
                        if (parent != null) {
                            parent.setNewCustomerInfo(tenKH, sdt); // Cập nhật thông tin khách hàng mới vào form chính
                            parent.refreshCustomerList(); // Cập nhật danh sách khách hàng trong form chính
                        }
                        dispose(); // Đóng form này sau khi thêm khách hàng
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        btnSua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String maKH = tableModel.getValueAt(selectedRow, 0).toString();
                    String tenKH = txtTenKhachHang.getText();
                    String sdt = txtSoDienThoai.getText();
                    if (tenKH.isEmpty() || sdt.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin khách hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                            String query = "UPDATE khachhang SET Ten = ?, DienThoai = ? WHERE MaKhachHang = ?";
                            PreparedStatement pstmt = conn.prepareStatement(query);
                            pstmt.setString(1, tenKH);
                            pstmt.setString(2, sdt);
                            pstmt.setString(3, maKH);
                            pstmt.executeUpdate();
                            conn.close();
                            loadData();
                            if (parent != null) {
                                parent.refreshCustomerList(); // Cập nhật danh sách khách hàng trong form chính
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn khách hàng cần sửa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnXoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String maKH = tableModel.getValueAt(selectedRow, 0).toString();
                    try {
                        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                        String query = "DELETE FROM khachhang WHERE MaKhachHang = ?";
                        PreparedStatement pstmt = conn.prepareStatement(query);
                        pstmt.setString(1, maKH);
                        pstmt.executeUpdate();
                        conn.close();
                        loadData();
                        if (parent != null) {
                            parent.refreshCustomerList(); // Cập nhật danh sách khách hàng trong form chính
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn khách hàng cần xóa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void loadData() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            String query = "SELECT * FROM khachhang";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            tableModel.setRowCount(0); // Clear existing data
            while (rs.next()) {
                String maKH = rs.getString("MaKhachHang");
                String tenKH = rs.getString("Ten");
                String sdt = rs.getString("DienThoai");
                tableModel.addRow(new Object[]{maKH, tenKH, sdt});
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
