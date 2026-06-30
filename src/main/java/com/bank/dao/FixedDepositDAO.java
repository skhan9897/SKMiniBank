package com.bank.dao;

import com.bank.model.FixedDeposit;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FixedDepositDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // Add Fixed Deposit
    public boolean addFixedDeposit(FixedDeposit fd) {

        boolean status = false;

        try {

            con = DBConnection.getConnection();

            String sql = "INSERT INTO fixed_deposit(customer_id,account_number,customer_name,fd_amount,interest_rate,duration_year,maturity_amount,open_date,maturity_date,status) VALUES(?,?,?,?,?,?,?,?,?,?)";

            ps = con.prepareStatement(sql);

            ps.setInt(1, fd.getCustomerId());
            ps.setString(2, fd.getAccountNumber());
            ps.setString(3, fd.getCustomerName());
            ps.setDouble(4, fd.getFdAmount());
            ps.setDouble(5, fd.getInterestRate());
            ps.setInt(6, fd.getDurationYear());
            ps.setDouble(7, fd.getMaturityAmount());
            ps.setString(8, fd.getOpenDate());
            ps.setString(9, fd.getMaturityDate());
            ps.setString(10, fd.getStatus());

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
    public List<FixedDeposit> getAllFD() {
    List<FixedDeposit> list = new ArrayList<>();

    try {
        con = DBConnection.getConnection();

        String sql = "SELECT * FROM fixed_deposit ORDER BY fd_id DESC";

        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {

            FixedDeposit fd = new FixedDeposit();

            fd.setFdId(rs.getInt("fd_id"));
            fd.setCustomerId(rs.getInt("customer_id"));
            fd.setAccountNumber(rs.getString("account_number"));
            fd.setCustomerName(rs.getString("customer_name"));
            fd.setFdAmount(rs.getDouble("fd_amount"));
            fd.setInterestRate(rs.getDouble("interest_rate"));
            fd.setDurationYear(rs.getInt("duration_year"));
            fd.setMaturityAmount(rs.getDouble("maturity_amount"));
            fd.setOpenDate(rs.getString("open_date"));
fd.setMaturityDate(rs.getString("maturity_date"));
            fd.setStatus(rs.getString("status"));

            list.add(fd);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
    

    // Get All Fixed Deposits
    public List<FixedDeposit> getAllFixedDeposits() {

        List<FixedDeposit> list = new ArrayList<>();

        try {

            con = DBConnection.getConnection();

            ps = con.prepareStatement(
                    "SELECT * FROM fixed_deposit ORDER BY fd_id DESC");

            rs = ps.executeQuery();

            while (rs.next()) {

                FixedDeposit fd = new FixedDeposit();

                fd.setFdId(rs.getInt("fd_id"));
                fd.setCustomerId(rs.getInt("customer_id"));
                fd.setAccountNumber(rs.getString("account_number"));
                fd.setCustomerName(rs.getString("customer_name"));
                fd.setFdAmount(rs.getDouble("fd_amount"));
                fd.setInterestRate(rs.getDouble("interest_rate"));
                fd.setDurationYear(rs.getInt("duration_year"));
                fd.setMaturityAmount(rs.getDouble("maturity_amount"));
                fd.setOpenDate(rs.getString("open_date"));
                fd.setMaturityDate(rs.getString("maturity_date"));
                fd.setStatus(rs.getString("status"));

                list.add(fd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    
    
    public FixedDeposit getFDByCustomerId(int customerId) {

    FixedDeposit fd = null;

    try {

        con = DBConnection.getConnection();

        String sql = "SELECT * FROM fixed_deposit WHERE customer_id=? ORDER BY fd_id DESC LIMIT 1";

        ps = con.prepareStatement(sql);
        ps.setInt(1, customerId);

        rs = ps.executeQuery();

        if (rs.next()) {

            fd = new FixedDeposit();

            fd.setFdId(rs.getInt("fd_id"));
            fd.setCustomerId(rs.getInt("customer_id"));
            fd.setAccountNumber(rs.getString("account_number"));
            fd.setCustomerName(rs.getString("customer_name"));

            fd.setFdAmount(rs.getDouble("fd_amount"));
            fd.setInterestRate(rs.getDouble("interest_rate"));
            fd.setDurationYear(rs.getInt("duration_year"));
            fd.setMaturityAmount(rs.getDouble("maturity_amount"));

            fd.setOpenDate(rs.getString("open_date"));
            fd.setMaturityDate(rs.getString("maturity_date"));
            fd.setStatus(rs.getString("status"));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return fd;
}
    
    
}