package com.bank.service;

import com.bank.dao.AccountDAO;
import com.bank.dao.CustomerDAO;
import com.bank.dao.TransactionDAO;
import com.bank.model.Account;
import com.bank.model.Customer;

public class BillService {

    public boolean payBill(int customerId, double amount, String billType, String consumerDetails) {
        CustomerDAO customerDAO = new CustomerDAO();
        AccountDAO accountDAO = new AccountDAO();
        TransactionDAO transactionDAO = new TransactionDAO();

        Customer customer = customerDAO.getCustomerById(customerId);
        if (customer == null || !"ACTIVE".equalsIgnoreCase(customer.getStatus())) {
            return false;
        }

        if (customer.getBalance() < amount) {
            return false;
        }

        // Deduct balance
        boolean success = accountDAO.withdraw(customer.getAccountNumber(), amount);
        
        if (success) {
            // Record Transaction
            double newBalance = customer.getBalance() - amount;
            transactionDAO.saveUpiTransaction(
                customer.getAccountNumber(),
                customer.getFullName(),
                billType + " (" + consumerDetails + ")",
                amount,
                newBalance,
                "Bill Payment - " + consumerDetails
            );
            return true;
        }

        return false;
    }
}
