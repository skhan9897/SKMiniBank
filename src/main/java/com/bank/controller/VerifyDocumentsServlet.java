package com.bank.controller;

import com.bank.dao.LoanDAO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/VerifyDocumentsServlet")
public class VerifyDocumentsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int loanId = Integer.parseInt(request.getParameter("loanId"));

            LoanDAO dao = new LoanDAO();

            boolean status = dao.verifyDocuments(loanId);

            if (status) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/LoanProfileServlet?loanId="
                        + loanId
                        + "&msg=verified");

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
                    + "/LoanProfileServlet?msg=error");

        }

    }

}