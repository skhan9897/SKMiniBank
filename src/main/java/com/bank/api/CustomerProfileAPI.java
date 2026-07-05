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

@WebServlet("/api/profile")
public class CustomerProfileAPI extends HttpServlet {

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

            Customer c = dao.getCustomerByAccountNumber(accountNumber.trim());

            if (c != null) {

                String json = "{"
                        + "\"status\":\"success\","
                        + "\"customerId\":\"" + c.getCustomerId() + "\","
                        + "\"customerCode\":\"" + c.getCustomerCode() + "\","
                        + "\"customerName\":\"" + c.getFullName() + "\","
                        + "\"fullName\":\"" + c.getFullName() + "\","
                        + "\"fatherName\":\"" + c.getFatherName() + "\","
                        + "\"motherName\":\"" + c.getMotherName() + "\","
                        + "\"email\":\"" + c.getEmail() + "\","
                        + "\"mobile\":\"" + c.getMobile() + "\","
                        + "\"aadhaar\":\"" + c.getAadhaar() + "\","
                        + "\"pan\":\"" + c.getPan() + "\","
                        + "\"address\":\"" + c.getAddress() + "\","
                        + "\"city\":\"" + c.getCity() + "\","
                        + "\"state\":\"" + c.getState() + "\","
                        + "\"pincode\":\"" + c.getPincode() + "\","
                        + "\"accountNumber\":\"" + c.getAccountNumber() + "\","
                        + "\"accountType\":\"" + c.getAccountType() + "\","
                        + "\"branch\":\"" + c.getBranch() + "\","
                        + "\"ifscCode\":\"" + c.getIfscCode() + "\","
                        + "\"balance\":\"" + c.getBalance() + "\","
                        + "\"upiId\":\"" + c.getUpiId() + "\","
                        + "\"upiStatus\":\"" + c.getUpiStatus() + "\","
                        + "\"kycStatus\":\"" + c.getKycStatus() + "\","
                        + "\"accountStatus\":\"" + c.getStatus() + "\""
                        + "}";

                out.print(json);

            } else {

                out.print("{"
                        + "\"status\":\"failed\","
                        + "\"message\":\"Customer Not Found\""
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