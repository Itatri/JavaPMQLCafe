package frmQLCafe;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.Serializable;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;


import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class frmQuanLySanPham extends JFrame implements Serializable {

    private static final long serialVersionUID = 1L;

    private JTextField txtTenSanPham;
    private JTextField txtGia;
    private JTable tableSanPham;

    private static final String URL = "jdbc:mysql://localhost:3306/cafe";
    private static final String USER = "root"; 
    private static final String PASSWORD = "";  
    //private JTextField textField;
    
    private JTextField textFieldImagePath; 

    private int selectedRow = -1;
    private JTextField txtMaSP;

    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frmQuanLySanPham window = new frmQuanLySanPham();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public frmQuanLySanPham() {
        getContentPane().setBackground(new Color(255, 255, 255));
        initialize();
        loadDataToTable();
    }

    private void initialize() {
        getContentPane().setBackground(new Color(255, 255, 255));
        setBounds(100, 100, 803, 569);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        
       

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.BLACK);
        panel.setBounds(-1, 0, 790, 51);
        getContentPane().add(panel);

        JLabel lblSnPhm = new JLabel("SẢN PHẨM ");
        lblSnPhm.setForeground(Color.WHITE);
        lblSnPhm.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblSnPhm.setBounds(342, 10, 117, 31);
        panel.add(lblSnPhm);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(255, 255, 255));
        panel_1.setBorder(new TitledBorder(null, "Danh S\u00E1ch S\u1EA3n Ph\u1EA9m ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.setBounds(-1, 51, 790, 432);
        getContentPane().add(panel_1);
        panel_1.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Tên Sản Phẩm : ");
        lblNewLabel_1.setForeground(Color.BLACK);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1.setBounds(10, 80, 179, 22);
        panel_1.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Giá : ");
        lblNewLabel_1_1.setForeground(Color.BLACK);
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1_1.setBounds(10, 112, 179, 22);
        panel_1.add(lblNewLabel_1_1);

        txtTenSanPham = new JTextField();
        txtTenSanPham.setColumns(10);
        txtTenSanPham.setBounds(199, 83, 581, 19);
        panel_1.add(txtTenSanPham);

        txtGia = new JTextField();
        txtGia.setColumns(10);
        txtGia.setBounds(199, 115, 581, 19);
        panel_1.add(txtGia);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 193, 770, 190);
        panel_1.add(scrollPane);

        tableSanPham = new JTable();
        tableSanPham.setBackground(new Color(242, 242, 242));
        scrollPane.setViewportView(tableSanPham);

        JButton btnThemSP = new JButton("Thêm Sản Phẩm ");
        btnThemSP.setForeground(Color.WHITE);
        btnThemSP.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnThemSP.setBackground(Color.BLACK);
        btnThemSP.setBounds(20, 393, 155, 29);
        panel_1.add(btnThemSP);
        
        btnThemSP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                themSanPham(); // Gọi phương thức để thêm sản phẩm
                loadDataToTable(); // Load lại dữ liệu sau khi thêm sản phẩm
            }
        });

        JButton btnSuaSP = new JButton("Sửa Sản Phẩm ");
        btnSuaSP.setForeground(Color.WHITE);
        btnSuaSP.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnSuaSP.setBackground(Color.BLACK);
        btnSuaSP.setBounds(221, 393, 155, 29);
        panel_1.add(btnSuaSP);

        JButton btnXoaSP = new JButton("Xóa Sản Phẩm ");
        btnXoaSP.setForeground(Color.WHITE);
        btnXoaSP.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnXoaSP.setBackground(Color.BLACK);
        btnXoaSP.setBounds(423, 393, 155, 29);
        panel_1.add(btnXoaSP);
        
        JLabel lblMaSP = new JLabel("Mã SP : ");
        lblMaSP.setForeground(Color.BLACK);
        lblMaSP.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblMaSP.setBounds(10, 48, 179, 22);
        panel_1.add(lblMaSP);
        
        txtMaSP = new JTextField();
        txtMaSP.setEditable(false); 
        txtMaSP.setColumns(10);
        txtMaSP.setBounds(199, 51, 581, 19);
        panel_1.add(txtMaSP);
        
        JButton btnXoaTatCa = new JButton("Xóa tất cả");
        btnXoaTatCa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Đặt các trường nhập liệu về rỗng
                txtMaSP.setText("");
                txtTenSanPham.setText("");
                txtGia.setText("");
                textFieldImagePath.setText("");
            }
        });

        btnXoaTatCa.setForeground(Color.WHITE);
        btnXoaTatCa.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnXoaTatCa.setBackground(Color.BLACK);
        btnXoaTatCa.setBounds(610, 393, 155, 29);
        panel_1.add(btnXoaTatCa);

        
     // Xử lý sự kiện khi click vào một dòng trên bảng
        tableSanPham.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableSanPham.getSelectedRow();
                if (row >= 0) {
                    selectedRow = row;
                    DefaultTableModel model = (DefaultTableModel) tableSanPham.getModel();
                    txtMaSP.setText(model.getValueAt(row, 0).toString()); // Hiển thị mã sản phẩm
                    txtTenSanPham.setText(model.getValueAt(row, 1).toString());
                    txtGia.setText(model.getValueAt(row, 2).toString());
                    textFieldImagePath.setText(model.getValueAt(row, 3).toString());


                }
            }
        });

        btnSuaSP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                suaSanPham(); // Gọi phương thức để sửa sản phẩm
                loadDataToTable(); // Load lại dữ liệu sau khi sửa sản phẩm
            }
        });

        btnXoaSP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xoaSanPham(); // Gọi phương thức để xóa sản phẩm
                loadDataToTable(); // Load lại dữ liệu sau khi xóa sản phẩm
            }
        });
        
        
        JLabel lblNewLabel_1_1_1 = new JLabel("Hình ảnh :");
        lblNewLabel_1_1_1.setForeground(Color.BLACK);
        lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1_1_1.setBounds(10, 144, 179, 22);
        panel_1.add(lblNewLabel_1_1_1);
        
        textFieldImagePath = new JTextField();
        textFieldImagePath.setEditable(false); // Chỉ đọc, không cho phép nhập liệu
        textFieldImagePath.setColumns(10);
        textFieldImagePath.setBounds(199, 144, 456, 19);
        panel_1.add(textFieldImagePath); // Add the TextField for image path
        
        JButton btnBrowse = new JButton("Chọn ảnh");
        btnBrowse.setForeground(Color.WHITE);
        btnBrowse.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnBrowse.setBackground(Color.BLACK);
        btnBrowse.setBounds(665, 143, 115, 22);
        panel_1.add(btnBrowse); // Add the button for browsing image

        btnBrowse.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                String imagePath = fileChooser.getSelectedFile().getAbsolutePath();
                textFieldImagePath.setText(imagePath);
                
                
                
                
            }
        });

        JButton btnThoat = new JButton("Thoát ");
        btnThoat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thoát không?", "Xác nhận thoát", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose(); // Đóng cửa sổ hiện tại
                }
            }
        });

        btnThoat.setForeground(Color.WHITE);
        btnThoat.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnThoat.setBackground(Color.BLACK);
        btnThoat.setBounds(684, 493, 95, 29);
        getContentPane().add(btnThoat);
    }
    
    private void suaSanPham() {
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để sửa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String tenSanPham = txtTenSanPham.getText();
        double gia = Double.parseDouble(txtGia.getText());
        String imagePath = textFieldImagePath.getText();

        Connection conn = getConnection();
        if (conn != null) {
            try {
                String query = "UPDATE sanpham SET Ten=?, Gia=?, LinkSP=? WHERE MaSanPham=?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, tenSanPham);
                pstmt.setDouble(2, gia);
                pstmt.setString(3, imagePath);
                pstmt.setInt(4, Integer.parseInt(tableSanPham.getValueAt(selectedRow, 0).toString())); // Lấy MaSanPham từ bảng
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
                JOptionPane.showMessageDialog(this, "Sửa sản phẩm thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Sửa sản phẩm thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void xoaSanPham() {
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sản phẩm này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Connection conn = getConnection();
            if (conn != null) {
                try {
                    String query = "DELETE FROM sanpham WHERE MaSanPham=?";
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, Integer.parseInt(tableSanPham.getValueAt(selectedRow, 0).toString())); // Lấy MaSanPham từ bảng
                    pstmt.executeUpdate();
                    pstmt.close();
                    conn.close();
                    JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Xóa sản phẩm thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    
    private void themSanPham() {
        String tenSanPham = txtTenSanPham.getText();
        double gia = Double.parseDouble(txtGia.getText());
        String imagePath = textFieldImagePath.getText();

        Connection conn = getConnection();
        if (conn != null) {
            try {
                String query = "INSERT INTO sanpham (Ten, Gia, LinkSP) VALUES (?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, tenSanPham);
                pstmt.setDouble(2, gia);
                pstmt.setString(3, imagePath);
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void loadDataToTable() {
        Vector<Vector<Object>> data = getSanPham();
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Mã Sản Phẩm");
        columnNames.add("Tên Sản Phẩm");
        columnNames.add("Giá");
        columnNames.add("Ảnh");  // Thêm cột LinkSP vào đây

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tableSanPham.setModel(model);
    }

    private Vector<Vector<Object>> getSanPham() {
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
                    row.add(rs.getString("LinkSP"));  // Thêm LinkSP vào dữ liệu
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
}
