package com.bank.dao;

import com.bank.model.Loan;
import com.bank.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {

    public int getTotalLoans() {
        String sql = "SELECT COUNT(*) FROM loan_request";
        return getCount(sql);
    }

    public int getApprovedLoans() {
        String sql = "SELECT COUNT(*) FROM loan_request WHERE status='APPROVED'";
        return getCount(sql);
    }

    public int getPendingLoans() {
        String sql = "SELECT COUNT(*) FROM loan_request WHERE status='PENDING'";
        return getCount(sql);
    }

    public int getRejectedLoans() {
        String sql = "SELECT COUNT(*) FROM loan_request WHERE status='REJECTED'";
        return getCount(sql);
    }

    public double getTotalLoanAmount() {
        String sql = "SELECT SUM(loan_amount) FROM loan_request";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public List<Loan> getAllLoans() {
        List<Loan> list = new ArrayList<>();
        String sql = "SELECT l.*, c.full_name FROM loan_request l JOIN customer c ON l.customer_id = c.customer_id ORDER BY l.request_date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Loan loan = new Loan();
                loan.setLoanId(rs.getInt("loan_id"));
                loan.setCustomerId(rs.getInt("customer_id"));
                loan.setCustomerName(rs.getString("full_name"));
                loan.setAccountNumber(rs.getString("account_number"));
                loan.setLoanType(rs.getString("loan_type"));
                loan.setLoanAmount(rs.getDouble("loan_amount"));
                loan.setStatus(rs.getString("status"));
                loan.setRequestDate(rs.getTimestamp("request_date"));
                list.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private int getCount(String sql) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
