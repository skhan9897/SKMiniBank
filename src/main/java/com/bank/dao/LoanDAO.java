package com.bank.dao;

import com.bank.model.Loan;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class LoanDAO {
    
  public boolean applyLoan(Loan loan) {

    boolean status = false;

    try {

        Connection con = DBConnection.getConnection();

        String sql = "INSERT INTO loan(customer_id,account_number,customer_name,loan_type,loan_amount,interest_rate,duration_year,status,apply_date) VALUES(?,?,?,?,?,?,?,?,CURDATE())";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, loan.getCustomerId());
        ps.setString(2, loan.getAccountNumber());
        ps.setString(3, loan.getCustomerName());
        ps.setString(4, loan.getLoanType());
        ps.setDouble(5, loan.getLoanAmount());
        ps.setDouble(6, loan.getInterestRate());
        ps.setInt(7, loan.getDurationYear());
        ps.setString(8, "Pending");

        status = ps.executeUpdate() > 0;

        ps.close();
        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}


public List<Loan> getAllLoans() {

    List<Loan> list = new ArrayList<>();

    try {

        Connection con = DBConnection.getConnection();

        String sql = "SELECT * FROM loan ORDER BY loan_id DESC";

        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Loan loan = new Loan();

            loan.setLoanId(rs.getInt("loan_id"));
            loan.setCustomerId(rs.getInt("customer_id"));
            loan.setAccountNumber(rs.getString("account_number"));
            loan.setCustomerName(rs.getString("customer_name"));
            loan.setLoanType(rs.getString("loan_type"));
            loan.setLoanAmount(rs.getDouble("loan_amount"));
            loan.setInterestRate(rs.getDouble("interest_rate"));
            loan.setDurationYear(rs.getInt("duration_year"));
            loan.setStatus(rs.getString("status"));
            loan.setApplyDate(rs.getDate("apply_date"));

            list.add(loan);

        }

        rs.close();
        ps.close();
        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

public Loan getLoanById(int loanId) {

    Loan loan = null;

    try {

        Connection con = DBConnection.getConnection();

        String sql = "SELECT * FROM loan WHERE loan_id=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, loanId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            loan = new Loan();

            loan.setLoanId(rs.getInt("loan_id"));
            loan.setCustomerId(rs.getInt("customer_id"));
            loan.setAccountNumber(rs.getString("account_number"));
            loan.setCustomerName(rs.getString("customer_name"));
            loan.setLoanType(rs.getString("loan_type"));
            loan.setLoanAmount(rs.getDouble("loan_amount"));
            loan.setInterestRate(rs.getDouble("interest_rate"));
            loan.setDurationYear(rs.getInt("duration_year"));
            loan.setStatus(rs.getString("status"));
            loan.setApplyDate(rs.getDate("apply_date"));

        }

        rs.close();
        ps.close();
        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return loan;
}

public boolean approveLoan(int loanId) {

    boolean status = false;

    try {

        Connection con = DBConnection.getConnection();

        String sql = "UPDATE loan SET status='Approved' WHERE loan_id=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, loanId);

        status = ps.executeUpdate() > 0;

        ps.close();
        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}
public boolean rejectLoan(int loanId) {

    boolean status = false;

    try {

        Connection con = DBConnection.getConnection();

        String sql = "UPDATE loan SET status='Rejected' WHERE loan_id=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, loanId);

        status = ps.executeUpdate() > 0;

        ps.close();
        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}
public boolean deleteLoan(int loanId) {

    boolean status = false;

    try {

        Connection con = DBConnection.getConnection();

        String sql = "DELETE FROM loan WHERE loan_id=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, loanId);

        status = ps.executeUpdate() > 0;

        ps.close();
        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}
public int getTotalLoans() {

    int total = 0;

    try {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps =
                con.prepareStatement("SELECT COUNT(*) FROM loan");

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
public int getApprovedLoans() {

    int total = 0;

    try {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps =
                con.prepareStatement("SELECT COUNT(*) FROM loan WHERE status='Approved'");

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
public int getPendingLoans() {

    int total = 0;

    try {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps =
                con.prepareStatement("SELECT COUNT(*) FROM loan WHERE status='Pending'");

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
public int getRejectedLoans() {

    int total = 0;

    try {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps =
                con.prepareStatement("SELECT COUNT(*) FROM loan WHERE status='Rejected'");

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

public double getTotalLoanAmount() {

    double total = 0;

    try {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps =
                con.prepareStatement("SELECT IFNULL(SUM(loan_amount),0) FROM loan");

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

 // Save Loan Application


}
