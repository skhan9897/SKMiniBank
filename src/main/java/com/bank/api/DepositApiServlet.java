package com.bank.api;

import com.bank.dao.AccountDAO;
import com.bank.dao.TransactionDAO;
import com.bank.model.Account;
import com.bank.model.LoginResponse;
import com.bank.model.Transaction;
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
        response.setCharacterEncoding("UTF-8");

        String accountNumber = request.getParameter("accountNumber");
        String amountStr = request.getParameter("amount");

        LoginResponse apiResponse = new LoginResponse();

        if (accountNumber == null || amountStr == null || accountNumber.isEmpty() || amountStr.isEmpty()) {
            apiResponse.setStatus("FAILED");
            apiResponse.setMessage("Required parameters (accountNumber, amount) are missing.");
            response.getWriter().write(gson.toJson(apiResponse));
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                apiResponse.setStatus("FAILED");
                apiResponse.setMessage("Amount must be greater than zero.");
                response.getWriter().write(gson.toJson(apiResponse));
                return;
            }

            AccountDAO accountDAO = new AccountDAO();
            // Try matching with normalized account number
            String normalizedAcc = accountNumber.trim().replaceAll("\\s+", "");
            Account account = accountDAO.getAccountByNumber(normalizedAcc);

            if (account == null) {
                // Try one more time with original just in case
                account = accountDAO.getAccountByNumber(accountNumber);
            }

            if (account == null) {
                apiResponse.setStatus("FAILED");
                apiResponse.setMessage("Account validation failed. Please refresh your app profile.");
            } else if ("FREEZE".equalsIgnoreCase(account.getStatus())) {
                apiResponse.setStatus("FAILED");
                apiResponse.setMessage("Transaction declined. Account is frozen.");
            } else {
                boolean success = accountDAO.deposit(accountNumber, amount);
                if (success) {
                    // Fetch updated data for response
                    account = accountDAO.getAccountByNumber(accountNumber);
                    
                    // NOTE: Transaction is already logged inside accountDAO.deposit()
                    // to prevent double-entry.

                    apiResponse.setStatus("SUCCESS");
                    apiResponse.setMessage("Amount ₹" + amount + " deposited successfully.");
                    apiResponse.setBalance(account.getBalance());
                    apiResponse.setAccountNumber(accountNumber);
                } else {
                    apiResponse.setStatus("FAILED");
                    apiResponse.setMessage("Server failed to update balance. Please contact support.");
                }
            }
        } catch (NumberFormatException e) {
            apiResponse.setStatus("FAILED");
            apiResponse.setMessage("Invalid amount format.");
        } catch (Exception e) {
            apiResponse.setStatus("FAILED");
            apiResponse.setMessage("Server Error: " + e.getMessage());
        }

        response.getWriter().write(gson.toJson(apiResponse));
    }
}

