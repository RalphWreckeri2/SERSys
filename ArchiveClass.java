package sersystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.*;
import java.util.List;

public class ArchiveClass extends JFrame {
	private JTable archiveTable;
	private JTextField searchField;
	private Connection connection;
	private StudentDatabase studentDatabase;

	public ArchiveClass(Connection connection) {
		this.connection = connection;
		this.studentDatabase = new StudentDatabase(connection);
		setTitle("Archived Students");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel mainPanel = new JPanel(new BorderLayout());

		// Search panel
		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		searchField = new JTextField(20);
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(e -> searchArchive());
		searchPanel.add(new JLabel("Search:"));
		searchPanel.add(searchField);
		searchPanel.add(searchButton);
		mainPanel.add(searchPanel, BorderLayout.NORTH);

		// Table
		archiveTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(archiveTable);
		mainPanel.add(scrollPane, BorderLayout.CENTER);

		add(mainPanel);

		loadArchivedStudents();
	}

	private void loadArchivedStudents() {
		try {
			List<StudentDatabase.StudentInfo> archivedStudents = studentDatabase.getArchivedStudents();
			updateTable(archivedStudents);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error loading archived students: " + e.getMessage(), "Database Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void updateTable(List<StudentDatabase.StudentInfo> students) {
		DefaultTableModel model = new DefaultTableModel(new Object[] { "ID", "Name", "Program", "Year", "Email",
				"Archived Date", "Application Number", "View Details" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 7; // Only the "View Details" column is editable
			}
		};

		for (StudentDatabase.StudentInfo student : students) {
			model.addRow(new Object[] { student.id, student.name, student.program, student.year, student.email,
					student.archivedDate, student.applicationNumber, "View" });
		}

		archiveTable.setModel(model);
		archiveTable.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer());
		archiveTable.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox()));
	}

	private void searchArchive() {
		String searchTerm = searchField.getText().trim().toLowerCase();
		DefaultTableModel model = (DefaultTableModel) archiveTable.getModel();
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
		archiveTable.setRowSorter(sorter);

		if (searchTerm.length() == 0) {
			sorter.setRowFilter(null);
		} else {
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchTerm));
		}
	}

	private void viewStudentDetails(int studentInformationId) {
		SwingUtilities.invokeLater(() -> {
			StudentDetailsView detailsView = new StudentDetailsView(connection, studentInformationId);
			detailsView.setVisible(true);
		});
	}

	class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setText((value == null) ? "" : value.toString());
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
			label = (value == null) ? "" : value.toString();
			button.setText(label);
			isPushed = true;
			return button;
		}

		@Override
		public Object getCellEditorValue() {
			if (isPushed) {
				int studentInformationId = (int) archiveTable.getValueAt(row, 0);
				viewStudentDetails(studentInformationId);
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
}
