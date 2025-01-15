package sersystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class SubjectsTaken extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private Connection connection;
	private int studentInformationId;
	private String program;
	private String year;
	private String semester;
	private String section;
	private boolean subjectsEntered = false;

	public SubjectsTaken(int studentInformationId, String program, String year, String semester, String section,
			Connection connection) {
		this.studentInformationId = studentInformationId;
		this.program = program;
		this.year = year;
		this.semester = semester;
		this.section = section;
		this.connection = connection;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSubjectsTaken = new JLabel("Subjects Taken");
		lblSubjectsTaken.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSubjectsTaken.setBounds(10, 11, 764, 22);
		contentPane.add(lblSubjectsTaken);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 44, 764, 473);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		model = new DefaultTableModel(new Object[][] {}, new String[] { "Subject ID", "Subject", "Proctor", "Semester",
				"Schedule", "Room", "Units", "Year", "Select" }) {
			private static final long serialVersionUID = 1L;
			Class<?>[] columnTypes = new Class[] { Integer.class, String.class, String.class, String.class,
					String.class, String.class, String.class, String.class, Boolean.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		};
		table.setModel(model);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveSubjects();
			}
		});
		btnSave.setBounds(685, 528, 89, 23);
		contentPane.add(btnSave);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(586, 528, 89, 23);
		contentPane.add(btnCancel);

		loadSubjects();
	}

	private void loadSubjects() {
		try {
			String programAbbreviation = program.equals("BS in Information Technology") ? "bsit" : "bspsych";

			for (int y = 1; y <= 4; y++) { // Loop through all 4 years
				String tableName = programAbbreviation + "_year" + y + "_" + y + "101";
				String sql = "SELECT * FROM " + tableName;
				PreparedStatement pstmt = connection.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
					model.addRow(new Object[] { rs.getInt("subjects_id"), rs.getString("subject"),
							rs.getString("proctor"), rs.getString("semester"), rs.getString("schedule"),
							rs.getString("room"), rs.getString("units"), "Year " + y, false });
				}

				rs.close();
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error loading subjects: " + e.getMessage(), "Database Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void saveSubjects() {
		try {
			String sql = "INSERT INTO irregular_student_subjects (student_information_id, program, year, subjects_id, subject) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pstmt = connection.prepareStatement(sql);

			// Map program to match ENUM values
			String programEnum = program.equals("BS in Information Technology") ? "BSIT" : "PSPsych";

			// Extract numeric year from the year string
			String numericYear = year.replaceAll("\\D", ""); // Extracts only the numeric part

			List<String> savedSubjects = new ArrayList<>();

			for (int i = 0; i < model.getRowCount(); i++) {
				Boolean isSelected = (Boolean) model.getValueAt(i, 8);
				if (isSelected) {
					int subjectId = (Integer) model.getValueAt(i, 0);
					String subject = (String) model.getValueAt(i, 1);

					pstmt.setInt(1, studentInformationId);
					pstmt.setString(2, programEnum);
					pstmt.setString(3, numericYear);
					pstmt.setInt(4, subjectId);
					pstmt.setString(5, subject);

					pstmt.addBatch();
					savedSubjects.add(String.valueOf(subjectId));
				}
			}

			if (savedSubjects.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No subjects selected. Please select at least one subject.",
						"Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}

			int[] results = pstmt.executeBatch();
			pstmt.close();

			int successCount = 0;
			for (int result : results) {
				if (result > 0) {
					successCount++;
				}
			}

			if (successCount == savedSubjects.size()) {
				subjectsEntered = true;
				JOptionPane.showMessageDialog(this, "All subjects saved successfully!", "Success",
						JOptionPane.INFORMATION_MESSAGE);
				dispose();
			} else if (successCount > 0) {
				JOptionPane.showMessageDialog(this,
						successCount + " out of " + savedSubjects.size()
								+ " subjects saved successfully. Some subjects could not be saved.",
						"Partial Success", JOptionPane.WARNING_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "No subjects could be saved. Please try again.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error saving subjects: " + e.getMessage(), "Database Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public boolean areSubjectsEntered() {
		return subjectsEntered;
	}
}
