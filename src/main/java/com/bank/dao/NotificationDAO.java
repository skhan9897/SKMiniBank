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

        String sql = "INSERT INTO notification "
                + "(customer_id, title, message, notification_type, status, is_read, action_url, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";

        ps = con.prepareStatement(sql);

        ps.setInt(1, n.getCustomerId());
        ps.setString(2, n.getTitle());
        ps.setString(3, n.getMessage());
        ps.setString(4, n.getNotificationType());
        ps.setString(5, n.getStatus());
        ps.setInt(6, n.getIsRead());
        ps.setString(7, n.getActionUrl());

        System.out.println("========== Notification Debug ==========");
        System.out.println("Customer ID : " + n.getCustomerId());
        System.out.println("Title       : " + n.getTitle());
        System.out.println("Message     : " + n.getMessage());
        System.out.println("Type        : " + n.getNotificationType());

        int rows = ps.executeUpdate();

        System.out.println("Rows Inserted : " + rows);

        if (rows > 0) {
            status = true;
            System.out.println("Notification Saved Successfully");
        } else {
            System.out.println("Notification Not Saved");
        }

    } catch (Exception e) {
        System.out.println("Notification Error:");
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
   public List<Notification> getNotifications(int customerId) {
    return getNotificationsByCustomer(customerId);
}
    
  
  public boolean saveUpiTransaction(String accountNumber,
                                  String customerName,
                                  String type,
                                  double amount,
                                  double balance) {

    boolean status = false;

    Connection con = null;
    PreparedStatement ps = null;

    try {

        con = DBConnection.getConnection();

        String sql =
            "INSERT INTO transactions(account_number,customer_name,transaction_type,amount,balance,transaction_date,status) VALUES(?,?,?,?,?,NOW(),?)";

        ps = con.prepareStatement(sql);

        ps.setString(1, accountNumber);
        ps.setString(2, customerName);
        ps.setString(3, type);
        ps.setDouble(4, amount);
        ps.setDouble(5, balance);
        ps.setString(6, "SUCCESS");

        status = ps.executeUpdate() > 0;

    } catch (Exception e) {

        e.printStackTrace();

    } finally {

        try {
            if (ps != null) ps.close();
        } catch (Exception e) {}

        try {
            if (con != null) con.close();
        } catch (Exception e) {}
    }

    return status;
}
  public boolean saveNotification(int customerId,
                                String title,
                                String message) {

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