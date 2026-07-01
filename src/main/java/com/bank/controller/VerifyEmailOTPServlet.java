package com.bank.controller;

import com.bank.dao.OTPDAO;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/VerifyEmailOTPServlet")
public class VerifyEmailOTPServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String otp = request.getParameter("otp");

        OTPDAO dao = new OTPDAO();

        if (dao.verifyOTP(email, otp)) {

            request.getSession().setAttribute("emailVerified", "YES");
            response.sendRedirect("register.jsp?msg=emailverified");

        } else {

            response.sendRedirect("register.jsp?msg=invalidotp");

        }
    }
}