package com.bank.api;

import com.bank.dao.UpiDAO;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/api/setUpiPin")
public class SetUpiPinApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
            String accountNumber = request.getParameter("accountNumber");
            String upiPin = request.getParameter("upiPin");
            String otp = request.getParameter("otp");
            // String transactionPin = request.getParameter("transactionPin"); // Not strictly required for bypass

            if (accountNumber == null || accountNumber.trim().isEmpty()
                    || upiPin == null || upiPin.length() != 4) {

                out.print("{\"status\":\"failed\",\"message\":\"Invalid Account Number or 4-digit UPI PIN\"}");
                return;
            }

            // OTP Bypass Logic
            if (otp == null || !otp.equals("9897")) {
                out.print("{\"status\":\"failed\",\"message\":\"Invalid OTP\"}");
                return;
            }

            UpiDAO dao = new UpiDAO();

            if (dao.setUpiPin(accountNumber, upiPin)) {
                out.print("{\"status\":\"success\",\"message\":\"UPI PIN Set Successfully\"}");
            } else {
                out.print("{\"status\":\"failed\",\"message\":\"Unable to Set UPI PIN. Account may not be active.\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"status\":\"error\",\"message\":\"Server Error: " + e.getMessage() + "\"}");
        }

        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
