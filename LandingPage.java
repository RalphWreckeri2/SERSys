package sersystem;

import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.*;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LandingPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() { 
				try {
					LandingPage frame = new LandingPage();
					frame.setVisible(true);	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LandingPage() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 710, 737);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 0, 255));
		panel.setBounds(0, 0, 694, 258);
		contentPane.add(panel);
		panel.setLayout(null);

		ImageIcon originalIcon = new ImageIcon("C:\\Users\\Administrator\\Documents\\resources\\background.png");

		int labelWidth = 674;
		int labelHeight = 236;
		Image scaledImage = originalIcon.getImage().getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);

		RoundedImageLabel roundedLabel = new RoundedImageLabel(new ImageIcon(scaledImage));
		roundedLabel.setBounds(10, 11, 674, 236);
		panel.add(roundedLabel);

		RoundedPanel panel_1 = new RoundedPanel();
		panel_1.setBackground(new Color(128, 0, 255));
		panel_1.setBounds(360, 291, 308, 306);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		RoundedButton btnNewButton_1_1 = new RoundedButton("Applicant's Portal");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminLogIn loginFrame = new AdminLogIn();
				loginFrame.setVisible(true);
				LandingPage.this.setVisible(false);
			}
		});

		btnNewButton_1_1.setText("Sign In");
		btnNewButton_1_1.setForeground(new Color(128, 0, 255));
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1_1.setBackground(new Color(250, 194, 5));
		btnNewButton_1_1.setBounds(69, 229, 176, 23);
		panel_1.add(btnNewButton_1_1);

		JLabel lblControlAccess = new JLabel("Control Access");
		lblControlAccess.setForeground(new Color(250, 194, 5));
		lblControlAccess.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblControlAccess.setBounds(81, 41, 147, 41);
		panel_1.add(lblControlAccess);

		JLabel lblAdminAccessOnly = new JLabel("Admin Access Only.");
		lblAdminAccessOnly.setForeground(new Color(255, 255, 255));
		lblAdminAccessOnly.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAdminAccessOnly.setBounds(98, 107, 124, 41);
		panel_1.add(lblAdminAccessOnly);

		JLabel lblManageMaintainAnd = new JLabel("Manage, maintain, and protect");
		lblManageMaintainAnd.setForeground(Color.WHITE);
		lblManageMaintainAnd.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblManageMaintainAnd.setBounds(56, 126, 206, 41);
		panel_1.add(lblManageMaintainAnd);

		JLabel lblTheFutureOf = new JLabel("the future of Dreams University.");
		lblTheFutureOf.setForeground(Color.WHITE);
		lblTheFutureOf.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTheFutureOf.setBounds(56, 147, 206, 41);
		panel_1.add(lblTheFutureOf);

		RoundedButton btnNewButton = new RoundedButton("Apply Now");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentInformation registrationFrame = null;
				try {
					registrationFrame = new StudentInformation();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				registrationFrame.setVisible(true);
				LandingPage.this.setVisible(false);
			}
		});

		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton.setForeground(new Color(250, 194, 5));
		btnNewButton.setBackground(new Color(128, 0, 255));
		btnNewButton.setBounds(33, 461, 213, 23);
		contentPane.add(btnNewButton);

		RoundedButton btnNewButton_1 = new RoundedButton("Applicant's Portal");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ApplicantLogIn loginFrame = new ApplicantLogIn();
				loginFrame.setVisible(true);
				LandingPage.this.setVisible(false);
			}
		});

		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1.setForeground(new Color(250, 194, 5));
		btnNewButton_1.setBackground(new Color(128, 0, 255));
		btnNewButton_1.setBounds(33, 529, 213, 23);
		contentPane.add(btnNewButton_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(128, 0, 255));
		panel_2.setBounds(0, 651, 694, 47);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblAtDreamsUniversity_1_2 = new JLabel(
				"@ 2024 Dreams University. All Rights Reserved | Contact: dreamsuniversity@gmail.com");
		lblAtDreamsUniversity_1_2.setForeground(new Color(255, 255, 255));
		lblAtDreamsUniversity_1_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAtDreamsUniversity_1_2.setBounds(106, 0, 482, 41);
		panel_2.add(lblAtDreamsUniversity_1_2);

		JLabel lblNewLabel = new JLabel("Welcome Future");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(33, 291, 213, 41);
		contentPane.add(lblNewLabel);

		JLabel lblDreamers = new JLabel("Dreamers!");
		lblDreamers.setForeground(new Color(250, 194, 5));
		lblDreamers.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDreamers.setBounds(33, 314, 213, 41);
		contentPane.add(lblDreamers);

		JLabel lblWhereBigIdeas = new JLabel("Where big ideas take shape and futures are built.");
		lblWhereBigIdeas.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblWhereBigIdeas.setBounds(33, 359, 288, 41);
		contentPane.add(lblWhereBigIdeas);

		JLabel lblAtDreamsUniversity = new JLabel("At Dreams University, we're here to support and");
		lblAtDreamsUniversity.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAtDreamsUniversity.setBounds(33, 377, 308, 41);
		contentPane.add(lblAtDreamsUniversity);

		JLabel lblAtDreamsUniversity_1 = new JLabel("inspire future dreamers like you. Explore, learn, and");
		lblAtDreamsUniversity_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAtDreamsUniversity_1.setBounds(33, 395, 308, 41);
		contentPane.add(lblAtDreamsUniversity_1);

		JLabel lblAtDreamsUniversity_1_1 = new JLabel("let your dreams soar!");
		lblAtDreamsUniversity_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAtDreamsUniversity_1_1.setBounds(33, 411, 308, 41);
		contentPane.add(lblAtDreamsUniversity_1_1);
		
		RoundedButton btnNewButton_1_2 = new RoundedButton("Student Portal");
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentPortal studentFrame = new StudentPortal();
				studentFrame.setVisible(true);
				LandingPage.this.setVisible(false);
			}
		});
		
		btnNewButton_1_2.setText("Student Portal");
		btnNewButton_1_2.setForeground(new Color(250, 194, 5));
		btnNewButton_1_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1_2.setBackground(new Color(128, 0, 255));
		btnNewButton_1_2.setBounds(33, 563, 213, 23);
		contentPane.add(btnNewButton_1_2);
		
		RoundedButton rndbtnApplicationDetails = new RoundedButton("Apply Now");
		rndbtnApplicationDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Details detailsFrame = new Details();
				detailsFrame.setVisible(true);
				LandingPage.this.setVisible(false);
			}
		}); 
		
		rndbtnApplicationDetails.setText("Application Details");
		rndbtnApplicationDetails.setForeground(new Color(250, 194, 5));
		rndbtnApplicationDetails.setFont(new Font("Tahoma", Font.BOLD, 12));
		rndbtnApplicationDetails.setBackground(new Color(128, 0, 255));
		rndbtnApplicationDetails.setBounds(33, 495, 213, 23);
		contentPane.add(rndbtnApplicationDetails);
	}
}