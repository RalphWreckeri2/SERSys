package sersystem;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class ForgotPassword extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String previousPage;
	private JTextField EmailFP;
	private static final Logger LOGGER = Logger.getLogger(ForgotPassword.class.getName());
	private static final Properties emailProps = new Properties();

	static {
		try (InputStream input = ForgotPassword.class.getClassLoader().getResourceAsStream("email.properties")) {
			if (input == null) {
				LOGGER.severe("Unable to find email.properties");
			} else {
				emailProps.load(input);
			}
		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, "Error loading email properties", ex);
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ForgotPassword frame = new ForgotPassword("ApplicantLogIn");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ForgotPassword(String previousPage) {
		this.previousPage = previousPage;
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
		panel_1.setBounds(107, 136, 403, 186);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("Please enter the email address");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(114, 21, 195, 14);
		panel_1.add(lblNewLabel_2);

		JLabel lblNewLabel_2_1 = new JLabel("Enter Email Address");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2_1.setBounds(36, 74, 190, 14);
		panel_1.add(lblNewLabel_2_1);

		EmailFP = new JTextField();
		EmailFP.setBounds(36, 88, 329, 20);
		panel_1.add(EmailFP);
		EmailFP.setColumns(10);

		RoundedButton btnNewButton = new RoundedButton("Back");
		btnNewButton.setBounds(293, 130, 72, 23);
		panel_1.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goBack();
			}
		});

		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setForeground(new Color(255, 198, 0));
		btnNewButton.setBackground(new Color(128, 0, 255));

		RoundedButton rndbtnSendResetToken = new RoundedButton("Send Reset Token");
		rndbtnSendResetToken.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendResetTokenAction();
			}
		});

		rndbtnSendResetToken.setText("Send Reset Token");
		rndbtnSendResetToken.setForeground(new Color(255, 198, 0));
		rndbtnSendResetToken.setFont(new Font("Tahoma", Font.BOLD, 11));
		rndbtnSendResetToken.setBackground(new Color(128, 0, 255));
		rndbtnSendResetToken.setBounds(36, 130, 177, 23);
		panel_1.add(rndbtnSendResetToken);

		JLabel lblNewLabel_2_2 = new JLabel("you use for logging in or registration");
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2_2.setBounds(93, 35, 237, 14);
		panel_1.add(lblNewLabel_2_2);
	}

	private void goBack() {
		if ("ApplicantLogIn".equals(previousPage)) {
			ApplicantLogIn loginFrame = new ApplicantLogIn();
			loginFrame.setVisible(true);
		} else if ("AdminLogIn".equals(previousPage)) {
			AdminLogIn adminLoginFrame = new AdminLogIn();
			adminLoginFrame.setVisible(true);
		}
		this.setVisible(false);
	}

	private boolean storeResetTokenInDatabase(String email, String resetToken) {
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		PreparedStatement insertStmt = null;

		try {
			connection = db_connection.getConnection();
			connection.setAutoCommit(false);
			if ("StudentPortal".equals(previousPage)) {
				// Delete existing tokens for this specific student email
				String deleteQuery = "DELETE FROM enrolled_student_reset_tokens WHERE enrolled_student_id = (SELECT enrolled_student_id FROM enrolled_student WHERE student_email = ?)";
				deleteStmt = connection.prepareStatement(deleteQuery);
				deleteStmt.setString(1, email);
				deleteStmt.executeUpdate();

				// Insert new token for this specific student email
				String insertQuery = "INSERT INTO enrolled_student_reset_tokens (enrolled_student_id, reset_token, expiration_timestamp) VALUES ((SELECT enrolled_student_id FROM enrolled_student WHERE student_email = ?), ?, ?)";
				insertStmt = connection.prepareStatement(insertQuery);
				insertStmt.setString(1, email);
				insertStmt.setString(2, resetToken);
				insertStmt.setTimestamp(3, Timestamp.from(Instant.now().plus(5, ChronoUnit.MINUTES)));
				insertStmt.executeUpdate();
			} else if ("ApplicantLogIn".equals(previousPage)) {
				// Delete existing tokens for this specific student email
				String deleteQuery = "DELETE FROM student_reset_tokens WHERE student_id = (SELECT student_id FROM student WHERE student_email = ?)";
				deleteStmt = connection.prepareStatement(deleteQuery);
				deleteStmt.setString(1, email);
				deleteStmt.executeUpdate();

				// Insert new token for this specific student email
				String insertQuery = "INSERT INTO student_reset_tokens (student_id, reset_token, expiration_timestamp) VALUES ((SELECT student_id FROM student WHERE student_email = ?), ?, ?)";
				insertStmt = connection.prepareStatement(insertQuery);
				insertStmt.setString(1, email);
				insertStmt.setString(2, resetToken);
				insertStmt.setTimestamp(3, Timestamp.from(Instant.now().plus(5, ChronoUnit.MINUTES)));
				insertStmt.executeUpdate();
			} else if ("AdminLogIn".equals(previousPage)) {
				// Delete existing tokens for this specific admin email
				String deleteQuery = "DELETE FROM admin_reset_tokens WHERE admin_id = (SELECT admin_id FROM admin WHERE admin_email = ?)";
				deleteStmt = connection.prepareStatement(deleteQuery);
				deleteStmt.setString(1, email);
				deleteStmt.executeUpdate();

				// Insert new token for this specific admin email
				String insertQuery = "INSERT INTO admin_reset_tokens (admin_id, reset_token, expiration_timestamp) VALUES ((SELECT admin_id FROM admin WHERE admin_email = ?), ?, ?)";
				insertStmt = connection.prepareStatement(insertQuery);
				insertStmt.setString(1, email);
				insertStmt.setString(2, resetToken);
				insertStmt.setTimestamp(3, Timestamp.from(Instant.now().plus(5, ChronoUnit.MINUTES)));
				insertStmt.executeUpdate();
			} else {
				JOptionPane.showMessageDialog(this, "Invalid user type.");
				return false;
			}

			connection.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			return false;
		} finally {
			try {
				if (deleteStmt != null)
					deleteStmt.close();
				if (insertStmt != null)
					insertStmt.close();
				if (connection != null) {
					connection.setAutoCommit(true);
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void sendResetTokenAction() {
		String email = getEmailInput().trim();

		// Check for empty email
		if (email.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter an email address.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Check if the email exists in the database
		if (!isEmailExistInDatabase(email)) {
			JOptionPane.showMessageDialog(this, "This email address is not registered in our system.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		String resetToken = generateResetToken();

		if (storeResetTokenInDatabase(email, resetToken)) {
			String subject = "Password Reset";
			String body = "Good Day " + email + "\n\n";
			body += "We received a request for password change. Please copy the reset token to proceed." + "\n\n";
			body += "Your reset token: " + resetToken + "\n\n";
			body += "If you did not request a password reset, please ignore this email.";
			body += "\n\nBest regards,\nDreams University Support Team";

			sendResetTokenEmail(email, subject, body);
		} else {
			JOptionPane.showMessageDialog(this, "Failed to generate reset token. Please try again.");
		}
	}

	private boolean isEmailExistInDatabase(String email) {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			connection = db_connection.getConnection();
			String query;

			if ("StudentPortal".equals(previousPage)) {
				query = "SELECT COUNT(*) FROM enrolled_student WHERE student_email = ?";
			} else if ("ApplicantLogIn".equals(previousPage)) {
				query = "SELECT COUNT(*) FROM student WHERE student_email = ?";
			} else if ("AdminLogIn".equals(previousPage)) {
				query = "SELECT COUNT(*) FROM admin WHERE admin_email = ?";
			} else {
				return false; // Invalid user type
			}

			stmt = connection.prepareStatement(query);
			stmt.setString(1, email);
			rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
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

		return false;
	}

	private void sendResetTokenEmail(final String email, final String subject, final String body) {
		System.setProperty("mail.debug", "true");

		final String host = "smtp.gmail.com";
		final String from = getEmailFrom();
		final String password = getEmailPassword();

		if (from == null || password == null) {
			LOGGER.severe(
					"Email configuration is missing. Please set EMAIL_FROM and EMAIL_PASSWORD environment variables or update email.properties file.");
			JOptionPane.showMessageDialog(this, "Email configuration error. Please contact support.");
			return;
		}

		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject(subject);

			// Updated body message to reflect 5-minute expiration
			String updatedBody = body + "\n\nPlease note that this reset token will expire in 5 minutes.";
			message.setText(updatedBody);

			Transport.send(message);
			JOptionPane.showMessageDialog(this, "Reset token sent successfully to your email.");

			// Create and show the ResetToken frame, passing the email and user type
			ResetToken resetTokenFrame = new ResetToken(email, previousPage);
			resetTokenFrame.setVisible(true);
			this.setVisible(false);

		} catch (MessagingException e) {
			LOGGER.log(Level.SEVERE, "Failed to send email", e);
			JOptionPane.showMessageDialog(this, "Failed to send email. Please try again later.");
		}
	}

	private String generateResetToken() {
		// Generate a UUID and convert it to a string
		String token = UUID.randomUUID().toString();

		// Remove hyphens and truncate the token to the first 5 characters
		String truncatedToken = token.replaceAll("-", "").substring(0, 5);

		// Convert the token to uppercase
		return truncatedToken.toUpperCase();
	}

	private String getEmailInput() {
		return EmailFP.getText();
	}

	private String getEmailFrom() {
		String from = System.getenv("EMAIL_FROM");
		if (from == null) {
			from = emailProps.getProperty("email.from");
		}
		return from;
	}

	private String getEmailPassword() {
		String password = System.getenv("EMAIL_PASSWORD");
		if (password == null) {
			password = emailProps.getProperty("email.password");
		}
		return password;
	}
}