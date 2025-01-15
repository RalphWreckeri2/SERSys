package sersystem;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

public class NewPassword extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String userEmail;
	private String userType;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewPassword frame = new NewPassword("test@example.com", "ApplicantLogIn");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public NewPassword(String email, String userType) {
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
		panel_1.setBounds(81, 115, 453, 213);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("Please enter your New Password.");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(130, 22, 221, 14);
		panel_1.add(lblNewLabel_2);

		RoundedButton btnNewButton = new RoundedButton("Confirm");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] newPasswordChars = passwordField.getPassword();
				char[] confirmPasswordChars = confirmPasswordField.getPassword();
				String newPassword = new String(newPasswordChars);
				String confirmPassword = new String(confirmPasswordChars);

				// Check if either password field is empty
				if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
					JOptionPane.showMessageDialog(NewPassword.this, "Please fill in both password fields.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (newPassword.length() < 5) {
					JOptionPane.showMessageDialog(NewPassword.this, "Password must be at least 5 characters long.");
				} else if (!newPassword.equals(confirmPassword)) {
					JOptionPane.showMessageDialog(NewPassword.this, "Passwords do not match. Please try again.");
				} else {
					if (updatePassword(userEmail, newPassword)) {
						JOptionPane.showMessageDialog(NewPassword.this, "Password updated successfully!");
						goToLogin();
					} else {
						JOptionPane.showMessageDialog(NewPassword.this, "Failed to update password. Please try again.");
					}
				}

				// Clear the password fields for security
				passwordField.setText("");
				confirmPasswordField.setText("");
			}
		});

		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setForeground(new Color(255, 198, 0));
		btnNewButton.setBackground(new Color(128, 0, 255));
		btnNewButton.setBounds(329, 167, 90, 23);
		panel_1.add(btnNewButton);

		JLabel lblNewLabel_2_2 = new JLabel("Enter New Password");
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_2.setBounds(36, 57, 150, 14);
		panel_1.add(lblNewLabel_2_2);

		passwordField = new JPasswordField();
		passwordField.setBounds(36, 74, 383, 20);
		panel_1.add(passwordField);

		JLabel lblNewLabel_2_2_1 = new JLabel("Re-enter New Password");
		lblNewLabel_2_2_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_2_1.setBounds(36, 105, 150, 14);
		panel_1.add(lblNewLabel_2_2_1);

		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(36, 124, 383, 20);
		panel_1.add(confirmPasswordField);
	}

	private boolean updatePassword(String email, String newPassword) {
		Connection connection = null;
		PreparedStatement updateStmt = null;
		PreparedStatement deleteStmt = null;

		try {
			connection = db_connection.getConnection();
			connection.setAutoCommit(false);

			String updateQuery;
			String deleteQuery;

			if ("StudentPortal".equals(userType)) {
				updateQuery = "UPDATE enrolled_student SET student_password = ? WHERE student_email = ?";
				deleteQuery = "DELETE FROM enrolled_student_reset_tokens WHERE enrolled_student_id = (SELECT enrolled_student_id FROM enrolled_student WHERE student_email = ?)";
			} else if ("ApplicantLogIn".equals(userType)) {
				updateQuery = "UPDATE student SET student_password = ? WHERE student_email = ?";
				deleteQuery = "DELETE FROM student_reset_tokens WHERE student_id = (SELECT student_id FROM student WHERE student_email = ?)";
			} else if ("AdminLogIn".equals(userType)) {
				updateQuery = "UPDATE admin SET admin_password = ? WHERE admin_email = ?";
				deleteQuery = "DELETE FROM admin_reset_tokens WHERE admin_id = (SELECT admin_id FROM admin WHERE admin_email = ?)";
			} else {
				JOptionPane.showMessageDialog(this, "Invalid user type.");
				return false;
			}

			updateStmt = connection.prepareStatement(updateQuery);
			updateStmt.setString(1, newPassword);
			updateStmt.setString(2, email);
			int rowsAffected = updateStmt.executeUpdate();

			if (rowsAffected > 0) {
				deleteStmt = connection.prepareStatement(deleteQuery);
				deleteStmt.setString(1, email);
				deleteStmt.executeUpdate();

				connection.commit();
				return true;
			} else {
				connection.rollback();
				return false;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		} finally {
			try {
				if (updateStmt != null)
					updateStmt.close();
				if (deleteStmt != null)
					deleteStmt.close();
				if (connection != null) {
					connection.setAutoCommit(true);
					connection.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void goToLogin() {
		if ("StudentPortal".equals(userType)) {
			StudentPortal loginFrame = new StudentPortal();
			loginFrame.setVisible(true); 
		} else if ("ApplicantLogIn".equals(userType)) {
			ApplicantLogIn loginFrame = new ApplicantLogIn();
			loginFrame.setVisible(true);
		} else if ("AdminLogIn".equals(userType)) {
			AdminLogIn adminLoginFrame = new AdminLogIn();
			adminLoginFrame.setVisible(true);
		}
		this.dispose();
	}
}