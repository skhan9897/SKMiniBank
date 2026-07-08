package com.bank.controller;

import com.bank.dao.InternetBankingDAO;
import com.bank.model.InternetBanking;

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

            ib.setCustomerId(Integer.parseInt(request.getParameter("customerId")));
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