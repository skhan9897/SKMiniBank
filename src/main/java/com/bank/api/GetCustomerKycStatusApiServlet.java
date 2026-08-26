package com.bank.api;

import com.bank.dao.CustomerDAO;
import com.bank.model.Customer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/customer/kyc-status")
public class GetCustomerKycStatusApiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String customerIdStr = request.getParameter("customerId");
            if (customerIdStr == null) {
                response.getWriter().print("{\"success\":false,\"message\":\"Customer ID required\"}");
                return;
            }

            int customerId = Integer.parseInt(customerIdStr);
            CustomerDAO dao = new CustomerDAO();
            Customer customer = dao.getCustomerById(customerId);

            if (customer == null) {
                response.getWriter().print("{\"success\":false,\"message\":\"Customer not found\"}");
                return;
            }

            String status = customer.getKycStatus();
            if (status == null || status.isEmpty()) {
                status = "PENDING";
            }

            response.getWriter().print("{\"success\":true,\"kycStatus\":\"" + status + "\"}");
        } catch (Exception e) {
            response.getWriter().print("{\"success\":false,\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}
