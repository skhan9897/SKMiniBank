package com.bank.controller;

import com.bank.dao.ATMRequestDAO;
import com.bank.dao.CustomerDAO;
import com.bank.dao.DebitCardDAO;
import com.bank.model.ATMRequest;
import com.bank.model.Customer;
import com.bank.model.DebitCard;

import java.io.IOException;
import java.time.LocalDate;

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

            // Save ATM Request
            ATMRequest atm = new ATMRequest();

            atm.setCustomerId(customerId);
            atm.setCardType(cardType);
            atm.setRequestDate(LocalDate.now().toString());
            atm.setStatus("PENDING");

            ATMRequestDAO atmDAO = new ATMRequestDAO();

            boolean requestSaved = atmDAO.applyATM(atm);

            if (!requestSaved) {

                response.sendRedirect(request.getContextPath()
                        + "/customer/atm-request.jsp?msg=Request Failed");
                return;
            }

            // Generate Virtual Debit Card
            DebitCard card = new DebitCard();

            card.setCustomerId(customerId);
            card.setCustomerName(customer.getFullName());
            card.setAccountNumber(customer.getAccountNumber());
            card.setCardType(cardType);

            card.setCardNumber(DebitCardDAO.generateCardNumber());
            card.setExpiryDate(DebitCardDAO.generateExpiry());
            card.setCvv(DebitCardDAO.generateCVV());

            card.setStatus("ACTIVE");
            card.setDispatchStatus("Virtual Card Ready");
            card.setExpectedDelivery("Within 7 Working Days");

            DebitCardDAO debitDAO = new DebitCardDAO();

            debitDAO.saveCard(card);

            // Redirect to Customer Profile
            response.sendRedirect(
                    request.getContextPath()
                    + "/CustomerProfileServlet?customerId="
                    + customerId
                    + "&msg=ATM Request Submitted Successfully");

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    request.getContextPath()
                    + "/customer/atm-request.jsp?msg=Server Error");
        }
    }
}