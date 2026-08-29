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

@WebServlet("/api/kyc/status")
public class KYCStatusApiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String customerIdStr = request.getParameter("customerId");
            if (customerIdStr == null) {
                response.getWriter().print("{\"success\":false,\"message\":\"Customer ID required\"}");
                return;
            }

            int customerId = Integer.parseInt(customerIdStr);
            ServiceRequestDAO dao = new ServiceRequestDAO();
            ServiceRequest service = dao.getLatestRequestByType(customerId, "KYC_UPDATE");

            if (service == null) {
                response.getWriter().print("{\"success\":false,\"message\":\"No KYC Request Found\"}");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = (service.getRequestDate() != null) ? sdf.format(service.getRequestDate()) : "";

            String json = "{"
                    + "\"success\":true,"
                    + "\"status\":\"" + service.getStatus().toUpperCase() + "\","
                    + "\"remarks\":\"" + (service.getRemarks() == null ? "" : service.getRemarks()) + "\","
                    + "\"requestDate\":\"" + date + "\""
                    + "}";

            response.getWriter().print(json);
        } catch (Exception e) {
            response.getWriter().print("{\"success\":false,\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}
