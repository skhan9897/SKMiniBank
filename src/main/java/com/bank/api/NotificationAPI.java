package com.bank.api;

import com.bank.dao.TransactionDAO;
import com.bank.model.Transaction;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/notification")
public class NotificationAPI extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {

            String accountNumber =
                    request.getParameter("accountNumber");

            if (accountNumber == null ||
                    accountNumber.trim().isEmpty()) {

                out.print(
                        "{\"status\":\"failed\",\"message\":\"Account Number Required\"}");
                return;
            }

            TransactionDAO dao = new TransactionDAO();

            List<Transaction> list =
                    dao.searchTransaction(accountNumber.trim());

            SimpleDateFormat sdf =
                    new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            StringBuilder json = new StringBuilder();

            json.append("{");
            json.append("\"status\":\"success\",");
            json.append("\"notifications\":[");

            for (int i = 0; i < list.size(); i++) {

                Transaction t = list.get(i);

                String title = t.getTransactionType();

                String message =
                        "₹ " + t.getAmount()
                        + " "
                        + t.getTransactionType()
                        + " Successful";

                json.append("{");

                json.append("\"id\":\"")
                        .append(t.getId())
                        .append("\",");

                json.append("\"title\":\"")
                        .append(title)
                        .append("\",");

                json.append("\"message\":\"")
                        .append(message)
                        .append("\",");

                json.append("\"amount\":\"")
                        .append(t.getAmount())
                        .append("\",");

                json.append("\"balance\":\"")
                        .append(t.getBalance())
                        .append("\",");

                json.append("\"status\":\"")
                        .append(t.getStatus())
                        .append("\",");

                json.append("\"date\":\"")
                        .append(sdf.format(t.getTransactionDate()))
                        .append("\"");

                json.append("}");

                if (i != list.size() - 1) {
                    json.append(",");
                }

            }

            json.append("]}");

            out.print(json.toString());

        } catch (Exception e) {

            e.printStackTrace();

            out.print(
                    "{\"status\":\"error\",\"message\":\""
                    + e.getMessage().replace("\"", "'")
                    + "\"}");

        } finally {

            out.flush();
            out.close();

        }

    }

}