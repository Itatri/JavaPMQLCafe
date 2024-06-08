package frmQLCafe;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.Serializable;

import javax.swing.JTable;
import javax.swing.JButton;

public class frmQuanLyMuaBan extends JFrame implements Serializable {

	  private static final long serialVersionUID = 1L;
	//private JFrame frame;
	private JTable tableBan;
	private JTable tableMon;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmQuanLyMuaBan window = new frmQuanLyMuaBan();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public frmQuanLyMuaBan() {
		getContentPane().setBackground(new Color(255, 255, 255));
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//frame = new JFrame();
		getContentPane().setBackground(new Color(255, 255, 255));
		setBounds(100, 100, 805, 532);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.BLACK);
		panel.setBounds(0, 0, 791, 51);
		getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel("MUA BÁN");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(346, 10, 102, 31);
		panel.add(lblNewLabel);
		
		tableBan = new JTable();
		tableBan.setBackground(new Color(242, 242, 242));
		tableBan.setBounds(10, 61, 383, 386);
		getContentPane().add(tableBan);
		
		tableMon = new JTable();
		tableMon.setBackground(new Color(242, 242, 242));
		tableMon.setBounds(403, 61, 378, 386);
		getContentPane().add(tableMon);
		
		JButton btnThemMon = new JButton("Thêm món ");
		btnThemMon.setForeground(Color.WHITE);
		btnThemMon.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnThemMon.setBackground(Color.BLACK);
		btnThemMon.setBounds(133, 457, 112, 29);
		getContentPane().add(btnThemMon);
		
		JButton btnThanhToan = new JButton("Thanh toán ");
		btnThanhToan.setForeground(Color.WHITE);
		btnThanhToan.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnThanhToan.setBackground(Color.BLACK);
		btnThanhToan.setBounds(540, 457, 112, 29);
		getContentPane().add(btnThanhToan);
	}
}
