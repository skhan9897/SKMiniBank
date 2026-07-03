package com.bank.dao;

import com.bank.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // Total Customers
    public int getTotalCustomers() {
        int total = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("SELECT COUNT(*) FROM customer");
            rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    // Total Accounts
    public int getTotalAccounts() {
        int total = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("SELECT COUNT(*) FROM account");
            rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    // Total Balance
    public double getTotalBalance() {
        double total = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("SELECT SUM(balance) FROM account");
            rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getDouble(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    // Total Transactions
    public int getTotalTransactions() {
        int total = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("SELECT COUNT(*) FROM transactions");
            rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
}