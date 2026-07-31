package com.bank.dao;

import com.bank.model.Account;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public boolean openAccount(Account account) {
        String sql = "INSERT INTO account(customer_id,account_number,account_type,branch_id,balance,status,opening_date) VALUES(?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, account.getCustomerId());
            ps.setString(2, account.getAccountNumber());
            ps.setString(3, account.getAccountType());
            ps.setInt(4, account.getBranchId());
            ps.setDouble(5, account.getBalance());
            ps.setString(6, account.getStatus());
            ps.setString(7, account.getOpeningDate());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Account> getAllAccounts() {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM account";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Account getAccountByNumber(String accountNumber) {
        String sql = "SELECT customer_id, account_number, account_type, balance, status, full_name FROM customer WHERE account_number=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Account a = new Account();
                    a.setCustomerId(rs.getInt("customer_id"));
                    a.setAccountNumber(rs.getString("account_number"));
                    a.setAccountType(rs.getString("account_type"));
                    a.setCustomerName(rs.getString("full_name"));
                    a.setBalance(rs.getDouble("balance"));
                    a.setStatus(rs.getString("status"));
                    return a;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateBalance(String accountNumber, double balance) {
        String sql = "UPDATE account SET balance=? WHERE account_number=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, balance);
            ps.setString(2, accountNumber);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
  
    public boolean deposit(String accountNumber, double amount) {
        String sql = "UPDATE customer SET balance = balance + ? WHERE account_number=? AND status<>'FREEZE'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setString(2, accountNumber);
            boolean ok = ps.executeUpdate() > 0;
            if (ok) {
                Account a = getAccountByNumber(accountNumber);
                new TransactionDAO().saveUpiTransaction(accountNumber, a.getCustomerName(), "DEPOSIT", amount, a.getBalance(), "Cash Deposit");
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean withdraw(String accountNumber, double amount) {
        String sql = "UPDATE customer SET balance = balance - ? WHERE account_number=? AND balance>=? AND status='ACTIVE'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setString(2, accountNumber);
            ps.setDouble(3, amount);
            boolean ok = ps.executeUpdate() > 0;
            if (ok) {
                Account a = getAccountByNumber(accountNumber);
                new TransactionDAO().saveUpiTransaction(accountNumber, a.getCustomerName(), "WITHDRAWAL", amount, a.getBalance(), "Cash Withdrawal");
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean transferAmount(String fromAccount, String toAccount, double amount, String description) {
        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            
            Account sender = getAccountByNumber(fromAccount);
            Account receiver = getAccountByNumber(toAccount);

            if (sender == null || receiver == null || !"ACTIVE".equalsIgnoreCase(sender.getStatus()) || sender.getBalance() < amount) {
                con.rollback();
                return false;
            }

            // Execute Transfer
            try (PreparedStatement debit = con.prepareStatement("UPDATE customer SET balance = balance - ? WHERE account_number=?")) {
                debit.setDouble(1, amount);
                debit.setString(2, fromAccount);
                debit.executeUpdate();
            }
            try (PreparedStatement credit = con.prepareStatement("UPDATE customer SET balance = balance + ? WHERE account_number=?")) {
                credit.setDouble(1, amount);
                credit.setString(2, toAccount);
                credit.executeUpdate();
            }

            con.commit();
            
            // SAVE TO TRANSACTIONS TABLE
            TransactionDAO tdao = new TransactionDAO();
            String senderDesc = (description == null || description.isEmpty()) ? "Sent to " + toAccount : description;
            tdao.saveUpiTransaction(fromAccount, sender.getCustomerName(), "DEBIT", amount, sender.getBalance() - amount, senderDesc);
            
            String receiverDesc = "Received from " + fromAccount;
            tdao.saveUpiTransaction(toAccount, receiver.getCustomerName(), "CREDIT", amount, receiver.getBalance() + amount, receiverDesc);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
