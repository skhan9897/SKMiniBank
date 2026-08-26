package com.bank.skminibank.model;

public class AtmStatusResponse {
    private boolean success;
    private String message;
    private int requestId;
    private String accountNumber;
    private String cardType;
    private String status;
    private String requestDate;
    private String approvalDate;
    private String expectedDeliveryDate;
    private String dispatchedDate;
    private String deliveredDate;
    private String approvedBy;
    private String remarks;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public int getRequestId() { return requestId; }
    public String getAccountNumber() { return accountNumber; }
    public String getCardType() { return cardType; }
    public String getStatus() { return status; }
    public String getRequestDate() { return requestDate; }
    public String getApprovalDate() { return approvalDate; }
    public String getExpectedDeliveryDate() { return expectedDeliveryDate; }
    public String getDispatchedDate() { return dispatchedDate; }
    public String getDeliveredDate() { return deliveredDate; }
    public String getApprovedBy() { return approvedBy; }
    public String getRemarks() { return remarks; }
}
