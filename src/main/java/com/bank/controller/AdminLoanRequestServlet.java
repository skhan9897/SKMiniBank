package com.bank.controller;

import com.bank.dao.ServiceRequestDAO;
import com.bank.model.ServiceRequest;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AdminLoanRequestServlet")
public class AdminLoanRequestServlet extends HttpServlet {

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

        try {
            ServiceRequestDAO dao = new ServiceRequestDAO();
            // Specifically fetch LOAN type requests
            List<ServiceRequest> requestList = dao.getRequestsByType("LOAN");

            System.out.println("DEBUG: AdminLoanRequestServlet found " + (requestList != null ? requestList.size() : 0) + " loan requests.");

            request.setAttribute("requestList", requestList);
            request.getRequestDispatcher("/admin/service-requests.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp?msg=error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
