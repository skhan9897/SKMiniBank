package com.bank.controller;

import com.bank.dao.CustomerDAO;
import com.bank.model.Customer;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CustomerListServlet")
public class CustomerListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            CustomerDAO dao = new CustomerDAO();

            List<Customer> customerList = dao.getAllCustomers();

            request.setAttribute("customerList", customerList);

            request.getRequestDispatcher("/admin/customer-list.jsp")
                   .forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute("errorMessage", e.getMessage());

            request.getRequestDispatcher("/admin/error.jsp")
                   .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}