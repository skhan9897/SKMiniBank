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

@WebServlet("/api/generateUpi")
public class GenerateUpiApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {

            String customerId = request.getParameter("customerId");
            String accountNumber = request.getParameter("accountNumber");

            if (customerId == null || customerId.trim().isEmpty()
                    || accountNumber == null || accountNumber.trim().isEmpty()) {

                out.print("{\"status\":\"failed\",\"message\":\"Customer ID and Account Number are required\"}");
                return;
            }

            UpiDAO dao = new UpiDAO();
            
            // If request has 'action=all', generate for everyone
            String action = request.getParameter("action");
            if ("all".equalsIgnoreCase(action)) {
                dao.generateUpiForAll();
                out.print("{\"status\":\"success\",\"message\":\"UPI IDs generated for all accounts using mobile number\"}");
                return;
            }

            Upi upi = new Upi();
            upi.setCustomerId(Integer.parseInt(customerId));
            upi.setAccountNumber(accountNumber);

            boolean status = dao.generateUpi(upi);

            if (status) {
                Upi generated = dao.getUpiByAccountNumber(accountNumber);
                out.print("{");
                out.print("\"status\":\"success\",");
                out.print("\"upiId\":\"" + generated.getUpiHandle() + "\",");
                out.print("\"message\":\"UPI Generated Successfully using Mobile Number\"");
                out.print("}");

            } else {

                out.print("{");
                out.print("\"status\":\"failed\",");
                out.print("\"message\":\"UPI already exists or generation failed\"");
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