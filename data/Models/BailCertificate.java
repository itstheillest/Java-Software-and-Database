package data.Models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Model class representing the document_information table
 * Contains all fields from your database table
 */
public class BailCertificate {

    // Primary key
    private int id;

    // Document Information Fields
    private String certificationNumber;
    private LocalDate dateOfFiling;
    private LocalDate dateIssued;
    private String documentStatus;

    // Personal Information Fields
    private String fullName;
    private LocalDate dateOfBirth;
    private int age;
    private String completeAddress;
    private String contactNumber;

    // Specific Document Information Fields (Bail Certificate)
    private String natureOfCase;
    private String caseNumber;
    private String courtPoliceStation;
    private LocalDate dateBailPosted;

    // Documents to Present
    private List<String> documentsToPresent;

    // Audit fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Default Constructor
    public BailCertificate() {}

    // Constructor with essential fields
    public BailCertificate(String certificationNumber, LocalDate dateOfFiling, LocalDate dateIssued,
                               String documentStatus, String fullName, LocalDate dateOfBirth,
                               int age, String completeAddress, String contactNumber,
                               String natureOfCase, String caseNumber, String courtPoliceStation,
                               LocalDate dateBailPosted) {
        this.certificationNumber = certificationNumber;
        this.dateOfFiling = dateOfFiling;
        this.dateIssued = dateIssued;
        this.documentStatus = documentStatus;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.completeAddress = completeAddress;
        this.contactNumber = contactNumber;
        this.natureOfCase = natureOfCase;
        this.caseNumber = caseNumber;
        this.courtPoliceStation = courtPoliceStation;
        this.dateBailPosted = dateBailPosted;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCertificationNumber() {
        return certificationNumber;
    }

    public void setCertificationNumber(String certificationNumber) {
        this.certificationNumber = certificationNumber;
    }

    public LocalDate getDateOfFiling() {
        return dateOfFiling;
    }

    public void setDateOfFiling(LocalDate dateOfFiling) {
        this.dateOfFiling = dateOfFiling;
    }

    public LocalDate getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(LocalDate dateIssued) {
        this.dateIssued = dateIssued;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCompleteAddress() {
        return completeAddress;
    }

    public void setCompleteAddress(String completeAddress) {
        this.completeAddress = completeAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getNatureOfCase() {
        return natureOfCase;
    }

    public void setNatureOfCase(String natureOfCase) {
        this.natureOfCase = natureOfCase;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getCourtPoliceStation() {
        return courtPoliceStation;
    }

    public void setCourtPoliceStation(String courtPoliceStation) {
        this.courtPoliceStation = courtPoliceStation;
    }

    public LocalDate getDateBailPosted() {
        return dateBailPosted;
    }

    public void setDateBailPosted(LocalDate dateBailPosted) {
        this.dateBailPosted = dateBailPosted;
    }

    public List<String> getDocumentsToPresent() {
        return documentsToPresent;
    }

    public void setDocumentsToPresent(List<String> documentsToPresent) {
        this.documentsToPresent = documentsToPresent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Utility method to get documents as comma-separated string
    public String getDocumentsAsString() {
        if (documentsToPresent == null || documentsToPresent.isEmpty()) {
            return "";
        }
        return String.join(", ", documentsToPresent);
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "DocumentInformation{" +
                "id=" + id +
                ", certificationNumber='" + certificationNumber + '\'' +
                ", fullName='" + fullName + '\'' +
                ", documentStatus='" + documentStatus + '\'' +
                ", natureOfCase='" + natureOfCase + '\'' +
                '}';
    }
}