package com.bank.controller;

import com.bank.dao.ChequeBookRequestDAO;
import com.bank.model.ChequeBookRequest;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/CustomerRequestServlet")
public class CustomerRequestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("customerId") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        int customerId = (Integer) session.getAttribute("customerId");

        ChequeBookRequestDAO chequeDAO = new ChequeBookRequestDAO();

        List<ChequeBookRequest> chequeList =
                chequeDAO.getRequestsByCustomerId(customerId);

        request.setAttribute("chequeList", chequeList);

        // आगे यहीं ATM, Loan, Net Banking आदि की Lists भी जोड़ेंगे

        request.getRequestDispatcher("customer/my-requests.jsp")
               .forward(request, response);
    }
}