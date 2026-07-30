package com.bank.dao;
 
import com.bank.model.Notification;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    public boolean addNotification(Notification n) {
        String sql = "INSERT INTO notification "
                + "(customer_id, title, message, notification_type, status, is_read, action_url, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, n.getCustomerId());
            ps.setString(2, n.getTitle());
            ps.setString(3, n.getMessage());
            ps.setString(4, n.getNotificationType());
            ps.setString(5, n.getStatus());
            ps.setInt(6, n.getIsRead());
            ps.setString(7, n.getActionUrl());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getUnreadCount(int customerId) {
        String sql = "SELECT COUNT(*) FROM notification WHERE customer_id=? AND is_read=0";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Notification> getNotificationsByCustomer(int customerId) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notification WHERE customer_id=? ORDER BY created_at DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Notification n = new Notification();
                    n.setNotificationId(rs.getInt("notification_id"));
                    n.setCustomerId(rs.getInt("customer_id"));
                    n.setTitle(rs.getString("title"));
                    n.setMessage(rs.getString("message"));
                    n.setNotificationType(rs.getString("notification_type"));
                    n.setStatus(rs.getString("status"));
                    n.setIsRead(rs.getInt("is_read"));
                    n.setActionUrl(rs.getString("action_url"));
                    n.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(n);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean markAsRead(int notificationId) {
        String sql = "UPDATE notification SET is_read=1 WHERE notification_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, notificationId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean markAllAsRead(int customerId) {
        String sql = "UPDATE notification SET is_read=1 WHERE customer_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Notification> getNotifications(int customerId) {
        return getNotificationsByCustomer(customerId);
    }
    
    public boolean saveUpiTransaction(String accountNumber, String customerName, String type, double amount, double balance) {
        String sql = "INSERT INTO transactions(account_number,customer_name,transaction_type,amount,balance,transaction_date,status) VALUES(?,?,?,?,?,NOW(),?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            ps.setString(2, customerName);
            ps.setString(3, type);
            ps.setDouble(4, amount);
            ps.setDouble(5, balance);
            ps.setString(6, "SUCCESS");
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveNotification(int customerId, String title, String message) {
        Notification n = new Notification();
        n.setCustomerId(customerId);
        n.setTitle(title);
        n.setMessage(message);
        n.setNotificationType("UPI");
        n.setStatus("SUCCESS");
        n.setIsRead(0);
        n.setActionUrl("/customer/notification.jsp");
        return addNotification(n);
    }
}
