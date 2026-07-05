package com.bank.dao;

import com.bank.model.Customer;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDAO {

    public Customer login(String email, String password) {

        Customer customer = null;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM customer WHERE email=? AND password=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                customer = new Customer();

                customer.setCustomerCode(rs.getString("customer_code"));
                customer.setFullName(rs.getString("full_name"));
                customer.setAccountNumber(rs.getString("account_number"));
                customer.setBalance(rs.getDouble("balance"));
                customer.setEmail(rs.getString("email"));
                customer.setMobile(rs.getString("mobile"));

            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return customer;
    }
}