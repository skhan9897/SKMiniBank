package com.bank.controller;

import com.bank.dao.WalletDAO;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/wallet/transfer")
public class WalletServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();
        
        try {
            String accountNumber = request.getParameter("accountNumber");
            String walletType = request.getParameter("walletType");
            String walletNumber = request.getParameter("walletNumber");
            double amount = Double.parseDouble(request.getParameter("amount"));
            
            WalletDAO dao = new WalletDAO();
            boolean success = dao.transferToWallet(accountNumber, walletType, walletNumber, amount);
            
            if (success) {
                jsonResponse.addProperty("status", "success");
                jsonResponse.addProperty("message", "Transfer successful");
            } else {
                jsonResponse.addProperty("status", "error");
                jsonResponse.addProperty("message", "Insufficient balance or invalid account");
            }
        } catch (Exception e) {
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "Internal Server Error: " + e.getMessage());
        }
        
        out.print(jsonResponse.toString());
        out.flush();
    }
}
