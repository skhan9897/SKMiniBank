package com.bank.api;

import com.bank.dao.KYCRequestDAO;
import com.bank.model.KYCRequest;

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

            KYCRequestDAO dao = new KYCRequestDAO();

            KYCRequest kyc = dao.getKYCByCustomerId(customerId);

            if (kyc == null) {

                response.getWriter().print(
                        "{\"success\":false,"
                        + "\"message\":\"No KYC Request Found\"}");
                return;
            }

            SimpleDateFormat sdf =
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String requestDate = "";
            String verificationDate = "";

            if (kyc.getRequestDate() != null) {
                requestDate = sdf.format(kyc.getRequestDate());
            }

            if (kyc.getVerificationDate() != null) {
                verificationDate = sdf.format(
                        kyc.getVerificationDate());
            }

            String json =
                    "{"
                    + "\"success\":true,"
                    + "\"kycId\":" + kyc.getKycId() + ","
                    + "\"accountNumber\":\"" + kyc.getAccountNumber() + "\","
                    + "\"aadhaarNumber\":\"" + kyc.getAadhaarNumber() + "\","
                    + "\"panNumber\":\"" + kyc.getPanNumber() + "\","
                    + "\"status\":\"" + kyc.getStatus() + "\","
                    + "\"remarks\":\"" + (kyc.getRemarks() == null ? "" : kyc.getRemarks()) + "\","
                    + "\"verifiedBy\":\"" + (kyc.getVerifiedBy() == null ? "" : kyc.getVerifiedBy()) + "\","
                    + "\"requestDate\":\"" + requestDate + "\","
                    + "\"verificationDate\":\"" + verificationDate + "\""
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