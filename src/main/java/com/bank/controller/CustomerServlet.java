package com.bank.controller;

import com.bank.dao.CustomerDAO;
import com.bank.model.Customer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/customer-list")
public class CustomerServlet extends HttpServlet {

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

        CustomerDAO dao = new CustomerDAO();

        boolean status = dao.addCustomer(customer);

        if (status) {

            response.sendRedirect("admin/customer-list.jsp?msg=Customer Added Successfully");

        } else {

            response.sendRedirect("admin/add-customer.jsp?msg=Failed");

        }

    }

}