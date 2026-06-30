package com.bank.dao;

import com.bank.model.ATMRequest;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ATMRequestDAO {

    Connection con = null;
    PreparedStatement ps = null;

    public boolean applyATM(ATMRequest atm) {

        boolean status = false;

        try {

            con = DBConnection.getConnection();

            String sql = "INSERT INTO atm_request(customer_id, card_type, request_date, status) VALUES(?,?,?,?)";

            ps = con.prepareStatement(sql);

            ps.setInt(1, atm.getCustomerId());
            ps.setString(2, atm.getCardType());
            ps.setString(3, atm.getRequestDate());
            ps.setString(4, atm.getStatus());

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