package com.bank.api;

import com.bank.dao.AccountDAO;
import com.bank.dao.TransactionDAO;
import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.model.LoginResponse;
import com.google.gson.Gson;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/deposit")
public class DepositApiServlet extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        String accountNumber = request.getParameter("accountNumber");
        String amountStr = request.getParameter("amount");
        
        LoginResponse apiResponse = new LoginResponse();

        if (accountNumber == null || amountStr == null) {
            apiResponse.setStatus("FAILED");
            apiResponse.setMessage("Missing parameters");
            response.getWriter().write(gson.toJson(apiResponse));
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            AccountDAO accountDAO = new AccountDAO();
            Account account = accountDAO.getAccountByNumber(accountNumber);

            if (account == null) {
                apiResponse.setStatus("FAILED");
                apiResponse.setMessage("Account not found");
            } else if ("FREEZE".equalsIgnoreCase(account.getStatus())) {
                apiResponse.setStatus("FAILED");
                apiResponse.setMessage("Account is frozen. Deposit not allowed.");
            } else {
                boolean success = accountDAO.deposit(accountNumber, amount);
                if (success) {
                    // Refresh account to get new balance
                    account = accountDAO.getAccountByNumber(accountNumber);
                    
                    // Log transaction
                    Transaction t = new Transaction();
                    t.setAccountNumber(accountNumber);
                    t.setCustomerName(account.getCustomerName());
                    t.setBalance(account.getBalance());
                    t.setTransactionType("Deposit");
                    t.setAmount(amount);
                    t.setTransactionDate(new java.sql.Timestamp(System.currentTimeMillis()));
                    t.setStatus("SUCCESS");
                    new TransactionDAO().addTransaction(t);

                    apiResponse.setStatus("SUCCESS");
                    apiResponse.setMessage("Deposit successful");
                    apiResponse.setBalance(account.getBalance());
                } else {
                    apiResponse.setStatus("FAILED");
                    apiResponse.setMessage("Deposit failed");
                }
            }
        } catch (Exception e) {
            apiResponse.setStatus("FAILED");
            apiResponse.setMessage("Error: " + e.getMessage());
        }

        response.getWriter().write(gson.toJson(apiResponse));
    }
}
