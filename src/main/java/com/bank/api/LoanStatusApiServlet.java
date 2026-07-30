package com.bank.api;

import com.bank.dao.ServiceRequestDAO;
import com.bank.model.ServiceRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/loan/status")
public class LoanStatusApiServlet extends HttpServlet {

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
                out.print("{\"success\":false,\"message\":\"Customer ID required\"}");
                return;
            }

            int customerId = Integer.parseInt(customerIdStr);
            ServiceRequestDAO dao = new ServiceRequestDAO();
            ServiceRequest loan = dao.getLatestRequestByType(customerId, "LOAN");

            if (loan == null) {
                out.print("{\"success\":false,\"message\":\"No Loan Request Found\"}");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String requestDate = (loan.getRequestDate() != null) ? sdf.format(loan.getRequestDate()) : "";
            String approvalDate = (loan.getApprovalDate() != null) ? sdf.format(loan.getApprovalDate()) : "";

            // Parse structured details
            String details = loan.getRequestDetails();
            String loanType = "Personal Loan";
            double loanAmount = 0;
            int tenure = 12;
            double income = 0;
            String purpose = "";

            if (details != null && details.contains("|")) {
                try {
                    String[] parts = details.split("\\|");
                    for (String part : parts) {
                        if (part.startsWith("type:")) loanType = part.substring(5);
                        if (part.startsWith("amount:")) loanAmount = Double.parseDouble(part.substring(7));
                        if (part.startsWith("tenure:")) tenure = Integer.parseInt(part.substring(7));
                        if (part.startsWith("income:")) income = Double.parseDouble(part.substring(7));
                        if (part.startsWith("purpose:")) purpose = part.substring(8);
                    }
                } catch (Exception e) {
                    loanType = details; // Fallback to raw string
                }
            } else {
                loanType = details;
            }

            StringBuilder json = new StringBuilder();
            json.append("{");
            json.append("\"success\":true,");
            json.append("\"message\":\"Loan status found\",");
            json.append("\"loanId\":").append(loan.getRequestId()).append(",");
            json.append("\"accountNumber\":\"").append(safe(loan.getAccountNumber())).append("\",");
            json.append("\"loanType\":\"").append(safe(loanType)).append("\",");
            json.append("\"loanAmount\":").append(loanAmount).append(",");
            json.append("\"tenureMonths\":").append(tenure).append(",");
            json.append("\"monthlyIncome\":").append(income).append(",");
            json.append("\"purpose\":\"").append(safe(purpose)).append("\",");
            json.append("\"status\":\"").append(safe(loan.getStatus())).append("\",");
            json.append("\"remarks\":\"").append(safe(loan.getRemarks())).append("\",");
            json.append("\"approvedBy\":\"").append(safe(loan.getApprovedBy())).append("\",");
            json.append("\"requestDate\":\"").append(requestDate).append("\",");
            json.append("\"approvalDate\":\"").append(approvalDate).append("\"");
            json.append("}");

            out.print(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"success\":false,\"message\":\"Server Error: " + safe(e.getMessage()) + "\"}");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private String safe(String value) {
        if (value == null || value.equals("null")) return "";
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
