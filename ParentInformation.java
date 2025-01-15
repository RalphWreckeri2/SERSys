package sersystem;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.math.BigDecimal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ParentInformation extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldFatherName, textFieldFatherAge, textFieldFatherOccupation, textFieldFatherContact,
			textFieldFatherAddress;
	private JTextField textFieldMotherName, textFieldMotherAge, textFieldMotherOccupation, textFieldMotherContact,
			textFieldMotherAddress;
	private JTextField textFieldMonthlyIncome, textFieldGuardianName, textFieldGuardianContact,
			textFieldGuardianAddress;
	private JComboBox<String> comboBoxFatherDeceased, comboBoxMotherDeceased;
	private int studentInformationId;
	private Connection connection;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ParentInformation frame = new ParentInformation(1); // Example student_information_id
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ParentInformation(int studentInformationId) {
		this.studentInformationId = studentInformationId;
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 817, 788);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		initializeComponents();
		try {
			initializeDatabase();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int option = JOptionPane.showConfirmDialog(ParentInformation.this, "Are you sure you want to exit?",
						"Confirm Exit", JOptionPane.YES_NO_OPTION);

				if (option == JOptionPane.YES_OPTION) {
					System.exit(0); // Close the application
				}
			}
		});
	}

	private void initializeComponents() {
		// Header panel
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(128, 0, 255));
		panel.setBounds(0, 0, 801, 86);
		contentPane.add(panel);

		JLabel lblCollegeApplication = new JLabel("College Application");
		lblCollegeApplication.setForeground(new Color(255, 198, 0));
		lblCollegeApplication.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblCollegeApplication.setBounds(491, 1, 300, 80);
		panel.add(lblCollegeApplication);

		JLabel lblDreams = new JLabel("DREAMS");
		lblDreams.setForeground(new Color(250, 194, 5));
		lblDreams.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDreams.setBounds(96, 16, 70, 43);
		panel.add(lblDreams);

		JLabel lblUniversity = new JLabel("UNIVERSITY");
		lblUniversity.setForeground(Color.WHITE);
		lblUniversity.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblUniversity.setBounds(96, 28, 70, 43);
		panel.add(lblUniversity);

		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(10, 1, 87, 84);
		panel.add(lblNewLabel_1);

		ImageIcon originalIcon = new ImageIcon("C:\\Users\\Administrator\\Documents\\resources\\purplelogo.png");
		Image img = originalIcon.getImage();
		Image scaledImg = img.getScaledInstance(lblNewLabel_1.getWidth(), lblNewLabel_1.getHeight(),
				Image.SCALE_SMOOTH);

		lblNewLabel_1.setIcon(new ImageIcon(scaledImg));

		JLabel lblNewLabel = new JLabel(
				"Stay informed every step of the way by visiting our Applicant's Portal! This platform is tailored to help you monitor your application status,");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(74, 97, 676, 21);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel(
				"view important updates, and access any additional information about your journey with us. Check in regularly to stay on track as you");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(84, 112, 649, 21);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_2_1 = new JLabel("move closer to becoming part of Dreams University!");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2_1.setBounds(277, 129, 255, 21);
		contentPane.add(lblNewLabel_2_1);

		// Main content
		RoundedPanel panel_1 = new RoundedPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(null);
		panel_1.setBackground(new Color(245, 245, 245));
		panel_1.setBounds(33, 166, 741, 525);
		contentPane.add(panel_1);

		// Father Information
		JLabel lblFather = new JLabel("Father");
		lblFather.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFather.setBounds(20, 75, 56, 25);
		panel_1.add(lblFather);

		JLabel lblFatherDeceased = new JLabel("Deceased:");
		lblFatherDeceased.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFatherDeceased.setBounds(20, 111, 62, 25);
		panel_1.add(lblFatherDeceased);

		String[] deceasedOptions = { "No", "Yes" };
		comboBoxFatherDeceased = new JComboBox<>(deceasedOptions);
		comboBoxFatherDeceased.setBounds(92, 111, 273, 25);
		panel_1.add(comboBoxFatherDeceased);

		JLabel lblFatherName = new JLabel("Name*:");
		lblFatherName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFatherName.setBounds(20, 147, 44, 25);
		panel_1.add(lblFatherName);

		textFieldFatherName = new JTextField();
		textFieldFatherName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldFatherName.setBounds(92, 147, 273, 25);
		panel_1.add(textFieldFatherName);

		JLabel lblFatherAge = new JLabel("Age*:");
		lblFatherAge.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFatherAge.setBounds(20, 183, 62, 25);
		panel_1.add(lblFatherAge);

		textFieldFatherAge = new JTextField();
		textFieldFatherAge.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldFatherAge.setBounds(91, 183, 273, 25);
		panel_1.add(textFieldFatherAge);

		JLabel lblFatherOccupation = new JLabel("Occupation:");
		lblFatherOccupation.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFatherOccupation.setBounds(20, 221, 69, 25);
		panel_1.add(lblFatherOccupation);

		textFieldFatherOccupation = new JTextField();
		textFieldFatherOccupation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldFatherOccupation.setBounds(91, 219, 273, 25);
		panel_1.add(textFieldFatherOccupation);

		JLabel lblFatherContact = new JLabel("Contact No.:");
		lblFatherContact.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFatherContact.setBounds(20, 257, 69, 25);
		panel_1.add(lblFatherContact);

		textFieldFatherContact = new JTextField();
		textFieldFatherContact.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldFatherContact.setBounds(91, 255, 273, 25);
		panel_1.add(textFieldFatherContact);

		JLabel lblFatherAddress = new JLabel("Address*:");
		lblFatherAddress.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFatherAddress.setBounds(20, 293, 69, 25);
		panel_1.add(lblFatherAddress);

		textFieldFatherAddress = new JTextField();
		textFieldFatherAddress.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldFatherAddress.setBounds(91, 291, 273, 25);
		panel_1.add(textFieldFatherAddress);

		// Mother Information
		JLabel lblMother = new JLabel("Mother");
		lblMother.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMother.setBounds(368, 75, 56, 25);
		panel_1.add(lblMother);

		JLabel lblMotherDeceased = new JLabel("Deceased:");
		lblMotherDeceased.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMotherDeceased.setBounds(368, 111, 62, 25);
		panel_1.add(lblMotherDeceased);

		comboBoxMotherDeceased = new JComboBox<>(deceasedOptions);
		comboBoxMotherDeceased.setBounds(440, 111, 273, 25);
		panel_1.add(comboBoxMotherDeceased);

		JLabel lblMotherName = new JLabel("Name*:");
		lblMotherName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMotherName.setBounds(368, 147, 44, 25);
		panel_1.add(lblMotherName);

		textFieldMotherName = new JTextField();
		textFieldMotherName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldMotherName.setBounds(440, 147, 273, 25);
		panel_1.add(textFieldMotherName);

		JLabel lblMotherAge = new JLabel("Age*:");
		lblMotherAge.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMotherAge.setBounds(368, 183, 62, 25);
		panel_1.add(lblMotherAge);

		textFieldMotherAge = new JTextField();
		textFieldMotherAge.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldMotherAge.setBounds(440, 183, 273, 25);
		panel_1.add(textFieldMotherAge);

		JLabel lblMotherOccupation = new JLabel("Occupation:");
		lblMotherOccupation.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMotherOccupation.setBounds(368, 221, 69, 25);
		panel_1.add(lblMotherOccupation);

		textFieldMotherOccupation = new JTextField();
		textFieldMotherOccupation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldMotherOccupation.setBounds(440, 219, 273, 25);
		panel_1.add(textFieldMotherOccupation);

		JLabel lblMotherContact = new JLabel("Contact No.:");
		lblMotherContact.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMotherContact.setBounds(368, 257, 69, 25);
		panel_1.add(lblMotherContact);

		textFieldMotherContact = new JTextField();
		textFieldMotherContact.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldMotherContact.setBounds(440, 255, 273, 25);
		panel_1.add(textFieldMotherContact);

		JLabel lblMotherAddress = new JLabel("Address*:");
		lblMotherAddress.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMotherAddress.setBounds(368, 293, 69, 25);
		panel_1.add(lblMotherAddress);

		textFieldMotherAddress = new JTextField();
		textFieldMotherAddress.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldMotherAddress.setBounds(440, 291, 273, 25);
		panel_1.add(textFieldMotherAddress);

		// Monthly Income
		JLabel lblMonthlyIncome = new JLabel("Estimated Monthly Family Income (Optional):");
		lblMonthlyIncome.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMonthlyIncome.setBounds(20, 329, 238, 25);
		panel_1.add(lblMonthlyIncome);

		textFieldMonthlyIncome = new JTextField();
		textFieldMonthlyIncome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldMonthlyIncome.setBounds(255, 327, 458, 25);
		panel_1.add(textFieldMonthlyIncome);

		JLabel lblGuardianName = new JLabel("Guardian Name:");
		lblGuardianName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGuardianName.setBounds(20, 401, 116, 25);
		panel_1.add(lblGuardianName);

		textFieldGuardianName = new JTextField();
		textFieldGuardianName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldGuardianName.setBounds(140, 399, 573, 25);
		panel_1.add(textFieldGuardianName);

		JLabel lblGuardianContact = new JLabel("Guardian Contact No:");
		lblGuardianContact.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGuardianContact.setBounds(20, 437, 116, 25);
		panel_1.add(lblGuardianContact);

		textFieldGuardianContact = new JTextField();
		textFieldGuardianContact.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldGuardianContact.setBounds(140, 435, 573, 25);
		panel_1.add(textFieldGuardianContact);

		JLabel lblGuardianAddress = new JLabel("Guardian Address:");
		lblGuardianAddress.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGuardianAddress.setBounds(20, 473, 116, 25);
		panel_1.add(lblGuardianAddress);

		textFieldGuardianAddress = new JTextField();
		textFieldGuardianAddress.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldGuardianAddress.setBounds(140, 471, 573, 25);
		panel_1.add(textFieldGuardianAddress);

		RoundedPanel panel_1_1 = new RoundedPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setBackground(Color.WHITE);
		panel_1_1.setBounds(20, 26, 693, 25);
		panel_1.add(panel_1_1);

		JLabel lblStudentInformation = new JLabel("Student Information");
		lblStudentInformation.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblStudentInformation.setBounds(279, 0, 155, 25);
		panel_1_1.add(lblStudentInformation);

		RoundedPanel panel_1_1_1 = new RoundedPanel();
		panel_1_1_1.setLayout(null);
		panel_1_1_1.setBackground(Color.WHITE);
		panel_1_1_1.setBounds(20, 365, 693, 25);
		panel_1.add(panel_1_1_1);

		JLabel lblGuardianInformationif = new JLabel("Guardian Information (if it applies)");
		lblGuardianInformationif.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblGuardianInformationif.setBounds(242, 0, 231, 25);
		panel_1_1_1.add(lblGuardianInformationif);

		// Next and Back buttons
		RoundedButton rndbtnNext = new RoundedButton("Next");
		rndbtnNext.setText("Next");
		rndbtnNext.setForeground(new Color(255, 198, 0));
		rndbtnNext.setFont(new Font("Tahoma", Font.BOLD, 11));
		rndbtnNext.setBackground(new Color(128, 0, 255));
		rndbtnNext.setBounds(702, 702, 72, 23);
		contentPane.add(rndbtnNext);

		// Add action listeners
		rndbtnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validateForm()) {
					saveParentInformation();
				}
			}
		});

		// Add action listeners to deceased dropdowns
		comboBoxFatherDeceased.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateFatherFieldsState();
			}
		});

		comboBoxMotherDeceased.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateMotherFieldsState();
			}
		});

		// Add key listeners to contact number fields
		textFieldFatherContact.addKeyListener(new NumberOnlyKeyListener());
		textFieldMotherContact.addKeyListener(new NumberOnlyKeyListener());
		textFieldGuardianContact.addKeyListener(new NumberOnlyKeyListener());

		// Add key listeners to age fields (new implementation)
		textFieldFatherAge.addKeyListener(new NumberOnlyKeyListener());
		textFieldMotherAge.addKeyListener(new NumberOnlyKeyListener());
	}

	private void initializeDatabase() throws SQLException {
		connection = db_connection.getConnection();
	}

	private boolean validateForm() {
		boolean isValid = true;
		StringBuilder errorMessage = new StringBuilder();

		boolean fatherDeceased = comboBoxFatherDeceased.getSelectedItem().equals("Yes");
		boolean motherDeceased = comboBoxMotherDeceased.getSelectedItem().equals("Yes");

		// Validate father's information
		if (!fatherDeceased) {
			if (textFieldFatherName.getText().trim().isEmpty()) {
				isValid = false;
				errorMessage.append("Father's name is required.\n");
			}
			if (textFieldFatherAge.getText().trim().isEmpty()) {
				isValid = false;
				errorMessage.append("Father's age is required.\n");
			} else if (!isValidInteger(textFieldFatherAge.getText().trim())) {
				isValid = false;
				errorMessage.append("Father's age must be a valid number.\n");
			}
			if (textFieldFatherAddress.getText().trim().isEmpty()) {
				isValid = false;
				errorMessage.append("Father's address is required.\n");
			}
		}

		// Validate mother's information
		if (!motherDeceased) {
			if (textFieldMotherName.getText().trim().isEmpty()) {
				isValid = false;
				errorMessage.append("Mother's name is required.\n");
			}
			if (textFieldMotherAge.getText().trim().isEmpty()) {
				isValid = false;
				errorMessage.append("Mother's age is required.\n");
			} else if (!isValidInteger(textFieldMotherAge.getText().trim())) {
				isValid = false;
				errorMessage.append("Mother's age must be a valid number.\n");
			}
			if (textFieldMotherAddress.getText().trim().isEmpty()) {
				isValid = false;
				errorMessage.append("Mother's address is required.\n");
			}
		}

		// Validate guardian information if both parents are deceased
		if (fatherDeceased && motherDeceased) {
			if (textFieldGuardianName.getText().trim().isEmpty()) {
				isValid = false;
				errorMessage.append("Guardian's name is required when both parents are deceased.\n");
			}
			if (textFieldGuardianContact.getText().trim().isEmpty()) {
				isValid = false;
				errorMessage.append("Guardian's contact is required when both parents are deceased.\n");
			}
			if (textFieldGuardianAddress.getText().trim().isEmpty()) {
				isValid = false;
				errorMessage.append("Guardian's address is required when both parents are deceased.\n");
			}
		}

		// Validate monthly income (optional)
		if (!textFieldMonthlyIncome.getText().trim().isEmpty()) {
			try {
				new BigDecimal(textFieldMonthlyIncome.getText().trim());
			} catch (NumberFormatException e) {
				isValid = false;
				errorMessage.append("Monthly income must be a valid number.\n");
			}
		}

		if (!isValid) {
			JOptionPane.showMessageDialog(this, errorMessage.toString(), "Validation Error", JOptionPane.ERROR_MESSAGE);
		}

		return isValid;
	}

	private boolean isValidInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private void updateFatherFieldsState() {
		boolean isDeceased = comboBoxFatherDeceased.getSelectedItem().equals("Yes");
		textFieldFatherName.setEnabled(!isDeceased);
		textFieldFatherAge.setEnabled(!isDeceased);
		textFieldFatherOccupation.setEnabled(!isDeceased);
		textFieldFatherContact.setEnabled(!isDeceased);
		textFieldFatherAddress.setEnabled(!isDeceased);

		if (isDeceased) {
			textFieldFatherName.setText("");
			textFieldFatherAge.setText("");
			textFieldFatherOccupation.setText("");
			textFieldFatherContact.setText("");
			textFieldFatherAddress.setText("");
		}
	}

	private void updateMotherFieldsState() {
		boolean isDeceased = comboBoxMotherDeceased.getSelectedItem().equals("Yes");
		textFieldMotherName.setEnabled(!isDeceased);
		textFieldMotherAge.setEnabled(!isDeceased);
		textFieldMotherOccupation.setEnabled(!isDeceased);
		textFieldMotherContact.setEnabled(!isDeceased);
		textFieldMotherAddress.setEnabled(!isDeceased);

		if (isDeceased) {
			textFieldMotherName.setText("");
			textFieldMotherAge.setText("");
			textFieldMotherOccupation.setText("");
			textFieldMotherContact.setText("");
			textFieldMotherAddress.setText("");
		}
	}

	private void saveParentInformation() {
		String sql = "INSERT INTO parent_information (student_information_id, father_name, father_age, father_occupation, "
				+ "father_contact, father_address, father_deceased, mother_name, mother_age, mother_occupation, "
				+ "mother_contact, mother_address, mother_deceased, monthly_income, guardian_name, guardian_contact, "
				+ "guardian_address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setInt(1, studentInformationId);

			// Father information
			boolean fatherDeceased = comboBoxFatherDeceased.getSelectedItem().equals("Yes");
			pstmt.setString(2, fatherDeceased ? null : textFieldFatherName.getText().trim());
			pstmt.setObject(3, fatherDeceased ? null : Integer.parseInt(textFieldFatherAge.getText().trim()));
			pstmt.setString(4, fatherDeceased ? null : textFieldFatherOccupation.getText().trim());
			pstmt.setString(5, fatherDeceased ? null : textFieldFatherContact.getText().trim());
			pstmt.setString(6, fatherDeceased ? null : textFieldFatherAddress.getText().trim());
			pstmt.setBoolean(7, fatherDeceased);

			// Mother information
			boolean motherDeceased = comboBoxMotherDeceased.getSelectedItem().equals("Yes");
			pstmt.setString(8, motherDeceased ? null : textFieldMotherName.getText().trim());
			pstmt.setObject(9, motherDeceased ? null : Integer.parseInt(textFieldMotherAge.getText().trim()));
			pstmt.setString(10, motherDeceased ? null : textFieldMotherOccupation.getText().trim());
			pstmt.setString(11, motherDeceased ? null : textFieldMotherContact.getText().trim());
			pstmt.setString(12, motherDeceased ? null : textFieldMotherAddress.getText().trim());
			pstmt.setBoolean(13, motherDeceased);

			// Handle monthly income
			String monthlyIncome = textFieldMonthlyIncome.getText().trim();
			if (monthlyIncome.isEmpty()) {
				pstmt.setNull(14, java.sql.Types.DECIMAL);
			} else {
				pstmt.setBigDecimal(14, new BigDecimal(monthlyIncome));
			}

			// Guardian information
			pstmt.setString(15, textFieldGuardianName.getText().trim());
			pstmt.setString(16, textFieldGuardianContact.getText().trim());
			pstmt.setString(17, textFieldGuardianAddress.getText().trim());

			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {
				JOptionPane.showMessageDialog(this, "Parent information saved successfully.", "Success",
						JOptionPane.INFORMATION_MESSAGE);
				// Navigate to SiblingInformation page
				SiblingInformation siblingInfo = new SiblingInformation(studentInformationId);
				siblingInfo.setVisible(true);
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Failed to save parent information.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
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

	// Add this inner class to handle number-only input for contact fields
	private class NumberOnlyKeyListener extends KeyAdapter {
		public void keyTyped(KeyEvent e) {
			char c = e.getKeyChar();
			if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
				e.consume();
			}
		}
	}
}
