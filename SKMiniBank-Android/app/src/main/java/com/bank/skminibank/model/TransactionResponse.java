package com.bank.skminibank.model;

import java.util.List;

public class TransactionResponse {
    private String status;
    private List<Transaction> transactions;

    public String getStatus() { return status; }
    public List<Transaction> getTransactions() { return transactions; }
}
