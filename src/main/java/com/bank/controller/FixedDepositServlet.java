package com.bank.controller;

import com.bank.dao.AccountDAO;
import com.bank.dao.FixedDepositDAO;
import com.bank.model.Account;
import com.bank.model.FixedDeposit;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FixedDepositServlet")
public class FixedDepositServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String accountNumber = request.getParameter("accountNumber");
            double amount = Double.parseDouble(request.getParameter("amount"));
            double interest = Double.parseDouble(request.getParameter("interest"));
            int duration = Integer.parseInt(request.getParameter("duration"));

            AccountDAO accountDAO = new AccountDAO();
            Account account = accountDAO.getAccountByNumber(accountNumber);

            if (account == null) {
                response.sendRedirect("admin/fixed-deposit.jsp?msg=Account Not Found");
                return;
            }

            // CHECK BALANCE IF NOT ADMIN
            javax.servlet.http.HttpSession session = request.getSession(false);
            String role = (session != null && session.getAttribute("role") != null) ? session.getAttribute("role").toString() : "ADMIN";

            if (account.getBalance() < amount) {
                String backUrl = "CUSTOMER".equalsIgnoreCase(role) ? "customer/fixed-deposit.jsp" : "admin/fixed-deposit.jsp";
                response.sendRedirect(backUrl + "?msg=Insufficient Balance");
                return;
            }

            // DEDUCT AMOUNT FROM CUSTOMER ACCOUNT
            boolean deducted = accountDAO.withdraw(accountNumber, amount);
            
            if (!deducted) {
                response.sendRedirect("admin/fixed-deposit.jsp?msg=Balance Deduction Failed");
                return;
            }

            double maturityAmount =
                    amount + ((amount * interest * duration) / 100);

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

            FixedDepositDAO dao = new FixedDepositDAO();

            String ctx = request.getContextPath();

            if (dao.addFixedDeposit(fd)) {
                if ("CUSTOMER".equalsIgnoreCase(role)) {
                    response.sendRedirect(ctx + "/customer/dashboard.jsp?msg=Fixed Deposit Created Successfully");
                } else {
                    response.sendRedirect(ctx + "/admin/fixed-deposit-list.jsp?msg=FD Created Successfully");
                }
            } else {
                // Re-deposit if FD record fails
                accountDAO.deposit(accountNumber, amount);
                String backUrl = "CUSTOMER".equalsIgnoreCase(role) ? "/customer/fixed-deposit.jsp" : "/admin/fixed-deposit.jsp";
                response.sendRedirect(ctx + backUrl + "?msg=FD Creation Failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp?msg=Error: " + e.getMessage());
        }

    }
}