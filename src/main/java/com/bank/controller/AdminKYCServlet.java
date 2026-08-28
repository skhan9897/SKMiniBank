package com.bank.controller;

import com.bank.dao.KYCRequestDAO;
import com.bank.model.KYCRequest;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AdminKYCServlet")
public class AdminKYCServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        javax.servlet.http.HttpSession session = request.getSession(false);
        String ctx = request.getContextPath();
        if ("/".equals(ctx)) ctx = "";

        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(ctx + "/SKMiniBankadmin-login.jsp");
            return;
        }

        KYCRequestDAO dao = new KYCRequestDAO();
        List<KYCRequest> kycList = dao.getAllKYCRequests();

        request.setAttribute("kycList", kycList);
        request.getRequestDispatcher("/admin/kyc/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
