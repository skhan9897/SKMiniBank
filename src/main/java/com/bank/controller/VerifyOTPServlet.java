package com.bank.controller;

import com.bank.util.Fast2SMSOTPUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/VerifyOTPServlet")
public class VerifyOTPServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");

        String mobile = request.getParameter("mobile");
        String otp = request.getParameter("otp");

        if (mobile == null || otp == null ||
            mobile.trim().isEmpty() || otp.trim().isEmpty()) {

            response.getWriter().print("Invalid Request");
            return;
        }

        String result = Fast2SMSOTPUtil.verifyOTP(mobile, otp);

        if (result.toLowerCase().contains("success")
                || result.contains("\"return\":true")) {

            response.getWriter().print("success");

        } else {

            response.getWriter().print("failed");

        }

    }
}