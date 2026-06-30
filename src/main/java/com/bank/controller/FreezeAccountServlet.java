package com.bank.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bank.util.DBConnection;

@WebServlet("/FreezeAccountServlet")
public class FreezeAccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String customerId = request.getParameter("customerId");

        try {

            Connection con = DBConnection.getConnection();

            String sql = "UPDATE customer SET status=? WHERE customer_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "FREEZE");
            ps.setInt(2, Integer.parseInt(customerId));

            int i = ps.executeUpdate();

            if (i > 0) {
                response.sendRedirect(
                        request.getContextPath()
                        + "/CustomerProfileServlet?customerId="
                        + customerId
                        + "&msg=freeze");
            } else {
                response.sendRedirect(
                        request.getContextPath()
                        + "/CustomerProfileServlet?customerId="
                        + customerId
                        + "&msg=error");
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(
                    request.getContextPath()
                    + "/CustomerProfileServlet?customerId="
                    + customerId
                    + "&msg=error");
        }
    }
}