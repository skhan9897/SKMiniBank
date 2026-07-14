package com.bank.controller;

import com.bank.dao.LoanDAO;
import com.bank.model.Loan;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoanRejectServlet")
public class LoanRejectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int loanId = Integer.parseInt(
                    request.getParameter("loanId"));

            LoanDAO dao = new LoanDAO();

            Loan loan = dao.getLoanById(loanId);

            if (loan == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin/loan-list.jsp?msg=notfound");

                return;

            }

            boolean reject = dao.rejectLoan(loanId);

            if (reject) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/LoanProfileServlet?loanId="
                        + loanId
                        + "&msg=rejected");

            } else {

                response.sendRedirect(
                        request.getContextPath()
                        + "/LoanProfileServlet?loanId="
                        + loanId
                        + "&msg=failed");

            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/loan-list.jsp?msg=error");

        }

    }

}