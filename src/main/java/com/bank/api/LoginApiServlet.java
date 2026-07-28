package com.bank.api;

import com.bank.model.Customer;
import com.bank.service.LoginService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/login")
public class LoginApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
            String mobile = request.getParameter("mobile");
            String password = request.getParameter("password");
            String otp = request.getParameter("otp");

            if (mobile == null || password == null || otp == null) {
                out.print("{\"status\":\"failed\",\"message\":\"Missing required fields\"}");
                return;
            }

            // Verify OTP (Mock: 9897)
            if (!"9897".equals(otp)) {
                out.print("{\"status\":\"failed\",\"message\":\"Invalid OTP entered\"}");
                return;
            }

            LoginService service = new LoginService();
            // Use the new mobile-based login method
            Customer customer = service.loginByMobile(mobile, password);

            if (customer != null) {
                out.print("{");
                out.print("\"status\":\"success\",");
                out.print("\"customerId\":" + customer.getCustomerId() + ",");
                out.print("\"customerName\":\"" + customer.getFullName() + "\",");
                out.print("\"accountNumber\":\"" + customer.getAccountNumber() + "\",");
                out.print("\"mobile\":\"" + customer.getMobile() + "\",");
                out.print("\"email\":\"" + customer.getEmail() + "\",");
                out.print("\"balance\":" + customer.getBalance() + ",");
                out.print("\"accountStatus\":\"" + customer.getStatus() + "\"");
                out.print("}");
            } else {
                out.print("{\"status\":\"failed\",\"message\":\"Incorrect Mobile Number or Password\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"status\":\"error\",\"message\":\"Server Error\"}");
        } finally {
            out.close();
        }
    }
}
