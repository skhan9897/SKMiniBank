package com.bank.dao;
 
import com.bank.model.Notification;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    
    
    
    public boolean addNotification(Notification n) {

    boolean status = false;

    try {

        con = DBConnection.getConnection();

        String sql =
        "INSERT INTO notification(customer_id,title,message,notification_type,status,is_read,action_url,created_at) VALUES(?,?,?,?,?,?,?,NOW())";

        ps = con.prepareStatement(sql);

        ps.setInt(1, n.getCustomerId());
        ps.setString(2, n.getTitle());
        ps.setString(3, n.getMessage());
        ps.setString(4, n.getNotificationType());
        ps.setString(5, n.getStatus());
        ps.setInt(6, n.getIsRead());
        ps.setString(7, n.getActionUrl());

        status = ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}
    public int getUnreadCount(int customerId) {

    int count = 0;

    try {

        con = DBConnection.getConnection();

        String sql =
        "SELECT COUNT(*) FROM notification WHERE customer_id=? AND is_read=0";

        ps = con.prepareStatement(sql);

        ps.setInt(1, customerId);

        rs = ps.executeQuery();

        if (rs.next()) {

            count = rs.getInt(1);

        }

    } catch (Exception e) {

        e.printStackTrace();

    }

    return count;
}
    public List<Notification> getNotificationsByCustomer(int customerId) {

    List<Notification> list = new ArrayList<>();

    try {

        con = DBConnection.getConnection();

        String sql =
        "SELECT * FROM notification WHERE customer_id=? ORDER BY created_at DESC";

        ps = con.prepareStatement(sql);
        ps.setInt(1, customerId);

        rs = ps.executeQuery();

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

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
    public boolean markAsRead(int notificationId) {

    boolean status = false;

    try {

        con = DBConnection.getConnection();

        String sql =
        "UPDATE notification SET is_read=1 WHERE notification_id=?";

        ps = con.prepareStatement(sql);
        ps.setInt(1, notificationId);

        status = ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}
    public boolean markAllAsRead(int customerId) {

    boolean status = false;

    try {

        con = DBConnection.getConnection();

        String sql =
        "UPDATE notification SET is_read=1 WHERE customer_id=?";

        ps = con.prepareStatement(sql);
        ps.setInt(1, customerId);

        status = ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}
}