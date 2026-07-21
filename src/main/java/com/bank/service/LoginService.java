package com.bank.service;

import com.bank.dao.LoginDAO;
import com.bank.model.Customer;

public class LoginService {

    private LoginDAO dao;

    public LoginService() {
        dao = new LoginDAO();
    }

    public Customer login(String accountNumber, String password) {

        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            return null;
        }

        if (password == null || password.trim().isEmpty()) {
            return null;
        }

        return dao.login(accountNumber, password);
    }
}