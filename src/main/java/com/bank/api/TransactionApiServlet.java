package com.bank.api;

import com.bank.dao.TransactionDAO;
import com.bank.model.Transaction;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/transactions")
public class TransactionApiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {

            String accountNumber = request.getParameter("accountNumber");

            if (accountNumber == null || accountNumber.trim().isEmpty()) {

                out.print("{\"status\":\"failed\",\"message\":\"Account Number is required\"}");
                return;
            }

            TransactionDAO dao = new TransactionDAO();
            List<Transaction> list = dao.getTransactionsByAccount(accountNumber);

            StringBuilder json = new StringBuilder();

            json.append("{");
            json.append("\"status\":\"success\",");
            json.append("\"transactions\":[");

            for (int i = 0; i < list.size(); i++) {

                Transaction t = list.get(i);

                json.append("{");
                json.append("\"transactionId\":").append(t.getTransactionId()).append(",");
                json.append("\"transactionType\":\"").append(t.getTransactionType()).append("\",");
                json.append("\"amount\":").append(t.getAmount()).append(",");
                json.append("\"balance\":").append(t.getBalance()).append(",");
                json.append("\"description\":\"").append(t.getDescription()).append("\",");
                json.append("\"transactionDate\":\"").append(t.getTransactionDate()).append("\"");
                json.append("}");

                if (i < list.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]}");

            out.print(json.toString());

        } catch (Exception e) {

            e.printStackTrace();

            out.print("{\"status\":\"error\",\"message\":\"Server Error\"}");
        }

        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}