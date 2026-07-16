package com.bank.controller;

import com.bank.dao.LoanDocumentDAO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoanDocumentsVerifyServlet")
public class LoanDocumentsVerifyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int loanId =
                    Integer.parseInt(request.getParameter("loanId"));

            String verifiedBy = "ADMIN";

            LoanDocumentDAO dao = new LoanDocumentDAO();

            boolean status =
                    dao.verifyDocuments(loanId, verifiedBy);

            if (status) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/LoanProfileServlet?loanId="
                        + loanId
                        + "&msg=documentVerified");

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

}