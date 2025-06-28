package com.wordprocessor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ControlNumberGenerator {
    private Map<String, Integer> monthlyCounters;
    private DateTimeFormatter formatter;
    
    public ControlNumberGenerator() {
        this.monthlyCounters = new HashMap<>();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM");
    }
    
    /**
     * Generates the next control number based on current date
     * Format: yyyy-MM-0000 (where 0000 is incremental per month)
     */
    public String generateControlNumber() {
        LocalDate currentDate = LocalDate.now();
        String yearMonth = currentDate.format(formatter);
        
        // Get current counter for this month (or initialize to 0)
        int currentCounter = monthlyCounters.getOrDefault(yearMonth, 0);
        
        // Increment counter
        currentCounter++;
        
        // Update the counter for this month
        monthlyCounters.put(yearMonth, currentCounter);
        
        // Format the control number with leading zeros
        return String.format("%s-%04d", yearMonth, currentCounter);
    }
    
    /**
     * Generates a control number for a specific date
     */
    public String generateControlNumber(LocalDate date) {
        String yearMonth = date.format(formatter);
        
        int currentCounter = monthlyCounters.getOrDefault(yearMonth, 0);
        currentCounter++;
        monthlyCounters.put(yearMonth, currentCounter);
        
        return String.format("%s-%04d", yearMonth, currentCounter);
    }
    
    /**
     * Gets the current counter value for a specific month without incrementing
     */
    public int getCurrentCounter(String yearMonth) {
        return monthlyCounters.getOrDefault(yearMonth, 0);
    }
    
    /**
     * Resets counter for a specific month (useful for testing or corrections)
     */
    public void resetCounter(String yearMonth) {
        monthlyCounters.put(yearMonth, 0);
    }
    
    /**
     * Gets all monthly counters (for debugging/monitoring)
     */
    public Map<String, Integer> getAllCounters() {
        return new HashMap<>(monthlyCounters);
    }
    
    // Demo/Test method
    public static void main(String[] args) {
        ControlNumberGenerator generator = new ControlNumberGenerator();
        
        System.out.println("=== Control Number Generator Demo ===");
        
        // Generate some control numbers for current date
        System.out.println("Current date control numbers:");
        for (int i = 1; i <= 5; i++) {
            String controlNumber = generator.generateControlNumber();
            System.out.println(i + ". " + controlNumber);
        }
        
        System.out.println("\n=== Testing Month Change Behavior ===");
        
        // Test with specific dates to show month reset behavior
        LocalDate june2025 = LocalDate.of(2025, 6, 15);
        LocalDate july2025 = LocalDate.of(2025, 7, 1);
        
        // Generate numbers for June
        System.out.println("June 2025 entries:");
        for (int i = 1; i <= 3; i++) {
            String controlNumber = generator.generateControlNumber(june2025);
            System.out.println(i + ". " + controlNumber);
        }
        
        // Generate numbers for July (should reset to 0001)
        System.out.println("\nJuly 2025 entries (counter resets):");
        for (int i = 1; i <= 3; i++) {
            String controlNumber = generator.generateControlNumber(july2025);
            System.out.println(i + ". " + controlNumber);
        }
        
        // Show current counters
        System.out.println("\n=== Current Counters ===");
        Map<String, Integer> counters = generator.getAllCounters();
        for (Map.Entry<String, Integer> entry : counters.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
