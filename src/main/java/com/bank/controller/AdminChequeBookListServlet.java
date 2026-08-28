package com.bank.controller;

import com.bank.dao.ServiceRequestDAO;
import com.bank.model.ServiceRequest;

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

        javax.servlet.http.HttpSession session = request.getSession(false);
        String ctx = request.getContextPath();
        if ("/".equals(ctx)) ctx = "";

        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(ctx + "/SKMiniBankadmin-login.jsp");
            return;
        }

        ServiceRequestDAO dao = new ServiceRequestDAO();

        // Fetch unified CHEQUE_BOOK requests
        List<ServiceRequest> requestList = dao.getRequestsByType("CHEQUE_BOOK");

        request.setAttribute("requestList", requestList);

        // Forward to unified results page
        request.getRequestDispatcher("/admin/service-requests.jsp")
                .forward(request, response);

    }

}
