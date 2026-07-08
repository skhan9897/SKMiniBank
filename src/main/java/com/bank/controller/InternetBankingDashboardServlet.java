package com.bank.controller;

import com.bank.dao.InternetBankingDAO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InternetBankingDashboardServlet")
public class InternetBankingDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            InternetBankingDAO dao = new InternetBankingDAO();

            request.setAttribute("totalRequests",
                    dao.getTotalRequests());

            request.setAttribute("approvedRequests",
                    dao.getApprovedRequests());

            request.setAttribute("pendingRequests",
                    dao.getPendingRequests());

            request.setAttribute("rejectedRequests",
                    dao.getRejectedRequests());

            request.getRequestDispatcher(
                    "/admin/internet-banking-dashboard.jsp")
                    .forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/internet-banking-dashboard.jsp?msg=error");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}