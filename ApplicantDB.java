package sersystem;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class ApplicantDB extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblStatus;
	private String applicationNumber;
	private RoundedButton btnProceedToBilling;
 
	public ApplicantDB(String applicationNumber) {
		this.applicationNumber = applicationNumber;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 555);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 0, 255));
		panel.setBounds(0, 0, 734, 80);
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

		RoundedButton btnLogOut = new RoundedButton("Log Out");
		btnLogOut.setBounds(596, 28, 107, 23);
		panel.add(btnLogOut);
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logout();
			}
		});

		btnLogOut.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnLogOut.setForeground(new Color(128, 0, 255));
		btnLogOut.setBackground(new Color(248, 202, 20));

		RoundedPanel panel_1 = new RoundedPanel();
		panel_1.setBorder(null);
		panel_1.setBackground(new Color(245, 245, 245));
		panel_1.setBounds(52, 146, 648, 334);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel = new JLabel("Welcome Future Dreamer!");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		lblNewLabel.setBounds(53, 91, 345, 44);
		contentPane.add(lblNewLabel);

		JLabel lblStatusTitle = new JLabel("Current Application Status");
		lblStatusTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblStatusTitle.setBounds(20, 19, 239, 20);
		panel_1.add(lblStatusTitle);

		lblStatus = new JLabel("");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblStatus.setBounds(20, 50, 607, 120);
		lblStatus.setVerticalAlignment(SwingConstants.TOP);
		panel_1.add(lblStatus);

		btnProceedToBilling = new RoundedButton("Proceed to Billing");
		btnProceedToBilling.setBackground(new Color(128, 0, 255));
		btnProceedToBilling.setForeground(new Color(248, 202, 20));
		btnProceedToBilling.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnProceedToBilling.setBounds(20, 189, 150, 23);
		btnProceedToBilling.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					proceedToBilling();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel_1.add(btnProceedToBilling);

		JLabel lblThisIsFor = new JLabel("Note: This is for online transactions only.");
		lblThisIsFor.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblThisIsFor.setBounds(20, 223, 256, 20);
		panel_1.add(lblThisIsFor);

		JLabel lblNoteOnceYour = new JLabel(
				"Once your application is accepted, you can proceed to the billings in this same portal. ");
		lblNoteOnceYour.setBounds(52, 237, 488, 20);
		panel_1.add(lblNoteOnceYour);
		lblNoteOnceYour.setFont(new Font("Tahoma", Font.PLAIN, 11));

		JLabel lblIfYourPayment = new JLabel(
				"If your payment is cash, no date appointment will be set, but once you are submitting ");
		lblIfYourPayment.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblIfYourPayment.setBounds(52, 275, 545, 20);
		panel_1.add(lblIfYourPayment);

		JLabel lblTheDocumentsIn = new JLabel(
				"the documents in the Registrar, you are required to pay a down payment.");
		lblTheDocumentsIn.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTheDocumentsIn.setBounds(52, 291, 545, 20);
		panel_1.add(lblTheDocumentsIn);

		loadApplicantStatus();
	}

	private void loadApplicantStatus() {
		Connection conn = db_connection.getConnection();
		if (conn != null) {
			try {
				String sql = "SELECT application_status FROM student WHERE application_number = ?";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, applicationNumber);

				ResultSet rs = stmt.executeQuery();

				if (rs.next()) {
					String status = rs.getString("application_status");
					updateStatusDisplay(status);
					updateBillingButtonState(status);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error loading applicant status: " + e.getMessage());
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void updateStatusDisplay(String status) {
		String message;
		Color messageColor;

		switch (status.toUpperCase()) {
		case "PENDING":
			message = "Status: PENDING\n\nThank you for submitting your college application. We are currently reviewing your documents and assessing\nyour qualifications. This process ensures that every applicant is evaluated fairly and thoroughly. Please be\npatient as we complete this step. Visit this portal to see updates. If you have any questions in the\nmeantime, feel free to contact our admissions office.";
			messageColor = new Color(255, 165, 0); // Orange
			break;
		case "ACCEPTED":
			message = "Status: ACCEPTED\n\nCongratulations! We are thrilled to inform you that your college application has been accepted.\nYour hard work and achievements have earned you a place in our academic community, and we can't wait\nto welcome you. Further details and steps will be sent to you shortly via email. If you have any questions\nor need assistance, our admissions team is here to help. Welcome aboard!";
			messageColor = new Color(0, 128, 0); // Green
			break;
		case "DECLINED":
			message = "Status: DECLINED\n\nThank you for applying to our institution.\nAfter careful consideration of your application, we regret to inform you that we are unable to offer you\nadmission at this time. Please know that this decision was made based on various factors and does not diminish\nyour accomplishments. We encourage you to continue striving for your goals and exploring other opportunities\n that align with your aspirations. Our best wishes are with you for a bright and successful future.";
			messageColor = new Color(255, 0, 0); // Red
			break;
		case "CHECKING":
			message = "Status: CHECKING\n\nYour application status is currently being processed.\nOur admissions team is carefully reviewing all submissions to ensure fair and thorough evaluation.\nThis can take some time depending on the number of applications we receive.\nWe appreciate your patience. Kindly visit this portal for updates.\nIn the meantime, if you have any questions or require assistance, please reach out to our admissions office.";
			messageColor = new Color(0, 0, 255); // Blue
			break;
		case "ENROLLED":
			message = "Status: ENROLLED\n\nCongratulations on completing your enrollment! You are now officially part of our college community.\nWe are excited to have you join us on this academic journey and look forward to supporting you in achieving\nyour goals. Details about your class schedule, orientation, and campus resources will be shared with you soon.\nIf you have any questions or need further assistance, don’t hesitate to reach out to the admissions or student\nservices team. Welcome to the next chapter of your future!";
			messageColor = new Color(0, 128, 0); // Green
			break;
		default:
			message = "Status: " + status.toUpperCase() + "\n\n" + status;
			messageColor = Color.BLACK;
		}

		lblStatus.setText("<html><div style='width: 590px;'>" + message.replace("\n", "<br>") + "</div></html>");
		lblStatus.setForeground(messageColor);
	}

	private void updateBillingButtonState(String status) {
		if ("ACCEPTED".equalsIgnoreCase(status)) {
			btnProceedToBilling.setEnabled(true);
			btnProceedToBilling.setToolTipText("Click here to proceed to billing.");
		} else if ("ENROLLED".equalsIgnoreCase(status)) {
			btnProceedToBilling.setEnabled(true);
			btnProceedToBilling.setToolTipText("Click here to proceed to billing.");
		} else {
			btnProceedToBilling.setEnabled(false);
			btnProceedToBilling.setToolTipText("You can proceed to billing only after your application is accepted.");
		}
	} 

	private void proceedToBilling() throws SQLException {
		Payment paymentFrame = new Payment("ApplicantDB", this.applicationNumber);
		paymentFrame.setVisible(true);
		this.dispose();
	}

	private void logout() {
		int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Confirm Logout",
				JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			dispose();
			new ApplicantLogIn().setVisible(true);
		}
	}
}
