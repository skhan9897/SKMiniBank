package com.bank.controller;

import com.bank.dao.TransactionDAO;
import com.bank.model.Transaction;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TransactionServlet")
public class TransactionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String accountNumber = request.getParameter("accountNumber");
            String customerName = request.getParameter("customerName");
            String transactionType = request.getParameter("transactionType");
            double amount = Double.parseDouble(request.getParameter("amount"));
            double balance = Double.parseDouble(request.getParameter("balance"));

            Transaction t = new Transaction();

            t.setAccountNumber(accountNumber);
            t.setCustomerName(customerName);
            t.setTransactionType(transactionType);
            t.setAmount(amount);
            t.setBalance(balance);
            t.setStatus("SUCCESS");

            TransactionDAO dao = new TransactionDAO();

            if (dao.addTransaction(t)) {

                response.sendRedirect("admin/transaction.jsp?success=Transaction Saved");

            } else {

                response.sendRedirect("admin/transaction.jsp?error=Transaction Failed");

            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect("admin/transaction.jsp?error=" + e.getMessage());

        }

    }
}