package com.bank.controller;

import com.bank.service.ServiceRequestService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ATMRequestServlet")
public class ATMRequestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("customerId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {

            int customerId = Integer.parseInt(
                    session.getAttribute("customerId").toString());

            String accountNumber = request.getParameter("accountNumber");
            String cardType = request.getParameter("cardType");

            ServiceRequestService service = new ServiceRequestService();

            boolean status = service.submitATMRequest(
                    customerId,
                    accountNumber,
                    cardType);

            if (status) {

                response.sendRedirect(request.getContextPath()
                        + "/customer/atm-request.jsp?msg=success");

            } else {

                response.sendRedirect(request.getContextPath()
                        + "/customer/atm-request.jsp?msg=failed");
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(request.getContextPath()
                    + "/customer/atm-request.jsp?msg=error");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

}