package com.bank.dao;

import com.bank.model.Customer;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    public Customer loginByMobile(String mobile, String password) {
        String sql = "SELECT * FROM customer WHERE mobile=? AND password=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, mobile);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapCustomer(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Customer login(String accountNumber, String password) {
        String sql = "SELECT * FROM customer WHERE account_number=? AND password=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapCustomer(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Customer mapCustomer(ResultSet rs) throws SQLException {
        Customer c = new Customer();
        c.setCustomerId(rs.getInt("customer_id"));
        c.setCustomerCode(rs.getString("customer_code"));
        c.setFullName(rs.getString("full_name"));
        c.setFatherName(rs.getString("father_name"));
        c.setMotherName(rs.getString("mother_name"));
        c.setMaritalStatus(rs.getString("marital_status"));
        c.setDob(rs.getString("dob"));
        c.setGender(rs.getString("gender"));
        c.setOccupation(rs.getString("occupation"));
        c.setMobile(rs.getString("mobile"));
        c.setAlternateMobile(rs.getString("alternate_mobile"));
        c.setEmail(rs.getString("email"));
        c.setAadhaar(rs.getString("aadhaar"));
        c.setPan(rs.getString("pan"));
        c.setAddress(rs.getString("address"));
        c.setCity(rs.getString("city"));
        c.setState(rs.getString("state"));
        c.setPincode(rs.getString("pincode"));
        c.setNomineeName(rs.getString("nominee_name"));
        c.setRelationship(rs.getString("relationship"));
        c.setNomineeMobile(rs.getString("nominee_mobile"));
        c.setAccountNumber(rs.getString("account_number"));
        c.setIfscCode(rs.getString("ifsc_code"));
        c.setAccountType(rs.getString("account_type"));
        c.setBranch(rs.getString("branch"));
        c.setBalance(rs.getDouble("balance"));
        c.setStatus(rs.getString("status"));
        c.setKycStatus(rs.getString("kyc_status"));
        c.setUpiId(rs.getString("upi_id"));
        c.setUpiStatus(rs.getString("upi_status"));
        return c;
    }
}
