package com.bank.controller;

import com.bank.dao.TransactionDAO;
import com.bank.model.Transaction;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        TransactionDAO dao = new TransactionDAO();

        List<Transaction> transactionList = dao.getAllTransactions();

        request.setAttribute("transactionList", transactionList);
        request.setAttribute("totalTransactions", dao.getTotalTransactions());
        request.setAttribute("totalDeposit", dao.getTotalDeposit());
        request.setAttribute("totalWithdraw", dao.getTotalWithdraw());
        request.setAttribute("totalTransfer", dao.getTotalTransfer());

        request.getRequestDispatcher("/admin/reports.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}