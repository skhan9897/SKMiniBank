package com.bank.dao;

import com.bank.model.FixedDeposit;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FixedDepositDAO {

    public boolean addFixedDeposit(FixedDeposit fd) {
        String sql = "INSERT INTO fixed_deposit(customer_id,account_number,customer_name,fd_amount,interest_rate,duration_year,maturity_amount,open_date,maturity_date,status) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
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
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<FixedDeposit> getAllFD() {
        List<FixedDeposit> list = new ArrayList<>();
        String sql = "SELECT * FROM fixed_deposit ORDER BY fd_id DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<FixedDeposit> getAllFixedDeposits() {
        return getAllFD();
    }
    
    public FixedDeposit getFDByCustomerId(int customerId) {
        String sql = "SELECT * FROM fixed_deposit WHERE customer_id=? ORDER BY fd_id DESC LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
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
                    return fd;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
