package com.bank.controller;

import com.bank.dao.LoanDAO;
import com.bank.model.Loan;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoanListServlet")
public class LoanListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            LoanDAO dao = new LoanDAO();

            List<Loan> loanList = dao.getAllLoans();

            request.setAttribute("loanList", loanList);

            request.getRequestDispatcher("/admin/loan-list.jsp")
                   .forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect("admin/loan-list.jsp?msg=error");

        }

    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);

    }

}