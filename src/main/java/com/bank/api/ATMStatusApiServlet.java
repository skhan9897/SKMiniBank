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
                        "{\"success\":false,"
                        + "\"message\":\"Customer ID is required\"}");
                return;
            }

            int customerId = Integer.parseInt(customerIdStr);

            ServiceRequestDAO dao = new ServiceRequestDAO();

            ServiceRequest atm = dao.getLatestATMRequest(customerId);

            if (atm == null) {

                response.getWriter().print(
                        "{\"success\":false,"
                        + "\"message\":\"No ATM request found\"}");
                return;
            }

            String requestDate = "";

            if (atm.getRequestDate() != null) {
                requestDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(atm.getRequestDate());
            }

            String cardType = atm.getRequestDetails();

            String json =
                    "{"
                    + "\"success\":true,"
                    + "\"message\":\"ATM Request Found\","
                    + "\"requestId\":" + atm.getRequestId() + ","
                    + "\"accountNumber\":\"" + atm.getAccountNumber() + "\","
                    + "\"cardType\":\"" + cardType + "\","
                    + "\"status\":\"" + atm.getStatus() + "\","
                    + "\"requestDate\":\"" + requestDate + "\""
                    + "}";

            response.getWriter().print(json);

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().print(
                    "{\"success\":false,"
                    + "\"message\":\"" + e.getMessage().replace("\"", "\\\"") + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}