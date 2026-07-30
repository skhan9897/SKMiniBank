package com.bank.dao;

import com.bank.model.Upi;
import com.bank.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpiDAO {

    public boolean generateUpi(Upi upi) {
        boolean status = false;
        try (Connection con = DBConnection.getConnection()) {
            // 1. Get mobile number for the customer
            String mobile = null;
            try (PreparedStatement psCust = con.prepareStatement("SELECT mobile FROM customer WHERE customer_id=?")) {
                psCust.setInt(1, upi.getCustomerId());
                try (ResultSet rsCust = psCust.executeQuery()) {
                    if (rsCust.next()) {
                        mobile = rsCust.getString("mobile");
                    }
                }
            }
            
            if (mobile == null || mobile.length() < 4) {
                return false;
            }

            // 2. Set default PIN as last 4 digits of mobile
            String defaultPin = mobile.substring(mobile.length() - 4);

            // 3. Check if UPI already exists
            try (PreparedStatement psCheck = con.prepareStatement("SELECT upi_id FROM upi WHERE account_number=?")) {
                psCheck.setString(1, upi.getAccountNumber());
                try (ResultSet rsCheck = psCheck.executeQuery()) {
                    if (rsCheck.next()) {
                        // If exists, just update it to new mobile-based format and set mobile-based PIN
                        try (PreparedStatement psUpd = con.prepareStatement("UPDATE upi SET upi_handle=?, upi_pin=?, status='ACTIVE' WHERE account_number=?")) {
                            psUpd.setString(1, mobile + "@skpay");
                            psUpd.setString(2, defaultPin);
                            psUpd.setString(3, upi.getAccountNumber());
                            return psUpd.executeUpdate() > 0;
                        }
                    }
                }
            }

            String upiHandle = mobile + "@skpay";
            String sql = "INSERT INTO upi(customer_id,account_number,upi_handle,upi_pin,status) VALUES(?,?,?,?,?)";
            try (PreparedStatement psIns = con.prepareStatement(sql)) {
                psIns.setInt(1, upi.getCustomerId());
                psIns.setString(2, upi.getAccountNumber());
                psIns.setString(3, upiHandle);
                psIns.setString(4, defaultPin);
                psIns.setString(5, "ACTIVE");
                status = psIns.executeUpdate() > 0;
            }
            
            if (status) {
                try (PreparedStatement psUpdCust = con.prepareStatement("UPDATE customer SET upi_id=?, upi_status='ACTIVE' WHERE customer_id=?")) {
                    psUpdCust.setString(1, upiHandle);
                    psUpdCust.setInt(2, upi.getCustomerId());
                    psUpdCust.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public void generateUpiForAll() {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT customer_id, account_number, mobile FROM customer";
            try (PreparedStatement psAll = con.prepareStatement(sql);
                 ResultSet rsAll = psAll.executeQuery()) {
                
                while (rsAll.next()) {
                    int cid = rsAll.getInt("customer_id");
                    String acc = rsAll.getString("account_number");
                    String mob = rsAll.getString("mobile");
                    
                    if (mob != null && mob.length() >= 4) {
                        String handle = mob + "@skpay";
                        String pin = mob.substring(mob.length() - 4);
                        
                        try (PreparedStatement psCheck = con.prepareStatement("SELECT upi_id FROM upi WHERE customer_id=?")) {
                            psCheck.setInt(1, cid);
                            try (ResultSet rsCheck = psCheck.executeQuery()) {
                                if (rsCheck.next()) {
                                    try (PreparedStatement psUpd = con.prepareStatement("UPDATE upi SET upi_handle=?, upi_pin=? WHERE customer_id=?")) {
                                        psUpd.setString(1, handle);
                                        psUpd.setString(2, pin);
                                        psUpd.setInt(3, cid);
                                        psUpd.executeUpdate();
                                    }
                                } else {
                                    try (PreparedStatement psIns = con.prepareStatement("INSERT INTO upi(customer_id, account_number, upi_handle, upi_pin, status) VALUES(?,?,?,?,?)")) {
                                        psIns.setInt(1, cid);
                                        psIns.setString(2, acc);
                                        psIns.setString(3, handle);
                                        psIns.setString(4, pin);
                                        psIns.setString(5, "ACTIVE");
                                        psIns.executeUpdate();
                                    }
                                }
                            }
                        }
                        
                        try (PreparedStatement psUpdCust = con.prepareStatement("UPDATE customer SET upi_id=?, upi_status='ACTIVE' WHERE customer_id=?")) {
                            psUpdCust.setString(1, handle);
                            psUpdCust.setInt(2, cid);
                            psUpdCust.executeUpdate();
                        }
                    }
                }
            }
            System.out.println("DEBUG: UPI PIN and ID generation for all completed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Upi getUpiByAccountNumber(String accountNumber) {
        Upi upi = null;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM upi WHERE account_number=?")) {
            ps.setString(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return upi;
    }

    public boolean setUpiPin(String accountNumber, String upiPin) {
        // Try update first
        String updateSql = "UPDATE upi SET upi_pin=?, status='ACTIVE' WHERE account_number=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(updateSql)) {
            ps.setString(1, upiPin);
            ps.setString(2, accountNumber);
            int rows = ps.executeUpdate();
            if (rows > 0) return true;
            
            // If update failed (row doesn't exist), we should insert
            CustomerDAO customerDAO = new CustomerDAO();
            com.bank.model.Customer c = customerDAO.getCustomerByAccountNumber(accountNumber);
            if (c != null) {
                String insertSql = "INSERT INTO upi(customer_id, account_number, upi_handle, upi_pin, status) VALUES(?,?,?,?,?)";
                try (PreparedStatement psIns = con.prepareStatement(insertSql)) {
                    psIns.setInt(1, c.getCustomerId());
                    psIns.setString(2, c.getAccountNumber());
                    psIns.setString(3, c.getMobile() + "@skpay");
                    psIns.setString(4, upiPin);
                    psIns.setString(5, "ACTIVE");
                    return psIns.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean verifyUpiPin(String accountNumber, String upiPin) {
        String sql = "SELECT upi_id FROM upi WHERE account_number=? AND upi_pin=? AND status='ACTIVE'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            ps.setString(2, upiPin);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getAccountNumberByUpi(String upiId) {
        String sql = "SELECT account_number FROM upi WHERE upi_handle=? AND status='ACTIVE'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, upiId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("account_number");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
