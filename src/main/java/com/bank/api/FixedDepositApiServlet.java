package com.bank.api;

import com.bank.dao.AccountDAO;
import com.bank.dao.FixedDepositDAO;
import com.bank.model.Account;
import com.bank.model.FixedDeposit;
import com.google.gson.Gson;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/fixed-deposit/create")
public class FixedDepositApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> resMap = new HashMap<>();
        Gson gson = new Gson();

        try {
            String accountNumber = request.getParameter("accountNumber");
            double amount = Double.parseDouble(request.getParameter("amount"));
            int duration = Integer.parseInt(request.getParameter("duration"));
            double interest = 7.5; // Default interest

            AccountDAO accountDAO = new AccountDAO();
            Account account = accountDAO.getAccountByNumber(accountNumber);

            if (account == null) {
                resMap.put("success", false);
                resMap.put("message", "Account not found");
                response.getWriter().print(gson.toJson(resMap));
                return;
            }

            if (account.getBalance() < amount) {
                resMap.put("success", false);
                resMap.put("message", "Insufficient balance in account");
                response.getWriter().print(gson.toJson(resMap));
                return;
            }

            // Deduct balance
            if (accountDAO.withdraw(accountNumber, amount)) {
                
                double maturityAmount = amount + ((amount * interest * duration) / 100);
                LocalDate openDate = LocalDate.now();
                LocalDate maturityDate = openDate.plusYears(duration);

                FixedDeposit fd = new FixedDeposit();
                fd.setCustomerId(account.getCustomerId());
                fd.setAccountNumber(accountNumber);
                fd.setCustomerName(account.getCustomerName());
                fd.setFdAmount(amount);
                fd.setInterestRate(interest);
                fd.setDurationYear(duration);
                fd.setMaturityAmount(maturityAmount);
                fd.setOpenDate(openDate.toString());
                fd.setMaturityDate(maturityDate.toString());
                fd.setStatus("ACTIVE");

                FixedDepositDAO fdDao = new FixedDepositDAO();
                if (fdDao.addFixedDeposit(fd)) {
                    resMap.put("success", true);
                    resMap.put("message", "Fixed Deposit created successfully");
                } else {
                    accountDAO.deposit(accountNumber, amount); // refund
                    resMap.put("success", false);
                    resMap.put("message", "Failed to create FD record");
                }
            } else {
                resMap.put("success", false);
                resMap.put("message", "Balance deduction failed");
            }
        } catch (Exception e) {
            resMap.put("success", false);
            resMap.put("message", "Error: " + e.getMessage());
        }
        response.getWriter().print(gson.toJson(resMap));
    }
}
