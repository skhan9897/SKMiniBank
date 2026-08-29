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
        // Fetch from customer table as it seems to be the primary store for account info in this app
        String sql = "SELECT customer_id, account_number, account_type, balance, status, full_name FROM customer WHERE account_number IS NOT NULL";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Account a = new Account();
                a.setCustomerId(rs.getInt("customer_id"));
                a.setAccountNumber(rs.getString("account_number"));
                a.setAccountType(rs.getString("account_type"));
                a.setCustomerName(rs.getString("full_name"));
                a.setBalance(rs.getDouble("balance"));
                a.setStatus(rs.getString("status"));
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Account getAccountByNumber(String accountNumber) {
        String sql = "SELECT customer_id, account_number, account_type, balance, status, full_name FROM customer WHERE REPLACE(account_number, ' ', '') = REPLACE(?, ' ', '')";
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
        String sql = "UPDATE customer SET balance=? WHERE REPLACE(account_number, ' ', '') = REPLACE(?, ' ', '')";
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
        // Robust matching for deposit
        String sql = "UPDATE customer SET balance = balance + ? WHERE REPLACE(account_number, ' ', '') = REPLACE(?, ' ', '') AND status<>'FREEZE'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setString(2, accountNumber);
            boolean ok = ps.executeUpdate() > 0;
            if (ok) {
                Account a = getAccountByNumber(accountNumber);
                if (a != null) {
                    new TransactionDAO().saveUpiTransaction(a.getAccountNumber(), a.getCustomerName(), "DEPOSIT", amount, a.getBalance(), "Cash Deposit");
                }
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean withdraw(String accountNumber, double amount) {
        // Robust matching for withdrawal
        String sql = "UPDATE customer SET balance = balance - ? WHERE REPLACE(account_number, ' ', '') = REPLACE(?, ' ', '') AND balance>=? AND status='ACTIVE' AND (kyc_status='VERIFIED' OR kyc_status='APPROVED')";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setString(2, accountNumber);
            ps.setDouble(3, amount);
            boolean ok = ps.executeUpdate() > 0;
            if (ok) {
                Account a = getAccountByNumber(accountNumber);
                if (a != null) {
                    new TransactionDAO().saveUpiTransaction(a.getAccountNumber(), a.getCustomerName(), "WITHDRAWAL", amount, a.getBalance(), "Cash Withdrawal");
                }
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

            if (sender == null || receiver == null) {
                con.rollback();
                return false;
            }

            // KYC and Status Validations
            if (!"ACTIVE".equalsIgnoreCase(sender.getStatus()) || sender.getBalance() < amount) {
                con.rollback();
                return false;
            }

            // Check KYC Status
            String kycSql = "SELECT kyc_status FROM customer WHERE account_number=?";
            try (PreparedStatement psKyc = con.prepareStatement(kycSql)) {
                psKyc.setString(1, fromAccount);
                try (ResultSet rsKyc = psKyc.executeQuery()) {
                    if (rsKyc.next()) {
                        String kyc = rsKyc.getString("kyc_status");
                        if (!"VERIFIED".equalsIgnoreCase(kyc)) {
                            System.err.println("Transfer blocked: KYC not verified for " + fromAccount);
                            con.rollback();
                            return false;
                        }
                    }
                }
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
            
            // Refresh balances to get exact values after transfer
            Account finalSender = getAccountByNumber(fromAccount);
            Account finalReceiver = getAccountByNumber(toAccount);

            String senderDesc = (description == null || description.isEmpty() || description.equals("Transfer")) ? "₹" + amount + " sent to " + toAccount : description;
            if (!senderDesc.contains("sent to")) senderDesc = "₹" + amount + " sent to " + toAccount + " (" + description + ")";
            
            tdao.saveUpiTransaction(fromAccount, finalSender.getCustomerName(), "DEBIT", amount, finalSender.getBalance(), senderDesc);
            
            String receiverDesc = "₹" + amount + " received from " + fromAccount;
            tdao.saveUpiTransaction(toAccount, finalReceiver.getCustomerName(), "CREDIT", amount, finalReceiver.getBalance(), receiverDesc);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
