package sample;

import sample.leftButtonPanel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class Inventory {
	
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
		Connection conn = Inventory.connect();
		// Load data from database when program starts
		loadMedicinesFromDatabase();
		Inventory.disconnect();
	}
    
    private static final Map<String, Integer> STOCK_LIST = new HashMap<>();
    
    // Load all medicines from database into STOCK_LIST
    public static void loadMedicinesFromDatabase() {
        String sql = "SELECT medName, stock FROM medicine_inventory";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // Clear existing data first
            STOCK_LIST.clear();
            
            while (rs.next()) {
                String medName = rs.getString("medName");
                int stock = rs.getInt("stock");
                STOCK_LIST.put(medName, stock);
            }
            
        } catch (SQLException e) {
            System.err.println("Error loading medicines from database: " + e.getMessage());
            e.printStackTrace();
            
            // If database loading fails, initialize with default values
            initializeDefaultStock();
        }
    }
    
    // medName, phramaClass, dosage, manufacturer, stock
    // Fallback method to initialize with default stock if database fails
    private static void initializeDefaultStock() {
        STOCK_LIST.clear();
        STOCK_LIST.put("Paracetamol", 324);
        STOCK_LIST.put("Biogesic", 7);
        STOCK_LIST.put("Coldzep", 249);
        STOCK_LIST.put("Robitussin", 34);
        STOCK_LIST.put("Cetirizine", 94);
        STOCK_LIST.put("Amoxicillin", 7842);
        STOCK_LIST.put("Ibuprofen", 84);
        STOCK_LIST.put("Loratadine", 72);
        STOCK_LIST.put("Omeprazole", 0);
        STOCK_LIST.put("Sting", 9993);
        System.out.println("Initialized with default stock values");
    }
    
    // Method to refresh stock list from database (call this when you need to update the display)
    public static void refreshFromDatabase() {
        loadMedicinesFromDatabase();
    }
    
    // medName, pharmaClass, dosageValue, brand, stockValue
    public boolean insertMedicineToDatabase(String medName, String pharmaClass, int dosage, String brand, int stock) {
        String sql = "INSERT INTO medicine_inventory (medName, pharmaClass, dosage, brand, stock) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/health_information_system_record", "root", "");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, medName);
            pstmt.setString(2, pharmaClass);
            pstmt.setInt(3, dosage);
            pstmt.setString(4, brand);
            pstmt.setInt(5, stock);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Update STOCK_LIST immediately
                STOCK_LIST.put(medName, stock);
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error inserting medicine: " + e.getMessage());
        }
        
        return false;
    }
    
    // medName, phramaClass, dosage, manufacturer, stock
    // Method to update stock in both database and memory
    public static boolean updateMedicineStock(String medName, int newStock) {
        String sql = "UPDATE medicine_inventory SET stock = ? WHERE medName = ?";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, newStock);
            pstmt.setString(2, medName);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Update STOCK_LIST
                STOCK_LIST.put(medName, newStock);
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error updating medicine stock: " + e.getMessage());
        }
        
        return false;
    }
    
    // Get manufacturer/brand for a specific medicine
    public static String getManufacturer(String medName) {
        String sql = "SELECT brand FROM medicine_inventory WHERE medName = ?";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, medName);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String brand = rs.getString("brand");
                return brand != null ? brand : "Generic";
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting manufacturer for " + medName + ": " + e.getMessage());
        }
        
        return "Generic"; // Fallback if not found
    }

    // Get dosage for a specific medicine
    public static String getDosage(String medName) {
        String sql = "SELECT dosage FROM medicine_inventory WHERE medName = ?";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, medName);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return String.valueOf(rs.getInt("dosage"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting dosage for " + medName + ": " + e.getMessage());
        }
        
        return "500"; // Fallback dosage
    }

    // Get pharmaceutical class for a specific medicine
    public static String getPharmaClass(String medName) {
        String sql = "SELECT pharmaClass FROM medicine_inventory WHERE medName = ?";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, medName);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String pharmaClass = rs.getString("pharmaClass");
                return pharmaClass != null ? pharmaClass : "Medicine";
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting pharmaClass for " + medName + ": " + e.getMessage());
        }
        
        return "Medicine"; // Fallback pharmaceutical class
    }

    // Get medicine type (combines pharmaClass and dosage)
    public static String getMedicineType(String medName) {
        String sql = "SELECT pharmaClass, dosage FROM medicine_inventory WHERE medName = ?";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, medName);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String pharmaClass = rs.getString("pharmaClass");
                int dosage = rs.getInt("dosage");
                
                if (pharmaClass != null) {
                    return pharmaClass + " (" + dosage + "mg)";
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting medicine type for " + medName + ": " + e.getMessage());
        }
        
        return "Medicine (500mg)"; // Fallback
    }

    // Optional: Get all medicine data at once (more efficient for multiple calls)
    public static Map<String, MedicineInfo> getAllMedicineInfo() {
        Map<String, MedicineInfo> medicineInfoMap = new HashMap<>();
        String sql = "SELECT medName, pharmaClass, dosage, brand, stock FROM medicine_inventory";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String medName = rs.getString("medName");
                MedicineInfo info = new MedicineInfo(
                    rs.getString("pharmaClass"),
                    rs.getInt("dosage"),
                    rs.getString("brand"),
                    rs.getInt("stock")
                );
                medicineInfoMap.put(medName, info);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all medicine info: " + e.getMessage());
        }
        
        return medicineInfoMap;
    }
    
    // Add this new method to your Inventory.java file
    public static Map<String, Object> getMedicineDetails(String medicineName) {
        Map<String, Object> details = new HashMap<>();
        String sql = "SELECT pharmaClass, dosage, brand, stock FROM medicine_inventory WHERE medName = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, medicineName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                details.put("pharmaClass", rs.getString("pharmaClass"));
                details.put("dosage", rs.getInt("dosage"));
                details.put("brand", rs.getString("brand"));
                details.put("stock", rs.getInt("stock"));
            }

        } catch (SQLException e) {
            System.err.println("Error getting medicine details for " + medicineName + ": " + e.getMessage());
        }
        return details;
    }
    
    // New method with sorting parameter
    public static List<String> getAllMedicines(String sortType) {
        List<String> sorted = new ArrayList<>(STOCK_LIST.keySet());
        
        switch (sortType) {
            case "Name (A-Z)":
                Collections.sort(sorted);
                break;
                
            case "Name (Z-A)":
                Collections.sort(sorted, Collections.reverseOrder());
                break;
                
            case "Low Stock":
                // Sort by quantity ascending (lowest first), then by name
                sorted.sort((med1, med2) -> {
                    int stock1 = getStock(med1);
                    int stock2 = getStock(med2);
                    
                    // First compare by stock level
                    int stockComparison = Integer.compare(stock1, stock2);
                    if (stockComparison != 0) {
                        return stockComparison;
                    }
                    // If stock levels are equal, sort by name
                    return med1.compareTo(med2);
                });
                break;
                
            case "Out of Stock":
                // Sort with out of stock items first, then by name
                sorted.sort((med1, med2) -> {
                    int stock1 = getStock(med1);
                    int stock2 = getStock(med2);
                    
                    // Out of stock items (stock = 0) should come first
                    boolean isOutOfStock1 = (stock1 == 0);
                    boolean isOutOfStock2 = (stock2 == 0);
                    
                    if (isOutOfStock1 && !isOutOfStock2) {
                        return -1; // med1 comes first (out of stock)
                    } else if (!isOutOfStock1 && isOutOfStock2) {
                        return 1; // med2 comes first (out of stock)
                    } else {
                        // Both are same status, sort by name
                        return med1.compareTo(med2);
                    }
                });
                break;
                
            default:
                Collections.sort(sorted); // Default to A-Z
                break;
        }
        
        return sorted;
    }

    public static int getStock(String medicineName) {
        return STOCK_LIST.getOrDefault(medicineName, 0);
    }

    public static void setStock(String name, int amount) {
        STOCK_LIST.put(name, amount);
        // Optionally update database immediately
        updateMedicineStock(name, amount);
    }
    
    public static boolean updateStock(String medName, int newQuantity) {
        String sql = "UPDATE medicine_inventory SET stock = ? WHERE medName = ?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/health_information_system_record", "root", "");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, newQuantity);
            pstmt.setString(2, medName);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean removeMedicineFromDatabase(String medicineName) {
        String sql = "DELETE FROM medicine_inventory WHERE medName = ?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/health_information_system_record", "root", "");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, medicineName);
            int rowsAffected = pstmt.executeUpdate();
            
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error removing medicine: " + e.getMessage());
            return false;
        }
    }
    
 // Replace the old updateMedicine method with this one in Inventory.java
    public static boolean updateMedicine(String medicineName, String pharmaClass, int dosage, String brand, int stock) {
        // FIXED: Corrected column name 'pharmaClass' and SQL parameter indexing
        String sql = "UPDATE medicine_inventory SET pharmaClass = ?, dosage = ?, brand = ?, stock = ? WHERE medName = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pharmaClass);
            pstmt.setInt(2, dosage);
            pstmt.setString(3, brand);
            pstmt.setInt(4, stock);
            pstmt.setString(5, medicineName); // This is the 5th parameter for the WHERE clause

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Helper class for medicine information
    public static class MedicineInfo {
        public final String pharmaClass;
        public final int dosage;
        public final String brand;
        public final int stock;
        
        public MedicineInfo(String pharmaClass, int dosage, String brand, int stock) {
            this.pharmaClass = pharmaClass != null ? pharmaClass : "Medicine";
            this.dosage = dosage;
            this.brand = brand != null ? brand : "Generic";
            this.stock = stock;
        }
        
        public String getMedicineType() {
            return pharmaClass + " (" + dosage + "mg)";
        }
    }
}