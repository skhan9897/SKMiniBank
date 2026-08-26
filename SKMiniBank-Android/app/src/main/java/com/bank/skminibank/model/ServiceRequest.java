package com.bank.skminibank.model;

public class ServiceRequest {
    private int requestId;
    private int customerId;
    private String accountNumber;
    private String requestType;
    private String requestDetails;
    private String status;
    private String remarks;
    private String approvedBy;
    private String requestDate;
    private String approvalDate;
    private String expectedDeliveryDate;
    private String dispatchedDate;
    private String deliveredDate;

    public int getRequestId() { return requestId; }
    public int getCustomerId() { return customerId; }
    public String getAccountNumber() { return accountNumber; }
    public String getRequestType() { return requestType; }
    public String getRequestDetails() { return requestDetails; }
    public String getStatus() { return status; }
    public String getRemarks() { return remarks; }
    public String getApprovedBy() { return approvedBy; }
    public String getRequestDate() { return requestDate; }
    public String getApprovalDate() { return approvalDate; }
    public String getExpectedDeliveryDate() { return expectedDeliveryDate; }
    public String getDispatchedDate() { return dispatchedDate; }
    public String getDeliveredDate() { return deliveredDate; }
}
