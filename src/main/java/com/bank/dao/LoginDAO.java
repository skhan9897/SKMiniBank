package com.bank.dao;

import com.bank.model.Customer;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDAO {

    public Customer login(String accountNumber, String password) {

        Customer customer = null;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM customer WHERE account_number=? AND password=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, accountNumber);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                customer = new Customer();

                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setCustomerCode(rs.getString("customer_code"));
                customer.setFullName(rs.getString("full_name"));
                customer.setAccountNumber(rs.getString("account_number"));
                customer.setBalance(rs.getDouble("balance"));
                customer.setEmail(rs.getString("email"));
                customer.setMobile(rs.getString("mobile"));

            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return customer;
    }
}