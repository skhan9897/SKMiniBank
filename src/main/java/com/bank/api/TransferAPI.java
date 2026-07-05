package com.bank.api;

import com.bank.dao.AccountDAO;
import com.bank.dao.CustomerDAO;
import com.bank.dao.TransactionDAO;
import com.bank.model.Customer;
import com.bank.model.Transaction;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/transfer")
public class TransferAPI extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            String fromAccount =
                    request.getParameter("fromAccount");

            String toAccount =
                    request.getParameter("toAccount");

            String amountStr =
                    request.getParameter("amount");

            if (fromAccount == null || toAccount == null
                    || amountStr == null
                    || fromAccount.trim().isEmpty()
                    || toAccount.trim().isEmpty()
                    || amountStr.trim().isEmpty()) {

                response.getWriter().print(
                        "{\"status\":\"failed\",\"message\":\"Invalid Request\"}");
                return;
            }

            double amount = Double.parseDouble(amountStr);

            if (amount <= 0) {

                response.getWriter().print(
                        "{\"status\":\"failed\",\"message\":\"Invalid Amount\"}");
                return;
            }

            if (fromAccount.equals(toAccount)) {

                response.getWriter().print(
                        "{\"status\":\"failed\",\"message\":\"Sender and Receiver Account cannot be same\"}");
                return;
            }

            AccountDAO accountDAO = new AccountDAO();

            boolean transferStatus =
                    accountDAO.transferMoney(
                            fromAccount.trim(),
                            toAccount.trim(),
                            amount);

            if (transferStatus) {

                CustomerDAO customerDAO = new CustomerDAO();

                Customer sender =
                        customerDAO.getCustomerByAccountNumber(fromAccount);

                Customer receiver =
                        customerDAO.getCustomerByAccountNumber(toAccount);

                TransactionDAO transactionDAO =
                        new TransactionDAO();

                // Sender Transaction
                Transaction debit = new Transaction();

                debit.setAccountNumber(fromAccount);
                debit.setCustomerName(sender.getFullName());
                debit.setTransactionType("Transfer Debit");
                debit.setAmount(amount);
                debit.setBalance(sender.getBalance());
                debit.setTransactionDate(
                        new Timestamp(System.currentTimeMillis()));
                debit.setStatus("SUCCESS");

                transactionDAO.addTransaction(debit);

                // Receiver Transaction
                Transaction credit = new Transaction();

                credit.setAccountNumber(toAccount);
                credit.setCustomerName(receiver.getFullName());
                credit.setTransactionType("Transfer Credit");
                credit.setAmount(amount);
                credit.setBalance(receiver.getBalance());
                credit.setTransactionDate(
                        new Timestamp(System.currentTimeMillis()));
                credit.setStatus("SUCCESS");

                transactionDAO.addTransaction(credit);

                response.getWriter().print(
                        "{"
                        + "\"status\":\"success\","
                        + "\"sender\":\"" + sender.getFullName() + "\","
                        + "\"receiver\":\"" + receiver.getFullName() + "\","
                        + "\"amount\":\"" + amount + "\","
                        + "\"balance\":\"" + sender.getBalance() + "\""
                        + "}");

            } else {

                response.getWriter().print(
                        "{\"status\":\"failed\",\"message\":\"Transfer Failed or Insufficient Balance\"}");

            }

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().print(
                    "{\"status\":\"error\",\"message\":\""
                    + e.getMessage().replace("\"", "'")
                    + "\"}");

        }

    }

}