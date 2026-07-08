package com.bank.controller;

import com.bank.dao.InternetBankingDAO;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ApproveInternetBankingServlet")
public class ApproveInternetBankingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int id = Integer.parseInt(request.getParameter("id"));

            InternetBankingDAO dao = new InternetBankingDAO();

            boolean status = dao.approveRequest(id);

            if (status) {

                response.sendRedirect(request.getContextPath()
                        + "/InternetBankingListServlet?msg=approved");

            } else {

                response.sendRedirect(request.getContextPath()
                        + "/admin/internet-banking-list.jsp?msg=failed");
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(request.getContextPath()
                    + "/admin/internet-banking-list.jsp?msg=error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}