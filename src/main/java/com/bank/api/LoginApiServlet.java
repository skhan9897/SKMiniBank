package com.bank.api;

import com.bank.dao.LoginDAO;
import com.bank.model.Customer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/login")
public class LoginApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {

            String accountNumber = request.getParameter("accountNumber");
            String password = request.getParameter("password");

            LoginDAO dao = new LoginDAO();

            Customer customer = dao.login(accountNumber, password);

            if (customer != null) {

                out.print("{");
                out.print("\"status\":\"success\",");
                out.print("\"customerId\":" + customer.getCustomerId() + ",");
                out.print("\"customerCode\":\"" + customer.getCustomerCode() + "\",");
                out.print("\"customerName\":\"" + customer.getFullName() + "\",");
                out.print("\"accountNumber\":\"" + customer.getAccountNumber() + "\",");
                out.print("\"mobile\":\"" + customer.getMobile() + "\",");
                out.print("\"email\":\"" + customer.getEmail() + "\",");
                out.print("\"balance\":" + customer.getBalance());
                out.print("}");

            } else {

                out.print("{");
                out.print("\"status\":\"failed\",");
                out.print("\"message\":\"Invalid Account Number or Password\"");
                out.print("}");
            }

        } catch (Exception e) {

            e.printStackTrace();

            out.print("{");
            out.print("\"status\":\"error\",");
            out.print("\"message\":\"Server Error\"");
            out.print("}");
        }

        out.flush();
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }
}