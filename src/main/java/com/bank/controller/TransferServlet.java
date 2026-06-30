package com.bank.controller;
import com.bank.dao.TransactionDAO;
import com.bank.model.Transaction;
import com.bank.model.Account;
import com.bank.dao.AccountDAO;
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

        boolean status = dao.transferMoney(fromAccount, toAccount, amount);

        if (status) {

    Account sender = dao.getAccountByNumber(fromAccount);

Transaction t = new Transaction();

t.setAccountNumber(fromAccount);

if (sender != null) {
    t.setCustomerName(sender.getCustomerName());
    t.setBalance(sender.getBalance());
}

t.setTransactionType("Transfer");
t.setAmount(amount);
t.setStatus("SUCCESS");

TransactionDAO td = new TransactionDAO();
td.addTransaction(t);
    response.sendRedirect("customer/dashboard.jsp?msg=Transfer Success");

} else {

    response.sendRedirect("transfer.jsp?msg=Transfer Failed");
}

    }

}