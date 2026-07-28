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
            int customerId = Integer.parseInt(request.getParameter("customerId"));
            String accountNumber = request.getParameter("accountNumber");
            String loanType = request.getParameter("loanType");
            double amount = Double.parseDouble(request.getParameter("loanAmount"));
            int tenure = Integer.parseInt(request.getParameter("tenureMonths"));
            double income = Double.parseDouble(request.getParameter("monthlyIncome"));
            String purpose = request.getParameter("purpose");

            ServiceRequestService service = new ServiceRequestService();
            boolean success = service.submitLoanRequest(customerId, accountNumber, loanType, amount, tenure, income, purpose);

            if (success) {
                response.getWriter().print("{\"status\":\"success\",\"message\":\"Loan Request Submitted Successfully\"}");
            } else {
                response.getWriter().print("{\"status\":\"failed\",\"message\":\"Unable to Submit Loan Request\"}");
            }
        } catch (Exception e) {
            response.getWriter().print("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}
