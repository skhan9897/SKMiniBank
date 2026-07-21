package com.bank.controller;

import com.bank.dao.KYCRequestDAO;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/AdminKYCActionServlet")
public class AdminKYCActionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int kycId = Integer.parseInt(request.getParameter("kycId"));
        String action = request.getParameter("action");
        String remarks = request.getParameter("remarks");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String verifiedBy = session.getAttribute("admin").toString();

        KYCRequestDAO dao = new KYCRequestDAO();

        boolean status = false;

        if ("approve".equalsIgnoreCase(action)) {

            status = dao.approveKYC(kycId, verifiedBy, remarks);

        } else if ("reject".equalsIgnoreCase(action)) {

            status = dao.rejectKYC(kycId, verifiedBy, remarks);

        }

        if (status) {
            response.sendRedirect("AdminKYCServlet?msg=success");
        } else {
            response.sendRedirect("AdminKYCServlet?msg=failed");
        }
    }
}