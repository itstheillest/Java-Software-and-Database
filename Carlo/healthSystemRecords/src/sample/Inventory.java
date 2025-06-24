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
	
	private static final String URL = "jdbc:mysql://localhost:3306/medicine_list";
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
        String sql = "SELECT medName, stock FROM medicinelist";
        
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
            
            System.out.println("Loaded " + STOCK_LIST.size() + " medicines from database");
            
        } catch (SQLException e) {
            System.err.println("Error loading medicines from database: " + e.getMessage());
            e.printStackTrace();
            
            // If database loading fails, initialize with default values
            initializeDefaultStock();
        }
    }
    
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
        String sql = "INSERT INTO medicinelist (medName, pharmaClass, dosage, brand, stock) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicine_list", "root", "");
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
                System.out.println("Medicine added successfully: " + medName);
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error inserting medicine: " + e.getMessage());
        }
        
        return false;
    }
    
    // Method to update stock in both database and memory
    public static boolean updateMedicineStock(String medName, int newStock) {
        String sql = "UPDATE medicinelist SET stock = ? WHERE medName = ?";
        
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
}