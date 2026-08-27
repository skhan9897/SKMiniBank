package com.bank.controller;

import com.bank.dao.CustomerDAO;
import com.bank.model.Customer;

import java.io.IOException;
import java.net.URLEncoder;

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

        try {
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

            // Auto-generated defaults for manual entry
            customer.setCustomerCode("SKC" + System.currentTimeMillis());
            customer.setCifNumber("CIF" + System.currentTimeMillis());
            customer.setAccountNumber("SKM" + (long)(Math.random() * 10000000000L));
            customer.setIfscCode("SKMB0001001");
            customer.setBranch("Bareilly Main Branch");
            customer.setUpiId(customer.getMobile() + "@skpay");
            customer.setUpiStatus("ACTIVE");
            customer.setStatus("ACTIVE");
            customer.setKycStatus("PENDING");
            customer.setPhoto("default_user.png");
            customer.setBalance(1000.0); // Default opening
            customer.setPassword("Pass@123"); // Default
            customer.setTransactionPin("9897"); // Default

            CustomerDAO dao = new CustomerDAO();
            boolean status = dao.addCustomer(customer);

            if (status) {
                response.sendRedirect("admin/customer-list.jsp?msg=Customer Added Successfully");
            } else {
                response.sendRedirect("admin/add-customer.jsp?msg=Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("admin/add-customer.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
