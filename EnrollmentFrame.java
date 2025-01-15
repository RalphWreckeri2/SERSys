package sersystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnrollmentFrame extends JFrame {

	private static final Logger LOGGER = Logger.getLogger(EnrollmentFrame.class.getName());
	private Connection conn;
	private int studentInformationId;
	private String program;
	private String currentSection;
	private int currentYear;
	private String currentSemester;
	private String enrollmentSemester;
	private String enrollmentYear;
	private boolean isIrregular;
	private JTable scheduleTable;
	private static final int MAX_UNITS = 21;

	public EnrollmentFrame(Connection conn, int studentInformationId) {
		this.conn = conn;
		this.studentInformationId = studentInformationId;

		setTitle("Enrollment System");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		try {
			loadStudentInfo();
			initComponents();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error initializing EnrollmentFrame", e);
			JOptionPane.showMessageDialog(this, "Error initializing enrollment system: " + e.getMessage(),
					"Initialization Error", JOptionPane.ERROR_MESSAGE);
			dispose();
		}
	}

	private void loadStudentInfo() throws SQLException {
		String sql = "SELECT program, year, semester, section, is_irregular FROM student_information WHERE student_information_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, studentInformationId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					this.program = rs.getString("program");
					this.currentYear = rs.getInt("year");
					this.currentSemester = rs.getString("semester");
					this.currentSection = rs.getString("section");
					this.isIrregular = rs.getBoolean("is_irregular");

					LOGGER.info("Student Info - Program: " + program + ", Year: " + currentYear + ", Semester: "
							+ currentSemester + ", Section: " + currentSection + ", Is Irregular: " + isIrregular);

					// Determine next enrollment semester and year
					if (currentSemester.equals("1st")) {
						this.enrollmentSemester = "2nd";
						this.enrollmentYear = String.valueOf(currentYear);
					} else {
						this.enrollmentSemester = "1st";
						this.enrollmentYear = String.valueOf(currentYear + 1);
					}

					LOGGER.info("Enrollment Info - Year: " + enrollmentYear + ", Semester: " + enrollmentSemester);
				} else {
					throw new SQLException("Student information not found for ID: " + studentInformationId);
				}
			}
		}
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		headerPanel.add(new JLabel(
				"Enrollment for " + program + " - Year " + enrollmentYear + " - " + enrollmentSemester + " Semester"));
		add(headerPanel, BorderLayout.NORTH);

		if (isIrregular) {
			add(createIrregularEnrollmentPanel(), BorderLayout.CENTER);
		} else {
			add(createRegularEnrollmentPanel(), BorderLayout.CENTER);
		}

		JButton enrollButton = new JButton("Enroll");
		enrollButton.addActionListener(e -> processEnrollment());
		add(enrollButton, BorderLayout.SOUTH);
	}

	private JPanel createIrregularEnrollmentPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		scheduleTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(scheduleTable);
		panel.add(scrollPane, BorderLayout.CENTER);

		JButton compileButton = new JButton("Compile Schedule");
		compileButton.addActionListener(e -> compileIrregularSchedule());
		panel.add(compileButton, BorderLayout.SOUTH);

		return panel;
	}

	private JPanel createRegularEnrollmentPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		scheduleTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(scheduleTable);
		panel.add(scrollPane, BorderLayout.CENTER);

		loadRegularSchedule();

		return panel;
	}

	private void compileIrregularSchedule() {
		try {
			List<Subject> currentSubjects = getAvailableSubjects(currentSemester, String.valueOf(currentYear));
			List<Subject> availableSubjects = getAvailableSubjects(enrollmentSemester, enrollmentYear);
			List<Subject> selectedSubjects = new ArrayList<>(currentSubjects);
			int totalUnits = currentSubjects.stream().mapToInt(Subject::getUnits).sum();

			for (Subject subject : availableSubjects) {
				if (totalUnits + subject.getUnits() <= MAX_UNITS && !hasConflict(selectedSubjects, subject)) {
					selectedSubjects.add(subject);
					totalUnits += subject.getUnits();
				}
			}

			displayCompiledSchedule(selectedSubjects);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error compiling irregular schedule", e);
			JOptionPane.showMessageDialog(this, "Error compiling schedule: " + e.getMessage(), "Database Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void loadRegularSchedule() {
		try {
			List<Subject> subjects = getRegularSubjects(enrollmentSemester, enrollmentYear);
			displayCompiledSchedule(subjects);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error loading regular schedule", e);
			JOptionPane.showMessageDialog(this, "Error loading schedule: " + e.getMessage(), "Database Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private List<Subject> getAvailableSubjects(String semester, String year) throws SQLException {
		List<Subject> subjects = new ArrayList<>();
		String tableName = getTableName(year);
		String sql = "SELECT * FROM " + tableName + " WHERE semester = ? AND subjects_id NOT IN "
				+ "(SELECT subjects_id FROM irregular_student_subjects "
				+ "WHERE student_information_id = ? AND program = ? AND year = ?)";

		LOGGER.info("Fetching available subjects from table: " + tableName);
		LOGGER.info("SQL query: " + sql);

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, semester);
			pstmt.setInt(2, studentInformationId);
			pstmt.setString(3, program);
			pstmt.setString(4, year);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					subjects.add(new Subject(rs.getInt("subjects_id"), rs.getString("subject"), rs.getString("proctor"),
							rs.getString("schedule"), rs.getString("room"), rs.getInt("units"),
							rs.getString("semester"), Integer.parseInt(year)));
				}
			}
		}
		LOGGER.info("Fetched " + subjects.size() + " available subjects");
		return subjects;
	}

	private List<Subject> getRegularSubjects(String semester, String year) throws SQLException {
		List<Subject> subjects = new ArrayList<>();
		String tableName = getTableName(year);
		String sql = "SELECT * FROM " + tableName + " WHERE semester = ?";

		LOGGER.info("Fetching regular subjects from table: " + tableName);
		LOGGER.info("SQL query: " + sql);

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, semester);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					subjects.add(new Subject(rs.getInt("subjects_id"), rs.getString("subject"), rs.getString("proctor"),
							rs.getString("schedule"), rs.getString("room"), rs.getInt("units"),
							rs.getString("semester"), Integer.parseInt(year)));
				}
			}
		}
		LOGGER.info("Fetched " + subjects.size() + " regular subjects");
		return subjects;
	}

	private String getTableName(String year) {
		String programPrefix = program.toLowerCase().replace(" ", "");
		String tableName = programPrefix + "_year" + year + "_" + year + (currentSection.equals("A") ? "101" : "102");
		LOGGER.info("Generated table name: " + tableName);
		return tableName;
	}

	private boolean hasConflict(List<Subject> selectedSubjects, Subject newSubject) {
		for (Subject subject : selectedSubjects) {
			if (scheduleConflict(subject.getSchedule(), newSubject.getSchedule())) {
				return true;
			}
		}
		return false;
	}

	private boolean scheduleConflict(String schedule1, String schedule2) {
		// Implement actual schedule conflict checking logic
		return false;
	}

	private void displayCompiledSchedule(List<Subject> subjects) {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Subject");
		model.addColumn("Proctor");
		model.addColumn("Schedule");
		model.addColumn("Room");
		model.addColumn("Units");
		model.addColumn("Semester");
		model.addColumn("Year");

		for (Subject subject : subjects) {
			model.addRow(new Object[] { subject.getName(), subject.getProctor(), subject.getSchedule(),
					subject.getRoom(), subject.getUnits(), subject.getSemester(), subject.getYear() });
		}

		scheduleTable.setModel(model);
	}

	private void processEnrollment() {
		try {
			conn.setAutoCommit(false);

			if (isIrregular) {
				LOGGER.info("Processing irregular enrollment");
				processIrregularEnrollment();
			} else {
				LOGGER.info("Processing regular enrollment");
				processRegularEnrollment();
			}

			updateStudentInformation();

			conn.commit();
			JOptionPane.showMessageDialog(this, "Enrollment successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
				LOGGER.log(Level.SEVERE, "Error rolling back transaction", ex);
			}
			LOGGER.log(Level.SEVERE, "Error processing enrollment", e);
			JOptionPane.showMessageDialog(this, "Error processing enrollment: " + e.getMessage(), "Database Error",
					JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Error resetting auto-commit", e);
			}
		}
	}

	private void processIrregularEnrollment() throws SQLException {
		// Clear existing irregular subjects for this student
		String clearSql = "DELETE FROM irregular_student_subjects WHERE student_information_id = ?";
		try (PreparedStatement clearStmt = conn.prepareStatement(clearSql)) {
			clearStmt.setInt(1, studentInformationId);
			clearStmt.executeUpdate();
		}

		// Insert new irregular subjects
		DefaultTableModel model = (DefaultTableModel) scheduleTable.getModel();
		for (int i = 0; i < model.getRowCount(); i++) {
			String subjectName = (String) model.getValueAt(i, 0);
			String semester = (String) model.getValueAt(i, 5);
			int year = (int) model.getValueAt(i, 6);

			Subject subject = new Subject(getSubjectId(subjectName, semester, year), subjectName,
					(String) model.getValueAt(i, 1), (String) model.getValueAt(i, 2), (String) model.getValueAt(i, 3),
					(int) model.getValueAt(i, 4), semester, year);
			insertIrregularSubject(subject);
		}
	}

	private void processRegularEnrollment() throws SQLException {
		// For regular students, we don't need to insert anything.
		// Their schedule is predetermined based on their program, year, and section.
		// The student_information table will be updated in updateStudentInformation()
		// method.
	}

	private void updateStudentInformation() throws SQLException {
		String sql = "UPDATE student_information SET year = ?, semester = ? WHERE student_information_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, Integer.parseInt(enrollmentYear));
			pstmt.setString(2, enrollmentSemester);
			pstmt.setInt(3, studentInformationId);
			pstmt.executeUpdate();
		}
	}

	private void insertIrregularSubject(Subject subject) throws SQLException {
		String sql = "INSERT INTO irregular_student_subjects (student_information_id, program, year, subjects_id, subject) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, studentInformationId);
			pstmt.setString(2, program);
			pstmt.setInt(3, subject.getYear());
			pstmt.setInt(4, subject.getId());
			pstmt.setString(5, subject.getName());
			pstmt.executeUpdate();
		}
	}

	private int getSubjectId(String subjectName, String semester, int year) throws SQLException {
		String tableName = getTableName(String.valueOf(year));
		String sql = "SELECT subjects_id FROM " + tableName + " WHERE subject = ? AND semester = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, subjectName);
			pstmt.setString(2, semester);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("subjects_id");
				}
			}
		}
		throw new SQLException("Subject not found: " + subjectName);
	}

	private static class Subject {
		private int id;
		private String name;
		private String proctor;
		private String schedule;
		private String room;
		private int units;
		private String semester;
		private int year;

		public Subject(int id, String name, String proctor, String schedule, String room, int units, String semester,
				int year) {
			this.id = id;
			this.name = name;
			this.proctor = proctor;
			this.schedule = schedule;
			this.room = room;
			this.units = units;
			this.semester = semester;
			this.year = year;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String getProctor() {
			return proctor;
		}

		public String getSchedule() {
			return schedule;
		}

		public String getRoom() {
			return room;
		}

		public int getUnits() {
			return units;
		}

		public String getSemester() {
			return semester;
		}

		public int getYear() {
			return year;
		}
	}
}