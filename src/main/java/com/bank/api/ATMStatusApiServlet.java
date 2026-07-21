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

@WebServlet("/api/atm/status")
public class ATMStatusApiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            String customerIdStr = request.getParameter("customerId");

            if (customerIdStr == null || customerIdStr.trim().isEmpty()) {
                response.getWriter().print(
                        "{\"success\":false,\"message\":\"Customer ID is required\"}");
                return;
            }

            int customerId = Integer.parseInt(customerIdStr);

            ServiceRequestDAO dao = new ServiceRequestDAO();
            ServiceRequest atm = dao.getLatestATMRequest(customerId);

            if (atm == null) {
                response.getWriter().print(
                        "{\"success\":false,\"message\":\"No ATM request found\"}");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String requestDate = "";
            String approvalDate = "";
            String expectedDeliveryDate = "";
            String dispatchedDate = "";
            String deliveredDate = "";
            String remarks = "";
            String approvedBy = "";

            if (atm.getRequestDate() != null)
                requestDate = sdf.format(atm.getRequestDate());

            if (atm.getApprovalDate() != null)
                approvalDate = sdf.format(atm.getApprovalDate());

            if (atm.getExpectedDeliveryDate() != null)
                expectedDeliveryDate = atm.getExpectedDeliveryDate().toString();

            if (atm.getDispatchedDate() != null)
                dispatchedDate = atm.getDispatchedDate().toString();

            if (atm.getDeliveredDate() != null)
                deliveredDate = atm.getDeliveredDate().toString();

            if (atm.getRemarks() != null)
                remarks = atm.getRemarks();

            if (atm.getApprovedBy() != null)
                approvedBy = atm.getApprovedBy();

            String cardType = atm.getRequestDetails();

            String json =
                    "{"
                    + "\"success\":true,"
                    + "\"message\":\"ATM Request Found\","
                    + "\"requestId\":" + atm.getRequestId() + ","
                    + "\"accountNumber\":\"" + safe(atm.getAccountNumber()) + "\","
                    + "\"cardType\":\"" + safe(cardType) + "\","
                    + "\"status\":\"" + safe(atm.getStatus()) + "\","
                    + "\"requestDate\":\"" + requestDate + "\","
                    + "\"approvalDate\":\"" + approvalDate + "\","
                    + "\"expectedDeliveryDate\":\"" + expectedDeliveryDate + "\","
                    + "\"dispatchedDate\":\"" + dispatchedDate + "\","
                    + "\"deliveredDate\":\"" + deliveredDate + "\","
                    + "\"approvedBy\":\"" + safe(approvedBy) + "\","
                    + "\"remarks\":\"" + safe(remarks) + "\""
                    + "}";

            response.getWriter().print(json);

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().print(
                    "{\"success\":false,\"message\":\""
                    + safe(e.getMessage())
                    + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

    private String safe(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"");
    }
}