package com.bank.api;

import com.bank.dao.ServiceRequestDAO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/admin/netbanking/reject")
public class AdminRejectNetBankingApiServlet extends HttpServlet {

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

            if (requestIdStr == null || requestIdStr.trim().isEmpty()) {

                response.getWriter().print(
                        "{\"success\":false,"
                        + "\"status\":\"failed\","
                        + "\"message\":\"Request ID is required\"}");
                return;
            }

            int requestId = Integer.parseInt(requestIdStr);

            if (approvedBy == null || approvedBy.trim().isEmpty()) {
                approvedBy = "ADMIN";
            }

            if (remarks == null) {
                remarks = "";
            }

            ServiceRequestDAO dao = new ServiceRequestDAO();

            boolean success = dao.rejectRequest(
                    requestId,
                    approvedBy,
                    remarks);

            if (success) {

                response.getWriter().print(
                        "{"
                        + "\"success\":true,"
                        + "\"status\":\"success\","
                        + "\"message\":\"Net Banking Request Rejected Successfully\""
                        + "}");

            } else {

                response.getWriter().print(
                        "{"
                        + "\"success\":false,"
                        + "\"status\":\"failed\","
                        + "\"message\":\"Unable to Reject Request\""
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