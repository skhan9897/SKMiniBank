package com.bank.controller;

import com.bank.dao.KYCRequestDAO;
import com.bank.model.KYCRequest;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AdminKYCDetailsServlet")
public class AdminKYCDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int kycId = Integer.parseInt(request.getParameter("kycId"));
            KYCRequestDAO dao = new KYCRequestDAO();
            
            // Note: Assuming there's a method to get KYC by ID, or using customer ID logic
            // For now, let's use the customer ID logic if that's what's available
            // Looking at the DAO outline, it has getKYCByCustomerId. 
            // If it doesn't have getKYCById, we might need to add it or use an alternative.
            // Let's check the DAO again.
            
            KYCRequest kyc = dao.getAllKYCRequests().stream()
                    .filter(k -> k.getKycId() == kycId)
                    .findFirst()
                    .orElse(null);

            if (kyc != null) {
                request.setAttribute("kyc", kyc);
                request.getRequestDispatcher("/admin/kyc/view.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/kyc/list.jsp?error=not_found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/kyc/list.jsp?error=exception");
        }
    }
}
