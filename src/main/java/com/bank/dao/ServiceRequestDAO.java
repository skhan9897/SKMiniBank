package com.bank.dao;

import com.bank.model.ServiceRequest;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ServiceRequestDAO {

    private Connection con;

    public ServiceRequestDAO() {
        con = DBConnection.getConnection();
    }

    // ================= SAVE REQUEST =================

    public boolean saveRequest(ServiceRequest request) {

        boolean status = false;

        try {

            String sql = "INSERT INTO service_request "
                    + "(customer_id, account_number, request_type, request_details, status, request_date) "
                    + "VALUES (?,?,?,?,?,NOW())";

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

    // ================= CUSTOMER REQUESTS =================

    public List<ServiceRequest> getCustomerRequests(int customerId) {

        List<ServiceRequest> list = new ArrayList<>();

        try {

            String sql = "SELECT * FROM service_request "
                    + "WHERE customer_id=? "
                    + "ORDER BY request_date DESC";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

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

                request.setExpectedDeliveryDate(
                        rs.getDate("expected_delivery_date"));

                request.setDispatchedDate(
                        rs.getDate("dispatched_date"));

                request.setDeliveredDate(
                        rs.getDate("delivered_date"));

                list.add(request);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    // ================= PENDING REQUESTS =================

public List<ServiceRequest> getPendingRequests() {

    List<ServiceRequest> list = new ArrayList<>();

    try {

        String sql = "SELECT * FROM service_request "
                + "WHERE status='PENDING' "
                + "ORDER BY request_date ASC";

        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

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

            list.add(request);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

// ================= REQUESTS BY TYPE =================

public List<ServiceRequest> getRequestsByType(String requestType) {

    List<ServiceRequest> list = new ArrayList<>();

    try {

        String sql = "SELECT * FROM service_request "
                + "WHERE request_type=? "
                + "ORDER BY request_date DESC";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, requestType);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

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

            list.add(request);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

// ================= NET BANKING REQUESTS =================

public List<ServiceRequest> getNetBankingRequests() {
    return getRequestsByType("NET_BANKING");
}

// ================= CUSTOMER REQUESTS ALIAS =================

public List<ServiceRequest> getRequestsByCustomerId(int customerId) {
    return getCustomerRequests(customerId);
}

// ================= APPROVE REQUEST =================

public boolean approveRequest(int requestId,
                              String approvedBy,
                              String remarks,
                              java.sql.Date expectedDeliveryDate) {

    boolean status = false;

    try {

        String sql = "UPDATE service_request "
                + "SET status=?, "
                + "remarks=?, "
                + "approved_by=?, "
                + "approval_date=NOW(), "
                + "expected_delivery_date=? "
                + "WHERE request_id=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, "APPROVED");
        ps.setString(2, remarks);
        ps.setString(3, approvedBy);
        ps.setDate(4, expectedDeliveryDate);
        ps.setInt(5, requestId);

        status = ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}

// ================= REJECT REQUEST =================

public boolean rejectRequest(int requestId,
                             String approvedBy,
                             String remarks) {

    boolean status = false;

    try {

        String sql = "UPDATE service_request "
                + "SET status=?, "
                + "remarks=?, "
                + "approved_by=?, "
                + "approval_date=NOW() "
                + "WHERE request_id=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, "REJECTED");
        ps.setString(2, remarks);
        ps.setString(3, approvedBy);
        ps.setInt(4, requestId);

        status = ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}

// ================= DISPATCH REQUEST =================


// ================= DELIVER REQUEST =================

public boolean deliverRequest(int requestId) {

    boolean status = false;

    try {

        String sql = "UPDATE service_request "
                   + "SET status='DELIVERED', "
                   + "delivered_date=NOW() "
                   + "WHERE request_id=?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, requestId);

        status = ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}


public boolean dispatchRequest(int requestId) {

    boolean status = false;

    try {

        String sql = "UPDATE service_request "
                   + "SET status='DISPATCHED', "
                   + "dispatched_date=NOW() "
                   + "WHERE request_id=?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, requestId);

        status = ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}
public ServiceRequest getLatestRequestByType(int customerId, String requestType) {

    ServiceRequest request = null;

    try {

        String sql = "SELECT * FROM service_request "
                   + "WHERE customer_id=? AND request_type=? "
                   + "ORDER BY request_date DESC LIMIT 1";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, customerId);
        ps.setString(2, requestType);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            request = new ServiceRequest();

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
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return request;
}

public ServiceRequest getLatestATMRequest(int customerId) {

    ServiceRequest request = null;

    try {

        String sql = "SELECT * FROM service_request "
                   + "WHERE customer_id=? AND request_type='ATM_CARD' "
                   + "ORDER BY request_date DESC LIMIT 1";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, customerId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            request = new ServiceRequest();

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

            rs.close();
            ps.close();
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return request;
}

}