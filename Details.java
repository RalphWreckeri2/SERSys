package sersystem;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Details extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Details frame = new Details();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Details() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 617, 573);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(128, 0, 255));
		panel.setBounds(0, 0, 601, 80);
		contentPane.add(panel);
		
		JLabel lblApplicationDetails = new JLabel("Application Details");
		lblApplicationDetails.setForeground(new Color(255, 198, 0));
		lblApplicationDetails.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblApplicationDetails.setBounds(345, 16, 246, 54);
		panel.add(lblApplicationDetails);
		
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
		panel_1.setBounds(49, 112, 505, 369);
		contentPane.add(panel_1);
		
		JLabel lblNoteTheFollowing = new JLabel("Note the following that needs to be passed");
		lblNoteTheFollowing.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNoteTheFollowing.setBounds(26, 21, 377, 41);
		panel_1.add(lblNoteTheFollowing);
		
		JLabel lblNewLabel = new JLabel("Personal Information");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(26, 73, 175, 14);
		panel_1.add(lblNewLabel);
		
		JLabel lblDocumentRequirements = new JLabel("Document Requirements");
		lblDocumentRequirements.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDocumentRequirements.setBounds(26, 94, 175, 14);
		panel_1.add(lblDocumentRequirements);
		
		JLabel lblxPicture = new JLabel("2x2 Picture");
		lblxPicture.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblxPicture.setBounds(58, 119, 175, 14);
		panel_1.add(lblxPicture);
		
		JLabel lblEsignature = new JLabel("E-Signature");
		lblEsignature.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEsignature.setBounds(58, 144, 175, 14);
		panel_1.add(lblEsignature);
		
		JLabel lblJhsForm = new JLabel("JHS FORM 137");
		lblJhsForm.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblJhsForm.setBounds(58, 169, 175, 14);
		panel_1.add(lblJhsForm);
		
		JLabel lblEsignature_1 = new JLabel("SHS Form 137");
		lblEsignature_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEsignature_1.setBounds(183, 119, 175, 14);
		panel_1.add(lblEsignature_1);
		
		JLabel lblEsignature_1_1 = new JLabel("Certificate of Good Moral");
		lblEsignature_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEsignature_1_1.setBounds(183, 144, 175, 14);
		panel_1.add(lblEsignature_1_1);
		
		JLabel lblEsignature_1_1_1 = new JLabel("SHS Certificate of Enrollment");
		lblEsignature_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEsignature_1_1_1.setBounds(183, 169, 175, 14);
		panel_1.add(lblEsignature_1_1_1);
		
		JLabel lblCertificateOfCompletion = new JLabel("Certificate of COmpletion");
		lblCertificateOfCompletion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCertificateOfCompletion.setBounds(58, 194, 175, 14);
		panel_1.add(lblCertificateOfCompletion);
		
		JLabel lblParentguardianInformation = new JLabel("Parent/Guardian Information");
		lblParentguardianInformation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblParentguardianInformation.setBounds(26, 219, 175, 14);
		panel_1.add(lblParentguardianInformation);
		
		JLabel lblSinglingInformation = new JLabel("Singling Information");
		lblSinglingInformation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSinglingInformation.setBounds(26, 242, 175, 14);
		panel_1.add(lblSinglingInformation);
		
		JLabel lblAvailablePrograms = new JLabel("Available Programs");
		lblAvailablePrograms.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAvailablePrograms.setBounds(26, 268, 377, 41);
		panel_1.add(lblAvailablePrograms);
		
		JLabel lblBachelorOfScience = new JLabel("Bachelor of Science in Psychology");
		lblBachelorOfScience.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBachelorOfScience.setBounds(26, 308, 262, 14);
		panel_1.add(lblBachelorOfScience);
		
		JLabel lblBachelorOfScience_2 = new JLabel("Bachelor of Science in Information Technology");
		lblBachelorOfScience_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBachelorOfScience_2.setBounds(26, 328, 262, 14);
		panel_1.add(lblBachelorOfScience_2);
		
		RoundedButton rndbtnBack = new RoundedButton("Back");
		rndbtnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LandingPage homeFrame = new LandingPage();
                homeFrame.setVisible(true);
                Details.this.setVisible(false);
			}
		});
		
		rndbtnBack.setText("Back");
		rndbtnBack.setForeground(new Color(255, 198, 0));
		rndbtnBack.setFont(new Font("Tahoma", Font.BOLD, 11));
		rndbtnBack.setBackground(new Color(128, 0, 255));
		rndbtnBack.setBounds(482, 492, 72, 23);
		contentPane.add(rndbtnBack);
	}
}
