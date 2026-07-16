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

        try {

            TransactionDAO dao = new TransactionDAO();

            String accountNumber = request.getParameter("accountNumber");

            List<Transaction> transactionList;

            if (accountNumber != null
                    && !accountNumber.trim().isEmpty()) {

                transactionList =
                        dao.getTransactionsByAccount(accountNumber);

            } else {

                transactionList =
                        dao.getAllTransactions();

            }

            request.setAttribute("transactionList", transactionList);
request.setAttribute("accountNumber", accountNumber);

request.getRequestDispatcher("/admin/transaction.jsp")
       .forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "admin/transaction.jsp?msg=error");

        }

    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);

    }

}