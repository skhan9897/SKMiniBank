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

@WebServlet("/WithdrawServlet")
public class WithdrawServlet extends HttpServlet {

    @Override
protected void doPost(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {

    String accountNumber = request.getParameter("accountNumber");
    double amount = Double.parseDouble(request.getParameter("amount"));

    AccountDAO dao = new AccountDAO();

    // Get Account
    Account account = dao.getAccountByNumber(accountNumber);

    if (account == null) {
        response.sendRedirect("admin/withdraw.jsp?msg=invalid");
        return;
    }

    // Account Status Validation

    String accountStatus = account.getStatus();

    if ("BLOCKED".equalsIgnoreCase(accountStatus)) {

        response.sendRedirect(
                request.getContextPath()
                + "/CustomerProfileServlet?customerId="
                + account.getCustomerId()
                + "&msg=blocked");

        return;
    }

    if ("FREEZE".equalsIgnoreCase(accountStatus)) {

        response.sendRedirect(
                request.getContextPath()
                + "/CustomerProfileServlet?customerId="
                + account.getCustomerId()
                + "&msg=freeze");

        return;
    }

    // Withdraw
    boolean status = dao.withdraw(accountNumber, amount);

    if (status) {

        account = dao.getAccountByNumber(accountNumber);

        Transaction t = new Transaction();

        t.setAccountNumber(accountNumber);
        t.setCustomerName(account.getCustomerName());
        t.setBalance(account.getBalance());
        t.setTransactionType("Withdraw");
        t.setAmount(amount);
        t.setTransactionDate(new java.sql.Timestamp(System.currentTimeMillis()));
        t.setStatus("SUCCESS");

        TransactionDAO td = new TransactionDAO();
        td.addTransaction(t);

        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = customerDAO.getCustomerByAccountNumber(accountNumber);

        if (customer != null) {

            response.sendRedirect(
                    "TransactionSuccess.jsp?customerId="
                    + customer.getCustomerId()
                    + "&amount="
                    + amount
                    + "&type=Withdraw");

        } else {

            response.sendRedirect("admin/customer-list.jsp");
        }

    } else {

        response.sendRedirect("admin/withdraw.jsp?msg=failed");
    }
}
}