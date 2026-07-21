package com.bank.api;

import com.bank.dao.ServiceRequestDAO;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/admin/atm/approve")
public class AdminApproveATMApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            String requestIdStr = request.getParameter("requestId");
            String approvedBy = request.getParameter("approvedBy");
            String remarks = request.getParameter("remarks");
            String expectedDelivery = request.getParameter("expectedDelivery");

            if (requestIdStr == null || requestIdStr.trim().isEmpty()) {

                response.getWriter().print(
                        "{\"success\":false,"
                        + "\"status\":\"failed\","
                        + "\"message\":\"Request ID is required\"}");
                return;
            }

            int requestId = Integer.parseInt(requestIdStr);

            Date deliveryDate = null;

            if (expectedDelivery != null
                    && !expectedDelivery.trim().isEmpty()) {

                deliveryDate = Date.valueOf(expectedDelivery);
            }

            ServiceRequestDAO dao = new ServiceRequestDAO();

            boolean status = dao.approveRequest(
                    requestId,
                    approvedBy,
                    remarks,
                    deliveryDate);

            if (status) {

                response.getWriter().print(
                        "{"
                        + "\"success\":true,"
                        + "\"status\":\"success\","
                        + "\"message\":\"ATM Card Approved Successfully\""
                        + "}");

            } else {

                response.getWriter().print(
                        "{"
                        + "\"success\":false,"
                        + "\"status\":\"failed\","
                        + "\"message\":\"Approval Failed\""
                        + "}");
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().print(
                    "{"
                    + "\"success\":false,"
                    + "\"status\":\"error\","
                    + "\"message\":\""
                    + e.getMessage().replace("\"", "\\\"")
                    + "\""
                    + "}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }
}