package com.bank.controller;

import com.bank.dao.CustomerDAO;
import com.bank.dao.ServiceRequestDAO;
import com.bank.model.Customer;
import com.bank.model.ServiceRequest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ATMRequestServlet")
public class ATMRequestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int customerId = Integer.parseInt(request.getParameter("customerId"));
            String cardType = request.getParameter("cardType");

            CustomerDAO customerDAO = new CustomerDAO();
            Customer customer = customerDAO.getCustomerById(customerId);

            if (customer == null) {

                response.sendRedirect(request.getContextPath()
                        + "/customer/atm-request.jsp?msg=Customer Not Found");
                return;
            }

            // Save ATM Request in Common Service Request Table
            ServiceRequest sr = new ServiceRequest();

            sr.setCustomerId(customerId);
            sr.setAccountNumber(customer.getAccountNumber());
            sr.setRequestType("ATM_CARD");
            sr.setRequestDetails("ATM Card Type : " + cardType);

            ServiceRequestDAO dao = new ServiceRequestDAO();

            boolean status = dao.saveRequest(sr);

            if (status) {

                response.sendRedirect(request.getContextPath()
                        + "/CustomerProfileServlet?customerId="
                        + customerId
                        + "&msg=ATM Request Submitted Successfully");

            } else {

                response.sendRedirect(request.getContextPath()
                        + "/customer/atm-request.jsp?msg=Request Failed");
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(request.getContextPath()
                    + "/customer/atm-request.jsp?msg=Server Error");
        }
    }
}