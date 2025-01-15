package sersystem;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.text.SimpleDateFormat;

public class AdminDB extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField searchField;
	private JTable table;
	private Connection connection;
	private StudentDatabase studentDatabase;
	private JComboBox<String> programComboBox;
	private JComboBox<String> yearComboBox;
	private JComboBox<String> statusComboBox;
	private JCheckBox irregularCheckBox;
	private JLabel studentCountLabel;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				AdminDB frame = new AdminDB();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public AdminDB() throws SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel headerPanel = createHeaderPanel();
		contentPane.add(headerPanel, BorderLayout.NORTH);

		JPanel mainPanel = createMainPanel();
		contentPane.add(mainPanel, BorderLayout.CENTER);

		initializeDatabase();
		studentDatabase = new StudentDatabase(connection);
		loadStudents();
	}

	private JPanel createHeaderPanel() {
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(new Color(128, 0, 255));
		headerPanel.setPreferredSize(new Dimension(1000, 100));
		headerPanel.setLayout(new BorderLayout());

		JLabel titleLabel = new JLabel("Admin Dashboard");
		titleLabel.setForeground(new Color(255, 198, 0));
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headerPanel.add(titleLabel, BorderLayout.CENTER);

		JPanel logoPanel = new JPanel();
		logoPanel.setOpaque(false);
		logoPanel.setPreferredSize(new Dimension(200, 100));
		logoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel logoLabel = new JLabel();
		ImageIcon logoIcon = new ImageIcon("C:\\Users\\Administrator\\Documents\\resources\\purplelogo.png");
		Image scaledImage = logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		logoLabel.setIcon(new ImageIcon(scaledImage));
		logoPanel.add(logoLabel);

		JLabel universityLabel = new JLabel("<html>DREAMS<br>UNIVERSITY</html>");
		universityLabel.setForeground(Color.WHITE);
		universityLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		logoPanel.add(universityLabel);

		headerPanel.add(logoPanel, BorderLayout.WEST);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setOpaque(false);

		RoundedButton logoutButton = createLogoutButton();
		buttonPanel.add(logoutButton);

		RoundedButton enrollmentButton = createEnrollmentActivationButton();
		buttonPanel.add(enrollmentButton);

		headerPanel.add(buttonPanel, BorderLayout.EAST);

		return headerPanel;
	}

	private RoundedButton createLogoutButton() {
		RoundedButton logoutButton = new RoundedButton("Log Out");
		logoutButton.setForeground(new Color(128, 0, 255));
		logoutButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		logoutButton.setBackground(new Color(248, 175, 7));
		logoutButton.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Confirm Logout",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				this.dispose();
				new LandingPage().setVisible(true);
			}
		});
		return logoutButton;
	}

	private RoundedButton createEnrollmentActivationButton() {
		RoundedButton enrollmentButton = new RoundedButton("Toggle Enrollment");
		enrollmentButton.setForeground(new Color(128, 0, 255));
		enrollmentButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		enrollmentButton.setBackground(new Color(248, 175, 7));
		enrollmentButton.addActionListener(e -> toggleEnrollmentActivation());
		return enrollmentButton;
	}

	private void toggleEnrollmentActivation() {
		try {
			String sql = "UPDATE system_settings SET enrollment_active = NOT enrollment_active";
			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
				int affectedRows = pstmt.executeUpdate();
				if (affectedRows > 0) {
					boolean isActive = isEnrollmentActive();
					JOptionPane.showMessageDialog(this, "Enrollment is now " + (isActive ? "activated" : "deactivated"),
							"Enrollment Status Changed", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error toggling enrollment activation: " + ex.getMessage(),
					"Database Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean isEnrollmentActive() throws SQLException {
		String sql = "SELECT enrollment_active FROM system_settings LIMIT 1";
		try (PreparedStatement pstmt = connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			if (rs.next()) {
				return rs.getBoolean("enrollment_active");
			}
		}
		return false; // Default to false if no setting is found
	}

	private JPanel createMainPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel filterPanel = createFilterPanel();
		mainPanel.add(filterPanel, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane();
		mainPanel.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Name", "Program", "Year",
				"Semester", "Irregular", "Email", "Status", "Actions" }) {
			private static final long serialVersionUID = 1L;
			Class[] types = new Class[] { java.lang.Integer.class, java.lang.String.class, java.lang.String.class,
					java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
					java.lang.String.class, java.lang.Object.class };
			boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false, false, true };

			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		scrollPane.setViewportView(table);

		table.getColumnModel().getColumn(8).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(8).setCellEditor(new ButtonEditor(new JCheckBox()));

		RoundedButton archivedFormsButton = new RoundedButton("View Archived Forms");
		archivedFormsButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		archivedFormsButton.setForeground(new Color(245, 192, 10));
		archivedFormsButton.setBackground(new Color(128, 0, 255));
		archivedFormsButton.addActionListener(e -> viewArchivedForms());
		mainPanel.add(archivedFormsButton, BorderLayout.SOUTH);

		return mainPanel;
	}

	private JPanel createFilterPanel() {
		JPanel filterPanel = new JPanel();
		filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		programComboBox = new JComboBox<>(new String[] { "All", "BSIT", "BSPsych" });
		yearComboBox = new JComboBox<>(new String[] { "All", "1", "2", "3", "4" });
		statusComboBox = new JComboBox<>(
				new String[] { "All", "Pending", "Checking", "Accepted", "Declined", "Enrolled" });
		irregularCheckBox = new JCheckBox("Irregular");
		searchField = new JTextField(20);
		RoundedButton searchButton = new RoundedButton("Search");
		searchButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		searchButton.setForeground(new Color(245, 192, 10));
		searchButton.setBackground(new Color(128, 0, 255));

		filterPanel.add(new JLabel("Program:"));
		filterPanel.add(programComboBox);
		filterPanel.add(new JLabel("Year:"));
		filterPanel.add(yearComboBox);
		filterPanel.add(new JLabel("Status:"));
		filterPanel.add(statusComboBox);
		filterPanel.add(irregularCheckBox);
		filterPanel.add(searchField);
		filterPanel.add(searchButton);

		studentCountLabel = new JLabel("Total Students: 0");
		studentCountLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		filterPanel.add(studentCountLabel);

		programComboBox.addActionListener(e -> loadStudents());
		yearComboBox.addActionListener(e -> loadStudents());
		statusComboBox.addActionListener(e -> loadStudents());
		irregularCheckBox.addActionListener(e -> loadStudents());
		searchButton.addActionListener(e -> searchStudents());

		return filterPanel;
	}

	private void initializeDatabase() throws SQLException {
		connection = db_connection.getConnection();
		System.out.println("Database connected successfully");
	}

	private void loadStudents() {
		String program = (String) programComboBox.getSelectedItem();
		String year = (String) yearComboBox.getSelectedItem();
		String status = (String) statusComboBox.getSelectedItem();
		boolean isIrregular = irregularCheckBox.isSelected();

		List<StudentDatabase.StudentInfo> students = studentDatabase.getStudents(program, year, status, isIrregular);
		updateTable(students);
		updateStudentCount(students.size());
	}

	private void searchStudents() {
		String searchTerm = searchField.getText().trim().toLowerCase();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
		table.setRowSorter(sorter);

		if (searchTerm.length() == 0) {
			sorter.setRowFilter(null);
		} else {
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchTerm));
		}

		updateStudentCount(table.getRowCount());
	}

	private void updateTable(List<StudentDatabase.StudentInfo> students) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		for (StudentDatabase.StudentInfo student : students) {
			model.addRow(new Object[] { student.id, student.name, student.program, student.year, student.semester,
					student.isIrregular ? "Yes" : "No", student.email, student.status, "Actions" });
		}
	}

	private void updateStudentCount(int count) {
		studentCountLabel.setText("Total Students: " + count);
	}

	class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setText("Options");
			return this;
		}
	}

	class ButtonEditor extends DefaultCellEditor {
		protected JButton button;
		private String label;
		private boolean isPushed;
		private int row, col;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(e -> fireEditingStopped());
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			this.row = row;
			this.col = column;
			label = "Options";
			button.setText(label);
			isPushed = true;
			return button;
		}

		@Override
		public Object getCellEditorValue() {
			if (isPushed) {
				showOptionsMenu(button, row);
			}
			isPushed = false;
			return label;
		}

		@Override
		public boolean stopCellEditing() {
			isPushed = false;
			return super.stopCellEditing();
		}
	}

	private void showOptionsMenu(JButton source, int row) {
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem updateStatusMenuItem = new JMenuItem("Update Status");
		JMenuItem archiveApplicantMenuItem = new JMenuItem("Archive Applicant");
		JMenuItem viewDetailsMenuItem = new JMenuItem("View Details");
		JMenuItem sendCredentialsMenuItem = new JMenuItem("Send Credentials");

		updateStatusMenuItem.addActionListener(e -> updateApplicantStatus(row));
		archiveApplicantMenuItem.addActionListener(e -> archiveApplicant(row));
		viewDetailsMenuItem.addActionListener(e -> viewApplicantDetails(row));
		sendCredentialsMenuItem.addActionListener(e -> showSendCredentialsOptions(row));

		popupMenu.add(updateStatusMenuItem);
		popupMenu.add(archiveApplicantMenuItem);
		popupMenu.add(viewDetailsMenuItem);
		popupMenu.add(sendCredentialsMenuItem);

		popupMenu.show(source, 0, source.getHeight());
	}

	private void updateApplicantStatus(int row) {
		String studentInformationIdString = table.getValueAt(row, 0).toString();
		String[] statuses = { "Pending", "Checking", "Accepted", "Declined", "Enrolled" };
		String currentStatus = (String) table.getValueAt(row, 7); // Assuming status is in the 8th column (index 7)
		String newStatus = (String) JOptionPane.showInputDialog(this,
				"Select new status for Student ID " + studentInformationIdString + ":", "Update Status",
				JOptionPane.QUESTION_MESSAGE, null, statuses, currentStatus);

		if (newStatus != null && !newStatus.equals(currentStatus)) {
			try {
				int studentInformationId = Integer.parseInt(studentInformationIdString);
				studentDatabase.updateStudentStatus(studentInformationId, newStatus);
				JOptionPane.showMessageDialog(this, "Status updated successfully.", "Success",
						JOptionPane.INFORMATION_MESSAGE);
				loadStudents();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this,
						"Invalid student information ID format: " + studentInformationIdString, "Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error updating status: " + e.getMessage(), "Database Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void archiveApplicant(int row) {
		String studentInformationIdString = table.getValueAt(row, 0).toString();
		int confirm = JOptionPane.showConfirmDialog(this,
				"Are you sure you want to archive Student ID " + studentInformationIdString + "?", "Confirm Archiving",
				JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			try {
				int studentInformationId = Integer.parseInt(studentInformationIdString);
				studentDatabase.archiveStudent(studentInformationId);
				JOptionPane.showMessageDialog(this, "Applicant archived successfully.", "Success",
						JOptionPane.INFORMATION_MESSAGE);
				loadStudents();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this,
						"Invalid student information ID format: " + studentInformationIdString, "Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error archiving applicant: " + e.getMessage(), "Database Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void viewApplicantDetails(int row) {
		String studentInformationIdString = table.getValueAt(row, 0).toString();
		try {
			int studentInformationId = Integer.parseInt(studentInformationIdString);
			SwingUtilities.invokeLater(() -> {
				StudentDetailsView detailsView = new StudentDetailsView(connection, studentInformationId);
				detailsView.setVisible(true);
			});
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Invalid student information ID format: " + studentInformationIdString,
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void showSendCredentialsOptions(int row) {
		String[] options = { "Send Applicant Credentials", "Send Student Credentials" };
		int choice = JOptionPane.showOptionDialog(this, "Choose credential type to send:", "Send Credentials",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		if (choice == 0) {
			sendApplicantCredentials(row);
		} else if (choice == 1) {
			sendStudentCredentials(row);
		}
	}

	private void sendApplicantCredentials(int row) {
		String studentInformationIdString = table.getValueAt(row, 0).toString();
		String email = (String) table.getValueAt(row, 6);

		int confirm = JOptionPane.showConfirmDialog(this,
				"Are you sure you want to send applicant credentials to " + email + "?", "Confirm Send Credentials",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (confirm == JOptionPane.YES_OPTION) {
			try {
				int studentInformationId = Integer.parseInt(studentInformationIdString);
				String applicationNumber = generateApplicationNumber();
				String password = generateRandomPassword();

				studentDatabase.storeApplicantCredentials(studentInformationId, applicationNumber, password, email);
				sendEmail(email, "Your Applicant Credentials",
						"Your application number: " + applicationNumber + "\nYour password: " + password);
				JOptionPane.showMessageDialog(this, "Applicant credentials sent to " + email, "Success",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this,
						"Invalid student information ID format: " + studentInformationIdString, "Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error sending applicant credentials: " + e.getMessage(),
						"Database Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void sendStudentCredentials(int row) {
		String studentInformationIdString = table.getValueAt(row, 0).toString();
		String email = (String) table.getValueAt(row, 6);

		int confirm = JOptionPane.showConfirmDialog(this,
				"Are you sure you want to send student credentials to " + email + "?", "Confirm Send Credentials",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (confirm == JOptionPane.YES_OPTION) {
			try {
				int studentInformationId = Integer.parseInt(studentInformationIdString);
				String studentNumber = generateStudentNumber();
				String password = generateRandomPassword();

				studentDatabase.storeStudentCredentials(studentInformationId, studentNumber, password);
				sendEmail(email, "Your Student Credentials",
						"Your student number: " + studentNumber + "\nYour password: " + password);
				JOptionPane.showMessageDialog(this, "Student credentials sent to " + email, "Success",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this,
						"Invalid student information ID format: " + studentInformationIdString, "Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error sending student credentials: " + e.getMessage(),
						"Database Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private String generateApplicationNumber() {
		return "APP" + generateRandomString(5);
	}

	private String generateStudentNumber() {
		return "STU" + generateRandomString(5);
	}

	private String generateRandomPassword() {
		return generateRandomString(5);
	}

	private String generateRandomString(int length) {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(chars.charAt(random.nextInt(chars.length())));
		}
		return sb.toString();
	}

	private void sendEmail(String to, String subject, String body) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true"); 
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		final String username = "ralphmatthew.samonte@gmail.com";
		final String password = "dodhcyvlxrthfuvc";

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(body);

			Transport.send(message);

			System.out.println("Email sent successfully");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	private void viewArchivedForms() {
		SwingUtilities.invokeLater(() -> {
			ArchiveClass archiveClass = new ArchiveClass(connection);
			archiveClass.setVisible(true);
		});
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
