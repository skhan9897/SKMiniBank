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

            String customerIdStr = request.getParameter("customerId");

            if (customerIdStr == null || customerIdStr.trim().isEmpty()) {
                out.print("{\"status\":\"failed\",\"message\":\"Customer ID Required\"}");
                return;
            }

            int customerId = Integer.parseInt(customerIdStr);

            CustomerDAO dao = new CustomerDAO();
            Customer c = dao.getCustomerById(customerId);

            if (c != null) {

                out.print("{");
                out.print("\"status\":\"success\",");
                out.print("\"customerId\":" + c.getCustomerId() + ",");
                out.print("\"customerName\":\"" + safe(c.getFullName()) + "\",");
                out.print("\"customerCode\":\"" + safe(c.getCustomerCode()) + "\",");
                out.print("\"accountNumber\":\"" + safe(c.getAccountNumber()) + "\",");
                out.print("\"accountType\":\"" + safe(c.getAccountType()) + "\",");
                out.print("\"branch\":\"" + safe(c.getBranch()) + "\",");
                out.print("\"balance\":" + c.getBalance() + ",");
                out.print("\"accountStatus\":\"" + safe(c.getStatus()) + "\",");
                out.print("\"kycStatus\":\"" + safe(c.getKycStatus()) + "\",");
                out.print("\"upiId\":\"" + safe(c.getUpiId()) + "\",");
                out.print("\"upiStatus\":\"" + safe(c.getUpiStatus()) + "\"");
                out.print("}");

            } else {

                out.print("{\"status\":\"failed\",\"message\":\"Customer Not Found\"}");

            }

        } catch (Exception e) {

            e.printStackTrace();

            out.print("{\"status\":\"error\",\"message\":\"" + safe(e.getMessage()) + "\"}");

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

    private String safe(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"");
    }
}