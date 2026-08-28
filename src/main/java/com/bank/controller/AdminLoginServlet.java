package com.bank.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String adminId = request.getParameter("adminId");
        String password = request.getParameter("password");

        // Default Admin Login
        if ("SKMB9897".equals(adminId) && "admin123".equals(password)) {
            
            String biometricEnabled = request.getParameter("biometricEnabled");
            
            if ("true".equals(biometricEnabled)) {
                String authId = java.util.UUID.randomUUID().toString();
                com.bank.dao.AdminBiometricDAO bioDao = new com.bank.dao.AdminBiometricDAO();
                bioDao.createAuthRequest(authId, adminId);
                
                request.setAttribute("authId", authId);
                request.setAttribute("adminId", adminId);
                request.getRequestDispatcher("SKMiniBankadmin-login.jsp").forward(request, response);
                return;
            }

            HttpSession session = request.getSession();
            session.setAttribute("admin", adminId);
            session.setAttribute("role", "ADMIN");
            response.sendRedirect(request.getContextPath() + "/DashboardServlet");
        } else {

            request.setAttribute("error", "Invalid Admin ID or Password");
            request.getRequestDispatcher("SKMiniBankadmin-login.jsp")
                   .forward(request, response);

        }
    }
}