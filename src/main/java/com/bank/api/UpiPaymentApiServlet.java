package com.bank.api;

import com.bank.dao.AccountDAO;
import com.bank.dao.NotificationDAO;
import com.bank.dao.TransactionDAO;
import com.bank.dao.UpiDAO;
import com.bank.model.Account;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/upiPayment")
public class UpiPaymentApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {

            String fromAccount = request.getParameter("fromAccount");
            String toUpiId = request.getParameter("toUpiId");
            String upiPin = request.getParameter("upiPin");
            String amountStr = request.getParameter("amount");
            String remarks = request.getParameter("remarks");

            if (fromAccount == null || fromAccount.trim().isEmpty()
                    || toUpiId == null || toUpiId.trim().isEmpty()
                    || upiPin == null || upiPin.trim().isEmpty()
                    || amountStr == null || amountStr.trim().isEmpty()) {

                out.print("{\"status\":\"failed\",\"message\":\"All Fields Required\"}");
                return;
            }

            double amount = Double.parseDouble(amountStr);

            if (amount <= 0) {

                out.print("{\"status\":\"failed\",\"message\":\"Invalid Amount\"}");
                return;
            }

            UpiDAO upiDAO = new UpiDAO();

            // Verify Sender UPI PIN
            if (!upiDAO.verifyUpiPin(fromAccount, upiPin)) {

                out.print("{\"status\":\"failed\",\"message\":\"Invalid UPI PIN\"}");
                return;
            }

            // Get Receiver Account
            String toAccount = upiDAO.getAccountNumberByUpi(toUpiId);

            if (toAccount == null) {

                out.print("{\"status\":\"failed\",\"message\":\"Receiver UPI ID Not Found\"}");
                return;
            }

            if (fromAccount.equals(toAccount)) {

                out.print("{\"status\":\"failed\",\"message\":\"Cannot Transfer To Same Account\"}");
                return;
            }

            AccountDAO accountDAO = new AccountDAO();

            Account sender = accountDAO.getAccountByNumber(fromAccount);
            Account receiver = accountDAO.getAccountByNumber(toAccount);

            if (sender == null || receiver == null) {

                out.print("{\"status\":\"failed\",\"message\":\"Account Not Found\"}");
                return;
            }

            boolean transferStatus = accountDAO.transferAmount(
                    fromAccount,
                    toAccount,
                    amount,
                    remarks
            );

            if (!transferStatus) {

                out.print("{\"status\":\"failed\",\"message\":\"Payment Failed\"}");
                return;
            }

            TransactionDAO transactionDAO = new TransactionDAO();

            transactionDAO.saveUpiTransaction(
                    fromAccount,
                    sender.getCustomerName(),
                    "UPI DEBIT",
                    amount,
                    sender.getBalance() - amount
            );

            transactionDAO.saveUpiTransaction(
                    toAccount,
                    receiver.getCustomerName(),
                    "UPI CREDIT",
                    amount,
                    receiver.getBalance() + amount
            );

            NotificationDAO notificationDAO = new NotificationDAO();

            notificationDAO.saveNotification(
                    sender.getCustomerId(),
                    "UPI Payment",
                    "₹" + amount + " sent to " + toUpiId
            );

            notificationDAO.saveNotification(
                    receiver.getCustomerId(),
                    "UPI Payment",
                    "₹" + amount + " received from " + fromAccount
            );

            out.print("{");
            out.print("\"status\":\"success\",");
            out.print("\"message\":\"UPI Payment Successful\",");
            out.print("\"fromAccount\":\"" + fromAccount + "\",");
            out.print("\"toUpiId\":\"" + toUpiId + "\",");
            out.print("\"amount\":\"" + amount + "\"");
            out.print("}");

        } catch (NumberFormatException e) {

            out.print("{");
            out.print("\"status\":\"failed\",");
            out.print("\"message\":\"Invalid Amount\"");
            out.print("}");

        } catch (Exception e) {

            e.printStackTrace();

            out.print("{");
            out.print("\"status\":\"error\",");
            out.print("\"message\":\"Server Error\"");
            out.print("}");

        } finally {

            out.flush();
            out.close();

        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }
}