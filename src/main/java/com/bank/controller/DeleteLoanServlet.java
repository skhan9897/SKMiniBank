package com.bank.controller;

import com.bank.dao.LoanRequestDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteLoanServlet")
public class DeleteLoanServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        LoanRequestDAO dao = new LoanRequestDAO();
        boolean success = dao.deleteLoan(id);

        if (success) {
            response.sendRedirect("admin/loan-dashboard.jsp?msg=deleted");
        } else {
            response.sendRedirect("admin/loan-dashboard.jsp?msg=failed");
        }
    }
}
