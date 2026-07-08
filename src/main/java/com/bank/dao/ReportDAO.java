package com.bank.dao;

import com.bank.model.Customer;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.bank.util.DBConnection;

public class ReportDAO {

    public int getTotalCustomers() {
        int total = 0;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM customer");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public int getTotalAccounts() {
        int total = 0;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM account");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
    
    public double getTotalBalance() {
    double total = 0;
    try {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "SELECT SUM(balance) FROM account");
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            total = rs.getDouble(1);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return total;
}

public double getTotalDeposit() {
    double total = 0;
    try {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "SELECT SUM(amount) FROM transactions WHERE transaction_type='Deposit'");
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            total = rs.getDouble(1);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return total;
}

public double getTotalWithdraw() {
    double total = 0;
    try {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "SELECT SUM(amount) FROM transactions WHERE transaction_type='Withdraw'");
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            total = rs.getDouble(1);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return total;
}

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

    } catch (Exception e) {
        e.printStackTrace();
    }
    return total;
}

public List<Customer> getRecentCustomers() {

    List<Customer> list = new ArrayList<>();

    try {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM customer ORDER BY customer_id DESC LIMIT 10");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Customer c = new Customer();

            c.setCustomerId(rs.getInt("customer_id"));
            c.setFullName(rs.getString("full_name"));
            c.setMobile(rs.getString("mobile"));
            c.setAccountNumber(rs.getString("account_number"));
            c.setBalance(rs.getDouble("balance"));
            c.setStatus(rs.getString("status"));

            list.add(c);

        }

    } catch (Exception e) {

        e.printStackTrace();

    }

    return list;

}



}