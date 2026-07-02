package com.bank.controller;

import com.bank.util.DBConnection;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/login")
public class ApiLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        JsonObject json = new JsonObject();

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users WHERE email=? AND password=?");

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                json.addProperty("status", true);
                json.addProperty("userId", rs.getInt("user_id"));
                json.addProperty("username", rs.getString("username"));
                json.addProperty("email", rs.getString("email"));
                json.addProperty("role", rs.getString("role"));

            } else {

                json.addProperty("status", false);
                json.addProperty("message", "Invalid Email or Password");

            }

            response.getWriter().print(json.toString());

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            json.addProperty("status", false);
            json.addProperty("message", e.getMessage());

            response.getWriter().print(json.toString());
        }
    }
}