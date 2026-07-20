package com.bank.controller;

import com.bank.dao.MobileBankingDAO;
import com.bank.dao.ServiceRequestDAO;
import com.bank.model.MobileBanking;
import com.bank.model.ServiceRequest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MobileBankingServlet")
public class MobileBankingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int customerId = Integer.parseInt(request.getParameter("customerId"));

            MobileBanking mb = new MobileBanking();

            mb.setCustomerId(customerId);
            mb.setAccountNumber(request.getParameter("accountNumber"));
            mb.setCustomerName(request.getParameter("customerName"));
            mb.setMobile(request.getParameter("mobile"));
            mb.setEmail(request.getParameter("email"));
            mb.setUsername(request.getParameter("username"));
            mb.setPassword(request.getParameter("password"));
            mb.setStatus("Pending");

            MobileBankingDAO dao = new MobileBankingDAO();

            boolean status = dao.saveRequest(mb);

            if (status) {

                // Save Common Service Request
                ServiceRequest sr = new ServiceRequest();

                sr.setCustomerId(customerId);
                sr.setAccountNumber(request.getParameter("accountNumber"));
                sr.setRequestType("MOBILE_BANKING");
                sr.setRequestDetails("Mobile Banking Registration");

                ServiceRequestDAO serviceDAO = new ServiceRequestDAO();
                serviceDAO.saveRequest(sr);

                response.sendRedirect(request.getContextPath()
                        + "/MobileBankingListServlet?msg=success");

            } else {

                response.sendRedirect(request.getContextPath()
                        + "/customer/mobile-banking.jsp?msg=failed");

            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(request.getContextPath()
                    + "/customer/mobile-banking.jsp?msg=error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }
}