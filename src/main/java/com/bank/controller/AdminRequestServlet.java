package com.bank.controller;

import com.bank.dao.ServiceRequestDAO;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AdminRequestServlet")
public class AdminRequestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String ctx = request.getContextPath();
        if ("/".equals(ctx)) ctx = "";

        // =========================
        // ADMIN LOGIN CHECK
        // =========================
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(ctx + "/SKMiniBankadmin-login.jsp");
            return;
        }

        try {
            // =========================
            // GET FORM DATA
            // =========================
            String requestIdStr = request.getParameter("requestId");
            String action = request.getParameter("action");
            String remarks = request.getParameter("remarks");

            System.out.println("DEBUG: Processing Request ID: " + requestIdStr + ", Action: " + action);

            if (requestIdStr == null
                    || requestIdStr.trim().isEmpty()
                    || action == null
                    || action.trim().isEmpty()) {
                response.sendRedirect(ctx + "/AdminAllRequestServlet?msg=invalid");
                return;
            }

            int requestId = Integer.parseInt(requestIdStr);
            String approvedBy = session.getAttribute("admin").toString();
            ServiceRequestDAO dao = new ServiceRequestDAO();
            boolean status = false;

            // =========================
            // ACTION LOGIC
            // =========================
            switch (action.toUpperCase()) {
                case "APPROVE":
                    Date expectedDelivery = null;
                    String delivery = request.getParameter("expectedDelivery");
                    if (delivery != null && !delivery.trim().isEmpty()) {
                        try {
                            expectedDelivery = Date.valueOf(delivery);
                        } catch (Exception e) {
                            System.err.println("DEBUG: Invalid Date Format: " + delivery);
                        }
                    }
                    status = dao.approveRequest(requestId, approvedBy, remarks, expectedDelivery);
                    break;

                case "REJECT":
                    status = dao.rejectRequest(requestId, approvedBy, remarks);
                    break;

                case "DISPATCH":
                    status = dao.dispatchRequest(requestId);
                    break;

                case "DELIVER":
                    status = dao.deliverRequest(requestId);
                    break;

                default:
                    response.sendRedirect(ctx + "/AdminAllRequestServlet?msg=invalidAction");
                    return;
            }

            // =========================
            // REDIRECT BACK
            // =========================
            if (status) {
                response.sendRedirect("AdminAllRequestServlet?msg=success");
            } else {
                response.sendRedirect("AdminAllRequestServlet?msg=error&error=Database update returned 0 rows affected. Check if ID exists.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("AdminAllRequestServlet?msg=error&error=" + java.net.URLEncoder.encode(e.toString(), "UTF-8"));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        String ctx = request.getContextPath();
        if ("/".equals(ctx)) ctx = "";
        response.sendRedirect(ctx + "/AdminAllRequestServlet");
    }
}
