package com.bank.controller;

import com.bank.model.Customer;
import com.bank.service.LoginService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accountNumber = request.getParameter("accountNumber");
        String password = request.getParameter("password");

        try {

            LoginService service = new LoginService();

            Customer customer = service.login(accountNumber, password);

            if (customer != null) {

                HttpSession session = request.getSession(true);

                session.setAttribute("customerId", customer.getCustomerId());
                session.setAttribute("customerCode", customer.getCustomerCode());
                session.setAttribute("customerName", customer.getFullName());
                session.setAttribute("accountNumber", customer.getAccountNumber());
                session.setAttribute("email", customer.getEmail());
                session.setAttribute("mobile", customer.getMobile());
                session.setAttribute("branch", customer.getBranch());
                session.setAttribute("accountType", customer.getAccountType());
                session.setAttribute("balance", customer.getBalance());
                session.setAttribute("accountStatus", customer.getStatus());
                session.setAttribute("role", "CUSTOMER");

                response.sendRedirect(
                        request.getContextPath()
                        + "/CustomerProfileServlet?customerId="
                        + customer.getCustomerId());

            } else {

                request.setAttribute("error",
                        "Invalid Account Number or Password");

                request.getRequestDispatcher("/login.jsp")
                        .forward(request, response);
            }

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute("error",
                    "Database Error : " + e.getMessage());

            request.getRequestDispatcher("/login.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }
}