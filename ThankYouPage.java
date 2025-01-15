package sersystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThankYouPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ThankYouPage frame = new ThankYouPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ThankYouPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 757, 627);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		initializeComponents();
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
		panel_1_1.setBounds(30, 26, 621, 283);
		panel_1.add(panel_1_1);

		JLabel lblThankYou = new JLabel("Thank You!");
		lblThankYou.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblThankYou.setBounds(270, 28, 100, 25);
		panel_1_1.add(lblThankYou);

		JTextArea txtThankYouMessage = new JTextArea();
		txtThankYouMessage.setBounds(30, 64, 561, 177);
		panel_1_1.add(txtThankYouMessage);
		txtThankYouMessage.setText("Your application has been successfully submitted.\n\n"
				+ "We appreciate your interest in Dreams University.\n"
				+ "Our admissions team will carefully review your application\n"
				+ "and will contact you soon with further information.\n\n"
				+ "Please check your email regularly for updates on your application status.\n\n"
				+ "If you have any questions, please don't hesitate to contact our admissions office.");
		txtThankYouMessage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtThankYouMessage.setEditable(false);
		txtThankYouMessage.setBackground(new Color(245, 245, 245));
		txtThankYouMessage.setWrapStyleWord(true);
		txtThankYouMessage.setLineWrap(true);

		RoundedButton btnClose = new RoundedButton("Close");
		btnClose.setText("Close");
		btnClose.setForeground(new Color(255, 198, 0));
		btnClose.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnClose.setBackground(new Color(128, 0, 255));
		btnClose.setBounds(581, 320, 72, 23);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LandingPage homeFrame = new LandingPage();
                homeFrame.setVisible(true);
                ThankYouPage.this.setVisible(false);
			}
		});
		panel_1.add(btnClose);
	}
}
