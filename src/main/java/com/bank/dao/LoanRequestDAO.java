package com.bank.dao;

import com.bank.model.LoanRequest;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LoanRequestDAO {

    private Connection con;

    public LoanRequestDAO() {
        con = DBConnection.getConnection();
    }

    // ==========================
    // Apply Loan
    // ==========================
    public boolean applyLoan(LoanRequest loan) {

        boolean status = false;

        try {

            String sql = "INSERT INTO loan_request("
                    + "customer_id,"
                    + "account_number,"
                    + "loan_type,"
                    + "loan_amount,"
                    + "tenure_months,"
                    + "monthly_income,"
                    + "purpose,"
                    + "status)"
                    + " VALUES(?,?,?,?,?,?,?,'PENDING')";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, loan.getCustomerId());
            ps.setString(2, loan.getAccountNumber());
            ps.setString(3, loan.getLoanType());
            ps.setDouble(4, loan.getLoanAmount());
            ps.setInt(5, loan.getTenureMonths());
            ps.setDouble(6, loan.getMonthlyIncome());
            ps.setString(7, loan.getPurpose());

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    // ==========================
    // Get Loan By Customer
    // ==========================
    public LoanRequest getLoanByCustomerId(int customerId) {

        LoanRequest loan = null;

        try {

            String sql = "SELECT * FROM loan_request "
                    + "WHERE customer_id=? "
                    + "ORDER BY request_date DESC LIMIT 1";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                loan = new LoanRequest();

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

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return loan;
    }
    
    // ==========================
// Get All Loan Requests
// ==========================
public List<LoanRequest> getAllLoanRequests() {

    List<LoanRequest> list = new ArrayList<>();

    try {

        String sql = "SELECT * FROM loan_request ORDER BY request_date DESC";

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

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

            list.add(loan);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

// ==========================
// Approve Loan
// ==========================
public boolean approveLoan(int loanId,
                           String approvedBy,
                           String remarks,
                           double interestRate) {

    boolean status = false;

    try {

        String sql = "UPDATE loan_request SET "
                + "status='APPROVED',"
                + "approved_by=?,"
                + "remarks=?,"
                + "interest_rate=?,"
                + "approval_date=NOW() "
                + "WHERE loan_id=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, approvedBy);
        ps.setString(2, remarks);
        ps.setDouble(3, interestRate);
        ps.setInt(4, loanId);

        status = ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}

// ==========================
// Reject Loan
// ==========================
public boolean rejectLoan(int loanId,
                          String approvedBy,
                          String remarks) {

    boolean status = false;

    try {

        String sql = "UPDATE loan_request SET "
                + "status='REJECTED',"
                + "approved_by=?,"
                + "remarks=?,"
                + "approval_date=NOW() "
                + "WHERE loan_id=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, approvedBy);
        ps.setString(2, remarks);
        ps.setInt(3, loanId);

        status = ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}

// ==========================
// Disburse Loan
// ==========================
public boolean disburseLoan(int loanId) {

    boolean status = false;

    try {

        String sql = "UPDATE loan_request SET "
                + "status='DISBURSED',"
                + "disbursement_date=NOW() "
                + "WHERE loan_id=?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, loanId);

        status = ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}
}