package com.bank.dao;

import com.bank.model.ServiceRequest;
import com.bank.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceRequestDAO {

    public boolean saveRequest(ServiceRequest request) {
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO service_request (customer_id, account_number, request_type, request_details, status, request_date) VALUES (?,?,?,?,?,NOW())";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, request.getCustomerId());
            ps.setString(2, request.getAccountNumber());
            ps.setString(3, request.getRequestType());
            ps.setString(4, request.getRequestDetails());
            ps.setString(5, "PENDING");
            status = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public List<ServiceRequest> getCustomerRequests(int customerId) {
        List<ServiceRequest> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM service_request WHERE customer_id=? ORDER BY request_date DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ServiceRequest> getPendingRequests() {
        List<ServiceRequest> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM service_request WHERE status='PENDING' ORDER BY request_date ASC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
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
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM service_request WHERE UPPER(request_type)=UPPER(?) ORDER BY request_date DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, requestType);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ServiceRequest> getNetBankingRequests() {
        return getRequestsByType("NET_BANKING");
    }

    public List<ServiceRequest> getRequestsByCustomerId(int customerId) {
        return getCustomerRequests(customerId);
    }

    public boolean approveRequest(int requestId, String approvedBy, String remarks, java.sql.Date expectedDeliveryDate) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE service_request SET status='APPROVED', remarks=?, approved_by=?, approval_date=NOW(), expected_delivery_date=? WHERE request_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, remarks);
            ps.setString(2, approvedBy);
            ps.setDate(3, expectedDeliveryDate);
            ps.setInt(4, requestId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean rejectRequest(int requestId, String approvedBy, String remarks) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE service_request SET status='REJECTED', remarks=?, approved_by=?, approval_date=NOW() WHERE request_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, remarks);
            ps.setString(2, approvedBy);
            ps.setInt(3, requestId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deliverRequest(int requestId) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE service_request SET status='DELIVERED', delivered_date=NOW() WHERE request_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, requestId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean dispatchRequest(int requestId) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE service_request SET status='DISPATCHED', dispatched_date=NOW() WHERE request_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, requestId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ServiceRequest getLatestRequestByType(int customerId, String requestType) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM service_request WHERE customer_id=? AND request_type=? ORDER BY request_date DESC LIMIT 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.setString(2, requestType);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ServiceRequest getLatestATMRequest(int customerId) {
        return getLatestRequestByType(customerId, "ATM_CARD");
    }

    private ServiceRequest mapRow(ResultSet rs) throws Exception {
        ServiceRequest request = new ServiceRequest();
        request.setRequestId(rs.getInt("request_id"));
        request.setCustomerId(rs.getInt("customer_id"));
        request.setAccountNumber(rs.getString("account_number"));
        request.setRequestType(rs.getString("request_type"));
        request.setRequestDetails(rs.getString("request_details"));
        request.setStatus(rs.getString("status"));
        request.setRemarks(rs.getString("remarks"));
        request.setApprovedBy(rs.getString("approved_by"));
        request.setRequestDate(rs.getTimestamp("request_date"));
        request.setApprovalDate(rs.getTimestamp("approval_date"));
        request.setExpectedDeliveryDate(rs.getDate("expected_delivery_date"));
        request.setDispatchedDate(rs.getDate("dispatched_date"));
        request.setDeliveredDate(rs.getDate("delivered_date"));
        return request;
    }
}
