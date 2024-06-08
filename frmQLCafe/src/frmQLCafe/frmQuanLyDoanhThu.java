package frmQLCafe;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

public class frmQuanLyDoanhThu extends JFrame {

    private static final long serialVersionUID = 1L;
  
    private JTable tableDSDoanhThu;
    JTextArea textAreaTongDoanhThu;

    private static final String URL = "jdbc:mysql://localhost:3306/cafe";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    
    
    private JComboBox<Integer> comboBoxTuNgayNgay;
    private JComboBox<Integer> comboBoxTuNgayThang;
    private JComboBox<Integer> comboBoxTuNgayNam;
    private JComboBox<Integer> comboBoxDenNgayNgay;
    private JComboBox<Integer> comboBoxDenNgayThang;
    private JComboBox<Integer> comboBoxDenNgayNam;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frmQuanLyDoanhThu window = new frmQuanLyDoanhThu();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public frmQuanLyDoanhThu() {
        getContentPane().setBackground(new Color(255, 255, 255));
        initialize();
        loadDoanhThuData();
        updateTotalRevenue();
    }

    private void initialize() {
    	
        setBounds(100, 100, 847, 467);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.BLACK);
        panel.setBounds(0, 0, 833, 51);
        getContentPane().add(panel);

        JLabel lblDoanhThu = new JLabel("DOANH THU ");
        lblDoanhThu.setForeground(Color.WHITE);
        lblDoanhThu.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblDoanhThu.setBounds(354, 10, 138, 31);
        panel.add(lblDoanhThu);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(255, 255, 255));
        panel_1.setBorder(new TitledBorder(null, "L\u1ECDc Doanh Thu ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.setBounds(10, 61, 279, 323);
        getContentPane().add(panel_1);
        panel_1.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Từ ngày : ");
        lblNewLabel_1.setForeground(Color.BLACK);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1.setBounds(10, 126, 77, 22);
        panel_1.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Đến ngày : ");
        lblNewLabel_1_1.setForeground(Color.BLACK);
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1_1.setBounds(10, 159, 77, 22);
        panel_1.add(lblNewLabel_1_1);

        /*txtTuNgay = new JTextField();
        txtTuNgay.setColumns(10);
        txtTuNgay.setBounds(104, 129, 165, 19);
        panel_1.add(txtTuNgay);

        txtDenNgay = new JTextField();
        txtDenNgay.setColumns(10);
        txtDenNgay.setBounds(104, 162, 165, 19);
        panel_1.add(txtDenNgay);*/
        
     // ComboBox cho từ ngày
        comboBoxTuNgayNgay = new JComboBox<>();
        comboBoxTuNgayNgay.setBounds(85, 128, 43, 20);
        panel_1.add(comboBoxTuNgayNgay);

        comboBoxTuNgayThang = new JComboBox<>();
        comboBoxTuNgayThang.setBounds(138, 128, 43, 20);
        panel_1.add(comboBoxTuNgayThang);

        comboBoxTuNgayNam = new JComboBox<>();
        comboBoxTuNgayNam.setBounds(191, 128, 60, 20);
        panel_1.add(comboBoxTuNgayNam);

        // ComboBox cho đến ngày
        comboBoxDenNgayNgay = new JComboBox<>();
        comboBoxDenNgayNgay.setBounds(85, 161, 43, 20);
        panel_1.add(comboBoxDenNgayNgay);

        comboBoxDenNgayThang = new JComboBox<>();
        comboBoxDenNgayThang.setBounds(138, 161, 43, 20);
        panel_1.add(comboBoxDenNgayThang);

        comboBoxDenNgayNam = new JComboBox<>();
        comboBoxDenNgayNam.setBounds(191, 161, 60, 20);
        panel_1.add(comboBoxDenNgayNam);

        // Thêm dữ liệu cho các ComboBox
        for (int i = 1; i <= 31; i++) {
            comboBoxTuNgayNgay.addItem(i);
            comboBoxDenNgayNgay.addItem(i);
        }

        for (int i = 1; i <= 12; i++) {
            comboBoxTuNgayThang.addItem(i);
            comboBoxDenNgayThang.addItem(i);
        }

        // Du lieu nam 
        int currentYear = java.time.Year.now().getValue();
        for (int i = 2000; i <= currentYear; i++) {
            comboBoxTuNgayNam.addItem(i);
            comboBoxDenNgayNam.addItem(i);
        }


        JButton btnLoc = new JButton("Lọc ");
        btnLoc.setForeground(Color.WHITE);
        btnLoc.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnLoc.setBackground(Color.BLACK);
        btnLoc.setBounds(22, 202, 65, 29);
        panel_1.add(btnLoc);
        
        /*btnLoc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tuNgay = txtTuNgay.getText();
                String denNgay = txtDenNgay.getText();
                loadFilteredDoanhThuData(tuNgay, denNgay);
            }
        });*/
        btnLoc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tuNgay = comboBoxTuNgayNam.getSelectedItem() + "-" + comboBoxTuNgayThang.getSelectedItem() + "-" + comboBoxTuNgayNgay.getSelectedItem();
                String denNgay = comboBoxDenNgayNam.getSelectedItem() + "-" + comboBoxDenNgayThang.getSelectedItem() + "-" + comboBoxDenNgayNgay.getSelectedItem();
                
                updateTotalRevenue(); // Cập nhật tổng doanh thu trước khi lọc
                loadFilteredDoanhThuData(tuNgay, denNgay);
                updateTotalRevenue(); // Cập nhật tổng doanh thu sau khi lọc
            }
        });

   



        JPanel panel_1_1 = new JPanel();
        panel_1_1.setBackground(new Color(255, 255, 255));
        panel_1_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Danh S\u00E1ch Doanh Thu ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel_1_1.setBounds(299, 61, 524, 323);
        getContentPane().add(panel_1_1);
        panel_1_1.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 23, 504, 290);
        panel_1_1.add(scrollPane);
        
      

        tableDSDoanhThu = new JTable();
        tableDSDoanhThu.setBackground(new Color(242, 242, 242));
        scrollPane.setViewportView(tableDSDoanhThu);

        JButton btnXuatBaoCao = new JButton("Xuất báo cáo ");
        btnXuatBaoCao.setForeground(Color.WHITE);
        btnXuatBaoCao.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnXuatBaoCao.setBackground(Color.BLACK);
        btnXuatBaoCao.setBounds(669, 393, 137, 29);
        getContentPane().add(btnXuatBaoCao);
        
        JLabel lblTongDoanhThu = new JLabel("Tổng doanh thu : ");
        lblTongDoanhThu.setForeground(Color.BLACK);
        lblTongDoanhThu.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblTongDoanhThu.setBounds(339, 394, 112, 22);
        getContentPane().add(lblTongDoanhThu);
        
         textAreaTongDoanhThu = new JTextArea();
        textAreaTongDoanhThu.setText("0.0");
        textAreaTongDoanhThu.setBounds(461, 396, 112, 22);
        getContentPane().add(textAreaTongDoanhThu);

    }

    private void loadDoanhThuData() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Mã Giao Dịch");
        model.addColumn("Thời Gian Giao Dịch");
        model.addColumn("Tổng Tiền");
        model.addColumn("Phương Thức Thanh Toán");
        model.addColumn("Mã Nhân Viên");

        try {
            Connection conn = getConnection();
            if (conn != null) {
                String query = "SELECT * FROM giaodich";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int maGiaoDich = rs.getInt("MaGiaoDich");
                    String thoiGianGiaoDich = rs.getString("ThoiGianGiaoDich");
                    double tongTien = rs.getDouble("TongTien");
                    String phuongThucThanhToan = rs.getString("PhuongThucThanhToan");
                    int maNhanVien = rs.getInt("MaNhanVien");
                    model.addRow(new Object[]{maGiaoDich, thoiGianGiaoDich, tongTien, phuongThucThanhToan, maNhanVien});
                }
                rs.close();
                stmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableDSDoanhThu.setModel(model);
    }
    
    private void loadFilteredDoanhThuData(String tuNgay, String denNgay) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Mã Giao Dịch");
        model.addColumn("Thời Gian Giao Dịch");
        model.addColumn("Tổng Tiền");
        model.addColumn("Phương Thức Thanh Toán");
        model.addColumn("Mã Nhân Viên");

        try {
            Connection conn = getConnection();
            if (conn != null) {
                String query = "SELECT * FROM giaodich WHERE DATE(ThoiGianGiaoDich) BETWEEN '" + tuNgay + "' AND '" + denNgay + "'";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int maGiaoDich = rs.getInt("MaGiaoDich");
                    String thoiGianGiaoDich = rs.getString("ThoiGianGiaoDich");
                    double tongTien = rs.getDouble("TongTien");
                    String phuongThucThanhToan = rs.getString("PhuongThucThanhToan");
                    int maNhanVien = rs.getInt("MaNhanVien");
                    model.addRow(new Object[]{maGiaoDich, thoiGianGiaoDich, tongTien, phuongThucThanhToan, maNhanVien});
                }
                rs.close();
                stmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableDSDoanhThu.setModel(model);
    }
    
    private void updateTotalRevenue() {
        double totalRevenue = 0.0;
        DefaultTableModel model = (DefaultTableModel) tableDSDoanhThu.getModel();
        
        for (int i = 0; i < model.getRowCount(); i++) {
            double amount = (double) model.getValueAt(i, 2); // Giả sử cột 2 chứa tổng tiền
            totalRevenue += amount;
        }
        
        textAreaTongDoanhThu.setText(String.valueOf(totalRevenue));
    }



  

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}