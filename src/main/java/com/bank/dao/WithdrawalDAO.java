package com.bank.dao;

import com.bank.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WithdrawalDAO {

    public boolean performWithdrawal(String accountNumber, String method, double amount) {
        String updateAccSql = "UPDATE customer SET balance = balance - ? WHERE account_number = ? AND balance >= ?";
        String insertTxnSql = "INSERT INTO transactions (account_number, amount, type, description, date) VALUES (?, ?, ?, ?, NOW())";
        
        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            
            try (PreparedStatement psAcc = con.prepareStatement(updateAccSql);
                 PreparedStatement psTxn = con.prepareStatement(insertTxnSql)) {
                
                psAcc.setDouble(1, amount);
                psAcc.setString(2, accountNumber);
                psAcc.setDouble(3, amount);
                int rows = psAcc.executeUpdate();
                
                if (rows > 0) {
                    psTxn.setString(1, accountNumber);
                    psTxn.setDouble(2, amount);
                    psTxn.setString(3, "DEBIT");
                    psTxn.setString(4, "Cash Withdrawal via " + method);
                    psTxn.executeUpdate();
                    
                    con.commit();
                    return true;
                } else {
                    con.rollback();
                    return false;
                }
            } catch (SQLException e) {
                con.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
