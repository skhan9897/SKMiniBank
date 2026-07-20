package com.bank.controller;

import com.bank.dao.ChequeBookRequestDAO;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AdminChequeBookServlet")
public class AdminChequeBookServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect("SKMiniBankadmin-login.jsp");
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));

        String action = request.getParameter("action");

        String remarks = request.getParameter("remarks");

        int approvedBy = (Integer) session.getAttribute("admin");

        ChequeBookRequestDAO dao = new ChequeBookRequestDAO();

        boolean status = false;

        if ("APPROVE".equalsIgnoreCase(action)) {

    Date expectedDelivery = Date.valueOf(request.getParameter("expectedDelivery"));
    status = dao.approveRequest(id, approvedBy, remarks, expectedDelivery);

} else if ("REJECT".equalsIgnoreCase(action)) {

    status = dao.rejectRequest(id, approvedBy, remarks);

} else if ("DISPATCH".equalsIgnoreCase(action)) {

    status = dao.dispatchRequest(id);

} else if ("DELIVER".equalsIgnoreCase(action)) {

    status = dao.deliverRequest(id);

}

        if (status) {
            response.sendRedirect("admin/cheque-book-requests.jsp?msg=success");
        } else {
            response.sendRedirect("admin/cheque-book-requests.jsp?msg=error");
        }

    }

}