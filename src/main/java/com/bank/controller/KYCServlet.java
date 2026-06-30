package com.bank.controller;

import com.bank.dao.CustomerDAO;
import com.bank.dao.KYCDAO;
import com.bank.model.Customer;
import com.bank.model.KYC;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/KYCServlet")
public class KYCServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int customerId = Integer.parseInt(request.getParameter("customerId"));

            CustomerDAO customerDAO = new CustomerDAO();
            Customer customer = customerDAO.getCustomerById(customerId);

            if (customer == null) {
                response.sendRedirect(request.getContextPath()
                        + "/admin/customer-list.jsp");
                return;
            }

            request.setAttribute("customer", customer);

            request.getRequestDispatcher("/admin/kyc.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int customerId =
                    Integer.parseInt(request.getParameter("customerId"));

            CustomerDAO customerDAO = new CustomerDAO();
            Customer customer =
                    customerDAO.getCustomerById(customerId);

            if (customer == null) {

                response.sendRedirect(request.getContextPath()
                        + "/admin/customer-list.jsp");

                return;
            }

            KYC kyc = new KYC();

            kyc.setCustomerId(customerId);
            kyc.setCustomerName(customer.getFullName());
            kyc.setMobile(customer.getMobile());
            kyc.setEmail(customer.getEmail());
            kyc.setAadhaar(customer.getAadhaar());
            kyc.setPan(customer.getPan());
            kyc.setAddress(customer.getAddress());

            kyc.setKycStatus("VERIFIED");
            kyc.setVerificationDate(LocalDate.now().toString());

            KYCDAO dao = new KYCDAO();

            boolean status = dao.verifyKYC(kyc);

            if (status) {

                customer.setKycStatus("VERIFIED");

                customerDAO.updateCustomer(customer);

                response.sendRedirect(request.getContextPath()
                        + "/CustomerProfileServlet?customerId="
                        + customerId
                        + "&msg=KYC Verified Successfully");

            } else {

                response.sendRedirect(request.getContextPath()
                        + "/admin/kyc.jsp?customerId="
                        + customerId
                        + "&error=Verification Failed");
            }

        } catch (Exception e) {
    e.printStackTrace();
    response.setContentType("text/plain");
    e.printStackTrace(response.getWriter());
}

    }

}