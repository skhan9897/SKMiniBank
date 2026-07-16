package com.bank.controller;

import com.bank.dao.LoanDAO;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoanApproveServlet")
public class LoanApproveServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String id = request.getParameter("loanId");

            if (id == null || id.trim().isEmpty()) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/LoanListServlet?msg=invalid");

                return;
            }

            int loanId = Integer.parseInt(id);

            LoanDAO dao = new LoanDAO();

            boolean status = dao.approveLoan(loanId);

            if (status) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/LoanProfileServlet?loanId="
                        + loanId
                        + "&msg=approved");

            } else {

                response.sendRedirect(
                        request.getContextPath()
                        + "/LoanProfileServlet?loanId="
                        + loanId
                        + "&msg=failed");

            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    request.getContextPath()
                    + "/LoanListServlet?msg=error");

        }

    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);

    }

}