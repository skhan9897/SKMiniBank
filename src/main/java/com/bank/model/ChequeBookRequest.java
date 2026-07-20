package com.bank.model;

public class ChequeBookRequest {

    private int id;
    private String accountNumber;
    private String customerName;
    private String mobile;
    private int leaves;
    private String address;
    private String status;
    private int customerId;
private String remarks;
private int approvedBy;
private java.sql.Timestamp approvalDate;
private java.sql.Date expectedDelivery;
private java.sql.Date dispatchedDate;
private java.sql.Date deliveredDate;
private java.sql.Timestamp requestDate;

    public ChequeBookRequest() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getLeaves() {
        return leaves;
    }

    public void setLeaves(int leaves) {
        this.leaves = leaves;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public int getCustomerId() {
    return customerId;
}

public void setCustomerId(int customerId) {
    this.customerId = customerId;
}

public String getRemarks() {
    return remarks;
}

public void setRemarks(String remarks) {
    this.remarks = remarks;
}

public int getApprovedBy() {
    return approvedBy;
}

public void setApprovedBy(int approvedBy) {
    this.approvedBy = approvedBy;
}

public java.sql.Timestamp getApprovalDate() {
    return approvalDate;
}

public void setApprovalDate(java.sql.Timestamp approvalDate) {
    this.approvalDate = approvalDate;
}

public java.sql.Date getExpectedDelivery() {
    return expectedDelivery;
}

public void setExpectedDelivery(java.sql.Date expectedDelivery) {
    this.expectedDelivery = expectedDelivery;
}

public java.sql.Date getDispatchedDate() {
    return dispatchedDate;
}

public void setDispatchedDate(java.sql.Date dispatchedDate) {
    this.dispatchedDate = dispatchedDate;
}

public java.sql.Date getDeliveredDate() {
    return deliveredDate;
}

public void setDeliveredDate(java.sql.Date deliveredDate) {
    this.deliveredDate = deliveredDate;
}

public java.sql.Timestamp getRequestDate() {
    return requestDate;
}

public void setRequestDate(java.sql.Timestamp requestDate) {
    this.requestDate = requestDate;
}

}