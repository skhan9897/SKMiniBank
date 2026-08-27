package com.bank.controller;

import com.bank.dao.CustomerDAO;
import com.bank.model.Customer;
import com.bank.util.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;

@WebServlet("/RegisterServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Customer customer = new Customer();

            // Personal Details
            customer.setFullName(request.getParameter("fullName"));
            customer.setFatherName(request.getParameter("fatherName"));
            customer.setMotherName(request.getParameter("motherName"));
            customer.setDob(request.getParameter("dob"));
            customer.setGender(request.getParameter("gender"));
            customer.setMaritalStatus(request.getParameter("maritalStatus"));
            customer.setOccupation(request.getParameter("occupation"));

            // Contact Details
            customer.setMobile(request.getParameter("mobile"));
            customer.setAlternateMobile(request.getParameter("alternateMobile"));
            customer.setEmail(request.getParameter("email"));

            // Identity
            customer.setAadhaar(request.getParameter("aadhaar"));
            customer.setPan(request.getParameter("pan"));

            // Address
            customer.setAddress(request.getParameter("address"));
            customer.setCity(request.getParameter("city"));
            customer.setState(request.getParameter("state"));
            customer.setPincode(request.getParameter("pincode"));

            // Nominee
            customer.setNomineeName(request.getParameter("nomineeName"));
            customer.setRelationship(request.getParameter("relationship"));
            customer.setNomineeMobile(request.getParameter("nomineeMobile"));

            // Bank Details
            customer.setCustomerCode("SKC" + System.currentTimeMillis());
            customer.setCifNumber("CIF" + System.currentTimeMillis());
            customer.setAccountNumber("SKM" + System.currentTimeMillis());
            customer.setIfscCode("SKMB0001001");
            customer.setAccountType(request.getParameter("accountType"));
            customer.setBranch("Bareilly Main Branch");

            customer.setBalance(
                    Double.parseDouble(request.getParameter("balance")));

            customer.setUpiId(customer.getMobile() + "@skpay");
            customer.setUpiStatus("ACTIVE");
            customer.setStatus("ACTIVE");
            customer.setKycStatus("PENDING");

            customer.setPassword(request.getParameter("password"));
            customer.setTransactionPin(
                    request.getParameter("transactionPin"));

            // Photo Upload
            Part filePart = request.getPart("photo");
            String fileName = "default_user.png";
            if (filePart != null && filePart.getSize() > 0) {
                fileName = customer.getAccountNumber() + "_" + getFileName(filePart);
                String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads" + File.separator + "customer_photos";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdirs();
                filePart.write(uploadPath + File.separator + fileName);
            }
            customer.setPhoto(fileName);

            String sourcePage = request.getHeader("Referer");
            if (sourcePage == null || sourcePage.isEmpty()) {
                sourcePage = "register.jsp";
            }

            CustomerDAO dao = new CustomerDAO();
            boolean status = false;
            try {
                status = dao.addCustomer(customer);
            } catch (Exception e) {
                e.printStackTrace();
                String errorMsg = java.net.URLEncoder.encode(e.getMessage(), "UTF-8");
                if (sourcePage.contains("?")) {
                    response.sendRedirect(sourcePage + "&error=" + errorMsg);
                } else {
                    response.sendRedirect(sourcePage + "?error=" + errorMsg);
                }
                return;
            }

            if (!status) {
                String errorMsg = java.net.URLEncoder.encode("Registration Failed - DB Error", "UTF-8");
                if (sourcePage.contains("?")) {
                    response.sendRedirect(sourcePage + "&error=" + errorMsg);
                } else {
                    response.sendRedirect(sourcePage + "?error=" + errorMsg);
                }
                return;
            }

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement("INSERT INTO users(username,email,password,role,status) VALUES(?,?,?,?,?)")) {

                ps.setString(1, customer.getFullName());
                ps.setString(2, customer.getAccountNumber());
                ps.setString(3, customer.getPassword());
                ps.setString(4, "CUSTOMER");
                ps.setString(5, "ACTIVE");
                ps.executeUpdate();
            }

            // Get generated Customer ID
            int customerId = 0;
            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps2 = con.prepareStatement("SELECT customer_id FROM customer WHERE account_number=?")) {
                ps2.setString(1, customer.getAccountNumber());
                try (ResultSet rs = ps2.executeQuery()) {
                    if (rs.next()) {
                        customerId = rs.getInt("customer_id");
                    }
                }
            }

            // Receipt Open
            response.sendRedirect(request.getContextPath() + "/AccountReceiptServlet?customerId=" + customerId);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }
}
