package com.bank.controller;

import com.bank.dao.OTPDAO;
import com.bank.model.OTP;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SendEmailOTPServlet")
public class SendEmailOTPServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        try {

            OTP otpObj = new OTP();
            otpObj.setEmail(email);
            otpObj.setMobile("");
            otpObj.setOtp(otp);
            otpObj.setOtpType("EMAIL");

            OTPDAO dao = new OTPDAO();
            dao.saveOTP(otpObj);

            final String senderEmail = "YOUR_GMAIL@gmail.com";
            final String senderPassword = "YOUR_16_DIGIT_APP_PASSWORD";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }

            });

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(senderEmail));

            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email));

            message.setSubject("SK Mini Bank Email Verification OTP");

            message.setText(
                    "Dear Customer,\n\n"
                    + "Your OTP is : " + otp
                    + "\n\nValid for 10 Minutes."
                    + "\n\nSK Mini Bank");

            Transport.send(message);

            response.sendRedirect("register.jsp?emailSent=true");

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect("register.jsp?emailSent=false");
        }

    }
}