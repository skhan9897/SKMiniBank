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

@WebServlet("/AdminMobileBankingServlet")
public class AdminMobileBankingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            ServiceRequestDAO dao = new ServiceRequestDAO();

            List<ServiceRequest> requestList = dao.getPendingRequests();

            request.setAttribute("requestList", requestList);

            request.getRequestDispatcher("/admin/service-requests.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();

            response.sendRedirect(request.getContextPath()
                    + "/admin/dashboard.jsp?msg=error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}