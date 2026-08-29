package com.bank.api;

import com.bank.dao.TransactionDAO;
import com.bank.model.Transaction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/transactions")
public class TransactionApiServlet extends HttpServlet {
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        Map<String, Object> apiResponse = new HashMap<>();

        try {
            String accountNumber = request.getParameter("accountNumber");

            if (accountNumber == null || accountNumber.trim().isEmpty()) {
                apiResponse.put("status", "failed");
                apiResponse.put("message", "Account Number is required");
                out.print(gson.toJson(apiResponse));
                return;
            }

            TransactionDAO dao = new TransactionDAO();
            // Fetch transactions using the cleaned account number
            List<Transaction> list = dao.getTransactionsByAccount(accountNumber.trim());

            apiResponse.put("status", "success");
            apiResponse.put("transactions", list);
            
            out.print(gson.toJson(apiResponse));

        } catch (Exception e) {
            e.printStackTrace();
            apiResponse.put("status", "error");
            apiResponse.put("message", "Server Error: " + e.getMessage());
            out.print(gson.toJson(apiResponse));
        } finally {
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
