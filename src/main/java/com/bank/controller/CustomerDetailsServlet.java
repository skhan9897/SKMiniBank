package com.bank.controller;

import com.bank.dao.CustomerDAO;
import com.bank.model.Customer;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CustomerDetailsServlet")
public class CustomerDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (idParam == null || idParam.trim().isEmpty()) {
            out.print("{}");
            return;
        }

        int customerId = Integer.parseInt(idParam);

        CustomerDAO dao = new CustomerDAO();
        Customer c = dao.getCustomerById(customerId);

        if (c != null) {

            out.print("{");
            out.print("\"customerId\":\"" + c.getCustomerId() + "\",");
            out.print("\"fullName\":\"" + c.getFullName() + "\",");
            out.print("\"mobile\":\"" + c.getMobile() + "\",");
            out.print("\"email\":\"" + c.getEmail() + "\",");
            out.print("\"aadhaar\":\"" + c.getAadhaar() + "\",");
            out.print("\"pan\":\"" + c.getPan() + "\",");
            out.print("\"address\":\"" + c.getAddress() + "\"");
            out.print("}");

        } else {

            out.print("{}");

        }

        out.flush();
        out.close();
    }
}