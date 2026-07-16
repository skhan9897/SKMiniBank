package com.bank.model;

import java.sql.Date;

public class Loan {

    private int loanId;
    private int customerId;

    private String accountNumber;
    private String customerName;

    private String loanType;

    private double loanAmount;
    private double interestRate;

    private int durationYear;

    private String status;

    private Date applyDate;
    
    private String applicantCategory;

public String getApplicantCategory() {
    return applicantCategory;
}

public void setApplicantCategory(String applicantCategory) {
    this.applicantCategory = applicantCategory;
}

    public Loan() {
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getDurationYear() {
        return durationYear;
    }

    public void setDurationYear(int durationYear) {
        this.durationYear = durationYear;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

}
