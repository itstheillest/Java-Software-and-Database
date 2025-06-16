package data.DAO;

import data.Connectors.DatabaseConnection;
import data.Models.BailCertificate;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Data Access Object for BailCertificate
 * Handles all database operations for the brgy_document_application table
 */
public class BailCertificateDAO {

    // SQL Queries
    private static final String INSERT_SQL = """
        INSERT INTO bail_certificate 
        (certification_number, date_of_filing, date_issued, document_status, 
         full_name, date_of_birth, age, complete_address, contact_number, 
         nature_of_case, case_number, court_police_station, date_bail_posted, 
         documents_to_present) 
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

    private static final String UPDATE_SQL = """
        UPDATE brgy_document_application SET 
        certification_number = ?, date_of_filing = ?, date_issued = ?, 
        document_status = ?, full_name = ?, date_of_birth = ?, age = ?, 
        complete_address = ?, contact_number = ?, nature_of_case = ?, 
        case_number = ?, court_police_station = ?, date_bail_posted = ?, 
        documents_to_present = ? 
        WHERE id = ?
        """;

    private static final String DELETE_SQL = "DELETE FROM brgy_document_application WHERE id = ?";

    private static final String SELECT_BY_ID_SQL = "SELECT * FROM brgy_document_application WHERE id = ?";

    private static final String SELECT_ALL_SQL = "SELECT * FROM brgy_document_application ORDER BY created_at DESC";

    private static final String SELECT_BY_STATUS_SQL =
            "SELECT * FROM brgy_document_application WHERE document_status = ? ORDER BY created_at DESC";

    private static final String SELECT_BY_NAME_SQL =
            "SELECT * FROM brgy_document_application WHERE full_name LIKE ? ORDER BY full_name";

    private static final String SELECT_BY_CERT_NUMBER_SQL =
            "SELECT * FROM brgy_document_application WHERE certification_number = ?";

    //Create - Insert a new document record
    public static boolean insert(BailCertificate document) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            setStatementParameters(stmt, document);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Get the generated ID
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        document.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error inserting document: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //Read - Get document by ID
    public BailCertificate findById(int id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDocument(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding document by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    //Read - Get all documents
    public List<BailCertificate> findAll() {
        List<BailCertificate> documents = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                documents.add(mapResultSetToDocument(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all documents: " + e.getMessage());
            e.printStackTrace();
        }
        return documents;
    }

    //Update - Update existing document
    public boolean update(BailCertificate document) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            setStatementParameters(stmt, document);
            stmt.setInt(15, document.getId()); // Set ID for WHERE clause

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating document: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //Delete - Remove document by ID
    public boolean delete(int id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting document: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //Search - Find documents by status
    public List<BailCertificate> findByStatus(String status) {
        List<BailCertificate> documents = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_STATUS_SQL)) {

            stmt.setString(1, status);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    documents.add(mapResultSetToDocument(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding documents by status: " + e.getMessage());
            e.printStackTrace();
        }
        return documents;
    }

    //Search - Find documents by name (partial match)
    public List<BailCertificate> findByName(String name) {
        List<BailCertificate> documents = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_NAME_SQL)) {

            stmt.setString(1, "%" + name + "%"); // For LIKE search

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    documents.add(mapResultSetToDocument(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding documents by name: " + e.getMessage());
            e.printStackTrace();
        }
        return documents;
    }

    //Find by certification number (unique lookup)
    public BailCertificate findByCertificationNumber(String certNumber) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_CERT_NUMBER_SQL)) {

            stmt.setString(1, certNumber);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDocument(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding document by cert number: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    //Helper method to set PreparedStatement parameters
    private static void setStatementParameters(PreparedStatement stmt, BailCertificate document) throws SQLException {
        stmt.setString(1, document.getCertificationNumber());
        stmt.setDate(2, Date.valueOf(document.getDateOfFiling()));
        stmt.setDate(3, document.getDateIssued() != null ? Date.valueOf(document.getDateIssued()) : null);
        stmt.setString(4, document.getDocumentStatus());
        stmt.setString(5, document.getFullName());
        stmt.setDate(6, Date.valueOf(document.getDateOfBirth()));
        stmt.setInt(7, document.getAge());
        stmt.setString(8, document.getCompleteAddress());
        stmt.setString(9, document.getContactNumber());
        stmt.setString(10, document.getNatureOfCase());
        stmt.setString(11, document.getCaseNumber());
        stmt.setString(12, document.getCourtPoliceStation());
        stmt.setDate(13, Date.valueOf(document.getDateBailPosted()));

        // Convert List<String> to JSON string for documents
        String documentsJson = null;
        if (document.getDocumentsToPresent() != null && !document.getDocumentsToPresent().isEmpty()) {
            documentsJson = document.getDocumentsToPresent().toString()
                    .replace("[", "[\"")
                    .replace("]", "\"]")
                    .replace(", ", "\", \"");
        }
        stmt.setString(14, documentsJson);
    }

    //Helper method to map ResultSet to BailCertificate object
    private BailCertificate mapResultSetToDocument(ResultSet rs) throws SQLException {
        BailCertificate document = new BailCertificate();

        document.setId(rs.getInt("id"));
        document.setCertificationNumber(rs.getString("certification_number"));

        // Handle dates properly
        Date dateOfFiling = rs.getDate("date_of_filing");
        if (dateOfFiling != null) {
            document.setDateOfFiling(dateOfFiling.toLocalDate());
        }

        Date dateIssued = rs.getDate("date_issued");
        if (dateIssued != null) {
            document.setDateIssued(dateIssued.toLocalDate());
        }

        document.setDocumentStatus(rs.getString("document_status"));
        document.setFullName(rs.getString("full_name"));

        Date dateOfBirth = rs.getDate("date_of_birth");
        if (dateOfBirth != null) {
            document.setDateOfBirth(dateOfBirth.toLocalDate());
        }

        document.setAge(rs.getInt("age"));
        document.setCompleteAddress(rs.getString("complete_address"));
        document.setContactNumber(rs.getString("contact_number"));
        document.setNatureOfCase(rs.getString("nature_of_case"));
        document.setCaseNumber(rs.getString("case_number"));
        document.setCourtPoliceStation(rs.getString("court_police_station"));

        Date dateBailPosted = rs.getDate("date_bail_posted");
        if (dateBailPosted != null) {
            document.setDateBailPosted(dateBailPosted.toLocalDate());
        }

        // Parse JSON documents back to List
        String documentsJson = rs.getString("documents_to_present");
        if (documentsJson != null && !documentsJson.trim().isEmpty()) {
            // Simple JSON parsing - remove brackets and quotes, split by comma
            String cleanJson = documentsJson.replace("[\"", "").replace("\"]", "").replace("\", \"", ",");
            List<String> documentsList = Arrays.asList(cleanJson.split(","));
            document.setDocumentsToPresent(documentsList);
        }

        // Handle timestamps
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            document.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            document.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return document;
    }
}