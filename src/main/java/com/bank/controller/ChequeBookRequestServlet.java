package com.bank.controller;

import com.bank.dao.ChequeBookRequestDAO;
import com.bank.model.ChequeBookRequest;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ChequeBookRequestServlet")
public class ChequeBookRequestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accountNumber = request.getParameter("accountNumber");
        String customerName = request.getParameter("customerName");
        String mobile = request.getParameter("mobile");
        int leaves = Integer.parseInt(request.getParameter("leaves"));
        String address = request.getParameter("address");

        ChequeBookRequest cheque = new ChequeBookRequest();

        cheque.setAccountNumber(accountNumber);
        cheque.setCustomerName(customerName);
        cheque.setMobile(mobile);
        cheque.setLeaves(leaves);
        cheque.setAddress(address);
        cheque.setStatus("Pending");

        ChequeBookRequestDAO dao = new ChequeBookRequestDAO();

        boolean status = dao.saveRequest(cheque);

System.out.println("Status = " + status);

if (status) {
    response.sendRedirect("admin/cheque-book-request.jsp?msg=success");
} else {
    response.sendRedirect("admin/cheque-book-request.jsp?msg=failed");
}
    }
}