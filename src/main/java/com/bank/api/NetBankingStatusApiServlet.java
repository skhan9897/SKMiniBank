package com.bank.api;

import com.bank.dao.ServiceRequestDAO;
import com.bank.model.ServiceRequest;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/netbanking/status")
public class NetBankingStatusApiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            String customerIdStr = request.getParameter("customerId");

            if (customerIdStr == null || customerIdStr.trim().isEmpty()) {
                response.getWriter().print("{\"success\":false,\"message\":\"Customer ID is required\"}");
                return;
            }

            int customerId = Integer.parseInt(customerIdStr);
            ServiceRequestDAO dao = new ServiceRequestDAO();
            ServiceRequest service = dao.getLatestRequestByType(customerId, "NET_BANKING");

            if (service == null) {
                response.getWriter().print("{\"success\":false,\"message\":\"No Net Banking request found\"}");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String requestDate = (service.getRequestDate() != null) ? sdf.format(service.getRequestDate()) : "";
            String approvalDate = (service.getApprovalDate() != null) ? sdf.format(service.getApprovalDate()) : "";

            String json =
                    "{"
                    + "\"success\":true,"
                    + "\"status\":\"" + service.getStatus() + "\","
                    + "\"remarks\":\"" + (service.getRemarks() != null ? service.getRemarks() : "") + "\","
                    + "\"requestDate\":\"" + requestDate + "\","
                    + "\"approvalDate\":\"" + approvalDate + "\""
                    + "}";

            response.getWriter().print(json);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("{\"success\":false,\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
