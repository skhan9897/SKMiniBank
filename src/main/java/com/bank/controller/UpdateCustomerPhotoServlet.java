package com.bank.controller;

import com.bank.dao.CustomerDAO;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/UpdateCustomerPhotoServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)
public class UpdateCustomerPhotoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int customerId = 0;
        try {
            // In Multipart, some servers need to access parts first
            Part idPart = request.getPart("customerId");
            if (idPart != null) {
                // Reading string from part
                java.util.Scanner s = new java.util.Scanner(idPart.getInputStream()).useDelimiter("\\A");
                String idStr = s.hasNext() ? s.next() : "";
                if (!idStr.isEmpty()) customerId = Integer.parseInt(idStr);
            } else {
                customerId = Integer.parseInt(request.getParameter("customerId"));
            }

            Part filePart = request.getPart("photo");

            if (filePart != null && filePart.getSize() > 0) {
                String fileName = "cust_" + customerId + "_" + System.currentTimeMillis() + ".jpg";
                
                String uploadPath = getServletContext().getRealPath("/") + "uploads" + File.separator + "customer_photos";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                filePart.write(uploadPath + File.separator + fileName);

                CustomerDAO dao = new CustomerDAO();
                boolean success = dao.updatePhoto(customerId, fileName);

                if (success) {
                    response.sendRedirect("admin/customer-profile.jsp?customerId=" + customerId + "&msg=photo_success");
                } else {
                    response.sendRedirect("admin/customer-profile.jsp?customerId=" + customerId + "&msg=photo_failed");
                }
            } else {
                response.sendRedirect("admin/customer-profile.jsp?customerId=" + customerId + "&msg=no_file");
            }
        } catch (Exception e) {
            e.printStackTrace();
            String redirectUrl = "admin/customer-list.jsp?error=upload_error";
            if (customerId != 0) {
                redirectUrl = "admin/customer-profile.jsp?customerId=" + customerId + "&error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8");
            }
            response.sendRedirect(redirectUrl);
        }
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "photo.png";
    }
}
