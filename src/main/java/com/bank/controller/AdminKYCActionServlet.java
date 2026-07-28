package com.bank.controller;

import com.bank.dao.KYCRequestDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AdminKYCActionServlet")
public class AdminKYCActionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/admin-login.jsp");
            return;
        }

        try {
            int kycId = Integer.parseInt(request.getParameter("kycId"));
            String action = request.getParameter("action");
            String remarks = request.getParameter("remarks");
            String verifiedBy = session.getAttribute("admin").toString();

            KYCRequestDAO dao = new KYCRequestDAO();
            boolean success = false;

            if ("approve".equalsIgnoreCase(action)) {
                success = dao.approveKYC(kycId, verifiedBy, remarks);
            } else if ("reject".equalsIgnoreCase(action)) {
                success = dao.rejectKYC(kycId, verifiedBy, remarks);
            }

            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin/kyc-success.jsp?msg=" + action);
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/kyc.jsp?error=failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/kyc.jsp?error=exception");
        }
    }
}
