package com.bank.dao;

import com.bank.model.Transaction;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    // Save Transaction
    public boolean saveTransaction(Transaction t) {

        boolean status = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO transactions(account_number, customer_name, transaction_type, amount, balance, transaction_date, status) VALUES(?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, t.getAccountNumber());
            ps.setString(2, t.getCustomerName());
            ps.setString(3, t.getTransactionType());
            ps.setDouble(4, t.getAmount());
            ps.setDouble(5, t.getBalance());
            ps.setTimestamp(6, t.getTransactionDate());
            ps.setString(7, t.getStatus());

            status = ps.executeUpdate() > 0;

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    // Get All Transactions
    public List<Transaction> getAllTransactions() {

        List<Transaction> list = new ArrayList<>();

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM transactions ORDER BY id DESC";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Transaction t = new Transaction();

                t.setId(rs.getInt("id"));
                t.setAccountNumber(rs.getString("account_number"));
                t.setCustomerName(rs.getString("customer_name"));
                t.setTransactionType(rs.getString("transaction_type"));
                t.setAmount(rs.getDouble("amount"));
                t.setBalance(rs.getDouble("balance"));
                t.setTransactionDate(rs.getTimestamp("transaction_date"));
                t.setStatus(rs.getString("status"));

                list.add(t);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
// Add Transaction
public boolean addTransaction(Transaction t) {
    return saveTransaction(t);
}

// Search Transaction by Account Number
public List<Transaction> searchTransaction(String accountNumber) {

    List<Transaction> list = new ArrayList<>();

    try {

        Connection con = DBConnection.getConnection();

        String sql = "SELECT * FROM transactions WHERE account_number=? ORDER BY id DESC";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, accountNumber);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Transaction t = new Transaction();

            t.setId(rs.getInt("id"));
            t.setAccountNumber(rs.getString("account_number"));
            t.setCustomerName(rs.getString("customer_name"));
            t.setTransactionType(rs.getString("transaction_type"));
            t.setAmount(rs.getDouble("amount"));
            t.setBalance(rs.getDouble("balance"));
            t.setTransactionDate(rs.getTimestamp("transaction_date"));
            t.setStatus(rs.getString("status"));

            list.add(t);
        }

        rs.close();
        ps.close();
        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
}