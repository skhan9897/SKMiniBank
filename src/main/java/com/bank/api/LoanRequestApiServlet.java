package com.bank.api;

import com.bank.service.ServiceRequestService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/loan/apply")
public class LoanRequestApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            System.out.println("DEBUG: Loan Request Received via API");
            
            String customerIdStr = request.getParameter("customerId");
            String accountNumber = request.getParameter("accountNumber");
            String loanType = request.getParameter("loanType");
            String amountStr = request.getParameter("loanAmount");
            String tenureStr = request.getParameter("tenureMonths");
            String incomeStr = request.getParameter("monthlyIncome");
            String purpose = request.getParameter("purpose");

            if (customerIdStr == null || accountNumber == null || loanType == null || amountStr == null) {
                System.out.println("DEBUG: Loan Request missing parameters");
                response.getWriter().print("{\"status\":\"failed\",\"message\":\"Missing parameters\"}");
                return;
            }

            int customerId = Integer.parseInt(customerIdStr);
            double amount = Double.parseDouble(amountStr);
            int tenure = Integer.parseInt(tenureStr);
            double income = Double.parseDouble(incomeStr);

            ServiceRequestService service = new ServiceRequestService();
            boolean success = service.submitLoanRequest(customerId, accountNumber, loanType, amount, tenure, income, purpose);

            if (success) {
                System.out.println("DEBUG: Loan Request Saved Successfully to service_request table");
                response.getWriter().print("{\"status\":\"success\",\"message\":\"Loan Request Submitted Successfully\"}");
            } else {
                System.out.println("DEBUG: Loan Request Save Failed in Service Layer");
                response.getWriter().print("{\"status\":\"failed\",\"message\":\"Unable to Submit Loan Request\"}");
            }
        } catch (Exception e) {
            System.out.println("DEBUG: Error in Loan API: " + e.getMessage());
            e.printStackTrace();
            response.getWriter().print("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}
