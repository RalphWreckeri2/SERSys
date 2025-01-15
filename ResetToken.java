package sersystem;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ResetToken extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String userEmail;
	private String userType;
	private JTextField textField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ResetToken frame = new ResetToken("test@example.com", "ApplicantLogIn");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ResetToken(String email, String userType) {
		this.userEmail = email;
		this.userType = userType;
		initializeComponents();
	}

	private void initializeComponents() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 629, 433);
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

		JLabel lblNewLabel = new JLabel("Forgot Password");
		lblNewLabel.setForeground(new Color(255, 198, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(393, 16, 210, 54);
		panel.add(lblNewLabel);

		RoundedPanel panel_1 = new RoundedPanel();
		panel_1.setBorder(null);
		panel_1.setBackground(new Color(245, 245, 245));
		panel_1.setBounds(80, 142, 453, 166);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("Please enter the Reset Token sent to your email.");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(77, 21, 322, 14);
		panel_1.add(lblNewLabel_2);

		RoundedButton btnNewButton = new RoundedButton("Back");
		btnNewButton.setText("Confirm");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String enteredToken = textField.getText().trim();
				if (enteredToken.isEmpty()) {
					JOptionPane.showMessageDialog(ResetToken.this, "Please enter a reset token.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					int result = handleTokenVerification(userEmail, enteredToken);
					if (result == 1) {
						JOptionPane.showMessageDialog(ResetToken.this, "Token verified successfully!");
						NewPassword newPasswordFrame = new NewPassword(userEmail, userType);
						newPasswordFrame.setVisible(true);
						ResetToken.this.dispose();
					} else if (result == 0) {
						JOptionPane.showMessageDialog(ResetToken.this, "Invalid token. Please try again.");
					} else if (result == -1) {
						int choice = JOptionPane.showConfirmDialog(ResetToken.this,
								"The reset token has expired. Would you like to request a new one?", "Token Expired",
								JOptionPane.YES_NO_OPTION);
						if (choice == JOptionPane.YES_OPTION) {
							ForgotPassword forgotPasswordFrame = new ForgotPassword(userType);
							forgotPasswordFrame.setVisible(true);
							ResetToken.this.dispose();
						}
					}
				}
			}
		});

		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setForeground(new Color(255, 198, 0));
		btnNewButton.setBackground(new Color(128, 0, 255));
		btnNewButton.setBounds(329, 111, 90, 23);
		panel_1.add(btnNewButton);

		JLabel lblNewLabel_2_2 = new JLabel("Enter Reset Token");
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_2.setBounds(36, 57, 150, 14);
		panel_1.add(lblNewLabel_2_2);

		textField = new JTextField();
		textField.setBounds(36, 74, 383, 20);
		panel_1.add(textField);
		textField.setColumns(10);
	}

	private int handleTokenVerification(String email, String enteredToken) {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			connection = db_connection.getConnection();

			String query;
			if ("StudentPortal".equals(userType)) {
				query = "SELECT esrt.reset_token, esrt.expiration_timestamp FROM enrolled_student_reset_tokens esrt "
						+ "JOIN enrolled_student es ON esrt.enrolled_student_id = es.enrolled_student_id "
						+ "WHERE es.student_email = ? " + "ORDER BY esrt.reset_token_id DESC LIMIT 1";
			} else if ("ApplicantLogIn".equals(userType)) {
				query = "SELECT srt.reset_token, srt.expiration_timestamp FROM student_reset_tokens srt "
						+ "JOIN student s ON srt.student_id = s.student_id " + "WHERE s.student_email = ? "
						+ "ORDER BY srt.reset_token_id DESC LIMIT 1";
			} else if ("AdminLogIn".equals(userType)) {
				query = "SELECT art.reset_token, art.expiration_timestamp FROM admin_reset_tokens art "
						+ "JOIN admin a ON art.admin_id = a.admin_id " + "WHERE a.admin_email = ? "
						+ "ORDER BY art.reset_token_id DESC LIMIT 1";
			} else {
				JOptionPane.showMessageDialog(this, "Invalid user type.");
				return 0;
			}

			stmt = connection.prepareStatement(query);
			stmt.setString(1, email);
			rs = stmt.executeQuery();

			if (rs.next()) {
				String storedToken = rs.getString("reset_token");
				Timestamp expirationTimestamp = rs.getTimestamp("expiration_timestamp");

				if (storedToken != null && storedToken.length() >= 5 && expirationTimestamp != null) {
					if (Instant.now().isBefore(expirationTimestamp.toInstant())) {
						return storedToken.equals(enteredToken.trim().toUpperCase()) ? 1 : 0;
					} else {
						return -1; // Token has expired
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "No reset token found for this email.");
			}
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "An error occurred while verifying the token. Please try again.");
			return 0;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
