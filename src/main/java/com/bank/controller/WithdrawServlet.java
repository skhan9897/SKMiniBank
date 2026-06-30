package com.bank.controller;

import com.bank.model.Account;
import com.bank.dao.AccountDAO;
import com.bank.dao.TransactionDAO;
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

        boolean status = dao.withdraw(accountNumber, amount);

       if (status) {

    Account account = dao.getAccountByNumber(accountNumber);

    Transaction t = new Transaction();

    t.setAccountNumber(accountNumber);

    if (account != null) {
        t.setCustomerName(account.getCustomerName());
        t.setBalance(account.getBalance());
    }

    t.setTransactionType("Withdraw");
    t.setAmount(amount);
    t.setStatus("SUCCESS");

    TransactionDAO td = new TransactionDAO();
    td.addTransaction(t);

    response.sendRedirect("admin/withdraw.jsp?msg=success");

} else {

    response.sendRedirect("admin/withdraw.jsp?msg=failed");
}
    }
}
