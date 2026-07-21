package com.bank.controller;

import com.bank.dao.KYCRequestDAO;
import com.bank.model.KYCRequest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AdminKYCViewServlet")
public class AdminKYCViewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {

            int customerId = Integer.parseInt(request.getParameter("customerId"));

            KYCRequestDAO dao = new KYCRequestDAO();

            KYCRequest kyc = dao.getKYCByCustomerId(customerId);

            request.setAttribute("kyc", kyc);

            request.getRequestDispatcher("admin/kyc/view.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("AdminKYCServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}