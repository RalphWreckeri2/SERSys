package sersystem;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Contactpage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String previousPage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Contactpage frame = new Contactpage("ApplicantLogIn");
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
	public Contactpage(String previousPage) {
		this.previousPage = previousPage;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 629, 433);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 0, 255));
		panel.setBounds(0, 0, 613, 80);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(10, -3, 87, 84);
		panel.add(lblNewLabel_1);

		ImageIcon originalIcon = new ImageIcon("C:\\Users\\Administrator\\Documents\\resources\\purplelogo.png");
		Image img = originalIcon.getImage();
		Image scaledImg = img.getScaledInstance(lblNewLabel_1.getWidth(), lblNewLabel_1.getHeight(),
				Image.SCALE_SMOOTH);

		lblNewLabel_1.setIcon(new ImageIcon(scaledImg));

		JLabel lblDreams = new JLabel("DREAMS");
		lblDreams.setForeground(new Color(250, 194, 5));
		lblDreams.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDreams.setBounds(96, 16, 70, 43);
		panel.add(lblDreams);

		JLabel lblUniversity = new JLabel("UNIVERSITY");
		lblUniversity.setForeground(new Color(255, 255, 255));
		lblUniversity.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblUniversity.setBounds(96, 28, 70, 43);
		panel.add(lblUniversity);

		JLabel lblNewLabel = new JLabel("Contact Us");
		lblNewLabel.setForeground(new Color(255, 198, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(464, 16, 139, 54);
		panel.add(lblNewLabel);

		RoundedPanel panel_1 = new RoundedPanel();
		panel_1.setBorder(null);
		panel_1.setBackground(new Color(245, 245, 245));
		panel_1.setBounds(41, 118, 536, 234);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("We're here to help! Feel free to reach out using the details below.");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(36, 21, 368, 14);
		panel_1.add(lblNewLabel_2);

		JLabel lblNewLabel_2_1 = new JLabel("Address");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2_1.setBounds(36, 46, 190, 14);
		panel_1.add(lblNewLabel_2_1);

		RoundedButton btnNewButton = new RoundedButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goBack();
			}
		});

		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setForeground(new Color(255, 198, 0));
		btnNewButton.setBackground(new Color(128, 0, 255));
		btnNewButton.setBounds(441, 200, 72, 23);
		panel_1.add(btnNewButton);

		JLabel lblNewLabel_2_2 = new JLabel("Dreams University");
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_2.setBounds(36, 64, 114, 14);
		panel_1.add(lblNewLabel_2_2);

		JLabel lblNewLabel_2_2_1 = new JLabel("123 Pako Street, Lipa City, Batangas");
		lblNewLabel_2_2_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_2_1.setBounds(36, 83, 318, 14);
		panel_1.add(lblNewLabel_2_2_1);

		JLabel lblNewLabel_2_1_1 = new JLabel("Phone");
		lblNewLabel_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2_1_1.setBounds(36, 108, 190, 14);
		panel_1.add(lblNewLabel_2_1_1);

		JLabel lblNewLabel_2_2_2 = new JLabel("0912 123 7894 | 781 4562");
		lblNewLabel_2_2_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_2_2.setBounds(36, 125, 252, 14);
		panel_1.add(lblNewLabel_2_2_2);

		JLabel lblNewLabel_2_1_1_1 = new JLabel("Socials");
		lblNewLabel_2_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2_1_1_1.setBounds(36, 150, 190, 14);
		panel_1.add(lblNewLabel_2_1_1_1);

		JLabel lblNewLabel_2_2_2_1 = new JLabel("dreamsuniversity@gmail.com | FB: Dreams University  ");
		lblNewLabel_2_2_2_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_2_2_1.setBounds(36, 167, 318, 14);
		panel_1.add(lblNewLabel_2_2_2_1);

		JLabel lblNewLabel_2_2_2_1_1 = new JLabel("| We are open Mon-Sat every 7:00 AM - 4:00 PM");
		lblNewLabel_2_2_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_2_2_1_1.setBounds(36, 192, 318, 14);
		panel_1.add(lblNewLabel_2_2_2_1_1);
	}

	private void goBack() {
		if ("ApplicantLogIn".equals(previousPage)) {
			ApplicantLogIn loginFrame = new ApplicantLogIn();
			loginFrame.setVisible(true);
		} else if ("AdminLogIn".equals(previousPage)) {
			AdminLogIn adminLoginFrame = new AdminLogIn();
			adminLoginFrame.setVisible(true);
		} else if ("StudentPortal".equals(previousPage)) {
			// If the previous page is StudentPortal, go back to StudentPortal
			StudentPortal studentPortalFrame = new StudentPortal();
			studentPortalFrame.setVisible(true);
		}
		// Close the current page
		this.setVisible(false);
	}

}