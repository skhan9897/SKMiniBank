package com.bank.dao;

import com.bank.model.KYCRequest;
import com.bank.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KYCRequestDAO {

    public boolean submitKYC(KYCRequest kyc) {
        String sql = "INSERT INTO kyc_request (customer_id, account_number, aadhaar_number, pan_number, aadhaar_front, aadhaar_back, pan_image, customer_photo, signature_image, status, request_date) VALUES (?,?,?,?,?,?,?,?,?,?,NOW())";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, kyc.getCustomerId());
            ps.setString(2, kyc.getAccountNumber());
            ps.setString(3, kyc.getAadhaarNumber());
            ps.setString(4, kyc.getPanNumber());
            ps.setString(5, kyc.getAadhaarFront());
            ps.setString(6, kyc.getAadhaarBack());
            ps.setString(7, kyc.getPanImage());
            ps.setString(8, kyc.getCustomerPhoto());
            ps.setString(9, kyc.getSignatureImage());
            ps.setString(10, "PENDING");
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public KYCRequest getKYCByCustomerId(int customerId) {
        String sql = "SELECT * FROM kyc_request WHERE customer_id=? ORDER BY request_date DESC LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<KYCRequest> getAllKYCRequests() {
        List<KYCRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM kyc_request ORDER BY request_date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean approveKYC(int kycId, String verifiedBy, String remarks) {
        String sql = "UPDATE kyc_request SET status='VERIFIED', verified_by=?, remarks=?, verification_date=NOW() WHERE kyc_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, verifiedBy);
            ps.setString(2, remarks);
            ps.setInt(3, kycId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean rejectKYC(int kycId, String verifiedBy, String remarks) {
        String sql = "UPDATE kyc_request SET status='REJECTED', verified_by=?, remarks=?, verification_date=NOW() WHERE kyc_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, verifiedBy);
            ps.setString(2, remarks);
            ps.setInt(3, kycId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public KYCRequest getKYCById(int kycId) {
        String sql = "SELECT * FROM kyc_request WHERE kyc_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, kycId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private KYCRequest mapRow(ResultSet rs) throws Exception {
        KYCRequest kyc = new KYCRequest();
        kyc.setKycId(rs.getInt("kyc_id"));
        kyc.setCustomerId(rs.getInt("customer_id"));
        kyc.setAccountNumber(rs.getString("account_number"));
        kyc.setAadhaarNumber(rs.getString("aadhaar_number"));
        kyc.setPanNumber(rs.getString("pan_number"));
        kyc.setAadhaarFront(rs.getString("aadhaar_front"));
        kyc.setAadhaarBack(rs.getString("aadhaar_back"));
        kyc.setPanImage(rs.getString("pan_image"));
        kyc.setCustomerPhoto(rs.getString("customer_photo"));
        kyc.setSignatureImage(rs.getString("signature_image"));
        kyc.setStatus(rs.getString("status"));
        kyc.setRemarks(rs.getString("remarks"));
        kyc.setVerifiedBy(rs.getString("verified_by"));
        kyc.setRequestDate(rs.getTimestamp("request_date"));
        kyc.setVerificationDate(rs.getTimestamp("verification_date"));
        return kyc;
    }
}
