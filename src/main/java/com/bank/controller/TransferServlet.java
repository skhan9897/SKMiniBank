package com.bank.controller;

import com.bank.dao.AccountDAO;
import com.bank.dao.CustomerDAO;
import com.bank.dao.NotificationDAO;
import com.bank.dao.TransactionDAO;
import com.bank.model.Account;
import com.bank.model.Customer;
import com.bank.model.Notification;
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

        // Sender Account
        Account sender = dao.getAccountByNumber(fromAccount);

        if (sender == null) {
            response.sendRedirect("admin/transfer.jsp?msg=invalid");
            return;
        }

        // Account Status Validation
        String accountStatus = sender.getStatus();

        if ("BLOCKED".equalsIgnoreCase(accountStatus)) {
            response.sendRedirect(
                    request.getContextPath()
                    + "/CustomerProfileServlet?customerId="
                    + sender.getCustomerId()
                    + "&msg=blocked");
            return;
        }

        if ("FREEZE".equalsIgnoreCase(accountStatus)) {
            response.sendRedirect(
                    request.getContextPath()
                    + "/CustomerProfileServlet?customerId="
                    + sender.getCustomerId()
                    + "&msg=freeze");
            return;
        }

        // Transfer
        boolean status = dao.transferMoney(fromAccount, toAccount, amount);

        if (status) {

            sender = dao.getAccountByNumber(fromAccount);

            Transaction t = new Transaction();
            t.setAccountNumber(fromAccount);
            t.setCustomerName(sender.getCustomerName());
            t.setBalance(sender.getBalance());
            t.setTransactionType("Transfer");
            t.setAmount(amount);
            t.setTransactionDate(new java.sql.Timestamp(System.currentTimeMillis()));
            t.setStatus("SUCCESS");

            TransactionDAO td = new TransactionDAO();
            td.addTransaction(t);

            CustomerDAO customerDAO = new CustomerDAO();

            Customer senderCustomer =
                    customerDAO.getCustomerByAccountNumber(fromAccount);

            Customer receiverCustomer =
                    customerDAO.getCustomerByAccountNumber(toAccount);

           NotificationDAO notificationDAO = new NotificationDAO();

System.out.println("Sender Customer = " + senderCustomer);
System.out.println("Receiver Customer = " + receiverCustomer);

// Sender Notification
if (senderCustomer != null) {
            // Sender Notification
            if (senderCustomer != null) {

                Notification senderNotification = new Notification();

                senderNotification.setCustomerId(senderCustomer.getCustomerId());
                senderNotification.setTitle("Transfer Successful");
                senderNotification.setMessage("₹ " + amount + " has been transferred successfully.");
                senderNotification.setNotificationType("TRANSFER");
                senderNotification.setStatus("ACTIVE");
                senderNotification.setIsRead(0);
                senderNotification.setActionUrl(
                        "CustomerProfileServlet?customerId="
                        + senderCustomer.getCustomerId());

               boolean saved = notificationDAO.addNotification(senderNotification);
System.out.println("Sender Notification Saved = " + saved);
            }

            // Receiver Notification
            if (receiverCustomer != null) {

                Notification receiverNotification = new Notification();

                receiverNotification.setCustomerId(receiverCustomer.getCustomerId());
                receiverNotification.setTitle("Amount Received");
                receiverNotification.setMessage("₹ " + amount + " has been credited to your account.");
                receiverNotification.setNotificationType("CREDIT");
                receiverNotification.setStatus("ACTIVE");
                receiverNotification.setIsRead(0);
                receiverNotification.setActionUrl(
                        "CustomerProfileServlet?customerId="
                        + receiverCustomer.getCustomerId());

                boolean saved2 = notificationDAO.addNotification(receiverNotification);
System.out.println("Receiver Notification Saved = " + saved2);
            }

            if (senderCustomer != null) {

                response.sendRedirect(
                        "TransactionSuccess.jsp?customerId="
                        + senderCustomer.getCustomerId()
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
    
}