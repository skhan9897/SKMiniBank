package com.bank.controller;

import com.bank.dao.LoanDAO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RejectLoanServlet")
public class RejectLoanServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int loanId = Integer.parseInt(request.getParameter("id"));

            LoanDAO dao = new LoanDAO();

            boolean status = dao.rejectLoan(loanId);

            if (status) {

                response.sendRedirect("admin/loan-list.jsp?msg=rejected");

            } else {

                response.sendRedirect("admin/loan-list.jsp?msg=failed");
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect("admin/loan-list.jsp?msg=error");

        }

    }

}