package sersystem;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
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
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SiblingInformation extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel siblingPanel;
	private List<JPanel> siblingPanels;
	private int siblingCount = 0;
	private int studentInformationId;
	private Connection connection;
	private JComboBox<String> comboBoxHasSiblings;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SiblingInformation frame = new SiblingInformation(1); // Example student_information_id
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SiblingInformation(int studentInformationId) throws SQLException {
		this.studentInformationId = studentInformationId;
		siblingPanels = new ArrayList<>();
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 757, 627);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		initializeComponents();
		initializeDatabase();

		// Add an initial sibling panel
		addSiblingPanel();
		updateSiblingPanelVisibility();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int option = JOptionPane.showConfirmDialog(SiblingInformation.this, "Are you sure you want to exit?",
						"Confirm Exit", JOptionPane.YES_NO_OPTION);

				if (option == JOptionPane.YES_OPTION) {
					System.exit(0); // Close the application
				}
			}
		});
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
		panel_1.setBounds(33, 171, 676, 372);
		contentPane.add(panel_1);

		RoundedPanel panel_1_1 = new RoundedPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setBackground(Color.WHITE);
		panel_1_1.setBounds(30, 11, 621, 25);
		panel_1.add(panel_1_1);

		JLabel lblStudentInformation = new JLabel("Sibling Information (If it may Apply)");
		lblStudentInformation.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblStudentInformation.setBounds(204, 0, 232, 25);
		panel_1_1.add(lblStudentInformation);

		siblingPanel = new RoundedPanel();
		siblingPanel.setLayout(null);
		siblingPanel.setBackground(new Color(255, 255, 255));
		siblingPanel.setBounds(30, 116, 621, 211);
		panel_1.add(siblingPanel);

		JScrollPane scrollPane = new JScrollPane(siblingPanel);
		scrollPane.setBounds(30, 116, 621, 211);
		scrollPane.setBorder(null);
		panel_1.add(scrollPane);

		RoundedButton rndbtnAddSibling = new RoundedButton("Add Sibling");
		rndbtnAddSibling.setText("Add Sibling");
		rndbtnAddSibling.setForeground(new Color(255, 198, 0));
		rndbtnAddSibling.setFont(new Font("Tahoma", Font.BOLD, 11));
		rndbtnAddSibling.setBackground(new Color(128, 0, 255));
		rndbtnAddSibling.setBounds(30, 338, 140, 23);
		rndbtnAddSibling.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addSiblingPanel();
			}
		});
		panel_1.add(rndbtnAddSibling);

		JLabel lblIHaveSiblings = new JLabel("I have siblings:");
		lblIHaveSiblings.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblIHaveSiblings.setBounds(30, 63, 185, 25);
		panel_1.add(lblIHaveSiblings);

		String[] options = { "Yes", "No" };
		comboBoxHasSiblings = new JComboBox<>(options);
		comboBoxHasSiblings.setBounds(115, 61, 232, 25);
		comboBoxHasSiblings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateSiblingPanelVisibility();
			}
		});
		panel_1.add(comboBoxHasSiblings);

		RoundedButton rndbtnNext = new RoundedButton("Next");
		rndbtnNext.setText("Next");
		rndbtnNext.setForeground(new Color(255, 198, 0));
		rndbtnNext.setFont(new Font("Tahoma", Font.BOLD, 11));
		rndbtnNext.setBackground(new Color(128, 0, 255));
		rndbtnNext.setBounds(637, 554, 72, 23);
		rndbtnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (validateForm()) {
					saveSiblingInformation();
				}
			}
		});
		contentPane.add(rndbtnNext);
	}

	private void initializeDatabase() throws SQLException {
		connection = db_connection.getConnection();
	}

	private void addSiblingPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, siblingCount * 211, 621, 211);

		JLabel lblSibling = new JLabel("Sibling " + (siblingCount + 1));
		lblSibling.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSibling.setBounds(20, 24, 232, 25);
		panel.add(lblSibling);

		// Add sibling detail fields dynamically
		addTextField(panel, "Name:", 20, 60, 64, 60, 232, 25, true);
		addTextField(panel, "Age:", 324, 60, 368, 60, 232, 25, true);
		addTextField(panel, "Highest Educational Attainment:", 20, 96, 189, 98, 411, 25, false);
		addTextField(panel, "Current School (if applicable)", 20, 133, 189, 132, 411, 25, false);
		addTextField(panel, "Year Graduated (If applicable)", 20, 169, 189, 171, 105, 25, false);
		addTextField(panel, "Occupation (if applicable)", 324, 168, 464, 171, 136, 25, false);

		// Button to remove a sibling panel
		RoundedButton rndbtnRemoveSibling = new RoundedButton("Drop");
		rndbtnRemoveSibling.setText("Drop");
		rndbtnRemoveSibling.setForeground(new Color(255, 198, 0));
		rndbtnRemoveSibling.setFont(new Font("Tahoma", Font.BOLD, 11));
		rndbtnRemoveSibling.setBackground(new Color(128, 0, 255));
		rndbtnRemoveSibling.setBounds(520, 24, 80, 23);
		rndbtnRemoveSibling.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeSiblingPanel(panel);
			}
		});
		panel.add(rndbtnRemoveSibling);

		siblingPanel.add(panel);
		siblingPanels.add(panel);
		siblingCount++;

		// Adjust the sibling panel's preferred size and revalidate
		siblingPanel.setPreferredSize(new Dimension(621, siblingCount * 211));
		JScrollPane scrollPane = (JScrollPane) siblingPanel.getParent().getParent();
		scrollPane.revalidate();
		siblingPanel.repaint();
	}

	private void addTextField(JPanel panel, String label, int labelX, int labelY, int fieldX, int fieldY,
			int fieldWidth, int fieldHeight, boolean required) {
		JLabel lbl = new JLabel(label);
		lbl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lbl.setBounds(labelX, labelY, 185, 25);
		panel.add(lbl);

		JTextField textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField.setBackground(new Color(235, 235, 235));
		textField.setBounds(fieldX, fieldY, fieldWidth, fieldHeight);
		panel.add(textField);

		// Store a reference to the text field for validation
		if (required) {
			textField.setName("required"); // Mark it as required
		}

		// Add KeyListener for numeric input validation
		if (label.equals("Age:") || label.contains("Year")) {
			textField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					char c = e.getKeyChar();
					if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
						e.consume();
					}
				}
			});
		}
	}

	private void removeSiblingPanel(JPanel panelToRemove) {
		siblingPanel.remove(panelToRemove);
		siblingPanels.remove(panelToRemove);
		siblingCount--;

		// Recalculate the height of the remaining sibling panels
		siblingPanel.setPreferredSize(new Dimension(621, siblingCount * 211));
		JScrollPane scrollPane = (JScrollPane) siblingPanel.getParent().getParent();
		scrollPane.revalidate();
		siblingPanel.repaint();
	}

	private void updateSiblingPanelVisibility() {
		boolean hasSiblings = comboBoxHasSiblings.getSelectedItem().equals("Yes");

		// Hide the sibling panels if the user doesn't have siblings
		siblingPanel.setVisible(hasSiblings);

		// Adjust the size of the scroll pane based on visibility
		JScrollPane scrollPane = (JScrollPane) siblingPanel.getParent().getParent();
		scrollPane.revalidate();
		scrollPane.repaint();
	}

	private boolean validateForm() {
		boolean hasSiblings = comboBoxHasSiblings.getSelectedItem().equals("Yes");
		if (!hasSiblings) {
			return true; // No validation needed if there are no siblings
		}

		boolean valid = true;

		for (JPanel siblingPanel : siblingPanels) {
			JTextField nameField = (JTextField) findComponentByName(siblingPanel, "Name:");
			JTextField ageField = (JTextField) findComponentByName(siblingPanel, "Age:");
			JTextField yearField = (JTextField) findComponentByName(siblingPanel, "Year Graduated (If applicable)");

			if (nameField.getText().trim().isEmpty() || ageField.getText().trim().isEmpty()) {
				valid = false;
				nameField.setBackground(Color.PINK);
				ageField.setBackground(Color.PINK);
			} else {
				nameField.setBackground(new Color(235, 235, 235));
				ageField.setBackground(new Color(235, 235, 235));
			}

			// Validate age is a valid integer
			try {
				Integer.parseInt(ageField.getText().trim());
				ageField.setBackground(new Color(235, 235, 235));
			} catch (NumberFormatException e) {
				valid = false;
				ageField.setBackground(Color.PINK);
			}

			// Validate year if it's not empty
			if (!yearField.getText().trim().isEmpty()) {
				try {
					Integer.parseInt(yearField.getText().trim());
					yearField.setBackground(new Color(235, 235, 235));
				} catch (NumberFormatException e) {
					valid = false;
					yearField.setBackground(Color.PINK);
				}
			}
		}

		if (!valid) {
			JOptionPane.showMessageDialog(this,
					"Please fill in name and age for all siblings. Age and Year must be valid numbers.",
					"Validation Error", JOptionPane.ERROR_MESSAGE);
		}

		return valid;
	}

	private void saveSiblingInformation() {
		String insertSQL = "INSERT INTO sibling_information (student_information_id, has_siblings, name, age, highest_education, current_school, year_graduated, occupation) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		boolean hasSiblings = comboBoxHasSiblings.getSelectedItem().equals("Yes");

		try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
			if (!hasSiblings) {
				// Insert a single record indicating no siblings
				ps.setInt(1, studentInformationId);
				ps.setBoolean(2, false);
				for (int i = 3; i <= 8; i++) {
					ps.setNull(i, java.sql.Types.NULL);
				}
				ps.executeUpdate();
			} else {
				// Insert records for each sibling
				for (JPanel siblingPanel : siblingPanels) {
					ps.setInt(1, studentInformationId);
					ps.setBoolean(2, true);

					String name = getTextFieldValue(siblingPanel, "Name:");
					ps.setString(3, name.isEmpty() ? null : name);

					String ageStr = getTextFieldValue(siblingPanel, "Age:");
					if (!ageStr.isEmpty()) {
						try {
							ps.setInt(4, Integer.parseInt(ageStr));
						} catch (NumberFormatException e) {
							ps.setNull(4, java.sql.Types.INTEGER);
						}
					} else {
						ps.setNull(4, java.sql.Types.INTEGER);
					}

					String highestEducation = getTextFieldValue(siblingPanel, "Highest Educational Attainment:");
					ps.setString(5, highestEducation.isEmpty() ? null : highestEducation);

					String currentSchool = getTextFieldValue(siblingPanel, "Current School (if applicable)");
					ps.setString(6, currentSchool.isEmpty() ? null : currentSchool);

					String yearGraduatedStr = getTextFieldValue(siblingPanel, "Year Graduated (If applicable)");
					if (!yearGraduatedStr.isEmpty()) {
						try {
							ps.setInt(7, Integer.parseInt(yearGraduatedStr));
						} catch (NumberFormatException e) {
							ps.setNull(7, java.sql.Types.INTEGER);
						}
					} else {
						ps.setNull(7, java.sql.Types.INTEGER);
					}

					String occupation = getTextFieldValue(siblingPanel, "Occupation (if applicable)");
					ps.setString(8, occupation.isEmpty() ? null : occupation);

					ps.executeUpdate();
				}
			}

			JOptionPane.showMessageDialog(this, "Sibling information saved successfully!", "Success",
					JOptionPane.INFORMATION_MESSAGE);
			openThankYouPage();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error saving sibling information: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private String getTextFieldValue(JPanel panel, String label) {
		Component component = findComponentByName(panel, label);
		if (component instanceof JTextField) {
			String value = ((JTextField) component).getText().trim();
			System.out.println("Getting value for " + label + ": " + value); // Debug log
			return value;
		}
		System.out.println("No text field found for label: " + label); // Debug log
		return "";
	}

	private Component findComponentByName(Container container, String labelText) {
		Component[] components = container.getComponents();
		for (int i = 0; i < components.length; i++) {
			Component component = components[i];
			if (component instanceof JLabel) {
				JLabel label = (JLabel) component;
				if (label.getText().equals(labelText)) {
					// Look for the next text field after this label
					for (int j = i + 1; j < components.length; j++) {
						if (components[j] instanceof JTextField) {
							return components[j];
						}
					}
				}
			}
		}
		return null;
	}

	private void openThankYouPage() {
		// Close the current frame
		this.dispose();

		// Open the Thank You page (assuming a class ThankYouPage exists)
		ThankYouPage thankYouPage = new ThankYouPage();
		thankYouPage.setVisible(true);
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
