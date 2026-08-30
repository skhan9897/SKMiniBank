package com.bank.dao;

import com.bank.model.Transaction;
import com.bank.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    /**
     * The master method to save any transaction. 
     * Uses a highly resilient query that works with both old and new table structures.
     */
    public boolean saveUpiTransaction(String accountNumber, String customerName, String type, double amount, double balance, String description) {
        if (accountNumber == null || accountNumber.isEmpty()) return false;
        
        // Clean account number
        String cleanAcc = accountNumber.trim();
        
        // Strategy 1: Modern structure (with status column)
        String sqlFull = "INSERT INTO transactions(account_number, customer_name, transaction_type, amount, balance, description, transaction_date, status) VALUES(?,?,?,?,?,?,NOW(),'SUCCESS')";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlFull)) {
            ps.setString(1, cleanAcc);
            ps.setString(2, customerName != null ? customerName : "Customer");
            ps.setString(3, type != null ? type.toUpperCase() : "TXN");
            ps.setDouble(4, amount);
            ps.setDouble(5, balance);
            ps.setString(6, description != null ? description : "Bank Transaction");
            
            if (ps.executeUpdate() > 0) {
                System.out.println("LOG: Transaction stored for " + cleanAcc);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("LOG: Full Insert failed, trying fallback: " + e.getMessage());
            
            // Strategy 2: Legacy structure (without status column)
            String sqlLegacy = "INSERT INTO transactions(account_number, customer_name, transaction_type, amount, balance, description, transaction_date) VALUES(?,?,?,?,?,?,NOW())";
            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sqlLegacy)) {
                ps.setString(1, cleanAcc);
                ps.setString(2, customerName != null ? customerName : "Customer");
                ps.setString(3, type != null ? type.toUpperCase() : "TXN");
                ps.setDouble(4, amount);
                ps.setDouble(5, balance);
                ps.setString(6, description != null ? description : "Bank Transaction");
                return ps.executeUpdate() > 0;
            } catch (SQLException ex) {
                System.err.println("LOG: All transaction insert strategies failed for " + cleanAcc);
                ex.printStackTrace();
            }
        }
        return false;
    }

    public boolean addTransaction(Transaction t) {
        return saveUpiTransaction(t.getAccountNumber(), t.getCustomerName(), t.getTransactionType(), t.getAmount(), t.getBalance(), t.getDescription());
    }

    public boolean saveTransaction(Transaction t) {
        return addTransaction(t);
    }

    public List<Transaction> getAllTransactions() {
        return fetchTransactions("SELECT * FROM transactions ORDER BY transaction_date DESC", false);
    }

    public List<Transaction> getTodayTransactions() {
        return fetchTransactions("SELECT * FROM transactions WHERE DATE(transaction_date) = CURDATE() ORDER BY transaction_date DESC", false);
    }

    public List<Transaction> getRecentTransactions(int limit) {
        return fetchTransactions("SELECT * FROM transactions ORDER BY transaction_date DESC LIMIT " + limit, false);
    }

    public List<Transaction> getTransactionsByAccount(String accountNumber) {
        if (accountNumber == null) return new ArrayList<>();
        String cleanAcc = accountNumber.trim().replaceAll("\\s+", "");
        // Use standard query that matches account numbers regardless of internal spaces
        String sql = "SELECT * FROM transactions WHERE REPLACE(account_number, ' ', '') = ? ORDER BY transaction_date DESC";
        return fetchTransactions(sql, true, cleanAcc);
    }

    private List<Transaction> fetchTransactions(String sql, boolean hasParam, String... params) {
        List<Transaction> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            if (hasParam && params != null) {
                for (int i = 0; i < params.length; i++) {
                    ps.setString(i + 1, params[i]);
                }
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transaction t = new Transaction();
                    // Robust mapping: Try different column names for ID
                    try {
                        t.setId(rs.getInt("transaction_id"));
                    } catch (Exception e) {
                        try { t.setId(rs.getInt("id")); } catch (Exception ignored) {}
                    }
                    
                    t.setAccountNumber(rs.getString("account_number"));
                    t.setCustomerName(rs.getString("customer_name"));
                    t.setTransactionType(rs.getString("transaction_type"));
                    t.setAmount(rs.getDouble("amount"));
                    t.setBalance(rs.getDouble("balance"));
                    t.setDescription(rs.getString("description"));
                    t.setTransactionDate(rs.getTimestamp("transaction_date"));
                    
                    try {
                        t.setStatus(rs.getString("status"));
                    } catch (Exception e) {
                        t.setStatus("SUCCESS");
                    }
                    list.add(t);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalTransactions() {
        String sql = "SELECT COUNT(*) FROM transactions WHERE DATE(transaction_date) = CURDATE()";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getTotalBalance() {
        String sql = "SELECT SUM(balance) FROM customer";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
