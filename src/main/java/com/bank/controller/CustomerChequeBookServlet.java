package com.bank.controller;

import com.bank.dao.ChequeBookRequestDAO;
import com.bank.model.ChequeBookRequest;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/CustomerChequeBookServlet")
public class CustomerChequeBookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int customerId =
                Integer.parseInt(request.getParameter("customerId"));

        ChequeBookRequestDAO dao = new ChequeBookRequestDAO();

        List<ChequeBookRequest> list =
                dao.getRequestsByCustomerId(customerId);

        request.setAttribute("list", list);

        request.getRequestDispatcher("/customer/my-cheque-book-requests.jsp")
               .forward(request, response);

    }
}