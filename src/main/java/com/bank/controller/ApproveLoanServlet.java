package com.bank.controller;

import com.bank.dao.LoanRequestDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ApproveLoanServlet")
public class ApproveLoanServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String ctx = request.getContextPath();
        if ("/".equals(ctx)) ctx = "";

        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(ctx + "/SKMiniBankadmin-login.jsp");
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));
        String admin = (String) session.getAttribute("admin");

        LoanRequestDAO dao = new LoanRequestDAO();
        boolean success = dao.approveLoan(id, admin, "Approved by Admin", 10.5); // Default interest rate

        if (success) {
            response.sendRedirect("admin/loan-dashboard.jsp?msg=approved");
        } else {
            response.sendRedirect("admin/loan-dashboard.jsp?msg=failed");
        }
    }
}
