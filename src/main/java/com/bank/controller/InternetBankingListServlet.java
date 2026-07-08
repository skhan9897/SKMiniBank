package com.bank.controller;

import com.bank.dao.InternetBankingDAO;
import com.bank.model.InternetBanking;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InternetBankingListServlet")
public class InternetBankingListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            InternetBankingDAO dao = new InternetBankingDAO();

            List<InternetBanking> requestList =
                    dao.getAllRequests();

            request.setAttribute("requestList", requestList);

            request.getRequestDispatcher(
                    "/admin/internet-banking-list.jsp")
                    .forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "admin/internet-banking-list.jsp?msg=error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}