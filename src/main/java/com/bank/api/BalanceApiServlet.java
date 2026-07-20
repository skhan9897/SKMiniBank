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

@WebServlet("/api/balance")
public class BalanceApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {

            int customerId = Integer.parseInt(request.getParameter("customerId"));

            CustomerDAO dao = new CustomerDAO();

            Customer customer = dao.getCustomerById(customerId);

            if (customer != null) {

                out.print("{");
                out.print("\"status\":\"success\",");
                out.print("\"accountNumber\":\"" + customer.getAccountNumber() + "\",");
                out.print("\"customerName\":\"" + customer.getFullName() + "\",");
                out.print("\"balance\":" + customer.getBalance());
                out.print("}");

            } else {

                out.print("{");
                out.print("\"status\":\"failed\"");
                out.print("}");

            }

        } catch (Exception e) {

            e.printStackTrace();

            out.print("{");
            out.print("\"status\":\"error\"");
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