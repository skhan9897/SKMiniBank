package com.bank.dao;

import com.bank.model.Transaction;
import com.bank.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // Add Transaction
    public boolean addTransaction(Transaction t) {

        boolean status = false;

        try {

            con = DBConnection.getConnection();

            String sql = "INSERT INTO transactions(account_number,customer_name,transaction_type,amount,balance,status) VALUES(?,?,?,?,?,?)";

            ps = con.prepareStatement(sql);

            ps.setString(1, t.getAccountNumber());
            ps.setString(2, t.getCustomerName());
            ps.setString(3, t.getTransactionType());
            ps.setDouble(4, t.getAmount());
            ps.setDouble(5, t.getBalance());
            ps.setString(6, t.getStatus());

            int i = ps.executeUpdate();

            if (i > 0) {
                status = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    // View All Transactions
    public List<Transaction> getAllTransactions() {

        List<Transaction> list = new ArrayList<>();

        try {

            con = DBConnection.getConnection();

            String sql = "SELECT * FROM transactions ORDER BY transaction_id DESC";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {

                Transaction t = new Transaction();

                t.setTransactionId(rs.getInt("transaction_id"));
                t.setAccountNumber(rs.getString("account_number"));
                t.setCustomerName(rs.getString("customer_name"));
                t.setTransactionType(rs.getString("transaction_type"));
                t.setAmount(rs.getDouble("amount"));
                t.setBalance(rs.getDouble("balance"));
                t.setTransactionDate(rs.getString("transaction_date"));
                t.setStatus(rs.getString("status"));

                list.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Search by Account Number
    public List<Transaction> searchTransaction(String accountNumber) {

        List<Transaction> list = new ArrayList<>();

        try {

            con = DBConnection.getConnection();

            String sql = "SELECT * FROM transactions WHERE account_number=? ORDER BY transaction_id DESC";

            ps = con.prepareStatement(sql);

            ps.setString(1, accountNumber);

            rs = ps.executeQuery();

            while (rs.next()) {

                Transaction t = new Transaction();

                t.setTransactionId(rs.getInt("transaction_id"));
                t.setAccountNumber(rs.getString("account_number"));
                t.setCustomerName(rs.getString("customer_name"));
                t.setTransactionType(rs.getString("transaction_type"));
                t.setAmount(rs.getDouble("amount"));
                t.setBalance(rs.getDouble("balance"));
                t.setTransactionDate(rs.getString("transaction_date"));
                t.setStatus(rs.getString("status"));

                list.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Delete Transaction
    public boolean deleteTransaction(int transactionId) {

        boolean status = false;

        try {

            con = DBConnection.getConnection();

            String sql = "DELETE FROM transactions WHERE transaction_id=?";

            ps = con.prepareStatement(sql);

            ps.setInt(1, transactionId);

            int i = ps.executeUpdate();

            if (i > 0) {
                status = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}