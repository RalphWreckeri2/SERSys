package sersystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class db_connection {
	private static final String URL = "jdbc:mysql://localhost:3306/sersystem";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			showErrorMessage("Database connection failed: " + e.getMessage());
		}
		return conn;
	}

	public static void executeQuery(String sql) {
		try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			showErrorMessage("Error executing query: " + e.getMessage());
		}
	}

	private static void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Database Error", JOptionPane.ERROR_MESSAGE);
	}
}
