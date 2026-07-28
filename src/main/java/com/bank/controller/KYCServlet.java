package com.bank.controller;

import com.bank.dao.CustomerDAO;
import com.bank.model.Customer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/KYCServlet")
public class KYCServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int customerId = Integer.parseInt(request.getParameter("customerId"));
            String kycStatus = request.getParameter("kycStatus");

            CustomerDAO dao = new CustomerDAO();
            Customer c = dao.getCustomerById(customerId);

            if (c != null) {
                c.setKycStatus(kycStatus.toUpperCase());
                boolean success = dao.updateCustomer(c);

                if (success) {
                    response.sendRedirect(request.getContextPath() + "/admin/kyc-success.jsp?msg=updated");
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/kyc.jsp?error=update_failed");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/kyc.jsp?error=not_found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/kyc.jsp?error=exception");
        }
    }
}
