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

@WebServlet("/TransactionServlet")
public class TransactionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        TransactionDAO dao = new TransactionDAO();

        List<Transaction> transactionList = dao.getAllTransactions();

        request.setAttribute("transactionList", transactionList);

        request.getRequestDispatcher("/admin/transaction.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}