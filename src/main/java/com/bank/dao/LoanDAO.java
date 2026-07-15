package com.bank.dao;

import com.bank.model.Loan;
import com.bank.util.DBConnection;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class LoanDAO {
    
    
    private Loan getLoanDetails(Connection con, int loanId) throws Exception {

    Loan loan = null;

    String sql = "SELECT * FROM loan WHERE loan_id=?";

    PreparedStatement ps = con.prepareStatement(sql);

    ps.setInt(1, loanId);

    ResultSet rs = ps.executeQuery();

    if(rs.next()){

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

    return loan;

}
    
    private boolean creditLoanAmount(Connection con,
                                 String accountNumber,
                                 double loanAmount) throws Exception {

    String sql =
            "UPDATE customer "
          + "SET balance = balance + ? "
          + "WHERE account_number=?";

    PreparedStatement ps =
            con.prepareStatement(sql);

    ps.setDouble(1, loanAmount);
    ps.setString(2, accountNumber);

    int row = ps.executeUpdate();

    ps.close();

    return row > 0;

}
    
    
    private double getCurrentBalance(Connection con,
                                 String accountNumber) throws Exception {

    double balance = 0;

    String sql =
            "SELECT balance FROM customer "
          + "WHERE account_number=?";

    PreparedStatement ps =
            con.prepareStatement(sql);

    ps.setString(1, accountNumber);

    ResultSet rs = ps.executeQuery();

    if (rs.next()) {

        balance = rs.getDouble("balance");

    }

    rs.close();
    ps.close();

    return balance;

}
    
    private boolean saveLoanTransaction(Connection con,
                                    String accountNumber,
                                    double loanAmount,
                                    double currentBalance) throws Exception {

    String sql =
        "INSERT INTO transactions "
      + "(account_number,transaction_type,amount,balance,remarks,transaction_date) "
      + "VALUES(?,?,?,?,?,NOW())";

    PreparedStatement ps = con.prepareStatement(sql);

    ps.setString(1, accountNumber);

    ps.setString(2, "LOAN CREDIT");

    ps.setDouble(3, loanAmount);

    ps.setDouble(4, currentBalance);

    ps.setString(5, "Loan Amount Credited By Admin");

    int row = ps.executeUpdate();

    ps.close();

    return row > 0;

}
    
 public int applyLoanAndReturnId(Loan loan) {

    int loanId = 0;

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {

        con = DBConnection.getConnection();

        String sql = "INSERT INTO loan "
                + "(customer_id,account_number,customer_name,"
                + "loan_type,loan_amount,interest_rate,"
                + "duration_year,status,apply_date) "
                + "VALUES(?,?,?,?,?,?,?,?,CURDATE())";

        ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, loan.getCustomerId());
        ps.setString(2, loan.getAccountNumber());
        ps.setString(3, loan.getCustomerName());
        ps.setString(4, loan.getLoanType());
        ps.setDouble(5, loan.getLoanAmount());
        ps.setDouble(6, loan.getInterestRate());
        ps.setInt(7, loan.getDurationYear());
        ps.setString(8, "Pending");

        int row = ps.executeUpdate();

        if (row > 0) {

            rs = ps.getGeneratedKeys();

            if (rs.next()) {

                loanId = rs.getInt(1);

            }

        }

    } catch (Exception e) {

        e.printStackTrace();

    } finally {

        try {
            if (rs != null) rs.close();
        } catch (Exception e) {}

        try {
            if (ps != null) ps.close();
        } catch (Exception e) {}

        try {
            if (con != null) con.close();
        } catch (Exception e) {}

    }

    return loanId;

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

    Connection con = null;

    try {

        con = DBConnection.getConnection();
        con.setAutoCommit(false);

        // Loan Details
        Loan loan = getLoanDetails(con, loanId);

        if (loan == null) {
            return false;
        }

        // Already Approved
        if ("Approved".equalsIgnoreCase(loan.getStatus())) {
            return true;
        }

        // Update Loan Status
        String updateLoan =
                "UPDATE loan SET status='Approved' WHERE loan_id=?";

        PreparedStatement ps1 = con.prepareStatement(updateLoan);
        ps1.setInt(1, loanId);

        int loanRow = ps1.executeUpdate();

        ps1.close();

        if (loanRow == 0) {
            con.rollback();
            return false;
        }

        // Credit Loan Amount
        String updateBalance =
                "UPDATE customer SET balance = balance + ? WHERE account_number=?";

        PreparedStatement ps2 = con.prepareStatement(updateBalance);

        ps2.setDouble(1, loan.getLoanAmount());
        ps2.setString(2, loan.getAccountNumber());

        int balanceRow = ps2.executeUpdate();

        ps2.close();

        if (balanceRow == 0) {
            con.rollback();
            return false;
        }

        // Current Balance
        double currentBalance = 0;

        PreparedStatement ps3 =
                con.prepareStatement(
                        "SELECT balance FROM customer WHERE account_number=?");

        ps3.setString(1, loan.getAccountNumber());

        ResultSet rs = ps3.executeQuery();

        if (rs.next()) {
            currentBalance = rs.getDouble("balance");
        }

        rs.close();
        ps3.close();

        // Transaction Entry
        PreparedStatement ps4 = con.prepareStatement(

            "INSERT INTO transactions(account_number,transaction_type,amount,balance,remarks,transaction_date) VALUES(?,?,?,?,?,NOW())"

        );

        ps4.setString(1, loan.getAccountNumber());
        ps4.setString(2, "LOAN CREDIT");
        ps4.setDouble(3, loan.getLoanAmount());
        ps4.setDouble(4, currentBalance);
        ps4.setString(5, "Loan Approved By Admin");

        ps4.executeUpdate();

        ps4.close();

        // Notification
        PreparedStatement ps5 = con.prepareStatement(

            "INSERT INTO notification(customer_id,title,message,status,created_date) VALUES(?,?,?,?,NOW())"

        );

        ps5.setInt(1, loan.getCustomerId());
        ps5.setString(2, "Loan Approved");
        ps5.setString(3,
                "Your loan of ₹" + loan.getLoanAmount()
                        + " has been approved and credited to your account.");
        ps5.setString(4, "UNREAD");

        ps5.executeUpdate();

        ps5.close();

        con.commit();

        return true;

    } catch (Exception e) {

        try {
            if (con != null) {
                con.rollback();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        e.printStackTrace();
        return false;

    } finally {

        try {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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
private boolean saveLoanNotification(Connection con,
                                     int customerId,
                                     double loanAmount) throws Exception {

    String sql =
        "INSERT INTO notifications(customer_id,title,message,status,created_date) "
      + "VALUES(?,?,?,?,NOW())";

    PreparedStatement ps = con.prepareStatement(sql);

    ps.setInt(1, customerId);

    ps.setString(2, "Loan Approved");

    ps.setString(3,
        "Congratulations! Your loan of ₹"
        + loanAmount
        + " has been approved and credited to your account.");

    ps.setString(4, "UNREAD");

    int row = ps.executeUpdate();

    ps.close();

    return row > 0;

}

public boolean creditLoanAmount(String accountNumber, double loanAmount) {

    boolean status = false;

    Connection con = null;
    PreparedStatement ps = null;

    try {

        con = DBConnection.getConnection();

        String sql =
                "UPDATE customer SET balance = balance + ? WHERE account_number=?";

        ps = con.prepareStatement(sql);

        ps.setDouble(1, loanAmount);
        ps.setString(2, accountNumber);

        status = ps.executeUpdate() > 0;

    } catch (Exception e) {

        e.printStackTrace();

    } finally {

        try {
            if (ps != null) ps.close();
        } catch (Exception e) {
        }

        try {
            if (con != null) con.close();
        } catch (Exception e) {
        }

    }

    return status;
}

public boolean addLoanTransaction(String accountNumber,
                                  double amount,
                                  double balance) {

    boolean status = false;

    Connection con = null;
    PreparedStatement ps = null;

    try {

        con = DBConnection.getConnection();

        String sql = "INSERT INTO transactions "
                + "(account_number, transaction_type, amount, balance, remarks) "
                + "VALUES(?,?,?,?,?)";

        ps = con.prepareStatement(sql);

        ps.setString(1, accountNumber);
        ps.setString(2, "LOAN CREDIT");
        ps.setDouble(3, amount);
        ps.setDouble(4, balance);
        ps.setString(5, "Loan Approved By Admin");

        status = ps.executeUpdate() > 0;

    } catch (Exception e) {

        e.printStackTrace();

    } finally {

        try {
            if (ps != null) ps.close();
        } catch (Exception e) {
        }

        try {
            if (con != null) con.close();
        } catch (Exception e) {
        }

    }

    return status;
}
public double getCustomerBalance(String accountNumber) {

    double balance = 0;

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {

        con = DBConnection.getConnection();

        String sql =
                "SELECT balance FROM customer WHERE account_number=?";

        ps = con.prepareStatement(sql);

        ps.setString(1, accountNumber);

        rs = ps.executeQuery();

        if (rs.next()) {

            balance = rs.getDouble("balance");

        }

    } catch (Exception e) {

        e.printStackTrace();

    } finally {

        try {
            if (rs != null) rs.close();
        } catch (Exception e) {
        }

        try {
            if (ps != null) ps.close();
        } catch (Exception e) {
        }

        try {
            if (con != null) con.close();
        } catch (Exception e) {
        }

    }

    return balance;
}

public boolean verifyDocuments(int loanId) {

    boolean status = false;

    Connection con = null;
    PreparedStatement ps = null;

    try {

        con = DBConnection.getConnection();

        String sql =
            "UPDATE loan_documents "
          + "SET verification_status='VERIFIED', "
          + "verified_by='ADMIN', "
          + "verified_date=NOW() "
          + "WHERE loan_id=?";

        ps = con.prepareStatement(sql);

        ps.setInt(1, loanId);

        status = ps.executeUpdate() > 0;

    } catch (Exception e) {

        e.printStackTrace();

    } finally {

        try {
            if (ps != null) ps.close();
        } catch (Exception e) {
        }

        try {
            if (con != null) con.close();
        } catch (Exception e) {
        }

    }

    return status;
}
}
