package com.bank.api;

import com.bank.dao.ServiceRequestDAO;
import com.bank.model.ServiceRequest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/myRequests")
public class MyRequestApiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {

            String customerIdStr = request.getParameter("customerId");

            if (customerIdStr == null || customerIdStr.trim().isEmpty()) {
                out.print("{\"status\":\"failed\",\"message\":\"Customer ID is required\"}");
                return;
            }

            int customerId = Integer.parseInt(customerIdStr);

            ServiceRequestDAO dao = new ServiceRequestDAO();
            List<ServiceRequest> requests = dao.getRequestsByCustomerId(customerId);

            StringBuilder json = new StringBuilder();

            json.append("{");
            json.append("\"status\":\"success\",");
            json.append("\"requests\":[");

            for (int i = 0; i < requests.size(); i++) {

                ServiceRequest r = requests.get(i);

                json.append("{");
                json.append("\"requestId\":").append(r.getRequestId()).append(",");
                json.append("\"requestType\":\"").append(r.getRequestType()).append("\",");
                json.append("\"status\":\"").append(r.getStatus()).append("\",");
                json.append("\"requestDate\":\"").append(r.getRequestDate()).append("\"");

                json.append("}");

                if (i < requests.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]}");

            out.print(json.toString());

        } catch (Exception e) {

            e.printStackTrace();

            out.print("{\"status\":\"error\",\"message\":\"Server Error\"}");
        }

        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}