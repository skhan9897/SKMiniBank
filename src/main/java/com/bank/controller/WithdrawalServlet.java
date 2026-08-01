package com.bank.controller;

import com.bank.dao.WithdrawalDAO;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/withdrawal")
public class WithdrawalServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();
        
        try {
            String accountNumber = request.getParameter("accountNumber");
            String method = request.getParameter("method");
            double amount = Double.parseDouble(request.getParameter("amount"));
            
            WithdrawalDAO dao = new WithdrawalDAO();
            boolean success = dao.performWithdrawal(accountNumber, method, amount);
            
            if (success) {
                jsonResponse.addProperty("status", "success");
                jsonResponse.addProperty("message", "Withdrawal successful");
            } else {
                jsonResponse.addProperty("status", "error");
                jsonResponse.addProperty("message", "Transaction failed: Insufficient balance or invalid account");
            }
        } catch (Exception e) {
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "Internal Server Error: " + e.getMessage());
        }
        
        out.print(jsonResponse.toString());
        out.flush();
    }
}
