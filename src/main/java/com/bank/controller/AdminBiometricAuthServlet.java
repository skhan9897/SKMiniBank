package com.bank.controller;

import com.bank.dao.AdminBiometricDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/AdminBiometricAuthServlet")
public class AdminBiometricAuthServlet extends HttpServlet {

    private AdminBiometricDAO dao = new AdminBiometricDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        response.setContentType("text/plain");

        if ("check".equals(action)) {
            String authId = request.getParameter("authId");
            String status = dao.checkStatus(authId);
            response.getWriter().write(status);
        } 
        else if ("get_pending".equals(action)) {
            String adminId = request.getParameter("adminId");
            String authId = dao.getPendingRequestForAdmin(adminId);
            response.getWriter().write(authId != null ? authId : "NONE");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String authId = request.getParameter("authId");
        
        if ("approve".equals(action)) {
            boolean success = dao.approveRequest(authId);
            response.getWriter().write(success ? "SUCCESS" : "FAILED");
        }
    }
}
