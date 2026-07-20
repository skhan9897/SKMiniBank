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

@WebServlet("/AdminAllRequestServlet")
public class AdminAllRequestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        ServiceRequestDAO dao = new ServiceRequestDAO();

        List<ServiceRequest> list = dao.getPendingRequests();

        request.setAttribute("requestList", list);

        request.getRequestDispatcher("/admin/service-requests.jsp")
               .forward(request, response);

    }
}