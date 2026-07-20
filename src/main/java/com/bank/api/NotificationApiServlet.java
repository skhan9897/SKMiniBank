package com.bank.api;

import com.bank.dao.NotificationDAO;
import com.bank.model.Notification;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/notifications")
public class NotificationApiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {

            String customerIdStr = request.getParameter("customerId");

            if (customerIdStr == null || customerIdStr.trim().isEmpty()) {

                out.print("{\"status\":\"failed\",\"message\":\"Customer ID is required\"}");
                return;
            }

            int customerId = Integer.parseInt(customerIdStr);

            NotificationDAO dao = new NotificationDAO();

            List<Notification> list = dao.getNotifications(customerId);

            StringBuilder json = new StringBuilder();

            json.append("{");
            json.append("\"status\":\"success\",");
            json.append("\"notifications\":[");

            for (int i = 0; i < list.size(); i++) {

                Notification n = list.get(i);

                json.append("{");
                json.append("\"notificationId\":").append(n.getNotificationId()).append(",");
                json.append("\"title\":\"").append(n.getTitle()).append("\",");
                json.append("\"message\":\"").append(n.getMessage()).append("\",");
                json.append("\"status\":\"").append(n.getStatus()).append("\",");
                json.append("\"createdAt\":\"").append(n.getCreatedAt()).append("\"");
                json.append("}");

                if (i < list.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]}");

            out.print(json.toString());

        } catch (Exception e) {

            e.printStackTrace();

            out.print("{\"status\":\"error\",\"message\":\"Server Error\"}");
        }

        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}