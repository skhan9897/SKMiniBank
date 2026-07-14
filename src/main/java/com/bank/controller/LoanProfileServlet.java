package com.bank.controller;

import com.bank.dao.LoanDAO;
import com.bank.model.Loan;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LoanProfileServlet")
public class LoanProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int loanId = Integer.parseInt(request.getParameter("loanId"));

            LoanDAO dao = new LoanDAO();

            Loan loan = dao.getLoanById(loanId);

            if (loan == null) {

                response.getWriter().println("Loan Not Found");

                return;
            }

            request.setAttribute("loan", loan);

            request.getRequestDispatcher("/admin/loan-profile.jsp")
                    .forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println("Error : " + e.getMessage());

        }

    }

}