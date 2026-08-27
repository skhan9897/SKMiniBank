package com.bank.controller;

import com.bank.dao.DashboardDAO;
import com.bank.model.Account;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BalanceReportServlet")
public class BalanceReportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        DashboardDAO dao = new DashboardDAO();
        List<Account> balanceList = dao.getCustomerBalanceList();
        double totalBalance = dao.getTotalBalance();

        request.setAttribute("balanceList", balanceList);
        request.setAttribute("totalBalance", totalBalance);

        request.getRequestDispatcher("/admin/balance-report.jsp")
               .forward(request, response);
    }
}
