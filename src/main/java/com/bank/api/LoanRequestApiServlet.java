package com.bank.api;

import com.bank.dao.LoanRequestDAO;
import com.bank.model.LoanRequest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/loan/apply")
public class LoanRequestApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            LoanRequest loan = new LoanRequest();

            loan.setCustomerId(Integer.parseInt(request.getParameter("customerId")));
            loan.setAccountNumber(request.getParameter("accountNumber"));
            loan.setLoanType(request.getParameter("loanType"));
            loan.setLoanAmount(Double.parseDouble(request.getParameter("loanAmount")));
            loan.setTenureMonths(Integer.parseInt(request.getParameter("tenureMonths")));
            loan.setMonthlyIncome(Double.parseDouble(request.getParameter("monthlyIncome")));
            loan.setPurpose(request.getParameter("purpose"));

            LoanRequestDAO dao = new LoanRequestDAO();

            boolean success = dao.applyLoan(loan);

            if (success) {
                response.getWriter().print(
                        "{\"success\":true,\"message\":\"Loan Request Submitted Successfully\"}");
            } else {
                response.getWriter().print(
                        "{\"success\":false,\"message\":\"Unable to Submit Loan Request\"}");
            }

        } catch (Exception e) {

            response.getWriter().print(
                    "{\"success\":false,\"message\":\""
                    + e.getMessage().replace("\"","\\\"")
                    + "\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request,response);
    }
}