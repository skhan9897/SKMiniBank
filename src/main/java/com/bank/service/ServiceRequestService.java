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
    
    public boolean submitChequeBookRequest(
        int customerId,
        String accountNumber,
        String chequeType) {

    try {

        if (customerId <= 0) {
            return false;
        }

        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            return false;
        }

        if (chequeType == null || chequeType.trim().isEmpty()) {
            return false;
        }

        ServiceRequest request = new ServiceRequest();

        request.setCustomerId(customerId);
        request.setAccountNumber(accountNumber.trim());

        // IMPORTANT:
        // Har jagah exactly same request type use karenge
        request.setRequestType("CHEQUE_BOOK");

        // ATM me cardType jaise store ho raha tha,
        // yahan chequeType store hoga
        request.setRequestDetails(chequeType.trim());

        ServiceRequestDAO dao = new ServiceRequestDAO();

        return dao.saveRequest(request);

    } catch (Exception e) {

        e.printStackTrace();
        return false;
    }
}
}