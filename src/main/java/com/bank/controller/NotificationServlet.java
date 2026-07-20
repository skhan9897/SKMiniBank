package com.bank.controller;

import com.bank.dao.NotificationDAO;
import com.bank.model.Notification;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/NotificationServlet")
public class NotificationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            HttpSession session = request.getSession(false);

            if (session == null) {

                response.sendRedirect("login.jsp");
                return;
            }

            Integer customerId =
                    (Integer) session.getAttribute("customerId");

            if (customerId == null) {

                response.sendRedirect("login.jsp");
                return;
            }

            String notificationId = request.getParameter("notificationId");

NotificationDAO dao = new NotificationDAO();

if (notificationId != null && !notificationId.isEmpty()) {
    dao.markAsRead(Integer.parseInt(notificationId));
}

String action = request.getParameter("action");

if ("markAll".equals(action)) {
    dao.markAllAsRead(customerId);
}
            

            List<Notification> list =
                    dao.getNotificationsByCustomer(customerId);

            int unread =
                    dao.getUnreadCount(customerId);

            request.setAttribute("notificationList", list);
            request.setAttribute("unreadCount", unread);

            request.getRequestDispatcher("/admin/notification.jsp")
                    .forward(request, response);

        } catch (Exception e) {

            throw new ServletException(e);

        }
    }
}