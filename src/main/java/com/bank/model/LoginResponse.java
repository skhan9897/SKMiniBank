package com.bank.model;

public class LoginResponse {
    private String status;
    private String message;
    private int customerId;
    private String customerCode;
    private String customerName;
    private String accountNumber;
    private String mobile;
    private String email;
    private double balance;

    public LoginResponse() {}

    // Getters
    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public int getCustomerId() { return customerId; }
    public String getCustomerCode() { return customerCode; }
    public String getCustomerName() { return customerName; }
    public String getAccountNumber() { return accountNumber; }
    public String getMobile() { return mobile; }
    public String getEmail() { return email; }
    public double getBalance() { return balance; }

    // Setters
    public void setStatus(String status) { this.status = status; }
    public void setMessage(String message) { this.message = message; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public void setCustomerCode(String customerCode) { this.customerCode = customerCode; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public void setEmail(String email) { this.email = email; }
    public void setBalance(double balance) { this.balance = balance; }
}
