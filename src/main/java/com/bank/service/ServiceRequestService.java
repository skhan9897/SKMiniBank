package com.bank.service;

import com.bank.dao.ServiceRequestDAO;
import com.bank.model.ServiceRequest;

public class ServiceRequestService {

    private ServiceRequestDAO dao;

    public ServiceRequestService() {
        dao = new ServiceRequestDAO();
    }

    public boolean submitATMRequest(int customerId, String accountNumber, String cardType) {
        if (customerId <= 0 || accountNumber == null || accountNumber.trim().isEmpty() || cardType == null) return false;
        ServiceRequest request = new ServiceRequest();
        request.setCustomerId(customerId);
        request.setAccountNumber(accountNumber);
        request.setRequestType("ATM_CARD");
        request.setRequestDetails(cardType);
        return dao.saveRequest(request);
    }

    public boolean submitChequeBookRequest(int customerId, String accountNumber, String chequeType) {
        if (customerId <= 0 || accountNumber == null || chequeType == null) return false;
        ServiceRequest request = new ServiceRequest();
        request.setCustomerId(customerId);
        request.setAccountNumber(accountNumber);
        request.setRequestType("CHEQUE_BOOK");
        request.setRequestDetails(chequeType);
        return dao.saveRequest(request);
    }

    public boolean submitLoanRequest(int customerId, String accountNumber, String loanType, double amount, int tenure, double income, String purpose) {
        if (customerId <= 0 || accountNumber == null || loanType == null) return false;
        ServiceRequest request = new ServiceRequest();
        request.setCustomerId(customerId);
        request.setAccountNumber(accountNumber);
        request.setRequestType("LOAN");
        // Structured data for easier parsing in Status API
        request.setRequestDetails(String.format("type:%s|amount:%.2f|tenure:%d|income:%.2f|purpose:%s",
                                  loanType, amount, tenure, income, purpose));
        return dao.saveRequest(request);
    }

    public boolean submitNetBankingRequest(int customerId, String accountNumber) {
        if (customerId <= 0 || accountNumber == null) return false;
        ServiceRequest request = new ServiceRequest();
        request.setCustomerId(customerId);
        request.setAccountNumber(accountNumber);
        request.setRequestType("NET_BANKING");
        request.setRequestDetails("Internet Banking Activation");
        return dao.saveRequest(request);
    }

    public boolean submitMobileBankingRequest(int customerId, String accountNumber) {
        if (customerId <= 0 || accountNumber == null) return false;
        ServiceRequest request = new ServiceRequest();
        request.setCustomerId(customerId);
        request.setAccountNumber(accountNumber);
        request.setRequestType("MOBILE_BANKING");
        request.setRequestDetails("Mobile Banking Activation");
        return dao.saveRequest(request);
    }

    public boolean submitKYCRequest(int customerId, String accountNumber, String aadhaar, String pan) {
        if (customerId <= 0 || accountNumber == null) return false;
        ServiceRequest request = new ServiceRequest();
        request.setCustomerId(customerId);
        request.setAccountNumber(accountNumber);
        request.setRequestType("KYC_UPDATE");
        request.setRequestDetails("Aadhaar: " + aadhaar + ", PAN: " + pan);
        return dao.saveRequest(request);
    }
}
