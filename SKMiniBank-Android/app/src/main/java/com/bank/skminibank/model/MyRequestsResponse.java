package com.bank.skminibank.model;

import java.util.List;

public class MyRequestsResponse {
    private String status;
    private List<ServiceRequest> requests;

    public String getStatus() { return status; }
    public List<ServiceRequest> getRequests() { return requests; }
}
