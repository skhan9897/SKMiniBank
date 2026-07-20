package com.bank.model;

public class Upi {

    private int upiId;
    private int customerId;
    private String accountNumber;
    private String upiHandle;
    private String upiPin;
    private String status;
    private String createdAt;

    public Upi() {
    }

    public int getUpiId() {
        return upiId;
    }

    public void setUpiId(int upiId) {
        this.upiId = upiId;
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

    public String getUpiHandle() {
        return upiHandle;
    }

    public void setUpiHandle(String upiHandle) {
        this.upiHandle = upiHandle;
    }

    public String getUpiPin() {
        return upiPin;
    }

    public void setUpiPin(String upiPin) {
        this.upiPin = upiPin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}