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

            HttpSession session = request.getSession();
            session.setAttribute("admin", adminId);

            response.sendRedirect("admin/SKMiniBank-System.jsp");

        } else {

            request.setAttribute("error", "Invalid Admin ID or Password");
            request.getRequestDispatcher("admin-login.jsp")
                   .forward(request, response);

        }
    }
}