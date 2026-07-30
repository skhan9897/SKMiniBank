package com.bank.dao;

import com.bank.model.MobileBanking;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MobileBankingDAO {

    public boolean saveRequest(MobileBanking mb) {
        String sql = "INSERT INTO mobile_banking "
                + "(customer_id, account_number, customer_name, mobile, email, username, password, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, mb.getCustomerId());
            ps.setString(2, mb.getAccountNumber());
            ps.setString(3, mb.getCustomerName());
            ps.setString(4, mb.getMobile());
            ps.setString(5, mb.getEmail());
            ps.setString(6, mb.getUsername());
            ps.setString(7, mb.getPassword());
            ps.setString(8, mb.getStatus());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
