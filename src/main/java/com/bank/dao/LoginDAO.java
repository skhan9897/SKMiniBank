package com.bank.dao;

import com.bank.model.Customer;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDAO {

    // Keep original login for Web (Account Number based)
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
                customer = mapCustomer(rs);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

    // New login for Android App (Mobile Number based)
    public Customer loginByMobile(String mobile, String password) {
        Customer customer = null;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM customer WHERE mobile=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, mobile);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                customer = mapCustomer(rs);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

    private Customer mapCustomer(ResultSet rs) throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId(rs.getInt("customer_id"));
        customer.setCustomerCode(rs.getString("customer_code"));
        customer.setFullName(rs.getString("full_name"));
        customer.setAccountNumber(rs.getString("account_number"));
        customer.setBalance(rs.getDouble("balance"));
        customer.setEmail(rs.getString("email"));
        customer.setMobile(rs.getString("mobile"));
        customer.setBranch(rs.getString("branch"));
        customer.setAccountType(rs.getString("account_type"));
        customer.setStatus(rs.getString("status"));
        return customer;
    }
}
