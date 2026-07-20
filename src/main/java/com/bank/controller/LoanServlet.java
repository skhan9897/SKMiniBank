package com.bank.controller;

import com.bank.dao.CustomerDAO;
import com.bank.dao.LoanDAO;
import com.bank.dao.LoanDocumentDAO;
import com.bank.dao.ServiceRequestDAO;
import com.bank.model.Customer;
import com.bank.model.Loan;
import com.bank.model.LoanDocument;
import com.bank.model.ServiceRequest;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/LoanServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class LoanServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "uploads/loan";

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int customerId =
                    Integer.parseInt(request.getParameter("customerId"));

            CustomerDAO customerDAO = new CustomerDAO();

            Customer customer =
                    customerDAO.getCustomerById(customerId);

            if (customer == null) {

                response.sendRedirect("admin/loan-apply.jsp?msg=invalidCustomer");
                return;

            }

            Loan loan = new Loan();

            loan.setCustomerId(customerId);
            loan.setAccountNumber(customer.getAccountNumber());
            loan.setCustomerName(customer.getFullName());

            loan.setLoanType(request.getParameter("loanType"));
            loan.setLoanAmount(Double.parseDouble(request.getParameter("loanAmount")));
            loan.setInterestRate(Double.parseDouble(request.getParameter("interestRate")));
            loan.setDurationYear(Integer.parseInt(request.getParameter("durationYear")));
            loan.setStatus("Pending");

            LoanDAO loanDAO = new LoanDAO();

            int loanId = loanDAO.applyLoanAndReturnId(loan);

            if (loanId == 0) {

                response.sendRedirect("admin/loan-apply.jsp?msg=failed");
                return;

            }

            String uploadPath =
                    getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;

            File folder = new File(uploadPath);

            if (!folder.exists()) {
                folder.mkdirs();
            }

            Part aadhaar = request.getPart("aadhaar");
            Part pan = request.getPart("pan");
            Part salarySlip = request.getPart("salarySlip");
            Part bankStatement = request.getPart("bankStatement");
            Part addressProof = request.getPart("addressProof");

            LoanDocument doc = new LoanDocument();

            doc.setLoanId(loanId);
            doc.setCustomerId(customerId);
            doc.setAccountNumber(customer.getAccountNumber());

            doc.setAadhaarFile(saveFile(aadhaar, uploadPath));
            doc.setPanFile(saveFile(pan, uploadPath));
            doc.setSalarySlipFile(saveFile(salarySlip, uploadPath));
            doc.setBankStatementFile(saveFile(bankStatement, uploadPath));
            doc.setAddressProofFile(saveFile(addressProof, uploadPath));

            doc.setVerificationStatus("Pending");
            doc.setRemarks("");

          LoanDocumentDAO documentDAO = new LoanDocumentDAO();

documentDAO.saveDocuments(doc);

// Save Service Request
ServiceRequest sr = new ServiceRequest();

sr.setCustomerId(customerId);
sr.setAccountNumber(customer.getAccountNumber());
sr.setRequestType("LOAN");

String details = "Loan Type: " + loan.getLoanType()
        + ", Amount: ₹" + loan.getLoanAmount()
        + ", Duration: " + loan.getDurationYear() + " Years";

sr.setRequestDetails(details);

ServiceRequestDAO serviceDAO = new ServiceRequestDAO();
serviceDAO.saveRequest(sr);

response.sendRedirect(request.getContextPath()
        + "/LoanListServlet?msg=success");
            response.sendRedirect(request.getContextPath()
                    + "/LoanListServlet?msg=success");

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect("admin/loan-apply.jsp?msg=error");

        }

    }

    private String saveFile(Part part, String uploadPath)
            throws Exception {

        if (part == null || part.getSize() == 0) {
            return "";
        }

        String fileName =
                System.currentTimeMillis()
                + "_"
                + part.getSubmittedFileName();

        part.write(uploadPath + File.separator + fileName);

        return fileName;
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);

    }

}