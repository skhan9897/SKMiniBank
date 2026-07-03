package com.bank.dao;

import com.bank.model.Customer;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // ==========================
    // Add Customer
    // ==========================

    public boolean addCustomer(Customer c) {

    boolean status = false;

    try {

        con = DBConnection.getConnection();

        String sql = "INSERT INTO customer("
+ "full_name,father_name,mother_name,marital_status,dob,gender,occupation,"
+ "mobile,alternate_mobile,email,aadhaar,pan,address,city,state,pincode,"
+ "nominee_name,relationship,nominee_mobile,"
+ "customer_code,cif_number,"
+ "account_number,ifsc_code,account_type,branch,balance,"
+ "mobile_verified,email_verified,"
+ "upi_id,upi_status,status,kyc_status,password,transaction_pin"
+ ") VALUES (?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?, ?,?,?)";
        ps = con.prepareStatement(sql);

        
ps.setString(1, c.getFullName());
ps.setString(2, c.getFatherName());
ps.setString(3, c.getMotherName());
ps.setString(4, c.getMaritalStatus());
ps.setString(5, c.getDob());
ps.setString(6, c.getGender());
ps.setString(7, c.getOccupation());

ps.setString(8, c.getMobile());
ps.setString(9, c.getAlternateMobile());
ps.setString(10, c.getEmail());

ps.setString(11, c.getAadhaar());
ps.setString(12, c.getPan());

ps.setString(13, c.getAddress());
ps.setString(14, c.getCity());
ps.setString(15, c.getState());
ps.setString(16, c.getPincode());

ps.setString(17, c.getNomineeName());
ps.setString(18, c.getRelationship());
ps.setString(19, c.getNomineeMobile());

ps.setString(20, c.getCustomerCode());
ps.setString(21, c.getCifNumber());

ps.setString(22, c.getAccountNumber());
ps.setString(23, c.getIfscCode());
ps.setString(24, c.getAccountType());
ps.setString(25, c.getBranch());
ps.setDouble(26, c.getBalance());
ps.setString(27,"NO"); // mobile_verified
ps.setString(28,"NO"); // email_verified

ps.setString(29, c.getUpiId());
ps.setString(30, c.getUpiStatus());

ps.setString(31, c.getStatus());
ps.setString(32, c.getKycStatus());

ps.setString(33, c.getPassword());
ps.setString(34, c.getTransactionPin());


        int i = ps.executeUpdate();

        if (i > 0) {
            status = true;
        }

    } catch (Exception e) {

        e.printStackTrace();

    } finally {

        try {
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    return status;
}
    public List<Customer> getAllCustomers() {

    List<Customer> list = new ArrayList<>();

    try {

        con = DBConnection.getConnection();

        String sql = "SELECT * FROM customer ORDER BY customer_id DESC";

        ps = con.prepareStatement(sql);

        rs = ps.executeQuery();

        while (rs.next()) {

            Customer c = new Customer();

            c.setCustomerId(rs.getInt("customer_id"));
            c.setFullName(rs.getString("full_name"));
            c.setFatherName(rs.getString("father_name"));
            c.setDob(rs.getString("dob"));
            c.setGender(rs.getString("gender"));
            c.setMobile(rs.getString("mobile"));
            c.setEmail(rs.getString("email"));
            c.setAadhaar(rs.getString("aadhaar"));
            c.setPan(rs.getString("pan"));
            c.setAddress(rs.getString("address"));
            c.setCity(rs.getString("city"));
            c.setState(rs.getString("state"));
            c.setPincode(rs.getString("pincode"));

            // Bank Details
            c.setAccountNumber(rs.getString("account_number"));
            c.setIfscCode(rs.getString("ifsc_code"));
            c.setAccountType(rs.getString("account_type"));
            c.setBranch(rs.getString("branch"));
            c.setBalance(rs.getDouble("balance"));
            c.setStatus(rs.getString("status"));

            list.add(c);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
    public Customer getCustomerById(int customerId) {

    Customer c = null;

    try {

        con = DBConnection.getConnection();

        String sql = "SELECT * FROM customer WHERE customer_id=?";

        ps = con.prepareStatement(sql);
        ps.setInt(1, customerId);

        rs = ps.executeQuery();

        if (rs.next()) {

            c = new Customer();

c.setCustomerId(rs.getInt("customer_id"));

c.setFullName(rs.getString("full_name"));
c.setFatherName(rs.getString("father_name"));
c.setMotherName(rs.getString("mother_name"));

c.setDob(rs.getString("dob"));
c.setGender(rs.getString("gender"));
c.setMaritalStatus(rs.getString("marital_status"));
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

c.setPassword(rs.getString("password"));
c.setTransactionPin(rs.getString("transaction_pin"));

            // Bank Details
            c.setAccountNumber(rs.getString("account_number"));
            c.setIfscCode(rs.getString("ifsc_code"));
            c.setAccountType(rs.getString("account_type"));
            c.setBranch(rs.getString("branch"));
            c.setBalance(rs.getDouble("balance"));
            c.setStatus(rs.getString("status"));
            c.setKycStatus(rs.getString("kyc_status"));

        }

    } catch (Exception e) {

        e.printStackTrace();

    } finally {

        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    return c;
}
    public boolean updateCustomer(Customer c) {

    boolean status = false;

    try {

        con = DBConnection.getConnection();

        String sql = "UPDATE customer SET "
                + "full_name=?,"
                + "father_name=?,"
                + "dob=?,"
                + "gender=?,"
                + "mobile=?,"
                + "email=?,"
                + "aadhaar=?,"
                + "pan=?,"
                + "address=?,"
                + "city=?,"
                + "state=?,"
                + "pincode=?,"
                + "account_number=?,"
                + "ifsc_code=?,"
                + "account_type=?,"
                + "branch=?,"
                + "balance=?,"
                + "status=?,"
                + "kyc_status=? "
                + "WHERE customer_id=?";

        ps = con.prepareStatement(sql);

        ps.setString(1, c.getFullName());
        ps.setString(2, c.getFatherName());
        ps.setString(3, c.getDob());
        ps.setString(4, c.getGender());
        ps.setString(5, c.getMobile());
        ps.setString(6, c.getEmail());
        ps.setString(7, c.getAadhaar());
        ps.setString(8, c.getPan());
        ps.setString(9, c.getAddress());
        ps.setString(10, c.getCity());
        ps.setString(11, c.getState());
        ps.setString(12, c.getPincode());
        ps.setString(13, c.getAccountNumber());
        ps.setString(14, c.getIfscCode());
        ps.setString(15, c.getAccountType());
        ps.setString(16, c.getBranch());
        ps.setDouble(17, c.getBalance());
        ps.setString(18, c.getStatus());
        ps.setString(19, c.getKycStatus());
        ps.setInt(20, c.getCustomerId());

        status = ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    return status;
}
    public Customer searchCustomerByMobile(String mobile) {

    Customer c = null;

    try {

        con = DBConnection.getConnection();

        String sql = "SELECT * FROM customer WHERE mobile=?";

        ps = con.prepareStatement(sql);
        ps.setString(1, mobile);

        rs = ps.executeQuery();

        if (rs.next()) {

            c = new Customer();

            c.setCustomerId(rs.getInt("customer_id"));
            c.setFullName(rs.getString("full_name"));
            c.setFatherName(rs.getString("father_name"));
            c.setDob(rs.getString("dob"));
            c.setGender(rs.getString("gender"));
            c.setMobile(rs.getString("mobile"));
            c.setEmail(rs.getString("email"));
            c.setAadhaar(rs.getString("aadhaar"));
            c.setPan(rs.getString("pan"));
            c.setAddress(rs.getString("address"));
            c.setCity(rs.getString("city"));
            c.setState(rs.getString("state"));
            c.setPincode(rs.getString("pincode"));

            // Bank Details
            c.setAccountNumber(rs.getString("account_number"));
            c.setIfscCode(rs.getString("ifsc_code"));
            c.setAccountType(rs.getString("account_type"));
            c.setBranch(rs.getString("branch"));
            c.setBalance(rs.getDouble("balance"));
c.setStatus(rs.getString("status"));
c.setKycStatus(rs.getString("kyc_status"));

        }

    } catch (Exception e) {

        e.printStackTrace();

    }

    return c;
}
    
    public Customer searchCustomerByEmail(String email) {

    Customer c = null;

    try {

        con = DBConnection.getConnection();

        String sql = "SELECT * FROM customer WHERE email=?";

        ps = con.prepareStatement(sql);
        ps.setString(1, email);

        rs = ps.executeQuery();

        if (rs.next()) {

            c = new Customer();

            c.setCustomerId(rs.getInt("customer_id"));
            c.setFullName(rs.getString("full_name"));
            c.setFatherName(rs.getString("father_name"));
            c.setDob(rs.getString("dob"));
            c.setGender(rs.getString("gender"));
            c.setMobile(rs.getString("mobile"));
            c.setEmail(rs.getString("email"));
            c.setAadhaar(rs.getString("aadhaar"));
            c.setPan(rs.getString("pan"));
            c.setAddress(rs.getString("address"));
            c.setCity(rs.getString("city"));
            c.setState(rs.getString("state"));
            c.setPincode(rs.getString("pincode"));

            // Bank Details
            c.setAccountNumber(rs.getString("account_number"));
            c.setIfscCode(rs.getString("ifsc_code"));
            c.setAccountType(rs.getString("account_type"));
            c.setBranch(rs.getString("branch"));
            c.setBalance(rs.getDouble("balance"));
            c.setStatus(rs.getString("status"));
            c.setKycStatus(rs.getString("kyc_status"));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return c;
}
    public Customer searchCustomerById(int customerId) {

    Customer c = null;

    try {

        con = DBConnection.getConnection();

        String sql = "SELECT * FROM customer WHERE customer_id=?";

        ps = con.prepareStatement(sql);
        ps.setInt(1, customerId);

        rs = ps.executeQuery();

        if (rs.next()) {

            c = new Customer();

            c.setCustomerId(rs.getInt("customer_id"));
            c.setFullName(rs.getString("full_name"));
            c.setFatherName(rs.getString("father_name"));
            c.setDob(rs.getString("dob"));
            c.setGender(rs.getString("gender"));
            c.setMobile(rs.getString("mobile"));
            c.setEmail(rs.getString("email"));
            c.setAadhaar(rs.getString("aadhaar"));
            c.setPan(rs.getString("pan"));
            c.setAddress(rs.getString("address"));
            c.setCity(rs.getString("city"));
            c.setState(rs.getString("state"));
            c.setPincode(rs.getString("pincode"));

            c.setAccountNumber(rs.getString("account_number"));
            c.setIfscCode(rs.getString("ifsc_code"));
            c.setAccountType(rs.getString("account_type"));
            c.setBranch(rs.getString("branch"));
            c.setBalance(rs.getDouble("balance"));
            c.setStatus(rs.getString("status"));
            c.setKycStatus(rs.getString("kyc_status"));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return c;
}
    public boolean deleteCustomer(int customerId) {

    boolean status = false;

    try {

        con = DBConnection.getConnection();

        String sql = "DELETE FROM customer WHERE customer_id=?";

        ps = con.prepareStatement(sql);
        ps.setInt(1, customerId);

        int i = ps.executeUpdate();

        if (i > 0) {
            status = true;
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}
    
    public boolean blockAccount(int customerId) {

    boolean status = false;

    try {

        Connection con = DBConnection.getConnection();

        String sql = "UPDATE customer SET status=? WHERE customer_id=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, "BLOCKED");
        ps.setInt(2, customerId);

        status = ps.executeUpdate() > 0;

        ps.close();
        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}
    public boolean freezeAccount(int customerId) {

    boolean status = false;

    try {

        Connection con = DBConnection.getConnection();

        String sql = "UPDATE customer SET status=? WHERE customer_id=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, "FREEZE");
        ps.setInt(2, customerId);

        status = ps.executeUpdate() > 0;

        ps.close();
        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}
    public boolean unblockAccount(int customerId) {

    boolean status = false;

    try {

        Connection con = DBConnection.getConnection();

        String sql = "UPDATE customer SET status=? WHERE customer_id=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, "ACTIVE");
        ps.setInt(2, customerId);

        status = ps.executeUpdate() > 0;

        ps.close();
        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}
    
    public boolean unfreezeAccount(int customerId) {

    boolean status = false;

    try {

        Connection con = DBConnection.getConnection();

        String sql = "UPDATE customer SET status=? WHERE customer_id=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, "ACTIVE");
        ps.setInt(2, customerId);

        status = ps.executeUpdate() > 0;

        ps.close();
        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}

// 👇 यह CustomerDAO class का आखिरी bracket रहेगा
}
