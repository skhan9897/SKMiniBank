package com.bank.api;

import com.bank.service.BillService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/bill/gas")
public class GasBillApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
            String customerIdStr = request.getParameter("customerId");
            String consumerId = request.getParameter("consumerId");
            String provider = request.getParameter("provider");
            String amountStr = request.getParameter("amount");

            if (customerIdStr == null || consumerId == null || provider == null || amountStr == null) {
                out.print("{\"status\":\"failed\",\"message\":\"Missing required fields\"}");
                return;
            }

            int customerId = Integer.parseInt(customerIdStr);
            double amount = Double.parseDouble(amountStr);

            BillService billService = new BillService();
            boolean success = billService.payBill(customerId, amount, "Gas Bill", provider + " - " + consumerId);

            if (success) {
                out.print("{\"status\":\"success\",\"message\":\"Gas Bill Paid Successfully\"}");
            } else {
                out.print("{\"status\":\"failed\",\"message\":\"Insufficient Balance or Invalid Account\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"status\":\"error\",\"message\":\"Server Error\"}");
        } finally {
            out.close();
        }
    }
}
