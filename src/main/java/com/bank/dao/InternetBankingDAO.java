package com.bank.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.bank.model.InternetBanking;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InternetBankingDAO {

    public boolean saveRequest(InternetBanking ib) {
        String sql = "INSERT INTO internet_banking(customer_id,account_number,customer_name,mobile,email,username,password,status) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ib.getCustomerId());
            ps.setString(2, ib.getAccountNumber());
            ps.setString(3, ib.getCustomerName());
            ps.setString(4, ib.getMobile());
            ps.setString(5, ib.getEmail());
            ps.setString(6, ib.getUsername());
            ps.setString(7, ib.getPassword());
            ps.setString(8, "Pending");
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<InternetBanking> getAllRequests() {
        List<InternetBanking> list = new ArrayList<>();
        String sql = "SELECT * FROM internet_banking ORDER BY ib_id DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                InternetBanking ib = new InternetBanking();
                ib.setIbId(rs.getInt("ib_id"));
                ib.setCustomerId(rs.getInt("customer_id"));
                ib.setAccountNumber(rs.getString("account_number"));
                ib.setCustomerName(rs.getString("customer_name"));
                ib.setMobile(rs.getString("mobile"));
                ib.setEmail(rs.getString("email"));
                ib.setUsername(rs.getString("username"));
                ib.setPassword(rs.getString("password"));
                ib.setStatus(rs.getString("status"));
                ib.setCreatedDate(rs.getTimestamp("created_date"));
                list.add(ib);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean approveRequest(int id) {
        String sql = "UPDATE internet_banking SET status='Approved' WHERE ib_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean rejectRequest(int id) {
        String sql = "UPDATE internet_banking SET status='Rejected' WHERE ib_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteRequest(int id) {
        String sql = "DELETE FROM internet_banking WHERE ib_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getTotalRequests() {
        String sql = "SELECT COUNT(*) FROM internet_banking";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getApprovedRequests() {
        String sql = "SELECT COUNT(*) FROM internet_banking WHERE status='Approved'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getPendingRequests() {
        String sql = "SELECT COUNT(*) FROM internet_banking WHERE status='Pending'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getRejectedRequests() {
        String sql = "SELECT COUNT(*) FROM internet_banking WHERE status='Rejected'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
