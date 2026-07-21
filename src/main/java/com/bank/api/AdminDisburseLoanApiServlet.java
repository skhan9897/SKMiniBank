package com.bank.api;

import com.bank.dao.LoanRequestDAO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/admin/loan/disburse")
public class AdminDisburseLoanApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            String loanIdStr = request.getParameter("loanId");

            if (loanIdStr == null || loanIdStr.trim().isEmpty()) {

                response.getWriter().print(
                        "{\"success\":false,"
                        + "\"status\":\"failed\","
                        + "\"message\":\"Loan ID is required\"}");
                return;
            }

            int loanId = Integer.parseInt(loanIdStr);

            LoanRequestDAO dao = new LoanRequestDAO();

            boolean success = dao.disburseLoan(loanId);

            if (success) {

                response.getWriter().print(
                        "{"
                        + "\"success\":true,"
                        + "\"status\":\"success\","
                        + "\"message\":\"Loan Disbursed Successfully\""
                        + "}");

            } else {

                response.getWriter().print(
                        "{"
                        + "\"success\":false,"
                        + "\"status\":\"failed\","
                        + "\"message\":\"Unable to Disburse Loan\""
                        + "}");
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().print(
                    "{"
                    + "\"success\":false,"
                    + "\"status\":\"error\","
                    + "\"message\":\""
                    + e.getMessage().replace("\"", "\\\"")
                    + "\""
                    + "}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }
}