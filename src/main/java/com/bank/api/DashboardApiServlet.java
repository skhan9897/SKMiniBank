package com.bank.api;

import com.bank.dao.CustomerDAO;
import com.bank.model.Customer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/dashboard")
public class DashboardApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {

            int customerId =
                    Integer.parseInt(request.getParameter("customerId"));

            CustomerDAO dao = new CustomerDAO();

            Customer c = dao.getCustomerById(customerId);

            if (c != null) {

                out.print("{");
                out.print("\"status\":\"success\",");
                out.print("\"customerName\":\"" + c.getFullName() + "\",");
                out.print("\"customerCode\":\"" + c.getCustomerCode() + "\",");
                out.print("\"accountNumber\":\"" + c.getAccountNumber() + "\",");
                out.print("\"accountType\":\"" + c.getAccountType() + "\",");
                out.print("\"branch\":\"" + c.getBranch() + "\",");
                out.print("\"balance\":" + c.getBalance() + ",");
                out.print("\"kycStatus\":\"" + c.getKycStatus() + "\",");
                out.print("\"upiId\":\"" + c.getUpiId() + "\",");
                out.print("\"upiStatus\":\"" + c.getUpiStatus() + "\"");
                out.print("}");

            } else {

                out.print("{\"status\":\"failed\"}");

            }

        } catch (Exception e) {

            e.printStackTrace();

            out.print("{\"status\":\"error\"}");

        }

        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }
}