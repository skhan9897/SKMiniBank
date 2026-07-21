package com.bank.api;

import com.bank.dao.ServiceRequestDAO;
import com.bank.model.ServiceRequest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/mobilebanking/apply")
public class MobileBankingRequestApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            int customerId = Integer.parseInt(request.getParameter("customerId"));
            String accountNumber = request.getParameter("accountNumber");

            ServiceRequest service = new ServiceRequest();
            service.setCustomerId(customerId);
            service.setAccountNumber(accountNumber);
            service.setRequestType("MOBILE_BANKING");
            service.setRequestDetails("Mobile Banking Activation");

            ServiceRequestDAO dao = new ServiceRequestDAO();

            boolean success = dao.saveRequest(service);

            if (success) {

                response.getWriter().print(
                        "{\"success\":true,"
                        + "\"status\":\"success\","
                        + "\"message\":\"Mobile Banking request submitted successfully\"}");

            } else {

                response.getWriter().print(
                        "{\"success\":false,"
                        + "\"status\":\"failed\","
                        + "\"message\":\"Unable to submit request\"}");
            }

        } catch (Exception e) {

            e.printStackTrace();

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