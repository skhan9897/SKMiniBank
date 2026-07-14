package com.bank.model;

public class LoanDocument {

    private int documentId;
    private int loanId;
    private int customerId;
    private String accountNumber;

    private String aadhaarFile;
    private String panFile;
    private String salarySlipFile;
    private String bankStatementFile;
    private String addressProofFile;

    private String verificationStatus;
    private String verifiedBy;
    private String remarks;

    private java.sql.Timestamp uploadDate;
    private java.sql.Timestamp verifiedDate;

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAadhaarFile() {
        return aadhaarFile;
    }

    public void setAadhaarFile(String aadhaarFile) {
        this.aadhaarFile = aadhaarFile;
    }

    public String getPanFile() {
        return panFile;
    }

    public void setPanFile(String panFile) {
        this.panFile = panFile;
    }

    public String getSalarySlipFile() {
        return salarySlipFile;
    }

    public void setSalarySlipFile(String salarySlipFile) {
        this.salarySlipFile = salarySlipFile;
    }

    public String getBankStatementFile() {
        return bankStatementFile;
    }

    public void setBankStatementFile(String bankStatementFile) {
        this.bankStatementFile = bankStatementFile;
    }

    public String getAddressProofFile() {
        return addressProofFile;
    }

    public void setAddressProofFile(String addressProofFile) {
        this.addressProofFile = addressProofFile;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public java.sql.Timestamp getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(java.sql.Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }

    public java.sql.Timestamp getVerifiedDate() {
        return verifiedDate;
    }

    public void setVerifiedDate(java.sql.Timestamp verifiedDate) {
        this.verifiedDate = verifiedDate;
    }
}