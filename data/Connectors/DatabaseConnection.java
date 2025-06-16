package data.Connectors;

import java.sql.*;
import java.util.Properties;

/**
 * Database connection utility class for XAMPP MySQL
 * Manages database connections using singleton pattern
 */
public class DatabaseConnection {

    // XAMPP default configuration
    private static final String URL = "jdbc:mysql://localhost:3306/brgy_document_application";
    private static final String USERNAME = "root";  // Default XAMPP username
    private static final String PASSWORD = "";      // Default XAMPP password (empty)
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    // Singleton instance
    private static DatabaseConnection instance;

    // Private constructor
    private DatabaseConnection() {}

    /**
     * Get singleton instance
     */
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    /**
     * Get database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL JDBC driver
            Class.forName(DRIVER);

            // Create and return connection
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found. Add mysql-connector-java to your classpath.", e);
        }
    }

    /**
     * Test database connection
     * @return true if connection successful, false otherwise
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("✅ Database connection successful!");
            System.out.println("Connected to: " + conn.getMetaData().getURL());
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Database connection failed!");
            System.err.println("Error: " + e.getMessage());
            System.err.println("\n🔧 Troubleshooting steps:");
            System.err.println("1. Make sure XAMPP is running");
            System.err.println("2. Check if MySQL service is started");
            System.err.println("3. Verify database name in URL: " + URL);
            System.err.println("4. Ensure MySQL JDBC driver is in classpath");
            return false;
        }
    }

    /**
     * Close connection safely
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    // Configuration getters (for external use if needed)
    public static String getUrl() {
        return URL;
    }

    public static String getUsername() {
        return USERNAME;
    }
}