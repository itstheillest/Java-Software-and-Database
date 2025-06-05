package main;

import java.awt.Color;
import java.awt.Font;

public class ApplicationConstants {
    // System Information
    public static final String SYSTEM_NAME = "Barangay Market Record Management System";
    public static final String VERSION = "1.0.0";
    public static final String BUILD_DATE = "January 2025";
    public static final String DEVELOPER_TEAM = "Local Government Development Team";

    // Window Configuration
    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 800;
    public static final String WINDOW_TITLE = SYSTEM_NAME + " v" + VERSION;

    // Sample data for dashboard
    public static final int totalRecords = 1247;
    public static final int pendingRequests = 23;
    public static final int completedToday = 8;
    public static final int activeUsers = 15;

    // Slideshow Configuration
    public static final int SLIDESHOW_DELAY = 3000; // 3 seconds
    public static final String[] IMAGE_PATHS = {
            "Images/pic1.jpg",
            "Images/pic2.jpg",
            "Images/pic3.jpg"
    };

    // Menu Items
    public static final String[] MENU_ITEMS = {
            "Dashboard",
            "Barangay Document Application",
            "File Complaints",
            "Barangay Tanod Records",
            "Health System Records",
            "About"
    };

    // Document Types
    public static final String[] DOCUMENT_TYPES = {
            "Select Document Type",
            "Certificate of Indigency",
            "Barangay Certificate for Business",
            "Certificate of Residency",
            "Certificate of Solo Parent",
            "Barangay ID",
            "Individual Barangay Clearance",
            "Business Barangay Clearance",
            "Business Permit"
    };

    // Status Options
    public static final String[] STATUS_OPTIONS = {
            "All", "Pending", "Approved", "Released", "Rejected"
    };

    // Civil Status Options
    public static final String[] CIVIL_STATUS_OPTIONS = {
            "Single", "Married", "Widowed", "Divorced", "Separated"
    };

    // Contact Information
    public static final String SUPPORT_EMAIL = "support@barangaymarket.gov.ph";
    public static final String SUPPORT_PHONE = "(02) 8XXX-XXXX";
    public static final String EMERGENCY_EMAIL = "emergency@barangaymarket.gov.ph";
    public static final String EMERGENCY_PHONE = "(02) 8XXX-YYYY";

    // Private constructor to prevent instantiation
    private ApplicationConstants() {
        throw new UnsupportedOperationException("This is a utility class");
    }
}