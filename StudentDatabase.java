package sersystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDatabase {
	private Connection connection;

	public StudentDatabase(Connection connection) {
		this.connection = connection;
	}

	public List<StudentInfo> getStudents(String program, String year, String status, boolean isIrregular) {
		List<StudentInfo> students = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT si.*, s.application_status " + "FROM student_information si "
				+ "LEFT JOIN student s ON si.student_information_id = s.student_information_id "
				+ "WHERE si.student_information_id NOT IN (SELECT student_information_id FROM archived_student)");

		if (!"All".equals(program)) {
			sql.append(" AND si.program = ?");
		}
		if (!"All".equals(year)) {
			sql.append(" AND si.year = ?");
		}
		if (!"All".equals(status)) {
			sql.append(" AND s.application_status = ?");
		}
		if (isIrregular) {
			sql.append(" AND si.is_irregular = true");
		}

		try (PreparedStatement pstmt = connection.prepareStatement(sql.toString())) {
			int paramIndex = 1;
			if (!"All".equals(program)) {
				pstmt.setString(paramIndex++, program);
			}
			if (!"All".equals(year)) {
				pstmt.setString(paramIndex++, year);
			}
			if (!"All".equals(status)) {
				pstmt.setString(paramIndex, status);
			}

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					StudentInfo student = new StudentInfo();
					student.id = rs.getInt("student_information_id");
					student.name = rs.getString("name");
					student.program = rs.getString("program");
					student.year = rs.getString("year");
					student.semester = rs.getString("semester");
					student.isIrregular = rs.getBoolean("is_irregular");
					student.email = rs.getString("email");
					student.status = rs.getString("application_status");
					students.add(student);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}

	public void updateStudentStatus(int studentInformationId, String newStatus) throws SQLException {
		String sql = "UPDATE student SET application_status = ? WHERE student_information_id = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, newStatus);
			pstmt.setInt(2, studentInformationId);
			pstmt.executeUpdate();
		}
	}

	public void archiveStudent(int studentInformationId) throws SQLException {
		String sql = "INSERT INTO archived_student (student_information_id, archived_date) VALUES (?, CURRENT_DATE)";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setInt(1, studentInformationId);
			pstmt.executeUpdate();
		}
	}

	public void sendApplicantCredentials(int studentInformationId, String applicationNumber, String password)
			throws SQLException {
		String sql = "UPDATE student SET application_number = ?, student_password = ? WHERE student_information_id = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, applicationNumber);
			pstmt.setString(2, password);
			pstmt.setInt(3, studentInformationId);
			pstmt.executeUpdate();
		}
	}

	public void sendStudentCredentials(int studentInformationId, String studentNumber, String password)
			throws SQLException {
		String sql = "INSERT INTO enrolled_student (student_number, student_password, student_information_id) VALUES (?, ?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, studentNumber);
			pstmt.setString(2, password);
			pstmt.setInt(3, studentInformationId);
			pstmt.executeUpdate();
		}
	}

	public void storeApplicantCredentials(int studentInformationId, String applicationNumber, String password,
			String email) throws SQLException {
		String insertSql = "INSERT INTO student (student_information_id, application_number, student_password, application_status, admin_id, student_email) VALUES (?, ?, ?, 'Pending', 2, ?)";
		try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
			insertStmt.setInt(1, studentInformationId);
			insertStmt.setString(2, applicationNumber);
			insertStmt.setString(3, password);
			insertStmt.setString(4, email);
			int affectedRows = insertStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Inserting student credentials failed, no rows affected.");
			}
		}
	}

	public void storeStudentCredentials(int studentInformationId, String studentNumber, String password)
			throws SQLException {
		// First, update the student table
		String updateStudentSql = "UPDATE student SET application_status = 'Enrolled' WHERE student_information_id = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(updateStudentSql)) {
			pstmt.setInt(1, studentInformationId);
			pstmt.executeUpdate();
		}

		// Fetch the email from student_information table
		String email = "";
		String getEmailSql = "SELECT email FROM student_information WHERE student_information_id = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(getEmailSql)) {
			pstmt.setInt(1, studentInformationId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					email = rs.getString("email");
				} else {
					throw new SQLException("No student found with ID: " + studentInformationId);
				}
			}
		}

		// Then, insert into enrolled_student table
		String insertEnrolledStudentSql = "INSERT INTO enrolled_student (student_number, student_password, admin_id, student_email, student_information_id) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(insertEnrolledStudentSql)) {
			pstmt.setString(1, studentNumber);
			pstmt.setString(2, password);
			pstmt.setInt(3, 2); // Set admin_id to 2
			pstmt.setString(4, email);
			pstmt.setInt(5, studentInformationId);
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Inserting enrolled student failed, no rows affected.");
			}
		}
	}

	public List<StudentInfo> getArchivedStudents() throws SQLException {
		List<StudentInfo> archivedStudents = new ArrayList<>();
		String sql = "SELECT si.student_information_id, si.name, si.program, si.year, si.email, ars.archived_date, s.application_number "
				+ "FROM student_information si "
				+ "JOIN archived_student ars ON si.student_information_id = ars.student_information_id "
				+ "LEFT JOIN student s ON si.student_information_id = s.student_information_id";

		try (PreparedStatement pstmt = connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				StudentInfo student = new StudentInfo();
				student.id = rs.getInt("student_information_id");
				student.name = rs.getString("name");
				student.program = rs.getString("program");
				student.year = rs.getString("year");
				student.email = rs.getString("email");
				student.archivedDate = rs.getDate("archived_date");
				student.applicationNumber = rs.getString("application_number");
				archivedStudents.add(student);
			}
		}
		return archivedStudents;
	}

	public String getStudentEmail(int studentInformationId) throws SQLException {
		String sql = "SELECT email FROM student_information WHERE student_information_id = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setInt(1, studentInformationId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getString("email");
				} else {
					throw new SQLException("No student found with ID: " + studentInformationId);
				}
			}
		}
	}

	public static class StudentInfo {
		public int id;
		public String name;
		public String program;
		public String year;
		public String semester;
		public boolean isIrregular;
		public String email;
		public String status;
		public Date archivedDate;
		public String applicationNumber;
	}
}
