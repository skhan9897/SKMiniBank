package com.bank.controller;

import com.bank.dao.CustomerDAO;
import com.bank.dao.DebitCardDAO;
import com.bank.dao.FixedDepositDAO;
import com.bank.model.Customer;
import com.bank.model.DebitCard;
import com.bank.model.FixedDeposit;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CustomerProfileServlet")
public class CustomerProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            HttpSession session = request.getSession(false);

            String id = request.getParameter("customerId");

            // URL में customerId नहीं है तो Session से लो
            if ((id == null || id.trim().isEmpty()) && session != null) {

                Object obj = session.getAttribute("customerId");

                if (obj != null) {
                    id = obj.toString();
                }
            }

            // Login नहीं है
            if (id == null || id.trim().isEmpty()) {

                response.sendRedirect(request.getContextPath()
                        + "/login.jsp");

                return;
            }

            int customerId = Integer.parseInt(id);

            CustomerDAO customerDAO = new CustomerDAO();
            Customer customer = customerDAO.searchCustomerById(customerId);

            if (customer == null) {

                response.sendRedirect(request.getContextPath()
                        + "/admin/customer-list.jsp?error=Customer Not Found");

                return;
            }

            DebitCardDAO debitDAO = new DebitCardDAO();
            DebitCard card = debitDAO.getCardByCustomerId(customerId);

            FixedDepositDAO fdDAO = new FixedDepositDAO();
            FixedDeposit fd = fdDAO.getFDByCustomerId(customerId);

            // Message Parameter
            String msg = request.getParameter("msg");

            if (msg != null && !msg.trim().isEmpty()) {
                request.setAttribute("msg", msg);
            }

            request.setAttribute("customer", customer);
            request.setAttribute("card", card);
            request.setAttribute("fd", fd);

            request.getRequestDispatcher("/admin/customer-profile.jsp")
                    .forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            throw new ServletException("Unable to Load Customer Profile", e);
        }

    }

}