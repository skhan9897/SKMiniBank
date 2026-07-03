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

        DashboardDAO dao = new DashboardDAO();

        request.setAttribute("totalCustomers", dao.getTotalCustomers());
        request.setAttribute("totalAccounts", dao.getTotalAccounts());
        request.setAttribute("totalBalance", dao.getTotalBalance());
        request.setAttribute("totalTransactions", dao.getTotalTransactions());

        request.getRequestDispatcher("/admin/dashboard.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);

    }
}