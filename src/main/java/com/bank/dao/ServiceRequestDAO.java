package com.bank.dao;

import com.bank.model.ServiceRequest;
import com.bank.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceRequestDAO {

    public boolean saveRequest(ServiceRequest request) {
        String sql = "INSERT INTO service_request (customer_id, account_number, request_type, request_details, status, request_date) VALUES (?,?,?,?,?,NOW())";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, request.getCustomerId());
            ps.setString(2, request.getAccountNumber());
            ps.setString(3, request.getRequestType());
            ps.setString(4, request.getRequestDetails());
            ps.setString(5, "PENDING");

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ServiceRequest> getCustomerRequests(int customerId) {
        List<ServiceRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM service_request WHERE customer_id=? ORDER BY request_date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ServiceRequest> getRequestsByCustomerId(int customerId) {
        return getCustomerRequests(customerId);
    }

    public List<ServiceRequest> getPendingRequests() {
        List<ServiceRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM service_request WHERE status='PENDING' ORDER BY request_date ASC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ServiceRequest> getAllRequests() {
        List<ServiceRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM service_request ORDER BY request_date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ServiceRequest> getRequestsByType(String requestType) {
        List<ServiceRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM service_request WHERE UPPER(request_type)=UPPER(?) ORDER BY request_date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, requestType);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<ServiceRequest> getNetBankingRequests() {
        return getRequestsByType("NET_BANKING");
    }

    public boolean approveRequest(int requestId, String approvedBy, String remarks, java.sql.Date expectedDeliveryDate) {
        System.out.println("LOG: Attempting to Approve Request ID: " + requestId);
        
        // Strategy 1: Full Update
        String sqlFull = "UPDATE service_request SET status='APPROVED', remarks=?, approved_by=?, approval_date=NOW(), expected_delivery_date=? WHERE request_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlFull)) {
            ps.setString(1, remarks);
            ps.setString(2, approvedBy);
            ps.setDate(3, expectedDeliveryDate);
            ps.setInt(4, requestId);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("LOG: Full Approval SUCCESS for ID: " + requestId);
                return true;
            }
        } catch (Exception e) {
            System.err.println("LOG: Full approval failed (likely column missing): " + e.getMessage());
        }

        // Strategy 2: Minimal Update (Resilient)
        String sqlBasic = "UPDATE service_request SET status='APPROVED' WHERE request_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlBasic)) {
            ps.setInt(1, requestId);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("LOG: Basic Fallback Approval SUCCESS for ID: " + requestId);
                return true;
            }
        } catch (Exception e) {
            System.err.println("LOG: Basic approval ALSO failed: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean rejectRequest(int requestId, String approvedBy, String remarks) {
        System.out.println("LOG: Attempting to Reject Request ID: " + requestId);
        
        // Strategy 1: Full Update
        String sqlFull = "UPDATE service_request SET status='REJECTED', remarks=?, approved_by=?, approval_date=NOW() WHERE request_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlFull)) {
            ps.setString(1, remarks);
            ps.setString(2, approvedBy);
            ps.setInt(3, requestId);
            int rows = ps.executeUpdate();
            if (rows > 0) return true;
        } catch (Exception e) {
            System.err.println("LOG: Full rejection failed: " + e.getMessage());
        }
        
        // Strategy 2: Minimal Update
        String sqlBasic = "UPDATE service_request SET status='REJECTED' WHERE request_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlBasic)) {
            ps.setInt(1, requestId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deliverRequest(int requestId) {
        String sql = "UPDATE service_request SET status='DELIVERED', delivered_date=NOW() WHERE request_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, requestId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean dispatchRequest(int requestId) {
        String sql = "UPDATE service_request SET status='DISPATCHED', dispatched_date=NOW() WHERE request_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, requestId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ServiceRequest getLatestRequestByType(int customerId, String requestType) {
        String sql = "SELECT * FROM service_request WHERE customer_id=? AND request_type=? ORDER BY request_date DESC LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.setString(2, requestType);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ServiceRequest getLatestATMRequest(int customerId) {
        return getLatestRequestByType(customerId, "ATM_CARD");
    }

    public boolean updateRequestStatus(int requestId, String status, String remarks, String approvedBy) {
        System.out.println("LOG: Updating Request Status - ID: " + requestId + ", Status: " + status);
        
        // Strategy 1: Full Update
        String sqlFull = "UPDATE service_request SET status=?, remarks=?, approved_by=?, approval_date=NOW() WHERE request_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlFull)) {
            ps.setString(1, status);
            ps.setString(2, remarks);
            ps.setString(3, approvedBy);
            ps.setInt(4, requestId);
            int rows = ps.executeUpdate();
            if (rows > 0) return true;
        } catch (Exception e) {
            System.err.println("LOG: Full Status Update Failed: " + e.getMessage());
        }

        // Strategy 2: Minimal Update (Fallback)
        String sqlBasic = "UPDATE service_request SET status=? WHERE request_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlBasic)) {
            ps.setString(1, status);
            ps.setInt(2, requestId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean disburseLoan(int requestId, String accountNumber, double amount, String remarks, String adminName) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            // 1. Update Request Status (Be very specific with table name and columns)
            String sqlReq = "UPDATE service_request SET status='DISBURSED', remarks=?, approved_by=? WHERE request_id=?";
            try (PreparedStatement ps = con.prepareStatement(sqlReq)) {
                ps.setString(1, remarks);
                ps.setString(2, adminName);
                ps.setInt(3, requestId);
                ps.executeUpdate();
            }

            // 2. Update Customer Balance (Check if balance column name is correct)
            String sqlBal = "UPDATE customer SET balance = balance + ? WHERE account_number=?";
            try (PreparedStatement ps = con.prepareStatement(sqlBal)) {
                ps.setDouble(1, amount);
                ps.setString(2, accountNumber.trim());
                int rows = ps.executeUpdate();
                if (rows == 0) {
                    throw new Exception("Account " + accountNumber + " not found in customer table.");
                }
            }

            // 3. Log Transaction
            double newBalance = 0;
            String customerName = "Loan Customer";
            try (PreparedStatement ps = con.prepareStatement("SELECT full_name, balance FROM customer WHERE account_number=?")) {
                ps.setString(1, accountNumber.trim());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        customerName = rs.getString("full_name");
                        newBalance = rs.getDouble("balance");
                    }
                }
            }

            String sqlTxn = "INSERT INTO transactions(account_number, customer_name, transaction_type, amount, balance, description, transaction_date, status) " +
                            "VALUES (?, ?, 'LOAN_DISBURSED', ?, ?, ?, NOW(), 'SUCCESS')";
            try (PreparedStatement ps = con.prepareStatement(sqlTxn)) {
                ps.setString(1, accountNumber.trim());
                ps.setString(2, customerName);
                ps.setDouble(3, amount);
                ps.setDouble(4, newBalance);
                ps.setString(5, "Loan Disbursed - Ref #" + requestId + " (" + remarks + ")");
                ps.executeUpdate();
            }

            con.commit();
            return true;
        } catch (Exception e) {
            System.err.println("CRITICAL: Loan Disbursement Failed for ID " + requestId + ": " + e.getMessage());
            if (con != null) try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            return false;
        } finally {
            if (con != null) try { con.close(); } catch (SQLException ignored) {}
        }
    }

    public boolean verifyKYC(int requestId, int customerId, String submittedAadhaar, String submittedPan, String adminName) {
        String sqlCheck = "SELECT aadhaar, pan FROM customer WHERE customer_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement psCheck = con.prepareStatement(sqlCheck)) {
            
            psCheck.setInt(1, customerId);
            try (ResultSet rs = psCheck.executeQuery()) {
                if (rs.next()) {
                    String dbAadhaar = rs.getString("aadhaar");
                    String dbPan = rs.getString("pan");
                    
                    // Validation: Must match exactly
                    if (dbAadhaar.equals(submittedAadhaar) && dbPan.equalsIgnoreCase(submittedPan)) {
                        // Match found - Approve KYC
                        con.setAutoCommit(false);
                        try {
                            // 1. Update Request Status
                            String sqlReq = "UPDATE service_request SET status='APPROVED', remarks='KYC Details Verified Matches Records', approved_by=?, approval_date=NOW() WHERE request_id=?";
                            try (PreparedStatement psReq = con.prepareStatement(sqlReq)) {
                                psReq.setString(1, adminName);
                                psReq.setInt(2, requestId);
                                psReq.executeUpdate();
                            }
                            
                            // 2. Update Customer KYC Status
                            String sqlCust = "UPDATE customer SET kyc_status='VERIFIED' WHERE customer_id=?";
                            try (PreparedStatement psCust = con.prepareStatement(sqlCust)) {
                                psCust.setInt(1, customerId);
                                psCust.executeUpdate();
                            }
                            
                            con.commit();
                            return true;
                        } catch (Exception e) {
                            con.rollback();
                            throw e;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private ServiceRequest mapRow(ResultSet rs) {
        ServiceRequest request = new ServiceRequest();
        try { request.setRequestId(rs.getInt("request_id")); } catch (Exception e) {}
        try { request.setCustomerId(rs.getInt("customer_id")); } catch (Exception e) {}
        try { request.setAccountNumber(rs.getString("account_number")); } catch (Exception e) {}
        try { request.setRequestType(rs.getString("request_type")); } catch (Exception e) {}
        try { request.setRequestDetails(rs.getString("request_details")); } catch (Exception e) {}
        try { request.setStatus(rs.getString("status")); } catch (Exception e) {}
        try { request.setRemarks(rs.getString("remarks")); } catch (Exception e) {}
        try { request.setApprovedBy(rs.getString("approved_by")); } catch (Exception e) {}
        try { request.setRequestDate(rs.getTimestamp("request_date")); } catch (Exception e) {}
        try { request.setApprovalDate(rs.getTimestamp("approval_date")); } catch (Exception e) {}
        try { request.setExpectedDeliveryDate(rs.getDate("expected_delivery_date")); } catch (Exception e) {}
        try { request.setDispatchedDate(rs.getDate("dispatched_date")); } catch (Exception e) {}
        try { request.setDeliveredDate(rs.getDate("delivered_date")); } catch (Exception e) {}
        return request;
    }
}
