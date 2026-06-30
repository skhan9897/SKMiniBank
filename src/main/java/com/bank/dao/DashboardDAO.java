package com.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.bank.util.DBConnection;

public class DashboardDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

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

    public double getTotalBalance() {

        double balance = 0;

        try {

            con = DBConnection.getConnection();

            ps = con.prepareStatement("SELECT SUM(balance) FROM account");

            rs = ps.executeQuery();

            if (rs.next()) {
                balance = rs.getDouble(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return balance;
    }

    public int getTotalTransactions() {

    int total = 0;

    try {

        con = DBConnection.getConnection();

        ps = con.prepareStatement(
                "SELECT COUNT(*) FROM transactions");

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