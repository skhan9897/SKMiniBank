package com.bank.api;

import com.bank.dao.KYCRequestDAO;
import com.bank.model.KYCRequest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/kyc/submit")
public class KYCRequestApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            KYCRequest kyc = new KYCRequest();

            kyc.setCustomerId(
                    Integer.parseInt(request.getParameter("customerId")));

            kyc.setAccountNumber(
                    request.getParameter("accountNumber"));

            kyc.setAadhaarNumber(
                    request.getParameter("aadhaarNumber"));

            kyc.setPanNumber(
                    request.getParameter("panNumber"));

            kyc.setAadhaarFront(
                    request.getParameter("aadhaarFront"));

            kyc.setAadhaarBack(
                    request.getParameter("aadhaarBack"));

            kyc.setPanImage(
                    request.getParameter("panImage"));

            kyc.setCustomerPhoto(
                    request.getParameter("customerPhoto"));

            kyc.setSignatureImage(
                    request.getParameter("signatureImage"));

            KYCRequestDAO dao = new KYCRequestDAO();

            boolean success = dao.submitKYC(kyc);

            if (success) {

                response.getWriter().print(
                        "{"
                        + "\"success\":true,"
                        + "\"status\":\"success\","
                        + "\"message\":\"KYC Submitted Successfully\""
                        + "}");

            } else {

                response.getWriter().print(
                        "{"
                        + "\"success\":false,"
                        + "\"status\":\"failed\","
                        + "\"message\":\"Unable to Submit KYC\""
                        + "}");
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().print(
                    "{"
                    + "\"success\":false,"
                    + "\"status\":\"error\","
                    + "\"message\":\""
                    + e.getMessage().replace("\"", "\\\"")
                    + "\""
                    + "}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }
}