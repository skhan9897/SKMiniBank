package com.bank.controller;

import com.bank.dao.LoanDocumentDAO;
import com.bank.model.LoanDocument;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/LoanDocumentsServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class LoanDocumentsServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "uploads/loan-documents";

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int loanId = Integer.parseInt(request.getParameter("loanId"));
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        String accountNumber = request.getParameter("accountNumber");

        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;

        File uploadFolder = new File(uploadPath);

        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        Part aadhaar = request.getPart("aadhaar");
        Part pan = request.getPart("pan");
        Part salarySlip = request.getPart("salarySlip");
        Part bankStatement = request.getPart("bankStatement");
        Part addressProof = request.getPart("addressProof");

        String aadhaarFile = saveFile(aadhaar, uploadPath);
        String panFile = saveFile(pan, uploadPath);
        String salaryFile = saveFile(salarySlip, uploadPath);
        String bankFile = saveFile(bankStatement, uploadPath);
        String addressFile = saveFile(addressProof, uploadPath);

        LoanDocument doc = new LoanDocument();

        doc.setLoanId(loanId);
        doc.setCustomerId(customerId);
        doc.setAccountNumber(accountNumber);

        doc.setAadhaarFile(aadhaarFile);
        doc.setPanFile(panFile);
        doc.setSalarySlipFile(salaryFile);
        doc.setBankStatementFile(bankFile);
        doc.setAddressProofFile(addressFile);

        doc.setVerificationStatus("Pending");

        LoanDocumentDAO dao = new LoanDocumentDAO();

        boolean status = dao.saveDocuments(doc);

        if (status) {

            response.sendRedirect(request.getContextPath()
                    + "/LoanProfileServlet?loanId="
                    + loanId
                    + "&msg=documentsUploaded");

        } else {

            response.sendRedirect(request.getContextPath()
                    + "/LoanProfileServlet?loanId="
                    + loanId
                    + "&msg=failed");
        }
    }

    private String saveFile(Part part, String uploadPath)
            throws IOException {

        if (part == null || part.getSize() == 0) {
            return "";
        }

        String fileName = System.currentTimeMillis()
                + "_" + part.getSubmittedFileName();

        part.write(uploadPath + File.separator + fileName);

        return fileName;
    }
}