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

@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accountNumber = request.getParameter("accountNumber");
        double amount = Double.parseDouble(request.getParameter("amount"));

        AccountDAO dao = new AccountDAO();

        boolean status = dao.deposit(accountNumber, amount);

        if (status) {

            // Get Updated Account
            Account account = dao.getAccountByNumber(accountNumber);

            // Save Transaction
            Transaction t = new Transaction();

            t.setAccountNumber(accountNumber);

            if (account != null) {
                t.setCustomerName(account.getCustomerName());
                t.setBalance(account.getBalance());
            }

            t.setTransactionType("Deposit");
            t.setAmount(amount);
            t.setStatus("SUCCESS");

            TransactionDAO td = new TransactionDAO();
            td.addTransaction(t);

            // Get Customer ID from Account Number
            CustomerDAO customerDAO = new CustomerDAO();
            Customer customer = customerDAO.getCustomerByAccountNumber(accountNumber);

            if (customer != null) {

                response.sendRedirect(
    "TransactionSuccess.jsp?customerId="
    + customer.getCustomerId()
    + "&amount="
    + amount
    + "&type=Deposit");

            } else {

                response.sendRedirect("admin/customer-list.jsp");
            }

        } else {

            response.sendRedirect("admin/deposit.jsp?msg=failed");
        }
    }
}