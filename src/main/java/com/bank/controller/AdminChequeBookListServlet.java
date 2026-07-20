package com.bank.controller;

import com.bank.dao.ChequeBookRequestDAO;
import com.bank.model.ChequeBookRequest;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/AdminChequeBookListServlet")
public class AdminChequeBookListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        ChequeBookRequestDAO dao = new ChequeBookRequestDAO();

        List<ChequeBookRequest> list = dao.getAllPendingRequests();

        request.setAttribute("list", list);

        request.getRequestDispatcher("/admin/cheque-book-requests.jsp")
                .forward(request, response);

    }

}