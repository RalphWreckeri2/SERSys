package sersystem;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminLogIn extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField AdminEmail;
	private JPasswordField AdminPassword;
 
	public AdminLogIn() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 629, 470);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 0, 255));
		panel.setBounds(0, 0, 613, 80);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Admin Sign In");
		lblNewLabel.setForeground(new Color(255, 198, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(416, 16, 187, 54);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(10, -3, 87, 84);
		panel.add(lblNewLabel_1);

		ImageIcon originalIcon = new ImageIcon("C:\\Users\\Administrator\\Documents\\resources\\purplelogo.png");
		Image img = originalIcon.getImage();
		Image scaledImg = img.getScaledInstance(lblNewLabel_1.getWidth(), lblNewLabel_1.getHeight(),
				Image.SCALE_SMOOTH);

		lblNewLabel_1.setIcon(new ImageIcon(scaledImg));

		JLabel lblDreams = new JLabel("DREAMS");
		lblDreams.setForeground(new Color(250, 194, 5));
		lblDreams.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDreams.setBounds(96, 16, 70, 43);
		panel.add(lblDreams);

		JLabel lblUniversity = new JLabel("UNIVERSITY");
		lblUniversity.setForeground(new Color(255, 255, 255));
		lblUniversity.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblUniversity.setBounds(96, 28, 70, 43);
		panel.add(lblUniversity);

		RoundedPanel panel_1 = new RoundedPanel();
		panel_1.setBorder(null);
		panel_1.setBackground(new Color(245, 245, 245));
		panel_1.setBounds(89, 121, 437, 234);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		AdminEmail = new JTextField();
		AdminEmail.setBackground(new Color(255, 255, 255));
		AdminEmail.setBounds(48, 70, 337, 20);
		panel_1.add(AdminEmail);
		AdminEmail.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Enter Email");
		lblNewLabel_2.setBounds(48, 56, 190, 14);
		panel_1.add(lblNewLabel_2);

		JLabel lblNewLabel_2_1 = new JLabel("Enter Password");
		lblNewLabel_2_1.setBounds(48, 101, 190, 14);
		panel_1.add(lblNewLabel_2_1);

		AdminPassword = new JPasswordField();
		AdminPassword.setBackground(new Color(255, 255, 255));
		AdminPassword.setBounds(48, 115, 337, 20);
		panel_1.add(AdminPassword);

		RoundedButton btnNewButton = new RoundedButton("Log In");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = AdminEmail.getText().trim();
				String password = new String(AdminPassword.getPassword()).trim();

				if (email.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(AdminLogIn.this, "Please fill in all fields!", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					validateLogin(email, password);
				}
			}
		});

		btnNewButton.setForeground(new Color(255, 198, 0));
		btnNewButton.setBackground(new Color(128, 0, 255));
		btnNewButton.setBounds(48, 156, 72, 23);
		panel_1.add(btnNewButton);

		RoundedButton btnForgotPassword = new RoundedButton("Forgot Password");
		btnForgotPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ForgotPassword fpFrame = new ForgotPassword("AdminLogIn");
				fpFrame.setVisible(true);
				AdminLogIn.this.setVisible(false);
			}
		});

		btnForgotPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnForgotPassword.setForeground(new Color(255, 198, 0));
		btnForgotPassword.setBackground(new Color(128, 0, 255));
		btnForgotPassword.setBounds(130, 156, 138, 23);
		panel_1.add(btnForgotPassword);

		RoundedButton btnContactUs = new RoundedButton("Contact Us");
		btnContactUs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Contactpage contactFrame = new Contactpage("AdminLogIn");
				contactFrame.setVisible(true);
				AdminLogIn.this.setVisible(false);
			}
		});

		btnContactUs.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnContactUs.setForeground(new Color(255, 198, 0));
		btnContactUs.setBackground(new Color(128, 0, 255));
		btnContactUs.setBounds(278, 156, 107, 23);
		panel_1.add(btnContactUs);

		RoundedButton rndbtnBack = new RoundedButton("Log In");
		rndbtnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LandingPage homeFrame = new LandingPage();
				homeFrame.setVisible(true);
				AdminLogIn.this.setVisible(false);
			}
		});

		rndbtnBack.setText("Back");
		rndbtnBack.setForeground(new Color(255, 198, 0));
		rndbtnBack.setFont(new Font("Tahoma", Font.BOLD, 11));
		rndbtnBack.setBackground(new Color(128, 0, 255));
		rndbtnBack.setBounds(454, 379, 72, 23);
		contentPane.add(rndbtnBack);
	}

	private boolean validateLogin(String email, String password) {
		if (email.isEmpty() || password.isEmpty()) {
			return false;
		}

		Connection conn = db_connection.getConnection();
		if (conn != null) {
			try {
				// First, check if the email exists
				String checkEmailSql = "SELECT * FROM admin WHERE admin_email = ?";
				PreparedStatement checkEmailStmt = conn.prepareStatement(checkEmailSql);
				checkEmailStmt.setString(1, email);
				ResultSet emailRs = checkEmailStmt.executeQuery();

				if (!emailRs.next()) {
					// Email doesn't exist, but we don't reveal this yet
					return checkBothCredentials(email, password);
				}

				// If email exists, check the password
				String checkPasswordSql = "SELECT * FROM admin WHERE admin_email = ? AND admin_password = ?";
				PreparedStatement checkPasswordStmt = conn.prepareStatement(checkPasswordSql);
				checkPasswordStmt.setString(1, email);
				checkPasswordStmt.setString(2, password);
				ResultSet passwordRs = checkPasswordStmt.executeQuery();

				if (passwordRs.next()) {
					JOptionPane.showMessageDialog(AdminLogIn.this, "Log In Successful!");
					dispose(); // Close the current frame
					// For ADMIN DB
					AdminDB dashboard = new AdminDB();
					dashboard.setVisible(true);
					return true;
				} else {
					// Password is incorrect
					JOptionPane.showMessageDialog(AdminLogIn.this, "Invalid password!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	private boolean checkBothCredentials(String email, String password) {
		Connection conn = db_connection.getConnection();
		if (conn != null) {
			try {
				String sql = "SELECT * FROM admin WHERE admin_email = ? OR admin_password = ?";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, email);
				stmt.setString(2, password);
				ResultSet rs = stmt.executeQuery();

				if (!rs.next()) {
					// Both email and password are incorrect
					JOptionPane.showMessageDialog(AdminLogIn.this, "Invalid credentials!", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					// Email is incorrect
					JOptionPane.showMessageDialog(AdminLogIn.this, "Invalid email address!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminLogIn frame = new AdminLogIn();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}