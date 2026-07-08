package com.bank.dao;

import com.bank.model.Transaction;
import com.bank.util.DBConnection;

import java.sql.*;
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
            ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            ps.setString(7, t.getStatus());

            status = ps.executeUpdate() > 0;

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public boolean addTransaction(Transaction t) {
        return saveTransaction(t);
    }

    // All Transactions
    public List<Transaction> getAllTransactions() {

        List<Transaction> list = new ArrayList<>();

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM transactions ORDER BY transaction_date DESC");

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

    // Total Transactions
    public int getTotalTransactions() {

        int total = 0;

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT COUNT(*) FROM transactions");

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

    // Total Deposit
    public double getTotalDeposit() {

        double total = 0;

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT IFNULL(SUM(amount),0) FROM transactions WHERE transaction_type='Deposit'");

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getDouble(1);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    // Total Withdraw
    public double getTotalWithdraw() {

        double total = 0;

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT IFNULL(SUM(amount),0) FROM transactions WHERE transaction_type='Withdraw'");

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getDouble(1);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    // Total Transfer
    public double getTotalTransfer() {

        double total = 0;

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT IFNULL(SUM(amount),0) FROM transactions WHERE transaction_type='Transfer'");

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getDouble(1);
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