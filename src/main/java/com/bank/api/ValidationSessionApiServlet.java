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

@WebServlet("/api/validateSession")
public class ValidationSessionApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {

            String customerIdStr = request.getParameter("customerId");
            String accountNumber = request.getParameter("accountNumber");

            if (customerIdStr == null || customerIdStr.trim().isEmpty()
                    || accountNumber == null || accountNumber.trim().isEmpty()) {

                out.print("{\"status\":\"failed\",\"message\":\"Missing Parameters\"}");
                return;
            }

            int customerId = Integer.parseInt(customerIdStr);

            CustomerDAO dao = new CustomerDAO();

            // यह Method CustomerDAO में होना चाहिए
            Customer customer = dao.getCustomerById(customerId);

            if (customer != null
                    && accountNumber.equals(customer.getAccountNumber())) {

                out.print("{");
                out.print("\"status\":\"success\",");
                out.print("\"customerId\":" + customer.getCustomerId() + ",");
                out.print("\"customerName\":\"" + customer.getFullName() + "\",");
                out.print("\"accountNumber\":\"" + customer.getAccountNumber() + "\",");
                out.print("\"balance\":" + customer.getBalance());
                out.print("}");

            } else {

                out.print("{");
                out.print("\"status\":\"failed\",");
                out.print("\"message\":\"Invalid Session\"");
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