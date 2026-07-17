package com.bank.controller;

import com.bank.dao.AccountDAO;
import com.bank.dao.CustomerDAO;
import com.bank.dao.TransactionDAO;
import com.bank.dao.NotificationDAO;

import com.bank.model.Account;
import com.bank.model.Customer;
import com.bank.model.Transaction;
import com.bank.model.Notification;


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

        // Get Account
        Account account = dao.getAccountByNumber(accountNumber);

        if (account == null) {
            response.sendRedirect("admin/deposit.jsp?msg=invalid");
            return;
        }

        // ==========================
        // Account Status Validation
        // ==========================

        String accountStatus = account.getStatus();

       
        if ("FREEZE".equalsIgnoreCase(accountStatus)) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/CustomerProfileServlet?customerId="
                    + account.getCustomerId()
                    + "&msg=freeze");

            return;
        }

        // Deposit
        boolean status = dao.deposit(accountNumber, amount);

        if (status) {

            // Get Updated Account
            account = dao.getAccountByNumber(accountNumber);

            // Save Transaction
            Transaction t = new Transaction();

            t.setAccountNumber(accountNumber);
            t.setCustomerName(account.getCustomerName());
            t.setBalance(account.getBalance());
            t.setTransactionType("Deposit");
            t.setAmount(amount);
            t.setTransactionDate(new java.sql.Timestamp(System.currentTimeMillis()));
            t.setStatus("SUCCESS");

           TransactionDAO td = new TransactionDAO();
td.addTransaction(t);

CustomerDAO customerDAO = new CustomerDAO();
Customer customer = customerDAO.getCustomerByAccountNumber(accountNumber);

if (customer != null) {

    Notification notification = new Notification();
    notification.setCustomerId(customer.getCustomerId());
    notification.setTitle("Deposit Successful");
    notification.setMessage("₹ " + amount + " has been deposited into your account.");
    notification.setNotificationType("DEPOSIT");
    notification.setStatus("ACTIVE");
    notification.setIsRead(0);
    notification.setActionUrl("CustomerProfileServlet?customerId=" + customer.getCustomerId());

    NotificationDAO notificationDAO = new NotificationDAO();
    notificationDAO.addNotification(notification);

    response.sendRedirect(
            "TransactionSuccess.jsp?customerId="
            + customer.getCustomerId()
            + "&amount=" + amount
            + "&type=Deposit");

} else {

    response.sendRedirect("admin/customer-list.jsp");
}
        } else {

            response.sendRedirect("admin/deposit.jsp?msg=failed");

        }
    }
}