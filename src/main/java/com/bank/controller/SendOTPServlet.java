package com.bank.controller;

import com.bank.util.Fast2SMSOTPUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SendOTPServlet")
public class SendOTPServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        String mobile = request.getParameter("mobile");

        // Mobile Validation
        if (mobile == null || !mobile.matches("\\d{10}")) {
            response.getWriter().print("Invalid Mobile Number");
            return;
        }

        try {

            // Fast2SMS API Call
            String apiResponse = Fast2SMSOTPUtil.sendOTP(mobile);

            System.out.println("Fast2SMS Response : " + apiResponse);

            if (apiResponse != null
                    && (apiResponse.contains("\"return\":true")
                    || apiResponse.toLowerCase().contains("success")
                    || apiResponse.toLowerCase().contains("otp"))) {

                response.getWriter().print("OTP Sent Successfully");

            } else {

                response.getWriter().print("Failed to Send OTP");

            }

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().print("Error : " + e.getMessage());

        }

    }

}