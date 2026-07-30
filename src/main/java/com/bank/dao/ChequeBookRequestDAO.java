package com.bank.dao;

import com.bank.model.ChequeBookRequest;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChequeBookRequestDAO {

    public List<ChequeBookRequest> getAllPendingRequests() {
        List<ChequeBookRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM cheque_book_request WHERE status='PENDING' ORDER BY request_date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ChequeBookRequest r = new ChequeBookRequest();
                r.setId(rs.getInt("id"));
                r.setCustomerId(rs.getInt("customer_id"));
                r.setAccountNumber(rs.getString("account_number"));
                r.setCustomerName(rs.getString("customer_name"));
                r.setMobile(rs.getString("mobile"));
                r.setLeaves(rs.getInt("leaves"));
                r.setAddress(rs.getString("address"));
                r.setStatus(rs.getString("status"));
                r.setRemarks(rs.getString("remarks"));
                r.setApprovedBy(rs.getInt("approved_by"));
                r.setApprovalDate(rs.getTimestamp("approval_date"));
                r.setExpectedDelivery(rs.getDate("expected_delivery"));
                r.setDispatchedDate(rs.getDate("dispatched_date"));
                r.setDeliveredDate(rs.getDate("delivered_date"));
                r.setRequestDate(rs.getTimestamp("request_date"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean approveRequest(int id, int approvedBy, String remarks, java.sql.Date expectedDelivery) {
        String sql = "UPDATE cheque_book_request SET status=?, remarks=?, approved_by=?, approval_date=NOW(), expected_delivery=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "APPROVED");
            ps.setString(2, remarks);
            ps.setInt(3, approvedBy);
            ps.setDate(4, expectedDelivery);
            ps.setInt(5, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean rejectRequest(int id, int approvedBy, String remarks) {
        String sql = "UPDATE cheque_book_request SET status=?, remarks=?, approved_by=?, approval_date=NOW() WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "REJECTED");
            ps.setString(2, remarks);
            ps.setInt(3, approvedBy);
            ps.setInt(4, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveRequest(ChequeBookRequest request) {
        String sql = "INSERT INTO cheque_book_request (customer_id, account_number, customer_name, mobile, leaves, address, status, request_date, remarks) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, request.getCustomerId());
            ps.setString(2, request.getAccountNumber());
            ps.setString(3, request.getCustomerName());
            ps.setString(4, request.getMobile());
            ps.setInt(5, request.getLeaves());
            ps.setString(6, request.getAddress());
            ps.setString(7, request.getStatus());
            ps.setTimestamp(8, request.getRequestDate());
            ps.setString(9, request.getRemarks());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ChequeBookRequest> getRequestsByCustomerId(int customerId) {
        List<ChequeBookRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM cheque_book_request WHERE customer_id=? ORDER BY request_date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChequeBookRequest r = new ChequeBookRequest();
                    r.setId(rs.getInt("id"));
                    r.setCustomerId(rs.getInt("customer_id"));
                    r.setAccountNumber(rs.getString("account_number"));
                    r.setCustomerName(rs.getString("customer_name"));
                    r.setMobile(rs.getString("mobile"));
                    r.setLeaves(rs.getInt("leaves"));
                    r.setAddress(rs.getString("address"));
                    r.setStatus(rs.getString("status"));
                    r.setRemarks(rs.getString("remarks"));
                    r.setApprovedBy(rs.getInt("approved_by"));
                    r.setApprovalDate(rs.getTimestamp("approval_date"));
                    r.setExpectedDelivery(rs.getDate("expected_delivery"));
                    r.setDispatchedDate(rs.getDate("dispatched_date"));
                    r.setDeliveredDate(rs.getDate("delivered_date"));
                    r.setRequestDate(rs.getTimestamp("request_date"));
                    list.add(r);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean dispatchRequest(int id) {
        String sql = "UPDATE cheque_book_request SET dispatched_date=CURDATE() WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deliverRequest(int id) {
        String sql = "UPDATE cheque_book_request SET delivered_date=CURDATE() WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
