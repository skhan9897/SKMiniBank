package com.bank.controller;

import com.bank.dao.CustomerDAO;
import com.bank.model.Customer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateCustomerServlet")
public class UpdateCustomerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Customer c = new Customer();

            c.setCustomerId(Integer.parseInt(request.getParameter("customerId")));

            c.setFullName(request.getParameter("fullName"));
            c.setMobile(request.getParameter("mobile"));
            c.setEmail(request.getParameter("email"));
            c.setCity(request.getParameter("city"));
            c.setState(request.getParameter("state"));

            // पुराने डेटा को सुरक्षित रखने के लिए
            CustomerDAO dao = new CustomerDAO();
            Customer old = dao.getCustomerById(c.getCustomerId());

            c.setFatherName(old.getFatherName());
            c.setDob(old.getDob());
            c.setGender(old.getGender());
            c.setAadhaar(old.getAadhaar());
            c.setPan(old.getPan());
            c.setAddress(old.getAddress());
            c.setPincode(old.getPincode());

            c.setAccountNumber(old.getAccountNumber());
            c.setIfscCode(old.getIfscCode());
            c.setAccountType(old.getAccountType());
            c.setBranch(old.getBranch());
            c.setBalance(old.getBalance());
            c.setStatus(old.getStatus());
            c.setKycStatus(old.getKycStatus());

            boolean status = dao.updateCustomer(c);

            if (status) {
                response.sendRedirect("admin/customer-list.jsp?msg=Customer Updated Successfully");
            } else {
                response.sendRedirect("admin/edit-customer.jsp?id="
                        + c.getCustomerId() + "&error=Update Failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("admin/customer-list.jsp?error="
                    + e.getMessage());
        }
    }
}