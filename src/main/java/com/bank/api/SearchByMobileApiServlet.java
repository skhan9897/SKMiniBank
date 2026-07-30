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

@WebServlet("/api/searchByMobile")
public class SearchByMobileApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
            String mobile = request.getParameter("mobile");

            if (mobile == null || mobile.trim().isEmpty()) {
                out.print("{\"status\":\"failed\",\"message\":\"Mobile Number Required\"}");
                return;
            }

            CustomerDAO dao = new CustomerDAO();
            Customer customer = dao.searchCustomerByMobile(mobile);

            if (customer != null) {
                // Ensure we return a valid UPI ID, even if not generated in DB yet
                String upiId = customer.getUpiId();
                if (upiId == null || upiId.isEmpty() || upiId.equalsIgnoreCase("null")) {
                    upiId = customer.getMobile() + "@skpay";
                }

                out.print("{");
                out.print("\"status\":\"success\",");
                out.print("\"customerId\":" + customer.getCustomerId() + ",");
                out.print("\"customerName\":\"" + customer.getFullName() + "\",");
                out.print("\"accountNumber\":\"" + customer.getAccountNumber() + "\",");
                out.print("\"upiId\":\"" + upiId + "\"");
                out.print("}");
            } else {
                out.print("{\"status\":\"failed\",\"message\":\"Receiver not found in SK Bank\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"status\":\"error\",\"message\":\"Server Error: " + e.getMessage() + "\"}");
        } finally {
            out.flush();
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
