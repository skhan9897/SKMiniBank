package com.bank.controller;

import com.bank.dao.AccountDAO;
import com.bank.dao.CustomerDAO;
import com.bank.dao.TransactionDAO;
import com.bank.model.Account;
import com.bank.model.Customer;
import com.bank.model.Transaction;

import java.io.IOException;
import java.sql.Timestamp;
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

        try {

            String accountNumber = request.getParameter("accountNumber").trim();
            double amount = Double.parseDouble(request.getParameter("amount"));

            if (amount <= 0) {
                response.sendRedirect("admin/withdraw.jsp?msg=invalid");
                return;
            }

            System.out.println("===== WITHDRAW =====");
            System.out.println("Account : " + accountNumber);
            System.out.println("Amount  : " + amount);

            AccountDAO dao = new AccountDAO();

            boolean status = dao.withdraw(accountNumber, amount);

            System.out.println("Withdraw Status : " + status);

            if (!status) {
                response.sendRedirect("admin/withdraw.jsp?msg=failed");
                return;
            }

            Account account = dao.getAccountByNumber(accountNumber);

            Transaction t = new Transaction();
            t.setAccountNumber(accountNumber);
            t.setTransactionType("Withdraw");
            t.setAmount(amount);
            t.setStatus("SUCCESS");
            t.setTransactionDate(new Timestamp(System.currentTimeMillis()));

            if (account != null) {
                t.setCustomerName(account.getCustomerName());
                t.setBalance(account.getBalance());
            }

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

        } catch (Exception e) {

            e.printStackTrace();
            response.getWriter().println("ERROR : " + e.getMessage());

        }

    }
}