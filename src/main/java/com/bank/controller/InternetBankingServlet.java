package com.bank.controller;

import com.bank.dao.InternetBankingDAO;
import com.bank.dao.ServiceRequestDAO;
import com.bank.model.InternetBanking;
import com.bank.model.ServiceRequest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InternetBankingServlet")
public class InternetBankingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            InternetBanking ib = new InternetBanking();

            int customerId = Integer.parseInt(request.getParameter("customerId"));

            ib.setCustomerId(customerId);
            ib.setAccountNumber(request.getParameter("accountNumber"));
            ib.setCustomerName(request.getParameter("customerName"));
            ib.setMobile(request.getParameter("mobile"));
            ib.setEmail(request.getParameter("email"));
            ib.setUsername(request.getParameter("username"));
            ib.setPassword(request.getParameter("password"));
            ib.setStatus("Pending");

            InternetBankingDAO dao = new InternetBankingDAO();

            boolean status = dao.saveRequest(ib);

            if (status) {

                // Save Common Service Request
                ServiceRequest sr = new ServiceRequest();

                sr.setCustomerId(customerId);
                sr.setAccountNumber(request.getParameter("accountNumber"));
                sr.setRequestType("NET_BANKING");
                sr.setRequestDetails("Net Banking Registration");

                ServiceRequestDAO serviceDAO = new ServiceRequestDAO();
                serviceDAO.saveRequest(sr);

                response.sendRedirect(request.getContextPath()
                        + "/InternetBankingListServlet?msg=success");

            } else {

                response.sendRedirect(request.getContextPath()
                        + "/admin/internet-banking-form.jsp?msg=failed");

            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(request.getContextPath()
                    + "/admin/internet-banking-form.jsp?msg=error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }
}