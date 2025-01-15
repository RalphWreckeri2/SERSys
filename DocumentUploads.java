package sersystem;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DocumentUploads extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_4;
	private JTextField textField_6;
	private JTextField textField_8;
	private JTextField textField_1;
	private JTextField textField_3;
	private Connection connection;
	private int studentInformationId;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DocumentUploads frame = new DocumentUploads(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public DocumentUploads(int studentInformationId) throws SQLException {
		this.studentInformationId = studentInformationId;
		initializeComponents();
		initializeDatabase();
		setupFileUploadListeners();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int option = JOptionPane.showConfirmDialog(DocumentUploads.this, "Are you sure you want to exit?",
						"Confirm Exit", JOptionPane.YES_NO_OPTION);

				if (option == JOptionPane.YES_OPTION) {
					System.exit(0); // Close the application
				}
			}
		});
	}

	private void initializeComponents() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 829, 573);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(128, 0, 255));
		panel.setBounds(0, 0, 813, 86);
		contentPane.add(panel);

		JLabel lblCollegeApplication = new JLabel("College Application");
		lblCollegeApplication.setForeground(new Color(255, 198, 0));
		lblCollegeApplication.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblCollegeApplication.setBounds(503, 1, 300, 80);
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
		lblNewLabel.setBounds(60, 97, 676, 21);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel(
				"view important updates, and access any additional information about your journey with us. Check in regularly to stay on track as you");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(69, 114, 649, 21);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_2_1 = new JLabel("move closer to becoming part of Dreams University!");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2_1.setBounds(279, 129, 255, 21);
		contentPane.add(lblNewLabel_2_1);

		RoundedPanel panel_1 = new RoundedPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(null);
		panel_1.setBackground(new Color(245, 245, 245));
		panel_1.setBounds(33, 171, 746, 318);
		contentPane.add(panel_1);

		JLabel lblName = new JLabel("2x2 Picture");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblName.setBounds(20, 61, 182, 25);
		panel_1.add(lblName);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField.setBounds(212, 61, 429, 25);
		panel_1.add(textField);

		JLabel lblBirthdate = new JLabel("E-Signature");
		lblBirthdate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBirthdate.setBounds(20, 97, 182, 25);
		panel_1.add(lblBirthdate);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_2.setBounds(212, 97, 429, 25);
		panel_1.add(textField_2);

		RoundedPanel panel_1_1 = new RoundedPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setBackground(Color.WHITE);
		panel_1_1.setBounds(20, 25, 705, 25);
		panel_1.add(panel_1_1);

		JLabel lblStudentInformation = new JLabel("Document Uploads");
		lblStudentInformation.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblStudentInformation.setBounds(290, 0, 155, 25);
		panel_1_1.add(lblStudentInformation);

		JLabel lblGender = new JLabel("JHS Form 137");
		lblGender.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGender.setBounds(20, 133, 182, 25);
		panel_1.add(lblGender);

		textField_4 = new JTextField();
		textField_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_4.setBounds(212, 133, 429, 25);
		panel_1.add(textField_4);

		JLabel lblMaritalStatus = new JLabel("SHS Form 137");
		lblMaritalStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMaritalStatus.setBounds(20, 169, 182, 25);
		panel_1.add(lblMaritalStatus);

		textField_6 = new JTextField();
		textField_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_6.setBounds(212, 169, 429, 25);
		panel_1.add(textField_6);

		JLabel lblAddress = new JLabel("Certificate of Good Moral");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAddress.setBounds(20, 205, 182, 25);
		panel_1.add(lblAddress);

		textField_8 = new JTextField();
		textField_8.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_8.setBounds(212, 205, 429, 25);
		panel_1.add(textField_8);

		JLabel lblShsCertificateOf = new JLabel("SHS Certificate of Enrollment");
		lblShsCertificateOf.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblShsCertificateOf.setBounds(20, 241, 182, 25);
		panel_1.add(lblShsCertificateOf);

		JLabel lblAddress_1_1 = new JLabel("Certificate of Completion");
		lblAddress_1_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAddress_1_1.setBounds(20, 277, 182, 25);
		panel_1.add(lblAddress_1_1);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_1.setBounds(212, 241, 429, 25);
		panel_1.add(textField_1);

		textField_3 = new JTextField();
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_3.setBounds(212, 275, 429, 25);
		panel_1.add(textField_3);

		RoundedButton rndbtnNext = new RoundedButton("Next");
		rndbtnNext.setText("Next");
		rndbtnNext.setForeground(new Color(255, 198, 0));
		rndbtnNext.setFont(new Font("Tahoma", Font.BOLD, 11));
		rndbtnNext.setBackground(new Color(128, 0, 255));
		rndbtnNext.setBounds(707, 500, 72, 23);
		contentPane.add(rndbtnNext);

		rndbtnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validateFields()) {
					saveDocumentUploads();
				} else {
					JOptionPane.showMessageDialog(DocumentUploads.this, "Please fill in all fields before proceeding.",
							"Validation Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	private void initializeDatabase() throws SQLException {
		connection = db_connection.getConnection();
		// System.out.println("Database connected successfully");
	}

	private void setupFileUploadListeners() {
		setupFileUploadButton("2x2 Picture", textField);
		setupFileUploadButton("E-Signature", textField_2);
		setupFileUploadButton("JHS Form 137", textField_4);
		setupFileUploadButton("SHS Form 137", textField_6);
		setupFileUploadButton("Certificate of Good Moral", textField_8);
		setupFileUploadButton("SHS Certificate of Enrollment", textField_1);
		setupFileUploadButton("Certificate of Completion", textField_3);
	}

	private void setupFileUploadButton(String documentType, JTextField textField) {
		RoundedButton uploadButton = new RoundedButton("Add");
		uploadButton.setText("Add");
		uploadButton.setForeground(new Color(255, 198, 0));
		uploadButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		uploadButton.setBackground(new Color(128, 0, 255));
		uploadButton.setBounds(textField.getX() + textField.getWidth() + 10, textField.getY(), 72, 25);
		((RoundedPanel) textField.getParent()).add(uploadButton);

		uploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				uploadFile(documentType, textField);
			}
		});
	}

	private void uploadFile(String documentType, JTextField textField) {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF, JPG & PNG Files", "pdf", "jpg", "png");
		fileChooser.setFileFilter(filter);

		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			textField.setText(selectedFile.getAbsolutePath());
		}
	}

	private boolean validateFields() {
		return !textField.getText().trim().isEmpty() && !textField_2.getText().trim().isEmpty()
				&& !textField_4.getText().trim().isEmpty() && !textField_6.getText().trim().isEmpty()
				&& !textField_8.getText().trim().isEmpty() && !textField_1.getText().trim().isEmpty()
				&& !textField_3.getText().trim().isEmpty();
	}

	private void saveDocumentUploads() {
		try {
			String sql = "INSERT INTO document_uploads (two_by_two_picture, signature, jhs_form_137, shs_form_137, certificate_of_good_moral, shs_certificate_of_enrollment, certificate_of_completion, student_information_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, textField.getText());
			pstmt.setString(2, textField_2.getText());
			pstmt.setString(3, textField_4.getText());
			pstmt.setString(4, textField_6.getText());
			pstmt.setString(5, textField_8.getText());
			pstmt.setString(6, textField_1.getText());
			pstmt.setString(7, textField_3.getText());
			pstmt.setInt(8, studentInformationId);

			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(this, "Document uploads saved successfully!");
				ParentInformation parentInfo = new ParentInformation(studentInformationId);
				parentInfo.setVisible(true);
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Failed to save document uploads.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error saving document uploads: " + e.getMessage(), "Database Error",
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

}