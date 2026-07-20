package com.bank.api;

import com.bank.dao.AccountDAO;
import com.bank.model.Account;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/account")
public class AccountApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {

            String accountNumber = request.getParameter("accountNumber");

            if (accountNumber == null || accountNumber.trim().isEmpty()) {

                out.print("{");
                out.print("\"status\":\"failed\",");
                out.print("\"message\":\"Account Number Required\"");
                out.print("}");

                return;
            }

            AccountDAO dao = new AccountDAO();

            Account account = dao.getAccountByNumber(accountNumber);

            if (account != null) {

                out.print("{");
                out.print("\"status\":\"success\",");
                out.print("\"customerId\":\"" + account.getCustomerId() + "\",");
                out.print("\"customerName\":\"" + account.getCustomerName() + "\",");
                out.print("\"accountNumber\":\"" + account.getAccountNumber() + "\",");
                out.print("\"accountType\":\"" + account.getAccountType() + "\",");
                out.print("\"balance\":\"" + account.getBalance() + "\",");
                out.print("\"statusValue\":\"" + account.getStatus() + "\"");
                out.print("}");

            } else {

                out.print("{");
                out.print("\"status\":\"failed\",");
                out.print("\"message\":\"Account Not Found\"");
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