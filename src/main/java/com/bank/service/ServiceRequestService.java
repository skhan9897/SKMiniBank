package com.bank.service;

import com.bank.dao.ServiceRequestDAO;
import com.bank.model.ServiceRequest;

public class ServiceRequestService {

    private ServiceRequestDAO dao;

    public ServiceRequestService() {
        dao = new ServiceRequestDAO();
    }

    // Common Method (Website + API)
    public boolean submitATMRequest(int customerId,
                                    String accountNumber,
                                    String cardType) {

        if (customerId <= 0
                || accountNumber == null || accountNumber.trim().isEmpty()
                || cardType == null || cardType.trim().isEmpty()) {
            return false;
        }

        ServiceRequest request = new ServiceRequest();

        request.setCustomerId(customerId);
        request.setAccountNumber(accountNumber);
        request.setRequestType("ATM_CARD");
        request.setRequestDetails(cardType);

        return dao.saveRequest(request);
    }
}