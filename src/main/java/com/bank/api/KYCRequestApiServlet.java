package com.bank.api;

import com.bank.service.ServiceRequestService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/kyc/submit")
public class KYCRequestApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            int customerId = Integer.parseInt(request.getParameter("customerId"));
            String accountNumber = request.getParameter("accountNumber");
            String aadhaar = request.getParameter("aadhaarNumber");
            String pan = request.getParameter("panNumber");

            ServiceRequestService service = new ServiceRequestService();
            boolean success = service.submitKYCRequest(customerId, accountNumber, aadhaar, pan);

            if (success) {
                response.getWriter().print("{\"status\":\"success\",\"message\":\"KYC Update Request Submitted\"}");
            } else {
                response.getWriter().print("{\"status\":\"failed\",\"message\":\"Unable to submit request\"}");
            }
        } catch (Exception e) {
            response.getWriter().print("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}
