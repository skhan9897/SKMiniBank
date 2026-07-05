package com.bank.api;


import com.bank.dao.LoginDAO;
import com.bank.model.Customer;

import java.io.IOException;

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

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        LoginDAO dao = new LoginDAO();

        Customer customer = dao.login(email, password);

        if (customer != null) {

            String json =
                    "{"
                    + "\"status\":\"success\","
                    + "\"customerId\":\"" + customer.getCustomerId() + "\","
                    + "\"customerName\":\"" + customer.getFullName() + "\","
                    + "\"accountNumber\":\"" + customer.getAccountNumber() + "\","
                    + "\"balance\":\"" + customer.getBalance() + "\""
                    + "}";

            response.getWriter().print(json);

        } else {

            response.getWriter().print(
                    "{\"status\":\"failed\"}"
            );

        }

    }
}