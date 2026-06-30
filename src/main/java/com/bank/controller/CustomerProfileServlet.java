package com.bank.controller;

import com.bank.dao.CustomerDAO;
import com.bank.model.Customer;
import com.bank.dao.FixedDepositDAO;
import com.bank.dao.DebitCardDAO;
import com.bank.model.FixedDeposit;
import com.bank.model.DebitCard;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CustomerProfileServlet")
public class CustomerProfileServlet extends HttpServlet {

    @Override
protected void doGet(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {

    try {

        int customerId = Integer.parseInt(request.getParameter("customerId"));

        // Customer
        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = customerDAO.searchCustomerById(customerId);

        // Debit Card
        DebitCardDAO debitDAO = new DebitCardDAO();
        DebitCard card = debitDAO.getCardByCustomerId(customerId);

        // Fixed Deposit
        FixedDepositDAO fdDAO = new FixedDepositDAO();
        FixedDeposit fd = fdDAO.getFDByCustomerId(customerId);

        request.setAttribute("customer", customer);
        request.setAttribute("card", card);
        request.setAttribute("fd", fd);

        request.getRequestDispatcher("/admin/customer-profile.jsp")
               .forward(request, response);

    } catch (Exception e) {
    e.printStackTrace();

    try {
        response.setContentType("text/plain");
        e.printStackTrace(new java.io.PrintWriter(response.getWriter(), true));
    } catch (Exception ex) {
        ex.printStackTrace();
    }
}
}
}