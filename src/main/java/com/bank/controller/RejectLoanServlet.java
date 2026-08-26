package com.bank.controller;

import com.bank.dao.LoanRequestDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/RejectLoanServlet")
public class RejectLoanServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if(admin == null) admin = "Admin";

        LoanRequestDAO dao = new LoanRequestDAO();
        boolean success = dao.rejectLoan(id, admin, "Rejected by Admin");

        if (success) {
            response.sendRedirect("admin/loan-dashboard.jsp?msg=rejected");
        } else {
            response.sendRedirect("admin/loan-dashboard.jsp?msg=failed");
        }
    }
}
