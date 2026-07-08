



package com.bank.dao;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.bank.model.InternetBanking;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class InternetBankingDAO {

    // Save Internet Banking Request
    public boolean saveRequest(InternetBanking ib) {

        boolean status = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO internet_banking(customer_id,account_number,customer_name,mobile,email,username,password,status) VALUES(?,?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, ib.getCustomerId());
            ps.setString(2, ib.getAccountNumber());
            ps.setString(3, ib.getCustomerName());
            ps.setString(4, ib.getMobile());
            ps.setString(5, ib.getEmail());
            ps.setString(6, ib.getUsername());
            ps.setString(7, ib.getPassword());
            ps.setString(8, "Pending");

            status = ps.executeUpdate() > 0;

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
    
    // Get All Internet Banking Requests
public List<InternetBanking> getAllRequests() {

    List<InternetBanking> list = new ArrayList<>();

    try {

        Connection con = DBConnection.getConnection();

        String sql = "SELECT * FROM internet_banking ORDER BY ib_id DESC";

        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

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

        rs.close();
        ps.close();
        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

public boolean approveRequest(int id) {

    boolean status = false;

    try {

        Connection con = DBConnection.getConnection();

        String sql = "UPDATE internet_banking SET status='Approved' WHERE ib_id=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, id);

        status = ps.executeUpdate() > 0;

        ps.close();
        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}
public boolean rejectRequest(int id) {

    boolean status = false;

    try {

        Connection con = DBConnection.getConnection();

        String sql = "UPDATE internet_banking SET status='Rejected' WHERE ib_id=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, id);

        status = ps.executeUpdate() > 0;

        ps.close();
        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}
public boolean deleteRequest(int id) {

    boolean status = false;

    try {

        Connection con = DBConnection.getConnection();

        String sql = "DELETE FROM internet_banking WHERE ib_id=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, id);

        status = ps.executeUpdate() > 0;

        ps.close();
        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}


public int getTotalRequests() {

    int total = 0;

    try {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps =
                con.prepareStatement("SELECT COUNT(*) FROM internet_banking");

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            total = rs.getInt(1);
        }

        rs.close();
        ps.close();
        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return total;
}
public int getApprovedRequests() {

    int total = 0;

    try {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps =
                con.prepareStatement("SELECT COUNT(*) FROM internet_banking WHERE status='Approved'");

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            total = rs.getInt(1);
        }

        rs.close();
        ps.close();
        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return total;
}
public int getPendingRequests() {

    int total = 0;

    try {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps =
                con.prepareStatement("SELECT COUNT(*) FROM internet_banking WHERE status='Pending'");

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            total = rs.getInt(1);
        }

        rs.close();
        ps.close();
        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return total;
}
public int getRejectedRequests() {

    int total = 0;

    try {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps =
                con.prepareStatement("SELECT COUNT(*) FROM internet_banking WHERE status='Rejected'");

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            total = rs.getInt(1);
        }

        rs.close();
        ps.close();
        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return total;
}

}
