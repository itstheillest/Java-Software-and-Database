package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class appointmentInventory {
	private static final String URL = "jdbc:mysql://localhost:3306/health_information_system_record";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	private static Connection connection;
	
	public static Connection connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Connected to the database!");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Failed to connect to the database!");
			e.printStackTrace();
		}
		return connection;
	}

	public static void disconnect() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
				System.out.println("Disconnected from the database!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Connection conn = appointmentInventory.connect();
		// Load data from database when program starts
		// put here the function to load all last name and id.
		appointmentInventory.disconnect();
	}
	
	// Add this method to your appointmentInventory.java
	public static List<AppointmentData> loadAppointmentInfoFromDatabase() {
	    List<AppointmentData> appointments = new ArrayList<>();
	    String sql = "SELECT last_name, first_name, middle_name, appointment_type, appointment_date, note, appointment_ID FROM appointment_inventory";
	    
	    try (PreparedStatement pstmt = connection.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {
	        
	        while (rs.next()) {
	            // You'll need to modify AppointmentData constructor to accept ID
	            AppointmentData appointment = new AppointmentData(
	                rs.getString("last_name"),
	                rs.getString("first_name"),
	                rs.getString("middle_name"),
	                rs.getString("appointment_type"),
	                rs.getString("appointment_date"),
	                rs.getString("note"),
	                rs.getInt("appointment_ID")
	            );
	            appointments.add(appointment);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return appointments;
	}
	
	public static void refreshmDatabase() {
		loadAppointmentInfoFromDatabase();
    }
	
	public static void insertAppointmentInfoToDatabase(AppointmentData appointment) {
	    System.out.println("Appointment object: " + appointment); // Debug line
	    System.out.println("Date: " + appointment.getSelectedDay()); // Debug line
	    
	    String sql = "INSERT INTO appointment_inventory (last_name, first_name, middle_name, appointment_type, appointment_date, note) VALUES (?, ?, ?, ?, ?, ?)";
	    
	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setString(1, appointment.getLastName());
	        pstmt.setString(2, appointment.getFirstName());
	        pstmt.setString(3, appointment.getMiddleName());
	        pstmt.setString(4, appointment.getAppointmentType());
	        pstmt.setString(5, appointment.getSelectedDay()); // This should work if it's String
	        pstmt.setString(6, appointment.getNote());
	        
	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Appointment inserted successfully!");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        disconnect(); // Close connection
	    }
	}
	
	public static void updateAppointmentInfo(String lastName, String firstName, String middleName, String appointmentType, String appointmentDate, 
			String note, int ID) {
		try {
				String sql = "UPDATE appointment_inventory SET last_name = ?, first_name = ?, " +
									"middle_name = ?, appointment_type = ?, appointment_date = ?, note = ? " +
									"WHERE appointment_ID = ?";

				PreparedStatement pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, lastName);
				pstmt.setString(2, firstName);
				pstmt.setString(3, middleName);
				pstmt.setString(4, appointmentType);
				pstmt.setString(5, appointmentDate);
				pstmt.setString(6, note);
				pstmt.setInt(7, ID);

				int rowsUpdated = pstmt.executeUpdate();
				System.out.println(rowsUpdated + " row(s) updated.");

			} catch (SQLException e) {
				System.out.println("Error updating appointment: " + e.getMessage());
			}
	}

	public static void removeAppointment(int id) {
		try {
			String sql = "DELETE FROM appointment_inventory WHERE appointment_ID = ?";

			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, id);

			int rowsDeleted = pstmt.executeUpdate();
			System.out.println(rowsDeleted + " row(s) deleted.");

			} catch (SQLException e) {
				System.out.println("Error deleting appointment: " + e.getMessage());
			}
	}
}
