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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;

public class StudentInformation extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JDateChooser dateChooser;
	private JTextField textField_3;
	private JComboBox<String> genderComboBox;
	private JTextField textField_5;
	private JComboBox<String> maritalStatusComboBox;
	private JTextField textField_7;
	private JTextField textField_8;
	private JComboBox<String> programComboBox;
	private JComboBox<String> yearComboBox;
	private JComboBox<String> semesterComboBox;
	private JComboBox<String> sectionComboBox;
	private RoundedButton btnNext;

	private Connection connection;
	private PreparedStatement pstmt;
	private boolean isIrregular = false;
	private int studentInformationId = -1;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentInformation frame = new StudentInformation();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public StudentInformation() throws SQLException {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 757, 742);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		initializeComponents();
		initializeDatabase();
	}

	private void initializeComponents() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(128, 0, 255));
		panel.setBounds(0, 0, 741, 86);
		contentPane.add(panel);

		JLabel lblCollegeApplication = new JLabel("College Application");
		lblCollegeApplication.setForeground(new Color(255, 198, 0));
		lblCollegeApplication.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblCollegeApplication.setBounds(431, 1, 300, 80);
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
		lblNewLabel.setBounds(33, 95, 676, 21);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel(
				"view important updates, and access any additional information about your journey with us. Check in regularly to stay on track as you");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(43, 114, 649, 21);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_2_1 = new JLabel("move closer to becoming part of Dreams University!");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2_1.setBounds(245, 134, 255, 21);
		contentPane.add(lblNewLabel_2_1);

		RoundedPanel panel_1 = new RoundedPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(null);
		panel_1.setBackground(new Color(245, 245, 245));
		panel_1.setBounds(33, 171, 676, 480);
		contentPane.add(panel_1);

		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblName.setBounds(20, 214, 70, 25);
		panel_1.add(lblName);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField.setBounds(77, 214, 238, 25);
		panel_1.add(textField);

		JLabel lblAge = new JLabel("Age:");
		lblAge.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAge.setBounds(325, 214, 40, 25);
		panel_1.add(lblAge);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_1.setBounds(394, 212, 247, 25);
		textField_1.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
				}
			}
		});
		panel_1.add(textField_1);

		JLabel lblBirthdate = new JLabel("Birthdate:");
		lblBirthdate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBirthdate.setBounds(20, 250, 150, 25);
		panel_1.add(lblBirthdate);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(77, 250, 238, 25);
		panel_1.add(dateChooser);

		JLabel lblDataPrivacyNotice = new JLabel("Data Privacy Notice");
		lblDataPrivacyNotice.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDataPrivacyNotice.setBounds(238, 11, 204, 41);
		panel_1.add(lblDataPrivacyNotice);

		JLabel lblNewLabel_2_1_1 = new JLabel(
				"By submitting this application form, you acknowledge and consent to the collection, use, and processing of your personal information");
		lblNewLabel_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2_1_1.setBounds(10, 56, 637, 21);
		panel_1.add(lblNewLabel_2_1_1);

		JLabel lblNewLabel_2_1_1_1 = new JLabel(
				"by Dreams University in accordance with the Data Privacy Act of 2012. The information provided will be used solely for admission,");
		lblNewLabel_2_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2_1_1_1.setBounds(20, 73, 621, 21);
		panel_1.add(lblNewLabel_2_1_1_1);

		JLabel lblNewLabel_2_1_1_1_1 = new JLabel(
				"enrollment, and related administrative purposes. We are committed to protecting your privacy, and your data will be handled with");
		lblNewLabel_2_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2_1_1_1_1.setBounds(20, 91, 627, 21);
		panel_1.add(lblNewLabel_2_1_1_1_1);

		JLabel lblNewLabel_2_1_1_1_2 = new JLabel("the utmost confidentiality and stored securely.");
		lblNewLabel_2_1_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2_1_1_1_2.setBounds(212, 108, 238, 21);
		panel_1.add(lblNewLabel_2_1_1_1_2);

		JLabel lblNewLabel_2_1_1_1_2_1 = new JLabel(
				"If you have any questions regarding our data privacy practices, please contact us at dreamsuniversity@gmail.com.");
		lblNewLabel_2_1_1_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2_1_1_1_2_1.setBounds(53, 140, 565, 21);
		panel_1.add(lblNewLabel_2_1_1_1_2_1);

		RoundedPanel panel_1_1 = new RoundedPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setBackground(Color.WHITE);
		panel_1_1.setBounds(20, 172, 621, 25);
		panel_1.add(panel_1_1);

		JLabel lblStudentInformation = new JLabel("Student Information");
		lblStudentInformation.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblStudentInformation.setBounds(249, 0, 155, 25);
		panel_1_1.add(lblStudentInformation);

		JLabel lblContactNumber = new JLabel("Contact #:");
		lblContactNumber.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblContactNumber.setBounds(325, 250, 76, 25);
		panel_1.add(lblContactNumber);

		textField_3 = new JTextField();
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_3.setBounds(394, 252, 247, 25);
		textField_3.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
				}
			}
		});
		panel_1.add(textField_3);

		JLabel lblGender = new JLabel("Gender:");
		lblGender.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGender.setBounds(20, 286, 150, 25);
		panel_1.add(lblGender);

		String[] genders = { "Male", "Female", "Other" };
		genderComboBox = new JComboBox<>(genders);
		genderComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		genderComboBox.setBounds(77, 288, 238, 25);
		panel_1.add(genderComboBox);

		JLabel lblNationality = new JLabel("Nationality");
		lblNationality.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNationality.setBounds(325, 286, 76, 25);
		panel_1.add(lblNationality);

		textField_5 = new JTextField();
		textField_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_5.setBounds(394, 288, 247, 25);
		panel_1.add(textField_5);

		JLabel lblMaritalStatus = new JLabel("Civil Status");
		lblMaritalStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMaritalStatus.setBounds(20, 322, 150, 25);
		panel_1.add(lblMaritalStatus);

		String[] maritalStatuses = { "Single", "Married", "Divorced", "Widowed" };
		maritalStatusComboBox = new JComboBox<>(maritalStatuses);
		maritalStatusComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		maritalStatusComboBox.setBounds(77, 324, 238, 25);
		panel_1.add(maritalStatusComboBox);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEmail.setBounds(325, 322, 76, 25);
		panel_1.add(lblEmail);

		textField_7 = new JTextField();
		textField_7.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_7.setBounds(394, 324, 247, 25);
		panel_1.add(textField_7);

		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAddress.setBounds(20, 358, 150, 25);
		panel_1.add(lblAddress);

		textField_8 = new JTextField();
		textField_8.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_8.setBounds(77, 360, 238, 25);
		panel_1.add(textField_8);

		JLabel lblProgram = new JLabel("Program");
		lblProgram.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblProgram.setBounds(325, 358, 76, 25);
		panel_1.add(lblProgram);

		String[] programs = { "BS in Information Technology", "BS in Psychology" };
		programComboBox = new JComboBox<>(programs);
		programComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		programComboBox.setBounds(394, 360, 247, 25);
		panel_1.add(programComboBox);

		JLabel lblYear = new JLabel("Year:");
		lblYear.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblYear.setBounds(20, 394, 70, 25);
		panel_1.add(lblYear);

		String[] years = { "1st Year", "2nd Year", "3rd Year", "4th Year" };
		yearComboBox = new JComboBox<>(years);
		yearComboBox.setBounds(77, 396, 238, 25);
		yearComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedYear = (String) yearComboBox.getSelectedItem();
				isIrregular = !"1st Year".equals(selectedYear);
				updateSectionComboBox();
			}
		});
		panel_1.add(yearComboBox);

		JLabel lblSemester = new JLabel("Semester:");
		lblSemester.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSemester.setBounds(325, 394, 70, 25);
		panel_1.add(lblSemester);

		String[] semesters = { "1st Semester", "2nd Semester" };
		semesterComboBox = new JComboBox<>(semesters);
		semesterComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		semesterComboBox.setBounds(394, 396, 247, 25);
		panel_1.add(semesterComboBox);

		JLabel lblSection = new JLabel("Section:");
		lblSection.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSection.setBounds(20, 432, 70, 25);
		panel_1.add(lblSection);

		sectionComboBox = new JComboBox<>();
		sectionComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		sectionComboBox.setBounds(77, 430, 238, 25);
		panel_1.add(sectionComboBox);

		btnNext = new RoundedButton("Next");
		btnNext.setForeground(new Color(255, 198, 0));
		btnNext.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNext.setBackground(new Color(128, 0, 255));
		btnNext.setBounds(637, 669, 72, 23);
		contentPane.add(btnNext);

		RoundedButton btnCancel = new RoundedButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel?", "Warning",
						JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {
					LandingPage homeFrame = new LandingPage();
					homeFrame.setVisible(true);
					StudentInformation.this.dispose();
				}
			}
		});

		btnCancel.setForeground(new Color(255, 198, 0));
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCancel.setBackground(new Color(128, 0, 255));
		btnCancel.setBounds(33, 669, 72, 23);
		contentPane.add(btnCancel);

		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validateFields()) {
					saveStudentInformation();
				} else {
					JOptionPane.showMessageDialog(StudentInformation.this,
							"Please fill out all fields before proceeding.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	private void updateSectionComboBox() {
		sectionComboBox.removeAllItems();
		String selectedYear = (String) yearComboBox.getSelectedItem();
		String yearNumber = selectedYear.substring(0, 1);
		sectionComboBox.addItem(yearNumber + "101");
		sectionComboBox.addItem(yearNumber + "102");
	}

	private void initializeDatabase() throws SQLException {
		connection = db_connection.getConnection();
	}

	private boolean validateFields() {
		return !textField.getText().trim().isEmpty() && !textField_1.getText().trim().isEmpty()
				&& dateChooser.getDate() != null && !textField_3.getText().trim().isEmpty()
				&& !textField_5.getText().trim().isEmpty() && !textField_7.getText().trim().isEmpty()
				&& !textField_8.getText().trim().isEmpty();
	}

	private void saveStudentInformation() {
		try {
			connection.setAutoCommit(false); // Start transaction

			// Check if the section has reached its limit
			String checkSectionSql = "SELECT COUNT(*) FROM student_information WHERE program = ? AND year = ? AND semester = ? AND section = ?";
			try (PreparedStatement checkStmt = connection.prepareStatement(checkSectionSql)) {
				checkStmt.setString(1, (String) programComboBox.getSelectedItem());
				checkStmt.setString(2, (String) yearComboBox.getSelectedItem());
				checkStmt.setString(3, (String) semesterComboBox.getSelectedItem());
				checkStmt.setString(4, (String) sectionComboBox.getSelectedItem());

				try (ResultSet rs = checkStmt.executeQuery()) {
					if (rs.next() && rs.getInt(1) >= 40) {
						JOptionPane.showMessageDialog(this,
								"This section has reached its maximum capacity of 40 students. Please choose another section.",
								"Section Full", JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
			}

			// Check if email already exists
			String checkEmailSql = "SELECT COUNT(*) FROM student_information WHERE email = ?";
			try (PreparedStatement checkEmailStmt = connection.prepareStatement(checkEmailSql)) {
				checkEmailStmt.setString(1, textField_7.getText());
				try (ResultSet rs = checkEmailStmt.executeQuery()) {
					if (rs.next() && rs.getInt(1) > 0) {
						JOptionPane.showMessageDialog(this,
								"This email is already registered. Please use a different email.", "Email Exists",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
			}

			String sql = "INSERT INTO student_information (name, age, birthdate, contact_number, gender, nationality, marital_status, email, address, program, year, semester, section, is_irregular) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, textField.getText());

			try {
				int age = Integer.parseInt(textField_1.getText());
				pstmt.setInt(2, age);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Invalid age format. Please enter a valid number.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				java.sql.Date birthdate = new java.sql.Date(dateChooser.getDate().getTime());
				pstmt.setDate(3, birthdate);
			} catch (NullPointerException e) {
				JOptionPane.showMessageDialog(this, "Please select a birthdate.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			pstmt.setString(4, textField_3.getText());
			pstmt.setString(5, (String) genderComboBox.getSelectedItem());
			pstmt.setString(6, textField_5.getText());
			pstmt.setString(7, (String) maritalStatusComboBox.getSelectedItem());
			pstmt.setString(8, textField_7.getText());
			pstmt.setString(9, textField_8.getText());

			// Map full program names to database values
			Map<String, String> programMap = new HashMap<>();
			programMap.put("BS in Information Technology", "BSIT");
			programMap.put("BS in Psychology", "BSPsych");

			String fullProgramName = (String) programComboBox.getSelectedItem();
			String dbProgramValue = programMap.get(fullProgramName);
			pstmt.setString(10, dbProgramValue);

			Map<String, String> yearMap = new HashMap<>();
			yearMap.put("1st Year", "1");
			yearMap.put("2nd Year", "2");
			yearMap.put("3rd Year", "3");
			yearMap.put("4th Year", "4");

			Map<String, String> semesterMap = new HashMap<>();
			semesterMap.put("1st Semester", "1st");
			semesterMap.put("2nd Semester", "2nd");

			String fullYearName = (String) yearComboBox.getSelectedItem();
			String dbYearValue = yearMap.get(fullYearName);
			pstmt.setString(11, dbYearValue);

			String fullSemesterName = (String) semesterComboBox.getSelectedItem();
			String dbSemesterValue = semesterMap.get(fullSemesterName);
			pstmt.setString(12, dbSemesterValue);

			pstmt.setString(13, (String) sectionComboBox.getSelectedItem());
			pstmt.setBoolean(14, isIrregular);

			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet generatedKeys = pstmt.getGeneratedKeys();
				if (generatedKeys.next()) {
					studentInformationId = generatedKeys.getInt(1);
				}

				if (isIrregular) {
					openSubjectsFrame();
				} else {
					connection.commit(); // Commit transaction
					JOptionPane.showMessageDialog(this, "Student information saved successfully!");
					proceedToNextStep();
				}
			} else {
				connection.rollback(); // Rollback if insert failed
				JOptionPane.showMessageDialog(this, "Failed to save student information.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e) {
			try {
				connection.rollback(); // Rollback on exception
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error saving student information: " + e.getMessage(), "Database Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void openSubjectsFrame() {
		String program = (String) programComboBox.getSelectedItem();
		String year = (String) yearComboBox.getSelectedItem();
		String semester = (String) semesterComboBox.getSelectedItem();
		String section = (String) sectionComboBox.getSelectedItem();
		SubjectsTaken subjectsFrame = new SubjectsTaken(studentInformationId, program, year, semester, section,
				connection);
		subjectsFrame.setVisible(true);
		subjectsFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosed(java.awt.event.WindowEvent windowEvent) {
				if (subjectsFrame.areSubjectsEntered()) {
					try {
						connection.commit(); // Commit transaction if subjects were entered
						JOptionPane.showMessageDialog(StudentInformation.this,
								"Student information and subjects saved successfully!");
						proceedToNextStep();
					} catch (SQLException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(StudentInformation.this, "Error saving data: " + e.getMessage(),
								"Database Error", JOptionPane.ERROR_MESSAGE);
					} finally {
						try {
							connection.setAutoCommit(true); // Reset auto-commit to true
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				} else {
					try {
						connection.rollback(); // Rollback if subjects were not entered
						JOptionPane.showMessageDialog(StudentInformation.this,
								"Student information was not saved. Please try again.", "Warning",
								JOptionPane.WARNING_MESSAGE);
					} catch (SQLException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(StudentInformation.this,
								"Error rolling back transaction: " + e.getMessage(), "Database Error",
								JOptionPane.ERROR_MESSAGE);
					} finally {
						try {
							connection.setAutoCommit(true); // Reset auto-commit to true
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
	}

	private void proceedToNextStep() {
		try {
			DocumentUploads frame = new DocumentUploads(studentInformationId);
			frame.setVisible(true);
			dispose();
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error opening Document Uploads: " + ex.getMessage(), "Database Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void clearForm() {
		textField.setText("");
		textField_1.setText("");
		dateChooser.setDate(null);
		textField_3.setText("");
		genderComboBox.setSelectedIndex(0);
		textField_5.setText("");
		maritalStatusComboBox.setSelectedIndex(0);
		textField_7.setText("");
		textField_8.setText("");
		programComboBox.setSelectedIndex(0);
		yearComboBox.setSelectedIndex(0);
		semesterComboBox.setSelectedIndex(0);
		sectionComboBox.setSelectedIndex(0);
		isIrregular = false;
	}

	@Override
	public void dispose() {
		try {
			if (pstmt != null)
				pstmt.close();
			if (connection != null) {
				connection.setAutoCommit(true); // Ensure auto-commit is set to true before closing
				// connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		super.dispose();
	}
}
