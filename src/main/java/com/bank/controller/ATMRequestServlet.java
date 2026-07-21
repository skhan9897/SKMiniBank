package com.bank.controller;

import com.bank.dao.ATMRequestDAO;
import com.bank.model.ATMRequest;

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
            String accountNumber = request.getParameter("accountNumber");
            String cardType = request.getParameter("cardType");

            ATMRequest atm = new ATMRequest();
            atm.setCustomerId(customerId);
            atm.setAccountNumber(accountNumber);
            atm.setCardType(cardType);
            atm.setRequestDate(LocalDate.now().toString());
            atm.setStatus("PENDING");

            ATMRequestDAO dao = new ATMRequestDAO();

            if (dao.applyATM(atm)) {

                response.sendRedirect(request.getContextPath()
                        + "/customer/atm-request-success.jsp");

            } else {

                response.sendRedirect(request.getContextPath()
                        + "/customer/atm-request.jsp?error=1");

            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(request.getContextPath()
                    + "/customer/atm-request.jsp?error=2");
        }
    }
}