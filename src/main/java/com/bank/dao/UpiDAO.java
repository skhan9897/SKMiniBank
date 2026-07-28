package com.bank.dao;

import com.bank.model.Upi;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpiDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public boolean generateUpi(Upi upi) {

        boolean status = false;

        try {

            con = DBConnection.getConnection();

            // 1. Get mobile number for the customer
            String mobile = null;
            PreparedStatement psCust = con.prepareStatement("SELECT mobile FROM customer WHERE customer_id=?");
            psCust.setInt(1, upi.getCustomerId());
            ResultSet rsCust = psCust.executeQuery();
            if (rsCust.next()) {
                mobile = rsCust.getString("mobile");
            }
            
            if (mobile == null || mobile.isEmpty()) {
                return false;
            }

            // 2. Check if UPI already exists
            ps = con.prepareStatement(
                    "SELECT upi_id FROM upi WHERE account_number=?");
            ps.setString(1, upi.getAccountNumber());

            rs = ps.executeQuery();

            if (rs.next()) {
                // If exists, just update it to new mobile-based format
                ps = con.prepareStatement("UPDATE upi SET upi_handle=? WHERE account_number=?");
                ps.setString(1, mobile + "@skpay");
                ps.setString(2, upi.getAccountNumber());
                return ps.executeUpdate() > 0;
            }

            String upiHandle = mobile + "@skpay";

            ps = con.prepareStatement(
                    "INSERT INTO upi(customer_id,account_number,upi_handle,status) VALUES(?,?,?,?)");

            ps.setInt(1, upi.getCustomerId());
            ps.setString(2, upi.getAccountNumber());
            ps.setString(3, upiHandle);
            ps.setString(4, "ACTIVE");

            status = ps.executeUpdate() > 0;
            
            // Also update customer table for consistency
            if (status) {
                PreparedStatement psUpdateCust = con.prepareStatement("UPDATE customer SET upi_id=?, upi_status='ACTIVE' WHERE customer_id=?");
                psUpdateCust.setString(1, upiHandle);
                psUpdateCust.setInt(2, upi.getCustomerId());
                psUpdateCust.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public void generateUpiForAll() {
        try {
            con = DBConnection.getConnection();
            
            // Get all customers who don't have a valid UPI or need refresh
            String sql = "SELECT customer_id, account_number, mobile FROM customer";
            PreparedStatement psAll = con.prepareStatement(sql);
            ResultSet rsAll = psAll.executeQuery();
            
            while (rsAll.next()) {
                int cid = rsAll.getInt("customer_id");
                String acc = rsAll.getString("account_number");
                String mob = rsAll.getString("mobile");
                
                if (mob != null && !mob.isEmpty()) {
                    String handle = mob + "@skpay";
                    
                    // Check upi table
                    PreparedStatement psCheck = con.prepareStatement("SELECT upi_id FROM upi WHERE customer_id=?");
                    psCheck.setInt(1, cid);
                    if (psCheck.executeQuery().next()) {
                        PreparedStatement psUpd = con.prepareStatement("UPDATE upi SET upi_handle=? WHERE customer_id=?");
                        psUpd.setString(1, handle);
                        psUpd.setInt(2, cid);
                        psUpd.executeUpdate();
                    } else {
                        PreparedStatement psIns = con.prepareStatement("INSERT INTO upi(customer_id, account_number, upi_handle, status) VALUES(?,?,?,?)");
                        psIns.setInt(1, cid);
                        psIns.setString(2, acc);
                        psIns.setString(3, handle);
                        psIns.setString(4, "ACTIVE");
                        psIns.executeUpdate();
                    }
                    
                    // Update customer table
                    PreparedStatement psUpdCust = con.prepareStatement("UPDATE customer SET upi_id=?, upi_status='ACTIVE' WHERE customer_id=?");
                    psUpdCust.setString(1, handle);
                    psUpdCust.setInt(2, cid);
                    psUpdCust.executeUpdate();
                }
            }
            System.out.println("DEBUG: UPI Generation for all accounts completed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Upi getUpiByAccountNumber(String accountNumber) {

    Upi upi = null;

    try {

        con = DBConnection.getConnection();

        ps = con.prepareStatement(
                "SELECT * FROM upi WHERE account_number=?");

        ps.setString(1, accountNumber);

        rs = ps.executeQuery();

        if (rs.next()) {

            upi = new Upi();

            upi.setUpiId(rs.getInt("upi_id"));
            upi.setCustomerId(rs.getInt("customer_id"));
            upi.setAccountNumber(rs.getString("account_number"));
            upi.setUpiHandle(rs.getString("upi_handle"));
            upi.setUpiPin(rs.getString("upi_pin"));
            upi.setStatus(rs.getString("status"));
            upi.setCreatedAt(rs.getString("created_at"));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return upi;
}
    public boolean setUpiPin(String accountNumber, String upiPin) {

    boolean status = false;

    try {

        con = DBConnection.getConnection();

        ps = con.prepareStatement(
                "UPDATE upi SET upi_pin=? WHERE account_number=? AND status='ACTIVE'");

        ps.setString(1, upiPin);
        ps.setString(2, accountNumber);

        status = ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}
    public boolean verifyUpiPin(String accountNumber, String upiPin) {

    boolean status = false;

    try {

        con = DBConnection.getConnection();

        ps = con.prepareStatement(
            "SELECT upi_id FROM upi WHERE account_number=? AND upi_pin=? AND status='ACTIVE'");

        ps.setString(1, accountNumber);
        ps.setString(2, upiPin);

        rs = ps.executeQuery();

        status = rs.next();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}
    public boolean upiTransfer(String fromAccount,
                           String toAccount,
                           double amount) {

    AccountDAO accountDAO = new AccountDAO();
    boolean success = accountDAO.transferAmount(
            fromAccount,
            toAccount,
            amount,
            "UPI Payment");

    if (success) {
        com.bank.util.KafkaService.logTransaction(fromAccount, toAccount, amount, "UPI Payment");
    }
    
    return success;
}
    public String getAccountNumberByUpi(String upiId) {

    String accountNumber = null;

    try {

        con = DBConnection.getConnection();

        ps = con.prepareStatement(
            "SELECT account_number FROM upi WHERE upi_handle=? AND status='ACTIVE'");

        ps.setString(1, upiId);

        rs = ps.executeQuery();

        if (rs.next()) {
            accountNumber = rs.getString("account_number");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return accountNumber;
}
}