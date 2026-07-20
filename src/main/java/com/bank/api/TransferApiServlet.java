package com.bank.api;

import com.bank.dao.AccountDAO;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/transfer")
public class TransferApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {

            String fromAccount = request.getParameter("fromAccount");
            String toAccount = request.getParameter("toAccount");
            String amountStr = request.getParameter("amount");
            String description = request.getParameter("description");

            if (fromAccount == null || fromAccount.trim().isEmpty()
                    || toAccount == null || toAccount.trim().isEmpty()
                    || amountStr == null || amountStr.trim().isEmpty()) {

                out.print("{\"status\":\"failed\",\"message\":\"All fields are required\"}");
                return;
            }

            double amount = Double.parseDouble(amountStr);

            if (amount <= 0) {
                out.print("{\"status\":\"failed\",\"message\":\"Invalid Amount\"}");
                return;
            }

            AccountDAO dao = new AccountDAO();

            boolean status = dao.transferAmount(
                    fromAccount,
                    toAccount,
                    amount,
                    description
            );

            if (status) {

                out.print("{");
                out.print("\"status\":\"success\",");
                out.print("\"message\":\"Amount transferred successfully\"");
                out.print("}");

            } else {

                out.print("{");
                out.print("\"status\":\"failed\",");
                out.print("\"message\":\"Transfer Failed\"");
                out.print("}");
            }

        } catch (Exception e) {

            e.printStackTrace();

            out.print("{");
            out.print("\"status\":\"error\",");
            out.print("\"message\":\"Server Error\"");
            out.print("}");
        }

        out.flush();
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }
}