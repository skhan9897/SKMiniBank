package com.bank.controller;

import com.bank.dao.CustomerDAO;
import com.bank.model.Customer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CustomerDashboardServlet")
public class CustomerDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        System.out.println("Session = " + session);
System.out.println("Email = " + session.getAttribute("email"));

        if (session == null || session.getAttribute("email") == null) {

            response.sendRedirect("login.jsp");
            return;

        }

        String email = session.getAttribute("email").toString();

        CustomerDAO dao = new CustomerDAO();

        Customer customer = dao.searchCustomerByEmail(email);

        request.setAttribute("customer", customer);

        request.getRequestDispatcher("customer/dashboard.jsp")
                .forward(request, response);

    }

}