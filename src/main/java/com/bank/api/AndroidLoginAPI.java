package com.bank.api;

import com.bank.dao.LoginDAO;
import com.bank.model.Customer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/login")
public class AndroidLoginAPI extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {

            String email = request.getParameter("email");
            String password = request.getParameter("password");

            if (email == null || password == null
                    || email.trim().isEmpty()
                    || password.trim().isEmpty()) {

                out.print("{"
                        + "\"status\":\"failed\","
                        + "\"message\":\"Email and Password Required\""
                        + "}");
                return;
            }

            LoginDAO dao = new LoginDAO();

            Customer customer = dao.login(email.trim(), password.trim());

            if (customer != null) {

                String json = "{"
                        + "\"status\":\"success\","
                        + "\"customerId\":\"" + customer.getCustomerId() + "\","
                        + "\"customerCode\":\"" + customer.getCustomerCode() + "\","
                        + "\"customerName\":\"" + customer.getFullName() + "\","
                        + "\"accountNumber\":\"" + customer.getAccountNumber() + "\","
                        + "\"email\":\"" + customer.getEmail() + "\","
                        + "\"mobile\":\"" + customer.getMobile() + "\","
                        + "\"balance\":\"" + customer.getBalance() + "\","
                        + "\"upiId\":\"" + customer.getUpiId() + "\","
                        + "\"statusText\":\"" + customer.getStatus() + "\""
                        + "}";

                out.print(json);

            } else {

                out.print("{"
                        + "\"status\":\"failed\","
                        + "\"message\":\"Invalid Email or Password\""
                        + "}");

            }

        } catch (Exception e) {

            e.printStackTrace();

            out.print("{"
                    + "\"status\":\"error\","
                    + "\"message\":\"" + e.getMessage().replace("\"", "'") + "\""
                    + "}");

        } finally {

            out.flush();
            out.close();

        }
    }
}