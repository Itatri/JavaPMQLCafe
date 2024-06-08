package frmQLCafe;

import java.awt.EventQueue;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.EtchedBorder;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class frmQuanLyDatBan extends JFrame implements Serializable {

    private static final long serialVersionUID = 1L;
    private JTextField txtTenKhachHang;
    private JTextField txtSoDienThoai;
    private JTextField txtSoBan;
    private JTextField txtGhiChua;
    private JTable tableDanhSachDatBan;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frmQuanLyDatBan window = new frmQuanLyDatBan();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public frmQuanLyDatBan() {
    	getContentPane().setBackground(new Color(255, 255, 255));
        initialize();
    }

    private void initialize() {
        setTitle("Form Quản Lý Đặt Bàn");
        setSize(813, 514);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 799, 51);
        panel.setLayout(null);
        panel.setBackground(Color.BLACK);
        getContentPane().add(panel);

        JLabel lbltBn = new JLabel("ĐẶT BÀN");
        lbltBn.setForeground(Color.WHITE);
        lbltBn.setFont(new Font("Tahoma", Font.BOLD, 20));
        lbltBn.setBounds(347, 10, 102, 31);
        panel.add(lbltBn);

        JPanel panelThongTinDatBan = new JPanel();
        panelThongTinDatBan.setBackground(new Color(255, 255, 255));
        panelThongTinDatBan.setLayout(null);
        panelThongTinDatBan.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Thông Tin Đặt Bàn", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelThongTinDatBan.setBounds(10, 61, 312, 366);
        getContentPane().add(panelThongTinDatBan);

        JLabel lblNewLabel_1 = new JLabel("Tên khách hàng:");
        lblNewLabel_1.setForeground(Color.BLACK);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1.setBounds(10, 62, 115, 22);
        panelThongTinDatBan.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Số điện thoại:");
        lblNewLabel_1_1.setForeground(Color.BLACK);
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1_1.setBounds(10, 94, 102, 22);
        panelThongTinDatBan.add(lblNewLabel_1_1);

        txtTenKhachHang = new JTextField();
        txtTenKhachHang.setColumns(10);
        txtTenKhachHang.setBounds(135, 65, 156, 19);
        panelThongTinDatBan.add(txtTenKhachHang);

        txtSoDienThoai = new JTextField();
        txtSoDienThoai.setColumns(10);
        txtSoDienThoai.setBounds(135, 97, 156, 19);
        panelThongTinDatBan.add(txtSoDienThoai);

        JLabel lblNewLabel_1_1_1 = new JLabel("Số bàn:");
        lblNewLabel_1_1_1.setForeground(Color.BLACK);
        lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1_1_1.setBounds(10, 126, 102, 22);
        panelThongTinDatBan.add(lblNewLabel_1_1_1);

        txtSoBan = new JTextField();
        txtSoBan.setColumns(10);
        txtSoBan.setBounds(135, 129, 77, 19);
        panelThongTinDatBan.add(txtSoBan);

        JLabel lblNewLabel_1_1_1_1 = new JLabel("Thời gian:");
        lblNewLabel_1_1_1_1.setForeground(Color.BLACK);
        lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1_1_1_1.setBounds(10, 158, 102, 22);
        panelThongTinDatBan.add(lblNewLabel_1_1_1_1);

        JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Ngày:");
        lblNewLabel_1_1_1_1_1.setForeground(Color.BLACK);
        lblNewLabel_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1_1_1_1_1.setBounds(10, 190, 102, 22);
        panelThongTinDatBan.add(lblNewLabel_1_1_1_1_1);

        JLabel lblNewLabel_1_1_1_1_2 = new JLabel("Trả trước:");
        lblNewLabel_1_1_1_1_2.setForeground(Color.BLACK);
        lblNewLabel_1_1_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1_1_1_1_2.setBounds(10, 222, 102, 22);
        panelThongTinDatBan.add(lblNewLabel_1_1_1_1_2);

        JLabel lblNewLabel_1_1_1_1_3 = new JLabel("Ghi chú:");
        lblNewLabel_1_1_1_1_3.setForeground(Color.BLACK);
        lblNewLabel_1_1_1_1_3.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1_1_1_1_3.setBounds(10, 254, 77, 22);
        panelThongTinDatBan.add(lblNewLabel_1_1_1_1_3);

        txtGhiChua = new JTextField();
        txtGhiChua.setColumns(10);
        txtGhiChua.setBounds(135, 264, 149, 79);
        panelThongTinDatBan.add(txtGhiChua);

        JCheckBox checkboxTraTruoc = new JCheckBox("");
        checkboxTraTruoc.setBackground(new Color(240, 240, 240));
        checkboxTraTruoc.setBounds(142, 224, 21, 21);
        panelThongTinDatBan.add(checkboxTraTruoc);

        JComboBox<String> cbThoiGian = new JComboBox<>();
        cbThoiGian.setBackground(new Color(255, 255, 255));
        cbThoiGian.setBounds(135, 160, 63, 21);
        panelThongTinDatBan.add(cbThoiGian);

        JComboBox<String> cbNgay = new JComboBox<>();
        cbNgay.setBackground(new Color(255, 255, 255));
        cbNgay.setBounds(135, 192, 113, 21);
        panelThongTinDatBan.add(cbNgay);

        JPanel PanelDanhSachDatBan = new JPanel();
        PanelDanhSachDatBan.setBackground(new Color(255, 255, 255));
        PanelDanhSachDatBan.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Danh Sách Đặt Bàn", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        PanelDanhSachDatBan.setBounds(332, 61, 457, 366);
        getContentPane().add(PanelDanhSachDatBan);
        PanelDanhSachDatBan.setLayout(null);

        tableDanhSachDatBan = new JTable();
        tableDanhSachDatBan.setBackground(new Color(242, 242, 242));
        tableDanhSachDatBan.setBounds(10, 23, 437, 321);
        PanelDanhSachDatBan.add(tableDanhSachDatBan);

        JButton btnThem = new JButton("Thêm");
        btnThem.setBounds(362, 437, 84, 29);
        getContentPane().add(btnThem);
        btnThem.setForeground(Color.WHITE);
        btnThem.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnThem.setBackground(Color.BLACK);

        JButton btnXoa = new JButton("Xóa");
        btnXoa.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnXoa.setBackground(Color.BLACK);
        btnXoa.setBounds(467, 437, 84, 29);
        getContentPane().add(btnXoa);

        JButton btnSua = new JButton("Sửa");
        btnSua.setForeground(Color.WHITE);
        btnSua.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnSua.setBackground(Color.BLACK);
        btnSua.setBounds(571, 437, 84, 29);
        getContentPane().add(btnSua);

        JButton btnLuu = new JButton("Lưu");
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnLuu.setBackground(Color.BLACK);
        btnLuu.setBounds(675, 437, 84, 29);
        getContentPane().add(btnLuu);
    }
}
