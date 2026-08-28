package com.bank.skminibank.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TransactionResponse {
    @SerializedName(value = "status", alternate = {"success"})
    private String status;

    @SerializedName(value = "transactions", alternate = {"data", "list", "history", "records"})
    private List<Transaction> transactions;

    public String getStatus() { return status; }
    public List<Transaction> getTransactions() { return transactions; }
}
