package com.bank.dao;

import com.bank.model.Transaction;
import com.bank.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public boolean saveTransaction(Transaction t) {
        String sql = "INSERT INTO transactions(account_number, customer_name, transaction_type, amount, balance, description, transaction_date, status) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getAccountNumber());
            ps.setString(2, t.getCustomerName());
            ps.setString(3, t.getTransactionType());
            ps.setDouble(4, t.getAmount());
            ps.setDouble(5, t.getBalance());
            ps.setString(6, t.getDescription());
            ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            ps.setString(8, t.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addTransaction(Transaction t) {
        return saveTransaction(t);
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT transaction_id, account_number, customer_name, transaction_type, amount, balance, description, transaction_date, status FROM transactions ORDER BY transaction_date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Transaction t = new Transaction();
                t.setId(rs.getInt("transaction_id"));
                t.setAccountNumber(rs.getString("account_number"));
                t.setCustomerName(rs.getString("customer_name"));
                t.setTransactionType(rs.getString("transaction_type"));
                t.setAmount(rs.getDouble("amount"));
                t.setBalance(rs.getDouble("balance"));
                t.setDescription(rs.getString("description"));
                t.setTransactionDate(rs.getTimestamp("transaction_date"));
                t.setStatus(rs.getString("status"));
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Transaction> getTodayTransactions() {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE DATE(transaction_date) = CURDATE() ORDER BY transaction_date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Transaction t = new Transaction();
                t.setId(rs.getInt("transaction_id"));
                t.setAccountNumber(rs.getString("account_number"));
                t.setCustomerName(rs.getString("customer_name"));
                t.setTransactionType(rs.getString("transaction_type"));
                t.setAmount(rs.getDouble("amount"));
                t.setBalance(rs.getDouble("balance"));
                t.setDescription(rs.getString("description"));
                t.setTransactionDate(rs.getTimestamp("transaction_date"));
                t.setStatus(rs.getString("status"));
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Transaction> getTransactionsByAccount(String accountNumber) {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_number=? ORDER BY transaction_date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transaction t = new Transaction();
                    t.setId(rs.getInt("transaction_id"));
                    t.setAccountNumber(rs.getString("account_number"));
                    t.setCustomerName(rs.getString("customer_name"));
                    t.setTransactionType(rs.getString("transaction_type"));
                    t.setAmount(rs.getDouble("amount"));
                    t.setBalance(rs.getDouble("balance"));
                    t.setDescription(rs.getString("description"));
                    t.setTransactionDate(rs.getTimestamp("transaction_date"));
                    t.setStatus(rs.getString("status"));
                    list.add(t);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean saveUpiTransaction(String accountNumber, String customerName, String type, double amount, double balance, String description) {
        String sql = "INSERT INTO transactions(account_number,customer_name,transaction_type,amount,balance,description,transaction_date,status) VALUES(?,?,?,?,?,?,NOW(),?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            ps.setString(2, customerName);
            ps.setString(3, type);
            ps.setDouble(4, amount);
            ps.setDouble(5, balance);
            ps.setString(6, description);
            ps.setString(7, "SUCCESS");
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getTotalTransactions() {
        String sql = "SELECT COUNT(*) AS cnt FROM transactions";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt("cnt");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getTotalDeposit() {
        String sql = "SELECT COALESCE(SUM(amount),0) AS total FROM transactions WHERE transaction_type='DEPOSIT'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getDouble("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public double getTotalWithdraw() {
        String sql = "SELECT COALESCE(SUM(amount),0) AS total FROM transactions WHERE transaction_type='WITHDRAWAL'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getDouble("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public double getTotalTransfer() {
        String sql = "SELECT COALESCE(SUM(amount),0) AS total FROM transactions WHERE transaction_type IN ('DEBIT','CREDIT','Transfer')";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getDouble("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
