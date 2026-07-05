package com.bank.dao;

import com.bank.model.Account;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // Open Account
    public boolean openAccount(Account account) {

        boolean status = false;

        try {

            con = DBConnection.getConnection();

            String sql = "INSERT INTO account(customer_id,account_number,account_type,branch_id,balance,status,opening_date) VALUES(?,?,?,?,?,?,?)";

            ps = con.prepareStatement(sql);

            ps.setInt(1, account.getCustomerId());
            ps.setString(2, account.getAccountNumber());
            ps.setString(3, account.getAccountType());
            ps.setInt(4, account.getBranchId());
            ps.setDouble(5, account.getBalance());
            ps.setString(6, account.getStatus());
            ps.setString(7, account.getOpeningDate());

            int i = ps.executeUpdate();

            if (i > 0) {
                status = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    // Get All Accounts
    public List<Account> getAllAccounts() {

        List<Account> list = new ArrayList<>();

        try {

            con = DBConnection.getConnection();

            ps = con.prepareStatement("SELECT * FROM account");

            rs = ps.executeQuery();

            while (rs.next()) {

                Account a = new Account();

                a.setAccountId(rs.getInt("account_id"));
                a.setCustomerId(rs.getInt("customer_id"));
                a.setAccountNumber(rs.getString("account_number"));
                a.setAccountType(rs.getString("account_type"));
                a.setBranchId(rs.getInt("branch_id"));
                a.setBalance(rs.getDouble("balance"));
                a.setStatus(rs.getString("status"));
                a.setOpeningDate(rs.getString("opening_date"));

                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Get Account By Number
    // Get Account By Number
public Account getAccountByNumber(String accountNumber) {

    Account a = null;

    try {

        con = DBConnection.getConnection();

        String sql = "SELECT customer_id, account_number, account_type, "
                   + "balance, status, full_name "
                   + "FROM customer WHERE account_number=?";

        ps = con.prepareStatement(sql);
        ps.setString(1, accountNumber);

        rs = ps.executeQuery();

        if (rs.next()) {

            a = new Account();

            a.setCustomerId(rs.getInt("customer_id"));
            a.setAccountNumber(rs.getString("account_number"));
            a.setAccountType(rs.getString("account_type"));
            a.setCustomerName(rs.getString("full_name"));
            a.setBalance(rs.getDouble("balance"));
            a.setStatus(rs.getString("status"));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return a;
}
    // Update Balance
    public boolean updateBalance(String accountNumber, double balance) {

        boolean status = false;

        try {

            con = DBConnection.getConnection();

            ps = con.prepareStatement("UPDATE account SET balance=? WHERE account_number=?");

            ps.setDouble(1, balance);
            ps.setString(2, accountNumber);

            int i = ps.executeUpdate();

            if (i > 0) {
                status = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
  
    
    
    public boolean deposit(String accountNumber, double amount) {

    boolean status = false;

    try {

        con = DBConnection.getConnection();

        String sql = "UPDATE customer SET balance = balance + ? WHERE account_number=?";

        ps = con.prepareStatement(sql);

        ps.setDouble(1, amount);
        ps.setString(2, accountNumber);

        if (ps.executeUpdate() > 0) {
            status = true;
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}
  public boolean withdraw(String accountNumber, double amount) {

    boolean status = false;

    try {

        con = DBConnection.getConnection();

        accountNumber = accountNumber.trim();

        // पहले balance check
        String sql = "SELECT balance FROM customer WHERE TRIM(account_number)=?";

        ps = con.prepareStatement(sql);
        ps.setString(1, accountNumber);

        rs = ps.executeQuery();

        if (rs.next()) {

            double balance = rs.getDouble("balance");

            System.out.println("Current Balance = " + balance);

            if (balance >= amount) {

                ps.close();

                sql = "UPDATE customer SET balance=? WHERE TRIM(account_number)=?";

                ps = con.prepareStatement(sql);

                ps.setDouble(1, balance - amount);
                ps.setString(2, accountNumber);

                int rows = ps.executeUpdate();

                System.out.println("Rows Updated = " + rows);

                status = rows > 0;

            } else {

                System.out.println("Insufficient Balance");

            }

        } else {

            System.out.println("Account Not Found");

        }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {

        try {
            if(rs!=null) rs.close();
            if(ps!=null) ps.close();
            if(con!=null) con.close();
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    return status;
}
    public boolean transferMoney(String fromAccount,
                             String toAccount,
                             double amount) {

    boolean status = false;

    try {

        con = DBConnection.getConnection();
        con.setAutoCommit(false);

        ps = con.prepareStatement(
                "SELECT balance FROM customer WHERE account_number=?");

        ps.setString(1, fromAccount);

        rs = ps.executeQuery();

        if (rs.next()) {

            double balance = rs.getDouble("balance");

            if (balance >= amount) {

                ps = con.prepareStatement(
                        "UPDATE customer SET balance = balance - ? WHERE account_number=?");

                ps.setDouble(1, amount);
                ps.setString(2, fromAccount);
                ps.executeUpdate();

                ps = con.prepareStatement(
                        "UPDATE customer SET balance = balance + ? WHERE account_number=?");

                ps.setDouble(1, amount);
                ps.setString(2, toAccount);
                ps.executeUpdate();

                con.commit();
                status = true;

            } else {

                con.rollback();
            }
        }

    } catch (Exception e) {

        e.printStackTrace();

        try {
            if (con != null) {
                con.rollback();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    } finally {

        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    return status;
}
}
