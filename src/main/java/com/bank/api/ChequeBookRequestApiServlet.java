package com.bank.api;

import com.bank.service.ServiceRequestService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/chequebook/apply")
public class ChequeBookRequestApiServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            String customerIdStr =
                    request.getParameter("customerId");

            String accountNumber =
                    request.getParameter("accountNumber");

            String chequeType =
                    request.getParameter("chequeType");

            // =========================
            // VALIDATION
            // =========================

            if (customerIdStr == null
                    || customerIdStr.trim().isEmpty()
                    || accountNumber == null
                    || accountNumber.trim().isEmpty()
                    || chequeType == null
                    || chequeType.trim().isEmpty()) {

                response.getWriter().print(
                        "{"
                        + "\"success\":false,"
                        + "\"status\":\"failed\","
                        + "\"message\":\"All fields are required\""
                        + "}"
                );

                return;
            }

            int customerId =
                    Integer.parseInt(customerIdStr);

            // =========================
            // SAME COMMON SERVICE
            // =========================

            ServiceRequestService service =
                    new ServiceRequestService();

            boolean saved =
                    service.submitChequeBookRequest(
                            customerId,
                            accountNumber,
                            chequeType
                    );

            // =========================
            // RESPONSE
            // =========================

            if (saved) {

                response.getWriter().print(
                        "{"
                        + "\"success\":true,"
                        + "\"status\":\"success\","
                        + "\"message\":\"Cheque Book request submitted successfully\""
                        + "}"
                );

            } else {

                response.getWriter().print(
                        "{"
                        + "\"success\":false,"
                        + "\"status\":\"failed\","
                        + "\"message\":\"Unable to submit Cheque Book request\""
                        + "}"
                );
            }

        } catch (NumberFormatException e) {

            response.getWriter().print(
                    "{"
                    + "\"success\":false,"
                    + "\"status\":\"failed\","
                    + "\"message\":\"Invalid Customer ID\""
                    + "}"
            );

        } catch (Exception e) {

            e.printStackTrace();

            String message =
                    e.getMessage() == null
                            ? "Server Error"
                            : e.getMessage().replace("\"", "\\\"");

            response.getWriter().print(
                    "{"
                    + "\"success\":false,"
                    + "\"status\":\"error\","
                    + "\"message\":\"" + message + "\""
                    + "}"
            );
        }
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }
}