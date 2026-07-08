package com.bank.controller;

import com.bank.dao.CustomerDAO;
import com.bank.dao.LoanDAO;
import com.bank.model.Customer;
import com.bank.model.Loan;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoanServlet")
public class LoanServlet extends HttpServlet {

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

            // Customer Table se Auto Mapping
            loan.setAccountNumber(customer.getAccountNumber());
            loan.setCustomerName(customer.getFullName());

            loan.setLoanType(request.getParameter("loanType"));

            loan.setLoanAmount(
                    Double.parseDouble(request.getParameter("loanAmount")));

            loan.setInterestRate(
                    Double.parseDouble(request.getParameter("interestRate")));

            loan.setDurationYear(
                    Integer.parseInt(request.getParameter("durationYear")));

            loan.setStatus("Pending");

            LoanDAO dao = new LoanDAO();

            boolean status = dao.applyLoan(loan);

            if (status) {

                response.sendRedirect("/LoanListServlet?msg=success");

            } else {

                response.sendRedirect("admin/loan-apply.jsp?msg=failed");

            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect("admin/loan-apply.jsp?msg=error");

        }

    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);

    }

}