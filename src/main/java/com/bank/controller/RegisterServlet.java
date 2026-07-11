package com.bank.controller;

import com.bank.dao.CustomerDAO;
import com.bank.model.Customer;
import com.bank.util.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Customer customer = new Customer();

            // Personal Details
            customer.setFullName(request.getParameter("fullName"));
            customer.setFatherName(request.getParameter("fatherName"));
            customer.setMotherName(request.getParameter("motherName"));
            customer.setDob(request.getParameter("dob"));
            customer.setGender(request.getParameter("gender"));
            customer.setMaritalStatus(request.getParameter("maritalStatus"));
            customer.setOccupation(request.getParameter("occupation"));

            // Contact Details
            customer.setMobile(request.getParameter("mobile"));
            customer.setAlternateMobile(request.getParameter("alternateMobile"));
            customer.setEmail(request.getParameter("email"));

            // Identity
            customer.setAadhaar(request.getParameter("aadhaar"));
            customer.setPan(request.getParameter("pan"));

            // Address
            customer.setAddress(request.getParameter("address"));
            customer.setCity(request.getParameter("city"));
            customer.setState(request.getParameter("state"));
            customer.setPincode(request.getParameter("pincode"));

            // Nominee
            customer.setNomineeName(request.getParameter("nomineeName"));
            customer.setRelationship(request.getParameter("relationship"));
            customer.setNomineeMobile(request.getParameter("nomineeMobile"));

            // Bank Details
            customer.setCustomerCode("SKC" + System.currentTimeMillis());
            customer.setCifNumber("CIF" + System.currentTimeMillis());
            customer.setAccountNumber("SKM" + System.currentTimeMillis());
            customer.setIfscCode("SKMB0001001");
            customer.setAccountType(request.getParameter("accountType"));
            customer.setBranch("Bareilly Main Branch");

            customer.setBalance(
                    Double.parseDouble(request.getParameter("balance")));

            customer.setUpiId(customer.getMobile() + "@skpay");
            customer.setUpiStatus("ACTIVE");
            customer.setStatus("ACTIVE");
            customer.setKycStatus("PENDING");

            customer.setPassword(request.getParameter("password"));
            customer.setTransactionPin(
                    request.getParameter("transactionPin"));

            CustomerDAO dao = new CustomerDAO();

            boolean status = dao.addCustomer(customer);

            if (!status) {

                response.sendRedirect(
                        "register.jsp?error=Registration Failed");

                return;

            }

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO users(username,email,password,role,status)"
                    + " VALUES(?,?,?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, customer.getFullName());

            // IMPORTANT:
            // Customer Login = Account Number
            ps.setString(2, customer.getAccountNumber());

            ps.setString(3, customer.getPassword());

            ps.setString(4, "CUSTOMER");

            ps.setString(5, "ACTIVE");

            ps.executeUpdate();

            ps.close();

            // Customer ID निकालना
            PreparedStatement ps2 =
                    con.prepareStatement(
                    "SELECT customer_id FROM customer WHERE account_number=?");

            ps2.setString(1,
                    customer.getAccountNumber());

            ResultSet rs = ps2.executeQuery();

            int customerId = 0;

            if (rs.next()) {

                customerId = rs.getInt("customer_id");

            }

            rs.close();
            ps2.close();
            con.close();

            // Receipt Open
            response.sendRedirect(
                    request.getContextPath()
                    + "/AccountReceiptServlet?customerId="
                    + customerId);

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(e.getMessage());

        }

    }

}