package com.bank.controller;

import com.bank.dao.AccountDAO;
import com.bank.model.Account;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AccountServlet")
public class AccountServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Account account = new Account();

            account.setCustomerId(Integer.parseInt(request.getParameter("customerId")));

            Random random = new Random();
            String accountNumber = "SKB" + (100000 + random.nextInt(900000));

            account.setAccountNumber(accountNumber);

            account.setAccountType(request.getParameter("accountType"));

            account.setBranchId(Integer.parseInt(request.getParameter("branchId")));

            account.setBalance(Double.parseDouble(request.getParameter("balance")));

            account.setStatus("ACTIVE");

            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            account.setOpeningDate(date);

            AccountDAO dao = new AccountDAO();

            boolean status = dao.openAccount(account);

            if (status) {

                response.sendRedirect("admin/account-list.jsp?msg=Account Opened Successfully");

            } else {

                response.sendRedirect("admin/open-account.jsp?msg=Failed");

            }

        } catch (Exception e) {

            e.printStackTrace();
            response.sendRedirect("admin/open-account.jsp?msg=Error");

        }

    }

}