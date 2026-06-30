package com.bank.controller;

import com.bank.dao.CustomerDAO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/DeleteCustomerServlet")
public class DeleteCustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int customerId = Integer.parseInt(request.getParameter("id"));

            CustomerDAO dao = new CustomerDAO();

            boolean status = dao.deleteCustomer(customerId);

            if (status) {

                response.sendRedirect(request.getContextPath() + "/admin/customer-list.jsp?success=Customer Deleted Successfully");

            } else {


                response.sendRedirect(request.getContextPath() + "/admin/customer-list.jsp?error=Something Went Wrong");
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect("customer-list.jsp?error=Something Went Wrong");

        }

    }

}