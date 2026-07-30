package com.bank.dao;

import com.bank.model.ATMRequest;
import com.bank.util.DBConnection;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ATMRequestDAO {

    public boolean applyATM(ATMRequest atm) {
        String sql = "INSERT INTO atm_request(customer_id, card_type, request_date, status) VALUES(?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, atm.getCustomerId());
            ps.setString(2, atm.getCardType());
            ps.setString(3, atm.getRequestDate());
            ps.setString(4, atm.getStatus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean approveATMRequest(int requestId) {
        String sql = "UPDATE atm_request SET status='APPROVED' WHERE request_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, requestId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean rejectATMRequest(int requestId) {
        String sql = "UPDATE atm_request SET status='REJECTED' WHERE request_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, requestId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ATMRequest getATMStatus(int customerId) {
        ATMRequest atm = null;
        String sql = "SELECT * FROM atm_request WHERE customer_id=? ORDER BY request_id DESC LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    atm = new ATMRequest();
                    atm.setRequestId(rs.getInt("request_id"));
                    atm.setCustomerId(rs.getInt("customer_id"));
                    atm.setAccountNumber(rs.getString("account_number"));
                    atm.setCardType(rs.getString("card_type"));
                    atm.setRequestDate(rs.getString("request_date"));
                    atm.setStatus(rs.getString("status"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return atm;
    }
}
