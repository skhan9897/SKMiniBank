package com.bank.api;

import com.bank.dao.CustomerDAO;
import com.bank.model.Customer;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/updateProfile")
public class UpdateProfileApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
            String customerIdStr = request.getParameter("customerId");
            String mobile = request.getParameter("mobile");
            String email = request.getParameter("email");

            if (customerIdStr == null || mobile == null || email == null) {
                out.print("{\"status\":\"failed\",\"message\":\"Missing required fields\"}");
                return;
            }

            int customerId = Integer.parseInt(customerIdStr);
            CustomerDAO dao = new CustomerDAO();
            
            // Fetch current customer to preserve other fields
            Customer c = dao.getCustomerById(customerId);
            if (c != null) {
                c.setMobile(mobile);
                c.setEmail(email);
                boolean success = dao.updateCustomer(c);

                if (success) {
                    out.print("{\"status\":\"success\",\"message\":\"Profile Updated Successfully\"}");
                } else {
                    out.print("{\"status\":\"failed\",\"message\":\"Update Failed\"}");
                }
            } else {
                out.print("{\"status\":\"failed\",\"message\":\"Customer not found\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"status\":\"error\",\"message\":\"Server Error\"}");
        } finally {
            out.close();
        }
    }
}
