package com.bank.controller;

import com.bank.dao.CustomerDAO;
import com.bank.model.Customer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AccountReceiptServlet")
public class AccountReceiptServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int customerId = Integer.parseInt(
                    request.getParameter("customerId"));

            CustomerDAO dao = new CustomerDAO();

            Customer customer = dao.getCustomerById(customerId);

            if (customer == null) {

                response.getWriter().println("Customer Not Found");

                return;
            }

            request.setAttribute("customer", customer);

            request.getRequestDispatcher(
                    "/admin/account-receipt.jsp")
                    .forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "Error : " + e.getMessage());
        }
    }
}