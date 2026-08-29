package com.bank.controller;

import com.bank.dao.DashboardDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        javax.servlet.http.HttpSession session = request.getSession(false);
        String ctx = request.getContextPath();
        if ("/".equals(ctx)) ctx = "";

        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(ctx + "/SKMiniBankadmin-login.jsp");
            return;
        }

        DashboardDAO dao = new DashboardDAO();
        com.bank.dao.TransactionDAO tdao = new com.bank.dao.TransactionDAO();

        request.setAttribute("totalCustomers", dao.getTotalCustomers());
        request.setAttribute("totalAccounts", dao.getTotalAccounts());
        request.setAttribute("totalBalance", dao.getTotalBalance());
        request.setAttribute("totalTransactions", dao.getTotalTransactions());
        request.setAttribute("totalPendingRequests", dao.getTotalPendingRequests());
        
        // Fetch last 15 transactions for the live feed - efficient way
        request.setAttribute("recentTransactions", tdao.getRecentTransactions(15));

        request.getRequestDispatcher("/admin/SKMiniBank-System.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);

    }
}