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

@WebServlet("/api/withdraw")
public class WithdrawAPI extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            String accountNumber =
                    request.getParameter("accountNumber");

            String amountStr =
                    request.getParameter("amount");

            if (accountNumber == null || amountStr == null
                    || accountNumber.trim().isEmpty()
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

            AccountDAO accountDAO = new AccountDAO();

            boolean status =
                    accountDAO.withdraw(accountNumber.trim(), amount);

            if (status) {

                CustomerDAO customerDAO = new CustomerDAO();

                Customer customer =
                        customerDAO.getCustomerByAccountNumber(accountNumber);

                Transaction transaction = new Transaction();

                transaction.setAccountNumber(accountNumber);
                transaction.setCustomerName(customer.getFullName());
                transaction.setTransactionType("Withdraw");
                transaction.setAmount(amount);
                transaction.setBalance(customer.getBalance());
                transaction.setTransactionDate(
                        new Timestamp(System.currentTimeMillis()));
                transaction.setStatus("SUCCESS");

                TransactionDAO transactionDAO =
                        new TransactionDAO();

                transactionDAO.addTransaction(transaction);

                response.getWriter().print(
                        "{"
                        + "\"status\":\"success\","
                        + "\"customerName\":\""
                        + customer.getFullName()
                        + "\","
                        + "\"balance\":\""
                        + customer.getBalance()
                        + "\""
                        + "}");

            } else {

                response.getWriter().print(
                        "{"
                        + "\"status\":\"failed\","
                        + "\"message\":\"Insufficient Balance or Account Not Found\""
                        + "}");

            }

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().print(
                    "{"
                    + "\"status\":\"error\","
                    + "\"message\":\""
                    + e.getMessage().replace("\"", "'")
                    + "\""
                    + "}");

        }

    }

}