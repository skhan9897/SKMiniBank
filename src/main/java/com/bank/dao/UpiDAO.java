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

            // Check if UPI already exists
            ps = con.prepareStatement(
                    "SELECT upi_id FROM upi WHERE account_number=?");
            ps.setString(1, upi.getAccountNumber());

            rs = ps.executeQuery();

            if (rs.next()) {
                return false;
            }

            String upiHandle = upi.getAccountNumber() + "@skbank";

            ps = con.prepareStatement(
                    "INSERT INTO upi(customer_id,account_number,upi_handle,status) VALUES(?,?,?,?)");

            ps.setInt(1, upi.getCustomerId());
            ps.setString(2, upi.getAccountNumber());
            ps.setString(3, upiHandle);
            ps.setString(4, "ACTIVE");

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
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

    return accountDAO.transferAmount(
            fromAccount,
            toAccount,
            amount,
            "UPI Payment");
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