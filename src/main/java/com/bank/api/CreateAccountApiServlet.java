package com.bank.api;

import com.bank.dao.CustomerDAO;
import com.bank.model.Customer;
import com.bank.util.DBConnection;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/createAccount")
public class CreateAccountApiServlet extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            Customer customer = new Customer();
            customer.setFullName(request.getParameter("fullName"));
            customer.setFatherName(request.getParameter("fatherName"));
            customer.setMotherName(request.getParameter("motherName"));
            customer.setDob(request.getParameter("dob"));
            customer.setGender(request.getParameter("gender"));
            customer.setMaritalStatus(request.getParameter("maritalStatus"));
            customer.setOccupation(request.getParameter("occupation"));
            customer.setMobile(request.getParameter("mobile"));
            customer.setAlternateMobile(request.getParameter("alternateMobile"));
            customer.setEmail(request.getParameter("email"));
            customer.setAadhaar(request.getParameter("aadhaar"));
            customer.setPan(request.getParameter("pan"));
            customer.setAddress(request.getParameter("address"));
            customer.setCity(request.getParameter("city"));
            customer.setState(request.getParameter("state"));
            customer.setPincode(request.getParameter("pincode"));
            customer.setNomineeName(request.getParameter("nomineeName"));
            customer.setRelationship(request.getParameter("relationship"));
            customer.setNomineeMobile(request.getParameter("nomineeMobile"));
            
            customer.setAccountType(request.getParameter("accountType"));
            customer.setBalance(Double.parseDouble(request.getParameter("balance")));
            customer.setPassword(request.getParameter("password"));
            customer.setTransactionPin(request.getParameter("transactionPin"));

            // Auto-generated details
            customer.setCustomerCode("SKC" + System.currentTimeMillis());
            customer.setCifNumber("CIF" + System.currentTimeMillis());
            customer.setAccountNumber("SKM" + (long)(Math.random() * 10000000000L));
            customer.setIfscCode("SKMB0001001");
            customer.setBranch("Digital Branch");
            customer.setUpiId(customer.getMobile() + "@skpay");
            customer.setUpiStatus("ACTIVE");
            customer.setStatus("ACTIVE");
            customer.setKycStatus("PENDING");
            customer.setPhoto("default_user.png"); // Photo upload for API can be added later

            CustomerDAO dao = new CustomerDAO();
            boolean success = dao.addCustomer(customer);

            if (success) {
                // Add to users table for login
                try (Connection con = DBConnection.getConnection();
                     PreparedStatement ps = con.prepareStatement("INSERT INTO users(username,email,password,role,status) VALUES(?,?,?,?,?)")) {
                    ps.setString(1, customer.getFullName());
                    ps.setString(2, customer.getAccountNumber());
                    ps.setString(3, customer.getPassword());
                    ps.setString(4, "CUSTOMER");
                    ps.setString(5, "ACTIVE");
                    ps.executeUpdate();
                }

                response.getWriter().write("{\"status\":\"success\", \"message\":\"Account created successfully! Account No: " + customer.getAccountNumber() + "\", \"accountNumber\":\"" + customer.getAccountNumber() + "\"}");
            } else {
                response.getWriter().write("{\"status\":\"failed\", \"message\":\"Database insertion failed\"}");
            }
        } catch (Exception e) {
            response.getWriter().write("{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}");
        }
    }
}
