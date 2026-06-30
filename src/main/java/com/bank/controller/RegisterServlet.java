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

        customer.setFullName(request.getParameter("fullname"));
        customer.setFatherName(request.getParameter("fathername"));
        customer.setDob(request.getParameter("dob"));
        customer.setGender(request.getParameter("gender"));
        customer.setMobile(request.getParameter("mobile"));
        customer.setEmail(request.getParameter("email"));
        customer.setAadhaar(request.getParameter("aadhaar"));
        customer.setPan(request.getParameter("pan"));
        customer.setAddress(request.getParameter("address"));
        customer.setCity(request.getParameter("city"));
        customer.setState(request.getParameter("state"));
        customer.setPincode(request.getParameter("pincode"));

        // Bank Details Auto Generate
        customer.setAccountNumber("SKM" + System.currentTimeMillis());
        customer.setIfscCode("SKMB0001001");
        customer.setAccountType("SAVING");
        customer.setBranch("Bareilly Main Branch");
        customer.setBalance(5000.00);
        customer.setStatus("ACTIVE");

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