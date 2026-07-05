package com.bank.controller;

import com.bank.dao.AccountDAO;
import com.bank.dao.CustomerDAO;
import com.bank.dao.TransactionDAO;

import com.bank.model.Account;
import com.bank.model.Customer;
import com.bank.model.Transaction;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TransferServlet")
public class TransferServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String fromAccount = request.getParameter("fromAccount");
        String toAccount = request.getParameter("toAccount");

        double amount = Double.parseDouble(request.getParameter("amount"));

        AccountDAO dao = new AccountDAO();

        boolean status = dao.transferMoney(fromAccount, toAccount, amount);

        if (status) {

            // Sender Account
            Account sender = dao.getAccountByNumber(fromAccount);

            // Save Transaction
            Transaction t = new Transaction();

            t.setAccountNumber(fromAccount);

            if (sender != null) {
                t.setCustomerName(sender.getCustomerName());
                t.setBalance(sender.getBalance());
            }

            t.setTransactionType("Transfer");
            t.setAmount(amount);
            t.setStatus("SUCCESS");

            TransactionDAO td = new TransactionDAO();
            td.addTransaction(t);

            // Get Customer ID
            CustomerDAO customerDAO = new CustomerDAO();
            Customer customer = customerDAO.getCustomerByAccountNumber(fromAccount);

            if (customer != null) {

                response.sendRedirect(
                        "TransactionSuccess.jsp?customerId="
                        + customer.getCustomerId()
                        + "&amount="
                        + amount
                        + "&type=Transfer");

            } else {

                response.sendRedirect("admin/customer-list.jsp");
            }

        } else {

            response.sendRedirect("admin/transfer.jsp?msg=failed");
        }
    }
}