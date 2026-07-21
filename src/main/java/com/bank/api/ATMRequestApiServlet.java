package com.bank.api;

import com.bank.dao.ServiceRequestDAO;
import com.bank.model.ServiceRequest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/atm/apply")
public class ATMRequestApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            String customerIdStr = request.getParameter("customerId");
            String accountNumber = request.getParameter("accountNumber");
            String cardType = request.getParameter("cardType");

            if (customerIdStr == null || accountNumber == null || cardType == null
                    || customerIdStr.isEmpty()
                    || accountNumber.isEmpty()
                    || cardType.isEmpty()) {

                response.getWriter().print(
                        "{\"success\":false,"
                        + "\"status\":\"failed\","
                        + "\"message\":\"All fields are required\"}");
                return;
            }

            int customerId = Integer.parseInt(customerIdStr);

            ServiceRequest service = new ServiceRequest();
            service.setCustomerId(customerId);
            service.setAccountNumber(accountNumber);
            service.setRequestType("ATM_CARD");

            // फिलहाल Card Type request_details में Store करेंगे
            service.setRequestDetails(cardType);

            ServiceRequestDAO dao = new ServiceRequestDAO();

            boolean saved = dao.saveRequest(service);

            if (saved) {

                response.getWriter().print(
                        "{"
                        + "\"success\":true,"
                        + "\"status\":\"success\","
                        + "\"message\":\"ATM Card request submitted successfully\""
                        + "}");

            } else {

                response.getWriter().print(
                        "{"
                        + "\"success\":false,"
                        + "\"status\":\"failed\","
                        + "\"message\":\"Unable to submit ATM request\""
                        + "}");
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().print(
                    "{"
                    + "\"success\":false,"
                    + "\"status\":\"error\","
                    + "\"message\":\"" + e.getMessage().replace("\"", "\\\"") + "\""
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