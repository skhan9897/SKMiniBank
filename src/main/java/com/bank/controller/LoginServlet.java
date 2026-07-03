package com.bank.controller;

import com.bank.util.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            con = DBConnection.getConnection();

            String sql = "SELECT * FROM customer WHERE email=? AND password=?";

            ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            rs = ps.executeQuery();

            if (rs.next()) {

                HttpSession session = request.getSession(true);

                session.setAttribute("customerId",
                        rs.getString("customer_code"));

                session.setAttribute("customerName",
                        rs.getString("full_name"));

                session.setAttribute("accountNumber",
                        rs.getString("account_number"));

                session.setAttribute("email",
                        rs.getString("email"));

                session.setAttribute("mobile",
                        rs.getString("mobile"));

                response.sendRedirect(request.getContextPath()
                        + "/CustomerProfileServlet");

            } else {

                request.setAttribute("error",
                        "Invalid Email or Password");

                request.getRequestDispatcher("login.jsp")
                        .forward(request, response);

            }

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute("error",
                    "Database Error");

            request.getRequestDispatcher("login.jsp")
                    .forward(request, response);

        } finally {

            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
            }

            try {
                if (ps != null) ps.close();
            } catch (Exception e) {
            }

            try {
                if (con != null) con.close();
            } catch (Exception e) {
            }

        }

    }

}