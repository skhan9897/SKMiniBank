package com.bank.api;

import com.bank.dao.ServiceRequestDAO;
import com.bank.model.ServiceRequest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/chequebook/apply")
public class ChequeBookRequestApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            int customerId = Integer.parseInt(request.getParameter("customerId"));
            String accountNumber = request.getParameter("accountNumber");
            String chequeType = request.getParameter("chequeType");

            ServiceRequest service = new ServiceRequest();
            service.setCustomerId(customerId);
            service.setAccountNumber(accountNumber);
            service.setRequestType("CHEQUE_BOOK");
            service.setRequestDetails(chequeType);

            ServiceRequestDAO dao = new ServiceRequestDAO();

            boolean status = dao.saveRequest(service);

            if (status) {

                response.getWriter().print(
                        "{\"success\":true,"
                        + "\"status\":\"success\","
                        + "\"message\":\"Cheque Book request submitted successfully\"}");

            } else {

                response.getWriter().print(
                        "{\"success\":false,"
                        + "\"status\":\"failed\","
                        + "\"message\":\"Unable to submit request\"}");
            }

        } catch (Exception e) {

            response.getWriter().print(
                    "{\"success\":false,"
                    + "\"status\":\"error\","
                    + "\"message\":\""
                    + e.getMessage().replace("\"", "\\\"")
                    + "\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }
}