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
            String accountNumber = request.getParameter("accountNumber");
            String password = request.getParameter("password");

            if (mobile == null || mobile.trim().length() != 10) {
                out.print("{\"status\":\"failed\",\"message\":\"Valid Mobile Number Required\"}");
                return;
            }

            CustomerDAO dao = new CustomerDAO();
            Customer customer = null;

            if (accountNumber != null && !accountNumber.trim().isEmpty()) {
                customer = dao.getCustomerByAccountNumber(accountNumber.trim());
                if (customer == null) {
                    out.print("{\"status\":\"failed\",\"message\":\"Account number not found\"}");
                    return;
                }
                // verify mobile matches
                if (!mobile.equals(customer.getMobile())) {
                    out.print("{\"status\":\"failed\",\"message\":\"Mobile number does not match account records\"}");
                    return;
                }
                // verify password if provided
                if (password == null || password.trim().isEmpty() || !password.equals(customer.getPassword())) {
                    out.print("{\"status\":\"failed\",\"message\":\"Invalid account password\"}");
                    return;
                }
            } else {
                // fallback: search by mobile only
                customer = dao.searchCustomerByMobile(mobile);
                if (customer == null) {
                    out.print("{\"status\":\"failed\",\"message\":\"Mobile number not registered with SK Mini Bank\"}");
                    return;
                }
            }

            // At this point, account/mobile/password validated. Send OTP (mock for now)
            String otp = String.valueOf((int)(Math.random()*900000) + 100000); // 6-digit mock OTP
            out.print("{\"status\":\"success\",\"message\":\"OTP sent to +91 " + mobile + "\",\"otp\":\"" + otp + "\"}");

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"status\":\"error\",\"message\":\"Server Error\"}");
        } finally {
            out.close();
        }
    }
}
