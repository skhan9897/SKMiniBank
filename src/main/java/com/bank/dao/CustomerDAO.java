package com.bank.dao;

import com.bank.model.Customer;
import com.bank.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public boolean addCustomer(Customer c) throws SQLException {
        // Step 1: Forcefully ensure 'photo' column exists (Direct ALTER attempt)
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement()) {
            st.executeUpdate("ALTER TABLE customer ADD COLUMN photo VARCHAR(255) DEFAULT 'default_user.png'");
        } catch (Exception e) {
            // Silence if column already exists
        }

        String sql = "INSERT INTO customer(full_name,father_name,mother_name,marital_status,dob,gender,occupation,mobile,alternate_mobile,email,aadhaar,pan,address,city,state,pincode,nominee_name,relationship,nominee_mobile,customer_code,cif_number,account_number,ifsc_code,account_type,branch,balance,mobile_verified,email_verified,upi_id,upi_status,status,kyc_status,password,transaction_pin,photo) VALUES (?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?, ?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
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
            ps.setString(27,"NO");
            ps.setString(28,"NO");
            ps.setString(29, c.getUpiId());
            ps.setString(30, c.getUpiStatus());
            ps.setString(31, c.getStatus());
            ps.setString(32, c.getKycStatus());
            ps.setString(33, c.getPassword());
            ps.setString(34, c.getTransactionPin());
            ps.setString(35, c.getPhoto());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updatePhoto(int customerId, String photoPath) {
        String sql = "UPDATE customer SET photo=? WHERE customer_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, photoPath);
            ps.setInt(2, customerId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer ORDER BY customer_id DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapCustomer(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Customer getCustomerById(int customerId) {
        String sql = "SELECT * FROM customer WHERE customer_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapCustomer(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateCustomer(Customer c) {
        String sql = "UPDATE customer SET full_name=?, father_name=?, dob=?, gender=?, mobile=?, email=?, aadhaar=?, pan=?, address=?, city=?, state=?, pincode=?, account_number=?, ifsc_code=?, account_type=?, branch=?, balance=?, status=?, kyc_status=? WHERE customer_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
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
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Customer searchCustomerByMobile(String mobile) {
        String sql = "SELECT * FROM customer WHERE mobile=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, mobile);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapCustomer(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Customer searchCustomerByEmail(String email) {
        String sql = "SELECT * FROM customer WHERE email=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapCustomer(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Customer searchCustomerById(int customerId) {
        return getCustomerById(customerId);
    }

    public boolean deleteCustomer(int customerId) {
        String sql = "DELETE FROM customer WHERE customer_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Customer getCustomerByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM customer WHERE account_number=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapCustomer(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean resetPassword(String accountNumber, String mobile, String newPassword) {
        String sql = "UPDATE customer SET password=? WHERE account_number=? AND mobile=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newPassword);
            ps.setString(2, accountNumber);
            ps.setString(3, mobile);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
        c.setPassword(rs.getString("password"));
        
        try {
            c.setPhoto(rs.getString("photo"));
        } catch (Exception e) {
            // photo column might be missing
            c.setPhoto(null);
        }

        return c;
    }
}
