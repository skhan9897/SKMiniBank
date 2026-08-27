package com.bank.controller;

import com.bank.dao.ServiceRequestDAO;
import com.bank.model.ServiceRequest;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AdminATMRequestServlet")
public class AdminATMRequestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        ServiceRequestDAO dao = new ServiceRequestDAO();
        
        // Use a broader search to ensure we catch the requests
        List<ServiceRequest> requestList = dao.getRequestsByType("ATM_CARD");
        
        // If the above returns nothing, let's try a fallback to see if ANYTHING is in the table
        if (requestList == null || requestList.isEmpty()) {
            System.out.println("DEBUG: ATM_CARD specific search returned zero. Fetching PENDING as fallback.");
            requestList = dao.getPendingRequests();
        }

        System.out.println("DEBUG: AdminATMRequestServlet sending " + (requestList != null ? requestList.size() : 0) + " items to JSP.");

        request.setAttribute("requestList", requestList);
        request.getRequestDispatcher("/admin/service-requests.jsp")
               .forward(request, response);
    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("errorMessage", "Error loading ATM requests: " + e.getMessage());
        request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
    }
}

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}
