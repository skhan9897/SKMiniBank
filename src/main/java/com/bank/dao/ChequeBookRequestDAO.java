package com.bank.dao;

import com.bank.model.ChequeBookRequest;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ChequeBookRequestDAO {

    private Connection con;

    public ChequeBookRequestDAO() {
        con = DBConnection.getConnection();
    }

    public boolean saveRequest(ChequeBookRequest cheque) {

        boolean status = false;

        try {

            String sql = "INSERT INTO cheque_book_request "
                    + "(account_number, customer_name, mobile, leaves, address, status) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, cheque.getAccountNumber());
            ps.setString(2, cheque.getCustomerName());
            ps.setString(3, cheque.getMobile());
            ps.setInt(4, cheque.getLeaves());
            ps.setString(5, cheque.getAddress());
            ps.setString(6, cheque.getStatus());

            int i = ps.executeUpdate();

            if (i > 0) {
                status = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

}