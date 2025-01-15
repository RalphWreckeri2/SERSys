package sersystem;

import sersystem.Subject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.List;
import java.util.Set;

public class StudentPortal extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(StudentPortal.class.getName());
	private int currentStudentInfoId;
	private static final int MAX_UNITS = 21;
	// Constants
	private static final String DB_ERROR = "Database Error";
	private static final String PAYMENT_SUCCESS = "Payment recorded successfully!";
	private static final String PAYMENT_FAILURE = "Failed to record payment. Please try again.";
	private static final Color PRIMARY_COLOR = new Color(128, 0, 255);
	private static final Color SECONDARY_COLOR = new Color(255, 198, 0);

	private String currentProgram;
	private int currentYear;
	private String currentSemester;

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private Connection conn;
	private JLabel balanceLabel;
	private JTextField paymentAmountField;
	private RoundedButton paymentButton;
	private BigDecimal totalAmount;
	private BigDecimal paidAmount;
	private BigDecimal remainingBalance;
	private JLabel qrCodeLabel;
	private JLabel totalAmountLabel;
	private JLabel remainingBalanceLabel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentPortal frame = new StudentPortal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private Connection getConnection() throws SQLException {
		return db_connection.getConnection();
	}

	public StudentPortal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 657, 468);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		initializeComponents();
	}

	private void initializeComponents() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(PRIMARY_COLOR);
		panel.setBounds(0, 0, 651, 80);
		setLocationRelativeTo(null);
		contentPane.add(panel);

		JLabel lblNewLabel = new JLabel("Student Portal");
		lblNewLabel.setForeground(SECONDARY_COLOR);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(435, 16, 189, 54);
		panel.add(lblNewLabel);

		JLabel lblDreams = new JLabel("DREAMS");
		lblDreams.setForeground(new Color(250, 194, 5));
		lblDreams.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDreams.setBounds(91, 16, 70, 43);
		panel.add(lblDreams);

		JLabel lblUniversity = new JLabel("UNIVERSITY");
		lblUniversity.setForeground(Color.WHITE);
		lblUniversity.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblUniversity.setBounds(91, 28, 70, 43);
		panel.add(lblUniversity);

		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(10, -3, 87, 84);
		panel.add(lblNewLabel_1);

		ImageIcon originalIcon = new ImageIcon("C:\\Users\\Administrator\\Documents\\resources\\purplelogo.png");
		Image img = originalIcon.getImage();
		Image scaledImg = img.getScaledInstance(lblNewLabel_1.getWidth(), lblNewLabel_1.getHeight(),
				Image.SCALE_SMOOTH);

		lblNewLabel_1.setIcon(new ImageIcon(scaledImg));

		RoundedPanel panel_1 = new RoundedPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(null);
		panel_1.setBackground(new Color(245, 245, 245));
		panel_1.setBounds(105, 128, 437, 234);
		contentPane.add(panel_1);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBackground(Color.WHITE);
		textField.setBounds(48, 70, 337, 20);
		panel_1.add(textField);

		JLabel lblNewLabel_2 = new JLabel("Enter Student Number");
		lblNewLabel_2.setBounds(48, 56, 190, 14);
		panel_1.add(lblNewLabel_2);

		JLabel lblNewLabel_2_1 = new JLabel("Enter Password");
		lblNewLabel_2_1.setBounds(48, 101, 190, 14);
		panel_1.add(lblNewLabel_2_1);

		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBackground(Color.WHITE);
		passwordField.setBounds(48, 115, 337, 20);
		panel_1.add(passwordField);

		RoundedButton btnNewButton = new RoundedButton("Log In");
		btnNewButton.addActionListener(e -> {
			String studentNumber = textField.getText().trim();
			String password = new String(passwordField.getPassword()).trim();

			if (studentNumber.isEmpty() || password.isEmpty()) {
				JOptionPane.showMessageDialog(StudentPortal.this, "Please fill in all fields!", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				validateLogin(studentNumber, password);
			}
		});

		btnNewButton.setForeground(SECONDARY_COLOR);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBackground(PRIMARY_COLOR);
		btnNewButton.setBounds(48, 156, 72, 23);
		panel_1.add(btnNewButton);

		RoundedButton btnForgotPassword = new RoundedButton("Forgot Password");
		btnForgotPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ForgotPassword fpFrame = new ForgotPassword("StudentPortal");
				fpFrame.setVisible(true);
				StudentPortal.this.setVisible(false);
			}
		});

		btnForgotPassword.setForeground(SECONDARY_COLOR);
		btnForgotPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnForgotPassword.setBackground(PRIMARY_COLOR);
		btnForgotPassword.setBounds(130, 156, 138, 23);
		panel_1.add(btnForgotPassword);

		RoundedButton btnContactUs = new RoundedButton("Contact Us");
		btnContactUs.setForeground(SECONDARY_COLOR);
		btnContactUs.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnContactUs.setBackground(PRIMARY_COLOR);
		btnContactUs.setBounds(278, 156, 107, 23);
		panel_1.add(btnContactUs);

		RoundedButton rndbtnBack = new RoundedButton("Contact Us");
		rndbtnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LandingPage homeFrame = new LandingPage();
				homeFrame.setVisible(true);
				StudentPortal.this.setVisible(false);
			}
		});

		rndbtnBack.setText("Back");
		rndbtnBack.setForeground(new Color(255, 198, 0));
		rndbtnBack.setFont(new Font("Tahoma", Font.BOLD, 11));
		rndbtnBack.setBackground(new Color(128, 0, 255));
		rndbtnBack.setBounds(470, 373, 72, 23);
		contentPane.add(rndbtnBack);
	}

	private void validateLogin(String studentNumber, String password) {
		try (Connection conn = getConnection();
				PreparedStatement checkPasswordStmt = conn.prepareStatement("SELECT si.* FROM enrolled_student es "
						+ "JOIN student_information si ON es.student_information_id = si.student_information_id "
						+ "WHERE es.student_number = ? AND es.student_password = ?")) {

			checkPasswordStmt.setString(1, studentNumber);
			checkPasswordStmt.setString(2, password);

			try (ResultSet rs = checkPasswordStmt.executeQuery()) {
				if (rs.next()) {
					JOptionPane.showMessageDialog(StudentPortal.this, "Log In Successful!");
					displayStudentInfo(rs, studentNumber);
					this.dispose();
				} else {
					JOptionPane.showMessageDialog(StudentPortal.this, "Invalid credentials!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error validating login", e);
			JOptionPane.showMessageDialog(StudentPortal.this, "Database error occurred. Please try again.", DB_ERROR,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean isFinalSemester(int year, String semester) {
		return year == 4 && semester.equalsIgnoreCase("2nd");
	}

	private void displayStudentInfo(ResultSet rs, String studentNumber) throws SQLException {
		JFrame studentInfoFrame = new JFrame("Student Information");
		studentInfoFrame.setSize(800, 600);
		studentInfoFrame.setLocationRelativeTo(null);
		studentInfoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(new Color(245, 245, 245));

		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(null);
		headerPanel.setBackground(PRIMARY_COLOR);
		headerPanel.setPreferredSize(new Dimension(800, 80));

		JLabel headerLabel = new JLabel("Student Information");
		headerLabel.setForeground(SECONDARY_COLOR);
		headerLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		headerLabel.setBounds(20, 20, 300, 40);
		headerPanel.add(headerLabel);

		mainPanel.add(headerPanel, BorderLayout.NORTH);

		JPanel contentPanel = new JPanel(new BorderLayout());
		contentPanel.setBackground(new Color(245, 245, 245));
		contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

		JPanel infoPanel = new JPanel(new GridLayout(5, 2, 10, 10));
		infoPanel.setBackground(Color.WHITE);
		infoPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

		addLabelAndValue(infoPanel, "Name:", rs.getString("name"));
		addLabelAndValue(infoPanel, "Program:", rs.getString("program"));
		addLabelAndValue(infoPanel, "Year:", rs.getString("year"));
		addLabelAndValue(infoPanel, "Semester:", rs.getString("semester"));
		String correctSection = rs.getInt("year") + "101";
		addLabelAndValue(infoPanel, "Section:", correctSection);

		contentPanel.add(infoPanel, BorderLayout.NORTH);

		String program = rs.getString("program");
		int year = rs.getInt("year");
		String semester = rs.getString("semester");
		String section = rs.getString("section");
		boolean isIrregular = rs.getBoolean("is_irregular");
		int studentInformationId = rs.getInt("student_information_id");

		JTable scheduleTable;
		if (isIrregular) {
			scheduleTable = createIrregularScheduleTable(getConnection(), studentInformationId, program, year,
					semester);
		} else {
			scheduleTable = createScheduleTable(getConnection(), program, year, semester, section);
		}

		JScrollPane scrollPane = new JScrollPane(scheduleTable);
		contentPanel.add(scrollPane, BorderLayout.CENTER);

		mainPanel.add(contentPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
		buttonPanel.setBackground(new Color(245, 245, 245));

		currentStudentInfoId = rs.getInt("student_information_id");

		RoundedButton enrollButton = new RoundedButton("Enroll");
		enrollButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (isFinalSemester(year, semester)) {
						JOptionPane.showMessageDialog(StudentPortal.this,
								"You are in your final semester. Enrollment is not required.", "Enrollment Blocked",
								JOptionPane.INFORMATION_MESSAGE);
					} else if (isEnrollmentActive()) {
						EnrollmentFrame enrollmentFrame = new EnrollmentFrame(getConnection(), currentStudentInfoId);
						enrollmentFrame.setVisible(true);
						enrollmentFrame.addWindowListener(new WindowAdapter() {
							@Override
							public void windowClosed(WindowEvent e) {
								try {
									try (Connection conn = getConnection();
											PreparedStatement stmt = conn.prepareStatement(
													"SELECT * FROM student_information WHERE student_information_id = ?")) {
										stmt.setInt(1, currentStudentInfoId);
										try (ResultSet rs = stmt.executeQuery()) {
											if (rs.next()) {
												studentInfoFrame.dispose();
												displayStudentInfo(rs, textField.getText());
											}
										}
									}
								} catch (SQLException ex) {
									LOGGER.log(Level.SEVERE, "Error refreshing student info", ex);
								}
							}
						});
					} else {
						JOptionPane.showMessageDialog(StudentPortal.this,
								"Enrollment is currently not available. Please try again later.", "Enrollment Closed",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(StudentPortal.this,
							"Error checking enrollment status. Please try again later.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		RoundedButton logoutButton = new RoundedButton("Logout");
		logoutButton.addActionListener(e -> {
			studentInfoFrame.dispose();
			new StudentPortal().setVisible(true);
		});

		for (RoundedButton button : new RoundedButton[] { enrollButton, logoutButton }) {
			button.setForeground(SECONDARY_COLOR);
			button.setFont(new Font("Tahoma", Font.BOLD, 11));
			button.setBackground(PRIMARY_COLOR);
			buttonPanel.add(button);
		}

		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		studentInfoFrame.getContentPane().add(mainPanel);
		studentInfoFrame.setVisible(true);

		studentInfoFrame.pack();
		studentInfoFrame.setVisible(true);
	}

	private boolean isEnrollmentActive() throws SQLException {
		String sql = "SELECT enrollment_active FROM system_settings LIMIT 1";
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			if (rs.next()) {
				return rs.getBoolean("enrollment_active");
			}
		}
		return false; // Default to false if no setting is found
	}

	private JTable createIrregularScheduleTable(Connection conn, int studentInformationId, String program, int year,
			String semester) throws SQLException {
		List<Subject> subjects = getIrregularStudentSubjects(conn, studentInformationId);

		String[] columnNames = { "Subject ID", "Subject", "Proctor", "Schedule", "Room", "Units" };
		Object[][] data = new Object[subjects.size()][6];

		for (int i = 0; i < subjects.size(); i++) {
			Subject subject = subjects.get(i);
			data[i] = new Object[] { subject.getId(), subject.getName(), subject.getProctor(), subject.getSchedule(),
					subject.getRoom(), subject.getUnits() };
		}

		return new JTable(data, columnNames);
	}

	private List<Subject> getIrregularStudentSubjects(Connection conn, int studentInformationId) throws SQLException {
		List<Subject> subjects = new ArrayList<>();

		// First, get the student's current program, year, and semester
		String studentInfoSql = "SELECT program, year, semester FROM student_information WHERE student_information_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(studentInfoSql)) {
			pstmt.setInt(1, studentInformationId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					this.currentProgram = rs.getString("program");
					this.currentYear = rs.getInt("year");
					this.currentSemester = rs.getString("semester");
				} else {
					throw new SQLException("Student information not found");
				}
			}
		}

		// Now, get all subjects for the current program and semester, including higher
		// years
		String sql = "SELECT s.subjects_id, s.subject, s.proctor, s.semester, s.schedule, s.room, s.units " + "FROM ("
				+ "    SELECT * FROM " + this.currentProgram.toLowerCase() + "_year1_1101 WHERE semester = ? "
				+ "    UNION ALL " + "    SELECT * FROM " + this.currentProgram.toLowerCase()
				+ "_year2_2101 WHERE semester = ? " + "    UNION ALL " + "    SELECT * FROM "
				+ this.currentProgram.toLowerCase() + "_year3_3101 WHERE semester = ? " + "    UNION ALL "
				+ "    SELECT * FROM " + this.currentProgram.toLowerCase() + "_year4_4101 WHERE semester = ? " + ") s "
				+ "WHERE s.subjects_id NOT IN ("
				+ "    SELECT subjects_id FROM irregular_student_subjects WHERE student_information_id = ?" + ") "
				+ "ORDER BY s.subjects_id";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, this.currentSemester);
			pstmt.setString(2, this.currentSemester);
			pstmt.setString(3, this.currentSemester);
			pstmt.setString(4, this.currentSemester);
			pstmt.setInt(5, studentInformationId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Subject subject = new Subject(rs.getInt("subjects_id"), rs.getString("subject"),
							rs.getString("proctor"), rs.getString("schedule"), rs.getString("room"), rs.getInt("units"),
							rs.getString("semester"), this.currentYear);
					subjects.add(subject);
				}
			}
		}

		return filterSubjects(subjects);
	}

	private List<Subject> filterSubjects(List<Subject> allSubjects) {
		List<Subject> filteredSubjects = new ArrayList<>();
		int totalUnits = 0;
		Set<String> schedules = new HashSet<String>();

		for (Subject subject : allSubjects) {
			if (totalUnits + subject.getUnits() > MAX_UNITS) {
				continue;
			}

			String schedule = subject.getSchedule();
			boolean hasConflict = false;
			if (schedule != null && !schedule.trim().isEmpty()) {
				String[] subjectSchedules = schedule.split(",");
				for (String subSchedule : subjectSchedules) {
					if (schedules.contains(subSchedule.trim())) {
						hasConflict = true;
						break;
					}
				}
			}

			if (!hasConflict) {
				filteredSubjects.add(subject);
				totalUnits += subject.getUnits();
				schedules.addAll(Arrays.asList(schedule.split(",")));
			}
		}

		return filteredSubjects;
	}

	private void addLabelAndValue(JPanel panel, String labelText, String value) {
		JLabel label = new JLabel(labelText);
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setForeground(PRIMARY_COLOR);
		panel.add(label);

		JLabel valueLabel = new JLabel(value);
		valueLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(valueLabel);
	}

	private JTable createScheduleTable(Connection conn, String program, int year, String semester, String section)
			throws SQLException {
		String programAbbreviation = getProgramAbbreviation(program);
		String tableName = String.format("%s_year%d_%d%s", programAbbreviation.toLowerCase(), year, year, section);

		// Remove the extra '1' from the section part if it exists
		tableName = tableName.replaceAll("(\\d+)1(\\d+)$", "$1$2");

		LOGGER.info("Attempting to access table: " + tableName);

		String sql = "SELECT * FROM " + tableName + " WHERE semester = ?";
		LOGGER.info("SQL query: " + sql);

		List<String> columnNames = new ArrayList<>();
		List<Object[]> data = new ArrayList<>();

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, semester);
			try (ResultSet rs = pstmt.executeQuery()) {
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();

				for (int i = 1; i <= columnCount; i++) {
					columnNames.add(metaData.getColumnName(i));
				}

				while (rs.next()) {
					Object[] row = new Object[columnCount];
					for (int i = 1; i <= columnCount; i++) {
						row[i - 1] = rs.getObject(i);
					}
					data.add(row);
				}
			}
		}

		DefaultTableModel model = new DefaultTableModel(data.toArray(new Object[0][]), columnNames.toArray());
		return new JTable(model);
	}

	private String getProgramAbbreviation(String program) {
		switch (program) {
		case "BSIT":
			return "bsit";
		case "BSPsych":
			return "bspsych";
		default:
			LOGGER.warning("Unknown program: " + program);
			return "unknown";
		}
	}
}