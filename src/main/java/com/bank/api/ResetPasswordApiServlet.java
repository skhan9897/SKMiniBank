package com.bank.api;

import com.bank.dao.CustomerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/resetPassword")
public class ResetPasswordApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
            String accountNumber = request.getParameter("accountNumber");
            String mobile = request.getParameter("mobile");
            String newPassword = request.getParameter("newPassword");

            if (accountNumber == null || mobile == null || newPassword == null) {
                out.print("{\"status\":\"failed\",\"message\":\"Missing required fields\"}");
                return;
            }

            CustomerDAO dao = new CustomerDAO();
            boolean success = dao.resetPassword(accountNumber, mobile, newPassword);

            if (success) {
                out.print("{\"status\":\"success\",\"message\":\"Password Reset Successfully\"}");
            } else {
                out.print("{\"status\":\"failed\",\"message\":\"Invalid Account Details\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"status\":\"error\",\"message\":\"Server Error\"}");
        } finally {
            out.close();
        }
    }
}
