package com.bank.dao;

import com.bank.model.KYCRequest;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class KYCRequestDAO {

    private Connection con;

    public KYCRequestDAO() {
        con = DBConnection.getConnection();
    }

    // ==========================
    // Submit KYC
    // ==========================
    public boolean submitKYC(KYCRequest kyc) {

        boolean status = false;

        try {

            String sql = "INSERT INTO kyc_request("
                    + "customer_id,"
                    + "account_number,"
                    + "aadhaar_number,"
                    + "pan_number,"
                    + "aadhaar_front,"
                    + "aadhaar_back,"
                    + "pan_image,"
                    + "customer_photo,"
                    + "signature_image,"
                    + "status)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,'PENDING')";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, kyc.getCustomerId());
            ps.setString(2, kyc.getAccountNumber());
            ps.setString(3, kyc.getAadhaarNumber());
            ps.setString(4, kyc.getPanNumber());
            ps.setString(5, kyc.getAadhaarFront());
            ps.setString(6, kyc.getAadhaarBack());
            ps.setString(7, kyc.getPanImage());
            ps.setString(8, kyc.getCustomerPhoto());
            ps.setString(9, kyc.getSignatureImage());

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    // ==========================
    // Get KYC By Customer
    // ==========================
    public KYCRequest getKYCByCustomerId(int customerId) {

        KYCRequest kyc = null;

        try {

            String sql = "SELECT * FROM kyc_request "
                    + "WHERE customer_id=? "
                    + "ORDER BY request_date DESC LIMIT 1";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                kyc = new KYCRequest();

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
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return kyc;
    }
    // ==========================
// Get All KYC Requests
// ==========================
public List<KYCRequest> getAllKYCRequests() {

    List<KYCRequest> list = new ArrayList<>();

    try {

        String sql = "SELECT * FROM kyc_request ORDER BY request_date DESC";

        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

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

            list.add(kyc);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

// ==========================
// Approve KYC
// ==========================
public boolean approveKYC(int kycId,
                          String verifiedBy,
                          String remarks) {

    boolean status = false;

    try {

        String sql = "UPDATE kyc_request SET "
                + "status='VERIFIED',"
                + "verified_by=?,"
                + "remarks=?,"
                + "verification_date=NOW() "
                + "WHERE kyc_id=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, verifiedBy);
        ps.setString(2, remarks);
        ps.setInt(3, kycId);

        status = ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}

// ==========================
// Reject KYC
// ==========================
public boolean rejectKYC(int kycId,
                         String verifiedBy,
                         String remarks) {

    boolean status = false;

    try {

        String sql = "UPDATE kyc_request SET "
                + "status='REJECTED',"
                + "verified_by=?,"
                + "remarks=?,"
                + "verification_date=NOW() "
                + "WHERE kyc_id=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, verifiedBy);
        ps.setString(2, remarks);
        ps.setInt(3, kycId);

        status = ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}
}