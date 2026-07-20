package com.bank.api;

import com.bank.dao.UpiDAO;
import com.bank.model.Upi;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/upi")
public class UpiDetailsApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {

            String accountNumber = request.getParameter("accountNumber");

            if (accountNumber == null || accountNumber.trim().isEmpty()) {

                out.print("{\"status\":\"failed\",\"message\":\"Account Number Required\"}");
                return;
            }

            UpiDAO dao = new UpiDAO();

            Upi upi = dao.getUpiByAccountNumber(accountNumber);

            if (upi != null) {

                out.print("{");
                out.print("\"status\":\"success\",");
                out.print("\"customerId\":\"" + upi.getCustomerId() + "\",");
                out.print("\"accountNumber\":\"" + upi.getAccountNumber() + "\",");
                out.print("\"upiId\":\"" + upi.getUpiHandle() + "\",");
                out.print("\"upiPin\":\"" + (upi.getUpiPin() == null ? "" : upi.getUpiPin()) + "\",");
                out.print("\"upiStatus\":\"" + upi.getStatus() + "\"");
                out.print("}");

            } else {

                out.print("{");
                out.print("\"status\":\"failed\",");
                out.print("\"message\":\"UPI Not Found\"");
                out.print("}");
            }

        } catch (Exception e) {

            e.printStackTrace();

            out.print("{");
            out.print("\"status\":\"error\",");
            out.print("\"message\":\"Server Error\"");
            out.print("}");
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