package sersystem;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.Date;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Payment extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String previousPage;
	private String applicationNumber;
	private JLabel qrCodeLabel;
	private Connection connection;
	private JLabel totalAmountLabel;
	private JLabel remainingBalanceLabel;
	private JTextField paymentAmountField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String applicationNumber = "AB123"; // Replace this with the actual application number
					Payment frame = new Payment("ApplicantDB", applicationNumber);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Payment(String previousPage, String applicationNumber) throws SQLException {
		this.previousPage = previousPage;
		this.applicationNumber = applicationNumber;
		initializeDatabase();
		initializeComponents();
		loadPaymentInfo();
	}

	private void initializeDatabase() throws SQLException {
		connection = db_connection.getConnection();
		System.out.println("Database connected successfully");
	}

	private void initializeComponents() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 673, 611);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 0, 255));
		panel.setBounds(0, 0, 657, 80);
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
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logout();
			}
		});

		btnLogOut.setForeground(new Color(128, 0, 255));
		btnLogOut.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnLogOut.setBackground(new Color(248, 202, 20));
		btnLogOut.setBounds(521, 28, 107, 23);
		panel.add(btnLogOut);

		RoundedPanel panel_1 = new RoundedPanel();
		panel_1.setBorder(null);
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(41, 110, 580, 401);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel = new JLabel("Welcome Future Dreamer!");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		lblNewLabel.setBounds(22, 11, 345, 44);
		panel_1.add(lblNewLabel);

		JLabel lblApplicationNumber = new JLabel("Application Number: " + applicationNumber);
		lblApplicationNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblApplicationNumber.setBounds(22, 55, 345, 20);
		panel_1.add(lblApplicationNumber);

		RoundedPanel panel_2 = new RoundedPanel();
		panel_2.setBorder(null);
		panel_2.setBackground(new Color(234, 234, 234));
		panel_2.setBounds(22, 86, 537, 230);
		panel_1.add(panel_2);
		panel_2.setLayout(null);

		RoundedButton btnGCash = new RoundedButton("GCash");
		btnGCash.setForeground(new Color(250, 200, 27));
		btnGCash.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnGCash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showQRCode("gcash");
			}
		});
		btnGCash.setBackground(new Color(128, 0, 255));
		btnGCash.setBounds(10, 11, 255, 23);
		panel_2.add(btnGCash);

		RoundedButton btnPayMaya = new RoundedButton("PayMaya");
		btnPayMaya.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnPayMaya.setForeground(new Color(250, 200, 27));
		btnPayMaya.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showQRCode("paymaya");
			}
		});
		btnPayMaya.setBackground(new Color(128, 0, 255));
		btnPayMaya.setBounds(275, 11, 252, 23);
		panel_2.add(btnPayMaya);

		JLabel lblNewLabel_2 = new JLabel("Transfer Fees may apply.");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(10, 205, 164, 14);
		panel_2.add(lblNewLabel_2);

		qrCodeLabel = new JLabel();
		qrCodeLabel.setBounds(173, 45, 181, 150);
		panel_2.add(qrCodeLabel);

		totalAmountLabel = new JLabel("Total Amount: ");
		totalAmountLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		totalAmountLabel.setBounds(22, 327, 345, 20);
		panel_1.add(totalAmountLabel);

		remainingBalanceLabel = new JLabel("Remaining Balance: ");
		remainingBalanceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		remainingBalanceLabel.setBounds(22, 358, 345, 20);
		panel_1.add(remainingBalanceLabel);

		paymentAmountField = new JTextField();
		paymentAmountField.setBounds(377, 327, 86, 20);
		panel_1.add(paymentAmountField);
		paymentAmountField.setColumns(10);

		RoundedButton btnConfirm = new RoundedButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmPayment();
			}
		});

		btnConfirm.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnConfirm.setBackground(new Color(128, 0, 255));
		btnConfirm.setForeground(new Color(250, 200, 27));
		btnConfirm.setBounds(470, 327, 89, 23);
		panel_1.add(btnConfirm);

		RoundedButton rndbtnBack = new RoundedButton("back");
		rndbtnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // Close the current frame
				ApplicantDB applicantDB = new ApplicantDB(applicationNumber); // Assuming applicationNumber is
																				// defined elsewhere
				applicantDB.setVisible(true);
			}
		});

		rndbtnBack.setText("Back");
		rndbtnBack.setForeground(new Color(250, 200, 27));
		rndbtnBack.setFont(new Font("Tahoma", Font.BOLD, 11));
		rndbtnBack.setBackground(new Color(128, 0, 255));
		rndbtnBack.setBounds(532, 522, 89, 23);
		contentPane.add(rndbtnBack);
	}

	private void loadPaymentInfo() {
		try {
			String sql = "SELECT si.program, si.year, s.application_status " + "FROM student_information si "
					+ "JOIN student s ON si.student_information_id = s.student_information_id "
					+ "WHERE s.application_number = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, applicationNumber);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				String program = rs.getString("program");
				int year = rs.getInt("year");
				String status = rs.getString("application_status");

				BigDecimal totalAmount = calculateTotalAmount(program, year);
				BigDecimal paidAmount = getPaidAmount();
				BigDecimal remainingBalance = totalAmount.subtract(paidAmount);

				totalAmountLabel.setText("Total Amount: " + totalAmount.setScale(2, RoundingMode.HALF_UP) + " PHP");
				remainingBalanceLabel
						.setText("Remaining Balance: " + remainingBalance.setScale(2, RoundingMode.HALF_UP) + " PHP");

				if ("ENROLLED".equals(status)) {
					paymentAmountField.setEnabled(false);
					JOptionPane.showMessageDialog(this, "You are already enrolled. No further payment is required.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error loading payment info: " + e.getMessage(), "Database Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private BigDecimal calculateTotalAmount(String program, int year) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		String programAbbreviation = getProgramAbbreviation(program);
		System.out.println("Program Abbreviation: " + programAbbreviation);
		String[] semesters = { "1st", "2nd" };
		String tableName = programAbbreviation + "_year" + year + "_" + year + "101";
		BigDecimal costPerUnit = getCostPerUnit(programAbbreviation);

		for (String sem : semesters) {
			if (year == getCurrentYear() && sem.equals("2nd") && getCurrentSemester().equals("1st")) {
				continue; // Skip future semester
			}

			String sql = "SELECT SUM(units) as total_units FROM " + tableName + " WHERE semester = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
				pstmt.setString(1, sem);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					int totalUnits = rs.getInt("total_units");
					totalAmount = totalAmount.add(costPerUnit.multiply(BigDecimal.valueOf(totalUnits)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error calculating total amount: " + e.getMessage(),
						"Database Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return totalAmount;
	}

	private BigDecimal getPaidAmount() {
		BigDecimal paidAmount = BigDecimal.ZERO;
		String sql = "SELECT SUM(payment_amount) FROM student_payment WHERE student_id = "
				+ "(SELECT student_id FROM student WHERE application_number = ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, applicationNumber);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				paidAmount = rs.getBigDecimal(1);
				if (paidAmount == null) {
					paidAmount = BigDecimal.ZERO;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error fetching paid amount: " + e.getMessage(), "Database Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return paidAmount;
	}

	private void confirmPayment() {
		String paymentAmountStr = paymentAmountField.getText().trim();
		if (paymentAmountStr.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter a payment amount.");
			return;
		}

		try {
			BigDecimal paymentAmount = new BigDecimal(paymentAmountStr);
			BigDecimal remainingBalance = new BigDecimal(
					remainingBalanceLabel.getText().split(":")[1].trim().split(" ")[0]);

			if (paymentAmount.compareTo(BigDecimal.ZERO) <= 0) {
				JOptionPane.showMessageDialog(this, "Payment amount must be greater than zero.");
				return;
			}

			if (paymentAmount.compareTo(remainingBalance) > 0) {
				JOptionPane.showMessageDialog(this, "Payment amount cannot exceed the remaining balance.");
				return;
			}

			if (insertPaymentRecord(paymentAmount)) {
				JOptionPane.showMessageDialog(this, "Payment recorded successfully!");
				updateStudentStatus();
				loadPaymentInfo(); // Refresh the payment info
			} else {
				JOptionPane.showMessageDialog(this, "Failed to record payment. Please try again.");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Invalid payment amount. Please enter a valid number.");
		}
	}

	private boolean insertPaymentRecord(BigDecimal amount) {
		String sql = "INSERT INTO student_payment (student_id, payment_amount, payment_date, student_information_id) "
				+ "SELECT s.student_id, ?, ?, s.student_information_id " + "FROM student s "
				+ "WHERE s.application_number = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setBigDecimal(1, amount);
			pstmt.setDate(2, Date.valueOf(LocalDate.now()));
			pstmt.setString(3, applicationNumber);
			int affectedRows = pstmt.executeUpdate();
			return affectedRows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error inserting payment record: " + e.getMessage(), "Database Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	private void updateStudentStatus() {
		String sql = "UPDATE student SET application_status = 'ENROLLED' WHERE application_number = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, applicationNumber);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error updating student status: " + e.getMessage(), "Database Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private String getProgramAbbreviation(String program) {
		if (program.equalsIgnoreCase("BS in Information Technology") || program.equalsIgnoreCase("BSIT")) {
			return "bsit";
		} else if (program.equalsIgnoreCase("BS in Psychology") || program.equalsIgnoreCase("BSPsych")) {
			return "bspsych";
		} else {
			System.out.println("Unknown program: " + program + ". Defaulting to BSIT.");
			return "bsit";
		}
	}

	private String getCurrentSemester() {
		String sql = "SELECT current_semester FROM system_settings WHERE id = 1";
		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			if (rs.next()) {
				return rs.getString("current_semester");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error fetching current semester: " + e.getMessage(), "Database Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return "1st"; // Default to 1st semester if there's an error
	}

	private int getCurrentYear() {
		String sql = "SELECT year FROM student_information WHERE student_information_id = (SELECT student_information_id FROM student WHERE application_number = ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, applicationNumber);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("year");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error fetching current year: " + e.getMessage(), "Database Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return 1; // Default to 1st year if there's an error
	}

	private BigDecimal getCostPerUnit(String programAbbreviation) {
		String sql = "SELECT cost_per_unit FROM program_costs WHERE program = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, programAbbreviation);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getBigDecimal("cost_per_unit");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error fetching cost per unit: " + e.getMessage(), "Database Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return new BigDecimal("500.00"); // Default value if there's an error
	}

	private void showQRCode(String paymentMethod) {
		String imagePath = "C:\\Users\\Administrator\\Documents\\resources\\" + paymentMethod + "_qr.png";
		File file = new File(imagePath);

		if (!file.exists()) {
			System.out.println("File does not exist: " + imagePath);
			qrCodeLabel.setIcon(null);
			qrCodeLabel.setText("QR code not available for now.");
			return;
		}

		try {
			BufferedImage img = ImageIO.read(file);
			if (img != null) {
				Image scaledImg = img.getScaledInstance(qrCodeLabel.getWidth(), qrCodeLabel.getHeight(),
						Image.SCALE_SMOOTH);
				qrCodeLabel.setIcon(new ImageIcon(scaledImg));
				qrCodeLabel.setText(null);
				System.out.println("Successfully loaded and displayed QR code for " + paymentMethod);
			} else {
				System.out.println("Failed to load image: " + imagePath);
				qrCodeLabel.setIcon(null);
				qrCodeLabel.setText("Failed to load QR code for " + paymentMethod);
			}
		} catch (IOException e) {
			e.printStackTrace();
			qrCodeLabel.setIcon(null);
			qrCodeLabel.setText("Error loading QR code for " + paymentMethod);
		}
	}

	private void logout() {
		int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Confirm Logout",
				JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			dispose();
			new ApplicantLogIn().setVisible(true);
		}
	}

	@Override
	public void dispose() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		super.dispose();
	}
}
