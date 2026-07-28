package com.bank.api;

import com.bank.service.ServiceRequestService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/submitRequest")
public class SubmitRequestApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
            int customerId = Integer.parseInt(request.getParameter("customerId"));
            String requestType = request.getParameter("requestType");

            ServiceRequestService service = new ServiceRequestService();
            boolean success = false;

            // Mapping generic request types to specific methods if needed, 
            // or just using a generic save if the ServiceRequestService supports it.
            // For now, let's use the individual methods if they match.
            
            if ("ATM Card Apply".equalsIgnoreCase(requestType)) {
                success = service.submitATMRequest(customerId, "N/A", "Classic");
            } else if ("Cheque Book Request".equalsIgnoreCase(requestType)) {
                success = service.submitChequeBookRequest(customerId, "N/A", "20 Pages");
            } else if ("Net Banking Enable".equalsIgnoreCase(requestType)) {
                success = service.submitNetBankingRequest(customerId, "N/A");
            } else if ("Mobile Banking Enable".equalsIgnoreCase(requestType)) {
                success = service.submitMobileBankingRequest(customerId, "N/A");
            } else {
                // Fallback for others
                success = service.submitATMRequest(customerId, "N/A", requestType);
            }

            if (success) {
                out.print("{\"status\":\"success\",\"message\":\"Request Submitted Successfully\"}");
            } else {
                out.print("{\"status\":\"failed\",\"message\":\"Unable to Submit Request\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"status\":\"error\",\"message\":\"Server Error\"}");
        } finally {
            out.close();
        }
    }
}
