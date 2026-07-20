package com.bank.controller;

import com.bank.dao.ServiceRequestDAO;
import com.bank.model.ServiceRequest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ChequeBookRequestServlet")
public class ChequeBookRequestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            ServiceRequest sr = new ServiceRequest();

            sr.setCustomerId(Integer.parseInt(request.getParameter("customerId")));
            sr.setAccountNumber(request.getParameter("accountNumber"));
            sr.setRequestType("CHEQUE_BOOK");

            String details = "Cheque Book Request | Leaves : "
                    + request.getParameter("leaves")
                    + " | Address : "
                    + request.getParameter("address");

            sr.setRequestDetails(details);

            ServiceRequestDAO dao = new ServiceRequestDAO();

            boolean status = dao.saveRequest(sr);

            if (status) {

                response.sendRedirect("customer/cheque-book-request.jsp?msg=success");

            } else {

                response.sendRedirect("customer/cheque-book-request.jsp?msg=error");

            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect("customer/cheque-book-request.jsp?msg=error");
        }
    }
}