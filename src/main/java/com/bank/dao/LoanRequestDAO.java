package com.bank.dao;

import com.bank.model.LoanRequest;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LoanRequestDAO {

    public boolean applyLoan(LoanRequest loan) {
        String sql = "INSERT INTO loan_request(customer_id, account_number, loan_type, loan_amount, tenure_months, monthly_income, purpose, status) VALUES(?,?,?,?,?,?,?,'PENDING')";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, loan.getCustomerId());
            ps.setString(2, loan.getAccountNumber());
            ps.setString(3, loan.getLoanType());
            ps.setDouble(4, loan.getLoanAmount());
            ps.setInt(5, loan.getTenureMonths());
            ps.setDouble(6, loan.getMonthlyIncome());
            ps.setString(7, loan.getPurpose());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public LoanRequest getLoanByCustomerId(int customerId) {
        String sql = "SELECT * FROM loan_request WHERE customer_id=? ORDER BY request_date DESC LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<LoanRequest> getAllLoanRequests() {
        List<LoanRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM loan_request ORDER BY request_date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean approveLoan(int loanId, String approvedBy, String remarks, double interestRate) {
        String sql = "UPDATE loan_request SET status='APPROVED', approved_by=?, remarks=?, interest_rate=?, approval_date=NOW() WHERE loan_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, approvedBy);
            ps.setString(2, remarks);
            ps.setDouble(3, interestRate);
            ps.setInt(4, loanId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean rejectLoan(int loanId, String approvedBy, String remarks) {
        String sql = "UPDATE loan_request SET status='REJECTED', approved_by=?, remarks=?, approval_date=NOW() WHERE loan_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, approvedBy);
            ps.setString(2, remarks);
            ps.setInt(3, loanId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean disburseLoan(int loanId) {
        String sql = "UPDATE loan_request SET status='DISBURSED', disbursement_date=NOW() WHERE loan_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, loanId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private LoanRequest mapRow(ResultSet rs) throws Exception {
        LoanRequest loan = new LoanRequest();
        loan.setLoanId(rs.getInt("loan_id"));
        loan.setCustomerId(rs.getInt("customer_id"));
        loan.setAccountNumber(rs.getString("account_number"));
        loan.setLoanType(rs.getString("loan_type"));
        loan.setLoanAmount(rs.getDouble("loan_amount"));
        loan.setTenureMonths(rs.getInt("tenure_months"));
        loan.setInterestRate(rs.getDouble("interest_rate"));
        loan.setMonthlyIncome(rs.getDouble("monthly_income"));
        loan.setPurpose(rs.getString("purpose"));
        loan.setStatus(rs.getString("status"));
        loan.setRemarks(rs.getString("remarks"));
        loan.setApprovedBy(rs.getString("approved_by"));
        loan.setRequestDate(rs.getTimestamp("request_date"));
        loan.setApprovalDate(rs.getTimestamp("approval_date"));
        loan.setDisbursementDate(rs.getTimestamp("disbursement_date"));
        return loan;
    }
}
