package com.bank.dao;

import com.bank.util.DBConnection;
import java.sql.*;

public class AdminBiometricDAO {

    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS admin_biometric_auth (" +
                     "auth_id VARCHAR(50) PRIMARY KEY, " +
                     "admin_id VARCHAR(50), " +
                     "status VARCHAR(20) DEFAULT 'PENDING', " +
                     "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean createAuthRequest(String authId, String adminId) {
        createTableIfNotExists();
        String sql = "INSERT INTO admin_biometric_auth (auth_id, admin_id, status) VALUES (?, ?, 'PENDING')";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, authId);
            ps.setString(2, adminId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String checkStatus(String authId) {
        String sql = "SELECT status FROM admin_biometric_auth WHERE auth_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, authId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "NOT_FOUND";
    }

    public boolean approveRequest(String authId) {
        String sql = "UPDATE admin_biometric_auth SET status = 'APPROVED' WHERE auth_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, authId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getPendingRequestForAdmin(String adminId) {
        // Simple logic: get latest pending request in last 2 minutes
        String sql = "SELECT auth_id FROM admin_biometric_auth WHERE admin_id = ? AND status = 'PENDING' " +
                     "AND created_at > NOW() - INTERVAL 2 MINUTE ORDER BY created_at DESC LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, adminId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("auth_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
