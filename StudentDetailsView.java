package sersystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class StudentDetailsView extends JFrame {
    private Connection connection;
    private int studentInformationId;
    private static final Color BACKGROUND_COLOR = new Color(240, 240, 240);
    private static final Color PANEL_COLOR = new Color(255, 255, 255);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font CONTENT_FONT = new Font("Arial", Font.PLAIN, 14);
    private static final Color LINK_COLOR = Color.BLUE;

    public StudentDetailsView(Connection connection, int studentInformationId) {
        this.connection = connection;
        this.studentInformationId = studentInformationId;

        setTitle("Student Details");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBackground(BACKGROUND_COLOR);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane);

        loadStudentDetails(mainPanel);
    }

    private void loadStudentDetails(JPanel mainPanel) {
        try {
            mainPanel.add(createInfoPanel("Student Information", loadStudentInfo()));
            mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            mainPanel.add(createInfoPanel("Document Uploads", loadDocumentUploads()));
            mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            mainPanel.add(createInfoPanel("Parent Information", loadParentInfo()));
            mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            mainPanel.add(createInfoPanel("Sibling Information", loadSiblingInfo()));
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading student details: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createInfoPanel(String title, JComponent content) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PANEL_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(10, 10, 10, 10)));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(TITLE_FONT);
        panel.add(titleLabel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JTextArea loadStudentInfo() throws SQLException {
        JTextArea infoArea = new JTextArea();
        infoArea.setFont(CONTENT_FONT);
        infoArea.setEditable(false);
        infoArea.setBackground(PANEL_COLOR);

        String sql = "SELECT * FROM student_information WHERE student_information_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, studentInformationId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    infoArea.append("Name: " + rs.getString("name") + "\n");
                    infoArea.append("Age: " + rs.getInt("age") + "\n");
                    infoArea.append("Birthdate: " + rs.getDate("birthdate") + "\n");
                    infoArea.append("Contact Number: " + rs.getString("contact_number") + "\n");
                    infoArea.append("Gender: " + rs.getString("gender") + "\n");
                    infoArea.append("Nationality: " + rs.getString("nationality") + "\n");
                    infoArea.append("Marital Status: " + rs.getString("marital_status") + "\n");
                    infoArea.append("Email: " + rs.getString("email") + "\n");
                    infoArea.append("Address: " + rs.getString("address") + "\n");
                    infoArea.append("Program: " + rs.getString("program") + "\n");
                    infoArea.append("Year: " + rs.getString("year") + "\n");
                    infoArea.append("Semester: " + rs.getString("semester") + "\n");
                    infoArea.append("Is Irregular: " + (rs.getBoolean("is_irregular") ? "Yes" : "No") + "\n");
                    infoArea.append("Section: " + rs.getString("section") + "\n");
                }
            }
        }
        return infoArea;
    }

    private JPanel loadDocumentUploads() throws SQLException {
        JPanel documentPanel = new JPanel();
        documentPanel.setLayout(new BoxLayout(documentPanel, BoxLayout.Y_AXIS));
        documentPanel.setBackground(PANEL_COLOR);

        String sql = "SELECT * FROM document_uploads WHERE student_information_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, studentInformationId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    addDocumentLink(documentPanel, "2x2 Picture", rs.getString("two_by_two_picture"));
                    addDocumentLink(documentPanel, "Signature", rs.getString("signature"));
                    addDocumentLink(documentPanel, "JHS Form 137", rs.getString("jhs_form_137"));
                    addDocumentLink(documentPanel, "SHS Form 137", rs.getString("shs_form_137"));
                    addDocumentLink(documentPanel, "Certificate of Good Moral", rs.getString("certificate_of_good_moral"));
                    addDocumentLink(documentPanel, "SHS Certificate of Enrollment", rs.getString("shs_certificate_of_enrollment"));
                    addDocumentLink(documentPanel, "Certificate of Completion", rs.getString("certificate_of_completion"));
                }
            }
        }
        return documentPanel;
    }

    private void addDocumentLink(JPanel panel, String documentName, String filePath) {
        JLabel linkLabel = new JLabel(documentName + ": " + (filePath != null ? "[View Document]" : "Not uploaded"));
        linkLabel.setFont(CONTENT_FONT);
        if (filePath != null && !filePath.isEmpty()) {
            linkLabel.setForeground(LINK_COLOR);
            linkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            linkLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    openFile(filePath);
                }
            });
        }
        panel.add(linkLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    private void openFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                JOptionPane.showMessageDialog(this, "File not found: " + filePath, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error opening file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JTextArea loadParentInfo() throws SQLException {
        JTextArea infoArea = new JTextArea();
        infoArea.setFont(CONTENT_FONT);
        infoArea.setEditable(false);
        infoArea.setBackground(PANEL_COLOR);

        String sql = "SELECT * FROM parent_information WHERE student_information_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, studentInformationId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    infoArea.append("Father's Name: " + rs.getString("father_name") + "\n");
                    infoArea.append("Father's Age: " + rs.getInt("father_age") + "\n");
                    infoArea.append("Father's Occupation: " + rs.getString("father_occupation") + "\n");
                    infoArea.append("Father's Contact: " + rs.getString("father_contact") + "\n");
                    infoArea.append("Father's Address: " + rs.getString("father_address") + "\n");
                    infoArea.append("Father Deceased: " + (rs.getBoolean("father_deceased") ? "Yes" : "No") + "\n");
                    infoArea.append("Mother's Name: " + rs.getString("mother_name") + "\n");
                    infoArea.append("Mother's Age: " + rs.getInt("mother_age") + "\n");
                    infoArea.append("Mother's Occupation: " + rs.getString("mother_occupation") + "\n");
                    infoArea.append("Mother's Contact: " + rs.getString("mother_contact") + "\n");
                    infoArea.append("Mother's Address: " + rs.getString("mother_address") + "\n");
                    infoArea.append("Mother Deceased: " + (rs.getBoolean("mother_deceased") ? "Yes" : "No") + "\n");
                    infoArea.append("Monthly Income: " + rs.getBigDecimal("monthly_income") + "\n");
                    infoArea.append("Guardian's Name: " + rs.getString("guardian_name") + "\n");
                    infoArea.append("Guardian's Contact: " + rs.getString("guardian_contact") + "\n");
                    infoArea.append("Guardian's Address: " + rs.getString("guardian_address") + "\n");
                }
            }
        }
        return infoArea;
    }

    private JTextArea loadSiblingInfo() throws SQLException {
        JTextArea infoArea = new JTextArea();
        infoArea.setFont(CONTENT_FONT);
        infoArea.setEditable(false);
        infoArea.setBackground(PANEL_COLOR);

        String sql = "SELECT * FROM sibling_information WHERE student_information_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, studentInformationId);
            try (ResultSet rs = pstmt.executeQuery()) {
                boolean hasSiblings = false;
                while (rs.next()) {
                    hasSiblings = true;
                    infoArea.append("Name: " + rs.getString("name") + "\n");
                    infoArea.append("Age: " + rs.getInt("age") + "\n");
                    infoArea.append("Highest Education: " + rs.getString("highest_education") + "\n");
                    infoArea.append("Current School: " + rs.getString("current_school") + "\n");
                    infoArea.append("Year Graduated: " + rs.getInt("year_graduated") + "\n");
                    infoArea.append("Occupation: " + rs.getString("occupation") + "\n");
                    infoArea.append("------------------------\n");
                }
                if (!hasSiblings) {
                    infoArea.append("No siblings recorded.\n");
                }
            }
        }
        return infoArea;
    }
}