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

@WebServlet("/api/sendOtp")
public class SendOtpApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
            String mobile = request.getParameter("mobile");

            if (mobile == null || mobile.trim().length() != 10) {
                out.print("{\"status\":\"failed\",\"message\":\"Valid Mobile Number Required\"}");
                return;
            }

            CustomerDAO dao = new CustomerDAO();
            Customer customer = dao.searchCustomerByMobile(mobile);

            if (customer != null) {
                // In a real app, we would use an SMS API here.
                // For this project, we are using a mock OTP: 9897
                out.print("{\"status\":\"success\",\"message\":\"OTP sent to +91 " + mobile + "\",\"otp\":\"9897\"}");
            } else {
                out.print("{\"status\":\"failed\",\"message\":\"Mobile number not registered with SK Mini Bank\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"status\":\"error\",\"message\":\"Server Error\"}");
        } finally {
            out.close();
        }
    }
}
