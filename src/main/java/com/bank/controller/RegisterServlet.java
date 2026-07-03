package com.bank.controller;

import com.bank.dao.CustomerDAO;
import com.bank.model.Customer;
import com.bank.util.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
        // Bank Details Auto Generate

customer.setCustomerCode("SKC" + System.currentTimeMillis());

customer.setCifNumber("CIF" + System.currentTimeMillis());

customer.setAccountNumber("SKM" + System.currentTimeMillis());

customer.setIfscCode("SKMB0001001");

customer.setAccountType(request.getParameter("accountType"));

customer.setBranch("Bareilly Main Branch");

customer.setBalance(Double.parseDouble(request.getParameter("balance")));

customer.setUpiId(request.getParameter("mobile") + "@skpay");

customer.setUpiStatus("ACTIVE");

customer.setStatus("ACTIVE");

customer.setKycStatus("PENDING");

customer.setPassword(request.getParameter("password"));

customer.setTransactionPin(request.getParameter("transactionPin"));

        String password = request.getParameter("password");

        CustomerDAO dao = new CustomerDAO();

        boolean status = dao.addCustomer(customer);

        if (status) {

            try {

                Connection con = DBConnection.getConnection();

                String sql = "INSERT INTO users(username,email,password,role,status) VALUES(?,?,?,?,?)";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, customer.getFullName());
                ps.setString(2, customer.getEmail());
                ps.setString(3, password);
                ps.setString(4, "CUSTOMER");
                ps.setString(5, "ACTIVE");

                ps.executeUpdate();

                ps.close();
                con.close();

                response.sendRedirect("login.jsp?success=Registration Successful");

            } catch (Exception e) {
    e.printStackTrace();
    response.getWriter().println(e.getMessage());
}

        } else {

            response.sendRedirect("register.jsp?error=Registration Failed");

        }

    }

}