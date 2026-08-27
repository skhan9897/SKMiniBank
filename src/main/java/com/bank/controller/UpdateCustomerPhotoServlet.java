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

        int customerId = Integer.parseInt(request.getParameter("customerId"));
        String accountNumber = request.getParameter("accountNumber");

        Part filePart = request.getPart("photo");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = accountNumber + "_update_" + System.currentTimeMillis() + "_" + getFileName(filePart);
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads" + File.separator + "customer_photos";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

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
