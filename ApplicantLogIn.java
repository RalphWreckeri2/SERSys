package sersystem;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class ApplicantLogIn extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField ApplicationNumber;
	private JPasswordField StudentPassword; 

	public ApplicantLogIn() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 629, 468);
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

		JLabel lblNewLabel = new JLabel("College Applicant's Portal");
		lblNewLabel.setForeground(new Color(255, 198, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(273, 16, 330, 54);
		panel.add(lblNewLabel);

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

		RoundedPanel panel_1 = new RoundedPanel();
		panel_1.setBorder(null);
		panel_1.setBackground(new Color(245, 245, 245));
		panel_1.setBounds(86, 128, 437, 234);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		ApplicationNumber = new JTextField();
		ApplicationNumber.setBackground(new Color(255, 255, 255));
		ApplicationNumber.setBounds(48, 70, 337, 20);
		panel_1.add(ApplicationNumber);
		ApplicationNumber.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Enter Application Number");
		lblNewLabel_2.setBounds(48, 56, 190, 14);
		panel_1.add(lblNewLabel_2);

		JLabel lblNewLabel_2_1 = new JLabel("Enter Password");
		lblNewLabel_2_1.setBounds(48, 101, 190, 14);
		panel_1.add(lblNewLabel_2_1);

		StudentPassword = new JPasswordField();
		StudentPassword.setColumns(10);
		StudentPassword.setBackground(new Color(255, 255, 255));
		StudentPassword.setBounds(48, 115, 337, 20);
		panel_1.add(StudentPassword);

		RoundedButton btnNewButton = new RoundedButton("Log In");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String applicationNumber = ApplicationNumber.getText().trim();
				String password = new String(StudentPassword.getPassword()).trim();

				if (applicationNumber.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(ApplicantLogIn.this, "Please fill in all fields!", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					validateLogin(applicationNumber, password);
				}
			}
		});

		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setForeground(new Color(255, 198, 0));
		btnNewButton.setBackground(new Color(128, 0, 255));
		btnNewButton.setBounds(48, 156, 72, 23);
		panel_1.add(btnNewButton);

		RoundedButton btnForgotPassword = new RoundedButton("Forgot Password");
		btnForgotPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ForgotPassword fpFrame = new ForgotPassword("ApplicantLogIn");
				fpFrame.setVisible(true);
				ApplicantLogIn.this.setVisible(false);
			}
		});

		btnForgotPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnForgotPassword.setForeground(new Color(255, 198, 0));
		btnForgotPassword.setBackground(new Color(128, 0, 255));
		btnForgotPassword.setBounds(130, 156, 138, 23);
		panel_1.add(btnForgotPassword);

		RoundedButton btnContactUs = new RoundedButton("Contact Us");
		btnContactUs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Contactpage contactFrame = new Contactpage("ApplicantLogIn");
				contactFrame.setVisible(true);
				ApplicantLogIn.this.setVisible(false);
			}
		});

		btnContactUs.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnContactUs.setForeground(new Color(255, 198, 0));
		btnContactUs.setBackground(new Color(128, 0, 255));
		btnContactUs.setBounds(278, 156, 107, 23);
		panel_1.add(btnContactUs);
		
		RoundedButton rndbtnBack = new RoundedButton("Contact Us");
		rndbtnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LandingPage homeFrame = new LandingPage();
                homeFrame.setVisible(true);
                ApplicantLogIn.this.setVisible(false);
			}
		});
		
		rndbtnBack.setBounds(451, 373, 72, 23);
		contentPane.add(rndbtnBack);
		rndbtnBack.setText("Back");
		rndbtnBack.setForeground(new Color(255, 198, 0));
		rndbtnBack.setFont(new Font("Tahoma", Font.BOLD, 11));
		rndbtnBack.setBackground(new Color(128, 0, 255));
	}

	private boolean validateLogin(String applicationNumber, String password) {
	    if (applicationNumber.isEmpty() || password.isEmpty()) {
	        return false;
	    }

	    Connection conn = db_connection.getConnection();
	    if (conn != null) {
	        try {
	            // First, check if the application number exists
	            String checkAppNumSql = "SELECT * FROM student WHERE application_number = ?";
	            PreparedStatement checkAppNumStmt = conn.prepareStatement(checkAppNumSql);
	            checkAppNumStmt.setString(1, applicationNumber);
	            ResultSet appNumRs = checkAppNumStmt.executeQuery();

	            if (!appNumRs.next()) {
	                // Application number doesn't exist
	                JOptionPane.showMessageDialog(ApplicantLogIn.this, "Invalid application number!", "Error", JOptionPane.ERROR_MESSAGE);
	                return false;
	            }

	            // If application number exists, check the password
	            String checkPasswordSql = "SELECT * FROM student WHERE application_number = ? AND student_password = ?";
	            PreparedStatement checkPasswordStmt = conn.prepareStatement(checkPasswordSql);
	            checkPasswordStmt.setString(1, applicationNumber);
	            checkPasswordStmt.setString(2, password);
	            ResultSet passwordRs = checkPasswordStmt.executeQuery();

	            if (passwordRs.next()) {
	                JOptionPane.showMessageDialog(ApplicantLogIn.this, "Log In Successful!");
	                dispose();

	                // Proceed to the next screen (ApplicantDB)
	                EventQueue.invokeLater(() -> {
	                    try {
	                        ApplicantDB frame = new ApplicantDB(applicationNumber);
	                        frame.setVisible(true);
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                });

	                return true;
	            } else {
	                // Password is incorrect
	                JOptionPane.showMessageDialog(ApplicantLogIn.this, "Invalid password!", "Error", JOptionPane.ERROR_MESSAGE);
	                return false;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (conn != null) {
	                    conn.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return false;
	}

	private boolean checkBothCredentials(String applicationNumber, String password) {
	    Connection conn = db_connection.getConnection();
	    if (conn != null) {
	        try {
	            // Check if either application number or password is incorrect
	            String sql = "SELECT * FROM student WHERE application_number = ? OR student_password = ?";
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setString(1, applicationNumber);
	            stmt.setString(2, password);
	            ResultSet rs = stmt.executeQuery();

	            if (!rs.next()) {
	                // Both application number and password are incorrect
	                JOptionPane.showMessageDialog(ApplicantLogIn.this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
	            } else {
	                // Application number is incorrect, inform the user
	                JOptionPane.showMessageDialog(ApplicantLogIn.this, "Invalid application number!", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (conn != null) {
	                    conn.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return false;
	}


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicantLogIn frame = new ApplicantLogIn();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}