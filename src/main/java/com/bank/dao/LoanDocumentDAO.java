package com.bank.dao;

import com.bank.model.LoanDocument;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoanDocumentDAO{
    
    public boolean saveDocuments(LoanDocument doc) {
        String sql = "INSERT INTO loan_documents (loan_id,customer_id,account_number,aadhaar_file,pan_file,salary_slip_file,bank_statement_file,address_proof_file,verification_status,remarks) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, doc.getLoanId());
            ps.setInt(2, doc.getCustomerId());
            ps.setString(3, doc.getAccountNumber());
            ps.setString(4, doc.getAadhaarFile());
            ps.setString(5, doc.getPanFile());
            ps.setString(6, doc.getSalarySlipFile());
            ps.setString(7, doc.getBankStatementFile());
            ps.setString(8, doc.getAddressProofFile());
            ps.setString(9, "Pending");
            ps.setString(10, doc.getRemarks());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public LoanDocument getDocumentsByLoanId(int loanId) {
        String sql = "SELECT * FROM loan_documents WHERE loan_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, loanId);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()){
                    LoanDocument doc = new LoanDocument();
                    doc.setDocumentId(rs.getInt("document_id"));
                    doc.setLoanId(rs.getInt("loan_id"));
                    doc.setCustomerId(rs.getInt("customer_id"));
                    doc.setAccountNumber(rs.getString("account_number"));
                    doc.setAadhaarFile(rs.getString("aadhaar_file"));
                    doc.setPanFile(rs.getString("pan_file"));
                    doc.setSalarySlipFile(rs.getString("salary_slip_file"));
                    doc.setBankStatementFile(rs.getString("bank_statement_file"));
                    doc.setAddressProofFile(rs.getString("address_proof_file"));
                    doc.setVerificationStatus(rs.getString("verification_status"));
                    doc.setVerifiedBy(rs.getString("verified_by"));
                    doc.setRemarks(rs.getString("remarks"));
                    doc.setUploadDate(rs.getTimestamp("upload_date"));
                    doc.setVerifiedDate(rs.getTimestamp("verified_date"));
                    return doc;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean verifyDocuments(int loanId, String verifiedBy) {
        String sql = "UPDATE loan_documents SET verification_status='Verified', verified_by=?, verified_date=NOW() WHERE loan_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, verifiedBy);
            ps.setInt(2, loanId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean rejectDocuments(int loanId, String verifiedBy) {
        String sql = "UPDATE loan_documents SET verification_status='Rejected', verified_by=?, verified_date=NOW() WHERE loan_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, verifiedBy);
            ps.setInt(2, loanId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
