package com.bank.api;

import com.bank.dao.AccountDAO;
import com.bank.dao.CustomerDAO;
import com.bank.dao.TransactionDAO;
import com.bank.model.Customer;
import com.bank.model.Transaction;
import com.bank.util.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/upiPayment")
public class UPIPaymentAPI extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            String fromAccount = request.getParameter("fromAccount");
            String upiId = request.getParameter("upiId");
            String amountStr = request.getParameter("amount");

            if (fromAccount == null || upiId == null || amountStr == null
                    || fromAccount.trim().isEmpty()
                    || upiId.trim().isEmpty()
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

            con = DBConnection.getConnection();

            ps = con.prepareStatement(
                    "SELECT account_number,full_name FROM customer WHERE upi_id=?");

            ps.setString(1, upiId.trim());

            rs = ps.executeQuery();

            if (!rs.next()) {

                response.getWriter().print(
                        "{\"status\":\"failed\",\"message\":\"Invalid UPI ID\"}");
                return;
            }

            String toAccount = rs.getString("account_number");
            String receiverName = rs.getString("full_name");

            AccountDAO accountDAO = new AccountDAO();

            boolean transfer =
                    accountDAO.transferMoney(
                            fromAccount.trim(),
                            toAccount,
                            amount);

            if (!transfer) {

                response.getWriter().print(
                        "{\"status\":\"failed\",\"message\":\"Insufficient Balance\"}");
                return;
            }

            CustomerDAO customerDAO = new CustomerDAO();

            Customer sender =
                    customerDAO.getCustomerByAccountNumber(fromAccount);

            Customer receiver =
                    customerDAO.getCustomerByAccountNumber(toAccount);

            TransactionDAO transactionDAO =
                    new TransactionDAO();

            Transaction debit = new Transaction();

            debit.setAccountNumber(fromAccount);
            debit.setCustomerName(sender.getFullName());
            debit.setTransactionType("UPI Debit");
            debit.setAmount(amount);
            debit.setBalance(sender.getBalance());
            debit.setTransactionDate(
                    new Timestamp(System.currentTimeMillis()));
            debit.setStatus("SUCCESS");

            transactionDAO.addTransaction(debit);

            Transaction credit = new Transaction();

            credit.setAccountNumber(toAccount);
            credit.setCustomerName(receiverName);
            credit.setTransactionType("UPI Credit");
            credit.setAmount(amount);
            credit.setBalance(receiver.getBalance());
            credit.setTransactionDate(
                    new Timestamp(System.currentTimeMillis()));
            credit.setStatus("SUCCESS");

            transactionDAO.addTransaction(credit);

            response.getWriter().print(
                    "{"
                    + "\"status\":\"success\","
                    + "\"receiver\":\"" + receiverName + "\","
                    + "\"amount\":\"" + amount + "\","
                    + "\"balance\":\"" + sender.getBalance() + "\""
                    + "}");

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().print(
                    "{\"status\":\"error\",\"message\":\""
                    + e.getMessage().replace("\"", "'")
                    + "\"}");

        } finally {

            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
            try { if (con != null) con.close(); } catch (Exception ignored) {}

        }

    }

}