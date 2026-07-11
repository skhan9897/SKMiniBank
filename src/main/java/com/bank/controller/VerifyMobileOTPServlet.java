package com.bank.controller;

import com.bank.util.Fast2SMSOTPUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/VerifyMobileOTPServlet")
public class VerifyMobileOTPServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");

        String mobile = request.getParameter("mobile");
        String otp = request.getParameter("otp");

        if (mobile == null || otp == null
                || mobile.trim().isEmpty()
                || otp.trim().isEmpty()) {

            response.getWriter().print("failed");
            return;
        }

        String result = Fast2SMSOTPUtil.verifyOTP(mobile, otp);

        if (result.contains("\"return\":true")
                || result.toLowerCase().contains("success")) {

            HttpSession session = request.getSession();
            session.setAttribute("mobileVerified", "YES");

            response.getWriter().print("success");

        } else {

            response.getWriter().print("failed");

        }

    }

}