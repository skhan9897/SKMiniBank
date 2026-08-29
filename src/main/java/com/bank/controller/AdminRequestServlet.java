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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String ctx = request.getContextPath();
        if ("/".equals(ctx)) ctx = "";

        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(ctx + "/SKMiniBankadmin-login.jsp");
            return;
        }

        try {
            String requestIdStr = request.getParameter("requestId");
            String action = request.getParameter("action");
            String remarks = request.getParameter("remarks");
            String accNo = request.getParameter("accountNumber");

            if (requestIdStr == null || action == null) {
                response.sendRedirect(ctx + "/AdminAllRequestServlet?msg=invalid");
                return;
            }

            int requestId = Integer.parseInt(requestIdStr);
            String adminName = session.getAttribute("admin").toString();
            ServiceRequestDAO dao = new ServiceRequestDAO();
            boolean status = false;

            switch (action.toUpperCase()) {
                case "VERIFY":
                    status = dao.updateRequestStatus(requestId, "DOC_VERIFICATION", remarks, adminName);
                    break;

                case "APPROVE":
                    Date exp = null;
                    if (request.getParameter("expectedDelivery") != null && !request.getParameter("expectedDelivery").isEmpty()) {
                        exp = Date.valueOf(request.getParameter("expectedDelivery"));
                    }
                    status = dao.approveRequest(requestId, adminName, remarks, exp);
                    break;

                case "REJECT":
                    status = dao.rejectRequest(requestId, adminName, remarks);
                    break;

                case "DISPATCH":
                    status = dao.dispatchRequest(requestId);
                    break;

                case "DELIVER":
                    status = dao.deliverRequest(requestId);
                    break;

                case "DISBURSE":
                    String amtStr = request.getParameter("approvedAmount");
                    if (amtStr == null || amtStr.trim().isEmpty()) {
                        response.sendRedirect("AdminAllRequestServlet?msg=error&error=Please enter approved amount");
                        return;
                    }
                    if (accNo == null || accNo.trim().equalsIgnoreCase("N/A")) {
                        response.sendRedirect("AdminAllRequestServlet?msg=error&error=Invalid Account Number (N/A)");
                        return;
                    }
                    try {
                        double amount = Double.parseDouble(amtStr);
                        status = dao.disburseLoan(requestId, accNo, amount, remarks, adminName);
                    } catch (NumberFormatException e) {
                        response.sendRedirect("AdminAllRequestServlet?msg=error&error=Invalid amount format");
                        return;
                    }
                    break;

                case "ACTIVATE":
                    String type = request.getParameter("requestType");
                    String genUser = "SKB" + (100000 + new java.util.Random().nextInt(900000));
                    String genPass = "BANK@" + (1000 + new java.util.Random().nextInt(9000));
                    String activationRemarks = "USER ID: " + genUser + " | PASSWORD: " + genPass;
                    
                    status = dao.updateRequestStatus(requestId, "ACTIVATED", activationRemarks, adminName);
                    if (status) {
                        response.sendRedirect(ctx + "/admin/activation-success.jsp?type=" + java.net.URLEncoder.encode(type, "UTF-8") 
                                + "&creds=" + java.net.URLEncoder.encode(activationRemarks, "UTF-8") 
                                + "&acc=" + accNo);
                        return;
                    }
                    break;

                case "VERIFY_KYC":
                    String aadhaar = request.getParameter("submittedAadhaar");
                    String pan = request.getParameter("submittedPan");
                    int custId = Integer.parseInt(request.getParameter("customerId"));
                    status = dao.verifyKYC(requestId, custId, aadhaar, pan, adminName);
                    if (!status) {
                        response.sendRedirect(ctx + "/AdminAllRequestServlet?msg=error&error=KYC Details Mismatch with Records");
                        return;
                    }
                    break;
            }

            if (status) {
                response.sendRedirect(ctx + "/AdminAllRequestServlet?msg=success");
            } else {
                response.sendRedirect(ctx + "/AdminAllRequestServlet?msg=failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(ctx + "/AdminAllRequestServlet?msg=error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
