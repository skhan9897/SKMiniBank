package com.bank.api;

import com.bank.dao.LoanRequestDAO;
import com.bank.model.LoanRequest;

import java.io.IOException;
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

        try {

            String customerIdStr = request.getParameter("customerId");

            if (customerIdStr == null || customerIdStr.trim().isEmpty()) {

                response.getWriter().print(
                        "{\"success\":false,"
                        + "\"message\":\"Customer ID is required\"}");
                return;
            }

            int customerId = Integer.parseInt(customerIdStr);

            LoanRequestDAO dao = new LoanRequestDAO();

            LoanRequest loan = dao.getLoanByCustomerId(customerId);

            if (loan == null) {

                response.getWriter().print(
                        "{\"success\":false,"
                        + "\"message\":\"No Loan Request Found\"}");
                return;
            }

            String requestDate = "";
            String approvalDate = "";
            String disbursementDate = "";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            if (loan.getRequestDate() != null) {
                requestDate = sdf.format(loan.getRequestDate());
            }

            if (loan.getApprovalDate() != null) {
                approvalDate = sdf.format(loan.getApprovalDate());
            }

            if (loan.getDisbursementDate() != null) {
                disbursementDate = sdf.format(loan.getDisbursementDate());
            }

            String json =
                    "{"
                    + "\"success\":true,"
                    + "\"loanId\":" + loan.getLoanId() + ","
                    + "\"accountNumber\":\"" + loan.getAccountNumber() + "\","
                    + "\"loanType\":\"" + loan.getLoanType() + "\","
                    + "\"loanAmount\":" + loan.getLoanAmount() + ","
                    + "\"interestRate\":" + loan.getInterestRate() + ","
                    + "\"tenureMonths\":" + loan.getTenureMonths() + ","
                    + "\"monthlyIncome\":" + loan.getMonthlyIncome() + ","
                    + "\"purpose\":\"" + loan.getPurpose() + "\","
                    + "\"status\":\"" + loan.getStatus() + "\","
                    + "\"remarks\":\"" + (loan.getRemarks() == null ? "" : loan.getRemarks()) + "\","
                    + "\"approvedBy\":\"" + (loan.getApprovedBy() == null ? "" : loan.getApprovedBy()) + "\","
                    + "\"requestDate\":\"" + requestDate + "\","
                    + "\"approvalDate\":\"" + approvalDate + "\","
                    + "\"disbursementDate\":\"" + disbursementDate + "\""
                    + "}";

            response.getWriter().print(json);

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().print(
                    "{\"success\":false,"
                    + "\"message\":\""
                    + e.getMessage().replace("\"", "\\\"")
                    + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}