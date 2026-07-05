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
public class BalanceAPI extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {

            String accountNumber = request.getParameter("accountNumber");

            if (accountNumber == null || accountNumber.trim().isEmpty()) {

                out.print("{"
                        + "\"status\":\"failed\","
                        + "\"message\":\"Account Number Required\""
                        + "}");
                return;
            }

            CustomerDAO dao = new CustomerDAO();

            Customer customer =
                    dao.getCustomerByAccountNumber(accountNumber.trim());

            if (customer != null) {

                String json = "{"
                        + "\"status\":\"success\","
                        + "\"customerId\":\"" + customer.getCustomerId() + "\","
                        + "\"customerName\":\"" + customer.getFullName() + "\","
                        + "\"accountNumber\":\"" + customer.getAccountNumber() + "\","
                        + "\"balance\":\"" + customer.getBalance() + "\""
                        + "}";

                out.print(json);

            } else {

                out.print("{"
                        + "\"status\":\"failed\","
                        + "\"message\":\"Account Not Found\""
                        + "}");

            }

        } catch (Exception e) {

            e.printStackTrace();

            out.print("{"
                    + "\"status\":\"error\","
                    + "\"message\":\""
                    + e.getMessage().replace("\"", "'")
                    + "\""
                    + "}");

        } finally {

            out.flush();
            out.close();

        }

    }

}