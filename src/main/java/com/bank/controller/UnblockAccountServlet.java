package com.bank.controller;

import com.bank.util.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UnblockAccountServlet")
public class UnblockAccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String customerId = request.getParameter("customerId");

        try {

            Connection con = DBConnection.getConnection();

            String sql = "UPDATE customer SET status=? WHERE customer_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "ACTIVE");
            ps.setInt(2, Integer.parseInt(customerId));

            int result = ps.executeUpdate();

            if (result > 0) {

                response.sendRedirect(request.getContextPath()
                        + "/admin/customer-profile.jsp?customerId="
                        + customerId);

            } else {

                response.sendRedirect(request.getContextPath()
                        + "/admin/customer-profile.jsp?customerId="
                        + customerId);

            }

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}