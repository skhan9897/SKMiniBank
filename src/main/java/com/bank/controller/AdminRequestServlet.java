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

        // =========================
        // ADMIN LOGIN CHECK
        // =========================
        if (session == null || session.getAttribute("admin") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/admin-login.jsp"
            );
            return;
        }

        try {

            // =========================
            // GET FORM DATA
            // =========================
            String requestIdStr = request.getParameter("requestId");
            String action = request.getParameter("action");
            String remarks = request.getParameter("remarks");

            if (requestIdStr == null
                    || requestIdStr.trim().isEmpty()
                    || action == null
                    || action.trim().isEmpty()) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/AdminATMRequestServlet?msg=invalid"
                );
                return;
            }

            int requestId = Integer.parseInt(requestIdStr);

            String approvedBy =
                    session.getAttribute("admin").toString();

            ServiceRequestDAO dao =
                    new ServiceRequestDAO();

            boolean status = false;

            // =========================
            // ACTION
            // =========================
            switch (action.toUpperCase()) {

                case "APPROVE":

                    Date expectedDelivery = null;

                    String delivery =
                            request.getParameter(
                                    "expectedDelivery"
                            );

                    if (delivery != null
                            && !delivery.trim().isEmpty()) {

                        expectedDelivery =
                                Date.valueOf(delivery);
                    }

                    status = dao.approveRequest(
                            requestId,
                            approvedBy,
                            remarks,
                            expectedDelivery
                    );

                    break;

                case "REJECT":

                    status = dao.rejectRequest(
                            requestId,
                            approvedBy,
                            remarks
                    );

                    break;

                case "DISPATCH":

                    status =
                            dao.dispatchRequest(
                                    requestId
                            );

                    break;

                case "DELIVER":

                    status =
                            dao.deliverRequest(
                                    requestId
                            );

                    break;

                default:

                    response.sendRedirect(
                            request.getContextPath()
                            + "/AdminATMRequestServlet?msg=invalidAction"
                    );
                    return;
            }

            // =========================
            // REDIRECT BACK TO ATM LIST
            // =========================
            if (status) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/AdminATMRequestServlet?msg=success"
                );

            } else {

                response.sendRedirect(
                        request.getContextPath()
                        + "/AdminATMRequestServlet?msg=failed"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    request.getContextPath()
                    + "/AdminATMRequestServlet?msg=error"
            );
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect(
                request.getContextPath()
                + "/AdminATMRequestServlet"
        );
    }
}