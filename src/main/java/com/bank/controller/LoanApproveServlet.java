package com.bank.controller;

import com.bank.dao.LoanDAO;
import com.bank.model.Loan;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoanApproveServlet")
public class LoanApproveServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int loanId = Integer.parseInt(request.getParameter("loanId"));

            LoanDAO dao = new LoanDAO();

            // Loan Details
            Loan loan = dao.getLoanById(loanId);

            if (loan == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/LoanListServlet?msg=notfound");

                return;
            }

            // Step 1 : Loan Approve
            boolean approve = dao.approveLoan(loanId);

            if (approve) {

                // Step 2 : Customer Balance Credit
                dao.creditLoanAmount(
                        loan.getAccountNumber(),
                        loan.getLoanAmount());

                // Step 3 : Updated Balance
                double balance =
                        dao.getCustomerBalance(
                                loan.getAccountNumber());

                // Step 4 : Transaction Entry
                dao.addLoanTransaction(
                        loan.getAccountNumber(),
                        loan.getLoanAmount(),
                        balance);

                // Step 5 : Redirect Loan Profile
                response.sendRedirect(
                        request.getContextPath()
                        + "/LoanProfileServlet?loanId="
                        + loanId
                        + "&msg=approved");

            } else {

                response.sendRedirect(
                        request.getContextPath()
                        + "/LoanProfileServlet?loanId="
                        + loanId
                        + "&msg=failed");

            }

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(e.getMessage());

        }

    }

}